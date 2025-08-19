/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CourseDto {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 概述
     */
    private String summary;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 价格（元）
     */
    private BigDecimal price;

    /**
     * 封面
     */
    private String image;

    private String video;
    /**
     * 封面
     */
    private String vod;

    /**
     * 级别|ONE("1", "初级"),TWO("2", "中级"),THREE("3", "高级")
     */
    private String level;

    /**
     * 收费|CHARGE("C", "收费"),FREE("F", "免费")
     */
    private String charge;

    /**
     * 状态|PUBLISH("P", "发布"),DRAFT("D", "草稿")
     */
    private String status;

    /**
     * 报名数
     */
    private Integer enroll;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    private List<CategoryDto> categorys;

    private List<ChapterDto> chapters;

    private List<SectionDto> sections;

    private String content;

    private TeacherDto teacher;

    private String teacherId;

    private Long goodsId;

    private String creatorLoginname;

    //频道号
    private String channelId;

    private String channelPassword;

    private List<String> mainImgs;
    private List<String> productImgs;
    private List<String> marqueeImgs;


    private String institutionName;
    private String institutionId;
    private String institutionLogo;

    private Number watchNum; //展示量
    private Number likeNum; //点赞量
    private Number collectNum; //收藏量

}
