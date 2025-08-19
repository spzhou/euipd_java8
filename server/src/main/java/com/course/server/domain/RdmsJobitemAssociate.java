/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

public class RdmsJobitemAssociate {
    private String id;

    private String jobitemId;

    private String associateId;

    private String replaceFileIdsStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobitemId() {
        return jobitemId;
    }

    public void setJobitemId(String jobitemId) {
        this.jobitemId = jobitemId;
    }

    public String getAssociateId() {
        return associateId;
    }

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }

    public String getReplaceFileIdsStr() {
        return replaceFileIdsStr;
    }

    public void setReplaceFileIdsStr(String replaceFileIdsStr) {
        this.replaceFileIdsStr = replaceFileIdsStr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", jobitemId=").append(jobitemId);
        sb.append(", associateId=").append(associateId);
        sb.append(", replaceFileIdsStr=").append(replaceFileIdsStr);
        sb.append("]");
        return sb.toString();
    }
}