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
public class RdmsHmiProjectExecuteInfoDto {
    private String id;
    private String projectCode;

    private String subProjectName;
    private String status;

    private Integer sumJobNumber;
    private Integer complateJobNumber;

    private Double manhourExeRate;  //工时完成率
    private Double manhourInTimeRate;  //工时及时率

    private Integer sumCharacterNumber;
    private Integer complateCharacterNumber;

    private Double budgetExeRate;  //预算执行率
    private Double budgetInTimeRate;  //预算达成率

    private List<RdmsHmiProjectExecuteInfoDto> childrenInfoList;
}
