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
public class RdmsHmiSubProjectReviewDetailDto {
    private String subprojectId;
    private RdmsProjectSubprojectDto subProjectDto;

    private List<RdmsJobItemDto> reviewJobitemList;

    //本子项目向下所有Character的评审信息
    private List<RdmsHmiCharacterReviewDetailDto> characterReviewDetailList;

    //包含下一级的子项目
    private List<RdmsHmiSubProjectReviewDetailDto> subProjectReviewDetailList;
}
