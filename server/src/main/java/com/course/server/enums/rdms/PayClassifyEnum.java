/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum PayClassifyEnum {
    TASK_FUNCTION("TASK_FUNCTION", "系统任务"),   //预立项阶段系统工程的全部费用归集
    FUNCTION_FEE("FUNCTION_FEE", "系统工程费用"),

    CHARACTER("CHARACTER", "功能开发"),   //这个是在立项后对 功能 的处理
    TASK_SUBP("TASK_SUBP", "项目任务"),
    TASK_TEST("TASK_TEST", "测试任务"),
    SUBP_MATERIAL("SUBP_MATERIAL", "开发阶段物料"),
    SUBP_FEE("SUBP_FEE", "开发阶段费用"),

    SUB_PROJECT("SUB_PROJECT", "子项目费用"),    //比如:子项目评审手动添加的费用

    PROJECT("PROJECT", "产品任务"),    //产品中心发的任务都在这里归集
    PROJ_MATERIAL("PROJ_MATERIAL", "产品阶段物料"),
    PROJ_FEE("PROJ_FEE", "产品阶段费用");

    private String classify;
    private String classifyName;

    PayClassifyEnum(String classify, String classifyName) {
        this.classify = classify;
        this.classifyName = classifyName;
    }

    public static PayClassifyEnum getPayClassifyEnumByClassify(String classify) {
        for (PayClassifyEnum classifyEnum : PayClassifyEnum.values()) {
            if (Objects.equals(classifyEnum.getClassify(), classify)) {
                return classifyEnum;
            }
        }
        return null;
    }

    public static PayClassifyEnum getPayClassifyEnumByName(String classifyName) {
        for (PayClassifyEnum classifyEnum : PayClassifyEnum.values()) {
            if (classifyEnum.getClassifyName().equals(classifyName)) {
                return classifyEnum;
            }
        }
        return null;
    }

}
