package com.memory.springboot.controller;

import java.util.Arrays;
import java.util.Map;
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

    // 页面展示查出一些数据
    @RequestMapping("/success")
    public String success(Map<String, Object> map){
        // classpath:/templates/success.html
        map.put("hello", "<h1>Hi</h1>");
        map.put("users", Arrays.asList("zhangsan", "lisi"));
        return "success";
    }
}
