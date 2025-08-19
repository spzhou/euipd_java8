/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class MallCartListDto {
    private String totalPrice;
    private Integer itemNum;
    private Boolean selectedAll=true;
    private List<MallCartItemDto> myShoppingCartItems;
    private Boolean onlinePayOrNot;
}
