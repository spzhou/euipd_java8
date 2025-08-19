/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsBossDto;
import com.course.server.dto.rdms.RdmsDemandDto;
import com.course.server.dto.rdms.RdmsPreProjectDto;
import com.course.server.dto.rdms.RdmsRoleUsersDto;
import com.course.server.enums.rdms.DemandStatusEnum;
import com.course.server.mapper.rdms.MyDemandMapper;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demand")
public class DemandController {
    private static final Logger LOG = LoggerFactory.getLogger(DemandController.class);
    public static final String BUSINESS_NAME = "需求管理";

    @Resource
    private RdmsDemandService rdmsDemandService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsPreProjectService rdmsProjectPrepareService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Autowired
    private RdmsBossService rdmsBossService;

    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsDemandDto>> listSavedDemandByCustomerId(@RequestBody PageDto<RdmsDemandDto> pageDto) {
        ResponseDto<PageDto<RdmsDemandDto>> responseDto = new ResponseDto<>();
        rdmsDemandService.listSavedDemandByCustomerId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/list-submitted")
    public ResponseDto<PageDto<RdmsDemandDto>> listSubmitDemandByPreProjectId(@RequestBody PageDto<RdmsDemandDto> pageDto) {
        ResponseDto<PageDto<RdmsDemandDto>> responseDto = new ResponseDto<>();
        rdmsDemandService.listSubmitDemandByPreProjectId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/get-demand/{demandId}")
    public ResponseDto<RdmsDemandDto> getDemandById(@PathVariable String demandId) {
        ResponseDto<RdmsDemandDto> responseDto = new ResponseDto<>();
        RdmsDemand rdmsDemandDiscern = rdmsDemandService.selectByPrimaryKey(demandId);
        RdmsDemandDto copy = CopyUtil.copy(rdmsDemandDiscern, RdmsDemandDto.class);
        responseDto.setContent(copy);
        return responseDto;
    }

    @PostMapping("/getDemandHasListFlag/{userId}")
    public ResponseDto<Boolean> getDemandHasListFlag(@PathVariable String userId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        Integer countOfDemandByUserID = rdmsDemandService.getCountOfDemandByUserID(userId);
        responseDto.setContent(countOfDemandByUserID >0);
        return responseDto;
    }

    /**
     * 通过文件idList查询文件列表
     */
    @PostMapping("/getDemandListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsDemandDto>> getDemandListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsDemandDto>> responseDto = new ResponseDto<>();
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        if(!ObjectUtils.isEmpty(character) && !ObjectUtils.isEmpty(character.getDemandListStr())){
            String demandListStr = character.getDemandListStr();
            List<String> stringList = JSON.parseArray(demandListStr, String.class);
            if(!CollectionUtils.isEmpty(stringList)){
                List<RdmsDemand> demandListByIdList = rdmsDemandService.getDemandListByIdList(stringList);
                List<RdmsDemandDto> demandDiscernDtos = CopyUtil.copyList(demandListByIdList, RdmsDemandDto.class);
                responseDto.setContent(demandDiscernDtos);
            }else{
                responseDto.setContent(null);
            }
        }

        responseDto.setSuccess(true);
        responseDto.setMessage("查询成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsDemandDto> save(@RequestBody RdmsDemandDto demandDto) {
        ResponseDto<RdmsDemandDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(demandDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(demandDto.getDemandName(), "需求名称");
        ValidatorUtil.require(demandDto.getDemandDescription(), "需求描述");
        ValidatorUtil.require(demandDto.getPreProjectId(), "关联项目");
        ValidatorUtil.require(demandDto.getDemandCustomerName(), "需求场景客户名称");
        ValidatorUtil.require(demandDto.getConfirmPersonName(), "需求确认人姓名");
        ValidatorUtil.require(demandDto.getConfirmContactTel(), "需求确认人电话");
        ValidatorUtil.require(demandDto.getWriterId(), "编制者");

        ValidatorUtil.length(demandDto.getDemandName(), "需求名称", 4, 50);
        ValidatorUtil.length(demandDto.getDemandDescription(), "需求描述", 10, 5000);
        ValidatorUtil.length(demandDto.getWorkCondition(), "生效条件", 0, 5000);

        RdmsDemand rdmsDemandDiscern = CopyUtil.copy(demandDto, RdmsDemand.class);
        String jsonString = JSON.toJSONString(demandDto.getFileList());
        rdmsDemandDiscern.setFileListStr(jsonString);
        rdmsDemandDiscern.setStatus(DemandStatusEnum.SAVED.getStatus());
        //添加产品经理
        RdmsPreProject rdmsProjectPrepare = rdmsProjectPrepareService.selectByPrimaryKey(rdmsDemandDiscern.getPreProjectId());
        rdmsDemandDiscern.setProductManagerId(rdmsProjectPrepare.getProductManagerId());
        rdmsDemandDiscern.setNextNode(demandDto.getWriterId());
        String demandId = rdmsDemandService.save(rdmsDemandDiscern);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(demandDto.getFileList())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(null);
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(demandDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                RdmsDemand rdmsDemand = rdmsDemandService.selectByPrimaryKey(demandId);
                if(!ObjectUtils.isEmpty(rdmsDemand)){
                    roleUsersDto.setPdmId(rdmsDemand.getProductManagerId());
                }
                rdmsFileAuthService.setFileAuthUser(demandDto.getFileList(), roleUsersDto);
            }
        }

        responseDto.setContent(demandDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/submit")
    @Transactional
    public ResponseDto<RdmsDemandDto> submit(@RequestBody RdmsDemandDto demandDto) {
        ResponseDto<RdmsDemandDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(demandDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(demandDto.getDemandName(), "需求名称");
        ValidatorUtil.require(demandDto.getDemandDescription(), "需求描述");
        ValidatorUtil.require(demandDto.getPreProjectId(), "关联项目");
        ValidatorUtil.require(demandDto.getDemandCustomerName(), "需求场景客户名称");
        ValidatorUtil.require(demandDto.getConfirmPersonName(), "需求确认人姓名");
        ValidatorUtil.require(demandDto.getConfirmContactTel(), "需求确认人电话");
        ValidatorUtil.require(demandDto.getWriterId(), "编制者");

        ValidatorUtil.length(demandDto.getDemandName(), "需求名称", 4, 50);
        ValidatorUtil.length(demandDto.getDemandDescription(), "需求描述", 10, 5000);
        ValidatorUtil.length(demandDto.getWorkCondition(), "生效条件", 0, 5000);

        RdmsDemand rdmsDemandDiscern = CopyUtil.copy(demandDto, RdmsDemand.class);
        String jsonString = JSON.toJSONString(demandDto.getFileList());
        rdmsDemandDiscern.setFileListStr(jsonString);
        rdmsDemandDiscern.setStatus(DemandStatusEnum.SUBMIT.getStatus());
        rdmsDemandDiscern.setSubmitTime(new Date());
        //添加产品经理节点
        RdmsPreProjectDto preProjectById = rdmsProjectPrepareService.getPreProjectSimpleInfo(demandDto.getPreProjectId());
        rdmsDemandDiscern.setProductManagerId(preProjectById.getProductManagerId());
        rdmsDemandDiscern.setNextNode(preProjectById.getProductManagerId());
        String demandId = rdmsDemandService.save(rdmsDemandDiscern);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(demandDto.getFileList())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(null);
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(demandDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                RdmsDemand rdmsDemand = rdmsDemandService.selectByPrimaryKey(demandId);
                if(!ObjectUtils.isEmpty(rdmsDemand)){
                    roleUsersDto.setPdmId(rdmsDemand.getProductManagerId());
                }
                rdmsFileAuthService.setFileAuthUser(demandDto.getFileList(), roleUsersDto);
            }
        }

        responseDto.setContent(demandDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/submit/{demandId}")
    @Transactional
    public ResponseDto<RdmsDemandDto> submitByDemandId(@PathVariable String demandId) {
        ResponseDto<RdmsDemandDto> responseDto = new ResponseDto<>();

        RdmsDemand rdmsDemandDiscern = rdmsDemandService.selectByPrimaryKey(demandId);
        rdmsDemandDiscern.setStatus(DemandStatusEnum.SUBMIT.getStatus());
        rdmsDemandDiscern.setSubmitTime(new Date());
        //添加产品经理节点
        rdmsDemandDiscern.setNextNode(rdmsDemandDiscern.getProductManagerId());
        rdmsDemandService.save(rdmsDemandDiscern);

        RdmsDemandDto copy = CopyUtil.copy(rdmsDemandDiscern, RdmsDemandDto.class);
        responseDto.setContent(copy);
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
        rdmsDemandService.delete(id);
        return responseDto;
    }

}
