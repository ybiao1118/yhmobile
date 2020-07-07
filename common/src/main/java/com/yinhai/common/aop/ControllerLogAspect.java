package com.yinhai.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.common.annotation.ControllerLog;
import com.yinhai.common.config.CommonConfig;
import com.yinhai.common.constant.ConstRequestBody;
import com.yinhai.common.constant.ConstRequestHeader;
import com.yinhai.common.entity.ErrLog;
import com.yinhai.common.entity.OperationalLog;
import com.yinhai.common.entity.result.ErrorResult;
import com.yinhai.common.entity.result.FailResult;
import com.yinhai.common.entity.result.JsonResult;
import com.yinhai.common.exception.BaseException;
import com.yinhai.common.service.AsynLogService;
import com.yinhai.common.util.*;
import org.apache.http.entity.ContentType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/** 日志记录及加解密切面类
 * @author yanbiao
 * @since 2019/11/11 23:08
 */
@Aspect
@Component
public class ControllerLogAspect {
    @Value("${spring.profiles.active}")
    private String mode;
    @Value("${common.isEncrypt}")
    private String isEncrypt;
    @Autowired
    private AsynLogService asynLogService;
    @Autowired
    private CommonConfig commonConfig;
    @Autowired
    private JwtUtil jwtUtil;

    private static final String TRUE = "true";

    @Pointcut("@annotation(com.yinhai.common.annotation.ControllerLog)")
    public void logPointCut() {
    }

    /**
     * 环绕通知
     * @param point 切点
     * @return 返回对象
     */
    @Around("logPointCut()")
    public JsonResult around(ProceedingJoinPoint point) {
        //System.out.println("=======around=====");
        //入参是否加密
        boolean paramIsEncrypt=true;
        //返回参数是否加密
        boolean resultIsEncrypt = true;
        JSONObject paramJson = new JSONObject();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        OperationalLog operlog = new OperationalLog();
        ControllerLog controllerLog = method.getAnnotation(ControllerLog.class);
        if (controllerLog != null) {
            String operModule = controllerLog.operModule();
            operlog.setModel(operModule);
            //返回参数是否加密
            resultIsEncrypt = controllerLog.resultIsEncrypt();
            //入参是否加密
            paramIsEncrypt=controllerLog.paramIsEncrypt();
        }
        //获取请求的类名
        String className = point.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        operlog.setAction(className + "." + methodName);
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //前端加密参数
        try {
            String data = "";
            String type = request.getMethod();
            String contentType = request.getContentType();
            if(contentType!=null&&!"".equals(contentType)){
                if(ContentType.APPLICATION_JSON.toString().contains(contentType)){
                    data = getRequestJson(request);
                }
            } else{
                data = request.getParameter(ConstRequestBody.DATA);
            }
            if (!StringUtils.isEmpty(data)) {
                //判断是否是加密模式
                if (TRUE.equals(isEncrypt)&&paramIsEncrypt) {
                    String params = "";
                    //将前端参数解密
                    params = RsaUtil.decrypt(data, RsaUtil.getPrivateKey(commonConfig.rsaPriKey));
                    params= URLDecoder.decode(params, "utf-8");
                    if (!StringUtils.isEmpty(params)) {
                        //解密成功
                        JSONObject jsonObject = JSON.parseObject(params);
                        /*
                        String sign = jsonObject.getString(ConstRequestBody.DATA_SIGN);
                        jsonObject.remove(ConstRequestBody.DATA_SIGN);
                        //验签
                       boolean flag = RsaUtil.verify(URLEncoder.encode(RsaUtil.getSignString(jsonObject),"utf-8"), RsaUtil.getPublicKey(commonConfig.rsaPubKey), sign);
                        //验签成功
                        if (flag) {
                            //System.out.println("验签成功=====");
                            paramJson.putAll(jsonObject);
                            paramJson.put(ConstRequestHeader.SOURCE,request.getHeader(ConstRequestHeader.SOURCE));
                        } else {//验签失败
                            //System.out.println("验签失败=====");
                            return new FailResult("参数验签失败",null);
                        }*/
                        //System.out.println("解密成功:params=====" + params);
                        paramJson.putAll(jsonObject);
                        paramJson.put(ConstRequestHeader.SOURCE,request.getHeader(ConstRequestHeader.SOURCE));
                    } else {
                        return new FailResult("数据解密失败",null);
                    }
                } else {
                    paramJson = JSON.parseObject(data);
                }
            }
            operlog.setMethod(request.getMethod());
            //将参数解密后放入controller参数中
            Object[] args = point.getArgs();
            args[0] = paramJson.toJSONString();
            // 获取执行结果
            JsonResult jsonResult = (JsonResult) point.proceed(args);

            //记录入参日志
            operlog.setReqParam(paramJson.toJSONString());
            if (null != jsonResult) {
                operlog.setStatusCode(String.valueOf(jsonResult.getStatus()));
                operlog.setRespParam(JSON.toJSONString(jsonResult));
                operlog.setDesc(jsonResult.getMsg());
            }
            String url = request.getRequestURI();
            //判断是否是加密模式和判断返回参数是否加密
            if(TRUE.equals(isEncrypt)&&resultIsEncrypt){
                //获取返回参数
                Object resultData = jsonResult.getData();
                if (null != resultData) {
                    //对参数加密
                    String result = RsaUtil.encrypt(URLEncoder.encode(resultData.toString(),"utf-8"), RsaUtil.getPublicKey(commonConfig.rsaPubKey));
                    jsonResult.setData(result);
                }
            }

            operlog.setIp(IpUtil.getIpAdrress(request));
            operlog.setUrl(url);
            operlog.setCreateTime(new Date());
            operlog.setSource(request.getHeader(ConstRequestHeader.SOURCE));
            operlog.setVersion(request.getHeader(ConstRequestHeader.VERSION));
            operlog.setRelease(mode);
            asynLogService.insertOperationalLog(operlog);
            return jsonResult;
        } catch (Throwable e) {
            try {
                ErrLog errLog = new ErrLog();
                // 将参数所在的数组转换成json
                errLog.setReqParam(paramJson.toJSONString());
                errLog.setAction(className + "." + methodName);
                if (e instanceof BaseException) {
                    BaseException exception = (BaseException) e;
                    errLog.setExcName(exception.getExceptionName());
                    errLog.setExcMsg(ExceptionUtil.stackTraceToString(exception.getExceptionName(),
                            exception.getExceptionMessage(), exception.getElements()));
                } else {
                    errLog.setExcName(e.getClass().getName());
                    errLog.setExcMsg(ExceptionUtil.stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
                }
                errLog.setUrl(request.getRequestURI());
                errLog.setIp(IpUtil.getIpAdrress(request));
                errLog.setRelease(mode);
                errLog.setCreateTime(new Date());
                errLog.setSource(request.getHeader(ConstRequestHeader.SOURCE));
                errLog.setVersion(request.getHeader(ConstRequestHeader.VERSION));
                if (controllerLog != null) {
                    String operModule = controllerLog.operModule();
                    errLog.setModel(operModule);
                }
                asynLogService.insertErrLog(errLog);
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return new ErrorResult();
            }
            return new ErrorResult();
        }
    }

    /** 从json类请求中获取入参*/
    private String getRequestJson(HttpServletRequest request){
        String result="";
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            result = new String(outSteam.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}
