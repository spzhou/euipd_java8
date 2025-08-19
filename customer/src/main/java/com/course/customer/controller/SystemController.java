/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.*;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsFileDto;
import com.course.server.dto.rdms.RdmsSystemDto;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system")
public class SystemController {
    private static final Logger LOG = LoggerFactory.getLogger(SystemController.class);
    public static final String SYSTEM_NAME = "系统设置";

    @Resource
    private RdmsSystemService rdmsSystemService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsFileService rdmsFileService;

    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsSystemDto> save(@RequestBody RdmsSystemDto systemDto) {
        ResponseDto<RdmsSystemDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(systemDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(systemDto.getId(), "质量ID");

        RdmsSystem rdmsSystem = CopyUtil.copy(systemDto, RdmsSystem.class);
        rdmsSystemService.save(rdmsSystem);

        responseDto.setContent(systemDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getSystemSimpleInfo/{systemId}")
    @Transactional
    public ResponseDto<RdmsSystemDto> getSystemSimpleInfo(@PathVariable String systemId) {
        ResponseDto<RdmsSystemDto> responseDto = new ResponseDto<>();
        if(!systemId.isEmpty()){
            RdmsSystem rdmsSystem = rdmsSystemService.selectByPrimaryKey(systemId);
            RdmsSystemDto systemDto = CopyUtil.copy(rdmsSystem, RdmsSystemDto.class);
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(systemDto.getCustomerId());
            systemDto.setCustomerName(rdmsCustomer.getCustomerName());
            responseDto.setContent(systemDto);
            responseDto.setMessage("保存成功");
        }else{
            responseDto.setContent(null);
            responseDto.setMessage("记录不存在");
        }
        return responseDto;
    }

    @PostMapping("/getUserImportTemplateInfo/{systemId}")
    @Transactional
    public ResponseDto<RdmsFileDto> getUserImportTemplateInfo(@PathVariable String systemId) {
        ResponseDto<RdmsFileDto> responseDto = new ResponseDto<>();
        if(!systemId.isEmpty()){
            RdmsSystem rdmsSystem = rdmsSystemService.selectByPrimaryKey(systemId);
            RdmsFileDto fileSimpleRecordInfo = rdmsFileService.getFileSimpleRecordInfo(rdmsSystem.getUserImportTemplateId());
            responseDto.setContent(fileSimpleRecordInfo);
            responseDto.setMessage("保存成功");
        }else{
            responseDto.setContent(null);
            responseDto.setMessage("记录不存在");
        }
        return responseDto;
    }



}
