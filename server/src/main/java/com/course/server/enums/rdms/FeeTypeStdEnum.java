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
public enum FeeTypeStdEnum {
    TEST("F2.2", "业务费-测试化验加工费"),
    POWER("F2.3","业务费-燃料动力费"),
    CONFERENCE("F2.4.1","业务费-会议费"),
    BUSINESS("F2.4.2","业务费-差旅费"),
    COOPERATION("F2.4.3","业务费-合作与交流费"),
    PROPERTY("F2.5","业务费-出版/文献..."),
    CONSULTING("F2.6", "专家咨询费"),
    OTHER("F2.7", "业务费-其他支出"),
    LABOR("F3.1","劳务费"),
    MANAGEMENT("F4.1", "间接经费-管理费"),
    PERFORMANCE("F4.2", "间接经费-绩效支出"),
    INFRASTRUCTURE("F6.1", "基本建设费");

    private String classify;
    private String classifyName;

    FeeTypeStdEnum(String classify, String classifyName) {
        this.classify = classify;
        this.classifyName = classifyName;
    }

    public static FeeTypeStdEnum getBudgetClassifyEnumByClassify(String classify) {
        for (FeeTypeStdEnum classifyEnum : FeeTypeStdEnum.values()) {
            if (Objects.equals(classifyEnum.getClassify(), classify)) {
                return classifyEnum;
            }
        }
        return null;
    }

    public static FeeTypeStdEnum getBudgetClassifyEnumByName(String classifyName) {
        for (FeeTypeStdEnum classifyEnum : FeeTypeStdEnum.values()) {
            if (classifyEnum.getClassifyName().equals(classifyName)) {
                return classifyEnum;
            }
        }
        return null;
    }

}
