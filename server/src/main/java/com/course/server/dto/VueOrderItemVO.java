
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;
import lombok.Data;

@Data
public class VueOrderItemVO {
    private String orderItemId;
    private String orderId;
    private String goodsId;
    private String goodsCount;
    private String goodsName;
    private String goodsIntro;//商品简介
    private String goodsCoverImg;
    private String sellingPrice;//单价
    private String goodsSerial;//商品序列号
    private String goodsDescribe;//商品描述
    private String subTotal;//小计

}
