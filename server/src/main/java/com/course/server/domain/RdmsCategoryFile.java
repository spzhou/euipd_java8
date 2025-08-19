/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class RdmsCategoryFile {
    private String id;

    private String fileId;

    private String projectId;

    private String name;

    private String categoryIdStr;

    private String writer;

    private Date fileCreateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryIdStr() {
        return categoryIdStr;
    }

    public void setCategoryIdStr(String categoryIdStr) {
        this.categoryIdStr = categoryIdStr;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(Date fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileId=").append(fileId);
        sb.append(", projectId=").append(projectId);
        sb.append(", name=").append(name);
        sb.append(", categoryIdStr=").append(categoryIdStr);
        sb.append(", writer=").append(writer);
        sb.append(", fileCreateTime=").append(fileCreateTime);
        sb.append("]");
        return sb.toString();
    }
}