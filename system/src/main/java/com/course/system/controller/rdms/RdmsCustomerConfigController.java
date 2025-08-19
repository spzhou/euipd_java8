/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.course.server.domain.RdmsCustomerConfig;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCustomerConfigDto;
import com.course.server.service.rdms.RdmsCustomerConfigService;
import com.course.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rdms/config/customer")
public class RdmsCustomerConfigController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerConfigController.class);
    public static final String BUSINESS_NAME = "客户配置";

    @Resource
    private RdmsCustomerConfigService rdmsCustomerConfigService;

    @GetMapping("/get-customer-config/{customerId}")
    public ResponseDto<RdmsCustomerConfigDto> getCustomerConfig(@PathVariable String customerId) {
        ResponseDto<RdmsCustomerConfigDto> responseDto = new ResponseDto<>();
        RdmsCustomerConfig customerConfig = rdmsCustomerConfigService.getCustomerConfig(customerId);
        RdmsCustomerConfigDto customerConfigDto = CopyUtil.copy(customerConfig, RdmsCustomerConfigDto.class);
        responseDto.setContent(customerConfigDto);
        return responseDto;
    }
    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsCustomerConfigDto> save(@RequestBody RdmsCustomerConfigDto customerConfigDto) throws Exception {
        ResponseDto<RdmsCustomerConfigDto> responseDto = new ResponseDto<>();
        //如果任何一个主数据字段为空,则返回错误
        if(
                customerConfigDto.getOssAccessKey()==null || customerConfigDto.getOssAccessKey().isEmpty()
             || customerConfigDto.getOssAccessKeySecret()==null || customerConfigDto.getOssAccessKeySecret().isEmpty()
             || customerConfigDto.getOssEndpoint()==null || customerConfigDto.getOssEndpoint().isEmpty()
             || customerConfigDto.getOssDomain()==null || customerConfigDto.getOssDomain().isEmpty()
             || customerConfigDto.getVodAccessKey()==null || customerConfigDto.getVodAccessKey().isEmpty()
             || customerConfigDto.getVodAccessKeySecret()==null || customerConfigDto.getVodAccessKeySecret().isEmpty()
        ){
            responseDto.setSuccess(false);
            responseDto.setMessage("请正确填写数据");
            responseDto.setContent(null);
            return responseDto;
        }

        RdmsCustomerConfig rdmsCustomerConfig = CopyUtil.copy(customerConfigDto, RdmsCustomerConfig.class);
        rdmsCustomerConfigService.save(rdmsCustomerConfig);

        responseDto.setContent(customerConfigDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsCustomerConfigDto>> list(@RequestBody PageDto<RdmsCustomerConfigDto> pageDto) {
        ResponseDto<PageDto<RdmsCustomerConfigDto>> responseDto = new ResponseDto<>();
        rdmsCustomerConfigService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto<PageDto<RdmsCustomerConfigDto>> delete(@PathVariable String id) {
        ResponseDto<PageDto<RdmsCustomerConfigDto>> responseDto = new ResponseDto<>();
        rdmsCustomerConfigService.delete(id);
        return responseDto;
    }

}
