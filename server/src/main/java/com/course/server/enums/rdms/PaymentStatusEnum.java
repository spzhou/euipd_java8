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
public enum PaymentStatusEnum {

    UNPAID("UNPAID", "应付未付"),
    PAID("PAID", "已支付");

    private String status;
    private String statusName;

    PaymentStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public static PaymentStatusEnum getPaymentStatusEnumByStatus(String status) {
        for (PaymentStatusEnum sourceEnum : PaymentStatusEnum.values()) {
            if (Objects.equals(sourceEnum.getStatus(), status)) {
                return sourceEnum;
            }
        }
        return null;
    }

    public static PaymentStatusEnum getPaymentStatusEnumByName(String statusName) {
        for (PaymentStatusEnum sourceEnum : PaymentStatusEnum.values()) {
            if (sourceEnum.getStatusName().equals(statusName)) {
                return sourceEnum;
            }
        }
        return null;
    }

}
