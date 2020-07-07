package com.yinhai.common.util;

import com.yinhai.common.entity.ErrLog;
import com.yinhai.common.service.AsynLogService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/** HTTP工具类，用于发送hiip请求
 * @author : zhang
 * @date : 2019/7/18
 */
@Component
public class HttpUtil {
	private static final int HTTP_SUCCESS = 200;
	@Autowired
	private AsynLogService asynLogService;
	/**
	 * 发送post请求
	 * @param url 请求地址
	 * @param param 请求参数
	 * @return 返回值字符串
	 */
	public  String doPost(String url, String param){
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		int status=0;
		try {
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new StringEntity(param, StandardCharsets.UTF_8));

			CloseableHttpResponse response = httpclient.execute(httppost);
			status = response.getStatusLine().getStatusCode();
			if (status != HTTP_SUCCESS) {
				throw new HttpException();
			}
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			httppost.releaseConnection();
			return result;
		} catch (Exception e) {
			ErrLog errLog=new ErrLog();
			String clazz = Thread.currentThread() .getStackTrace() [1].getClassName();
			String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
			errLog.setReqParam(param);
			errLog.setExcName(e.getClass().getName());
			errLog.setAction(clazz+"."+method);
			errLog.setCreateTime(new Date());
			errLog.setExcMsg(ExceptionUtil.stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
			asynLogService.insertErrLog(errLog);
			return "<res><responseCode>01</responseCode><resultCode>"+status+"</resultCode><resultDesc>接口响应异常</resultDesc></res>";

		}
	}

	/** 将HIIP的soap请求结果转成json格式
	 * @param xml 通过http请求获取返回的String，判断这个String是XML还是JSON，然后做出对应的解析方式
	 * @return
	 */
	public  JSON result2Json(String xml)  {
		String context="";
		if("".equals(xml)){
			return new JSONObject();
		}
		if(xml.startsWith("<soap:Envelope")){
			context = getSoapResult(xml);
		}
		else if(xml.startsWith("<res>")){
			context = xml;
		}
		JSON json= XmlUtil.xml2Json(context);
		return json;
	}

	/** 截取soap结果中实际返回的值*/
	private  String getSoapResult(String soapXml){
		if(soapXml.indexOf("<resultXml>")>0){
			return getSoapResultContext(soapXml, "resultXml");
		}
		else if(soapXml.indexOf("<ns1:resultXml>")>0){
			return getSoapResultContext(soapXml, "ns1:resultXml");
		}
		else{
			return "";
		}
	}

	/** 将xml中转码符号还原*/
	private  String getSoapResultContext(String soapXml, String resultEle){
		int begin = soapXml.indexOf("<"+resultEle+">");
		int end =  soapXml.indexOf("</"+resultEle+">");
		String context = soapXml.substring(begin+("<"+resultEle+">").length(), end);
		if(!"".equals(context)) {
			context = StringEscapeUtils.unescapeHtml4(context);
		}
		return context;
	}

}
