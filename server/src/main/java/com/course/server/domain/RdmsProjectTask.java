/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsProjectTask {
    private String id;

    private String taskSerialNo;

    private String customerId;

    private String preProjectId;

    private String projectId;

    private String subprojectId;

    private String productManagerId;

    private String projectManagerId;

    private String taskName;

    private String taskDescription;

    private String testMethod;

    private String fileListStr;

    private String managerId;

    private String status;

    private String jobitemType;

    private String jobitemId;

    private Integer treeDeep;

    private Date createTime;

    private Date submitTime;

    private BigDecimal budget;

    private String parentTaskId;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskSerialNo() {
        return taskSerialNo;
    }

    public void setTaskSerialNo(String taskSerialNo) {
        this.taskSerialNo = taskSerialNo;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobitemType() {
        return jobitemType;
    }

    public void setJobitemType(String jobitemType) {
        this.jobitemType = jobitemType;
    }

    public String getJobitemId() {
        return jobitemId;
    }

    public void setJobitemId(String jobitemId) {
        this.jobitemId = jobitemId;
    }

    public Integer getTreeDeep() {
        return treeDeep;
    }

    public void setTreeDeep(Integer treeDeep) {
        this.treeDeep = treeDeep;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
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
        sb.append(", taskSerialNo=").append(taskSerialNo);
        sb.append(", customerId=").append(customerId);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", projectId=").append(projectId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", projectManagerId=").append(projectManagerId);
        sb.append(", taskName=").append(taskName);
        sb.append(", taskDescription=").append(taskDescription);
        sb.append(", testMethod=").append(testMethod);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", managerId=").append(managerId);
        sb.append(", status=").append(status);
        sb.append(", jobitemType=").append(jobitemType);
        sb.append(", jobitemId=").append(jobitemId);
        sb.append(", treeDeep=").append(treeDeep);
        sb.append(", createTime=").append(createTime);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", budget=").append(budget);
        sb.append(", parentTaskId=").append(parentTaskId);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}