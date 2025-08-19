/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.enums.FileGroupingEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsCharacterSimpleDto {

    private String id;

    private String parent;

    private Integer deep;

    private String characterName;

    private Integer iterationVersion;

    private String customerId;

    private String preProjectId;

    private String projectId;

    private String subprojectId;

    private String milestoneId;

    private String productManagerId;

    private String fileListStr;

    private String demandListStr;

    private String writerId;

    private String nextNode;

    private String status;
    private String auxStatus;
    private String versionUpdate;

    private String jobitemType;

    private String jobitemId;

    private String projectType;

    private Date createTime;

    private Date submitTime;

    private Date setupedTime;
}
