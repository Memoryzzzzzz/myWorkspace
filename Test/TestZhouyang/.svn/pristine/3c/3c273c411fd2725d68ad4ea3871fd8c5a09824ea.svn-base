package com.mittp.autotest.runTest.member.login;


import com.mittp.autotest.runTest.AbstractTest;
import org.openqa.selenium.By;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 账号密码登录
 *
 * @author zhouyang
 * 2019-07-03 19:54
 */
@Component
@Order(value = 1)
public class AccountLoginTest extends AbstractTest implements IMemberLogin {

    private static final String TITLE = "accountLogin";

    @Override
    protected void init() {
        super.init();
        this.name = TITLE;
    }

    /**
     * 测试
     */
    @Override
    protected void test() {
        driver.get("http://www.56jiyun.com/jywl/index.htm");
        driver.findElement(By.id("uname")).sendKeys("15956352975");
        driver.findElement(By.id("pwd")).sendKeys("111111");
        driver.findElement(By.cssSelector(".login-btn")).click();
    }
}
