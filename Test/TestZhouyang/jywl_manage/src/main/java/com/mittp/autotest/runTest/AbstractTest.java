package com.mittp.autotest.runTest;


import com.alibaba.fastjson.JSON;

import com.mittp.autotest.constant.Constant;
import com.mittp.autotest.utils.SpringUtil;
import com.mittp.autotest.utils.ThreadLocalDateUtil;
import com.mittp.autotest.vo.TestResultVO;
import com.mittp.autotest.websocket.WebSocketHandler;
import java.text.ParseException;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 抽象的测试基类
 *
 * @author zhouyang
 * 2019-07-03 19:55
 */
public abstract class AbstractTest implements ITest {

    private Logger logger = LoggerFactory.getLogger(AbstractTest.class);
    protected WebDriver driver;
    protected String name;
    protected String note;

    protected void init() {
        this.name = "";
        this.note = "";
    }

    /**
     * 启动
     */
    private void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\myWorkspace\\Test\\SeleniumDriver\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Override
    public final void run() throws Exception {
        String result = Constant.TRUE;
        try {
            this.init();
            this.setUp();
            this.test();
            this.tearDown();
        } catch (Exception ex) {
            result = Constant.FALSE;
            logger.error("{},Test Fail", this.name, ex);
        }
        this.send(this.getTestResult(result));

    }

    /**
     * 测试
     */
    protected abstract void test();

    /**
     * 关闭
     */
    private void tearDown() {
        driver.quit();
    }


    private String getTestResult(String result) throws ParseException {
        TestResultVO testResultVO = new TestResultVO();
        testResultVO.setName(this.name);
        testResultVO.setResult(result);
        testResultVO.setRemark(this.note);
        testResultVO.setTime(ThreadLocalDateUtil.formatDate(new Date(), ThreadLocalDateUtil.DATE_FORMAT_SECOND));
        return JSON.toJSONString(testResultVO);
    }

    private void send(String msg) throws Exception {
        SpringUtil.getBean(WebSocketHandler.class).sendMsg(msg);
    }


}
