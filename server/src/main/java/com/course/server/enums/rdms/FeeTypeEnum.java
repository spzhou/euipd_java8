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
public enum FeeTypeEnum {
    TEST("TEST", "测试费"),
    POWER("POWER","动力费"),
    CONFERENCE("CONFERENCE","会议费"),
    BUSINESS("BUSINESS","差旅费"),
    COOPERATION("COOPERATION","合作费"),
    PROPERTY("PROPERTY","知识产权费"),
    LABOR("LABOR","劳务费"),
    CONSULTING("CONSULTING", "专家费"),
    MANAGEMENT("MANAGEMENT", "管理费"),
    OTHER("OTHER", "其他费用");


/*    TRAVEL_EXPENSE("TRAVEL_EXPENSE", "差旅费"),
    BUSINESS_EXPENSE("BUSINESS_EXPENSE", "招待费"),
    EXPERT_FEE("EXPERT_FEE", "专家费"),
    CERTIFICATION_FEE("CERTIFICATION_FEE", "认证费"),
    PROPERTY_FEE("PROPERTY_FEE", "知识产权"),
    OTHER_FEE("OTHER_FEE", "其他费用")*/

    private final String status;
    private final String name;

    FeeTypeEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static FeeTypeEnum getFeeTypeByStatus(String status) {
        for (FeeTypeEnum feeTypeEnum : FeeTypeEnum.values()) {
            if (Objects.equals(feeTypeEnum.getStatus(), status)) {
                return feeTypeEnum;
            }
        }
        return null;
    }

    public static FeeTypeEnum getFeeTypeByName(String name) {
        for (FeeTypeEnum feeTypeEnum : FeeTypeEnum.values()) {
            if (feeTypeEnum.getName().equals(name)) {
                return feeTypeEnum;
            }
        }
        return null;
    }
}
