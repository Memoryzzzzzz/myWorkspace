package com.memory.springboot.bean;

/**
 * @ClassName Dog
 * @Description TODO
 * @Author Zhong Yanghao
 * @Param 狗
 * @Date 2019/6/28 16:03
 */
public class Dog {

    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Dog{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
