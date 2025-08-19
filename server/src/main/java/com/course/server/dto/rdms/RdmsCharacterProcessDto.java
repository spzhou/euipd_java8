/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RdmsCharacterProcessDto {
    private String id;

    private String characterId;

    private String customerId;

    private String executorId;
    private String executorName;

    private Integer deep;

    private String jobDescription;

    private String nextNode;
    private String nextNodeName;

    private String fileListStr;

    private String approvalStatus;
    private String approvalStatusName;

    private String processStatus;
    private String processStatusName;

    private Date createTime;
    private String createTimeStr;

    private Integer deleted;

    private RdmsCharacterBudgetDto characterBudget; //审批通过时,确定计划工时

    private String loginUserId;

    private String jobType;
    private String projectType;

    private List<RdmsHmiProcessAttachmentDto> processAttachmentDtos;

}
