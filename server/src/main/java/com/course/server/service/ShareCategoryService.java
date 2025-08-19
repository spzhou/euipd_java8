/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.ShareCategory;
import com.course.server.domain.ShareCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.LiveCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ShareCategoryDto;
import com.course.server.mapper.LiveCategoryMapper;
import com.course.server.mapper.ShareCategoryMapper;
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
public class ShareCategoryService {

    @Resource
    private ShareCategoryMapper shareCategoryMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ShareCategoryExample shareCategoryExample = new ShareCategoryExample();
        List<ShareCategory> shareCategoryList = shareCategoryMapper.selectByExample(shareCategoryExample);
        PageInfo<ShareCategory> pageInfo = new PageInfo<>(shareCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ShareCategoryDto> shareCategoryDtoList = CopyUtil.copyList(shareCategoryList, ShareCategoryDto.class);
        pageDto.setList(shareCategoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ShareCategoryDto shareCategoryDto) {
        ShareCategory shareCategory = CopyUtil.copy(shareCategoryDto, ShareCategory.class);
        if (ObjectUtils.isEmpty(shareCategoryDto.getId())) {
            this.insert(shareCategory);
        } else {
            this.update(shareCategory);
        }
    }

    /**
     * 新增
     */
    private void insert(ShareCategory shareCategory) {
        shareCategory.setId(UuidUtil.getShortUuid());
        shareCategoryMapper.insert(shareCategory);
    }

    /**
     * 更新
     */
    private void update(ShareCategory shareCategory) {
        shareCategoryMapper.updateByPrimaryKey(shareCategory);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        shareCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * 批量保存分享分类，先删除原有分类再插入新的分类
     * 
     * @param shareId 分享ID
     * @param dtoList 分类数据传输对象列表
     */
    @Transactional
    public void saveBatch(String shareId, List<CategoryDto> dtoList) {
        ShareCategoryExample example = new ShareCategoryExample();
        example.createCriteria().andShareIdEqualTo(shareId);
        shareCategoryMapper.deleteByExample(example);
        for (int i = 0, l = dtoList.size(); i < l; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            ShareCategory shareCategory = new ShareCategory();
            shareCategory.setId(UuidUtil.getShortUuid());
            shareCategory.setShareId(shareId);
            shareCategory.setCategoryId(categoryDto.getId());
            insert(shareCategory);
        }
    }

    /**
     * 查找课程下所有分类
     * 根据分享ID查询该分享下的所有分类信息
     * 
     * @param shareId 分享ID
     * @return 返回分享分类数据传输对象列表
     */
    public List<ShareCategoryDto> listByLive(String shareId) {
        ShareCategoryExample example = new ShareCategoryExample();
        example.createCriteria().andShareIdEqualTo(shareId);
        List<ShareCategory> shareCategoryList = shareCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(shareCategoryList, ShareCategoryDto.class);
    }

}
