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
public enum FixedDepartmentEnum {
    PRODUCT_CENTER("PRODUCT", "产品中心"),
    SYSTEM_ENGINEERING_DEPARTMENT("SYSTEM", "系统工程部"),
    RESEARCH_AND_DEVELOPMENT_CENTER("R_AND_D", "研发中心"),
    TEST_DEPARTMENT("TEST", "测试部"),
    QUALITY_DEPARTMENT("QUALITY","质量部"),
    REGULATION_DEPARTMENT("REGULATION","法规部");

    private final String code;
    private final String name;

    FixedDepartmentEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean isExist(String code){
        //判断FixedDepartmentEnum中是否包含code对应的枚举
        if (code == null) {
            return false;
        }
        for (FixedDepartmentEnum codeEnum : FixedDepartmentEnum.values()) {
            if (codeEnum.getCode().equals(code.trim())) {
                return true;
            }
        }
        return false;
    }

    public static FixedDepartmentEnum getEnumByCode(String code) {
        for (FixedDepartmentEnum codeEnum : FixedDepartmentEnum.values()) {
            if (Objects.equals(codeEnum.getCode(), code)) {
                return codeEnum;
            }
        }
        return null;
    }

    public static FixedDepartmentEnum getEnumByName(String name) {
        for (FixedDepartmentEnum codeEnum : FixedDepartmentEnum.values()) {
            if (codeEnum.getName().equals(name)) {
                return codeEnum;
            }
        }
        return null;
    }

}
