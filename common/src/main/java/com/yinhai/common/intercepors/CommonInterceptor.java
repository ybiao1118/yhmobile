package com.yinhai.common.intercepors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.common.config.CommonConfig;
import com.yinhai.common.constant.ConstRequestHeader;
import com.yinhai.common.entity.ErrLog;
import com.yinhai.common.entity.result.ErrorResult;
import com.yinhai.common.entity.result.FailResult;
import com.yinhai.common.enums.RedisBusinessTypeEnum;
import com.yinhai.common.enums.ResultEnum;
import com.yinhai.common.service.AsynLogService;
import com.yinhai.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/** 普通请求拦截器,校验消息头
 * @author yanbiao
 * @since 2019/10/19 21:41
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Value("${spring.profiles.active}")
    private String mode;
    @Value("${common.source}")
    private String source;
	/**
	 * 是否启用白名单
	 */
	@Value("${common.useWhiteIP}")
	private String useWhiteIP;
    @Value("${common.whiteIP}")
    private String whiteIp;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonConfig config;
    @Autowired
    private AsynLogService asynLogService;

    /** 时间戳最大差*/
    private static final int LIMIT_TIME = 1000 * 60*10;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//System.out.println("=======common interceptor=====");
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer;
        // 获取时间戳
        String timestamp = request.getHeader(ConstRequestHeader.TIMESTAMP);
        // 获取随机字符串
        String nonce = request.getHeader(ConstRequestHeader.NONCE);
        String reqSource = request.getHeader(ConstRequestHeader.SOURCE);
        // 请求版本
        String version = request.getHeader(ConstRequestHeader.VERSION);
        // 获取签名
        String signature = request.getHeader(ConstRequestHeader.SIGNATURE);
		// 获取token
		String token = request.getHeader(ConstRequestHeader.TOKEN);
        //System.out.println("消息头signature===="+signature);
        try{
			// 判断参数是否为空
			if (StringUtils.isEmpty(timestamp) ||
					StringUtils.isEmpty(nonce) ||
					StringUtils.isEmpty(reqSource) ||
					StringUtils.isEmpty(version) ||
					StringUtils.isEmpty(signature)) {
				//非法请求
				writer = response.getWriter();
				writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL,ResultEnum.ILLEGAL.getMsg())));
				return false;
			}

			// 判断请求的version参数是否正确
			String[] sourceItem = source.split(",");
			//是否合法来源
			boolean legalFlag = false;
			for (String item: sourceItem) {
				if(item.equals(reqSource)){
					legalFlag = true;
					break;
				}
			}
			if (!legalFlag) {
				writer = response.getWriter();
				writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL_CLIENT)));
				return false;
			}

			// 判断时间是否大于60秒
			if ((System.currentTimeMillis()-Long.parseLong(timestamp)) > LIMIT_TIME) {
				//请求超时(防止重放攻击)
				writer = response.getWriter();
				writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL_OVERTIME)));
				return false;
			}

			// 判断该用户的nonceStr参数是否已经在redis中
			if (redisUtil.hasKey(RedisBusinessTypeEnum.HEADER_NONCE +nonce)) {
				//请求仅一次有效（防止短时间内的重放攻击）
				writer = response.getWriter();
				writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL_FREQUENCE)));
				return false;
			}
			if("true".equals(useWhiteIP)){
				//System.out.println("ip地址==========="+ IpUtil.getIpAdrress(request));
				//判断请求ip，是否是白名单ip
				String[] whiteIps = whiteIp.split(",");
				boolean isNotAllowIp = true;
				for (String ip:whiteIps) {
					if(ip.equals(IpUtil.getIpAdrress(request))){
						isNotAllowIp = false;
						break;
					}
				}
				if(isNotAllowIp){
					writer = response.getWriter();
					writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL)));
					return false;
				}
			}
			//TODO 请求仅一次有效（防止短时间内的重放攻击）

			// 如果签名验证不通过
			JSONObject headerParamJo=new JSONObject();
			headerParamJo.put(ConstRequestHeader.TIMESTAMP,timestamp);
			headerParamJo.put(ConstRequestHeader.NONCE,nonce);
			headerParamJo.put(ConstRequestHeader.SOURCE,reqSource);
			headerParamJo.put(ConstRequestHeader.VERSION,version);
			if(token!=null&&!"".equals(token)){
				headerParamJo.put(ConstRequestHeader.TOKEN,token);
			}
			// 对请求头参数进行签名
			////System.out.println("==签名开始： "+(new Date()).getTime());
			String sign = Aes256Util.getAesDecrypt(signature,config.aesSignKey);
			JSONObject signJson=JSONObject.parseObject(sign);
			if (!headerParamJo.equals(signJson)) {
				//非法请求（防止请求参数被篡改）
				writer = response.getWriter();
				writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL_PARAM)));
				//System.out.println("验签失败====");
				return false;
			}
			//System.out.println("验签成功====");
			// 将本次用户请求的nonceStr参数存到redis中设置60秒后自动删除
			redisUtil.set(RedisBusinessTypeEnum.HEADER_NONCE+nonce,nonce, 60);
			return true;
        }
        catch (Exception e){
            writer = response.getWriter();
            writer.write(JSON.toJSONString(new ErrorResult()));
            try{
                // 请求的参数
                String data = request.getParameter("data");
                ErrLog excepLog = new ErrLog();
                // 将参数所在的数组转换成json
                excepLog.setReqParam(data);
                excepLog.setExcName(e.getClass().getName());
                excepLog.setExcMsg(ExceptionUtil.stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
                excepLog.setUrl(request.getRequestURI());
                excepLog.setIp(IpUtil.getIpAdrress(request));
                excepLog.setRelease(version);
                excepLog.setCreateTime(new Date());
                asynLogService.insertErrLog(excepLog);
                e.printStackTrace();
            }catch (Throwable throwable){
                throwable.printStackTrace();
            }
            return false;
        }
    }

}
