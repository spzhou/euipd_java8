/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsCustomer2 {
    private String id;

    private String loginName;

    private String password;

    private String customerName;

    private String creditCode;

    private String regAddress;

    private String officeAddress;

    private String adminName;

    private String contactPhone;

    private String licenseLink;

    private String status;

    private String loginToken;

    private String loginIp;

    private Integer deleted;

    private Date createTime;

    private Integer userNumLimit;

    private Date expirationTime;

    private String budgetMode;

    private Integer storageSpace;

    private Integer enAuthEdit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getLicenseLink() {
        return licenseLink;
    }

    public void setLicenseLink(String licenseLink) {
        this.licenseLink = licenseLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserNumLimit() {
        return userNumLimit;
    }

    public void setUserNumLimit(Integer userNumLimit) {
        this.userNumLimit = userNumLimit;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getBudgetMode() {
        return budgetMode;
    }

    public void setBudgetMode(String budgetMode) {
        this.budgetMode = budgetMode;
    }

    public Integer getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(Integer storageSpace) {
        this.storageSpace = storageSpace;
    }

    public Integer getEnAuthEdit() {
        return enAuthEdit;
    }

    public void setEnAuthEdit(Integer enAuthEdit) {
        this.enAuthEdit = enAuthEdit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", password=").append(password);
        sb.append(", customerName=").append(customerName);
        sb.append(", creditCode=").append(creditCode);
        sb.append(", regAddress=").append(regAddress);
        sb.append(", officeAddress=").append(officeAddress);
        sb.append(", adminName=").append(adminName);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", licenseLink=").append(licenseLink);
        sb.append(", status=").append(status);
        sb.append(", loginToken=").append(loginToken);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", deleted=").append(deleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", userNumLimit=").append(userNumLimit);
        sb.append(", expirationTime=").append(expirationTime);
        sb.append(", budgetMode=").append(budgetMode);
        sb.append(", storageSpace=").append(storageSpace);
        sb.append(", enAuthEdit=").append(enAuthEdit);
        sb.append("]");
        return sb.toString();
    }
}