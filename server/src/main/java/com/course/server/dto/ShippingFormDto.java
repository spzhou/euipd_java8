/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by 廖师兄
 */
@Data
public class ShippingFormDto {

	@NotBlank
	private String receiverName;

/*
	@NotBlank
	private String receiverPhone;
*/

	@NotBlank
	private String receiverMobile;

	@NotBlank
	private String receiverProvince;

	@NotBlank
	private String receiverCity;

	@NotBlank
	private String receiverDistrict;

	@NotBlank
	private String receiverAddress;

	@NotBlank
	private String receiverZip;

	@NotBlank
	private String memberId;

}
