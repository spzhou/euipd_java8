/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsUserRoleUser;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RdmsCustomerUserDto extends RdmsCustomerUser implements java.io.Serializable {

    private String customerName;

    private String departmentName;

    private String joinTimeStr;

    private String createTimeStr;

    private String birthdayStr;

    private String userRoleListStr;  //权限分组

    private String customerUserId;  //为了前端使用统一变量名

    private Boolean isAdmin;

    private Boolean ipmtTransferDisable;  //穿梭框中 禁止移动
    private Boolean pdmTransferDisable;  //穿梭框中 禁止移动
    private Boolean pjmTransferDisable;  //穿梭框中 禁止移动


}
