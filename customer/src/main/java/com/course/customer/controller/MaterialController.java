/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.service.ecology.util.*;
import com.course.server.service.rdms.*;
import com.course.server.service.util.jdbc.JDBCService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/material")
public class MaterialController {
    private static final Logger LOG = LoggerFactory.getLogger(MaterialController.class);
    public static final String BUSINESS_NAME = "物料管理";

    @Value("${oa.material.form.creater}")
    private String OA_MATERIAL_FORM_CREATER; //刘亚如 的ID 与系统配置文件 oa.material.form.creater=393 对应

    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;

    @Resource
    private RdmsMaterialService rdmsMaterialService;
    @Resource
    private RdmsMaterialProcessService rdmsMaterialProcessService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsBomService rdmsBomService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;
    @Resource
    private JDBCService jdbcService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsOaHrmUserService rdmsOaHrmUserService;
    @Resource
    private RdmsBomTreeService rdmsBomTreeService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsSysgmService rdmsSysgmService;
    @Autowired
    private RdmsBossService rdmsBossService;

    /**
     * 保存客户信息
     */
    @PostMapping("/saveMaterialApplication")
    @Transactional
    public ResponseDto<String> saveMaterialApplication(@RequestBody RdmsMaterialManageDto materialManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(materialManageDto.getProjectType(), "项目类型");
        if (materialManageDto.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())) {
            ValidatorUtil.require(materialManageDto.getPreProjectId(), "项目Id");
        } else if (materialManageDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())) {
            ValidatorUtil.require(materialManageDto.getProjectId(), "项目Id");
        } else {
            ValidatorUtil.require(materialManageDto.getProjectId(), "项目Id");
            ValidatorUtil.require(materialManageDto.getSubprojectId(), "项目Id");
        }
        ValidatorUtil.require(materialManageDto.getCode(), "申请单号");
        ValidatorUtil.require(materialManageDto.getName(), "申请标题");
        ValidatorUtil.require(materialManageDto.getCustomerId(), "公司Id");
        ValidatorUtil.require(materialManageDto.getJobType(), "工单类型");

        ValidatorUtil.require(materialManageDto.getJobitemId(), "工单Id");
        ValidatorUtil.require(materialManageDto.getMaterialList(), "物料清单");
        ValidatorUtil.require(materialManageDto.getReason(), "领用事由");
        ValidatorUtil.require(materialManageDto.getWriterId(), "填表人");
        ValidatorUtil.require(materialManageDto.getNextNode(), "下一节点");
        ValidatorUtil.require(materialManageDto.getUsageMode(), "使用方式");

        ValidatorUtil.length(materialManageDto.getName(), "申请标题", 2, 30);
        ValidatorUtil.length(materialManageDto.getReason(), "领用事由", 6, 800);
        ValidatorUtil.length(materialManageDto.getUsageMode(), "使用方式", 2, 20);

        ValidatorUtil.listNotEmpty(materialManageDto.getMaterialList(), "物料清单");

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
        if (!(rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_BUG.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_BUG.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.ASSIST.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TEST.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
        )) {
            ValidatorUtil.require(materialManageDto.getCharacterId(), "功能特性Id");
        }

        //将物料信息保存到物料表中
        for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
            RdmsMaterial material = CopyUtil.copy(materialDto, RdmsMaterial.class);
            rdmsMaterialService.save(material);
        }

        //保存申请表
        RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
        materialManage.setJobType(rdmsJobItem.getType());
        materialManage.setApproverId(materialManageDto.getNextNode());
        if (materialManageDto.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())) {
            materialManage.setAuxStatus(JobItemTypeEnum.TASK_FUNCTION.getType());
        } else if (materialManageDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())) {
            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_TEST.getType())) {
                materialManage.setAuxStatus(JobItemTypeEnum.TASK_TEST.getType());
            } else {
                materialManage.setAuxStatus(JobItemTypeEnum.TASK_PRODUCT.getType());
            }
        } else {
            if(materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_BUG.getType())
                    || (materialManageDto.getInheritJobType() != null && materialManageDto.getInheritJobType().equals(JobItemTypeEnum.TASK_BUG.getType()))){
                ValidatorUtil.require(materialManageDto.getProjectId(), "项目Id");
                ValidatorUtil.require(materialManageDto.getSubprojectId(), "子项目Id");
                materialManage.setAuxStatus(JobItemTypeEnum.TASK_BUG.getType());
            }else{
                ValidatorUtil.require(materialManageDto.getProjectId(), "项目Id");
                ValidatorUtil.require(materialManageDto.getSubprojectId(), "子项目Id");
                materialManage.setAuxStatus(JobItemTypeEnum.TASK_SUBP.getType());
            }

        }
//        materialManage.setOpeStatus(MaterialOpeStatusEnum.APPLICATION.getStatus());
        rdmsMaterialManageService.save(materialManage);

        //填写一条过程记录
        RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
        materialProcess.setMaterialId(materialManage.getId());
        materialProcess.setExecutorId(materialManageDto.getWriterId());
        materialProcess.setDeep(1);
        materialProcess.setDescription("提交物料申请");
        materialProcess.setNextNode(materialManage.getNextNode());
        materialProcess.setSubmitTime(new Date());
        materialProcess.setStatus(ApplicationStatusEnum.APPLICATION.getStatus());
        rdmsMaterialProcessService.save(materialProcess);

        responseDto.setContent(materialManage.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveMaterialApplication_return")
    @Transactional
    public ResponseDto<String> saveMaterialApplication_return(@RequestBody RdmsMaterialManageDto materialManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(materialManageDto.getCode(), "申请单号");
        ValidatorUtil.require(materialManageDto.getName(), "申请标题");
        ValidatorUtil.require(materialManageDto.getCustomerId(), "公司Id");
        ValidatorUtil.require(materialManageDto.getJobType(), "工单类型");

        ValidatorUtil.require(materialManageDto.getJobitemId(), "工单Id");
        ValidatorUtil.require(materialManageDto.getMaterialList(), "物料清单");
        ValidatorUtil.require(materialManageDto.getReason(), "领用事由");
        ValidatorUtil.require(materialManageDto.getWriterId(), "填表人");
//        ValidatorUtil.require(materialManageDto.getNextNode(), "下一节点");

        ValidatorUtil.length(materialManageDto.getName(), "申请标题", 2, 30);
        ValidatorUtil.length(materialManageDto.getReason(), "领用事由", 6, 800);

        ValidatorUtil.listNotEmpty(materialManageDto.getMaterialList(), "物料清单");

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
        //将物料信息保存到物料表中
        BigDecimal totalCost = new BigDecimal(0);
        for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
            ValidatorUtil.require(materialDto.getCheckOrNot(), "是否检查");
            ValidatorUtil.require(materialDto.getAmount(), "退库数量");
            ValidatorUtil.require(materialDto.getBatchCode(), "批次号/序列号");
            ValidatorUtil.require(materialDto.getTargetWarehouseCode(), "退库目标仓库");
//            ValidatorUtil.length(materialDto.getTargetWarehouseCode(), "退库目标仓库", 2, 100);

            String token_code = UuidUtil.getShortUuid();
            //先给原始物料设置一个token_code
            RdmsMaterial originMaterial = rdmsMaterialService.selectByPrimaryKey(materialDto.getId());
            originMaterial.setTokenCode(token_code);
            rdmsMaterialService.update(originMaterial);

            RdmsMaterialReturn material = CopyUtil.copy(materialDto, RdmsMaterialReturn.class);
            material.setId(null);
            material.setOriginCode(material.getCode());
            material.setCode(materialManageDto.getCode());
            material.setPreTaxSumPrice(material.getPreTaxUnitPrice().multiply(BigDecimal.valueOf(material.getAmount())));
            material.setTokenCode(token_code);
            rdmsMaterialService.save_return(material);
            totalCost = totalCost.add(material.getPreTaxUnitPrice().multiply(BigDecimal.valueOf(material.getAmount())));
        }

        //保存申请表
        RdmsMaterialManageReturn materialManageReturn = CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class);
        materialManageReturn.setJobType(rdmsJobItem.getType());
        materialManageReturn.setApproverId(materialManageDto.getNextNode());
        if (materialManageDto.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())) {
            materialManageReturn.setAuxStatus(JobItemTypeEnum.TASK_FUNCTION.getType());
            RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(materialManageDto.getCustomerId());
            if (!ObjectUtils.isEmpty(sysgmByCustomerId)) {
                materialManageReturn.setNextNode(sysgmByCustomerId.getSysgmId());
            } else {
                LOG.error("系统工程总监未设置. 发生位置:saveMaterialApplication_return方法 {}", this.getClass().getName());
                throw new BusinessException(BusinessExceptionCode.SYSGM_NOT_SET);
            }

        } else if (materialManageDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())) {
            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_TEST.getType())) {
                materialManageReturn.setAuxStatus(JobItemTypeEnum.TASK_TEST.getType());
                materialManageReturn.setNextNode(rdmsJobItem.getProjectManagerId());

            } else {
                materialManageReturn.setAuxStatus(JobItemTypeEnum.TASK_PRODUCT.getType());
                materialManageReturn.setNextNode(rdmsJobItem.getProductManagerId());
            }
        } else {
            if (materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType()) || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())) {
                materialManageReturn.setAuxStatus(JobItemTypeEnum.TASK_PRODUCT.getType());
                materialManageReturn.setNextNode(rdmsJobItem.getProductManagerId());
            } else if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_BUG.getType()) || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_BUG.getType())) {
                materialManageReturn.setAuxStatus(JobItemTypeEnum.TASK_BUG.getType());
                materialManageReturn.setNextNode(rdmsJobItem.getTestManagerId());
            } else {
                ValidatorUtil.require(materialManageDto.getProjectId(), "项目Id");
                materialManageReturn.setAuxStatus(JobItemTypeEnum.TASK_SUBP.getType());
                materialManageReturn.setNextNode(rdmsJobItem.getProjectManagerId());
            }
        }
        materialManageReturn.setId(null);
        materialManageReturn.setOpeStatus(MaterialOpeStatusEnum.RETURN_APPLICATION.getStatus());
        materialManageReturn.setAccountCost(totalCost);
        materialManageReturn.setOriginCode(materialManageDto.getOriginCode());  //原始单号
        rdmsMaterialManageService.save_return(materialManageReturn);

        //标记原始单据的操作状态
        RdmsMaterialManage rdmsMaterialManage = rdmsMaterialManageService.getMaterialManageSimpleInfoByCode_origin(materialManageDto.getOriginCode());
        rdmsMaterialManage.setOpeStatus(MaterialOpeStatusEnum.RETURN.getStatus());
        rdmsMaterialManageService.updateByPrimaryKeySelective(rdmsMaterialManage);

        //填写一条过程记录
        RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
        materialProcess.setMaterialId(materialManageReturn.getId());
        materialProcess.setExecutorId(materialManageDto.getWriterId());
        materialProcess.setDeep(1);
        materialProcess.setDescription("提交物料退库申请");
        materialProcess.setNextNode(materialManageReturn.getNextNode());
        materialProcess.setSubmitTime(new Date());
        materialProcess.setStatus(ApplicationStatusEnum.APPLICATION.getStatus());
        rdmsMaterialProcessService.save_return(materialProcess);

        responseDto.setContent(materialManageReturn.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveMaterialListToBom")
    @Transactional
    public ResponseDto<String> saveMaterialListToBom(@RequestBody RdmsHmiMaterialToBomDto materialToBomDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(materialToBomDto) && !ObjectUtils.isEmpty(materialToBomDto.getMaterialDtoList())) {
            for (RdmsMaterialDto materialDto : materialToBomDto.getMaterialDtoList()) {
                materialDto.setStatus("");
                RdmsBom bom = CopyUtil.copy(materialDto, RdmsBom.class);
                bom.setDeleted(0);
                bom.setStatus(BomStatusEnum.NORMAL.getStatus());
                bom.setCharacterId(materialToBomDto.getCharacterId());
                RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(materialToBomDto.getCharacterId());
                if (!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getSubprojectId())) {
                    bom.setSubprojectId(rdmsCharacter.getSubprojectId());
                }
                bom.setBomVersion(1);
                rdmsBomService.save(bom);

                RdmsMaterial rdmsMaterial = new RdmsMaterial();
                rdmsMaterial.setId(materialDto.getId());
                rdmsMaterial.setInBom(1);
                rdmsMaterialService.updateByPrimaryKeySelective(rdmsMaterial);
            }
        }
        responseDto.setContent(materialToBomDto.getCharacterId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBomTree")
    @Transactional
    public ResponseDto<String> saveBomTree(@RequestBody List<RdmsBomTreeDto> bomTreeDtoList) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!CollectionUtils.isEmpty(bomTreeDtoList)) {
            rdmsBomTreeService.saveBomTree(bomTreeDtoList);
        }
        responseDto.setContent("");
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBomTree_main")
    @Transactional
    public ResponseDto<String> saveBomTree_main(@RequestBody List<RdmsBomTreeDto> bomTreeDtoList) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!CollectionUtils.isEmpty(bomTreeDtoList)) {
            rdmsBomTreeService.saveBomTree_main(bomTreeDtoList);
        }
        responseDto.setContent("");
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getBomTree/{subprojectId}")
    @Transactional
    public ResponseDto<RdmsBomTreeDto> getBomTree(@PathVariable String subprojectId) {
//        subprojectId = "GlSbePeV";
        ResponseDto<RdmsBomTreeDto> responseDto = new ResponseDto<>();
        List<RdmsBomTree> bomTreeList = rdmsBomTreeService.getBomTreeListBySubprojectId(subprojectId);
        List<RdmsBomTreeDto> rdmsBomTreeDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bomTreeList)) {
            for (RdmsBomTree bomTree : bomTreeList) {
                RdmsBomTreeDto bomTreeDto = CopyUtil.copy(bomTree, RdmsBomTreeDto.class);
                bomTreeDto.setAmount(bomTree.getAmount().toString());
                rdmsBomTreeDtos.add(bomTreeDto);
            }
        }
        RdmsBomTreeDto bomTree = rdmsBomTreeService.getBomTree(rdmsBomTreeDtos);

        responseDto.setContent(bomTree);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getBomTree_main/{projectId}")
    @Transactional
    public ResponseDto<RdmsBomTreeDto> getBomTree_main(@PathVariable String projectId) {
        ResponseDto<RdmsBomTreeDto> responseDto = new ResponseDto<>();
        List<RdmsBomTreeMain> bomTreeMainListByProjectId = rdmsBomTreeService.getBomTreeMainListByProjectId(projectId);
        List<RdmsBomTreeDto> rdmsBomTreeDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bomTreeMainListByProjectId)) {
            for (RdmsBomTreeMain bomTree : bomTreeMainListByProjectId) {
                RdmsBomTreeDto bomTreeDto = CopyUtil.copy(bomTree, RdmsBomTreeDto.class);
                bomTreeDto.setAmount(bomTree.getAmount().toString());
                rdmsBomTreeDtos.add(bomTreeDto);
            }
        }
        RdmsBomTreeDto bomTree = rdmsBomTreeService.getBomTree(rdmsBomTreeDtos);

        responseDto.setContent(bomTree);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/synchronizationBomTree/{mainProjectId}")
    @Transactional
    public ResponseDto<Integer> synchronizationBomTree(@PathVariable String mainProjectId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        int count = 0;
        if (!ObjectUtils.isEmpty(mainProjectId)) {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(mainProjectId);
            if (rdmsProject != null) {
                List<RdmsProjectSubprojectDto> subprojectListByProjectId = rdmsProjectService.getSubprojectListByProjectId(mainProjectId);
                if (!CollectionUtils.isEmpty(subprojectListByProjectId)) {
                    for (RdmsProjectSubprojectDto subprojectDto : subprojectListByProjectId) {
                        List<RdmsBomTree> bomTreeListBySubprojectId = rdmsBomTreeService.getBomTreeListBySubprojectId(subprojectDto.getId());
                        if (!CollectionUtils.isEmpty(bomTreeListBySubprojectId)) {
                            for (RdmsBomTree bomTree : bomTreeListBySubprojectId) {
                                RdmsBomTreeMain bomTreeMain = CopyUtil.copy(bomTree, RdmsBomTreeMain.class);
                                bomTreeMain.setProjectId(mainProjectId);
                                rdmsBomTreeService.save_main_noUpdate(bomTreeMain);
                                count++;
                            }
                        }
                    }
                }
            }
        }

        responseDto.setContent(count);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveMaterialListToBom_subproject")
    @Transactional
    public ResponseDto<String> saveMaterialListToBom_subproject(@RequestBody RdmsHmiMaterialToBomDto materialToBomDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(materialToBomDto) && !ObjectUtils.isEmpty(materialToBomDto.getMaterialDtoList())) {
            for (RdmsMaterialDto materialDto : materialToBomDto.getMaterialDtoList()) {
                materialDto.setStatus("");
                RdmsBom bom = CopyUtil.copy(materialDto, RdmsBom.class);
                bom.setDeleted(0);
                bom.setStatus(BomStatusEnum.NORMAL.getStatus());
                bom.setSubprojectId(materialToBomDto.getSubprojectId());
                bom.setBomVersion(1);
                rdmsBomService.save(bom);

                RdmsMaterial rdmsMaterial = new RdmsMaterial();
                rdmsMaterial.setId(materialDto.getId());
                rdmsMaterial.setInBom(1);
                rdmsMaterialService.updateByPrimaryKeySelective(rdmsMaterial);
            }
        }
        responseDto.setContent(materialToBomDto.getCharacterId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/submitBom/{characterId}")
    @Transactional
    public ResponseDto<String> submitBom(@PathVariable String characterId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsCharacter character = new RdmsCharacter();
        character.setId(characterId);
        character.setBomStatus(CharacterBomStatusEnum.SUBMIT.getStatus());
        character.setBomVersion(1);
        rdmsCharacterService.updateByPrimaryKeySelective(character);
        responseDto.setContent(character.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/submitSubProjectBom/{subprojectId}")
    @Transactional
    public ResponseDto<String> submitSubProjectBom(@PathVariable String subprojectId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsProjectSubproject subproject = new RdmsProjectSubproject();
        subproject.setId(subprojectId);
        subproject.setBomStatus(CharacterBomStatusEnum.SUBMIT.getStatus());
        subproject.setBomVersion(1);
        rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
        responseDto.setContent(subproject.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/rejectSubProjectBom/{subprojectId}")
    @Transactional
    public ResponseDto<String> rejectSubProjectBom(@PathVariable String subprojectId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsProjectSubproject subproject = new RdmsProjectSubproject();
        subproject.setId(subprojectId);
        subproject.setBomStatus(CharacterBomStatusEnum.EDIT.getStatus());
        subproject.setBomVersion(1);
        rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
        responseDto.setContent(subproject.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/rejectBom/{characterId}")
    @Transactional
    public ResponseDto<String> rejectBom(@PathVariable String characterId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsCharacter character = new RdmsCharacter();
        character.setId(characterId);
        character.setBomStatus(CharacterBomStatusEnum.EDIT.getStatus());
        character.setBomVersion(1);
        rdmsCharacterService.updateByPrimaryKeySelective(character);
        responseDto.setContent(character.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBomItem")
    @Transactional
    public ResponseDto<String> saveBomItem(@RequestBody RdmsBomDto bomDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(bomDto.getName(), "物料名称");
        ValidatorUtil.require(bomDto.getBomCode(), "物料编号");
        ValidatorUtil.require(bomDto.getModel(), "物料型号");
        ValidatorUtil.require(bomDto.getUnit(), "计量单位");
        ValidatorUtil.require(bomDto.getPreTaxUnitPrice(), "未税单价");
        ValidatorUtil.require(bomDto.getCharacterId(), "功能特性Id");
        ValidatorUtil.require(bomDto.getAmount(), "使用数量");
        ValidatorUtil.require(bomDto.getSupplierName(), "供应商");

        ValidatorUtil.length(bomDto.getName(), "物料名称", 2, 80);
        ValidatorUtil.length(bomDto.getBomCode(), "物料编号", 2, 50);
        ValidatorUtil.length(bomDto.getModel(), "物料型号", 2, 50);
        ValidatorUtil.length(bomDto.getUnit(), "计量单位", 1, 10);
        ValidatorUtil.length(bomDto.getSupplierName(), "供应商", 2, 100);

        RdmsBom bom = CopyUtil.copy(bomDto, RdmsBom.class);

        bom.setPreTaxSumPrice(bom.getPreTaxUnitPrice().multiply(BigDecimal.valueOf(bom.getAmount())));
        bom.setDeleted(0);
        bom.setStatus(BomStatusEnum.NORMAL.getStatus());
        bom.setBomVersion(1);

        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bomDto.getCharacterId());
        if (!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getSubprojectId())) {
            bom.setSubprojectId(rdmsCharacter.getSubprojectId());
        }
        rdmsBomService.save(bom);

        responseDto.setContent(bom.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveEditedBomItem")
    @Transactional
    public ResponseDto<String> saveEditedBomItem(@RequestBody RdmsBomDto bomDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(bomDto.getName(), "物料名称");
        ValidatorUtil.require(bomDto.getBomCode(), "物料编号");
        ValidatorUtil.require(bomDto.getModel(), "物料型号");
        ValidatorUtil.require(bomDto.getUnit(), "计量单位");
        ValidatorUtil.require(bomDto.getPreTaxUnitPrice(), "未税单价");
        ValidatorUtil.require(bomDto.getAmount(), "使用数量");
        ValidatorUtil.require(bomDto.getSupplierName(), "供应商");

        ValidatorUtil.length(bomDto.getName(), "物料名称", 2, 80);
        ValidatorUtil.length(bomDto.getBomCode(), "物料编号", 2, 50);
        ValidatorUtil.length(bomDto.getModel(), "物料型号", 2, 50);
        ValidatorUtil.length(bomDto.getUnit(), "计量单位", 1, 10);
        ValidatorUtil.length(bomDto.getSupplierName(), "供应商", 2, 100);

        RdmsBom bom = CopyUtil.copy(bomDto, RdmsBom.class);
        bom.setPreTaxSumPrice(bom.getPreTaxUnitPrice().multiply(BigDecimal.valueOf(bom.getAmount())));
        rdmsBomService.save(bom);

        responseDto.setContent(bom.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBomItem_subproject")
    @Transactional
    public ResponseDto<String> saveBomItem_subproject(@RequestBody RdmsBomDto bomDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(bomDto.getName(), "物料名称");
        ValidatorUtil.require(bomDto.getBomCode(), "物料编号");
        ValidatorUtil.require(bomDto.getModel(), "物料型号");
        ValidatorUtil.require(bomDto.getUnit(), "计量单位");
        ValidatorUtil.require(bomDto.getPreTaxUnitPrice(), "未税单价");
        ValidatorUtil.require(bomDto.getSubprojectId(), "项目Id");
        ValidatorUtil.require(bomDto.getAmount(), "使用数量");
        ValidatorUtil.require(bomDto.getSupplierName(), "供应商");

        ValidatorUtil.length(bomDto.getName(), "物料名称", 2, 80);
        ValidatorUtil.length(bomDto.getBomCode(), "物料编号", 2, 50);
        ValidatorUtil.length(bomDto.getModel(), "物料型号", 2, 50);
        ValidatorUtil.length(bomDto.getUnit(), "计量单位", 1, 10);
        ValidatorUtil.length(bomDto.getSupplierName(), "供应商", 2, 100);

        RdmsBom bom = CopyUtil.copy(bomDto, RdmsBom.class);

        bom.setPreTaxSumPrice(bom.getPreTaxUnitPrice().multiply(BigDecimal.valueOf(bom.getAmount())));
        bom.setDeleted(0);
        bom.setStatus(BomStatusEnum.NORMAL.getStatus());
        bom.setBomVersion(1);
//        bom.setSubprojectId(bomDto.getSubprojectId());

        rdmsBomService.save(bom);

        responseDto.setContent(bom.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBomItem_subproject_import")
    @Transactional
    public ResponseDto<String> saveBomItem_subproject_import(@RequestBody List<RdmsBomDto> bomDtoList) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!CollectionUtils.isEmpty(bomDtoList)) {
            for (RdmsBomDto bomDto : bomDtoList) {
                ValidatorUtil.require(bomDto.getName(), "物料名称");
                ValidatorUtil.require(bomDto.getBomCode(), "物料编号");
                ValidatorUtil.require(bomDto.getModel(), "物料型号");
                ValidatorUtil.require(bomDto.getUnit(), "计量单位");
                ValidatorUtil.require(bomDto.getPreTaxUnitPrice(), "未税单价");
                ValidatorUtil.require(bomDto.getSubprojectId(), "项目Id");
                ValidatorUtil.require(bomDto.getAmount(), "使用数量");
                ValidatorUtil.require(bomDto.getSupplierName(), "供应商");

                ValidatorUtil.length(bomDto.getName(), "物料名称", 2, 80);
                ValidatorUtil.length(bomDto.getBomCode(), "物料编号", 2, 50);
                ValidatorUtil.length(bomDto.getModel(), "物料型号", 2, 50);
                ValidatorUtil.length(bomDto.getUnit(), "计量单位", 1, 10);
                ValidatorUtil.length(bomDto.getSupplierName(), "供应商", 2, 100);

                RdmsBom bom = CopyUtil.copy(bomDto, RdmsBom.class);

                bom.setPreTaxSumPrice(bom.getPreTaxUnitPrice().multiply(BigDecimal.valueOf(bom.getAmount())));
                bom.setDeleted(0);
                bom.setStatus(BomStatusEnum.NORMAL.getStatus());
                bom.setBomVersion(1);

                rdmsBomService.save(bom);
            }
        }

        responseDto.setContent("");
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/approveMaterialApplication")
    @Transactional
    public ResponseDto<String> approveMaterialApplication(@RequestBody RdmsMaterialManageDto materialManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(materialManageDto.getCode(), "申请单号");
        ValidatorUtil.require(materialManageDto.getApproveDescription(), "审批意见");
        ValidatorUtil.require(materialManageDto.getCapitalSource(), "资金来源");
        ValidatorUtil.length(materialManageDto.getApproveDescription(), "审批描述", 2, 500);
        ValidatorUtil.listNotEmpty(materialManageDto.getMaterialList(), "物料清单");

        if (!ObjectUtils.isEmpty(materialManageDto.getOpeStatus())
                && (materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPLICATION.getStatus())
                || materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus()))) {
            //退库申请通过

            for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
                RdmsMaterialReturn material = CopyUtil.copy(materialDto, RdmsMaterialReturn.class);
                material.setUsageMode(materialManageDto.getUsageMode());
                material.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialService.updateByPrimaryKeySelective_return(material);
            }

            //保存申请表
            RdmsMaterialManageReturn materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class);
            String executorId = materialManage.getNextNode();  //质量工单时产品经理  //其他工单时项目经理  //预立项阶段是系统工程师或产品经理
            materialManage.setNextNode(materialManage.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
            if (!ObjectUtils.isEmpty(materialManageDto.getApproveStatus())
                    && materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.REJECT.getStatus())) {
                materialManage.setStatus(ApplicationStatusEnum.REJECT.getStatus());
            } else {
                materialManage.setStatus(materialManageDto.getApproveStatus());
            }
            materialManage.setApproveTime(new Date());
            if (materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus())
                    && !materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.SUPPLEMENT.getStatus())) {
                materialManage.setOpeStatus(MaterialOpeStatusEnum.RETURN_COMPLETE.getStatus());
            } else if (materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus())
                    && materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.SUPPLEMENT.getStatus())) {
                materialManage.setOpeStatus(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus());
            } else if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.REJECT.getStatus())) {
                materialManage.setOpeStatus(MaterialOpeStatusEnum.RETURN_COMPLETE.getStatus());
            } else {
                materialManage.setOpeStatus(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus());
            }
            String materialManageReturnId = rdmsMaterialManageService.updateByPrimaryKeySelective_return(materialManage);

            //处理原始领料单状态
            RdmsMaterialManageReturn rdmsMaterialManageReturn = rdmsMaterialManageService.selectByPrimaryKey_return(materialManageReturnId);
            RdmsMaterialManageDto originMaterialManageItem = rdmsMaterialManageService.getMaterialApplicationByCode_origin(rdmsMaterialManageReturn.getOriginCode());

            if (!ObjectUtils.isEmpty(materialManageDto.getApproveStatus())
                    && materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.REJECT.getStatus())) {
                originMaterialManageItem.setOpeStatus(null);
                rdmsMaterialManageService.update(originMaterialManageItem);

                //恢复原始物料表的对应物料状态
                //得到退料的列表
                List<RdmsMaterialReturn> materialList_return = rdmsMaterialService.getMaterialListByApplicationCode_return(rdmsMaterialManageReturn.getCode());
                for (RdmsMaterialReturn materialReturn : materialList_return) {
                    List<RdmsMaterial> originMaterialByTokenCode = rdmsMaterialService.getOriginMaterialByTokenCode(materialReturn.getTokenCode());
                    for (RdmsMaterial material : originMaterialByTokenCode) {
                        material.setTokenCode(null);
                        rdmsMaterialService.update(material);
                    }
                }

                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManage.getId());
                materialProcess.setExecutorId(executorId);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("退料申请被驳回!");
                materialProcess.setNextNode(materialManage.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);

                responseDto.setContent(materialManage.getId());

            } else if (!ObjectUtils.isEmpty(materialManageDto.getApproveStatus())
                    && materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.SUPPLEMENT.getStatus())) {
                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManage.getId());
                materialProcess.setExecutorId(executorId);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("退料退回增补材料!");
                materialProcess.setNextNode(materialManage.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);

                responseDto.setContent(materialManage.getId());

            } else {
                //处理原始领料单状态
                if (originMaterialManageItem != null
                        && materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPLICATION.getStatus())) {
                    originMaterialManageItem.setOpeStatus(null);
                    originMaterialManageItem.setAccountCost(originMaterialManageItem.getAccountCost().subtract(rdmsMaterialManageReturn.getAccountCost()));

                    //如果全部退了, 则将原来的领料单, 设置为完成状态
                    List<RdmsMaterial> remainOriginMaterialList = new ArrayList<>();
                    //读出原始领料单的物料表
                    List<RdmsMaterial> originMaterialList = rdmsMaterialService.getMaterialListByApplicationCode_origin(originMaterialManageItem.getCode());
                    //读出所有退库的料单
                    List<RdmsMaterialReturn> materialReturnList = rdmsMaterialService.getMaterialListByOriginCode_return(originMaterialManageItem.getCode());
                    if (!CollectionUtils.isEmpty(originMaterialList)) {
                        if (!CollectionUtils.isEmpty(materialReturnList)) {
                            for (RdmsMaterial originMaterial : originMaterialList) {
                                for (RdmsMaterialReturn materialReturn : materialReturnList) {
                                    if (!ObjectUtils.isEmpty(originMaterial.getTokenCode())
                                            && originMaterial.getTokenCode().equals(materialReturn.getTokenCode())) {
                                        if (originMaterial.getAmount() >= materialReturn.getAmount()) {
                                            //某一条物料没有都退掉
                                            originMaterial.setAmount(originMaterial.getAmount() - materialReturn.getAmount());
                                            originMaterial.setPreTaxSumPrice(originMaterial.getPreTaxUnitPrice().multiply(BigDecimal.valueOf(originMaterial.getAmount())));
                                            originMaterial.setTokenCode(null);  //Token保证只用一次
                                            rdmsMaterialService.update(originMaterial);
                                            break;
                                        }
                                    }
                                }
                            }
                            //遍历原始领料单, 看是不是还有数量不为零的物料, 如果数量为零,则删除该条记录
                            for (RdmsMaterial originMaterial : originMaterialList) {
                                if (originMaterial.getAmount() > 0) {
                                    remainOriginMaterialList.add(originMaterial);
                                }
                            }
                            if (CollectionUtils.isEmpty(remainOriginMaterialList)) {
                                //将原始领料单设置为完成状态
                                originMaterialManageItem.setStatus(ApplicationStatusEnum.COMPLETE.getStatus());
                                RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(originMaterialManageItem.getCode());
                                recordBySerialNo.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                                rdmsBudgetResearchExeService.update(recordBySerialNo);
                            }
                        }
                    }

                    rdmsMaterialManageService.update(originMaterialManageItem);
                }

                if (!materialManage.getUsageMode().equals(MaterialUsageModeEnum.BORROW.getStatus())) {
                    if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {  //系统工程任务
                        //填写一条预算执行记录, 支付状态为应付未付 , 等物料单处理完成时, 标记为已支付
                        RdmsJobItem jobItemDb = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                        RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
                        budgetResearchExe.setId(null);
                        budgetResearchExe.setCustomerId(jobItemDb.getCustomerId());
                        budgetResearchExe.setPreprojectId(jobItemDb.getPreProjectId());
                        budgetResearchExe.setSerialNo(materialManage.getCode());
                        budgetResearchExe.setName(materialManage.getName());
                        budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                        // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                        if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManage.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                            if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                                budgetResearchExe.setPaymentTime(new Date());
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            } else {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                                budgetResearchExe.setPaymentTime(null);
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            }
                        }
                        if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManage.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                            if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                                budgetResearchExe.setPaymentTime(new Date());
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            } else {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                                budgetResearchExe.setPaymentTime(null);
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            }
                        }

                    } else {
                        //填写一条预算执行记录, 支付状态为应付未付 , 等物料单处理完成时, 标记为已支付
                        RdmsJobItem jobItemDb = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                        RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
                        budgetResearchExe.setId(null);
                        budgetResearchExe.setCustomerId(jobItemDb.getCustomerId());
                        budgetResearchExe.setProjectId(jobItemDb.getProjectId());
                        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemDb.getSubprojectId());
                        budgetResearchExe.setParentId(subproject.getParent());
                        budgetResearchExe.setSubprojectId(jobItemDb.getSubprojectId());
                        budgetResearchExe.setSerialNo(materialManage.getCode());
                        budgetResearchExe.setName(materialManage.getName());
                        budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                        // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                        if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManage.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                    || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                            } else {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                            }
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                            if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                                budgetResearchExe.setPaymentTime(new Date());
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            } else {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                                budgetResearchExe.setPaymentTime(null);
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            }
                        }
                        if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManage.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                    || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                            } else {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                            }
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                            if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                                budgetResearchExe.setPaymentTime(new Date());
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            } else {
                                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                                budgetResearchExe.setPaymentTime(null);
                                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                            }
                        }
                    }
                }

                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManage.getId());
                materialProcess.setExecutorId(executorId);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("主管经理审批完成");
                materialProcess.setNextNode(materialManage.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);

                responseDto.setContent(materialManage.getId());
            }

        } else {
            //处理领料的情况
            for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
                RdmsMaterial material = CopyUtil.copy(materialDto, RdmsMaterial.class);
                material.setUsageMode(materialManageDto.getUsageMode());
                material.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialService.updateByPrimaryKeySelective(material);
            }

            //保存申请表
            RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
            String executorId = materialManage.getNextNode();  //质量工单时产品经理  //其他工单时项目经理  //预立项阶段是系统工程师或产品经理  //Task_bug是测试主管
            materialManage.setNextNode(materialManage.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
            materialManage.setStatus(materialManageDto.getApproveStatus());
            materialManage.setApproveTime(new Date());
            rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);

            if (!materialManage.getUsageMode().equals(MaterialUsageModeEnum.BORROW.getStatus())) {
                if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {  //系统工程任务
                    //填写一条预算执行记录, 支付状态为应付未付 , 等物料单处理完成时, 标记为已支付
                    RdmsJobItem jobItemDb = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                    RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
                    budgetResearchExe.setId(null);
                    budgetResearchExe.setCustomerId(jobItemDb.getCustomerId());
                    budgetResearchExe.setPreprojectId(jobItemDb.getPreProjectId());
                    budgetResearchExe.setSerialNo(materialManage.getCode());
                    budgetResearchExe.setName(materialManage.getName());
                    budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                    // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                    if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                        budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                        budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                        budgetResearchExe.setAmount(materialManage.getAccountCost());
                        budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                        RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                        if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                            budgetResearchExe.setId(recordBySerialNo.getId());
                        }
                        if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                            budgetResearchExe.setPaymentTime(new Date());
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        } else {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                            budgetResearchExe.setPaymentTime(null);
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        }
                    }
                    if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                        budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                        budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                        budgetResearchExe.setAmount(materialManage.getAccountCost());
                        budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                        RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                        if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                            budgetResearchExe.setId(recordBySerialNo.getId());
                        }
                        if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                            budgetResearchExe.setPaymentTime(new Date());
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        } else {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                            budgetResearchExe.setPaymentTime(null);
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        }
                    }

                } else {
                    //填写一条预算执行记录, 支付状态为应付未付 , 等物料单处理完成时, 标记为已支付
                    RdmsJobItem jobItemDb = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                    RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
                    budgetResearchExe.setId(null);
                    budgetResearchExe.setCustomerId(jobItemDb.getCustomerId());
                    budgetResearchExe.setProjectId(jobItemDb.getProjectId());
                    RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemDb.getSubprojectId());
                    budgetResearchExe.setParentId(subproject.getParent());
                    budgetResearchExe.setSubprojectId(jobItemDb.getSubprojectId());
                    budgetResearchExe.setSerialNo(materialManage.getCode());
                    budgetResearchExe.setName(materialManage.getName());
                    budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                    // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                    if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                        budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                        budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                        budgetResearchExe.setAmount(materialManage.getAccountCost());
                        if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                            budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                        } else {
                            budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                        }
                        RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                        if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                            budgetResearchExe.setId(recordBySerialNo.getId());
                        }
                        if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                            budgetResearchExe.setPaymentTime(new Date());
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        } else {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                            budgetResearchExe.setPaymentTime(null);
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        }
                    }
                    if (materialManage.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                        budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                        budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                        budgetResearchExe.setAmount(materialManage.getAccountCost());
                        if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                            budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                        } else {
                            budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                        }
                        RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManage.getCode());
                        if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                            budgetResearchExe.setId(recordBySerialNo.getId());
                        }
                        if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                            budgetResearchExe.setPaymentTime(new Date());
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        } else {
                            budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                            budgetResearchExe.setPaymentTime(null);
                            String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                        }
                    }
                }
            }

            //填写一条过程记录
            RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
            materialProcess.setMaterialId(materialManage.getId());
            materialProcess.setExecutorId(executorId);
            long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
            materialProcess.setDeep((int) materialProcessCount);
            materialProcess.setDescription("主管经理审批完成");
            materialProcess.setNextNode(materialManage.getNextNode());
            materialProcess.setSubmitTime(new Date());
            materialProcess.setStatus(materialManageDto.getApproveStatus());
            rdmsMaterialProcessService.save(materialProcess);

            responseDto.setContent(materialManage.getId());
        }

        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/approveMaterialApplication-sap")
    @Transactional
    public ResponseDto<String> approveMaterialApplicationBySap(@RequestBody RdmsMaterialManageDto materialManageDto) throws Exception {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(materialManageDto.getCode(), "申请单号");
        ValidatorUtil.require(materialManageDto.getApproveDescription(), "审批意见");
        ValidatorUtil.require(materialManageDto.getCapitalSource(), "资金来源");
        ValidatorUtil.length(materialManageDto.getApproveDescription(), "审批描述", 2, 500);
        ValidatorUtil.listNotEmpty(materialManageDto.getMaterialList(), "物料清单");

        if (!ObjectUtils.isEmpty(materialManageDto.getOpeStatus())
                && (materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPLICATION.getStatus()))) {  //这是一个退库申请单

            if (materialManageDto.getStatus().equals(ApplicationStatusEnum.APPLICATION.getStatus())
                    && materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.PRE_APPROVED.getStatus()))   //对申请单进行预批准
            {   //此时的approveStatus是PRE_APPROVED
                //预批准的情况
                //1. 处理退料的情况
                for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
                    RdmsMaterialReturn rdmsMaterialReturn = rdmsMaterialService.selectByPrimaryKey_return(materialDto.getId());
                    rdmsMaterialReturn.setUsageMode(materialManageDto.getUsageMode());
                    rdmsMaterialReturn.setStatus(materialManageDto.getApproveStatus());   //PRE_APPROVED
                    rdmsMaterialService.updateByPrimaryKeySelective_return(rdmsMaterialReturn);
                }
                //2. 保存申请表
                //保存申请表
                RdmsMaterialManageReturn materialManageReturn = CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class);
                String executorId = materialManageReturn.getNextNode();  //质量工单时产品经理  //其他工单时项目经理  //预立项阶段是系统工程师或产品经理
                materialManageReturn.setNextNode(materialManageReturn.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
                materialManageReturn.setStatus(materialManageDto.getApproveStatus());   //PRE_APPROVED
                materialManageReturn.setApproveTime(new Date());
                materialManageReturn.setOpeStatus(MaterialOpeStatusEnum.RETURN_PRE_APPROVED.getStatus());
                String materialManageReturnId = rdmsMaterialManageService.updateByPrimaryKeySelective_return(materialManageReturn);

                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManageReturn.getId());
                materialProcess.setExecutorId(executorId);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManageReturn.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("主管经理审批完成");
                materialProcess.setNextNode(materialManageReturn.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);

                responseDto.setContent(materialManageReturn.getId());

                //查询到物料申请的发起人
                if (!ObjectUtils.isEmpty(materialManageDto.getWriterId())) {
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialManageDto.getWriterId());
                    //项目OA推送留存
                    //本地调试时候 设置为 false 就不会向 OA发送申请单
                    if (true) {
                        //将流程推送到OA
                        RdmsOaHrmUser rdmsOaHrmUser = rdmsOaHrmUserService.selectByLoginIdAndSaveToDB(rdmsCustomerUser.getLoginName());
                        if (rdmsOaHrmUser != null) {
                            String userId = rdmsOaHrmUser.getId();
                            String departmentCode = rdmsOaHrmUser.getDepartmentcode();
                            String title = materialManageDto.getName();
                            Integer workflowId = 1223;   //退库

                            //mainData
                            List<Field<String>> mainData = new ArrayList<>();
                            {
                                Field<String> mainData1 = new Field<>();
                                mainData1.setFieldName("sqr");  //申请人
                                mainData1.setFieldValue(userId);
                                Field<String> mainData2 = new Field<>();
                                mainData2.setFieldName("sqrq");  //申请日期
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String format = simpleDateFormat.format(materialManageDto.getCreateTime());
                                mainData2.setFieldValue(format);
                                Field<String> mainData3 = new Field<>();
                                mainData3.setFieldName("szbm");  //部门代码
                                mainData3.setFieldValue(departmentCode);
                                Field<String> mainData4 = new Field<>();
                                mainData4.setFieldName("szgs");  //所属公司
                                mainData4.setFieldValue("2");
//                                Field<String> mainData5 = new Field<>();
//                                mainData5.setFieldName("fhbh");  //发货编号
//                                mainData5.setFieldValue("");
                                Field<String> mainData6 = new Field<>();
                                mainData6.setFieldName("bz");  //备注
                                mainData6.setFieldValue(materialManageDto.getReason());

                                //mainData.add(mainData0);
                                mainData.add(mainData1);
                                mainData.add(mainData2);
                                mainData.add(mainData3);
                                mainData.add(mainData4);
//                                mainData.add(mainData5);
                                mainData.add(mainData6);
                            }

                            List<RdmsSAPMaterialDto> sapMaterialDtos = new ArrayList<>();
                            List<RdmsMaterialDto> materialList = materialManageDto.getMaterialList();
                            for (RdmsMaterialDto materialDto_origin : materialList) {
                                //判断是否为退库单
                                RdmsMaterialDto materialDto = new RdmsMaterialDto();
                                RdmsMaterialManageReturn materialManage = rdmsMaterialManageService.getMaterialManageSimpleInfoByCode_return(materialDto_origin.getCode());
                                if(!ObjectUtils.isEmpty(materialManage)){
                                    RdmsMaterialReturn materialReturn = rdmsMaterialService.selectByPrimaryKey_return(materialDto_origin.getId());
                                    materialDto = CopyUtil.copy(materialReturn, RdmsMaterialDto.class);
                                }else{
                                    RdmsMaterial rdmsMaterial = rdmsMaterialService.selectByPrimaryKey(materialDto_origin.getId());
                                    materialDto = CopyUtil.copy(rdmsMaterial, RdmsMaterialDto.class);
                                }

                                RdmsSAPMaterialDto sapMaterialDto = new RdmsSAPMaterialDto();
                                //String code = materialDto.getCode().substring(15, 19);  //单号
                                //private Integer code; // 记录编码
                                sapMaterialDto.setCode(0);  //这个值只能是0,任何其他数字都不能正常显示!!!!!
                                //private String materialNo; // 物料编号
                                sapMaterialDto.setMaterialNo(materialDto.getBomCode());
                                //private String materialName; // 物料名称/描述
                                sapMaterialDto.setMaterialName(materialDto.getName());
                                //private String materialSpcf; // 规格型号
                                sapMaterialDto.setMaterialSpcf(materialDto.getModel());
                                //private String warehouseNo; // 仓库编号
                                sapMaterialDto.setWarehouseNo(materialDto.getWarehouseCode());
                                //private String warehouseName; // 仓库名称
                                sapMaterialDto.setWarehouseName("");
                                //private String warehouseCode; // 仓库编码
                                sapMaterialDto.setWarehouseCode("");
                                //private String batchCodeSno; // 批次号
                                sapMaterialDto.setBatchCodeSno(materialDto.getBatchCode());
                                //private String materialType; // 物料类型
                                sapMaterialDto.setMaterialType("");
                                //private Integer num; // 数量
                                sapMaterialDto.setNum(materialDto.getAmount().intValue());
                                //private String unit; // 单位
                                sapMaterialDto.setUnit(materialDto.getUnit());
                                //private String usageMode;  //使用方式  借用等
                                sapMaterialDto.setUsageMode(materialManageDto.getUsageMode());
                                //是否检查
                                sapMaterialDto.setIsRecheck(materialDto.getCheckOrNot());
                                //报检单号
                                sapMaterialDto.setRecheckItemNo(materialDto.getCheckItemNo());
                                //目标仓库
                                sapMaterialDto.setTargetWarehouseCode(materialDto.getTargetWarehouseCode());
                                //关联OA申请ID
                                sapMaterialDto.setRequestId(materialManageDto.getRequestId());

                                RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                                if (!ObjectUtils.isEmpty(rdmsJobItem) && !ObjectUtils.isEmpty(rdmsJobItem.getProjectId())) {
                                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                    //private String projectCode;  //项目代码
                                    sapMaterialDto.setProjectCode(rdmsProject.getProjectCode());
                                    //private String projectName;  //项目名称
                                    sapMaterialDto.setProjectName(rdmsProject.getProjectName());
                                } else if (!ObjectUtils.isEmpty(rdmsJobItem) && !ObjectUtils.isEmpty(rdmsJobItem.getPreProjectId())) {
                                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsJobItem.getPreProjectId());
                                    //private String projectCode;  //项目代码
                                    sapMaterialDto.setProjectCode(rdmsPreProject.getProjectCode());
                                    //private String projectName;  //项目名称
                                    sapMaterialDto.setProjectName(rdmsPreProject.getPreProjectName());
                                }

                                sapMaterialDtos.add(sapMaterialDto);
                            }

                            DetailData detailData = new DetailData();
                            detailData.setTableDBName("formtable_main_214_dt1");

                            OaFlowUtils oaFlowUtils = new OaFlowUtils();
                            List<WorkflowRequestTableRecord> workflowRequestTableRecords = oaFlowUtils.createOADetailDateRecordList_materialReturn(sapMaterialDtos);

                            detailData.setWorkflowRequestTableRecords(workflowRequestTableRecords);

                            String API_URL = "/api/workflow/paService/doCreateRequest";

                            OaFlowUtils flowUtils = new OaFlowUtils<>();
                            title += "-(" + materialManageDto.getCode()+ ":" + rdmsOaHrmUser.getLastname() + ")";
                            OaRes oaRes = flowUtils.doCreateRequest(API_URL, OA_MATERIAL_FORM_CREATER, workflowId, title, mainData, detailData);

                            if (!oaRes.getCode().equals("SUCCESS")) {
                                LOG.error("OA接口调用失败, 发生在MaterialController类approveMaterialApplicationBySap方法中");
                                throw new BusinessException(BusinessExceptionCode.OA_INTERFACE_ERROR);
                            }
                        } else {
                            LOG.error("用户不存在, 发生在MaterialController类approveMaterialApplicationBySap方法中");
                            throw new BusinessException(BusinessExceptionCode.USER_NOT_EXIST_ERROR);
                        }
                    }
                } else {
                    LOG.error("输入参数错误, 发生在MaterialController类approveMaterialApplicationBySap方法中");
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }

            }

            if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.REJECT.getStatus())) {
                //驳回的情况
                materialManageDto.setStatus(ApplicationStatusEnum.REJECT.getStatus());
                materialManageDto.setUpdateTime(new Date());
                materialManageDto.setApproveTime(new Date());
                materialManageDto.setOpeStatus(MaterialOpeStatusEnum.RETURN_COMPLETE.getStatus());
                rdmsMaterialManageService.updateByPrimaryKeySelective_return(CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class));

                //处理退库驳回物料的状态
                List<RdmsMaterialReturn> materialList = rdmsMaterialService.getMaterialListByApplicationCode_return(materialManageDto.getCode());
                if (!CollectionUtils.isEmpty(materialList)) {
                    materialList.forEach(rdmsMaterial -> {
                        rdmsMaterial.setStatus(ApplicationStatusEnum.REJECT.getStatus());
                        rdmsMaterial.setUpdateTime(new Date());
                        rdmsMaterialService.update_return(rdmsMaterial);
                    });
                }
                //恢复原始物料表的对应物料状态
                List<RdmsMaterialReturn> materialList_return = rdmsMaterialService.getMaterialListByApplicationCode_return(materialManageDto.getCode());
                for (RdmsMaterialReturn materialReturn : materialList_return) {
                    List<RdmsMaterial> originMaterialByTokenCode = rdmsMaterialService.getOriginMaterialByTokenCode(materialReturn.getTokenCode());
                    for (RdmsMaterial material : originMaterialByTokenCode) {
                        material.setTokenCode(null);
                        rdmsMaterialService.update(material);
                    }
                }

                //处理原始领料单状态
                RdmsMaterialManageReturn rdmsMaterialManageReturn = rdmsMaterialManageService.selectByPrimaryKey_return(materialManageDto.getId());
                RdmsMaterialManageDto originMaterialManageItem = rdmsMaterialManageService.getMaterialApplicationByCode_origin(rdmsMaterialManageReturn.getOriginCode());
                originMaterialManageItem.setOpeStatus(null);
                rdmsMaterialManageService.update(originMaterialManageItem);

                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManageDto.getId());
                materialProcess.setExecutorId(null);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManageDto.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("退料申请被驳回!");
                materialProcess.setNextNode(materialManageDto.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);

            }

        } else if (!ObjectUtils.isEmpty(materialManageDto.getOpeStatus())
                && (materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus()))) {

            if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.SUPPLEMENT.getStatus())) {
                //增补材料的情况
                RdmsMaterialManageReturn materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class);
                materialManage.setOpeStatus(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus());
                materialManage.setNextNode(materialManage.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
                materialManage.setStatus(materialManageDto.getApproveStatus());
                materialManage.setApproveTime(new Date());
                rdmsMaterialManageService.updateByPrimaryKeySelective_return(materialManage);

                String executorId = materialManage.getNextNode();  //质量工单时产品经理  //其他工单时项目经理  //预立项阶段是系统工程师或产品经理
                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManage.getId());
                materialProcess.setExecutorId(executorId);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("退料退回增补材料!");
                materialProcess.setNextNode(materialManage.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);

                responseDto.setContent(materialManage.getId());

            }

            if (materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())) {
                //复核通过的情况
                RdmsMaterialManageReturn materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class);
                materialManage.setOpeStatus(MaterialOpeStatusEnum.RETURN_COMPLETE.getStatus());
                materialManage.setNextNode(null);
                materialManage.setStatus(materialManageDto.getApproveStatus());
                materialManage.setApproveTime(new Date());
                rdmsMaterialManageService.updateByPrimaryKeySelective_return(materialManage);

                String executorId = materialManage.getNextNode();  //质量工单时产品经理  //其他工单时项目经理  //预立项阶段是系统工程师或产品经理
                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManage.getId());
                materialProcess.setExecutorId(executorId);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("退料退回增补材料!");
                materialProcess.setNextNode(materialManage.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);

                responseDto.setContent(materialManage.getId());
            }
        } else {
            //处理领料的情况
            if (materialManageDto.getStatus().equals(ApplicationStatusEnum.APPLICATION.getStatus())
                    && materialManageDto.getApproveStatus().equals(ApplicationStatusEnum.PRE_APPROVED.getStatus())) {   //此时的approveStatus是PRE_APPROVED
                //查询到物料申请的发起人
                if (!ObjectUtils.isEmpty(materialManageDto.getWriterId())) {
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialManageDto.getWriterId());
                    //项目OA推送留存
                    if (true)  //false不向OA推送流程
                    {
                        //将流程推送到OA
                        RdmsOaHrmUser rdmsOaHrmUser = rdmsOaHrmUserService.selectByLoginIdAndSaveToDB(rdmsCustomerUser.getLoginName());
                        if (rdmsOaHrmUser != null) {
                            String userId = rdmsOaHrmUser.getId();
                            String departmentid = rdmsOaHrmUser.getDepartmentid();
                            String title = materialManageDto.getName();
                            Integer workflowId = 1222;

                            //mainData
                            List<Field<String>> mainData = new ArrayList<>();
                            {
                                /*Field<String> mainData0 = new Field<>();
                                if(!ObjectUtils.isEmpty(materialManageDto.getRequestId())){
                                    mainData0.setFieldName("requestId");
                                    mainData0.setFieldValue(materialManageDto.getRequestId());
                                }*/
                                Field<String> mainData1 = new Field<>();
                                mainData1.setFieldName("sqr");  //申请人
                                mainData1.setFieldValue(userId);
                                Field<String> mainData2 = new Field<>();
                                mainData2.setFieldName("sqrq");  //申请日期
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String format = simpleDateFormat.format(materialManageDto.getCreateTime());
                                mainData2.setFieldValue(format);
                                Field<String> mainData3 = new Field<>();
                                mainData3.setFieldName("szbm");  //部门代码
                                mainData3.setFieldValue(departmentid);
                                Field<String> mainData4 = new Field<>();
                                mainData4.setFieldName("szgs");  //所属公司
                                mainData4.setFieldValue("2");
                                Field<String> mainData5 = new Field<>();
                                mainData5.setFieldName("fhbh");  //发货编号
                                mainData5.setFieldValue("");
                                Field<String> mainData6 = new Field<>();
                                mainData6.setFieldName("bz");  //备注
                                if(!ObjectUtils.isEmpty(materialManageDto.getRequestId())){
                                    mainData6.setFieldValue(materialManageDto.getReason()+"(原单RequestId:"+materialManageDto.getRequestId()+")");
                                }else{
                                    mainData6.setFieldValue(materialManageDto.getReason());
                                }

                                /*if(!ObjectUtils.isEmpty(materialManageDto.getRequestId())){
                                    mainData.add(mainData0);
                                }*/
                                mainData.add(mainData1);
                                mainData.add(mainData2);
                                mainData.add(mainData3);
                                mainData.add(mainData4);
                                mainData.add(mainData5);
                                mainData.add(mainData6);
                            }

                            List<RdmsSAPMaterialDto> sapMaterialDtos = new ArrayList<>();
                            List<RdmsMaterialDto> materialList = materialManageDto.getMaterialList();

                            for (RdmsMaterialDto materialDto : materialList) {
                                RdmsSAPMaterialDto sapMaterialDto = new RdmsSAPMaterialDto();
                                //String code = materialDto.getCode().substring(15, 19);  //单号
                                //private Integer code; // 记录编码
                                sapMaterialDto.setCode(0);  //这个值只能是0,任何其他数字都不能正常显示!!!!!
                                //private String materialNo; // 物料编号
                                sapMaterialDto.setMaterialNo(materialDto.getBomCode());
                                //private String materialName; // 物料名称
                                sapMaterialDto.setMaterialName(materialDto.getName());
                                //private String materialSpcf; // 规格型号
                                sapMaterialDto.setMaterialSpcf(materialDto.getModel());
                                //private String warehouseNo; // 仓库编号
                                sapMaterialDto.setWarehouseNo(materialDto.getWarehouseCode());
                                //private String warehouseName; // 仓库名称
                                sapMaterialDto.setWarehouseName("");
                                //private String warehouseCode; // 仓库编码
                                sapMaterialDto.setWarehouseCode("");
                                //private String batchCodeSno; // 批次号
                                sapMaterialDto.setBatchCodeSno(materialDto.getBatchCode());
                                //private String materialType; // 物料类型
                                sapMaterialDto.setMaterialType("");
                                //private Integer num; // 数量
                                sapMaterialDto.setNum(materialDto.getAmount().intValue());
                                //private String unit; // 单位
                                sapMaterialDto.setUnit(materialDto.getUnit());
                                //private String usageMode;  //使用方式  借用等
                                sapMaterialDto.setUsageMode(materialManageDto.getUsageMode());

                                sapMaterialDto.setReserve1(materialDto.getReserve1());   //原始项目号_用于财务成本统计
                                sapMaterialDto.setReserve2(materialDto.getReserve2());

                                RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                                if (!ObjectUtils.isEmpty(rdmsJobItem) && !ObjectUtils.isEmpty(rdmsJobItem.getProjectId())) {
                                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                    //private String projectCode;  //项目代码
                                    sapMaterialDto.setProjectCode(rdmsProject.getProjectCode());
                                    //private String projectName;  //项目名称
                                    sapMaterialDto.setProjectName(rdmsProject.getProjectName());
                                } else if (!ObjectUtils.isEmpty(rdmsJobItem) && !ObjectUtils.isEmpty(rdmsJobItem.getPreProjectId())) {
                                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsJobItem.getPreProjectId());
                                    //private String projectCode;  //项目代码
                                    sapMaterialDto.setProjectCode(rdmsPreProject.getProjectCode());
                                    //private String projectName;  //项目名称
                                    sapMaterialDto.setProjectName(rdmsPreProject.getPreProjectName());
                                }

                                sapMaterialDtos.add(sapMaterialDto);
                            }

                            DetailData detailData = new DetailData();
                            detailData.setTableDBName("formtable_main_213_dt1");

                            OaFlowUtils oaFlowUtils = new OaFlowUtils();
                            List<WorkflowRequestTableRecord> workflowRequestTableRecords = oaFlowUtils.createOADetailDateRecordList(sapMaterialDtos);

                            detailData.setWorkflowRequestTableRecords(workflowRequestTableRecords);

                            String API_URL = "/api/workflow/paService/doCreateRequest";

                            OaFlowUtils flowUtils = new OaFlowUtils<>();
                            title += "-(" + materialManageDto.getCode()+ ":" + rdmsOaHrmUser.getLastname() + ")";
                            OaRes oaRes = flowUtils.doCreateRequest(API_URL, OA_MATERIAL_FORM_CREATER, workflowId, title, mainData, detailData);

                            if (oaRes.getCode().equals("SUCCESS")) {
                                //将物料信息保存到物料表中
                                for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
                                    RdmsMaterial material = CopyUtil.copy(materialDto, RdmsMaterial.class);
                                    material.setUsageMode(materialManageDto.getUsageMode());
                                    material.setStatus(materialManageDto.getApproveStatus());
                                    rdmsMaterialService.update(material);
                                }

                                //保存申请表
                                RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                                String executorId = materialManage.getNextNode();  //质量工单时产品经理  //其他工单时项目经理
                                materialManage.setNextNode(materialManage.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
                                materialManage.setStatus(materialManageDto.getApproveStatus());
                                materialManage.setApproveTime(new Date());
                                materialManage.setRequestId(oaRes.getData().getRequestid().toString());  //记录requestId
                                rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);

                                //填写一条过程记录
                                RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
                                materialProcess.setMaterialId(materialManage.getId());
                                materialProcess.setExecutorId(executorId);
                                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                                materialProcess.setDeep((int) materialProcessCount);
                                materialProcess.setDescription("项目经理审批完成");
                                materialProcess.setNextNode(materialManage.getNextNode());
                                materialProcess.setSubmitTime(new Date());
                                materialProcess.setStatus(materialManageDto.getApproveStatus());
                                rdmsMaterialProcessService.save(materialProcess);

                                responseDto.setContent(materialManage.getId());
                            } else {
                                LOG.error("OA接口调用失败: {}:{}", oaRes.getCode(),oaRes.getErrMsg() );
                                throw new BusinessException(BusinessExceptionCode.OA_INTERFACE_ERROR);
                            }
                        } else {
                            LOG.error("OA用户不存在, 发生在MaterialController类approveMaterialApplicationBySap方法中");
                            throw new BusinessException(BusinessExceptionCode.USER_NOT_EXIST_ERROR);
                        }
                    } else {
                        //!!!在不向OA推送流程的情况下做本地测试!!!
                        //将物料信息保存到物料表中
                        for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
                            RdmsMaterial material = CopyUtil.copy(materialDto, RdmsMaterial.class);
                            material.setUsageMode(materialManageDto.getUsageMode());
                            material.setStatus(materialManageDto.getApproveStatus());
                            rdmsMaterialService.update(material);
                        }

                        //保存申请表
                        RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                        String executorId = materialManage.getNextNode();  //质量工单时产品经理  //其他工单时项目经理
                        materialManage.setNextNode(materialManage.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
                        materialManage.setStatus(materialManageDto.getApproveStatus());
                        materialManage.setApproveTime(new Date());
                        double random = (Math.random() * 100000 + 1);
                        int number = (int) random;
                        materialManage.setRequestId(""+number);
                        rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);

                        //填写一条过程记录
                        RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
                        materialProcess.setMaterialId(materialManage.getId());
                        materialProcess.setExecutorId(executorId);
                        long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                        materialProcess.setDeep((int) materialProcessCount);
                        materialProcess.setDescription("项目经理审批完成");
                        materialProcess.setNextNode(materialManage.getNextNode());
                        materialProcess.setSubmitTime(new Date());
                        materialProcess.setStatus(materialManageDto.getApproveStatus());
                        rdmsMaterialProcessService.save(materialProcess);

                        responseDto.setContent(materialManage.getId());
                    }
                } else {
                    LOG.error("输入参数错误, 发生在MaterialController类approveMaterialApplicationBySap方法中");
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }
            } else {
                //将物料信息保存到物料表中
                for (RdmsMaterialDto materialDto : materialManageDto.getMaterialList()) {
                    RdmsMaterial material = CopyUtil.copy(materialDto, RdmsMaterial.class);
                    material.setUsageMode(materialManageDto.getUsageMode());
                    material.setStatus(materialManageDto.getApproveStatus());
                    rdmsMaterialService.update(material);
                }

                //保存申请表
                RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                String executorId = materialManage.getNextNode();  //质量工单时产品经理  //其他工单时项目经理
                materialManage.setNextNode(materialManage.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
                materialManage.setStatus(materialManageDto.getApproveStatus());
                materialManage.setApproveTime(new Date());
                rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);

                //填写一条过程记录
                RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
                materialProcess.setMaterialId(materialManage.getId());
                materialProcess.setExecutorId(executorId);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManage.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("项目经理审批完成");
                materialProcess.setNextNode(materialManage.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save(materialProcess);

                responseDto.setContent(materialManage.getId());
            }
        }

        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/listMaterialsByCharacterId")
    public ResponseDto<PageDto<RdmsMaterialManageDto>> listMaterialsByCharacterId(@RequestBody PageDto<RdmsMaterialManageDto> pageDto) {
        ResponseDto<PageDto<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        rdmsMaterialManageService.listByCharacterId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getQualityMaterialApplicationNumByUserId/{userId}")
    public ResponseDto<Long> getQualityMaterialApplicationNumByUserId(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        Long applicationNumByUserId = rdmsMaterialManageService.getQualityMaterialApplicationNumByUserId(userId);
        responseDto.setContent(applicationNumByUserId);
        return responseDto;
    }

    @PostMapping("/getListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialManageDto>> getListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> listByCharacterId = rdmsMaterialManageService.getListByCharacterId(characterId, false);
        responseDto.setContent(listByCharacterId);
        return responseDto;
    }

    @PostMapping("/getListByNextNodeIdForApprover/{loginUser}")
    public ResponseDto<List<RdmsMaterialManageDto>> getListByNextNodeIdForApprover(@PathVariable String loginUser) {
        ResponseDto<List<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> listByLoginUser = rdmsMaterialManageService.getListByNextNodeIdForApprover(loginUser);
        responseDto.setContent(listByLoginUser);
        return responseDto;
    }

    @PostMapping("/getListByQualityId/{qualityId}")
    public ResponseDto<List<RdmsMaterialManageDto>> getListByQualityId(@PathVariable String qualityId) {
        ResponseDto<List<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> listByQualityId = rdmsMaterialManageService.getListByQualityId(qualityId);
        responseDto.setContent(listByQualityId);
        return responseDto;
    }

    @PostMapping("/getListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsMaterialManageDto>> getListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> listByJobitemId = rdmsMaterialManageService.getTaskJobMaterialListBySubprojectId(subprojectId);
        responseDto.setContent(listByJobitemId);
        return responseDto;
    }

    @PostMapping("/getMaterialListByApplicationCode/{code}")
    public ResponseDto<List<RdmsMaterialDto>> getMaterialListByApplicationCode(@PathVariable String code) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialDto> materialListByApplicationCode = rdmsMaterialService.getMaterialListByApplicationCode(code);
        responseDto.setContent(materialListByApplicationCode);
        return responseDto;
    }

    @PostMapping("/getListByPreProjectId/{preprojectId}")
    public ResponseDto<List<RdmsMaterialManageDto>> getListByPreProjectId(@PathVariable String preprojectId) {
        ResponseDto<List<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> listByJobitemId = rdmsMaterialManageService.getTaskJobMaterialListByPreProjectId(preprojectId);
        responseDto.setContent(listByJobitemId);
        return responseDto;
    }

    @PostMapping("/getListByProjectId/{projectId}")
    public ResponseDto<List<RdmsMaterialManageDto>> getListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> listByJobitemId = rdmsMaterialManageService.getTaskJobMaterialListByProjectId(projectId);
        responseDto.setContent(listByJobitemId);
        return responseDto;
    }

    @PostMapping("/getBomMaterialListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsBomDto>> getBomMaterialListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsBomDto>> responseDto = new ResponseDto<>();
        List<RdmsBomDto> bomMaterialListByCharacterId = rdmsMaterialService.getBomMaterialListByCharacterId(characterId);
        responseDto.setContent(bomMaterialListByCharacterId);
        return responseDto;
    }

    @PostMapping("/getSAPMaterialList/{customerId}")
    public ResponseDto<List<RdmsSAPMaterialDto>> getSAPMaterialList(@PathVariable String customerId) {
        ResponseDto<List<RdmsSAPMaterialDto>> responseDto = new ResponseDto<>();
        List<RdmsSAPMaterialDto> sapdbMaterialList = jdbcService.getSAPDBMaterialList();
        responseDto.setContent(sapdbMaterialList);
        return responseDto;
    }

    @PostMapping("/getSAPMaterialByBomCodeAndBatchCode")
    public ResponseDto<List<RdmsSAPMaterialDto>> getSAPMaterialByBomCodeAndBatchCode(@RequestParam String bomCode, String warehouseCode) {
        ResponseDto<List<RdmsSAPMaterialDto>> responseDto = new ResponseDto<>();
        List<RdmsSAPMaterialDto> sapdbMaterialList = jdbcService.getSAPMaterialByBomCodeAndBatchCode(bomCode, warehouseCode);
        responseDto.setContent(sapdbMaterialList);
        return responseDto;
    }

    @PostMapping("/getBomMaterialListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsBomDto>> getBomMaterialListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsBomDto>> responseDto = new ResponseDto<>();
        List<RdmsBomDto> bomMaterialListByCharacterId = rdmsMaterialService.getBomMaterialListBySubprojectId(subprojectId);
        responseDto.setContent(bomMaterialListByCharacterId);
        return responseDto;
    }

    @PostMapping("/getCommonBomMaterialListByProjectId/{projectId}")
    public ResponseDto<List<RdmsBomDto>> getCommonBomMaterialListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsBomDto>> responseDto = new ResponseDto<>();
        List<RdmsBomDto> bomMaterialListByCharacterId = rdmsMaterialService.getCommonBomMaterialListByProjectId(projectId);
        responseDto.setContent(bomMaterialListByCharacterId);
        return responseDto;
    }

    @PostMapping("/getArchivedCharacterListByProjectId/{projectId}")
    public ResponseDto<List<RdmsCharacterDto>> getArchivedCharacterListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> archivedCharacterListByProjectId = rdmsMaterialService.getArchivedCharacterListByProjectId(projectId);
        responseDto.setContent(archivedCharacterListByProjectId);
        return responseDto;
    }

    @PostMapping("/getBomMaterialListByProjectId/{projectId}")
    public ResponseDto<List<RdmsBomDto>> getBomMaterialListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsBomDto>> responseDto = new ResponseDto<>();
        List<RdmsBomDto> bomMaterialListByProjectId = rdmsMaterialService.getBomMaterialListByProjectId(projectId);
        responseDto.setContent(bomMaterialListByProjectId);
        return responseDto;
    }

    @PostMapping("/getBomMaterialListByCharacterIdList")
    public ResponseDto<List<RdmsBomDto>> getBomMaterialListByCharacterIdList(@RequestBody List<String> characterIdList) {
        ResponseDto<List<RdmsBomDto>> responseDto = new ResponseDto<>();
        List<RdmsBomDto> bomMaterialList = rdmsMaterialService.getBomMaterialListByCharacterIdList(characterIdList);
        responseDto.setContent(bomMaterialList);
        return responseDto;
    }

    @PostMapping("/getMaterialListByProjectId/{projectId}")
    public ResponseDto<RdmsBomConfigDto> getMaterialListByProjectId(@PathVariable String projectId) {
        ResponseDto<RdmsBomConfigDto> responseDto = new ResponseDto<>();
        RdmsBomConfigDto materialListByProjectId = rdmsMaterialService.getMaterialListByProjectId(projectId);
        responseDto.setContent(materialListByProjectId);
        return responseDto;
    }

    @PostMapping("/getMaterialManageItemByCode/{code}")
    public ResponseDto<RdmsMaterialManageDto> getMaterialManageItemByCode(@PathVariable String code) {
        ResponseDto<RdmsMaterialManageDto> responseDto = new ResponseDto<>();
        RdmsMaterialManageDto materialApplicationByCode = rdmsMaterialManageService.getMaterialApplicationByCode_origin(code);
        responseDto.setContent(materialApplicationByCode);
        return responseDto;
    }

    @PostMapping("/getMaterialListById/{bomConfigId}")
    public ResponseDto<RdmsBomConfigDto> getMaterialListById(@PathVariable String bomConfigId) {
        ResponseDto<RdmsBomConfigDto> responseDto = new ResponseDto<>();
        RdmsBomConfigDto materialList = rdmsMaterialService.getMaterialListById(bomConfigId);
        responseDto.setContent(materialList);
        return responseDto;
    }

    @PostMapping("/getCapitalizationListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getCapitalizationListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.CAPITALIZATION);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getInHandListByCharacterIdAndUsageMode(characterId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getCapitalizationListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsMaterialDto>> getCapitalizationListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.CAPITALIZATION);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getInHandListBySubprojectIdAndUsageMode(subprojectId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getCapitalizationSummaryListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getCapitalizationSummaryListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.CAPITALIZATION);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getMaterialSummaryListByCharacterIdAndUsageMode(characterId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, null);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getBorrowListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getBorrowListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.BORROW);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getInHandListByCharacterIdAndUsageMode(characterId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getBorrowListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsMaterialDto>> getBorrowListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.BORROW);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getInHandListBySubprojectIdAndUsageMode(subprojectId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getBorrowListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsMaterialDto>> getBorrowListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.BORROW);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getInHandListByCustomerIdAndUsageMode(customerId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getExpenditureListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsMaterialDto>> getExpenditureListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.EXPENDITURE);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getInHandListByCustomerIdAndUsageMode(customerId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getCapitalizationListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsMaterialDto>> getCapitalizationListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.CAPITALIZATION);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getInHandListByCustomerIdAndUsageMode(customerId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getBorrowSummaryListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getBorrowSummaryListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.BORROW);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getMaterialSummaryListByCharacterIdAndUsageMode(characterId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, null);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getExpenditureListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getExpenditureListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.EXPENDITURE);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getHaveApprovedListByCharacterIdAndUsageMode(characterId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_COMPLETE);
                statusEnumList.add(ApplicationStatusEnum.COMPLETE);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getExpenditureListBySubProjectId/{subprojectId}")
    public ResponseDto<List<RdmsMaterialDto>> getExpenditureListBySubProjectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.EXPENDITURE);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getHaveApprovedListBySubprojectIdAndUsageMode(subprojectId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<ApplicationStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ApplicationStatusEnum.PRE_APPROVED);
                statusEnumList.add(ApplicationStatusEnum.APPROVED);
                statusEnumList.add(ApplicationStatusEnum.SUBMIT);
                statusEnumList.add(ApplicationStatusEnum.SUPPLEMENT);
                statusEnumList.add(ApplicationStatusEnum.PRE_COMPLETE);
                statusEnumList.add(ApplicationStatusEnum.COMPLETE);
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, statusEnumList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getExpenditureSummaryListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getExpenditureSummaryListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<MaterialUsageModeEnum> usageModeEnums = new ArrayList<>();
        usageModeEnums.add(MaterialUsageModeEnum.EXPENDITURE);
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getMaterialSummaryListByCharacterIdAndUsageMode(characterId, usageModeEnums);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, null);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getMaterialListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getMaterialListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getMaterialSummaryListByCharacterIdAndUsageMode(characterId, null);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListByCodeInListAndStatusList(stringList, null);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getMaterialListForBomSelected/{characterId}")
    public ResponseDto<List<RdmsMaterialDto>> getMaterialListForBomSelected(@PathVariable String characterId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManage> materialManageList = rdmsMaterialManageService.getMaterialManageListForBomSelected(characterId);
        if (!CollectionUtils.isEmpty(materialManageList)) {
            List<String> stringList = materialManageList.stream().map(RdmsMaterialManage::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListForBomSelected(stringList, characterId);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getDirectMaterialListForBomSelected/{subprojectId}")
    public ResponseDto<List<RdmsMaterialDto>> getDirectMaterialListForBomSelected(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsMaterialDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> materialManageDtoList = rdmsMaterialManageService.getDirectMaterialSummaryListBySubprojectIdAndUsageMode(subprojectId, null);
        if (!CollectionUtils.isEmpty(materialManageDtoList)) {
            List<String> stringList = materialManageDtoList.stream().map(RdmsMaterialManageDto::getCode).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                List<RdmsMaterialDto> materialListByCodeInList = rdmsMaterialService.getMaterialListForBomSelected(stringList);
                responseDto.setContent(materialListByCodeInList);
            }
        }
        return responseDto;
    }

    @PostMapping("/getMaterialApplicationListByWriterId/{writerId}")
    public ResponseDto<List<RdmsMaterialManageDto>> getMaterialApplicationListByWriterId(@PathVariable String writerId) {
        ResponseDto<List<RdmsMaterialManageDto>> responseDto = new ResponseDto<>();
        List<RdmsMaterialManageDto> listByWriterId = rdmsMaterialManageService.getMaterialApplicationListByWriterId(writerId);
        responseDto.setContent(listByWriterId);
        return responseDto;
    }

    @PostMapping("/getMaterialApplicationNum/{userId}")
    public ResponseDto<Integer> getMaterialApplicationNum(@PathVariable String userId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        Integer materialApplicationNum = rdmsMaterialManageService.getMaterialApplicationAndSubmitSumNum(userId);
        responseDto.setContent(materialApplicationNum);
        return responseDto;
    }

    @PostMapping("/getMaterialApplicationNum_project/{userId}")
    public ResponseDto<Integer> getMaterialApplicationNum_project(@PathVariable String userId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        Integer materialApplicationNum = rdmsMaterialManageService.getMaterialApplicationAndSubmitSumNum_project(userId);
        responseDto.setContent(materialApplicationNum);
        return responseDto;
    }

    @PostMapping("/getMaterialApplicationByCode/{code}")
    public ResponseDto<RdmsMaterialManageDto> getMaterialApplicationByCode(@PathVariable String code) {
        ResponseDto<RdmsMaterialManageDto> responseDto = new ResponseDto<>();
        RdmsMaterialManageDto materialApplicationByCode = rdmsMaterialManageService.getMaterialApplicationByCodeMix(code);
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialApplicationByCode.getWriterId());
        materialApplicationByCode.setWriterName(rdmsCustomerUser.getTrueName());
        if (!ObjectUtils.isEmpty(materialApplicationByCode.getNextNode())) {
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(materialApplicationByCode.getNextNode());
            materialApplicationByCode.setNextNodeName(rdmsCustomerUser1.getTrueName());
        }
        if (!ObjectUtils.isEmpty(materialApplicationByCode.getApproverId())) {
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(materialApplicationByCode.getApproverId());
            materialApplicationByCode.setApproverName(rdmsCustomerUser2.getTrueName());  //项目经理
        }
        if (!ObjectUtils.isEmpty(materialApplicationByCode.getJobitemId())) {
            RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialApplicationByCode.getJobitemId());
            materialApplicationByCode.setJobitemName(rdmsJobItem.getJobName());
        }

        responseDto.setContent(materialApplicationByCode);
        return responseDto;
    }

    @PostMapping("/confirmCancel/{code}")
    public ResponseDto<String> confirmCancel(@PathVariable String code) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsMaterialManageDto materialApplication = rdmsMaterialManageService.getMaterialManageSimpleInfoByCode_origin(code);
        if (ObjectUtils.isEmpty(materialApplication)) {
            RdmsMaterialManageReturn materialManageReturn = rdmsMaterialManageService.getMaterialManageSimpleInfoByCode_return(code);
            if (ObjectUtils.isEmpty(materialManageReturn)) {
                responseDto.setContent(null);
            } else {
                materialManageReturn.setStatus(ApplicationStatusEnum.CANCEL.getStatus());
                String s = rdmsMaterialManageService.update_return(materialManageReturn);
                responseDto.setContent(s);

                //需要将原始的申请单的状态设置为为已批准状态
                RdmsMaterialManageDto materialManage = rdmsMaterialManageService.getMaterialManageSimpleInfoByCode_origin(materialManageReturn.getOriginCode());
                if(!ObjectUtils.isEmpty(materialManage)){
                    materialManage.setOpeStatus(null);
                    rdmsMaterialManageService.update(materialManage);
                }
            }
        } else {
            materialApplication.setStatus(ApplicationStatusEnum.CANCEL.getStatus());
            RdmsMaterialManage copy = CopyUtil.copy(materialApplication, RdmsMaterialManage.class);
            String s = rdmsMaterialManageService.update(copy);
            responseDto.setContent(s);
        }
        return responseDto;
    }

    @PostMapping("/updateMaterialApproveFileListStr")
    public ResponseDto<String> updateMaterialApproveFileListStr(@RequestBody RdmsMaterialManageDto materialManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(materialManageDto.getApproveFileListStr())) {
            RdmsMaterialManage copy = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
            String id = "";
            if (!ObjectUtils.isEmpty(materialManageDto.getOpeStatus()) && materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus())) {
                id = rdmsMaterialManageService.updateMaterialApproveFileListStr_return(copy);
            } else {
                id = rdmsMaterialManageService.updateMaterialApproveFileListStr(copy);
            }
            responseDto.setContent(id);
        } else {
            responseDto.setContent(null);
        }
        return responseDto;
    }

    @PostMapping("/updateMaterialDealWithFileListStr")
    public ResponseDto<String> updateMaterialDealWithFileListStr(@RequestBody RdmsMaterialManageDto materialManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(materialManageDto.getDealWithFileListStr())) {
            RdmsMaterialManage copy = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
            String id = "";
            if (!ObjectUtils.isEmpty(materialManageDto.getOpeStatus()) && materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus())) {
                id = rdmsMaterialManageService.updateMaterialDealWithFileListStr_return(copy);
            } else {
                id = rdmsMaterialManageService.updateMaterialDealWithFileListStr(copy);
            }
            responseDto.setContent(id);
        } else {
            responseDto.setContent(null);
        }
        return responseDto;
    }

    @PostMapping("/updateMaterialCompleteFileListStr")
    public ResponseDto<String> updateMaterialCompleteFileListStr(@RequestBody RdmsMaterialManageDto materialManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(materialManageDto.getCompleteFileListStr())) {
            RdmsMaterialManage copy = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
            String id = "";
            if (!ObjectUtils.isEmpty(materialManageDto.getOpeStatus()) && materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus())) {
                id = rdmsMaterialManageService.updateMaterialCompleteFileListStr_return(copy);
            } else {
                id = rdmsMaterialManageService.updateMaterialCompleteFileListStr(copy);
            }
            responseDto.setContent(id);
        } else {
            responseDto.setContent(null);
        }
        return responseDto;
    }

    @PostMapping("/submit")
    public ResponseDto<String> submit(@RequestBody RdmsMaterialManageDto materialManageDto) {
        ValidatorUtil.require(materialManageDto.getCode(), "申请单号");
        ValidatorUtil.require(materialManageDto.getLoginUserId(), "当前用户");
        ValidatorUtil.require(materialManageDto.getSubmitDescription(), "提交说明");
        ValidatorUtil.length(materialManageDto.getUsageMode(), "领用事由", 2, 500);

        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(materialManageDto.getOpeStatus()) && materialManageDto.getOpeStatus().equals(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus())) {

            RdmsMaterialManageDto materialApplication = rdmsMaterialManageService.getMaterialApplicationByCode_return(materialManageDto.getCode());
            materialApplication.setStatus(ApplicationStatusEnum.SUBMIT.getStatus());
            materialApplication.setSubmitDescription(materialManageDto.getSubmitDescription());
            RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
            if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(materialApplication.getCustomerId());
                materialApplication.setNextNode(sysgmByCustomerId.getSysgmId());
            } else if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())
                    || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                materialApplication.setNextNode(rdmsJobItem.getProductManagerId());
            } else {
                materialApplication.setNextNode(rdmsJobItem.getProjectManagerId());
            }
            RdmsMaterialManageReturn copy = CopyUtil.copy(materialApplication, RdmsMaterialManageReturn.class);
            rdmsMaterialManageService.update_return(copy);

            //填写一条过程记录
            RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
            materialProcess.setMaterialId(materialApplication.getId());
            materialProcess.setExecutorId(materialManageDto.getLoginUserId());
            long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialApplication.getId());
            materialProcess.setDeep((int) materialProcessCount);
            materialProcess.setDescription("物料处置完成, 提交审批");
            if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsJobItem.getPreProjectId());
                materialProcess.setNextNode(rdmsPreProject.getSystemManagerId());
            } else if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                materialApplication.setNextNode(rdmsJobItem.getProductManagerId());
            } else {
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(materialApplication.getSubprojectId());
                materialProcess.setNextNode(subproject.getProjectManagerId());
            }
            materialProcess.setSubmitTime(new Date());
            materialProcess.setStatus(ApplicationStatusEnum.SUBMIT.getStatus());
            rdmsMaterialProcessService.save_return(materialProcess);

            responseDto.setContent(materialApplication.getCode());
        } else {
            RdmsMaterialManageDto materialApplication = rdmsMaterialManageService.getMaterialApplicationByCode_origin(materialManageDto.getCode());
            materialApplication.setStatus(ApplicationStatusEnum.SUBMIT.getStatus());
            materialApplication.setSubmitDescription(materialManageDto.getSubmitDescription());
            RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
            if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(materialApplication.getCustomerId());
                materialApplication.setNextNode(sysgmByCustomerId.getSysgmId());
            } else if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                materialApplication.setNextNode(rdmsJobItem.getProductManagerId());
            } else {
                materialApplication.setNextNode(rdmsJobItem.getProjectManagerId());
            }
            RdmsMaterialManage copy = CopyUtil.copy(materialApplication, RdmsMaterialManage.class);
            rdmsMaterialManageService.update(copy);

            //填写一条过程记录
            RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
            materialProcess.setMaterialId(materialApplication.getId());
            materialProcess.setExecutorId(materialManageDto.getLoginUserId());
            long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialApplication.getId());
            materialProcess.setDeep((int) materialProcessCount);
            materialProcess.setDescription("物料处置完成, 提交审批");
            if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsJobItem.getPreProjectId());
                materialProcess.setNextNode(rdmsPreProject.getSystemManagerId());
            } else if (rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                materialApplication.setNextNode(rdmsJobItem.getProductManagerId());
            } else {
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(materialApplication.getSubprojectId());
                materialProcess.setNextNode(subproject.getProjectManagerId());
            }
            materialProcess.setSubmitTime(new Date());
            materialProcess.setStatus(ApplicationStatusEnum.SUBMIT.getStatus());
            rdmsMaterialProcessService.save(materialProcess);

            responseDto.setContent(materialApplication.getCode());
        }

        return responseDto;
    }

    /**
     * 删除BOM条目
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteBomItem(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsBomService.delete(id);
        return responseDto;
    }

}
