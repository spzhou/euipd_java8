/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SectionDto {

    /**
     * ID
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 课程|course.id
     */
    private String courseId;

    /**
     * 章|chapter.id
     */
    private String chapterId;

    /**
     * 视频
     */
    private String video;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 收费|C 收费；F 免费
     */
    private String charge;

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

    private String vod;

    private String url;  //mp4 Url




}