/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.config;

import lombok.Data;
import net.polyv.live.v1.config.LiveGlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Data
@Component
public class StartupListener implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(StartupListener.class);
    private String userId = "a610cb7ee8";
    private String appId = "f2e3xng6f1";
    private String appSecret = "7b96dc36f0f4469da783c9f8db71bce1";

    /**
     * 设置应用上下文
     * 在应用启动时初始化Polyv直播全局配置
     * 
     * @param applicationContext Spring应用上下文
     * @throws BeansException Bean异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LiveGlobalConfig.init(appId,userId,appSecret);
        LOG.info("--初始化完成--");
    }
}