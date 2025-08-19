/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsBugFeedback {
    private String id;

    private String bugSerial;

    private String name;

    private String customerId;

    private String projectId;

    private String subprojectId;

    private String productManagerId;

    private String projectManagerId;

    private String testManagerId;

    private String associateProjectStr;

    private String fileListStr;

    private String consumer;

    private String writerId;

    private String confirmTel;

    private String status;

    private String characterId;

    private String jobitemId;

    private String executorId;

    private String type;

    private String bugDescription;

    private String triggerCondition;

    private String analysisName;

    private String essentialAnalysis;

    private String solution;

    private String outputIndicator;

    private String testMethod;

    private Date planSubmissionTime;

    private String feedbackDescription;

    private String feedbackFileIdListStr;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBugSerial() {
        return bugSerial;
    }

    public void setBugSerial(String bugSerial) {
        this.bugSerial = bugSerial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTestManagerId() {
        return testManagerId;
    }

    public void setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
    }

    public String getAssociateProjectStr() {
        return associateProjectStr;
    }

    public void setAssociateProjectStr(String associateProjectStr) {
        this.associateProjectStr = associateProjectStr;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getConfirmTel() {
        return confirmTel;
    }

    public void setConfirmTel(String confirmTel) {
        this.confirmTel = confirmTel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getJobitemId() {
        return jobitemId;
    }

    public void setJobitemId(String jobitemId) {
        this.jobitemId = jobitemId;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBugDescription() {
        return bugDescription;
    }

    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }

    public String getTriggerCondition() {
        return triggerCondition;
    }

    public void setTriggerCondition(String triggerCondition) {
        this.triggerCondition = triggerCondition;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    public String getEssentialAnalysis() {
        return essentialAnalysis;
    }

    public void setEssentialAnalysis(String essentialAnalysis) {
        this.essentialAnalysis = essentialAnalysis;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getOutputIndicator() {
        return outputIndicator;
    }

    public void setOutputIndicator(String outputIndicator) {
        this.outputIndicator = outputIndicator;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public Date getPlanSubmissionTime() {
        return planSubmissionTime;
    }

    public void setPlanSubmissionTime(Date planSubmissionTime) {
        this.planSubmissionTime = planSubmissionTime;
    }

    public String getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(String feedbackDescription) {
        this.feedbackDescription = feedbackDescription;
    }

    public String getFeedbackFileIdListStr() {
        return feedbackFileIdListStr;
    }

    public void setFeedbackFileIdListStr(String feedbackFileIdListStr) {
        this.feedbackFileIdListStr = feedbackFileIdListStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", bugSerial=").append(bugSerial);
        sb.append(", name=").append(name);
        sb.append(", customerId=").append(customerId);
        sb.append(", projectId=").append(projectId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", projectManagerId=").append(projectManagerId);
        sb.append(", testManagerId=").append(testManagerId);
        sb.append(", associateProjectStr=").append(associateProjectStr);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", consumer=").append(consumer);
        sb.append(", writerId=").append(writerId);
        sb.append(", confirmTel=").append(confirmTel);
        sb.append(", status=").append(status);
        sb.append(", characterId=").append(characterId);
        sb.append(", jobitemId=").append(jobitemId);
        sb.append(", executorId=").append(executorId);
        sb.append(", type=").append(type);
        sb.append(", bugDescription=").append(bugDescription);
        sb.append(", triggerCondition=").append(triggerCondition);
        sb.append(", analysisName=").append(analysisName);
        sb.append(", essentialAnalysis=").append(essentialAnalysis);
        sb.append(", solution=").append(solution);
        sb.append(", outputIndicator=").append(outputIndicator);
        sb.append(", testMethod=").append(testMethod);
        sb.append(", planSubmissionTime=").append(planSubmissionTime);
        sb.append(", feedbackDescription=").append(feedbackDescription);
        sb.append(", feedbackFileIdListStr=").append(feedbackFileIdListStr);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}