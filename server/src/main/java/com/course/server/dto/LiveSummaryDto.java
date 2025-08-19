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
public class LiveSummaryDto {
    private String videoId;
    private String teacherId;
    private String name;
    private String profile;
    private String teacherInfo;
    private String title;

    private String vod;
    private String url;

    private String instName;
    private String instId;
    private String instSummary;
    private String instImage;

    private String videoCover;

}