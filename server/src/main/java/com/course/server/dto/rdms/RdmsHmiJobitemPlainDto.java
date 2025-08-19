/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.Date;

@Data
public class RdmsHmiJobitemPlainDto {
    //获得的数据
    private String id;
    private String jobSerial;
    private String jobName;
    private Date createTime;
    private String type;
    private String executorName;

    public RdmsHmiJobitemPlainDto(String id,String jobSerial, String jobName, String type, String executorName, Date createTime) {
        this.id = id;
        this.jobSerial = jobSerial;
        this.jobName= jobName;
        this.createTime = createTime;
        this.type = type;
        this.executorName = executorName;
    }
}
