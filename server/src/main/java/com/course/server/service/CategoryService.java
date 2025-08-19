/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
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
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 列表查询
     */
    public List<CategoryDto> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        return categoryDtoList;
    }

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        pageDto.setList(categoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);
        if (ObjectUtils.isEmpty(categoryDto.getId())) {
            this.insert(category);
        } else {
            this.update(category);
        }
    }

    /**
     * 新增
     */
    private void insert(Category category) {
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    /**
     * 更新
     */
    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 删除
     */
    @Transactional
    public void delete(String id) {
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除子分类
     * 删除指定分类下的所有子分类
     * 
     * @param id 父分类ID
     */
    public void deleteChildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if ("00000000".equals(category.getParent())) {
            // 如果是一级分类，需要删除其下的二级分类
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(example);
        }
    }
}
