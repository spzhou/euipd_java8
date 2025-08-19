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
public enum JobItemStatusEnum {

    PLAN("PLAN", "计划"),
    HANDLING("HANDLING", "处理"),
    REFUSE("REFUSE", "拒收"),
    SUBMIT("SUBMIT", "提交"),
    TESTING("TESTING","测试"),
    CHA_RECHECK("CHA_RECHECK","待复核"),
    QUA_RECHECK("QUA_RECHECK","质量复核"),  //质量复核
    SUB_RECHECK("SUB_RECHECK","待复核"),
    EVALUATE("EVALUATE","评价"),
    APPROVED("APPROVED","批准"),
    COMPLETED("COMPLETED", "完成"),
    ARCHIVED("ARCHIVED", "已归档"),
    ALARM("ALARM", "报警"),
    CANCEL("CANCEL", "取消"),
    OVERDUE("OVERDUE", "逾期");

    private String status;
    private String statusName;

    JobItemStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static JobItemStatusEnum getJobItemStatusEnumByStatus(String status) {
        for (JobItemStatusEnum statusEnum : JobItemStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static JobItemStatusEnum getJobItemStatusEnumByName(String statusName) {
        for (JobItemStatusEnum statusEnum : JobItemStatusEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return null;
    }

}
