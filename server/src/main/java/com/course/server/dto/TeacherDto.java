/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TeacherDto {
    private String id;

    private String name;

    private String nickname;

    private String image;

    private String position;

    private String motto;

    private String intro;

    private String creatorLoginname;

    private String articleTitle;

    private Date showTime;
    private String showTimeStr;

    private Integer sort;

    private String articleCoverImg;

    private Boolean plan;
    private String subjectIntro;

    private String institutionName;
    private String institutionId; //根据创建人,查询出来的, 其实是创建人的机构Id----需要确认一下
    private String institutionLogo;

    private Number watchNum; //展示量
    private Number likeNum; //点赞量
    private Number collectNum; //收藏量

    private List<CategoryDto> categorys;

    private String institution; //机构名称
    private String instId; //机构ID---填表的时候填写的
}