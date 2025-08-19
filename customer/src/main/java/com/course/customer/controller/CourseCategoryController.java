/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCourseCategory;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCourseCategoryDto;
import com.course.server.service.RdmsCourseCategoryService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course/category")
public class CourseCategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "分类";

    @Resource
    private RdmsCourseCategoryService rdmsCourseCategoryService;

    /**
     * 列表查询
     */
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsCourseCategoryDto> categoryDtoList = rdmsCourseCategoryService.all();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }

    @PostMapping("/getCategoryById/{categoryId}")
    public ResponseDto getCategoryById(@PathVariable String categoryId) {
        ResponseDto responseDto = new ResponseDto();
        RdmsCourseCategory category = rdmsCourseCategoryService.selectByPrimaryKey(categoryId);
        responseDto.setContent(category);
        return responseDto;
    }

    @PostMapping("/getLevel1List")
    public ResponseDto getLevel1List() {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsCourseCategory> level1List = rdmsCourseCategoryService.getLevel1List();
        responseDto.setContent(level1List);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCourseCategoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RdmsCourseCategoryDto categoryDto) {
        // 保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);

        ResponseDto responseDto = new ResponseDto();
        rdmsCourseCategoryService.save(categoryDto);
        responseDto.setContent(categoryDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCourseCategoryService.delete(id);
        return responseDto;
    }
}
