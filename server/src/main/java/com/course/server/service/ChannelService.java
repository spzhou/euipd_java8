/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.ChannelDto;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChannelMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.LiveMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.course.server.service.common.constant.Constant.DEFAULT_AIIMOOC_CHANNEL;

@Service
public class ChannelService {

    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private LiveMapper liveMapper;
    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新频道信息
     * 
     * @param channelDto 频道数据传输对象
     */
    public void save(ChannelDto channelDto) {
        Channel channel = CopyUtil.copy(channelDto, Channel.class);
        if (ObjectUtils.isEmpty(channel.getId())) {
            this.insert(channel);
        } else {
            this.update(channel);
        }
    }

    /**
     * 新增
     * 创建新的频道记录
     * 
     * @param channel 频道对象
     */
    private void insert(Channel channel) {
        channel.setId(UuidUtil.getShortUuid());
        channelMapper.insert(channel);
    }

    /**
     * 更新
     * 更新频道信息
     * 
     * @param channel 频道对象
     */
    public void update(Channel channel) {
        channelMapper.updateByPrimaryKey(channel);
    }

/*    public Channel getChannelByLoginname(String loginname){
        ChannelExample channelExample = new ChannelExample();
        ChannelExample.Criteria criteria = channelExample.createCriteria();
        criteria.andUserLoginnameEqualTo(loginname);
        List<Channel> channels = channelMapper.selectByExample(channelExample);
        if(channels.size()==0){
            return null;
        }
        return channels.get(0);
    }*/

    /**
     * 根据登录名获取频道信息
     * 通过用户登录名查询对应的频道信息
     * 
     * @param loginName 用户登录名
     * @return 返回频道对象，如果不存在则返回null
     */
    public Channel getChannelByLoginName(String loginName){
        ChannelExample channelExample = new ChannelExample();
        ChannelExample.Criteria criteria = channelExample.createCriteria();
        criteria.andUserLoginnameEqualTo(loginName);
        List<Channel> channels = channelMapper.selectByExample(channelExample);
        if(channels.size()==0){
            return null;
        }
        return channels.get(0);
    }

    /**
     * 根据频道ID获取频道信息
     * 通过频道ID查询对应的频道信息
     * 
     * @param channelId 频道ID
     * @return 返回频道对象，如果不存在则返回null
     */
    public Channel getChannelByChannelId(String channelId){
        ChannelExample channelExample = new ChannelExample();
        ChannelExample.Criteria criteria = channelExample.createCriteria();
        criteria.andChannelIdEqualTo(channelId);
        List<Channel> channels = channelMapper.selectByExample(channelExample);
        if(channels.size()==0){
            return null;
        }
        return channels.get(0);
    }

}
