
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车页面购物项VO
 */
@Data
public class MallCartItemDto implements Serializable {
    private String cartItemId;
    private String goodsId;
    private Integer goodsCount;
    private String goodsName;
    private String goodsIntro;
    private String goodsCoverImg;
    private String sellingPrice;
    private String goodsStatus; //SellStatusEnum
    private String goodsTotalPrice;
    private String goodsStock;  //库存
    private Boolean goodsSelected;
    private String supplierComName;
    private Boolean isOnlinePayGoods;
}
