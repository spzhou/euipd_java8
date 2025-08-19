/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.aliyun.vod20170321.models.*;
import com.course.server.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class RdmsAliyunVodService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsAliyunVodService.class);

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
     * @param videoId
     * @return
     * @throws Exception
     */
    public GetPlayInfoResponse GetPlayInfo(String videoId) throws Exception {
        com.aliyun.vod20170321.Client client = RdmsAliyunVodService.createClient(Constants.ALIBABA_CLOUD_ACCESS_KEY_ID, Constants.ALIBABA_CLOUD_ACCESS_KEY_SECRET);
        GetPlayInfoRequest getPlayInfoRequest = new GetPlayInfoRequest()
                .setVideoId(videoId);
        RuntimeOptions runtime = new RuntimeOptions();
        LOG.info("VOD:"+videoId);
        GetPlayInfoResponse resp = client.getPlayInfoWithOptions(getPlayInfoRequest, runtime);
        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(resp)));
        return resp;

    }

    /**
     * 获取音视频播放时所需的播放凭证
     * @param videoId
     * @return
     * @throws Exception
     */
    public GetVideoPlayAuthResponse GetVideoPlayAuth(String videoId) throws Exception {
        com.aliyun.vod20170321.Client client = RdmsAliyunVodService.createClient(Constants.ALIBABA_CLOUD_ACCESS_KEY_ID, Constants.ALIBABA_CLOUD_ACCESS_KEY_SECRET);
        com.aliyun.vod20170321.models.GetVideoPlayAuthRequest getVideoPlayAuthRequest = new com.aliyun.vod20170321.models.GetVideoPlayAuthRequest()
                .setVideoId(videoId);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            GetVideoPlayAuthResponse videoPlayAuthWithOptions = client.getVideoPlayAuthWithOptions(getVideoPlayAuthRequest, runtime);
            return videoPlayAuthWithOptions;

        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
            return null;
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
            return null;
        }
    }

    /**
     * 获取视频信息
     * @param videoId
     * @return
     * @throws Exception
     */
    public GetVideoInfoResponse GetVideoInfo(String videoId) throws Exception {
        GetVideoInfoResponse videoInfoWithOptions = new GetVideoInfoResponse();
        com.aliyun.vod20170321.Client client = RdmsAliyunVodService.createClient(Constants.ALIBABA_CLOUD_ACCESS_KEY_ID, Constants.ALIBABA_CLOUD_ACCESS_KEY_SECRET);
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
     * @param videoId
     * @return
     * @throws Exception
     */
    private String getVideoOrigInfo(String videoId) throws Exception {
        GetMezzanineInfoResponse mezzanineInfoWithOptions =new GetMezzanineInfoResponse();
        com.aliyun.vod20170321.Client client = RdmsAliyunVodService.createClient(Constants.ALIBABA_CLOUD_ACCESS_KEY_ID, Constants.ALIBABA_CLOUD_ACCESS_KEY_SECRET);
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
     *
     * @throws Exception
     */
    private String submitTranscodeJobs(String videoId) throws Exception {
        com.aliyun.vod20170321.Client client = RdmsAliyunVodService.createClient(Constants.ALIBABA_CLOUD_ACCESS_KEY_ID, Constants.ALIBABA_CLOUD_ACCESS_KEY_SECRET);
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
