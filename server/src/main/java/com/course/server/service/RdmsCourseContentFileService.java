/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.RdmsCourseContentFile;
import com.course.server.domain.RdmsCourseContentFileExample;
import com.course.server.dto.rdms.RdmsCourseContentFileDto;
import com.course.server.mapper.RdmsCourseContentFileMapper;
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
public class RdmsCourseContentFileService {

    @Resource
    private RdmsCourseContentFileMapper rdmsCourseContentFileMapper;

    /**
     * 列表查询
     */
    public List<RdmsCourseContentFileDto> list(String courseId) {
        RdmsCourseContentFileExample example = new RdmsCourseContentFileExample();
        RdmsCourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<RdmsCourseContentFile> fileList = rdmsCourseContentFileMapper.selectByExample(example);
        fileList = fileList.stream().sorted(Comparator.comparing(RdmsCourseContentFile::getImgClass)).collect(Collectors.toList());
        return CopyUtil.copyList(fileList, RdmsCourseContentFileDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsCourseContentFileDto courseContentFileDto) {
        RdmsCourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto, RdmsCourseContentFile.class);
        if (ObjectUtils.isEmpty(courseContentFileDto.getId())) {
            this.insert(courseContentFile);
        } else {
            this.update(courseContentFile);
        }
    }

    /**
     * 新增
     */
    private void insert(RdmsCourseContentFile courseContentFile) {
        courseContentFile.setId(UuidUtil.getShortUuid());
        rdmsCourseContentFileMapper.insert(courseContentFile);
    }

    /**
     * 更新
     */
    private void update(RdmsCourseContentFile courseContentFile) {
        rdmsCourseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        rdmsCourseContentFileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据图片分类,获取课程装修图片
     * 根据课程ID和图片分类获取课程装修图片URL列表
     * 
     * @param courseId 课程ID
     * @param imgClass 图片分类
     * @return 返回图片URL列表，如果不存在则返回null
     */
    public List<String> getCourseImgUrl(String courseId, String imgClass){
        RdmsCourseContentFileExample courseContentFileExample = new RdmsCourseContentFileExample();
        RdmsCourseContentFileExample.Criteria criteria = courseContentFileExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId).andImgClassEqualTo(imgClass);
        List<RdmsCourseContentFile> courseContentFiles = rdmsCourseContentFileMapper.selectByExample(courseContentFileExample);

        if(courseContentFiles.size() == 0){
            return null;
        }
        List<RdmsCourseContentFile> courseContentFilesTemp = new ArrayList<>();
        for(RdmsCourseContentFile courseContentFile : courseContentFiles){
            //如果不想显示某张图片,只要将其顺序号设置为0 就可以了
            if(courseContentFile.getOrderNum() !=null && courseContentFile.getOrderNum() != 0){
                courseContentFilesTemp.add(courseContentFile);
            }
        }
        courseContentFilesTemp = courseContentFilesTemp.stream().sorted(Comparator.comparing(RdmsCourseContentFile::getOrderNum)).collect(Collectors.toList());

        List<String> imgList = new ArrayList<>();
        for(RdmsCourseContentFile courseContentFile : courseContentFilesTemp){
            imgList.add(courseContentFile.getUrl());
        }

        return imgList;
    }
}
