package com.yinhai.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
/**Soap配置文件类
 * @author yanbiao
 * @since 2019/11/14 19:44
 */
@Component
@PropertySource("classpath:soap.properties")
public class SoapConfig {

    /**业务接口地址 */
    @Value("${soap.platUrl}")
    public String platUrl;
    /**业务接口凭证 */
    @Value("${soap.platUserKey}")
    public String platUserKey;
    /**业务接口用户 */
    @Value("${soap.platUserName}")
    public String platUserName;
    /**业务接口密码 */
    @Value("${soap.platPassword}")
    public String platPassword;

    /**支付接口地址 */
    @Value("${soap.payUrl}")
    public String payUrl;
    /**支付接口凭证 */
    @Value("${soap.payUserKey}")
    public String payUserKey;
    /**支付接口用户 */
    @Value("${soap.payUserName}")
    public String payUserName;
    /**支付接口密码 */
    @Value("${soap.payPassword}")
    public String payPassword;
}
