/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

@Data
public class InstitutionPageDto extends PageDto {

    private String status; //营业 打烊 停业 放假 注销 ...

    private String categoryId;

    private UserDto user;

    private Boolean adminFlag = false;

}
