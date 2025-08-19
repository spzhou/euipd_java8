/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.course.server.enums.GoodsClassEnum;
import com.course.server.enums.ProductStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MallGoodsInfoDto {
    private Long goodsId;

    private String productId;

    private String goodsName;

    private String goodsIntro;

    private BigDecimal originalPrice;

    private BigDecimal sellingPrice;

    private Integer stockNum;

    private String tag;

    private Integer goodsSellStatus;

    private Date createTime;

    private Date updateTime;

    private Integer performanceBasePrice;

    private Integer platformPurchasePrice;

    private Integer sellClass;

    private String charge;

    private String institutionId;
    private String instAddress;

    private String instContactUserId;
    private String contactName;
    private String contactMobile;

    private String instContractId;

    private String platContactUserId;

    private String platContractId;

    private String groupId;

    private String productClass;

    private String supplierLoginname;

    private String status;

    private String image;

    private List<String> marqueeImgs;
    private String comContactQrCode;
    private String comContactProfile;
    private Boolean isOnlinePayGoods;



    public MallGoodsInfoDto(){

    }

    public MallGoodsInfoDto(CourseDto courseDto, String goodsClass){
        this.goodsName=courseDto.getName();
        this.goodsIntro=courseDto.getSummary();
        this.originalPrice=courseDto.getPrice();
        this.sellingPrice=courseDto.getPrice();
        this.stockNum=10000;
        this.tag=null;
        this.goodsSellStatus=0;
        this.createTime=new Date();
        this.updateTime=new Date();
        this.performanceBasePrice=0;
        this.platformPurchasePrice=0;
        this.sellClass=1;
        this.charge=courseDto.getCharge();;
        this.institutionId=null;
        this.instContactUserId=null;
        this.instContractId=null;
        this.platContactUserId=null;
        this.platContractId=null;
        this.groupId=null;
        this.productId=courseDto.getId();
        this.productClass= goodsClass;
        this.supplierLoginname=courseDto.getCreatorLoginname();
        this.status= courseDto.getStatus();
        this.image= courseDto.getImage();
    }

    public MallGoodsInfoDto(ProductDto productDto, String goodsClass){
        this.goodsName=productDto.getGoodsName();
        this.goodsIntro=productDto.getGoodsIntro();
        this.originalPrice=BigDecimal.valueOf(productDto.getOriginalPrice());
        this.sellingPrice=BigDecimal.valueOf(productDto.getSellingPrice());
        this.stockNum=productDto.getStockNum();
        this.tag=null;
        this.goodsSellStatus=0;
        this.createTime=new Date();
        this.updateTime=new Date();
        this.performanceBasePrice=0;
        this.platformPurchasePrice=0;
        this.sellClass=1;
        this.charge=productDto.getCharge();
        this.institutionId=null;
        this.instContactUserId=null;
        this.instContractId=null;
        this.platContactUserId=null;
        this.platContractId=null;
        this.groupId=null;
        this.productId=productDto.getProductId();
        this.productClass= goodsClass;
        this.supplierLoginname=productDto.getLoginName();
        this.status= productDto.getStatus();
        this.image=productDto.getImage();
    }

}