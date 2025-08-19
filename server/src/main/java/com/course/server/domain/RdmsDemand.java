/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsDemand {
    private String id;

    private String customerId;

    private String preProjectId;

    private String productManagerId;

    private String demandName;

    private String demandCustomerName;

    private String confirmPersonName;

    private String confirmContactTel;

    private String demandDescription;

    private String workCondition;

    private String fileListStr;

    private String writerId;

    private String nextNode;

    private String status;

    private Date createTime;

    private Date submitTime;

    private String jobitemId;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProductManagerId() {
        return productManagerId;
    }

    public void setProductManagerId(String productManagerId) {
        this.productManagerId = productManagerId;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getDemandCustomerName() {
        return demandCustomerName;
    }

    public void setDemandCustomerName(String demandCustomerName) {
        this.demandCustomerName = demandCustomerName;
    }

    public String getConfirmPersonName() {
        return confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName) {
        this.confirmPersonName = confirmPersonName;
    }

    public String getConfirmContactTel() {
        return confirmContactTel;
    }

    public void setConfirmContactTel(String confirmContactTel) {
        this.confirmContactTel = confirmContactTel;
    }

    public String getDemandDescription() {
        return demandDescription;
    }

    public void setDemandDescription(String demandDescription) {
        this.demandDescription = demandDescription;
    }

    public String getWorkCondition() {
        return workCondition;
    }

    public void setWorkCondition(String workCondition) {
        this.workCondition = workCondition;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
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

    public String getJobitemId() {
        return jobitemId;
    }

    public void setJobitemId(String jobitemId) {
        this.jobitemId = jobitemId;
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
        sb.append(", customerId=").append(customerId);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", demandName=").append(demandName);
        sb.append(", demandCustomerName=").append(demandCustomerName);
        sb.append(", confirmPersonName=").append(confirmPersonName);
        sb.append(", confirmContactTel=").append(confirmContactTel);
        sb.append(", demandDescription=").append(demandDescription);
        sb.append(", workCondition=").append(workCondition);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", writerId=").append(writerId);
        sb.append(", nextNode=").append(nextNode);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", jobitemId=").append(jobitemId);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}