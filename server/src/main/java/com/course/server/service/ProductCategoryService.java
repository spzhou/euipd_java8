/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.ProductCategory;
import com.course.server.domain.ProductCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.ProductCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.mapper.ProductCategoryMapper;
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
public class ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ProductCategoryExample productCategoryExample = new ProductCategoryExample();
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(productCategoryExample);
        PageInfo<ProductCategory> pageInfo = new PageInfo<>(productCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ProductCategoryDto> productCategoryDtoList = CopyUtil.copyList(productCategoryList, ProductCategoryDto.class);
        pageDto.setList(productCategoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = CopyUtil.copy(productCategoryDto, ProductCategory.class);
        if (ObjectUtils.isEmpty(productCategoryDto.getId())) {
            this.insert(productCategory);
        } else {
            this.update(productCategory);
        }
    }

    /**
     * 新增
     */
    private void insert(ProductCategory productCategory) {
        productCategory.setId(UuidUtil.getShortUuid());
        productCategoryMapper.insert(productCategory);
    }

    /**
     * 更新
     */
    private void update(ProductCategory productCategory) {
        productCategoryMapper.updateByPrimaryKey(productCategory);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        productCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * 批量保存产品分类，先删除原有分类再插入新的分类
     * 
     * @param productId 产品ID
     * @param dtoList 分类数据传输对象列表
     */
    @Transactional
    public void saveBatch(String productId, List<CategoryDto> dtoList) {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andProductIdEqualTo(productId);
        productCategoryMapper.deleteByExample(example);
        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(UuidUtil.getShortUuid());
            productCategory.setProductId(productId);
            productCategory.setCategoryId(categoryDto.getId());
            insert(productCategory);
        }
    }

    /**
     * 查找产品下所有分类
     * 根据产品ID查询该产品下的所有分类信息
     * 
     * @param productId 产品ID
     * @return 返回产品分类数据传输对象列表
     */
    public List<ProductCategoryDto> listByProductId(String productId) {
        ProductCategoryExample example = new ProductCategoryExample();
        example.createCriteria().andProductIdEqualTo(productId);
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(productCategoryList, ProductCategoryDto.class);
    }

}
