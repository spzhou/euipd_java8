
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单列表页面VO
 */
@Data
public class OrderListDto {

    private String orderId;
    private String orderNo;
    private Double preTaxPrice;
    private Integer payType;
    private Integer orderStatus;
    private String orderStatusString;
    private String buyerAddr;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private List<OrderItemVO> mallOrderItemVOS;

}
