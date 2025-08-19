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
public class InstitutionMemberDto {
    private String id;

    private String  institutionId;
    private String  institutionName;
    private String  institutionImage; //封面url

    private String  memberLoginName;

    private String  name;

    private String  registName;

    private String  status;

    private Date    createTime;

    private Date    updateTime;

    private String  profileImg;

    private String  title;

    private String  motto;

    private String  intro;

    private String  creatorLoginname;

}