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
public enum CharacterStatusEnum {
    NOTSET("NOTSET", "未设置"),
    DISCARD("DISCARD", "废弃"),
    SAVED("SAVED", "保存"),
    SUBMIT("SUBMIT", "提交"),
    APPROVE("APPROVE", "审批中"),
    APPROVED("APPROVED", "批准"),
    SETUP("SETUP", "申请立项"),
    SETUPED("SETUPED", "立项"),
    DECOMPOSED("DECOMPOSED", "分解"),
    MERGED("MERGED", "合并"),
    ITERATING("ITERATING", "修订"),
    REFUSE("REFUSE","拒绝"),
    DEVELOPING("DEVELOPING", "开发中"),
    INTEGRATION("INTEGRATION", "集成中"),
    DEV_COMPLETE("DEV_COMPLETE", "开发完成"),
    QUALITY("QUALITY", "质量问题处理"),
    REVIEW("REVIEW", "功能评审"),
    HISTORY("HISTORY", "历史版本"),
    ARCHIVED("ARCHIVED", "归档"),
    UPDATE("UPDATE", "迭代处理"),
    OUT_SOURCE("OUT_SOURCE", "功能外包"),
    UPDATED_HISTORY("UPDATED_HISTORY", "历史版本"),
    UPDATED("UPDATED", "迭代更新");

    private final String status;
    private final String statusName;

    CharacterStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static CharacterStatusEnum getCharacterEnumByStatus(String status) {
        for (CharacterStatusEnum statusEnum : CharacterStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static CharacterStatusEnum getCharacterEnumByName(String statusName) {
        for (CharacterStatusEnum statusEnum : CharacterStatusEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return null;
    }
}
