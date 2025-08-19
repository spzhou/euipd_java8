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
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cbb")
public class CbbController {
    private static final Logger LOG = LoggerFactory.getLogger(CbbController.class);
    public static final String BUSINESS_NAME = "CBB管理";

    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsCbbService rdmsCbbService;
    @Autowired
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsFileAuthService rdmsFileAuthService;
    @Autowired
    private RdmsJobItemService rdmsJobItemService;
    @Autowired
    private RdmsCharacterDataService rdmsCharacterDataService;

    @PostMapping("/checkoutAbandon/{cbbId}")
    @Transactional
    public ResponseDto<String> checkoutAbandon(@PathVariable String cbbId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsCbb rdmsCbb = rdmsCbbService.selectByPrimaryKey(cbbId);
        if(!ObjectUtils.isEmpty(rdmsCbb)){
            RdmsCbb rdmsCbbOrigin = rdmsCbbService.selectByPrimaryKey(rdmsCbb.getCbbSerial());
            if(!ObjectUtils.isEmpty(rdmsCbbOrigin)){
                rdmsCbbOrigin.setStatus(CharacterStatusEnum.ARCHIVED.getStatus());
                rdmsCbbService.update(rdmsCbbOrigin);
            }
            rdmsCbbService.delete(cbbId);
        }

        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(rdmsCbb.getCharacterId());
        rdmsCharacterService.delete(character.getId());

        responseDto.setContent(character.getId());
        return responseDto;
    }

    @PostMapping("/getRecordAndData")
    @Transactional
    public ResponseDto<RdmsCharacterDto> getRecordAndData(@RequestParam String cbbId, String loginUserId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto recordAndData = new RdmsCharacterDto();
        RdmsCbb rdmsCbb = rdmsCbbService.selectByPrimaryKey(cbbId);
        if(cbbId != null && !ObjectUtils.isEmpty(rdmsCbb)){
            recordAndData = rdmsCharacterService.getRecordAndData(rdmsCbb.getCharacterId(), loginUserId);
        }
        responseDto.setContent(recordAndData);
        return responseDto;
    }

    @PostMapping("/listUpdateSuggestItems")
    public ResponseDto listUpdateSuggestItems(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCbbService.listUpdateSuggestItems(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/list-module")
    public ResponseDto<PageDto<RdmsCbbDto>> listModule(@RequestBody PageDto<RdmsCbbDto> pageDto) {
        ResponseDto<PageDto<RdmsCbbDto>> responseDto = new ResponseDto<>();
        rdmsCbbService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getCbbListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsHmiCharacterPlainDto>> getCbbListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsHmiCharacterPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCbbDto> cbbListByCustomerId = rdmsCbbService.getCbbListByCustomerId(customerId);
        if(!CollectionUtils.isEmpty(cbbListByCustomerId)){
            List<RdmsHmiCharacterPlainDto> characterPlainList = cbbListByCustomerId.stream().map(item -> new RdmsHmiCharacterPlainDto(item.getId(), item.getCbbName())).collect(Collectors.toList());
            responseDto.setContent(characterPlainList);
        }
        return responseDto;
    }

    /**
     * 在保存的时候,现将数据存到character表中
     */
    @PostMapping("/saveRecordAndData")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveRecordAndData(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getId(), "CBB ID");
        ValidatorUtil.require(characterDto.getCharacterName(), "CBB名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "组件描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getProjectType(), "项目类型");
        ValidatorUtil.require(characterDto.getJobitemId(), "任务ID");
        ValidatorUtil.require(characterDto.getJobitemType(), "任务类型");
        ValidatorUtil.require(characterDto.getLoginUserId(), "当前用户");

        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 2500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 2500);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 10, 2500);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 10, 2500);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 10, 2500);

        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        characterDto.setFileListStr(jsonString);

        characterDto.setStatus(CharacterStatusEnum.ARCHIVED.getStatus()); //已存档的状态保存
        characterDto.setWriterId(characterDto.getLoginUserId());  //将编制人修改为自己

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(characterDto.getJobitemId());
        if(!ObjectUtils.isEmpty(rdmsJobItem)){
            rdmsJobItem.setCharacterId(characterDto.getId());
            rdmsJobItemService.update(rdmsJobItem);

            characterDto.setCharacterSerial(characterDto.getId());
            characterDto.setDeep(0);
            characterDto.setIterationVersion(1);
            characterDto.setSubprojectId(rdmsJobItem.getSubprojectId());
            characterDto.setProductManagerId(rdmsJobItem.getProductManagerId());
            characterDto.setProjectManagerId(rdmsJobItem.getProjectManagerId());
            characterDto.setAuxStatus(CharacterStatusEnum.NOTSET.getStatus());
            characterDto.setJobitemType(JobItemTypeEnum.CBB_DEFINE.getType());
            characterDto.setJobitemId(characterDto.getJobitemId());
            characterDto.setProjectType(characterDto.getProjectType());
            characterDto.setStage(DocTypeEnum.PROJECT.getType());
            characterDto.setUpdateTime(new Date());
            characterDto.setCreateTime(new Date());
            characterDto.setSubmitTime(new Date());
            characterDto.setCbbProjectId(rdmsJobItem.getSubprojectId());
        }

        rdmsCbbService.saveRecordAndData(characterDto);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(characterDto.getFileIds())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(characterDto.getLoginUserId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(characterDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                if(!ObjectUtils.isEmpty(characterDto.getProductManagerId())){
                    roleUsersDto.setPdmId(characterDto.getProductManagerId());
                }
                rdmsFileAuthService.setFileAuthUser(characterDto.getFileIds(), roleUsersDto);
            }
        }

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/checkout")
    @Transactional
    public ResponseDto<String> checkout(@RequestBody RdmsCbbDto cbbDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(cbbDto.getId(), "CBB ID");
        ValidatorUtil.require(cbbDto.getCbbSerial(), "CBB_Serial");
        ValidatorUtil.require(cbbDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(cbbDto.getLoginUserId(), "当前用户");

        cbbDto.setStatus(CharacterStatusEnum.SAVED.getStatus());
        cbbDto.setCreaterId(cbbDto.getLoginUserId());
        cbbDto.setJobitemType(JobItemTypeEnum.SUGGEST_UPDATE.getType());
        cbbDto.setDevVersion(String.valueOf((Double.parseDouble(cbbDto.getDevVersion())  + 1)));
        cbbDto.setCreateTime(new Date());
        cbbDto.setReleaseTime(null);
        String cbbId = rdmsCbbService.save(cbbDto);

        //标记原始的cbb的状态
        RdmsCbb rdmsOrigin = rdmsCbbService.selectByPrimaryKey(cbbDto.getCbbSerial());
        rdmsOrigin.setStatus(CharacterStatusEnum.UPDATE.getStatus());
        rdmsCbbService.update(rdmsOrigin);

        //将CBB对应的信息在Character表中进行保存
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(cbbDto.getCharacterId());
        RdmsCharacter copyCharacter = CopyUtil.copy(rdmsCharacter, RdmsCharacter.class);
        copyCharacter.setId(null);
        copyCharacter.setCharacterSerial(rdmsCharacter.getId());
        copyCharacter.setIterationVersion(rdmsCharacter.getIterationVersion() + 1);
        copyCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());
        copyCharacter.setJobitemType(JobItemTypeEnum.CBB_DEFINE.getType());
        copyCharacter.setJobitemId(null);
        copyCharacter.setProjectType(ProjectTypeEnum.SUB_PROJECT.getType());
        copyCharacter.setCreateTime(new Date());
        copyCharacter.setUpdateTime(new Date());
        String newCharacterId = rdmsCharacterService.save(copyCharacter);

        //将CharacterID写入CBB记录
        RdmsCbb rdmsCbb = rdmsCbbService.selectByPrimaryKey(cbbId);
        rdmsCbb.setCharacterId(newCharacterId);
        rdmsCbbService.update(rdmsCbb);

        //将Character的Data信息保存给新的Character
        RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(rdmsCharacter.getId());
        RdmsCharacterData copyCharacterData = CopyUtil.copy(rdmsCharacterData, RdmsCharacterData.class);
        copyCharacterData.setId(newCharacterId);
        copyCharacterData.setCreateTime(new Date());
        copyCharacterData.setUpdateTime(new Date());
        rdmsCharacterDataService.save(copyCharacterData);

        //填写文件的访问权限人员
        List<String> fileIdList = JSON.parseArray(cbbDto.getFileIdListStr(), String.class);
        if(!CollectionUtils.isEmpty(fileIdList)){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(cbbDto.getLoginUserId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(cbbDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                if(!ObjectUtils.isEmpty(cbbDto.getProductManagerId())){
                    roleUsersDto.setPdmId(cbbDto.getProductManagerId());
                }
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }
        responseDto.setContent(newCharacterId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getCbbData/{cbbProjectId}")
    @Transactional
    public ResponseDto<RdmsCharacterDto> getCbbData(@PathVariable String cbbProjectId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto characterDto = new RdmsCharacterDto();
        List<RdmsCharacter> characterByCbbProjectId = rdmsCharacterService.getCharacterByCbbProjectId(cbbProjectId);
        if(!CollectionUtils.isEmpty(characterByCbbProjectId)){
            RdmsCharacter rdmsCharacter = characterByCbbProjectId.get(0);
            characterDto = CopyUtil.copy(rdmsCharacter, RdmsCharacterDto.class);
        }
        RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(characterDto.getId());
        characterDto.setCharacterData(rdmsCharacterData);

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getCbbSimpleInfo/{cbbId}")
    @Transactional
    public ResponseDto<RdmsCbb> getCbbSimpleInfo(@PathVariable String cbbId) {
        ResponseDto<RdmsCbb> responseDto = new ResponseDto<>();
        RdmsCbb rdmsCbb = rdmsCbbService.selectByPrimaryKey(cbbId);
        responseDto.setContent(rdmsCbb);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getCbbSimpleInfoByCode/{cbbProjectId}")
    @Transactional
    public ResponseDto<RdmsCharacter> getCbbSimpleInfoByCode(@PathVariable String cbbProjectId) {
        ResponseDto<RdmsCharacter> responseDto = new ResponseDto<>();
        List<RdmsCharacter> characterByCbbProjectId = rdmsCharacterService.getCharacterByCbbProjectId(cbbProjectId);
        if(!CollectionUtils.isEmpty(characterByCbbProjectId)){
            RdmsCharacter rdmsCharacter = characterByCbbProjectId.get(0);
            responseDto.setContent(rdmsCharacter);
        }else{
            responseDto.setContent(null);
        }
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveCharacterToCbb/{characterId}")
    @Transactional
    public ResponseDto<String> saveCharacterToCbb(@PathVariable String characterId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        rdmsCbbService.saveCharacterToCbb(characterId, DocTypeEnum.PROJECT);
        responseDto.setContent(characterId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getCbbRecordDetailById")
    public ResponseDto<RdmsCbbDto> getCbbRecordDetailById(@RequestParam String cbbId, String loginUserId) {
        ResponseDto<RdmsCbbDto> responseDto = new ResponseDto<>();
        RdmsCbbDto cbbRecordDetailById = rdmsCbbService.getCbbRecordDetailById(cbbId, loginUserId);
        responseDto.setContent(cbbRecordDetailById);
        return responseDto;
    }

}
