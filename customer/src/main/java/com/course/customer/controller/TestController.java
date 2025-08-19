/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
    public static final String BUSINESS_NAME = "客户";

    /**
     * 测试接口
     * 用于测试Customer模块是否正常启动
     * 
     * @return 返回测试消息
     */
    @GetMapping("/test")
    public ResponseDto list(){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent("Customer模块已启动!");
        return responseDto;
    }
}
