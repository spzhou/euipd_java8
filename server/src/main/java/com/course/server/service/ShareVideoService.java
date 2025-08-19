/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.enums.PageCategoryEnum;
import com.course.server.mapper.ChannelMapper;
import com.course.server.mapper.InstitutionMapper;
import com.course.server.mapper.ShareCategoryMapper;
import com.course.server.mapper.ShareUploadMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 跟后台功能逻辑相关的服务类在这里写
 * 不要写到polyv的接口类里面去
 * 因为,polyv的接口迟早是要替换掉的
 * */

@Service
public class ShareVideoService {

    @Resource
    private ShareUploadMapper shareMapper;
    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private ShareCategoryMapper shareCategoryMapper;
    @Resource
    private ChannelService channelService;
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private MemberService memberService;

    private static final Logger LOG = LoggerFactory.getLogger(ShareVideoService.class);

    /**
     * 将13位时间戳转换为Date对象
     * 将字符串格式的时间戳转换为Java Date对象
     * 
     * @param time 13位时间戳字符串
     * @return 返回转换后的Date对象，转换失败则返回null
     */
    public Date timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 新课列表查询，只查询已发布的，按创建日期倒序
     */
    public void listShares(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        Calendar calendar = Calendar.getInstance(); //获得当前时间
        calendar.add(Calendar.DATE,-180);
        Date delDate = calendar.getTime();

        ShareUploadExample shareExample = new ShareUploadExample();
        shareExample.createCriteria()
                .andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode())
                .andNameIsNotNull();
        shareExample.setOrderByClause("updated_at desc");
        List<ShareUpload> shareList = shareMapper.selectByExample(shareExample);
        long total = shareMapper.countByExample(shareExample);
        List<ShareDto> shareDtoList = CopyUtil.copyList(shareList, ShareDto.class);

        pageDto.setTotal(total);
        pageDto.setList(shareDtoList);
    }

    /**
     * 列表查询：关联课程分类表
     * 根据用户信息查询分享视频列表，并关联机构信息
     * 
     * @param pageDto 分页查询对象
     */
    @Transactional
    public void list(SharePageDto pageDto) {
        UserDto userDto = pageDto.getUser();
        List<ShareDto> shareDtoList = new ArrayList<>();
        List<ShareUpload> shareUploadList = new ArrayList<>();

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        //1. 查出所有的课程
        ShareUploadExample shareExample = new ShareUploadExample();
        shareExample.setOrderByClause("sort desc");
        ShareUploadExample.Criteria criteria2 = shareExample.createCriteria();
        criteria2.andCreatorLoginnameEqualTo(userDto.getLoginName());
        shareUploadList = shareMapper.selectByExample(shareExample);
        PageInfo<ShareUpload> pageInfo = new PageInfo<>(shareUploadList);
        pageDto.setTotal(pageInfo.getTotal());

        shareDtoList = CopyUtil.copyList(shareUploadList, ShareDto.class);

        //为课程卡片添加机构名称
        for(ShareDto shareDto : shareDtoList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria = institutionExample.createCriteria();
            criteria.andCreatorLoginnameEqualTo(shareDto.getCreatorLoginname());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                shareDto.setInstitutionName(institutions.get(0).getName());
                shareDto.setInstitutionId(institutions.get(0).getId());
                shareDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        pageDto.setList(shareDtoList);
    }

    /**
     * Web端列表查询
     * 根据分类ID查询分享视频列表，支持分页和分类筛选
     * 
     * @param pageDto 分页查询对象
     */
    @Transactional
    public void webList(SharePageDto pageDto) {
        List<ShareDto> shareDtoList = new ArrayList<>();
        List<ShareCategory> shareCategoryList = null;
        //1. 如果categoryId不为null，就只查询对应categoryId的课程；
        if(!ObjectUtils.isEmpty(pageDto.getCategoryId())){
            ShareCategoryExample shareCategoryExample = new ShareCategoryExample();
            ShareCategoryExample.Criteria criteria4 = shareCategoryExample.createCriteria();
            criteria4.andCategoryIdEqualTo(pageDto.getCategoryId());
            shareCategoryList = shareCategoryMapper.selectByExample(shareCategoryExample);

            List<ShareUpload> shareList_temp = new ArrayList<>();
            //2. 根据categoryList查询对应的courseList
            for(ShareCategory shareCategory: shareCategoryList){
                ShareUpload share = shareMapper.selectByPrimaryKey(shareCategory.getShareId());
                if(share!=null){
                    shareList_temp.add(share);
                }

            }
            //机构中存在机构被删除,但是分类表中还有这个institutionId的情况
            List<ShareUpload> shareList = new ArrayList<>();
            for(ShareUpload share :shareList_temp){
                if(share.getSort()==null){
                    share.setSort(0);
                }
                if(share.getStatus()==null){
                    share.setStatus(CourseStatusEnum.DRAFT.getCode());
                }
                shareList.add(share);
            }

            for(ShareUpload share : shareList){
                if(share.getStatus().equals(CourseStatusEnum.PUBLISH.getCode())){
                    ShareDto courseDto = CopyUtil.copy(share, ShareDto.class);
                    shareDtoList.add(courseDto);
                }
            }

            shareDtoList = shareDtoList.stream().sorted(Comparator.comparing(ShareDto::getSort).reversed()).collect(Collectors.toList());

            int count = shareDtoList.size();
            int page = pageDto.getPage();
            int size = pageDto.getSize();
            int end = page*size;
            if(end > count){
                end = count;
            }

            shareDtoList= shareDtoList.subList((page-1)*size, end);
            pageDto.setTotal(count);

        }else{//categoryId为空
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            List<ShareUpload> shareList = new ArrayList<>();

            //1. 查出所有的机构
            ShareUploadExample shareExample = new ShareUploadExample();
            shareExample.setOrderByClause("sort desc");
            ShareUploadExample.Criteria criteria = shareExample.createCriteria();
            criteria.andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
            shareList = shareMapper.selectByExample(shareExample);
            PageInfo<ShareUpload> pageInfo = new PageInfo<>(shareList);
            pageDto.setTotal(pageInfo.getTotal());

            shareDtoList = CopyUtil.copyList(shareList, ShareDto.class);
        }

        //为课程卡片添加机构名称
        for(ShareDto shareDto : shareDtoList){
            InstitutionExample institutionExample = new InstitutionExample();
            InstitutionExample.Criteria criteria = institutionExample.createCriteria();
            criteria.andCreatorLoginnameEqualTo(shareDto.getCreatorLoginname());
            List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
            if(institutions.size()>0){
                shareDto.setInstitutionName(institutions.get(0).getName());
                shareDto.setInstitutionId(institutions.get(0).getId());
                shareDto.setInstitutionLogo(institutions.get(0).getLogo());
            }
        }

        //为卡片增加点赞\收藏等信息
        MemberActionDto memberActionDto =new MemberActionDto();
        memberActionDto.setPageCategory(PageCategoryEnum.SHARE.getCode());
        for(ShareDto shareDto : shareDtoList){
            memberActionDto.setParamId(shareDto.getId());
            ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
            if(actionAllInfoDto != null){
                actionAllInfoDto.setLikeNum(actionAllInfoDto.getLikeNum()!=null ? actionAllInfoDto.getLikeNum() :0);
                actionAllInfoDto.setWatchNum(actionAllInfoDto.getWatchNum()!=null ? actionAllInfoDto.getWatchNum() :0);
                actionAllInfoDto.setCollectNum(actionAllInfoDto.getCollectNum()!=null ? actionAllInfoDto.getCollectNum() :0);
            }
            shareDto.setWatchNum(actionAllInfoDto.getWatchNum());
            shareDto.setLikeNum(actionAllInfoDto.getLikeNum());
            shareDto.setCollectNum(actionAllInfoDto.getCollectNum());
        }

        pageDto.setList(shareDtoList);
    }

    /**
     * 根据ID查找分享视频
     * 通过ID查询分享视频详情，只返回已发布的视频
     * 
     * @param id 分享视频ID
     * @return 返回分享视频数据传输对象，如果不存在或未发布则返回null
     */
    public ShareDto findShare(String id) {
        ShareUpload share = shareMapper.selectByPrimaryKey(id);
        if (share == null || !CourseStatusEnum.PUBLISH.getCode().equals(share.getStatus())) {
            return null;
        }
        ShareDto shareUploadDto = CopyUtil.copy(share, ShareDto.class);

        return shareUploadDto;
    }

    /**
     * 更新分享视频
     * 更新分享视频信息，自动设置更新时间
     * 
     * @param shareUpload 分享视频对象
     */
    public void update(ShareUpload shareUpload) {
        shareUpload.setUpdatedAt(new Date());
        if(shareUpload.getStatus()==null){
            shareUpload.setStatus(CourseStatusEnum.DRAFT.getCode());
        }
        shareMapper.updateByPrimaryKey(shareUpload);
    }


}
