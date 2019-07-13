package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName HelloWorld
 * @Description 启动程序
 * @Param
 * @Author Zhong Yanghao
 * @Date 2019/6/26
 *
 * @SpringBootApplication
 * 来标注一个主程序类
 * 说明这是一个 spring boot 应用
 */
@SpringBootApplication
public class HelloWorld {

    public static void main(String[] args) {

        // Spring 应用启动
        SpringApplication.run(HelloWorld.class,args);
    }
}
