/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsCourse;
import com.course.server.dto.RdmsChapterDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class RdmsCourseDto extends RdmsCourse {
    private String id;

    private String name;

    private String summary;

    private Integer time;

    private BigDecimal price;

    private String image;

    private String video;

    private String vod;

    private String level;

    private String charge;

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

    private Integer newCourse;

    private Integer promotion;

    private List<String> categorys;

    private List<RdmsChapterDto> chapters;

    private List<RdmsSectionDto> sections;

    private String content;

    private RdmsTeacherDto teacher;

}
