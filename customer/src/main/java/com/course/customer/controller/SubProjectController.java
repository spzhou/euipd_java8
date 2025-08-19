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
import com.course.server.dto.RdmsBudgetResearchDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.dto.rdms.RdmsProjectSubprojectDto;
import com.course.server.enums.rdms.*;
import com.course.server.service.rdms.*;
import com.course.server.service.util.DateUtil;
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
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sub-project")
public class SubProjectController {
    private static final Logger LOG = LoggerFactory.getLogger(SubProjectController.class);
    public static final String BUSINESS_NAME = "子项目";

    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsCharacterProcessService rdmsCharacterProcessService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsBudgetExeService rdmsBudgetExeService;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Resource
    private RdmsFeeManageService rdmsFeeManageService;
    @Resource
    private RdmsBudgetResearchService rdmsBudgetResearchService;
    @Resource
    private RdmsProjectSecLevelService rdmsProjectSecLevelService;
    @Resource
    private RdmsCharacterSecLevelService rdmsCharacterSecLevelService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Autowired
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsGanttService rdmsGanttService;
    @Autowired
    private RdmsCbbService rdmsCbbService;

    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsProjectSubprojectDto> save(@RequestBody RdmsProjectSubprojectDto subprojectDto) throws ParseException {

        ValidatorUtil.require(subprojectDto.getCustomerId(), "公司ID");
        ValidatorUtil.require(subprojectDto.getProjectId(), "项目ID");
        ValidatorUtil.require(subprojectDto.getParent(), "上级项目ID");

        ValidatorUtil.require(subprojectDto.getLabel(), "子项目名称");
        ValidatorUtil.require(subprojectDto.getIntroduce(), "子项目概要");
        ValidatorUtil.require(subprojectDto.getProjectManagerId(), "子项目经理");
        ValidatorUtil.require(subprojectDto.getTargetMilestoneId(), "目标里程碑");
        ValidatorUtil.require(subprojectDto.getDeep(), "Deep值");
        ValidatorUtil.require(subprojectDto.getCreatorId(), "创建者");
        ValidatorUtil.require(subprojectDto.getProjectCode(), "项目Code");
        ValidatorUtil.require(subprojectDto.getReleaseTime(), "评审时间");

        ResponseDto<RdmsProjectSubprojectDto> responseDto = new ResponseDto<>();
        RdmsProjectSubproject projectSubproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
        /*RdmsMilestoneDto rdmsMilestoneDto = new RdmsMilestoneDto();
        if(subprojectDto.getTargetMilestoneId() != null){
            rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(subprojectDto.getTargetMilestoneId());
            projectSubproject.setReleaseTime(rdmsMilestoneDto.getReviewTime());
        }*/
        Date adjustReleaseTime = DateUtil.afternoon5pm(subprojectDto.getReleaseTime());
        projectSubproject.setReleaseTime(adjustReleaseTime);
        //计算子项目总预算
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subprojectDto.getProjectId());
        RdmsProjectSubproject parentProject = rdmsSubprojectService.selectByPrimaryKey(subprojectDto.getParent());
        projectSubproject.setStatus(ProjectStatusEnum.SETUPED.getStatus());
        projectSubproject.setType(ProjectTypeEnum.SUB_PROJECT.getType());
        //添加项目通用字段
        projectSubproject.setPreProjectId(rdmsProject.getPreProjectId());
        projectSubproject.setSupervise(rdmsProject.getSupervise());
        projectSubproject.setProductManagerId(rdmsProject.getProductManagerId());
        projectSubproject.setKeyMemberListStr(rdmsProject.getKeyMemberListStr());
        projectSubproject.setDevVersion(rdmsProject.getDevVersion());
        projectSubproject.setSetupedTime(rdmsProject.getSetupedTime());
        projectSubproject.setRdType(rdmsProject.getRdType());

        //创建子项目
        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.save(projectSubproject);

        //保存项目保密等级信息
        RdmsProjectSecLevel projectSecLevel = new RdmsProjectSecLevel();
        projectSecLevel.setProjectId(rdmsProjectSubproject.getProjectId());
        projectSecLevel.setSubprojectId(rdmsProjectSubproject.getId());
        projectSecLevel.setLevel(subprojectDto.getSecretLevel());
        rdmsProjectSecLevelService.saveBySubprojectId(projectSecLevel);

        //保存预算
//        if(rdmsProject.getRdType().equals(RdTypeEnum.DIRECT.getType()))
        {
            RdmsBudgetResearch rdmsBudgetResearch = CopyUtil.copy(subprojectDto.getBudgetData(), RdmsBudgetResearch.class);
            rdmsBudgetResearch.setId(null);
            rdmsBudgetResearch.setCustomerId(rdmsProject.getCustomerId());
            rdmsBudgetResearch.setProjectId(rdmsProject.getId());
            rdmsBudgetResearch.setParentId(parentProject.getId());
            rdmsBudgetResearch.setSubprojectId(rdmsProjectSubproject.getId());  //子项目ID
            rdmsBudgetResearch.setStage(BudgetTypeEnum.SUB_PROJECT.getType());
            rdmsBudgetResearch.setIsRoot(0);
            rdmsBudgetResearchService.transformBudgetToYuan(rdmsBudgetResearch);
            String id = rdmsBudgetResearchService.save(rdmsBudgetResearch);

            //对parent项目的预算进行调减
            //用parentId去找出对应的parent子项目预算
            RdmsBudgetResearchDto parentRecord = rdmsBudgetResearchService.getRecordByCustomerIdAndSubprojectId(rdmsBudgetResearch.getCustomerId(), rdmsBudgetResearch.getParentId());
            parentRecord.setEquipmentFeeApprovedBt(parentRecord.getEquipmentFeeApprovedBt().subtract(rdmsBudgetResearch.getEquipmentFeeApprovedBt()));
            parentRecord.setEquipmentFeeApprovedZc(parentRecord.getEquipmentFeeApprovedZc().subtract(rdmsBudgetResearch.getEquipmentFeeApprovedZc()));
            parentRecord.setEquipmentFeeBt(parentRecord.getEquipmentFeeBt().subtract(rdmsBudgetResearch.getEquipmentFeeBt()));
            parentRecord.setEquipmentFeeZc(parentRecord.getEquipmentFeeZc().subtract(rdmsBudgetResearch.getEquipmentFeeZc()));

            parentRecord.setTestFeeApprovedBt(parentRecord.getTestFeeApprovedBt().subtract(rdmsBudgetResearch.getTestFeeApprovedBt()));
            parentRecord.setTestFeeApprovedZc(parentRecord.getTestFeeApprovedZc().subtract(rdmsBudgetResearch.getTestFeeApprovedZc()));
            parentRecord.setTestFeeBt(parentRecord.getTestFeeBt().subtract(rdmsBudgetResearch.getTestFeeBt()));
            parentRecord.setTestFeeZc(parentRecord.getTestFeeZc().subtract(rdmsBudgetResearch.getTestFeeZc()));

            parentRecord.setMaterialFeeApprovedBt(parentRecord.getMaterialFeeApprovedBt().subtract(rdmsBudgetResearch.getMaterialFeeApprovedBt()));
            parentRecord.setMaterialFeeApprovedZc(parentRecord.getMaterialFeeApprovedZc().subtract(rdmsBudgetResearch.getMaterialFeeApprovedZc()));
            parentRecord.setMaterialFeeBt(parentRecord.getMaterialFeeBt().subtract(rdmsBudgetResearch.getMaterialFeeBt()));
            parentRecord.setMaterialFeeZc(parentRecord.getMaterialFeeZc().subtract(rdmsBudgetResearch.getMaterialFeeZc()));

            parentRecord.setPowerFeeApprovedBt(parentRecord.getPowerFeeApprovedBt().subtract(rdmsBudgetResearch.getPowerFeeApprovedBt()));
            parentRecord.setPowerFeeApprovedZc(parentRecord.getPowerFeeApprovedZc().subtract(rdmsBudgetResearch.getPowerFeeApprovedZc()));
            parentRecord.setPowerFeeBt(parentRecord.getPowerFeeBt().subtract(rdmsBudgetResearch.getPowerFeeBt()));
            parentRecord.setPowerFeeZc(parentRecord.getPowerFeeZc().subtract(rdmsBudgetResearch.getPowerFeeZc()));

            parentRecord.setConferenceFeeApprovedBt(parentRecord.getConferenceFeeApprovedBt().subtract(rdmsBudgetResearch.getConferenceFeeApprovedBt()));
            parentRecord.setConferenceFeeApprovedZc(parentRecord.getConferenceFeeApprovedZc().subtract(rdmsBudgetResearch.getConferenceFeeApprovedZc()));
            parentRecord.setConferenceFeeBt(parentRecord.getConferenceFeeBt().subtract(rdmsBudgetResearch.getConferenceFeeBt()));
            parentRecord.setConferenceFeeZc(parentRecord.getConferenceFeeZc().subtract(rdmsBudgetResearch.getConferenceFeeZc()));

            parentRecord.setBusinessFeeApprovedBt(parentRecord.getBusinessFeeApprovedBt().subtract(rdmsBudgetResearch.getBusinessFeeApprovedBt()));
            parentRecord.setBusinessFeeApprovedZc(parentRecord.getBusinessFeeApprovedZc().subtract(rdmsBudgetResearch.getBusinessFeeApprovedZc()));
            parentRecord.setBusinessFeeBt(parentRecord.getBusinessFeeBt().subtract(rdmsBudgetResearch.getBusinessFeeBt()));
            parentRecord.setBusinessFeeZc(parentRecord.getBusinessFeeZc().subtract(rdmsBudgetResearch.getBusinessFeeZc()));

            parentRecord.setCooperationFeeApprovedBt(parentRecord.getCooperationFeeApprovedBt().subtract(rdmsBudgetResearch.getCooperationFeeApprovedBt()));
            parentRecord.setCooperationFeeApprovedZc(parentRecord.getCooperationFeeApprovedZc().subtract(rdmsBudgetResearch.getCooperationFeeApprovedZc()));
            parentRecord.setCooperationFeeBt(parentRecord.getCooperationFeeBt().subtract(rdmsBudgetResearch.getCooperationFeeBt()));
            parentRecord.setCooperationFeeZc(parentRecord.getCooperationFeeZc().subtract(rdmsBudgetResearch.getCooperationFeeZc()));

            parentRecord.setPropertyFeeApprovedBt(parentRecord.getPropertyFeeApprovedBt().subtract(rdmsBudgetResearch.getPropertyFeeApprovedBt()));
            parentRecord.setPropertyFeeApprovedZc(parentRecord.getPropertyFeeApprovedZc().subtract(rdmsBudgetResearch.getPropertyFeeApprovedZc()));
            parentRecord.setPropertyFeeBt(parentRecord.getPropertyFeeBt().subtract(rdmsBudgetResearch.getPropertyFeeBt()));
            parentRecord.setPropertyFeeZc(parentRecord.getPropertyFeeZc().subtract(rdmsBudgetResearch.getPropertyFeeZc()));

            parentRecord.setLaborFeeApprovedBt(parentRecord.getLaborFeeApprovedBt().subtract(rdmsBudgetResearch.getLaborFeeApprovedBt()));
            parentRecord.setLaborFeeApprovedZc(parentRecord.getLaborFeeApprovedZc().subtract(rdmsBudgetResearch.getLaborFeeApprovedZc()));
            parentRecord.setLaborFeeBt(parentRecord.getLaborFeeBt().subtract(rdmsBudgetResearch.getLaborFeeBt()));
            parentRecord.setLaborFeeZc(parentRecord.getLaborFeeZc().subtract(rdmsBudgetResearch.getLaborFeeZc()));

            parentRecord.setStaffFeeApprovedBt(parentRecord.getStaffFeeApprovedBt().subtract(rdmsBudgetResearch.getStaffFeeApprovedBt()));
            parentRecord.setStaffFeeApprovedZc(parentRecord.getStaffFeeApprovedZc().subtract(rdmsBudgetResearch.getStaffFeeApprovedZc()));
            parentRecord.setStaffFeeBt(parentRecord.getStaffFeeBt().subtract(rdmsBudgetResearch.getStaffFeeBt()));
            parentRecord.setStaffFeeZc(parentRecord.getStaffFeeZc().subtract(rdmsBudgetResearch.getStaffFeeZc()));

            parentRecord.setConsultingFeeApprovedBt(parentRecord.getConsultingFeeApprovedBt().subtract(rdmsBudgetResearch.getConsultingFeeApprovedBt()));
            parentRecord.setConsultingFeeApprovedZc(parentRecord.getConsultingFeeApprovedZc().subtract(rdmsBudgetResearch.getConsultingFeeApprovedZc()));
            parentRecord.setConsultingFeeBt(parentRecord.getConsultingFeeBt().subtract(rdmsBudgetResearch.getConsultingFeeBt()));
            parentRecord.setConsultingFeeZc(parentRecord.getConsultingFeeZc().subtract(rdmsBudgetResearch.getConsultingFeeZc()));

            parentRecord.setManagementFeeApprovedBt(parentRecord.getManagementFeeApprovedBt().subtract(rdmsBudgetResearch.getManagementFeeApprovedBt()));
            parentRecord.setManagementFeeApprovedZc(parentRecord.getManagementFeeApprovedZc().subtract(rdmsBudgetResearch.getManagementFeeApprovedZc()));
            parentRecord.setManagementFeeBt(parentRecord.getManagementFeeBt().subtract(rdmsBudgetResearch.getManagementFeeBt()));
            parentRecord.setManagementFeeZc(parentRecord.getManagementFeeZc().subtract(rdmsBudgetResearch.getManagementFeeZc()));

            parentRecord.setOtherFeeApprovedBt(parentRecord.getOtherFeeApprovedBt().subtract(rdmsBudgetResearch.getOtherFeeApprovedBt()));
            parentRecord.setOtherFeeApprovedZc(parentRecord.getOtherFeeApprovedZc().subtract(rdmsBudgetResearch.getOtherFeeApprovedZc()));
            parentRecord.setOtherFeeBt(parentRecord.getOtherFeeBt().subtract(rdmsBudgetResearch.getOtherFeeBt()));
            parentRecord.setOtherFeeZc(parentRecord.getOtherFeeZc().subtract(rdmsBudgetResearch.getOtherFeeZc()));

            rdmsBudgetResearchService.update(parentRecord);
        }

        //父级项目的附加费减去给子项目的部分
        parentProject.setAddCharge(parentProject.getAddCharge().subtract(projectSubproject.getAddCharge()));
        //父级项目功能开发费重新计算
        parentProject.setBudget(parentProject.getBudget().subtract(projectSubproject.getBudget()));
        //更新父项目预算信息
        rdmsSubprojectService.save(parentProject);

        //更新Character的子项目ID
        if (!CollectionUtils.isEmpty(subprojectDto.getCharacters())) {
            List<RdmsCharacterDto> characterDtos = subprojectDto.getCharacters();
            for (RdmsCharacterDto characterDto : characterDtos) {
                RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
                character.setSubprojectId(rdmsProjectSubproject.getId());
                character.setStatus(CharacterStatusEnum.SETUPED.getStatus());

                if (character.getPlanCompleteTime() == null) {
                    character.setPlanCompleteTime(adjustReleaseTime);
                }
                if (character.getPlanCompleteTime() != null && adjustReleaseTime.getTime() < character.getPlanCompleteTime().getTime()) {
                    character.setPlanCompleteTime(adjustReleaseTime);
                }
                String characterId = rdmsCharacterService.update(character);
                //填写文件的访问权限人员——项目经理
                if (!ObjectUtils.isEmpty(character.getFileListStr())) {
                    List<String> fileIdList = JSON.parseArray(character.getFileListStr(), String.class);
                    {
                        //设置文件授权 权限
                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                        roleUsersDto.setLoginUserId(null);
                        roleUsersDto.setReceiverId(null);
                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(character.getCustomerId());
                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                        roleUsersDto.setSuperId(null);
                        roleUsersDto.setIpmtId(null);
                        roleUsersDto.setPjmId(null);
                        roleUsersDto.setPdmId(subprojectDto.getProjectManagerId());
                        rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
                    }
                }

                //功能保密等级设置
                RdmsCharacterSecLevel characterSecLevel = new RdmsCharacterSecLevel();
                characterSecLevel.setProjectId(character.getProjectId());
                characterSecLevel.setSubprojectId(character.getSubprojectId());
                characterSecLevel.setCharacterId(characterId);
                characterSecLevel.setLevel(subprojectDto.getSecretLevel());
                rdmsCharacterSecLevelService.saveByCharacterId(characterSecLevel);

                //CharacterProcess
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(character.getId());
                characterProcess.setExecutorId(subprojectDto.getCreatorId());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                characterProcess.setDeep((int) characterProcessCount);
                characterProcess.setJobDescription("功能已经分配到子项目");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.DISTRIBUTE.getStatus());
                characterProcess.setNextNode(rdmsProjectSubproject.getProjectManagerId());
                rdmsCharacterProcessService.save(characterProcess);
            }
        }

        //创建子项目相关Gantt记录
        rdmsGanttService.createGanttRecordBySubproject(rdmsProjectSubproject);

        RdmsProjectSubprojectDto rdmsProjectSubprojectDto = CopyUtil.copy(rdmsProjectSubproject, RdmsProjectSubprojectDto.class);
        responseDto.setContent(rdmsProjectSubprojectDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/save-module-subproject")
    @Transactional
    public ResponseDto<RdmsProjectSubprojectDto> saveModuleSubproject(@RequestBody RdmsProjectSubprojectDto subprojectDto) {

        ValidatorUtil.require(subprojectDto.getCustomerId(), "公司ID");
        ValidatorUtil.require(subprojectDto.getProjectId(), "项目ID");
        ValidatorUtil.require(subprojectDto.getParent(), "上级项目ID");

        ValidatorUtil.require(subprojectDto.getLabel(), "子项目名称");
        ValidatorUtil.require(subprojectDto.getIntroduce(), "子项目概要");
        ValidatorUtil.require(subprojectDto.getProjectManagerId(), "子项目经理");
        ValidatorUtil.require(subprojectDto.getDeep(), "Deep值");
        ValidatorUtil.require(subprojectDto.getCreatorId(), "创建者");
        ValidatorUtil.require(subprojectDto.getProjectCode(), "项目Code");

        ResponseDto<RdmsProjectSubprojectDto> responseDto = new ResponseDto<>();
        RdmsProjectSubproject projectSubproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
        projectSubproject.setReleaseTime(null);
        //计算子项目总预算
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subprojectDto.getProjectId());
        RdmsProjectSubproject parentProject = rdmsSubprojectService.selectByPrimaryKey(subprojectDto.getParent());
        projectSubproject.setStatus(ProjectStatusEnum.SETUPED.getStatus());
        projectSubproject.setType(ProjectTypeEnum.SUB_PROJECT.getType());
        //添加项目通用字段
        projectSubproject.setPreProjectId(rdmsProject.getPreProjectId());
        projectSubproject.setSupervise(rdmsProject.getSupervise());
        projectSubproject.setProductManagerId(rdmsProject.getProductManagerId());
        projectSubproject.setDevVersion(rdmsProject.getDevVersion());
        projectSubproject.setSetupedTime(rdmsProject.getSetupedTime());
        projectSubproject.setRdType(rdmsProject.getRdType());

        projectSubproject.setBudget(BigDecimal.ZERO);
        projectSubproject.setAddCharge(BigDecimal.ZERO);
//        projectSubproject.setChargeRate(BigDecimal.ZERO);
        //创建子项目
        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.save(projectSubproject);

        RdmsBudgetResearchDto rdmsBudgetResearch = new RdmsBudgetResearchDto();
        rdmsBudgetResearch.setId(null);
        rdmsBudgetResearch.setCustomerId(rdmsProject.getCustomerId());
        rdmsBudgetResearch.setProjectId(rdmsProject.getId());
        rdmsBudgetResearch.setParentId(parentProject.getId());
        rdmsBudgetResearch.setSubprojectId(rdmsProjectSubproject.getId());  //子项目ID
        rdmsBudgetResearch.setIsRoot(0);
        rdmsBudgetResearchService.save(rdmsBudgetResearch);

        RdmsProjectSubprojectDto rdmsProjectSubprojectDto = CopyUtil.copy(rdmsProjectSubproject, RdmsProjectSubprojectDto.class);
        responseDto.setContent(rdmsProjectSubprojectDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 根据后台数据,计算实时的项目执行情况数据
     *
     * @param subprojectId
     * @return
     */
    @PostMapping("/calSubprojectSelfBudgetActiveExeInfo/{subprojectId}")
    public ResponseDto<RdmsBudgetExeSummaryDto> getBudgetActiveExeSummaryBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<RdmsBudgetExeSummaryDto> responseDto = new ResponseDto<>();
        RdmsBudgetExeSummaryDto budgetExeSummaryBySubprojectId = rdmsManhourService.calSubprojectSelfBudgetActiveExeInfo(subprojectId);
        responseDto.setContent(budgetExeSummaryBySubprojectId);
        return responseDto;
    }

    /**
     * 根据后台数据,计算完工的项目执行情况数据
     *
     * @param subprojectId
     * @return
     */
    @PostMapping("/getBudgetExeSummaryBySubprojectId/{subprojectId}")
    public ResponseDto<RdmsBudgetExeSummaryDto> getCharacterBudgetExeSummary(@PathVariable String subprojectId) {
        ResponseDto<RdmsBudgetExeSummaryDto> responseDto = new ResponseDto<>();
        RdmsBudgetExeSummaryDto budgetExeSummaryBySubprojectId = rdmsManhourService.getBudgetExeSummaryBySubprojectId(subprojectId);
        responseDto.setContent(budgetExeSummaryBySubprojectId);
        return responseDto;
    }

    @PostMapping("/getTakePartInSubprojectList/{customerUserId}")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getTakePartInSubprojectList(@PathVariable String customerUserId) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectSubprojectDto> takePartInSubprojectList = rdmsSubprojectService.getTakePartInSubprojectList(customerUserId);
        responseDto.setContent(takePartInSubprojectList);
        return responseDto;
    }

    @PostMapping("/getCanBeOutsourcingFlag/{subproject}")
    public ResponseDto<Boolean> getCanBeOutsourcingFlag(@PathVariable String subproject) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        Boolean canBeOutsourcingFlag = rdmsSubprojectService.getCanBeOutsourcingFlag(subproject);
        responseDto.setContent(canBeOutsourcingFlag);
        return responseDto;
    }

    /**
     * 从数据库项目评审记录中读取相应的项目执行数据
     *
     * @param subprojectId
     * @return
     */
    @PostMapping("/getSubprojectBudgetExeInfoList/{subprojectId}")
    public ResponseDto<RdmsBudgetExeDto> getSubprojectBudgetExeInfoList(@PathVariable String subprojectId) {
        ResponseDto<RdmsBudgetExeDto> responseDto = new ResponseDto<>();
        RdmsBudgetExeDto subprojectBudgetExeSummary = rdmsManhourService.getSubprojectBudgetExeSummary(subprojectId);
        responseDto.setContent(subprojectBudgetExeSummary);
        return responseDto;
    }

    @PostMapping("/getSubprojectBudgetExeSummary_researchExe/{subprojectId}")
    public ResponseDto<RdmsBudgetExeDto> getSubprojectBudgetExeSummary_researchExe(@PathVariable String subprojectId) {
        ResponseDto<RdmsBudgetExeDto> responseDto = new ResponseDto<>();
        RdmsBudgetExeDto subprojectBudgetExeSummary = rdmsManhourService.getSubprojectBudgetExeSummary_researchExe(subprojectId);
        responseDto.setContent(subprojectBudgetExeSummary);
        return responseDto;
    }

    @PostMapping("/getSubprojectBoardInfo/{subprojectId}")
    public ResponseDto<RdmsBoardSubprojectDto> getSubprojectBoardInfo(@PathVariable String subprojectId) {
        ResponseDto<RdmsBoardSubprojectDto> responseDto = new ResponseDto<>();
        RdmsBoardSubprojectDto subprojectBoardInfo = rdmsSubprojectService.getSubprojectBoardInfo_direct(subprojectId);
        responseDto.setContent(subprojectBoardInfo);
        return responseDto;
    }

    @PostMapping("/saveOutSourcing")
    public ResponseDto saveOutSourcing(@RequestBody RdmsSubprojectService.OutsourceParameter parameter) {
        ResponseDto responseDto = new ResponseDto<>();
        rdmsSubprojectService.saveOutSourcing(parameter);
        responseDto.setContent("");
        return responseDto;
    }

    /**
     * 根据后台数据, 计算项目执行情况的简要关键信息
     *
     * @param subprojectId
     * @return
     */
    @PostMapping("/getSubprojectAndChildrenExeSimpleInfo")
    public ResponseDto<List<RdmsSubprojectExeSimpleInfoDto>> getSubprojectAndChildrenExeSimpleInfo(@RequestParam String subprojectId, String loginUserId) {
        ResponseDto<List<RdmsSubprojectExeSimpleInfoDto>> responseDto = new ResponseDto<>();
        List<RdmsSubprojectExeSimpleInfoDto> subprojectExeSimpleInfoDtoList = new ArrayList<>();
        List<String> allChildrenSubprojectIdList = rdmsSubprojectService.getAllChildrenSubprojectId(subprojectId);

        for (String itemId : allChildrenSubprojectIdList) {
            RdmsSubprojectExeSimpleInfoDto childSubproject = rdmsSubprojectService.getSubprojectExeSimpleInfo(itemId, loginUserId);
            subprojectExeSimpleInfoDtoList.add(childSubproject);
        }
        responseDto.setContent(subprojectExeSimpleInfoDtoList);
        return responseDto;
    }

    @PostMapping("/getProjectAllReviewInfoList/{subprojectId}")
    public ResponseDto<List<RdmsBudgetExeDto>> getProjectAllReviewInfoList(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsBudgetExeDto>> responseDto = new ResponseDto<>();
        List<RdmsBudgetExe> childrenList = rdmsBudgetExeService.getChildrenListByParentId(subprojectId);
        List<RdmsBudgetExeDto> rdmsBudgetExeDtos = CopyUtil.copyList(childrenList, RdmsBudgetExeDto.class);
        List<RdmsBudgetExeDto> collect = rdmsBudgetExeDtos.stream().sorted(Comparator.comparing(RdmsBudgetExeDto::getCreateTime).reversed()).collect(Collectors.toList());
        responseDto.setContent(collect);
        return responseDto;
    }

    @PostMapping("/getReviewJobInfo")
    public ResponseDto<RdmsHmiJobItemDocPageDto> getLastReviewInfo(@RequestParam String subprojectId, String loginUserId) {
        ResponseDto<RdmsHmiJobItemDocPageDto> responseDto = new ResponseDto<>();
        RdmsHmiJobItemDocPageDto lastReviewInfo = rdmsSubprojectService.getReviewJobInfo(subprojectId, loginUserId);
        responseDto.setContent(lastReviewInfo);
        return responseDto;
    }

    @PostMapping("/listProjectTaskJobChargeBySubProjectId/{subprojectId}")
    public ResponseDto<BigDecimal> listProjectTaskJobChargeBySubProjectId(@PathVariable String subprojectId) {
        ResponseDto<BigDecimal> responseDto = new ResponseDto<>();
        if (ObjectUtils.isEmpty(subprojectId)) {
            return responseDto;
        }

        BigDecimal subprojectSelfActiveAccountCharge = rdmsManhourService.getSubprojectTaskActiveAccountCharge(subprojectId);
        responseDto.setContent(subprojectSelfActiveAccountCharge);
        return responseDto;
    }

    @PostMapping("/getCharacterListBySubProjectReviewJobitem/{reviewJobitemId}")
    public ResponseDto<List<RdmsBudgetExe>> getCharacterListBySubProjectReviewJobitem(@PathVariable String reviewJobitemId) {
        ResponseDto<List<RdmsBudgetExe>> responseDto = new ResponseDto<>();

        RdmsJobItem reviewJobitem = rdmsJobItemService.selectByPrimaryKey(reviewJobitemId);
        List<RdmsBudgetExe> characterBudgetList = new ArrayList<>();

        List<String> subprojectIdCollect = rdmsSubprojectService.getAllChildrenSubprojectId(reviewJobitem.getSubprojectId());

        for (String subprojectId : subprojectIdCollect) {
            List<RdmsBudgetExe> characterBudgetExeInfoList = rdmsBudgetExeService.getItemListByParentAndeDocType(subprojectId, DocTypeEnum.CHARACTER);
            characterBudgetList.addAll(characterBudgetExeInfoList);
        }
        responseDto.setContent(characterBudgetList);
        return responseDto;
    }

    @PostMapping("/getCharacterListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsBudgetExe>> getCharacterListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsBudgetExe>> responseDto = new ResponseDto<>();
        List<RdmsBudgetExe> characterBudgetList = new ArrayList<>();
        List<RdmsBudgetExe> characterBudgetExeInfoList = rdmsBudgetExeService.getItemListByParentAndeDocType(subprojectId, DocTypeEnum.CHARACTER);
        characterBudgetList.addAll(characterBudgetExeInfoList);
        responseDto.setContent(characterBudgetList);
        return responseDto;
    }

    @PostMapping("/getSubProjectCbbList/{subprojectId}")
    public ResponseDto<RdmsProjectSubprojectDto> getSubProjectCbbList(@PathVariable String subprojectId) {
        ResponseDto<RdmsProjectSubprojectDto> responseDto = new ResponseDto<>();
        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        RdmsProjectSubprojectDto rdmsProjectSubprojectDto = CopyUtil.copy(rdmsProjectSubproject, RdmsProjectSubprojectDto.class);
        if(!ObjectUtils.isEmpty(rdmsProjectSubproject) && !ObjectUtils.isEmpty(rdmsProjectSubproject.getModuleIdListStr())){
            List<String> cbbIdList = JSON.parseArray(rdmsProjectSubproject.getModuleIdListStr(), String.class);
            if (!CollectionUtils.isEmpty(cbbIdList)) {
                try {
                    List<RdmsCbbDto> cbbListByIdList = rdmsCbbService.getCbbListByIdList(rdmsProjectSubproject.getCustomerId(), cbbIdList);
                    rdmsProjectSubprojectDto.setCbbList(cbbListByIdList);
                } catch (Exception e) {
                    // 记录异常日志或进行其他异常处理
                    LOG.info("Error occurred while fetching character list: {}", e.getMessage());
                }
            }
        }
        responseDto.setContent(rdmsProjectSubprojectDto);
        return responseDto;
    }

    @PostMapping("/editSubProjectInfo")
    @Transactional
    public ResponseDto editSubProjectInfo(@RequestBody RdmsProjectSubprojectDto subprojectDto) {

        ValidatorUtil.require(subprojectDto.getCustomerId(), "公司ID");
        ValidatorUtil.require(subprojectDto.getProjectId(), "项目ID");
        ValidatorUtil.require(subprojectDto.getLabel(), "子项目名称");
        ValidatorUtil.require(subprojectDto.getIntroduce(), "子项目概要");
        ValidatorUtil.require(subprojectDto.getProjectManagerId(), "子项目经理");
        ValidatorUtil.require(subprojectDto.getTestManagerId(), "测试主管");

        ResponseDto responseDto = new ResponseDto<>();
        RdmsProjectSubproject projectSubproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
        projectSubproject.setProductManagerId(subprojectDto.getOriginProductManagerId());  //产品经理保持不变
        projectSubproject.setSupervise(subprojectDto.getOriginIpmtId());
        //更新子项目
        rdmsSubprojectService.updateByPrimaryKeySelective(projectSubproject);

        {//如果项目经理发生了变化
            if (!subprojectDto.getProjectManagerId().equals(subprojectDto.getOriginProjectManagerId())) {
                //修改直接下级子项目的creatorId
                List<RdmsProjectSubproject> subprojectListByParentId = rdmsSubprojectService.getSubprojectListByParentId(subprojectDto.getId());
                if(!CollectionUtils.isEmpty(subprojectListByParentId)){
                    for(RdmsProjectSubproject subproject: subprojectListByParentId){
                        subproject.setCreatorId(subprojectDto.getProjectManagerId());
                        rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
                    }
                }

                //该项目项下的所有功能开发的执行人
                //通过原始子项目ID和原始项目经理ID获取相应的功能条目
                List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectIdAndNextNodeId(subprojectDto.getId(), subprojectDto.getOriginProjectManagerId());
                if(!CollectionUtils.isEmpty(characterList)){
                    for (RdmsCharacterDto characterDto : characterList) {
                        characterDto.setNextNode(subprojectDto.getProjectManagerId());
                        rdmsCharacterService.updateByPrimaryKeySelective(characterDto);
                    }
                }

                //将该项目项下所有任务的项目经理更新为新的项目经理;
                List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectId(subprojectDto.getId(), null, null);
                if (!CollectionUtils.isEmpty(jobitemList)) {
                    RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(subprojectDto.getId());
                    for (RdmsJobItemDto jobItemDto : jobitemList) {
                        //修改项目经理
                        jobItemDto.setProjectManagerId(rdmsProjectSubproject.getProjectManagerId());
                        rdmsJobItemService.updateByPrimaryKeySelective(jobItemDto);

                        //获取Job的附件和property的附件
                        List<String> fileIds = new ArrayList<>();
                        List<String> fileIdList = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
                        if(!CollectionUtils.isEmpty(fileIdList)){
                            fileIds.addAll(fileIdList);
                        }
                        List<String> propertyFileIdList = rdmsJobItemService.getPropertyFileIdList(jobItemDto.getId());
                        if(!CollectionUtils.isEmpty(fileIdList)){
                            fileIds.addAll(propertyFileIdList);
                        }
                        //查看文件的授权, 授权给原来项目经理的,改为授权给新项目经理
                        if(!CollectionUtils.isEmpty(fileIds)){
                            for(String fileId: fileIds){
                                RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileId);
                                if(!(ObjectUtils.isEmpty(byFileId) || ObjectUtils.isEmpty(byFileId.getAuthIdsStr()))){
                                    String replace = byFileId.getAuthIdsStr().replace(subprojectDto.getOriginProjectManagerId(), subprojectDto.getProjectManagerId());
                                    byFileId.setAuthIdsStr(replace);
                                    rdmsFileAuthService.updateByPrimaryKeySelective(byFileId);
                                }
                            }
                        }
                    }
                }
                //处理未完成的任务
                List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(JobItemStatusEnum.PLAN);
                statusEnumList.add(JobItemStatusEnum.HANDLING);
                statusEnumList.add(JobItemStatusEnum.SUBMIT);
                statusEnumList.add(JobItemStatusEnum.TESTING);
                statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.SUB_RECHECK);
                statusEnumList.add(JobItemStatusEnum.EVALUATE);
                statusEnumList.add(JobItemStatusEnum.APPROVED);
                List<RdmsJobItem> jobitemListByNextNodeId = rdmsJobItemService.getJobitemListByNextNodeId(subprojectDto.getOriginProjectManagerId(), statusEnumList);
                if (!CollectionUtils.isEmpty(jobitemListByNextNodeId)) {
                    for (RdmsJobItem jobItem : jobitemListByNextNodeId) {
                        jobItem.setNextNode(subprojectDto.getProjectManagerId());
                        rdmsJobItemService.updateByPrimaryKeySelective(jobItem);
                    }
                }
                //处理未完成的物料申请
                {
                    List<RdmsMaterialManageDto> listByNextNodeId = rdmsMaterialManageService.getListByNextNodeIdForApprover(subprojectDto.getOriginProjectManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsMaterialManageDto materialManageDto: listByNextNodeId){
                            RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                            materialManage.setNextNode(subprojectDto.getProjectManagerId());
                            rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);
                        }
                    }
                }
                //处理未完成的费用申请
                {
                    List<RdmsFeeManage> listByNextNodeId = rdmsFeeManageService.getListByNextNodeId(subprojectDto.getOriginProjectManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsFeeManage feeManageDto: listByNextNodeId){
                            feeManageDto.setNextNode(subprojectDto.getProjectManagerId());
                            rdmsFeeManageService.updateByPrimaryKeySelective(feeManageDto);
                        }
                    }
                }

            }
        }

        {//如果测试主管发生了变化
            if (!subprojectDto.getTestManagerId().equals(subprojectDto.getOriginTestManagerId())) {
                //将该项目项下所有任务的测试主管更新为新的测试主管;
                List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectId(subprojectDto.getId(), null, null);
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(subprojectDto.getId());
                if (!CollectionUtils.isEmpty(jobitemList)) {
                    for (RdmsJobItemDto jobItemDto : jobitemList) {
                        //修改测试主管
                        jobItemDto.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
                        rdmsJobItemService.updateByPrimaryKeySelective(jobItemDto);
                    }
                }
            }
            //处理未完成的任务
            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.PLAN);
            statusEnumList.add(JobItemStatusEnum.HANDLING);
            statusEnumList.add(JobItemStatusEnum.SUBMIT);
            statusEnumList.add(JobItemStatusEnum.TESTING);
            statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
            statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
            statusEnumList.add(JobItemStatusEnum.SUB_RECHECK);
            statusEnumList.add(JobItemStatusEnum.EVALUATE);
            statusEnumList.add(JobItemStatusEnum.APPROVED);
            List<RdmsJobItem> jobitemListByNextNodeId = rdmsJobItemService.getJobitemListByNextNodeId(subprojectDto.getOriginTestManagerId(), statusEnumList);
            if (!CollectionUtils.isEmpty(jobitemListByNextNodeId)) {
                for (RdmsJobItem jobItem : jobitemListByNextNodeId) {
                    jobItem.setNextNode(subprojectDto.getTestManagerId());
                    rdmsJobItemService.updateByPrimaryKeySelective(jobItem);
                }
            }
            //处理未完成的物料申请
            {
                List<RdmsMaterialManageDto> listByNextNodeId = rdmsMaterialManageService.getListByNextNodeIdForApprover(subprojectDto.getOriginTestManagerId());
                if(!CollectionUtils.isEmpty(listByNextNodeId)){
                    for(RdmsMaterialManageDto materialManageDto: listByNextNodeId){
                        RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                        materialManage.setNextNode(subprojectDto.getTestManagerId());
                        rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);
                    }
                }
            }
            //处理未完成的费用申请
            {
                List<RdmsFeeManage> listByNextNodeId = rdmsFeeManageService.getListByNextNodeId(subprojectDto.getOriginTestManagerId());
                if(!CollectionUtils.isEmpty(listByNextNodeId)){
                    for(RdmsFeeManage feeManageDto: listByNextNodeId){
                        feeManageDto.setNextNode(subprojectDto.getTestManagerId());
                        rdmsFeeManageService.updateByPrimaryKeySelective(feeManageDto);
                    }
                }
            }
        }

        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 列表查询申请立项的项目
     */
    @PostMapping("/getSubProjectForPjm")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getSubProjectForPjm(@RequestBody RdmsHmiIdsDto idsDto) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<ProjectStatusEnum> projectStatusEnums = new ArrayList<>();
        projectStatusEnums.add(ProjectStatusEnum.SETUPED);
        projectStatusEnums.add(ProjectStatusEnum.ONGOING);
        projectStatusEnums.add(ProjectStatusEnum.SUSPEND);
        projectStatusEnums.add(ProjectStatusEnum.DEV_COMPLETE);
        projectStatusEnums.add(ProjectStatusEnum.INTEGRATION);
        projectStatusEnums.add(ProjectStatusEnum.REVIEW_SUBP);
        projectStatusEnums.add(ProjectStatusEnum.REVIEW_PRO);
        projectStatusEnums.add(ProjectStatusEnum.COMPLETE);
//        projectStatusEnums.add(ProjectStatusEnum.CLOSE);
        projectStatusEnums.add(ProjectStatusEnum.OUT_SOURCE);

        List<RdmsProjectSubprojectDto> projectList = rdmsSubprojectService.getMixProjectListByPjmAndStatusList(idsDto.getCustomerId(), idsDto.getProjectManagerId(), projectStatusEnums);
        for (RdmsProjectSubprojectDto subprojectDto : projectList) {
            if (!subprojectDto.getRdType().equals(RdTypeEnum.MODULE.getType())) {
                subprojectDto.setSumBudget(subprojectDto.getBudget().add(subprojectDto.getAddCharge()));
            }
            Integer applicationNum = rdmsMaterialManageService.getApplicationNumBySubprojectId(subprojectDto.getId());
            subprojectDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getApplicationNumBySubprojectId(subprojectDto.getId());
            subprojectDto.setFeeAppNum(feeApplicationNum);
        }
        responseDto.setContent(projectList);
        return responseDto;
    }

   @PostMapping("/getSubprojectExeData")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getSubprojectExeDataByPdm(@RequestBody RdmsHmiIdsDto idsDto) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<ProjectStatusEnum> projectStatusEnums = new ArrayList<>();
        projectStatusEnums.add(ProjectStatusEnum.SETUPED);
        projectStatusEnums.add(ProjectStatusEnum.ONGOING);
        projectStatusEnums.add(ProjectStatusEnum.SUSPEND);
        projectStatusEnums.add(ProjectStatusEnum.DEV_COMPLETE);
        projectStatusEnums.add(ProjectStatusEnum.INTEGRATION);
        projectStatusEnums.add(ProjectStatusEnum.REVIEW_SUBP);
        projectStatusEnums.add(ProjectStatusEnum.REVIEW_PRO);
        projectStatusEnums.add(ProjectStatusEnum.COMPLETE);
//        projectStatusEnums.add(ProjectStatusEnum.CLOSE);
        projectStatusEnums.add(ProjectStatusEnum.OUT_SOURCE);


        List<RdmsProjectSubprojectDto> projectList = rdmsSubprojectService.getMixProjectListByPdmAndStatusList(idsDto.getCustomerId(), idsDto.getProductManagerId(), projectStatusEnums);
        for (RdmsProjectSubprojectDto subprojectDto : projectList) {
            if (!subprojectDto.getRdType().equals(RdTypeEnum.MODULE.getType())) {
                subprojectDto.setSumBudget(subprojectDto.getBudget().add(subprojectDto.getAddCharge()));
            }
            Integer applicationNum = rdmsMaterialManageService.getApplicationNumBySubprojectId(subprojectDto.getId());
            subprojectDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getApplicationNumBySubprojectId(subprojectDto.getId());
            subprojectDto.setFeeAppNum(feeApplicationNum);
        }
        responseDto.setContent(projectList);
        return responseDto;
    }

    @PostMapping("/getArchiveSubProjectForPjm")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getArchiveSubProjectForPjm(@RequestBody RdmsHmiIdsDto idsDto) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<ProjectStatusEnum> projectStatusEnums = new ArrayList<>();
        projectStatusEnums.add(ProjectStatusEnum.ARCHIVED);
        List<RdmsProjectSubprojectDto> projectList = rdmsSubprojectService.getMixProjectListByPjmAndStatusList(idsDto.getCustomerId(), idsDto.getProjectManagerId(), projectStatusEnums);
        responseDto.setContent(projectList);
        return responseDto;
    }

    /**
     *
     */
    @PostMapping("/ListSubprojectByIds")
    public ResponseDto<List<RdmsProjectSubprojectDto>> ListSubprojectByIds(@RequestBody RdmsHmiIdsDto idsDto) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectSubprojectDto> rdmsProjectSubprojectDtos = rdmsSubprojectService.ListSubprojectByIds(idsDto);
        responseDto.setContent(rdmsProjectSubprojectDtos);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getSubProjectInfo")
    public ResponseDto<RdmsProjectSubprojectDto> getSubProjectInfo(@RequestParam String subProjectId, String loginUserId) {
        ResponseDto<RdmsProjectSubprojectDto> responseDto = new ResponseDto<>();

        RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.selectByPrimaryKey(subProjectId);
        if (!ObjectUtils.isEmpty(subProjectInfo)) {
            RdmsProjectSubprojectDto projectSubprojectDto = CopyUtil.copy(subProjectInfo, RdmsProjectSubprojectDto.class);
            //当前项目保密等级
            List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getBySubProjectId(subProjectId);
            if(!CollectionUtils.isEmpty(rdmsProjectSecLevels)){
                projectSubprojectDto.setSecretLevel(rdmsProjectSecLevels.get(0).getLevel());
            }else{
                projectSubprojectDto.setSecretLevel(0);
            }
            //父级项目保密等级
            List<RdmsProjectSecLevel> byProjectId = rdmsProjectSecLevelService.getByProjectId(subProjectInfo.getProjectId());
            if(!CollectionUtils.isEmpty(byProjectId)){
                projectSubprojectDto.setMainSecretLevel(byProjectId.get(0).getLevel());
            }else{
                projectSubprojectDto.setMainSecretLevel(0);
            }

            List<CharacterStatusEnum> statusList = new ArrayList<>();
            statusList.add(CharacterStatusEnum.SETUPED);
            statusList.add(CharacterStatusEnum.DECOMPOSED);
            statusList.add(CharacterStatusEnum.ITERATING);
            statusList.add(CharacterStatusEnum.DEVELOPING);
            statusList.add(CharacterStatusEnum.INTEGRATION);
            statusList.add(CharacterStatusEnum.DEV_COMPLETE);
            List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectIdAndStatusList(subProjectId, statusList);
            if (!CollectionUtils.isEmpty(characterList)) {
                projectSubprojectDto.setCharacters(characterList);
            }
            rdmsSubprojectService.appendSubProjectInfo(subProjectInfo, projectSubprojectDto, loginUserId);

            /*List<RdmsMilestoneDto> milestoneList = rdmsSubprojectService.getSubprojectMilestoneList(subProjectId);
            projectSubprojectDto.setMilestoneList(milestoneList);*/
            responseDto.setContent(projectSubprojectDto);
        } else {
            responseDto.setContent(null);
        }
        return responseDto;
    }

    @PostMapping("/getProjectExecuteInfo/{subProjectId}")
    public ResponseDto<RdmsHmiProjectExecuteInfoDto> getProjectExecuteInfo(@PathVariable String subProjectId) {
        ResponseDto<RdmsHmiProjectExecuteInfoDto> responseDto = new ResponseDto<>();
        //1. 读取当前项目的功能列表
        //1. 列出开发一条功能的所有工单
        //1. 计算每一条工单的实际工时
        //2. 计算开发一条功能的全部计划工时 和 实际工时
        //3.
        //2. 统计全部完工工时
        //1. 读取当前项目的子项目

        //2.


        //TODO 完成函数的编写
        RdmsHmiProjectExecuteInfoDto projectExecuteInfoDto = new RdmsHmiProjectExecuteInfoDto();
        projectExecuteInfoDto.setId(subProjectId);
        projectExecuteInfoDto.setProjectCode("PJ-2023-001");
        projectExecuteInfoDto.setSubProjectName("Demo一级子项目");
        projectExecuteInfoDto.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
        projectExecuteInfoDto.setSumJobNumber(78);
        projectExecuteInfoDto.setComplateJobNumber(78);
        projectExecuteInfoDto.setManhourExeRate(0.95);
        projectExecuteInfoDto.setManhourInTimeRate(1.17);
        projectExecuteInfoDto.setSumCharacterNumber(23);
        projectExecuteInfoDto.setComplateCharacterNumber(23);
        projectExecuteInfoDto.setBudgetExeRate(1.06);
        projectExecuteInfoDto.setBudgetInTimeRate(1.15);

        List<RdmsHmiProjectExecuteInfoDto> childrenInfoList = new ArrayList<>();
        RdmsHmiProjectExecuteInfoDto copy = CopyUtil.copy(projectExecuteInfoDto, RdmsHmiProjectExecuteInfoDto.class);
        copy.setId("qwertyuu");
        copy.setSubProjectName("Demo二级子项目1");
        childrenInfoList.add(copy);
        RdmsHmiProjectExecuteInfoDto copy1 = CopyUtil.copy(copy, RdmsHmiProjectExecuteInfoDto.class);
        copy1.setId("asdfghjk");
        copy1.setSubProjectName("Demo二级子项目2");
        childrenInfoList.add(copy1);

        projectExecuteInfoDto.setChildrenInfoList(childrenInfoList);

        responseDto.setContent(projectExecuteInfoDto);
        return responseDto;
    }

    @PostMapping("/getSubProjectTree/{subProjectId}")
    public ResponseDto<List<RdmsHmiTree<RdmsHmiTreeInfoDto>>> getSubProjectTree(@PathVariable String subProjectId) {
        ResponseDto<List<RdmsHmiTree<RdmsHmiTreeInfoDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeList = rdmsSubprojectService.getSubProjectTreeList(subProjectId);
        responseDto.setContent(projectTreeList);
        return responseDto;
    }

    @PostMapping("/getNextLevelSubproject/{subProjectId}")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getNextLevelSubproject(@PathVariable String subProjectId) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectSubprojectDto> nextLevelSubproject = rdmsSubprojectService.getNextLevelSubproject(subProjectId);
        responseDto.setContent(nextLevelSubproject);
        return responseDto;
    }

    @PostMapping("/getSubProjectDetailInfo/{subprojectId}")
    public ResponseDto<RdmsProjectSubprojectDto> getSubProjectDetailInfo(@PathVariable String subprojectId) {
        ResponseDto<RdmsProjectSubprojectDto> responseDto = new ResponseDto<>();
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        RdmsProjectSubprojectDto subprojectInfoDto = CopyUtil.copy(subproject, RdmsProjectSubprojectDto.class);
        if (subprojectInfoDto.getProjectManagerId() != null) {
            RdmsCustomerUser projectManager = rdmsCustomerUserService.selectByPrimaryKey(subprojectInfoDto.getProjectManagerId());
            subprojectInfoDto.setProjectManagerName(projectManager.getTrueName());
        }
        if (subprojectInfoDto.getProductManagerId() != null) {
            RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(subprojectInfoDto.getProductManagerId());
            subprojectInfoDto.setProductManagerName(productManager.getTrueName());
        }
        if (subprojectInfoDto.getSupervise() != null) {
            RdmsCustomerUser supervise = rdmsCustomerUserService.selectByPrimaryKey(subprojectInfoDto.getSupervise());
            subprojectInfoDto.setSuperviseName(supervise.getTrueName());
        }

        String keyMemberListStr = subproject.getKeyMemberListStr();
        List<String> keyMemberList = JSON.parseArray(keyMemberListStr, String.class);
        List<RdmsHmiNameAndIdDto> keyMemberNameIdList = new ArrayList<>();
        StringBuilder keyMemberNameListStr = new StringBuilder();
        for (String userId : keyMemberList) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(userId);
            RdmsHmiNameAndIdDto nameAndIdDto = new RdmsHmiNameAndIdDto(rdmsCustomerUser.getId(), rdmsCustomerUser.getTrueName());
            keyMemberNameIdList.add(nameAndIdDto);

            keyMemberNameListStr.append(nameAndIdDto.getName()).append(" ");
        }
        subprojectInfoDto.setKeyMemberList(keyMemberNameIdList);
        subprojectInfoDto.setKeyMemberListStr(keyMemberNameListStr.toString());

        responseDto.setContent(subprojectInfoDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getSubProjectSimpleInfo/{subProjectId}")
    public ResponseDto<RdmsProjectSubprojectDto> getSubProjectSimpleInfo(@PathVariable String subProjectId) {
        ResponseDto<RdmsProjectSubprojectDto> responseDto = new ResponseDto<>();
        RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(subProjectId);
        if (!ObjectUtils.isEmpty(subProjectInfo)) {
            RdmsProjectSubprojectDto projectSubprojectDto = CopyUtil.copy(subProjectInfo, RdmsProjectSubprojectDto.class);
            if (!ObjectUtils.isEmpty(projectSubprojectDto)) {
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectSubprojectDto.getProjectId());
                projectSubprojectDto.setProductManagerId(rdmsProject.getProductManagerId());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                projectSubprojectDto.setProductManagerName(rdmsCustomerUser.getTrueName());

                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(projectSubprojectDto.getProjectManagerId());
                projectSubprojectDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            }
            responseDto.setContent(projectSubprojectDto);
        } else {
            responseDto.setContent(null);
        }
        return responseDto;
    }

    @PostMapping("/getModuleSubProjectInfo/{customerId}")
    public ResponseDto<RdmsProjectSubproject> getModuleSubProjectInfo(@PathVariable String customerId) {
        ResponseDto<RdmsProjectSubproject> responseDto = new ResponseDto<>();
        RdmsProjectSubproject moduleSubProject = rdmsSubprojectService.getModuleSubProject(customerId);
        if (!ObjectUtils.isEmpty(moduleSubProject)) {
            responseDto.setContent(moduleSubProject);
        } else {
            responseDto.setContent(null);
        }
        return responseDto;
    }

    @PostMapping("/getSubprojectSelfRemainBudget/{subProjectId}")
    public ResponseDto<BigDecimal> getSubprojectSelfRemainBudget(@PathVariable String subProjectId) {
        ResponseDto<BigDecimal> responseDto = new ResponseDto<>();
        BigDecimal subprojectSelfRemainBudget = rdmsManhourService.getSubprojectSelfRemainBudget(subProjectId);
        responseDto.setContent(subprojectSelfRemainBudget);
        return responseDto;
    }

    @Transactional
    @PostMapping("/getSubprojectDevelopCompleteStatus/{subprojectId}")
    public ResponseDto<Boolean> getSubprojectDevelopCompleteStatus(@PathVariable String subprojectId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        boolean devComplateStatusFlag = rdmsSubprojectService.getDevComplateStatusFlag(subprojectId);
        responseDto.setContent(devComplateStatusFlag);
        return responseDto;
    }

    @Transactional
    @PostMapping("/getSubprojectIntCompleteStatus/{subprojectId}")
    public ResponseDto<Boolean> getSubprojectIntCompleteStatus(@PathVariable String subprojectId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        boolean devComplateFlag = rdmsSubprojectService.getDevComplateStatusFlag(subprojectId);
        //BOM编辑完成
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        boolean intComplateFlag = false;
        if (!ObjectUtils.isEmpty(subproject) && !ObjectUtils.isEmpty(subproject.getBomStatus()) && subproject.getBomStatus().equals(CharacterBomStatusEnum.SUBMIT.getStatus())) {
            intComplateFlag = true;
        }

        responseDto.setContent(devComplateFlag && intComplateFlag);
        return responseDto;
    }

    /**
     * 查询依据CharacterId创建的工单, 并且这些工单处于submit状态
     */
    @PostMapping("/getNumOfReviewSubprojectByNextNode/{nextNode}")
    public ResponseDto<Integer> getNumOfReviewSubprojectByNextNode(@PathVariable String nextNode) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        List<ProjectStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(ProjectStatusEnum.REVIEW_SUBP);
        Integer reviewNum = rdmsSubprojectService.getNumOfSubProjectByNextNodeAndStatus(nextNode, statusEnumList);
        responseDto.setContent(reviewNum);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @Transactional
    @PostMapping("/getSubprojectReviewInfoList")
    public ResponseDto<List<RdmsJobItemDto>> getSubprojectReviewInfoList(@RequestParam String subProjectId, String loginUserId) {
        ResponseDto<List<RdmsJobItemDto>> responseDto = new ResponseDto<>();
        List<JobItemStatusEnum> statusList = new ArrayList<>();
        statusList.add(JobItemStatusEnum.COMPLETED);
        statusList.add(JobItemStatusEnum.ARCHIVED);

        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.REVIEW);

        List<RdmsJobItemDto> reviewJobitemDetailList = new ArrayList<>();
        List<RdmsJobItemDto> reviewJobitemList = rdmsJobItemService.getJobitemListBySubProjectId(subProjectId, statusList, typeList);
        if (!CollectionUtils.isEmpty(reviewJobitemList)) {
            List<RdmsJobItemDto> jobItemDtos = reviewJobitemList.stream().sorted(Comparator.comparing(RdmsJobItemDto::getCreateTime).reversed()).collect(Collectors.toList());
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                RdmsJobItemDto jobitemDetailInfo = rdmsJobItemService.getReviewJobitemDetailInfo(jobItemDto.getId(), loginUserId);
                reviewJobitemDetailList.add(jobitemDetailInfo);
            }
        }
        responseDto.setContent(reviewJobitemDetailList);
        return responseDto;
    }

    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsProjectSubprojectDto>> list(@RequestBody PageDto<RdmsProjectSubprojectDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        rdmsSubprojectService.customerSubProjectList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listSubProjectByPJM")
    public ResponseDto<PageDto<RdmsProjectSubprojectDto>> listSubProjectByPJM(@RequestBody PageDto<RdmsProjectSubprojectDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        rdmsSubprojectService.listSubProjectByPJM(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/listSubprojectsAndCharacterList")
    public ResponseDto<PageDto<RdmsHmiSubprojectAndCharacterMixDto>> listSubprojectsAndCharacterList(@RequestBody PageDto<RdmsHmiSubprojectAndCharacterMixDto> pageDto) {
        ResponseDto<PageDto<RdmsHmiSubprojectAndCharacterMixDto>> responseDto = new ResponseDto<>();
        rdmsProjectService.listSubprojectsAndCharacterList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }


    @PostMapping("/listSubProjectByTM")
    public ResponseDto<PageDto<RdmsProjectSubprojectDto>> listSubProjectByTM(@RequestBody PageDto<RdmsProjectSubprojectDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        rdmsSubprojectService.listSubProjectByTM(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/archive/{subprojectId}")
    public ResponseDto archive(@PathVariable String subprojectId) {
        ResponseDto responseDto = new ResponseDto();
        RdmsProjectSubproject subproject = new RdmsProjectSubproject();
        subproject.setId(subprojectId);
        subproject.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
        rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
        return responseDto;
    }

    @PostMapping("/getTestSubprojectListByTM/{testManagerId}")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getTestSubprojectListByTM(@PathVariable String testManagerId) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto();
        List<RdmsProjectSubprojectDto> testSubprojectListByTM = rdmsSubprojectService.getTestSubprojectListByTM(testManagerId);
        responseDto.setContent(testSubprojectListByTM);
        return responseDto;
    }

    @PostMapping("/getSubprojectTaskList/{subprojectId}")
    public ResponseDto<List<RdmsJobItemDto>> getSubprojectTaskList(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsJobItemDto>> responseDto = new ResponseDto();
        List<RdmsJobItemDto> subprojectTaskList = rdmsSubprojectService.getSubprojectTaskList(subprojectId);
        responseDto.setContent(subprojectTaskList);
        return responseDto;
    }

    @PostMapping("/getTestFlagSubprojectListByTM/{testManagerId}")
    public ResponseDto<Boolean> getTestFlagSubprojectListByTM(@PathVariable String testManagerId) {
        ResponseDto<Boolean> responseDto = new ResponseDto();
        List<RdmsProjectSubprojectDto> testSubprojectListByTM = rdmsSubprojectService.getTestSubprojectListByTM(testManagerId);
        if (!CollectionUtils.isEmpty(testSubprojectListByTM)) {
            responseDto.setContent(true);
        } else {
            responseDto.setContent(false);
        }
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsSubprojectService.delete(id);
        return responseDto;
    }


}
