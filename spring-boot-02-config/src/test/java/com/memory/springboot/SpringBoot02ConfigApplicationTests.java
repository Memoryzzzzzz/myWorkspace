package com.memory.springboot;

import com.memory.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring Boot 单元测试
 *
 * 可以在测试期间很方便的，类似编码一样进行自动注入等容器的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02ConfigApplicationTests {

    @Autowired
    Person person;

    @Autowired
    ApplicationContext ioc;

    @Test
    public void testHelloService(){
        boolean helloService =  ioc.containsBean("helloService");
        System.out.println(helloService);
    }

    @Test
    public void contextLoads() {
        System.out.println(person);
    }

}
