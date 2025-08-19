/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsProjectSubproject {
    private String id;

    private String parent;

    private Integer deep;

    private String projectCode;

    private String preProjectId;

    private String projectId;

    private String customerId;

    private String label;

    private String introduce;

    private String supervise;

    private String productManagerId;

    private String projectManagerId;

    private String testManagerId;

    private String keyMemberListStr;

    private String fileListStr;

    private BigDecimal budget;

    private BigDecimal addCharge;

    private BigDecimal chargeRate;

    private String incentivePolicy;

    private String status;

    private String rdType;

    private String creatorId;

    private BigDecimal devVersion;

    private String targetMilestoneId;

    private Date createTime;

    private Date setupedTime;

    private Date releaseTime;

    private String type;

    private String capitalSource;

    private String moduleIdListStr;

    private String bomStatus;

    private Integer bomVersion;

    private String outSourceStatus;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPreProjectId() {
        return preProjectId;
    }

    public void setPreProjectId(String preProjectId) {
        this.preProjectId = preProjectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSupervise() {
        return supervise;
    }

    public void setSupervise(String supervise) {
        this.supervise = supervise;
    }

    public String getProductManagerId() {
        return productManagerId;
    }

    public void setProductManagerId(String productManagerId) {
        this.productManagerId = productManagerId;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getTestManagerId() {
        return testManagerId;
    }

    public void setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
    }

    public String getKeyMemberListStr() {
        return keyMemberListStr;
    }

    public void setKeyMemberListStr(String keyMemberListStr) {
        this.keyMemberListStr = keyMemberListStr;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getAddCharge() {
        return addCharge;
    }

    public void setAddCharge(BigDecimal addCharge) {
        this.addCharge = addCharge;
    }

    public BigDecimal getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(BigDecimal chargeRate) {
        this.chargeRate = chargeRate;
    }

    public String getIncentivePolicy() {
        return incentivePolicy;
    }

    public void setIncentivePolicy(String incentivePolicy) {
        this.incentivePolicy = incentivePolicy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRdType() {
        return rdType;
    }

    public void setRdType(String rdType) {
        this.rdType = rdType;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public BigDecimal getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(BigDecimal devVersion) {
        this.devVersion = devVersion;
    }

    public String getTargetMilestoneId() {
        return targetMilestoneId;
    }

    public void setTargetMilestoneId(String targetMilestoneId) {
        this.targetMilestoneId = targetMilestoneId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSetupedTime() {
        return setupedTime;
    }

    public void setSetupedTime(Date setupedTime) {
        this.setupedTime = setupedTime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapitalSource() {
        return capitalSource;
    }

    public void setCapitalSource(String capitalSource) {
        this.capitalSource = capitalSource;
    }

    public String getModuleIdListStr() {
        return moduleIdListStr;
    }

    public void setModuleIdListStr(String moduleIdListStr) {
        this.moduleIdListStr = moduleIdListStr;
    }

    public String getBomStatus() {
        return bomStatus;
    }

    public void setBomStatus(String bomStatus) {
        this.bomStatus = bomStatus;
    }

    public Integer getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(Integer bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getOutSourceStatus() {
        return outSourceStatus;
    }

    public void setOutSourceStatus(String outSourceStatus) {
        this.outSourceStatus = outSourceStatus;
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
        sb.append(", parent=").append(parent);
        sb.append(", deep=").append(deep);
        sb.append(", projectCode=").append(projectCode);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", projectId=").append(projectId);
        sb.append(", customerId=").append(customerId);
        sb.append(", label=").append(label);
        sb.append(", introduce=").append(introduce);
        sb.append(", supervise=").append(supervise);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", projectManagerId=").append(projectManagerId);
        sb.append(", testManagerId=").append(testManagerId);
        sb.append(", keyMemberListStr=").append(keyMemberListStr);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", budget=").append(budget);
        sb.append(", addCharge=").append(addCharge);
        sb.append(", chargeRate=").append(chargeRate);
        sb.append(", incentivePolicy=").append(incentivePolicy);
        sb.append(", status=").append(status);
        sb.append(", rdType=").append(rdType);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", devVersion=").append(devVersion);
        sb.append(", targetMilestoneId=").append(targetMilestoneId);
        sb.append(", createTime=").append(createTime);
        sb.append(", setupedTime=").append(setupedTime);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", type=").append(type);
        sb.append(", capitalSource=").append(capitalSource);
        sb.append(", moduleIdListStr=").append(moduleIdListStr);
        sb.append(", bomStatus=").append(bomStatus);
        sb.append(", bomVersion=").append(bomVersion);
        sb.append(", outSourceStatus=").append(outSourceStatus);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}