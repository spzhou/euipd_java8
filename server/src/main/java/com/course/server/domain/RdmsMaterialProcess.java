/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsMaterialProcess {
    private String id;

    private String materialId;

    private String executorId;

    private Integer deep;

    private String description;

    private String nextNode;

    private Date submitTime;

    private String status;

    private Date createTime;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
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
        sb.append(", materialId=").append(materialId);
        sb.append(", executorId=").append(executorId);
        sb.append(", deep=").append(deep);
        sb.append(", description=").append(description);
        sb.append(", nextNode=").append(nextNode);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}