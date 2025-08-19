
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.util.List;

/**
 * 订单详情页页面VO
 */
@Data
public class VueOrderDetail {
    private String orderId;
    private String orderNo;
    private String userId;
    private String userName;
    private String userNote;
    private String supplierName;
    private String supplierTel;
    private String preTaxPrice;
    private String taxRate;   //*
    private String shippingFee;//*
    private String totalPrice;
    private String payStatus;  //*
    private String payType;  //*
    private String orderStatus; //*
    private String createTime; //*
    private Integer itemNum;
    private List<VueOrderItemVO> mallOrderItemVOS;
    private String shippingAddress;
    private String shippingVO = null;
    private Integer status;

}
