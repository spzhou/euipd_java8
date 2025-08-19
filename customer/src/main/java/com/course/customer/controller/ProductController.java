/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCustomerUserJobLevel;
import com.course.server.domain.RdmsProduct;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsProductDto;
import com.course.server.dto.rdms.RdmsProductDto;
import com.course.server.service.rdms.RdmsCustomerUserJobLevelService;
import com.course.server.service.rdms.RdmsProductService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    public static final String BUSINESS_NAME = "产品管理";

    @Resource
    private RdmsProductService rdmsProductService;


    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsProductDto>> list(@RequestBody PageDto<RdmsProductDto> pageDto) {
        ResponseDto<PageDto<RdmsProductDto>> responseDto = new ResponseDto<>();
        rdmsProductService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsProductDto> save(@RequestBody RdmsProductDto productDto) {
        ResponseDto<RdmsProductDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(productDto.getCustomerId(), "客户ID");

        ValidatorUtil.length(productDto.getCustomerId(), "需求名称", 8, 8);

        RdmsProduct rdmsProduct = CopyUtil.copy(productDto, RdmsProduct.class);
        rdmsProductService.save(rdmsProduct);

        responseDto.setContent(productDto);
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
        rdmsProductService.delete(id);
        return responseDto;
    }

}
