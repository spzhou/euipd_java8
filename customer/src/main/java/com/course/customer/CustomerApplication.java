/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.course")
@MapperScan("com.course.server.mapper")
public class CustomerApplication {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CustomerApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		//读取配置变量函数  env.getProperty() 还可以通过@Value 读取配置变量
		LOG.info("Customer地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}

}
