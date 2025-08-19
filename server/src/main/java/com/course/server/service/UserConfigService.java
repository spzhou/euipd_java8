/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.UserConfig;
import com.course.server.domain.UserConfigExample;
import com.course.server.dto.UserConfigDto;
import com.course.server.dto.UserDto;
import com.course.server.mapper.UserConfigMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserConfigService {

    private static final Logger LOG = LoggerFactory.getLogger(UserConfigService.class);

    @Resource
    private UserConfigMapper userConfigMapper;

    /**
     * 保存用户配置
     * 保存或更新用户的配置信息，如果不存在则新增，存在则更新
     * 
     * @param userConfigDto 用户配置数据传输对象
     */
    public void save(UserConfigDto userConfigDto){
        UserConfig userConfig = CopyUtil.copy(userConfigDto, UserConfig.class);

        UserConfigExample userConfigExample = new UserConfigExample();
        UserConfigExample.Criteria criteria = userConfigExample.createCriteria();
        criteria.andLoginNameEqualTo(userConfigDto.getLoginName());
        List<UserConfig> userConfigs = userConfigMapper.selectByExample(userConfigExample);
        if(userConfigs.size()==0){
            userConfig.setId(UuidUtil.getShortUuid());
            userConfigMapper.insert(userConfig);
        }else{
            userConfig.setId(userConfigs.get(0).getId());
            userConfigMapper.updateByPrimaryKey(userConfig);
        }
    }

    /**
     * 读取用户配置
     * 根据登录名读取用户的下载路径配置
     * 
     * @param loginName 登录名
     * @return 返回用户的下载路径，如果不存在则返回null
     */
    public String read(String loginName){
        UserConfigExample userConfigExample = new UserConfigExample();
        UserConfigExample.Criteria criteria = userConfigExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<UserConfig> userConfigs = userConfigMapper.selectByExample(userConfigExample);
        if(userConfigs.size()>0){
           return userConfigs.get(0).getDownLoadPath();
        }
        return null;
    }


}
