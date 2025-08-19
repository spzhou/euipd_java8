/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum ReviewResultEnum {

    PASS("PASS", "通过",2),
    CONDITIONAL("CONDITIONAL", "有条件通过",4),
    SUPPLEMENT("SUPPLEMENT", "增补&评审",6),
    REJECT("REJECT", "驳回",8);

    private String status;
    private String statusName;
    private int statusNum;

    ReviewResultEnum(String status, String statusName, int statusNum) {
        this.status = status;
        this.statusName = statusName;
        this.statusNum = statusNum;
    }

    public static ReviewResultEnum getReviewResultEnumByStatus(String status) {
        for (ReviewResultEnum statusEnum : ReviewResultEnum.values()) {
            if (Objects.equals(statusEnum.getStatus(), status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static ReviewResultEnum getReviewResultEnumByName(String statusName) {
        for (ReviewResultEnum statusEnum : ReviewResultEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static ReviewResultEnum getReviewResultEnumByNum(int statusNum) {
        for (ReviewResultEnum statusEnum : ReviewResultEnum.values()) {
            if (statusEnum.getStatusNum()==statusNum) {
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

    public int getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(int statusNum) {
        this.statusNum = statusNum;
    }
}
