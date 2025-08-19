/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.dto.CategoryDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RdmsTeacherDto {
    private String id;

    private String name;

    private String nickname;

    private String image;

    private String position;

    private String motto;

    private String intro;

    private String creatorLoginname;

    private Integer sort;

    private String customerId;

    private String institution; //机构名称
    private String instId; //机构ID---填表的时候填写的
}
