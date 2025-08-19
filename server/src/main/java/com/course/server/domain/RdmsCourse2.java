/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsCourse2 {
    private String id;

    private String name;

    private String summary;

    private Integer time;

    private BigDecimal price;

    private String image;

    private String video;

    private String vod;

    private String level;

    private String charge;

    private String status;

    private Integer enroll;

    private Integer sort;

    private Date createdAt;

    private Date updatedAt;

    private String teacherId;

    private String customerId;

    private String creatorLoginname;

    private Integer like;

    private Integer collect;

    private Integer watch;

    private Integer power;

    private Integer newCourse;

    private Integer promotion;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVod() {
        return vod;
    }

    public void setVod(String vod) {
        this.vod = vod;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEnroll() {
        return enroll;
    }

    public void setEnroll(Integer enroll) {
        this.enroll = enroll;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCreatorLoginname() {
        return creatorLoginname;
    }

    public void setCreatorLoginname(String creatorLoginname) {
        this.creatorLoginname = creatorLoginname;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getWatch() {
        return watch;
    }

    public void setWatch(Integer watch) {
        this.watch = watch;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(Integer newCourse) {
        this.newCourse = newCourse;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", summary=").append(summary);
        sb.append(", time=").append(time);
        sb.append(", price=").append(price);
        sb.append(", image=").append(image);
        sb.append(", video=").append(video);
        sb.append(", vod=").append(vod);
        sb.append(", level=").append(level);
        sb.append(", charge=").append(charge);
        sb.append(", status=").append(status);
        sb.append(", enroll=").append(enroll);
        sb.append(", sort=").append(sort);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", customerId=").append(customerId);
        sb.append(", creatorLoginname=").append(creatorLoginname);
        sb.append(", like=").append(like);
        sb.append(", collect=").append(collect);
        sb.append(", watch=").append(watch);
        sb.append(", power=").append(power);
        sb.append(", newCourse=").append(newCourse);
        sb.append(", promotion=").append(promotion);
        sb.append("]");
        return sb.toString();
    }
}