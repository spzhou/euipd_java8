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
public class ProjectSummaryDto {
    private String id;

    private String projectCode;

    private String projectName;

    private String productManagerName;

    private String projectManagerName;

    private String testManagerName;

    private Date releaseTime;
    private Long toReleaseDay;  //发布倒计时

    private BigDecimal budgetImpRate; //预算执行率

    private BigDecimal manhourImpRate;  //工时执行率

    private Integer sumJobitemNum;

    private Integer completeJobitemNum;

    private Double averageJobitemTime;  //平均工单工时数

    private Integer overtimeJobitemNum;  //逾期工单数

    private Double overdueRate;  //总延期率

    private RdmsMilestoneDto nextReviewMilestone;  //待评审里程碑时间

    private Integer submitFileNum;  //提交文档数

    private RdmsPerformanceDto performanceNo1;
    private RdmsPerformanceDto performanceNo2;
    private RdmsPerformanceDto performanceNo3;

    private List<RdmsPerformanceDto> performanceList;

}
