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
public enum BudgetApplicantStatusEnum {

    SAVE("SAVE", "保存"),
    SUBMIT("SUBMIT", "提交"),
    REFUSED("REFUSED", "驳回"),
    APPROVED("APPROVED", "批准"),
    COMPLETE("COMPLETE", "完成");

    private final String status;
    private final String name;

    BudgetApplicantStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static BudgetApplicantStatusEnum getBudgetApplicantStatusEnumByStatus(String status) {
        for (BudgetApplicantStatusEnum statusEnum : BudgetApplicantStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static BudgetApplicantStatusEnum getBudgetApplicantStatusEnumByName(String name) {
        for (BudgetApplicantStatusEnum statusEnum : BudgetApplicantStatusEnum.values()) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum;
            }
        }
        return null;
    }

}
