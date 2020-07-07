package com.yinhai.common.annotation;

import java.lang.annotation.*;

/** 日志记录及加解密注解类
 * @author yanbiao
 * @since 2019/11/11 23:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLog {
    /** 业务模块的中文描述*/
    String operModule() default "";
    /** 入参是否加密，true：加密，false：不加密*/
    boolean paramIsEncrypt() default true;
    /** 返回参数是否加密，true：加密，false：不加密*/
    boolean resultIsEncrypt() default true;
}