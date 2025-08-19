/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCbbMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RdmsCbbService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCbbService.class);

    @Resource
    private RdmsCbbMapper rdmsCbbMapper;
    @Autowired
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Autowired
    private RdmsCharacterService rdmsCharacterService;
    @Autowired
    private RdmsCharacterDataService rdmsCharacterDataService;
    @Autowired
    private RdmsSubprojectService rdmsSubprojectService;
    @Autowired
    private RdmsProjectService rdmsProjectService;
    @Autowired
    private RdmsCustomerService rdmsCustomerService;

    @Transactional
    public List<RdmsCbbDto> getCbbListByIdList(String customerId , List<String> cbbIdList) {
        RdmsCbbExample cbbExample = new RdmsCbbExample();
        RdmsCbbExample.Criteria criteria = cbbExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andIdIn(cbbIdList)
                .andDeletedEqualTo(0);
        List<RdmsCbb> rdmsCbbs = rdmsCbbMapper.selectByExample(cbbExample);
        return CopyUtil.copyList(rdmsCbbs, RdmsCbbDto.class);
    }

    @Transactional
    public long getSuggestUpdateNum(String createrId) {
        RdmsCbbExample cbbExample = new RdmsCbbExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.SUBMIT.getStatus());
        statusList.add(CharacterStatusEnum.SAVED.getStatus());
        statusList.add(CharacterStatusEnum.REFUSE.getStatus());
        cbbExample.setOrderByClause("create_time desc");
        cbbExample.createCriteria()
                .andCreaterIdEqualTo(createrId)
                .andJobitemTypeEqualTo(JobItemTypeEnum.SUGGEST_UPDATE.getType())
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        return rdmsCbbMapper.countByExample(cbbExample);
    }

    @Transactional
    public RdmsCbbDto getRecordAndData(String cbbId, String loginUserId) {
        RdmsCbb rdmsCbb = this.selectByPrimaryKey(cbbId);
        RdmsCbbDto cbbDto = CopyUtil.copy(rdmsCbb, RdmsCbbDto.class);
        cbbDto.setLoginUserId(loginUserId);
        this.appendCbbRecordAndDataInfo(cbbDto);
        return cbbDto;
    }

    @Transactional
    public void listUpdateSuggestItems(PageDto<RdmsCbbDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCbbExample cbbExample = new RdmsCbbExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.SUBMIT.getStatus());
        statusList.add(CharacterStatusEnum.SAVED.getStatus());
        statusList.add(CharacterStatusEnum.REFUSE.getStatus());
        cbbExample.setOrderByClause("create_time desc");
        cbbExample.createCriteria()
                .andCreaterIdEqualTo(pageDto.getActor())
                .andJobitemTypeEqualTo(JobItemTypeEnum.SUGGEST_UPDATE.getType())
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCbb> rdmsCbbs = rdmsCbbMapper.selectByExample(cbbExample);
        List<RdmsCbbDto> rdmsCbbDtos = CopyUtil.copyList(rdmsCbbs, RdmsCbbDto.class);
        if(!CollectionUtils.isEmpty(rdmsCbbDtos)){
            for(RdmsCbbDto cbbDto: rdmsCbbDtos){
                cbbDto.setLoginUserId(pageDto.getLoginUserId());
                this.appendRecordSimpleInfo(cbbDto);
            }
        }
        PageInfo<RdmsCbb> pageInfo = new PageInfo<>(rdmsCbbs);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsCbbDtos);
    }

    @Transactional
    public RdmsCbbDto getCbbRecordDetailById(String cbbId, String loginUserId) {
        RdmsCbb rdmsCbb = this.selectByPrimaryKey(cbbId);
        RdmsCbbDto cbbDto = CopyUtil.copy(rdmsCbb, RdmsCbbDto.class);
        if(ObjectUtils.isEmpty(cbbDto)){
            return cbbDto;
        }
        cbbDto.setLoginUserId(loginUserId);
        this.appendCbbRecordAndDataInfo(cbbDto);
        return cbbDto;
    }

    public void appendCbbRecordAndDataInfo(RdmsCbbDto cbbDto){
        if(!ObjectUtils.isEmpty(cbbDto)){
            this.appendRecordSimpleInfo(cbbDto);
            //添加data数据
            RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(cbbDto.getCharacterId());
            if(! ObjectUtils.isEmpty(rdmsCharacterData)){
                cbbDto.setFunctionDescription(rdmsCharacterData.getFunctionDescription());
                cbbDto.setWorkCondition(rdmsCharacterData.getWorkCondition());
                cbbDto.setInputLogicalDesc(rdmsCharacterData.getInputLogicalDesc());
                cbbDto.setFunctionLogicalDesc(rdmsCharacterData.getFunctionLogicalDesc());
                cbbDto.setOutputLogicalDesc(rdmsCharacterData.getOutputLogicalDesc());
                cbbDto.setTestMethod(rdmsCharacterData.getTestMethod());
            }
        }
    }

    public void appendRecordSimpleInfo(RdmsCbbDto cbbDto){
        if(!ObjectUtils.isEmpty(cbbDto)){
            if(! ObjectUtils.isEmpty(cbbDto.getProductManagerId())){
                RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(cbbDto.getProductManagerId());
                if(productManager != null){
                    cbbDto.setProductManagerName(productManager.getTrueName());
                }
            }
            if(! ObjectUtils.isEmpty(cbbDto.getCustomerId())){
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(cbbDto.getCustomerId());
                if(rdmsCustomer != null){
                    cbbDto.setCustomerName(rdmsCustomer.getCustomerName());
                }
            }
            RdmsCustomerUser writer = rdmsCustomerUserService.selectByPrimaryKey(cbbDto.getCreaterId());
            if(writer != null){
                cbbDto.setCreaterName(writer.getTrueName());
            }
        }
    }

    public void list(@NotNull PageDto<RdmsCbbDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCbbExample rdmsCbbExample = new RdmsCbbExample();
        rdmsCbbExample.setOrderByClause("create_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATE.getStatus());
        rdmsCbbExample.createCriteria().andCustomerIdEqualTo(pageDto.getCustomerId())
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCbb> rdmsCbbs = rdmsCbbMapper.selectByExample(rdmsCbbExample);
        PageInfo<RdmsCbb> pageInfo = new PageInfo<>(rdmsCbbs);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsCbbDto> rdmsCbbDtos = CopyUtil.copyList(rdmsCbbs, RdmsCbbDto.class);
        if(!CollectionUtils.isEmpty(rdmsCbbDtos)){
            for (RdmsCbbDto rdmsCbbDto : rdmsCbbDtos) {
                if(!ObjectUtils.isEmpty(rdmsCbbDto.getCreaterId())){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCbbDto.getCreaterId());
                    rdmsCbbDto.setCreaterName(rdmsCustomerUser.getTrueName());
                }
                if(!ObjectUtils.isEmpty(rdmsCbbDto.getProjectManagerId())){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCbbDto.getProjectManagerId());
                    rdmsCbbDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
                }
                if(!ObjectUtils.isEmpty(rdmsCbbDto.getProductManagerId())){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCbbDto.getProductManagerId());
                    rdmsCbbDto.setProductManagerName(rdmsCustomerUser.getTrueName());
                }
                if(!ObjectUtils.isEmpty(rdmsCbbDto.getSystemManagerId())){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCbbDto.getSystemManagerId());
                    rdmsCbbDto.setSystemManagerName(rdmsCustomerUser.getTrueName());
                }

                //读取character的定义信息或者将项目或子项定义为一个模块的信息
                RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(rdmsCbbDto.getCharacterId());
                rdmsCbbDto.setCharacter(rdmsCharacter);
            }
        }
        pageDto.setList(rdmsCbbDtos);
    }

    @Transactional
    public List<RdmsCbbDto> getCbbListByCustomerId(String customerId){
        RdmsCbbExample cbbExample = new RdmsCbbExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        RdmsCbbExample.Criteria criteria = cbbExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCbb> rdmsCbbs = rdmsCbbMapper.selectByExample(cbbExample);
        return CopyUtil.copyList(rdmsCbbs, RdmsCbbDto.class);
    }

    @Transactional
    public String saveRecordAndData(@NotNull RdmsCharacterDto characterDto) {
        if(ObjectUtils.isEmpty(characterDto.getId())){
            return null;
        }
        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        rdmsCharacterService.save(character);
        RdmsCharacterData characterData = CopyUtil.copy(characterDto, RdmsCharacterData.class);
        characterData.setId(characterDto.getId());
        rdmsCharacterDataService.save(characterData);
        return characterDto.getId();
    }

    @Transactional
    public void saveCharacterToCbb(@NotNull String characterId, DocTypeEnum docTypeEnum) {
        //生成CBB记录
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsCbb rdmsCbb = new RdmsCbb();
        rdmsCbb.setCharacterId(character.getId());
        rdmsCbb.setCustomerId(character.getCustomerId());
        rdmsCbb.setCbbName(character.getCharacterName());
        RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(character.getId());
        if(!ObjectUtils.isEmpty(rdmsCharacterData)){
            rdmsCbb.setCbbIntroduce(rdmsCharacterData.getFunctionDescription());
        }
        if(!ObjectUtils.isEmpty(character.getSubprojectId())){
            RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(character.getSubprojectId());
            if(!ObjectUtils.isEmpty(rdmsProjectSubproject)){
                rdmsCbb.setSupervise(rdmsProjectSubproject.getSupervise());
                rdmsCbb.setProductManagerId(rdmsProjectSubproject.getProductManagerId());
                rdmsCbb.setProjectManagerId(rdmsProjectSubproject.getProjectManagerId());
                rdmsCbb.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
                rdmsCbb.setKeyMemberListStr(rdmsProjectSubproject.getKeyMemberListStr());
            }
        }
        if(!ObjectUtils.isEmpty(character.getProjectId())){
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(character.getProjectId());
            if(!ObjectUtils.isEmpty(rdmsProject)){
                rdmsCbb.setSystemManagerId(rdmsProject.getSystemManagerId());
            }
        }
        rdmsCbb.setCreaterId(character.getWriterId());
        rdmsCbb.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
        rdmsCbb.setType(docTypeEnum.getType());
        rdmsCbb.setRelatedId(null);
        rdmsCbb.setCreateTime(new Date());
        rdmsCbb.setReleaseTime(new Date());
        rdmsCbb.setDeleted(0);
        rdmsCbb.setDevVersion("1.00");
        this.save(rdmsCbb);
    }

    /**
     */
    @Transactional
    public List<RdmsCbbDto> getCbbByCustomerId(String customerId){
        RdmsCbbExample rdmsCbbExample = new RdmsCbbExample();
        rdmsCbbExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsCbb> rdmsCbbs = rdmsCbbMapper.selectByExample(rdmsCbbExample);
        if(CollectionUtils.isEmpty(rdmsCbbs)){
            return null;
        }
        List<RdmsCbbDto> rdmsCbbDtos = CopyUtil.copyList(rdmsCbbs, RdmsCbbDto.class);
        return rdmsCbbDtos;
    }

    @Transactional
    public List<RdmsCbbDto> getCbbByCbbCode(String characterId){
        RdmsCbbExample rdmsCbbExample = new RdmsCbbExample();
        rdmsCbbExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andDeletedEqualTo(0);
        List<RdmsCbb> rdmsCbbs = rdmsCbbMapper.selectByExample(rdmsCbbExample);
        if(CollectionUtils.isEmpty(rdmsCbbs)){
            return null;
        }
        List<RdmsCbbDto> rdmsCbbDtos = CopyUtil.copyList(rdmsCbbs, RdmsCbbDto.class);
        return rdmsCbbDtos;
    }

    public RdmsCbb selectByPrimaryKey(String id) {
        RdmsCbb rdmsCbb = rdmsCbbMapper.selectByPrimaryKey(id);
        return rdmsCbb;
    }

    public String updateByPrimaryKeySelective(@NotNull RdmsCbb rdmsCbb) {
        RdmsCbb rdmsCbb1 = this.selectByPrimaryKey(rdmsCbb.getId());
        if(ObjectUtils.isEmpty(rdmsCbb1)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsCbbMapper.updateByPrimaryKeySelective(rdmsCbb);
            return rdmsCbb.getId();
        }
    }
    /**
     * 保存
     */
    public String save(@NotNull RdmsCbb rdmsCbb) {
        if(rdmsCbb.getCreateTime()==null){
            rdmsCbb.setCreateTime(new Date());
        }
        rdmsCbb.setDeleted(0);
        if(!ObjectUtils.isEmpty(rdmsCbb.getId())){
            RdmsCbb rdmsCbb1 = this.selectByPrimaryKey(rdmsCbb.getId());
            if(ObjectUtils.isEmpty(rdmsCbb1)){
                return this.insert(rdmsCbb);
            }else{
                return this.update(rdmsCbb);
            }
        }else{
            return this.insert(rdmsCbb);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsCbb rdmsCbb) {
        if(ObjectUtils.isEmpty(rdmsCbb.getId())){  //当前端页面给出projectID时,将不为空
            rdmsCbb.setId(UuidUtil.getShortUuid());
        }
        RdmsCbb rdmsCbb1 = this.selectByPrimaryKey(rdmsCbb.getId());
        if(!ObjectUtils.isEmpty(rdmsCbb1)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsCbbMapper.insert(rdmsCbb);
            return rdmsCbb.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsCbb rdmsCbb) {
        if(ObjectUtils.isEmpty(rdmsCbb.getId())){
            return null;
        }
        RdmsCbb rdmsCbb1 = this.selectByPrimaryKey(rdmsCbb.getId());
        if(!ObjectUtils.isEmpty(rdmsCbb1)){
            rdmsCbb.setId(rdmsCbb1.getId());
            rdmsCbbMapper.updateByPrimaryKey(rdmsCbb);
            return rdmsCbb.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCbb rdmsCbb = rdmsCbbMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsCbb)){
            rdmsCbb.setDeleted(1);
            this.update(rdmsCbb);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
