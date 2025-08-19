/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.List;

@Data
public class RdmsHmiCharacterDocPageInfoDto {

    private String id;
    private String characterName;
    private String customerId;
    private String projectName;
    private String subprojectName;
    private String productManagerName;
    private String writerName;
    private String functionDescription;
    private String workCondition;
    private String inputLogicalDesc;
    private String functionLogicalDesc;
    private String outputLogicalDesc;
    private String testMethod;
    private List<RdmsFileDto> characterFileList;  //组件定义提交的附件
    private String projectManagerName;

    private RdmsBudgetExeDto budgetExeDto;

    private List<RdmsHmiJobItemDocPageDto> allDevJobList;
    private List<RdmsHmiJobItemDocPageDto> allTestJobList;
    private List<RdmsHmiJobItemDocPageDto> allIntegrationJobList;
    private List<RdmsHmiJobItemDocPageDto> allReviewJobList;

}
