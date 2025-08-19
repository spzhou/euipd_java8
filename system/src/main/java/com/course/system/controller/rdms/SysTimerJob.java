/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//启动定时任务, 需要再SystemApplication中添加@EnableScheduling注解

@Component("SysTimerTaskJob")
public class SysTimerJob {
    private static final Logger LOG = LoggerFactory.getLogger(SysTimerJob.class);
    public static final String BUSINESS_NAME = "系统定时任务";

    //帮我写一个定时任务的执行函数
//    @Scheduled(cron = "0 10 0 * * ?")
    @Scheduled(fixedDelay = 60 * 1000L)
    public void job1() throws Exception {
        LOG.info("每分钟执行一次");
        //获取当前时间
        //获取当前时间
        java.util.Date now = new java.util.Date();
        //格式化当前时间
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //输出当前时间
        LOG.info("当前时间：" + dateFormat.format(now));
    }

}
