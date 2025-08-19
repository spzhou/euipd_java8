/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.constants.Constants;
import com.course.server.domain.*;
import com.course.server.dto.IndexConfigDto;
import com.course.server.mapper.IndexConfigMapper;
import com.course.server.mapper.InstitutionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexConfigService {
    @Resource
    private IndexConfigMapper indexConfigMapper;
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private ChannelService channelService;
    @Resource
    private PolyvLiveService polyvLiveService;

    /**
     * 获取机构首页配置信息
     * 查询指定机构的首页配置，如果不存在则创建默认配置
     * 
     * @param institutionId 机构ID
     * @return 返回首页配置对象
     */
    public IndexConfig getIndexConfigInfo(String institutionId){
        IndexConfigExample indexConfigExample = new IndexConfigExample();
        IndexConfigExample.Criteria criteria = indexConfigExample.createCriteria();
        criteria.andInstitutionIdEqualTo(institutionId);
        List<IndexConfig> indexConfigs = indexConfigMapper.selectByExample(indexConfigExample);
        if(indexConfigs.size()==0){
            //组装一条基本的配置信息
            IndexConfigExample indexConfigExample1 = new IndexConfigExample();
            IndexConfigExample.Criteria criteria1 = indexConfigExample1.createCriteria();
            criteria1.andInstitutionIdEqualTo(Constants.PLANT_INSTITUTION_ID);
            List<IndexConfig> indexConfig_plat = indexConfigMapper.selectByExample(indexConfigExample1);

            IndexConfig indexConfig = new IndexConfig();
            indexConfig.setInstitutionId(institutionId);
            indexConfig.setMainPlayerWarmupImg(indexConfig_plat.get(0).getMainPlayerWarmupImg());
            Institution institution = institutionMapper.selectByPrimaryKey(institutionId);

            Channel channel = channelService.getChannelByLoginName(institution.getCreatorLoginname());
            if(channel != null){
                indexConfig.setMainLiveChannelId(channel.getChannelId());
            }
            indexConfig.setCoverImgRedirectUrl(indexConfig_plat.get(0).getCoverImgRedirectUrl());
            indexConfig.setMainCover(indexConfig_plat.get(0).getMainCover());
            indexConfig.setPlatLiveShow(1);
            indexConfig.setClosePlatMenuFlag(0);
            indexConfig.setBlackNameListFlag(0);

            //为客户写入一条默认数据
            indexConfig.setId(UuidUtil.getShortUuid());
            indexConfigMapper.insert(indexConfig);
            return indexConfig;

        }
        return indexConfigs.get(0);
    }

    /**
     * 保存首页配置
     * 保存或更新机构的首页配置信息
     * 
     * @param indexConfigDto 首页配置数据传输对象
     */
    public void save(IndexConfigDto indexConfigDto){
        IndexConfig indexConfigInfo = getIndexConfigInfo(indexConfigDto.getInstitutionId());
        if(indexConfigInfo == null){
            indexConfigDto.setId(UuidUtil.getShortUuid());
            IndexConfig indexConfig = CopyUtil.copy(indexConfigDto, IndexConfig.class);
            if(indexConfigDto.getPlatLiveShow()==null){
                indexConfig.setPlatLiveShow(1);
            }else {
                indexConfig.setPlatLiveShow(indexConfigDto.getPlatLiveShow() ? 1 : 0);
            }
            if(indexConfigDto.getClosePlatMenuFlag()==null){
                indexConfig.setClosePlatMenuFlag(0);
            }else{
                indexConfig.setClosePlatMenuFlag(indexConfigDto.getClosePlatMenuFlag()? 1: 0);
            }
            if(indexConfigDto.getBlackNameListFlag()==null){
                indexConfig.setBlackNameListFlag(0);
            }else{
                indexConfig.setBlackNameListFlag(indexConfigDto.getBlackNameListFlag()? 1: 0);
            }
            indexConfigMapper.insert(indexConfig);
        }else{
            indexConfigDto.setId(indexConfigInfo.getId());
            IndexConfig indexConfig = CopyUtil.copy(indexConfigDto, IndexConfig.class);
            if(indexConfigDto.getPlatLiveShow()==null){
                indexConfig.setPlatLiveShow(1);
            }else {
                indexConfig.setPlatLiveShow(indexConfigDto.getPlatLiveShow() ? 1 : 0);
            }
            if(indexConfigDto.getClosePlatMenuFlag()==null){
                indexConfig.setClosePlatMenuFlag(0);
            }else{
                indexConfig.setClosePlatMenuFlag(indexConfigDto.getClosePlatMenuFlag()? 1: 0);
            }
            if(indexConfigDto.getBlackNameListFlag()==null){
                indexConfig.setBlackNameListFlag(0);
            }else{
                indexConfig.setBlackNameListFlag(indexConfigDto.getBlackNameListFlag()? 1: 0);
            }
            indexConfigMapper.updateByPrimaryKey(indexConfig);
        }
    }
    /**
     * 保存首页封面图片
     * 更新机构的首页封面图片URL
     * 
     * @param indexConfigDto 包含封面图片URL的配置对象
     */
    public void saveIndexCover(IndexConfigDto indexConfigDto){
        //只要将url保存到数据库中即可
        IndexConfigExample indexConfigExample = new IndexConfigExample();
        IndexConfigExample.Criteria criteria =indexConfigExample.createCriteria();
        criteria.andInstitutionIdEqualTo(indexConfigDto.getInstitutionId());
        List<IndexConfig> indexConfigInfos = indexConfigMapper.selectByExample(indexConfigExample);
        if(indexConfigInfos.size() == 0){
            return;
        }else{
            indexConfigInfos.get(0).setMainCover(indexConfigDto.getMainCover());
            indexConfigMapper.updateByPrimaryKey(indexConfigInfos.get(0));
        }
    }

    /**
     * 保存直播封面图片
     * 将直播封面图片上传到保利威平台并保存URL
     * 
     * @param indexConfigDto 包含直播封面图片的配置对象
     * @throws Exception 上传异常
     */
    public void saveLiveCover(IndexConfigDto indexConfigDto) throws Exception {
        //将封面上传到保利威平台
        InstitutionExample institutionExample = new InstitutionExample();
        InstitutionExample.Criteria criteria = institutionExample.createCriteria();
        criteria.andIdEqualTo(indexConfigDto.getInstitutionId());
        List<Institution> institutions = institutionMapper.selectByExample(institutionExample);
        Channel channel = new Channel();
        if(institutions.size()>0){
            channel = channelService.getChannelByLoginName(institutions.get(0).getCreatorLoginname());
        }
        String redirectUrl = "https://www.aiisci.com/#/company-index/"+ indexConfigDto.getInstitutionId();

        polyvLiveService.setPlayerWarmUpImg(channel.getChannelId(), indexConfigDto.getMainPlayerWarmupImg(), redirectUrl);
        //上传成功后,将Url保存到数据库
        IndexConfigExample indexConfigExample = new IndexConfigExample();
        IndexConfigExample.Criteria criteria1 =indexConfigExample.createCriteria();
        criteria1.andInstitutionIdEqualTo(indexConfigDto.getInstitutionId());
        List<IndexConfig> indexConfigInfos = indexConfigMapper.selectByExample(indexConfigExample);
        if(indexConfigInfos.size() == 0){
            return;
        }else{
            indexConfigInfos.get(0).setMainPlayerWarmupImg(indexConfigDto.getMainPlayerWarmupImg());
            indexConfigMapper.updateByPrimaryKey(indexConfigInfos.get(0));
        }
    }



}
