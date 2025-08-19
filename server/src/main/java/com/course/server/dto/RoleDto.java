/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoleDto {

    private Integer index;
    /**
     * id
     */
    private String id;
    /**
     * 角色
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    private String customerId;
    private String userId;

    private List<String> resourceIds;

    private List<String> userIds;

    private List<String> roleIds;

    private int enAuthEdit;

}
