/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsJobItem;
import com.course.server.domain.RdmsJobItemProcess;
import com.course.server.domain.RdmsProjectTask;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsProjectTaskDto;
import com.course.server.enums.rdms.*;
import com.course.server.service.rdms.RdmsCustomerUserJobLevelService;
import com.course.server.service.rdms.RdmsJobItemService;
import com.course.server.service.rdms.RdmsProjectTaskService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project-task")
public class ProjectTaskController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectTaskController.class);
    public static final String BUSINESS_NAME = "项目任务";

    @Resource
    private RdmsProjectTaskService rdmsProjectTaskService;


    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsProjectTaskDto>> list(@RequestBody PageDto<RdmsProjectTaskDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectTaskDto>> responseDto = new ResponseDto<>();
        rdmsProjectTaskService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsProjectTaskDto> save(@RequestBody RdmsProjectTaskDto projectTaskDto) {
        ResponseDto<RdmsProjectTaskDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectTaskDto.getCustomerId(), "客户ID");

        ValidatorUtil.length(projectTaskDto.getCustomerId(), "需求名称", 8, 8);

        RdmsProjectTask rdmsProjectTask = CopyUtil.copy(projectTaskDto, RdmsProjectTask.class);
        rdmsProjectTaskService.save(rdmsProjectTask);

        responseDto.setContent(projectTaskDto);
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
        rdmsProjectTaskService.delete(id);
        return responseDto;
    }

}
