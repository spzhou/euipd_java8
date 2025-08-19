/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class Product {
    private String productId;

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String image;

    private Integer originalPrice;

    private Integer sellingPrice;

    private Integer stockNum;

    private String tag;

    private String status;

    private String loginName;

    private Date createTime;

    private Date updateTime;

    private String supplierId;

    private Integer performanceBasePrice;

    private Integer platformPurchasePrice;

    private Integer pricingType;

    private String comId;

    private String comContactUserId;

    private String comContractId;

    private String platContactUserId;

    private String platContractId;

    private String charge;

    private Integer sort;

    private Integer enroll;

    private String courseId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getPerformanceBasePrice() {
        return performanceBasePrice;
    }

    public void setPerformanceBasePrice(Integer performanceBasePrice) {
        this.performanceBasePrice = performanceBasePrice;
    }

    public Integer getPlatformPurchasePrice() {
        return platformPurchasePrice;
    }

    public void setPlatformPurchasePrice(Integer platformPurchasePrice) {
        this.platformPurchasePrice = platformPurchasePrice;
    }

    public Integer getPricingType() {
        return pricingType;
    }

    public void setPricingType(Integer pricingType) {
        this.pricingType = pricingType;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getComContactUserId() {
        return comContactUserId;
    }

    public void setComContactUserId(String comContactUserId) {
        this.comContactUserId = comContactUserId;
    }

    public String getComContractId() {
        return comContractId;
    }

    public void setComContractId(String comContractId) {
        this.comContractId = comContractId;
    }

    public String getPlatContactUserId() {
        return platContactUserId;
    }

    public void setPlatContactUserId(String platContactUserId) {
        this.platContactUserId = platContactUserId;
    }

    public String getPlatContractId() {
        return platContractId;
    }

    public void setPlatContractId(String platContractId) {
        this.platContractId = platContractId;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnroll() {
        return enroll;
    }

    public void setEnroll(Integer enroll) {
        this.enroll = enroll;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productId=").append(productId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsIntro=").append(goodsIntro);
        sb.append(", image=").append(image);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", stockNum=").append(stockNum);
        sb.append(", tag=").append(tag);
        sb.append(", status=").append(status);
        sb.append(", loginName=").append(loginName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", supplierId=").append(supplierId);
        sb.append(", performanceBasePrice=").append(performanceBasePrice);
        sb.append(", platformPurchasePrice=").append(platformPurchasePrice);
        sb.append(", pricingType=").append(pricingType);
        sb.append(", comId=").append(comId);
        sb.append(", comContactUserId=").append(comContactUserId);
        sb.append(", comContractId=").append(comContractId);
        sb.append(", platContactUserId=").append(platContactUserId);
        sb.append(", platContractId=").append(platContractId);
        sb.append(", charge=").append(charge);
        sb.append(", sort=").append(sort);
        sb.append(", enroll=").append(enroll);
        sb.append(", courseId=").append(courseId);
        sb.append("]");
        return sb.toString();
    }
}