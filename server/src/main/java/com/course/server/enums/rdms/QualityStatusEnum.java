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
public enum QualityStatusEnum {
    SAVE("SAVE", "保存"),
    SUBMIT("SUBMIT", "缺陷反馈"),
    SAVE_ANALYSIS("SAVE_ANALYSIS", "保存分析"),
    HANDLING("HANDLING", "处理中"),
    JOB_SUBMIT("JOB_SUBMIT", "作业提交"),
    RECHECK("RECHECK","复核"),
    REJECT("REJECT","驳回"),
    COMPLETED("COMPLETED", "完成"),
    ARCHIVED("ARCHIVED", "归档");

    private String status;
    private String statusName;

    QualityStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static QualityStatusEnum getQualityStatusEnumByStatus(String status) {
        for (QualityStatusEnum statusEnum : QualityStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static QualityStatusEnum getQualityStatusEnumByName(String statusName) {
        for (QualityStatusEnum statusEnum : QualityStatusEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return null;
    }
}
