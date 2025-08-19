/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsProjectSubproject;
import com.course.server.dto.RdmsBudgetResearchDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsProjectSubprojectDto extends RdmsProjectSubproject {

    private List<RdmsHmiNameAndIdDto> keyMemberList;

    private List<RdmsFileDto> fileList;

    private String projectName;

    private String projectManagerName;

    private String productManagerName;

    private String superviseName;

    private String testManagerName;
    private String systemManagerName;

    private String creatorName;

    private String bossId;
    private String bossName;

    private String targetMilestoneName;

    private String releaseTimeStr;

    private List<RdmsProjectSubprojectDto> children;

    private List<RdmsCharacterDto> characters;
    private List<RdmsCbbDto> cbbList;
    private List<RdmsMilestoneDto> milestoneList;
    private List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeList;
    private List<RdmsUserOutlineInfoDto> workerList;

    private Integer handlingJobNum;
    private Integer submitJobNum;
    private Integer submitJobNum_subProject;
    private Integer evaluateJobNum;

    private Boolean isReviewing;
    private Boolean isCompleted;
    private Boolean isDevCompleted;  //"伪"完成状态 只在评审过程中使用, 不再打开开发状态, 只允许完成评审委员会发的工单

    private BigDecimal sumBudget;

    private Integer materialAppNum;
    private Integer feeAppNum;

    private RdmsBudgetResearchDto budgetData;

    private int secretLevel;  //项目保密等级
    private int mainSecretLevel;  //主项目保密等级

    //当修改项目信息时,可能产生新的任命人员,这里将原始任命人员保存下来,以供对比判断
    private String originProjectManagerId;
    private String originProductManagerId;
    private String originTestManagerId;
    private String originIpmtId;

}
