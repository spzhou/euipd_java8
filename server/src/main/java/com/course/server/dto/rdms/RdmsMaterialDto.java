/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsMaterialDto {

    private String id;

    private String code;

    private String bomCode;

    private String name;

    private String model;

    private String unit;

    private Double amount;

    private String usageMode;

    private BigDecimal preTaxUnitPrice;

    private BigDecimal taxRate;

    private BigDecimal preTaxSumPrice;

    private BigDecimal sumPrice;

    private String sales;

    private String contactPhone;

    private String supplierName;

    private String supplierAddress;

    private String writerId;

    private String status;

    private String auxStatus;

    private String fileListStr;

    private String storageCode;

    private String warehouseCode;

    private Date updateTime;

    private Date createTime;

    private String writerName;

    private Integer inBom;

    List<RdmsMaterialDto> materialList;  //如果是组件级物料, 是会包含下一级物料的

    private String batchCode; //批次号

    private Integer checkOrNot;  //是否检验  0是 1否

    private String checkItemNo ;  //报检单号

    private String targetWarehouseCode ;  //目标仓库代码

    private String reserve1;  //预留1

    private String reserve2;  //预留2


}
