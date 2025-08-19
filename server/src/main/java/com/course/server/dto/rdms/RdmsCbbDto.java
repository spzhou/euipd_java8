/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsCbb;
import com.course.server.domain.RdmsCharacter;
import com.course.server.domain.RdmsCharacterData;
import lombok.Data;

import java.util.List;

@Data
public class RdmsCbbDto extends RdmsCbb {
    private String customerName;
    private String createrName;
    private String projectManagerName;
    private String productManagerName;
    private String systemManagerName;
    private RdmsCharacter character;

    private String characterName;
    private String functionDescription;
    private String workCondition;
    private String inputLogicalDesc;
    private String functionLogicalDesc;
    private String outputLogicalDesc;
    private String testMethod;

    private String projectType;
    private String jobitemId;
    private String jobitemType;
    private String loginUserId;
    private String moduleIdListStr;
    private List<String> fileIds;

    private RdmsCharacterData cbbData;
}
