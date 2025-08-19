
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;


public enum OrderStatusEnum {

    ERROR(-9, "ERROR"),
    ORDER_PRE_PAY(0, "待支付"),
    ORDER_PAID(1, "已支付"),
    ORDER_PACKAGED(2, "配货完成"),
    ORDER_EXPRESS(3, "出库完成"),

    ORDER_SUCCESS(6, "交易成功"),       //交易成功之后就不能再编辑了,避免更新时间发生变化
    ORDER_PAY_CONFIRM(8, "财务确认完成"),     //经过财务确认之后,订单转态为8 ,就可以结算了
    ACCOUNT_CONFIRM(10, "结算完成"),
    ORDER_CLOSED_BY_MALLUSER(-1, "手动关闭"),
    ORDER_CLOSED_BY_EXPIRED(-2, "超时关闭"),
    ORDER_CLOSED_BY_JUDGE(-3, "商家关闭"),
    ORDER_RETURN(-4, "退款订单");

    private int orderStatus;

    private String name;

    OrderStatusEnum(int orderStatus, String name) {
        this.orderStatus = orderStatus;
        this.name = name;
    }

    public static OrderStatusEnum getMallOrderStatusEnumByStatus(int orderStatus) {
        for (OrderStatusEnum mallOrderStatusEnum : OrderStatusEnum.values()) {
            if (mallOrderStatusEnum.getOrderStatus() == orderStatus) {
                return mallOrderStatusEnum;
            }
        }
        return ERROR;
    }

    public static OrderStatusEnum getMallOrderStatusEnumByName(String name) {
        for (OrderStatusEnum mallOrderStatusEnum : OrderStatusEnum.values()) {
            if (mallOrderStatusEnum.getName().equals(name)) {
                return mallOrderStatusEnum;
            }
        }
        return ERROR;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
