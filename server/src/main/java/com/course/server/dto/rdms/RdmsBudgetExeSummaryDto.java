/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RdmsBudgetExeSummaryDto {
    private String id;
    private String name;
    private Double devManhourApproved;
    private Double testManhourApproved;
    private BigDecimal materialBudgetApproved;
    private BigDecimal otherFeeBudgetApproved;

    private Double devManhourActive;
    private Double testManhourActive;
    private BigDecimal materialBudgetActive;
    private BigDecimal otherFeeBudgetActive;

    private BigDecimal sumBudgetApproved;
    private BigDecimal sumBudgetActive;

    private BigDecimal budgetRate;

    private String childrenIdListStr;  //功能
    private String childSubprojectIdListStr;  //子项目

    List<RdmsBudgetExeSummaryDto> children;  // 下一级character
    List<RdmsBudgetExeSummaryDto> childSubprojectList;  // 下一级subproject
}
