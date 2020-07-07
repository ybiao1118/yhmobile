package com.yinhai.common.entity;

import lombok.Data;

import java.util.Date;

/**接口调用日志记录类
 * @author yanbiao
 * @since 2019/11/8 17:54
 */
@Data
public class InterfaceLog {
    /** 接口方法名*/
    private String action;
    /** 方法入参*/
    private String reqParam;
    /** 方法出参*/
    private String respParam;
    /** 接口调用时间*/
    private Date createTime;
    /** 返回结果代码*/
    private String resultCode;
    /** 返回结果描述*/
    private String resultDesc;
    /** 响应码*/
    private String responseCode;
    /** 响应时间*/
    private String responseTime;
    /** 接口请求版本*/
    private  String release;
}
