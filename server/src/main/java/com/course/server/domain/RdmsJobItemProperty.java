/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsJobItemProperty {
    private String id;

    private String jobItemId;

    private String jobDescription;

    private String testKeyPoints;

    private String fileListStr;

    private String referenceFileListStr;

    private Date createTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobItemId() {
        return jobItemId;
    }

    public void setJobItemId(String jobItemId) {
        this.jobItemId = jobItemId;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getTestKeyPoints() {
        return testKeyPoints;
    }

    public void setTestKeyPoints(String testKeyPoints) {
        this.testKeyPoints = testKeyPoints;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public String getReferenceFileListStr() {
        return referenceFileListStr;
    }

    public void setReferenceFileListStr(String referenceFileListStr) {
        this.referenceFileListStr = referenceFileListStr;
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
        sb.append(", jobItemId=").append(jobItemId);
        sb.append(", jobDescription=").append(jobDescription);
        sb.append(", testKeyPoints=").append(testKeyPoints);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", referenceFileListStr=").append(referenceFileListStr);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}