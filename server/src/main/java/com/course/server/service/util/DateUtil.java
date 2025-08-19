/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 将日期时间设置为当天的零点时间
     * 将输入日期的时间部分设置为00:00:00.000
     * 
     * @param datetime 输入日期时间
     * @return 返回设置为零点时间的日期
     * @throws IllegalArgumentException 如果输入日期为null
     * @throws RuntimeException 如果转换失败
     */
    public static Date zeroTimeOfDay(Date datetime) {
        if (datetime == null) {
            throw new IllegalArgumentException("Input date cannot be null");
        }

        LocalDateTime localDateTime = datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime adjustedTime = localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);

        try {
            return Date.from(adjustedTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            // Log the exception for debugging purposes
            System.err.println("Error converting LocalDateTime to Date: " + e.getMessage());
            throw new RuntimeException("Failed to convert LocalDateTime to Date", e);
        }
    }
    /**
     * 将日期时间设置为当天的下午5点
     * 将输入日期的时间部分设置为17:00:00.000
     * 
     * @param datetime 输入日期时间
     * @return 返回设置为下午5点时间的日期
     * @throws IllegalArgumentException 如果输入日期为null
     * @throws RuntimeException 如果转换失败
     */
    public static Date afternoon5pm(Date datetime) {
        if (datetime == null) {
            throw new IllegalArgumentException("Input date cannot be null");
        }

        LocalDateTime localDateTime = datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime adjustedTime = localDateTime.withHour(17).withMinute(0).withSecond(0).withNano(0);

        try {
            return Date.from(adjustedTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            // Log the exception for debugging purposes
            System.err.println("Error converting LocalDateTime to Date: " + e.getMessage());
            throw new RuntimeException("Failed to convert LocalDateTime to Date", e);
        }
    }
    /**
     * 将日期时间设置为当天的23:59:00
     * 将输入日期的时间部分设置为23:59:00.000
     * 
     * @param datetime 输入日期时间
     * @return 返回设置为23:59:00时间的日期
     * @throws IllegalArgumentException 如果输入日期为null
     * @throws ParseException 如果时间解析失败
     */
    public static Date afternoon23_59pm(Date datetime) throws ParseException {
        if (datetime == null) {
            throw new IllegalArgumentException("Input date cannot be null");
        }

        // Convert Date to LocalDateTime
        LocalDateTime localDateTime = datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Format the date part and set time to 23:59:00
        String datePart = localDateTime.format(OUTPUT_FORMATTER);
        String adjustedTimeStr = datePart + " 23:59:00";

        // Parse the adjusted time string back to LocalDateTime
        LocalDateTime adjustedTime = LocalDateTime.parse(adjustedTimeStr, INPUT_FORMATTER);

        // Convert LocalDateTime back to Date
        return Date.from(adjustedTime.atZone(ZoneId.systemDefault()).toInstant());

    }

    /**
     * 将日期时间设置为当天的23:59:59
     * 将输入日期的时间部分设置为23:59:59.000
     * 
     * @param datetime 输入日期时间
     * @return 返回设置为23:59:59时间的日期
     * @throws IllegalArgumentException 如果输入日期为null
     * @throws RuntimeException 如果转换失败
     */
    public static Date afternoon23_59_59pm(Date datetime) {
        if (datetime == null) {
            throw new IllegalArgumentException("Input date cannot be null");
        }

        LocalDateTime localDateTime = datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime adjustedTime = localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(0);

        try {
            return Date.from(adjustedTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            // Log the exception for debugging purposes
            System.err.println("Error converting LocalDateTime to Date: " + e.getMessage());
            throw new RuntimeException("Failed to convert LocalDateTime to Date", e);
        }
    }
    /**
     * 日期加一天
     * 将输入日期增加24小时
     * 
     * @param datetime 输入日期时间
     * @return 返回增加一天后的日期
     */
    public static Date addOneDay(Date datetime) {
        return new Date(datetime.getTime() + (24 * 60 * 60 * 1000));
    }

    /**
     * 日期减一天
     * 将输入日期减少24小时
     * 
     * @param datetime 输入日期时间
     * @return 返回减少一天后的日期
     */
    public static Date subtractOneDay(Date datetime) {
        return new Date(datetime.getTime() - (24 * 60 * 60 * 1000));
    }


}
