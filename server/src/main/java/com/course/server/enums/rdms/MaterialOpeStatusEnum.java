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
public enum MaterialOpeStatusEnum {
    APPLICATION("APPLICATION", "申请中"),
    GTE_MATERIAL("GTE_MATERIAL", "领料中"),
    RETURN("RETURN", "退库处理中"),
    RETURN_APPLICATION("RETURN_APPLICATION", "退库申请中"),
    RETURN_PRE_APPLICATION("RETURN_PRE_APPLICATION", "退库申请中"),
    RETURN_APPROVED("RETURN_APPROVED", "审批通过"),
    RETURN_PRE_APPROVED("RETURN_PRE_APPROVED", "预批准"),
    RETURN_COMPLETE("RETURN_COMPLETE", "退库完成"),
    APPROVED("APPROVED", "已审批"),
    COMPLETE("COMPLETE", "完成");

    private final String status;
    private final String name;

    MaterialOpeStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static MaterialOpeStatusEnum getMaterialOpeStatusByStatus(String status) {
        for (MaterialOpeStatusEnum materialOpeStatusEnum : MaterialOpeStatusEnum.values()) {
            if (Objects.equals(materialOpeStatusEnum.getStatus(), status)) {
                return materialOpeStatusEnum;
            }
        }
        return null;
    }

    public static MaterialOpeStatusEnum getMaterialOpeStatusByName(String name) {
        for (MaterialOpeStatusEnum materialOpeStatusEnum : MaterialOpeStatusEnum.values()) {
            if (materialOpeStatusEnum.getName().equals(name)) {
                return materialOpeStatusEnum;
            }
        }
        return null;
    }
}
