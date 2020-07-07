package com.yinhai.common.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.common.config.SoapConfig;
import com.yinhai.common.entity.ExteriorLog;
import com.yinhai.common.entity.result.FailResult;
import com.yinhai.common.entity.result.JsonResult;
import com.yinhai.common.entity.result.SuccessResult;
import com.yinhai.common.enums.HiipAPIEnum;
import com.yinhai.common.enums.HiipAPIReturnCodeEnum;
import com.yinhai.common.enums.ResultEnum;
import com.yinhai.common.service.AsynLogService;
import com.yinhai.common.constant.ConstHiipResoStr;
import com.yinhai.common.util.HttpUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/** Service基础类
 * @author : zhang
 * @date : 2019/7/17
 */
@Setter
public abstract class BaseService {
	@Autowired
	private HttpUtil httpUtil;
	@Autowired
	private AsynLogService asynLogService;
	@Autowired
	private SoapConfig config;
	@Value("${spring.profiles.active}")
	private String version;
	/** 平台的soap头 */
	private static String SOAP_TITLE_CXF="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:int=\"http://interfaces.yinhai.com/\">";
	/** 非平台的soap头 */
	private static String SOAP_TITLE_XFIRE="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:int=\"http://interfaces.yinhai.com\">";

	/** 调用平台业务接口 */
	protected String callPlatBusiness(HiipAPIEnum action, String param) throws Exception{
		String result="";
			Long startTime=System.currentTimeMillis();
			String requestXml=parseHiipSoapRequestXml(action,parseHiipRequestXml(param),
					SOAP_TITLE_CXF,
					config.platUserKey,
					config.platUserName,
					config.platPassword);
			String resultXml=httpUtil.doPost(config.platUrl, requestXml);

			//将xml转成json字符串
			result = httpUtil.result2Json(resultXml).toString();
			//System.out.println("result===="+result);
			JSONObject jsonObject= JSONObject.parseObject(result);
			Long endTime=System.currentTimeMillis();
			ExteriorLog log=new ExteriorLog();
			log.setAction(action.getCode());
			log.setReqParam(requestXml);
			log.setRespParam(resultXml);
			log.setCreateTime(new Date());
			log.setRelease(version);
			log.setResultCode(jsonObject.getString("resultCode"));
			log.setResultDesc(jsonObject.getString("resultDesc"));
			log.setResponseCode(jsonObject.getString("responseCode"));
			log.setResponseTime(String.valueOf(endTime-startTime));
			asynLogService.insertExteriorLog(log);
		return result;
	}
	/** 调用非平台接口 */
	protected String callNonPlatBusiness(HiipAPIEnum action, String param) throws Exception {
		String result="";
			Long startTime=System.currentTimeMillis();
			String requestXml=parseHiipSoapRequestXml(action,parseHiipRequestXml(param),
				SOAP_TITLE_XFIRE,
				config.payUserKey,
				config.payUserName,
				config.payPassword);
			String resultXml=httpUtil.doPost(config.payUrl,requestXml);
			result = httpUtil.result2Json(resultXml).toString();
			JSONObject resultJson= JSONObject.parseObject(result);
			Long endTime=System.currentTimeMillis();
			ExteriorLog log=new ExteriorLog();
			log.setAction(action.getCode());
			log.setReqParam(requestXml);
			log.setRespParam(resultXml);
			log.setCreateTime(new Date());
			log.setRelease(version);
			log.setResultCode(resultJson.getString("resultCode"));
			log.setResultDesc(resultJson.getString("resultDesc"));
			log.setResponseCode(resultJson.getString("responseCode"));
			log.setResponseTime(String.valueOf(endTime-startTime));
			asynLogService.insertExteriorLog(log);
		return result;
	}
	/** 调用支付平台接口 */
	protected String callPlatPayBusiness(HiipAPIEnum action, String param) throws Exception {
		String result="";
			Long startTime=System.currentTimeMillis();
			String requestXml=parseHiipSoapRequestXml(action,parseHiipRequestXml(param),
				SOAP_TITLE_XFIRE,
				config.payUserKey,
				config.payUserName,
				config.payPassword);
			String resultXml=httpUtil.doPost(config.payUrl,requestXml);
			result = httpUtil.result2Json(resultXml).toString();
			JSONObject resultJson= JSONObject.parseObject(result);
			Long endTime=System.currentTimeMillis();
			ExteriorLog log=new ExteriorLog();
			log.setAction(action.getCode());
			log.setReqParam(requestXml);
			log.setRespParam(resultXml);
			log.setCreateTime(new Date());
			log.setRelease(version);
			log.setResultCode(resultJson.getString("resultCode"));
			log.setResultDesc(resultJson.getString("resultDesc"));
			log.setResponseCode(resultJson.getString("responseCode"));
			log.setResponseTime(String.valueOf(endTime-startTime));
			asynLogService.insertExteriorLog(log);
		return result;
	}

	/**
	 *  Hiip项目入参xml添加 head等外层元素
	 */
	private String parseHiipRequestXml(String param){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><head><responseType>XMl</responseType><sourceId>12</sourceId></head>" +
				("".equals(param)?"<body></body>":param) +
				"</request>";
	}

	/** 拼接HIIP soap入参xml */
	private String parseHiipSoapRequestXml(HiipAPIEnum action, String requestXml,String title, String userKey, String userName, String password){
		StringBuilder sb =  new StringBuilder().append(title);
		if(null != userKey && !"".equals(userKey)){
			sb.append("<soapenv:Header><AuthenticationToken><Userkey>")
				.append(userKey)
				.append("</Userkey><Username>")
				.append(userName)
				.append("</Username><Password>")
				.append(password)
				.append("</Password></AuthenticationToken></soapenv:Header>");
		}
		else{
			sb.append("<soapenv:Header/>");
		}
		sb.append("<soapenv:Body><int:callBusiness>")
		.append("<action>")
		.append(action.getCode())
		.append("</action><requestXml><![CDATA[")
		.append(requestXml)
		.append("]]></requestXml></int:callBusiness></soapenv:Body></soapenv:Envelope>");
		//System.out.println("=接口入参； "+sb.toString());
		return sb.toString();
	}

	/** 判断HIIP返回resultCode码值是否为判定值*/
	protected boolean isHiipResultCode(HiipAPIReturnCodeEnum apiReturnCodeEnum, JSONObject resultJson){
		return apiReturnCodeEnum.getCode().equals(resultJson.getString(ConstHiipResoStr.RESULT_CODE));
	}
	/**
	 * 查询类返回值公共类
	 * @param respParam
	 * @throws Exception
	 */
	protected JsonResult queryResult(String respParam){
		JSONObject resultJson= JSON.parseObject(respParam);
		if(isHiipResultCode(HiipAPIReturnCodeEnum.CODE_1006, resultJson)){
			return new SuccessResult(ResultEnum.SUCCESS_NODATA,resultJson.getString(ConstHiipResoStr.RESULT_DESC));
		}
		else if(isHiipResultCode(HiipAPIReturnCodeEnum.CODE_1007, resultJson)){
			return new SuccessResult(resultJson.getString(ConstHiipResoStr.RESULT_DESC),resultJson.get(ConstHiipResoStr.RESULT));
		}
		else{
			return new FailResult(resultJson.getString(ConstHiipResoStr.RESULT_DESC));
		}
	}
}
