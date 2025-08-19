/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsManhourStandard;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsHmiWorkerManhourSummaryDto;
import com.course.server.dto.rdms.RdmsManhourStandardDto;
import com.course.server.service.rdms.RdmsManhourService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manhour")
public class ManhourController {
    private static final Logger LOG = LoggerFactory.getLogger(ManhourController.class);
    public static final String BUSINESS_NAME = "职级管理";

    @Resource
    private RdmsManhourService rdmsManhourService;


    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsManhourStandardDto>> list(@RequestBody PageDto<RdmsManhourStandardDto> pageDto) {
        ResponseDto<PageDto<RdmsManhourStandardDto>> responseDto = new ResponseDto<>();
        rdmsManhourService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/get-standard/{customerId}")
    public ResponseDto<RdmsManhourStandardDto> getStandardManhour(@PathVariable String customerId){
        ResponseDto<RdmsManhourStandardDto> responseDto = new ResponseDto<>();
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(customerId);
        RdmsManhourStandardDto rdmsManhourStandardDto = CopyUtil.copy(standardManhour, RdmsManhourStandardDto.class);
        responseDto.setContent(rdmsManhourStandardDto);
        return responseDto;
    }
    @PostMapping("/getManhourFeeByWorkerId/{workerId}")
    public ResponseDto<String> getManhourFeeByWorkerId(@PathVariable String workerId){
        ResponseDto<String> responseDto = new ResponseDto<>();
        BigDecimal manhourFee = rdmsManhourService.getManhourFeeByWorkerId(workerId);
        responseDto.setContent(manhourFee.toString());
        return responseDto;
    }
    @PostMapping("/getManhourSummaryByCharacterId/{characterId}")
    public ResponseDto<List<RdmsHmiWorkerManhourSummaryDto>> getManhourSummaryByCharacterId(@PathVariable String characterId){
        ResponseDto<List<RdmsHmiWorkerManhourSummaryDto>> responseDto = new ResponseDto<>();
        List<RdmsHmiWorkerManhourSummaryDto> workerManhourSummaryDtoList = new ArrayList<>();

        List<String> developerIdList = rdmsManhourService.getDeveloperIdListByCharacter(characterId);
        if (!CollectionUtils.isEmpty(developerIdList)) {
            for(String developerId: developerIdList){
                RdmsHmiWorkerManhourSummaryDto manhourSummaryInfoByWorker = rdmsManhourService.getManhourSummaryInfoByWorkerIdAndCharacterId(developerId, characterId);
                workerManhourSummaryDtoList.add(manhourSummaryInfoByWorker);
            }
        }
        responseDto.setContent(workerManhourSummaryDtoList);
        return responseDto;
    }
    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsManhourStandardDto> save(@RequestBody RdmsManhourStandardDto manhourDto) {
        ResponseDto<RdmsManhourStandardDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(manhourDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(manhourDto.getDevManhourFee(), "开发工时费");
//        ValidatorUtil.require(manhourDto.getTestManhourFee(), "测试工时费");

        ValidatorUtil.length(manhourDto.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(manhourDto.getDevManhourFee().toString(), "工时费率", 1, 10);
//        ValidatorUtil.length(manhourDto.getTestManhourFee().toString(), "工时费率", 1, 10);
        manhourDto.setTestManhourFee(manhourDto.getDevManhourFee());  //测试费和开发费用设置为相同的额度
        RdmsManhourStandard rdmsManhourStandard = CopyUtil.copy(manhourDto, RdmsManhourStandard.class);
        rdmsManhourService.save(rdmsManhourStandard);

        responseDto.setContent(manhourDto);
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
        rdmsManhourService.delete(id);
        return responseDto;
    }


}
