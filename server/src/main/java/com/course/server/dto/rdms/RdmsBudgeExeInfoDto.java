/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.enums.rdms.BudgetExeTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RdmsBudgeExeInfoDto {
    private String id;
    private Integer itemNum;
    private Integer completeItemNum;  //完成数量
    private String typeName;
    private BudgetExeTypeEnum type;
    private Double sumManhour;  //分项总工时
    private BigDecimal itemBudget;  //分项预算金额
    private BigDecimal activeCharge;  //实际支出
    private BigDecimal budgetRate;  //预算执行率
    private Integer approveNum;  //待审批条数
    private Integer recheckNum;  //待复核条数
    List<RdmsBudgeExeInfoDto> children;
}
