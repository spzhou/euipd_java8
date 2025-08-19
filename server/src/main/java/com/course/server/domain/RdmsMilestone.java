/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsMilestone {
    private String id;

    private String projectId;

    private String milestoneName;

    private String checkOut;

    private Date reviewTime;

    private Date createTime;

    private String isRelease;

    private Integer isReviewed;

    private String deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(String isRelease) {
        this.isRelease = isRelease;
    }

    public Integer getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(Integer isReviewed) {
        this.isReviewed = isReviewed;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", milestoneName=").append(milestoneName);
        sb.append(", checkOut=").append(checkOut);
        sb.append(", reviewTime=").append(reviewTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", isRelease=").append(isRelease);
        sb.append(", isReviewed=").append(isReviewed);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}