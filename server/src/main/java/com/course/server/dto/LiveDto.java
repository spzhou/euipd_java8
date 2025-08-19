/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class LiveDto {
    private String id;

    private String name;

    private String summary;

    private Integer time;

    private BigDecimal price;

    private String image;

    private String video;

    private String vod;

    private String level;

    private String status;

    private Integer enroll;

    private Integer sort;

    private Date createdAt;

    private Date updatedAt;

    private String teacherId;

    private String creatorLoginname;

    private Integer like;

    private Integer collect;

    private Integer watch;

    private Integer power;

    private Integer promotion;

    private String channelId;

    private String url;

    private String format;

    private String sessionId;

    private Date timestemp;

    private List<CategoryDto> categorys;

    private TeacherDto teacher;

    private Integer newCourse;

    private String channelPassword;

    private String liveStatus;

    private Date showTime;
    private String showTimeStr;
    private Boolean plan;

    private Integer showClass;  // 1: 计划  0: 回放  2:正在直播

    private String institutionName;
    private String institutionId;
    private String institutionLogo;

    private Number watchNum; //展示量
    private Number likeNum; //点赞量
    private Number collectNum; //收藏量

    private String teacherUrl;  //讲师直播地址
    private String assistantUrl; //助教登录地址

    private String uploadUrl;

    private String liveStatusManual;
}