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
public class RdmsSubprojectExeSimpleInfoDto {
    private String id;
    private String projectCode;
    private String projectName;
    private String status;

    private Integer jobSumNum;
    private Integer jobComplateNum;
    private Integer characterSumNum;
    private Integer characterComplateNum;
    private BigDecimal budgetApproved;
    private BigDecimal budgetActive;  //按下发工单计算
    private BigDecimal budgetRate;  //预算执行率

    private List<RdmsSubprojectExeSimpleInfoDto> children;
}
