/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsJobItemProcess {
    private String id;

    private String jobItemId;

    private String executorId;

    private Integer deep;

    private String jobDescription;

    private String fileListStr;

    private String nextNode;

    private Date actualSubmissionTime;

    private String processStatus;

    private String characterIdListStr;

    private String approvalStatus;

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

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public Date getActualSubmissionTime() {
        return actualSubmissionTime;
    }

    public void setActualSubmissionTime(Date actualSubmissionTime) {
        this.actualSubmissionTime = actualSubmissionTime;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getCharacterIdListStr() {
        return characterIdListStr;
    }

    public void setCharacterIdListStr(String characterIdListStr) {
        this.characterIdListStr = characterIdListStr;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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
        sb.append(", executorId=").append(executorId);
        sb.append(", deep=").append(deep);
        sb.append(", jobDescription=").append(jobDescription);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", nextNode=").append(nextNode);
        sb.append(", actualSubmissionTime=").append(actualSubmissionTime);
        sb.append(", processStatus=").append(processStatus);
        sb.append(", characterIdListStr=").append(characterIdListStr);
        sb.append(", approvalStatus=").append(approvalStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}