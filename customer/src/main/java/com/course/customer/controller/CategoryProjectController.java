/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCategory;
import com.course.server.domain.RdmsCategoryProject;
import com.course.server.domain.RdmsProject;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCategoryProjectDto;
import com.course.server.dto.rdms.RdmsCategoryProjectDto;
import com.course.server.service.rdms.RdmsCategoryProjectService;
import com.course.server.service.rdms.RdmsProjectService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category-project")
public class CategoryProjectController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryProjectController.class);
    public static final String BUSINESS_NAME = "项目分类管理";

    @Resource
    private RdmsCategoryProjectService rdmsCategoryProjectService;
    @Autowired
    private RdmsProjectService rdmsProjectService;


    @PostMapping("/getCategoryList_all/{customerId}")
    public ResponseDto<List<RdmsCategoryProjectDto>> getCategoryList_all(@PathVariable String customerId) {
        ResponseDto<List<RdmsCategoryProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsCategoryProject> categoryListAll = rdmsCategoryProjectService.getCategoryList_all(customerId);
        List<RdmsCategoryProjectDto> categoryDtos = CopyUtil.copyList(categoryListAll, RdmsCategoryProjectDto.class);
        responseDto.setContent(categoryDtos);
        return responseDto;
    }

    @PostMapping("/getCategoryList_all_show/{customerId}")
    public ResponseDto<List<RdmsCategoryProjectDto>> getCategoryList_all_show(@PathVariable String customerId) {
        ResponseDto<List<RdmsCategoryProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsCategoryProject> categoryListAll = rdmsCategoryProjectService.getCategoryList_all_show(customerId);
        List<RdmsCategoryProjectDto> categoryDtos = CopyUtil.copyList(categoryListAll, RdmsCategoryProjectDto.class);
        responseDto.setContent(categoryDtos);
        return responseDto;
    }

    @PostMapping("/getCategoryList/{projectId}")
    public ResponseDto<List<String>> getCategoryList(@PathVariable String projectId) {
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        if(!ObjectUtils.isEmpty(rdmsProject)){
            List<String> strings = JSON.parseArray(rdmsProject.getCategoryIdListStr(), String.class);
            responseDto.setContent(strings);
        }else{
            responseDto.setContent(null);
        }
        return responseDto;
    }

    @PostMapping("/updateCategoryItem")
    @Transactional
    public ResponseDto<String> updateCategoryItem(@RequestBody RdmsCategoryProjectDto rdmsCategoryProjectDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsCategoryProjectDto.getParent(), "父级分类");
        ValidatorUtil.require(rdmsCategoryProjectDto.getName(), "分类名称");
        ValidatorUtil.require(rdmsCategoryProjectDto.getId(), "分类ID");

        RdmsCategoryProject categoryProject = CopyUtil.copy(rdmsCategoryProjectDto, RdmsCategoryProject.class);
        categoryProject.setIsDelete(0);
        String update = rdmsCategoryProjectService.update(categoryProject);
        responseDto.setContent(update);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");

        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsCategoryProjectDto> save(@RequestBody RdmsCategoryProjectDto rdmsCategoryProjectDto) {
        ResponseDto<RdmsCategoryProjectDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsCategoryProjectDto.getParent(), "父级分类");
        ValidatorUtil.require(rdmsCategoryProjectDto.getName(), "分类名称");

        RdmsCategoryProject categoryProject = CopyUtil.copy(rdmsCategoryProjectDto, RdmsCategoryProject.class);
        List<RdmsCategoryProject> rdmsCategoryProjects = rdmsCategoryProjectService.selectByCategoryName(rdmsCategoryProjectDto.getName());
        if(CollectionUtils.isEmpty(rdmsCategoryProjects)){
            if(!CollectionUtils.isEmpty(rdmsCategoryProjectDto.getAuthUserIds())){
                String authUserIdStr = JSON.toJSONString(rdmsCategoryProjectDto.getAuthUserIds());
                categoryProject.setAuthUserIdStr(authUserIdStr);
            }
            rdmsCategoryProjectService.save(categoryProject);
            responseDto.setContent(rdmsCategoryProjectDto);
            responseDto.setSuccess(true);
            responseDto.setMessage("保存成功");
        }else{
            responseDto.setContent(null);
            responseDto.setSuccess(false);
            responseDto.setMessage("分类名称已存在!");
        }

        return responseDto;
    }

    @PostMapping("/saveCategoryList")
    @Transactional
    public ResponseDto<String> saveCategoryList(@RequestParam String projectId, String categoryIdList) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectId, "项目ID");
        ValidatorUtil.require(categoryIdList, "分类ID");
        RdmsProject rdmsProject = new RdmsProject();
        rdmsProject.setId(projectId);
        rdmsProject.setCategoryIdListStr(categoryIdList);
        rdmsProjectService.updateByPrimaryKeySelective(rdmsProject);

        responseDto.setContent(projectId);
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
        rdmsCategoryProjectService.delete(id);
        return responseDto;
    }

}
