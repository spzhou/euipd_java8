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
public enum BudgetExeTypeEnum {
    DEVELOP("DEVELOP", "开发工单"),
    ASSIST("ASSIST", "协作工单"),
    TEST("TEST","测试工单"),
    MATERIAL("MATERIAL","物料费用"),
    OTHER_FEE("OTHER_FEE","其他费用");

    private final String type;
    private final String name;

    BudgetExeTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static BudgetExeTypeEnum getBudgetExeTypeEnumByType(String type) {
        for (BudgetExeTypeEnum exeTypeEnum : BudgetExeTypeEnum.values()) {
            if (Objects.equals(exeTypeEnum.getType(), type)) {
                return exeTypeEnum;
            }
        }
        return null;
    }

    public static BudgetExeTypeEnum getBudgetExeTypeEnumByName(String name) {
        for (BudgetExeTypeEnum exeTypeEnum : BudgetExeTypeEnum.values()) {
            if (exeTypeEnum.getName().equals(name)) {
                return exeTypeEnum;
            }
        }
        return null;
    }


}
