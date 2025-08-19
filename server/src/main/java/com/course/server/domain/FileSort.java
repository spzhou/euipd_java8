/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class FileSort {
    private String id;

    private String videoClass;

    private String paramId;

    private String title;

    private String vod;

    private String content;

    private String videoCover;

    private String institution;

    private String logoUrl;

    private Integer sort;

    private Integer rand;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoClass() {
        return videoClass;
    }

    public void setVideoClass(String videoClass) {
        this.videoClass = videoClass;
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVod() {
        return vod;
    }

    public void setVod(String vod) {
        this.vod = vod;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getRand() {
        return rand;
    }

    public void setRand(Integer rand) {
        this.rand = rand;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", videoClass=").append(videoClass);
        sb.append(", paramId=").append(paramId);
        sb.append(", title=").append(title);
        sb.append(", vod=").append(vod);
        sb.append(", content=").append(content);
        sb.append(", videoCover=").append(videoCover);
        sb.append(", institution=").append(institution);
        sb.append(", logoUrl=").append(logoUrl);
        sb.append(", sort=").append(sort);
        sb.append(", rand=").append(rand);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}