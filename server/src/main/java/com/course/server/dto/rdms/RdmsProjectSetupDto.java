/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.List;

@Data
public class RdmsProjectSetupDto extends RdmsProjectDto {
    private List<RdmsCharacterDto> characterList;
    private String targetProjectId;
    private String loginUserId;
    private String rejectReason;
    private List<String> moduleIdList;
    private String moduleSetupProjectId;
    private int secretLevel;  //项目保密等级
    private String saveOrSubmitFlag;  // save / submit
    private String categoryId;
}
