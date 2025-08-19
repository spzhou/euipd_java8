/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum ActorTypeEnum {

    ALL("ALL", "所有人员", 0),
    WORKER("WORKER", "普通工作人员", 1),
    PROJECT_MANAGER("PROJECT_MANAGER", "项目经理",2),
    PRODUCT_MANAGER("PRODUCT_MANAGER", "产品经理",3),
    IPMT_ITMT("IPMT_ITMT", "创新战略委员会",4),
    SUPERVISE("SUPERVISE","监管委员",5),
    ADMIN("ADMIN", "管理员",6),
    BOSS("BOSS", "所有者",7),
    CREATER("CREATER", "创建者",8);

    private String status;
    private String statusName;
    private int statusNum;

    ActorTypeEnum(String status, String statusName, int statusNum) {
        this.status = status;
        this.statusName = statusName;
        this.statusNum = statusNum;
    }

    public static ActorTypeEnum getActorTypeEnumByStatus(String status) {
        for (ActorTypeEnum statusEnum : ActorTypeEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return WORKER;
    }

    public static ActorTypeEnum getActorTypeEnumByName(String statusName) {
        for (ActorTypeEnum statusEnum : ActorTypeEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return WORKER;
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

    public int getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(int statusNum) {
        this.statusNum = statusNum;
    }


}
