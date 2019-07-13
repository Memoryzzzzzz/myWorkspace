package com.memory.springboot.config;

import com.memory.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyAppConfig
 * @Description TODO
 * @Author Zhong Yanghao
 * @Param
 * @Date 2019/7/5 16:09
 */

/**
 * @Configuration：指明当前类是一个配置类，就是来代替之前的 Spring 配置文件
 *
 * 在配置文件中用 <bean></bean> 标签添加组件
 */
@Configuration
public class MyAppConfig {

    // 将方法的返回值添加到容器中：容器中的这个组件默认的 Id 就是方法名
    @Bean
    public HelloService helloService(){
        System.out.println("配置类 @Bean 给容器中添加组件");
        return new HelloService();
    }
}
