/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum ProjectStatusEnum {

    PREPARE("PREPARE", "准备立项"),
    SETUP_EDIT("SETUP_EDIT","立项编辑"),  //在研发部编辑状态
    SETUP("SETUP","申请立项"),  //到达上级审批者手上
    SETUPED("SETUPED","已立项"),
    ONGOING("ONGOING", "执行中"),
    SUSPEND("SUSPEND", "暂停"),
    INTEGRATION("INTEGRATION", "项目集成"),
    DEV_COMPLETE("DEV_COMPLETE", "增补材料"),
    EN_REVIEW("EN_REVIEW", "可评审"),
    REVIEW_SUBP("REVIEW_SUBP", "项目评审"),
    REVIEW_PRO("REVIEW_PRO", "项目评审"),

    COMPLETE("COMPLETE", "已完成"),
    CLOSE("CLOSE", "关闭"),
    OUT_SOURCE("OUT_SOURCE", "项目外包"),
    ARCHIVED("ARCHIVED", "已归档");

    private String status;
    private String statusName;

    ProjectStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static ProjectStatusEnum getProjectEnumByStatus(String status) {
        for (ProjectStatusEnum statusEnum : ProjectStatusEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static ProjectStatusEnum getProjectEnumByName(String statusName) {
        for (ProjectStatusEnum statusEnum : ProjectStatusEnum.values()) {
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
