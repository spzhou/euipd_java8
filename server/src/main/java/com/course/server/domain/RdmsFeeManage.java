/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsFeeManage {
    private String id;

    private String code;

    private String name;

    private String characterId;

    private String qualityId;

    private String jobitemId;

    private String customerId;

    private String projectId;

    private String subprojectId;

    private String preProjectId;

    private String type;

    private BigDecimal accountCost;

    private BigDecimal appliedAmount;

    private BigDecimal actualCost;

    private String capitalSource;

    private String writerId;

    private String approverId;

    private String nextNode;

    private String reason;

    private String approveDescription;

    private String status;

    private String auxStatus;

    private String jobType;

    private String approveFileListStr;

    private String dealWithFileListStr;

    private String submitDescription;

    private String submitApproveDescription;

    private String completeFileListStr;

    private Date approveTime;

    private Date updateTime;

    private Date createTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getJobitemId() {
        return jobitemId;
    }

    public void setJobitemId(String jobitemId) {
        this.jobitemId = jobitemId;
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

    public String getPreProjectId() {
        return preProjectId;
    }

    public void setPreProjectId(String preProjectId) {
        this.preProjectId = preProjectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAccountCost() {
        return accountCost;
    }

    public void setAccountCost(BigDecimal accountCost) {
        this.accountCost = accountCost;
    }

    public BigDecimal getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(BigDecimal appliedAmount) {
        this.appliedAmount = appliedAmount;
    }

    public BigDecimal getActualCost() {
        return actualCost;
    }

    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    public String getCapitalSource() {
        return capitalSource;
    }

    public void setCapitalSource(String capitalSource) {
        this.capitalSource = capitalSource;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApproveDescription() {
        return approveDescription;
    }

    public void setApproveDescription(String approveDescription) {
        this.approveDescription = approveDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuxStatus() {
        return auxStatus;
    }

    public void setAuxStatus(String auxStatus) {
        this.auxStatus = auxStatus;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getApproveFileListStr() {
        return approveFileListStr;
    }

    public void setApproveFileListStr(String approveFileListStr) {
        this.approveFileListStr = approveFileListStr;
    }

    public String getDealWithFileListStr() {
        return dealWithFileListStr;
    }

    public void setDealWithFileListStr(String dealWithFileListStr) {
        this.dealWithFileListStr = dealWithFileListStr;
    }

    public String getSubmitDescription() {
        return submitDescription;
    }

    public void setSubmitDescription(String submitDescription) {
        this.submitDescription = submitDescription;
    }

    public String getSubmitApproveDescription() {
        return submitApproveDescription;
    }

    public void setSubmitApproveDescription(String submitApproveDescription) {
        this.submitApproveDescription = submitApproveDescription;
    }

    public String getCompleteFileListStr() {
        return completeFileListStr;
    }

    public void setCompleteFileListStr(String completeFileListStr) {
        this.completeFileListStr = completeFileListStr;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", characterId=").append(characterId);
        sb.append(", qualityId=").append(qualityId);
        sb.append(", jobitemId=").append(jobitemId);
        sb.append(", customerId=").append(customerId);
        sb.append(", projectId=").append(projectId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", type=").append(type);
        sb.append(", accountCost=").append(accountCost);
        sb.append(", appliedAmount=").append(appliedAmount);
        sb.append(", actualCost=").append(actualCost);
        sb.append(", capitalSource=").append(capitalSource);
        sb.append(", writerId=").append(writerId);
        sb.append(", approverId=").append(approverId);
        sb.append(", nextNode=").append(nextNode);
        sb.append(", reason=").append(reason);
        sb.append(", approveDescription=").append(approveDescription);
        sb.append(", status=").append(status);
        sb.append(", auxStatus=").append(auxStatus);
        sb.append(", jobType=").append(jobType);
        sb.append(", approveFileListStr=").append(approveFileListStr);
        sb.append(", dealWithFileListStr=").append(dealWithFileListStr);
        sb.append(", submitDescription=").append(submitDescription);
        sb.append(", submitApproveDescription=").append(submitApproveDescription);
        sb.append(", completeFileListStr=").append(completeFileListStr);
        sb.append(", approveTime=").append(approveTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}