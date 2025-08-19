/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.Date;

@Data
public class RdmsMaterialProcessDto {
    private String id;

    private String materialId;

    private String executorId;

    private Integer deep;

    private String description;

    private String nextNode;

    private Date submitTime;

    private String status;

    private Date createTime;

}
