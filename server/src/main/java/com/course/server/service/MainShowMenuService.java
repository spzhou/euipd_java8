/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.aliyun.vod20170321.models.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.course.server.constants.Constants;
import com.course.server.domain.*;
import com.course.server.dto.MainShowMenuDto;
import com.course.server.enums.VideoClassEnum;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainShowMenuService {
    @Resource
    private MainShowMenuMapper mainShowMenuMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private SectionMapper sectionMapper;
    @Resource
    private ShareUploadMapper shareUploadMapper;
    @Resource
    private LiveMapper liveMapper;
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private IndexConfigMapper indexConfigMapper;
    @Resource
    private ChannelService channelService;
    @Resource
    private AliyunVodService aliyunVodService;

    /**
     * 通过机构的Id查询"今日推荐"视频列表
     * 大屏幕是可以配置别人家的视频的,只要知道相应视频的ID
     * 
     * @return 返回平台主显示菜单列表
     */
    public List<MainShowMenuDto> getMainShowMenuList_plat(){
        MainShowMenuExample mainShowMenuExample = new MainShowMenuExample();
//        MainShowMenuExample.Criteria criteria = mainShowMenuExample.createCriteria();
        mainShowMenuExample.setOrderByClause("sort asc");
        MainShowMenuExample.Criteria criteria = mainShowMenuExample.createCriteria();
        criteria.andInstitutionIdEqualTo(Constants.PLANT_INSTITUTION_ID);
        //如果商家的大屏是播放列表是空的,就加载平台播放列表
        //如果商家的大屏有配置,则显示商家的视频推荐
        //商家可以选择是否转播平台的视频列表
        List<MainShowMenu> mainShowMenuList = mainShowMenuMapper.selectByExample(mainShowMenuExample);

        List<MainShowMenuDto> mainShowMenuDtos = CopyUtil.copyList(mainShowMenuList, MainShowMenuDto.class);
        for(MainShowMenuDto mainShowMenuDto : mainShowMenuDtos){
            if(mainShowMenuDto.getVideoClass().equals(VideoClassEnum.COURSE.getCode())){
                Section section = sectionMapper.selectByPrimaryKey(mainShowMenuDto.getVideoId());
                if(section==null){
                    continue;
                }
                mainShowMenuDto.setVod(section.getVod());
                //TODO mainShowMenuDto.setUrl();
                try{
                    GetPlayInfoResponse getPlayInfoResponse = aliyunVodService.GetPlayInfo(section.getVod());
                    boolean isMp4 = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getFormat().equals("mp4");
                    String duration = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getDuration();
                    String url = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getPlayURL();

                    if(Double.parseDouble(duration) > 1.0 && isMp4){
                        mainShowMenuDto.setUrl(url);
                    }
                }catch (Exception e){
                    continue;
                }

                mainShowMenuDto.setTitle(section.getTitle());
                Course course = courseMapper.selectByPrimaryKey(section.getCourseId());
                mainShowMenuDto.setImage(course.getImage());

                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria1 = institutionExample.createCriteria();
                criteria1.andCreatorLoginnameEqualTo(course.getCreatorLoginname());
                //查出课程的出品方
                List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
                if(institutions.size()>0){
                    mainShowMenuDto.setVideoFromInstId(institutions.get(0).getId());
                }
            }

            if(mainShowMenuDto.getVideoClass().equals(VideoClassEnum.SHARE.getCode())){
                ShareUpload shareUpload = shareUploadMapper.selectByPrimaryKey(mainShowMenuDto.getVideoId());
                if(shareUpload==null){
                    continue;
                }
                mainShowMenuDto.setVod(shareUpload.getVod());
                //TODO mainShowMenuDto.setUrl();
                try{
                    GetPlayInfoResponse getPlayInfoResponse = aliyunVodService.GetPlayInfo(shareUpload.getVod());
                    boolean isMp4 = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getFormat().equals("mp4");
                    String duration = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getDuration();
                    String url = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getPlayURL();

                    if(Double.parseDouble(duration) > 1.0 && isMp4){
                        mainShowMenuDto.setUrl(url);
                    }
                }catch (Exception e){
                    continue;
                }
                mainShowMenuDto.setTitle(shareUpload.getName());
                mainShowMenuDto.setImage(shareUpload.getImage());

                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria2 = institutionExample.createCriteria();
                criteria2.andCreatorLoginnameEqualTo(shareUpload.getCreatorLoginname());
                //查出科技分享出品方的Id
                List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
                if(institutions.size()>0){
                    mainShowMenuDto.setVideoFromInstId(institutions.get(0).getId());
                }
            }

            if(mainShowMenuDto.getVideoClass().equals(VideoClassEnum.LIVE.getCode())){
                Live live = liveMapper.selectByPrimaryKey(mainShowMenuDto.getVideoId());
                if(live==null){
                    continue;
                }
                mainShowMenuDto.setVod(live.getVod());
                //TODO mainShowMenuDto.setUrl();
                try{
                    GetPlayInfoResponse getPlayInfoResponse = aliyunVodService.GetPlayInfo(live.getVod());
                    boolean isMp4 = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getFormat().equals("mp4");
                    String duration = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getDuration();
                    String url = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getPlayURL();

                    if(Double.parseDouble(duration) > 1.0 && isMp4){
                        mainShowMenuDto.setUrl(url);
                    }
                }catch (Exception e){
                    continue;
                }
                mainShowMenuDto.setTitle(live.getName());
                mainShowMenuDto.setImage(live.getImage());

                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria3 = institutionExample.createCriteria();
                criteria3.andCreatorLoginnameEqualTo(live.getCreatorLoginname());
                //查出直播视频出品方的Id
                List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
                if(institutions.size()>0){
                    mainShowMenuDto.setVideoFromInstId(institutions.get(0).getId());
                }
            }
        }

        return mainShowMenuDtos;
    }

    /**
     * 获取机构主显示菜单列表
     * 查询指定机构的主显示菜单，如果机构没有配置则默认转播平台视频列表
     * 
     * @param institutionId 机构ID
     * @return 返回主显示菜单列表
     */
    @Transactional
    public List<MainShowMenuDto> getMainShowMenuList(String institutionId){
        MainShowMenuExample mainShowMenuExample = new MainShowMenuExample();
        mainShowMenuExample.setOrderByClause("sort asc");
        MainShowMenuExample.Criteria criteria = mainShowMenuExample.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId);
        //如果商家的大屏是播放列表是空的,就加载平台播放列表
        //如果商家的大屏有配置,则显示商家的视频推荐
        //商家可以选择是否转播平台的视频列表
        List<MainShowMenu> mainShowMenuList = mainShowMenuMapper.selectByExample(mainShowMenuExample);
        if(mainShowMenuList.size()>0){
            //判断商家是否同意接收平台的节目清单
            IndexConfigExample indexConfigExample = new IndexConfigExample();
            IndexConfigExample.Criteria criteria1 = indexConfigExample.createCriteria();
            criteria1.andInstitutionIdEqualTo(institutionId);
            List<IndexConfig> indexConfigs = indexConfigMapper.selectByExample(indexConfigExample);
            //在没有创建配置表之前开的商铺需要执行下面的创建默认配置文件的操作
            IndexConfig indexConfig = new IndexConfig();
            if(indexConfigs.size()==0){
                indexConfig.setId(UuidUtil.getShortUuid());
                indexConfig.setInstitutionId(institutionId);
                indexConfig.setMainPlayerWarmupImg("https://my-course-img.oss-cn-shanghai.aliyuncs.com/image/1227%2A690%E8%A7%86%E9%A2%91%E5%B0%81%E9%9D%A2.png");
                String loginName = institutionMapper.selectByPrimaryKey(institutionId).getCreatorLoginname();
                Channel channel = channelService.getChannelByLoginName(loginName);
                if(channel!=null){
                    indexConfig.setMainLiveChannelId(channel.getChannelId());
                }
                indexConfig.setMainCover("https://my-course-img.oss-cn-shanghai.aliyuncs.com/image/1227%2A690%E8%A7%86%E9%A2%91%E5%B0%81%E9%9D%A2.png");
                indexConfig.setPlatLiveShow(1);
                indexConfig.setClosePlatMenuFlag(0);
                indexConfig.setBlackNameListFlag(0);

                indexConfigMapper.insert(indexConfig);
            }else{
                indexConfig = indexConfigs.get(0);
            }

            if(indexConfig.getClosePlatMenuFlag()==0){
                //没有关闭平台的节目单
                MainShowMenuExample mainShowMenuExample1 = new MainShowMenuExample();
                mainShowMenuExample1.setOrderByClause("sort asc");
                MainShowMenuExample.Criteria criteria5 = mainShowMenuExample1.createCriteria();
                criteria5.andInstitutionIdEqualTo(Constants.PLANT_INSTITUTION_ID);
                List<MainShowMenu> mainShowMenuList_plat = mainShowMenuMapper.selectByExample(mainShowMenuExample1);
                //将平台的播放列表追加到商家的播放列表上
                mainShowMenuList.addAll(mainShowMenuList_plat);
            }

        }else{
            //默认转播平台的视频列表
            MainShowMenuExample mainShowMenuExample1 = new MainShowMenuExample();
            mainShowMenuExample1.setOrderByClause("sort asc");
            MainShowMenuExample.Criteria criteria5 = mainShowMenuExample1.createCriteria();
            criteria5.andInstitutionIdEqualTo(Constants.PLANT_INSTITUTION_ID);
            List<MainShowMenu> mainShowMenuList_plat = mainShowMenuMapper.selectByExample(mainShowMenuExample1);
            //将平台的播放列表追加到商家的播放列表上
            mainShowMenuList.addAll(mainShowMenuList_plat);
        }

        List<MainShowMenuDto> mainShowMenuDtos = CopyUtil.copyList(mainShowMenuList, MainShowMenuDto.class);
        for(MainShowMenuDto mainShowMenuDto : mainShowMenuDtos){
            if(mainShowMenuDto.getVideoClass().equals(VideoClassEnum.COURSE.getCode())){
                Section section = sectionMapper.selectByPrimaryKey(mainShowMenuDto.getVideoId());
                if(section==null){
                    continue;
                }
                mainShowMenuDto.setVod(section.getVod());
                try{
                    GetPlayInfoResponse getPlayInfoResponse = aliyunVodService.GetPlayInfo(section.getVod());
                    boolean isMp4 = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getFormat().equals("mp4");
                    String duration = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getDuration();
                    String url = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getPlayURL();

                    if(Double.parseDouble(duration) > 1.0 && isMp4){
                        mainShowMenuDto.setUrl(url);
                    }
                }catch (Exception e){
                    continue;
                }
                mainShowMenuDto.setTitle(section.getTitle());
                Course course = courseMapper.selectByPrimaryKey(section.getCourseId());
                mainShowMenuDto.setImage(course.getImage());

                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria1 = institutionExample.createCriteria();
                criteria1.andCreatorLoginnameEqualTo(course.getCreatorLoginname());
                //查出课程的出品方
                List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
                if(institutions.size()>0){
                    mainShowMenuDto.setVideoFromInstId(institutions.get(0).getId());
                }
            }

            if(mainShowMenuDto.getVideoClass().equals(VideoClassEnum.SHARE.getCode())){
                ShareUpload shareUpload = shareUploadMapper.selectByPrimaryKey(mainShowMenuDto.getVideoId());
                if(shareUpload==null){
                    continue;
                }
                mainShowMenuDto.setVod(shareUpload.getVod());
                try{
                    GetPlayInfoResponse getPlayInfoResponse = aliyunVodService.GetPlayInfo(shareUpload.getVod());
                    boolean isMp4 = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getFormat().equals("mp4");
                    String duration = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getDuration();
                    String url = getPlayInfoResponse.getBody().getPlayInfoList().getPlayInfo().get(0).getPlayURL();

                    if(Integer.parseInt(duration) >0 && isMp4){
                        mainShowMenuDto.setUrl(url);
                    }
                }catch (Exception e){
                    continue;
                }
                mainShowMenuDto.setTitle(shareUpload.getName());
                mainShowMenuDto.setImage(shareUpload.getImage());

                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria2 = institutionExample.createCriteria();
                criteria2.andCreatorLoginnameEqualTo(shareUpload.getCreatorLoginname());
                //查出科技分享出品方的Id
                List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
                if(institutions.size()>0){
                    mainShowMenuDto.setVideoFromInstId(institutions.get(0).getId());
                }
            }

            if(mainShowMenuDto.getVideoClass().equals(VideoClassEnum.LIVE.getCode())){
                Live live = liveMapper.selectByPrimaryKey(mainShowMenuDto.getVideoId());
                if(live==null){
                    continue;
                }
                mainShowMenuDto.setUrl(live.getUrl());
                mainShowMenuDto.setTitle(live.getName());
                mainShowMenuDto.setImage(live.getImage());

                InstitutionExample institutionExample = new InstitutionExample();
                InstitutionExample.Criteria criteria3 = institutionExample.createCriteria();
                criteria3.andCreatorLoginnameEqualTo(live.getCreatorLoginname());
                //查出直播视频出品方的Id
                List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
                if(institutions.size()>0){
                    mainShowMenuDto.setVideoFromInstId(institutions.get(0).getId());
                }
            }
        }

        for(MainShowMenuDto mainShowMenuDto : mainShowMenuDtos){
            if(mainShowMenuDto.getInstitutionId().equals(Constants.PLANT_INSTITUTION_ID)){
                mainShowMenuDto.setPlatFlag(Constants.PLANT_SHOW_LIST_FLAG);
            }
        }

        return mainShowMenuDtos;
    }

    /**
     * 保存主显示菜单
     * 保存或更新主显示菜单项，自动设置视频标题和ID
     * 
     * @param mainShowMenu 主显示菜单对象
     */
    public void save(MainShowMenu mainShowMenu){
        //查看对应的机构播放清单中是否存在这个videoId
        //添加ID
        mainShowMenu.setId(UuidUtil.getShortUuid());

        switch (mainShowMenu.getVideoClass()){
            case "COURSE" :
                Section section = sectionMapper.selectByPrimaryKey(mainShowMenu.getVideoId());
                if(section == null){
                    return;
                }
                mainShowMenu.setSubjectTitle(section.getTitle());
                break;
            case "LIVE" :
                Live live = liveMapper.selectByPrimaryKey(mainShowMenu.getVideoId());
                if(live == null){
                    return;
                }
                mainShowMenu.setSubjectTitle(live.getName());
                break;
            case "SHARE" :
                ShareUpload shareUpload = shareUploadMapper.selectByPrimaryKey(mainShowMenu.getVideoId());
                if(shareUpload == null){
                    return;
                }
                mainShowMenu.setSubjectTitle(shareUpload.getName());
                break;
        }

        MainShowMenuExample mainShowMenuExample = new MainShowMenuExample();
        MainShowMenuExample.Criteria criteria = mainShowMenuExample.createCriteria();
        criteria.andInstitutionIdEqualTo(mainShowMenu.getInstitutionId())
                .andVideoIdEqualTo(mainShowMenu.getVideoId());
        List<MainShowMenu> mainShowMenus = mainShowMenuMapper.selectByExample(mainShowMenuExample);
        if(mainShowMenus.size()==0){
            if(!mainShowMenu.getSubjectTitle().isEmpty()){
                mainShowMenuMapper.insert(mainShowMenu);
            }
        }else if(mainShowMenus.size()>0){
            if(!mainShowMenu.getSubjectTitle().isEmpty()){
                mainShowMenuMapper.updateByPrimaryKey(mainShowMenu);
            }
        }

    }

    /**
     * 删除主显示菜单
     * 根据ID删除主显示菜单项
     * 
     * @param id 主显示菜单ID
     */
    public void delete(String id) {
        mainShowMenuMapper.deleteByPrimaryKey(id);
    }


}
