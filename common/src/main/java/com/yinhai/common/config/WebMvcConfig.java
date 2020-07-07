package com.yinhai.common.config;


import com.yinhai.common.intercepors.ApiServerInterceptor;
import com.yinhai.common.intercepors.CommonInterceptor;
import com.yinhai.common.intercepors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**自定义WebMvc配置类
 * @author yanbiao
 * @since 2019/10/9 22:48
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private  LoginInterceptor loginInterceptor;
    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private ApiServerInterceptor apiServerInterceptor;
    /** 添加拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1.加入的顺序就是拦截器执行的顺序，
        //2.按顺序执行所有拦截器的preHandle
        //3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle
        //普通请求拦截器；消息头验证；
       /* registry.addInterceptor(commonInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**","/v2/**","/swagger-ui.html"
                        ,"/error","/webjars/**","/");*/
        //登录验证拦截器
       /* registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
            .excludePathPatterns("/user/login","/users/**","/choose/**",
                "/swagger-resources/**","/v2/**","/swagger-ui.html"
                ,"/error","/webjars/**","/sms/**","/");*/
        //对外接口服务拦截器
//        registry.addInterceptor(apiServerInterceptor).addPathPatterns("/sms/**","/wxmp/**");


    }
    /** 添加静态资源映射器*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");

}

}
