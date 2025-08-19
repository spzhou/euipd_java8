/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsGantt {
    private String id;

    private String parent;

    private String jobitemId;

    private String characterId;

    private String subprojectId;

    private String projectId;

    private String preprojectId;

    private String customerId;

    private String createrId;

    private String executorId;

    private String text;

    private Date startDate;

    private Integer duration;

    private Double manhour;

    private Double progress;

    private String status;

    private String type;

    private Integer open;

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

    public String getJobitemId() {
        return jobitemId;
    }

    public void setJobitemId(String jobitemId) {
        this.jobitemId = jobitemId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(String subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPreprojectId() {
        return preprojectId;
    }

    public void setPreprojectId(String preprojectId) {
        this.preprojectId = preprojectId;
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

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getManhour() {
        return manhour;
    }

    public void setManhour(Double manhour) {
        this.manhour = manhour;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
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

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
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
        sb.append(", jobitemId=").append(jobitemId);
        sb.append(", characterId=").append(characterId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", projectId=").append(projectId);
        sb.append(", preprojectId=").append(preprojectId);
        sb.append(", customerId=").append(customerId);
        sb.append(", createrId=").append(createrId);
        sb.append(", executorId=").append(executorId);
        sb.append(", text=").append(text);
        sb.append(", startDate=").append(startDate);
        sb.append(", duration=").append(duration);
        sb.append(", manhour=").append(manhour);
        sb.append(", progress=").append(progress);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", open=").append(open);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}