/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class MemberAction {
    private String id;

    private String pageCategory;

    private String paramId;

    private String institutionId;

    private String memberId;

    private String action;

    private Integer num;

    private String ip;

    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageCategory() {
        return pageCategory;
    }

    public void setPageCategory(String pageCategory) {
        this.pageCategory = pageCategory;
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pageCategory=").append(pageCategory);
        sb.append(", paramId=").append(paramId);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", memberId=").append(memberId);
        sb.append(", action=").append(action);
        sb.append(", num=").append(num);
        sb.append(", ip=").append(ip);
        sb.append(", time=").append(time);
        sb.append("]");
        return sb.toString();
    }
}