/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.*;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.service.rdms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/function-tree")
public class FunctionTreeController {
    private static final Logger LOG = LoggerFactory.getLogger(FunctionTreeController.class);
    public static final String BUSINESS_NAME = "功能树";

    @Resource
    private RdmsFunctionTreeService rdmsFunctionTreeService;
    @Autowired
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Autowired
    private RdmsPreProjectService rdmsPreProjectService;
    @Autowired
    private RdmsCharacterService rdmsCharacterService;


    @PostMapping("/saveFunctionTree")
    @Transactional
    public ResponseDto<RdmsFunctionTreeDto> saveFunctionTree(@RequestBody List<RdmsFunctionTreeDto> functionTreeDtoList) {
        ResponseDto<RdmsFunctionTreeDto> responseDto = new ResponseDto<>();
        RdmsFunctionTreeDto functionTree = new RdmsFunctionTreeDto();
        if (!CollectionUtils.isEmpty(functionTreeDtoList)) {
            rdmsFunctionTreeService.saveFunctionTree(functionTreeDtoList);
            functionTree = rdmsFunctionTreeService.getFunctionTree(functionTreeDtoList.get(0).getPreprojectId());
        }
        responseDto.setContent(functionTree);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getFunctionTree/{preprojectId}")
    @Transactional
    public ResponseDto<RdmsFunctionTreeDto> getFunctionTree(@PathVariable String preprojectId) {
        ResponseDto<RdmsFunctionTreeDto> responseDto = new ResponseDto<>();

        RdmsFunctionTreeDto functionTree = rdmsFunctionTreeService.getFunctionTree(preprojectId);

        responseDto.setContent(functionTree);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getFunctionTreeByProjectId/{projectId}")
    @Transactional
    public ResponseDto<RdmsFunctionTreeDto> getFunctionTreeByProjectId(@PathVariable String projectId) {
        ResponseDto<RdmsFunctionTreeDto> responseDto = new ResponseDto<>();

        RdmsFunctionTreeDto functionTree = rdmsFunctionTreeService.getFunctionTreeByProjectId(projectId);

        responseDto.setContent(functionTree);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/synchronizationFunctionTree/{preprojectId}")
    @Transactional
    public ResponseDto<Integer> synchronizationFunctionTree(@PathVariable String preprojectId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        int count = 0;
        List<CharacterStatusEnum> characterStatusEnumList = new ArrayList<>();
//        characterStatusEnumList.add(CharacterStatusEnum.SAVED);     //功能编辑过程中
//        characterStatusEnumList.add(CharacterStatusEnum.SUBMIT);    //功能编辑过程中
//        characterStatusEnumList.add(CharacterStatusEnum.APPROVE);   //功能编辑过程中
        characterStatusEnumList.add(CharacterStatusEnum.APPROVED);
        characterStatusEnumList.add(CharacterStatusEnum.SETUP);
        characterStatusEnumList.add(CharacterStatusEnum.SETUPED);
        characterStatusEnumList.add(CharacterStatusEnum.DECOMPOSED);
//        characterStatusEnumList.add(CharacterStatusEnum.MERGED);    //被合并的
        characterStatusEnumList.add(CharacterStatusEnum.ITERATING);
        characterStatusEnumList.add(CharacterStatusEnum.DEVELOPING);
        characterStatusEnumList.add(CharacterStatusEnum.INTEGRATION);
        characterStatusEnumList.add(CharacterStatusEnum.DEV_COMPLETE);
        characterStatusEnumList.add(CharacterStatusEnum.QUALITY);
        characterStatusEnumList.add(CharacterStatusEnum.REVIEW);
        characterStatusEnumList.add(CharacterStatusEnum.ARCHIVED);
//        characterStatusEnumList.add(CharacterStatusEnum.UPDATE);      //进入迭代计划
        characterStatusEnumList.add(CharacterStatusEnum.OUT_SOURCE);

        List<CharacterStatusEnum> notInAusStatusEnumList = new ArrayList<>();
        notInAusStatusEnumList.add(CharacterStatusEnum.HISTORY);

        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByPreProjectIdAndStatusAndNotInAuxStatus(preprojectId, characterStatusEnumList, notInAusStatusEnumList);

        if (!CollectionUtils.isEmpty(characterList)) {
            for (RdmsCharacterDto characterItem : characterList) {
                RdmsFunctionTree rdmsFunctionTree = rdmsFunctionTreeService.selectByPrimaryKey(characterItem.getId());
                if (ObjectUtils.isEmpty(rdmsFunctionTree)) {
                    RdmsFunctionTree functionTree = new RdmsFunctionTree();
                    functionTree.setId(characterItem.getId());
                    functionTree.setParent(null);
                    functionTree.setDeep(0);
                    functionTree.setCode(characterItem.getCharacterSerial());
                    functionTree.setName(characterItem.getCharacterName());
                    functionTree.setWriterId(characterItem.getWriterId());
                    functionTree.setPreprojectId(preprojectId);
                    functionTree.setProjectType(characterItem.getProjectType());
                    functionTree.setJobType(characterItem.getJobitemType());
                    functionTree.setStatus(characterItem.getStatus());
                    functionTree.setAuxStatus(characterItem.getAuxStatus());
                    rdmsFunctionTreeService.save_noUpdate(functionTree);
                    count++;
                }
            }
        }

        responseDto.setContent(count);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

}
