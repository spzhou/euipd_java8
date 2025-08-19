/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.payPro.vo;

import com.course.server.enums.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {

	private Integer status;

	private String msg;

	private T data;

	private ResponseVO(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	private ResponseVO(Integer status, T data) {
		this.status = status;
		this.data = data;
	}

	public static <T> ResponseVO<T> successByMsg(String msg) {
		return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(), msg);
	}

	public static <T> ResponseVO<T> success(T data) {
		return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(), data);
	}

	public static <T> ResponseVO<T> success() {
		return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getDesc());
	}

	public static <T> ResponseVO<T> error(ResponseEnum responseEnum) {
		return new ResponseVO<>(responseEnum.getCode(), responseEnum.getDesc());
	}

	public static <T> ResponseVO<T> error(ResponseEnum responseEnum, String msg) {
		return new ResponseVO<>(responseEnum.getCode(), msg);
	}

	public static <T> ResponseVO<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
		return new ResponseVO<>(responseEnum.getCode(),
				Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " + bindingResult.getFieldError().getDefaultMessage());
	}
}
