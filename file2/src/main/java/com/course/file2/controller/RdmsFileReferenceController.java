/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.file2.controller;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsFileReferenceDto;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rdms/reference")
public class RdmsFileReferenceController {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsFileReferenceController.class);
    public static final String BUSINESS_NAME = "参考文献管理";

    @Resource
    private RdmsFileReferenceService rdmsFileReferenceService;

    @Value("${oss.domain}")
    private String ossDomain;


    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsFileReferenceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsFileReferenceDto> save(@RequestBody RdmsFileReferenceDto fileReferenceDto) {
        ResponseDto<RdmsFileReferenceDto> responseDto = new ResponseDto<>();
        RdmsFileReference rdmsFileReference = CopyUtil.copy(fileReferenceDto, RdmsFileReference.class);
        rdmsFileReferenceService.save(rdmsFileReference);
        RdmsFileReferenceDto rdmsFileReferenceDto = CopyUtil.copy(rdmsFileReference, RdmsFileReferenceDto.class);
        responseDto.setContent(rdmsFileReferenceDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsFileReferenceService.delete(id);
        return responseDto;
    }

}
