/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain.rdms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class MyCharacter {
    private String id;

    private String characterName;

    private String customerId;

    private String projectId;

    private String subprojectId;

    private String productManagerId;

    private String status;

    //job_item
    private String jobitemId;

    private String jobSerial;

    private String jobName;

    private String projectManagerId;

    private String executorId;

    private String jobStatus;

    private String type;


}
