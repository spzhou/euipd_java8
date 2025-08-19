/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.calculate;

import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DateUtilsTest {

    @Test
    public void workDay() throws ParseException {
        List<String> holidays = new ArrayList<>();//假定放假日期
        holidays.add("2023-11-01");
        holidays.add("2023-11-06");//周六
        holidays.add("2023-11-17");//周日
        holidays.add("2023-11-18");

        List<String> overTimes = new ArrayList<>();//假定放假日期
        overTimes.add("2023-11-25");
        overTimes.add("2023-11-26");//周六
        overTimes.add("2023-12-07");//周日
        overTimes.add("2023-12-08");

        List<String> workDayList = CalDateUtils.workDay("2023-11-15~2023-12-04", holidays, overTimes);
        assert workDayList != null;
        HashSet<String> workdaySet = new HashSet<>(workDayList);
        for (String s : workdaySet) {
            System.out.println(s);
        }
    }
}
