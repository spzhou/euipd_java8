/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.RdmsCourseOwnCategory;
import com.course.server.domain.RdmsCourseOwnCategoryExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsCourseOwnCategoryDto;
import com.course.server.mapper.RdmsCourseOwnCategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsCourseOwnCategoryService {

    @Resource
    private RdmsCourseOwnCategoryMapper rdmsCourseOwnCategoryMapper;

    /**
     * 列表查询
     * 分页查询RDMS课程自有分类列表
     * 
     * @param pageDto 分页查询对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCourseOwnCategoryExample courseCategoryExample = new RdmsCourseOwnCategoryExample();
        List<RdmsCourseOwnCategory> courseCategoryList = rdmsCourseOwnCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<RdmsCourseOwnCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsCourseOwnCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, RdmsCourseOwnCategoryDto.class);
        pageDto.setList(courseCategoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新RDMS课程自有分类信息
     * 
     * @param courseCategoryDto 课程自有分类数据传输对象
     */
    public void save(RdmsCourseOwnCategoryDto courseCategoryDto) {
        RdmsCourseOwnCategory courseOwnCategory = CopyUtil.copy(courseCategoryDto, RdmsCourseOwnCategory.class);
        if (ObjectUtils.isEmpty(courseCategoryDto.getId())) {
            this.insert(courseOwnCategory);
        } else {
            this.update(courseOwnCategory);
        }
    }

    /**
     * 新增
     * 创建新的RDMS课程自有分类记录
     * 
     * @param courseOwnCategory 课程自有分类对象
     */
    private void insert(RdmsCourseOwnCategory courseOwnCategory) {
        courseOwnCategory.setId(UuidUtil.getShortUuid());
        rdmsCourseOwnCategoryMapper.insert(courseOwnCategory);
    }

    /**
     * 更新
     * 更新RDMS课程自有分类信息
     * 
     * @param courseOwnCategory 课程自有分类对象
     */
    private void update(RdmsCourseOwnCategory courseOwnCategory) {
        rdmsCourseOwnCategoryMapper.updateByPrimaryKey(courseOwnCategory);
    }

    /**
     * 删除
     * 根据ID删除RDMS课程自有分类记录
     * 
     * @param id 课程自有分类ID
     */
    public void delete(String id) {
        rdmsCourseOwnCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * 批量保存课程自有分类，先删除原有分类再插入新的分类
     * 
     * @param courseId 课程ID
     * @param categoryIdList 分类ID列表
     */
    @Transactional
    public void saveBatch(String courseId, List<String> categoryIdList) {
        RdmsCourseOwnCategoryExample example = new RdmsCourseOwnCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        rdmsCourseOwnCategoryMapper.deleteByExample(example);
        for (int i = 0, l = categoryIdList.size(); i < l; i++) {
            RdmsCourseOwnCategory courseOwnCategory = new RdmsCourseOwnCategory();
            courseOwnCategory.setId(UuidUtil.getShortUuid());
            courseOwnCategory.setCourseId(courseId);
            courseOwnCategory.setCategoryId(categoryIdList.get(i));
            insert(courseOwnCategory);
        }
    }

    /**
     * 查找课程下所有分类
     * 根据课程ID查询该课程下的所有分类ID列表
     * 
     * @param courseId 课程ID
     * @return 返回分类ID列表
     */
    public List<String> listByCourse(String courseId) {
        RdmsCourseOwnCategoryExample example = new RdmsCourseOwnCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<RdmsCourseOwnCategory> courseCategoryList = rdmsCourseOwnCategoryMapper.selectByExample(example);
        return courseCategoryList.stream().map(RdmsCourseOwnCategory::getCategoryId).collect(Collectors.toList());
    }

}
