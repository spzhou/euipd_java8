/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsBudgetExe;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsBudgetExeDto extends RdmsBudgetExe {
    List<RdmsBudgetExeDto> characterBudgetInfoList;
    List<RdmsBudgetExeDto> childSubprojectBudgetInfoList;

    private BigDecimal sumManhourBudget;   //总计划工时
    private BigDecimal sumActiveManhourBudget;  //累计工时
}
