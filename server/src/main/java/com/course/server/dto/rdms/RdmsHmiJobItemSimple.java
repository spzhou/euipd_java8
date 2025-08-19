/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsHmiJobItemSimple {
    private String id;

    private String jobSerial;

    private String jobName;

    private String itemName;  //前端统一名称, 方便混合表格显示

    private String status;

    private String type;

    private String docType;

    private String projectType;

    private int index;
}
