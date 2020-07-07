package com.yinhai.common.entity.result;

import com.yinhai.common.enums.ResultEnum;

/** 失败返回类
 * @author yanbiao
 * @since 2019/11/9 22:43
 */
public class FailResult extends JsonResult {
    /**
     * 失败返回类
     */
    public FailResult() {
        super(ResultEnum.FAIL, null);
    }
    /**
     * 失败返回类，默认msg
     */
    public FailResult(Object data) {
        super(ResultEnum.FAIL, data);
    }
    /**
     * 失败返回类，自定义msg
     */
    public FailResult(String msg,Object data) {
        super(ResultEnum.FAIL,msg, data);
    }
    public FailResult(ResultEnum resultEnum){super(resultEnum,resultEnum.getMsg());}
    public FailResult(ResultEnum resultEnum, String msg) {
        super(resultEnum, msg, null);
    }
    public FailResult(ResultEnum resultEnum, String msg, Object data) {
        super(resultEnum, msg, data);
    }
}
