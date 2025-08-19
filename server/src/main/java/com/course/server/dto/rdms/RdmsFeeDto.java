/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class RdmsFeeDto {
    private String id;

    private String characterId;

    private String code;

    private String name;

    private String type;

    private String reason;

    private BigDecimal preTaxPrice;

    private BigDecimal tax;

    private BigDecimal taxRate;

    private BigDecimal price;

    private String writerId;

    private String nextNode;

    private String status;

    private String auxStatus;

    private String fileListStr;

    private Date updateTime;

    private Date createTime;

}
