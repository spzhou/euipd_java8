/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class IndexConfigDto {
    private String id;
    private String institutionId;
    private String mainPlayerWarmupImg;

    private String mainLiveChannelId;

    private String coverImgRedirectUrl;

    private Date warmupTime;

    private Date showTime;

    private String upInstitutionId;

    private String upProductId;

    private String upCourseId;

    private String mainCover;

    private Boolean platLiveShow;

    private Boolean closePlatMenuFlag;

    private Boolean blackNameListFlag;

}