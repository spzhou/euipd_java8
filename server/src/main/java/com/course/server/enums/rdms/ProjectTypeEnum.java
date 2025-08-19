/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum ProjectTypeEnum {
    PRE_PROJECT("PRE_PROJECT", "预立项项目"),
    DEV_PROJECT("DEV_PROJECT", "项目定义"),
    SUB_PROJECT("SUB_PROJECT", "子项目开发"),
    PROJECT("PROJECT", "产品开发"),
    PRODUCT("PRODUCT", "PLM阶段"),
    UPD_PROJECT("UPD_PROJECT", "升级开发"),
    MODULE("MODULE", "组件开发");

    private String type;
    private String typeName;

    ProjectTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static ProjectTypeEnum getProjectTypeEnumByType(String type) {
        for (ProjectTypeEnum typeEnum : ProjectTypeEnum.values()) {
            if (Objects.equals(typeEnum.getType(), type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static ProjectTypeEnum getProjectTypeEnumByName(String typeName) {
        for (ProjectTypeEnum typeEnum : ProjectTypeEnum.values()) {
            if (typeEnum.getTypeName().equals(typeName)) {
                return typeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
