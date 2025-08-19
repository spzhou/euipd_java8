/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsUserRoleUserDto {
    private String id;

    private String roleId;

    private String userId;

    private String name;

    private String customerId;

}
