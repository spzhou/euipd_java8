
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;


public enum PayStatusEnum {

    PAY_CANCEL(-2, "取消支付"),
    PAY_ERROR(-1, "支付失败"),
    UNPAID(0,"待支付"),
    PAY_COMPLETE(1, "已支付"),
    ORDER_PAY_SUCCESS(200, "支付成功");

    private int payStatus;

    private String name;

    PayStatusEnum(int payStatus, String name) {
        this.payStatus = payStatus;
        this.name = name;
    }

    /**
     * 根据支付状态码获取对应的支付状态枚举
     * 
     * @param payStatus 支付状态码
     * @return 返回对应的支付状态枚举，如果未找到则返回待支付状态
     */
    public static PayStatusEnum getPayStatusEnumByStatus(int payStatus) {
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            if (payStatusEnum.getPayStatus() == payStatus) {
                return payStatusEnum;
            }
        }
        return UNPAID;
    }

    /**
     * 根据支付状态名称获取对应的支付状态枚举
     * 
     * @param name 支付状态名称
     * @return 返回对应的支付状态枚举，如果未找到则返回待支付状态
     */
    public static PayStatusEnum getPayStatusEnumByName(String name) {
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            if (payStatusEnum.getName().equals(name)) {
                return payStatusEnum;
            }
        }
        return UNPAID;
    }

    /**
     * 获取支付状态码
     * 
     * @return 返回支付状态码
     */
    public int getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态码
     * 
     * @param payStatus 支付状态码
     */
    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取支付状态名称
     * 
     * @return 返回支付状态名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置支付状态名称
     * 
     * @param name 支付状态名称
     */
    public void setName(String name) {
        this.name = name;
    }
}
