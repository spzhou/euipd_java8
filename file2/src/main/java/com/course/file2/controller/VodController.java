/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.file2.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.course.file2.dto.UploadVideoResponseDto;
import com.course.server.domain.RdmsVodFile;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsVodFileDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.RdmsVodFileService;
import com.course.server.util.Base64ToMultipartFile;
import com.course.server.util.CopyUtil;
import com.course.server.util.VodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

//文档: https://help.aliyun.com/zh/vod/user-guide/media-uploader-t/?spm=a2c4g.11186623.help-menu-29932.d_2_2_0.31e3eb68XHnaOY

@RestController
@RequestMapping("/admin")
public class VodController {

    private static final Logger LOG = LoggerFactory.getLogger(VodController.class);
    public static final String BUSINESS_NAME = "VOD上传";

    @Resource
    private RdmsVodFileService rdmsVodFileService;

    @Value("${vod.accessKeyId}")
    private String accessKeyId;

    @Value("${vod.accessKeySecret}")
    private String accessKeySecret;

    @Value("${vod.regionId}")
    String vodRegionId;

    @Value("${vod.categoryId}")
    String categoryId;

    @Value("${vod.workflowId}")
    String workflowId;

    @Value("${vod.storageLocation}")
    String storageLocation;

    //填入AccessKey信息
    private  DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = vodRegionId;  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    @RequestMapping(value = "/get-auth/{vod}", method = RequestMethod.GET)
    public ResponseDto getAuth(@PathVariable String vod) throws ClientException {
        LOG.info("获取播放授权开始, Vid= {}", vod);
        ResponseDto responseDto = new ResponseDto();
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = VodUtil.getVideoPlayAuth(client, vod);
            LOG.info("授权码 = {}", response.getPlayAuth());
            responseDto.setContent(response.getPlayAuth());
            //VideoMeta信息
            LOG.info("VideoMeta = {}", JSON.toJSONString(response.getVideoMeta()));
        } catch (Exception e) {
            LOG.info("ErrorMessage = " + e.getLocalizedMessage());
        }
        LOG.info("获取播放授权结束");
        return responseDto;
    }

    @PostMapping("/vod")
    public ResponseDto fileUpload(@RequestBody RdmsVodFileDto fileDto) throws Exception {
        LOG.info("上传文件开始");
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        Integer shardIndex = fileDto.getShardIndex();
        Integer shardSize = fileDto.getShardSize();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();

        String path = new StringBuffer(dir)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();

        String title = fileDto.getName().substring(0, fileDto.getName().lastIndexOf("."));
        UploadVideoVod uploadVideoVod = new UploadVideoVod();
        UploadStreamResponse response = uploadVideoVod.UploadStream(accessKeyId,accessKeySecret,title,fileDto.getName(), shard.getInputStream());
        if (response.isSuccess()) {
            LOG.info("VideoId=" + response.getVideoId() + "\n");

            LOG.info("保存文件记录开始");
            fileDto.setPath(path);
            fileDto.setVod(response.getVideoId());
            rdmsVodFileService.save(fileDto);
        } else {
             //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            LOG.info("VideoId=" + response.getVideoId() + "\n");
            LOG.info("ErrorCode=" + response.getCode() + "\n");
            LOG.info("ErrorMessage=" + response.getMessage() + "\n");
        }

        ResponseDto responseDto = new ResponseDto();
        if(response.isSuccess()){
            responseDto.setContent(fileDto);
        }else{
            responseDto.setContent(response);
            responseDto.setCode(response.getCode());
            responseDto.setMessage(response.getMessage());
            responseDto.setSuccess(false);
        }

        return responseDto;
    }


    @PostMapping("/file-save")
    public ResponseDto saveFile(@RequestBody RdmsVodFileDto fileDto) throws Exception {
        LOG.info("保存开始");
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        String path = new StringBuffer(dir)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();

            LOG.info("保存文件记录开始");
            fileDto.setPath(path);
            fileDto.setVod(fileDto.getVod());
            rdmsVodFileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(fileDto);
        return responseDto;
    }

    @GetMapping("/file-info/{key}")
    public ResponseDto getFile(@PathVariable String key) throws Exception {
        RdmsVodFile file = rdmsVodFileService.selectByKey(key);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(file);
        return responseDto;
    }

    /**
     * 获取视频信息
     * @param client 发送请求客户端
     * @return GetVideoInfoResponse 获取视频信息响应数据
     * @throws Exception
     */
    private GetVideoInfoResponse getVideoInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }


    /**
     * 获取视频上传地址和凭证
     * @param vodClient
     * @return
     * @throws ClientException
     * 文档:https://help.aliyun.com/zh/vod/developer-reference/api-vod-2017-03-21-createuploadvideo?spm=a2c4g.11186623.0.i9
     */
    public CreateUploadVideoResponse createUploadVideo(DefaultAcsClient vodClient, String fileName) throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setFileName(fileName);
        request.setTitle(fileName);
        //request.setDescription("this is desc");
        //request.setTags("tag1,tag2");
//        request.setCoverURL("http://vod.aliyun.com/test_cover_url.jpg");
        request.setCateId(Long.valueOf(categoryId));
//        request.setTemplateGroupId("174bd7dd9284f08e51ee849a75d1e8e8");
        request.setWorkflowId(workflowId);
        request.setStorageLocation(storageLocation);
        //request.setAppId("app-1000000");
        //设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        CreateUploadVideoResponse acsResponse = vodClient.getAcsResponse(request);
        return acsResponse;
    }
    /**
     * 刷新音/视频上传凭证
     * @param client 发送请求客户端
     * @return RefreshUploadVideoResponse 刷新音/视频上传凭证响应数据
     * @throws Exception
     */
    public RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client, String videoId) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        //音频或视频ID
        request.setVideoId(videoId);
        RefreshUploadVideoResponse acsResponse = client.getAcsResponse(request);
        return acsResponse;
    }

    // 请求示例
    @PostMapping("/get-address-auth")
    public ResponseDto getUploadAddressAndUploadAuth(@RequestBody RdmsVodFileDto fileDto) throws ClientException {
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        try {
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            LOG.info("创建链接client:"+client.toString());
            response = createUploadVideo(client,fileDto.getName());
            LOG.info("获取授权信息返回数据:"+response.toString());
            LOG.info("VideoId = " + response.getVideoId() + "\n");
            LOG.info("UploadAddress = " + response.getUploadAddress() + "\n");
            LOG.info("UploadAuth = " + response.getUploadAuth() + "\n");
            LOG.info("RequestId = " + response.getRequestId() + "\n");
        } catch (Exception e) {
            LOG.info("ErrorMessage = " + e.getLocalizedMessage());
        }

        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        String path = new StringBuffer(dir)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();

        UploadVideoResponseDto videoResponseDto = CopyUtil.copy(response, UploadVideoResponseDto.class);
        videoResponseDto.setPath(path);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(videoResponseDto);
        return responseDto;
    }

    // 请求示例
    @PostMapping("/refresh-address-auth")
    public ResponseDto refreshUploadAddressAndUploadAuth(@RequestBody RdmsVodFileDto fileDto) throws ClientException {
        RefreshUploadVideoResponse response = new RefreshUploadVideoResponse();
        try {
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            response = refreshUploadVideo(client, fileDto.getVod());
            LOG.info("VideoId = " + response.getVideoId() + "\n");
            LOG.info("UploadAddress = " + response.getUploadAddress() + "\n");
            LOG.info("UploadAuth = " + response.getUploadAuth() + "\n");
            LOG.info("RequestId = " + response.getRequestId() + "\n");
        } catch (Exception e) {
            LOG.info("ErrorMessage = " + e.getLocalizedMessage());
        }

        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        String path = new StringBuffer(dir)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();

        UploadVideoResponseDto videoResponseDto = CopyUtil.copy(response, UploadVideoResponseDto.class);
        videoResponseDto.setPath(path);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(response);
        return responseDto;
    }


    // 请求示例
    public GetVideoInfoResponse getVodInfo(String videoId) throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetVideoInfoResponse response = new GetVideoInfoResponse();
        try {
            response = getVideoInfo(client, videoId);

            LOG.info("Title = " + response.getVideo().getTitle() + "\n");
            LOG.info("Description = " + response.getVideo().getDescription() + "\n");
        } catch (Exception e) {
            LOG.info("ErrorMessage = " + e.getLocalizedMessage());
        }
        LOG.info("RequestId = " + response.getRequestId() + "\n");

        return response;
    }

}
