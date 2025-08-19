/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseCategoryExample;
import com.course.server.domain.InstitutionCategory;
import com.course.server.domain.InstitutionCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.InstitutionCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.mapper.InstitutionCategoryMapper;
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
public class InstitutionCategoryService {

    @Resource
    private InstitutionCategoryMapper institutionCategoryMapper;

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * 批量保存机构课程分类，先删除原有分类再插入新的分类
     * 
     * @param institutionId 机构ID
     * @param orgId 组织ID
     * @param dtoList 分类数据传输对象列表
     */
    @Transactional
    public void saveBatch(String institutionId, String orgId, List<CategoryDto> dtoList) {
        InstitutionCategoryExample example = new InstitutionCategoryExample();
        example.createCriteria().andInstitutionIdEqualTo(institutionId);
        institutionCategoryMapper.deleteByExample(example);
        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            InstitutionCategory institutionCategory = new InstitutionCategory();
            institutionCategory.setId(UuidUtil.getShortUuid());
            institutionCategory.setInstitutionId(institutionId);
            institutionCategory.setOrgId(orgId);
            institutionCategory.setCategoryId(categoryDto.getId());
            insert(institutionCategory);
        }
    }

    /**
     * 批量保存机构品牌分类
     * 根据某一机构品牌，先清空品牌分类，再保存品牌分类
     * 
     * @param institutionId 机构ID
     * @param orgId 品牌ID
     * @param dtoList 分类数据传输对象列表
     */
    @Transactional
    public void saveOrgBrandBatch(String institutionId, String orgId, List<CategoryDto> dtoList) {
        InstitutionCategoryExample example = new InstitutionCategoryExample();
        example.createCriteria().andOrgIdEqualTo(orgId);
        institutionCategoryMapper.deleteByExample(example);

        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            InstitutionCategory institutionCategory = new InstitutionCategory();
            institutionCategory.setId(UuidUtil.getShortUuid());
            institutionCategory.setInstitutionId(institutionId);
            institutionCategory.setOrgId(orgId);
            institutionCategory.setCategoryId(categoryDto.getId());
            insert(institutionCategory);
        }
    }



    /**
     * 新增
     */
    private void insert(InstitutionCategory institutionCategory) {
        institutionCategory.setId(UuidUtil.getShortUuid());
        institutionCategoryMapper.insert(institutionCategory);
    }

    /**
     * 查找课程下所有分类
     * 根据机构ID查询该机构下的所有分类信息
     * 
     * @param institutionId 机构ID
     * @return 返回机构分类数据传输对象列表
     */
    public List<InstitutionCategoryDto> listByCourse(String institutionId) {
        InstitutionCategoryExample example = new InstitutionCategoryExample();
        example.createCriteria().andInstitutionIdEqualTo(institutionId);
        List<InstitutionCategory> institutionCategoryList = institutionCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(institutionCategoryList, InstitutionCategoryDto.class);
    }


    /**
     * 根据品牌ID查找分类列表
     * 查询指定品牌下的所有分类信息
     * 
     * @param orgId 品牌ID
     * @return 返回机构分类数据传输对象列表
     */
    public List<InstitutionCategoryDto> listByOrgId(String orgId) {
        InstitutionCategoryExample example = new InstitutionCategoryExample();
        example.createCriteria().andOrgIdEqualTo(orgId);
        List<InstitutionCategory> institutionCategoryList = institutionCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(institutionCategoryList, InstitutionCategoryDto.class);
    }

}
