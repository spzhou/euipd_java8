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
public class RdmsSAPMaterialDto {

    private Integer code; // 记录编码

    private String materialNo; // 物料编号

    private String materialName; // 物料名称/描述

    private String materialSpcf; // 规格型号

    private String warehouseNo; // 仓库编号

    private String warehouseName; // 仓库名称

    private String warehouseCode; // 仓库编码

    private String batchCodeSno; // 批次号

    private String materialType; // 物料类型

    private Integer num; // 数量

    private String unit; // 单位

    private String usageMode;  //使用方式  借用等

    private String projectCode;  //项目代码

    private String projectName;  //项目名称

    private Double Price;  //价格

    private Integer isRecheck;  //是否检查  0:是 ; 1:否
//    private Integer checkOrNot;  //是否检验  0是 1否

    private String recheckItemNo;  //报检单号

    private String requestId;  //原始申请OA请求Id

    private String checkItemNo ;  //报检单号

    private String targetWarehouseCode ;  //目标仓库代码

    private String reserve1;  //原始项目代号

    private String reserve2;  //预留2
}
