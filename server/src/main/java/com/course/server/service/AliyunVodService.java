/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.aliyun.vod20170321.models.*;
import com.course.server.constants.Constants;
import com.course.server.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AliyunVodService {

    private static final Logger LOG = LoggerFactory.getLogger(AliyunVodService.class);

    /**
     * 创建阿里云VOD客户端
     * 使用AccessKey ID和Secret创建VOD服务客户端实例
     * 
     * @param accessKeyId 访问密钥ID
     * @param accessKeySecret 访问密钥密文
     * @return 返回VOD客户端实例
     * @throws Exception 创建客户端异常
     */
    public static com.aliyun.vod20170321.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "vod.cn-shanghai.aliyuncs.com";
        return new com.aliyun.vod20170321.Client(config);
    }

    /**
     * 获取视频的播放信息
     * 通过视频ID获取视频的播放地址和相关信息
     * 
     * @param videoId 视频ID
     * @return 返回视频播放信息响应对象
     * @throws Exception 获取播放信息异常
     */
    public GetPlayInfoResponse GetPlayInfo(String videoId) throws Exception {
        com.aliyun.vod20170321.Client client = AliyunVodService.createClient(Constants.VOD_ACCESS_KEY_ID, Constants.VOD_ACCESS_KEY_SECRET);
        GetPlayInfoRequest getPlayInfoRequest = new GetPlayInfoRequest()
                .setVideoId(videoId);
        RuntimeOptions runtime = new RuntimeOptions();
        LOG.info("VOD:"+videoId);
        GetPlayInfoResponse resp = client.getPlayInfoWithOptions(getPlayInfoRequest, runtime);
        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(resp)));
        return resp;

    }

    /**
     * 获取视频信息
     * 通过视频ID获取视频的基本信息
     * 
     * @param videoId 视频ID
     * @return 返回视频信息响应对象
     * @throws Exception 获取视频信息异常
     */
    public GetVideoInfoResponse GetVideoInfo(String videoId) throws Exception {
        GetVideoInfoResponse videoInfoWithOptions = new GetVideoInfoResponse();
        com.aliyun.vod20170321.Client client = AliyunVodService.createClient(Constants.VOD_ACCESS_KEY_ID, Constants.VOD_ACCESS_KEY_SECRET);
        GetVideoInfoRequest getVideoInfoRequest = new GetVideoInfoRequest()
                .setVideoId(videoId);
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            videoInfoWithOptions = client.getVideoInfoWithOptions(getVideoInfoRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return videoInfoWithOptions;

    }

    /**
     * 获取视频的原始文件Url
     * 通过视频ID获取视频原始文件的下载地址
     * 
     * @param videoId 视频ID
     * @return 返回视频原始文件的URL地址
     * @throws Exception 获取原始文件信息异常
     */
    private String getVideoOrigInfo(String videoId) throws Exception {
        GetMezzanineInfoResponse mezzanineInfoWithOptions =new GetMezzanineInfoResponse();
        com.aliyun.vod20170321.Client client = AliyunVodService.createClient(Constants.VOD_ACCESS_KEY_ID, Constants.VOD_ACCESS_KEY_SECRET);
        GetMezzanineInfoRequest getMezzanineInfoRequest = new GetMezzanineInfoRequest()
                .setVideoId(videoId)
                .setOutputType("cdn")
                .setAdditionType("video");
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            mezzanineInfoWithOptions = client.getMezzanineInfoWithOptions(getMezzanineInfoRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return mezzanineInfoWithOptions.body.getMezzanine().getFileURL();
    }


    /**
     * 将上传到阿里云的视频转码成手机播放不加密的MP4视频
     * 提交视频转码任务，使用指定的转码模板
     * 
     * @param videoId 视频ID
     * @return 返回转码任务ID
     * @throws Exception 提交转码任务异常
     */
    private String submitTranscodeJobs(String videoId) throws Exception {
        com.aliyun.vod20170321.Client client = AliyunVodService.createClient(Constants.VOD_ACCESS_KEY_ID, Constants.VOD_ACCESS_KEY_SECRET);
        SubmitTranscodeJobsRequest submitTranscodeJobsRequest = new SubmitTranscodeJobsRequest()
                .setTemplateGroupId("1e871ca04b3f530ba646345d76c2dbc8") //标清转码模板
                .setVideoId(videoId);
        RuntimeOptions runtime = new RuntimeOptions();
        SubmitTranscodeJobsResponse resp = client.submitTranscodeJobsWithOptions(submitTranscodeJobsRequest, runtime);
        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(resp)));

        LOG.info(resp.getBody().getTranscodeTaskId());
        return resp.getBody().getTranscodeTaskId();

    }



}
