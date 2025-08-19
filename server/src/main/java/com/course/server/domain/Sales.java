/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class Sales {
    private String id;

    private String trueName;

    private String companyName;

    private String desc;

    private String contactPhone;

    private String region;

    private String city;

    private Date createTime;

    private String salesClassify;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSalesClassify() {
        return salesClassify;
    }

    public void setSalesClassify(String salesClassify) {
        this.salesClassify = salesClassify;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", trueName=").append(trueName);
        sb.append(", companyName=").append(companyName);
        sb.append(", desc=").append(desc);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", region=").append(region);
        sb.append(", city=").append(city);
        sb.append(", createTime=").append(createTime);
        sb.append(", salesClassify=").append(salesClassify);
        sb.append("]");
        return sb.toString();
    }
}