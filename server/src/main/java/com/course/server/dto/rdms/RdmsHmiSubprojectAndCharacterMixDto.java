/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsHmiSubprojectAndCharacterMixDto {
    private String id;  // characterId or subprojectId

    private String itemName;   //characterName or subproject label

    private String projectId;
    private String projectName;

    private String parent;  // parentJobitemId or parent

    private String projectManagerId;
    private String projectManagerName;

    private String productManagerId;
    private String productManagerName;

    private String description; //taskDescription or introduce

    private String writerId;
    private String writerName;

    private BigDecimal budget;

    private BigDecimal version;

    private Date complateTime;

    private Date createrTime;

    private String parentOrChildren;
    private String status;
    private String index;
    private String docType;

    private List<RdmsHmiSubprojectAndCharacterMixDto> children;

}
