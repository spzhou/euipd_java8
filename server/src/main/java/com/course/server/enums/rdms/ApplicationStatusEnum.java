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
public enum ApplicationStatusEnum {
    NOTSET("NOTSET", "未设置"),
    APPLICATION("APPLICATION", "申请"),
    REJECT("REJECT", "拒绝"),
    PRE_APPROVED("PRE_APPROVED", "预批准"),
    APPROVED("APPROVED", "批准"),
    SUBMIT("SUBMIT", "待签审"),  //物料处置完成后, 提交审批
    SUPPLEMENT("SUPPLEMENT", "退回"),
    CANCEL("CANCEL", "取消"),
    PRE_COMPLETE("PRE_COMPLETE", "复核通过"),
    COMPLETE("COMPLETE", "结束");

    private final String status;
    private final String name;

    ApplicationStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static ApplicationStatusEnum getApplicationStatusByStatus(String status) {
        for (ApplicationStatusEnum applicationStatusEnum : ApplicationStatusEnum.values()) {
            if (Objects.equals(applicationStatusEnum.getStatus(), status)) {
                return applicationStatusEnum;
            }
        }
        return null;
    }

    public static ApplicationStatusEnum getApplicationStatusByName(String name) {
        for (ApplicationStatusEnum applicationStatusEnum : ApplicationStatusEnum.values()) {
            if (applicationStatusEnum.getName().equals(name)) {
                return applicationStatusEnum;
            }
        }
        return null;
    }
}
