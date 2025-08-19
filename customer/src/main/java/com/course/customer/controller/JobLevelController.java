/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCustomerUserJobLevel;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCustomerUserJobLevelDto;
import com.course.server.service.rdms.RdmsCustomerUserJobLevelService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/job-level")
public class JobLevelController {
    private static final Logger LOG = LoggerFactory.getLogger(JobLevelController.class);
    public static final String BUSINESS_NAME = "职级管理";

    @Resource
    private RdmsCustomerUserJobLevelService rdmsJobLevelService;


    @PostMapping("/list-all/{customerId}")
    public ResponseDto<List<RdmsCustomerUserJobLevelDto>> listAll(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUserJobLevelDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUserJobLevelDto> rdmsCustomerUserJobLevelDtos = rdmsJobLevelService.listAll(customerId);
        responseDto.setContent(rdmsCustomerUserJobLevelDtos);
        return responseDto;
    }

    @PostMapping("/glance")
    public ResponseDto<Integer> glance(@RequestBody RdmsCustomerUserJobLevelDto jobLevelDto) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        int num = rdmsJobLevelService.glanceByLevelCode(jobLevelDto);
        responseDto.setContent(num);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsCustomerUserJobLevelDto> save(@RequestBody RdmsCustomerUserJobLevelDto jobLevelDto) {
        ResponseDto<RdmsCustomerUserJobLevelDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(jobLevelDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(jobLevelDto.getLevelCode(), "职级代码");
        ValidatorUtil.require(jobLevelDto.getManHourFee(), "工时费率");

        ValidatorUtil.length(jobLevelDto.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(jobLevelDto.getLevelCode(), "职级代码", 2, 4);
        ValidatorUtil.length(jobLevelDto.getManHourFee().toString(), "工时费率", 0, 10);

        RdmsCustomerUserJobLevel customerUserJobLevel = CopyUtil.copy(jobLevelDto, RdmsCustomerUserJobLevel.class);
        rdmsJobLevelService.save(customerUserJobLevel);

        responseDto.setContent(jobLevelDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsJobLevelService.delete(id);
        return responseDto;
    }

}
