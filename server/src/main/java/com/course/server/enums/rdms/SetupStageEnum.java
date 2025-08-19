/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum SetupStageEnum {

    SETUP("SETUP", "预立项"),
    SETUPED("SETUPED", "已立项");

    private String status;
    private String statusName;

    SetupStageEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static SetupStageEnum getSetupStageEnumByStatus(String status) {
        for (SetupStageEnum statusEnum : SetupStageEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static SetupStageEnum getSetupStageEnumByName(String statusName) {
        for (SetupStageEnum statusEnum : SetupStageEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return null;
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
