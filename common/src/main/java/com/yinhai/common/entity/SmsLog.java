package com.yinhai.common.entity;

import lombok.Data;

/** 短信发送日志
 * @author : zhang
 * @since : 2019/12/4
 */
@Data
public class SmsLog {
	/**短信渠道*/
	private String serverType;
	/**接收手机号*/
	private String mobile;
	/**短信类型*/
	private String businessType;
	/**短信模版*/
	private String tempId;
	/**短信模版参数*/
	private String params;
	/**响应状态*/
	private String code;
	/**响应描述*/
	private String msg;
	/**返参*/
	private String result;
	/**日志时间*/
	private String logTime;
}
