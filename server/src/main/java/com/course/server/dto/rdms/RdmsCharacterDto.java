/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsCharacter;
import com.course.server.domain.RdmsCharacterData;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RdmsCharacterDto extends RdmsCharacter {

    private String functionDescription;

    private String workCondition;

    private String inputLogicalDesc;

    private String functionLogicalDesc;

    private String outputLogicalDesc;

    private String testMethod;

    private String customerName;

    private String preProjectName;

    private String projectName;

    private String subprojectName;

    private String productManagerName;

    private String projectManagerId;
    private String projectManagerName;

    private String itemName;  // =characterName 在 功能开发 tab面板中为何和工单名称统一显示添加

    private List<RdmsFileDto> fileList;  //组件定义提交的附件

    private List<RdmsFileDto> jobItemFileList;  //工单带的附件

    private List<RdmsDemandDto> demandList;

    private RdmsCharacterDto parentCharacter;

    private String writerName;

    private String nextNodeName;

    private List<String> fileIds;

    private String createTimeStr;

    private String submitTimeStr;

    private String setupedTimeStr;

    private Integer treeDeep;

    private String docType;

    private String loginUserId;

    private String jobName; //为了作为子表在前端显示添加

    private List<RdmsHmiJobitemPlainDto> devJobitemPlainList;
    private List<RdmsJobItemDto> children;
    private List<RdmsCharacterDto> childrenList;
    private List<RdmsCharacterDto> referenceCharacterList;
    private String showFlag;
    private int index;

    private boolean hasIntJobitem;
    private Boolean hasReviewJobitem;

    private Integer submittedJobNum;
    private Boolean devCompleteStatus;

    private Integer materialAppNum;
    private Integer feeAppNum;

    private BigDecimal haveUsedBudget;

    private String rdType;

    private RdmsBudgetSheetDto budgetDetail;
    private RdmsCharacterData characterData;

    public RdmsCharacterDto() {
        super.setDevManhour(0.0);
        super.setDevManhourApproved(0.0);
        super.setTestFee(BigDecimal.ZERO);
        super.setTestManhour(0.0);
        super.setTestManhourApproved(0.0);
        super.setMaterialFee(BigDecimal.ZERO);
        super.setMaterialFeeApproved(BigDecimal.ZERO);
        super.setPowerFee(BigDecimal.ZERO);
        super.setConferenceFee(BigDecimal.ZERO);
        super.setBusinessFee(BigDecimal.ZERO);
        super.setCooperationFee(BigDecimal.ZERO);
        super.setPropertyFee(BigDecimal.ZERO);
        super.setLaborFee(BigDecimal.ZERO);
        super.setConsultingFee(BigDecimal.ZERO);
        super.setManagementFee(BigDecimal.ZERO);
        super.setSumOtherFee(BigDecimal.ZERO);
        super.setBudget(BigDecimal.ZERO);

    }
}
