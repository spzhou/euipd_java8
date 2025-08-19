/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsIpmt;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsIpmtDto;
import com.course.server.service.rdms.RdmsIpmtService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ipmt")
public class IpmtController {
    private static final Logger LOG = LoggerFactory.getLogger(IpmtController.class);
    public static final String BUSINESS_NAME = "IPMT";

    @Resource
    private RdmsIpmtService rdmsIpmtService;

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsIpmtDto>> list(@RequestBody PageDto<RdmsIpmtDto> pageDto) {
        ResponseDto<PageDto<RdmsIpmtDto>> responseDto = new ResponseDto<>();
        rdmsIpmtService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }


    /**
     * 列表查询
     */
    @PostMapping("/listByCustomerId/{customerId}")
    public ResponseDto<List<String>> list(@PathVariable String customerId) {
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        List<String> rdmsIpmtDtos = rdmsIpmtService.listByCustomerId(customerId);
        responseDto.setContent(rdmsIpmtDtos);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsIpmtDto> save(@RequestBody RdmsIpmtDto rdmsIpmtDto) {
        ResponseDto<RdmsIpmtDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsIpmtDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(rdmsIpmtDto.getIpmtIds(), "IPMT ID");

        //将数组转换成JSON string
        String jsonString = JSON.toJSONString(rdmsIpmtDto.getIpmtIds());

        RdmsIpmt rdmsIpmt = CopyUtil.copy(rdmsIpmtDto, RdmsIpmt.class);
        rdmsIpmt.setIpmtIdList(jsonString);
        rdmsIpmtService.save(rdmsIpmt);

        responseDto.setContent(rdmsIpmtDto);
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
        rdmsIpmtService.delete(id);
        return responseDto;
    }

}
