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
public class RdmsHmiSubprojectAndJobItemMixDto {
    private String id;  // jobitemId or subprojectId

    private String itemName;   //jobName or subproject label

    private String projectId;
    private String projectName;

    private String parent;  // parentJobitemId or parent

    private String projectManagerId;
    private String projectManagerName;

    private String productManagerId;
    private String productManagerName;

    private String description; //taskDescription or introduce

    private Date submitTime;// planSubmissionTime or releaseTime of subproject

    private String executorId; // executorId or projectManagerId
    private String executorName;  //executorName or projectManagerName

    private Date createrTime;

    private String parentOrChildren;

    private List<RdmsHmiSubprojectAndJobItemMixDto> children;

}
