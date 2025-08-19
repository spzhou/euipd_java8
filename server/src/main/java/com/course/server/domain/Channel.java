/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class Channel {
    private String id;

    private String channelId;

    private String institutionId;

    private String userLoginname;

    private String liveStatus;

    private String channelPassword;

    private String showUrl;

    private String teacherUrl;

    private Date timestamp;

    private String sessionid;

    private Date startTime;

    private Date endTime;

    private String liveId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getUserLoginname() {
        return userLoginname;
    }

    public void setUserLoginname(String userLoginname) {
        this.userLoginname = userLoginname;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getChannelPassword() {
        return channelPassword;
    }

    public void setChannelPassword(String channelPassword) {
        this.channelPassword = channelPassword;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getTeacherUrl() {
        return teacherUrl;
    }

    public void setTeacherUrl(String teacherUrl) {
        this.teacherUrl = teacherUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", channelId=").append(channelId);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", userLoginname=").append(userLoginname);
        sb.append(", liveStatus=").append(liveStatus);
        sb.append(", channelPassword=").append(channelPassword);
        sb.append(", showUrl=").append(showUrl);
        sb.append(", teacherUrl=").append(teacherUrl);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", sessionid=").append(sessionid);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", liveId=").append(liveId);
        sb.append("]");
        return sb.toString();
    }
}