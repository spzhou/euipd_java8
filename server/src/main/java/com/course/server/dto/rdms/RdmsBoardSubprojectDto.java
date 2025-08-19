/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsProjectSubproject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsBoardSubprojectDto {

    private RdmsProjectSubproject subproject;

    private BigDecimal sumBudget;
    private BigDecimal activeBudget;

    private BigDecimal sumManhourBudget;  //工时预算
    private BigDecimal sumManhourFee;  //已发工时费

    private BigDecimal sumMaterialBudget;
    private BigDecimal sumMaterialFee;  //物料总投入

    private BigDecimal sumEquipmentBudget;
    private BigDecimal sumEquipmentFee;  //设备总投入

    private BigDecimal sumFeeBudget;
    private BigDecimal sumFee;

    private Long sumJobitemNum;  //所有上级\下级的工单总数
    private Long complateJobNum;  //完工工单数
    private Long exeJobNum;  //执行中工单数
    private Long overdueJobNum;  //逾期工单数

    private Double sumManhour;
    private Double sumExeManhour; //执行中工时总数
    private Double sumCompManhour; //完工工时总数

    private Long submitDocNum;  //提交文档总数
    private List<String> docIdList; //文档Id列表

}
