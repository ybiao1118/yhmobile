package com.yinhai.common.entity.result;

import com.yinhai.common.enums.ResultEnum;

/** 成功返回类
 * @author yanbiao
 * @since 2019/11/9 16:05
 */
public class SuccessResult extends JsonResult {
    /**
     * 成功返回类
     */
    public SuccessResult() {
        super(ResultEnum.SUCCESS, null);
    }

    /**
     * 成功返回类，默认msg
     */
    public SuccessResult(Object data) {
        super(ResultEnum.SUCCESS, data);
    }

    /**
     * 成功返回类，自定义msg
     */
    public SuccessResult(String msg,Object data) {
        super(ResultEnum.SUCCESS, msg,data);
    }

    public SuccessResult(ResultEnum resultEnum, String msg){
    	super(resultEnum,msg);
	}
	public SuccessResult(ResultEnum resultEnum, String msg, Object data) {
		super(resultEnum, msg, data);
	}
}
