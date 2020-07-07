package com.yinhai.common.util;

/**异常处理工具类
 * @author yanbiao
 * @since 2019/11/28 16:46
 */
public class ExceptionUtil {
    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public static String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stet : elements) {
            stringBuilder.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + stringBuilder.toString();
        return message;
    }
}
