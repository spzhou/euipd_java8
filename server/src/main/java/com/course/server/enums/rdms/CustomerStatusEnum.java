/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum CustomerStatusEnum {

    DEFAULT("NaN", "未设定"),
    CUSTOMER_REGISTER("REGISTER", "注册"),
    CUSTOMER_NORMAL("NORMAL", "在业"),
    CUSTOMER_STOP("STOP","停业"),
    CUSTOMER_HOLIDAY("HOLIDAY", "休假"),
    CUSTOMER_FROZEN("FROZEN", "冻结");

    private String status;
    private String statusName;

    CustomerStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static CustomerStatusEnum getCustomerEnumByStatus(String status) {
        for (CustomerStatusEnum statusEnum : CustomerStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public static CustomerStatusEnum getCustomerEnumByName(String statusName) {
        for (CustomerStatusEnum statusEnum : CustomerStatusEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
