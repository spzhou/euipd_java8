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
public enum BudgetTypeEnum {

    PRE_PROJECT("PRE_PROJECT", "预立项预算"),
    SUB_PROJECT("SUB_PROJECT", "子项目预算"),
    PROJECT("PROJECT", "项目预算"),
    PRODUCT("PRODUCT", "产品管理");

    private final String type;
    private final String name;

    BudgetTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static BudgetTypeEnum getBudgetTypeEnumByType(String type) {
        for (BudgetTypeEnum typeEnum : BudgetTypeEnum.values()) {
            if (Objects.equals(typeEnum.getType(), type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static BudgetTypeEnum getBudgetTypeEnumByName(String name) {
        for (BudgetTypeEnum typeEnum : BudgetTypeEnum.values()) {
            if (typeEnum.getName().equals(name)) {
                return typeEnum;
            }
        }
        return null;
    }

}
