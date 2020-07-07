package com.yinhai.common.entity.result;

import com.yinhai.common.enums.ResultEnum;

/** 错误返回类
 * @author yanbiao
 * @since 2019/11/9 22:43
 */
public class ErrorResult extends JsonResult {
    /**
     * 错误返回类
     */
    public ErrorResult() {
        super(ResultEnum.ERROR, null);
    }
    /**
     * 错误返回类，默认msg
     * @param data
     */
    public ErrorResult(Object data) {
        super(ResultEnum.ERROR, data);
    }
    /**
     * 错误返回类，自定义msg
     * @param msg
     * @param data
     */
    public ErrorResult(String msg,Object data) {
        super(ResultEnum.ERROR,msg, data);
    }

    public ErrorResult(ResultEnum resultEnum, String msg, Object data) {
        super(resultEnum, msg, data);
    }
}
