package com.yinhai.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**Common配置文件类
 * @author yanbiao
 * @since 2019/11/14 19:44
 */
@Configuration
@PropertySource("classpath:common.properties")
public class CommonConfig {
    /**jwt签名密码*/
    @Value("${jwt.signKey}")
    public  String jwtSignKey;
    /**jwt 有效时间,单位小时*/
    @Value("${jwt.refreshTime}")
    public int jwtRefreshTime;
    /**jwt存到redis时间,单位天*/
    @Value("${jwt.longTime}")
    public int jwtLongTime;
    /**aes加密秘钥*/
    @Value("${aes.signkey}")
    public String aesSignKey;
    /**rsa加密公钥*/
    @Value("${rsa.pubKey}")
    public String rsaPubKey;
    /**rsa加密私钥*/
    @Value("${rsa.priKey}")
    public String rsaPriKey;

}
