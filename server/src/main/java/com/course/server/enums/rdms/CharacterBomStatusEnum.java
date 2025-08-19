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
public enum CharacterBomStatusEnum {
    EDIT("EDIT", "可编辑"),
    SUBMIT("SUBMIT", "已提交"),
    RECHECK("RECHECK", "复核中"),
    LOCKED("LOCKED", "锁定");

    private final String status;
    private final String name;

    CharacterBomStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static CharacterBomStatusEnum getBomStatusByStatus(String status) {
        for (CharacterBomStatusEnum statusEnum : CharacterBomStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static CharacterBomStatusEnum getBomStatusByName(String name) {
        for (CharacterBomStatusEnum statusEnum : CharacterBomStatusEnum.values()) {
            if (statusEnum.getName().equals(name)) {
                return statusEnum;
            }
        }
        return null;
    }
}
