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
public class InstitutionOrgBrandDto {
    private String id;

    private String name;

    private Integer watch;

    private Integer promotion;

    private String contact;

    private String phone;

    private String logo;

    private String orgLink;

    private String creatorLoginname;

    private Date createdAt;

    private Date updatedAt;

    private Integer hasInstitution; //大于0意味着平台上有机构商铺

    private List<CategoryDto> categorys;

    private String massage;
}