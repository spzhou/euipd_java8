/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsProject {
    private String id;

    private String projectCode;

    private String preProjectId;

    private String customerId;

    private String projectName;

    private String projectIntroduce;

    private String supervise;

    private String productManagerId;

    private String projectManagerId;

    private String testManagerId;

    private String systemManagerId;

    private String createrId;

    private String keyMemberListStr;

    private String fileListStr;

    private BigDecimal budget;

    private BigDecimal addCharge;

    private BigDecimal chargeRate;

    private String incentivePolicy;

    private String status;

    private String rdType;

    private BigDecimal devVersion;

    private Date createTime;

    private Date setupedTime;

    private Date releaseTime;

    private String versionUpdate;

    private String joinedProjectIdsStr;

    private String moduleIdListStr;

    private String categoryIdListStr;

    private Integer deleted;

    private String iterationIdStr;

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

    public String getPreProjectId() {
        return preProjectId;
    }

    public void setPreProjectId(String preProjectId) {
        this.preProjectId = preProjectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectIntroduce() {
        return projectIntroduce;
    }

    public void setProjectIntroduce(String projectIntroduce) {
        this.projectIntroduce = projectIntroduce;
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

    public String getSystemManagerId() {
        return systemManagerId;
    }

    public void setSystemManagerId(String systemManagerId) {
        this.systemManagerId = systemManagerId;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
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

    public BigDecimal getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(BigDecimal devVersion) {
        this.devVersion = devVersion;
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

    public String getVersionUpdate() {
        return versionUpdate;
    }

    public void setVersionUpdate(String versionUpdate) {
        this.versionUpdate = versionUpdate;
    }

    public String getJoinedProjectIdsStr() {
        return joinedProjectIdsStr;
    }

    public void setJoinedProjectIdsStr(String joinedProjectIdsStr) {
        this.joinedProjectIdsStr = joinedProjectIdsStr;
    }

    public String getModuleIdListStr() {
        return moduleIdListStr;
    }

    public void setModuleIdListStr(String moduleIdListStr) {
        this.moduleIdListStr = moduleIdListStr;
    }

    public String getCategoryIdListStr() {
        return categoryIdListStr;
    }

    public void setCategoryIdListStr(String categoryIdListStr) {
        this.categoryIdListStr = categoryIdListStr;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getIterationIdStr() {
        return iterationIdStr;
    }

    public void setIterationIdStr(String iterationIdStr) {
        this.iterationIdStr = iterationIdStr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectCode=").append(projectCode);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", customerId=").append(customerId);
        sb.append(", projectName=").append(projectName);
        sb.append(", projectIntroduce=").append(projectIntroduce);
        sb.append(", supervise=").append(supervise);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", projectManagerId=").append(projectManagerId);
        sb.append(", testManagerId=").append(testManagerId);
        sb.append(", systemManagerId=").append(systemManagerId);
        sb.append(", createrId=").append(createrId);
        sb.append(", keyMemberListStr=").append(keyMemberListStr);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", budget=").append(budget);
        sb.append(", addCharge=").append(addCharge);
        sb.append(", chargeRate=").append(chargeRate);
        sb.append(", incentivePolicy=").append(incentivePolicy);
        sb.append(", status=").append(status);
        sb.append(", rdType=").append(rdType);
        sb.append(", devVersion=").append(devVersion);
        sb.append(", createTime=").append(createTime);
        sb.append(", setupedTime=").append(setupedTime);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", versionUpdate=").append(versionUpdate);
        sb.append(", joinedProjectIdsStr=").append(joinedProjectIdsStr);
        sb.append(", moduleIdListStr=").append(moduleIdListStr);
        sb.append(", categoryIdListStr=").append(categoryIdListStr);
        sb.append(", deleted=").append(deleted);
        sb.append(", iterationIdStr=").append(iterationIdStr);
        sb.append("]");
        return sb.toString();
    }
}