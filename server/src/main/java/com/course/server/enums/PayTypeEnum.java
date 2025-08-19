
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;


public enum PayTypeEnum {

    ERROR(-9, "ERROR"),
    NOT_PAY(0, "未付款"),
    ALI_PAY(1, "支付宝"),
    WEIXIN_PAY(2, "微信支付"),
    BANK_PAY(3, "银行转账付款");

    private int payType;

    private String name;

    PayTypeEnum(int payType, String name) {
        this.payType = payType;
        this.name = name;
    }

    public static PayTypeEnum getPayTypeEnumByType(int payType) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getPayType() == payType) {
                return payTypeEnum;
            }
        }
        return ERROR;
    }

    public static PayTypeEnum getPayTypeEnumByName(String name) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getName().equals(name)) {
                return payTypeEnum;
            }
        }
        return ERROR;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
