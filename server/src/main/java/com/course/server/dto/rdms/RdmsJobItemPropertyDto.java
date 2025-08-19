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
public class RdmsJobItemPropertyDto {
    private String id;

    private String jobItemId;

    private String jobDescription;

    private String testKeyPoints;

    private String fileListStr;
    private List<RdmsFileDto> fileItems;

    private String referenceFileListStr;
    private List<RdmsFileDto> referenceFileList;

    private RdmsHmiJobEvaluateDto jobEvaluate;

    private Date createTime;

    private Integer deleted;

    private String loginUserId;

    private int reviewResult;
    /*
      <el-radio :label="2">通过</el-radio>
      <el-radio :label="4">通过(有条件)</el-radio>  <!--评审工单通过状态, 产品经理发工单给项目经理, 完成具体的工单-->
      <el-radio :label="6">增补材料</el-radio>  <!-- 评审工单, 保持现有状态, 产品经理发工单给项目经理, 增补完成后, 再批准-->
      <el-radio :label="8">驳回</el-radio> <!--功能状态回到系统集成阶段, 由项目经理重新发集成工单, 全部完成后, 重新提交审批-->
    */

    private String projectId;
    private String subprojectId;
    private String projectType;
    private String jobType;

    private String isCbb;  //功能组件作为CBB发布

    private Date planSubmissionTime;

}
