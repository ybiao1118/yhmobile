package com.yinhai.common.util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
/** ip地址工具类
 * @author yanbiao
 * @since 2019/11/11 23:08
 */
public class IpUtil {
    /**
     * 本地ip地址
     */
    private static final String LOCAL_IP = "127.0.0.1";
    /**
     * 默认ip地址
     */
    private static final String DEFAULT_IP = "0:0:0:0:0:0:0:1";
    /**
     * 默认ip地址长度
     */
    private static final int DEFAULT_IP_LENGTH = 15;
    /**
     * 获取合法ip地址
     * @param request request
     * @return ip
     */
    public static String getIpAdrress(HttpServletRequest request) {
        String unknown = "unknown";
        //squid 服务代理
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)) {
            //apache服务代理
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            //weblogic 代理
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            //有些代理
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            //nginx代理
            ip = request.getHeader("X-Real-IP");
        }

        /*
         * 如果此时还是获取不到ip地址，那么最后就使用request.getRemoteAddr()来获取
         * */
        if(ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(StringUtils.equals(ip,LOCAL_IP) || StringUtils.equals(ip,DEFAULT_IP)){
                //根据网卡取本机配置的IP
                InetAddress iNet = null;
                try {
                    iNet = InetAddress.getLocalHost();
                    ip= iNet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if(!StringUtils.isEmpty(ip) && ip.length()> DEFAULT_IP_LENGTH){
            if(ip.indexOf(",") > 0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
