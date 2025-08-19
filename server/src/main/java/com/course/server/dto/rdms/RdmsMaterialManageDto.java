/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsMaterialManage;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsMaterialManageDto extends RdmsMaterialManage {

    private String characterName;
    private String subprojectName;
    private String preprojectName;

    private String customerName;

    private String writerName;

    private String nextNodeName;

    private String approverName;

    private List<RdmsFileDto> approveFileList;

    private List<RdmsFileDto> dealWithFileList;

    private List<RdmsFileDto> completeFileList;

    List<RdmsMaterialDto> materialList;

    private String approveStatus;

    private String loginUserId;

    private String jobType;
    private String docType;
    private String projectType;
    private String jobitemName;

    private String originCode;
    private String inheritJobType;
}
