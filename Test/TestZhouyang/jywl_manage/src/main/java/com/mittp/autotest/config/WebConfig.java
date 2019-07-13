package com.mittp.autotest.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
//@ComponentScan //@ComponentScan主要就是定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中
//@PropertySource({"classpath:application.properties")
//@ImportResource("classpath:META-INF/spring/*.xml")
public class WebConfig implements WebMvcConfigurer {


}
