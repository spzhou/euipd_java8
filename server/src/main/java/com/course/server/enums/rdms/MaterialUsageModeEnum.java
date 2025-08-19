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
public enum MaterialUsageModeEnum {
    BORROW("BORROW", "借用"),
    CAPITALIZATION("CAPITALIZATION", "资产化"),
    EXPENDITURE("EXPENDITURE", "费用化");

    private final String status;
    private final String name;

    MaterialUsageModeEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static MaterialUsageModeEnum getMaterialUsageModeByStatus(String status) {
        for (MaterialUsageModeEnum materialUsageModeEnum : MaterialUsageModeEnum.values()) {
            if (Objects.equals(materialUsageModeEnum.getStatus(), status)) {
                return materialUsageModeEnum;
            }
        }
        return null;
    }

    public static MaterialUsageModeEnum getMaterialUsageModeByName(String name) {
        for (MaterialUsageModeEnum materialUsageModeEnum : MaterialUsageModeEnum.values()) {
            if (materialUsageModeEnum.getName().equals(name)) {
                return materialUsageModeEnum;
            }
        }
        return null;
    }
}
