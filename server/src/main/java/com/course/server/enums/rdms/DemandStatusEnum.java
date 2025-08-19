/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum DemandStatusEnum {

    DEFAULT("NULL", "未设定"),
    SAVED("SAVED", "已保存"),
    HANDLING("HANDLING", "处理中"),
    SUBMIT("SUBMIT", "已提交"),
    ABANDON("ABANDON", "废弃"),
    COMPLETED("COMPLETED", "已完成");

    private String status;
    private String statusName;

    DemandStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static DemandStatusEnum getDemandEnumByStatus(String status) {
        for (DemandStatusEnum statusEnum : DemandStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public static DemandStatusEnum getDemandEnumByName(String statusName) {
        for (DemandStatusEnum statusEnum : DemandStatusEnum.values()) {
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
