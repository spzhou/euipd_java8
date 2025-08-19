/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCharacterProcess;
import com.course.server.domain.RdmsCharacter;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCharacterProcessDto;
import com.course.server.dto.rdms.RdmsHmiCharacterProcessDto;
import com.course.server.service.rdms.RdmsCharacterProcessService;
import com.course.server.service.rdms.RdmsCharacterService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/character-process")
public class CharacterProcessController {
    private static final Logger LOG = LoggerFactory.getLogger(CharacterProcessController.class);
    public static final String BUSINESS_NAME = "特性处理";

    @Resource
    private RdmsCharacterProcessService rdmsCharacterProcessService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;


    /**
     * 保存客户信息
     */
    @PostMapping("/submit")
    public ResponseDto<RdmsCharacterProcessDto> submit(@RequestBody RdmsCharacterProcessDto characterProcessDto) {
        ResponseDto<RdmsCharacterProcessDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterProcessDto.getCharacterId(), "特性ID");
        ValidatorUtil.require(characterProcessDto.getExecutorId(), "执行者ID");
        ValidatorUtil.require(characterProcessDto.getJobDescription(), "工作描述");
        ValidatorUtil.require(characterProcessDto.getDeep(), "执行深度");

        RdmsCharacterProcess rdmsCharacterProcess = CopyUtil.copy(characterProcessDto, RdmsCharacterProcess.class);
        rdmsCharacterProcessService.submit(rdmsCharacterProcess);

        responseDto.setContent(characterProcessDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


    @PostMapping("/reject")
    public ResponseDto<RdmsCharacterProcessDto> reject(@RequestBody RdmsCharacterProcessDto characterProcessDto) {
        ResponseDto<RdmsCharacterProcessDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterProcessDto.getCharacterId(), "特性ID");
        ValidatorUtil.require(characterProcessDto.getExecutorId(), "执行者ID");
        ValidatorUtil.require(characterProcessDto.getJobDescription(), "工作描述");
        ValidatorUtil.require(characterProcessDto.getDeep(), "执行深度");
//        ValidatorUtil.require(characterProcessDto.getPlanSubmissionTime(), "计划提交时间");

        RdmsCharacterProcess rdmsCharacterProcess = CopyUtil.copy(characterProcessDto, RdmsCharacterProcess.class);
        rdmsCharacterProcessService.reject(rdmsCharacterProcess);

        responseDto.setContent(characterProcessDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/list-by-characterId/{id}")
    public ResponseDto<List<RdmsCharacterProcessDto>> listByCharacterId(@PathVariable String id){
        ResponseDto<List<RdmsCharacterProcessDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterProcessDto> rdmsCharacterProcessDtos = rdmsCharacterProcessService.listByCharacterId(id);
        responseDto.setContent(rdmsCharacterProcessDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/listProcessAllInfoByCharacterId/{id}")
    public ResponseDto<RdmsHmiCharacterProcessDto> listProcessAllInfoByCharacterId(@PathVariable String id){
        ResponseDto<RdmsHmiCharacterProcessDto> responseDto = new ResponseDto<>();
        RdmsHmiCharacterProcessDto hmiCharacterProcessDto = rdmsCharacterProcessService.listProcessAllInfoByCharacterId(id);
        responseDto.setContent(hmiCharacterProcessDto);
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
        rdmsCharacterProcessService.delete(id);
        return responseDto;
    }

}
