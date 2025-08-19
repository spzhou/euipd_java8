/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsHmiWorkLoadCellDto {
    private long  dayStamp; //日期时间戳
    private String workerId;  //customerUserId
    private String jobitemId;
    private String dateStr;   // "yyyy-MM-dd"
    private double loadCell;  //对应每日的份额


}
