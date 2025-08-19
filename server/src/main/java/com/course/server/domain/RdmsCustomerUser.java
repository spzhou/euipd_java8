/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsCustomerUser {
    private String id;

    private String customerId;

    private String departmentId;

    private String jobNum;

    private String jobPosition;

    private String title;

    private String jobLevel;

    private Date joinTime;

    private String officeEmail;

    private String officeTel;

    private String officeAddress;

    private String status;

    private Date createTime;

    private String userId;

    private String loginName;

    private String trueName;

    private String subject;

    private Date birthday;

    private String educationBackground;

    private String degree;

    private String description;

    private String profile;

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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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
        sb.append(", departmentId=").append(departmentId);
        sb.append(", jobNum=").append(jobNum);
        sb.append(", jobPosition=").append(jobPosition);
        sb.append(", title=").append(title);
        sb.append(", jobLevel=").append(jobLevel);
        sb.append(", joinTime=").append(joinTime);
        sb.append(", officeEmail=").append(officeEmail);
        sb.append(", officeTel=").append(officeTel);
        sb.append(", officeAddress=").append(officeAddress);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", userId=").append(userId);
        sb.append(", loginName=").append(loginName);
        sb.append(", trueName=").append(trueName);
        sb.append(", subject=").append(subject);
        sb.append(", birthday=").append(birthday);
        sb.append(", educationBackground=").append(educationBackground);
        sb.append(", degree=").append(degree);
        sb.append(", description=").append(description);
        sb.append(", profile=").append(profile);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}