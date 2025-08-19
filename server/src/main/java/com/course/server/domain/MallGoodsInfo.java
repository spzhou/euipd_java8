/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class MallGoodsInfo {
    private Long goodsId;

    private String productId;

    private String goodsName;

    private String goodsIntro;

    private BigDecimal originalPrice;

    private BigDecimal sellingPrice;

    private Integer stockNum;

    private String tag;

    private Integer goodsSellStatus;

    private Date createTime;

    private Date updateTime;

    private Integer performanceBasePrice;

    private Integer platformPurchasePrice;

    private Integer sellClass;

    private String charge;

    private String institutionId;

    private String instContactUserId;

    private String instContractId;

    private String platContactUserId;

    private String platContractId;

    private String groupId;

    private String productClass;

    private String supplierLoginname;

    private String status;

    private String image;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
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

    public Integer getGoodsSellStatus() {
        return goodsSellStatus;
    }

    public void setGoodsSellStatus(Integer goodsSellStatus) {
        this.goodsSellStatus = goodsSellStatus;
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

    public Integer getSellClass() {
        return sellClass;
    }

    public void setSellClass(Integer sellClass) {
        this.sellClass = sellClass;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstContactUserId() {
        return instContactUserId;
    }

    public void setInstContactUserId(String instContactUserId) {
        this.instContactUserId = instContactUserId;
    }

    public String getInstContractId() {
        return instContractId;
    }

    public void setInstContractId(String instContractId) {
        this.instContractId = instContractId;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getSupplierLoginname() {
        return supplierLoginname;
    }

    public void setSupplierLoginname(String supplierLoginname) {
        this.supplierLoginname = supplierLoginname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", productId=").append(productId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsIntro=").append(goodsIntro);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", stockNum=").append(stockNum);
        sb.append(", tag=").append(tag);
        sb.append(", goodsSellStatus=").append(goodsSellStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", performanceBasePrice=").append(performanceBasePrice);
        sb.append(", platformPurchasePrice=").append(platformPurchasePrice);
        sb.append(", sellClass=").append(sellClass);
        sb.append(", charge=").append(charge);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", instContactUserId=").append(instContactUserId);
        sb.append(", instContractId=").append(instContractId);
        sb.append(", platContactUserId=").append(platContactUserId);
        sb.append(", platContractId=").append(platContractId);
        sb.append(", groupId=").append(groupId);
        sb.append(", productClass=").append(productClass);
        sb.append(", supplierLoginname=").append(supplierLoginname);
        sb.append(", status=").append(status);
        sb.append(", image=").append(image);
        sb.append("]");
        return sb.toString();
    }
}