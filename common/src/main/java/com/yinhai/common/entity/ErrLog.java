package com.yinhai.common.entity;

import lombok.Data;

import java.util.Date;

/**异常日志记录实体类
 * @author yanbiao
 * @since 2019/11/8 17:54
 */
@Data
public class ErrLog {
    /** id*/
    private  String id;
    /** 方法入参*/
    private  String reqParam;
    /** 异常描述*/
    private  String excName;
    /** 异常详情*/
    private  String excMsg;
    /** 操作模块*/
    private  String model;
    /** 类方法名称*/
    private  String action;
    /** 项目模式*/
    private  String release;
    /** 请求url地址*/
    private  String url;
    /** 请求来源IP地址*/
    private  String ip;
    /** 请求来源*/
    private String source;
    /** 请求来源版本*/
    private String version;
    /** 日志记录时间*/
    private Date createTime;

}
