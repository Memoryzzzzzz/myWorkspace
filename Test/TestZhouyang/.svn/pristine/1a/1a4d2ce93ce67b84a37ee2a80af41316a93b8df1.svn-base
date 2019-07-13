package com.mittp.autotest.controller;

import com.mittp.autotest.aspect.TestTag;
import com.mittp.autotest.runTest.member.edi.EdiTest;
import com.mittp.autotest.runTest.member.edi.IMemberEdi;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 会员中心Edi
 */
@Controller
@RequestMapping("/member/edi/")
public class MemberEdiController extends BaseController {

    @Resource
    private EdiTest ediTest;

    /**
     * edi
     *
     * @return ModelAndView edi
     * @throws Exception Exception
     */
    @RequestMapping("/release")
    public ModelAndView edi() throws Exception {
        return new ModelAndView("member/MemberEdi");
    }

    @RequestMapping("releaseDemand")
    @ResponseBody
    @TestTag(name = "releaseDemand", desc = "需求单Edi测试")
    public void accountLogin() throws Exception {
        ediTest.run();
    }


    @RequestMapping("integration")
    @ResponseBody
    public void all() throws Exception {
        this.integration(IMemberEdi.class);
    }
}
