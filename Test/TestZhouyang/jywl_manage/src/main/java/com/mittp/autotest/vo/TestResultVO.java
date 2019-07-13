package com.mittp.autotest.vo;

/**
 * @author zhouyang
 * 2019-07-03 19：00
 */
public class TestResultVO {

    /**
     * 测试项名称
     */
    private String name;
    /**
     * 测试项描述
     */
    private String desc;
    /**
     * 测试完成时间
     */
    private String time;
    /**
     * 测试结果
     */
    private String result;
    /**
     * 测试备注
     */
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
