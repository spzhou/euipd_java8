/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.CourseContentFile;
import com.course.server.domain.CourseContentFileExample;
import com.course.server.domain.InstitutionContentFile;
import com.course.server.domain.InstitutionContentFileExample;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.dto.InstitutionContentFileDto;
import com.course.server.enums.InstitutionImgClassEnum;
import com.course.server.mapper.CourseContentFileMapper;
import com.course.server.mapper.InstitutionContentFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitutionContentFileService {

    @Resource
    private InstitutionContentFileMapper institutionContentFileMapper;

    /**
     * 列表查询
     * 根据机构ID查询机构内容文件列表，按图片分类排序
     * 
     * @param institutionId 机构ID
     * @return 返回机构内容文件数据传输对象列表
     */
    public List<InstitutionContentFileDto> list(String institutionId) {
        InstitutionContentFileExample example = new InstitutionContentFileExample();
        InstitutionContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId);
        List<InstitutionContentFile> fileList = institutionContentFileMapper.selectByExample(example);

        fileList = fileList.stream().sorted(Comparator.comparing(InstitutionContentFile::getImgClass)).collect(Collectors.toList());
        return CopyUtil.copyList(fileList, InstitutionContentFileDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新机构内容文件信息
     * 
     * @param institutionContentFileDto 机构内容文件数据传输对象
     */
    public void save(InstitutionContentFileDto institutionContentFileDto) {
        InstitutionContentFile institutionContentFile = CopyUtil.copy(institutionContentFileDto, InstitutionContentFile.class);
        if (ObjectUtils.isEmpty(institutionContentFileDto.getId())) {
            this.insert(institutionContentFile);
        } else {
            this.update(institutionContentFile);
        }
    }

    /**
     * 新增
     * 创建新的机构内容文件记录
     * 
     * @param institutionContentFile 机构内容文件对象
     */
    private void insert(InstitutionContentFile institutionContentFile) {
        institutionContentFile.setId(UuidUtil.getShortUuid());
        institutionContentFileMapper.insert(institutionContentFile);
    }

    /**
     * 更新
     * 更新机构内容文件信息
     * 
     * @param institutionContentFile 机构内容文件对象
     */
    private void update(InstitutionContentFile institutionContentFile) {
        institutionContentFileMapper.updateByPrimaryKey(institutionContentFile);
    }

    /**
     * 删除
     * 根据ID删除机构内容文件记录
     * 
     * @param id 机构内容文件ID
     */
    public void delete(String id) {
        institutionContentFileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取机构主图片
     * 根据机构ID获取机构的主图片URL
     * 
     * @param institutionId 机构ID
     * @return 返回主图片URL，如果不存在则返回null
     */
    public String getInstitutionMainImg(String institutionId) {
        InstitutionContentFileExample example = new InstitutionContentFileExample();
        InstitutionContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId).andImgClassEqualTo(InstitutionImgClassEnum.MAIN.getDesc())
                .andOrderNumIsNotNull();

        List<InstitutionContentFile> fileList = institutionContentFileMapper.selectByExample(example);
        if(fileList.size()==0){
            return null;
        }
        fileList = fileList.stream().sorted(Comparator.comparing(InstitutionContentFile::getOrderNum)).collect(Collectors.toList());

        InstitutionContentFileDto institutionContentFileDto = CopyUtil.copy(fileList.get(0), InstitutionContentFileDto.class);
        return institutionContentFileDto.getUrl();
    }

    /**
     * 获取机构信息图片列表
     * 根据机构ID获取机构的信息图片URL列表
     * 
     * @param institutionId 机构ID
     * @return 返回信息图片URL列表，如果不存在则返回null
     */
    public List<String> getInstitutionInfoImgs(String institutionId) {
        InstitutionContentFileExample example = new InstitutionContentFileExample();
        InstitutionContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId).andImgClassEqualTo(InstitutionImgClassEnum.INFO.getDesc());
        List<InstitutionContentFile> fileList = institutionContentFileMapper.selectByExample(example);
        if(fileList.size()==0){
            return null;
        }
        fileList = fileList.stream().sorted(Comparator.comparing(InstitutionContentFile::getOrderNum)).collect(Collectors.toList());

        List<String> infoImgUrls = new ArrayList<>();
        for(InstitutionContentFile institutionContentFile: fileList){
            infoImgUrls.add(institutionContentFile.getUrl());
        }

        return infoImgUrls;
    }

    /**
     * 获取机构联系信息图片
     * 根据机构ID获取机构的联系信息图片URL
     * 
     * @param institutionId 机构ID
     * @return 返回联系信息图片URL，如果不存在则返回null
     */
    public String getInstiContactInfoImg(String institutionId) {
        InstitutionContentFileExample example = new InstitutionContentFileExample();
        InstitutionContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId).andImgClassEqualTo(InstitutionImgClassEnum.CONTACT.getDesc());
        List<InstitutionContentFile> fileList = institutionContentFileMapper.selectByExample(example);
        if(fileList.size()==0){
            return null;
        }
        fileList = fileList.stream().sorted(Comparator.comparing(InstitutionContentFile::getOrderNum)).collect(Collectors.toList());
        InstitutionContentFileDto institutionContentFileDto = CopyUtil.copy(fileList.get(0), InstitutionContentFileDto.class);
        return institutionContentFileDto.getUrl();
    }

}
