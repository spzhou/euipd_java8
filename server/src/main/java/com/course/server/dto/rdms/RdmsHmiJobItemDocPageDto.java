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
public class RdmsHmiJobItemDocPageDto {
    private String id;
    private String jobSerial;
    private String jobName;
    private String executorId;
    private String executorName;
    private String nextNodeId;
    private String nextNodeName;
    private String status;
    private String type;
    private String taskDescription;
    private Double manhour;
    private List<RdmsFileDto> taskFileList;  //任务附件  //对于评审工单而言, 这里的附件是"提交人"给的
    private String evaluateDesc;
    private Integer evaluateScore;
    private String submitDescription;
    private List<RdmsFileDto> submitFileList;  //提交附件  //对于评审工单而言, 这里的附件是评审组给出的
    private List<RdmsFileDto> processFileList;  //执行过程附件
}
