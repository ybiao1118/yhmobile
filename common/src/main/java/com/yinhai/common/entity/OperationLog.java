package com.yinhai.common.entity;

import lombok.Data;

import java.util.Date;


/**操作日志记录类
 * @author yanbiao
 * @since 2019/11/8 17:53
 */
@Data
public class OperationLog {
    /** id*/
    private String id;
    /** 操作模块*/
    private String model;
    /** 请求方法名*/
    private String action;
    /** 项目版本*/
    private String release;
    /** 入参*/
    private String reqParam;
    /** 出参*/
    private String respParam;
    /** 操作类型*/
    private String type;
    /** 操作描述*/
    private String desc;
    /** 请求url地址*/
    private String url;
    /** 请求IP地址*/
    private String ip;
    /** 请求方法POST、GET*/
    private String method;
    /** 响应状态码*/
    private String statusCode;
    /** 调用时间*/
    private Date createTime;

}
