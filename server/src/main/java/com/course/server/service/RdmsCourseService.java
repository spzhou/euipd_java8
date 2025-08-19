/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.aliyun.vod20170321.models.GetPlayInfoResponse;
import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.dto.rdms.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsCourseService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCourseService.class);
    @Resource
    private RdmsCourseMapper rdmsCourseMapper;
    @Resource
    private RdmsCourseSectionMapper rdmsCourseSectionMapper;
    @Resource
    private RdmsCourseOwnCategoryService rdmsCourseOwnCategoryService;
    @Resource
    private RdmsCourseOwnCategoryMapper rdmsCourseOwnCategoryMapper;
    @Resource
    private RdmsCourseContentMapper rdmsCourseContentMapper;
    @Resource
    private RdmsTeacherService rdmsTeacherService;
    @Resource
    private RdmsCourseChapterMapper rdmsCourseChapterMapper;
    @Resource
    private RdmsSectionService sectionService;
    @Resource
    private AliyunVodService aliyunVodService;

    /**
     * 列表查询：关联课程分类表
     * @param pageDto
     */
    @Transactional
    public void list(RdmsCoursePageDto pageDto) {
        List<RdmsCourseDto> courseDtoList = new ArrayList<>();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        //1. 查出所有的课程
        RdmsCourseExample courseExample = new RdmsCourseExample();
        courseExample.setOrderByClause("sort asc");
        RdmsCourseExample.Criteria criteria2 = courseExample.createCriteria();
        criteria2.andCustomerIdEqualTo(pageDto.getCustomerId());
        List<RdmsCourse> rdmsCourses = rdmsCourseMapper.selectByExample(courseExample);
        PageInfo<RdmsCourse> pageInfo = new PageInfo<>(rdmsCourses);
        pageDto.setTotal(pageInfo.getTotal());

        courseDtoList = CopyUtil.copyList(rdmsCourses, RdmsCourseDto.class);
        pageDto.setList(courseDtoList);
    }

    /**
     * 前端课程列表查询
     * 根据分类ID筛选课程，支持分页查询已发布的课程
     * 
     * @param pageDto 包含分页参数和分类ID的查询条件
     */
    @Transactional
    public void webList(RdmsCoursePageDto pageDto) {
        List<RdmsCourseDto> courseDtoList = new ArrayList<>();
        List<RdmsCourseOwnCategory> courseCategoryList = null;
        //1. 如果categoryId不为null，就只查询对应categoryId的课程；
        if(!ObjectUtils.isEmpty(pageDto.getCategoryId())){
            RdmsCourseOwnCategoryExample courseCategoryExample = new RdmsCourseOwnCategoryExample();
            RdmsCourseOwnCategoryExample.Criteria criteria4 = courseCategoryExample.createCriteria();
            criteria4.andCategoryIdEqualTo(pageDto.getCategoryId());
            courseCategoryList = rdmsCourseOwnCategoryMapper.selectByExample(courseCategoryExample);

            List<RdmsCourse> courseList_temp = new ArrayList<>();
            //2. 根据categoryList查询对应的courseList
            for(RdmsCourseOwnCategory courseCategory: courseCategoryList){
                RdmsCourse rdmsCourse = rdmsCourseMapper.selectByPrimaryKey(courseCategory.getCourseId());
                if(rdmsCourse!=null){
                    courseList_temp.add(rdmsCourse);
                }

            }
            //机构中存在机构被删除,但是分类表中还有这个institutionId的情况
            List<RdmsCourse> courseList = new ArrayList<>();
            for(RdmsCourse course :courseList_temp){
                if(course.getSort()==null){
                    course.setSort(0);
                }
                if(course.getStatus()==null){
                    course.setStatus(CourseStatusEnum.DRAFT.getCode());
                }
                courseList.add(course);
            }

            for(RdmsCourse course : courseList){
                if(course.getStatus().equals(CourseStatusEnum.PUBLISH.getCode())){
                    RdmsCourseDto courseDto = CopyUtil.copy(course, RdmsCourseDto.class);
                    courseDtoList.add(courseDto);
                }
            }

            courseDtoList = courseDtoList.stream().sorted(Comparator.comparing(RdmsCourseDto::getSort).reversed()).collect(Collectors.toList());

            int count = courseDtoList.size();
            int page = pageDto.getPage();
            int size = pageDto.getSize();
            int end = page*size;
            if(end > count){
                end = count;
            }

            courseDtoList= courseDtoList.subList((page-1)*size, end);
            pageDto.setTotal(count);

        }else{//categoryId为空
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            List<RdmsCourse> courseList = new ArrayList<>();
            RdmsCourseExample courseExample = new RdmsCourseExample();
            courseExample.setOrderByClause("sort asc");
            RdmsCourseExample.Criteria criteria = courseExample.createCriteria();
            criteria.andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
            courseList = rdmsCourseMapper.selectByExample(courseExample);
            PageInfo<RdmsCourse> pageInfo = new PageInfo<>(courseList);
            pageDto.setTotal(pageInfo.getTotal());

            courseDtoList = CopyUtil.copyList(courseList, RdmsCourseDto.class);
        }

        pageDto.setList(courseDtoList);
    }

    /**
     * 根据课程ID获取课程信息
     * 查询指定课程的详细信息并转换为DTO对象
     * 
     * @param courseId 课程ID
     * @return 返回课程DTO对象
     */
    @Transactional
    public RdmsCourseDto getCourseDto(String courseId) {
        RdmsCourse course = rdmsCourseMapper.selectByPrimaryKey(courseId);
        RdmsCourseDto courseDto = CopyUtil.copy(course, RdmsCourseDto.class);
        return courseDto;
    }

    /**
     * 首页课程列表查询
     * 根据状态筛选课程，支持分页查询并按排序字段排序
     * 
     * @param pageDto 包含分页参数和状态筛选条件的查询对象
     */
    @Transactional
    public void indexList(RdmsCoursePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserDto userDto = pageDto.getUser();
        List<RdmsCourseDto> courseDtoList = new ArrayList<>();//myCourseMapper.list(pageDto);

        //1. 查出所有的课程
        List<RdmsCourse> courseList = new ArrayList<>();
        RdmsCourseExample courseExample = new RdmsCourseExample();
        RdmsCourseExample.Criteria criteria2 = courseExample.createCriteria();
        if(!ObjectUtils.isEmpty(pageDto.getStatus())) {
            criteria2.andStatusEqualTo(pageDto.getStatus());
            courseList = rdmsCourseMapper.selectByExample(courseExample);
        }else{
            courseList = rdmsCourseMapper.selectByExample(courseExample);
        }
        courseDtoList = CopyUtil.copyList(courseList, RdmsCourseDto.class);

        //课程排序
        courseDtoList = courseDtoList.stream().sorted(Comparator.comparing(RdmsCourseDto::getSort).reversed()).collect(Collectors.toList());

        pageDto.setList(courseDtoList);
    }

    /**
     * 新课列表查询
     * 查询已发布的新课程，按创建日期倒序排列
     * 
     * @param pageDto 分页查询对象
     * @return 返回新课程数据传输对象列表
     */
    public List<RdmsCourseDto> listNew(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCourseExample courseExample = new RdmsCourseExample();
        courseExample.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        courseExample.setOrderByClause("created_at desc");
        List<RdmsCourse> courseList = rdmsCourseMapper.selectByExample(courseExample);

        List<RdmsCourseDto> courseDtoList = CopyUtil.copyList(courseList, RdmsCourseDto.class);
        return courseDtoList;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public void save(RdmsCourseDto courseDto) {
        RdmsCourse course = CopyUtil.copy(courseDto, RdmsCourse.class);
        if (ObjectUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }

        // 批量保存课程分类
        rdmsCourseOwnCategoryService.saveBatch(course.getId(), courseDto.getCategorys());
        //取回course的Id
        courseDto.setId(course.getId());

    }

    /**
     * 新增
     */
    private void insert(RdmsCourse course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        if(course.getStatus()==null){
            course.setStatus(CourseStatusEnum.DRAFT.getCode());
        }
        rdmsCourseMapper.insert(course);
    }

    /**
     * 更新
     */
    public void update(RdmsCourse course) {
        course.setUpdatedAt(new Date());
        if(course.getStatus()==null){
            course.setStatus(CourseStatusEnum.DRAFT.getCode());
        }
        rdmsCourseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        rdmsCourseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新课程时长
     * 根据课程ID计算并更新课程的总时长
     * 
     * @param courseId 课程ID
     */
    @Transactional
    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        //myCourseMapper.updateTime(courseId);
        /*
        update course c set `time` = (select sum(`time`) from `section` where course_id = #{courseId})
        where c.id = #{courseId}
        * */
        //1. 查出当前课程的所有section,并计算所有section的time和
        RdmsCourseSectionExample sectionExample = new RdmsCourseSectionExample();
        RdmsCourseSectionExample.Criteria criteria = sectionExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<RdmsCourseSection> sectionList = rdmsCourseSectionMapper.selectByExample(sectionExample);

        Integer time = 0;
        for(RdmsCourseSection section : sectionList){
            if(section.getTime()==null){
                time+=0;
            }else{
                time+=section.getTime();
            }
        }
        //2. update此条course的时间
        RdmsCourse course = rdmsCourseMapper.selectByPrimaryKey(courseId);
        course.setTime(time);
        rdmsCourseMapper.updateByPrimaryKeySelective(course);

    }

    /**
     * 查找课程内容
     */
    public RdmsCourseContentDto findContent(String id) {
        RdmsCourseContent content = rdmsCourseContentMapper.selectByPrimaryKey(id);
        if (content == null) {
            return null;
        }
        return CopyUtil.copy(content, RdmsCourseContentDto.class);
    }

    /**
     * 保存课程内容，包含新增和修改
     */
    public int saveContent(RdmsCourseContentDto contentDto) {
        RdmsCourseContent content = CopyUtil.copy(contentDto, RdmsCourseContent.class);
        int i = rdmsCourseContentMapper.updateByPrimaryKeyWithBLOBs(content);
        if (i == 0) {
            i = rdmsCourseContentMapper.insert(content);
        }
        return i;
    }

    /**
     * 课程排序
     * 根据排序数据传输对象更新课程的排序值
     * 
     * @param sortDto 排序数据传输对象
     */
    @Transactional
    public void sort(RdmsSortDto sortDto) {
        // 修改当前记录的排序值
        RdmsCourse course = rdmsCourseMapper.selectByPrimaryKey(sortDto.getId());
        course.setSort(sortDto.getNewSort());
        rdmsCourseMapper.updateByPrimaryKey(course);
    }

    /**
     * 查找某一课程
     * 供web模块使用，只能查询已发布的课程，包含完整的课程信息
     * 
     * @param id 课程ID
     * @return 返回课程数据传输对象，如果不存在或未发布则返回null
     */
    public RdmsCourseDto findCourse(String id) {
        RdmsCourse course = rdmsCourseMapper.selectByPrimaryKey(id);
        if (course == null || !CourseStatusEnum.PUBLISH.getCode().equals(course.getStatus())) {
            return null;
        }
        RdmsCourseDto courseDto = CopyUtil.copy(course, RdmsCourseDto.class);

        // 查询内容
        RdmsCourseContent content = rdmsCourseContentMapper.selectByPrimaryKey(id);
        if (content != null) {
            courseDto.setContent(content.getContent());
        }

        // 查找讲师信息
        RdmsTeacherDto teacherDto = rdmsTeacherService.findById(courseDto.getTeacherId());
        if(teacherDto!= null){
            courseDto.setTeacher(teacherDto);
        }

        // 查找章信息
        RdmsCourseChapterExample chapterExample =new RdmsCourseChapterExample();
        RdmsCourseChapterExample.Criteria criteria = chapterExample.createCriteria();
        criteria.andCourseIdEqualTo(id).andSortIsNotNull();
        List<RdmsCourseChapter> chapterList = rdmsCourseChapterMapper.selectByExample(chapterExample);
        List<RdmsChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, RdmsChapterDto.class);

//        LOG.info(chapterDtoList.toString());
        if(chapterDtoList.size()>0){
            chapterDtoList = chapterDtoList.stream().sorted(Comparator.comparing(RdmsChapterDto::getSort)).collect(Collectors.toList());
            courseDto.setChapters(chapterDtoList);
        }

        // 查找节信息
        List<RdmsSectionDto> sectionDtoList = sectionService.listByCourse(id);
        //添加MP4播放地址
        for(RdmsSectionDto sectionDto : sectionDtoList){
            try{
                GetPlayInfoResponse getPlayInfoResponse = aliyunVodService.GetPlayInfo(sectionDto.getVod());
                boolean isMp4 = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getFormat().equals("mp4");
                String duration = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getDuration();
                String url = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getPlayURL();

                if(Double.parseDouble(duration) >1.0 && isMp4){
                    sectionDto.setUrl(url);
                }
            }catch (Exception e){
                continue;
            }
        }

        if(!sectionDtoList.isEmpty()){
            courseDto.setSections(sectionDtoList);
        }

        return courseDto;
    }

    /**
     * 查询某个用户的产品
     * 根据登录名查询用户创建的所有课程
     * 
     * @param loginName 登录名
     * @return 返回课程数据传输对象列表
     */
    public List<RdmsCourseDto> getUserProduct(String loginName) {
        RdmsCourseExample courseExample = new RdmsCourseExample();
        RdmsCourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(loginName);
        List<RdmsCourse> courseList = rdmsCourseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList, RdmsCourseDto.class);
    }

    /**
     * 根据课程ID获取课程信息
     * 通过课程ID查询课程基本信息
     * 
     * @param id 课程ID
     * @return 返回课程数据传输对象
     */
    public RdmsCourseDto getCourse(String id) {
        RdmsCourse course = rdmsCourseMapper.selectByPrimaryKey(id);
        return CopyUtil.copy(course, RdmsCourseDto.class);
    }

    /**
     * 根据节ID获取课程信息
     * 通过节ID查询对应的课程信息
     * 
     * @param sectionId 节ID
     * @return 返回课程数据传输对象，如果不存在则返回null
     */
    public RdmsCourseDto getCourseBySectionId(String sectionId){
        RdmsCourseSectionExample sectionExample = new RdmsCourseSectionExample();
        RdmsCourseSectionExample.Criteria criteria = sectionExample.createCriteria();
        criteria.andIdEqualTo(sectionId);
        List<RdmsCourseSection> sectionList = rdmsCourseSectionMapper.selectByExample(sectionExample);
        if(sectionList.size()==0){
            return null;
        }
        RdmsCourse course = rdmsCourseMapper.selectByPrimaryKey(sectionList.get(0).getCourseId());

        return CopyUtil.copy(course, RdmsCourseDto.class);
    }

}
