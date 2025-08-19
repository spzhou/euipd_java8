/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer;

import com.course.server.service.rdms.RdmsJobItemGanttService;
import com.course.server.service.rdms.RdmsPerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("timerTaskJob")
public class TimerTaskJob {
    private static final Logger LOG = LoggerFactory.getLogger(TimerTaskJob.class);
    public static final String BUSINESS_NAME = "定时任务";

    @Resource
    private RdmsPerformanceService rdmsPerformanceService;
    @Resource
    private RedisTemplate redisTemplate;

//    private static final String CUSTOMER_ID = "gXi38Sc7";  //测试用ID
    private static final String CUSTOMER_ID = "P93BKcxQ";  //瑞柯恩的ID

    @Autowired
    private RdmsJobItemGanttService rdmsJobItemGanttService;

    /**
     * 如果是在Spring Boot项目中，需要在启动类上添加@EnableScheduling来开启定时任务
     * fixedRate有一个时刻表的概念，在任务启动时，T1、T2、T3就已经排好了执行的时刻，比如1分、2分、3分，当T1的执行时间大于1分钟时，就会造成T2晚点，当T1执行完时T2立即执行
     */
    /**
     * 定时任务3：月度账户统计
     * 每2小时执行一次，计算并更新月度账户统计信息
     * 
     * @throws ParseException 解析异常
     */
    @Scheduled(fixedDelay = 2 * 60 * 60 * 1000L)
    public void job3() throws ParseException {
        rdmsPerformanceService.monthAccount();
    }

    /**
     * 定时任务5：每日汇总数据更新
     * 每天零点零一分执行，计算大屏幕展示的汇总信息并缓存到Redis
     * 
     * @throws ParseException 解析异常
     */
    @Scheduled(cron = "0 1 0 * * ?")
    public void job5() throws ParseException {
        LOG.info("job5定时任务被执行 - 每天零点零一分");
        //计算大屏幕展示的汇总信息
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期格式化为字符串
        String token = CUSTOMER_ID+"-"+currentDate.format(formatter);
        String radisData = (String)redisTemplate.opsForValue().get(token);
        if(ObjectUtils.isEmpty(radisData)){
            rdmsPerformanceService.getCustomerSummaryData_toRedis(CUSTOMER_ID, token);
        }
    }

    /**
     * 定时任务6：计划任务下发
     * 每天8:30执行，检查计划任务中是否有今天下发的任务，如果有则下发这些计划任务
     * 
     * @throws ParseException 解析异常
     */
    @Scheduled(cron = "0 30 8 * * ?")
    public void job6() throws ParseException {
        LOG.info("job6定时任务被执行 - 每天8:30. customerId:{}", CUSTOMER_ID);
        //观察计划任务中是否有今天下发的任务,如果有的话就讲这些计划任务下发下去
        rdmsJobItemGanttService.issuePlanTask(CUSTOMER_ID);
    }

    //定时任务将按照 cron 表达式 0 0 0 * * ? 在每天零点执行
/*    @Scheduled(cron = "0 20 12 * * ?")
    public void job7() throws ParseException {
        LOG.info("job7定时任务被执行 - 每天12:20");
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期格式化为字符串
        String token = CUSTOMER_ID+"-"+currentDate.format(formatter);
        String radisData = (String)redisTemplate.opsForValue().get(token);
        if(ObjectUtils.isEmpty(radisData)){
            rdmsPerformanceService.getCustomerSummaryData_toRedis(CUSTOMER_ID, token);
        }
    }*/

}

