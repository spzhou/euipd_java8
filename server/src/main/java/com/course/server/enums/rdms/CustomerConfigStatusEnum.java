/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum CustomerConfigStatusEnum {

    DEFAULT("DEFAULT", "未设定"),
    USED("USED", "使用"),
    UNUSED("UNUSED", "未使用"),
    SUSPEND("SUSPEND", "暂停"),
    FROZEN("FROZEN", "冻结"),
    STOP("STOP","停用");

    private String status;
    private String statusName;

    CustomerConfigStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static CustomerConfigStatusEnum getCustomerConfigEnumByStatus(String status) {
        for (CustomerConfigStatusEnum statusEnum : CustomerConfigStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public static CustomerConfigStatusEnum getCustomerConfigEnumByName(String statusName) {
        for (CustomerConfigStatusEnum statusEnum : CustomerConfigStatusEnum.values()) {
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
