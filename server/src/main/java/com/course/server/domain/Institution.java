/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class Institution {
    private String id;

    private String name;

    private String slogan;

    private String image;

    private String video;

    private String vod;

    private String level;

    private String status;

    private Integer sort;

    private Date createdAt;

    private Date updatedAt;

    private String creatorLoginname;

    private Integer like;

    private Integer collect;

    private Integer watch;

    private Integer power;

    private Integer promotion;

    private String contact;

    private String phone;

    private String address;

    private String logo;

    private String contactAvatar;

    private String contactQrCode;

    private String orgLink;

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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContactAvatar() {
        return contactAvatar;
    }

    public void setContactAvatar(String contactAvatar) {
        this.contactAvatar = contactAvatar;
    }

    public String getContactQrCode() {
        return contactQrCode;
    }

    public void setContactQrCode(String contactQrCode) {
        this.contactQrCode = contactQrCode;
    }

    public String getOrgLink() {
        return orgLink;
    }

    public void setOrgLink(String orgLink) {
        this.orgLink = orgLink;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", slogan=").append(slogan);
        sb.append(", image=").append(image);
        sb.append(", video=").append(video);
        sb.append(", vod=").append(vod);
        sb.append(", level=").append(level);
        sb.append(", status=").append(status);
        sb.append(", sort=").append(sort);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", creatorLoginname=").append(creatorLoginname);
        sb.append(", like=").append(like);
        sb.append(", collect=").append(collect);
        sb.append(", watch=").append(watch);
        sb.append(", power=").append(power);
        sb.append(", promotion=").append(promotion);
        sb.append(", contact=").append(contact);
        sb.append(", phone=").append(phone);
        sb.append(", address=").append(address);
        sb.append(", logo=").append(logo);
        sb.append(", contactAvatar=").append(contactAvatar);
        sb.append(", contactQrCode=").append(contactQrCode);
        sb.append(", orgLink=").append(orgLink);
        sb.append("]");
        return sb.toString();
    }
}