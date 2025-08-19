/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class InstitutionMember {
    private String id;

    private String institutionId;

    private String memberLoginName;

    private String name;

    private String registName;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String profileImg;

    private String title;

    private String motto;

    private String intro;

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

    public String getMemberLoginName() {
        return memberLoginName;
    }

    public void setMemberLoginName(String memberLoginName) {
        this.memberLoginName = memberLoginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistName() {
        return registName;
    }

    public void setRegistName(String registName) {
        this.registName = registName;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", memberLoginName=").append(memberLoginName);
        sb.append(", name=").append(name);
        sb.append(", registName=").append(registName);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", profileImg=").append(profileImg);
        sb.append(", title=").append(title);
        sb.append(", motto=").append(motto);
        sb.append(", intro=").append(intro);
        sb.append("]");
        return sb.toString();
    }
}