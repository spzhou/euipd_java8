/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCategory;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCategoryDto;
import com.course.server.service.rdms.RdmsCategoryService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "分类管理";

    @Resource
    private RdmsCategoryService rdmsCategoryService;


    @PostMapping("/getCategoryList_all_plus/{customerId}")
    public ResponseDto<List<RdmsCategoryDto>> getCategoryList_all_plus(@PathVariable String customerId) {
        ResponseDto<List<RdmsCategoryDto>> responseDto = new ResponseDto<>();
        List<RdmsCategory> categoryListAll = rdmsCategoryService.getCategoryList_all_plus(customerId);
        List<RdmsCategoryDto> categoryDtos = CopyUtil.copyList(categoryListAll, RdmsCategoryDto.class);
        if(!CollectionUtils.isEmpty(categoryDtos)){
            for(RdmsCategoryDto categoryDto: categoryDtos){
                List<String> strings1 = JSON.parseArray(categoryDto.getAuthUserIdStr(), String.class);
                categoryDto.setAuthUserIds(strings1);
            }
        }
        responseDto.setContent(categoryDtos);
        return responseDto;
    }

    @PostMapping("/getCategoryList_all/{customerId}")
    public ResponseDto<List<RdmsCategoryDto>> getCategoryList_all(@PathVariable String customerId) {
        ResponseDto<List<RdmsCategoryDto>> responseDto = new ResponseDto<>();
        List<RdmsCategory> categoryListAll = rdmsCategoryService.getCategoryList_all(customerId);
        List<RdmsCategoryDto> categoryDtos = CopyUtil.copyList(categoryListAll, RdmsCategoryDto.class);
        responseDto.setContent(categoryDtos);
        return responseDto;
    }

    @PostMapping("/getCategoryList_open")
    public ResponseDto<List<RdmsCategoryDto>> getCategoryList_open(@RequestParam String customerId, String loginUserId) {
        ResponseDto<List<RdmsCategoryDto>> responseDto = new ResponseDto<>();
        List<RdmsCategory> categoryListAll = rdmsCategoryService.getCategoryList_open(customerId);
        List<RdmsCategoryDto> categoryDtos = CopyUtil.copyList(categoryListAll, RdmsCategoryDto.class);
        List<RdmsCategoryDto> categoryDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(categoryDtos)){
            for(RdmsCategoryDto categoryDto: categoryDtos){
                if(!ObjectUtils.isEmpty(categoryDto)){
                    List<String> userIdList = JSON.parseArray(categoryDto.getAuthUserIdStr(), String.class);
                    if(!ObjectUtils.isEmpty(userIdList) && userIdList.contains(loginUserId)){
                        categoryDtoList.add(categoryDto);
                    }
                }
            }
        }
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }
    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsCategoryDto> save(@RequestBody RdmsCategoryDto rdmsCategoryDto) {
        ResponseDto<RdmsCategoryDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsCategoryDto.getParent(), "父级分类");
        ValidatorUtil.require(rdmsCategoryDto.getName(), "分类名称");
//        ValidatorUtil.require(rdmsCategoryDto.getSort(), "分类序号");

        RdmsCategory category = CopyUtil.copy(rdmsCategoryDto, RdmsCategory.class);
        List<RdmsCategory> rdmsCategories = rdmsCategoryService.selectByCategoryName(rdmsCategoryDto.getName());
        if(CollectionUtils.isEmpty(rdmsCategories)){
            if(!CollectionUtils.isEmpty(rdmsCategoryDto.getAuthUserIds())){
                String authUserIdStr = JSON.toJSONString(rdmsCategoryDto.getAuthUserIds());
                category.setAuthUserIdStr(authUserIdStr);
            }
            rdmsCategoryService.save(category);
            responseDto.setContent(rdmsCategoryDto);
            responseDto.setSuccess(true);
            responseDto.setMessage("保存成功");
        }else{
            responseDto.setContent(null);
            responseDto.setSuccess(false);
            responseDto.setMessage("分类名称已存在!");
        }

        return responseDto;
    }

    @PostMapping("/updateCategoryItem")
    @Transactional
    public ResponseDto<RdmsCategoryDto> updateCategoryItem(@RequestBody RdmsCategoryDto rdmsCategoryDto) {
        ResponseDto<RdmsCategoryDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsCategoryDto.getParent(), "父级分类");
        ValidatorUtil.require(rdmsCategoryDto.getName(), "分类名称");
        ValidatorUtil.require(rdmsCategoryDto.getId(), "分类ID");

        RdmsCategory category = CopyUtil.copy(rdmsCategoryDto, RdmsCategory.class);
        if(!CollectionUtils.isEmpty(rdmsCategoryDto.getAuthUserIds())){
            String authUserIdStr = JSON.toJSONString(rdmsCategoryDto.getAuthUserIds());
            category.setAuthUserIdStr(authUserIdStr);
        }
        rdmsCategoryService.update(category);
        responseDto.setContent(rdmsCategoryDto);
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
        rdmsCategoryService.delete(id);
        return responseDto;
    }

}
