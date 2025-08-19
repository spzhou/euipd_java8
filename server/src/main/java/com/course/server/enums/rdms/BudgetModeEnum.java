/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum BudgetModeEnum {

    FREE("FREE", "宽松"),
    STRICT("STRICT", "严格");

    private String mode;

    private String name;

    BudgetModeEnum(String mode, String name) {
        this.mode = mode;
        this.name = name;
    }

    public static BudgetModeEnum getBudgetModeEnumByMode(String mode) {
        for (BudgetModeEnum modeEnum : BudgetModeEnum.values()) {
            if (Objects.equals(modeEnum.getMode(), mode)) {
                return modeEnum;
            }
        }
        return FREE;
    }

    public static BudgetModeEnum getBudgetModeEnumByName(String name) {
        for (BudgetModeEnum modeEnum : BudgetModeEnum.values()) {
            if (modeEnum.getName().equals(name)) {
                return modeEnum;
            }
        }
        return FREE;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }
}
