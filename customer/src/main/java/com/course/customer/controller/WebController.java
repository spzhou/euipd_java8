/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCourseSection;
import com.course.server.dto.RdmsCoursePageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCourseDto;
import com.course.server.dto.rdms.RdmsSectionDto;
import com.course.server.service.web.WebService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/web")
public class WebController {
    private static final Logger LOG = LoggerFactory.getLogger(WebController.class);
    public static final String BUSINESS_NAME = "WEB";

    @Resource
    private WebService webService;


    @PostMapping("/getCourseDetailInfo/{courseId}")
    public ResponseDto<RdmsCourseDto> getCourseDetailInfo(@PathVariable String courseId) {
        // 保存校验
        ValidatorUtil.require(courseId, "课程ID");
        ResponseDto<RdmsCourseDto> responseDto = new ResponseDto<>();
        RdmsCourseDto courseDetail = webService.getCourseDetail(courseId);
        responseDto.setContent(courseDetail);
        return responseDto;
    }

    @PostMapping("/getSectionInfo/{sectionId}")
    public ResponseDto<RdmsSectionDto> getSectionInfo(@PathVariable String sectionId) {
        // 保存校验
        ValidatorUtil.require(sectionId, "小节ID");
        ResponseDto<RdmsSectionDto> responseDto = new ResponseDto<>();
        RdmsCourseSection sectionDetail = webService.getSectionDetail(sectionId);
        responseDto.setContent(CopyUtil.copy(sectionDetail, RdmsSectionDto.class));
        return responseDto;
    }

    @PostMapping("/course-list")
    public ResponseDto<RdmsCoursePageDto> courseList(@RequestBody RdmsCoursePageDto pageDto) {
        ResponseDto<RdmsCoursePageDto> responseDto = new ResponseDto<>();
        // 调用外部服务获取课程列表
        webService.getCourseList(pageDto);
        responseDto.setContent(pageDto);

        // 设置成功状态码
        responseDto.setSuccess(true);
        responseDto.setCode("200");
        responseDto.setMessage("Success");

        return responseDto;
    }


}
