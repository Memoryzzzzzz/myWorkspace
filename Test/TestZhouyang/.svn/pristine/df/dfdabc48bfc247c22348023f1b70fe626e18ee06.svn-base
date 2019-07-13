package com.mittp.autotest.controller;

import com.google.common.collect.Lists;
import com.mittp.autotest.aspect.TestTag;
import com.mittp.autotest.exception.NotControllerException;
import com.mittp.autotest.runTest.ITest;
import com.mittp.autotest.runTest.member.login.IMemberLogin;
import com.mittp.autotest.utils.ReflectUtil;
import com.mittp.autotest.vo.TestResultVO;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContextExtensionsKt;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Controller;


/**
 * 控制器基类
 *
 * @author zhouyang
 * 2019-07-05 16:49
 */
@Controller
public class BaseController {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 模块集成测试
     *
     * @throws Exception Exception
     */
    public void integration(Class<? extends ITest> clazz) throws Exception {
        Map<String, ?> map = applicationContext.getBeansOfType(clazz);
        List<ITest> loginTests = Lists.newArrayList();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            loginTests.add((ITest)entry.getValue());
        }
        loginTests.sort(AnnotationAwareOrderComparator.INSTANCE);
        for (ITest item : loginTests) {
            item.run();//有异步，会延时
        }
    }


    protected List<TestResultVO> listOfTestCase(Class<?> clazz) throws NoSuchFieldException {
        List<TestResultVO> result = Lists.newArrayList();
        if (!clazz.isAnnotationPresent(Controller.class)) {
            throw new NotControllerException("非控制器类，不能获取TestTag");
        }
        //测试用例描述
        List<Map<String, String>> caseDescMap = ReflectUtil.getAnnotationMethod(clazz, TestTag.class);
        if (caseDescMap == null || caseDescMap.size() <= 0) {
            return new ArrayList<>();
        }
        for (Map<String, String> cases : caseDescMap) {
            for (Entry<String, String> entry : cases.entrySet()) {
                TestResultVO resultVO = new TestResultVO();
                resultVO.setName(entry.getKey());
                resultVO.setDesc(entry.getValue());
                result.add(resultVO);
            }
        }
        return result;
    }

}
