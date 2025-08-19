/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum JobItemProcessStatusEnum {

    DEFAULT("NULL", "未设定"),
    ISSUE("ISSUE", "任务下发"),
    REFUSE("REFUSE", "工单拒收"),
    SUBMIT("SUBMIT", "作业提交"),
    TESTING("TESTING", "测试中"),
    APPROVE("APPROVE","作业签审"),
    REJECT("REJECT","工单驳回"),
    TRANSFER("TRANSFER","转交修订"),
    CHA_RECHECK("CHA_RECHECK","功能复核"),
    QUA_RECHECK("QUA_RECHECK","质量复核"),
    SUB_RECHECK("SUB_RECHECK","项目复核"),
    EVALUATE("EVALUATE", "工作评价"),
    COMPLETE("COMPLETE", "工作完成");

    private String status;
    private String statusName;

    JobItemProcessStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static JobItemProcessStatusEnum getJobItemStatusEnumByStatus(String status) {
        for (JobItemProcessStatusEnum statusEnum : JobItemProcessStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public static JobItemProcessStatusEnum getJobItemStatusEnumByName(String statusName) {
        for (JobItemProcessStatusEnum statusEnum : JobItemProcessStatusEnum.values()) {
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
