/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.MemberCourse;
import com.course.server.domain.MemberCourseExample;
import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.MemberCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MemberCourseService {

    @Resource
    private MemberCourseMapper memberCourseMapper;

    /**
     * 列表查询
     * 分页查询会员课程报名列表
     * 
     * @param pageDto 分页查询对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);
        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberCourseDto> memberCourseDtoList = CopyUtil.copyList(memberCourseList, MemberCourseDto.class);
        pageDto.setList(memberCourseDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新会员课程报名信息
     * 
     * @param memberCourseDto 会员课程数据传输对象
     */
    public void save(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
        if (ObjectUtils.isEmpty(memberCourseDto.getId())) {
            this.insert(memberCourse);
        } else {
            this.update(memberCourse);
        }
    }

    /**
     * 新增
     * 创建新的会员课程报名记录
     * 
     * @param memberCourse 会员课程对象
     */
    private void insert(MemberCourse memberCourse) {
        Date now = new Date();
        memberCourse.setId(UuidUtil.getShortUuid());
        memberCourse.setAt(now);
        memberCourseMapper.insert(memberCourse);
    }

    /**
     * 更新
     * 更新会员课程报名信息
     * 
     * @param memberCourse 会员课程对象
     */
    private void update(MemberCourse memberCourse) {
        memberCourseMapper.updateByPrimaryKey(memberCourse);
    }

    /**
     * 删除
     * 根据ID删除会员课程报名记录
     * 
     * @param id 会员课程ID
     */
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 报名，先判断是否已报名
     * 处理会员课程报名，如果已报名则返回现有信息，否则创建新报名记录
     * 
     * @param memberCourseDto 会员课程数据传输对象
     * @return 返回报名信息数据传输对象
     */
    public MemberCourseDto enroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourseDb = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        if (memberCourseDb == null) {
            MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
            this.insert(memberCourse);
            // 将数据库信息全部返回，包括id, at
            return CopyUtil.copy(memberCourse, MemberCourseDto.class);
        } else {
            // 如果已经报名，则直接返回已报名的信息
            return CopyUtil.copy(memberCourseDb, MemberCourseDto.class);
        }
    }

    /**
     * 根据memberId和courseId查询记录
     * 根据会员ID和课程ID查询报名记录
     * 
     * @param memberId 会员ID
     * @param courseId 课程ID
     * @return 返回会员课程对象，如果不存在则返回null
     */
    public MemberCourse select(String memberId, String courseId) {
        MemberCourseExample example = new MemberCourseExample();
        example.createCriteria()
                .andCourseIdEqualTo(courseId)
                .andMemberIdEqualTo(memberId);
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberCourseList)) {
            return null;
        } else {
            return memberCourseList.get(0);
        }
    }

    /**
     * 获取报名信息
     * 根据会员ID和课程ID获取报名信息
     * 
     * @param memberCourseDto 会员课程数据传输对象
     * @return 返回报名信息数据传输对象
     */
    public MemberCourseDto getEnroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        return CopyUtil.copy(memberCourse, MemberCourseDto.class);
    }
}
