/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum WorkerStatusEnum {

    DEFAULT("NaN", "未设定"),
    WORKER_NORMAL("NORMAL", "正常"),
//    WORKER_ASK_FOR_LEAVE("LEAVE","请假"),
//    WORKER_HOLIDAY("HOLIDAY", "休假"),
    WORKER_VACATE("VACATE", "辞职"),
    WORKER_DISMISS("DISMISS", "辞退");

    private String status;
    private String statusName;

    WorkerStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static WorkerStatusEnum getWorkerEnumByStatus(String status) {
        for (WorkerStatusEnum statusEnum : WorkerStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public static WorkerStatusEnum getWorkerEnumByName(String statusName) {
        for (WorkerStatusEnum statusEnum : WorkerStatusEnum.values()) {
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
