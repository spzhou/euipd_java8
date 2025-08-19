/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsHmiNotifyDto {
    private String customerId;
    private String projectId;
    private String preProjectId;
    private String subprojectId;
    private String jobitemId;
    private String characterId;
    private String demandId;

    private String projectType;
    private String actorId;
    private String status;

    private Integer count;
    private Boolean flag;


}
