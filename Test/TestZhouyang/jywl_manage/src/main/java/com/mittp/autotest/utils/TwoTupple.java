package com.mittp.autotest.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 二元组
 *
 * @author zhouyang 2019-07-03 19:28
 */
public class TwoTupple<TFirst, TSecond> implements Serializable {

    private ArrayList<Object> container;

    //静态块>代码块>构造函数   父类>子类
    {
        container = new ArrayList<>();
    }

    public TwoTupple(TFirst a, TSecond b) {
        container.add(a);
        container.add(b);
    }

    @SuppressWarnings("unchecked")
    public TFirst first() {
        return (TFirst) container.get(0);
    }

    @SuppressWarnings("unchecked")
    public TSecond second() {
        return (TSecond) container.get(1);
    }
}
