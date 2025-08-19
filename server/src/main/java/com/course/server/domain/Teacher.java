/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class Teacher {
    private String id;

    private String name;

    private String nickname;

    private String image;

    private String position;

    private String motto;

    private String intro;

    private String creatorLoginname;

    private String articleTitle;

    private Date showTime;

    private Integer sort;

    private String articleCoverImg;

    private Integer plan;

    private String subjectIntro;

    private String institution;

    private String instId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getCreatorLoginname() {
        return creatorLoginname;
    }

    public void setCreatorLoginname(String creatorLoginname) {
        this.creatorLoginname = creatorLoginname;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getArticleCoverImg() {
        return articleCoverImg;
    }

    public void setArticleCoverImg(String articleCoverImg) {
        this.articleCoverImg = articleCoverImg;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public String getSubjectIntro() {
        return subjectIntro;
    }

    public void setSubjectIntro(String subjectIntro) {
        this.subjectIntro = subjectIntro;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", nickname=").append(nickname);
        sb.append(", image=").append(image);
        sb.append(", position=").append(position);
        sb.append(", motto=").append(motto);
        sb.append(", intro=").append(intro);
        sb.append(", creatorLoginname=").append(creatorLoginname);
        sb.append(", articleTitle=").append(articleTitle);
        sb.append(", showTime=").append(showTime);
        sb.append(", sort=").append(sort);
        sb.append(", articleCoverImg=").append(articleCoverImg);
        sb.append(", plan=").append(plan);
        sb.append(", subjectIntro=").append(subjectIntro);
        sb.append(", institution=").append(institution);
        sb.append(", instId=").append(instId);
        sb.append("]");
        return sb.toString();
    }
}