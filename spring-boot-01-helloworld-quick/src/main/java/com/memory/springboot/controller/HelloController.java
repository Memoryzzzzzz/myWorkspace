package com.memory.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author Zhong Yanghao
 * @Param
 * @Date 2019/6/27 19:40
 */

// 这个类的所有方法返回的数据直接写给浏览器（如果是对象可以转为json数据）
// @ResponseBody
// @Controller
// @RestController = @ResponseBody + @Controller
@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String hello(){
        return "hello world quick!";
    }
}
