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
public enum AuxStatusEnum {
    NOTSET("NOTSET", "未设置"),
    PRINTED("PRINTED", "已打印");

    private final String status;
    private final String name;

    AuxStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static AuxStatusEnum getAuxStatusByStatus(String status) {
        for (AuxStatusEnum materialManageEnum : AuxStatusEnum.values()) {
            if (Objects.equals(materialManageEnum.getStatus(), status)) {
                return materialManageEnum;
            }
        }
        return null;
    }

    public static AuxStatusEnum getAuxStatusByName(String name) {
        for (AuxStatusEnum materialManageEnum : AuxStatusEnum.values()) {
            if (materialManageEnum.getName().equals(name)) {
                return materialManageEnum;
            }
        }
        return null;
    }
}
