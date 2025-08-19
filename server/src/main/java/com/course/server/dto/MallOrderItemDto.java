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
public class MallOrderItemDto {
    private String orderItemId;

    private String orderId;

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String image;

    private BigDecimal sellingPrice;

    private Integer goodsCount;

    private Date createTime;

}