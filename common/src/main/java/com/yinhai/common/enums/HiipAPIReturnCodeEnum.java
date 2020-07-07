package com.yinhai.common.enums;

/** HIIP接口返回状态码大全
 * @author : zhang
 * @since : 2020/1/2
 */
public enum HiipAPIReturnCodeEnum {
	/** 成功*/
	CODE_0("0","成功!"),
	CODE_1("1","失败!"),
	CODE_3("3","网络连接出现问题!"),
	CODE_5("-1","失败"),
	CODE_2("2","未知!"),
	CODE_00("00","成功!"),
	CODE_01("01","失败!"),
	CODE_9099("9099","未知错误!"),
	CODE_1001("1001","请求参数必填或格式不对!"),
	CODE_1002("1002","请求参数异常!"),
	CODE_1003("1003","方法不存在!"),
	CODE_1004("1004","验证失败，电话号码不匹配!"),
	CODE_1005("1005","验证失败，无记录!"),
	CODE_1006("1006","无记录!"),
	CODE_1007("1007","操作成功!"),
	CODE_1008("1008","无就诊记录!"),
	CODE_1009("1009","手机号码不匹配!"),
	CODE_1010("1010","码值不在定义范围内！"),
	CODE_1096("1096","HIS成功，平台失败!"),
	CODE_1097("1097","平台HttpClient调用接口失败!"),
	CODE_1098("1098","His接口执行失败!"),
	CODE_1099("1099","未知异常!"),
	CODE_9009("9009","接口参数异常!"),
	CODE_9999("9999","平台特殊异常!"),
	CODE_9999_00("9999_00","该交易号支付成功!"),
	CODE_9999_01("9999_01","该交易号支付失败!"),
	CODE_9999_99("9999_99","未知错误!"),



	CODE_1101("1101","号源锁定成功!"),
	CODE_1102("1102","号源锁定失败!"),
	CODE_1103("1103","预约成功!"),
	CODE_1104("1104","预约失败!"),
	CODE_1105("1105","销号成功!"),
	CODE_1106("1106","销号失败!"),
	CODE_1107("1107","重复预约!"),
	CODE_1108("1108","订单无效!"),
	CODE_1109("1109","支付申请成功!"),
	CODE_1110("1110","重复锁定!"),
	CODE_1111("1111","订单状态错误!"),
	CODE_1112("1112","该病人HIS已挂号!"),

	CODE_1201("1201","短信服务响应成功!"),
	CODE_1202("1202","短信服务响应失败!"),
	CODE_1203("1203","短信发送失败!"),
	CODE_1204("1204","短信校验失败!"),
	CODE_1205("1205","通知信息发生失败!"),
	CODE_1206("1206","短信校验成功!"),

	CODE_1412("1412","校验成功，该证件号可以使用!"),
	CODE_1402("1402","校验失败，该证件号已被注册!"),
	CODE_1403("1403","登录验证成功!"),
	CODE_1404("1404","帐号或密码错误!"),
	CODE_1405("1405","帐号不存在!"),
	CODE_1406("1406","用户注册成功!"),
	CODE_1407("1407","用户注册失败!"),
	CODE_1408("1408","用户资料修改成功!"),
	CODE_1409("1409","用户资料修改失败!"),
	CODE_1410("1410","密码修改成功!"),
	CODE_1411("1411","密码修改失败!"),


	CODE_1501("1501","就诊人添加成功!"),
	CODE_1502("1502","就诊人添加失败!"),
	CODE_1503("1503","就诊人资料修改成功!"),
	CODE_1504("1504","就诊人资料修改失败!"),
	CODE_1505("1505","就诊人注销成功!"),
	CODE_1506("1506","就诊人注销失败!"),
	CODE_1507("1507","重复添加"),
	CODE_1508("1508","本人信息重复添加"),
	CODE_1509("1509","本人信息不可注销"),
	CODE_1510("1510","同一注册号只可管理5个就诊人（含本人）"),
	CODE_1511("1511","同一手机号最多3个就诊人共同使用（含本人）"),
	CODE_1512("1512","已注册用户不能再被其他人添加为就诊人"),


	CODE_1601("1601","检索成功!"),
	CODE_1602("1602","检索无记录!"),
	CODE_1603("1603","注册成功"),
	CODE_1604("1604","注册失败"),
	CODE_1605("1605","更新成功"),
	CODE_1606("1606","更新失败"),
	CODE_1607("1607","无效识别号")


	,CODE_1701("1701","请求成功")
	,CODE_1702("1702","下单失败")
	,CODE_1703("1703","支付失败")
	,CODE_1704("1704","退款失败")
	,CODE_1705("1705","查询订单失败")

	,CODE_1801("1801","缴费成功")
	,CODE_1802("1802","缴费失败")

	;

	private String code;
	private String desc;

	HiipAPIReturnCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}