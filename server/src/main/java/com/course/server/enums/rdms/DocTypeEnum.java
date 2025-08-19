/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum DocTypeEnum {

    DEFAULT("DEFAULT", "默认"),
    FILE("FILE", "文件"),
    DEMAND("DEMAND", "需求"),
    CHARACTER("CHARACTER", "功能"),
    QUALITY("QUALITY", "质量"),
    BUG("BUG", "缺陷"),
    JOBITEM("JOBITEM", "工单"),
    PRE_PROJECT("PRE_PROJECT", "已立项"),
    SUB_PROJECT("SUB_PROJECT", "项目"),
    MILESTONE("MILESTONE", "里程碑"),
    BUDGET("BUDGET", "预算"),
    BUDGET_ADJUST("BUDGET_ADJUST", "预算调整"),
    PROJECT("PROJECT", "项目");

    private String type;
    private String typeName;

    DocTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static DocTypeEnum getDocTypeEnumByType(String type) {
        for (DocTypeEnum typeEnum : DocTypeEnum.values()) {
            if (Objects.equals(typeEnum.getType(), type)) {
                return typeEnum;
            }
        }
        return DEFAULT;
    }

    public static DocTypeEnum getDocTypeEnumByName(String typeName) {
        for (DocTypeEnum typeEnum : DocTypeEnum.values()) {
            if (typeEnum.getTypeName().equals(typeName)) {
                return typeEnum;
            }
        }
        return DEFAULT;
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
