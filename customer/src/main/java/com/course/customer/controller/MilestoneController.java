/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.*;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.ProjectStatusEnum;
import com.course.server.mapper.RdmsMilestoneMapper;
import com.course.server.service.rdms.RdmsCustomerUserService;
import com.course.server.service.rdms.RdmsMilestoneService;
import com.course.server.service.rdms.RdmsProjectService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/milestone")
public class MilestoneController {
    private static final Logger LOG = LoggerFactory.getLogger(MilestoneController.class);
    public static final String BUSINESS_NAME = "里程碑";

    @Resource
    private RdmsMilestoneService rdmsMilestoneService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsMilestoneMapper rdmsMilestoneMapper;

    /**
     * 列表查询
     */
    @PostMapping("/listByProjectId/{projectId}")
    public ResponseDto<List<RdmsMilestoneDto>> list(@PathVariable String projectId) {
        ResponseDto<List<RdmsMilestoneDto>> responseDto = new ResponseDto<>();
        List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(projectId);
        responseDto.setContent(rdmsMilestoneDtos);
        return responseDto;
    }

    @PostMapping("/customer-milestone-list")
    public ResponseDto<PageDto<RdmsMilestoneDto>> listSetupedProjectMilestonesByCustomerId(@RequestBody PageDto<RdmsMilestoneDto> pageDto) {
        ResponseDto<PageDto<RdmsMilestoneDto>> responseDto = new ResponseDto<>();
        rdmsMilestoneService.listMilestones(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/sendMilestoneTask")
    public ResponseDto<Integer> sendMilestoneTask(@RequestBody RdmsJobItemDto jobItemsDto) throws ParseException {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        Integer i = rdmsMilestoneService.sendMilestoneTask(jobItemsDto);
        responseDto.setContent(i);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsMilestoneDto> save(@RequestBody RdmsMilestoneDto milestoneDto) {
        ResponseDto<RdmsMilestoneDto> responseDto = new ResponseDto<>();
        ValidatorUtil.require(milestoneDto.getProjectId(), "项目ID");
        ValidatorUtil.require(milestoneDto.getMilestoneName(), "里程碑名称");
        ValidatorUtil.require(milestoneDto.getCheckOut(), "验收指标");
        ValidatorUtil.require(milestoneDto.getReviewTime(), "评审时间");

        ValidatorUtil.length(milestoneDto.getProjectId(), "项目ID", 8, 8);
        ValidatorUtil.length(milestoneDto.getMilestoneName(), "里程碑名称", 2, 50);
        ValidatorUtil.length(milestoneDto.getCheckOut(), "验收指标", 6, 500);

        rdmsMilestoneService.save(milestoneDto);

        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/edit")
    @Transactional
    public ResponseDto<RdmsMilestoneDto> edit(@RequestBody RdmsMilestoneDto milestoneDto) {
        ResponseDto<RdmsMilestoneDto> responseDto = new ResponseDto<>();
        rdmsMilestoneService.edit(milestoneDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("修改成功");
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsMilestoneService.delete(id);
        responseDto.setSuccess(true);
        return responseDto;
    }

}
