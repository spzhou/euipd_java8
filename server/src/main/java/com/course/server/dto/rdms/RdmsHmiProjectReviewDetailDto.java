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
public class RdmsHmiProjectReviewDetailDto {
    private String projectId;
    private RdmsProjectDto projectDto;

    private List<RdmsJobItemDto> reviewJobitemList;

    private List<RdmsHmiSubProjectReviewDetailDto> subProjectReviewDetailList;

}
