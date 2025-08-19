/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MallOrderDto {
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

}