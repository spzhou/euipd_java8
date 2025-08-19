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
public class RdmsHmiTestJobitemAuxPlainDto {
    private String id;  //测试工单的Id

    private String testedId;
    private String testedJobSerial;
    private String testedJobName;

    private List<RdmsDemandDto> demandDtoList;

    private RdmsCharacterDto characterDto;

    private String testedTaskDescription;

    private List<RdmsFileDto> testedFileList;

    private RdmsJobItemPropertyDto testedPropertyDto;

    private List<RdmsJobItemDto> bugJobitemList;

}
