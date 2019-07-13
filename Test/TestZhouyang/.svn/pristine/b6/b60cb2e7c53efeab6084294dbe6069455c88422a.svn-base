package com.mittp.autotest.utils;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mittp.autotest.aspect.TestTag;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 反射Util
 *
 * @author zhouyang
 * 2019-07-05 13:40
 */
public class ReflectUtil {

    /**
     * 获取指定类，指定注解的方法
     *
     * @param clazz 指定类型
     * @param annoClazz 指定注解
     * @return List<String>
     */
    public static List<Map<String, String>> getAnnotationMethod(Class<?> clazz, Class<? extends TestTag> annoClazz) throws NoSuchFieldException {
        List<Map<String, String>> result = Lists.newArrayList();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annoClazz)) {
                TestTag testTag = method.getAnnotation(TestTag.class);
                Map<String, String> methodTag = Maps.newHashMap();
                methodTag.put(testTag.name(), testTag.desc());//原则上要求方法名和注解name一致
                result.add(methodTag);
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        List<Map<String, String>> test = null;
//        try {
//            test = ReflectUtil.getAnnotationMethod(MemberLoginController.class, TestTag.class);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        System.out.println(test);
//    }
}
