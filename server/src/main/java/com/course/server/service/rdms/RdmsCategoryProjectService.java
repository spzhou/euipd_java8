/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsCategoryProject;
import com.course.server.domain.RdmsCategoryProjectExample;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCategoryProjectMapper;
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
public class RdmsCategoryProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCategoryProjectService.class);

    @Resource
    private RdmsCategoryProjectMapper rdmsCategoryProjectMapper;

    public List<RdmsCategoryProject> getCategoryList_all(String customerId){
        RdmsCategoryProjectExample categoryExample = new RdmsCategoryProjectExample();
        categoryExample.setOrderByClause("sort asc");
        RdmsCategoryProjectExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andIsDeleteEqualTo(0);
        List<RdmsCategoryProject> rdmsCategories = rdmsCategoryProjectMapper.selectByExample(categoryExample);
        return rdmsCategories;
    }

    public List<RdmsCategoryProject> getCategoryList_all_show(String customerId){
        RdmsCategoryProjectExample categoryExample = new RdmsCategoryProjectExample();
        categoryExample.setOrderByClause("sort asc");
        RdmsCategoryProjectExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andIsOpenEqualTo(0)
                .andIsDeleteEqualTo(0);
        List<RdmsCategoryProject> rdmsCategories = rdmsCategoryProjectMapper.selectByExample(categoryExample);
        return rdmsCategories;
    }

    public void initCategoryByCustomerId(String customerId){
        RdmsCategoryProject categoryProject = this.selectByPrimaryKey(customerId);
        if(ObjectUtils.isEmpty(categoryProject)){
            RdmsCategoryProject category = new RdmsCategoryProject();
            category.setId(customerId);
            category.setParent(customerId);
            category.setName("全部项目");
            category.setCustomerId(customerId);
            category.setSort(0);
            category.setIsOpen(0);
            this.insert(category);
        }
    }

    public RdmsCategoryProject selectByPrimaryKey(String id){
        return rdmsCategoryProjectMapper.selectByPrimaryKey(id);
    }

    public List<RdmsCategoryProject> selectByCategoryName(String categoryName){
        RdmsCategoryProjectExample categoryExample = new RdmsCategoryProjectExample();
        RdmsCategoryProjectExample.Criteria criteria = categoryExample.createCriteria()
                .andNameEqualTo(categoryName)
                .andIsDeleteEqualTo(0);
        List<RdmsCategoryProject> rdmsCategories = rdmsCategoryProjectMapper.selectByExample(categoryExample);
        return rdmsCategories;
    }

    /**
     * 保存
     */
    public String save(RdmsCategoryProject category) {
        if(ObjectUtils.isEmpty(category.getId())){
            return this.insert(category);
        }else{
            RdmsCategoryProject rdmsCategory = this.selectByPrimaryKey(category.getId());
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
    private String insert(RdmsCategoryProject category) {
        if(ObjectUtils.isEmpty(category.getId())){
            category.setId(UuidUtil.getShortUuid());
        }
        RdmsCategoryProject rdmsCategory = rdmsCategoryProjectMapper.selectByPrimaryKey(category.getId());
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
            rdmsCategoryProjectMapper.insert(category);
            return category.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsCategoryProject category) {
        if(ObjectUtils.isEmpty(category.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsCategoryProject rdmsCategory = this.selectByPrimaryKey(category.getId());
        if(ObjectUtils.isEmpty(rdmsCategory)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsCategoryProjectMapper.updateByPrimaryKey(category);
            return category.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsCategoryProject category){
        rdmsCategoryProjectMapper.updateByPrimaryKeySelective(category);
        return category.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCategoryProject category = rdmsCategoryProjectMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(category)){
            category.setIsDelete(1);
            this.update(category);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
