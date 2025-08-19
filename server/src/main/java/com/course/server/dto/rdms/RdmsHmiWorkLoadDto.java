/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsHmiWorkLoadDto {
    private long  dayStamp; //日期时间戳
    private String workerId;  //customerUserId
    private String dateStr;   // "yyyy-MM-dd"
    private double dayLoad;      //对应每日的总负荷
    private double averageDayLoad;      //平均每日的负荷
    private double averageDayLoadOfWeek;      //向前算一周平均每日的负荷
}
