/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import lombok.Data;

import java.util.List;

@Data
public class DetailData {

    /**
     * 表名名称
     */
    private String tableDBName;

    /**
     * "deleteAll":"1"(删除该流程原有明细)
     */
    private String deleteAll;

    /**
     * "deleteKeys":"12,13"（删除该流程明细id=12，13d的明细行）
     */
    private String deleteKeys;

    /**
     * 明细列表
     */
    private List<WorkflowRequestTableRecord> workflowRequestTableRecords;

}
