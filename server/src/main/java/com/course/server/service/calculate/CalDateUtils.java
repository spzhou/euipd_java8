/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.calculate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalDateUtils {

    /**
     * 判断指定日期是否为周末
     * 检查给定日期是否为周六或周日
     * 
     * @param bDate 日期字符串，格式为yyyy-MM-dd
     * @return 返回true表示是周末，false表示不是周末
     * @throws ParseException 日期解析异常
     */
    public static boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }

    }

    /**
     * 获取指定日期的周末类型
     * 判断给定日期是否为周末，并返回对应的周末标识
     * 
     * @param bDate 日期字符串，格式为yyyy-MM-dd
     * @return 返回Calendar.SATURDAY(7)表示周六，Calendar.SUNDAY(1)表示周日，0表示不是周末
     * @throws ParseException 日期解析异常
     */
    public static int weekendDay(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return Calendar.SATURDAY;
        } else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return Calendar.SUNDAY;
        }else{
            return 0;
        }

    }

    /**
     * 获取指定日期范围内的所有日期列表
     * 生成从开始日期到结束日期之间的所有日期，包括开始和结束日期
     * 
     * @param beginDateStr 开始日期字符串，格式为yyyy-MM-dd
     * @param endDateStr 结束日期字符串，格式为yyyy-MM-dd
     * @return 返回日期字符串列表，格式为yyyy-MM-dd
     */
    public  static List<String> getDayListOfDate(String beginDateStr, String endDateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        Calendar beginGC = null;
        Calendar endGC = null;
        List<String> list = new ArrayList<String>();
        try {
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);
            beginGC = Calendar.getInstance();
            beginGC.setTime(beginDate);
            endGC = Calendar.getInstance();
            endGC.setTime(endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
                list.add(sdf.format(beginGC.getTime()));
                beginGC.add(Calendar.DAY_OF_MONTH, 1);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定日期范围内的周末日期列表
     * 生成从开始日期到结束日期之间的所有周末日期
     * 
     * @param beginDateStr 开始日期字符串，格式为yyyy-MM-dd
     * @param endDateStr 结束日期字符串，格式为yyyy-MM-dd
     * @return 返回周末日期字符串列表，格式为yyyy-MM-dd
     */
    public static List<String> getWeekendListOfDate(String beginDateStr, String endDateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        Calendar beginGC = null;
        Calendar endGC = null;
        List<String> list = new ArrayList<String>();
        try {
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);
            beginGC = Calendar.getInstance();
            beginGC.setTime(beginDate);
            endGC = Calendar.getInstance();
            endGC.setTime(endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
                int week = getWeek(beginGC.getTime());
                if ( week == 1 || week == 7) {
                    list.add(sdf.format(beginGC.getTime()));
                }
                beginGC.add(Calendar.DAY_OF_MONTH, 1);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定日期的星期几
     * 返回日期对应的星期标识，1表示周日，2表示周一，...，7表示周六
     * 
     * @param date 日期对象
     * @return 返回星期标识，1-7分别对应周日到周六
     */
    public static int getWeek(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        int weekInt = c.get(Calendar.DAY_OF_WEEK);
        return weekInt;
    }

    /**
     * 获取指定时间范围内的工作日列表
     * 计算从开始时间到结束时间之间的工作日，排除节假日和加班日
     * 
     * @param startTime 开始时间字符串
     * @param endTime 结束时间字符串
     * @param holidayDayList 节假日列表
     * @param overTimeDayList 加班日列表
     * @return 返回工作日字符串列表
     * @throws ParseException 日期解析异常
     */
    public static List<String> workDay(String startTime, String endTime, List<String> holidayDayList, List<String> overTimeDayList ) throws ParseException {
        String dateValue = startTime + "~" + endTime;
        return workDay(dateValue, holidayDayList, overTimeDayList);
    }

    /**
     * @param dateValue 日期区间【2018-01-01~2018-12-31】
     *                dateValue = "2023-11-15~2023-12-04"
     * @param holidayDayList 手动填写的休假列表
     *                List<String> holidayDayList = new ArrayList<String>();//假定放假日期
     *      *         holidayDayList.add("2023-11-01"); //不在计算区间
     *      *         holidayDayList.add("2023-11-06"); //不在计算区间
     *      *         holidayDayList.add("2023-11-17");
     *      *         holidayDayList.add("2023-11-18"); //周六
     * @param overTimeDayList 手动添加的加班日期
     *                List<String> overTimeDayList = new ArrayList<String>();//假定放假日期
     *      *         overTimeDayList.add("2023-11-25"); //周六
     *      *         overTimeDayList.add("2023-11-26"); //周日
     *      *         overTimeDayList.add("2023-12-07"); //不在计算区间
     *      *         overTimeDayList.add("2023-12-08"); //不在计算区间
     * @return 工作日期列表
     * @throws ParseException
     */
    public static List<String> workDay(String dateValue, List<String> holidayDayList, List<String> overTimeDayList ) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom = dateValue.split("~")[0];
        String dateTo = dateValue.split("~")[1];

        if(dateFrom.equals(dateTo)) {
            return null;
        } else {
            Date dateF = f.parse(dateFrom);
            Date dateT = f.parse(dateTo);

            List<String> holidayList = new ArrayList<>();//假定放假日期
            if(holidayDayList != null){
                for(String dayStr: holidayDayList){
                    Date aDay = f.parse(dayStr);
                    if(aDay.getTime() >= dateF.getTime() && aDay.getTime() <= dateT.getTime()){
                        holidayList.add(dayStr);
                    }
                }
            }
            List<String> overTimeList = new ArrayList<>();//假定放假日期
            if(overTimeDayList != null){
                for(String dayStr: overTimeDayList){
                    Date aDay = f.parse(dayStr);
                    if(aDay.getTime() >= dateF.getTime() && aDay.getTime() <= dateT.getTime()){
                        overTimeList.add(dayStr);
                    }
                }
            }
            //获取所有工作日
            List<String> days = getDayListOfDate(dateFrom, dateTo);
            //获取所有周末
            List<String> weekends = getWeekendListOfDate(dateFrom, dateTo);

            //利用HashSet的特性去重
            Set<String> holidaySet = new HashSet<>();
            //得到所有的假期
            if(!holidayList.isEmpty()){
                holidaySet.addAll(holidayList);
            }
            holidaySet.addAll(weekends);

            assert days != null;
            Set<String> workdaySet = new HashSet<>(days);
            //去掉所有节假日等休息日
            workdaySet.removeAll(holidaySet);
            //再把加班的日子加进去
            if(!overTimeList.isEmpty()){
                workdaySet.addAll(overTimeList);
            }

            List<String> workdayList = new ArrayList<>(workdaySet);
            return workdayList;
        }

    }


    /**
     * 获取工作日的上下班时间
     * 根据工作开始时间和上下班时间计算当日的上下班时间
     * 
     * @param jobStartTime 工作开始时间
     * @param onDutyTime 上班时间
     * @param offDutyTime 下班时间
     * @return 返回包含上下班时间的DutyTimeResult对象
     * @throws ParseException 日期解析异常
     */
    public static DutyTimeResult getDutyTime(Date jobStartTime, Date onDutyTime, Date offDutyTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String jobStartStr = sdf.format(jobStartTime);
        String startDayStr = jobStartStr.split(" ")[0];

        //开始当日的上班时间
        String nDutyStr = sdf.format(onDutyTime);
        String onDutyTimeStr = nDutyStr.split(" ")[1];
        String startDayOnDutyTimeStr = startDayStr + " " + onDutyTimeStr;
        Date onDuty = sdf.parse(startDayOnDutyTimeStr);
        //开始当日的下班时间
        String offDutyStr = sdf.format(offDutyTime);
        String offDutyTimeStr = offDutyStr.split(" ")[1];
        String startDayOffDutyTimeStr = startDayStr + " " + offDutyTimeStr;
        Date offDuty = sdf.parse(startDayOffDutyTimeStr);
        DutyTimeResult startDayDutyTime = new DutyTimeResult(onDuty, offDuty);
        return startDayDutyTime;
    }

    public static class DutyTimeResult {
        public final Date onDutyTime;
        public final Date offDutyTime;

        /**
         * 构造上下班时间结果对象
         * 
         * @param onDutyTime 上班时间
         * @param offDutyTime 下班时间
         */
        public DutyTimeResult(Date onDutyTime, Date offDutyTime ) {
            this.onDutyTime = onDutyTime;
            this.offDutyTime = offDutyTime;
        }
    }

    /**
     * 调整日期到指定时间
     * 将日期字符串的时间部分调整为指定时间
     * 
     * @param dateStr 日期字符串
     * @param someTime 指定时间
     * @return 返回调整后的日期时间字符串
     * @throws ParseException 日期解析异常
     */
    public static String adjustToSomeTime(String dateStr, Date someTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);  //避免输入字符串错误
        String startAdjust;
        String temDateStr = sdf.format(date);
        String day = temDateStr.split(" ")[0];

        String onDutyStr = sdf.format(someTime);
        String hour = onDutyStr.split(" ")[1];
        startAdjust = day + " " + hour;  //一整天
        return startAdjust;
    }

    /**
     * 获取下一个工作日的上班时间
     * 计算工作开始时间后一天的上班时间
     * 
     * @param jobStartTime 工作开始时间
     * @param onDutyTime 上班时间
     * @return 返回下一个工作日的上班时间字符串
     */
    public static String nextDayOnDutyTime(Date jobStartTime, Date onDutyTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startAdjust;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(jobStartTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date temDate = calendar.getTime();
        String temDateStr = sdf.format(temDate);
        String day = temDateStr.split(" ")[0];

        String onDutyStr = sdf.format(onDutyTime);
        String hour = onDutyStr.split(" ")[1];
        startAdjust = day + " " + hour;  //一整天
        return startAdjust;
    }

    /**
     * 获取前一个工作日的下班时间
     * 计算工作结束时间前一天的下班时间
     * 
     * @param jobEndTime 工作结束时间
     * @param offDutyTime 下班时间
     * @return 返回前一个工作日的下班时间字符串
     */
    public static String prevDayOffDutyTime(Date jobEndTime, Date offDutyTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endAdjust;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(jobEndTime);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date temDate = calendar.getTime();
        String temDateStr = sdf.format(temDate);
        String day = temDateStr.split(" ")[0];

        String offDutyStr = sdf.format(offDutyTime);
        String hour = offDutyStr.split(" ")[1];
        endAdjust = day + " " + hour;  //一整天
        return endAdjust;
    }

    /**
     * 得到后一个工作日, 如果当前日期本来就是工作日, 得到的还是当天
     * @param dayTimeStr
     * @return
     * @throws ParseException
     */
    public static String getNextWorkDay(String dayTimeStr , Date onDutyTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dayTimeStr);
        int weekday = CalDateUtils.weekendDay(dayTimeStr);
        if(weekday == Calendar.SATURDAY){
            //如果是周六, 将日期加2
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            date = calendar.getTime();
            dayTimeStr = sdf.format(date);
            String dayTimeStrAdjust = CalDateUtils.adjustToSomeTime(dayTimeStr, onDutyTime);
            return dayTimeStrAdjust;
        }else if(weekday == Calendar.SUNDAY){
            //如果是周六, 将日期加2
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
            dayTimeStr = sdf.format(date);
            String dayTimeStrAdjust = CalDateUtils.adjustToSomeTime(dayTimeStr, onDutyTime);
            return dayTimeStrAdjust;
        }else{
            String dayTimeStrAdjust = CalDateUtils.adjustToSomeTime(dayTimeStr, onDutyTime);
            return dayTimeStrAdjust;
        }
    }

    /**
     * 得到前一个工作日, 如果当前日期本来就是工作日, 得到的还是当天
     * @param dayTimeStr
     * @return
     * @throws ParseException
     */
    public static String getPrevWorkDay(String dayTimeStr, Date offDutyTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dayTimeStr);
        int weekday = CalDateUtils.weekendDay(dayTimeStr);
        if(weekday == Calendar.SATURDAY){
            //如果是周六, 将日期加2
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            dayTimeStr = sdf.format(date);
            String dayTimeStrAdjust = CalDateUtils.adjustToSomeTime(dayTimeStr, offDutyTime);
            return dayTimeStrAdjust;
        }else if(weekday == Calendar.SUNDAY){
            //如果是周六, 将日期加2
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -2);
            date = calendar.getTime();
            dayTimeStr = sdf.format(date);
            String dayTimeStrAdjust = CalDateUtils.adjustToSomeTime(dayTimeStr, offDutyTime);
            return dayTimeStrAdjust;
        }else{
            String dayTimeStrAdjust = CalDateUtils.adjustToSomeTime(dayTimeStr, offDutyTime);
            return dayTimeStrAdjust;
        }
    }

}
