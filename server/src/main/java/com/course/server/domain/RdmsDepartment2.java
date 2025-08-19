/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsDepartment2 {
    private String id;

    private String parent;

    private String departName;

    private String customerId;

    private String managerId;

    private String assistantId;

    private String secretaryId;

    private String phone;

    private String departDescription;

    private Date createTime;

    private Date updateTime;

    private Integer fixed;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    public String getSecretaryId() {
        return secretaryId;
    }

    public void setSecretaryId(String secretaryId) {
        this.secretaryId = secretaryId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartDescription() {
        return departDescription;
    }

    public void setDepartDescription(String departDescription) {
        this.departDescription = departDescription;
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

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
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
        sb.append(", parent=").append(parent);
        sb.append(", departName=").append(departName);
        sb.append(", customerId=").append(customerId);
        sb.append(", managerId=").append(managerId);
        sb.append(", assistantId=").append(assistantId);
        sb.append(", secretaryId=").append(secretaryId);
        sb.append(", phone=").append(phone);
        sb.append(", departDescription=").append(departDescription);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", fixed=").append(fixed);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}