package com.memory.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName HelloController
 * @Param  * @param null
 * @Return {@link null}
 * @Description // HelloWorld
 * @Author Zhong Yanghao
 * @Date 2019/7/18 15:12
 */

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
