/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsBudgetResearchExe {
    private String id;

    private String customerId;

    private String projectId;

    private String parentId;

    private String subprojectId;

    private String preprojectId;

    private String payClassify;

    private String payId;

    private String serialNo;

    private String name;

    private String executorId;

    private String classify;

    private String capitalSource;

    private BigDecimal amount;

    private String paymentStatue;

    private Date paymentTime;

    private Date createTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(String subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getPreprojectId() {
        return preprojectId;
    }

    public void setPreprojectId(String preprojectId) {
        this.preprojectId = preprojectId;
    }

    public String getPayClassify() {
        return payClassify;
    }

    public void setPayClassify(String payClassify) {
        this.payClassify = payClassify;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getCapitalSource() {
        return capitalSource;
    }

    public void setCapitalSource(String capitalSource) {
        this.capitalSource = capitalSource;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentStatue() {
        return paymentStatue;
    }

    public void setPaymentStatue(String paymentStatue) {
        this.paymentStatue = paymentStatue;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", projectId=").append(projectId);
        sb.append(", parentId=").append(parentId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", preprojectId=").append(preprojectId);
        sb.append(", payClassify=").append(payClassify);
        sb.append(", payId=").append(payId);
        sb.append(", serialNo=").append(serialNo);
        sb.append(", name=").append(name);
        sb.append(", executorId=").append(executorId);
        sb.append(", classify=").append(classify);
        sb.append(", capitalSource=").append(capitalSource);
        sb.append(", amount=").append(amount);
        sb.append(", paymentStatue=").append(paymentStatue);
        sb.append(", paymentTime=").append(paymentTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}
