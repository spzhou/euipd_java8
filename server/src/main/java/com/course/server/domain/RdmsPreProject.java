/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsPreProject {
    private String id;

    private String projectCode;

    private String customerId;

    private String preProjectName;

    private BigDecimal devVersion;

    private String preProjectIntroduce;

    private String productManagerId;

    private String systemManagerId;

    private String rdType;

    private Date createTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPreProjectName() {
        return preProjectName;
    }

    public void setPreProjectName(String preProjectName) {
        this.preProjectName = preProjectName;
    }

    public BigDecimal getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(BigDecimal devVersion) {
        this.devVersion = devVersion;
    }

    public String getPreProjectIntroduce() {
        return preProjectIntroduce;
    }

    public void setPreProjectIntroduce(String preProjectIntroduce) {
        this.preProjectIntroduce = preProjectIntroduce;
    }

    public String getProductManagerId() {
        return productManagerId;
    }

    public void setProductManagerId(String productManagerId) {
        this.productManagerId = productManagerId;
    }

    public String getSystemManagerId() {
        return systemManagerId;
    }

    public void setSystemManagerId(String systemManagerId) {
        this.systemManagerId = systemManagerId;
    }

    public String getRdType() {
        return rdType;
    }

    public void setRdType(String rdType) {
        this.rdType = rdType;
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
        sb.append(", projectCode=").append(projectCode);
        sb.append(", customerId=").append(customerId);
        sb.append(", preProjectName=").append(preProjectName);
        sb.append(", devVersion=").append(devVersion);
        sb.append(", preProjectIntroduce=").append(preProjectIntroduce);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", systemManagerId=").append(systemManagerId);
        sb.append(", rdType=").append(rdType);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}