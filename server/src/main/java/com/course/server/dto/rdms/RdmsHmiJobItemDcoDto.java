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
public class RdmsHmiJobItemDcoDto {
    private String id;
    private String jobSerial;
    private String jobName;
    private String taskDescription;
    private List<RdmsFileDto> fileList;    //工单任务附件
    private List<RdmsFileDto> processFileList;   //过程附件
    private RdmsJobItemPropertyDto propertyDto;

    //对应本条工单的测试工单
    private List<RdmsHmiJobItemDcoDto>  testJobitemList;  //测试工单  可能有多个

}
