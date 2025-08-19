/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsCharacter;
import com.course.server.domain.RdmsProject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsProjectDto extends RdmsProject {

    private String superviseName;
    private String bossId;
    private String bossName;

    private String productManagerName;

    private String projectManagerName;

    private String testManagerName;
    private String systemManagerName;

    private List<String> keyMemberList;

    private List<String> fileIdList;
    private String fileIdListStr;

    private List<RdmsFileDto> fileList;

    private String releaseTimeStr;

    private String createTimeStr;

    private String setupedTimeStr;

    private List<RdmsMilestoneDto> milestoneList;
    private RdmsProjectSubprojectDto projectTree;
    private List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeList;
    private List<RdmsCharacterDto> characters;
    private List<RdmsCbbDto> cbbList;
    private List<RdmsJobItemDto> reviewJobDetail;
    private Integer characterNum;
    private List<RdmsUserOutlineInfoDto> workerList;

    private Boolean hasReviewJobitem;
    private List<String> joinProjectIdList;

    private int handlingJobNum;
    private int submitJobNum;
    private int submitJobNum_testTask;
    private int secretLevel;  //项目保密等级

    //当修改项目信息时,可能产生新的任命人员,这里将原始任命人员保存下来,以供对比判断
    private String originProjectManagerId;
    private String originProductManagerId;
    private String originSystemManagerId;
    private String originTestManagerId;
    private String originIpmtId;

    private Integer materialAppNum;
    private Integer feeAppNum;
}
