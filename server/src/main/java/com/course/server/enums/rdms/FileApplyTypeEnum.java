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
public enum FileApplyTypeEnum {
    CONSULT("CONSULT", "文档调阅"),
    APPLY("APPLY", "文档申请");

    private final String type;
    private final String name;

    FileApplyTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static FileApplyTypeEnum getFileApplyTypeByType(String type) {
        for (FileApplyTypeEnum applyTypeEnum : FileApplyTypeEnum.values()) {
            if (Objects.equals(applyTypeEnum.getType(), type)) {
                return applyTypeEnum;
            }
        }
        return null;
    }

    public static FileApplyTypeEnum getFileApplyTypeByName(String name) {
        for (FileApplyTypeEnum applyTypeEnum : FileApplyTypeEnum.values()) {
            if (applyTypeEnum.getName().equals(name)) {
                return applyTypeEnum;
            }
        }
        return null;
    }
}
