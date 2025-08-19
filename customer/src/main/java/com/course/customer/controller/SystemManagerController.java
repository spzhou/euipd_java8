/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsSystemManager;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCustomerUserDto;
import com.course.server.dto.rdms.RdmsSystemManagerDto;
import com.course.server.service.rdms.RdmsSystemManagerService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system-manager")
public class SystemManagerController {
    private static final Logger LOG = LoggerFactory.getLogger(SystemManagerController.class);
    public static final String BUSINESS_NAME = "系统经理";

    @Resource
    private RdmsSystemManagerService rdmsSystemManagerService;

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsSystemManagerDto>> list(@RequestBody PageDto<RdmsSystemManagerDto> pageDto) {
        ResponseDto<PageDto<RdmsSystemManagerDto>> responseDto = new ResponseDto<>();
        rdmsSystemManagerService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/listByCustomerId/{customerId}")
    public ResponseDto<List<String>> listByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        List<String> rdmsSystemManagerDtos = rdmsSystemManagerService.listByCustomerId(customerId);
        responseDto.setContent(rdmsSystemManagerDtos);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsCustomerUserDto>> getListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUserDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUserDto> systemManagers = rdmsSystemManagerService.getListByCustomerId(customerId);
        responseDto.setContent(systemManagers);
        return responseDto;
    }

    @PostMapping("/listSystemManagerByCustomerId/{customerId}")
    public ResponseDto<List<RdmsCustomerUser>> listSystemManagerByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUser>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsSystemManagerService.listSystemManagerByCustomerId(customerId);
        responseDto.setContent(rdmsCustomerUsers);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsSystemManagerDto> save(@RequestBody RdmsSystemManagerDto rdmsSystemManagerDto) {
        ResponseDto<RdmsSystemManagerDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsSystemManagerDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(rdmsSystemManagerDto.getSystemManagerIds(), "系统工程师ID");

        //将数组转换成JSON string
        String jsonString = JSON.toJSONString(rdmsSystemManagerDto.getSystemManagerIds());

        RdmsSystemManager rdmsSystemManager = CopyUtil.copy(rdmsSystemManagerDto, RdmsSystemManager.class);
        rdmsSystemManager.setSystemManagerIdListStr(jsonString);
        rdmsSystemManagerService.save(rdmsSystemManager);

        responseDto.setContent(rdmsSystemManagerDto);
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
        rdmsSystemManagerService.delete(id);
        return responseDto;
    }

}
