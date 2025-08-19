/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.constants.Constants;
import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.*;
import com.course.server.mapper.*;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitutionService {

    private static final Logger LOG = LoggerFactory.getLogger(InstitutionService.class);

    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private InstitutionOrgBrandMapper institutionOrgBrandMapper;

    @Resource
    private InstitutionService institutionService;
   @Resource
    private InstitutionCategoryService institutionCategoryService;
    @Resource
    private InstitutionCategoryMapper institutionCategoryMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private LiveMapper liveMapper;
    @Resource
    private InstitutionMemberMapper institutionMemberMapper;
    @Resource
    private InstitutionAuthorityMapper institutionAuthorityMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private InstitutionBlackListMapper institutionBlackListMapper;
    @Resource
    private MemberService memberService;


    /**
     * 获取我的机构列表
     * 根据登录名查询用户创建的机构列表
     * 
     * @param loginName 登录名
     * @return 返回机构列表
     */
    public List<Institution> myInstList(String loginName){
        InstitutionExample institutionExample = new InstitutionExample();
        InstitutionExample.Criteria criteria = institutionExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(loginName);
        List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
        return institutions;
    }

    /**
     * 获取机构权限列表
     * 根据机构ID查询机构的权限配置列表
     * 
     * @param institutionId 机构ID
     * @return 返回机构权限列表
     */
    public List<InstitutionAuthority> getInstList(String institutionId){
        InstitutionAuthorityExample institutionAuthorityExample = new InstitutionAuthorityExample();
        InstitutionAuthorityExample.Criteria criteria = institutionAuthorityExample.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId);
        List<InstitutionAuthority> institutionAuthorities = institutionAuthorityMapper.selectByExample(institutionAuthorityExample);
        return institutionAuthorities;
    }

    /**
     * 保存机构权限
     * 保存或更新机构的权限配置信息
     * 
     * @param institutionAuthority 机构权限对象
     */
    public void saveInstAuth(InstitutionAuthority institutionAuthority){
        InstitutionAuthorityExample institutionAuthorityExample = new InstitutionAuthorityExample();
        InstitutionAuthorityExample.Criteria criteria = institutionAuthorityExample.createCriteria();
        criteria.andAuthorityLoginNameEqualTo(institutionAuthority.getAuthorityLoginName())
                .andInstitutionIdEqualTo(institutionAuthority.getInstitutionId());
        List<InstitutionAuthority> institutionAuthorities = institutionAuthorityMapper.selectByExample(institutionAuthorityExample);
        if(institutionAuthorities.size()==0){
            String uid = UuidUtil.getShortUuid();
            institutionAuthority.setId(uid);
            institutionAuthorityMapper.insert(institutionAuthority);
        }
    }


    /**
     * 列表查询：公司课程分类表
     * 和课程的分类一致,不再做新的分类
     * 
     * @param pageDto 分页查询对象
     */
    @Transactional
    public void list(InstitutionPageDto pageDto) {
        UserDto userDto = pageDto.getUser();
        List<InstitutionDto> institutionDtoList = new ArrayList<>();
        List<Institution> institutionList = new ArrayList<>();

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        //1. 查出所有的机构
        InstitutionExample institutionExample = new InstitutionExample();
        institutionExample.setOrderByClause("sort desc");
        InstitutionExample.Criteria criteria = institutionExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(userDto.getLoginName());
        institutionList = institutionMapper.selectByExample(institutionExample);
        PageInfo<Institution> pageInfo = new PageInfo<>(institutionList);
        pageDto.setTotal(pageInfo.getTotal());

        for(Institution institution: institutionList){
            InstitutionDto institutionDto = new InstitutionDto();
            BeanUtils.copyProperties(institution,institutionDto);
            institutionDto.setRoleEnum(RoleEnum.MASTER.getCode());
            institutionDtoList.add(institutionDto);
        }

        //为机构增加频道信息
        for(InstitutionDto institutionDto : institutionDtoList){
            ChannelExample channelExample = new ChannelExample();
            ChannelExample.Criteria criteria2 = channelExample.createCriteria();
            criteria2.andUserLoginnameEqualTo(institutionDto.getCreatorLoginname());
            List<Channel> channels = channelMapper.selectByExample(channelExample);
            if(channels.size()>0){
                institutionDto.setChannelId(channels.get(0).getChannelId());
            }
        }

        pageDto.setList(institutionDtoList);
    }

    /**
     * 品牌列表查询
     * 分页查询机构品牌信息，支持分类筛选
     * 
     * @param pageDto 分页查询参数
     */
    @Transactional
    public void listBrand(InstitutionPageDto pageDto) {
//        LOG.info("进入listBrand函数进行处理");
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        InstitutionOrgBrandExample institutionOrgBrandExample = new InstitutionOrgBrandExample();
        institutionOrgBrandExample.setOrderByClause("created_at desc, name asc");
        List<InstitutionOrgBrand> institutionOrgBrandList = institutionOrgBrandMapper.selectByExample(institutionOrgBrandExample);
        PageInfo<InstitutionOrgBrand> pageInfo = new PageInfo<>(institutionOrgBrandList);
        pageDto.setTotal(pageInfo.getTotal());

        List<InstitutionOrgBrandDto> institutionOrgBrandDtoList = CopyUtil.copyList(institutionOrgBrandList, InstitutionOrgBrandDto.class);
        for(InstitutionOrgBrandDto institutionOrgBrandDto : institutionOrgBrandDtoList){
            InstitutionCategoryExample institutionCategoryExample = new InstitutionCategoryExample();
            InstitutionCategoryExample.Criteria criteria = institutionCategoryExample.createCriteria();
            criteria.andOrgIdEqualTo(institutionOrgBrandDto.getId());
            List<InstitutionCategory> institutionCategories = institutionCategoryMapper.selectByExample(institutionCategoryExample);
            if(institutionCategories.size()>0){
                String institutionId = institutionCategories.get(0).getInstitutionId();
                if(institutionId != null){
                    institutionOrgBrandDto.setHasInstitution(1);
                }else {
                    institutionOrgBrandDto.setHasInstitution(0);
                }
            }
        }

        pageDto.setList(institutionOrgBrandDtoList);
    }


    /**
     * Web端机构列表查询
     * 支持分类筛选的机构列表查询，为前端提供机构展示数据
     * 
     * @param pageDto 分页查询参数
     */
    @Transactional
    public void webList(InstitutionPageDto pageDto) {
        List<InstitutionDto> institutionDtoList = new ArrayList<>();
        //1. 如果categoryId不为null，就只查询对应categoryId的课程；
        List<InstitutionCategory> institutionCategoryList = null;
        if(!ObjectUtils.isEmpty(pageDto.getCategoryId())){
            InstitutionCategoryExample institutionCategoryExample = new InstitutionCategoryExample();
            InstitutionCategoryExample.Criteria criteria4 = institutionCategoryExample.createCriteria();
            criteria4.andCategoryIdEqualTo(pageDto.getCategoryId());
            institutionCategoryList = institutionCategoryMapper.selectByExample(institutionCategoryExample);

            List<Institution> institutionList_temp = new ArrayList<>();
            //2. 根据categoryList查询对应的courseList
            for(InstitutionCategory institutionCategory: institutionCategoryList){
                Institution institution1 = institutionMapper.selectByPrimaryKey(institutionCategory.getInstitutionId());
                if(institution1!=null){
                    institutionList_temp.add(institution1);
                }

            }
            //机构中存在机构被删除,但是分类表中还有这个institutionId的情况
            List<Institution> institutionList = new ArrayList<>();
            for(Institution institution :institutionList_temp){
                if(institution.getSort()==null){
                    institution.setSort(0);
                }
                if(institution.getStatus()==null){
                    institution.setStatus(InstitutionStatusEnum.CLOSED.getCode());
                }
                institutionList.add(institution);
            }

            for(Institution institution : institutionList){
                if(institution.getStatus().equals(InstitutionStatusEnum.OPENING.getCode())){
                    InstitutionDto institutionDto = CopyUtil.copy(institution, InstitutionDto.class);
                    institutionDtoList.add(institutionDto);
                }
            }

            institutionDtoList = institutionDtoList.stream().sorted(Comparator.comparing(InstitutionDto::getSort).reversed()).collect(Collectors.toList());

            int count = institutionDtoList.size();
            int page = pageDto.getPage();
            int size = pageDto.getSize();
            int end = page*size;
            if(end > count){
                end = count;
            }

            institutionDtoList= institutionDtoList.subList((page-1)*size, end);
            pageDto.setTotal(count);

        }else{//categoryId为空
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            List<Institution> institutionList = new ArrayList<>();

            //1. 查出所有的机构
            InstitutionExample institutionExample = new InstitutionExample();
            institutionExample.setOrderByClause("sort desc");
            InstitutionExample.Criteria criteria = institutionExample.createCriteria();
            criteria.andStatusEqualTo(InstitutionStatusEnum.OPENING.getCode());
            institutionList = institutionMapper.selectByExample(institutionExample);
            PageInfo<Institution> pageInfo = new PageInfo<>(institutionList);
            pageDto.setTotal(pageInfo.getTotal());

            institutionDtoList = CopyUtil.copyList(institutionList, InstitutionDto.class);
        }

        //为机构增加频道信息
        for(InstitutionDto institutionDto : institutionDtoList){
            ChannelExample channelExample = new ChannelExample();
            ChannelExample.Criteria criteria = channelExample.createCriteria();
            criteria.andUserLoginnameEqualTo(institutionDto.getCreatorLoginname());
            List<Channel> channels = channelMapper.selectByExample(channelExample);
            if(channels.size()>0){
                institutionDto.setChannelId(channels.get(0).getChannelId());
            }
        }

        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        for(InstitutionDto institutionDto : institutionDtoList){
            memberActionDto.setParamId(institutionDto.getId()); //给机构参数
            ActionAllInfoDto actionAllInfoDto = memberService.allInstitutionActionInfo(memberActionDto);
            if(actionAllInfoDto!=null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            institutionDto.setWatchNum(actionAllInfoDto.getWatchNum());
            institutionDto.setLikeNum(actionAllInfoDto.getLikeNum());
            institutionDto.setCollectNum(actionAllInfoDto.getCollectNum());
        }

        pageDto.setList(institutionDtoList);
    }


    /**
     * 品牌列表查询
     * 分页查询机构品牌信息，支持分类筛选和排序
     * 
     * @param pageDto 分页查询参数
     */
    @Transactional
    public void brandList(InstitutionPageDto pageDto) {
        List<InstitutionOrgBrandDto> institutionOrgBrandDtoList = new ArrayList<>();
        //1. 如果categoryId不为null，就只查询对应categoryId的课程；
        List<InstitutionCategory> institutionCategoryList = null;
        if(!ObjectUtils.isEmpty(pageDto.getCategoryId())){
            InstitutionCategoryExample institutionCategoryExample = new InstitutionCategoryExample();
            InstitutionCategoryExample.Criteria criteria4 = institutionCategoryExample.createCriteria();
            criteria4.andCategoryIdEqualTo(pageDto.getCategoryId());
            institutionCategoryList = institutionCategoryMapper.selectByExample(institutionCategoryExample);

            List<InstitutionOrgBrand> institutionOrgBrandList = new ArrayList<>();
            //2. 根据categoryList查询对应的courseList
            for(InstitutionCategory institutionCategory: institutionCategoryList){
                InstitutionOrgBrand institutionOrgBrand = institutionOrgBrandMapper.selectByPrimaryKey(institutionCategory.getOrgId());
                if(institutionOrgBrand!=null){
                    institutionOrgBrandList.add(institutionOrgBrand);
                }
            }

//            institutionOrgBrandList = institutionOrgBrandList.stream().sorted(Comparator.comparing(InstitutionOrgBrand::getWatch).reversed()).collect(Collectors.toList());

            int count = institutionOrgBrandList.size();
            int page = pageDto.getPage();
            int size = pageDto.getSize();
            int end = page*size;
            if(end > count){
                end = count;
            }

            institutionOrgBrandList= institutionOrgBrandList.subList((page-1)*size, end);
            pageDto.setTotal(count);

            institutionOrgBrandDtoList = CopyUtil.copyList(institutionOrgBrandList, InstitutionOrgBrandDto.class);

            for(InstitutionOrgBrandDto institutionOrgBrandDto: institutionOrgBrandDtoList ){
                Institution institution = institutionMapper.selectByPrimaryKey(institutionOrgBrandDto.getId());
                if(institution!=null){
                    institutionOrgBrandDto.setHasInstitution(1);
                }else{
                    institutionOrgBrandDto.setHasInstitution(0);
                }
            }
        }else{//categoryId为空
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            List<InstitutionOrgBrand> institutionOrgBrandList = new ArrayList<>();
            InstitutionOrgBrandExample institutionOrgBrandExample = new InstitutionOrgBrandExample();
            institutionOrgBrandExample.setOrderByClause("watch desc");
            InstitutionOrgBrandExample.Criteria criteria = institutionOrgBrandExample.createCriteria();
            institutionOrgBrandList = institutionOrgBrandMapper.selectByExample(institutionOrgBrandExample);
            PageInfo<InstitutionOrgBrand> pageInfo = new PageInfo<>(institutionOrgBrandList);
            pageDto.setTotal(pageInfo.getTotal());

            institutionOrgBrandDtoList = CopyUtil.copyList(institutionOrgBrandList, InstitutionOrgBrandDto.class);

            for(InstitutionOrgBrandDto institutionOrgBrandDto: institutionOrgBrandDtoList ){
                Institution institution = institutionMapper.selectByPrimaryKey(institutionOrgBrandDto.getId());
                if(institution!=null){
                    institutionOrgBrandDto.setHasInstitution(1);
                }else{
                    institutionOrgBrandDto.setHasInstitution(0);
                }
            }
        }

        pageDto.setList(institutionOrgBrandDtoList);
    }


    /**
     * 获取机构详细信息
     * 根据机构ID查询机构详细信息，包括频道信息和用户行为统计
     * 
     * @param institutionId 机构ID
     * @return 返回机构详细信息
     */
    @Transactional
    public InstitutionDto getInst(String institutionId) {

        Institution institution = institutionMapper.selectByPrimaryKey(institutionId);
        InstitutionDto institutionDto = CopyUtil.copy(institution, InstitutionDto.class);

        ChannelExample channelExample = new ChannelExample();
        ChannelExample.Criteria criteria = channelExample.createCriteria();
        criteria.andUserLoginnameEqualTo(institutionDto.getCreatorLoginname());
        List<Channel> channels = channelMapper.selectByExample(channelExample);
        if(channels.size()>0){
            institutionDto.setChannelId(channels.get(0).getChannelId());
        }

        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setParamId(institutionDto.getId()); //给机构参数
        ActionAllInfoDto actionAllInfoDto = memberService.allInstitutionActionInfo(memberActionDto);
        if(actionAllInfoDto!=null){
            actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
            actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
            actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
        }
        institutionDto.setWatchNum(actionAllInfoDto.getWatchNum());
        institutionDto.setLikeNum(actionAllInfoDto.getLikeNum());
        institutionDto.setCollectNum(actionAllInfoDto.getCollectNum());
        return institutionDto;
    }


    /**
     * 首页机构列表
     * 查询首页展示的机构列表，支持状态筛选
     * 
     * @param pageDto 分页查询对象
     */
    @Transactional
    public void indexList(InstitutionPageDto pageDto) {
        UserDto userDto = pageDto.getUser();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<InstitutionDto> institutionDtoList = new ArrayList<>();

        //1. 查出所有的机构
        InstitutionExample institutionExample = new InstitutionExample();
        institutionExample.setOrderByClause("sort desc");
        InstitutionExample.Criteria criteria = institutionExample.createCriteria();
        criteria.andStatusEqualTo(InstitutionStatusEnum.OPENING.getCode());

        List<Institution> institutionList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(pageDto.getStatus())) {
            criteria.andStatusEqualTo(pageDto.getStatus());
            institutionList = institutionMapper.selectByExample(institutionExample);
        }else{
            institutionList = institutionMapper.selectByExample(institutionExample);
        }

        for(Institution institution : institutionList){
            MemberActionDto memberActionDto = new MemberActionDto();
            memberActionDto.setParamId(institution.getId());
            ActionAllInfoDto actionAllInfoDto = memberService.allInstitutionActionInfo(memberActionDto);
            if(actionAllInfoDto!=null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            InstitutionDto institutionDto = CopyUtil.copy(institution, InstitutionDto.class);
            institutionDto.setWatchNum(actionAllInfoDto.getWatchNum());
            institutionDto.setLikeNum(actionAllInfoDto.getLikeNum());
            institutionDto.setCollectNum(actionAllInfoDto.getCollectNum());
            institutionDtoList.add(institutionDto);
        }

        PageInfo<InstitutionDto> pageInfo = new PageInfo<>(institutionDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(institutionDtoList);
    }


    /**
     * 我的邀请
     * 查询我发出的邀请列表
     * 
     * @param pageDto 分页查询对象
     */
    @Transactional
    public void myInviteList(InstitutionMemberPageDto pageDto) {
        UserDto userDto = pageDto.getUser();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<InstitutionMember> institutionMemberList = new ArrayList<>();
        if(userDto.getLoginName()!=null){
            //得到自己的机构ID
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria = institutionExample.createCriteria();
            criteria.andCreatorLoginnameEqualTo(userDto.getLoginName());
            List<Institution> institutionList = institutionMapper.selectByExample(institutionExample);
            String institutionId = "";
            if(institutionList.size()>0){
                institutionId = institutionList.get(0).getId();
            }
            //根据机构ID查看哪些人已经加盟进来了

            InstitutionMemberExample institutionMemberExample = new InstitutionMemberExample();
            InstitutionMemberExample.Criteria criteria1 = institutionMemberExample.createCriteria();
            criteria1.andInstitutionIdEqualTo(institutionId);
            institutionMemberList = institutionMemberMapper.selectByExample(institutionMemberExample);
        }
        List<InstitutionMemberDto> institutionMemberDtoList = new ArrayList<>();

        for(InstitutionMember institutionMember : institutionMemberList){
            if(!institutionMember.getMemberLoginName().equals(userDto.getLoginName())){
                InstitutionMemberDto institutionMemberDto = CopyUtil.copy(institutionMember, InstitutionMemberDto.class);
                institutionMemberDtoList.add(institutionMemberDto);
            }
        }


        PageInfo<InstitutionMemberDto> pageInfo = new PageInfo<>(institutionMemberDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(institutionMemberDtoList);
    }

    /**
     * 邀请我的
     * 查询我收到的邀请列表
     * 
     * @param pageDto 分页查询对象
     */
    @Transactional
    public void inviteMeList(InstitutionMemberPageDto pageDto) {
        UserDto userDto = pageDto.getUser();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
//        List<InstitutionMember> institutionMemberList = new ArrayList<>();
        //通过自己的loginName查看是否有邀请记录
        InstitutionMemberExample institutionMemberExample = new InstitutionMemberExample();
        InstitutionMemberExample.Criteria criteria = institutionMemberExample.createCriteria();
        criteria.andMemberLoginNameEqualTo(userDto.getLoginName())
                .andStatusNotEqualTo(InstMemberStatusEnum.REFUSE.getCode());
        List<InstitutionMember> institutionMembers = institutionMemberMapper.selectByExample(institutionMemberExample);
        //如果有,则查出邀请机构的信息, 卡片可以通过点击图片进入发起邀请的机构
        List<InstitutionMemberDto> institutionMemberDtos = new ArrayList<>();
        for(InstitutionMember institutionMember : institutionMembers){
            InstitutionMemberDto institutionMemberDto = CopyUtil.copy(institutionMember, InstitutionMemberDto.class);
            //查institution表得到机构的名称和封面
            Institution institution = institutionMapper.selectByPrimaryKey(institutionMember.getInstitutionId());
            institutionMemberDto.setInstitutionName(institution.getName());
            institutionMemberDto.setInstitutionImage(institution.getImage());
            institutionMemberDtos.add(institutionMemberDto);
        }

        List<InstitutionMemberDto> institutionMemberDtoList = new ArrayList<>();
        //查出userDto.loginName对应的机构
        String myselfInstId = "";
        InstitutionExample institutionExample = new InstitutionExample();
        InstitutionExample.Criteria criteria1= institutionExample.createCriteria();
        criteria1.andCreatorLoginnameEqualTo(userDto.getLoginName());
        List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
        if(institutions.size()>0){
            myselfInstId= institutions.get(0).getId();
        }
        for(InstitutionMemberDto institutionMemberDto : institutionMemberDtos){
            //在邀请我的记录中,机构不是自己的,将数据选择出来
            if(!institutionMemberDto.getInstitutionId().equals(myselfInstId)){
                institutionMemberDtoList.add(institutionMemberDto);
            }
        }

        PageInfo<InstitutionMemberDto> pageInfo = new PageInfo<>(institutionMemberDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(institutionMemberDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public void save(InstitutionDto institutionDto) {
        Institution institution = CopyUtil.copy(institutionDto, Institution.class);
        if (ObjectUtils.isEmpty(institutionDto.getId())) {
            this.insert(institution);
            addInstitutionMemver(institution);

            Date now = new Date();
            InstitutionOrgBrand institutionOrgBrand = CopyUtil.copy(institutionDto, InstitutionOrgBrand.class);
            institutionOrgBrand.setCreatedAt(now);
            institutionOrgBrand.setUpdatedAt(now);
            institutionOrgBrand.setId(UuidUtil.getShortUuid());
            institutionOrgBrandMapper.insert(institutionOrgBrand);

            // 批量保存机构分类
            if(institutionDto.getCategorys()==null){
                List<CategoryDto> categorys = new ArrayList<>();
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId("00000100");
                categoryDto.setParent("00000000");
                categoryDto.setName("默认分类");
                categoryDto.setSort(1);
                categorys.add(categoryDto);
                institutionDto.setCategorys(categorys);
            }
            institutionCategoryService.saveBatch(institution.getId(),institutionOrgBrand.getId(), institutionDto.getCategorys());

        } else {
            this.update(institution);

            InstitutionOrgBrand institutionOrgBrand = CopyUtil.copy(institutionDto, InstitutionOrgBrand.class);
            institutionOrgBrand.setUpdatedAt(new Date());
            institutionOrgBrandMapper.updateByPrimaryKey(institutionOrgBrand);


            List<InstitutionCategoryDto> institutionCategoryDtos = institutionCategoryService.listByCourse(institution.getId());
            String orgId = institutionCategoryDtos.get(0).getOrgId();
            // 批量保存机构分类
            if(institutionDto.getCategorys()==null){
                List<CategoryDto> categorys = new ArrayList<>();
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId("00000100");
                categoryDto.setParent("00000000");
                categoryDto.setName("默认分类");
                categoryDto.setSort(1);
                categorys.add(categoryDto);
                institutionDto.setCategorys(categorys);
            }
            institutionCategoryService.saveBatch(institution.getId(), orgId, institutionDto.getCategorys());
        }
    }

    /**
     * 同步机构数据
     * 将机构数据同步到机构品牌表中
     */
    @Transactional
    public void synch() {
        InstitutionExample institutionExample = new InstitutionExample();
        List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
        for(Institution institution : institutions){
            //institutionId 和 orgId是相同的
            //后续早操过过程中, 创建商铺时, 将orgId作为institutionId填写institution表

            InstitutionOrgBrand institutionOrgBrand = CopyUtil.copy(institution, InstitutionOrgBrand.class);
            InstitutionOrgBrand institutionOrgBrand1 = institutionOrgBrandMapper.selectByPrimaryKey(institutionOrgBrand.getId());
            if(institutionOrgBrand1==null){
                institutionOrgBrandMapper.insert(institutionOrgBrand);
            }else{
                institutionOrgBrandMapper.updateByPrimaryKey(institutionOrgBrand);
            }

        }
    }

    /**
     * 保存机构品牌
     * 保存或更新机构品牌信息
     * 
     * @param institutionOrgBrandDto 机构品牌数据传输对象
     */
    @Transactional
    public void saveBrand(InstitutionOrgBrandDto institutionOrgBrandDto) {
        if (ObjectUtils.isEmpty(institutionOrgBrandDto.getId())) {
            this.insertBrand(institutionOrgBrandDto);
        } else {
            this.updateBrand(institutionOrgBrandDto);
        }
    }

    /**
     * 根据名称查找机构品牌
     * 通过机构名称查询对应的机构品牌信息
     * 
     * @param instName 机构名称
     * @return 返回机构品牌信息，如果不存在则返回null
     */
    @Transactional
    public InstitutionOrgBrandDto findByName(String instName) {
        InstitutionOrgBrandExample institutionOrgBrandExample = new InstitutionOrgBrandExample();
        InstitutionOrgBrandExample.Criteria criteria = institutionOrgBrandExample.createCriteria();
        criteria.andNameEqualTo(instName);
        List<InstitutionOrgBrand> institutionOrgBrandList = institutionOrgBrandMapper.selectByExample(institutionOrgBrandExample);
        if(institutionOrgBrandList.size()>0){
            return CopyUtil.copy(institutionOrgBrandList.get(0), InstitutionOrgBrandDto.class);
        }
        return null;
    }

    public void addInstitutionMemver(Institution institution) {
        //保存机构成员
        InstitutionMemberExample institutionMemberExample = new InstitutionMemberExample();
        InstitutionMemberExample.Criteria criteria = institutionMemberExample.createCriteria();
        criteria.andMemberLoginNameEqualTo(institution.getCreatorLoginname())
                .andInstitutionIdEqualTo(institution.getId());
        List<InstitutionMember> institutionMembers = institutionMemberMapper.selectByExample(institutionMemberExample);
        if(institutionMembers.size()==0){
            //insert
            InstitutionMember institutionMember = new InstitutionMember();
            String shortUid = UuidUtil.getShortUuid();
            institutionMember.setId(shortUid);
            institutionMember.setInstitutionId(institution.getId());
            institutionMember.setMemberLoginName(institution.getCreatorLoginname());
            institutionMember.setStatus(InstMemberStatusEnum.PASS.getCode());

            //查询店主信息
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria1 = userExample.createCriteria();
            criteria1.andLoginNameEqualTo(institution.getCreatorLoginname());
            List<User> users = userMapper.selectByExample(userExample);
            if(users.size()>0){
                institutionMember.setName(users.get(0).getName());
                institutionMember.setRegistName(users.get(0).getName());
            }
            institutionMemberMapper.insert(institutionMember);
        }
    }


    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public boolean memberInvite(InstitutionMemberDto institutionMemberDto) {
        //根据loginName和真实姓名去查user表,确认用户存在,并读取用户的头像等信息
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(institutionMemberDto.getMemberLoginName())
                .andNameEqualTo(institutionMemberDto.getName());

        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size()>0){
            InstitutionMember institutionMember = CopyUtil.copy(institutionMemberDto, InstitutionMember.class);
            institutionMember.setStatus(InstMemberStatusEnum.INVITING.getCode());
            if (ObjectUtils.isEmpty(institutionMember.getId())) {
                this.insertMember(institutionMember);
            } else {
                this.updateMember(institutionMember);
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 接受对方邀请
     * 接受机构邀请，将邀请状态改为通过
     * 
     * @param institutionMemberDto 机构成员数据传输对象
     */
    @Transactional
    public void acceptInvite(InstitutionMemberDto institutionMemberDto) {
        //读出相应的记录.将状态改为Pass
        InstitutionMember institutionMember = CopyUtil.copy(institutionMemberDto, InstitutionMember.class);
        institutionMember.setStatus(InstMemberStatusEnum.PASS.getCode());
        institutionMember.setProfileImg(institutionMemberDto.getInstitutionImage());
        institutionMember.setUpdateTime(new Date());
        institutionMemberMapper.updateByPrimaryKey(institutionMember);
    }

    /**
     * 拒绝对方邀请
     * 拒绝机构邀请，将邀请状态改为拒绝
     * 
     * @param institutionMemberDto 机构成员数据传输对象
     */
    @Transactional
    public void refuseInvite(InstitutionMemberDto institutionMemberDto) {
        //读出相应的记录.将状态改为Pass
        InstitutionMember institutionMember = CopyUtil.copy(institutionMemberDto, InstitutionMember.class);
        institutionMember.setStatus(InstMemberStatusEnum.REFUSE.getCode());
        institutionMember.setProfileImg(institutionMemberDto.getInstitutionImage());
        institutionMember.setUpdateTime(new Date());
        institutionMemberMapper.updateByPrimaryKey(institutionMember);
    }

    /**
     * 新增
     * 创建新的机构记录
     * 
     * @param institution 机构对象
     */
    public void insert(Institution institution) {
        Date now = new Date();
        institution.setCreatedAt(now);
        institution.setUpdatedAt(now);
        if(institution.getId() == null){
            institution.setId(UuidUtil.getShortUuid());
        }
        institutionMapper.insert(institution);
    }

    /**
     * 新增机构品牌
     * 创建新的机构品牌记录
     * 
     * @param institutionOrgBrandDto 机构品牌数据传输对象
     */
    public void insertBrand(InstitutionOrgBrandDto institutionOrgBrandDto) {
        if(institutionService.findByName(institutionOrgBrandDto.getName())!=null){
            institutionOrgBrandDto.setMassage("存在相同的机构名称!");
            return;
        }

        Date now = new Date();
        InstitutionOrgBrand institutionOrgBrand = CopyUtil.copy(institutionOrgBrandDto, InstitutionOrgBrand.class);
        institutionOrgBrand.setCreatedAt(now);
        institutionOrgBrand.setUpdatedAt(now);
        institutionOrgBrand.setId(UuidUtil.getShortUuid());
        institutionOrgBrandMapper.insert(institutionOrgBrand);

        // 批量保存机构分类
        if(institutionOrgBrandDto.getCategorys()==null){
            List<CategoryDto> categorys = new ArrayList<>();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId("00000100");
            categoryDto.setParent("00000000");
            categoryDto.setName("默认分类");
            categoryDto.setSort(1);
            categorys.add(categoryDto);
            institutionOrgBrandDto.setCategorys(categorys);
        }
        //注意: institutionOrgBrand 的 id 对应的在保存category表中 是 org_id
        //当根据名片进一步创建机构的时候, 根据org_id对应的将 institutionId填写进去
        institutionCategoryService.saveOrgBrandBatch(null , institutionOrgBrand.getId(), institutionOrgBrandDto.getCategorys());
    }

    /**
     * 新增
     * 创建新的机构成员记录
     * 
     * @param institutionMember 机构成员对象
     */
    private void insertMember(InstitutionMember institutionMember) {
        Date now = new Date();
        institutionMember.setCreateTime(now);
        institutionMember.setUpdateTime(now);
        institutionMember.setId(UuidUtil.getShortUuid());
        institutionMemberMapper.insert(institutionMember);
    }

    /**
     * 更新
     * 更新机构信息
     * 
     * @param institution 机构对象
     */
    public void update(Institution institution) {
        institution.setUpdatedAt(new Date());
        institutionMapper.updateByPrimaryKey(institution);
    }

    /**
     * 更新机构品牌
     * 更新机构品牌信息，包括分类信息的同步更新
     * 
     * @param institutionOrgBrandDto 机构品牌数据传输对象
     */
    public void updateBrand(InstitutionOrgBrandDto institutionOrgBrandDto) {
        InstitutionOrgBrand institutionOrgBrand = CopyUtil.copy(institutionOrgBrandDto, InstitutionOrgBrand.class);
        institutionOrgBrand.setUpdatedAt(new Date());
        institutionOrgBrandMapper.updateByPrimaryKey(institutionOrgBrand);

        // 批量保存机构分类
        if(institutionOrgBrandDto.getCategorys()==null){
            List<CategoryDto> categorys = new ArrayList<>();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId("00000100");
            categoryDto.setParent("00000000");
            categoryDto.setName("默认分类");
            categoryDto.setSort(1);
            categorys.add(categoryDto);
            institutionOrgBrandDto.setCategorys(categorys);
        }

        //根据org_id查category表,看对应的是否有institutionid,如果有,需要读出来,对要更新的item进行数据补充
        InstitutionCategoryExample institutionCategoryExample = new InstitutionCategoryExample();
        InstitutionCategoryExample.Criteria criteria = institutionCategoryExample.createCriteria();
        criteria.andOrgIdEqualTo(institutionOrgBrand.getId());
        List<InstitutionCategory> institutionCategories = institutionCategoryMapper.selectByExample(institutionCategoryExample);
        if(institutionCategories.size()==0){
            institutionCategoryService.saveOrgBrandBatch(null, institutionOrgBrand.getId(), institutionOrgBrandDto.getCategorys());
            return;
        }
        String institutionId = institutionCategories.get(0).getInstitutionId();
        institutionCategoryService.saveOrgBrandBatch(institutionId, institutionOrgBrand.getId(), institutionOrgBrandDto.getCategorys());
        if(institutionId!=null){
            Institution institution1 = CopyUtil.copy(institutionOrgBrandDto,Institution.class);
            institution1.setId(institutionId); //使用institutionID替换掉orgId
            institutionMapper.updateByPrimaryKeySelective(institution1);
        }
    }

    /**
     * 更新
     * 更新机构成员信息
     * 
     * @param institutionMember 机构成员对象
     */
    private void updateMember(InstitutionMember institutionMember) {
        institutionMember.setUpdateTime(new Date());
        institutionMemberMapper.updateByPrimaryKey(institutionMember);
    }

    /**
     * 删除
     * 根据ID删除机构记录
     * 
     * @param id 机构ID
     */
    public void delete(String id) {
        institutionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除机构品牌
     * 根据ID删除机构品牌记录
     * 
     * @param id 机构品牌ID
     */
    public void brandDelete(String id) {
        institutionOrgBrandMapper.deleteByPrimaryKey(id);
    }


   /**
     * 删除邀请
     * 根据ID删除机构邀请记录
     * 
     * @param id 邀请记录ID
     */
    public void deleteInvite(String id) {
        institutionMemberMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取机构产品列表
     * 查询机构下所有成员的产品信息
     * 
     * @param institutionId 机构ID
     * @return 返回产品列表
     */
    public List<ProductDto> getProductList(String institutionId){
        //先查机构的成员
        InstitutionMemberExample institutionMemberExample = new InstitutionMemberExample();
        InstitutionMemberExample.Criteria criteria = institutionMemberExample.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId);
        List<InstitutionMember> institutionMembers = institutionMemberMapper.selectByExample(institutionMemberExample);
        //再查所有成员的产品
        List<ProductDto> allProductList = new ArrayList<>();
        for(InstitutionMember institutionMember : institutionMembers){
            ProductExample productExample = new ProductExample();
            ProductExample.Criteria criteria1 = productExample.createCriteria();
            criteria1.andLoginNameEqualTo(institutionMember.getMemberLoginName());
            criteria1.andStatusEqualTo(ProductStatusEnum.PUBLISH.getCode());
            List<Product> productList = productMapper.selectByExample(productExample);
            for(Product product : productList){
                allProductList.add(CopyUtil.copy(product, ProductDto.class));
            }
        }

        //为产品卡片添加机构信息
        for(ProductDto productDto : allProductList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria9 = institutionExample.createCriteria();
            criteria9.andCreatorLoginnameEqualTo(productDto.getLoginName());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                productDto.setInstitutionName(institutions.get(0).getName());
                productDto.setInstitutionId(institutions.get(0).getId());
                productDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setPageCategory(PageCategoryEnum.PRODUCT.getCode());
        for(ProductDto productDto : allProductList){
            memberActionDto.setParamId(productDto.getProductId());
            ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
            if(actionAllInfoDto!=null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            productDto.setWatchNum(actionAllInfoDto.getWatchNum());
            productDto.setLikeNum(actionAllInfoDto.getLikeNum());
            productDto.setCollectNum(actionAllInfoDto.getCollectNum());
        }

        return allProductList;
    }

    /**
     * 获取机构课程列表
     * 查询机构下所有成员的课程信息
     * 
     * @param institutionId 机构ID
     * @return 返回课程列表
     */
    public List<CourseDto> getCourseList(String institutionId){
        //先查机构的成员
        InstitutionMemberExample institutionMemberExample = new InstitutionMemberExample();
        InstitutionMemberExample.Criteria criteria = institutionMemberExample.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId);
        List<InstitutionMember> institutionMembers = institutionMemberMapper.selectByExample(institutionMemberExample);
        //再查所有成员的产品
        List<CourseDto> allCourseList = new ArrayList<>();
        for(InstitutionMember institutionMember : institutionMembers){
            CourseExample courseExample = new CourseExample();
            CourseExample.Criteria criteria1 = courseExample.createCriteria();
            criteria1.andCreatorLoginnameEqualTo(institutionMember.getMemberLoginName());
            criteria1.andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
            List<Course> courseList = courseMapper.selectByExample(courseExample);
            for(Course course : courseList){
                allCourseList.add(CopyUtil.copy(course, CourseDto.class));
            }
        }

        //为课程卡片添加机构名称
        for(CourseDto courseDto : allCourseList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria9 = institutionExample.createCriteria();
            criteria9.andCreatorLoginnameEqualTo(courseDto.getCreatorLoginname());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                courseDto.setInstitutionName(institutions.get(0).getName());
                courseDto.setInstitutionId(institutions.get(0).getId());
                courseDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setPageCategory(PageCategoryEnum.COURSE.getCode());
        for(CourseDto courseDto : allCourseList){
            memberActionDto.setParamId(courseDto.getId());
            ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
            if(actionAllInfoDto!=null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            courseDto.setWatchNum(actionAllInfoDto.getWatchNum());
            courseDto.setLikeNum(actionAllInfoDto.getLikeNum());
            courseDto.setCollectNum(actionAllInfoDto.getCollectNum());
        }

        return allCourseList;
    }

    /**
     * 获取机构直播列表
     * 查询机构下的直播信息，包括计划直播和回放
     * 
     * @param institutionId 机构ID
     * @return 返回直播列表
     */
    public List<LiveDto> getLiveList(String institutionId){
        Institution institution = institutionMapper.selectByPrimaryKey(institutionId);
        List<LiveDto> liveDtoList = new ArrayList<>();

        LiveExample liveExample = new LiveExample();
        liveExample.setOrderByClause("show_time asc");
        LiveExample.Criteria criteria = liveExample.createCriteria();
        //计划: 发布状态 ,未来时间, plan yes, 不管有没有url , 当前商户
        criteria.andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode())
                .andShowTimeGreaterThan(new Date())
//                .andPlanEqualTo(Constants.LIVE_SHOW_PLAN)
                .andCreatorLoginnameEqualTo(institution.getCreatorLoginname());
        List<Live> liveList_plan = liveMapper.selectByExample(liveExample);
        for(Live live : liveList_plan){
            LiveDto liveDto = CopyUtil.copy(live, LiveDto.class);
            liveDto.setShowClass(ShowClassEnum.PLAN.getCode());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(liveDto.getShowTime()!=null){
                String format = sdf.format(liveDto.getShowTime());
                liveDto.setShowTimeStr(format);
            }

            liveDtoList.add(liveDto);
        }


        LiveExample liveExample_repeat = new LiveExample();
        liveExample_repeat.setOrderByClause("show_time desc, created_at desc");
        LiveExample.Criteria criteria_repeat = liveExample_repeat.createCriteria();
        //回放: 发布状态, 已过时间, 不管plan状态, 有url , 当前商户
        criteria_repeat.andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode())
                .andShowTimeLessThan(new Date())
                .andUrlIsNotNull()
                .andCreatorLoginnameEqualTo(institution.getCreatorLoginname());
        List<Live> liveList_repeat = liveMapper.selectByExample(liveExample_repeat);
        for(Live live : liveList_repeat){
            LiveDto liveDto = CopyUtil.copy(live, LiveDto.class);
            liveDto.setShowClass(ShowClassEnum.REPEATE.getCode());
            liveDtoList.add(liveDto);
        }

//TODO 尚未进行正确性验证调试
        //为课程卡片添加机构名称
        for(LiveDto liveDto : liveDtoList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria9 = institutionExample.createCriteria();
            criteria9.andCreatorLoginnameEqualTo(liveDto.getCreatorLoginname());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                liveDto.setInstitutionName(institutions.get(0).getName());
                liveDto.setInstitutionId(institutions.get(0).getId());
                liveDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setPageCategory(PageCategoryEnum.COURSE.getCode());
        for(LiveDto liveDto : liveDtoList){
            memberActionDto.setParamId(liveDto.getId());
            ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
            if(actionAllInfoDto!=null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            liveDto.setWatchNum(actionAllInfoDto.getWatchNum());
            liveDto.setLikeNum(actionAllInfoDto.getLikeNum());
            liveDto.setCollectNum(actionAllInfoDto.getCollectNum());
        }

        return liveDtoList;
    }

    /**
     * 获取机构信息
     * 根据ID查询机构基本信息
     * 
     * @param id 机构ID
     * @return 返回机构信息
     */
    public Institution getInstitutionInfo(String id){
        Institution institution = institutionMapper.selectByPrimaryKey(id);
        return institution;
    }

    /**
     * 保存黑名单机构
     * 将机构添加到黑名单中
     * 
     * @param institutionBlackListDto 机构黑名单数据传输对象
     */
    public void saveBlackInstitution(InstitutionBlackListDto institutionBlackListDto){
        InstitutionBlackListExample institutionBlackListExample = new InstitutionBlackListExample();
        InstitutionBlackListExample.Criteria criteria = institutionBlackListExample.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionBlackListDto.getInstitutionId())
                .andBlackInstitutionIdEqualTo(institutionBlackListDto.getBlackInstitutionId());
        List<InstitutionBlackList> institutionBlackLists = institutionBlackListMapper.selectByExample(institutionBlackListExample);
        if(institutionBlackLists.size()==0
                && (!institutionBlackListDto.getInstitutionId().equals(institutionBlackListDto.getBlackInstitutionId()))){

            //判断拉黑的机构是否存在
            Institution institution = institutionMapper.selectByPrimaryKey(institutionBlackListDto.getBlackInstitutionId());
            if(institution == null){
                return;
            }
            InstitutionBlackList institutionBlackList = CopyUtil.copy(institutionBlackListDto, InstitutionBlackList.class);
            institutionBlackList.setId(UuidUtil.getShortUuid());
            institutionBlackListMapper.insert(institutionBlackList);
        }
    }
    /**
     * 获取黑名单机构列表
     * 查询指定机构的黑名单列表
     * 
     * @param institutionId 机构ID
     * @return 返回黑名单机构列表
     */
    public List<InstitutionBlackListDto> getBlackInstitutionList(String institutionId){
        InstitutionBlackListExample institutionBlackListExample = new InstitutionBlackListExample();
        InstitutionBlackListExample.Criteria criteria = institutionBlackListExample.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId);
        List<InstitutionBlackList> institutionBlackLists = institutionBlackListMapper.selectByExample(institutionBlackListExample);
        List<InstitutionBlackListDto> institutionBlackListDtos = CopyUtil.copyList(institutionBlackLists, InstitutionBlackListDto.class);
        for(InstitutionBlackListDto institutionBlackList : institutionBlackListDtos){
            Institution institution = institutionMapper.selectByPrimaryKey(institutionBlackList.getBlackInstitutionId());
            if(institution != null && institution.getName()!=null){
                institutionBlackList.setBlackInstitutionName(institution.getName());
            }
        }
        return institutionBlackListDtos;
    }

    /**
     * 删除黑名单机构项
     * 从黑名单中移除指定的机构
     * 
     * @param id 黑名单记录ID
     */
    public void delBlackInstitutionItem(String id){
        InstitutionBlackListExample institutionBlackListExample = new InstitutionBlackListExample();
        InstitutionBlackListExample.Criteria criteria = institutionBlackListExample.createCriteria();
        criteria.andIdEqualTo(id);
        institutionBlackListMapper.deleteByExample(institutionBlackListExample);
    }


    /**
     * 根据登录名获取机构信息
     * 通过创建者登录名查询对应的机构信息
     * 
     * @param loginName 登录名
     * @return 返回机构信息，如果不存在则返回null
     */
    public Institution getInstitutionByLoginName(String loginName){
        InstitutionExample institutionExample = new InstitutionExample();
        InstitutionExample.Criteria criteria = institutionExample.createCriteria();
        criteria.andCreatorLoginnameEqualTo(loginName);

        List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
        if(institutions.size()>0){
            return institutions.get(0);
        }
        return null;
    }

}
