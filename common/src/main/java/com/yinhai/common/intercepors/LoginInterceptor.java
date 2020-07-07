package com.yinhai.common.intercepors;

import com.alibaba.fastjson.JSON;
import com.yinhai.common.config.CommonConfig;
import com.yinhai.common.constant.ConstRequestBody;
import com.yinhai.common.constant.ConstRequestHeader;
import com.yinhai.common.entity.ErrLog;
import com.yinhai.common.entity.JwtAccount;
import com.yinhai.common.entity.result.ErrorResult;
import com.yinhai.common.entity.result.FailResult;
import com.yinhai.common.entity.result.JsonResult;
import com.yinhai.common.enums.RedisBusinessTypeEnum;
import com.yinhai.common.enums.ResultEnum;
import com.yinhai.common.service.AsynLogService;
import com.yinhai.common.util.ExceptionUtil;
import com.yinhai.common.util.IpUtil;
import com.yinhai.common.util.JwtUtil;
import com.yinhai.common.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/** 登录拦截器,验证token
 * @author yanbiao
 * @since 2019/10/19 21:41
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${common.source}")
    private String source;
    @Value("${common.whiteIP}")
    private String whiteIp;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CommonConfig config;
    @Autowired
    private AsynLogService asynLogService;
	/** 时间戳最大差*/
	private static final int LIMIT_TIME = 1000 * 60*10;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //System.out.println("拦截器开始时间======"+ formatter.format(ZonedDateTime.now()));

        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        // 获取token
        String token = request.getHeader(ConstRequestHeader.TOKEN);
		String reqSource = request.getHeader(ConstRequestHeader.SOURCE);
		if(null != token&&!"".equals(token)){
			try{
				//验证token有效性
				try {
					JwtAccount jwt=jwtUtil.parseJwt(token);
					String userId=jwt.getAppId();
					//获取存在redis里的token
					String refreshJwt = redisUtil.get(RedisBusinessTypeEnum.TOKEN +reqSource+ userId)+"";
					//当redis里的token和传过来的token一致则代表登录成功
					if(refreshJwt!=null&&refreshJwt!=""&&token.equals(refreshJwt)){
						return true;
					}else{
						//用户未登录,重新登录
						writer = response.getWriter();
						writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL_NOTLOGIN)));
						return false;
					}

				}
				catch (ExpiredJwtException e) {
					// 如果是JWT过期
					// 当存储在redis的JWT没有过期，即refresh time 没有过期
					//获取token中的userId
					String userId = e.getClaims().getSubject();
					//System.out.println("appId============" + userId);
					String refreshJwt = redisUtil.get(RedisBusinessTypeEnum.TOKEN +reqSource+ userId)+"";
					if (refreshJwt!=null&&refreshJwt!=""&&token.equals(refreshJwt)) {
						// 重新申请新的JWT
						String newJwt = jwtUtil.createJwt(UUID.randomUUID().toString(), userId);
						// 将签发的JWT存储到Redis
						redisUtil.set(RedisBusinessTypeEnum.TOKEN +reqSource+ userId, newJwt,config.jwtLongTime*24*60 * 60 * 1000);
						writer = response.getWriter();
						writer.write(JSON.toJSONString(new JsonResult(ResultEnum.ILLEGAL_OVERTIME,"新jwt",newJwt)));
						//System.out.println("拦截器重发jwt时间======"+formatter.format(ZonedDateTime.now()));
						return false;
					} else {
						// jwt时间失效过期,jwt refresh time失效 返回jwt过期客户端重新登录
						writer = response.getWriter();
						writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL_NOTLOGIN)));
						return false;
					}
				}
				catch (Exception e) {
					// 其他错误
					writer = response.getWriter();
					writer.write(JSON.toJSONString(new ErrorResult()));
					try{
						// 请求的参数
						String data = request.getParameter(ConstRequestBody.DATA);
						ErrLog excepLog = new ErrLog();
						// 将参数所在的数组转换成json
						excepLog.setReqParam(data);
						excepLog.setExcName(e.getClass().getName());
						excepLog.setExcMsg(ExceptionUtil.stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
						excepLog.setUrl(request.getRequestURI());
						excepLog.setIp(IpUtil.getIpAdrress(request));
						excepLog.setRelease(reqSource);
						excepLog.setCreateTime(new Date());
						asynLogService.insertErrLog(excepLog);
						e.printStackTrace();
					}catch (Throwable throwable){
						throwable.printStackTrace();
					}
					return false;
				}
			}
			catch (Exception e){
				writer = response.getWriter();
				writer.write(JSON.toJSONString(new ErrorResult()));
				try{
					// 请求的参数
					String data = request.getParameter(ConstRequestBody.DATA);
					ErrLog excepLog = new ErrLog();
					// 将参数所在的数组转换成json
					excepLog.setReqParam(data);
					excepLog.setExcName(e.getClass().getName());
					// 异常信息
					excepLog.setExcMsg(ExceptionUtil.stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
					excepLog.setUrl(request.getRequestURI());
					excepLog.setIp(IpUtil.getIpAdrress(request));
					excepLog.setRelease(reqSource);
					excepLog.setCreateTime(new Date());
					asynLogService.insertErrLog(excepLog);
					e.printStackTrace();
				}catch (Throwable throwable){
					throwable.printStackTrace();
					writer = response.getWriter();
					writer.write(JSON.toJSONString(new ErrorResult()));
				}
				return false;
			}
		}else{
			writer = response.getWriter();
			writer.write(JSON.toJSONString(new FailResult(ResultEnum.ILLEGAL_NOTLOGIN)));
			return false;
		}

    }
}
