
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单详情页页面订单项VO
 */
@Data
public class OrderItemVO {
    private String orderItemId;
    private String orderId;
    private Long goodsId;
    private Integer goodsCount;
    private String goodsName;
    private String goodsIntro;//商品简介
    private String image;
    private BigDecimal sellingPrice;//单价
    private String goodsSerial;//商品序列号
    private String goodsDescribe;//商品描述
    private String z9Purchase;//平台采购价
    private String basePrice;//销售最低价-绩效基础价格
    private String subTotal;//小计

}
