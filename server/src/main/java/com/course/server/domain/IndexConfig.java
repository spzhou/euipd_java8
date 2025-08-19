/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class IndexConfig {
    private String id;

    private String institutionId;

    private String mainPlayerWarmupImg;

    private String mainLiveChannelId;

    private String coverImgRedirectUrl;

    private Date warmupTime;

    private Date showTime;

    private String upInstitutionId;

    private String upProductId;

    private String upCourseId;

    private String mainCover;

    private Integer platLiveShow;

    private Integer closePlatMenuFlag;

    private Integer blackNameListFlag;

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

    public String getMainPlayerWarmupImg() {
        return mainPlayerWarmupImg;
    }

    public void setMainPlayerWarmupImg(String mainPlayerWarmupImg) {
        this.mainPlayerWarmupImg = mainPlayerWarmupImg;
    }

    public String getMainLiveChannelId() {
        return mainLiveChannelId;
    }

    public void setMainLiveChannelId(String mainLiveChannelId) {
        this.mainLiveChannelId = mainLiveChannelId;
    }

    public String getCoverImgRedirectUrl() {
        return coverImgRedirectUrl;
    }

    public void setCoverImgRedirectUrl(String coverImgRedirectUrl) {
        this.coverImgRedirectUrl = coverImgRedirectUrl;
    }

    public Date getWarmupTime() {
        return warmupTime;
    }

    public void setWarmupTime(Date warmupTime) {
        this.warmupTime = warmupTime;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public String getUpInstitutionId() {
        return upInstitutionId;
    }

    public void setUpInstitutionId(String upInstitutionId) {
        this.upInstitutionId = upInstitutionId;
    }

    public String getUpProductId() {
        return upProductId;
    }

    public void setUpProductId(String upProductId) {
        this.upProductId = upProductId;
    }

    public String getUpCourseId() {
        return upCourseId;
    }

    public void setUpCourseId(String upCourseId) {
        this.upCourseId = upCourseId;
    }

    public String getMainCover() {
        return mainCover;
    }

    public void setMainCover(String mainCover) {
        this.mainCover = mainCover;
    }

    public Integer getPlatLiveShow() {
        return platLiveShow;
    }

    public void setPlatLiveShow(Integer platLiveShow) {
        this.platLiveShow = platLiveShow;
    }

    public Integer getClosePlatMenuFlag() {
        return closePlatMenuFlag;
    }

    public void setClosePlatMenuFlag(Integer closePlatMenuFlag) {
        this.closePlatMenuFlag = closePlatMenuFlag;
    }

    public Integer getBlackNameListFlag() {
        return blackNameListFlag;
    }

    public void setBlackNameListFlag(Integer blackNameListFlag) {
        this.blackNameListFlag = blackNameListFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", mainPlayerWarmupImg=").append(mainPlayerWarmupImg);
        sb.append(", mainLiveChannelId=").append(mainLiveChannelId);
        sb.append(", coverImgRedirectUrl=").append(coverImgRedirectUrl);
        sb.append(", warmupTime=").append(warmupTime);
        sb.append(", showTime=").append(showTime);
        sb.append(", upInstitutionId=").append(upInstitutionId);
        sb.append(", upProductId=").append(upProductId);
        sb.append(", upCourseId=").append(upCourseId);
        sb.append(", mainCover=").append(mainCover);
        sb.append(", platLiveShow=").append(platLiveShow);
        sb.append(", closePlatMenuFlag=").append(closePlatMenuFlag);
        sb.append(", blackNameListFlag=").append(blackNameListFlag);
        sb.append("]");
        return sb.toString();
    }
}