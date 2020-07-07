package com.yinhai.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanbiao
 * @since 2019/11/27 15:03
 */
@Data
@NoArgsConstructor
public class BaseException extends RuntimeException {
   private  String exceptionName;
   private  String exceptionMessage;
   private  StackTraceElement[] elements;
    public BaseException(String exceptionName, String exceptionMessage, StackTraceElement[] elements){
        this.exceptionName=exceptionName;
        this.exceptionMessage=exceptionMessage;
        this.elements=elements;
    }
}
