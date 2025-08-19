/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.TeacherCategory;
import com.course.server.domain.TeacherCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.RdmsTeacherCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherCategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RdmsTeacherCategoryService {

    @Resource
    private TeacherCategoryMapper teacherCategoryMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        TeacherCategoryExample teacherCategoryExample = new TeacherCategoryExample();
        List<TeacherCategory> teacherCategoryList = teacherCategoryMapper.selectByExample(teacherCategoryExample);
        PageInfo<TeacherCategory> pageInfo = new PageInfo<>(teacherCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsTeacherCategoryDto> teacherCategoryDtoList = CopyUtil.copyList(teacherCategoryList, RdmsTeacherCategoryDto.class);
        pageDto.setList(teacherCategoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsTeacherCategoryDto teacherCategoryDto) {
        TeacherCategory teacherCategory = CopyUtil.copy(teacherCategoryDto, TeacherCategory.class);
        if (ObjectUtils.isEmpty(teacherCategoryDto.getId())) {
            this.insert(teacherCategory);
        } else {
            this.update(teacherCategory);
        }
    }

    /**
     * 新增
     */
    private void insert(TeacherCategory teacherCategory) {
        teacherCategory.setId(UuidUtil.getShortUuid());
        teacherCategoryMapper.insert(teacherCategory);
    }

    /**
     * 更新
     */
    private void update(TeacherCategory teacherCategory) {
        teacherCategoryMapper.updateByPrimaryKey(teacherCategory);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        teacherCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * 批量保存教师分类，先删除原有分类再插入新的分类
     * 
     * @param courseId 教师ID
     * @param dtoList 分类数据传输对象列表
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
        TeacherCategoryExample example = new TeacherCategoryExample();
        example.createCriteria().andTeacherIdEqualTo(courseId);
        teacherCategoryMapper.deleteByExample(example);
        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            TeacherCategory teacherCategory = new TeacherCategory();
            teacherCategory.setId(UuidUtil.getShortUuid());
            teacherCategory.setTeacherId(courseId);
            teacherCategory.setCategoryId(categoryDto.getId());
            insert(teacherCategory);
        }
    }

    /**
     * 查找课程下所有分类
     * 根据教师ID查询该教师下的所有分类信息
     * 
     * @param teacherId 教师ID
     * @return 返回教师分类数据传输对象列表
     */
    public List<RdmsTeacherCategoryDto> listByTeacher(String teacherId) {
        TeacherCategoryExample example = new TeacherCategoryExample();
        example.createCriteria().andTeacherIdEqualTo(teacherId);
        List<TeacherCategory> teacherCategoryList = teacherCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(teacherCategoryList, RdmsTeacherCategoryDto.class);
    }

}
