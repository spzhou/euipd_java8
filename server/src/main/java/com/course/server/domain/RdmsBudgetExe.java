/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsBudgetExe {
    private String id;

    private String itemId;

    private String parentId;

    private String itemName;

    private String docType;

    private String reviewDesc;

    private String reviewLeaderId;

    private String reviewCooIdStr;

    private Double devManhourApproved;

    private Double testManhourApproved;

    private BigDecimal materialFeeApproved;

    private BigDecimal otherFeeApproved;

    private BigDecimal sumBudgetApproved;

    private Double devManhourActive;

    private Double testManhourActive;

    private BigDecimal materialFeeActive;

    private BigDecimal otherFeeActive;

    private BigDecimal sumBudgetActive;

    private BigDecimal budgetRate;

    private String submitFileIdListStr;

    private String reviewFileIdListStr;

    private String childrenIdListStr;

    private String childSubprojectIdListStr;

    private String result;

    private Date reviewTime;

    private Date updateTime;

    private Date createTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }

    public String getReviewLeaderId() {
        return reviewLeaderId;
    }

    public void setReviewLeaderId(String reviewLeaderId) {
        this.reviewLeaderId = reviewLeaderId;
    }

    public String getReviewCooIdStr() {
        return reviewCooIdStr;
    }

    public void setReviewCooIdStr(String reviewCooIdStr) {
        this.reviewCooIdStr = reviewCooIdStr;
    }

    public Double getDevManhourApproved() {
        return devManhourApproved;
    }

    public void setDevManhourApproved(Double devManhourApproved) {
        this.devManhourApproved = devManhourApproved;
    }

    public Double getTestManhourApproved() {
        return testManhourApproved;
    }

    public void setTestManhourApproved(Double testManhourApproved) {
        this.testManhourApproved = testManhourApproved;
    }

    public BigDecimal getMaterialFeeApproved() {
        return materialFeeApproved;
    }

    public void setMaterialFeeApproved(BigDecimal materialFeeApproved) {
        this.materialFeeApproved = materialFeeApproved;
    }

    public BigDecimal getOtherFeeApproved() {
        return otherFeeApproved;
    }

    public void setOtherFeeApproved(BigDecimal otherFeeApproved) {
        this.otherFeeApproved = otherFeeApproved;
    }

    public BigDecimal getSumBudgetApproved() {
        return sumBudgetApproved;
    }

    public void setSumBudgetApproved(BigDecimal sumBudgetApproved) {
        this.sumBudgetApproved = sumBudgetApproved;
    }

    public Double getDevManhourActive() {
        return devManhourActive;
    }

    public void setDevManhourActive(Double devManhourActive) {
        this.devManhourActive = devManhourActive;
    }

    public Double getTestManhourActive() {
        return testManhourActive;
    }

    public void setTestManhourActive(Double testManhourActive) {
        this.testManhourActive = testManhourActive;
    }

    public BigDecimal getMaterialFeeActive() {
        return materialFeeActive;
    }

    public void setMaterialFeeActive(BigDecimal materialFeeActive) {
        this.materialFeeActive = materialFeeActive;
    }

    public BigDecimal getOtherFeeActive() {
        return otherFeeActive;
    }

    public void setOtherFeeActive(BigDecimal otherFeeActive) {
        this.otherFeeActive = otherFeeActive;
    }

    public BigDecimal getSumBudgetActive() {
        return sumBudgetActive;
    }

    public void setSumBudgetActive(BigDecimal sumBudgetActive) {
        this.sumBudgetActive = sumBudgetActive;
    }

    public BigDecimal getBudgetRate() {
        return budgetRate;
    }

    public void setBudgetRate(BigDecimal budgetRate) {
        this.budgetRate = budgetRate;
    }

    public String getSubmitFileIdListStr() {
        return submitFileIdListStr;
    }

    public void setSubmitFileIdListStr(String submitFileIdListStr) {
        this.submitFileIdListStr = submitFileIdListStr;
    }

    public String getReviewFileIdListStr() {
        return reviewFileIdListStr;
    }

    public void setReviewFileIdListStr(String reviewFileIdListStr) {
        this.reviewFileIdListStr = reviewFileIdListStr;
    }

    public String getChildrenIdListStr() {
        return childrenIdListStr;
    }

    public void setChildrenIdListStr(String childrenIdListStr) {
        this.childrenIdListStr = childrenIdListStr;
    }

    public String getChildSubprojectIdListStr() {
        return childSubprojectIdListStr;
    }

    public void setChildSubprojectIdListStr(String childSubprojectIdListStr) {
        this.childSubprojectIdListStr = childSubprojectIdListStr;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
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
        sb.append(", itemId=").append(itemId);
        sb.append(", parentId=").append(parentId);
        sb.append(", itemName=").append(itemName);
        sb.append(", docType=").append(docType);
        sb.append(", reviewDesc=").append(reviewDesc);
        sb.append(", reviewLeaderId=").append(reviewLeaderId);
        sb.append(", reviewCooIdStr=").append(reviewCooIdStr);
        sb.append(", devManhourApproved=").append(devManhourApproved);
        sb.append(", testManhourApproved=").append(testManhourApproved);
        sb.append(", materialFeeApproved=").append(materialFeeApproved);
        sb.append(", otherFeeApproved=").append(otherFeeApproved);
        sb.append(", sumBudgetApproved=").append(sumBudgetApproved);
        sb.append(", devManhourActive=").append(devManhourActive);
        sb.append(", testManhourActive=").append(testManhourActive);
        sb.append(", materialFeeActive=").append(materialFeeActive);
        sb.append(", otherFeeActive=").append(otherFeeActive);
        sb.append(", sumBudgetActive=").append(sumBudgetActive);
        sb.append(", budgetRate=").append(budgetRate);
        sb.append(", submitFileIdListStr=").append(submitFileIdListStr);
        sb.append(", reviewFileIdListStr=").append(reviewFileIdListStr);
        sb.append(", childrenIdListStr=").append(childrenIdListStr);
        sb.append(", childSubprojectIdListStr=").append(childSubprojectIdListStr);
        sb.append(", result=").append(result);
        sb.append(", reviewTime=").append(reviewTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}