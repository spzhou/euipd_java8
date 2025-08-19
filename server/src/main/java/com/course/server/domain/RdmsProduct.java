/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsProduct {
    private String id;

    private String customerId;

    private String productCode;

    private String productName;

    private String description;

    private BigDecimal budget;

    private String ipmtId;

    private String productManagerId;

    private String projectId;

    private String status;

    private Date setupTime;

    private Date releaseTimePlan;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getIpmtId() {
        return ipmtId;
    }

    public void setIpmtId(String ipmtId) {
        this.ipmtId = ipmtId;
    }

    public String getProductManagerId() {
        return productManagerId;
    }

    public void setProductManagerId(String productManagerId) {
        this.productManagerId = productManagerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(Date setupTime) {
        this.setupTime = setupTime;
    }

    public Date getReleaseTimePlan() {
        return releaseTimePlan;
    }

    public void setReleaseTimePlan(Date releaseTimePlan) {
        this.releaseTimePlan = releaseTimePlan;
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
        sb.append(", productCode=").append(productCode);
        sb.append(", productName=").append(productName);
        sb.append(", description=").append(description);
        sb.append(", budget=").append(budget);
        sb.append(", ipmtId=").append(ipmtId);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", projectId=").append(projectId);
        sb.append(", status=").append(status);
        sb.append(", setupTime=").append(setupTime);
        sb.append(", releaseTimePlan=").append(releaseTimePlan);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}