/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsCbb {
    private String id;

    private String cbbSerial;

    private String characterId;

    private String customerId;

    private String cbbName;

    private String cbbIntroduce;

    private String supervise;

    private String productManagerId;

    private String projectManagerId;

    private String testManagerId;

    private String systemManagerId;

    private String createrId;

    private String keyMemberListStr;

    private String fileIdListStr;

    private String moduleIdListStr;

    private String status;

    private String devVersion;

    private String type;

    private String jobitemType;

    private String relatedId;

    private Date createTime;

    private Date releaseTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCbbSerial() {
        return cbbSerial;
    }

    public void setCbbSerial(String cbbSerial) {
        this.cbbSerial = cbbSerial;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCbbName() {
        return cbbName;
    }

    public void setCbbName(String cbbName) {
        this.cbbName = cbbName;
    }

    public String getCbbIntroduce() {
        return cbbIntroduce;
    }

    public void setCbbIntroduce(String cbbIntroduce) {
        this.cbbIntroduce = cbbIntroduce;
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

    public String getFileIdListStr() {
        return fileIdListStr;
    }

    public void setFileIdListStr(String fileIdListStr) {
        this.fileIdListStr = fileIdListStr;
    }

    public String getModuleIdListStr() {
        return moduleIdListStr;
    }

    public void setModuleIdListStr(String moduleIdListStr) {
        this.moduleIdListStr = moduleIdListStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(String devVersion) {
        this.devVersion = devVersion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJobitemType() {
        return jobitemType;
    }

    public void setJobitemType(String jobitemType) {
        this.jobitemType = jobitemType;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
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
        sb.append(", cbbSerial=").append(cbbSerial);
        sb.append(", characterId=").append(characterId);
        sb.append(", customerId=").append(customerId);
        sb.append(", cbbName=").append(cbbName);
        sb.append(", cbbIntroduce=").append(cbbIntroduce);
        sb.append(", supervise=").append(supervise);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", projectManagerId=").append(projectManagerId);
        sb.append(", testManagerId=").append(testManagerId);
        sb.append(", systemManagerId=").append(systemManagerId);
        sb.append(", createrId=").append(createrId);
        sb.append(", keyMemberListStr=").append(keyMemberListStr);
        sb.append(", fileIdListStr=").append(fileIdListStr);
        sb.append(", moduleIdListStr=").append(moduleIdListStr);
        sb.append(", status=").append(status);
        sb.append(", devVersion=").append(devVersion);
        sb.append(", type=").append(type);
        sb.append(", jobitemType=").append(jobitemType);
        sb.append(", relatedId=").append(relatedId);
        sb.append(", createTime=").append(createTime);
        sb.append(", releaseTime=").append(releaseTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}
