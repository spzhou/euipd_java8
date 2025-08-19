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
public class MallShoppingCartItemDto {
    private String cartItemId;

    private String customerId;

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private BigDecimal sellingPrice;

    private Integer goodsCount;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;

    private String institutionId;

    private Integer buyfrom;

    private Boolean selected;

    private String image;

    private Boolean isOnlinePayGoods;

}