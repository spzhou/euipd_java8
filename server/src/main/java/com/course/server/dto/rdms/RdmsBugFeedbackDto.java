/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsBugFeedback;
import com.course.server.domain.RdmsQuality;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsBugFeedbackDto extends RdmsBugFeedback {
    private String writerName;
    private String productManagerName;
    private String projectManagerName;
    private String testManagerName;
    private String executorName;
    private String projectName;
    private String subprojectName;
    private String associateProjectNameStr;
    private List<RdmsFileDto> fileList;
    private List<RdmsFileDto> feedbackFileList;
    private String docType;
    private String itemName;  //前端统一名称, 方便混合表格显示
    private String loginUserId;
    private String characterName;
    private String jobSerial;
    private String jobName;
    private Date jobCompleteTime;
    private String jobitemPropertyFileIdListStr;
    private boolean jobCompleteFlag;

    private List<RdmsHmiJobItemSimple> children;
    private List<RdmsMaterialManageDto> materialCostList;
    private List<RdmsFeeManageDto> feeCostList;
    private BigDecimal sumManhourCost;
    private BigDecimal sumMaterialCost;
    private BigDecimal sumFeeCost;
    private BigDecimal sumCost;

    private Integer submittedJobNum;
    private Integer materialAppNum;
    private Integer feeAppNum;

    private RdmsJobItemPropertyDto propertyDto;

}
