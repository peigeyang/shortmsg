package com.qbb.shortmsg.shortmsg.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 重写addCorsMappings方法实现跨域的设置
     * 当然跨域还可以通过在Controller或方法上添加‘@CrossOrigin("http://domain2.com")’的注解实现，不过下面这种方便统一管理
     * 参考：https://docs.spring.io/spring/docs/current/spring-framework-reference/html/cors.html
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("localhost:8080")  //允许的origin
                .allowedMethods("GET", "POST", "DELETE") //允许的方法
                .allowCredentials(true) //是否允许携带cookie
                .allowedHeaders("Content-Type, Accept, Authorization")
                .maxAge(3600);
    }

    //全局跨域，Enabling CORS for the whole application is as simple as:
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }*/
}