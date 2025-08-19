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
public enum OperateTypeEnum {

    DEVELOP("DEVELOP", "开发"),
    TEST("TEST", "测试");

    private String type;
    private String typeName;

    OperateTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static OperateTypeEnum getOperateTypeEnumByType(String type) {
        for (OperateTypeEnum typeEnum : OperateTypeEnum.values()) {
            if (Objects.equals(typeEnum.getType(), type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static OperateTypeEnum getOperateTypeEnumByName(String typeName) {
        for (OperateTypeEnum typeEnum : OperateTypeEnum.values()) {
            if (typeEnum.getTypeName().equals(typeName)) {
                return typeEnum;
            }
        }
        return null;
    }
}
