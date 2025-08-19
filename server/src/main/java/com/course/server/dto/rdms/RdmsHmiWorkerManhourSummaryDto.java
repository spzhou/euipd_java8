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
public class RdmsHmiWorkerManhourSummaryDto {
    private String id;    //customerUserId
    private String workerName;
    private Integer taskJobNum;
    private Integer testJobNum;
    private Integer devJobNum;
    private Double taskJobSumManhour;
    private Double testJobSumManhour;
    private Double devJobSumManhour;
    private Integer sumJobNum;
    private Double sumManhour;

    private BigDecimal manhourFee;
}
