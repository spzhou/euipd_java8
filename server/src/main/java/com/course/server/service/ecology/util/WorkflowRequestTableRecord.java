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
public class WorkflowRequestTableRecord {

    /**
     * 明细表recordOrder参数说明  0:代表新增，创建流程全部传0即可 传入其他行id，代表修改该id的明细(该id为数据库明细表的id值)
     */
    private Integer recordOrder = 0;

    /**
     * 一条明细上的所有属性
     */
    private List<Field> workflowRequestTableFields;
}
