/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

public enum SellStatusEnum {

    DEFAULT(-1, "未设定"),
    SELL_STATUS_GOODS_APPROVE(0, "商品审批中"),
    SELL_STATUS_UP(1,"商品上架"),
    SELL_STATUS_DOWN(2, "商品下架"),
    STOCK_OUT(3, "缺货");

    private int status;
    private String statusName;

    SellStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static SellStatusEnum getSellStatusEnumByStatus(int status) {
        for (SellStatusEnum statusEnum : SellStatusEnum.values()) {
            if (statusEnum.getStatus() == status) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public static SellStatusEnum getSellStatusEnumByName(String statusName) {
        for (SellStatusEnum statusEnum : SellStatusEnum.values()) {
            if (statusEnum.getStatusName().equals(statusName)) {
                return statusEnum;
            }
        }
        return DEFAULT;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
