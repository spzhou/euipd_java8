/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.enums.rdms.MixTypeEnum;
import com.course.server.enums.rdms.ProjectTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsHmiProjectMixDto {
    private String id;   //projectId or subprojectId
    private ProjectTypeEnum type;

    private MixTypeEnum mixType;   //project subproject

    private String projectCode;

    private String customerId;

    private String projectName;

    private String projectIntroduce;  //introduce

    private String supervise;
    private String superviseName;

    private String productManagerId;
    private String productManagerName;

    private String projectManagerId;
    private String projectManagerName;

    private String testManagerId;
    private String testManagerName;

    private List<String> keyMemberList;

    private BigDecimal budget;

    private String incentivePolicy;

    private List<RdmsHmiTree> projectTree;

    private String status;

    private String parent;

    private Integer deep;

    private Date createTime;
    private Date setupedTime;
    private Date releaseTime;

    private Integer handlingJobNum;
    private Integer submitJobNum;
    private Integer evaluateJobNum;

}
