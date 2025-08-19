/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.RdmsCourseChapter;
import com.course.server.domain.RdmsCourseChapterExample;
import com.course.server.dto.RdmsChapterDto;
import com.course.server.dto.RdmsChapterPageDto;
import com.course.server.dto.rdms.RdmsCourseDto;
import com.course.server.mapper.RdmsCourseChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsChapterService {

    @Resource
    private RdmsCourseChapterMapper rdmsCourseChapterMapper;
    @Autowired
    private RdmsCourseService rdmsCourseService;

    /**
     * 列表查询
     */
    public void list(RdmsChapterPageDto chapterPageDto) {
        PageHelper.startPage(chapterPageDto.getPage(), chapterPageDto.getSize());
        RdmsCourseChapterExample chapterExample = new RdmsCourseChapterExample();
        RdmsCourseChapterExample.Criteria criteria = chapterExample.createCriteria().andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(chapterPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<RdmsCourseChapter> chapterList = rdmsCourseChapterMapper.selectByExample(chapterExample);
        PageInfo<RdmsCourseChapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<RdmsChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, RdmsChapterDto.class);
        chapterDtoList = chapterDtoList.stream().sorted(Comparator.comparing(RdmsChapterDto::getSort)).collect(Collectors.toList());
        chapterPageDto.setList(chapterDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsChapterDto chapterDto) {
        RdmsCourseChapter chapter = CopyUtil.copy(chapterDto, RdmsCourseChapter.class);
        if (ObjectUtils.isEmpty(chapterDto.getId())) {
            this.insert(chapter);
        } else {
            this.update(chapter);
        }
    }

    /**
     * 新增
     */
    private void insert(RdmsCourseChapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapter.setDeleted(0);
        rdmsCourseChapterMapper.insert(chapter);
    }

    /**
     * 更新
     */
    private void update(RdmsCourseChapter chapter) {
        chapter.setDeleted(0);
        rdmsCourseChapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        RdmsCourseChapter rdmsCourseChapter = rdmsCourseChapterMapper.selectByPrimaryKey(id);
        rdmsCourseChapter.setDeleted(1);
        rdmsCourseChapterMapper.updateByPrimaryKey(rdmsCourseChapter);
    }

    /**
     * 查询某一课程下的所有章
     */
    public RdmsChapterDto getChapterInfo(String chapterId) {
        RdmsCourseChapter rdmsCourseChapter = rdmsCourseChapterMapper.selectByPrimaryKey(chapterId);
        RdmsChapterDto chapterDto = CopyUtil.copy(rdmsCourseChapter, RdmsChapterDto.class);
        if (rdmsCourseChapter != null) {
            RdmsCourseDto course = rdmsCourseService.getCourse(rdmsCourseChapter.getCourseId());
            chapterDto.setCourseName(course.getName());
        }
        return chapterDto;
    }

    /**
     * 查询某一课程下的所有章
     */
    public List<RdmsChapterDto> listByCourse(String courseId) {
        RdmsCourseChapterExample example = new RdmsCourseChapterExample();
        example.setOrderByClause("sort asc");
        example.createCriteria().andCourseIdEqualTo(courseId).andDeletedEqualTo(0);
        List<RdmsCourseChapter> chapterList = rdmsCourseChapterMapper.selectByExample(example);
        List<RdmsChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, RdmsChapterDto.class);
        return chapterDtoList;
    }


}
