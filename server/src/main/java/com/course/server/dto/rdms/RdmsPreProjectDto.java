/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsPreProject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsPreProjectDto extends RdmsPreProject {
    private String id;

    private String customerId;

    private String preProjectName;

    private BigDecimal devVersion;

    private String preProjectIntroduce;

    private String productManagerId;

    private String rdType;

    private Date createTime;

    private Integer deleted;

    private String productManagerName;
    private String systemManagerName;

    private String status;

    private String projectId;

    private String createTimeStr;

    private Long handlingItems;  //功能/组件 待处理的条目数
    private Long demandHandlingItems;  //需求 待处理的条目数
    private Long updateItems;  //迭代 待处理的条目数

    private Integer handlingJobNum;
    private Integer submitJobNum;   //功能/组件 已提交的工单数
    private Integer taskSubmitJobNum;   //任务工单提交数目

    private List<RdmsCharacterDto> characterDtoList;
    private String bossName;
    private String bossId;
    private String sysgmId;

    private Integer materialAppNum;
    private Integer feeAppNum;

}
