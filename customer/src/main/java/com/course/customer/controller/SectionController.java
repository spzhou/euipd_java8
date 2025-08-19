/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCourse;
import com.course.server.dto.RdmsChapterDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.dto.rdms.RdmsCourseDto;
import com.course.server.dto.rdms.RdmsSectionDto;
import com.course.server.enums.CourseChargeEnum;
import com.course.server.service.RdmsChapterService;
import com.course.server.service.RdmsCourseService;
import com.course.server.service.RdmsSectionService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public static final String BUSINESS_NAME = "小节";

    @Resource
    private RdmsSectionService sectionService;
    @Resource
    private RdmsCourseService courseService;
    @Autowired
    private RdmsChapterService rdmsChapterService;
    @Autowired
    private RdmsCourseService rdmsCourseService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody SectionPageDto sectionPageDto) {
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(sectionPageDto.getChapterId(), "篇章ID");
        sectionService.list(sectionPageDto);
        responseDto.setContent(sectionPageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto save(@RequestBody RdmsSectionDto sectionDto) throws Exception {
        // 保存校验
        ValidatorUtil.require(sectionDto.getChapterId(), "篇章");
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.require(sectionDto.getVideo(), "视频");
        ValidatorUtil.require(sectionDto.getVod(), "VideoId");
        ValidatorUtil.require(sectionDto.getTime(), "时长");
        ValidatorUtil.length( sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length( sectionDto.getVideo(), "视频", 1, 500);
        ValidatorUtil.length( sectionDto.getVod(), "VideoId", 1, 100);

        ResponseDto responseDto = new ResponseDto();

        RdmsChapterDto chapterInfo = rdmsChapterService.getChapterInfo(sectionDto.getChapterId());
        if(chapterInfo!=null){
            RdmsCourseDto course = rdmsCourseService.getCourse(chapterInfo.getCourseId());
            if(course!=null){
                sectionDto.setCourseId(course.getId());
            }else{
                LOG.error("找不到该课程");
                throw new RuntimeException("找不到该课程");
            }
        }else{
            LOG.error("找不到该课程");
            throw new RuntimeException("找不到该课程");
        }

        sectionDto.setCreatedAt(new Date());
        SectionSaveAndChargeInfoHandle(sectionDto, responseDto);
        return responseDto;
    }

    private void SectionSaveAndChargeInfoHandle(RdmsSectionDto sectionDto, ResponseDto responseDto) throws Exception {
        //如果小节是收费的,课程是免费的,则将课程改为收费的
        RdmsCourseDto courseDto = courseService.getCourse(sectionDto.getCourseId());
        if(sectionDto.getCharge()!=null && courseDto.getCharge()!=null){
            if(sectionDto.getCharge().equals(CourseChargeEnum.CHARGE.getCode())){
                if(!courseDto.getCharge().equals(CourseChargeEnum.CHARGE.getCode())){
                    courseDto.setCharge(CourseChargeEnum.CHARGE.getCode());
                    RdmsCourse course = CopyUtil.copy(courseDto, RdmsCourse.class);
                    courseService.update(course);
                }
            }else{
                //如果所有小节都是免费的, 则课程是免费的
                List<RdmsSectionDto> sectionDtoList = sectionService.listByCourse(courseDto.getId());
                Boolean chargeFlag = true;
                for(RdmsSectionDto sectionDto1 : sectionDtoList){
                    if(sectionDto1.getCharge().equals(CourseChargeEnum.CHARGE.getCode())){
                        chargeFlag = false;
                        break;
                    }
                }
                if(chargeFlag){
                    courseDto.setCharge(CourseChargeEnum.FREE.getCode());
                    RdmsCourse course = CopyUtil.copy(courseDto, RdmsCourse.class);
                    courseService.update(course);
                }
            }
        }else{
            sectionDto.setCharge(CourseChargeEnum.FREE.getCode());
        }

        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        sectionService.delete(id);
        return responseDto;
    }
}
