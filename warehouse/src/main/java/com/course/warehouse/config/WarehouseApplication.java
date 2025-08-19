/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.warehouse.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * Author: 周朔鹏
 * Date: 2025-08-18
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.course")
@MapperScan("com.course.server.mapper")
public class WarehouseApplication {

	private static final Logger LOG = LoggerFactory.getLogger(WarehouseApplication.class);

	/**
	 * 应用程序入口
	 * 启动仓库管理服务
	 * 
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WarehouseApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("Warehouse地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}

}
