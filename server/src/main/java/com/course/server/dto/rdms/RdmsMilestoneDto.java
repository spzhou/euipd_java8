/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsMilestone;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RdmsMilestoneDto extends RdmsMilestone {
    private String customerId;
    private String projectName;
    private String productManagerName;
    private String projectCode;

    private String reviewTimeStr;

    private String createTimeStr;

    private Date releaseTime; //输入参数. 当返回数据时为null

    private Boolean isOverTime;

}
