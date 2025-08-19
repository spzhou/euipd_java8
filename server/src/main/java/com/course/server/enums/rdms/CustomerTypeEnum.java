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
public enum CustomerTypeEnum {

    SUPPLYCHAIN("SUPPLYCHAIN", "供应链生态伙伴"),
    DELIVERER("DELIVERER", "交付端生态伙伴"),
    DEVELOPER("DEVELOPER", "开发者生态伙伴"),
    OEEC("OEEC", "开放工程实验中心");

    private final String type;
    private final String typeName;

    CustomerTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static CustomerTypeEnum getCustomerTypeEnumByType(String type) {
        for (CustomerTypeEnum typeEnum : CustomerTypeEnum.values()) {
            if (Objects.equals(typeEnum.getType(), type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static CustomerTypeEnum getCustomerTypeEnumByName(String typeName) {
        for (CustomerTypeEnum typeEnum : CustomerTypeEnum.values()) {
            if (typeEnum.getTypeName().equals(typeName)) {
                return typeEnum;
            }
        }
        return null;
    }

}
