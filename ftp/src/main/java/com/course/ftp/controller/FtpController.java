/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.ftp.controller;
import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.rdms.RdmsFtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

@RestController
@RequestMapping("/ftp")
public class FtpController {
    private static final Logger LOG = LoggerFactory.getLogger(FtpController.class);
    public static final String BUSINESS_NAME = "FTP";

    @Resource
    private RdmsFtpService rdmsFtpService;

    /**
     * 单文件上传接口
     * 处理单个文件的上传请求，支持指定文件夹、文件大小限制、用途和客户信息
     * 
     * @param folder 目标文件夹路径
     * @param file 要上传的文件
     * @param size 文件大小限制（字节）
     * @param use 文件用途说明
     * @param customer 客户标识
     * @return 返回上传结果，包含文件信息
     * @throws Exception 上传过程中可能出现的异常
     */
    @PostMapping("/single-upload")
    public ResponseDto<FileDto> singleUpload(@RequestParam String folder, MultipartFile file, Integer size, String use, String customer) throws Exception {
        LOG.info("上传文件开始");
        FileDto fileDto = rdmsFtpService.singleUpload(folder, file, size, use, customer);
        ResponseDto<FileDto> responseDto = new ResponseDto<>();
        responseDto.setContent(fileDto);

        return responseDto;
    }


}
