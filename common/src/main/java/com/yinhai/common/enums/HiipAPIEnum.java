package com.yinhai.common.enums;

/** HIIP 接口清单
 * @author : zhang
 * @since : 2019/12/27
 */
public enum HiipAPIEnum {
	/** 注册校验*/
	USER_REG_CHECK("hospInfoH06"),
	/** 注册*/
	USER_REGISTER("hospInfoH07"),
	/** 用户登录*/
	USER_LOGIN("hospInfoH08"),
	/** 用户信息修改*/
	USER_INFO_CHANGE("hospInfoH09"),
	/** 重置密码*/
	USER_PWD_RESET("hospInfoH16"),
	/** 就诊人添加*/
	PATIENT_ADD("hospInfoH10"),
	/** 就诊人修改*/
	PATIENT_CHANGE("hospInfoH11"),
	/** 就诊人删除*/
	PATIENT_DEL("hospInfoH12"),
	/** 获取就诊人列表*/
	PATIENT_LIST("hospInfoH13"),
	/** 获取就诊人详情*/
	PATIENT_INFO("hospInfoH13"),

	/** 获取待缴费列表*/
	OUTPATIENT_UNPAIDLIST("expenses01"),
	/** 获取处方列表*/
	OUTPATIENT_PRESLIST("expenses04"),
	/** 获取处方详情*/
	OUTPATIENT_PRESINFO("expenses05"),
	/** 获取缴费记录列表*/
	OUTPATIENT_CHARGERECLIST("expenses06"),
	/** 获取缴费记录详情*/
	OUTPATIENT_CHARGERECINFO("expenses07"),
	/** 预支付下单*/
	OUTPATIENT_YHPAYPREPAY("yHPayPrepay"),
	/** 微信退款*/
	OUTPATIENT_YHPAYREFUND("yHPayRefund"),
	/** 诊间支付*/
	OUTPATIENT_PAY("expenses03"),

	/** 获取检验报告列表*/
	REPORT_VERIFYLIST("reportR01"),
	/** 获取检验报告列表(非平台)*/
	REPORT_VERIFYLIST_INDEPEND("independence09"),
	/** 获取检验报告详情*/
	REPORT_VERIFYINFOS("reportR03"),
	/** 获取检验报告详情(非平台)*/
	REPORT_VERIFYINFOS_INDEPEND("independence10"),
	/** 获取检查报告列表*/
	REPORT_INSPECTLIST("reportR01"),
	/** 获取检查报告列表(非平台)*/
	REPORT_INSPECTLIST_INDEPEND("independence11"),
	/** 获取检查报告详情*/
	REPORT_INSPECTINFOS("reportR04"),
	/** 获取检查报告详情(非平台)*/
	REPORT_INSPECTINFOS_INDEPEND("independence12"),

	/** 获取调查状态*/
	SATISFICING_INQUIRYSTATUS("satisficing00"),

	/** 获取满意度题目列表*/
	SATISFICING_LIST("satisficing01"),
	/** 提交满意度答案*/
	SATISFICING_COMMIT("satisficing02"),
	/** 获取调查列表*/
	SATISFICING_INQUIRYLIST("satisficing07"),
	/** 获取调查问卷*/
	SATISFICING_INQUIRYINFO("satisficing08"),
	/** 提交问卷调查*/
	SATISFICING_INQUIRYEDIT("satisficing09"),
	/** 提交用户反馈*/
	SATISFICING_USERFEEDBACK("satisficing10"),

	/** 诊疗项目物价查询*/
	PRICE_DIAGNOSIS("hospInfoH03"),
	/** 药品物价查询*/
	PRICE_DRUG("hospInfoH04"),
	/** 特殊材料物价查询*/
	PRICE_SPECIALMATERIALS("hospInfoH05"),

	/**根据身体部位查询病症**/
	SMART_GUIDE_SYMPTOM("weChatSG01"),
	/**根据病症查询并发症**/
	SMART_GUIDE_COMPLICATION("weChatSG01"),
	/**根据病症、并发症查询科室**/
	SMART_GUIDE_DEPT("weChatSG01"),

    /**获取医院列表**/
    HOSPITAL_LIST("weChatI01"),
    HOSPITAL_LIST_V5("independence011"),
    /**获取医院简介**/
    HOSPITAL_INFO("weChat01"),
	/**获取医院地图链接**/
	HOSPITAL_MAP_URL("weChatI03"),
	/**获取医院楼层信息**/
	HOSPITAL_STOREY_RESULT("weChatF01"),
    /**获取科室列表**/
    DEPT_LIST("weChat02"),
    /**获取科室简介**/
    DEPT_INFO("weChat03"),
    /**获取医生列表**/
    DOCTOR_LIST("weChat04"),
    /**获取医生简介**/
    DOCTOR_INFO("weChat05"),
	/**获取公告列表**/
	NOTICE_LIST("weChat06"),
	/**获取公告详情**/
	NOTICE_INFO("weChat07"),
	/**获取医院名称**/
	GET_HOSPITAL_NAME("weChat18"),

	/**挂号规则**/
	APPOINTMENT_RULES("healthBookingH15"),
	APPOINTMENT_RULES_V5("independence34"),
	/**挂号排班科室列表**/
	APPOINTMENT_DEPTS("healthBookingH01"),
	APPOINTMENT_DEPTS_V5("independence_csgh01"),
	/**挂号排班医生列表**/
	APPOINTMENT_DOCTORS("healthBookingH02"),
	APPOINTMENT_DOCTORS_V5("independence_csgh02"),
	/**挂号医生排班列表**/
	APPOINTMENT_SCHEDULES("healthBookingH03"),
	APPOINTMENT_SCHEDULES_V5("independence_csgh02"),
	/**挂号锁号**/
	APPOINTMENT_LOCK("healthBookingH04"),
	APPOINTMENT_LOCK_V5("independence0401"),
	/**挂号回写**/
	APPOINTMENT_REG("healthBookingH06"),
	APPOINTMENT_REG_V5("independence0601"),
	/**挂号支付后的退号**/
	APPOINTMENT_DROP("healthBookingH07"),
	APPOINTMENT_DROP_V5("independence0701"),
	/**挂号未支付的取消**/
	APPOINTMENT_CANCEL_V5("independence0501"),
	/**医生收藏**/
	COLLECT_DOCTOR("independence45"),
	/**获取预约列表**/
	APPOINTMENT_GET_LIST("healthBookingH09"),
	APPOINTMENT_GET_LIST_V5("independence0801"),
	/**获取预约详情**/
	APPOINTMENT_GET_INFO("appointmentGetList"),
	APPOINTMENT_GET_INFO_V5("independence0801"),


	/**微信预支付**/
	WXPAY_PREPAY("yHPayPrepay"),
	/**健康资讯列表**/
	EDUCATION_LIST("weChat08"),
	/**住院服务--获取病人在院信息**/
	IN_HOSPTIAL_GET_PATIENT_INFO_v7("expenses21"),
	IN_HOSPTIAL_GET_PATIENT_INFO_v5("independence19"),
	/**住院服务--获取病人信息(包含出院)**/
	IN_HOSPTIAL_GET_PATIENT_INFO_ALL_v7("expenses26"),
	/**获取住院记录**/
	IN_HOSPTIAL_GET_RECORD_v7("expenses27"),
	/**住院预存**/
	IN_HOSPTIAL_PRESTORE_v7("expenses10"),
	IN_HOSPTIAL_PRESTORE_v5("independence17"),
	/**获取住院预存记录**/
	IN_HOSPTIAL_GET_PRESTORE_RECORD_v7("expenses11"),
	IN_HOSPTIAL_GET_PRESTORE_RECORD_v5("independence18"),
	/**获取住院费用每日汇总清单**/
	IN_HOSPTIAL_GET_DAILY_SUMMARY_v7("expenses12"),
	IN_HOSPTIAL_GET_DAILY_LISTNG_v5("independence21"),
	/**获取住院费用每日清单**/
	IN_HOSPTIAL_GET_DAILY_DETAIL_v7("expenses14"),
	/**住院费用汇总查询**/
	IN_HOSPTIAL_GET_SUMMARY_LISTNG_v7("expenses13"),
	IN_HOSPTIAL_GET_SUMMARY_LISTNG_v5("independence22"),
	/**住院病历查询**/
	IN_HOSPTIAL_GET_MEDICAL_RECORD_v7("expenses28"),


	/**互联网医院模块---查询在线问诊记录**/
	INTERNET_HOSPTIAL_GET_CHAT_RECORDS("netHospitalA07"),
	/**互联网医院模块---修改在线问诊记录**/
	INTERNET_HOSPTIAL_UDATE_CHAT_RECORDS("netHospitalA04"),
	/**互联网医院模块---新增在线问诊记录**/
	INTERNET_HOSPTIAL_ADD_CHAT_RECORDS("netHospitalA06"),
	/**互联网医院模块---根据排班计划获取科室**/
	INTERNET_HOSPITAL_GET_DEPT("netHospitalI04"),
	/**互联网医院模块---根据排班计划获取医生**/
	INTERNET_HOSPITAL_GET_DOCTOR("netHospitalI05"),
    /**互联网医院模块---查询医生当前及后两天班次：是否接诊**/
    INTERNET_HOSPITAL_FIND_DOCTOR_SCHEDULE("netHospitalI06"),
	/**互联网医院模块---获取医生**/
	INTERNET_HOSPITAL_GET_DOCTOR_DETAIL("netHospitalI03"),
	/**互联网医院模块---患者自报材料**/
	INTERNET_HOSPITAL_PATIENT_SELF_REPORTED("netHospitalA01"),
	/**互联网医院模块--查询问诊记录-问诊评价**/
	INTERNET_HOSPITAL_SELECT_EVALUATION("netHospitalA19"),
	/**互联网医院模块--查询医生待问诊记录**/
	INTERNET_HOSPITAL_NOT_INTERROGATION("netHospitalA11"),
    /**互联网医院模块--取消问诊**/
	INTERNET_HOSPITAL_CANCEL_INTERROGATION("netHospitalA15"),
    /**互联网医院模块--锁号**/
	INTERNET_HOSPITAL_LOCK_NUMBER("netHospitalA16"),
    /**互联网医院模块---判断是否已存在该资料**/
    INTERNET_HOSPITAL_PATIENT_EXIST_REPORTED("netHospitalA17"),
	/**互联网医院模块---判断存在该资料获取问诊id**/
    INTERNET_HOSPITAL_PATIENT_EXIST_RECORDS("netHospitalA20"),
    /**互联网医院模块---查询医院地址**/
    INTERNET_HOSPITAL_SELECT_HOSPITAL_ADDRESS("netHospitalA18"),
	/**互联网医院模块---查询医生接诊状态：是否接诊**/
	INTERNET_HOSPITAL_FIND_ADMISSION_STATUS("netHospitalUser05"),
	/**互联网医院模块---修改医生接诊状态**/
	INTERNET_HOSPITAL_CHANGE_ADMISSION_STATUS("netHospitalUser06"),
    /**互联网医院模块---查询医生正在问诊数量**/
    INTERNET_HOSPITAL_GET_DOCTOR_INQUIRY_NUM("netHospitalA10"),
    /**互联网医院模块---患者查询候诊排队号**/
    INTERNET_HOSPITAL_QUERY_DOCTOR_ORDERNUM("netHospitalA12"),
	/**互联网医院模块---根据身份证号查询医生信息**/
	INTERNET_HOSPITAL_GET_DOCOR_INFO_BY_IDCARD_NO("netHospitalUser10"),
	/**互联网医院模块---修改问诊记录的排队数量**/
	INTERNET_HOSPITAL_UPDATE_QUEUE_COUNT("netHospitalA14"),
	/**互联网医院模块---下载保存腾讯聊天记录**/
	INTERNET_HOSPITAL_SAVE_CHAT_INFO("netHospitalC01"),
	/**互联网医院模块---定时任务--结束正在问诊记录**/
	INTERNET_HOSPITAL_STOP_CHAT("netHospitalC02"),
	/**互联网医院模块---定时任务--作废待接诊的记录**/
	INTERNET_HOSPITAL_CANCELLATION_CHAT("netHospitalC03"),


	/**互联网医院模块---用户手机号是否注册*/
	CHECK_USER_PHONE("netHospitalUser03"),
	/**互联网医院模块---用户注册*/
	REGISTER_USER("netHospitalUser01"),
	/**互联网医院模块---在线问诊密码登录*/
	LOGIN_USER("netHospitalUser02"),
    /**互联网医院模块---在线问诊注销用户*/
	DELETE_USER("netHospitalUser04"),
	/**互联网医院模块---获取患者问诊资料*/
	CONSULTINFO("netHospitalA02"),
	/**互联网医院模块---改变问诊记录状态*/
	UPDATERECODESTATUS("netHospitalA04"),
    /**互联网医院模块---新增反馈意见*/
    USER_FEEDBACK("netHospitalF01"),
	/**互联网医院模块---获取用户处方记录*/
	RECIPET_LIST("netHospitalR03"),


	/**互联网医院模块--问诊支付-问诊费用支付**/
	INTERNET_HOSPITAL_FEE_PAYMENT("netHospitalP01"),
	/**互联网医院模块--问诊支付-推送患者信息到HIS**/
	INTERNET_HOSPITAL_GET_PATIENT_INFO("netHospitalP02"),
	/**互联网医院模块--问诊支付-处方付费**/
	INTERNET_HOSPITAL_PRESCRIPTION_PAY("netHospitalP03"),
	/**互联网医院模块--问诊支付-结算交易**/
	INTERNET_HOSPITAL_SETTLE_TRANSACTE("netHospitalP04"),
	/**互联网医院模块--问诊支付-获取微信小程序用户身份**/
	INTERNET_HOSPITAL_GET_WXMINI_USERINFO("netHospitalP05"),
	/**互联网医院模块--问诊支付-获取患者问诊支付状态**/
	INTERNET_HOSPITAL_GET_CONSULT_PAYSTATUS("netHospitalP06"),
	/**互联网医院模块--问诊支付-获取患者缴费记录列表**/
	INTERNET_HOSPITAL_GET_PATIENT_PAYLIST("netHospitalP07"),
	/**互联网医院模块--问诊支付-获取患者缴费记录详情**/
	INTERNET_HOSPITAL_GET_PATIENT_PAYINFO("netHospitalP08"),
	/**互联网医院模块--问诊支付-患者取消问诊**/
	INTERNET_HOSPITAL_CANCEL_CONSULT("netHospitalP09"),
	/**互联网医院模块--问诊支付-查询患者未支付处方信息**/
	INTERNET_HOSPITAL_GET_PATIENT_RECIPE_RECORD("netHospitalP10"),
	/**互联网医院模块--问诊支付-获取患者待支付信息**/
	INTERNET_HOSPITAL_QUERY_PRES_PAYINFO("netHospitalP11"),
	/**互联网医院模块--问诊评价-查询问诊信息**/
	INTERNET_HOSPITAL_SHOW_RECORD_INFO("netHospitalUser08"),
	/**互联网医院模块--问诊评价-问诊评价**/
	INTERNET_HOSPITAL_SAVE_EVALUATION("netHospitalA05"),

	/**互联网医院模块--物流管理-获取收件人列表**/
	INTERNET_HOSPITAL_GET_RECEIPT_LIST("netHospitalL001"),
	/**互联网医院模块--物流管理-获取默认收件信息**/
	INTERNET_HOSPITAL_GET_DEFAULT_MESSAGE("netHospitalL002"),
	/**互联网医院模块--物流管理-更新默认收件信息**/
	INTERNET_HOSPITAL_UPDATE_DEFAULT_MESSAGE("netHospitalL004"),
	/**互联网医院模块--物流管理-保存收件信息**/
	INTERNET_HOSPITAL_SAVE_MESSAGE("netHospitalL005"),
    /**互联网医院模块--物流管理-查询处方表主键**/
    INTERNET_HOSPITAL_SELECT_RECIPE_ID("netHospitalR01_9"),
	/**互联网医院模块--物流管理-保存收件信息**/
	INTERNET_HOSPITAL_SELECT_LOGISTICS_DETAIL("netHospitalR01_6"),
	/**互联网医院模块--物流管理-查询收件信息**/
	INTERNET_HOSPITAL_SELECT_RECIPE("netHospitalR01_7"),
    /**互联网医院模块--物流管理-修改配送方式**/
	INTERNET_HOSPITAL_UPDATE_SEND("netHospitalR01_8"),
	/**互联网医院模块--物流管理-保存收件信息**/
	INTERNET_HOSPITAL_SELECT_EXPRESS("netHospitalL006"),
	/**互联网医院模块--物流管理-添加收件地址**/
	INTERNET_HOSPITAL_ADD_RECEIVE("netHospitalL007"),
	/**互联网医院模块--物流管理-修改收件地址**/
	INTERNET_HOSPITAL_UPDATE_RECEIVE("netHospitalL008"),
	/**互联网医院模块--物流管理-删除收件地址**/
	INTERNET_HOSPITAL_DELETE_RECEIVE("netHospitalL009"),
	/**互联网医院模块--物流管理-查询默认寄件信息**/
	INTERNET_HOSPITAL_DEFAULT_DELIVERY("netHospitalL011"),

	/**V7版 体检*/
	HEALTH_CHECK_LIST("reportT01"),
	HEALTH_CHECK_TOTAL("reportT02"),
	HEALTH_CHECK_DETAIL("reportT05"),

	/**V5版 体检*/
	HEALTH_CHECK_V5("2");

	private String code;

	HiipAPIEnum(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}
}
