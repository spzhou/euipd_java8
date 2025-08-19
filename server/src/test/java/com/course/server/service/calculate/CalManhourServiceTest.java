/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.calculate;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalManhourServiceTest {

    @Test
    public void calManhourInLaw() throws ParseException {
        //计算统计周期起止日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2023-12-12");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date timeStart = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        Date timeNext = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date timeEnd = calendar.getTime();
    }
}
