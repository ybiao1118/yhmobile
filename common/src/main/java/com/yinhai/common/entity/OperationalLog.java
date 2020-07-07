package com.yinhai.common.entity;

import lombok.Data;

import java.util.Date;


/**操作日志记录类
 * @author yanbiao
 * @since 2019/11/8 17:53
 */
@Data
public class OperationalLog {
    /** id*/
    private String id;
    /** 操作模块*/
    private String model;
    /** 请求类方法名*/
    private String action;
    /** 项目模式*/
    private String release;
    /** 入参*/
    private String reqParam;
    /** 出参*/
    private String respParam;
    /** 请求来源*/
    private String source;
    /** 请求来源版本*/
    private String version;
    /** 请求url地址*/
    private String url;
    /** 请求IP地址*/
    private String ip;
    /** 请求方法POST、GET*/
    private String method;
    /** 响应状态码*/
    private String statusCode;
    /** 响应结果描述*/
    private String desc;
    /** 调用时间*/
    private Date createTime;

}
