/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsFggm;
import com.course.server.domain.RdmsPdgm;
import com.course.server.domain.RdmsSysgm;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pdgm-sysgm")
public class DirectorController {
    private static final Logger LOG = LoggerFactory.getLogger(DirectorController.class);
    public static final String BUSINESS_NAME = "产品与系统总监";

    @Resource
    private RdmsPdgmService rdmsPdgmService;
    @Resource
    private RdmsSysgmService rdmsSysgmService;
    @Resource
    private RdmsFggmService rdmsFggmService;
    @Autowired
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Autowired
    private RdmsBossService rdmsBossService;

    /**
     * 列表查询
     */
    @PostMapping("/getPdgmAndSysgm/{customerId}")
    public ResponseDto<RdmsDirectorDto> list(@PathVariable String customerId) {
        ResponseDto<RdmsDirectorDto> responseDto = new ResponseDto<>();
        RdmsDirectorDto rdmsDirectorDto = new RdmsDirectorDto();

        RdmsPdgmDto pdgmByCustomerId = rdmsPdgmService.getPdgmByCustomerId(customerId);
        if(ObjectUtils.isEmpty(pdgmByCustomerId)){
            RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
            rdmsDirectorDto.setCustomerId(customerId);
            rdmsDirectorDto.setPdgmId(bossByCustomerId.getBossId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bossByCustomerId.getBossId());
            rdmsDirectorDto.setPdgmName(rdmsCustomerUser.getTrueName());

            RdmsPdgm rdmsPdgm = new RdmsPdgm();
            rdmsPdgm.setPdgmId(rdmsDirectorDto.getPdgmId());
            rdmsPdgm.setCustomerId(customerId);
            rdmsPdgmService.save(rdmsPdgm);
        }else{
            rdmsDirectorDto.setPdgmId(pdgmByCustomerId.getPdgmId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(pdgmByCustomerId.getPdgmId());
            rdmsDirectorDto.setPdgmName(rdmsCustomerUser.getTrueName());
        }

        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(customerId);
        if(ObjectUtils.isEmpty(sysgmByCustomerId)){
            RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
            rdmsDirectorDto.setCustomerId(customerId);
            rdmsDirectorDto.setSysgmId(bossByCustomerId.getBossId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bossByCustomerId.getBossId());
            rdmsDirectorDto.setSysgmName(rdmsCustomerUser.getTrueName());

            RdmsSysgm rdmSysgm = new RdmsSysgm();
            rdmSysgm.setSysgmId(rdmsDirectorDto.getSysgmId());
            rdmSysgm.setCustomerId(customerId);
            rdmsSysgmService.save(rdmSysgm);
        }else{
            rdmsDirectorDto.setSysgmId(sysgmByCustomerId.getSysgmId());
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(sysgmByCustomerId.getSysgmId());
            rdmsDirectorDto.setSysgmName(rdmsCustomerUser2.getTrueName());
        }

        RdmsFggmDto fggmByCustomerId = rdmsFggmService.getFggmByCustomerId(customerId);
        if(ObjectUtils.isEmpty(fggmByCustomerId)){
            RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
            rdmsDirectorDto.setCustomerId(customerId);
            rdmsDirectorDto.setFggmId(bossByCustomerId.getBossId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bossByCustomerId.getBossId());
            rdmsDirectorDto.setFggmName(rdmsCustomerUser.getTrueName());

            RdmsFggm rdmFggm = new RdmsFggm();
            rdmFggm.setFggmId(rdmsDirectorDto.getFggmId());
            rdmFggm.setCustomerId(customerId);
            rdmsFggmService.save(rdmFggm);
        }else{
            rdmsDirectorDto.setFggmId(fggmByCustomerId.getFggmId());
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(rdmsDirectorDto.getFggmId());
            rdmsDirectorDto.setFggmName(rdmsCustomerUser2.getTrueName());
        }

        responseDto.setContent(rdmsDirectorDto);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/savePdgm")
    @Transactional
    public ResponseDto<String> savePdgm(@RequestBody RdmsDirectorDto pdgmDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(pdgmDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(pdgmDto.getPdgmId(), "产品总监");

        RdmsPdgm pdgm = CopyUtil.copy(pdgmDto, RdmsPdgm.class);
        String save = rdmsPdgmService.save(pdgm);

        responseDto.setContent(save);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveSysgm")
    @Transactional
    public ResponseDto<String> saveSysgm(@RequestBody RdmsDirectorDto directorDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(directorDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(directorDto.getSysgmId(), "系统工程总监");

        RdmsSysgm sysgm = CopyUtil.copy(directorDto, RdmsSysgm.class);
        String save = rdmsSysgmService.save(sysgm);

        responseDto.setContent(save);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveFggm")
    @Transactional
    public ResponseDto<String> saveFggm(@RequestBody RdmsDirectorDto directorDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(directorDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(directorDto.getFggmId(), "法规部总监");

        RdmsFggm fggm = CopyUtil.copy(directorDto, RdmsFggm.class);
        String save = rdmsFggmService.save(fggm);

        responseDto.setContent(save);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


}
