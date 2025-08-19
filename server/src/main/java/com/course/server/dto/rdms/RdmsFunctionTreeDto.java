/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.List;

@Data
public class RdmsFunctionTreeDto {
    private String id;
    private String parent;
    private Integer deep;
    private String code;
    private String label;
    private String name;
    private String preprojectId;
    private String preprojectName;
    private String projectType;
    private String jobType;
    private String writerId;
    private String writerName;
    private String status;
    private String auxStatus;
    private Boolean haveUpdated;
    private List<RdmsFunctionTreeDto> children;
}
