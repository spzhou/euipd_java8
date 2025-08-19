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
public enum OverLoadStatusEnum {
    NORMAL("NORMAL", "正常工作量"),
    OVERLOAD("OVERLOAD", "工作超负荷");

    private final String status;
    private final String name;

    OverLoadStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static OverLoadStatusEnum getOverLoadStatusByStatus(String status) {
        for (OverLoadStatusEnum statusEnum : OverLoadStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static OverLoadStatusEnum getOverLoadStatusByName(String name) {
        for (OverLoadStatusEnum statusEnum : OverLoadStatusEnum.values()) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum;
            }
        }
        return null;
    }
}
