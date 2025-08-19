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
public class RdmsJobItemProcessDto {
    private String id;

    private String jobItemId;

    private String executorId;
    private String executorName;

    private Integer deep;

    private String jobDescription;

    private String fileListStr;
    private List<RdmsFileDto> fileList;

    private String nextNode;
    private String nextNodeExecutorName;

    private Date actualSubmissionTime;
    private String actualSubmissionTimeStr;

    private String processStatus;
    private String characterIdListStr;

    private String approvalStatus;

    private Date createTime;
    private String createTimeStr;

    private Integer deleted;

    List<RdmsHmiCharacterPlainDto> characterPlainDtos;

    private String loginUserId;
    private String projectType;

}
