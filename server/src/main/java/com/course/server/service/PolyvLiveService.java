/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.Channel;
import com.course.server.dto.ChannelDto;
import com.course.server.dto.ChannelInfoDto;
import com.course.server.polyv.util.*;
import com.course.server.util.CopyUtil;
import net.polyv.common.v1.exception.PloyvSdkException;
import net.polyv.live.v1.config.LiveGlobalConfig;
import net.polyv.live.v1.constant.LiveConstant;
import net.polyv.live.v1.entity.channel.operate.LiveChannelInfoRequest;
import net.polyv.live.v1.entity.channel.operate.LiveChannelInfoResponse;
import net.polyv.live.v1.entity.channel.operate.LiveSonChannelInfoListRequest;
import net.polyv.live.v1.entity.channel.operate.LiveSonChannelInfoListResponse;
import net.polyv.live.v1.entity.player.LiveSetPlayerImgRequest;
import net.polyv.live.v1.service.channel.impl.LiveChannelOperateServiceImpl;
import net.polyv.live.v1.service.channel.impl.LiveChannelStateServiceImpl;
import net.polyv.live.v1.service.player.impl.LivePlayerServiceImpl;
import net.polyv.live.v1.util.LiveSignUtil;
import net.polyv.live.v2.entity.channel.operate.LiveChannelV2Request;
import net.polyv.live.v2.entity.channel.operate.LiveChannelV2Response;
import net.polyv.live.v2.entity.channel.state.LiveListChannelStreamStatusV2Request;
import net.polyv.live.v2.entity.channel.state.LiveListChannelStreamStatusV2Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 直接调动polyv接口的服务类
 * 将来可以被整体替换成阿里云的服务接口
 * 自己写的服务类不在这里写
 */
@Service
public class PolyvLiveService {

    @Resource
    private ChannelService channelService;
    @Resource
    private UserService userService;


    private static final Logger LOG = LoggerFactory.getLogger(PolyvLiveService.class);

    /**
     * 创建直播频道
     * 通过保利威平台创建新的直播频道，并保存频道信息到数据库
     * 
     * @param loginName 用户登录名
     * @param courseName 课程名称
     * @param categoryId 分类ID
     * @return 返回创建的频道数据传输对象
     * @throws Exception 创建频道异常
     */
    public ChannelDto CreateLiveChannel(String loginName ,String courseName, Integer categoryId) throws Exception {

        LiveChannelV2Request liveChannelRequest = new LiveChannelV2Request();
        LiveChannelV2Response liveChannelResponse = null;
        try {
            //生成随机密码
            String channelPassword = RandomStringUtils.randomAlphanumeric(6);
            liveChannelRequest
                    .setName(courseName)
                    .setNewScene(LiveConstant.NewSceneType.TRAIN.getCode())
                    .setTemplate("alone")
                    .setCategoryId(categoryId)
                    .setChannelPasswd(channelPassword);

            liveChannelResponse = new LiveChannelOperateServiceImpl().createChannelV2(liveChannelRequest);
            Assert.assertNotNull(liveChannelResponse);
            if (liveChannelResponse != null) {
                //to do something ......
                //将生成的频道号保存到用户表中
                Channel channel = new Channel();
                channel.setUserLoginname(loginName);
                channel.setChannelId(liveChannelResponse.getChannelId());
                channel.setChannelPassword(liveChannelResponse.getChannelPasswd());
                String showUrl = "https://live.polyv.cn/watch/"+ liveChannelResponse.getChannelId();
                channel.setShowUrl(showUrl);
                String teacherUrl = "https://live.polyv.net/web-start/login?channelId="+ liveChannelResponse.getChannelId();
                channel.setTeacherUrl(teacherUrl);

                ChannelDto channelDto = CopyUtil.copy(channel, ChannelDto.class);
                //将频道信息更新的用户数据中
                channelService.save(channelDto);

                LOG.debug("频道创建成功{}", JSON.toJSONString(liveChannelResponse));
                LOG.debug("网页开播地址：https://live.polyv.net/web-start/login?channelId={}", liveChannelResponse.getChannelId());
                LOG.debug("网页观看地址：https://live.polyv.cn/watch/{} ", liveChannelResponse.getChannelId());

                return channelDto;
            }
        } catch (PloyvSdkException e) {
            //参数校验不合格 或者 请求服务器端500错误，错误信息见PloyvSdkException.getMessage(),B
            LOG.error(e.getMessage(), e);
            // 异常返回做B端异常的业务逻辑，记录log 或者 上报到ETL 或者回滚事务
            throw e;
        } catch (Exception e) {
            LOG.error("SDK调用异常", e);
            throw e;
        }
        return null;
    }

    /**
     * 频道单点登录
     * 为指定频道生成单点登录的授权URL
     * 
     * @param channelId 频道ID
     * @return 返回频道授权登录地址
     * @throws IOException IO异常
     * @throws NoSuchAlgorithmException 算法异常
     */
    public String channelSSO(String channelId) throws IOException, NoSuchAlgorithmException {

        String timestamp = String.valueOf(System.currentTimeMillis());
        //自定义的token，只能使用一次，且10秒内有效
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //频道号
        String url = String.format("http://api.polyv.net/live/v2/channels/%s/set-token", channelId);

        //1、设置频道单点登录token
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appId", LiveGlobalConfig.getAppId());
        requestMap.put("timestamp", timestamp);
        requestMap.put("token", token);
        requestMap.put("sign", LiveSignUtil.getSign(requestMap, LiveGlobalConfig.getAppSecret()));
        //String response = HttpUtil.postFormBody(url, requestMap);
        String response = HttpUtil.postFormBody(url, requestMap);
        //TODO 判断response返回是否成功

        //2、生成频道授权登录地址
        String redirectUrl = String.format("https://console.polyv.net/live/#/teacher/%s/base-info/channel-info",
                channelId);
        String authURL = "https://console.polyv.net/teacher/auth-login";
        authURL += "?channelId=" + channelId + "&token=" + token + "&redirect=" + URLEncoder.encode(redirectUrl, "utf-8");
        LOG.info("频道单点登录设置成功，跳转地址为：{}", authURL);

        return authURL;
    }

    /**
     * 讲师单点登录
     * 为指定频道生成讲师单点登录的授权URL
     * 
     * @param channelId 频道ID
     * @return 返回讲师授权登录地址
     * @throws IOException IO异常
     * @throws NoSuchAlgorithmException 算法异常
     */
    public String teacherSSO(String channelId) throws IOException, NoSuchAlgorithmException {

        String timestamp = String.valueOf(System.currentTimeMillis());
        //自定义的token，只能使用一次，且10秒内有效
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        String url = String.format("http://api.polyv.net/live/v2/channels/%s/set-token", channelId);

        //1、设置频道单点登录token
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appId", LiveGlobalConfig.getAppId());
        requestMap.put("timestamp", timestamp);
        requestMap.put("token", token);
        requestMap.put("sign", LiveSignUtil.getSign(requestMap, LiveGlobalConfig.getAppSecret()));
        String response = HttpUtil.postFormBody(url, requestMap);
        //TODO 判断response返回是否成功

        //2、生成讲师授权登录地址
        String redirectUrl = "https://console.polyv.net/web-start/?channelId=" + channelId;
        String authURL = "https://console.polyv.net/teacher/auth-login";
        authURL +=
                "?channelId=" + channelId + "&token=" + token + "&redirect=" + URLEncoder.encode(redirectUrl, "utf-8");
        LOG.info("讲师单点登录地址设置成功，跳转地址为：{}", authURL);
        return authURL;
    }

    /**
     * 获取直播状态
     * 查询指定频道的直播流状态信息
     * 
     * @param channelId 频道ID
     * @return 返回直播状态信息的JSON字符串
     * @throws Exception 查询异常
     */
    public String getLiveStatus(String channelId) throws Exception {
        LiveListChannelStreamStatusV2Request liveListChannelStreamStatusV2Request = new LiveListChannelStreamStatusV2Request();
        List<LiveListChannelStreamStatusV2Response> liveListChannelStreamStatusV2Respons;
        try {
            //准备测试数据
            //String channelIds = String.format("%s,%s", super.getAloneChannelId(), super.createChannel());
            liveListChannelStreamStatusV2Request.setChannelIds(channelId);
            liveListChannelStreamStatusV2Respons = new LiveChannelStateServiceImpl().listChannelLiveStreamV2(
                    liveListChannelStreamStatusV2Request);
            Assert.assertNotNull(liveListChannelStreamStatusV2Respons);
            if (liveListChannelStreamStatusV2Respons != null) {
                //to do something ......
                LOG.debug("测试批量查询频道直播状态成功:{}", JSON.toJSONString(liveListChannelStreamStatusV2Respons));
                return JSON.toJSONString(liveListChannelStreamStatusV2Respons);
            }
            return null;
        } catch (PloyvSdkException e) {
            //参数校验不合格 或者 请求服务器端500错误，错误信息见PloyvSdkException.getMessage()
            LOG.error(e.getMessage(), e);
            // 异常返回做B端异常的业务逻辑，记录log 或者 上报到ETL 或者回滚事务
            throw e;
        } catch (Exception e) {
            LOG.error("SDK调用异常", e);
            throw e;
        }
    }

    /**
     * 设置播放器暖场图片
     * 为指定频道设置播放器的暖场图片和跳转链接
     * 
     * @param channelId 频道ID
     * @param coverImgUrl 封面图片URL
     * @param redirectUrl 跳转链接
     * @return 返回设置是否成功
     * @throws Exception 设置异常
     * @throws NoSuchAlgorithmException 算法异常
     * @throws URISyntaxException URI语法异常
     */
    public Boolean setPlayerWarmUpImg(String channelId, String coverImgUrl, String redirectUrl) throws Exception, NoSuchAlgorithmException, URISyntaxException {
        LiveSetPlayerImgRequest liveSetChatAdminDataRequest = new LiveSetPlayerImgRequest();
        Boolean liveSetChatAdminDataResponse = null;
        try {
            liveSetChatAdminDataRequest.setChannelId(channelId)
                    .setCoverImage(coverImgUrl)
                    .setCoverHref(redirectUrl);
            liveSetChatAdminDataResponse = new LivePlayerServiceImpl().setPlayerImg(liveSetChatAdminDataRequest);
            Assert.assertNotNull(liveSetChatAdminDataResponse);
            if (liveSetChatAdminDataResponse) {
                //to do something ......
                LOG.debug("测试设置播放器暖场图片成功 ");
                return true;
            }
        } catch (PloyvSdkException e) {
            //参数校验不合格 或者 请求服务器端500错误，错误信息见PloyvSdkException.getMessage()
            LOG.error(e.getMessage(), e);
            // 异常返回做B端异常的业务逻辑，记录log 或者 上报到ETL 或者回滚事务
            throw e;
        } catch (Exception e) {
            LOG.error("SDK调用异常", e);
            throw e;
        }
        return false;
    }

    /**
     * 助教单点登录
     * 为指定助教账号生成单点登录的授权URL
     * 
     * @param accountId 助教账号ID
     * @return 返回助教授权登录地址
     * @throws IOException IO异常
     * @throws NoSuchAlgorithmException 算法异常
     */
    public String assistantSSO(String accountId ) throws IOException, NoSuchAlgorithmException {
        //公共参数,填写自己的实际参数
        String appId = LiveGlobalConfig.getAppId();
        String appSecret = LiveGlobalConfig.getAppSecret();
//        String userId = super.userId;
        String timestamp = String.valueOf(System.currentTimeMillis());
        //自定义的token，只能使用一次，且10秒内有效
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //助教账号
//        String accountId = "0041965681";
        String url = String.format("http://api.polyv.net/live/v2/channels/%s/set-account-token", accountId);

        //1、设置助教单点登录token
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appId", appId);
        requestMap.put("timestamp", timestamp);
        requestMap.put("token", token);
        requestMap.put("sign", LiveSignUtil.getSign(requestMap, appSecret));
        String response = HttpUtil.postFormBody(url, requestMap);
        //TODO 判断response返回是否成功

        //2、生成助教授权登录地址
        String redirectUrl = "https://console.polyv.net/assistant/?accountId=" + accountId;
        String authURL = "https://console.polyv.net/teacher/auth-login";
        authURL +=
                "?channelId=" + accountId + "&token=" + token + "&redirect=" + URLEncoder.encode(redirectUrl, "utf-8");
        LOG.info("助教单点登录设置成功，跳转地址为：{}", authURL);
        return authURL;
    }

    /**
     * 获取子频道信息列表
     * 查询指定频道下所有角色的信息列表
     * 
     * @param channelId 频道ID
     * @return 返回子频道信息列表响应对象
     * @throws Exception 查询异常
     * @throws NoSuchAlgorithmException 算法异常
     */
    public LiveSonChannelInfoListResponse getSonChannelInfoList(String channelId) throws Exception, NoSuchAlgorithmException {
        LiveSonChannelInfoListRequest liveSonChannelInfoListRequest = new LiveSonChannelInfoListRequest();
        LiveSonChannelInfoListResponse liveSonChannelInfoResponse;
        try {
            //准备测试数据
            liveSonChannelInfoListRequest.setChannelId(channelId);
            liveSonChannelInfoResponse = new LiveChannelOperateServiceImpl().getSonChannelInfoList(
                    liveSonChannelInfoListRequest);
            Assert.assertNotNull(liveSonChannelInfoResponse);
            if (liveSonChannelInfoResponse != null) {
                //to do something ......
                LOG.debug("查询频道号下所有角色信息成功{}", JSON.toJSONString(liveSonChannelInfoResponse));
                return liveSonChannelInfoResponse;
            }
        } catch (PloyvSdkException e) {
            //参数校验不合格 或者 请求服务器端500错误，错误信息见PloyvSdkException.getMessage(),B
            LOG.error(e.getMessage(), e);
            // 异常返回做B端异常的业务逻辑，记录log 或者 上报到ETL 或者回滚事务
            throw e;
        } catch (Exception e) {
            LOG.error("SDK调用异常", e);
            throw e;
        }
        return null;
    }


}
