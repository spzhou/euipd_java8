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

@Data
public class ShareUploadDto {
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

}