/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.CourseContentFile;
import com.course.server.domain.CourseContentFileExample;
import com.course.server.domain.ProductContentFile;
import com.course.server.domain.ProductContentFileExample;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.enums.ImgClassEnum;
import com.course.server.mapper.CourseContentFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseContentFileService {

    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * 列表查询
     * 根据课程ID查询课程内容文件列表，按图片分类排序
     * 
     * @param courseId 课程ID
     * @return 返回课程内容文件数据传输对象列表
     */
    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> fileList = courseContentFileMapper.selectByExample(example);
        /*for(CourseContentFile courseContentFile : fileList){
            switch (courseContentFile.getImgClass()){
                case "MAIN":
                    courseContentFile.setImgClass(ImgClassEnum.getImgClassEnumByCode("MAIN").getDesc());
                    break;
                case "PRODUCT":
                    courseContentFile.setImgClass(ImgClassEnum.getImgClassEnumByCode("PRODUCT").getDesc());
                    break;
                default:
                    courseContentFile.setImgClass(ImgClassEnum.getImgClassEnumByCode("MARQUEE").getDesc());
            }
        }*/
        fileList = fileList.stream().sorted(Comparator.comparing(CourseContentFile::getImgClass)).collect(Collectors.toList());
        return CopyUtil.copyList(fileList, CourseContentFileDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新课程内容文件信息
     * 
     * @param courseContentFileDto 课程内容文件数据传输对象
     */
    public void save(CourseContentFileDto courseContentFileDto) {
        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto, CourseContentFile.class);
        if (ObjectUtils.isEmpty(courseContentFileDto.getId())) {
            this.insert(courseContentFile);
        } else {
            this.update(courseContentFile);
        }
    }

    /**
     * 新增
     * 创建新的课程内容文件记录
     * 
     * @param courseContentFile 课程内容文件对象
     */
    private void insert(CourseContentFile courseContentFile) {
        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }

    /**
     * 更新
     * 更新课程内容文件信息
     * 
     * @param courseContentFile 课程内容文件对象
     */
    private void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 删除
     * 根据ID删除课程内容文件记录
     * 
     * @param id 课程内容文件ID
     */
    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
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
        CourseContentFileExample courseContentFileExample = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = courseContentFileExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId).andImgClassEqualTo(imgClass);
        List<CourseContentFile> courseContentFiles = courseContentFileMapper.selectByExample(courseContentFileExample);

        if(courseContentFiles.size() == 0){
            return null;
        }
        List<CourseContentFile> courseContentFilesTemp = new ArrayList<>();
        for(CourseContentFile courseContentFile : courseContentFiles){
            //如果不想显示某张图片,只要将其顺序号设置为0 就可以了
            if(courseContentFile.getOrderNum() !=null && courseContentFile.getOrderNum() != 0){
                courseContentFilesTemp.add(courseContentFile);
            }
        }
        courseContentFilesTemp = courseContentFilesTemp.stream().sorted(Comparator.comparing(CourseContentFile::getOrderNum)).collect(Collectors.toList());

        List<String> imgList = new ArrayList<>();
        for(CourseContentFile courseContentFile : courseContentFilesTemp){
            imgList.add(courseContentFile.getUrl());
        }

        return imgList;
    }
}
