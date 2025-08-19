/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

public class MainShowMenu {
    private String id;

    private Integer sort;

    private String videoClass;

    private String videoId;

    private String institutionId;

    private String subjectTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVideoClass() {
        return videoClass;
    }

    public void setVideoClass(String videoClass) {
        this.videoClass = videoClass;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sort=").append(sort);
        sb.append(", videoClass=").append(videoClass);
        sb.append(", videoId=").append(videoId);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", subjectTitle=").append(subjectTitle);
        sb.append("]");
        return sb.toString();
    }
}