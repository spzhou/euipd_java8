/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsJobItem2 {
    private String id;

    private String jobSerial;

    private String jobName;

    private String customerId;

    private String createrId;

    private String preProjectId;

    private String projectId;

    private String subprojectId;

    private String characterId;

    private String qualityId;

    private String testedJobitemId;

    private String parentJobitemId;

    private String projectManagerId;

    private String productManagerId;

    private String testManagerId;

    private String executorId;

    private String nextNode;

    private String status;

    private String type;

    private String auxType;

    private String projectType;

    private String demandIdStr;

    private String mergeCharacterIdStr;

    private String fileListStr;

    private Double manhour;

    private BigDecimal standManhourFee;

    private Double performanceManhour;

    private String taskDescription;

    private Date createTime;

    private Date planSubmissionTime;

    private Date actualSubmissionTime;

    private String propertyId;

    private String reviewWorkerIdStr;

    private String reviewResult;

    private String evaluateDesc;

    private Integer evaluateScore;

    private Date updateTime;

    private Date completeTime;

    private Integer settleAccounts;

    private Integer deleted;

    private String note;

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

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
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

    public String getQualityId() {
        return qualityId;
    }

    public void setQualityId(String qualityId) {
        this.qualityId = qualityId;
    }

    public String getTestedJobitemId() {
        return testedJobitemId;
    }

    public void setTestedJobitemId(String testedJobitemId) {
        this.testedJobitemId = testedJobitemId;
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

    public String getAuxType() {
        return auxType;
    }

    public void setAuxType(String auxType) {
        this.auxType = auxType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDemandIdStr() {
        return demandIdStr;
    }

    public void setDemandIdStr(String demandIdStr) {
        this.demandIdStr = demandIdStr;
    }

    public String getMergeCharacterIdStr() {
        return mergeCharacterIdStr;
    }

    public void setMergeCharacterIdStr(String mergeCharacterIdStr) {
        this.mergeCharacterIdStr = mergeCharacterIdStr;
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

    public Double getPerformanceManhour() {
        return performanceManhour;
    }

    public void setPerformanceManhour(Double performanceManhour) {
        this.performanceManhour = performanceManhour;
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

    public Date getPlanSubmissionTime() {
        return planSubmissionTime;
    }

    public void setPlanSubmissionTime(Date planSubmissionTime) {
        this.planSubmissionTime = planSubmissionTime;
    }

    public Date getActualSubmissionTime() {
        return actualSubmissionTime;
    }

    public void setActualSubmissionTime(Date actualSubmissionTime) {
        this.actualSubmissionTime = actualSubmissionTime;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getReviewWorkerIdStr() {
        return reviewWorkerIdStr;
    }

    public void setReviewWorkerIdStr(String reviewWorkerIdStr) {
        this.reviewWorkerIdStr = reviewWorkerIdStr;
    }

    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    public String getEvaluateDesc() {
        return evaluateDesc;
    }

    public void setEvaluateDesc(String evaluateDesc) {
        this.evaluateDesc = evaluateDesc;
    }

    public Integer getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(Integer evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getSettleAccounts() {
        return settleAccounts;
    }

    public void setSettleAccounts(Integer settleAccounts) {
        this.settleAccounts = settleAccounts;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        sb.append(", createrId=").append(createrId);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", projectId=").append(projectId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", characterId=").append(characterId);
        sb.append(", qualityId=").append(qualityId);
        sb.append(", testedJobitemId=").append(testedJobitemId);
        sb.append(", parentJobitemId=").append(parentJobitemId);
        sb.append(", projectManagerId=").append(projectManagerId);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", testManagerId=").append(testManagerId);
        sb.append(", executorId=").append(executorId);
        sb.append(", nextNode=").append(nextNode);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", auxType=").append(auxType);
        sb.append(", projectType=").append(projectType);
        sb.append(", demandIdStr=").append(demandIdStr);
        sb.append(", mergeCharacterIdStr=").append(mergeCharacterIdStr);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", manhour=").append(manhour);
        sb.append(", standManhourFee=").append(standManhourFee);
        sb.append(", performanceManhour=").append(performanceManhour);
        sb.append(", taskDescription=").append(taskDescription);
        sb.append(", createTime=").append(createTime);
        sb.append(", planSubmissionTime=").append(planSubmissionTime);
        sb.append(", actualSubmissionTime=").append(actualSubmissionTime);
        sb.append(", propertyId=").append(propertyId);
        sb.append(", reviewWorkerIdStr=").append(reviewWorkerIdStr);
        sb.append(", reviewResult=").append(reviewResult);
        sb.append(", evaluateDesc=").append(evaluateDesc);
        sb.append(", evaluateScore=").append(evaluateScore);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", settleAccounts=").append(settleAccounts);
        sb.append(", deleted=").append(deleted);
        sb.append(", note=").append(note);
        sb.append("]");
        return sb.toString();
    }
}