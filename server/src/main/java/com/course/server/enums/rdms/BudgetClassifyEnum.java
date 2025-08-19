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
public enum BudgetClassifyEnum {

    EQUIPMENT("EQUIPMENT", "设备费"),
    TEST("TEST", "测试费"),
    MATERIAL("MATERIAL", "物料费"),
    POWER("POWER","动力费"),
    CONFERENCE("CONFERENCE","会议费"),
    BUSINESS("BUSINESS","差旅费"),
    COOPERATION("COOPERATION","合作费"),
    PROPERTY("PROPERTY","知识产权费"),
    LABOR("LABOR","劳务费"),
    STAFF("STAFF", "人员费"),
    CONSULTING("CONSULTING", "专家费"),
    MANAGEMENT("MANAGEMENT", "管理费"),
    OTHER("OTHER", "其他费用");

    private String classify;
    private String classifyName;

    BudgetClassifyEnum(String classify, String classifyName) {
        this.classify = classify;
        this.classifyName = classifyName;
    }

    public static BudgetClassifyEnum getBudgetClassifyEnumByClassify(String classify) {
        for (BudgetClassifyEnum classifyEnum : BudgetClassifyEnum.values()) {
            if (Objects.equals(classifyEnum.getClassify(), classify)) {
                return classifyEnum;
            }
        }
        return null;
    }

    public static BudgetClassifyEnum getBudgetClassifyEnumByName(String classifyName) {
        for (BudgetClassifyEnum classifyEnum : BudgetClassifyEnum.values()) {
            if (classifyEnum.getClassifyName().equals(classifyName)) {
                return classifyEnum;
            }
        }
        return null;
    }

}
