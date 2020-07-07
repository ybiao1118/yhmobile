package com.yinhai.common.entity;

import lombok.Data;

import java.util.Date;

/**异常日志记录实体类
 * @author yanbiao
 * @since 2019/11/8 17:54
 */
@Data
public class ExcLog {
    /** id*/
    private  String id;
    /** 方法入参*/
    private  String reqParam;
    /** 异常名称*/
    private  String excName;
    /** 异常信息*/
    private  String excMsg;
    /** 操作模块*/
    private  String model;
    /** 方法名称*/
    private  String action;
    /** 姓名版本*/
    private  String release;
    /** 请求url地址*/
    private  String url;
    /** 请求IP地址*/
    private  String ip;
    /** 创建时间*/
    private Date createTime;

}
