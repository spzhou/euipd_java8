/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsFileText;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsFileTextDto;
import com.course.server.service.rdms.RdmsFileTextService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/file-text")
public class FileTextController {
    private static final Logger LOG = LoggerFactory.getLogger(FileTextController.class);
    public static final String BUSINESS_NAME = "字符文件";

    @Resource
    private RdmsFileTextService rdmsFileTextService;


    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsFileTextDto>> listFileTextByCustomerId(@RequestBody PageDto<RdmsFileTextDto> pageDto) {
        ResponseDto<PageDto<RdmsFileTextDto>> responseDto = new ResponseDto<>();
        rdmsFileTextService.listFileTextByCustomerId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }


    @PostMapping("/get-text/{textId}")
    public ResponseDto<RdmsFileTextDto> getFileTextById(@PathVariable String textId) {
        ResponseDto<RdmsFileTextDto> responseDto = new ResponseDto<>();
        RdmsFileText rdmsFileText = rdmsFileTextService.selectByPrimaryKey(textId);
        RdmsFileTextDto copy = CopyUtil.copy(rdmsFileText, RdmsFileTextDto.class);
        responseDto.setContent(copy);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsFileTextDto> save(@RequestBody RdmsFileTextDto textDto) {
        ResponseDto<RdmsFileTextDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(textDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(textDto.getText(), "文字内容");
        ValidatorUtil.require(textDto.getUse(), "内容分类");

        ValidatorUtil.length(textDto.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(textDto.getText(), "需求描述", 0, 65535);
        ValidatorUtil.length(textDto.getUse(), "生效条件", 0, 20);

        RdmsFileText rdmsFileText = CopyUtil.copy(textDto, RdmsFileText.class);
        rdmsFileTextService.save(rdmsFileText);

        responseDto.setContent(textDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsFileTextService.delete(id);
        return responseDto;
    }

}
