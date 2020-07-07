package com.yinhai.common.util;

import com.yinhai.common.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yanbiao
 * @since 2019/11/9 16:05
 */
@Data
public class JsonResult implements Serializable {
    /** 返回参数*/
    private Object data;
    /** 状态码*/
    private  int status;
    /** 状态描述*/
    private String msg;

    /**
     * 默认msg构造器
     * @param resultEnum  返回状态枚举类
     * @param data 返回参数
     */
    public JsonResult(ResultEnum resultEnum, Object data) {
        this.status=resultEnum.getCode();
        this.msg=resultEnum.getMsg();
        this.data = data;
    }

    /**
     * 自定义msg构造器
     * @param resultEnum 返回状态枚举类
     * @param msg 自定义msg
     * @param data 返回参数
     */
    public JsonResult(ResultEnum resultEnum, String msg,Object data){
        this.status=resultEnum.getCode();
        this.msg=msg;
        this.data = data;
    }
}
