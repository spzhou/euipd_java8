/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.file2.controller;

import com.aliyun.sts20150401.models.AssumeRoleResponse;
import com.aliyun.oss.*;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.tea.TeaException;
import com.course.server.domain.RdmsCustomerConfig;
import com.course.server.domain.RdmsFileDownload;
import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsFileApplyDto;
import com.course.server.dto.rdms.RdmsFileDto;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.rdms.CustomerConfigStatusEnum;
import com.course.server.enums.rdms.FileApplicationStatusEnum;
import com.course.server.service.rdms.*;
import com.course.server.service.util.IpUtils;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/rdms")
public class RdmsOssController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsOssController.class);

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.bucket_web}")
    private String bucket_web;

    @Value("${oss.domain}")
    private String ossDomain;

    @Value("${oss.domain_web}")
    private String ossDomain_web;

    @Value("${oss.role_arn}")
    private String role_arn;

    @Value("${oss.endpoint_sts}")
    private String endpoint_sts;

    public static final String BUSINESS_NAME = "文件上传下载";

    @Resource
    private RdmsCustomerConfigService rdmsCustomerConfigService;
    @Resource
    private RdmsFileDownloadService rdmsFileDownloadService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsFileApplyService rdmsFileApplyService;
    @Resource
    private IpUtils ipUtils;
    @Resource
    private RdmsCustomerService rdmsCustomerService;

    @PostMapping("/oss-simple")
    public ResponseDto fileUpload(@RequestParam String folder, MultipartFile file, Integer size, String use) throws Exception {
        LOG.info("上传文件开始");
        FileDto fileDto = new FileDto();
        FileGroupingEnum useEnum = FileGroupingEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        fileDto.setKey(key);
        String fileName = file.getOriginalFilename();
        fileDto.setName(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        fileDto.setSuffix(suffix);
        String dir = useEnum.name().toLowerCase();
        fileDto.setUse(dir);
//        String path = dir + "/" + key + "." + suffix;
        String path = "";
        if(folder.equals("")){
            path = dir + "/" + key + "-" + fileName;
        }else{
            path = dir + "/" + folder + "/" + key + "-" + fileName;
        }
        fileDto.setPath(path);
        fileDto.setAbsPath(ossDomain + path);
        fileDto.setSize(size);
        fileDto.setCreatedAt(new Date());

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            ossClient.putObject(bucket, path, new ByteArrayInputStream(file.getBytes()));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(fileDto);

        return responseDto;
    }


    @PostMapping("/oss-simple-rdms")
    public ResponseDto<FileDto> fileUploadV2(@RequestParam String folder, MultipartFile file, Integer size, String use, String customer) throws Exception {
        LOG.info("上传文件开始");
        //处理客户配置
        String accessKeyId_t;
        String accessKeySecret_t;
        String endpoint_t;
        String bucket_t;
        String ossDomain_t;

        FileDto fileDto = new FileDto();
        //计算用户存储空间使用情况
        //正常使用账号, 不做空间限制
        /*Integer customerFilesSumSize = rdmsFileService.getCustomerFilesSumSize(customer);
        double usedSpace = (double) customerFilesSumSize / 1024.0 / 1024.0;
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customer);
        double storageSpace = rdmsCustomer.getStorageSpace() * 1024.0;  //Mb

        if(usedSpace >= storageSpace){
            //文件存储空间不足
            throw new BusinessException(BusinessExceptionCode.FILE_SPACE_LIMIT_ERROR);
        }else*/
        {
            RdmsCustomerConfig customerConfig = rdmsCustomerConfigService.getCustomerConfig(customer);
            if(ObjectUtils.notEqual(null, customerConfig)){
                if(customerConfig.getStatus().equals(CustomerConfigStatusEnum.USED.getStatus())){
                    accessKeyId_t = customerConfig.getOssAccessKey();
                    accessKeySecret_t = customerConfig.getOssAccessKeySecret();
                    endpoint_t = customerConfig.getOssEndpoint();
                    bucket_t = customerConfig.getOssBucket();
                    ossDomain_t = customerConfig.getOssDomain();
                }else{
                    accessKeyId_t = accessKeyId;
                    accessKeySecret_t = accessKeySecret;
                    endpoint_t = endpoint;
                    bucket_t = bucket;
                    ossDomain_t = ossDomain;  //需要进行签名访问的bucket
                }
            }else{
                accessKeyId_t = accessKeyId;
                accessKeySecret_t = accessKeySecret;
                endpoint_t = endpoint;
                bucket_t = bucket;
                ossDomain_t = ossDomain;
            }

//            FileDto fileDto = new FileDto();
            FileGroupingEnum useEnum = FileGroupingEnum.getByCode(use);
            assert useEnum != null;
            String key = UuidUtil.getShortUuid();
            fileDto.setKey(key);
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            fileDto.setName(fileName);
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            fileDto.setSuffix(suffix);
            fileDto.setUse(useEnum.getCode().toLowerCase());
            fileDto.setCustomer(customer);
            String dir = "";
            if(!fileDto.getCustomer().isEmpty()){
                dir = customer + "/" + fileDto.getUse();
            }else{
                dir = fileDto.getUse();
            }
            String path = "";
            if(folder.isEmpty() || folder.equals("null")){
                path = dir + "/" + key + "-" + fileName;
            }else{
                path = dir + "/" + folder + "/" + key + "-" + fileName;
            }
            fileDto.setPath(path);
            fileDto.setAbsPath(ossDomain_t + path);
            fileDto.setSize(size);
            fileDto.setCreatedAt(new Date());

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint_t, accessKeyId_t, accessKeySecret_t);

            try {
                ossClient.putObject(bucket_t, path, new ByteArrayInputStream(file.getBytes()));
            } catch (OSSException oe) {
                System.out.println("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                System.out.println("Error Message:" + oe.getErrorMessage());
                System.out.println("Error Code:" + oe.getErrorCode());
                System.out.println("Request ID:" + oe.getRequestId());
                System.out.println("Host ID:" + oe.getHostId());
            } catch (ClientException ce) {
                System.out.println("Caught an ClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with OSS, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message:" + ce.getMessage());
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }

        ResponseDto<FileDto> responseDto = new ResponseDto<>();
        responseDto.setContent(fileDto);

        return responseDto;
    }

    @PostMapping("/oss-file-upload-web")
    public ResponseDto<FileDto> ossFileUploadWeb(@RequestParam String folder, MultipartFile file, Integer size, String use, String customer) throws Exception {
        LOG.info("上传文件开始");
        //处理客户配置
        String accessKeyId_t;
        String accessKeySecret_t;
        String endpoint_t;
        String bucket_t;
        String ossDomain_t;

        RdmsCustomerConfig customerConfig = rdmsCustomerConfigService.getCustomerConfig(customer);
        if(ObjectUtils.notEqual(null, customerConfig)){
            if(customerConfig.getStatus().equals(CustomerConfigStatusEnum.USED.getStatus())){
                accessKeyId_t = customerConfig.getOssAccessKey();
                accessKeySecret_t = customerConfig.getOssAccessKeySecret();
                endpoint_t = customerConfig.getOssEndpoint();
                bucket_t = customerConfig.getOssBucketWeb();
                ossDomain_t = customerConfig.getOssDomainWeb();
            }else{
                accessKeyId_t = accessKeyId;
                accessKeySecret_t = accessKeySecret;
                endpoint_t = endpoint;
                bucket_t = bucket_web;
                ossDomain_t = ossDomain_web;   //公共读的bucket
            }
        }else{
            accessKeyId_t = accessKeyId;
            accessKeySecret_t = accessKeySecret;
            endpoint_t = endpoint;
            bucket_t = bucket_web;
            ossDomain_t = ossDomain_web;
        }


        FileDto fileDto = new FileDto();
        FileGroupingEnum useEnum = FileGroupingEnum.getByCode(use);
        assert useEnum != null;
        String key = UuidUtil.getShortUuid();
        fileDto.setKey(key);
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        fileDto.setName(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        fileDto.setSuffix(suffix);
        fileDto.setUse(useEnum.getCode().toLowerCase());
        fileDto.setCustomer(customer);
        String dir = "";
        if(!fileDto.getCustomer().isEmpty()){
            dir = customer + "/" + fileDto.getUse();
        }else{
            dir = fileDto.getUse();
        }
        String path = "";
        if(folder.isEmpty() || folder.equals("null")){
            path = dir + "/" + key + "-" + fileName;
        }else{
            path = dir + "/" + folder + "/" + key + "-" + fileName;
        }
        fileDto.setPath(path);
        fileDto.setAbsPath(ossDomain_t + path);
        fileDto.setSize(size);
        fileDto.setCreatedAt(new Date());

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint_t, accessKeyId_t, accessKeySecret_t);

        try {
            ossClient.putObject(bucket_t, path, new ByteArrayInputStream(file.getBytes()));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        ResponseDto<FileDto> responseDto = new ResponseDto<>();
        responseDto.setContent(fileDto);

        return responseDto;
    }

    @PostMapping("/oss-get-signed-url")
    public ResponseDto<String> getSignedUrl( @RequestParam @NotEmpty String fileFullName,
                                             @RequestParam @NotEmpty String customer,
                                             @RequestParam @NotEmpty String user,
                                             HttpServletRequest servletRequest) throws Exception {
        LOG.info("获取签名Url");
        //处理客户配置
        String accessKeyId_t;
        String accessKeySecret_t;
        String endpoint_t;
        String bucket_t;
        String ossDomain_t;

        RdmsCustomerConfig customerConfig = rdmsCustomerConfigService.getCustomerConfig(customer);
        if(ObjectUtils.notEqual(null, customerConfig)){
            if(customerConfig.getStatus().equals(CustomerConfigStatusEnum.USED.getStatus())){
                accessKeyId_t = customerConfig.getOssAccessKey();
                accessKeySecret_t = customerConfig.getOssAccessKeySecret();
                endpoint_t = customerConfig.getOssEndpoint();
                bucket_t = customerConfig.getOssBucket();
                ossDomain_t = customerConfig.getOssDomain();
            }else{
                accessKeyId_t = accessKeyId;
                accessKeySecret_t = accessKeySecret;
                endpoint_t = endpoint;
                bucket_t = bucket;
                ossDomain_t = ossDomain;
            }
        }else{
            accessKeyId_t = accessKeyId;
            accessKeySecret_t = accessKeySecret;
            endpoint_t = endpoint;
            bucket_t = bucket;
            ossDomain_t = ossDomain;
        }

        AssumeRoleResponse stsAssumeRole = this.createSTSAssumeRole();
        String accessKeyId_sts = stsAssumeRole.getBody().getCredentials().getAccessKeyId();
        String accessKeySecret_sts = stsAssumeRole.getBody().getCredentials().getAccessKeySecret();
        String securityToken_sts = stsAssumeRole.getBody().getCredentials().getSecurityToken();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId_sts, accessKeySecret_sts, securityToken_sts);

        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint_t, accessKeyId_t, accessKeySecret_t);

        URL signedUrl = null;
        String url = null;
        try {
            // 指定生成的签名URL过期时间，单位为毫秒。本示例以设置过期时间为1小时为例。
            Date expiration = new Date(new Date().getTime() + 3600 * 1000L);

            // 生成签名URL。
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket_t, fileFullName, HttpMethod.GET);
            // 设置过期时间。
            request.setExpiration(expiration);

            // 通过HTTP GET请求生成签名URL。
            signedUrl = ossClient.generatePresignedUrl(request);
//            url = signedUrl.getProtocol()+"://"+signedUrl.getHost()+signedUrl.getFile();
            url = "https://"+signedUrl.getHost()+signedUrl.getFile();
            // 打印签名URL。
            System.out.println("signed url for getObject: " + signedUrl);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            /* Do not forget to shut down the client finally to release all allocated resources.*/
            ossClient.shutdown();
        }

        //将用户下载的文件写入下载文件数据表
        RdmsFileDto fileByFullName = rdmsFileService.getFileByFullName(fileFullName);
        if(!org.springframework.util.ObjectUtils.isEmpty(fileByFullName) && !fileByFullName.getOperatorId().equals(user)){
            RdmsFileDownload fileDownload = CopyUtil.copy(fileByFullName, RdmsFileDownload.class);
            fileDownload.setId(null);
            fileDownload.setFileId(fileByFullName.getId());
            fileDownload.setDownloadorId(user);
            fileDownload.setDownloadTime(new Date());
            fileDownload.setUpdateTime(new Date());
            fileDownload.setDownloadIp(ipUtils.getIpAddress(servletRequest));
            rdmsFileDownloadService.save(fileDownload);
        }
        //判断所下载的文件是否在申请列表中,如果在申请列表中,将申请记录标记为完成
        List<RdmsFileApplyDto> applyFileList = rdmsFileApplyService.getApplyFileListByFileIdAndCustomerUserId(fileByFullName.getId(), user);
        if(!CollectionUtils.isEmpty(applyFileList)){
            for(RdmsFileApplyDto applyDto: applyFileList){
                applyDto.setApplicationStatus(FileApplicationStatusEnum.COMPLETE.getStatus());
                rdmsFileApplyService.update(applyDto);
            }
        }

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setContent(url);

        return responseDto;
    }

    public com.aliyun.sts20150401.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Sts
        config.endpoint = endpoint_sts;
        return new com.aliyun.sts20150401.Client(config);
    }

    /**
     * 创建临时的STS角色,从而获得临时的key等参数
     * @return
     * @throws Exception
     */
    private AssumeRoleResponse createSTSAssumeRole() throws Exception {
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        //com.aliyun.sts20150401.Client client = Sample.createClient(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"), System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));

        com.aliyun.sts20150401.Client client = this.createClient(accessKeyId, accessKeySecret);
        com.aliyun.sts20150401.models.AssumeRoleRequest assumeRoleRequest = new com.aliyun.sts20150401.models.AssumeRoleRequest()
                .setRoleArn(role_arn)
                .setDurationSeconds(3600L)
                .setRoleSessionName("zhoushuopeng")
                .setExternalId("abcd1234");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            AssumeRoleResponse assumeRoleResponse = client.assumeRoleWithOptions(assumeRoleRequest, runtime);
            return assumeRoleResponse;
        } catch (TeaException error) {
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
            return null;
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
            return null;
        }
    }

}
