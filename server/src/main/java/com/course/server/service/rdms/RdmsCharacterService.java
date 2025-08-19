/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.course.server.domain.*;
import com.course.server.domain.rdms.MyCharacter;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCbbMapper;
import com.course.server.mapper.RdmsCharacterMapper;
import com.course.server.mapper.RdmsProjectSubprojectMapper;
import com.course.server.mapper.rdms.MyCharacterMapper;
import com.course.server.service.calculate.CalBudgetService;
import com.course.server.util.CopyUtil;
import com.course.server.util.DeepCopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsCharacterService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsCharacterService.class);

    @Resource
    private RdmsCharacterMapper rdmsCharacterMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsDemandService rdmsDemandService;
    @Resource
    private RdmsMilestoneService rdmsMilestoneService;
    @Resource
    private RdmsCharacterProcessService rdmsCharacterProcessService;
    @Resource
    private RdmsJobItemService rdmsJobItemsService;
    @Resource
    private RdmsProjectSubprojectMapper rdmsProjectSubprojectMapper;
    @Resource
    private MyCharacterMapper myCharacterMapper;
    @Resource
    private RdmsJobItemProcessService rdmsJobItemProcessService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private CalBudgetService calBudgetService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsTreeService rdmsTreeService;
    @Resource
    private RdmsCharacterDataService rdmsCharacterDataService;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Resource
    private RdmsFeeManageService rdmsFeeManageService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsBudgetExeService rdmsBudgetExeService;
    @Resource
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;
    @Resource
    private RdmsDocService rdmsDocService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private RdmsCbbService rdmsCbbService;

    public List<RdmsCharacter> getCharacterByCbbProjectId(String cbbProjectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andCbbProjectIdEqualTo(cbbProjectId)
                .andStatusEqualTo(CharacterStatusEnum.ARCHIVED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return rdmsCharacters;
    }

    public List<RdmsCharacter> getSetupedCharacterListBySubprojectId(String subprojectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusEqualTo(CharacterStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return rdmsCharacters;
    }

    public List<RdmsCharacter> getAfterDevelopingCharacterListBySubprojectId(String subprojectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.DEVELOPING.getStatus());
        statusList.add(CharacterStatusEnum.INTEGRATION.getStatus());
        statusList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());
        statusList.add(CharacterStatusEnum.REVIEW.getStatus());
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        characterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return rdmsCharacters;
    }

    @Transactional
    public long getApprovedItemsNum(String customerId, String preProjectId, String userId) {
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(CharacterStatusEnum.APPROVED.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andAuxStatusNotEqualTo(JobItemTypeEnum.FUNCTION.getType())  //排除系统工程历史记录
                .andDeletedEqualTo(0);
        return rdmsCharacterMapper.countByExample(characterExample);
    }


    @Transactional
    public void listSuggestItems(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());

        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.SUBMIT.getStatus());
        statusList.add(CharacterStatusEnum.SAVED.getStatus());
//        statusList.add(CharacterStatusEnum.HISTORY.getStatus());
        statusList.add(CharacterStatusEnum.DISCARD.getStatus());
        characterExample.setOrderByClause("create_time desc");
        characterExample.createCriteria()
                .andWriterIdEqualTo(pageDto.getActor())
                .andJobitemTypeEqualTo(JobItemTypeEnum.SUGGEST.getType())
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        List<RdmsCharacterDto> characterDtoList = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        if(!CollectionUtils.isEmpty(rdmsCharacters)){
            for(RdmsCharacterDto characterDto: characterDtoList){
                characterDto.setLoginUserId(pageDto.getLoginUserId());
                this.appendRecordSimpleInfo(characterDto);
            }
        }
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(characterDtoList);
    }

    @Transactional
    public long getSuggestNum(String writerId) {
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.SUBMIT.getStatus());
        statusList.add(CharacterStatusEnum.SAVED.getStatus());
//        statusList.add(CharacterStatusEnum.HISTORY.getStatus());
        statusList.add(CharacterStatusEnum.DISCARD.getStatus());
        characterExample.setOrderByClause("create_time desc");
        characterExample.createCriteria()
                .andWriterIdEqualTo(writerId)
                .andJobitemTypeEqualTo(JobItemTypeEnum.SUGGEST.getType())
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
         return rdmsCharacterMapper.countByExample(characterExample);
    }

    public List<RdmsCharacter> getCharacterListByMilestone(String milestoneId){
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.createCriteria()
                .andMilestoneIdEqualTo(milestoneId)
                .andDeletedEqualTo(0);
        return rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
    }

    public List<RdmsCharacter> getCharacterListByProjectId(String projectId){
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andDeletedEqualTo(0);
        return rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
    }
    public List<RdmsCharacter> getCharacterListByCustomerId(String customerId){
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        return rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
    }
    public List<RdmsCharacter> getCharacterListByMilestone_valid(String milestoneId){
        List<RdmsCharacter> rdmsCharacters_valid = new ArrayList<>();
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.createCriteria()
                .andMilestoneIdEqualTo(milestoneId)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        if(!CollectionUtils.isEmpty(rdmsCharacters)){
            for(RdmsCharacter character: rdmsCharacters){
                if(!ObjectUtils.isEmpty(character.getSubprojectId())){
                    RdmsProjectSubprojectDto subprojectBySubprojectId = rdmsProjectService.getSubprojectBySubprojectId(character.getSubprojectId());
                    if(subprojectBySubprojectId.getStatus().equals(ProjectStatusEnum.SETUPED.getStatus()) || subprojectBySubprojectId.getStatus().equals(ProjectStatusEnum.ONGOING.getStatus())){
                        rdmsCharacters_valid.add(character);
                    }
                }
            }
        }
        return rdmsCharacters_valid;
    }
    public void appendRecordSimpleInfo(RdmsCharacterDto characterDto){
        if(!ObjectUtils.isEmpty(characterDto)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            characterDto.setCreateTimeStr(sdf.format(characterDto.getCreateTime()));
            if(! ObjectUtils.isEmpty(characterDto.getProductManagerId())){
                RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                if(productManager != null){
                    characterDto.setProductManagerName(productManager.getTrueName());
                }
            }
            String fileListStr = characterDto.getFileListStr();
            if(fileListStr != null && fileListStr.length() > 6){
                List<String> fileIdList = JSON.parseArray(fileListStr, String.class);
                if(! CollectionUtils.isEmpty(fileIdList)){
                    List<RdmsFileDto> fileDtos = new ArrayList<>();
                    for(String id : fileIdList){
                        RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(id);
                        RdmsFileDto fileDto = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
                        //读取访问权限
                        if(!ObjectUtils.isEmpty(fileDto)){
                            //补充填写文件权限信息
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                            if(ObjectUtils.isEmpty(byFileId)){
                                fileDto.setAuthStatus(true);
                            }else{
                                String authIdsStr = byFileId.getAuthIdsStr();
                                if(ObjectUtils.isEmpty(authIdsStr)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                    if(!ObjectUtils.isEmpty(characterDto.getLoginUserId()) && strings.contains(characterDto.getLoginUserId())){
                                        fileDto.setAuthStatus(true);
                                    }else{
                                        fileDto.setAuthStatus(false);
                                    }
                                }
                            }
                        }

                        fileDtos.add(fileDto);
                    }
                    characterDto.setFileList(fileDtos);
                }
            }
            //需求列表
            String demandListStr = characterDto.getDemandListStr();
            if(demandListStr != null){
                List<String> demandIdList = JSON.parseArray(demandListStr, String.class);
                if(! CollectionUtils.isEmpty(demandIdList)){
                    List<RdmsDemandDto> demandDiscernDtos = new ArrayList<>();
                    for(String id : demandIdList){
                        RdmsDemand rdmsDemandDiscern = rdmsDemandService.selectByPrimaryKey(id);
                        RdmsDemandDto demandDiscernDto = CopyUtil.copy(rdmsDemandDiscern, RdmsDemandDto.class);
                        if(!ObjectUtils.isEmpty(demandDiscernDto.getFileListStr())){
                            List<String> fileIdList = JSON.parseArray(demandDiscernDto.getFileListStr(), String.class);
                            if(!CollectionUtils.isEmpty(fileIdList)){
                                for(String fileId: fileIdList){
                                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                                    //读取访问权限
                                    if(!ObjectUtils.isEmpty(fileDto)){
                                        //补充填写文件权限信息
                                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                        if(ObjectUtils.isEmpty(byFileId)){
                                            fileDto.setAuthStatus(true);
                                        }else{
                                            String authIdsStr = byFileId.getAuthIdsStr();
                                            if(ObjectUtils.isEmpty(authIdsStr)){
                                                fileDto.setAuthStatus(true);
                                            }else{
                                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                                if(!ObjectUtils.isEmpty(characterDto.getLoginUserId()) && strings.contains(characterDto.getLoginUserId())){
                                                    fileDto.setAuthStatus(true);
                                                }else{
                                                    fileDto.setAuthStatus(false);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                        demandDiscernDtos.add(demandDiscernDto);
                    }
                    characterDto.setDemandList(demandDiscernDtos);
                }
            }
            if(! ObjectUtils.isEmpty(characterDto.getCustomerId())){
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(characterDto.getCustomerId());
                if(rdmsCustomer != null){
                    characterDto.setCustomerName(rdmsCustomer.getCustomerName());
                }
            }
            if(! ObjectUtils.isEmpty(characterDto.getPreProjectId())){
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                if(! ObjectUtils.isEmpty(rdmsPreProject)){
                    characterDto.setPreProjectName(rdmsPreProject.getPreProjectName());
                }
            }
            if(! ObjectUtils.isEmpty(characterDto.getProjectId())){
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(characterDto.getProjectId());
                if(! ObjectUtils.isEmpty(rdmsProject)){
                    characterDto.setProjectName(rdmsProject.getProjectName());
                }
            }
            if(! ObjectUtils.isEmpty(characterDto.getSubprojectId())){
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(characterDto.getSubprojectId());
                if(! ObjectUtils.isEmpty(subproject)){
                    characterDto.setSubprojectName(subproject.getLabel());
                    characterDto.setRdType(subproject.getRdType());

                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
                    characterDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
                }
            }
            if(! ObjectUtils.isEmpty(characterDto.getMilestoneId())){
                RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(characterDto.getMilestoneId());
                if(!ObjectUtils.isEmpty(rdmsMilestoneDto)){
                    characterDto.setPlanCompleteTime(rdmsMilestoneDto.getReviewTime());
                }
            }
            RdmsCustomerUser writer = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getWriterId());
            if(writer != null){
                characterDto.setWriterName(writer.getTrueName());
            }

            if(!ObjectUtils.isEmpty(characterDto.getSubprojectId())){
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(characterDto.getSubprojectId());
                if(!ObjectUtils.isEmpty(subproject)){
                    characterDto.setProjectManagerId(subproject.getProjectManagerId());
                }
            }

            BigDecimal bigDecimal = this.calcSumOtherFee(characterDto.getId());
            characterDto.setSumOtherFee(bigDecimal);
        }
    }

    public BigDecimal calcSumOtherFee(String characterId){
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        BigDecimal sum = BigDecimal.ZERO;
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getTestFee())){
            sum = sum.add(rdmsCharacter.getTestFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getPowerFee())){
            sum = sum.add(rdmsCharacter.getPowerFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getConferenceFee())){
            sum = sum.add(rdmsCharacter.getConferenceFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getBusinessFee())){
            sum = sum.add(rdmsCharacter.getBusinessFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getCooperationFee())){
            sum = sum.add(rdmsCharacter.getCooperationFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getPropertyFee())){
            sum = sum.add(rdmsCharacter.getPropertyFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getLaborFee())){
            sum = sum.add(rdmsCharacter.getLaborFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getConsultingFee())){
            sum = sum.add(rdmsCharacter.getConsultingFee());
        }
        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getManagementFee())){
            sum = sum.add(rdmsCharacter.getManagementFee());
        }
        return  sum;
    }

    public void appendCharacterRecordAndDataInfo(RdmsCharacterDto characterDto){
        if(!ObjectUtils.isEmpty(characterDto)){
            this.appendRecordSimpleInfo(characterDto);
            //添加data数据
            RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(characterDto.getId());
            if(! ObjectUtils.isEmpty(rdmsCharacterData)){
                characterDto.setFunctionDescription(rdmsCharacterData.getFunctionDescription());
                characterDto.setWorkCondition(rdmsCharacterData.getWorkCondition());
                characterDto.setInputLogicalDesc(rdmsCharacterData.getInputLogicalDesc());
                characterDto.setFunctionLogicalDesc(rdmsCharacterData.getFunctionLogicalDesc());
                characterDto.setOutputLogicalDesc(rdmsCharacterData.getOutputLogicalDesc());
                characterDto.setTestMethod(rdmsCharacterData.getTestMethod());
            }
        }
    }

    public RdmsCharacterTreeDto getCharacterTree(String characterId){
        RdmsCharacterTreeDto rdmsCharacterTreeDto = new RdmsCharacterTreeDto();
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        rdmsCharacterTreeDto.setThisCharacter(rdmsCharacter);
        //分解自哪条功能
        if(!ObjectUtils.isEmpty(rdmsCharacter.getParent())){
            RdmsCharacter rdmsCharacter1 = this.selectByPrimaryKey(rdmsCharacter.getParent());
            rdmsCharacterTreeDto.setParentCharacter(rdmsCharacter1);
        }
        //分解出哪几条功能
        List<RdmsCharacterDto> characterListByParentId = this.getCharacterListByParentId(characterId);
        List<RdmsCharacter> rdmsCharacters = CopyUtil.copyList(characterListByParentId, RdmsCharacter.class);
        rdmsCharacterTreeDto.setChildCharacterList(rdmsCharacters);
        //迭代的历史版本
        List<RdmsCharacter> iterationVersionList = this.getIterationVersionList(characterId);
        rdmsCharacterTreeDto.setIterationCharacterList(iterationVersionList);
        return rdmsCharacterTreeDto;
    }

    public RdmsCharacterInheritTreeDto getCharacterInheritTree(String characterId){
        //继承树；
        //1. 如果工单类型是 SeggestUpdate， IterationVersion 大于1， 那么他的上一级记录是 characterSerial对应的记录
        //2. 如果工单类型是 decompose， 则他的父级记录是 parent 对应的记录
        //3. 如果工单类型是 Iteration， 则他的父级记录是 characterSerial相同 ，IterationVersion 减1 状态为history的记录， 如果IterationVersion==1 则没有更前面的迭代记录了

        //程序目标: 从当期记录开始, 向前查所有的迭代记录, 到达分解自记录或者迭代根记录为止

        RdmsCharacterInheritTreeDto inheritTree = new RdmsCharacterInheritTreeDto();
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        if(ObjectUtils.isEmpty(rdmsCharacter)){
            return null;
        }
        inheritTree.setThisCharacter(rdmsCharacter);

        RdmsCharacter tempCharacter = CopyUtil.copy(rdmsCharacter, RdmsCharacter.class);
        List<RdmsCharacter> characterList = new ArrayList<>();
        characterList.add(tempCharacter);
        boolean flag = true;
        int num = 0;
        do {
            num++;
            switch (tempCharacter.getJobitemType()) {
                case "SUGGEST_UPDATE" :{
                    if(tempCharacter.getIterationVersion()>1){
                        tempCharacter = this.selectByPrimaryKey(tempCharacter.getCharacterSerial());
                        characterList.add(tempCharacter);
                    }else{
                        flag = false;
                    }
                    break;
                }
                case "DECOMPOSE" :{
                    if(!ObjectUtils.isEmpty(tempCharacter.getParent())){
                        tempCharacter = this.selectByPrimaryKey(tempCharacter.getParent());
                        characterList.add(tempCharacter);
                    }else{
                        flag = false;
                    }
                    break;
                }
                case "ITERATION" :{
                    if(tempCharacter.getIterationVersion()>1){
                        for(int i = tempCharacter.getIterationVersion(); i > 1;){
                            i = i - 1;
                            RdmsCharacterExample characterExample = new RdmsCharacterExample();
                            characterExample.createCriteria()
                                    .andCharacterSerialEqualTo(tempCharacter.getCharacterSerial())
                                    .andIterationVersionEqualTo(i)
                                    .andStatusEqualTo(CharacterStatusEnum.HISTORY.getStatus());
                            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
                            if(!CollectionUtils.isEmpty(rdmsCharacters)){
                                tempCharacter = rdmsCharacters.get(0);
                                characterList.add(tempCharacter);
                            }else{
                                i = i - 1;
                                RdmsCharacterExample characterExample2 = new RdmsCharacterExample();
                                characterExample2.createCriteria()
                                        .andCharacterSerialEqualTo(tempCharacter.getCharacterSerial())
                                        .andIterationVersionEqualTo(i)
                                        .andStatusEqualTo(CharacterStatusEnum.HISTORY.getStatus());
                                List<RdmsCharacter> rdmsCharacters2 = rdmsCharacterMapper.selectByExample(characterExample2);
                                if(!CollectionUtils.isEmpty(rdmsCharacters2)){
                                    tempCharacter = rdmsCharacters2.get(0);
                                    characterList.add(tempCharacter);
                                }else{
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }else{
                        flag = false;
                    }
                    break;
                }
                default:{
                    flag = false;
                }
            }
        } while (flag && num <100 );
        List<RdmsCharacter> rdmsCharacterList = characterList.stream().sorted(Comparator.comparing(RdmsCharacter::getUpdateTime).reversed()).collect(Collectors.toList());
        inheritTree.setIterationCharacterList(rdmsCharacterList);
        return inheritTree;
    }

    /**
    * Customer 查询时, 还是使用PreProjectId 作为参数,
    */
    @Transactional
    public void listByCustomerId(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        if(pageDto.getStatus() != null){
            rdmsCharacterExample.createCriteria()
                    .andCustomerIdEqualTo(pageDto.getKeyWord())
                    .andStatusEqualTo(pageDto.getStatus())
                    .andDeletedEqualTo(0);
        }else{
            rdmsCharacterExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsProductCharacterDtos);
    }

    /**
    * Customer 查询时, 还是使用PreProjectId 作为参数,
    */
    @Transactional
    public void listDevelopingCharactersByPjm(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.DEVELOPING.getStatus());
        statusEnumList.add(CharacterStatusEnum.INTEGRATION.getStatus());
        statusEnumList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());
        rdmsCharacterExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andNextNodeEqualTo(pageDto.getActor())
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(pageDto.getLoginUserId());
            this.appendRecordSimpleInfo(characterDto);
            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.SUBMIT);
            Integer jobitemNumberByCharacterAndStatus = rdmsJobItemsService.getDevelopJobitemNumberByCharacterAndStatus_notAssist(characterDto.getId(), statusList);
            characterDto.setSubmittedJobNum(jobitemNumberByCharacterAndStatus);
            Integer applicationNum = rdmsMaterialManageService.getApplicationNum(characterDto.getId());
            characterDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getApplicationNum(characterDto.getId());
            characterDto.setFeeAppNum(feeApplicationNum);

            Boolean characterDevelopCompleteStatus = this.getCharacterDevelopCompleteStatus(characterDto.getId());
            characterDto.setDevCompleteStatus(characterDevelopCompleteStatus);
        }
        pageDto.setList(rdmsProductCharacterDtos);
    }

    @Transactional
    public void listTestCharactersByTm(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");

        List<String> projectTypeList = new ArrayList<>();
        projectTypeList.add(ProjectStageEnum.PROJECT.getStage());

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.SETUPED.getStatus());
        statusEnumList.add(CharacterStatusEnum.DECOMPOSED.getStatus());
        statusEnumList.add(CharacterStatusEnum.ITERATING.getStatus());
        statusEnumList.add(CharacterStatusEnum.DEVELOPING.getStatus());
        statusEnumList.add(CharacterStatusEnum.INTEGRATION.getStatus());

        List<String> auxStatusEnumList = new ArrayList<>();
        auxStatusEnumList.add(CharacterStatusEnum.HISTORY.getStatus());
        auxStatusEnumList.add(CharacterStatusEnum.UPDATED.getStatus());

        rdmsCharacterExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andStageIn(projectTypeList)
                .andStatusIn(statusEnumList)
                .andAuxStatusNotIn(auxStatusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(pageDto.getLoginUserId());
            this.appendRecordSimpleInfo(characterDto);
            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.SUBMIT);
            Integer jobitemNumberByCharacterAndStatus = rdmsJobItemsService.getTaskTestJobitemNumberByCharacterAndStatus(characterDto.getId(), statusList);
            characterDto.setSubmittedJobNum(jobitemNumberByCharacterAndStatus);
        }
        pageDto.setList(rdmsProductCharacterDtos);
    }


    @Transactional
    public Boolean getCharacterDevelopCompleteStatus(String characterId) {
        Boolean flag = false;
        //已经完成的工单和工单数
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        jobitemTypeList.add(JobItemTypeEnum.REVIEW);

        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);

        List<RdmsJobItemDto> completeJobList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, jobItemStatusList, jobitemTypeList);
        List<RdmsJobItemDto> allJobList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, null, jobitemTypeList);
        if (!CollectionUtils.isEmpty(completeJobList) && !CollectionUtils.isEmpty(allJobList)) {
            if (completeJobList.size() == allJobList.size()) {
                for (RdmsJobItemDto jobItemDto : completeJobList) {
                    if (jobItemDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType()) && jobItemDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())) {
                        flag = true;
                    }
                }
            }
        }

        return flag && this.getMaterialManageCompleteFlag(characterId) && this.getOtherFeeManageCompleteFlag(characterId);
    }

    public Boolean getMaterialManageCompleteFlag(String characterId){
        List<RdmsMaterialManageDto> materialList = rdmsMaterialManageService.getListByCharacterId(characterId, false);
        if(! CollectionUtils.isEmpty(materialList))
        {
            List<RdmsMaterialManageDto> expenditureMaterialManageDtos = materialList.stream().filter(item -> item.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())).collect(Collectors.toList());
            long count1 = expenditureMaterialManageDtos.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())).count();
            long count2 = expenditureMaterialManageDtos.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())).count();
            return expenditureMaterialManageDtos.size() == count1 + count2;
        }
        return true;
    }

    public Boolean getOtherFeeManageCompleteFlag(String characterId){
        List<RdmsFeeManageDto> otherFeeList = rdmsFeeManageService.getListByCharacterId(characterId);
        if(! CollectionUtils.isEmpty(otherFeeList))
        {
            long count1 = otherFeeList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())).count();
            long count2 = otherFeeList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())).count();
            return otherFeeList.size() == count1 + count2;
        }
        return true;
    }

    @Transactional
    public RdmsHmiCharacterCompleteStatusDto getCharacterDevelopCompleteInfoAndStatus(String characterId) {
        RdmsHmiCharacterCompleteStatusDto characterCompleteStatusDto = new RdmsHmiCharacterCompleteStatusDto();
        Boolean flag = false;
        //已经完成的工单和工单数
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        jobitemTypeList.add(JobItemTypeEnum.REVIEW);

        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);

        List<RdmsJobItemDto> completeJobList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, jobItemStatusList, jobitemTypeList);
        List<RdmsJobItemDto> allJobList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, null, jobitemTypeList);
        if (!CollectionUtils.isEmpty(completeJobList) && !CollectionUtils.isEmpty(allJobList)) {
            if (completeJobList.size() == allJobList.size()) {
                for (RdmsJobItemDto jobItemDto : completeJobList) {
                    if (jobItemDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType()) && jobItemDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())) {
                        flag = true;
                        characterCompleteStatusDto.setIntComplete(flag);
                    }
                }
            }
        }

        RdmsHmiCharacterCompleteStatusDto materialManageCompleteInfo = this.getMaterialManageCompleteInfo(characterId);
        if(materialManageCompleteInfo.getMaterialApplicationNum() != null){
            characterCompleteStatusDto.setMaterialApplicationNum(materialManageCompleteInfo.getMaterialApplicationNum());
        }else{
            characterCompleteStatusDto.setMaterialApplicationNum(0);
        }
        if(materialManageCompleteInfo.getMaterialCompleteNum() != null){
            characterCompleteStatusDto.setMaterialCompleteNum(materialManageCompleteInfo.getMaterialCompleteNum());
        }else{
            characterCompleteStatusDto.setMaterialCompleteNum(0L);
        }

        boolean flag1 = false;
        Integer completeNum = 0;
        try {
            if(characterCompleteStatusDto.getMaterialCompleteNum() != null){
                completeNum = Math.toIntExact(characterCompleteStatusDto.getMaterialCompleteNum());
            }else{
                completeNum = 0;
            }
            // 使用 intValue
        } catch (ArithmeticException e) {
            // 处理超出范围的情况
        }
        if(completeNum.equals(characterCompleteStatusDto.getMaterialApplicationNum())){
            flag1 = true;
        }

        RdmsHmiCharacterCompleteStatusDto otherFeeManageCompleteInfo = this.getOtherFeeManageCompleteInfo(characterId);
        if(otherFeeManageCompleteInfo.getFeeApplicationNum() != null){
            characterCompleteStatusDto.setFeeApplicationNum(otherFeeManageCompleteInfo.getFeeApplicationNum());
        }else{
            characterCompleteStatusDto.setFeeApplicationNum(0);
        }

        if(otherFeeManageCompleteInfo.getFeeCompleteNum() != null){
            characterCompleteStatusDto.setFeeCompleteNum(otherFeeManageCompleteInfo.getFeeCompleteNum());
        }else{
            characterCompleteStatusDto.setFeeCompleteNum(0L);
        }

        boolean flag2 = false;
        Integer completeFeeNum = 0;
        try {
            if(characterCompleteStatusDto.getFeeCompleteNum() != null){
                completeFeeNum = Math.toIntExact(characterCompleteStatusDto.getFeeCompleteNum());
            }else{
                completeFeeNum = 0;
            }
            // 使用 intValue
        } catch (ArithmeticException e) {
            // 处理超出范围的情况
        }
        if(completeFeeNum.equals(characterCompleteStatusDto.getFeeApplicationNum())){
            flag2 = true;
        }

        if(flag && flag1 && flag2){
            characterCompleteStatusDto.setCompleteStatus(true);
        }else{
            characterCompleteStatusDto.setCompleteStatus(false);
        }
        return characterCompleteStatusDto;
    }

    public RdmsHmiCharacterCompleteStatusDto getMaterialManageCompleteInfo(String characterId){
        RdmsHmiCharacterCompleteStatusDto characterCompleteStatusDto = new RdmsHmiCharacterCompleteStatusDto();
        List<RdmsMaterialManageDto> materialList = rdmsMaterialManageService.getListByCharacterId(characterId, false);
        if(! CollectionUtils.isEmpty(materialList))
        {
            List<RdmsMaterialManageDto> expenditureMaterialManageDtos = materialList.stream().filter(item -> item.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())).collect(Collectors.toList());
            characterCompleteStatusDto.setMaterialApplicationNum(expenditureMaterialManageDtos.size());
            long count1 = expenditureMaterialManageDtos.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())).count();
            long count2 = expenditureMaterialManageDtos.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())).count();
            characterCompleteStatusDto.setMaterialCompleteNum(count1 + count2);
        }
        return characterCompleteStatusDto;
    }

    public RdmsHmiCharacterCompleteStatusDto getOtherFeeManageCompleteInfo(String characterId){
        RdmsHmiCharacterCompleteStatusDto characterCompleteStatusDto = new RdmsHmiCharacterCompleteStatusDto();
        List<RdmsFeeManageDto> otherFeeList = rdmsFeeManageService.getListByCharacterId(characterId);
        if(! CollectionUtils.isEmpty(otherFeeList))
        {
            characterCompleteStatusDto.setFeeApplicationNum(otherFeeList.size());
            long count1 = otherFeeList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())).count();
            long count2 = otherFeeList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())).count();
            characterCompleteStatusDto.setFeeCompleteNum(count1 + count2);
        }
        return characterCompleteStatusDto;
    }

    @Transactional
    public Integer getNumOfDevCompleteCharacterByNextNode(String nextNode){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria().andDeletedEqualTo(0);
        criteria.andStatusEqualTo(CharacterStatusEnum.DEV_COMPLETE.getStatus());
        criteria.andNextNodeEqualTo(nextNode);
        return (Integer) (int)rdmsCharacterMapper.countByExample(characterExample);
    }
    /**
    * 根据PreProjectID进行分页列表查询
    */
    @Transactional
    public void listByPreProjectId(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        if(pageDto.getStatus() != null){
            rdmsCharacterExample.createCriteria()
                    .andWriterIdEqualTo(pageDto.getActor())
                    .andPreProjectIdEqualTo(pageDto.getKeyWord())
                    .andStatusEqualTo(pageDto.getStatus())
                    .andDeletedEqualTo(0);
        }else{
            rdmsCharacterExample.createCriteria().andPreProjectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(pageDto.getLoginUserId());
            this.appendRecordSimpleInfo(characterDto);
        }
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsProductCharacterDtos);
    }

    /**
    * 根据PreProjectID进行分页列表查询
    */
    @Transactional
    public List<RdmsCharacterDto> listAllApprovedByPreProjectId(String preProjectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andPreProjectIdEqualTo(preProjectId)
                .andStatusEqualTo(CharacterStatusEnum.APPROVED.getStatus())
                .andAuxStatusNotEqualTo(JobItemTypeEnum.FUNCTION.getType())   //不包含经过系统工程处理的功能
                .andDeletedEqualTo(0);

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }

    /**
    * 根据ProjectID和setuped状态查询
    */
    @Transactional
    public List<RdmsCharacter> listSetupedByProjectId(String projectId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("setuped_time desc");
        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusEqualTo(CharacterStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        return rdmsCharacters;
    }

    @Transactional
    public Integer getCountOfDevelopingCharacterByPjm(String pjmId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.createCriteria()
                .andStatusEqualTo(CharacterStatusEnum.DEVELOPING.getStatus())
                .andNextNodeEqualTo(pjmId)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsCharacterMapper.countByExample(rdmsCharacterExample);
    }

    @Transactional
    public long getCountOfCompleteCharacterBySubproject(String subprojectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        statusList.add(CharacterStatusEnum.HISTORY.getStatus());
        statusList.add(CharacterStatusEnum.DISCARD.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED_HISTORY.getStatus());
        characterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        return rdmsCharacterMapper.countByExample(characterExample);
    }

    @Transactional
    public long getCountOfCharacterBySubproject(String subprojectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        return rdmsCharacterMapper.countByExample(characterExample);
    }

    /**
    * 根据ProjectID进行分页列表查询
    */
    @Transactional
    public void listByProjectId(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        if(pageDto.getStatus() != null){
            rdmsCharacterExample.createCriteria()
                    .andProjectIdEqualTo(pageDto.getKeyWord())  //ProjectId
                    .andStatusEqualTo(pageDto.getStatus())
                    .andDeletedEqualTo(0);
        }else{
            rdmsCharacterExample.createCriteria().andPreProjectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsProductCharacterDtos);
    }

    /**
    * 根据ProjectID查询全部组件定义表
    */
    @Transactional
    public List<RdmsCharacterDto> listByProjectId(String projectId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusEqualTo(CharacterStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }

    public Integer getCharacterNumByProjectId(String projectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        return (Integer) (int)rdmsCharacterMapper.countByExample(characterExample);
    }

    @Transactional
    public List<RdmsCharacterDto> getSetupedCharacterListByProjectId(String projectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");

        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusEqualTo(CharacterStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
            //确认性添加数据
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
            if(! ObjectUtils.isEmpty(rdmsProject)){
                characterDto.setProjectManagerId(rdmsProject.getProjectManagerId());
            }
        }
        return rdmsProductCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getSetupCharacterListByProjectId(String projectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusEqualTo(CharacterStatusEnum.SETUP.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
            if(! ObjectUtils.isEmpty(rdmsProject)){
                characterDto.setProjectManagerId(rdmsProject.getProjectManagerId());
            }
        }
        return rdmsProductCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getSetupedCharacterListBySubProjectId(String subprojectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusEqualTo(CharacterStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        if(!CollectionUtils.isEmpty(rdmsProductCharacterDtos)){
            for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
                characterDto.setLoginUserId(loginUserId);
                this.appendRecordSimpleInfo(characterDto);

                RdmsBudgetSheetDto budgetDto = new RdmsBudgetSheetDto();
                RdmsManhourStandard standardManhourByCustomerId = rdmsManhourService.getStandardManhourByCustomerId(characterDto.getCustomerId());
                BigDecimal devManhourFee = standardManhourByCustomerId.getDevManhourFee().multiply(BigDecimal.valueOf(characterDto.getDevManhourApproved()));
                BigDecimal testManhourFee = standardManhourByCustomerId.getTestManhourFee().multiply(BigDecimal.valueOf(characterDto.getTestManhourApproved()));
                budgetDto.setStaff(devManhourFee.add(testManhourFee));
                budgetDto.setMaterial(characterDto.getMaterialFeeApproved()); //功能预算 是先制定一个数额, 然后进行批准,批准过程中可能有调整
                budgetDto.setOther(characterDto.getSumOtherFee());
                characterDto.setBudgetDetail(budgetDto);
            }
        }
        return rdmsProductCharacterDtos;
    }

    /**
    * 根据ProjectID查询全部组件定义表
    */
    @Transactional
    public List<RdmsCharacterDto> listBySubprojectId(String subprojectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }

    /**
    * 根据subProjectID查询全部组件定义表
    */
    @Transactional
    public List<RdmsCharacterDto> listBySubProjectId(String subprojectId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }



    /**
    * 根据ProjectID查询全部组件定义表
    */
    @Transactional
    public List<RdmsCharacterDto> listAllCharactersByProjectId(String projectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");

        List<String> characterStatusList = new ArrayList<>();
        characterStatusList.add(CharacterStatusEnum.SETUPED.getStatus());
        characterStatusList.add(CharacterStatusEnum.DECOMPOSED.getStatus());
        characterStatusList.add(CharacterStatusEnum.ITERATING.getStatus());
        characterStatusList.add(CharacterStatusEnum.DEVELOPING.getStatus());
        characterStatusList.add(CharacterStatusEnum.INTEGRATION.getStatus());
        characterStatusList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());
        characterStatusList.add(CharacterStatusEnum.QUALITY.getStatus());
        characterStatusList.add(CharacterStatusEnum.REVIEW.getStatus());
        characterStatusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        characterStatusList.add(CharacterStatusEnum.UPDATE.getStatus());
        characterStatusList.add(CharacterStatusEnum.UPDATED.getStatus());
        characterStatusList.add(CharacterStatusEnum.UPDATED_HISTORY.getStatus());

        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusIn(characterStatusList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> listAllCharactersBySubProjectId(String subprojectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");

        List<String> characterStatusList = new ArrayList<>();
        characterStatusList.add(CharacterStatusEnum.SETUPED.getStatus());
        characterStatusList.add(CharacterStatusEnum.DECOMPOSED.getStatus());
        characterStatusList.add(CharacterStatusEnum.ITERATING.getStatus());
        characterStatusList.add(CharacterStatusEnum.DEVELOPING.getStatus());
        characterStatusList.add(CharacterStatusEnum.INTEGRATION.getStatus());
        characterStatusList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());
        characterStatusList.add(CharacterStatusEnum.QUALITY.getStatus());
        characterStatusList.add(CharacterStatusEnum.REVIEW.getStatus());
        characterStatusList.add(CharacterStatusEnum.OUT_SOURCE.getStatus());

        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        if(!subproject.getRdType().equals(RdTypeEnum.MODULE.getType())){
            characterStatusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        }

        rdmsCharacterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(characterStatusList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
            //计算已发生费用金额
            BigDecimal characterActiveAccountCharge = rdmsManhourService.getCharacterActiveAccountCharge(characterDto.getId());
            characterDto.setHaveUsedBudget(characterActiveAccountCharge);
        }
        return rdmsCharacterDtos;
    }

/*    @Transactional
    public void listEditing(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andWriterIdEqualTo(pageDto.getActor())
                .andPreProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(CharacterStatusEnum.SAVED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendCharacterRecordAndDataInfo(characterDto);
        }
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsProductCharacterDtos);
    }*/

    @Transactional
    public List<RdmsCharacterDto> getUpdateCharacterList(String preProjectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("update_time asc");
        rdmsCharacterExample.createCriteria()
                .andPreProjectIdEqualTo(preProjectId)
                .andStatusEqualTo(CharacterStatusEnum.UPDATE.getStatus())
                .andJobitemTypeEqualTo(JobItemTypeEnum.UPDATE.getType())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);

        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendCharacterRecordAndDataInfo(characterDto);
            //添加原记录工单列表
            List<RdmsJobItemDto> jobitemListByCharacter = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getParent(), null, null);
            List<RdmsHmiJobitemPlainDto> jobitemPlainDtoList = jobitemListByCharacter.stream().map(item -> new RdmsHmiJobitemPlainDto(item.getId(), item.getJobSerial(), item.getJobName(), item.getType(), item.getExecutorName(), item.getCreateTime())).collect(Collectors.toList());
            List<RdmsHmiJobitemPlainDto> hmiJobitemPlainDtos = jobitemPlainDtoList.stream().sorted(Comparator.comparing(RdmsHmiJobitemPlainDto::getCreateTime)).collect(Collectors.toList());
            characterDto.setDevJobitemPlainList(hmiJobitemPlainDtos);

        }
        return rdmsProductCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByPreProjectIdAndStatusTypeList(String preProjectId, List<CharacterStatusEnum> characterStatusEnumList, List<JobItemTypeEnum> jobItemTypeEnumList) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("update_time asc");
        RdmsCharacterExample.Criteria criteria = rdmsCharacterExample.createCriteria()
                .andPreProjectIdEqualTo(preProjectId)
                .andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(characterStatusEnumList)){
            for(CharacterStatusEnum statusEnum: characterStatusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            if(!org.springframework.util.ObjectUtils.isEmpty(statusList)){
                criteria.andStatusIn(statusList);
            }
        }

        List<String> typeList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(jobItemTypeEnumList)){
            for(JobItemTypeEnum typeEnum: jobItemTypeEnumList){
                typeList.add(typeEnum.getType());
            }
            if(!org.springframework.util.ObjectUtils.isEmpty(typeList)){
                criteria.andJobitemTypeIn(typeList);
            }
        }

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        return rdmsProductCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterByParentIdAndStatus(String parentId, JobItemTypeEnum jobitemType) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("update_time asc");
        RdmsCharacterExample.Criteria criteria = rdmsCharacterExample.createCriteria()
                .andParentEqualTo(parentId)
                .andJobitemTypeEqualTo(jobitemType.getType())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        return rdmsProductCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByPreProjectIdAndStatusAndNotInAuxStatus(String preProjectId, List<CharacterStatusEnum> characterStatusEnumList, List<CharacterStatusEnum> notInAuxStatusList) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("update_time asc");
        RdmsCharacterExample.Criteria criteria = rdmsCharacterExample.createCriteria()
                .andPreProjectIdEqualTo(preProjectId)
                .andJobitemTypeNotEqualTo(JobItemTypeEnum.FUNCTION.getType())  //排除funtion类型的记录
                .andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(characterStatusEnumList)){
            for(CharacterStatusEnum statusEnum: characterStatusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            if(!org.springframework.util.ObjectUtils.isEmpty(statusList)){
                criteria.andStatusIn(statusList);
            }
        }

        List<String> auxStatusList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(notInAuxStatusList)){
            for(CharacterStatusEnum auxStatusEnum: notInAuxStatusList){
                auxStatusList.add(auxStatusEnum.getStatus());
            }
            if(!org.springframework.util.ObjectUtils.isEmpty(auxStatusList)){
                criteria.andAuxStatusNotIn(auxStatusList);
            }
        }

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        return rdmsProductCharacterDtos;
    }

    /**
     * 公司用户查询 "已审批" 清单
     * @param pageDto
     */
    @Transactional
    public void listApproved(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andPreProjectIdEqualTo(pageDto.getKeyWord())
                .andProjectIdIsNull()   //避免预立项阶段 读出系统工程阶段评审通过的Character
                .andNextNodeEqualTo(pageDto.getActor())   //下一节点是产品经理
                .andStatusEqualTo(CharacterStatusEnum.APPROVED.getStatus())
                .andAuxStatusNotEqualTo(JobItemTypeEnum.FUNCTION.getType())  // 排除系统工程产生的历史记录
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(pageDto.getLoginUserId());
            this.appendRecordSimpleInfo(characterDto);
        }

        pageDto.setList(rdmsProductCharacterDtos);
    }

    @Transactional
    public void listModule(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED.getStatus());
        rdmsCharacterExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsCharacterDtos){
            characterDto.setLoginUserId(pageDto.getLoginUserId());
            this.appendRecordSimpleInfo(characterDto);
        }
        //是否已经有组件开发项目
        List<RdmsProject> moduleProjectList = rdmsProjectService.getModuleProjectList(pageDto.getCustomerId());
        if(!CollectionUtils.isEmpty(moduleProjectList)){
            pageDto.setStatus("hasModuleProject");
        }else{
            pageDto.setStatus(null);
        }
        pageDto.setList(rdmsCharacterDtos);
    }

    @Transactional
    public void listModuleBySuper(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED.getStatus());
        rdmsCharacterExample.createCriteria()
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsCharacterDtos){
            characterDto.setLoginUserId(pageDto.getLoginUserId());
            this.appendRecordSimpleInfo(characterDto);
        }
        pageDto.setList(rdmsCharacterDtos);
    }

 /*   @Transactional
    public void listSystemApproved(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andWriterIdEqualTo(pageDto.getActor())
                .andStatusEqualTo(CharacterStatusEnum.APPROVED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsProductCharacterDtos);
    }*/

/*  @Transactional
    public List<RdmsCharacterDto> listApprovedByPreProjectId(String preProjectId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        rdmsCharacterExample.createCriteria()
                .andPreProjectIdEqualTo(preProjectId)
                .andStatusEqualTo(CharacterStatusEnum.APPROVED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }*/

    @Transactional
    public void setCharacterStatusByJobitemId(String jobItemId){
        //获得所有的demandCharacter清单
        RdmsJobItem rdmsJobItem = rdmsJobItemsService.selectByPrimaryKey(jobItemId);
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.createCriteria()
                .andJobitemIdEqualTo(jobItemId)
                .andDeletedEqualTo(0)
                .andJobitemTypeNotEqualTo(JobItemTypeEnum.FUNCTION.getType());
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        if(! CollectionUtils.isEmpty(rdmsCharacters)){
            for(RdmsCharacter character: rdmsCharacters){
                character.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
                character.setSubmitTime(new Date());
                if(rdmsJobItem.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
                    character.setNextNode(rdmsJobItem.getProductManagerId());
                }else{
                    character.setNextNode(rdmsJobItem.getProjectManagerId());
                }
                this.save(character);

                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(character.getId());
                characterProcess.setExecutorId(rdmsJobItem.getExecutorId());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("提交组件定义表");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
                characterProcess.setNextNode(character.getNextNode());
                rdmsCharacterProcessService.save(characterProcess);

                //查看有没有父级character, 如果有, 查看是不是DECOMPOSED 状态, 如果是, 标记aux_status
                if(!ObjectUtils.isEmpty(character.getParent()) ){
                    RdmsCharacter parentCharacter = this.selectByPrimaryKey(character.getParent());
                    if(parentCharacter.getStatus().equals(CharacterStatusEnum.DECOMPOSED.getStatus())){
                        parentCharacter.setAuxStatus(CharacterStatusEnum.HISTORY.getStatus());  //处于history状况后,不在进行资金统计
                        this.save(parentCharacter);

                        RdmsCharacterProcess parentCharacterProcess = new RdmsCharacterProcess();
                        parentCharacterProcess.setCharacterId(character.getParent());
                        parentCharacterProcess.setExecutorId(rdmsJobItem.getExecutorId());
                        long parentCharacterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getParent());
                        parentCharacterProcess.setDeep((int)parentCharacterProcessCount);
                        parentCharacterProcess.setJobDescription("被分解后, 新记录已经提交");
                        parentCharacterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                        parentCharacterProcess.setNextNode(character.getNextNode());
                        rdmsCharacterProcessService.save(parentCharacterProcess);
                    }
                }
            }
        }
    }

    @Transactional
    public void setCharacterListProcessInfoByJobitemId(
            String jobitemId,
            String loginUserId,
            String nextNode,
            CharacterStatusEnum characterStatusEnum,
            CharacterProcessStatusEnum processStatusEnum,
            String description
    ) {
        List<RdmsCharacterDto> characterList = this.getCharacterListByJobitemId(jobitemId, null);
        if(! CollectionUtils.isEmpty(characterList)){
            List<RdmsCharacter> rdmsCharacters = CopyUtil.copyList(characterList, RdmsCharacter.class);
            for(RdmsCharacter character : rdmsCharacters){
                //对Character状态进行处理
                character.setNextNode(nextNode);
                character.setStatus(characterStatusEnum.getStatus());
                String characterId = this.save(character);

                //对Character Process状态进行处理
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterId);
                characterProcess.setExecutorId(loginUserId);
                characterProcess.setNextNode(character.getNextNode());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription(description);
                characterProcess.setProcessStatus(processStatusEnum.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
            }
        }
    }

    /**
     * 批准
     */
    @Transactional
    public String approve(RdmsCharacterProcessDto characterProcessDto) {
        //1. 获取对应的Character
        RdmsCharacter currentOperatingCharacter = this.selectByPrimaryKey(characterProcessDto.getCharacterId());
        if(ObjectUtils.isEmpty(currentOperatingCharacter)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        //补全数据
        if(characterProcessDto.getCharacterBudget().getDevManhour() == null){
            characterProcessDto.getCharacterBudget().setDevManhour(characterProcessDto.getCharacterBudget().getDevManhourApproved());
        }
        if(characterProcessDto.getCharacterBudget().getTestManhour() == null){
            characterProcessDto.getCharacterBudget().setTestManhour(characterProcessDto.getCharacterBudget().getTestManhourApproved());
        }
        if(characterProcessDto.getCharacterBudget().getMaterialFee() == null){
            characterProcessDto.getCharacterBudget().setMaterialFee(characterProcessDto.getCharacterBudget().getMaterialFeeApproved());
        }

        //获取工单的类型
        JobItemTypeEnum jobType = JobItemTypeEnum.getJobItemTypeEnumByType(currentOperatingCharacter.getJobitemType());
        if(ObjectUtils.isEmpty(jobType)){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        switch (jobType){
            case UPDATE:
            case SUGGEST_UPDATE:
            case MERGE:
            case DEMAND:{
                currentOperatingCharacter.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                currentOperatingCharacter.setNextNode(currentOperatingCharacter.getProductManagerId());

                //将计划工时保存到Character记录
                calBudgetService.setCharacterBudget(currentOperatingCharacter,characterProcessDto.getCharacterBudget());
                this.save(currentOperatingCharacter);

                //创建process记录
                RdmsCharacterProcess characterProcess = CopyUtil.copy(characterProcessDto, RdmsCharacterProcess.class);
                //从数据库中将所有这个工单上传的附件都拉出来, 避免由于某种原因遗漏附件.
                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                attachmentDto.setUse(FileGroupingEnum.CHARACTER.getCode());
                attachmentDto.setItemId(currentOperatingCharacter.getId());
                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                String fileIdsStr = JSONObject.toJSONString(fileIds);
                characterProcess.setFileListStr(fileIdsStr);
                long characterProcessCount1 = rdmsCharacterProcessService.getCharacterProcessCount(characterProcess.getCharacterId());
                characterProcess.setDeep((int)characterProcessCount1);
                characterProcess.setExecutorId(characterProcessDto.getLoginUserId());
                characterProcess.setNextNode(currentOperatingCharacter.getNextNode());
                characterProcess.setJobDescription("功能/组件定义审批通过");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.APPROVED.getStatus());
                String characterProcessId = rdmsCharacterProcessService.save(characterProcess);

                //处理工单状态
                //查看工单下的所有Character, 如果所有的Character都已经处于approve或者iterating状态, 则设置工单为完成状态, 否则不做处理
                rdmsJobItemsService.setJobitemInfoAtDemandApproveOrIteDecJobSubmit(currentOperatingCharacter.getJobitemId());

                return CharacterStatusEnum.APPROVED.getStatus();
            }
            case DECOMPOSE:{
                ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(characterProcessDto.getProjectType());
                if(ObjectUtils.isEmpty(projectType)){
                    LOG.info("Error: projectType 输入错误! {}", this.getClass().getName());
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }
                //获取分解前的parentCharacter分解之前的状态, 之前是什么状态, 分解之后还是什么状态
                if(ObjectUtils.isEmpty(currentOperatingCharacter.getParent())){
                    LOG.info("Error: getParentCharacterId 输入错误! {}", this.getClass().getName());
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }

                if(projectType.getType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
                    RdmsCharacter parentCharacter = this.selectByPrimaryKey(currentOperatingCharacter.getParent());
                    if(parentCharacter.getStatus().equals(CharacterStatusEnum.DECOMPOSED.getStatus())){
                        currentOperatingCharacter.setNextNode(currentOperatingCharacter.getProductManagerId());
                        currentOperatingCharacter.setStatus(CharacterStatusEnum.APPROVE.getStatus());
                        calBudgetService.setCharacterBudget(currentOperatingCharacter,characterProcessDto.getCharacterBudget());
                        this.save(currentOperatingCharacter);

                        //创建process记录
                        RdmsCharacterProcess characterProcess = CopyUtil.copy(characterProcessDto, RdmsCharacterProcess.class);
                        //从数据库中将所有这个工单上传的附件都拉出来, 避免由于某种原因遗漏附件.
                        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                        attachmentDto.setUse(FileGroupingEnum.CHARACTER.getCode());
                        attachmentDto.setItemId(currentOperatingCharacter.getId());
                        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                        String fileIdsStr = JSONObject.toJSONString(fileIds);
                        characterProcess.setFileListStr(fileIdsStr);

                        long characterProcessCount1 = rdmsCharacterProcessService.getCharacterProcessCount(characterProcess.getCharacterId());
                        characterProcess.setDeep((int)characterProcessCount1);
                        characterProcess.setExecutorId(characterProcessDto.getLoginUserId());
                        characterProcess.setNextNode(currentOperatingCharacter.getNextNode());
                        characterProcess.setJobDescription("功能/特性审批中");
                        characterProcess.setProcessStatus(CharacterProcessStatusEnum.APPROVE.getStatus());
                        String characterProcessId = rdmsCharacterProcessService.save(characterProcess);
                    }else{
                        LOG.info("Error: parentCharacter.getStatus()! {}", this.getClass().getName());
                        throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
                    }

                    //处理被分解记录的相关信息
                    //根据被分解的记录的ID查询所有分解后的记录, 如果全部都处于setuped状态,则工单完成
                    List<RdmsCharacterDto> childrenCharacterList = this.getCharacterListByJobitemId(currentOperatingCharacter.getJobitemId(), null);
                    boolean allSetupedFlag = true;
                    for(RdmsCharacterDto characterDto: childrenCharacterList){
                        if(!(characterDto.getStatus().equals(CharacterStatusEnum.APPROVE.getStatus())
                                ||characterDto.getStatus().equals(CharacterStatusEnum.APPROVED.getStatus())
                                ||characterDto.getStatus().equals(CharacterStatusEnum.HISTORY.getStatus())
                                ||characterDto.getStatus().equals(CharacterStatusEnum.DECOMPOSED.getStatus())
                                ||characterDto.getStatus().equals(CharacterStatusEnum.ITERATING.getStatus()))
                        ){
                            allSetupedFlag = false;
                            break;
                        }
                    }
                    if(allSetupedFlag){
                        //本条Character对应的jobItem为完成状态
                        RdmsJobItem jobItems = rdmsJobItemsService.selectByPrimaryKey(currentOperatingCharacter.getJobitemId());
                        if(!ObjectUtils.isEmpty(jobItems)){
                            jobItems.setStatus(JobItemStatusEnum.EVALUATE.getStatus());   //通过产品经理的审批, 经过评价后, 工作人员的工单就完成了, 这是产品经理把关的事情, 至于生成的功能需要评审, 是另一个层面的事情.
                            jobItems.setNextNode(currentOperatingCharacter.getProductManagerId());
                            String jobItemId = rdmsJobItemsService.update(jobItems);

                            //创建对应工单的process状态
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItemId);
                            jobItemProcess.setExecutorId(characterProcessDto.getLoginUserId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                            jobItemProcess.setDeep((int)jobItemProcessCount);
                            jobItemProcess.setJobDescription("签审通过,提交质量评价");
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.EVALUATE.getStatus());
                            jobItemProcess.setNextNode(jobItems.getProductManagerId());
                            rdmsJobItemProcessService.save(jobItemProcess);

                            {
                                //将分解出来的Character全部设为完成审批状态
                                for(RdmsCharacterDto characterDto: childrenCharacterList){
                                    if(characterDto.getStatus().equals(CharacterStatusEnum.APPROVE.getStatus())){
                                        characterDto.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                                        String characterID = this.save(CopyUtil.copy(characterDto, RdmsCharacter.class));

                                        //对Character Process状态进行处理
                                        RdmsCharacterProcess rdmsCharacterProcess = new RdmsCharacterProcess();
                                        rdmsCharacterProcess.setCharacterId(characterID);
                                        rdmsCharacterProcess.setExecutorId(characterProcessDto.getLoginUserId());
                                        rdmsCharacterProcess.setNextNode(characterDto.getNextNode());
                                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterID);
                                        rdmsCharacterProcess.setDeep((int)characterProcessCount);
                                        rdmsCharacterProcess.setJobDescription("分解功能完成审批");
                                        rdmsCharacterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                                        rdmsCharacterProcessService.save(rdmsCharacterProcess);
                                    }

                                }
                            }
                        }
                    }
                }

                //处理立项之后的分解任务
                if(projectType.getType().equals(ProjectTypeEnum.DEV_PROJECT.getType())){
                    RdmsCharacter parentCharacter = this.selectByPrimaryKey(currentOperatingCharacter.getParent());
                    if(parentCharacter.getStatus().equals(CharacterStatusEnum.DECOMPOSED.getStatus())){
                        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(currentOperatingCharacter.getSubprojectId());
                        //下一节点应该是系统工程师
                        if(!ObjectUtils.isEmpty(subproject) && !ObjectUtils.isEmpty(subproject.getProjectId())){
                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
                            if(!ObjectUtils.isEmpty(rdmsProject)){
                                currentOperatingCharacter.setNextNode(rdmsProject.getSystemManagerId());  //系统工程师
                            }else{
                                currentOperatingCharacter.setNextNode(rdmsProject.getProductManagerId());  //产品经理
                            }
                        }else{
                            if(!ObjectUtils.isEmpty(subproject)){
                                currentOperatingCharacter.setNextNode(subproject.getProductManagerId());
                            }else{
                                LOG.info("功能组件工作任务审批: {}", this.getClass().getName());
                                throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
                            }
                        }

                        currentOperatingCharacter.setStatus(CharacterStatusEnum.SETUP.getStatus());
                        currentOperatingCharacter.setSetupedTime(new Date());
                        //保存审批后的预算
                        calBudgetService.setCharacterBudget(currentOperatingCharacter,characterProcessDto.getCharacterBudget());
                        this.save(currentOperatingCharacter);

                        //对Character Process状态进行处理
                        RdmsCharacterProcess characterProcess = CopyUtil.copy(characterProcessDto, RdmsCharacterProcess.class);
                        characterProcess.setCharacterId(currentOperatingCharacter.getId());
                        characterProcess.setExecutorId(characterProcessDto.getLoginUserId());
                        characterProcess.setNextNode(currentOperatingCharacter.getNextNode());
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(currentOperatingCharacter.getId());
                        characterProcess.setDeep((int)characterProcessCount);
                        if(characterProcess.getJobDescription() == null){
                            characterProcess.setJobDescription("项目经理审批通过");
                        }
                        characterProcess.setProcessStatus(CharacterProcessStatusEnum.SETUP.getStatus());
                        rdmsCharacterProcessService.save(characterProcess);

                    }else{
                        LOG.info("Error: parentCharacter.getStatus()! {}", this.getClass().getName());
                        throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
                    }

                    RdmsJobItem jobItems = rdmsJobItemsService.selectByPrimaryKey(currentOperatingCharacter.getJobitemId());
                    if(!ObjectUtils.isEmpty(jobItems)) {
                        //判断当前工单是否还有submit的组件定义
                        List<RdmsCharacterDto> decomposeCharacterList = this.getCharacterListByJobitemId(jobItems.getId(), null);
                        int size = (int) decomposeCharacterList.stream().filter(item -> item.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus())).count();
                        if(size == 0){
                            //处理当前工单状态
                            jobItems.setStatus(JobItemStatusEnum.CHA_RECHECK.getStatus());   //通过产品经理的审批, 经过评价后, 工作人员的工单就完成了, 这是产品经理把关的事情, 至于生成的功能需要评审, 是另一个层面的事情.
                            String jobItemId = rdmsJobItemsService.update(jobItems);

                            //创建对应工单的process状态
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItemId);
                            jobItemProcess.setExecutorId(characterProcessDto.getLoginUserId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                            jobItemProcess.setDeep((int)jobItemProcessCount);
                            jobItemProcess.setJobDescription("功能迭代提交系统工程复核");
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.CHA_RECHECK.getStatus());
                            jobItemProcess.setNextNode(jobItems.getExecutorId());
                            rdmsJobItemProcessService.save(jobItemProcess);

                            //创建复核工单
                            RdmsJobItem reCheckJobitem = CopyUtil.copy(jobItems, RdmsJobItem.class);
                            reCheckJobitem.setId(null);
                            reCheckJobitem.setJobSerial(null);
                            reCheckJobitem.setJobName("复核:" + jobItems.getJobName());
                            reCheckJobitem.setTestedJobitemId(jobItems.getId());
                            reCheckJobitem.setCharacterId(jobItems.getCharacterId());  //关键   分解复核工单的CharacterId是被分解的CharacterId, 后续处理通过这个ID去得到全部分解出来的Character;   修订复核工单记载的characterID是修订的原始记录characterID
                            // customerId preProjectId projectId projectManagerId productManagerId  以上通过copy获得
                            reCheckJobitem.setTaskDescription("请对特性/功能的分解进行复核.");
                            reCheckJobitem.setManhour(0.0);
                            reCheckJobitem.setExecutorId(currentOperatingCharacter.getNextNode()); //复核工单的执行人应该是系统工程师, 如果没有系统工程师设置,就是产品经理
                            reCheckJobitem.setNextNode(jobItems.getExecutorId());
                            reCheckJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                            reCheckJobitem.setType(JobItemTypeEnum.CHA_RECHECK.getType());
                            reCheckJobitem.setProjectType(jobItems.getProjectType());
                            String reCheckJobitemId = rdmsJobItemsService.save(reCheckJobitem);

                            //创建对应工单的process状态
                            RdmsJobItemProcess reCheckJobitemIdProcess = new RdmsJobItemProcess();
                            reCheckJobitemIdProcess.setJobItemId(reCheckJobitemId);
                            reCheckJobitemIdProcess.setExecutorId(characterProcessDto.getLoginUserId());
                            long jobItemProcessCount1 = rdmsJobItemProcessService.getJobItemProcessCount(reCheckJobitemId);
                            reCheckJobitemIdProcess.setDeep((int) jobItemProcessCount1);
                            reCheckJobitemIdProcess.setJobDescription("对特性/功能迭代的进行复核");
                            reCheckJobitemIdProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                            reCheckJobitemIdProcess.setNextNode(reCheckJobitem.getExecutorId());
                            rdmsJobItemProcessService.save(reCheckJobitemIdProcess);
                        }
                    }
                }
                return JobItemStatusEnum.CHA_RECHECK.getStatus();
            }
            case ITERATION:{
                ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(characterProcessDto.getProjectType());
                if(ObjectUtils.isEmpty(projectType)){
                    LOG.info("Error: projectType 输入错误! {}", this.getClass().getName());
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }

                if(projectType.getType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
                    //1. 用编辑记录 替换 原记录
                    RdmsCharacter iterationCharacter = DeepCopyUtil.deepCopy(currentOperatingCharacter, RdmsCharacter.class);
                    iterationCharacter.setId(currentOperatingCharacter.getCharacterSerial()); //重要! 覆盖掉原记录, 原记录内容由history记录保留
                    iterationCharacter.setNextNode(iterationCharacter.getProductManagerId());
                    iterationCharacter.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                    iterationCharacter.setJobitemType(JobItemTypeEnum.ITERATION.getType());  //当前的工单是迭代工单

                    //替换为新数据
                    RdmsCharacterData characterData = this.getCharacterData(currentOperatingCharacter.getId());
                    characterData.setId(currentOperatingCharacter.getCharacterSerial());
                    this.saveCharacterData(characterData);

                    //保存审批后的预算
                    calBudgetService.setCharacterBudget(iterationCharacter,characterProcessDto.getCharacterBudget());
                    this.save(iterationCharacter);

                    //创建迭代记录的process记录
                    RdmsCharacterProcess characterProcess = CopyUtil.copy(characterProcessDto, RdmsCharacterProcess.class);
                    //从数据库中将所有这个工单上传的附件都拉出来, 避免由于某种原因遗漏附件.
                    RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                    attachmentDto.setUse(FileGroupingEnum.CHARACTER.getCode());
                    attachmentDto.setItemId(currentOperatingCharacter.getId());  //上传附件时
                    List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                    for(RdmsFileDto fileDto : fileDtos){
                        fileDto.setItemId(iterationCharacter.getId());
                        rdmsFileService.update(CopyUtil.copy(fileDto, RdmsFile.class));
                    }
                    List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                    String fileIdsStr = JSONObject.toJSONString(fileIds);
                    characterProcess.setFileListStr(fileIdsStr);

                    long characterProcessCount1 = rdmsCharacterProcessService.getCharacterProcessCount(characterProcess.getCharacterId());
                    characterProcess.setDeep((int)characterProcessCount1);
                    characterProcess.setExecutorId(characterProcessDto.getLoginUserId());
                    characterProcess.setNextNode(iterationCharacter.getNextNode());
                    characterProcess.setJobDescription("功能/特性审批中");
                    characterProcess.setProcessStatus(CharacterProcessStatusEnum.APPROVED.getStatus());
                    String characterProcessId = rdmsCharacterProcessService.save(characterProcess);

                    //2. 将编辑记录 discard
                    {
                        currentOperatingCharacter.setStatus(CharacterStatusEnum.DISCARD.getStatus());
                        currentOperatingCharacter.setParent(null);
                        currentOperatingCharacter.setDeep(null);
                        currentOperatingCharacter.setNextNode(null);
                        this.save(currentOperatingCharacter);

                        RdmsCharacterProcess currentOperatingCharacterProcess = new RdmsCharacterProcess();
                        currentOperatingCharacterProcess.setCharacterId(currentOperatingCharacter.getId());
                        currentOperatingCharacterProcess.setExecutorId(characterProcessDto.getLoginUserId());
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(currentOperatingCharacter.getId());
                        currentOperatingCharacterProcess.setDeep((int)characterProcessCount);
                        currentOperatingCharacterProcess.setJobDescription("修订审批通过");
                        currentOperatingCharacterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                        currentOperatingCharacterProcess.setNextNode(null);
                        rdmsCharacterProcessService.save(currentOperatingCharacterProcess);
                    }

                    //处理工单状态
                    RdmsJobItem jobItems = rdmsJobItemsService.selectByPrimaryKey(iterationCharacter.getJobitemId());
                    if(!ObjectUtils.isEmpty(jobItems)){
                        jobItems.setStatus(JobItemStatusEnum.EVALUATE.getStatus());   //通过产品经理的审批, 经过评价后, 工作人员的工单就完成了, 这是产品经理把关的事情, 至于生成的功能需要评审, 是另一个层面的事情.
                        jobItems.setNextNode(iterationCharacter.getProductManagerId());
                        String jobItemId = rdmsJobItemsService.update(jobItems);

                        //创建对应工单的process状态
                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobItemId);
                        jobItemProcess.setExecutorId(characterProcessDto.getLoginUserId());  // process的执行人是当前操作的用户; 工单本身的执行人是工单具体工作的执行人
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                        jobItemProcess.setDeep((int)jobItemProcessCount);
                        jobItemProcess.setJobDescription("签审通过,提交质量评价");
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.EVALUATE.getStatus());
                        jobItemProcess.setNextNode(jobItems.getExecutorId());
                        rdmsJobItemProcessService.save(jobItemProcess);
                    }
                }

                //立项之后再进行修订
                if(projectType.getType().equals(ProjectTypeEnum.DEV_PROJECT.getType())){
                    {
                        currentOperatingCharacter.setStatus(CharacterStatusEnum.SETUP.getStatus());

                        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(currentOperatingCharacter.getSubprojectId());
                        //下一节点应该是系统工程师
                        if(!ObjectUtils.isEmpty(subproject) && !ObjectUtils.isEmpty(subproject.getProjectId())){
                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
                            if(!ObjectUtils.isEmpty(rdmsProject)){
                                currentOperatingCharacter.setNextNode(rdmsProject.getSystemManagerId());  //系统工程师
                            }else{
                                currentOperatingCharacter.setNextNode(rdmsProject.getProductManagerId());  //产品经理
                            }
                        }else{
                            if(!ObjectUtils.isEmpty(subproject)){
                                currentOperatingCharacter.setNextNode(subproject.getProductManagerId());
                            }else{
                                LOG.info("功能组件工作任务审批: {}", this.getClass().getName());
                                throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
                            }
                        }

                        //保存审批后的预算
                        // 1. 增加character的预算
                        calBudgetService.setCharacterBudget(currentOperatingCharacter,characterProcessDto.getCharacterBudget());
                        String currentCharacterId = this.save(currentOperatingCharacter);

                        RdmsCharacterProcess currentOperatingCharacterProcess = new RdmsCharacterProcess();
                        currentOperatingCharacterProcess.setCharacterId(currentCharacterId);
                        currentOperatingCharacterProcess.setExecutorId(characterProcessDto.getLoginUserId());
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(currentCharacterId);
                        currentOperatingCharacterProcess.setDeep((int)characterProcessCount);
                        currentOperatingCharacterProcess.setJobDescription("修订审批通过");
                        currentOperatingCharacterProcess.setProcessStatus(CharacterProcessStatusEnum.SETUP.getStatus());
                        currentOperatingCharacterProcess.setNextNode(currentOperatingCharacter.getNextNode());
                        rdmsCharacterProcessService.save(currentOperatingCharacterProcess);

                        //创建复核工单
                        RdmsJobItem jobItems = rdmsJobItemsService.selectByPrimaryKey(currentOperatingCharacter.getJobitemId());
                        if(!ObjectUtils.isEmpty(jobItems)) {
                            RdmsJobItem reCheckJobitem = CopyUtil.copy(jobItems, RdmsJobItem.class);
                            reCheckJobitem.setId(null);
                            reCheckJobitem.setJobSerial(null);
                            reCheckJobitem.setJobName("复核:" + jobItems.getJobName());
                            reCheckJobitem.setTestedJobitemId(jobItems.getId());
                            reCheckJobitem.setCharacterId(currentOperatingCharacter.getId());  //关键代码
                            // customerId preProjectId projectId projectManagerId productManagerId  以上通过copy获得
                            reCheckJobitem.setTaskDescription("请对特性/功能的迭代进行复核.");
                            reCheckJobitem.setManhour(0.0);
                            reCheckJobitem.setExecutorId(currentOperatingCharacter.getNextNode());
                            reCheckJobitem.setNextNode(jobItems.getExecutorId());
                            reCheckJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                            reCheckJobitem.setType(JobItemTypeEnum.CHA_RECHECK.getType());
                            reCheckJobitem.setProjectType(jobItems.getProjectType());
                            String jobItemId = rdmsJobItemsService.save(reCheckJobitem);

                            //创建对应工单的process状态
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItemId);
                            jobItemProcess.setExecutorId(characterProcessDto.getLoginUserId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                            jobItemProcess.setDeep((int) jobItemProcessCount);
                            jobItemProcess.setJobDescription("对特性/功能迭代的进行复核");
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                            jobItemProcess.setNextNode(currentOperatingCharacter.getNextNode());
                            rdmsJobItemProcessService.save(jobItemProcess);
                        }

                        //处理当前的任务工单
                        if(!ObjectUtils.isEmpty(jobItems)){
                            jobItems.setStatus(JobItemStatusEnum.CHA_RECHECK.getStatus());   //通过产品经理的审批, 经过评价后, 工作人员的工单就完成了, 这是产品经理把关的事情, 至于生成的功能需要评审, 是另一个层面的事情.
                            String jobItemId = rdmsJobItemsService.update(jobItems);

                            //创建对应工单的process状态
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItemId);
                            jobItemProcess.setExecutorId(characterProcessDto.getLoginUserId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                            jobItemProcess.setDeep((int)jobItemProcessCount);
                            jobItemProcess.setJobDescription("功能迭代提交产品经理复核");
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.CHA_RECHECK.getStatus());
                            jobItemProcess.setNextNode(jobItems.getExecutorId());
                            rdmsJobItemProcessService.save(jobItemProcess);
                        }
                    }
                }
                return JobItemStatusEnum.CHA_RECHECK.getStatus();
            }
            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
    }

    /**
     * 根据projectID查询处于立项申请状态的项目列表
     * @param projectId
     */
    @Transactional
    public List<RdmsCharacterDto> getSetupListByProjectId(String projectId, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        rdmsCharacterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusEqualTo(CharacterStatusEnum.SETUP.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }

    /**
     */
    @Transactional
    public RdmsHmiCharacterDocDto getHmiCharacterDocDto(String characterId, String loginUserId) {
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        RdmsHmiCharacterDocDto hmiCharacterDocDto = CopyUtil.copy(character, RdmsHmiCharacterDocDto.class);
        RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(characterId);
        hmiCharacterDocDto.setFunctionDescription(rdmsCharacterData.getFunctionDescription());

        {//组件定义本身产生的附件文档
            //添加Character的附件
            if(! ObjectUtils.isEmpty(character.getFileListStr())){
                List<String> fileIdList = JSON.parseArray(character.getFileListStr(), String.class);
                if(! CollectionUtils.isEmpty(fileIdList)){
                    List<RdmsFileDto> fileDtos = new ArrayList<>();
                    for(String fileId: fileIdList){
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        //读取访问权限
                        if(!ObjectUtils.isEmpty(fileDto)){
                            //补充填写文件权限信息
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                            if(ObjectUtils.isEmpty(byFileId)){
                                fileDto.setAuthStatus(true);
                            }else{
                                String authIdsStr = byFileId.getAuthIdsStr();
                                if(ObjectUtils.isEmpty(authIdsStr)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                    if(strings.contains(loginUserId)){
                                        fileDto.setAuthStatus(true);
                                    }else{
                                        fileDto.setAuthStatus(false);
                                    }
                                }
                            }
                        }
                        fileDtos.add(fileDto);
                    }
                    hmiCharacterDocDto.setFileList(fileDtos);
                }
            }

            //添加Character的工单附件: 创建这条Character的工单的附件
            RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(character.getJobitemId());
            List<String> jobItemFileIdList = JSON.parseArray(jobItem.getFileListStr(), String.class);
            if(! CollectionUtils.isEmpty(jobItemFileIdList)){
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for(String fileId: jobItemFileIdList){
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }
                    fileDtos.add(fileDto);
                }
                hmiCharacterDocDto.setJobItemFileList(fileDtos);
            }
            //获取工单的Process附件
            List<RdmsJobItemProcess> jobitemProcessList = rdmsJobItemProcessService.getJobProcessListByJobId(jobItem.getId());
            List<String> fileList = new ArrayList<String>();
            for(RdmsJobItemProcess process: jobitemProcessList){
                if(! ObjectUtils.isEmpty(process.getFileListStr()) && process.getFileListStr().length()>6){
                    List<String> stringList = JSON.parseArray(process.getFileListStr(), String.class);
                    fileList.addAll(stringList);
                }
            }
            List<RdmsFileDto> fileDtos = new ArrayList<>();
            if(! CollectionUtils.isEmpty(fileList)){
                for(String fileId: fileList){
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                    fileDtos.add(fileDto);
                }
            }
            hmiCharacterDocDto.setJobItemProcessFileList(fileDtos);
        }

        //获取研发工单信息
        List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getDevelopJobitemsByCharacterId(characterId, loginUserId);
        List<RdmsHmiJobItemDcoDto> rdmsHmiJobItemDcoDtos = CopyUtil.copyList(jobItemDtos, RdmsHmiJobItemDcoDto.class);

        //获取上述工单的测试工单,及交付信息
        if(! CollectionUtils.isEmpty(rdmsHmiJobItemDcoDtos)){
            for(RdmsHmiJobItemDcoDto jobItemDcoDto: rdmsHmiJobItemDcoDtos){
                List<RdmsJobItemDto> testJobItemDtos = rdmsJobItemsService.getJobitemByTestedJobitemId(jobItemDcoDto.getId(), loginUserId, JobItemTypeEnum.TEST);
                List<RdmsHmiJobItemDcoDto> hmiTestJobItemDcoDtos = CopyUtil.copyList(testJobItemDtos, RdmsHmiJobItemDcoDto.class);
                jobItemDcoDto.setTestJobitemList(hmiTestJobItemDcoDtos);
            }
        }
        hmiCharacterDocDto.setJobitemDocList(rdmsHmiJobItemDcoDtos);

        return hmiCharacterDocDto;
    }

    /**
     */
    @Transactional
    public List<RdmsFileDto> getHmiAllAttachmentsOfCharacter(String characterId) {
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        List<RdmsFileDto>  allFilesOfCharacterDto = new ArrayList<>();

        {//组件定义本身产生的附件文档
            //添加Character的附件
            List<String> fileIdList = JSON.parseArray(character.getFileListStr(), String.class);
            if (!CollectionUtils.isEmpty(fileIdList)) {
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String fileId : fileIdList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                    fileDtos.add(fileDto);
                }
                allFilesOfCharacterDto.addAll(fileDtos);
            }

            //产生本Character的工单的全部附件
            List<RdmsFileDto> jobitemAllAttachments = rdmsJobItemsService.getJobitemAllAttachments(character.getJobitemId());
            if (!CollectionUtils.isEmpty(jobitemAllAttachments)) {
                allFilesOfCharacterDto.addAll(jobitemAllAttachments);
            }
        }
        {//获取研发工单信息
            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getAllJobitemSimpleInfoByCharacterId(characterId);
            if(!CollectionUtils.isEmpty(jobItemDtos)){
                for(RdmsJobItemDto jobItemDto: jobItemDtos){
                    List<RdmsFileDto> jobitemAllAttachments = rdmsJobItemsService.getJobitemAllAttachments(jobItemDto.getId());
                    if (!CollectionUtils.isEmpty(jobitemAllAttachments)) {
                        allFilesOfCharacterDto.addAll(jobitemAllAttachments);
                    }
                }
            }
        }

        return allFilesOfCharacterDto.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 通过怕热subprojectId查询 "已审批" 清单
     * @param subprojectId
     */
/*    @Transactional
    public List<RdmsCharacterDto> listCharactersBySubprojectId(String subprojectId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }*/

    /**
     * 根据parentID查询characterList
     * @param parentCharacterId
     */
    @Transactional
    public List<RdmsCharacterDto> getCharacterListByParentId(String parentCharacterId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andParentEqualTo(parentCharacterId)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListBySubprojectIdAndNextNodeId(String subprojectId, String nextNodeId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andNextNodeEqualTo(nextNodeId)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        return rdmsCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacter> getIterationVersionList(String characterId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andCharacterSerialEqualTo(characterId)
                .andStatusNotEqualTo(CharacterStatusEnum.DISCARD.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        if(!CollectionUtils.isEmpty(rdmsCharacters)){
            List<RdmsCharacter> rdmsCharacterList = rdmsCharacters.stream().filter(item -> !item.getId().equals(characterId)).collect(Collectors.toList());
            return rdmsCharacterList;
        }else{
            return null;
        }
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByJobitemIdAndStatusList(String jobitemId, List<CharacterStatusEnum> statusEnumList){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andDeletedEqualTo(0)
                .andAuxStatusNotEqualTo(CharacterStatusEnum.HISTORY.getStatus())
                .andJobitemIdEqualTo(jobitemId);

        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(CharacterStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        List<RdmsCharacterDto> characterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        if(!CollectionUtils.isEmpty(characterDtos)){
            for(RdmsCharacterDto characterDto : characterDtos){
                if(!ObjectUtils.isEmpty(characterDto.getModuleIdListStr())){
                    List<String> characterIdList = JSON.parseArray(characterDto.getModuleIdListStr(), String.class);
                    if(!CollectionUtils.isEmpty(characterIdList)){
                        List<RdmsCharacterDto> referenceCharacterList = this.getCharacterListByIdList(characterDto.getCustomerId(), characterIdList);
                        characterDto.setReferenceCharacterList(referenceCharacterList);
                    }
                }
            }
        }

        return characterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByJobitemIdAndStatusTypeList(String jobitemId, List<CharacterStatusEnum> characterStatusEnumList, List<JobItemTypeEnum> jobItemTypeEnumList){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria().andDeletedEqualTo(0).andJobitemIdEqualTo(jobitemId);

        if(! CollectionUtils.isEmpty(characterStatusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(CharacterStatusEnum statusEnum: characterStatusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        if(! CollectionUtils.isEmpty(jobItemTypeEnumList)){
            List<String> typeList = new ArrayList<>();
            for(JobItemTypeEnum typeEnum: jobItemTypeEnumList){
                typeList.add(typeEnum.getType());
            }
            criteria.andJobitemTypeIn(typeList);
        }

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterByPreProjectIdAndStatus(String preProjectId, List<CharacterStatusEnum> statusEnumList){
        List<RdmsCharacterDto> characterList = new ArrayList<>();
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria().andDeletedEqualTo(0).andPreProjectIdEqualTo(preProjectId);

        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(CharacterStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getDecomposedChildrenCharacters(String parentCharacterId, String status, String loginUserId) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andParentEqualTo(parentCharacterId)
                .andStatusEqualTo(status)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setLoginUserId(loginUserId);
            this.appendRecordSimpleInfo(characterDto);
        }
        return rdmsProductCharacterDtos;
    }

    /**
     */
    @Transactional
    public void getDevelopingCharacterList(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        if(subproject.getProjectManagerId().equals(pageDto.getActor())) {
            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusEqualTo(CharacterStatusEnum.DEVELOPING.getStatus())
                    .andDeletedEqualTo(0);

            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), JobItemStatusEnum.HANDLING.getStatus(), jobitemTypeList);
                    for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                        jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                        jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                    }
                    characterDto.setChildren(jobItemDtos);
                    characterDto.setShowFlag("character");
                    characterDto.setIndex(index += 1);
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                    characterDto.setProductManagerName(customerUser.getTrueName());
                    characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(rdmsCharacterDtos);
        }
    }

    /**
     */
    @Transactional
    public void getCharacterListAndAllDevelopJobitem(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        if(subproject.getProjectManagerId().equals(pageDto.getActor())) {
            List<String> statusList = new ArrayList<>();
            statusList.add(CharacterStatusEnum.DEVELOPING.getStatus());
            statusList.add(CharacterStatusEnum.INTEGRATION.getStatus());
            statusList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());
            statusList.add(CharacterStatusEnum.REVIEW.getStatus());

            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusIn(statusList)
                    .andDeletedEqualTo(0);

            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
                jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
                jobitemTypeList.add(JobItemTypeEnum.REVIEW);
                jobitemTypeList.add(JobItemTypeEnum.REVIEW_NOTIFY);
                jobitemTypeList.add(JobItemTypeEnum.TASK);

                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), null, jobitemTypeList);
                    for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                        jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                        jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                        if(jobItemDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())){
                            characterDto.setHasIntJobitem(true); //已经发集成工单了
                        }
                    }
                    characterDto.setChildren(jobItemDtos);
                    characterDto.setShowFlag("character");
                    characterDto.setIndex(index += 1);
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                    characterDto.setProductManagerName(customerUser.getTrueName());
                    characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(rdmsCharacterDtos);
        }
    }

    /**
     */
    @Transactional
    public RdmsCharacterDto getCharacterAndJobitemList(String characterId) {

        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto rdmsCharacterDto = CopyUtil.copy(rdmsCharacter, RdmsCharacterDto.class);

        if (!ObjectUtils.isEmpty(rdmsCharacterDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
            jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
            jobitemTypeList.add(JobItemTypeEnum.REVIEW);
            jobitemTypeList.add(JobItemTypeEnum.REVIEW_NOTIFY);
            jobitemTypeList.add(JobItemTypeEnum.TASK);

            List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
            jobItemStatusList.add(JobItemStatusEnum.HANDLING);
            jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
            jobItemStatusList.add(JobItemStatusEnum.TESTING);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, jobItemStatusList, jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                if (jobItemDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                    rdmsCharacterDto.setHasIntJobitem(true); //已经发集成工单了
                }
                jobItemDto.setIndex(index++);
            }
            rdmsCharacterDto.setChildren(jobItemDtos);
            rdmsCharacterDto.setItemName(rdmsCharacterDto.getCharacterName());  //为了统一前端字段 名称
            rdmsCharacterDto.setDocType(DocTypeEnum.CHARACTER.getType());
            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCharacterDto.getProductManagerId());
            rdmsCharacterDto.setProductManagerName(customerUser.getTrueName());
        }
        return rdmsCharacterDto;
    }

    @Transactional
    public RdmsCharacterDto getCharacterAndTestTaskJobitemList(String characterId) {

        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto rdmsCharacterDto = CopyUtil.copy(rdmsCharacter, RdmsCharacterDto.class);

        if (!ObjectUtils.isEmpty(rdmsCharacterDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.TASK_TEST);

            List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
            jobItemStatusList.add(JobItemStatusEnum.HANDLING);
            jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
            jobItemStatusList.add(JobItemStatusEnum.TESTING);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, jobItemStatusList, jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                jobItemDto.setIndex(index++);
            }
            rdmsCharacterDto.setChildren(jobItemDtos);
            rdmsCharacterDto.setItemName(rdmsCharacterDto.getCharacterName());  //为了统一前端字段 名称
            rdmsCharacterDto.setDocType(DocTypeEnum.CHARACTER.getType());
            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCharacterDto.getProductManagerId());
            rdmsCharacterDto.setProductManagerName(customerUser.getTrueName());
        }
        return rdmsCharacterDto;
    }

    @Transactional
    public RdmsHmiJobItemDocPageDto getLastReviewInfo(String characterId, String loginUserId) {
        RdmsHmiJobItemDocPageDto reviewJobInfo = new RdmsHmiJobItemDocPageDto();
        List<RdmsJobItem> jobitemList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusAndJobType(characterId, JobItemStatusEnum.ARCHIVED.getStatus(), JobItemTypeEnum.REVIEW.getType());
        if(!CollectionUtils.isEmpty(jobitemList)){
            RdmsJobItem reviewInfo = jobitemList.get(0);
            //private String id;
            reviewJobInfo.setId(reviewInfo.getId());
            //private String jobSerial;
            reviewJobInfo.setJobSerial(reviewInfo.getJobSerial());
            //private String jobName;
            reviewJobInfo.setJobName(reviewInfo.getJobName());
            //private String executorId;
            reviewJobInfo.setExecutorId(reviewInfo.getExecutorId());
            //private String executorName;
            reviewJobInfo.setExecutorName(rdmsCustomerUserService.selectByPrimaryKey(reviewInfo.getExecutorId()).getTrueName());
            //private String nextNodeId;
            reviewJobInfo.setNextNodeId(reviewInfo.getProjectManagerId());
            //private String nextNodeName;
            reviewJobInfo.setNextNodeName(rdmsCustomerUserService.selectByPrimaryKey(reviewInfo.getProjectManagerId()).getTrueName());
            //private String status;
            reviewJobInfo.setStatus(reviewInfo.getStatus());
            //private String type;
            reviewJobInfo.setType(reviewInfo.getType());
            //private String taskDescription;
            reviewJobInfo.setTaskDescription(reviewInfo.getTaskDescription());
            //private Double manhour;
            reviewJobInfo.setManhour(0.0);
            //private List<RdmsFileDto> taskFileList;  //任务附件
            if(! ObjectUtils.isEmpty(reviewInfo.getFileListStr()) && reviewInfo.getFileListStr().length()>6){
                List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(reviewInfo.getFileListStr(), loginUserId);
                reviewJobInfo.setTaskFileList(fileList);
            }
            //private String evaluateDesc;
            reviewJobInfo.setEvaluateDesc(reviewInfo.getEvaluateDesc());
            //private Integer evaluateScore;
            reviewJobInfo.setEvaluateScore(reviewInfo.getEvaluateScore());
            //private String submitDescription;
            List<RdmsJobItemProperty> jobItemProperty = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(reviewInfo.getId());
            if(! CollectionUtils.isEmpty(jobItemProperty)){
                reviewJobInfo.setSubmitDescription(jobItemProperty.get(0).getJobDescription());
                //private List<RdmsFileDto> submitFileList;  //提交附件
                if(! ObjectUtils.isEmpty(jobItemProperty.get(0).getFileListStr()) && jobItemProperty.get(0).getFileListStr().length()>6){
                    List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(jobItemProperty.get(0).getFileListStr(), loginUserId);
                    reviewJobInfo.setSubmitFileList(fileList);
                }
            }
        }
        return reviewJobInfo;
    }

    @Transactional
    public RdmsCharacter getIterationOriginRecord(String newCharacterId) {
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(newCharacterId);
        RdmsCharacter originRecord = this.selectByPrimaryKey(rdmsCharacter.getCharacterSerial());
        return originRecord;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByPjmByMmStatus(String pjmId, CharacterStatusEnum mmStatus) {
        List<RdmsProjectSubprojectDto> subProjectListByPjm = rdmsSubprojectService.getSubProjectListByPjm(pjmId);
        List<RdmsCharacterDto> characterDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(subProjectListByPjm)){
            List<String> stringList = subProjectListByPjm.stream().map(RdmsProjectSubprojectDto::getId).collect(Collectors.toList());
            if(! CollectionUtils.isEmpty(stringList)){
                for(String subprojectId: stringList){
                    List<RdmsCharacterDto> characterList = this.getCharacterListBySubprojectIdAndMmStatus(subprojectId, mmStatus);
                    characterDtoList.addAll(characterList);
                }
            }
        }

//        List<RdmsCharacterDto> collect = characterDtoList.stream().filter(item -> item.getMaterialFeeApproved().doubleValue() > 0).collect(Collectors.toList());
        return characterDtoList;
    }

    /**
     */
    @Transactional
    public RdmsCharacterDto getCharacterAndSubmitJobitems(String characterId) {

        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto rdmsCharacterDto = CopyUtil.copy(rdmsCharacter, RdmsCharacterDto.class);
        rdmsCharacterDto.setHasIntJobitem(false);
        rdmsCharacterDto.setItemName(rdmsCharacterDto.getCharacterName());  //为了统一前端字段 名称
        rdmsCharacterDto.setDocType(DocTypeEnum.CHARACTER.getType());
        RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCharacterDto.getProductManagerId());
        rdmsCharacterDto.setProductManagerName(customerUser.getTrueName());

        if (!ObjectUtils.isEmpty(rdmsCharacterDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
            jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
            jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                if (jobItemDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                    rdmsCharacterDto.setHasIntJobitem(true); //已经发集成工单了
                }
                jobItemDto.setIndex(index++);
            }
            rdmsCharacterDto.setChildren(jobItemDtos);
        }
        return rdmsCharacterDto;
    }

    @Transactional
    public RdmsCharacterDto getCharacterAndTestJobSubmitJobitems(String characterId) {

        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto rdmsCharacterDto = CopyUtil.copy(rdmsCharacter, RdmsCharacterDto.class);
        rdmsCharacterDto.setHasIntJobitem(false);
        rdmsCharacterDto.setItemName(rdmsCharacterDto.getCharacterName());  //为了统一前端字段 名称
        rdmsCharacterDto.setDocType(DocTypeEnum.CHARACTER.getType());
        RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsCharacterDto.getProductManagerId());
        rdmsCharacterDto.setProductManagerName(customerUser.getTrueName());

        if (!ObjectUtils.isEmpty(rdmsCharacterDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.TASK_TEST);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                if (jobItemDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                    rdmsCharacterDto.setHasIntJobitem(true); //已经发集成工单了
                }
                jobItemDto.setIndex(index++);
            }
            rdmsCharacterDto.setChildren(jobItemDtos);
        }
        return rdmsCharacterDto;
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getCharacterListAndAllTestJobitem(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        if(subproject.getTestManagerId().equals(pageDto.getActor())) {
            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusEqualTo(CharacterStatusEnum.DEVELOPING.getStatus())
                    .andDeletedEqualTo(0);

            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.TEST);
                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), JobItemStatusEnum.HANDLING.getStatus(), jobitemTypeList);
                    for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                        jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                        jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                    }
                    characterDto.setChildren(jobItemDtos);
                    characterDto.setShowFlag("character");
                    characterDto.setIndex(index += 1);
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                    characterDto.setProductManagerName(customerUser.getTrueName());
                    characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(rdmsCharacterDtos);
        }
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getCharacterListAndAllSubmitTestJobitem(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        if(subproject.getTestManagerId().equals(pageDto.getActor())) {
            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusEqualTo(CharacterStatusEnum.DEVELOPING.getStatus())
                    .andDeletedEqualTo(0);

            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.TEST);
                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);
                    for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                        jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                        jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                    }
                    characterDto.setChildren(jobItemDtos);
                    characterDto.setShowFlag("character");
                    characterDto.setIndex(index += 1);
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                    characterDto.setProductManagerName(customerUser.getTrueName());
                    characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(rdmsCharacterDtos);
        }
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getEvaluateJobitemList(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        if(subproject.getProjectManagerId().equals(pageDto.getActor())) {
            List<String> statusList = new ArrayList<>();
            statusList.add(CharacterStatusEnum.DEVELOPING.getStatus());
            statusList.add(CharacterStatusEnum.INTEGRATION.getStatus());
            statusList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());

            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusIn(statusList)
                    .andDeletedEqualTo(0);
            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
                jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
                jobitemTypeList.add(JobItemTypeEnum.TASK);

                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);
                    for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                        jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                        jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                    }
                    characterDto.setChildren(jobItemDtos);
                    characterDto.setShowFlag("character");
                    characterDto.setIndex(index += 1);
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                    characterDto.setProductManagerName(customerUser.getTrueName());
                    characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(rdmsCharacterDtos);
        }
    }

    /**
     */
    @Transactional
    public void getTestingJobitemList(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.DEVELOPING.getStatus());
        statusList.add(CharacterStatusEnum.INTEGRATION.getStatus());
        statusList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());

        if(subproject.getProjectManagerId().equals(pageDto.getActor())) {
            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusIn(statusList)
                    .andDeletedEqualTo(0);
            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            List<RdmsCharacterDto> characterDtos = new ArrayList<>();
            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
                jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
                jobitemTypeList.add(JobItemTypeEnum.TASK);

                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), JobItemStatusEnum.TESTING.getStatus(),jobitemTypeList);
                    if(! CollectionUtils.isEmpty(jobItemDtos)){
                        for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                            jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                            jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                        }
                    }

                    if(!CollectionUtils.isEmpty(jobItemDtos)){
                        characterDto.setChildren(jobItemDtos);
                        characterDto.setShowFlag("character");
                        characterDto.setIndex(index += 1);
                        RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                        characterDto.setProductManagerName(customerUser.getTrueName());
                        characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                        characterDto.setDocType(DocTypeEnum.CHARACTER.getType());

                        characterDtos.add(characterDto);
                    }
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(characterDtos);
        }
    }

    /**
     */
    @Transactional
    public void getCharacterIntegrationJobList(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        if(subproject.getProjectManagerId().equals(pageDto.getActor())) {
            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusEqualTo(CharacterStatusEnum.INTEGRATION.getStatus())
                    .andDeletedEqualTo(0);
            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            List<RdmsCharacterDto> characterDtos = new ArrayList<>();
            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    characterDto.setShowFlag("character");
                    characterDto.setIndex(index += 1);
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                    characterDto.setProductManagerName(customerUser.getTrueName());
                    characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());

                    characterDtos.add(characterDto);
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(characterDtos);
        }
    }

    /**
     */
    @Transactional
    public RdmsHmiCharacterIntOriginDto getCharacterIntSubmitOriginInfo(String characterId) {
        RdmsHmiCharacterIntOriginDto plantInfoDto = new RdmsHmiCharacterIntOriginDto();
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto characterDto = CopyUtil.copy(character, RdmsCharacterDto.class);
        if(! ObjectUtils.isEmpty(characterDto.getFileListStr())){
            List<String> stringList = JSON.parseArray(characterDto.getFileListStr(), String.class);
            for(String fileId : stringList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                characterDto.getFileList().add(fileDto);
            }
        }
        //添加工单附件
        RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(characterDto.getJobitemId());
        List<String> stringList = JSON.parseArray(jobItem.getFileListStr(), String.class);
        for(String fileId : stringList){
            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
            characterDto.getJobItemFileList().add(fileDto);
        }
        plantInfoDto.setCharacter(characterDto);


        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.HANDLING.getStatus(), jobitemTypeList);
        if(! CollectionUtils.isEmpty(jobitemList)){
            RdmsJobItemDto jobItemDto = jobitemList.get(0);
            plantInfoDto.setIntJobitem(jobItemDto);

            List<RdmsJobItemDto> assistJobitemList = rdmsJobItemsService.getJobitemListByJobitemIdAndStatus(jobitemList.get(0).getId(), JobItemStatusEnum.COMPLETED.getStatus(), JobItemTypeEnum.ASSIST.getType());
            if(! CollectionUtils.isEmpty(assistJobitemList)){
                plantInfoDto.setAssistJobitemList(assistJobitemList);
            }
        }
        return plantInfoDto;
    }

    @Transactional
    public RdmsJobItemDto getIntegrationJobitemInfo(String characterId) {
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterId, null, jobitemTypeList);
        if(! CollectionUtils.isEmpty(jobitemList)){
            return jobitemList.get(0);
        }
        return null;
    }

    /**
     */
    @Transactional
    public RdmsHmiCharacterIntOriginDto getCharacterIntReviewOriginInfo(String characterId) {
        RdmsHmiCharacterIntOriginDto plantInfoDto = new RdmsHmiCharacterIntOriginDto();
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto characterDto = CopyUtil.copy(character, RdmsCharacterDto.class);
        if(! ObjectUtils.isEmpty(characterDto) && ! ObjectUtils.isEmpty(characterDto.getFileListStr())){
            List<String> stringList = JSON.parseArray(characterDto.getFileListStr(), String.class);
            for(String fileId : stringList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                characterDto.getFileList().add(fileDto);
            }
        }
        //添加工单附件
        RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(characterDto.getJobitemId());
        List<String> stringList = JSON.parseArray(jobItem.getFileListStr(), String.class);
        for(String fileId : stringList){
            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
            characterDto.getJobItemFileList().add(fileDto);
        }
        plantInfoDto.setCharacter(characterDto);

        //集成工单信息
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), jobitemTypeList);
        if(! CollectionUtils.isEmpty(jobitemList)){
            RdmsJobItemDto jobItemDto = jobitemList.get(0);
            plantInfoDto.setIntJobitem(jobItemDto);

            List<RdmsJobItemDto> assistJobitemList = rdmsJobItemsService.getJobitemListByJobitemIdAndStatus(jobitemList.get(0).getId(), JobItemStatusEnum.COMPLETED.getStatus(), JobItemTypeEnum.ASSIST.getType());
            if(! CollectionUtils.isEmpty(assistJobitemList)){
                plantInfoDto.setAssistJobitemList(assistJobitemList);
            }
        }
        return plantInfoDto;
    }

    /**
     */
    @Transactional
    public List<RdmsFileDto> getHmiAllAttachmentsOfIntCharacter(String characterId) {
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
//        jobitemTypeList.add(JobItemTypeEnum.TEST);
//        jobitemTypeList.add(JobItemTypeEnum.ASSIST);
//        jobitemTypeList.add(JobItemTypeEnum.BUG);
        //只需列出一级工单即可
        jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, jobItemStatusList, jobitemTypeList);

        //将未完成的集成工单加进去
        List<JobItemTypeEnum> jobitemTypeList2 = new ArrayList<>();
        jobitemTypeList2.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList2 = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, null, jobitemTypeList2);

        //汇总需要查询的工单
        jobitemList.addAll(jobitemList2);

        List<String> fileIdList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(jobitemList)){
            for(RdmsJobItemDto jobItemDto: jobitemList){
                List<String> jobitemAllRelatedDocIdList = rdmsDocService.getJobitemAllRelatedDocIdList(jobItemDto.getId());
                fileIdList.addAll(jobitemAllRelatedDocIdList);
            }
        }
        HashSet<String> objects = new HashSet<>(fileIdList);
        List<String> list = new ArrayList<>(objects);
        List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdList(list);
        return fileList;
    }

    /**
     * TODO 需要检查
     */
    @Transactional
    public void getDevelopCharacterAndJobitemListByMyCharacter(PageDto<RdmsCharacterDto> pageDto, String jobitemStatus /*JobItemStatusEnum.SUBMIT.getStatus()*/, List<JobItemTypeEnum> jobitemTypeList) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<RdmsCharacterDto> characterDtos = new ArrayList<>();
        //只有Character存在对应的工单时才会有记录返回
        List<String> jobitemTpyes = new ArrayList<>();
        if(! CollectionUtils.isEmpty(jobitemTypeList)){
            for(JobItemTypeEnum type : jobitemTypeList){
                jobitemTpyes.add(type.getType());
            }
        }
        List<String> characterStatusList = new ArrayList<>();
        characterStatusList.add(CharacterStatusEnum.DEVELOPING.getStatus());
        characterStatusList.add(CharacterStatusEnum.INTEGRATION.getStatus());
        characterStatusList.add(CharacterStatusEnum.DEV_COMPLETE.getStatus());
        List<MyCharacter> characterUnionJobitemList = myCharacterMapper.getCharacterUnionJobitemListByTypeList(
                pageDto.getKeyWord(),
                characterStatusList,
                jobitemStatus,
                jobitemTpyes);

        Map<String, List<MyCharacter>> stringListMap = characterUnionJobitemList.stream().collect(Collectors.groupingBy(MyCharacter::getId));
        if(! CollectionUtils.isEmpty(stringListMap)){
            int index = 0;

            for(String id : stringListMap.keySet()){
                RdmsCharacter character = rdmsCharacterMapper.selectByPrimaryKey(id);
                RdmsCharacterDto characterDto = CopyUtil.copy(character, RdmsCharacterDto.class);

                List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), jobitemStatus, jobitemTypeList);
                if (!CollectionUtils.isEmpty(jobItemDtos)) {
                    for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                        jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                        jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                    }
                }
                characterDto.setChildren(jobItemDtos);
                characterDto.setShowFlag("character");
                characterDto.setIndex(index += 1);
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                characterDto.setProductManagerName(customerUser.getTrueName());
                characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                characterDto.setDocType(DocTypeEnum.CHARACTER.getType());

                characterDtos.add(characterDto);
            }
            PageInfo<RdmsCharacterDto> pageInfo = new PageInfo<>(characterDtos);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(characterDtos);
        }else{
            PageInfo<RdmsCharacterDto> pageInfo = new PageInfo<>(characterDtos);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(characterDtos);
        }
    }

    /**
     */
    @Transactional
    public void getApprovedJobitemList(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time asc");
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(pageDto.getKeyWord());
        if(subproject.getProjectManagerId().equals(pageDto.getActor())) {
            rdmsCharacterExample.createCriteria()
                    .andSubprojectIdEqualTo(pageDto.getKeyWord())
                    .andStatusEqualTo(CharacterStatusEnum.DEVELOPING.getStatus())
                    .andDeletedEqualTo(0);
            List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
            List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
            List<RdmsCharacterDto> characterDtos = new ArrayList<>();

            if (!CollectionUtils.isEmpty(rdmsCharacterDtos)) {
                int index = 0;
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
                for (RdmsCharacterDto characterDto : rdmsCharacterDtos) {
                    List<RdmsJobItemDto> jobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), JobItemStatusEnum.APPROVED.getStatus(), jobitemTypeList);
                    if(! CollectionUtils.isEmpty(jobItemDtos)){
                        for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                            jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                            jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                        }
                        characterDto.setChildren(jobItemDtos);
                        characterDto.setShowFlag("character");
                        characterDto.setIndex(index += 1);
                        RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
                        characterDto.setProductManagerName(customerUser.getTrueName());
                        characterDto.setItemName(characterDto.getCharacterName());  //为了统一前端字段 名称
                        characterDto.setDocType(DocTypeEnum.CHARACTER.getType());

                        characterDtos.add(characterDto);
                    }
                }
            }
            PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
            pageDto.setTotal(pageInfo.getTotal());
            pageDto.setList(characterDtos);
        }
    }

    /**
     * 根据parentID查询characterList
     * @param jobItemId
     */
    @Transactional
    public List<RdmsCharacterDto> getCharacterListByJobItemIdAndStatus(String jobItemId, String status) {
        if(!ObjectUtils.isEmpty(status)){
            CharacterStatusEnum characterEnumByStatus = CharacterStatusEnum.getCharacterEnumByStatus(status);
            if(ObjectUtils.isEmpty(characterEnumByStatus)){
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }

        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = rdmsCharacterExample.createCriteria()
                .andJobitemIdEqualTo(jobItemId)
                .andDeletedEqualTo(0);
        if(!ObjectUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }

        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            characterDto.setCreateTimeStr(sdf.format(characterDto.getCreateTime()));
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
            if(rdmsCustomerUser1 != null){
                characterDto.setProductManagerName(rdmsCustomerUser1.getTrueName());
            }

            String attachmentListStr = characterDto.getFileListStr();
            if(attachmentListStr != null){
                List<String> attachmentIdList = JSON.parseArray(attachmentListStr, String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                if(! CollectionUtils.isEmpty(attachmentIdList)){
                    for(String id : attachmentIdList){
                        RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(id);
                        RdmsFileDto fileDto = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
                        fileDtos.add(fileDto);
                    }
                    characterDto.setFileList(fileDtos);
                }
            }
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(characterDto.getCustomerId());
            characterDto.setCustomerName(rdmsCustomer.getCustomerName());

            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getWriterId());
            if(rdmsCustomerUser2 != null){
                characterDto.setWriterName(rdmsCustomerUser2.getTrueName());
            }
        }
        return rdmsProductCharacterDtos;
    }

    /**
     *
     */
    @Transactional
    public RdmsCharacterDto getIterationCharacterByJobitemId(String jobItemId, String loginUserId) {
        //1. 通过修订工单jobItem记录的characterID 和 iteration 状态 以及 sava状态 去查出迭代的记录
        //2. 如果没有则创建; 如果有了,则读取出来

        RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(jobItemId);
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.ITERATION.getType());
        typeList.add(JobItemTypeEnum.UPDATE.getType());
        typeList.add(JobItemTypeEnum.SUGGEST_UPDATE.getType());
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andJobitemIdEqualTo(jobItemId)
                .andJobitemTypeIn(typeList)
                .andDeletedEqualTo(0);
        if(jobItem.getStatus().equals(JobItemStatusEnum.CHA_RECHECK.getStatus())){
            criteria.andStatusEqualTo(CharacterStatusEnum.SETUP.getStatus());
        }else{
            criteria.andStatusEqualTo(CharacterStatusEnum.SAVED.getStatus());
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        if(CollectionUtils.isEmpty(rdmsCharacters)){
            LOG.error("函数: getIterationCharacterByJobitemId {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }

        RdmsCharacterDto characterDto = CopyUtil.copy(rdmsCharacters.get(0), RdmsCharacterDto.class);
        if (!ObjectUtils.isEmpty(characterDto)) {
            characterDto.setLoginUserId(loginUserId);
            this.appendCharacterRecordAndDataInfo(characterDto);
            //根据characterID查出所有工单附件列表
            String fileListStr = jobItem.getFileListStr();
            List<String> fileList = JSON.parseArray(fileListStr, String.class);
            if(! CollectionUtils.isEmpty(fileList) && !fileListStr.equals("[]")){
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : fileList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                characterDto.setJobItemFileList(fileDtos);
            }
        }
        return characterDto;
    }

    /**
     *
     */
    @Transactional
    public RdmsCharacterDto getMergedCharacterByJobitemId(String jobItemId, String loginUserId) {
        //1. 通过修订工单jobItem记录的characterID 和 iteration 状态 以及 sava状态 去查出迭代的记录
        //2. 如果没有则创建; 如果有了,则读取出来

        RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(jobItemId);
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andJobitemIdEqualTo(jobItemId)
                .andJobitemTypeEqualTo(JobItemTypeEnum.MERGE.getType())
                .andStatusEqualTo(CharacterStatusEnum.SAVED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        if(CollectionUtils.isEmpty(rdmsCharacters)){
            LOG.error("函数: getIterationCharacterByJobitemId {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        RdmsCharacterDto characterDto = CopyUtil.copy(rdmsCharacters.get(0), RdmsCharacterDto.class);
        if (!ObjectUtils.isEmpty(characterDto)) {
            characterDto.setLoginUserId(loginUserId);
            this.appendCharacterRecordAndDataInfo(characterDto);
            //根据characterID查出所有工单附件列表
            String fileListStr = jobItem.getFileListStr();
            List<String> fileList = JSON.parseArray(fileListStr, String.class);
            if(! CollectionUtils.isEmpty(fileList) && !fileListStr.equals("[]")){
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : fileList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                characterDto.setJobItemFileList(fileDtos);
            }
        }
        return characterDto;
    }

    /**
     *
     */
    @Transactional
    public List<MyCharacter> getMergedCharacter(String characterId) {

        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        List<String> mergedCharacterIdList = JSON.parseArray(rdmsCharacter.getMergedCharacterIdStr(), String.class);
        if(!CollectionUtils.isEmpty(mergedCharacterIdList)){
            List<MyCharacter> characterList = myCharacterMapper.getCharacterListByIdList(rdmsCharacter.getCustomerId(),mergedCharacterIdList);
            return characterList;
        }
        return null;
    }

    @Transactional
    public RdmsHmiCharacterDocPageInfoDto getCharacterDocPageInfo(String characterId, String loginUserId) {
        RdmsHmiCharacterDocPageInfoDto characterDocPageDto = new RdmsHmiCharacterDocPageInfoDto();
        RdmsCharacterDto characterDto = this.getCharacterRecordDetailById(characterId, loginUserId);

        //private String id;
        characterDocPageDto.setId(characterDto.getId());
        //private String characterName;
        characterDocPageDto.setCharacterName(characterDto.getCharacterName());
        //private String customerId;
        characterDocPageDto.setCustomerId(characterDto.getCustomerId());
        //private String projectName;
        if(!ObjectUtils.isEmpty(characterDto.getProjectId())){
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(characterDto.getProjectId());
            if(!ObjectUtils.isEmpty(rdmsProject)){
                characterDocPageDto.setProjectName(rdmsProject.getProjectName());
                //private String productManagerName;
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                    characterDocPageDto.setProductManagerName(rdmsCustomerUser.getTrueName());
                }
            }
        }
        if(!ObjectUtils.isEmpty(characterDto.getSubprojectId())){
            //private String subprojectName;
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(characterDto.getSubprojectId());
            if(!ObjectUtils.isEmpty(subproject)){
                characterDocPageDto.setSubprojectName(subproject.getLabel());
                RdmsCustomerUser projectManager = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
                if(!ObjectUtils.isEmpty(projectManager)){
                    characterDocPageDto.setProjectManagerName(projectManager.getTrueName());
                }
            }
        }

        //private String writerName;
        RdmsCustomerUser writer = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getWriterId());
        if(!ObjectUtils.isEmpty(writer)){
            characterDocPageDto.setWriterName(writer.getTrueName());
        }
        //private String functionDescription;
        characterDocPageDto.setFunctionDescription(characterDto.getFunctionDescription());
        //private String workCondition;
        characterDocPageDto.setWorkCondition(characterDto.getWorkCondition());
        //private String inputLogicalDesc;
        characterDocPageDto.setInputLogicalDesc(characterDto.getInputLogicalDesc());
        //private String functionLogicalDesc;
        characterDocPageDto.setFunctionLogicalDesc(characterDto.getFunctionLogicalDesc());
        //private String outputLogicalDesc;
        characterDocPageDto.setOutputLogicalDesc(characterDto.getOutputLogicalDesc());
        //private String testMethod;
        characterDocPageDto.setTestMethod(characterDto.getTestMethod());
        //private List<RdmsFileDto> characterFileList;  //组件定义提交的附件
        if(! ObjectUtils.isEmpty(characterDto.getFileListStr())){
            List<String> strings = JSON.parseArray(characterDto.getFileListStr(), String.class);
            List<RdmsFileDto> fileDtos = rdmsFileService.getFileListByIdList(strings);
            if(!CollectionUtils.isEmpty(fileDtos)){
                for(RdmsFileDto fileDto: fileDtos){
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> authIds = JSON.parseArray(authIdsStr, String.class);
                                if(authIds.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }
                }
            }
            characterDocPageDto.setCharacterFileList(fileDtos);
        }
        //private String projectManagerName;
        //代码在上方

        //private RdmsBudgetExeDto budgetExeDto;
        RdmsBudgetExe rdmsBudgetExe = rdmsBudgetExeService.selectByPrimaryKey(characterId);  //id he item 是保持一致的
        characterDocPageDto.setBudgetExeDto(CopyUtil.copy(rdmsBudgetExe, RdmsBudgetExeDto.class));
        //private List<RdmsHmiJobItemDocPageDto> allDevJobList;
        List<JobItemTypeEnum> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEVELOP);
        jobTypeList.add(JobItemTypeEnum.ASSIST);
        List<String> devJobitemIdList = rdmsJobItemsService.getJobitemIdListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), jobTypeList);
        List<RdmsHmiJobItemDocPageDto> devJobitemDocList = new ArrayList<>();
        for(String jobId: devJobitemIdList){
            RdmsHmiJobItemDocPageDto jobItemDocPageInfo = rdmsJobItemsService.getJobItemDocPageInfo(jobId, loginUserId);
            devJobitemDocList.add(jobItemDocPageInfo);
        }
        characterDocPageDto.setAllDevJobList(devJobitemDocList);

        //private List<RdmsHmiJobItemDocPageDto> allTestJobList;
        List<JobItemTypeEnum> testJobTypeList = new ArrayList<>();
        testJobTypeList.add(JobItemTypeEnum.TEST);
        List<String> testJobitemIdList = rdmsJobItemsService.getJobitemIdListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), testJobTypeList);
        List<RdmsHmiJobItemDocPageDto> testJobitemDocList = new ArrayList<>();
        for(String jobId: testJobitemIdList){
            RdmsHmiJobItemDocPageDto jobItemDocPageInfo = rdmsJobItemsService.getJobItemDocPageInfo(jobId, loginUserId);
            testJobitemDocList.add(jobItemDocPageInfo);
        }
        characterDocPageDto.setAllTestJobList(testJobitemDocList);

        //private List<RdmsHmiJobItemDocPageDto> allIntegrationJobList;
        List<JobItemTypeEnum> intJobTypeList = new ArrayList<>();
        intJobTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<String> intJobitemIdList = rdmsJobItemsService.getJobitemIdListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), intJobTypeList);
        List<RdmsHmiJobItemDocPageDto> intJobitemDocList = new ArrayList<>();
        for(String jobId: intJobitemIdList){
            RdmsHmiJobItemDocPageDto jobItemDocPageInfo = rdmsJobItemsService.getJobItemDocPageInfo(jobId, loginUserId);
            intJobitemDocList.add(jobItemDocPageInfo);
        }
        characterDocPageDto.setAllIntegrationJobList(intJobitemDocList);

        //private List<RdmsHmiJobItemDocPageDto> allReviewJobList;
        List<JobItemTypeEnum> reviewJobTypeList = new ArrayList<>();
        reviewJobTypeList.add(JobItemTypeEnum.REVIEW);
        List<JobItemStatusEnum> reviewJobStatusList = new ArrayList<>();
        reviewJobStatusList.add(JobItemStatusEnum.COMPLETED);
        reviewJobStatusList.add(JobItemStatusEnum.ARCHIVED);
//        List<String> reviewJobitemIdList = rdmsJobItemsService.getJobitemIdListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), reviewJobTypeList);
        List<RdmsJobItemDto> reviewJobitemList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, reviewJobStatusList, reviewJobTypeList);
        List<RdmsHmiJobItemDocPageDto> reviewJobitemDocList = new ArrayList<>();
        for(RdmsJobItemDto jobItemDto: reviewJobitemList){
            RdmsHmiJobItemDocPageDto jobItemDocPageInfo = rdmsJobItemsService.getJobItemDocPageInfo(jobItemDto.getId(), loginUserId);
            reviewJobitemDocList.add(jobItemDocPageInfo);
        }
        characterDocPageDto.setAllReviewJobList(reviewJobitemDocList);

        return characterDocPageDto;
    }

    /**
     * 获取一个功能开发过程中产生的所有文件附件
     * @param characterId
     * @return
     */
    @Transactional
    public RdmsHmiCharacterArchiveDocsDto getCharacterArchiveDocs(String characterId, String loginUserId) {
        RdmsHmiCharacterArchiveDocsDto archiveDocsDto = this.getRdmsCharacterAllFileList(characterId, loginUserId);

        //查看parent character
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        if(!ObjectUtils.isEmpty(rdmsCharacter.getParent())){
            RdmsHmiCharacterArchiveDocsDto rdmsCharacterAllFileList = this.getCharacterArchiveDocs(rdmsCharacter.getParent(), loginUserId);
            archiveDocsDto.setOldVersion(rdmsCharacterAllFileList);
        }

        return archiveDocsDto;
    }

    private RdmsHmiCharacterArchiveDocsDto getRdmsCharacterAllFileList(String characterId, String loginUserId) {
        RdmsHmiCharacterArchiveDocsDto archiveDocsDto = new RdmsHmiCharacterArchiveDocsDto();
        //查出所有的功能迭代记录
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        archiveDocsDto.setId(rdmsCharacter.getId());
        archiveDocsDto.setCharacterName(rdmsCharacter.getCharacterName());

        //组件定义文档附件
        if(! ObjectUtils.isEmpty(rdmsCharacter.getFileListStr())){
            List<String> strings = JSON.parseArray(rdmsCharacter.getFileListStr(), String.class);
            List<RdmsFileDto> fileDtos = rdmsFileService.getFileListByIdList(strings);
            archiveDocsDto.setCharacterFileList(fileDtos);
        }

        //开发工单输出文档
        List<JobItemTypeEnum> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEVELOP);
        jobTypeList.add(JobItemTypeEnum.ASSIST);
        List<String> devJobitemIdList = rdmsJobItemsService.getJobitemIdListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), jobTypeList);
        List<RdmsFileDto> devJobitemDocList = new ArrayList<>();
        for(String jobId: devJobitemIdList){
            List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemsService.getJobItemArchiveDocs(jobId, loginUserId);
            devJobitemDocList.addAll(jobItemArchiveDocs);
        }
        archiveDocsDto.setDevelopFileList(devJobitemDocList);

        //测试工单输出文档
        List<JobItemTypeEnum> testJobTypeList = new ArrayList<>();
        testJobTypeList.add(JobItemTypeEnum.TEST);
        List<String> testJobitemIdList = rdmsJobItemsService.getJobitemIdListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), testJobTypeList);
        List<RdmsFileDto> testJobitemDocList = new ArrayList<>();
        for(String jobId: testJobitemIdList){
            List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemsService.getJobItemArchiveDocs(jobId, loginUserId);
            testJobitemDocList.addAll(jobItemArchiveDocs);
        }
        archiveDocsDto.setTestFileList(testJobitemDocList);

        //集成工单输出文件
        List<JobItemTypeEnum> intJobTypeList = new ArrayList<>();
        intJobTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<String> intJobitemIdList = rdmsJobItemsService.getJobitemIdListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), intJobTypeList);
        List<RdmsFileDto> intJobitemDocList = new ArrayList<>();
        for(String jobId: intJobitemIdList){
            List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemsService.getJobItemArchiveDocs(jobId, loginUserId);
            intJobitemDocList.addAll(jobItemArchiveDocs);
        }
        archiveDocsDto.setIntFileList(intJobitemDocList);

        //评审输出文件
        List<JobItemTypeEnum> reviewJobTypeList = new ArrayList<>();
        reviewJobTypeList.add(JobItemTypeEnum.REVIEW);
        List<JobItemStatusEnum> reviewJobStatusList = new ArrayList<>();
        reviewJobStatusList.add(JobItemStatusEnum.COMPLETED);
        reviewJobStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<RdmsJobItemDto> reviewJobitemList = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, reviewJobStatusList, reviewJobTypeList);
        List<RdmsFileDto> reviewJobitemDocList = new ArrayList<>();
        for(RdmsJobItemDto jobItemDto: reviewJobitemList){
            List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemsService.getJobItemArchiveDocs(jobItemDto.getId(), loginUserId);
            reviewJobitemDocList.addAll(jobItemArchiveDocs);
        }
        archiveDocsDto.setReviewFileList(reviewJobitemDocList);

        return archiveDocsDto;
    }

    /**
     *
     */
    @Transactional
    public List<RdmsCharacterDto> getCharacterListByJobitemId(String jobItemId, String loginUserId) {
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andJobitemIdEqualTo(jobItemId)
                .andStatusNotEqualTo(CharacterStatusEnum.DISCARD.getStatus())
                .andJobitemTypeNotEqualTo(JobItemTypeEnum.FUNCTION.getType())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        if(! CollectionUtils.isEmpty(rdmsCharacterDtos)){
            for(RdmsCharacterDto characterDto : rdmsCharacterDtos){
                if (!ObjectUtils.isEmpty(characterDto)) {
                    characterDto.setLoginUserId(loginUserId);
                    this.appendRecordSimpleInfo(characterDto);

                    //根据characterID查出所有工单附件列表
                    RdmsJobItem rdmsJobItem = rdmsJobItemsService.selectByPrimaryKey(jobItemId);
                    String fileListStr = rdmsJobItem.getFileListStr();
                    List<String> fileList = JSON.parseArray(fileListStr, String.class);
                    if(! CollectionUtils.isEmpty(fileList) && !fileListStr.equals("[]")){
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        for (String id : fileList) {
                            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                            fileDtos.add(fileDto);
                        }
                        characterDto.setJobItemFileList(fileDtos);
                    }
                }
            }
        }
        return rdmsCharacterDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByFunctionJobitemId(String jobItemId) {
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andJobitemIdEqualTo(jobItemId)
                .andStatusNotEqualTo(CharacterStatusEnum.DISCARD.getStatus())
                .andJobitemTypeEqualTo(JobItemTypeEnum.FUNCTION.getType())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        List<RdmsCharacterDto> rdmsCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        return rdmsCharacterDtos;
    }

    /**
     *
     */
    @Transactional
    public RdmsCharacterDto getCharacterSimpleInfo(String characterId) {
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto characterDto = CopyUtil.copy(character, RdmsCharacterDto.class);
        if(!ObjectUtils.isEmpty(characterDto) && !ObjectUtils.isEmpty(characterDto.getProductManagerId())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getProductManagerId());
            characterDto.setProductManagerName(rdmsCustomerUser.getTrueName());
        }
        return characterDto;
    }

    @Transactional
    public List<String> getCharacterNameListByIdList(String customerId , List<String> characterIdList) {
        if(CollectionUtils.isEmpty(characterIdList)){
            return null;
        }
        List<MyCharacter> characterListByIdList = myCharacterMapper.getCharacterListByIdList(customerId, characterIdList);
        if(!CollectionUtils.isEmpty(characterListByIdList)){
            return characterListByIdList.stream().map(MyCharacter::getCharacterName).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByIdList(String customerId , List<String> characterIdList) {
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andIdIn(characterIdList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public RdmsHmiTree<RdmsCharacterSimpleDto> getCharacterTreeByProjectId(String projectId){
        //取得所有已完工的characterList
        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.ARCHIVED);
        statusEnumList.add(CharacterStatusEnum.DECOMPOSED); //分解掉了, 但是会是某些子特性的父级特性
        statusEnumList.add(CharacterStatusEnum.UPDATED);
        statusEnumList.add(CharacterStatusEnum.UPDATED_HISTORY);
        List<RdmsCharacterDto> characterList = this.getCharacterListByProjectIdAndStatusList(projectId, statusEnumList);
        List<RdmsCharacterSimpleDto> rdmsCharacterSimpleDtos = CopyUtil.copyList(characterList, RdmsCharacterSimpleDto.class);

        List<RdmsHmiTree<RdmsCharacterSimpleDto>> rdmsHmiTrees = new ArrayList<>();
        for(RdmsCharacterSimpleDto characterDto : rdmsCharacterSimpleDtos){
            RdmsHmiTree<RdmsCharacterSimpleDto> hmiTree = new RdmsHmiTree<>();
            hmiTree.setObj(characterDto);
            hmiTree.setParent(characterDto.getParent());
            hmiTree.setLabel(characterDto.getCharacterName());
            hmiTree.setDeep(characterDto.getDeep());
            hmiTree.setId(characterDto.getId());
            hmiTree.setStatus(characterDto.getStatus());
            hmiTree.setAuxStatus(characterDto.getAuxStatus());
            hmiTree.setVersionUpdate(characterDto.getVersionUpdate());
            rdmsHmiTrees.add(hmiTree);
        }

        //树算法
        return (RdmsHmiTree<RdmsCharacterSimpleDto>) rdmsTreeService.getTree(rdmsHmiTrees);
    }

    @Transactional
    public RdmsHmiTree<RdmsFileDto> getDocTreeByCharacterId(String characterId, String loginUserId){
        RdmsHmiCharacterArchiveDocsDto characterArchiveDocs = this.getCharacterArchiveDocs(characterId, loginUserId);
        RdmsHmiTree<RdmsFileDto> docTree = this.createDocTree(characterArchiveDocs);
        return docTree;
    }

    private RdmsHmiTree<RdmsFileDto> createDocTree(RdmsHmiCharacterArchiveDocsDto docsDto){
        RdmsHmiTree<RdmsFileDto> hmiTree = new RdmsHmiTree<>();
        if(!ObjectUtils.isEmpty(docsDto)){
            hmiTree.setId(docsDto.getId());
            hmiTree.setLabel(docsDto.getCharacterName());
            hmiTree.setStatus("character");
            hmiTree.setObj(null);

            List<RdmsHmiTree<RdmsFileDto>> docTreeList = new ArrayList<>();
            RdmsHmiArchiveDocsDto archiveDocsDto = CopyUtil.copy(docsDto, RdmsHmiArchiveDocsDto.class);
            if(!ObjectUtils.isEmpty(archiveDocsDto)){
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                if(archiveDocsDto.getCharacterFileList() != null){
                    fileDtos.addAll(archiveDocsDto.getCharacterFileList());
                }
                if(archiveDocsDto.getDevelopFileList() != null){
                    fileDtos.addAll(archiveDocsDto.getDevelopFileList());
                }
                if(archiveDocsDto.getTestFileList() != null){
                    fileDtos.addAll(archiveDocsDto.getTestFileList());
                }
                if(archiveDocsDto.getIntFileList() != null){
                    fileDtos.addAll(archiveDocsDto.getIntFileList());
                }
                if(archiveDocsDto.getReviewFileList() != null){
                    fileDtos.addAll(archiveDocsDto.getReviewFileList());
                }

                List<RdmsFileDto> fileDtoList = fileDtos.stream().distinct().collect(Collectors.toList());

                for(RdmsFileDto fileDto: fileDtoList){
                    RdmsHmiTree<RdmsFileDto> fileTree = new RdmsHmiTree<>();
                    fileTree.setId(fileDto.getId());
                    fileTree.setLabel(fileDto.getName());
                    fileTree.setStatus("file");
                    fileTree.setObj(fileDto);
                    docTreeList.add(fileTree);
                }
            }

            if(!ObjectUtils.isEmpty(docsDto.getOldVersion())){
                RdmsHmiTree<RdmsFileDto> docTree = this.createDocTree(docsDto.getOldVersion());
                docTreeList.add(docTree);
                hmiTree.setChildren(docTreeList);
            }else{
                hmiTree.setChildren(docTreeList);
            }
        }
        return hmiTree;
    }

    /**
     *
     */
    @Transactional
    public RdmsHmiCharacterReviewDetailDto getCharacterReviewDetailInfo(String characterId, String loginUserId) {
        RdmsHmiCharacterReviewDetailDto characterReviewDetailDto = new RdmsHmiCharacterReviewDetailDto();

        characterReviewDetailDto.setCharacterId(characterId);

        RdmsCharacterDto characterDto = this.getCharacterRecordDetailById(characterId, loginUserId);
        characterReviewDetailDto.setCharacterDto(characterDto);

        {
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.REVIEW);
            List<RdmsJobItemDto> reviewJobItemDtos = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.COMPLETED.getStatus(), jobitemTypeList);
            if(! CollectionUtils.isEmpty(reviewJobItemDtos)){
                for(RdmsJobItemDto jobItemDto: reviewJobItemDtos){
                    RdmsJobItemDto jobitemRecordDetailInfo = rdmsJobItemsService.getJobitemDetailInfo(jobItemDto.getId(), loginUserId);
                    if(CollectionUtils.isEmpty(characterReviewDetailDto.getReviewJobitemList())){
                        List<RdmsJobItemDto> jobitemList = new ArrayList<>();
                        jobitemList.add(jobitemRecordDetailInfo);
                        characterReviewDetailDto.setReviewJobitemList(jobitemList);
                    }else{
                        characterReviewDetailDto.getReviewJobitemList().add(jobitemRecordDetailInfo);
                    }
                }
            }
        }

        return characterReviewDetailDto;
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListBySubprojectId(String subprojectId, CharacterStatusEnum statusEnum){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusNotEqualTo(CharacterStatusEnum.DISCARD.getStatus())
                .andAuxStatusNotEqualTo(CharacterStatusEnum.HISTORY.getStatus())
                .andDeletedEqualTo(0);
        if(! ObjectUtils.isEmpty(statusEnum)){
            criteria.andStatusEqualTo(statusEnum.getStatus());
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getArchivedCharacterListByCustomerId(String customerId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED_HISTORY.getStatus());
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getReleaseCharacterListBySubprojectId(String subprojectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.ARCHIVED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED.getStatus());
        statusList.add(CharacterStatusEnum.UPDATED_HISTORY.getStatus());
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andAuxStatusNotEqualTo(CharacterStatusEnum.HISTORY.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getArchivedCharacterListBySubprojectIdList(List<String> subprojectIdList ){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andSubprojectIdIn(subprojectIdList)
                .andStatusEqualTo(CharacterStatusEnum.ARCHIVED.getStatus())
                .andAuxStatusNotEqualTo(CharacterStatusEnum.HISTORY.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListBySubprojectIdAndMmStatus(String subprojectId, CharacterStatusEnum mmStatus){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        if(! ObjectUtils.isEmpty(mmStatus)){
            criteria.andMmStatusEqualTo(mmStatus.getStatus());
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListBySubprojectIdAndStatusList(String subprojectId, List<CharacterStatusEnum> statusEnumList){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria().andSubprojectIdEqualTo(subprojectId).andDeletedEqualTo(0);
        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(CharacterStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByProjectIdAndStatusList(String projectId, List<CharacterStatusEnum> statusEnumList){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(CharacterStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public List<RdmsCharacterDto> getCharacterListByProjectIdAndStatusList_notUpdated(String projectId, List<CharacterStatusEnum> statusEnumList){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andVersionUpdateIsNull()
                .andDeletedEqualTo(0);
        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(CharacterStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
    }

    @Transactional
    public Integer getNumOfCharactersByNextNodeAndStatus(String nextNode, List<CharacterStatusEnum> statusEnumList){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("create_time desc");
        RdmsCharacterExample.Criteria criteria = characterExample.createCriteria().andNextNodeEqualTo(nextNode).andDeletedEqualTo(0);
        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(CharacterStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int)rdmsCharacterMapper.countByExample(characterExample);
    }

    @Transactional
    public Integer getNumOfUpdateCharactersByUserId(String userId){
        List<RdmsPreProjectDto> preProjectList = rdmsPreProjectService.getPreProjectListByPdmId(userId);
        long sum = 0;
        if(! CollectionUtils.isEmpty(preProjectList)){
            for(RdmsPreProjectDto preProjectDto: preProjectList){
                RdmsCharacterExample characterExample = new RdmsCharacterExample();
                characterExample.createCriteria()
                        .andPreProjectIdEqualTo(preProjectDto.getId())
                        .andStatusEqualTo(CharacterStatusEnum.UPDATE.getStatus())
                        .andDeletedEqualTo(0);

                long l = rdmsCharacterMapper.countByExample(characterExample);
                sum += l;
            }
        }
        return (Integer) (int)sum;
    }

    /**
     *
     */
    @Transactional
    public RdmsCharacterDto getCharacterRecordDetailById(String characterId, String loginUserId) {
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto characterDto = CopyUtil.copy(character, RdmsCharacterDto.class);
        if(ObjectUtils.isEmpty(characterDto)){
            return characterDto;
        }
        characterDto.setLoginUserId(loginUserId);
        this.appendCharacterRecordAndDataInfo(characterDto);

        //根据characterID查出所有工单附件列表
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsService.listByCharacterId(characterId);
        if(! CollectionUtils.isEmpty(rdmsJobItems)){
            List<RdmsFileDto> fileDtos = new ArrayList<>();
            List<String> stringList = new ArrayList<>();
            for(RdmsJobItem jobItems : rdmsJobItems) {
                if (!ObjectUtils.isEmpty(jobItems.getFileListStr()) && jobItems.getFileListStr().length() > 6) {
                    List<String> sList = JSON.parseArray(jobItems.getFileListStr(), String.class);
                    stringList.addAll(sList);
                }
            }
            for(String id : stringList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                //读取访问权限
                if(!ObjectUtils.isEmpty(fileDto)){
                    //补充填写文件权限信息
                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                    if(ObjectUtils.isEmpty(byFileId)){
                        fileDto.setAuthStatus(true);
                    }else{
                        String authIdsStr = byFileId.getAuthIdsStr();
                        if(ObjectUtils.isEmpty(authIdsStr)){
                            fileDto.setAuthStatus(true);
                        }else{
                            List<String> strings = JSON.parseArray(authIdsStr, String.class);
                            if(strings.contains(loginUserId)){
                                fileDto.setAuthStatus(true);
                            }else{
                                fileDto.setAuthStatus(false);
                            }
                        }
                    }
                }
                fileDtos.add(fileDto);
            }
            characterDto.setJobItemFileList(fileDtos);
        }

        List<RdmsJobItemDto> jobitemListByCharacter = rdmsJobItemsService.getJobitemListByCharacterIdAndStatusTypeList(characterDto.getId(), null, null);
        List<RdmsHmiJobitemPlainDto> jobitemPlainDtoList = jobitemListByCharacter.stream().map(item -> new RdmsHmiJobitemPlainDto(item.getId(), item.getJobSerial(), item.getJobName(), item.getType(), item.getExecutorName(), item.getCreateTime())).collect(Collectors.toList());
        List<RdmsHmiJobitemPlainDto> hmiJobitemPlainDtos = jobitemPlainDtoList.stream().sorted(Comparator.comparing(RdmsHmiJobitemPlainDto::getCreateTime)).collect(Collectors.toList());
        characterDto.setDevJobitemPlainList(hmiJobitemPlainDtos);

        //是否存在评审工单  用于要求补充材料的评审场景
        List<RdmsJobItem> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusAndJobType(characterId, JobItemStatusEnum.SUBMIT.getStatus(), JobItemTypeEnum.REVIEW.getType());
        if(!CollectionUtils.isEmpty(jobitemList)){
            characterDto.setHasReviewJobitem(true);
        }else{
            characterDto.setHasReviewJobitem(false);
        }

        if(!ObjectUtils.isEmpty(characterDto.getModuleIdListStr())){
            List<String> characterIdList = JSON.parseArray(characterDto.getModuleIdListStr(), String.class);
            if(!CollectionUtils.isEmpty(characterIdList)){
                List<RdmsCbbDto> cbbListByIdList = rdmsCbbService.getCbbListByIdList(character.getCustomerId(), characterIdList);
                if(!CollectionUtils.isEmpty(cbbListByIdList)){
                    List<String> characterIds = cbbListByIdList.stream().map(RdmsCbbDto::getCharacterId).collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(characterIds)){
                        List<RdmsCharacterDto> characterListByIdList = this.getCharacterListByIdList(character.getCustomerId(), characterIds);
                        characterDto.setReferenceCharacterList(characterListByIdList);
                    }
                }
            }
        }

        return characterDto;
    }

    public RdmsCharacter getSavedCharacterByIteratingCharacterId(String characterId){
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        if(!ObjectUtils.isEmpty(rdmsCharacter)){
            if(rdmsCharacter.getStatus().equals(CharacterStatusEnum.ITERATING.getStatus())){
                RdmsCharacterExample characterExample = new RdmsCharacterExample();
                characterExample.createCriteria().andStatusEqualTo(CharacterStatusEnum.SAVED.getStatus()).andCharacterSerialEqualTo(characterId);
                List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
                if(!CollectionUtils.isEmpty(rdmsCharacters)){
                    return rdmsCharacters.get(0);
                }
            }
        }
        return null;
    }

    public void calAndUpdateCharacterSelfBudget(String characterId){
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(character.getCustomerId());
        {
            double sumOtherFee = character.getTestFee().floatValue()
                    + character.getPowerFee().floatValue()
                    + character.getConferenceFee().floatValue()
                    + character.getBusinessFee().floatValue()
                    + character.getCooperationFee().floatValue()
                    + character.getPropertyFee().floatValue()
                    + character.getLaborFee().floatValue()
                    + character.getConsultingFee().floatValue()
                    + character.getManagementFee().floatValue();
            character.setSumOtherFee(BigDecimal.valueOf(sumOtherFee));
            character.setSumOtherFee(BigDecimal.valueOf(sumOtherFee));
            if (!(character.getDevManhourApproved() == null || character.getTestManhourApproved() == null || character.getMaterialFeeApproved() == null)) {
                double budget = character.getDevManhourApproved() * standardManhour.getDevManhourFee().floatValue()
                        + character.getTestManhourApproved() * standardManhour.getTestManhourFee().floatValue()
                        + character.getMaterialFeeApproved().floatValue()
                        + sumOtherFee;
                character.setBudget(BigDecimal.valueOf(budget));
            }
        }
        this.update(character);
    }

    /**
    * 根据productManagerID进行分页列表查询
    */
/*    @Transactional
    public void listByProductManagerId(PageDto<RdmsCharacterDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.setOrderByClause("create_time desc");
        rdmsCharacterExample.createCriteria()
                .andProductManagerIdEqualTo(pageDto.getActor())
                .andPreProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(pageDto.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        List<RdmsCharacterDto> rdmsProductCharacterDtos = CopyUtil.copyList(rdmsCharacters, RdmsCharacterDto.class);
        for(RdmsCharacterDto characterDto : rdmsProductCharacterDtos){
            this.appendRecordSimpleInfo(characterDto);
        }
        PageInfo<RdmsCharacter> pageInfo = new PageInfo<>(rdmsCharacters);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsProductCharacterDtos);
    }*/

    /**
     *
     */
    public String save(RdmsCharacter character) {
        BigDecimal sumOtherFee = initSumOtherFee(character);
        character.setSumOtherFee(sumOtherFee);

        if(ObjectUtils.isEmpty(character.getId())){
            return this.insert(character);
        }else{
            RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(character.getId());
            if(ObjectUtils.isEmpty(rdmsCharacter)){
                return this.insert(character);
            }else{
                return this.update(character);
            }
        }
    }

    public String close(String characterId) {
        if(!ObjectUtils.isEmpty(characterId)){
            RdmsCharacter character = this.selectByPrimaryKey(characterId);
            //判断是否为预立项阶段, 如果不是预立项阶段, 什么也不做
            if(character.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())) {
                character.setStatus(CharacterStatusEnum.DISCARD.getStatus());
                character.setNextNode(null);
                this.update(character);

                //创建process记录
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterId);
                long characterProcessCount1 = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess.setDeep((int) characterProcessCount1);
                characterProcess.setExecutorId(character.getProductManagerId());
                characterProcess.setNextNode(null);
                characterProcess.setJobDescription("功能/组件定义被废弃");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                rdmsCharacterProcessService.save(characterProcess);

//只有 DEMAND 工单对应的功能,才能 关闭!

                /*
                //如果被关闭的功能是SUGGEST_UPDATE功能
                if(character.getJobitemType().equals(JobItemTypeEnum.SUGGEST_UPDATE.getType())){
                    RdmsCharacter originCharacter = this.selectByPrimaryKey(character.getCharacterSerial());
                    originCharacter.setStatus(CharacterStatusEnum.ARCHIVED.getStatus());
                    String characterOriginId = this.update(originCharacter);

                    //创建process记录
                    RdmsCharacterProcess characterProcessOrigin = new RdmsCharacterProcess();
                    characterProcessOrigin.setCharacterId(characterOriginId);
                    long characterProcessOriginCount1 = rdmsCharacterProcessService.getCharacterProcessCount(characterOriginId);
                    characterProcessOrigin.setDeep((int) characterProcessOriginCount1);
                    characterProcessOrigin.setExecutorId(character.getProductManagerId());
                    characterProcessOrigin.setNextNode(null);
                    characterProcessOrigin.setJobDescription("放弃功能修订,恢复状态");
                    characterProcessOrigin.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                    rdmsCharacterProcessService.save(characterProcessOrigin);
                }

                //如果被关闭的功能是MERGE功能
                if(character.getJobitemType().equals(JobItemTypeEnum.MERGE.getType())){
                    RdmsJobItem jobItem1 = rdmsJobItemService.selectByPrimaryKey(character.getJobitemId());
                    if(!ObjectUtils.isEmpty(jobItem1) && !ObjectUtils.isEmpty(jobItem1.getMergeCharacterIdStr())){
                        List<String> characterIdList = JSON.parseArray(jobItem1.getMergeCharacterIdStr(), String.class);
                        if(!CollectionUtils.isEmpty(characterIdList)){
                            for(String mergedId: characterIdList){
                                RdmsCharacter originCharacter = this.selectByPrimaryKey(mergedId);
                                originCharacter.setStatus(CharacterStatusEnum.ARCHIVED.getStatus());
                                String characterOriginId = this.update(originCharacter);

                                //创建process记录
                                RdmsCharacterProcess characterProcessOrigin = new RdmsCharacterProcess();
                                characterProcessOrigin.setCharacterId(characterOriginId);
                                long characterProcessOriginCount1 = rdmsCharacterProcessService.getCharacterProcessCount(characterOriginId);
                                characterProcessOrigin.setDeep((int) characterProcessOriginCount1);
                                characterProcessOrigin.setExecutorId(character.getProductManagerId());
                                characterProcessOrigin.setNextNode(null);
                                characterProcessOrigin.setJobDescription("放弃功能合并,恢复状态");
                                characterProcessOrigin.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                                rdmsCharacterProcessService.save(characterProcessOrigin);
                            }
                        }
                    }
                }*/

                //处理工单状态
                //查看工单下的所有Character, 如果所有的Character都已经处于approve或者iterating或者discard状态, 则设置工单为完成状态, 否则不做处理
                rdmsJobItemsService.setJobitemInfoAtDemandApproveOrIteDecJobSubmit(character.getJobitemId());
            }
        }
        return characterId;
    }

    private static BigDecimal initSumOtherFee(RdmsCharacter character) {
        character.setTestFee(ObjectUtils.isEmpty(character.getTestFee()) ? BigDecimal.ZERO : character.getTestFee());
        character.setPowerFee(ObjectUtils.isEmpty(character.getPowerFee()) ? BigDecimal.ZERO : character.getPowerFee());
        character.setConferenceFee(ObjectUtils.isEmpty(character.getConferenceFee()) ? BigDecimal.ZERO : character.getConferenceFee());
        character.setBusinessFee(ObjectUtils.isEmpty(character.getBusinessFee()) ? BigDecimal.ZERO : character.getBusinessFee());
        character.setCooperationFee(ObjectUtils.isEmpty(character.getCooperationFee()) ? BigDecimal.ZERO : character.getCooperationFee());
        character.setPropertyFee(ObjectUtils.isEmpty(character.getPropertyFee()) ? BigDecimal.ZERO : character.getPropertyFee());
        character.setLaborFee(ObjectUtils.isEmpty(character.getLaborFee()) ? BigDecimal.ZERO : character.getLaborFee());
        character.setConsultingFee(ObjectUtils.isEmpty(character.getConsultingFee()) ? BigDecimal.ZERO : character.getConsultingFee());
        character.setManagementFee(ObjectUtils.isEmpty(character.getManagementFee()) ? BigDecimal.ZERO : character.getManagementFee());

        BigDecimal sumOtherFee = character.getTestFee()
                .add(character.getPowerFee())
                .add(character.getConferenceFee())
                .add(character.getBusinessFee())
                .add(character.getCooperationFee())
                .add(character.getPropertyFee())
                .add(character.getLaborFee())
                .add(character.getConsultingFee())
                .add(character.getManagementFee());
        return sumOtherFee;
    }

    /**
     *
     */
    @Transactional
    public String saveRecordAndData(RdmsCharacterDto characterDto) {
        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String characterId = this.save(character);
        this.calAndUpdateCharacterSelfBudget(characterId);
        RdmsCharacterData characterData = CopyUtil.copy(characterDto, RdmsCharacterData.class);
        characterData.setId(characterId);
        rdmsCharacterDataService.save(characterData);
        return characterId;
    }

    @Transactional
    public RdmsCharacterDto getRecordAndData(String characterId, String loginUserId) {
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(characterId);
        RdmsCharacterDto characterDto = CopyUtil.copy(rdmsCharacter, RdmsCharacterDto.class);
        characterDto.setLoginUserId(loginUserId);
        this.appendCharacterRecordAndDataInfo(characterDto);
        return characterDto;
    }

    @Transactional
    public RdmsCharacterData getCharacterData(String characterId) {
        RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(characterId);
        return rdmsCharacterData;
    }

    @Transactional
    public String saveCharacterData(RdmsCharacterData cData) {
        String characterId = rdmsCharacterDataService.save(cData);
        return characterId;
    }

    /**
     * 功能分解保存时, 使用这个保存函数, 如果创建新记录, 会增加process记录
     * 会添加process记录
     */
    public String saveDecomposeCharacter(RdmsCharacter character, RdmsCharacterData characterData) {
        //查看父级Character的tree_deep
        RdmsCharacter parentCharacter = this.selectByPrimaryKey(character.getParent());
        if(parentCharacter != null && parentCharacter.getDeep() != null){
            character.setDeep(parentCharacter.getDeep() + 1);
        }else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        if(ObjectUtils.isEmpty(character.getId())){
            character.setStatus(CharacterStatusEnum.SAVED.getStatus());
            character.setJobitemType(JobItemTypeEnum.DECOMPOSE.getType());
            character.setIterationVersion(1);
            String characterId = this.save(character);
            characterData.setId(characterId);
            rdmsCharacterDataService.save(characterData);

            //添加process
            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(characterId);
            characterProcess.setExecutorId(character.getWriterId());
            characterProcess.setNextNode(character.getWriterId());
            long processCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
            characterProcess.setDeep((int)processCount);
            characterProcess.setJobDescription("创建功能分解记录");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
            rdmsCharacterProcessService.save(characterProcess);

            return characterId;
        }else{
            RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(character.getId());
            if(! ObjectUtils.isEmpty(rdmsCharacter)){
                character.setId(rdmsCharacter.getId());
                character.setStatus(CharacterStatusEnum.SAVED.getStatus());
                character.setJobitemType(JobItemTypeEnum.DECOMPOSE.getType());
                character.setIterationVersion(1);
                String characterId = this.save(character);
                characterData.setId(characterId);
                rdmsCharacterDataService.update(characterData);
                //添加process
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterId);
                characterProcess.setExecutorId(character.getWriterId());
                characterProcess.setNextNode(character.getWriterId());
                long processCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess.setDeep((int)processCount);
                characterProcess.setJobDescription("创建功能分解记录");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
                return characterId;

            }else {
                character.setStatus(CharacterStatusEnum.SAVED.getStatus());
                character.setJobitemType(JobItemTypeEnum.DECOMPOSE.getType());
                character.setIterationVersion(1);
                String characterId = this.save(character);
                characterData.setId(characterId);
                rdmsCharacterDataService.save(characterData);

                //添加process
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterId);
                characterProcess.setExecutorId(character.getWriterId());
                characterProcess.setNextNode(character.getWriterId());
                long processCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess.setDeep((int)processCount);
                characterProcess.setJobDescription("创建功能分解记录");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
                return characterId;
            }
        }
    }

    public RdmsCharacter selectByPrimaryKey(String id){
        return rdmsCharacterMapper.selectByPrimaryKey(id);
    }

    public List<RdmsCharacter> getcharacterListByCharacterIdList(List<String> characterIdList){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andIdIn(characterIdList)
                .andDeletedEqualTo(0);
        return rdmsCharacterMapper.selectByExample(characterExample);
    }

    @Data
    public static class CbbListStr {
        private String characterId;
        private String moduleIdListStr;
    }
    @Transactional
    public String startResearchAndDevelopment(CbbListStr cbbListStr){
        RdmsCharacter rdmsCharacter = rdmsCharacterMapper.selectByPrimaryKey(cbbListStr.getCharacterId());
        if(! ObjectUtils.isEmpty(rdmsCharacter) && rdmsCharacter.getStatus().equals(CharacterStatusEnum.SETUPED.getStatus())){
            rdmsCharacter.setStatus(CharacterStatusEnum.DEVELOPING.getStatus());

            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(rdmsCharacter.getSubprojectId());
            rdmsCharacter.setNextNode(subproject.getProjectManagerId());  //将nextNode设置为项目经理

            rdmsCharacter.setModuleIdListStr(cbbListStr.getModuleIdListStr());
            this.save(rdmsCharacter);

            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(cbbListStr.getCharacterId());
            characterProcess.setExecutorId(subproject.getProjectManagerId());
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(cbbListStr.getCharacterId());
            characterProcess.setDeep((int)characterProcessCount);
            characterProcess.setJobDescription("功能/特性进入开发状态");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.ONGOING.getStatus());
            characterProcess.setNextNode(rdmsCharacter.getNextNode());
            rdmsCharacterProcessService.save(characterProcess);

            //将子项目标记为研发中状态
            RdmsProjectSubproject subproject1 = new RdmsProjectSubproject();
            subproject1.setId(subproject.getId());
            subproject1.setStatus(ProjectStatusEnum.ONGOING.getStatus());
            rdmsSubprojectService.updateByPrimaryKeySelective(subproject1);

            return cbbListStr.getCharacterId();
        }
        return null;
    }

    public RdmsCharacter getIterationHistoryRecord(String characterSerial){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.createCriteria()
                .andCharacterSerialEqualTo(characterSerial)
                .andStatusEqualTo(CharacterStatusEnum.HISTORY.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return rdmsCharacters.get(0);
    }

    public List<RdmsCharacter> getCharacterListBySerial(String characterSerial){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        characterExample.setOrderByClause("character_serial desc");
        characterExample.createCriteria()
                .andCharacterSerialEqualTo(characterSerial)
                .andDeletedEqualTo(0);
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        return rdmsCharacters;
    }

    public BigDecimal calCharacterBudget(String characterId){
        RdmsCharacter character = this.selectByPrimaryKey(characterId);
        RdmsManhourStandard standManhour = rdmsManhourService.getStandardManhourByCustomerId(character.getCustomerId());
        BigDecimal devFee = BigDecimal.valueOf(character.getDevManhourApproved()).multiply(standManhour.getDevManhourFee());
        BigDecimal testFee = BigDecimal.valueOf(character.getTestManhourApproved()).multiply(standManhour.getTestManhourFee());

        double sumOtherFee = character.getTestFee().floatValue()
                + character.getPowerFee().floatValue()
                + character.getConferenceFee().floatValue()
                + character.getBusinessFee().floatValue()
                + character.getCooperationFee().floatValue()
                + character.getPropertyFee().floatValue()
                + character.getLaborFee().floatValue()
                + character.getConsultingFee().floatValue()
                + character.getManagementFee().floatValue();
        character.setSumOtherFee(BigDecimal.valueOf(sumOtherFee));

        BigDecimal budget = BigDecimal.ZERO.add(devFee.add(testFee))
                .add(character.getSumOtherFee())
                .add(character.getMaterialFeeApproved());
        return budget;
    }


    private String insert(RdmsCharacter character) {
        if(ObjectUtils.isEmpty(character.getId())){  //当前端页面给出projectID时,将不为空
            character.setId(UuidUtil.getShortUuid());
        }
        if(ObjectUtils.isEmpty(character.getCharacterSerial()) || character.getCharacterSerial().length() < 8){
            character.setCharacterSerial(character.getId()); //character记录内容可能由于某种原因需要copy, serialNo用于定位原始数据
        }

        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(character.getId());
        if(! ObjectUtils.isEmpty(rdmsCharacter)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            CharacterStatusEnum characterEnumByStatus = CharacterStatusEnum.getCharacterEnumByStatus(character.getStatus());
            if(characterEnumByStatus == null){
                throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
            }
            character.setDeleted(0);
            if(character.getDeep() == null){
                character.setDeep(0);
            }
            character.setCreateTime(new Date());
            character.setUpdateTime(new Date());
            if(ObjectUtils.isEmpty(character.getAuxStatus())){
                character.setAuxStatus(CharacterStatusEnum.NOTSET.getStatus());
            }
            if(ObjectUtils.isEmpty(character.getMmStatus())){
                character.setMmStatus(CharacterStatusEnum.NOTSET.getStatus());
            }
            if(ObjectUtils.isEmpty(character.getBomStatus())){
                character.setBomStatus(CharacterBomStatusEnum.EDIT.getStatus());
            }
            rdmsCharacterMapper.insert(character);
            return character.getId();
        }
    }

    public String update(RdmsCharacter character) {
        if(ObjectUtils.isEmpty(character.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        if(ObjectUtils.isEmpty(character.getCharacterSerial()) || character.getCharacterSerial().length() < 8){
            character.setCharacterSerial(character.getId()); //character记录内容可能由于某种原因需要copy, serialNo用于定位原始数据
        }
        RdmsCharacter rdmsCharacter = this.selectByPrimaryKey(character.getId());
        if(ObjectUtils.isEmpty(rdmsCharacter)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            character.setUpdateTime(new Date());
            character.setCreateTime(rdmsCharacter.getCreateTime());
            character.setDeleted(0);
            if(character.getDeep() == null){
                character.setDeep(0);
            }
            if(ObjectUtils.isEmpty(character.getAuxStatus())){
                character.setAuxStatus(CharacterStatusEnum.NOTSET.getStatus());
            }
            if(ObjectUtils.isEmpty(character.getMmStatus())){
                character.setMmStatus(CharacterStatusEnum.NOTSET.getStatus());
            }
            if(ObjectUtils.isEmpty(character.getBomStatus())){
                character.setBomStatus(CharacterBomStatusEnum.EDIT.getStatus());
            }
            rdmsCharacterMapper.updateByPrimaryKey(character);
            return character.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsCharacter character){
        rdmsCharacterMapper.updateByPrimaryKeySelective(character);
        return character.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCharacterExample rdmsCharacterExample = new RdmsCharacterExample();
        rdmsCharacterExample.createCriteria().andIdEqualTo(id);
        List<RdmsCharacter> productCharacters = rdmsCharacterMapper.selectByExample(rdmsCharacterExample);
        if(! CollectionUtils.isEmpty(productCharacters)){
            RdmsCharacter productCharacter = productCharacters.get(0);
            productCharacter.setDeleted(1);
            rdmsCharacterMapper.updateByPrimaryKey(productCharacter);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    @Transactional
    public String saveUpdateCharacterRecord(String characterId, String preProjectId){
        ValidatorUtil.require(characterId, "功能/特性Id");
        ValidatorUtil.require(preProjectId, "预立项项目Id");

        //获得原来的Character记录
        RdmsCharacter currentOperatingCharacter = this.selectByPrimaryKey(characterId);
        if(ObjectUtils.isEmpty(currentOperatingCharacter)){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }

        //处理原记录
        currentOperatingCharacter.setVersionUpdate(CharacterStatusEnum.UPDATED.getStatus());  //版本升级
        this.save(currentOperatingCharacter);

        //生成一条新的用于迭代的记录
        RdmsCharacter updateCharacter = new RdmsCharacter();
        updateCharacter.setId(null);
        updateCharacter.setCharacterName(currentOperatingCharacter.getCharacterName());
        updateCharacter.setStatus(CharacterStatusEnum.UPDATE.getStatus());
        updateCharacter.setPreProjectId(preProjectId);
        updateCharacter.setParent(currentOperatingCharacter.getId());
        updateCharacter.setJobitemType(JobItemTypeEnum.UPDATE.getType());
        updateCharacter.setCustomerId(currentOperatingCharacter.getCustomerId());
        updateCharacter.setIterationVersion(currentOperatingCharacter.getIterationVersion() + 1);
        updateCharacter.setFileListStr(currentOperatingCharacter.getFileListStr());
        updateCharacter.setDemandListStr(currentOperatingCharacter.getDemandListStr());
        updateCharacter.setWriterId(currentOperatingCharacter.getWriterId());
        updateCharacter.setUpdateTime(new Date());
        String updateCharacterId = this.save(updateCharacter);

        RdmsCharacterData characterData = rdmsCharacterDataService.selectByPrimaryKey(characterId);
        characterData.setId(updateCharacterId);
        characterData.setUpdateTime(new Date());
        return rdmsCharacterDataService.save(characterData);
    }

    @Transactional
    public String saveIterationCharacterRecord(String characterId, String preProjectId){
        ValidatorUtil.require(characterId, "功能/特性Id");
        ValidatorUtil.require(preProjectId, "预立项项目Id");

        //查看是否存在,评审完成但是未归档的工单
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.REVIEW);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, jobItemStatusList, jobitemTypeList);
        if(! CollectionUtils.isEmpty(jobitemList)){
            for(RdmsJobItemDto jobItemDto: jobitemList){
                jobItemDto.setStatus(JobItemStatusEnum.ARCHIVED.getStatus());
                rdmsJobItemService.update(jobItemDto);
            }
        }

        //获得原来的Character记录
        RdmsCharacter currentOperatingCharacter = this.selectByPrimaryKey(characterId);
        if(ObjectUtils.isEmpty(currentOperatingCharacter)){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }

        //处理原记录
        currentOperatingCharacter.setStatus(CharacterStatusEnum.UPDATED.getStatus());  //版本升级
        this.save(currentOperatingCharacter);

        //生成一条新的用于迭代的记录
        RdmsCharacter updateCharacter = new RdmsCharacter();
        updateCharacter.setId(null);
        updateCharacter.setCharacterName(currentOperatingCharacter.getCharacterName());
        updateCharacter.setStatus(CharacterStatusEnum.UPDATE.getStatus());
        updateCharacter.setAuxStatus(CharacterStatusEnum.UPDATE.getStatus());
        updateCharacter.setPreProjectId(preProjectId);
        updateCharacter.setParent(currentOperatingCharacter.getId());
        updateCharacter.setJobitemType(JobItemTypeEnum.UPDATE.getType());
        updateCharacter.setCustomerId(currentOperatingCharacter.getCustomerId());
        updateCharacter.setIterationVersion(currentOperatingCharacter.getIterationVersion() + 1);
        updateCharacter.setFileListStr(currentOperatingCharacter.getFileListStr());
        updateCharacter.setDemandListStr(currentOperatingCharacter.getDemandListStr());
        updateCharacter.setWriterId(currentOperatingCharacter.getWriterId());
        updateCharacter.setUpdateTime(new Date());
        String updateCharacterId = this.save(updateCharacter);

        RdmsCharacterData characterData = rdmsCharacterDataService.selectByPrimaryKey(characterId);
        characterData.setId(updateCharacterId);
        characterData.setUpdateTime(new Date());
        return rdmsCharacterDataService.save(characterData);
    }

}
