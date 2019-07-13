package com.memory.springboot02config02.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author Zhong Yanghao
 * @Param
 * @Date 2019/7/10 14:56
 */

@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
}
