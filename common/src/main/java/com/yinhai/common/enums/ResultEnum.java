package com.yinhai.common.enums;



/** 返回值枚举类
 * @author yanbiao
 * @since 2019/11/27 11:29
 */
public enum ResultEnum {
    /**无效终端*/
    ILLEGAL(1000,"非法请求"),
    ILLEGAL_MISSING(1001,"无效接口") ,
    /** 短时间高频次请求*/
    ILLEGAL_FREQUENCE(1002,"请求频次异常"),
    ILLEGAL_NOTALLOW(1003,"请求不允许"),
    ILLEGAL_CLIENT(1005,"无效终端"),
    ILLEGAL_PARAM(1006,"请求参数异常"),
    /** 用户验证失败*/
    ILLEGAL_NOTLOGIN(1101,"用户未登录"),
    ILLEGAL_OVERTIME(1102,"用户超时"),
    ILLEGAL_NONE(1103,"用户非法"),

    /** 业务操作成功时*/
    SUCCESS(2000,"操作成功"),
    /** 业务操作成功且正在图文呼叫患者*/
    SUCCESSBYUSUAL(2002,"图文问诊"),
    /** 业务操作成功且正在视频呼叫患者*/
    SUCCESSBYVIDEO(2003,"视频问诊"),
    /** 查询类操作，查询结果为空时*/
    SUCCESS_NODATA(2001,"暂无有效数据"),

    /** 业务操作失败时*/
    FAIL(4000,"操作失败"),
    /** 具体业务涉及到需要区分失败类型时*/
    FAIL_PARAM(4001,"请求入参异常"),

    /**用户注册时，已经注册的情况*/
    FAIL_REPEAT_REGISTER(4101,"已注册"),
    /** 由于调用接口失败，从而注册失败的情况以及其它一些会导致失败的情况*/
    FAIL_REGISTER(4102,"注册失败"),
    /** 单一终端重复登录的情况*/
    FAIL_LOGIN_MORE(4103,"重复登录"),
    /** 登录账号时，账号不存在的情况*/
    FAIL_LOGIN_NON_EXISTENT(4104,"账号不存在"),

    /** 在线问诊，患者提交资料中的图片时*/
    FAIL_NOT_IMG(4105,"这不是图片"),



    ERROR(5000,"系统异常")
    ;

    /** 状态码*/
    private int code;
    /** 状态描述*/
    private String msg;

    ResultEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
    public int getCode() {
        return code;
    }
}
