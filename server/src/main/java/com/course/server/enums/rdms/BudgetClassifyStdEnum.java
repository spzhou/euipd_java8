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
public enum BudgetClassifyStdEnum {

    EQUIPMENT("F1.1", "设备费"),
    MATERIAL("F2.1", "材料费"),  //业务费-材料费
    TEST("F2.2", "测试化验加工费"),   //业务费-测试化验加工费
    POWER("F2.3","燃料动力费"),  //业务费-燃料动力费
    CONFERENCE("F2.4.1","代理费"),  //业务费-会议费
    BUSINESS("F2.4.2","差旅费"),  //业务费-差旅费
    COOPERATION("F2.4.3","注册认证费"),  //业务费-合作与交流费
    PROPERTY("F2.5","知识产权/出版/文献..."),  //业务费-出版/文献...
    CONSULTING("F2.6", "临床试验"),   //专家咨询费
    OTHER("F2.7", "其他支出"),  //业务费-其他支出
    LABOR("F3.1","非临床研究费"),  //劳务费
    MANAGEMENT("F4.1", "间接经费-管理费"),
    PERFORMANCE("F4.2", "间接经费-绩效支出"),
    STAFF("F5.1", "人员费"),
    INFRASTRUCTURE("F6.1", "基本建设费");

    private String classify;
    private String classifyName;

    BudgetClassifyStdEnum(String classify, String classifyName) {
        this.classify = classify;
        this.classifyName = classifyName;
    }

    public static BudgetClassifyStdEnum getBudgetClassifyEnumByClassify(String classify) {
        for (BudgetClassifyStdEnum classifyEnum : BudgetClassifyStdEnum.values()) {
            if (Objects.equals(classifyEnum.getClassify(), classify)) {
                return classifyEnum;
            }
        }
        return null;
    }

    public static BudgetClassifyStdEnum getBudgetClassifyEnumByName(String classifyName) {
        for (BudgetClassifyStdEnum classifyEnum : BudgetClassifyStdEnum.values()) {
            if (classifyEnum.getClassifyName().equals(classifyName)) {
                return classifyEnum;
            }
        }
        return null;
    }

}
