/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

public class InstitutionBlackList {
    private String id;

    private String institutionId;

    private String blackInstitutionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getBlackInstitutionId() {
        return blackInstitutionId;
    }

    public void setBlackInstitutionId(String blackInstitutionId) {
        this.blackInstitutionId = blackInstitutionId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", blackInstitutionId=").append(blackInstitutionId);
        sb.append("]");
        return sb.toString();
    }
}