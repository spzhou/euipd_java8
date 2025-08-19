/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum CharacterProcessStatusEnum {
    EDITING("EDITING", "编辑"),
    SUBMIT("SUBMIT", "提交"),
    EXAMINE("EXAMINE","签审"),
    APPROVE("APPROVE","审批中"),
    APPROVED("APPROVED","批准"),
    SETUP("SETUP","申请立项"),
    SETUPED("SETUPED","立项"),
    DISTRIBUTE("DISTRIBUTE","已分配"),
    ONGOING("ONGOING","开发中"),
    ITERATION("ITERATION", "修订"),
    DECOMPOSE("DECOMPOSE", "分解"),
    REFUSE("REFUSE","拒绝"),
    REVIEW("REVIEW", "评审"),
    QUALITY("QUALITY", "质量处理"),
    COMPLETE("COMPLETE", "完成");

    private String status;
    private String statusName;

    CharacterProcessStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static CharacterProcessStatusEnum getJobItemStatusEnumByStatus(String status) {
        for (CharacterProcessStatusEnum statusEnum : CharacterProcessStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static CharacterProcessStatusEnum getJobItemStatusEnumByName(String statusName) {
        for (CharacterProcessStatusEnum statusEnum : CharacterProcessStatusEnum.values()) {
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
