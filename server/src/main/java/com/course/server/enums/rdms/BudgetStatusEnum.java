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
public enum BudgetStatusEnum {

    CREATED("CREATED", "创建"),
    SUBMITTED("SUBMITTED", "提交"),
    ADJUSTED("ADJUSTED", "调整"),
    PAUSED("PAUSED", "暂停"),
    SUSPENDED("SUSPENDED", "中止"),
    COMPLETE("COMPLETE", "完成");

    private final String status;
    private final String name;

    BudgetStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static BudgetStatusEnum getBudgetStatusEnumByStatus(String status) {
        for (BudgetStatusEnum statusEnum : BudgetStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static BudgetStatusEnum getBudgetStatusEnumByName(String name) {
        for (BudgetStatusEnum statusEnum : BudgetStatusEnum.values()) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum;
            }
        }
        return null;
    }

}
