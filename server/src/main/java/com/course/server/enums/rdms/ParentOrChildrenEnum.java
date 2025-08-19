/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum ParentOrChildrenEnum {

    PARENT("PARENT", "父级"),
    CHILDREN("CHILDREN", "子级");

    private String key;
    private String name;

    ParentOrChildrenEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static ParentOrChildrenEnum getParentOrChildrenEnumByType(String key) {
        for (ParentOrChildrenEnum typeEnum : ParentOrChildrenEnum.values()) {
            if (Objects.equals(typeEnum.getKey(), key)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static ParentOrChildrenEnum getParentOrChildrenEnumByName(String name) {
        for (ParentOrChildrenEnum typeEnum : ParentOrChildrenEnum.values()) {
            if (typeEnum.getName().equals(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKer(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
