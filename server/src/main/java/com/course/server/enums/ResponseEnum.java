/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

	ERROR(-1, "服务端错误"),
	SUCCESS(0, "成功"),
	PASSWORD_ERROR(1,"密码错误"),
	USERNAME_EXIST(2, "用户名已存在"),
	USER_NOT_EXIST(10008, "用户名不存在"),
	PARAM_ERROR(3, "参数错误"),
	EMAIL_EXIST(4, "邮箱已存在"),
	PHONE_EXIST(10002, "用户已存在"),
	NEED_LOGIN(10, "用户未登录, 请先登录"),
	PRODUCT_OFF_SALE_OR_DELETE(12, "商品下架或删除"),
	PRODUCT_NOT_EXIST(13, "商品不存在"),
	PRODUCT_STOCK_ERROR(14, "库存不足,请检查库存"),
	CART_PRODUCT_NOT_EXIST(15, "购物车里无此商品"),
	DELETE_SHIPPING_FAIL(16, "删除收货地址失败"),
	SHIPPING_NOT_EXIST(17, "收货地址不存在"),
	CART_SELECTED_IS_EMPTY(18, "请选择商品后下单"),
	ORDER_NOT_EXIST(19, "订单不存在"),
	ORDER_STATUS_ERROR(20, "订单状态有误"),
	NO_PERMISSION_ERROR(21, "无权限"),
	MALL_EXCEPTION(22, "商城处理异常"),

	ORDER_PAY_COMPLETED(200, "订单支付成功"),
	ORDER_PAYING(202, "订单支付系统确认中"),
	ORDER_PAY_TIMEOUT(205, "支付超时"),

	VERIFY_CODE_ERROR(1001, "手机验证码错误"),
	REGISTER_ERROR(1002, "用户注册失败"),
	SMS_CODE_ERROR(10001, "获取验证码失败"),
	PASSWORD_MODIFY_ERROR(10003, "密码修改失败"),
	USERNAME_OR_PASSWORD_ERROR(10005, "用户名或密码错误"),
	USER_INFO_UPDATE_ERROR(10006, "完善用户信息失败"),
	LOGIN_OUT_ERROR(10007, "退出登录失败"),


	ORDER_NOT_EXIST_ERROR(20001, "查询订单失败"),
	MODIFY_CUSTOMER_INFO_ERROR(20002, "完善用户信息错误"),
	QUERY_ORDER_ERROR(20003, "查询订单失败"),
	CREATE_ORDER_ERROR(20005, "创建订单失败"),
	CANCEL_ORDER_ERROR(20006, "取消订单失败"),
	ORDER_LIST_ERROR(20007, "订单列表错误"),
	MODIFY_CUSTOMER_ADDRESS_ERROR(20008, "修改收货地址错误"),
	ORDER_PAY_ERROR(20009, "支付失败"),
	COMPANY_VERIFY_ERROR(20010, "公司信息校验失败"),
	NON_CUSTOMER_ADDRESS_ERROR(20011, "客户收货地址为空"),
	CART_EMPTY_ERROR(20012, "购物车为空"),
	CUSTOMER_NO_MATCH_ERROR(20013, "客户信息不匹配"),
	ORDER_STATUS_NO_MATCH_ERROR(20015, "订单状态错误"),
	BUYER_ADDRESS_EMPTY_ERROR(20016, "订单状态错误"),
	USER_NO_MATCH_ERROR(20017, "用户不匹配"),

	CART_TOTAL_PRICE_ERROR(20018, "价格错误"),
	CUSTOMER_ADD_ERROR(20019, "将用户添加到客户名单失败"),
	CART_ADD_ERROR(20020, "购物车添加商品失败"),
	CART_UPDATE_ERROR(20021, "更新商品数量失败"),
	CART_DELETE_ERROR(20022, "商品删除失败"),
	GOODS_SEARCH_ERROR(20023, "没有查到任何商品"),
	GOODS_ID_ERROR(20025, "产品ID输入有误"),
	GOODS_NOT_EXIT_ERROR(20026, "商品不存在"),
	GOODS_DOWN_ERROR(20027, "商品已下架"),
	CATEGORY_DATA_ERROR(20028, "分类数据错误"),
	CART_NUM_QUERY_ERROR(20029, "购物车商品数量查询"),
	CART_ALL_SELECT_ERROR(20029, "购物车全选操作错误"),
	ORDER_NOT_PAY_ERROR(20030, "订单未完成支付"),

	;

	Integer code;

	String desc;

	ResponseEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
