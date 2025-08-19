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
public class MemberActionDto {
    private String id;
    private String pageCategory;
    private String pathName;
    private String paramId;
    private String institutionId;
    private String memberId;
    private String action;
    private Integer num;
    private String ip;
    private Date time;
}
