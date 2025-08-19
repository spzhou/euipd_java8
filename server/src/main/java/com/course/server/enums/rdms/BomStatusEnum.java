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
public enum BomStatusEnum {
    NORMAL("NORMAL", "未设置"),
    REPLACED("REPLACED", "未设置"),
    CANCEL("CANCEL", "未设置"),
    OPTIONAL("OPTIONAL", "未设置");

    private final String status;
    private final String name;

    BomStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static BomStatusEnum getBomStatusByStatus(String status) {
        for (BomStatusEnum statusEnum : BomStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static BomStatusEnum getBomStatusByName(String name) {
        for (BomStatusEnum statusEnum : BomStatusEnum.values()) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum;
            }
        }
        return null;
    }
}
