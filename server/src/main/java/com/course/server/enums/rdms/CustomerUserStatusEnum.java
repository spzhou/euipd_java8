/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

public enum CustomerUserStatusEnum {

    DEFAULT(-1, "未设定"),
    WORKER_NORMAL(0, "正常"),
    WORKER_ASK_FOR_LEAVE(1,"请假"),
    WORKER_HOLIDAY(2, "休假"),
    WORKER_VACATE(3, "辞职"),
    WORKER_DISMISS(4, "辞退");

    private int status;
    private String statusName;

    CustomerUserStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static CustomerUserStatusEnum getCustomerUserEnumByStatus(int status) {
        for (CustomerUserStatusEnum statusEnum : CustomerUserStatusEnum.values()) {
            if (statusEnum.getStatus() == status) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public static CustomerUserStatusEnum getCustomerUserEnumByName(String statusName) {
        for (CustomerUserStatusEnum statusEnum : CustomerUserStatusEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
