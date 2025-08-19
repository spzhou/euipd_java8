/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.web;

import com.course.server.domain.RdmsCourseSection;
import com.course.server.dto.RdmsCoursePageDto;
import com.course.server.dto.RdmsChapterDto;
import com.course.server.dto.rdms.RdmsCourseDto;
import com.course.server.dto.rdms.RdmsSectionDto;
import com.course.server.service.RdmsChapterService;
import com.course.server.service.RdmsCourseService;
import com.course.server.service.RdmsSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class WebService {
    private static final Logger LOG = LoggerFactory.getLogger(WebService.class);
    @Resource
    private RdmsCourseService rdmsCourseService;
    @Resource
    private RdmsChapterService rdmsChapterService;
    @Resource
    private RdmsSectionService rdmsSectionService;

    /**
     * 获取课程详情
     * 根据课程ID获取课程的详细信息，包括章节和节信息
     * 
     * @param id 课程ID
     * @return 返回课程详细信息，如果不存在则返回null
     */
    public RdmsCourseDto getCourseDetail(String id) {
        RdmsCourseDto course = rdmsCourseService.getCourse(id);
        if(course!=null){
            List<RdmsChapterDto> rdmsChapterDtos = rdmsChapterService.listByCourse(id);
            if(rdmsChapterDtos!=null){
                for(RdmsChapterDto rdmsChapterDto : rdmsChapterDtos){
                    List<RdmsSectionDto> rdmsSectionDtos = rdmsSectionService.listByChapter(rdmsChapterDto.getId());
                    rdmsChapterDto.setSectionlist(rdmsSectionDtos);
                }
            }else{
                return null;
            }
            course.setChapters(rdmsChapterDtos);
            return course;
        }else{
            return null;
        }
    }

    /**
     * 获取章节详情
     * 根据章节ID获取章节的详细信息
     * 
     * @param id 章节ID
     * @return 返回章节详细信息
     */
    public RdmsCourseSection getSectionDetail(String id) {
        return rdmsSectionService.getSectionByPrimaryKey(id);
    }

    /**
     * 获取课程列表
     * 根据分页参数获取课程列表
     * 
     * @param pageDto 分页查询对象
     */
    public void getCourseList(RdmsCoursePageDto pageDto) {
        rdmsCourseService.webList(pageDto);
    }


}
