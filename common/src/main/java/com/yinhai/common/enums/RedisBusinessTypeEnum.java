package com.yinhai.common.enums;

/** Reids缓存业务类型
 * @author : zhang
 * @since : 2019/12/4
 */
public enum RedisBusinessTypeEnum {
	/**请求消息头随机码*/
	HEADER_NONCE,
	/**用户凭证*/
	TOKEN,
	/**短信验证码*/
	VERIFY_CODE,
	/**用户信息*/
	USER_INFO
	;

}
