package com.mittp.autotest.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mittp.autotest.aspect.TestTag;
import com.mittp.autotest.constant.Constant;
import com.mittp.autotest.runTest.member.login.AccountLoginTest;

import com.mittp.autotest.runTest.member.login.IMemberLogin;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 会员中心登录注册
 *
 * @author zhouyang
 * 2019-07-04 19:55
 */
@Controller
@RequestMapping("/member/login/")
public class MemberLoginController extends BaseController {

    @Resource
    private AccountLoginTest accountLoginTest;

    /**
     * 登录页
     *
     * @return ModelAndView login
     * @throws Exception Exception
     */
    @RequestMapping("signin")
    public ModelAndView login() throws Exception {
        Map<String, String> testCaseMap = new HashMap<>();
        testCaseMap.put(Constant.TEST_CASE, JSON.toJSONString(this.listOfTestCase(MemberLoginController.class)));
        return new ModelAndView("member/MemberLogin", testCaseMap);
    }


    @RequestMapping("accountLogin")
    @ResponseBody
    @TestTag(name = "accountLogin", desc = "账号密码登录")
    public void accountLogin() throws Exception {
        accountLoginTest.run();
    }


    @RequestMapping("integration")
    @ResponseBody
    public void all() throws Exception {
        this.integration(IMemberLogin.class);
    }

}
