/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsCharacterData {
    private String id;

    private String functionDescription;

    private String workCondition;

    private String inputLogicalDesc;

    private String functionLogicalDesc;

    private String outputLogicalDesc;

    private String testMethod;

    private Date createTime;

    private Date updateTime;

    /**
     * 获取ID
     * 
     * @return 返回ID值
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     * 
     * @param id 要设置的ID值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取功能描述
     * 
     * @return 返回功能描述
     */
    public String getFunctionDescription() {
        return functionDescription;
    }

    /**
     * 设置功能描述
     * 
     * @param functionDescription 要设置的功能描述
     */
    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    /**
     * 获取工作条件
     * 
     * @return 返回工作条件
     */
    public String getWorkCondition() {
        return workCondition;
    }

    /**
     * 设置工作条件
     * 
     * @param workCondition 要设置的工作条件
     */
    public void setWorkCondition(String workCondition) {
        this.workCondition = workCondition;
    }

    /**
     * 获取输入逻辑描述
     * 
     * @return 返回输入逻辑描述
     */
    public String getInputLogicalDesc() {
        return inputLogicalDesc;
    }

    /**
     * 设置输入逻辑描述
     * 
     * @param inputLogicalDesc 要设置的输入逻辑描述
     */
    public void setInputLogicalDesc(String inputLogicalDesc) {
        this.inputLogicalDesc = inputLogicalDesc;
    }

    /**
     * 获取功能逻辑描述
     * 
     * @return 返回功能逻辑描述
     */
    public String getFunctionLogicalDesc() {
        return functionLogicalDesc;
    }

    /**
     * 设置功能逻辑描述
     * 
     * @param functionLogicalDesc 要设置的功能逻辑描述
     */
    public void setFunctionLogicalDesc(String functionLogicalDesc) {
        this.functionLogicalDesc = functionLogicalDesc;
    }

    /**
     * 获取输出逻辑描述
     * 
     * @return 返回输出逻辑描述
     */
    public String getOutputLogicalDesc() {
        return outputLogicalDesc;
    }

    /**
     * 设置输出逻辑描述
     * 
     * @param outputLogicalDesc 要设置的输出逻辑描述
     */
    public void setOutputLogicalDesc(String outputLogicalDesc) {
        this.outputLogicalDesc = outputLogicalDesc;
    }

    /**
     * 获取测试方法
     * 
     * @return 返回测试方法
     */
    public String getTestMethod() {
        return testMethod;
    }

    /**
     * 设置测试方法
     * 
     * @param testMethod 要设置的测试方法
     */
    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    /**
     * 获取创建时间
     * 
     * @return 返回创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 
     * @return 返回更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     * 
     * @param updateTime 要设置的更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 重写toString方法
     * 返回对象的字符串表示，包含所有字段信息
     * 
     * @return 返回对象的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", functionDescription=").append(functionDescription);
        sb.append(", workCondition=").append(workCondition);
        sb.append(", inputLogicalDesc=").append(inputLogicalDesc);
        sb.append(", functionLogicalDesc=").append(functionLogicalDesc);
        sb.append(", outputLogicalDesc=").append(outputLogicalDesc);
        sb.append(", testMethod=").append(testMethod);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}