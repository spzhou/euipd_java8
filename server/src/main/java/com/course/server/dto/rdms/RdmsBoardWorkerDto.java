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
public class RdmsBoardWorkerDto {

    private RdmsCustomerUserDto customerUserDto;

    private Long sumJobitemNum;  //工单总数
    private List<RdmsJobItemDto> complateJobList;  //完工工单数
    private List<RdmsJobItemDto> exeJobList;  //执行中工单数
    private List<RdmsJobItemDto> overdueJobList;  //逾期工单数

    private Double sumManhour;
    private Double sumExeManhour; //执行中工时总数
    private Double sumCompManhour; //完工工时总数

    private Long submitDocNum;  //提交文档总数
    private List<RdmsFileDto> docList;
}
