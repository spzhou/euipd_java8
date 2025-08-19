/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class Demand {
    private String id;

    private String demandName;

    private String demandInst;

    private String desc;

    private String demandContactName;

    private String demandContactPhone;

    private String recommend;

    private String involved;

    private String region;

    private String companyName;

    private String productName;

    private Date date1;

    private String name;

    private String phone;

    private String cooperation;

    private Date createTime;

    private String demandClassify;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getDemandInst() {
        return demandInst;
    }

    public void setDemandInst(String demandInst) {
        this.demandInst = demandInst;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDemandContactName() {
        return demandContactName;
    }

    public void setDemandContactName(String demandContactName) {
        this.demandContactName = demandContactName;
    }

    public String getDemandContactPhone() {
        return demandContactPhone;
    }

    public void setDemandContactPhone(String demandContactPhone) {
        this.demandContactPhone = demandContactPhone;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getInvolved() {
        return involved;
    }

    public void setInvolved(String involved) {
        this.involved = involved;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCooperation() {
        return cooperation;
    }

    public void setCooperation(String cooperation) {
        this.cooperation = cooperation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDemandClassify() {
        return demandClassify;
    }

    public void setDemandClassify(String demandClassify) {
        this.demandClassify = demandClassify;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", demandName=").append(demandName);
        sb.append(", demandInst=").append(demandInst);
        sb.append(", desc=").append(desc);
        sb.append(", demandContactName=").append(demandContactName);
        sb.append(", demandContactPhone=").append(demandContactPhone);
        sb.append(", recommend=").append(recommend);
        sb.append(", involved=").append(involved);
        sb.append(", region=").append(region);
        sb.append(", companyName=").append(companyName);
        sb.append(", productName=").append(productName);
        sb.append(", date1=").append(date1);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", cooperation=").append(cooperation);
        sb.append(", createTime=").append(createTime);
        sb.append(", demandClassify=").append(demandClassify);
        sb.append("]");
        return sb.toString();
    }
}