/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginAdminGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    LoginAdminGatewayFilter loginAdminGatewayFilter;

    /**
     * 应用网关过滤器
     * 创建并返回登录验证网关过滤器实例
     * 
     * @param config 过滤器配置对象
     * @return 返回登录验证网关过滤器
     */
    @Override
    public GatewayFilter apply(Object config) {
        return loginAdminGatewayFilter;
    }
}