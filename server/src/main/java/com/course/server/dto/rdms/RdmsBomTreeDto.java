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
public class RdmsBomTreeDto {
    private String id;
    private String parent;
    private Integer deep;
    private String bomCode;
    private String label;
    private String name;
    private String model;
    private String unit;
    private String amount;
    private String subprojectId;
    private String projectId;
    private List<RdmsBomTreeDto> children;
}
