/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.dto.*;
import com.course.server.dto.rdms.RdmsTeacherDto;
import com.course.server.service.RdmsTeacherCategoryService;
import com.course.server.service.RdmsTeacherService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course/teacher")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);
    public static final String BUSINESS_NAME = "讲师";

    @Resource
    private RdmsTeacherService rdmsTeacherService;
    @Resource
    private RdmsTeacherCategoryService rdmsTeacherCategoryService;


    /**
     * 列表查询
     */
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsTeacherDto> teacherDtoList = rdmsTeacherService.all();
        responseDto.setContent(teacherDtoList);
        return responseDto;
    }

    @PostMapping("/my-teacher/{customerId}")
    public ResponseDto myTeacher(@PathVariable String customerId) {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsTeacherDto> teacherDtoList = rdmsTeacherService.myTeacher(customerId);
        responseDto.setContent(teacherDtoList);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody RdmsTeacherPageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsTeacherService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RdmsTeacherDto teacherDto) {
        // 保存校验
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
//        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
//        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
//        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
//        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 300);
//        ValidatorUtil.length(teacherDto.getInstitution(), "机构简称", 1, 20);
        ValidatorUtil.length(teacherDto.getCustomerId(), "机构ID", 8, 8);  //创建人的机构ID 不一定是讲师的所在机构

        ResponseDto responseDto = new ResponseDto();
        rdmsTeacherService.save(teacherDto);
        responseDto.setContent(teacherDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsTeacherService.delete(id);
        return responseDto;
    }

    @PostMapping("/list-category/{teacherId}")
    public ResponseDto listCategory(@PathVariable String teacherId) {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsTeacherCategoryDto> dtoList = rdmsTeacherCategoryService.listByTeacher(teacherId);
        responseDto.setContent(dtoList);
        return responseDto;
    }
}
