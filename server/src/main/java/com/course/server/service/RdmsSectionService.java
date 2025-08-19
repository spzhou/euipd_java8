/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.RdmsCourseSection;
import com.course.server.domain.RdmsCourseSectionExample;
import com.course.server.dto.SectionPageDto;
import com.course.server.dto.rdms.RdmsSectionDto;
import com.course.server.mapper.RdmsCourseSectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsSectionService {

    @Resource
    private RdmsCourseSectionMapper rdmsCourseSectionMapper;

    @Resource
    private RdmsCourseService courseService;


    /**
     * 列表查询
     */
    public void list(SectionPageDto sectionPageDto) {
        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        RdmsCourseSectionExample sectionExample = new RdmsCourseSectionExample();
        RdmsCourseSectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!ObjectUtils.isEmpty(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        sectionExample.setOrderByClause("sort asc");
        List<RdmsCourseSection> sectionList = rdmsCourseSectionMapper.selectByExample(sectionExample);
        PageInfo<RdmsCourseSection> pageInfo = new PageInfo<>(sectionList);
        sectionPageDto.setTotal(pageInfo.getTotal());
        List<RdmsSectionDto> sectionDtoList = CopyUtil.copyList(sectionList, RdmsSectionDto.class);
        sectionPageDto.setList(sectionDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public void save(RdmsSectionDto sectionDto) throws Exception {
        RdmsCourseSection section = CopyUtil.copy(sectionDto, RdmsCourseSection.class);
        if (ObjectUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    /**
     * 新增
     */
    public void insert(RdmsCourseSection section) throws Exception {
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
        rdmsCourseSectionMapper.insert(section);

    }

    /**
     * 更新
     */
    public void update(RdmsCourseSection section) throws Exception {
        section.setUpdatedAt(new Date());
        rdmsCourseSectionMapper.updateByPrimaryKey(section);

    }

    /**
     * 删除
     */
    public void delete(String id) {
        rdmsCourseSectionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询某一课程下的所有节
     * 根据课程ID查询该课程下的所有节信息
     * 
     * @param courseId 课程ID
     * @return 返回节数据传输对象列表，如果课程ID为空则返回null
     */
    public List<RdmsSectionDto> listByCourse(String courseId) {
        if(courseId==null){
            return null;
        }
        RdmsCourseSectionExample example = new RdmsCourseSectionExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<RdmsCourseSection> sectionList = rdmsCourseSectionMapper.selectByExample(example);
        List<RdmsSectionDto> sectionDtoList = CopyUtil.copyList(sectionList, RdmsSectionDto.class);
        return sectionDtoList;
    }

    /**
     * 根据章节ID查询节列表
     * 查询指定章节下的所有节，按排序字段升序排列
     * 
     * @param chapterId 章节ID
     * @return 返回节数据传输对象列表
     */
    public List<RdmsSectionDto> listByChapter(String chapterId) {
        if(chapterId==null){
            return null;
        }
        RdmsCourseSectionExample example = new RdmsCourseSectionExample();
        example.setOrderByClause("sort asc");
        example.createCriteria().andChapterIdEqualTo(chapterId);
        List<RdmsCourseSection> sectionList = rdmsCourseSectionMapper.selectByExample(example);
        List<RdmsSectionDto> sectionDtoList = CopyUtil.copyList(sectionList, RdmsSectionDto.class);
        return sectionDtoList;
    }

    /**
     * 根据Vod查询是否存在相应记录
     * 通过VOD标识查询对应的节记录
     * 
     * @param vod VOD标识
     * @return 返回节数据传输对象，如果不存在则返回null
     */
    public RdmsSectionDto findSectionRecord(String vod) {
        if(vod==null){
            return null;
        }
        RdmsCourseSectionExample example = new RdmsCourseSectionExample();
        example.createCriteria().andVodEqualTo(vod);
        List<RdmsCourseSection> sectionList = rdmsCourseSectionMapper.selectByExample(example);
        if(sectionList.size()>0){
            return CopyUtil.copy(sectionList.get(0), RdmsSectionDto.class);
        }
        return null;
    }

    /**
     * 根据主键查询节信息
     * 通过ID查询指定的节详细信息
     * 
     * @param id 节ID
     * @return 返回节对象
     */
    public RdmsCourseSection getSectionByPrimaryKey(String id){
        RdmsCourseSectionExample sectionExample = new RdmsCourseSectionExample();
        return rdmsCourseSectionMapper.selectByPrimaryKey(id);
    }
}
