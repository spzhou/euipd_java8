/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.dto.FileDto;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.ftp.UploadStatusEnum;
import com.course.server.service.util.ftp.FTPConfig;
import com.course.server.service.util.ftp.FTPUtil;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class RdmsFtpService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsFtpService.class);

    /**
     * 注入 ftp 连接配置
     */
    @Autowired
    FTPConfig config;

    @Autowired
    FTPUtil ftpUtil;

    public FileDto singleUpload(@RequestParam String folder, MultipartFile file, Integer size, String use, String customer) throws IOException {

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
        fileDto.setAbsPath(config.getWorkingDirectory() + "/" + path);
        fileDto.setSize(size);
        fileDto.setCreatedAt(new Date());

        InputStream input = new ByteArrayInputStream(file.getBytes());

        String remotePath = "/" + path;  //相对于ftp用户根目录的路径
        String name = key + "-" + fileName;
        ftpUtil.upload(remotePath, name, input);
        return fileDto;
    }

}
