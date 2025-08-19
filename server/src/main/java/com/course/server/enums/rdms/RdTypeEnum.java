/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum RdTypeEnum {
    DEVELOPMENT("DEVELOPMENT", "开发项目"),
    INTEGRATION("INTEGRATION", "集成项目"),
    DIRECT("DIRECT", "直接立项"),
    MODULE("MODULE", "组件开发"),
    RESEARCH("RESEARCH", "课题项目"),
    PRODUCT("PRODUCT", "既有产品");

    private String type;
    private String name;

    RdTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static RdTypeEnum getRdTypeEnumByType(String type) {
        for (RdTypeEnum typeEnum : RdTypeEnum.values()) {
            if (Objects.equals(typeEnum.getType(), type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static RdTypeEnum getRdTypeEnumByName(String name) {
        for (RdTypeEnum typeEnum : RdTypeEnum.values()) {
            if (typeEnum.getName().equals(name)) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
