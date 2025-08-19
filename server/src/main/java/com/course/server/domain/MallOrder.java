/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.Date;

public class MallOrder {
    private String orderId;

    private String orderNo;

    private String customerId;

    private String userNote;

    private String institutionId;

    private String supplierId;

    private String buyerName;

    private String buyerAddr;

    private String buyerTel;

    private Double preTaxPrice;

    private Double totalPrice;

    private Integer payStatus;

    private Integer payType;

    private Date payTime;

    private Integer orderStatus;

    private String contractId;

    private Integer isDeleted;

    private Date createTime;

    private Date updateTime;

    private Date approveTime;

    private Double userDiscountRate;

    private Double taxRate;

    private Double shippingFee;

    private Double discount;

    private Integer buyFrom;

    private String payImgUrl;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddr() {
        return buyerAddr;
    }

    public void setBuyerAddr(String buyerAddr) {
        this.buyerAddr = buyerAddr;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public Double getPreTaxPrice() {
        return preTaxPrice;
    }

    public void setPreTaxPrice(Double preTaxPrice) {
        this.preTaxPrice = preTaxPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Double getUserDiscountRate() {
        return userDiscountRate;
    }

    public void setUserDiscountRate(Double userDiscountRate) {
        this.userDiscountRate = userDiscountRate;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getBuyFrom() {
        return buyFrom;
    }

    public void setBuyFrom(Integer buyFrom) {
        this.buyFrom = buyFrom;
    }

    public String getPayImgUrl() {
        return payImgUrl;
    }

    public void setPayImgUrl(String payImgUrl) {
        this.payImgUrl = payImgUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", customerId=").append(customerId);
        sb.append(", userNote=").append(userNote);
        sb.append(", institutionId=").append(institutionId);
        sb.append(", supplierId=").append(supplierId);
        sb.append(", buyerName=").append(buyerName);
        sb.append(", buyerAddr=").append(buyerAddr);
        sb.append(", buyerTel=").append(buyerTel);
        sb.append(", preTaxPrice=").append(preTaxPrice);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", payType=").append(payType);
        sb.append(", payTime=").append(payTime);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", contractId=").append(contractId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", approveTime=").append(approveTime);
        sb.append(", userDiscountRate=").append(userDiscountRate);
        sb.append(", taxRate=").append(taxRate);
        sb.append(", shippingFee=").append(shippingFee);
        sb.append(", discount=").append(discount);
        sb.append(", buyFrom=").append(buyFrom);
        sb.append(", payImgUrl=").append(payImgUrl);
        sb.append("]");
        return sb.toString();
    }
}