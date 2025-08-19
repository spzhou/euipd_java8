/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductDto {
    private String productId;

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String image;

    private Integer originalPrice;

    private Integer sellingPrice;

    private Integer stockNum;

    private String tag;

    private String status;

    private String loginName;

    private Date createTime;

    private Date updateTime;

    private String supplierId;

    private Integer performanceBasePrice;

    private Integer platformPurchasePrice;

    private Integer pricingType;

    private String comId;

    private String comContactUserId;

    private String comContractId;

    private String platContactUserId;

    private String platContractId;

    private String charge;

    private Integer sort;

    private Integer enroll;

    private String courseId;

    private List<CategoryDto> categorys;

    private List<String> mainImgs;
    private List<String> productImgs;
    private List<String> marqueeImgs;

    private String institutionName;
    private String institutionId;
    private String institutionLogo;

    private Number watchNum; //展示量
    private Number likeNum; //点赞量
    private Number collectNum; //收藏量

}