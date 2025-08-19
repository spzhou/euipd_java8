/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsProductManager;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCustomerUserDto;
import com.course.server.dto.rdms.RdmsProductManagerDto;
import com.course.server.service.rdms.RdmsProductManagerService;
import com.course.server.service.rdms.RdmsProductManagerService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product-manager")
public class ProductManagerController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductManagerController.class);
    public static final String BUSINESS_NAME = "产品经理";

    @Resource
    private RdmsProductManagerService rdmsProductManagerService;

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsProductManagerDto>> list(@RequestBody PageDto<RdmsProductManagerDto> pageDto) {
        ResponseDto<PageDto<RdmsProductManagerDto>> responseDto = new ResponseDto<>();
        rdmsProductManagerService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }


    /**
     * 列表查询
     */
    @PostMapping("/listByCustomerId/{customerId}")
    public ResponseDto<List<String>> listByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        List<String> rdmsProductManagerDtos = rdmsProductManagerService.listByCustomerId(customerId);
        responseDto.setContent(rdmsProductManagerDtos);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsCustomerUserDto>> getListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUserDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUserDto> projectManagers = rdmsProductManagerService.getListByCustomerId(customerId);
        responseDto.setContent(projectManagers);
        return responseDto;
    }


    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsProductManagerDto> save(@RequestBody RdmsProductManagerDto rdmsProductManagerDto) {
        ResponseDto<RdmsProductManagerDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsProductManagerDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(rdmsProductManagerDto.getProductManagerIds(), "产品经理ID");

        //将数组转换成JSON string
        String jsonString = JSON.toJSONString(rdmsProductManagerDto.getProductManagerIds());

        RdmsProductManager rdmsProductManager = CopyUtil.copy(rdmsProductManagerDto, RdmsProductManager.class);
        rdmsProductManager.setProductManagerIdListStr(jsonString);
        rdmsProductManagerService.save(rdmsProductManager);

        responseDto.setContent(rdmsProductManagerDto);
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
        rdmsProductManagerService.delete(id);
        return responseDto;
    }

}
