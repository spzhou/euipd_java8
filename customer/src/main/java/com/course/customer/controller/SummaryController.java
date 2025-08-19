/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.service.rdms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/summary")
public class SummaryController {
    private static final Logger LOG = LoggerFactory.getLogger(SummaryController.class);
    public static final String BUSINESS_NAME = "汇总数据";

    @Autowired
    private RdmsPerformanceService rdmsPerformanceService;
    @Autowired
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/listProjectInfo")
    public ResponseDto<PageDto<ProjectSummaryDto>> listProjectInfo(@RequestBody PageDto<ProjectSummaryDto> pageDto) throws ParseException {
        ResponseDto<PageDto<ProjectSummaryDto>> responseDto = new ResponseDto<>();
        List<ProjectSummaryDto> projectSummaryList = new ArrayList<>();
        rdmsProjectService.listProject(pageDto);

        for(ProjectSummaryDto projectSummary: pageDto.getList()) {
            ProjectSummaryDto summaryDto = rdmsPerformanceService.getProjectSummaryInfo(projectSummary.getId());
            projectSummaryList.add(summaryDto);
        }
        pageDto.setList(projectSummaryList);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getCustomerSummaryInfo/{customerId}")
    public ResponseDto<CustomerSummaryDto> getCustomerSummaryInfo(@PathVariable String customerId) throws ParseException {
        ResponseDto<CustomerSummaryDto> responseDto = new ResponseDto<>();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期格式化为字符串
        String formattedDate = customerId+"-"+currentDate.format(formatter);
        try {
            String radisData = (String)redisTemplate.opsForValue().get(formattedDate);
            if (radisData != null) {
                CustomerSummaryDto customerSummaryDto1 = JSON.parseObject(radisData, CustomerSummaryDto.class);
                if (!ObjectUtils.isEmpty(customerSummaryDto1)) {
                    responseDto.setContent(customerSummaryDto1);
                    return responseDto;
                }
            }
        } catch (Exception e) {
            // 处理 Redis 连接失败的情况
            throw new RuntimeException("Failed to connect to Redis", e);
        }

        return responseDto;
    }


}
