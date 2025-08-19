/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.dto.rdms.RdmsTeacherDto;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RdmsTeacherService {

    @Resource
    private RdmsTeacherMapper rdmsTeacherMapper;
    @Resource
    private RdmsTeacherCategoryService teacherCategoryService;

    /**
     * 列表查询
     * 查询所有RDMS讲师列表
     * 
     * @return 返回讲师数据传输对象列表
     */
    public List<RdmsTeacherDto> all() {
        RdmsTeacherExample teacherExample = new RdmsTeacherExample();
        List<RdmsTeacher> teacherList = rdmsTeacherMapper.selectByExample(teacherExample);
        return CopyUtil.copyList(teacherList, RdmsTeacherDto.class);
    }

    /**
     * 列表某个用户创建的讲师查询
     * 根据登录名查询该用户创建的所有讲师
     * 
     * @param loginname 用户登录名
     * @return 返回讲师数据传输对象列表
     */
    public List<RdmsTeacherDto> all(String loginname) {
        RdmsTeacherExample teacherExample = new RdmsTeacherExample();
        RdmsTeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(loginname);
        List<RdmsTeacher> teacherList = rdmsTeacherMapper.selectByExample(teacherExample);
        return CopyUtil.copyList(teacherList, RdmsTeacherDto.class);
    }

    /**
     * 列表某个用户创建的讲师查询
     * 根据客户ID查询我的讲师列表
     * 
     * @param customerId 客户ID
     * @return 返回讲师数据传输对象列表
     */
    public List<RdmsTeacherDto> myTeacher(String customerId) {
        RdmsTeacherExample teacherExample = new RdmsTeacherExample();
        RdmsTeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        List<RdmsTeacher> teacherList = rdmsTeacherMapper.selectByExample(teacherExample);
        return CopyUtil.copyList(teacherList, RdmsTeacherDto.class);
    }

    /**
     * 列表查询
     * 分页查询当前用户创建的RDMS讲师列表
     * 
     * @param pageDto RDMS讲师分页查询对象
     */
    public void list(RdmsTeacherPageDto pageDto) {
        UserDto userDto = pageDto.getUser();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsTeacherExample teacherExample = new RdmsTeacherExample();
        RdmsTeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(userDto.getLoginName());
        List<RdmsTeacher> teacherList = rdmsTeacherMapper.selectByExample(teacherExample);
        PageInfo<RdmsTeacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsTeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, RdmsTeacherDto.class);

        pageDto.setList(teacherDtoList);
    }

    /**
     * 列表查询
     * 根据登录名分页查询RDMS讲师列表
     * 
     * @param pageDto 分页查询对象
     * @param loginName 用户登录名
     */
    public void list(PageDto pageDto,String loginName) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsTeacherExample teacherExample = new RdmsTeacherExample();
        RdmsTeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(loginName);
        List<RdmsTeacher> teacherList = rdmsTeacherMapper.selectByExample(teacherExample);
        PageInfo<RdmsTeacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsTeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, RdmsTeacherDto.class);
        pageDto.setList(teacherDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新RDMS讲师信息
     * 
     * @param teacherDto 讲师数据传输对象
     */
    @Transactional
    public void save(RdmsTeacherDto teacherDto) {
        RdmsTeacher teacher = CopyUtil.copy(teacherDto, RdmsTeacher.class);
        if (ObjectUtils.isEmpty(teacherDto.getId())) {
            this.insert(teacher);
        } else {
            this.update(teacher);
        }
    }

    /**
     * 新增
     * 创建新的RDMS讲师记录
     * 
     * @param teacher 讲师对象
     */
    private void insert(RdmsTeacher teacher) {
        teacher.setId(UuidUtil.getShortUuid());
        rdmsTeacherMapper.insert(teacher);
    }

    /**
     * 更新
     * 更新RDMS讲师信息
     * 
     * @param teacher 讲师对象
     */
    private void update(RdmsTeacher teacher) {
        rdmsTeacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 删除
     * 根据ID删除RDMS讲师记录
     * 
     * @param id 讲师ID
     */
    public void delete(String id) {
        rdmsTeacherMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查找
     * 根据ID查询讲师信息
     * 
     * @param id 讲师ID
     * @return 返回讲师数据传输对象
     */
    public RdmsTeacherDto findById(String id) {
        RdmsTeacher teacher = rdmsTeacherMapper.selectByPrimaryKey(id);
        return CopyUtil.copy(teacher, RdmsTeacherDto.class);
    }

}
