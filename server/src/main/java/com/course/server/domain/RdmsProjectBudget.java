/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsProjectBudget {
    private String id;

    private String customerId;

    private String projectId;

    private BigDecimal devManhour;

    private BigDecimal devManhourFee;

    private BigDecimal testManhour;

    private BigDecimal testManhourFee;

    private BigDecimal materialFee;

    private BigDecimal otherFee;

    private Date createTime;

    private String deleted;

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

    public BigDecimal getDevManhour() {
        return devManhour;
    }

    public void setDevManhour(BigDecimal devManhour) {
        this.devManhour = devManhour;
    }

    public BigDecimal getDevManhourFee() {
        return devManhourFee;
    }

    public void setDevManhourFee(BigDecimal devManhourFee) {
        this.devManhourFee = devManhourFee;
    }

    public BigDecimal getTestManhour() {
        return testManhour;
    }

    public void setTestManhour(BigDecimal testManhour) {
        this.testManhour = testManhour;
    }

    public BigDecimal getTestManhourFee() {
        return testManhourFee;
    }

    public void setTestManhourFee(BigDecimal testManhourFee) {
        this.testManhourFee = testManhourFee;
    }

    public BigDecimal getMaterialFee() {
        return materialFee;
    }

    public void setMaterialFee(BigDecimal materialFee) {
        this.materialFee = materialFee;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
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
        sb.append(", devManhour=").append(devManhour);
        sb.append(", devManhourFee=").append(devManhourFee);
        sb.append(", testManhour=").append(testManhour);
        sb.append(", testManhourFee=").append(testManhourFee);
        sb.append(", materialFee=").append(materialFee);
        sb.append(", otherFee=").append(otherFee);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}