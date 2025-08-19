/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum MixTypeEnum {
    SUB_PROJECT("SUB_PROJECT", "项目"),
    PROJECT("PROJECT", "主项目"),

    CHARACTER("CHARACTER", "功能/组件"),
    JOBITEM("JOBITEM", "工单");

    private String type;
    private String typeName;

    MixTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static MixTypeEnum getMixTypeEnumByType(String type) {
        for (MixTypeEnum typeEnum : MixTypeEnum.values()) {
            if (Objects.equals(typeEnum.getType(), type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static MixTypeEnum getMixTypeEnumByName(String typeName) {
        for (MixTypeEnum typeEnum : MixTypeEnum.values()) {
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
