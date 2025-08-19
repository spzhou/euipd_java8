/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCategory;
import com.course.server.domain.RdmsCategoryFile;
import com.course.server.domain.RdmsFile;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCategoryDto;
import com.course.server.dto.rdms.RdmsCategoryFileDto;
import com.course.server.service.rdms.RdmsCategoryFileService;
import com.course.server.service.rdms.RdmsFileService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import net.sf.json.JSONString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category-file")
public class CategoryFileController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryFileController.class);
    public static final String BUSINESS_NAME = "文件分类管理";

    @Resource
    private RdmsCategoryFileService rdmsCategoryFileService;
    @Resource
    private RdmsFileService rdmsFileService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCategoryFileService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsCategoryFileDto> save(@RequestBody RdmsCategoryFileDto rdmsCategoryFileDto) {
        ResponseDto<RdmsCategoryFileDto> responseDto = new ResponseDto<>();

//        ValidatorUtil.require(rdmsCategoryFileDto.getCategoryIdList(), "分类ID");
        ValidatorUtil.require(rdmsCategoryFileDto.getFileId(), "文件ID");
        RdmsCategoryFile categoryFile = CopyUtil.copy(rdmsCategoryFileDto, RdmsCategoryFile.class);

        RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(rdmsCategoryFileDto.getFileId());
        String jsonString = JSON.toJSONString(rdmsCategoryFileDto.getCategoryIdList());
        categoryFile.setCategoryIdStr(jsonString);
        categoryFile.setWriter(rdmsFile.getOperatorId());
        categoryFile.setName(rdmsFile.getName());
        categoryFile.setProjectId(rdmsFile.getProjectId());
        categoryFile.setFileCreateTime(rdmsFile.getCreateTime());
        rdmsCategoryFileService.save(categoryFile);

        responseDto.setContent(rdmsCategoryFileDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getAllCategoryFilesByProjectId/{projectId}")
    public ResponseDto<List<RdmsCategoryFileDto>> getAllCategoryFilesByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsCategoryFileDto>> responseDto = new ResponseDto<>();
        List<RdmsCategoryFileDto> allCategoryFilesByProjectId = rdmsCategoryFileService.getAllCategoryFilesByProjectId(projectId);
        responseDto.setContent(allCategoryFilesByProjectId);
        return responseDto;
    }

    @PostMapping("/getCategoryFileSimpleInfo/{fileId}")
    public ResponseDto<RdmsCategoryFileDto> getCategoryFileSimpleInfo(@PathVariable String fileId) {
        ResponseDto<RdmsCategoryFileDto> responseDto = new ResponseDto<>();
        RdmsCategoryFile rdmsCategoryFile = rdmsCategoryFileService.selectByFileId(fileId);
        RdmsCategoryFileDto copy = CopyUtil.copy(rdmsCategoryFile, RdmsCategoryFileDto.class);
        responseDto.setContent(copy);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCategoryFileService.delete(id);
        return responseDto;
    }

}
