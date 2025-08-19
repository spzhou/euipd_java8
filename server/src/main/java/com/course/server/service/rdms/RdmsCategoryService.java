/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsCategory;
import com.course.server.domain.RdmsCategoryExample;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCategoryMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RdmsCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCategoryService.class);

    @Resource
    private RdmsCategoryMapper rdmsCategoryMapper;

    public List<RdmsCategory> getCategoryList_all_plus(String customerId){
        RdmsCategoryExample categoryExample = new RdmsCategoryExample();
        categoryExample.setOrderByClause("sort asc");
        RdmsCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        List<RdmsCategory> rdmsCategories = rdmsCategoryMapper.selectByExample(categoryExample);
        return rdmsCategories;
    }

    public List<RdmsCategory> getCategoryList_all(String customerId){
        RdmsCategoryExample categoryExample = new RdmsCategoryExample();
        categoryExample.setOrderByClause("sort asc");
        RdmsCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andIsDeleteEqualTo(0);
        List<RdmsCategory> rdmsCategories = rdmsCategoryMapper.selectByExample(categoryExample);
        return rdmsCategories;
    }

    public List<RdmsCategory> getCategoryList_open(String customerId){
        RdmsCategoryExample categoryExample = new RdmsCategoryExample();
        categoryExample.setOrderByClause("sort asc");
        RdmsCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andIsOpenEqualTo(1)
                .andIsDeleteEqualTo(0);
        List<RdmsCategory> rdmsCategories = rdmsCategoryMapper.selectByExample(categoryExample);
        return rdmsCategories;
    }

    public void initCategoryByCustomerId(String customerId){
        List<RdmsCategory> categoryListAll = getCategoryList_all(customerId);
        if(CollectionUtils.isEmpty(categoryListAll)){
            RdmsCategory category = new RdmsCategory();
            category.setParent("00000000");
            category.setName("说明书");
            category.setCustomerId(customerId);
            category.setSort(0);
            category.setIsOpen(1);
            this.insert(category);

            RdmsCategory category1 = new RdmsCategory();
            category1.setParent("00000000");
            category1.setName("规格书");
            category1.setCustomerId(customerId);
            category1.setSort(1);
            category1.setIsOpen(1);
            this.insert(category1);

            RdmsCategory category2 = new RdmsCategory();
            category2.setParent("00000000");
            category2.setName("售后文件");
            category2.setCustomerId(customerId);
            category2.setSort(2);
            category2.setIsOpen(1);
            this.insert(category2);

            RdmsCategory category3 = new RdmsCategory();
            category3.setParent("00000000");
            category3.setName("市场文件");
            category3.setCustomerId(customerId);
            category3.setSort(3);
            category3.setIsOpen(1);
            this.insert(category3);
        }
    }

    public RdmsCategory selectByPrimaryKey(String id){
        return rdmsCategoryMapper.selectByPrimaryKey(id);
    }

    public List<RdmsCategory> selectByCategoryName(String categoryName){
        RdmsCategoryExample categoryExample = new RdmsCategoryExample();
        RdmsCategoryExample.Criteria criteria = categoryExample.createCriteria()
                .andNameEqualTo(categoryName)
                .andIsDeleteEqualTo(0);
        List<RdmsCategory> rdmsCategories = rdmsCategoryMapper.selectByExample(categoryExample);
        return rdmsCategories;
    }

    /**
     * 保存
     */
    public String save(RdmsCategory category) {
        if(ObjectUtils.isEmpty(category.getId())){
            return this.insert(category);
        }else{
            RdmsCategory rdmsCategory = this.selectByPrimaryKey(category.getId());
            if(ObjectUtils.isEmpty(rdmsCategory)){
                return this.insert(category);
            }else{
                return this.update(category);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsCategory category) {
        if(ObjectUtils.isEmpty(category.getId())){
            category.setId(UuidUtil.getShortUuid());
        }
        RdmsCategory rdmsCategory = rdmsCategoryMapper.selectByPrimaryKey(category.getId());
        if(! ObjectUtils.isEmpty(rdmsCategory)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            if(ObjectUtils.isEmpty(category.getIsOpen())){
                category.setIsOpen(0);
            }
            if(ObjectUtils.isEmpty(category.getIsDelete())){
                category.setIsDelete(0);
            }
            if(ObjectUtils.isEmpty(category.getSort())){
                category.setSort(5);
            }
            rdmsCategoryMapper.insert(category);
            return category.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsCategory category) {
        if(ObjectUtils.isEmpty(category.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsCategory rdmsCategory = this.selectByPrimaryKey(category.getId());
        if(ObjectUtils.isEmpty(rdmsCategory)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsCategoryMapper.updateByPrimaryKey(category);
            return category.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsCategory category){
        rdmsCategoryMapper.updateByPrimaryKeySelective(category);
        return category.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCategory category = rdmsCategoryMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(category)){
            category.setIsDelete(1);
            this.update(category);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
