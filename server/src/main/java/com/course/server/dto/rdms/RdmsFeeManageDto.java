/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsFeeManage;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsFeeManageDto extends RdmsFeeManage {

    private String characterName;

    private String customerName;

    private String writerName;

    private String approverName;

    private String nextNodeName;

    private String approveStatus;

    private List<RdmsFileDto> approveFileList;

    private List<RdmsFileDto> dealWithFileList;

    private List<RdmsFileDto> completeFileList;

    private String loginUserId;

    private String docType;   //不同阶段的费用用这个 参数来区分:   PreProject  Project SubProject
    private String jobType;
    private String projectType;
    private String jobitemName;
    private String auxType;
}
