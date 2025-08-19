/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsDemand;
import com.course.server.domain.RdmsJobItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsJobItemDto extends RdmsJobItem {

    private String customerName;
    private String preProjectName;

    private String milestoneId;

    private List<RdmsDemandDto> demandDtoList;
    private List<String> demandIdList;
    private List<String> demandNameList;

    private List<String> testJobitemIdList;
    private List<String> assistJobitemIdList;

    private String projectName;

    private String subprojectName;

    private String characterName;
    private RdmsCharacterDto characterDto;
    private List<String> characterIdList;

    private String projectManagerName;

    private String productManagerName;

    private Integer remainManhour;

    private List<RdmsFileDto> fileList;

    private List<String> checkedFileIdList; //发工单时, 复选框勾选的附件

    private String processFileListStr;
    private List<RdmsFileDto> processFileList;

    private RdmsJobItemPropertyDto propertyDto;

    private String planSubmissionTimeStr;

    private String actualSubmissionTimeStr;

    private String executorName;

    private String nextNodeName;

    private String statusName;

    private String processStatus;

    private String createTimeStr;

    private String jobDescription;

    private String loginUserId;

    private List<String> reviewWorkerIdList;
    private List<String> reviewWorkerNameList;

    private List<RdmsHmiCharacterPlainDto> characterPlainDtos;

    private List<RdmsCharacterDto> children;

    private List<RdmsJobItemDto> assistJobitemList;
    private List<RdmsJobItemDto> testJobitemList;
    private List<RdmsJobItemProcessDto> jobitemProcessList; //包含联合评审

    private List<RdmsJobItemDto> reviewJobitemList; //包含联合评审
    private List<RdmsJobItemDto> reviewCooJobitemList;
    private List<RdmsJobItemDto> reviewSupplementJobitemList;
    private List<RdmsJobItemDto> reviewConditionalJobitemList;

    private String showFlag;

    private int index;

    private String docType;
    private String parentOrChildren;  // value:  parent  children

    private String itemName;  // = jobName  在 功能开发 tab面板中为了和特性名称名称统一显示添加

    private Boolean childrenJobComplateFlag;

    private Integer submitJobNum;  //提交上来的子工单数量  协作工单或者bug工单的数量

    private String testedJobType;
    private String parentJobType;
    private Double standManhour; //标准工时

    private Long jobRemainSecond;  //工单剩余时间 单位为 : 秒
    private Long jobCompleteSecond;  //工单结束时, 提前或逾期的时间 单位为 : 秒

    private Long materialApplicationNum;  //物料申请单数量
    private Long feeApplicationNum;  //费用申请单数量

    private String associateProjectStr; //创建质量工单的时候使用
    private String reviewGroupLeaderName; //评审组长姓名

    private String rdType;
    private Date milestoneTime;

    private String originJobitemId;  //原始的jobitemId
    private String associatedJobitemId;  //关联的jobitemId
    private String replaceFileIdsStr;  //被替换的文件Id的json字符串

    private String functionType; //系统功能处理类型
    private String sysgmId; // 系统工程总监Id

    private Double declareManhour;
    private String declareReason;
}
