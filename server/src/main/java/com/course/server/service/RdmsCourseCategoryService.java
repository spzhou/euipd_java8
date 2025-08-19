/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.RdmsCourseCategoryExample;
import com.course.server.domain.RdmsCourseCategory;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsCourseCategoryDto;
import com.course.server.mapper.RdmsCourseCategoryMapper;
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
public class RdmsCourseCategoryService {

    @Resource
    private RdmsCourseCategoryMapper rdmsCourseCategoryMapper;
    private RdmsCourseCategory rdmsCourseCategory;

    /**
     * 列表查询
     */
    public List<RdmsCourseCategoryDto> all() {
        RdmsCourseCategoryExample categoryExample = new RdmsCourseCategoryExample();
        categoryExample.setOrderByClause("sort asc");
        categoryExample.createCriteria().andDeletedEqualTo(0);
        List<RdmsCourseCategory> rdmsCourseCategories = rdmsCourseCategoryMapper.selectByExample(categoryExample);
        List<RdmsCourseCategoryDto> categoryDtoList = CopyUtil.copyList(rdmsCourseCategories, RdmsCourseCategoryDto.class);
        return categoryDtoList;
    }

    /**
     * 根据主键查询课程分类
     * 通过ID查询指定的课程分类信息
     * 
     * @param id 课程分类ID
     * @return 返回课程分类对象
     */
    public RdmsCourseCategory selectByPrimaryKey(String id){
        return rdmsCourseCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取一级分类列表
     * 查询所有的一级课程分类（父分类为00000000）
     * 
     * @return 返回一级分类列表
     */
    public List<RdmsCourseCategory> getLevel1List(){
        RdmsCourseCategoryExample categoryExample = new RdmsCourseCategoryExample();
        categoryExample.createCriteria().andParentEqualTo("00000000").andDeletedEqualTo(0);
        List<RdmsCourseCategory> rdmsCourseCategories = rdmsCourseCategoryMapper.selectByExample(categoryExample);
        return rdmsCourseCategories;
    }

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCourseCategoryExample categoryExample = new RdmsCourseCategoryExample();
        categoryExample.setOrderByClause("sort asc");
        categoryExample.createCriteria().andDeletedEqualTo(0);
        List<RdmsCourseCategory> categoryList = rdmsCourseCategoryMapper.selectByExample(categoryExample);
        PageInfo<RdmsCourseCategory> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsCourseCategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, RdmsCourseCategoryDto.class);
        pageDto.setList(categoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsCourseCategoryDto categoryDto) {
        RdmsCourseCategory category = CopyUtil.copy(categoryDto, RdmsCourseCategory.class);
        if (ObjectUtils.isEmpty(categoryDto.getId())) {
            this.insert(category);
        } else {
            this.update(category);
        }
    }

    /**
     * 新增
     */
    private void insert(RdmsCourseCategory category) {
        category.setId(UuidUtil.getShortUuid());
        category.setDeleted(0);
        rdmsCourseCategoryMapper.insert(category);
    }

    /**
     * 更新
     */
    private void update(RdmsCourseCategory category) {
        rdmsCourseCategoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 删除
     */
    @Transactional
    public void delete(String id) {
        deleteChildren(id);
        RdmsCourseCategory rdmsCourseCategory1 = rdmsCourseCategoryMapper.selectByPrimaryKey(id);
        if(rdmsCourseCategory1 != null){
            rdmsCourseCategory1.setDeleted(1);
            rdmsCourseCategoryMapper.updateByPrimaryKey(rdmsCourseCategory1);
        }
    }

    /**
     * 删除子分类
     * 删除指定分类下的所有子分类，将删除标志设置为1
     * 
     * @param id 父分类ID
     */
    public void deleteChildren(String id) {
        RdmsCourseCategory category = rdmsCourseCategoryMapper.selectByPrimaryKey(id);
        if ("00000000".equals(category.getParent())) {
            // 如果是一级分类，需要删除其下的二级分类
            RdmsCourseCategoryExample example = new RdmsCourseCategoryExample();
            example.createCriteria().andParentEqualTo(category.getId());
            List<RdmsCourseCategory> rdmsCourseCategories = rdmsCourseCategoryMapper.selectByExample(example);
            if(!rdmsCourseCategories.isEmpty()){
                for (RdmsCourseCategory rdmsCourseCategory : rdmsCourseCategories) {
                    rdmsCourseCategory.setDeleted(1);
                    rdmsCourseCategoryMapper.updateByPrimaryKey(rdmsCourseCategory);
                }
            }

        }
    }
}
