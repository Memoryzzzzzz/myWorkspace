package com.mittp.autotest.runTest.member.edi;

import com.mittp.autotest.runTest.AbstractTest;
import com.mittp.autotest.utils.ThreadLocalDateUtil;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 会员中心Edi功能测试
 */
@Component
@Order(value = 1)
public class EdiTest extends AbstractTest implements IMemberEdi{

    private static final String TITLE = "Edi";

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
        Date loadTime = new Date(System.currentTimeMillis() + 86400000);
        Date unloadTime = new Date(System.currentTimeMillis() + 172800000);
        // Test name: Edi
        // Step # | name | target | value | comment
        // 1 | open | http://www.56jiyun.com/delegate_order.html |  |
        driver.get("http://www.56jiyun.com/delegate_order.html");
        // 2 | setWindowSize | 1920x1040 |  |
        driver.manage().window().setSize(new Dimension(1920, 1040));
        // 3 | click | id=installAddress |  |
        driver.findElement(By.id("installAddress")).click();
        // 4 | type | id=installAddress | 金三角 |
        driver.findElement(By.id("installAddress")).sendKeys("金三角");
        // 5 | click | css=.main_wrap |  |
        driver.findElement(By.cssSelector(".main_wrap")).click();
        // 6 | click | id=installTime |  |
        driver.findElement(By.id("installTime")).click();
        // 7 | type | id=installTime | loadTime|
        driver.findElement(By.id("installTime")).sendKeys(ThreadLocalDateUtil.formatDate(loadTime, ThreadLocalDateUtil.DATE_FORMAT_SECOND));
        // 8 | click | name=types |  |
        driver.findElement(By.name("types")).click();
        // 9 | type | name=types | 螺纹钢-二级 |
        driver.findElement(By.name("types")).sendKeys("螺纹钢-二级");
        // 10 | click | name=weight |  |
        driver.findElement(By.name("weight")).click();
        // 11 | type | name=weight | 66 |
        driver.findElement(By.name("weight")).sendKeys("66");
        // 12 | click | name=num |  |
        driver.findElement(By.name("num")).click();
        // 13 | type | name=num | 66 |
        driver.findElement(By.name("num")).sendKeys("66");
        // 14 | click | name=spec |  |
        driver.findElement(By.name("spec")).click();
        // 15 | type | name=spec | 23*23 |
        driver.findElement(By.name("spec")).sendKeys("23*23");
        // 16 | click | css=.mt30 |  |
        driver.findElement(By.cssSelector(".mt30")).click();
        // 17 | click | css=.confirmInfo |  |
        driver.findElement(By.cssSelector(".confirmInfo")).click();
        // 18 | click | css=.ui-dialog-autofocus |  |
        driver.findElement(By.cssSelector(".ui-dialog-autofocus")).click();
        // 19 | click | id=uninstallAddress |  |
        driver.findElement(By.id("uninstallAddress")).click();
        // 20 | type | id=uninstallAddress | 无锡城南库 |
        driver.findElement(By.id("uninstallAddress")).sendKeys("无锡城南库");
        // 21 | click | css=#uninstall > .content-wrap |  |
        driver.findElement(By.cssSelector("#uninstall > .content-wrap")).click();
        // 22 | click | id=uninstallTime |  |
        driver.findElement(By.id("uninstallTime")).click();
        // 23 | type | id=installTime | loadTime|
        driver.findElement(By.id("uninstallTime")).sendKeys(ThreadLocalDateUtil.formatDate(unloadTime, ThreadLocalDateUtil.DATE_FORMAT_SECOND));
        // 24 | click | css=.submit |  |
        driver.findElement(By.cssSelector(".submit")).click();
        // 25 | click | css=#xqd_tab > li:nth-child(2) |  |
        driver.findElement(By.cssSelector("#xqd_tab > li:nth-child(2)")).click();
    }
}
