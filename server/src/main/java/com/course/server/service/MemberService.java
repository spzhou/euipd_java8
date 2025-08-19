/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.MemberActionEnum;
import com.course.server.enums.PageCategoryEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberActionMapper memberActionMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private LiveMapper liveMapper;
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private ShareUploadMapper shareUploadMapper;


    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberExample memberExample = new MemberExample();
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        PageInfo<Member> pageInfo = new PageInfo<>(memberList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberDto> memberDtoList = CopyUtil.copyList(memberList, MemberDto.class);
        pageDto.setList(memberDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(MemberDto memberDto) {
        Member member = CopyUtil.copy(memberDto, Member.class);
        if (ObjectUtils.isEmpty(memberDto.getId())) {
            this.insert(member);
        } else {
            this.update(member);
        }
    }

    /**
     * 新增
     */
    private void insert(Member member) {
        Date now = new Date();
        member.setId(UuidUtil.getShortUuid());
        member.setRegisterTime(now);
        memberMapper.insert(member);
    }

    /**
     * 更新
     */
    private void update(Member member) {
        memberMapper.updateByPrimaryKey(member);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey(id);
    }

    /**
     * 按手机号查找
     * 根据手机号查询会员信息并转换为数据传输对象
     * 
     * @param mobile 手机号
     * @return 返回会员数据传输对象
     */
    public MemberDto findByMobile(String mobile) {
        Member member = this.selectByMobile(mobile);
        return CopyUtil.copy(member, MemberDto.class);
    }

    /**
     * 按手机号查找
     * 根据手机号查询会员信息
     * 
     * @param mobile 手机号
     * @return 返回会员对象，如果不存在则返回null
     */
    public Member selectByMobile(String mobile) {
        if (ObjectUtils.isEmpty(mobile)) {
            return null;
        }
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(example);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }

    }
    /**
     * 主键查找
     * 根据主键ID查询会员信息
     * 
     * @param id 会员ID
     * @return 返回会员对象，如果不存在则返回null
     */
    public Member selectByPrimaryKey(String id) {
        if (ObjectUtils.isEmpty(id)) {
            return null;
        }
        MemberExample example = new MemberExample();
        example.createCriteria().andIdEqualTo(id);
        List<Member> memberList = memberMapper.selectByExample(example);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }

    }

    /**
     * 会员登录
     * 验证会员手机号和密码，登录成功后返回登录信息
     * 
     * @param memberDto 会员数据传输对象
     * @return 返回登录会员数据传输对象
     * @throws BusinessException 登录失败异常
     */
    public LoginMemberDto login(MemberDto memberDto) {
        Member member = selectByMobile(memberDto.getMobile());
        if (member == null) {
            LOG.info("手机号不存在, {}", memberDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
        } else {
            if (member.getPassword().equals(memberDto.getPassword())) {
                // 登录成功
                LoginMemberDto loginMemberDto = CopyUtil.copy(member, LoginMemberDto.class);
                return loginMemberDto;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", memberDto.getPassword(), member.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
            }
        }
    }

    /**
     * 重置密码
     * 根据手机号重置会员密码
     * 
     * @param memberDto 会员数据传输对象
     * @throws BusinessException 会员不存在异常
     */
    public void resetPassword(MemberDto memberDto) throws BusinessException {
        Member memberDb = this.selectByMobile(memberDto.getMobile());
        if (memberDb == null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        } else {
            Member member = new Member();
            member.setId(memberDb.getId());
            member.setPassword(memberDto.getPassword());
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }

    /**
     * 保存用户行为信息
     * 保存用户的点赞、收藏、观看等行为信息，并关联机构ID
     * 
     * @param memberActionDto 用户行为数据传输对象
     */
    public void saveMemberAction(MemberActionDto memberActionDto) {
        String shortUuid = UuidUtil.getShortUuid();
        memberActionDto.setId(shortUuid);
        if(memberActionDto.getPageCategory().equals(PageCategoryEnum.COURSE.getCode())){
            CourseExample courseExample = new CourseExample();
            CourseExample.Criteria criteria = courseExample.createCriteria();
            criteria.andIdEqualTo(memberActionDto.getParamId());
            List<Course> courseList = courseMapper.selectByExample(courseExample);
            if(courseList.size()>0){
                String loginName = courseList.get(0).getCreatorLoginname();
                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria1 = institutionExample.createCriteria();
                criteria1.andCreatorLoginnameEqualTo(loginName);
                List<Institution> institutionList = institutionMapper.selectByExample(institutionExample);
                if(institutionList.size()>0){
                    memberActionDto.setInstitutionId(institutionList.get(0).getId());
                }
            }
        }else if(memberActionDto.getPageCategory().equals(PageCategoryEnum.PRODUCT.getCode())){
            ProductExample productExample = new ProductExample();
            ProductExample.Criteria criteria = productExample.createCriteria();
            criteria.andProductIdEqualTo(memberActionDto.getParamId());
            List<Product> productList = productMapper.selectByExample(productExample);
            if(productList.size()>0){
                String loginName = productList.get(0).getLoginName();
                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria1 = institutionExample.createCriteria();
                criteria1.andCreatorLoginnameEqualTo(loginName);
                List<Institution> institutionList = institutionMapper.selectByExample(institutionExample);
                if(institutionList.size()>0){
                    memberActionDto.setInstitutionId(institutionList.get(0).getId());
                }
            }
        }else if(memberActionDto.getPageCategory().equals(PageCategoryEnum.LIVE.getCode())){
            LiveExample liveExample = new LiveExample();
            LiveExample.Criteria criteria = liveExample.createCriteria();
            criteria.andIdEqualTo(memberActionDto.getParamId());
            List<Live> liveList = liveMapper.selectByExample(liveExample);
            if(liveList.size()>0){
                String loginName = liveList.get(0).getCreatorLoginname();
                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria1 = institutionExample.createCriteria();
                criteria1.andCreatorLoginnameEqualTo(loginName);
                List<Institution> institutionList = institutionMapper.selectByExample(institutionExample);
                if(institutionList.size()>0){
                    memberActionDto.setInstitutionId(institutionList.get(0).getId());
                }
            }
        }else if(memberActionDto.getPageCategory().equals(PageCategoryEnum.SHARE.getCode())){
            ShareUploadExample shareUploadExample = new ShareUploadExample();
            ShareUploadExample.Criteria criteria = shareUploadExample.createCriteria();
            criteria.andIdEqualTo(memberActionDto.getParamId());
            List<ShareUpload> shareUploadList = shareUploadMapper.selectByExample(shareUploadExample);
            if(shareUploadList.size()>0){
                String loginName = shareUploadList.get(0).getCreatorLoginname();
                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria1 = institutionExample.createCriteria();
                criteria1.andCreatorLoginnameEqualTo(loginName);
                List<Institution> institutionList = institutionMapper.selectByExample(institutionExample);
                if(institutionList.size()>0){
                    memberActionDto.setInstitutionId(institutionList.get(0).getId());
                }
            }
        }else if(memberActionDto.getPageCategory().equals(PageCategoryEnum.INSTITUTION.getCode())){
            memberActionDto.setInstitutionId(memberActionDto.getId());
        }

        MemberAction memberAction = CopyUtil.copy(memberActionDto, MemberAction.class);
        memberActionMapper.insert(memberAction);


    }

    /**
     * 更新用户行为信息
     * 根据ID更新用户的点赞、收藏、观看等行为信息
     * 
     * @param memberActionDto 用户行为数据传输对象
     */
    public void updateMemberAction(MemberActionDto memberActionDto) {
        MemberAction memberAction = CopyUtil.copy(memberActionDto, MemberAction.class);
        MemberActionExample memberActionExample = new MemberActionExample();
        MemberActionExample.Criteria criteria = memberActionExample.createCriteria();
        criteria.andIdEqualTo(memberAction.getId());
        memberActionMapper.updateByExample(memberAction, memberActionExample);
    }

    /**
     * 获取用户行为信息
     * 根据条件查询用户的点赞、收藏、观看等行为信息
     * 
     * @param memberActionDto 用户行为数据传输对象
     * @return 返回用户行为对象，如果不存在则返回null
     */
    public MemberAction readMemberAction(MemberActionDto memberActionDto) {
        MemberActionExample memberActionExample = new MemberActionExample();
        MemberActionExample.Criteria criteria = memberActionExample.createCriteria();
        if(memberActionDto.getMemberId()==null){
            criteria.andPageCategoryEqualTo(memberActionDto.getPageCategory())
                    .andParamIdEqualTo(memberActionDto.getParamId())
                    .andMemberIdIsNull()
                    .andActionEqualTo(memberActionDto.getAction());
        }else{
            criteria.andPageCategoryEqualTo(memberActionDto.getPageCategory())
                    .andParamIdEqualTo(memberActionDto.getParamId())
                    .andMemberIdEqualTo(memberActionDto.getMemberId())
                    .andActionEqualTo(memberActionDto.getAction());
        }

        List<MemberAction> memberActionList = memberActionMapper.selectByExample(memberActionExample);
        if(memberActionList.size()>0){
            return memberActionList.get(0);
        }
        return null;
    }

    /**
     * 根据IP和今天日期获取用户行为信息
     * 查询指定用户今天的行为信息，用于防止重复操作
     * 
     * @param memberActionDto 用户行为数据传输对象
     * @return 返回用户行为对象，如果不存在或今天已操作则返回null
     */
    public MemberAction readMemberActionByIpAndToday(MemberActionDto memberActionDto) {
        MemberActionExample memberActionExample = new MemberActionExample();
        MemberActionExample.Criteria criteria = memberActionExample.createCriteria();
        if(memberActionDto.getMemberId()==null){
            return null;
        }else{
            criteria.andPageCategoryEqualTo(memberActionDto.getPageCategory())
                    .andParamIdEqualTo(memberActionDto.getParamId())
                    .andMemberIdEqualTo(memberActionDto.getMemberId())
                    .andActionEqualTo(memberActionDto.getAction());
        }
        List<MemberAction> memberActionList = memberActionMapper.selectByExample(memberActionExample);
        if(memberActionList.size()>0){
            //判断今天是否已经收藏过了
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(cal.getTime());

            String today_db = sdf.format(memberActionList.get(0).getTime());
            if(today.equals(today_db)){
                return null;
            }
            return memberActionList.get(0);
        }
        return null;
    }

    /**
     * 获取机构用户行为统计信息
     * 统计指定机构的观看、点赞、收藏总数
     * 
     * @param memberActionDto 用户行为数据传输对象
     * @return 返回行为统计数量
     */
    public Integer institutionSumInfo(MemberActionDto memberActionDto) {
        MemberActionExample memberActionExample = new MemberActionExample();
        MemberActionExample.Criteria criteria = memberActionExample.createCriteria();
        int number = 0;
        if(memberActionDto.getAction().equals(MemberActionEnum.WATCH.getCode())){
            criteria.andPageCategoryEqualTo(memberActionDto.getPageCategory())
                    .andParamIdEqualTo(memberActionDto.getParamId())
                    .andActionEqualTo(MemberActionEnum.WATCH.getCode());
            List<MemberAction> memberActionList = memberActionMapper.selectByExample(memberActionExample);

            if(memberActionList.size()>0){
                for(MemberAction memberAction : memberActionList){
                    number += memberAction.getNum();
                }
            }
        }else if(memberActionDto.getAction().equals(MemberActionEnum.LIKE.getCode())){
            criteria.andPageCategoryEqualTo(memberActionDto.getPageCategory())
                    .andParamIdEqualTo(memberActionDto.getParamId())
                    .andActionEqualTo(MemberActionEnum.LIKE.getCode());
            List<MemberAction> memberActionList = memberActionMapper.selectByExample(memberActionExample);

            if(memberActionList.size()>0){
                for(MemberAction memberAction : memberActionList){
                    number += memberAction.getNum();
                }
            }
        }else if(memberActionDto.getAction().equals(MemberActionEnum.COLLECT.getCode())){
            criteria.andPageCategoryEqualTo(memberActionDto.getPageCategory())
                    .andParamIdEqualTo(memberActionDto.getParamId())
                    .andActionEqualTo(MemberActionEnum.COLLECT.getCode());
            List<MemberAction> memberActionList = memberActionMapper.selectByExample(memberActionExample);

            if(memberActionList.size()>0){
                for(MemberAction memberAction : memberActionList){
                    number += memberAction.getNum();
                }
            }
        }

        return number;
    }
    /**
     * 查询用户行为信息
     * 统计指定对象的观看、点赞、收藏总数
     * 
     * @param memberActionDto 用户行为数据传输对象
     * @return 返回行为统计信息数据传输对象
     */
    public ActionAllInfoDto actionAllInfo(MemberActionDto memberActionDto) {
        MemberActionExample memberActionExample = new MemberActionExample();
        MemberActionExample.Criteria criteria = memberActionExample.createCriteria();
        criteria.andPageCategoryEqualTo(memberActionDto.getPageCategory())
                .andParamIdEqualTo(memberActionDto.getParamId());
        List<MemberAction> memberActionList = memberActionMapper.selectByExample(memberActionExample);
        ActionAllInfoDto actionAllInfoDto = new ActionAllInfoDto();
        int watchNum = 0;
        int likeNum = 0;
        int collectNum = 0;
        for(MemberAction memberAction : memberActionList){
            if(memberAction.getAction().equals(MemberActionEnum.WATCH.getCode())){
                watchNum += memberAction.getNum();
            }else if(memberAction.getAction().equals(MemberActionEnum.LIKE.getCode())){
                likeNum += memberAction.getNum();
            }else if(memberAction.getAction().equals(MemberActionEnum.COLLECT.getCode())){
                collectNum += memberAction.getNum();
            }
        }
        actionAllInfoDto.setLikeNum(likeNum);
        actionAllInfoDto.setCollectNum(collectNum);
        actionAllInfoDto.setWatchNum(watchNum);

        return actionAllInfoDto;
    }

    /**
     * 查询商户总的用户行为信息
     * 统计指定机构下所有对象的观看、点赞、收藏总数
     * 
     * @param memberActionDto 用户行为数据传输对象
     * @return 返回行为统计信息数据传输对象
     */
    public ActionAllInfoDto allInstitutionActionInfo(MemberActionDto memberActionDto) {
        int watchNum = 0;
        MemberActionExample memberActionExample = new MemberActionExample();
        MemberActionExample.Criteria criteria = memberActionExample.createCriteria();
        criteria.andInstitutionIdEqualTo(memberActionDto.getParamId())
                .andActionEqualTo(MemberActionEnum.WATCH.getCode());
        List<MemberAction> memberActionList = memberActionMapper.selectByExample(memberActionExample);
        for(MemberAction memberAction : memberActionList){
            watchNum += memberAction.getNum();
        }

        int likeNum = 0;
        MemberActionExample memberActionExample1 = new MemberActionExample();
        MemberActionExample.Criteria criteria1 = memberActionExample1.createCriteria();
        criteria1.andInstitutionIdEqualTo(memberActionDto.getParamId())
                .andActionEqualTo(MemberActionEnum.LIKE.getCode());
        List<MemberAction> memberActionList1 = memberActionMapper.selectByExample(memberActionExample1);
        for(MemberAction memberAction : memberActionList1){
            likeNum += memberAction.getNum();
        }

        int collectNum = 0;
        MemberActionExample memberActionExample2 = new MemberActionExample();
        MemberActionExample.Criteria criteria2 = memberActionExample2.createCriteria();
        criteria2.andInstitutionIdEqualTo(memberActionDto.getParamId())
                .andActionEqualTo(MemberActionEnum.COLLECT.getCode());
        List<MemberAction> memberActionList2 = memberActionMapper.selectByExample(memberActionExample2);
        for(MemberAction memberAction : memberActionList2){
            collectNum += memberAction.getNum();
        }

        ActionAllInfoDto actionAllInfoDto = new ActionAllInfoDto();

        actionAllInfoDto.setLikeNum(likeNum);
        actionAllInfoDto.setCollectNum(collectNum);
        actionAllInfoDto.setWatchNum(watchNum);

        return actionAllInfoDto;
    }


}
