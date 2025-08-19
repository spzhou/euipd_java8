/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsJobItemGantt {
    private String id;

    private String jobSerial;

    private String jobName;

    private String customerId;

    private String preProjectId;

    private String projectId;

    private String subprojectId;

    private String characterId;

    private String createrId;

    private String parentJobitemId;

    private String projectManagerId;

    private String productManagerId;

    private String testManagerId;

    private String executorId;

    private String nextNode;

    private String status;

    private String type;

    private String projectType;

    private String fileListStr;

    private Double manhour;

    private BigDecimal standManhourFee;

    private String taskDescription;

    private Date createTime;

    private Date planStartTime;

    private Date planSubmissionTime;

    private Date updateTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobSerial() {
        return jobSerial;
    }

    public void setJobSerial(String jobSerial) {
        this.jobSerial = jobSerial;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(String subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getParentJobitemId() {
        return parentJobitemId;
    }

    public void setParentJobitemId(String parentJobitemId) {
        this.parentJobitemId = parentJobitemId;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getProductManagerId() {
        return productManagerId;
    }

    public void setProductManagerId(String productManagerId) {
        this.productManagerId = productManagerId;
    }

    public String getTestManagerId() {
        return testManagerId;
    }

    public void setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public Double getManhour() {
        return manhour;
    }

    public void setManhour(Double manhour) {
        this.manhour = manhour;
    }

    public BigDecimal getStandManhourFee() {
        return standManhourFee;
    }

    public void setStandManhourFee(BigDecimal standManhourFee) {
        this.standManhourFee = standManhourFee;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanSubmissionTime() {
        return planSubmissionTime;
    }

    public void setPlanSubmissionTime(Date planSubmissionTime) {
        this.planSubmissionTime = planSubmissionTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        sb.append(", jobSerial=").append(jobSerial);
        sb.append(", jobName=").append(jobName);
        sb.append(", customerId=").append(customerId);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", projectId=").append(projectId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", characterId=").append(characterId);
        sb.append(", createrId=").append(createrId);
        sb.append(", parentJobitemId=").append(parentJobitemId);
        sb.append(", projectManagerId=").append(projectManagerId);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", testManagerId=").append(testManagerId);
        sb.append(", executorId=").append(executorId);
        sb.append(", nextNode=").append(nextNode);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", projectType=").append(projectType);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", manhour=").append(manhour);
        sb.append(", standManhourFee=").append(standManhourFee);
        sb.append(", taskDescription=").append(taskDescription);
        sb.append(", createTime=").append(createTime);
        sb.append(", planStartTime=").append(planStartTime);
        sb.append(", planSubmissionTime=").append(planSubmissionTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}