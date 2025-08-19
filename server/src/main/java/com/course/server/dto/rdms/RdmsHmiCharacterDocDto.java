/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsHmiCharacterDocDto {
    //Character信息
    private String id;
    private String characterName;
    private Integer iterationVersion;
    private String functionDescription;
    private List<RdmsFileDto> fileList;
    private List<RdmsFileDto> jobItemFileList;  //创建这条Character的工单的附件
    private List<RdmsFileDto> jobItemProcessFileList;   //创建这条Character的工单流转附件

    //工单列表
    List<RdmsHmiJobItemDcoDto> jobitemDocList;


}
