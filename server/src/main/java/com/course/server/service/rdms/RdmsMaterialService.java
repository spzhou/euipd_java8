/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.ApplicationStatusEnum;
import com.course.server.enums.rdms.BomStatusEnum;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.enums.rdms.JobItemTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBomMapper;
import com.course.server.mapper.RdmsMaterialMapper;
import com.course.server.mapper.RdmsMaterialReturnMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsMaterialService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsMaterialService.class);

    @Resource
    private RdmsMaterialMapper rdmsMaterialMapper;
    @Resource
    private RdmsMaterialReturnMapper rdmsMaterialReturnMapper;
    @Resource
    private RdmsBomMapper rdmsBomMapper;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsBomConfigService rdmsBomConfigService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsBomService rdmsBomService;

    public List<RdmsMaterialDto> getMaterialListByApplicationCodeMix(String code){
        RdmsMaterialExample materialExample = new RdmsMaterialExample();
        materialExample.createCriteria().andCodeEqualTo(code).andDeletedEqualTo(0);
        List<RdmsMaterial> rdmsMaterials = rdmsMaterialMapper.selectByExample(materialExample);
        if(CollectionUtils.isEmpty(rdmsMaterials)){
            RdmsMaterialReturnExample materialReturnExample = new RdmsMaterialReturnExample();
            materialReturnExample.createCriteria().andCodeEqualTo(code).andDeletedEqualTo(0);
            List<RdmsMaterialReturn> rdmsMaterialReturns = rdmsMaterialReturnMapper.selectByExample(materialReturnExample);
            rdmsMaterials = CopyUtil.copyList(rdmsMaterialReturns, RdmsMaterial.class);
        }
        return CopyUtil.copyList(rdmsMaterials, RdmsMaterialDto.class);
    }
    public List<RdmsMaterialDto> getMaterialListByApplicationCode(String code) {
        RdmsMaterialExample materialExample = new RdmsMaterialExample();
        materialExample.createCriteria().andCodeEqualTo(code).andDeletedEqualTo(0);
        List<RdmsMaterial> rdmsMaterials = rdmsMaterialMapper.selectByExample(materialExample);
        return CopyUtil.copyList(rdmsMaterials, RdmsMaterialDto.class);
    }

    public List<RdmsMaterial> getMaterialListByApplicationCode_origin(String code){
        RdmsMaterialExample materialExample = new RdmsMaterialExample();
        materialExample.createCriteria()
                .andCodeEqualTo(code)
                .andAmountGreaterThan(0.0)
                .andDeletedEqualTo(0);
        List<RdmsMaterial> rdmsMaterials = rdmsMaterialMapper.selectByExample(materialExample);
        return rdmsMaterials;
    }

    public List<RdmsMaterialReturn> getMaterialListByApplicationCode_return(String code){
            RdmsMaterialReturnExample materialReturnExample = new RdmsMaterialReturnExample();
            materialReturnExample.createCriteria().andCodeEqualTo(code).andDeletedEqualTo(0);
            List<RdmsMaterialReturn> rdmsMaterialReturns = rdmsMaterialReturnMapper.selectByExample(materialReturnExample);
        return rdmsMaterialReturns;
    }

    public List<RdmsMaterial> getOriginMaterialByTokenCode(String tokenCode){
            RdmsMaterialExample materialExample = new RdmsMaterialExample();
        materialExample.createCriteria().andTokenCodeEqualTo(tokenCode).andDeletedEqualTo(0);
        List<RdmsMaterial> rdmsMaterials = rdmsMaterialMapper.selectByExample(materialExample);
        return rdmsMaterials;
    }

    public List<RdmsMaterialReturn> getMaterialListByOriginCode_return(String originCode){
            RdmsMaterialReturnExample materialReturnExample = new RdmsMaterialReturnExample();
            materialReturnExample.createCriteria().andOriginCodeEqualTo(originCode).andDeletedEqualTo(0);
            List<RdmsMaterialReturn> rdmsMaterialReturns = rdmsMaterialReturnMapper.selectByExample(materialReturnExample);
        return rdmsMaterialReturns;
    }

    /*public List<RdmsMaterial> getMaterialListEliminateReturnMaterials(String originCode){
        List<RdmsMaterial> originMaterialList = this.getMaterialListByApplicationCode_origin(originCode);
        if(!CollectionUtils.isEmpty(originMaterialList)){
            List<RdmsMaterialReturn> returnMaterialList = this.getMaterialListByOriginCode_return(originCode);
            if(!CollectionUtils.isEmpty(returnMaterialList)){
                List<String> originBomCodeList = originMaterialList.stream().map(RdmsMaterial::getBomCode).collect(Collectors.toList());
                List<String> returnBomCodeList = returnMaterialList.stream().map(RdmsMaterialReturn::getBomCode).collect(Collectors.toList());
                List<RdmsMaterial> remainMaterialList = new ArrayList<>();
                for(String originBomCode: originBomCodeList){
                    //得到这个物料条目
                    List<RdmsMaterial> originMaterial = originMaterialList.stream().filter(item -> item.getBomCode().equals(originBomCode)).collect(Collectors.toList());
                    List<RdmsMaterialReturn> returnMaterial = returnMaterialList.stream().filter(item -> item.getBomCode().equals(originBomCode)).collect(Collectors.toList());
                    RdmsMaterial rdmsMaterial = originMaterial.get(0); //只能有一条
                    RdmsMaterialReturn rdmsMaterialReturn = returnMaterial.get(0);  //只能有一条
                    if(returnBomCodeList.contains(originBomCode)){
                        if(!Objects.equals(rdmsMaterial.getAmount(), rdmsMaterialReturn.getAmount())){
                            rdmsMaterial.setAmount(rdmsMaterial.getAmount() - rdmsMaterialReturn.getAmount());
                            remainMaterialList.add(rdmsMaterial);
                        }
                    }else{
                        remainMaterialList.add(rdmsMaterial);
                    }
                }
                return remainMaterialList;

            }else{
                return originMaterialList;
            }
        }else{
            return null;
        }
    }

*/
    @Transactional
    public List<RdmsMaterialDto> getMaterialListByCodeInListAndStatusList(List<String> codeList, List<ApplicationStatusEnum> statusEnumList){
        RdmsMaterialExample materialExample = new RdmsMaterialExample();
        RdmsMaterialExample.Criteria criteria = materialExample.createCriteria()
                .andCodeIn(codeList)
                .andDeletedEqualTo(0);
        List<String> statusList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(statusEnumList)){
            for(ApplicationStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsMaterial> rdmsMaterials = rdmsMaterialMapper.selectByExample(materialExample);
        List<RdmsMaterialDto> rdmsMaterialDtos = CopyUtil.copyList(rdmsMaterials, RdmsMaterialDto.class);
        for(RdmsMaterialDto materialDto: rdmsMaterialDtos){
            RdmsMaterialManageDto materialApplicationByCode = rdmsMaterialManageService.getMaterialApplicationByCode_origin(materialDto.getCode());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialApplicationByCode.getWriterId());
            materialDto.setWriterName(rdmsCustomerUser.getTrueName());
        }
        return rdmsMaterialDtos;
    }

    @Transactional
    public List<RdmsBomDto> getBomMaterialListByCharacterId(String characterId){
        RdmsBomExample bomExample = new RdmsBomExample();
        List<String> bomStatus = new ArrayList<>();
        bomStatus.add(BomStatusEnum.NORMAL.getStatus());
        bomStatus.add(BomStatusEnum.OPTIONAL.getStatus());
        bomExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(bomStatus)
                .andDeletedEqualTo(0);
        List<RdmsBom> rdmsBomList = rdmsBomMapper.selectByExample(bomExample);
        List<RdmsBomDto> rdmsBomDtos = CopyUtil.copyList(rdmsBomList, RdmsBomDto.class);
        if(!CollectionUtils.isEmpty(rdmsBomDtos)){
            for(RdmsBomDto bomDto: rdmsBomDtos){
                if(!ObjectUtils.isEmpty(bomDto) && !ObjectUtils.isEmpty(bomDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bomDto.getCharacterId());
                    bomDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        return rdmsBomDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getArchivedCharacterListByProjectId(String projectId){
        //获得子项目的所有子项目Id
        List<String> allChildrenSubprojectIdList = rdmsSubprojectService.getAllChildrenSubprojectId(projectId);
        List<RdmsCharacterDto> archivedCharacterListBySubprojectIdList = rdmsCharacterService.getArchivedCharacterListBySubprojectIdList(allChildrenSubprojectIdList);
        return archivedCharacterListBySubprojectIdList;
    }

    @Transactional
    public List<RdmsBomDto> getBomMaterialListBySubprojectId(String subprojectId){
        //获得子项目的所有子项目Id
        List<String> allChildrenSubprojectId = rdmsSubprojectService.getAllChildrenSubprojectId(subprojectId);
        RdmsBomExample bomExample = new RdmsBomExample();
        bomExample.setOrderByClause("create_time asc");
        List<String> bomStatus = new ArrayList<>();
        bomStatus.add(BomStatusEnum.NORMAL.getStatus());
        bomStatus.add(BomStatusEnum.OPTIONAL.getStatus());
        bomExample.createCriteria()
                .andSubprojectIdIn(allChildrenSubprojectId)
                .andStatusIn(bomStatus)
                .andDeletedEqualTo(0);
        List<RdmsBom> rdmsBomList = rdmsBomMapper.selectByExample(bomExample);
        List<RdmsBomDto> rdmsBomDtos = CopyUtil.copyList(rdmsBomList, RdmsBomDto.class);
        if(!CollectionUtils.isEmpty(rdmsBomDtos)){
            for(RdmsBomDto bomDto: rdmsBomDtos){
                if(!ObjectUtils.isEmpty(bomDto) && !ObjectUtils.isEmpty(bomDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bomDto.getCharacterId());
                    bomDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        return rdmsBomDtos;
    }

    @Transactional
    public List<RdmsBomDto> getCommonBomMaterialListByProjectId(String projectId){
        //获得子项目的所有子项目Id
        List<String> allChildrenSubprojectId = rdmsSubprojectService.getAllChildrenSubprojectId(projectId);
        RdmsBomExample bomExample = new RdmsBomExample();
        List<String> bomStatus = new ArrayList<>();
        bomStatus.add(BomStatusEnum.NORMAL.getStatus());
        bomStatus.add(BomStatusEnum.OPTIONAL.getStatus());
        bomExample.createCriteria()
                .andSubprojectIdIn(allChildrenSubprojectId)
                .andCharacterIdIsNull()
                .andStatusIn(bomStatus)
                .andDeletedEqualTo(0);
        List<RdmsBom> rdmsBomList = rdmsBomMapper.selectByExample(bomExample);
        List<RdmsBomDto> rdmsBomDtos = CopyUtil.copyList(rdmsBomList, RdmsBomDto.class);
        return rdmsBomDtos;
    }

    @Transactional
    public List<RdmsBomDto> getBomMaterialListByProjectId(String projectId){
        return getBomMaterialListBySubprojectId(projectId);
    }

    @Transactional
    public List<RdmsBomDto> getBomMaterialListByCharacterIdList(List<String> characterIdList){
        List<RdmsBomDto> projectBomList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(characterIdList)){
            for(String characterId: characterIdList){
                List<RdmsBomDto> bomMaterialListByCharacterId = this.getBomMaterialListByCharacterId(characterId);
                projectBomList.addAll(bomMaterialListByCharacterId);
            }
        }
        return projectBomList;
    }

    @Transactional
    public RdmsBomConfigDto getMaterialListByProjectId(String projectId){
        List<RdmsBomConfig> activeBomConfigList = rdmsBomConfigService.getActiveBomConfigByProjectId(projectId);
        if(!CollectionUtils.isEmpty(activeBomConfigList)){
            RdmsBomConfigDto bomConfigDto = CopyUtil.copy(activeBomConfigList.get(0), RdmsBomConfigDto.class);
            String characterIdListStr = activeBomConfigList.get(0).getCharacterIdListStr();
            List<String> characterIdList = JSON.parseArray(characterIdListStr, String.class);
            bomConfigDto.setCharacterIdList(characterIdList);
            List<RdmsBomDto> bomMaterialList = this.getBomMaterialListByCharacterIdList(characterIdList);
            bomConfigDto.setMaterialList(bomMaterialList);
            return bomConfigDto;
        }else {
            //没有默认BOM配置的情况
            RdmsBomConfig bomConfig = new RdmsBomConfig();
            //private String id;
            bomConfig.setId(UuidUtil.getShortUuid());
            //private String projectId;
            bomConfig.setProjectId(projectId);
            //private String name;
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
            bomConfig.setName(rdmsProject.getProjectName());
            //private Integer isActive;
            bomConfig.setIsActive(1);
            //private String characterIdListStr;
            List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(CharacterStatusEnum.ARCHIVED);
            statusEnumList.add(CharacterStatusEnum.UPDATE);
            List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByProjectIdAndStatusList(projectId, statusEnumList);
            List<String> characterIdList = characterList.stream().map(RdmsCharacterDto::getId).collect(Collectors.toList());
            String characterIdStr = JSON.toJSONString(characterIdList);
            bomConfig.setCharacterIdListStr(characterIdStr);
            //private Date updateTime;
            bomConfig.setUpdateTime(new Date());
            //private Date createTime;
            bomConfig.setCreateTime(new Date());
            //private Integer deleted;
            bomConfig.setDeleted(0);
            rdmsBomConfigService.save(bomConfig);

            //返回BOM配置
            RdmsBomConfigDto bomConfigDto = new RdmsBomConfigDto();
            bomConfigDto.setCharacterIdList(characterIdList);
            List<RdmsBomDto> bomMaterialList = this.getBomMaterialListByCharacterIdList(characterIdList);
            bomConfigDto.setMaterialList(bomMaterialList);
            return bomConfigDto;
        }
    }

    @Transactional
    public RdmsBomConfigDto getMaterialListById(String bomConfigId){
        RdmsBomConfig bomConfig = rdmsBomConfigService.selectByPrimaryKey(bomConfigId);
        RdmsBomConfigDto bomConfigDto = CopyUtil.copy(bomConfig, RdmsBomConfigDto.class);
        String characterIdListStr = bomConfigDto.getCharacterIdListStr();
        List<String> characterIdList = JSON.parseArray(characterIdListStr, String.class);
        bomConfigDto.setCharacterIdList(characterIdList);
        List<RdmsBomDto> bomMaterialList = this.getBomMaterialListByCharacterIdList(characterIdList);
        bomConfigDto.setMaterialList(bomMaterialList);
        return bomConfigDto;
    }

    @Transactional
    public List<RdmsMaterialDto> getMaterialListForBomSelected(List<String> codeList){
        RdmsMaterialExample materialExample = new RdmsMaterialExample();
        RdmsMaterialExample.Criteria criteria = materialExample.createCriteria()
                .andCodeIn(codeList)
                .andInBomIsNull()
                .andDeletedEqualTo(0);
        List<RdmsMaterial> rdmsMaterials = rdmsMaterialMapper.selectByExample(materialExample);
        List<RdmsMaterialDto> rdmsMaterialDtos = CopyUtil.copyList(rdmsMaterials, RdmsMaterialDto.class);
        for(RdmsMaterialDto materialDto: rdmsMaterialDtos){
            RdmsMaterialManageDto materialApplicationByCode = rdmsMaterialManageService.getMaterialApplicationByCode_origin(materialDto.getCode());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialApplicationByCode.getWriterId());
            materialDto.setWriterName(rdmsCustomerUser.getTrueName());
        }
        return rdmsMaterialDtos;
    }

    @Transactional
    public List<RdmsMaterialDto> getMaterialListForBomSelected(List<String> codeList, String characterId){
        RdmsMaterialExample materialExample = new RdmsMaterialExample();
        RdmsMaterialExample.Criteria criteria = materialExample.createCriteria()
                .andCodeIn(codeList)
                .andDeletedEqualTo(0);
        List<RdmsMaterial> rdmsMaterials = rdmsMaterialMapper.selectByExample(materialExample);
        //排除掉当前功能已经BOM表中的物料
        List<String> bomCodeListByCharacterId = rdmsBomService.getBomCodeListByCharacterId(characterId);
        if(!CollectionUtils.isEmpty(rdmsMaterials) && !CollectionUtils.isEmpty(bomCodeListByCharacterId)){
            List<RdmsMaterial> collect = rdmsMaterials.stream().filter(item -> !bomCodeListByCharacterId.contains(item.getBomCode())).collect(Collectors.toList());
            List<RdmsMaterialDto> rdmsMaterialDtos = CopyUtil.copyList(collect, RdmsMaterialDto.class);
            for(RdmsMaterialDto materialDto: rdmsMaterialDtos){
                RdmsMaterialManageDto materialApplicationByCode = rdmsMaterialManageService.getMaterialApplicationByCode_origin(materialDto.getCode());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialApplicationByCode.getWriterId());
                materialDto.setWriterName(rdmsCustomerUser.getTrueName());
            }
            return rdmsMaterialDtos;
        }else{
            List<RdmsMaterialDto> rdmsMaterialDtos = CopyUtil.copyList(rdmsMaterials, RdmsMaterialDto.class);
            for(RdmsMaterialDto materialDto: rdmsMaterialDtos){
                RdmsMaterialManageDto materialApplicationByCode = rdmsMaterialManageService.getMaterialApplicationByCode_origin(materialDto.getCode());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialApplicationByCode.getWriterId());
                materialDto.setWriterName(rdmsCustomerUser.getTrueName());
            }
            return rdmsMaterialDtos;
        }
    }

    public RdmsMaterial selectByPrimaryKey(String id){
        return rdmsMaterialMapper.selectByPrimaryKey(id);
    }

    public RdmsMaterialReturn selectByPrimaryKey_return(String id){
        return rdmsMaterialReturnMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsMaterial material) {
        if(ObjectUtils.isEmpty(material.getId())){
            return this.insert(material);
        }else{
            RdmsMaterial rdmsMaterial = this.selectByPrimaryKey(material.getId());
            if(ObjectUtils.isEmpty(rdmsMaterial)){
                return this.insert(material);
            }else{
                return this.update(material);
            }
        }
    }

    public String save_return(RdmsMaterialReturn material) {
        if(ObjectUtils.isEmpty(material.getId())){
            return this.insert_return(material);
        }else{
            RdmsMaterialReturn rdmsMaterialReturn = this.selectByPrimaryKey_return(material.getId());
            if(ObjectUtils.isEmpty(rdmsMaterialReturn)){
                return this.insert_return(material);
            }else{
                return this.update_return(material);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(@NotNull RdmsMaterial material) {
        if(org.springframework.util.ObjectUtils.isEmpty(material.getId())){
            material.setId(UuidUtil.getShortUuid());
        }
        RdmsMaterial rdmsMaterial = rdmsMaterialMapper.selectByPrimaryKey(material.getId());
        if(! ObjectUtils.isEmpty(rdmsMaterial)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            material.setCreateTime(new Date());
            material.setUpdateTime(new Date());
            material.setDeleted(0);
            if(ObjectUtils.isEmpty(material.getAuxStatus())){
                material.setAuxStatus(ApplicationStatusEnum.NOTSET.getStatus());
            }
            rdmsMaterialMapper.insert(material);
            return material.getId();
        }
    }

    private String insert_return(@NotNull RdmsMaterialReturn material) {
        if(org.springframework.util.ObjectUtils.isEmpty(material.getId())){
            material.setId(UuidUtil.getShortUuid());
        }
        RdmsMaterialReturn rdmsMaterialReturn = rdmsMaterialReturnMapper.selectByPrimaryKey(material.getId());
        if(! ObjectUtils.isEmpty(rdmsMaterialReturn)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            material.setCreateTime(new Date());
            material.setUpdateTime(new Date());
            material.setDeleted(0);
            if(ObjectUtils.isEmpty(material.getAuxStatus())){
                material.setAuxStatus(ApplicationStatusEnum.NOTSET.getStatus());
            }
            rdmsMaterialReturnMapper.insert(material);
            return material.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsMaterial material) {
        if(ObjectUtils.isEmpty(material.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterial rdmsMaterial = this.selectByPrimaryKey(material.getId());
        if(ObjectUtils.isEmpty(rdmsMaterial)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            material.setUpdateTime(new Date());
            material.setDeleted(0);
            rdmsMaterialMapper.updateByPrimaryKey(material);
            return material.getId();
        }
    }

    public String update_return(RdmsMaterialReturn material) {
        if(ObjectUtils.isEmpty(material.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialReturn rdmsMaterialReturn = this.selectByPrimaryKey_return(material.getId());
        if(ObjectUtils.isEmpty(rdmsMaterialReturn)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            material.setUpdateTime(new Date());
            material.setDeleted(0);
            rdmsMaterialReturnMapper.updateByPrimaryKey(material);
            return material.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsMaterial material){
        rdmsMaterialMapper.updateByPrimaryKeySelective(material);
        return material.getId();
    }

    public String updateByPrimaryKeySelective_return(RdmsMaterialReturn material){
        rdmsMaterialReturnMapper.updateByPrimaryKeySelective(material);
        return material.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsMaterial material = rdmsMaterialMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(material)){
            material.setDeleted(1);
            this.update(material);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    public void delete_return(String id) {
        RdmsMaterialReturn rdmsMaterialReturn = rdmsMaterialReturnMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsMaterialReturn)){
            rdmsMaterialReturn.setDeleted(1);
            this.update_return(rdmsMaterialReturn);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }


}
