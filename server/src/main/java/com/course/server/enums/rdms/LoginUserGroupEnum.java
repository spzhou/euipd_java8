/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum LoginUserGroupEnum {

    CUSTOMER("CUSTOMER", "机构客户"),
    DEVELOPER("DEVELOPER","开发者"),
    CUSTOMER_VIP1("CUSTOMER_VIP1", "高级机构客户-1级"),
    DEVELOPER_VIP1("DEVELOPER_VIP1", "高级开发者-1级");

    private String status;
    private String statusName;

    LoginUserGroupEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static LoginUserGroupEnum getLoginUserGroupEnumByStatus(String status) {
        for (LoginUserGroupEnum statusEnum : LoginUserGroupEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return DEVELOPER;
    }

    public static LoginUserGroupEnum getLoginUserGroupEnumByName(String statusName) {
        for (LoginUserGroupEnum statusEnum : LoginUserGroupEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return DEVELOPER;
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
