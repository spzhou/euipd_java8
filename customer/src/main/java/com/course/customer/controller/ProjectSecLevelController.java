/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsProjectSecLevel;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsProjectSecLevelDto;
import com.course.server.service.rdms.RdmsProjectSecLevelService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/project-sec-level")
public class ProjectSecLevelController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectSecLevelController.class);
    public static final String BUSINESS_NAME = "保密等级";

    @Resource
    private RdmsProjectSecLevelService rdmsProjectSecLevelService;

    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsProjectSecLevelDto> save(@RequestBody RdmsProjectSecLevelDto projectSecLevelDto) {
        ResponseDto<RdmsProjectSecLevelDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSecLevelDto.getProjectId(), "项目ID");
        ValidatorUtil.require(projectSecLevelDto.getSubprojectId(), "子项目ID");
        ValidatorUtil.require(projectSecLevelDto.getLevel(), "保密级别");

        RdmsProjectSecLevel projectSecLevel = CopyUtil.copy(projectSecLevelDto, RdmsProjectSecLevel.class);
        rdmsProjectSecLevelService.saveBySubprojectId(projectSecLevel);

        responseDto.setContent(projectSecLevelDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/get-by-projectId/{projectId}")
    @Transactional
    public ResponseDto<List<RdmsProjectSecLevel>> getByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsProjectSecLevel>> responseDto = new ResponseDto<>();
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getByProjectId(projectId);
        responseDto.setContent(rdmsProjectSecLevels);
        responseDto.setSuccess(true);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsProjectSecLevelService.delete(id);
        return responseDto;
    }

}
