/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsMaterialManageMapper;
import com.course.server.mapper.RdmsMaterialManageReturnMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsMaterialManageService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsMaterialManageService.class);

    @Resource
    private RdmsMaterialManageMapper rdmsMaterialManageMapper;
    @Resource
    private RdmsMaterialManageReturnMapper rdmsMaterialManageReturnMapper;
    @Resource
    private RdmsMaterialService rdmsMaterialService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsQualityService rdmsQualityService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Autowired
    private RdmsProjectService rdmsProjectService;
    @Autowired
    private RdmsPreProjectService rdmsPreProjectService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;
    @Resource
    private RdmsMaterialProcessService rdmsMaterialProcessService;

    //客户物料操作员的ID 刘亚如
    @Value("${material.manager.customer.userid}")
    private String materialManagerId;

    public void listByCustomerId(PageDto<RdmsMaterialManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        PageInfo<RdmsMaterialManage> pageInfo = new PageInfo<>(rdmsMaterialManages);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsMaterialManageDto> rdmsMaterialDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        pageDto.setList(rdmsMaterialDtos);
    }

    public void listByProjectId(PageDto<RdmsMaterialManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria().andProjectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        PageInfo<RdmsMaterialManage> pageInfo = new PageInfo<>(rdmsMaterialManages);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsMaterialManageDto> rdmsMaterialDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        pageDto.setList(rdmsMaterialDtos);
    }

    public void listBySubprojectId(PageDto<RdmsMaterialManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria().andSubprojectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        PageInfo<RdmsMaterialManage> pageInfo = new PageInfo<>(rdmsMaterialManages);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsMaterialManageDto> rdmsMaterialDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        pageDto.setList(rdmsMaterialDtos);
    }

    public void listByCharacterId(PageDto<RdmsMaterialManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("create_time desc");
        materialExample.createCriteria().andCharacterIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        PageInfo<RdmsMaterialManage> pageInfo = new PageInfo<>(rdmsMaterialManages);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsMaterialManageDto> rdmsMaterialDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialDtos)) {
            for (RdmsMaterialManageDto material : rdmsMaterialDtos) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        pageDto.setList(materialManageDtos);
    }

    /**
     *
     */
    public List<RdmsMaterialManageDto> getListByCustomerId(String customerId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        return CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
    }

    /**
     * 处理OA流程审批完成后的状态反馈信息
     * 如果状态为APPROVED 为审批通过, 任何其他状态都作为拒绝处理
     */
    @Transactional
    public void workflowBudgetDealwith(@NotNull RdmsAuthWorkflowStatusDto workflowStatusDto) {
        //根据反馈信息中的单号, 查出当时的申请信息
        if (ObjectUtils.isEmpty(workflowStatusDto) || ObjectUtils.isEmpty(workflowStatusDto.getCode()) || ObjectUtils.isEmpty(workflowStatusDto.getStatus())) {
            return;
        }
        RdmsMaterialManageDto materialManageDto = this.getMaterialApplicationByCodeMix(workflowStatusDto.getCode());
        if (ObjectUtils.isEmpty(materialManageDto)) {
            return;
        }

        if (workflowStatusDto.getStatus().equals(ApplicationStatusEnum.APPROVED.getStatus())) {
            if (!ObjectUtils.isEmpty(materialManageDto)) {
                materialManageDto.setStatus(ApplicationStatusEnum.APPROVED.getStatus());
                materialManageDto.setUpdateTime(new Date());
                materialManageDto.setApproveTime(new Date());
                RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                if(!ObjectUtils.isEmpty(materialManage)){
                    rdmsMaterialManageMapper.updateByPrimaryKey(materialManage);
                }
                RdmsMaterialManageReturn materialManageReturn = CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class);
                if(!ObjectUtils.isEmpty(materialManageReturn)){
                    materialManageReturn.setOpeStatus(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus());
                    rdmsMaterialManageReturnMapper.updateByPrimaryKey(materialManageReturn);
                }

                List<RdmsMaterialDto> materialListByApplicationCode = rdmsMaterialService.getMaterialListByApplicationCodeMix(workflowStatusDto.getCode());
                if (!CollectionUtils.isEmpty(materialListByApplicationCode)) {
                    List<RdmsMaterial> rdmsMaterials = CopyUtil.copyList(materialListByApplicationCode, RdmsMaterial.class);
                    for(RdmsMaterial rdmsMaterial : rdmsMaterials){
                        RdmsMaterial rdmsMaterial1 = rdmsMaterialService.selectByPrimaryKey(rdmsMaterial.getId());
                        if(!ObjectUtils.isEmpty(rdmsMaterial1)){
                            rdmsMaterial1.setStatus(ApplicationStatusEnum.APPROVED.getStatus());
                            rdmsMaterial1.setUpdateTime(new Date());
                            rdmsMaterialService.update(rdmsMaterial1);
                        }
                    }

                    List<RdmsMaterialReturn> rdmsMaterialsReturn = CopyUtil.copyList(materialListByApplicationCode, RdmsMaterialReturn.class);
                    for(RdmsMaterialReturn rdmsMaterialReturn : rdmsMaterialsReturn){
                        RdmsMaterialReturn rdmsMaterialReturn1 = rdmsMaterialService.selectByPrimaryKey_return(rdmsMaterialReturn.getId());
                        if(!ObjectUtils.isEmpty(rdmsMaterialReturn1)){
                            rdmsMaterialReturn1.setStatus(ApplicationStatusEnum.APPROVED.getStatus());
                            rdmsMaterialReturn1.setUpdateTime(new Date());
                            rdmsMaterialService.update_return(rdmsMaterialReturn1);
                        }
                    }
                }

                if (!materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.BORROW.getStatus())) {
                    if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {  //系统工程任务
                        //填写一条预算执行记录, 支付状态为应付未付 , 等物料单处理完成时, 标记为已支付
                        RdmsJobItem jobItemDb = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                        RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
                        budgetResearchExe.setId(null);
                        budgetResearchExe.setCustomerId(jobItemDb.getCustomerId());
                        budgetResearchExe.setPreprojectId(jobItemDb.getPreProjectId());
                        budgetResearchExe.setSerialNo(materialManageDto.getCode());
                        budgetResearchExe.setName(materialManageDto.getName());
                        budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                        // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        //通过OA审批时, 领料审批到approved状态，预算就按照 paid 处理  ！！！！
                        budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                        budgetResearchExe.setPaymentTime(new Date());
                        String save = rdmsBudgetResearchExeService.save(budgetResearchExe);

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
                        budgetResearchExe.setSerialNo(materialManageDto.getCode());
                        budgetResearchExe.setName(materialManageDto.getName());
                        budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                        // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                    || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                            } else {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                            }
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                    || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                            } else {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                            }
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        //通过OA审批时, 领料审批到approved状态，预算就按照 paid 处理  ！！！！
                        budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                        budgetResearchExe.setPaymentTime(new Date());
                        String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                    }

                }
            }
            //填写一条过程记录
            RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
            materialProcess.setMaterialId(materialManageDto.getId());
            materialProcess.setExecutorId(null);
            long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManageDto.getId());
            materialProcess.setDeep((int) materialProcessCount);
            materialProcess.setDescription("领料申请被OA流程批准!");
            materialProcess.setNextNode(materialManageDto.getNextNode());
            materialProcess.setSubmitTime(new Date());
            materialProcess.setStatus(materialManageDto.getApproveStatus());
            rdmsMaterialProcessService.save(materialProcess);

        } else {
            //其他状态码或错误的状态码都会产生 拒绝效应
            materialManageDto.setStatus(ApplicationStatusEnum.REJECT.getStatus());
            materialManageDto.setUpdateTime(new Date());
            rdmsMaterialManageMapper.updateByPrimaryKey(CopyUtil.copy(materialManageDto, RdmsMaterialManage.class));

            List<RdmsMaterial> materialListByApplicationCode = rdmsMaterialService.getMaterialListByApplicationCode_origin(workflowStatusDto.getCode());
            if (!CollectionUtils.isEmpty(materialListByApplicationCode)) {
                List<RdmsMaterial> rdmsMaterials = CopyUtil.copyList(materialListByApplicationCode, RdmsMaterial.class);
                rdmsMaterials.forEach(rdmsMaterial -> {
                    rdmsMaterial.setStatus(ApplicationStatusEnum.REJECT.getStatus());
                    rdmsMaterial.setUpdateTime(new Date());
                    rdmsMaterialService.update(rdmsMaterial);
                });
            }

            //填写一条过程记录
            RdmsMaterialProcess materialProcess = new RdmsMaterialProcess();
            materialProcess.setMaterialId(materialManageDto.getId());
            materialProcess.setExecutorId(null);
            long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManageDto.getId());
            materialProcess.setDeep((int) materialProcessCount);
            materialProcess.setDescription("领料申请被驳回!");
            materialProcess.setNextNode(materialManageDto.getNextNode());
            materialProcess.setSubmitTime(new Date());
            materialProcess.setStatus(materialManageDto.getApproveStatus());
            rdmsMaterialProcessService.save(materialProcess);
        }
    }

    /**
     * 处理OA流程物料退库审批完成后的状态反馈信息
     * 如果状态为APPROVED 为审批通过, 任何其他状态都作为拒绝处理
     */
    @Transactional
    public void workflowReturnWarehouseDealwith(@NotNull RdmsAuthWorkflowStatusDto workflowStatusDto) {
        //根据反馈信息中的单号, 查出当时的申请信息
        if (ObjectUtils.isEmpty(workflowStatusDto) || ObjectUtils.isEmpty(workflowStatusDto.getCode()) || ObjectUtils.isEmpty(workflowStatusDto.getStatus())) {
            return;
        }
        RdmsMaterialManageDto materialManageDto = this.getMaterialApplicationByCode_return(workflowStatusDto.getCode());
        if (ObjectUtils.isEmpty(materialManageDto)) {
            return;
        }

        if (workflowStatusDto.getStatus().equals(ApplicationStatusEnum.APPROVED.getStatus())) {
            if (!ObjectUtils.isEmpty(materialManageDto)) {
                //标记料单状态
                materialManageDto.setStatus(ApplicationStatusEnum.APPROVED.getStatus());
                materialManageDto.setUpdateTime(new Date());
                materialManageDto.setApproveTime(new Date());
                materialManageDto.setOpeStatus(MaterialOpeStatusEnum.RETURN_APPROVED.getStatus());
                rdmsMaterialManageReturnMapper.updateByPrimaryKey(CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class));

                //标记物料状态
                List<RdmsMaterialReturn> materialList = rdmsMaterialService.getMaterialListByApplicationCode_return(workflowStatusDto.getCode());
                if (!CollectionUtils.isEmpty(materialList)) {
                    List<RdmsMaterialReturn> rdmsMaterials = CopyUtil.copyList(materialList, RdmsMaterialReturn.class);
                    for (RdmsMaterialReturn rdmsMaterial : rdmsMaterials){
                        rdmsMaterial.setStatus(ApplicationStatusEnum.APPROVED.getStatus());
                        rdmsMaterial.setUpdateTime(new Date());
                        rdmsMaterialService.update_return(rdmsMaterial);
                    }
                }

                //3. 处理原始领料单状态
                RdmsMaterialManageReturn rdmsMaterialManageReturn = this.selectByPrimaryKey_return(materialManageDto.getId());
                RdmsMaterialManageDto originMaterialManageItem = this.getMaterialApplicationByCode_origin(rdmsMaterialManageReturn.getOriginCode());

                if (originMaterialManageItem != null) {
                    originMaterialManageItem.setOpeStatus(null);  //需要正式批准的时候 设置为null
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

                    this.update(originMaterialManageItem);
                }

                if (!materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.BORROW.getStatus())) {
                    if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_FUNCTION.getType())) {  //系统工程任务
                        //填写一条预算执行记录, 支付状态为应付未付 , 等物料单处理完成时, 标记为已支付
                        RdmsJobItem jobItemDb = rdmsJobItemService.selectByPrimaryKey(materialManageDto.getJobitemId());
                        RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
                        budgetResearchExe.setId(null);
                        budgetResearchExe.setCustomerId(jobItemDb.getCustomerId());
                        budgetResearchExe.setPreprojectId(jobItemDb.getPreProjectId());
                        budgetResearchExe.setSerialNo(materialManageDto.getCode());
                        budgetResearchExe.setName(materialManageDto.getName());
                        budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                        // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
//                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
//                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        //通过OA审批时, 领料审批到approved状态，预算就按照 paid 处理  ！！！！
                        budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                        budgetResearchExe.setPaymentTime(new Date());
                        String save = rdmsBudgetResearchExeService.save(budgetResearchExe);

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
                        budgetResearchExe.setSerialNo(materialManageDto.getCode());
                        budgetResearchExe.setName(materialManageDto.getName());
                        budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                        // 如果是借用 不处理; 如果是 费用化 计 物料费; 如果是资产化 计 设备费
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
//                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                    || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                            } else {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                            }
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        if (materialManageDto.getUsageMode().equals(MaterialUsageModeEnum.CAPITALIZATION.getStatus())) {
                            budgetResearchExe.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
                            budgetResearchExe.setCapitalSource(materialManageDto.getCapitalSource());
//                            budgetResearchExe.setAmount(materialManageDto.getAccountCost());
                            budgetResearchExe.setAmount(materialManageDto.getAccountCost().multiply(BigDecimal.valueOf(-1)));  //负数单
                            if (materialManageDto.getJobType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                                    || materialManageDto.getJobType().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.QUALITY.getType())
                                    || materialManageDto.getAuxStatus().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                            } else {
                                budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                            }
                            RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(materialManageDto.getCode());
                            if (!ObjectUtils.isEmpty(recordBySerialNo)) {
                                budgetResearchExe.setId(recordBySerialNo.getId());
                            }
                        }
                        //通过OA审批时, 领料审批到approved状态，预算就按照 paid 处理  ！！！！
                        budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                        budgetResearchExe.setPaymentTime(new Date());
                        String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                    }

                }
                //填写一条过程记录
                RdmsMaterialProcessReturn materialProcess = new RdmsMaterialProcessReturn();
                materialProcess.setMaterialId(materialManageDto.getId());
                materialProcess.setExecutorId(null);
                long materialProcessCount = rdmsMaterialProcessService.getMaterialProcessCount(materialManageDto.getId());
                materialProcess.setDeep((int) materialProcessCount);
                materialProcess.setDescription("退料申请被OA流程批准!");
                materialProcess.setNextNode(materialManageDto.getNextNode());
                materialProcess.setSubmitTime(new Date());
                materialProcess.setStatus(materialManageDto.getApproveStatus());
                rdmsMaterialProcessService.save_return(materialProcess);
            }
        } else {
            //其他状态码或错误的状态码都会产生 拒绝效应
            //驳回的情况
            materialManageDto.setStatus(ApplicationStatusEnum.REJECT.getStatus());
            materialManageDto.setUpdateTime(new Date());
            materialManageDto.setApproveTime(new Date());
            materialManageDto.setOpeStatus(MaterialOpeStatusEnum.RETURN_COMPLETE.getStatus());
            rdmsMaterialManageReturnMapper.updateByPrimaryKey(CopyUtil.copy(materialManageDto, RdmsMaterialManageReturn.class));

            //处理退库驳回物料的状态
            List<RdmsMaterialReturn> materialList = rdmsMaterialService.getMaterialListByApplicationCode_return(workflowStatusDto.getCode());
            if (!CollectionUtils.isEmpty(materialList)) {
                materialList.forEach(rdmsMaterial -> {
                    rdmsMaterial.setStatus(ApplicationStatusEnum.REJECT.getStatus());
                    rdmsMaterial.setUpdateTime(new Date());
                    rdmsMaterialService.update_return(rdmsMaterial);
                });
            }
            //恢复原始物料表的对应物料状态
            List<RdmsMaterialReturn> materialList_return = rdmsMaterialService.getMaterialListByApplicationCode_return(workflowStatusDto.getCode());
            for (RdmsMaterialReturn materialReturn : materialList_return) {
                List<RdmsMaterial> originMaterialByTokenCode = rdmsMaterialService.getOriginMaterialByTokenCode(materialReturn.getTokenCode());
                for (RdmsMaterial material : originMaterialByTokenCode) {
                    material.setTokenCode(null);
                    rdmsMaterialService.update(material);
                }
            }

            //处理原始领料单状态
            RdmsMaterialManageReturn rdmsMaterialManageReturn = this.selectByPrimaryKey_return(materialManageDto.getId());
            RdmsMaterialManageDto originMaterialManageItem = this.getMaterialApplicationByCode_origin(rdmsMaterialManageReturn.getOriginCode());
            originMaterialManageItem.setOpeStatus(null);
            this.update(originMaterialManageItem);

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
    }

    /**
     *
     */
    public List<RdmsMaterialManageDto> getListByProjectId(String projectId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        return CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
    }

    /**
     *
     */
    public List<RdmsMaterialManageDto> getMaterialListBySubprojectId(String subprojectId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        materialExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getListByCharacterId(String characterId, boolean includeQuality) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        if (!includeQuality) {
            criteria.andJobTypeNotEqualTo(JobItemTypeEnum.QUALITY.getType());
        }
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        RdmsMaterialManageReturnExample materialReturnExample = new RdmsMaterialManageReturnExample();
        materialReturnExample.setOrderByClause("update_time desc");
        materialReturnExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        if (!includeQuality) {
            criteria.andJobTypeNotEqualTo(JobItemTypeEnum.QUALITY.getType());
        }
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialReturnExample);
        List<RdmsMaterialManage> rdmsMaterialManages1 = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManages1);
        rdmsMaterialManages.sort(Comparator.comparing(RdmsMaterialManage::getUpdateTime).reversed());

        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getListByNextNodeIdForApprover(String nextNodeId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.PRE_COMPLETE.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        if(!nextNodeId.equals(materialManagerId)){
            criteria.andNextNodeEqualTo(nextNodeId);
        }
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        RdmsMaterialManageReturnExample materialReturnExample = new RdmsMaterialManageReturnExample();
        materialReturnExample.setOrderByClause("update_time desc");
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList_return.add(ApplicationStatusEnum.PRE_COMPLETE.getStatus());
        RdmsMaterialManageReturnExample.Criteria criteria1 = materialReturnExample.createCriteria()
                .andStatusIn(statusList_return)
                .andDeletedEqualTo(0);
        if(!nextNodeId.equals(materialManagerId)){
            criteria1.andNextNodeEqualTo(nextNodeId);
        }
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialReturnExample);
        List<RdmsMaterialManage> rdmsMaterialManages1 = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManages1);
        rdmsMaterialManages.sort(Comparator.comparing(RdmsMaterialManage::getUpdateTime).reversed());

        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage materialManage : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(materialManage.getId());
                if(!ObjectUtils.isEmpty(simpleRecordInfo) && !ObjectUtils.isEmpty(simpleRecordInfo.getJobitemId())){
                    RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(simpleRecordInfo.getJobitemId());
                    if(!ObjectUtils.isEmpty(rdmsJobItem)){
                        simpleRecordInfo.setJobType(rdmsJobItem.getType());
                        if (!ObjectUtils.isEmpty(simpleRecordInfo.getCharacterId())) {
                            simpleRecordInfo.setDocType(DocTypeEnum.CHARACTER.getType());
                        } else {
                            if (rdmsJobItem.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())) {
                                simpleRecordInfo.setDocType(DocTypeEnum.PRE_PROJECT.getType());
                            } else {
                                simpleRecordInfo.setDocType(DocTypeEnum.SUB_PROJECT.getType());
                            }
                        }
                    }
                }
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getListByQualityId(String qualityId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andQualityIdEqualTo(qualityId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        RdmsMaterialManageReturnExample materialReturnExample = new RdmsMaterialManageReturnExample();
        materialReturnExample.setOrderByClause("update_time desc");
        materialReturnExample.createCriteria()
                .andQualityIdEqualTo(qualityId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialReturnExample);
        List<RdmsMaterialManage> rdmsMaterialManages1 = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManages1);
        rdmsMaterialManages.sort(Comparator.comparing(RdmsMaterialManage::getUpdateTime).reversed());

        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getTaskJobMaterialListBySubprojectId(String subprojectId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        typeList.add(JobItemTypeEnum.DEVELOP.getType());

        materialExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusNotIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        RdmsMaterialManageReturnExample materialReturnExample = new RdmsMaterialManageReturnExample();
        materialReturnExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusNotIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialReturnExample);
        List<RdmsMaterialManage> rdmsMaterialManageList = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManageList);

        List<RdmsMaterialManage> collect = rdmsMaterialManages.stream().sorted(Comparator.comparing(RdmsMaterialManage::getCreateTime).reversed()).collect(Collectors.toList());
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(collect)) {
            for (RdmsMaterialManage material : collect) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getTaskJobMaterialListByPreProjectId(String preprojectId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        materialExample.createCriteria()
                .andPreProjectIdEqualTo(preprojectId)
                .andStatusNotIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        RdmsMaterialManageReturnExample materialReturnExample = new RdmsMaterialManageReturnExample();
        materialReturnExample.setOrderByClause("update_time desc");
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList_return.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList_return = new ArrayList<>();
        typeList_return.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        typeList_return.add(JobItemTypeEnum.ASSIST.getType());
        materialReturnExample.createCriteria()
                .andPreProjectIdEqualTo(preprojectId)
                .andStatusNotIn(statusList_return)
                .andJobTypeIn(typeList_return)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialReturnExample);
        List<RdmsMaterialManage> rdmsMaterialManages1 = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManages1);
        List<RdmsMaterialManage> collect = rdmsMaterialManages.stream().sorted(Comparator.comparing(RdmsMaterialManage::getCreateTime).reversed()).collect(Collectors.toList());

        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(collect)) {
            for (RdmsMaterialManage material : collect) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getTaskJobMaterialListByProjectId(String projectId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_PRODUCT.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        materialExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusNotIn(statusList)
                .andAuxStatusEqualTo(JobItemTypeEnum.TASK_PRODUCT.getType())
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        RdmsMaterialManageReturnExample materialReturnExample = new RdmsMaterialManageReturnExample();
        materialReturnExample.setOrderByClause("update_time desc");
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList_return.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList_return = new ArrayList<>();
        typeList_return.add(JobItemTypeEnum.TASK_PRODUCT.getType());
        typeList_return.add(JobItemTypeEnum.ASSIST.getType());
        materialReturnExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusNotIn(statusList_return)
                .andAuxStatusEqualTo(JobItemTypeEnum.TASK_PRODUCT.getType())
                .andJobTypeIn(typeList_return)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialReturnExample);
        List<RdmsMaterialManage> rdmsMaterialManages1 = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManages1);
        List<RdmsMaterialManage> collect = rdmsMaterialManages.stream().sorted(Comparator.comparing(RdmsMaterialManage::getCreateTime).reversed()).collect(Collectors.toList());

        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(collect)) {
            for (RdmsMaterialManage material : collect) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getListByCharacterIdForStatistics(String characterId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        materialExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getHaveApprovedListByCharacterId(String characterId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        materialExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getHaveApprovedListByCharacterIdAndUsageMode(String characterId, List<MaterialUsageModeEnum> modeEnumList) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<String> modeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(modeEnumList)) {
            for (MaterialUsageModeEnum usageModeEnum : modeEnumList) {
                modeList.add(usageModeEnum.getStatus());
            }
            criteria.andUsageModeIn(modeList);
        }

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getHaveApprovedListBySubprojectIdAndUsageMode(String subprojectId, List<MaterialUsageModeEnum> modeEnumList) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<String> modeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(modeEnumList)) {
            for (MaterialUsageModeEnum usageModeEnum : modeEnumList) {
                modeList.add(usageModeEnum.getStatus());
            }
            criteria.andUsageModeIn(modeList);
        }

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getInHandListByCharacterIdAndUsageMode(String characterId, List<MaterialUsageModeEnum> modeEnumList) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.PRE_APPROVED.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<String> modeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(modeEnumList)) {
            for (MaterialUsageModeEnum usageModeEnum : modeEnumList) {
                modeList.add(usageModeEnum.getStatus());
            }
            criteria.andUsageModeIn(modeList);
        }

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getInHandListBySubprojectIdAndUsageMode(String subprojectId, List<MaterialUsageModeEnum> modeEnumList) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.PRE_APPROVED.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<String> modeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(modeEnumList)) {
            for (MaterialUsageModeEnum usageModeEnum : modeEnumList) {
                modeList.add(usageModeEnum.getStatus());
            }
            criteria.andUsageModeIn(modeList);
        }

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getInHandListByCustomerIdAndUsageMode(String customerId, List<MaterialUsageModeEnum> modeEnumList) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.PRE_APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<String> modeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(modeEnumList)) {
            for (MaterialUsageModeEnum usageModeEnum : modeEnumList) {
                modeList.add(usageModeEnum.getStatus());
            }
            criteria.andUsageModeIn(modeList);
        }

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getMaterialSummaryListByCharacterIdAndUsageMode(String characterId, List<MaterialUsageModeEnum> modeEnumList) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andDeletedEqualTo(0);
        List<String> modeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(modeEnumList)) {
            for (MaterialUsageModeEnum usageModeEnum : modeEnumList) {
                modeList.add(usageModeEnum.getStatus());
            }
            criteria.andUsageModeIn(modeList);
        }

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManage> getMaterialManageListForBomSelected(String characterId) {
        List<RdmsMaterialManage> rdmsMaterialManages = getRdmsMaterialManageList(characterId);

        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        if (!ObjectUtils.isEmpty(character) && !ObjectUtils.isEmpty(character.getModuleIdListStr())) {
            List<String> characterIdList = JSON.parseArray(character.getModuleIdListStr(), String.class);
            if (!CollectionUtils.isEmpty(characterIdList)) {
                for (String id : characterIdList) {
                    List<RdmsMaterialManage> rdmsMaterialManageList = this.getRdmsMaterialManageList(id);
                    rdmsMaterialManages.addAll(rdmsMaterialManageList);
                }
            }
        }
        return rdmsMaterialManages;
    }

    private List<RdmsMaterialManage> getRdmsMaterialManageList(String characterId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        materialManageExample.setOrderByClause("update_time desc");
        RdmsMaterialManageExample.Criteria criteria = materialManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialManageExample);
        return rdmsMaterialManages;
    }

    public List<RdmsMaterialManageDto> getDirectMaterialSummaryListBySubprojectIdAndUsageMode(String subprojectId, List<MaterialUsageModeEnum> modeEnumList) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("update_time desc");
        RdmsMaterialManageExample.Criteria criteria = materialExample.createCriteria()
                .andCharacterIdIsNull()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<String> modeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(modeEnumList)) {
            for (MaterialUsageModeEnum usageModeEnum : modeEnumList) {
                modeList.add(usageModeEnum.getStatus());
            }
            criteria.andUsageModeIn(modeList);
        }

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage material : rdmsMaterialManages) {
                RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                materialManageDtos.add(simpleRecordInfo);
            }
        }
        return materialManageDtos;
    }

    public List<RdmsMaterialManageDto> getMaterialApplicationListByWriterId(String writerId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.setOrderByClause("create_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.COMPLETE.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.NOTSET.getStatus());
        materialExample.createCriteria()
                .andWriterIdEqualTo(writerId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);

        RdmsMaterialManageReturnExample materialExample_return = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList_return.add(ApplicationStatusEnum.COMPLETE.getStatus());
        statusList_return.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList_return.add(ApplicationStatusEnum.NOTSET.getStatus());
        materialExample_return.createCriteria()
                .andWriterIdEqualTo(writerId)
                .andStatusNotIn(statusList_return)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialExample_return);
        List<RdmsMaterialManage> rdmsMaterialManages1 = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManages1);

        List<RdmsMaterialManage> collect = rdmsMaterialManages.stream().sorted(Comparator.comparing(RdmsMaterialManage::getCreateTime).reversed()).collect(Collectors.toList());

        List<RdmsMaterialManageDto> materialManageDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(collect)) {
            for (RdmsMaterialManage material : collect) {
                if(!ObjectUtils.isEmpty(material) && !ObjectUtils.isEmpty(material.getId())){
                    RdmsMaterialManageDto simpleRecordInfo = this.getSimpleRecordInfo(material.getId());
                    materialManageDtos.add(simpleRecordInfo);
                }
            }
        }
        return materialManageDtos;
    }

    public String updateMaterialApproveFileListStr(RdmsMaterialManage materialManage) {
        RdmsMaterialManage rdmsMaterialManage = new RdmsMaterialManage();
        rdmsMaterialManage.setId(materialManage.getId());
        rdmsMaterialManage.setApproveFileListStr(materialManage.getApproveFileListStr());
        this.updateByPrimaryKeySelective(rdmsMaterialManage);

        {
            //设置文件授权 权限
            List<String> fileIdList = JSON.parseArray(materialManage.getDealWithFileListStr(), String.class);
            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
            roleUsersDto.setLoginUserId(null);
            roleUsersDto.setReceiverId(null);
            roleUsersDto.setBossId(null);
            RdmsCustomerUser adminUserInfo = rdmsCustomerUserService.getAdminUserInfo(materialManage.getCustomerId());
            roleUsersDto.setSuperId(adminUserInfo.getId());
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(materialManage.getProjectId());
            if (!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getSupervise())) {
                roleUsersDto.setIpmtId(rdmsProject.getSupervise());
            }
            if (!ObjectUtils.isEmpty(materialManage.getSubprojectId())) {
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(materialManage.getSubprojectId());
                if (!ObjectUtils.isEmpty(rdmsProjectSubproject)) {
                    roleUsersDto.setPjmId(rdmsProjectSubproject.getProjectManagerId());
                    roleUsersDto.setPdmId(rdmsProjectSubproject.getProductManagerId());
                }
            }
            if (!ObjectUtils.isEmpty(materialManage.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(materialManage.getPreProjectId());
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
                roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
            }
            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
        }

        return rdmsMaterialManage.getId();
    }

    public String updateMaterialApproveFileListStr_return(RdmsMaterialManage materialManage) {
        RdmsMaterialManageReturn rdmsMaterialManage = new RdmsMaterialManageReturn();
        rdmsMaterialManage.setId(materialManage.getId());
        rdmsMaterialManage.setApproveFileListStr(materialManage.getApproveFileListStr());
        this.updateByPrimaryKeySelective_return(rdmsMaterialManage);

        {
            //设置文件授权 权限
            List<String> fileIdList = JSON.parseArray(materialManage.getDealWithFileListStr(), String.class);
            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
            roleUsersDto.setLoginUserId(null);
            roleUsersDto.setReceiverId(null);
            roleUsersDto.setBossId(null);
            RdmsCustomerUser adminUserInfo = rdmsCustomerUserService.getAdminUserInfo(materialManage.getCustomerId());
            roleUsersDto.setSuperId(adminUserInfo.getId());
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(materialManage.getProjectId());
            if (!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getSupervise())) {
                roleUsersDto.setIpmtId(rdmsProject.getSupervise());
            }
            if (!ObjectUtils.isEmpty(materialManage.getSubprojectId())) {
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(materialManage.getSubprojectId());
                if (!ObjectUtils.isEmpty(rdmsProjectSubproject)) {
                    roleUsersDto.setPjmId(rdmsProjectSubproject.getProjectManagerId());
                    roleUsersDto.setPdmId(rdmsProjectSubproject.getProductManagerId());
                }
            }
            if (!ObjectUtils.isEmpty(materialManage.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(materialManage.getPreProjectId());
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
                roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
            }
            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
        }

        return rdmsMaterialManage.getId();
    }

    public String updateMaterialDealWithFileListStr(RdmsMaterialManage materialManage) {
        RdmsMaterialManage rdmsMaterialManage = new RdmsMaterialManage();
        rdmsMaterialManage.setId(materialManage.getId());
        rdmsMaterialManage.setDealWithFileListStr(materialManage.getDealWithFileListStr());
        this.updateByPrimaryKeySelective(rdmsMaterialManage);

        {
            //设置文件授权 权限
            List<String> fileIdList = JSON.parseArray(materialManage.getDealWithFileListStr(), String.class);
            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
            roleUsersDto.setLoginUserId(null);
            roleUsersDto.setReceiverId(null);
            roleUsersDto.setBossId(null);
            RdmsCustomerUser adminUserInfo = rdmsCustomerUserService.getAdminUserInfo(materialManage.getCustomerId());
            roleUsersDto.setSuperId(adminUserInfo.getId());
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(materialManage.getProjectId());
            if (!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getSupervise())) {
                roleUsersDto.setIpmtId(rdmsProject.getSupervise());
            }
            if (!ObjectUtils.isEmpty(materialManage.getSubprojectId())) {
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(materialManage.getSubprojectId());
                if (!ObjectUtils.isEmpty(rdmsProjectSubproject)) {
                    roleUsersDto.setPjmId(rdmsProjectSubproject.getProjectManagerId());
                    roleUsersDto.setPdmId(rdmsProjectSubproject.getProductManagerId());
                }
            }
            if (!ObjectUtils.isEmpty(materialManage.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(materialManage.getPreProjectId());
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
                roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
            }
            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
        }

        return rdmsMaterialManage.getId();
    }


    public String updateMaterialDealWithFileListStr_return(RdmsMaterialManage materialManage) {
        RdmsMaterialManageReturn rdmsMaterialManage = new RdmsMaterialManageReturn();
        rdmsMaterialManage.setId(materialManage.getId());
        rdmsMaterialManage.setDealWithFileListStr(materialManage.getDealWithFileListStr());
        this.updateByPrimaryKeySelective_return(rdmsMaterialManage);
        {
            //设置文件授权 权限
            List<String> fileIdList = JSON.parseArray(materialManage.getDealWithFileListStr(), String.class);
            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
            roleUsersDto.setLoginUserId(null);
            roleUsersDto.setReceiverId(null);
            roleUsersDto.setBossId(null);
            RdmsCustomerUser adminUserInfo = rdmsCustomerUserService.getAdminUserInfo(materialManage.getCustomerId());
            roleUsersDto.setSuperId(adminUserInfo.getId());
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(materialManage.getProjectId());
            if (!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getSupervise())) {
                roleUsersDto.setIpmtId(rdmsProject.getSupervise());
            }
            if (!ObjectUtils.isEmpty(materialManage.getSubprojectId())) {
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(materialManage.getSubprojectId());
                if (!ObjectUtils.isEmpty(rdmsProjectSubproject)) {
                    roleUsersDto.setPjmId(rdmsProjectSubproject.getProjectManagerId());
                    roleUsersDto.setPdmId(rdmsProjectSubproject.getProductManagerId());
                }
            }
            if (!ObjectUtils.isEmpty(materialManage.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(materialManage.getPreProjectId());
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
                roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
            }
            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
        }

        return rdmsMaterialManage.getId();
    }


    public String updateMaterialCompleteFileListStr(RdmsMaterialManage materialManage) {
        RdmsMaterialManage rdmsMaterialManage = new RdmsMaterialManage();
        rdmsMaterialManage.setId(materialManage.getId());
        rdmsMaterialManage.setCompleteFileListStr(materialManage.getCompleteFileListStr());
        this.updateByPrimaryKeySelective(rdmsMaterialManage);

        {
            //设置文件授权 权限
            List<String> fileIdList = JSON.parseArray(materialManage.getDealWithFileListStr(), String.class);
            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
            roleUsersDto.setLoginUserId(null);
            roleUsersDto.setReceiverId(null);
            roleUsersDto.setBossId(null);
            RdmsCustomerUser adminUserInfo = rdmsCustomerUserService.getAdminUserInfo(materialManage.getCustomerId());
            roleUsersDto.setSuperId(adminUserInfo.getId());
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(materialManage.getProjectId());
            if (!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getSupervise())) {
                roleUsersDto.setIpmtId(rdmsProject.getSupervise());
            }
            if (!ObjectUtils.isEmpty(materialManage.getSubprojectId())) {
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(materialManage.getSubprojectId());
                if (!ObjectUtils.isEmpty(rdmsProjectSubproject)) {
                    roleUsersDto.setPjmId(rdmsProjectSubproject.getProjectManagerId());
                    roleUsersDto.setPdmId(rdmsProjectSubproject.getProductManagerId());
                }
            }
            if (!ObjectUtils.isEmpty(materialManage.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(materialManage.getPreProjectId());
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
                roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
            }
            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
        }

        return rdmsMaterialManage.getId();
    }

    public String updateMaterialCompleteFileListStr_return(RdmsMaterialManage materialManage) {
        RdmsMaterialManageReturn rdmsMaterialManage = new RdmsMaterialManageReturn();
        rdmsMaterialManage.setId(materialManage.getId());
        rdmsMaterialManage.setCompleteFileListStr(materialManage.getCompleteFileListStr());
        this.updateByPrimaryKeySelective_return(rdmsMaterialManage);

        {
            //设置文件授权 权限
            List<String> fileIdList = JSON.parseArray(materialManage.getDealWithFileListStr(), String.class);
            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
            roleUsersDto.setLoginUserId(null);
            roleUsersDto.setReceiverId(null);
            roleUsersDto.setBossId(null);
            RdmsCustomerUser adminUserInfo = rdmsCustomerUserService.getAdminUserInfo(materialManage.getCustomerId());
            roleUsersDto.setSuperId(adminUserInfo.getId());
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(materialManage.getProjectId());
            if (!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getSupervise())) {
                roleUsersDto.setIpmtId(rdmsProject.getSupervise());
            }
            if (!ObjectUtils.isEmpty(materialManage.getSubprojectId())) {
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(materialManage.getSubprojectId());
                if (!ObjectUtils.isEmpty(rdmsProjectSubproject)) {
                    roleUsersDto.setPjmId(rdmsProjectSubproject.getProjectManagerId());
                    roleUsersDto.setPdmId(rdmsProjectSubproject.getProductManagerId());
                }
            }
            if (!ObjectUtils.isEmpty(materialManage.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(materialManage.getPreProjectId());
                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
                roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
            }
            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
        }

        return rdmsMaterialManage.getId();
    }

    /**
     *
     */
    public List<RdmsMaterialManageDto> getListByJobitemId(String jobitemId) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        return CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
    }

    public List<RdmsMaterialManageDto> getListByJobitemIdAndUseMode(String jobitemId, MaterialUsageModeEnum usageModeEnum) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.PRE_COMPLETE.getStatus());
        statusList.add(ApplicationStatusEnum.COMPLETE.getStatus());
        materialExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId)
                .andDeletedEqualTo(0)
                .andStatusIn(statusList)
                .andUsageModeEqualTo(usageModeEnum.getStatus());

        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        return CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
    }

    public Integer getApplicationNum(String characterId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        materialManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        materialManageReturnExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList_return)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    public Integer getQualityMaterialApplicationNum(String qualityId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        materialManageExample.createCriteria()
                .andQualityIdEqualTo(qualityId)
                .andStatusIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        materialManageReturnExample.createCriteria()
                .andQualityIdEqualTo(qualityId)
                .andStatusIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    public Integer getApplicationNumBySubprojectId(String subprojectId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        typeList.add(JobItemTypeEnum.DEVELOP.getType());
        materialManageExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList_return = new ArrayList<>();
        typeList_return.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList_return.add(JobItemTypeEnum.ASSIST.getType());
        typeList_return.add(JobItemTypeEnum.TASK_TEST.getType());
        typeList_return.add(JobItemTypeEnum.DEVELOP.getType());
        materialManageReturnExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList_return)
                .andJobTypeIn(typeList_return)
                .andDeletedEqualTo(0);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    public Integer getApplicationNumByPreProjectId(String preprojectId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        materialManageExample.createCriteria()
                .andPreProjectIdEqualTo(preprojectId)
                .andStatusIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList_return = new ArrayList<>();
        typeList_return.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        typeList_return.add(JobItemTypeEnum.ASSIST.getType());
        materialManageReturnExample.createCriteria()
                .andPreProjectIdEqualTo(preprojectId)
                .andStatusIn(statusList_return)
                .andJobTypeIn(typeList_return)
                .andDeletedEqualTo(0);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    public Integer getApplicationNumByProjectId(String projectId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_PRODUCT.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        materialManageExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusIn(statusList)
                .andAuxStatusEqualTo(JobItemTypeEnum.TASK_PRODUCT.getType())
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList_return = new ArrayList<>();
        typeList_return.add(JobItemTypeEnum.TASK_PRODUCT.getType());
        typeList_return.add(JobItemTypeEnum.ASSIST.getType());
        materialManageReturnExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusIn(statusList_return)
                .andAuxStatusEqualTo(JobItemTypeEnum.TASK_PRODUCT.getType())
                .andJobTypeIn(typeList_return)
                .andDeletedEqualTo(0);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);

    }

    public Long getApplicationNumByJobitemId(String jobitemId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        materialManageExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        long l = rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        materialManageReturnExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId)
                .andStatusIn(statusList_return)
                .andDeletedEqualTo(0);
        return l + rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    public Long getQualityMaterialApplicationNumByUserId(String customerUserId) {
        List<RdmsQuality> qualityListByPdm = rdmsQualityService.getQualityListByPdm(customerUserId);
        if (!CollectionUtils.isEmpty(qualityListByPdm)) {
            List<String> stringList = qualityListByPdm.stream().map(RdmsQuality::getId).collect(Collectors.toList());
            RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
            List<String> statusList = new ArrayList<>();
            statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
            statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
            materialManageExample.createCriteria()
                    .andNextNodeEqualTo(customerUserId)
                    .andQualityIdIn(stringList)
                    .andStatusIn(statusList)
                    .andDeletedEqualTo(0);
            long l = rdmsMaterialManageMapper.countByExample(materialManageExample);

            RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
            List<String> statusList_return = new ArrayList<>();
            statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
            statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
            materialManageReturnExample.createCriteria()
                    .andNextNodeEqualTo(customerUserId)
                    .andQualityIdIn(stringList)
                    .andStatusIn(statusList_return)
                    .andDeletedEqualTo(0);
            return l + rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
        } else {
            return 0L;
        }

    }

    public Integer getMaterialApplicationAndSubmitSumNum(String userId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        typeList.add(JobItemTypeEnum.DEVELOP.getType());
        typeList.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        materialManageExample.createCriteria()
                .andNextNodeEqualTo(userId)
                .andStatusIn(statusList)
                .andJobTypeNotIn(typeList)
                .andDeletedEqualTo(0);
//        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialManageExample);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList_return = new ArrayList<>();
        typeList_return.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList_return.add(JobItemTypeEnum.ASSIST.getType());
        typeList_return.add(JobItemTypeEnum.TASK_TEST.getType());
        typeList_return.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        typeList_return.add(JobItemTypeEnum.DEVELOP.getType());
        materialManageReturnExample.createCriteria()
                .andNextNodeEqualTo(userId)
                .andStatusIn(statusList_return)
                .andJobTypeNotIn(typeList_return)
                .andDeletedEqualTo(0);
//        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialManageReturnExample);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    public Integer getMaterialApplicationAndSubmitSumNum_project(String userId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        materialManageExample.createCriteria()
                .andNextNodeEqualTo(userId)
                .andStatusIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList_return = new ArrayList<>();
        typeList_return.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList_return.add(JobItemTypeEnum.TASK_TEST.getType());
        materialManageReturnExample.createCriteria()
                .andNextNodeEqualTo(userId)
                .andStatusIn(statusList_return)
                .andJobTypeIn(typeList_return)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialManageReturnExample);
        List<RdmsMaterialManage> rdmsMaterialManages1 = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        rdmsMaterialManages.addAll(rdmsMaterialManages1);

        int count = 0;
        if (!CollectionUtils.isEmpty(rdmsMaterialManages)) {
            for (RdmsMaterialManage materialManage : rdmsMaterialManages) {
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(materialManage.getSubprojectId());
                if (!subproject.getStatus().equals(ProjectStatusEnum.ARCHIVED.getStatus())) {
                    count++;
                }
            }
        }
        return count;
    }

    public Integer getMaterialApplicationNumByCharacterId(String characterId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        materialManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.APPLICATION.getStatus());
        materialManageReturnExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList_return)
                .andDeletedEqualTo(0);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    public Integer getMaterialSubmitNumByCharacterId(String characterId) {
        RdmsMaterialManageExample materialManageExample = new RdmsMaterialManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        materialManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        int i = (int) rdmsMaterialManageMapper.countByExample(materialManageExample);

        RdmsMaterialManageReturnExample materialManageReturnExample = new RdmsMaterialManageReturnExample();
        List<String> statusList_return = new ArrayList<>();
        statusList_return.add(ApplicationStatusEnum.SUBMIT.getStatus());
        materialManageReturnExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList_return)
                .andDeletedEqualTo(0);
        return i + (int) rdmsMaterialManageReturnMapper.countByExample(materialManageReturnExample);
    }

    /**
     *
     */
    public RdmsMaterialManageDto getMaterialApplicationByCodeMix(String applicationCode) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria().andCodeEqualTo(applicationCode).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        if (CollectionUtils.isEmpty(rdmsMaterialManages)) {
            RdmsMaterialManageReturnExample materialExampleReturn = new RdmsMaterialManageReturnExample();
            materialExampleReturn.createCriteria().andCodeEqualTo(applicationCode).andDeletedEqualTo(0);
            List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialExampleReturn);
            rdmsMaterialManages = CopyUtil.copyList(rdmsMaterialManageReturns, RdmsMaterialManage.class);
        }
        if (CollectionUtils.isEmpty(rdmsMaterialManages)) {
            LOG.error("函数: getMaterialApplicationByCode {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        List<RdmsMaterialManageDto> rdmsMaterialManageDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        if (rdmsMaterialManageDtos.size() != 1) {
            LOG.error("函数: getMaterialApplicationByCode {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialManageDto materialManageDto = rdmsMaterialManageDtos.get(0);
        //添加审批附件文件信息
        List<String> appFileIdList = JSON.parseArray(materialManageDto.getApproveFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(appFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : appFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setApproveFileList(fileDtoList);
        }
        //添加物料处置附件文件信息
        List<String> dealFileIdList = JSON.parseArray(materialManageDto.getDealWithFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(dealFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : dealFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setDealWithFileList(fileDtoList);
        }
        //添加物料处置附件文件信息
        List<String> compFileIdList = JSON.parseArray(materialManageDto.getCompleteFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(compFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : compFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setCompleteFileList(fileDtoList);
        }

        //进一步读取物料清单
        List<RdmsMaterialDto> materialListByApplicationCode = rdmsMaterialService.getMaterialListByApplicationCodeMix(materialManageDto.getCode());
        materialManageDto.setMaterialList(materialListByApplicationCode);
        return materialManageDto;
    }

    public RdmsMaterialManageDto getMaterialApplicationByCode_origin(String applicationCode) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria()
                .andCodeEqualTo(applicationCode)
                .andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        if (CollectionUtils.isEmpty(rdmsMaterialManages)) {
            LOG.error("函数: getMaterialApplicationByCode {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        List<RdmsMaterialManageDto> rdmsMaterialManageDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        if (rdmsMaterialManageDtos.size() != 1) {
            LOG.error("函数: getMaterialApplicationByCode {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialManageDto materialManageDto = rdmsMaterialManageDtos.get(0);
        //添加审批附件文件信息
        List<String> appFileIdList = JSON.parseArray(materialManageDto.getApproveFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(appFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : appFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setApproveFileList(fileDtoList);
        }
        //添加物料处置附件文件信息
        List<String> dealFileIdList = JSON.parseArray(materialManageDto.getDealWithFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(dealFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : dealFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setDealWithFileList(fileDtoList);
        }
        //添加物料处置附件文件信息
        List<String> compFileIdList = JSON.parseArray(materialManageDto.getCompleteFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(compFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : compFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setCompleteFileList(fileDtoList);
        }

        //进一步读取物料清单
        List<RdmsMaterial> materialListByApplicationCode = rdmsMaterialService.getMaterialListByApplicationCode_origin(materialManageDto.getCode());
        List<RdmsMaterialDto> rdmsMaterialDtos = CopyUtil.copyList(materialListByApplicationCode, RdmsMaterialDto.class);
        materialManageDto.setMaterialList(rdmsMaterialDtos);
        return materialManageDto;
    }

    public RdmsMaterialManageDto getMaterialApplicationByCode_return(String applicationCode) {
        RdmsMaterialManageReturnExample materialExample = new RdmsMaterialManageReturnExample();
        materialExample.createCriteria().andCodeEqualTo(applicationCode).andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManages = rdmsMaterialManageReturnMapper.selectByExample(materialExample);
        if (CollectionUtils.isEmpty(rdmsMaterialManages)) {
            LOG.error("函数: getMaterialApplicationByCode {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        List<RdmsMaterialManageDto> rdmsMaterialManageDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        if (rdmsMaterialManageDtos.size() != 1) {
            LOG.error("函数: getMaterialApplicationByCode {}", this.getClass().getName());
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialManageDto materialManageDto = rdmsMaterialManageDtos.get(0);
        //添加审批附件文件信息
        List<String> appFileIdList = JSON.parseArray(materialManageDto.getApproveFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(appFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : appFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setApproveFileList(fileDtoList);
        }
        //添加物料处置附件文件信息
        List<String> dealFileIdList = JSON.parseArray(materialManageDto.getDealWithFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(dealFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : dealFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setDealWithFileList(fileDtoList);
        }
        //添加物料处置附件文件信息
        List<String> compFileIdList = JSON.parseArray(materialManageDto.getCompleteFileListStr(), String.class);
        if (!CollectionUtils.isEmpty(compFileIdList)) {
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for (String fileId : compFileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            materialManageDto.setCompleteFileList(fileDtoList);
        }

        //进一步读取物料清单
        List<RdmsMaterialReturn> materialListByApplicationCode = rdmsMaterialService.getMaterialListByApplicationCode_return(materialManageDto.getCode());
        List<RdmsMaterialDto> rdmsMaterialDtos = CopyUtil.copyList(materialListByApplicationCode, RdmsMaterialDto.class);
        materialManageDto.setMaterialList(rdmsMaterialDtos);
        return materialManageDto;
    }

    public RdmsMaterialManageDto getMaterialManageSimpleInfoByCode_origin(String code) {
        RdmsMaterialManageExample materialExample = new RdmsMaterialManageExample();
        materialExample.createCriteria().andCodeEqualTo(code).andDeletedEqualTo(0);
        List<RdmsMaterialManage> rdmsMaterialManages = rdmsMaterialManageMapper.selectByExample(materialExample);
        List<RdmsMaterialManageDto> rdmsMaterialManageDtos = CopyUtil.copyList(rdmsMaterialManages, RdmsMaterialManageDto.class);
        if (rdmsMaterialManageDtos.size() > 1) {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        if (ObjectUtils.isEmpty(rdmsMaterialManageDtos)) {
            return null;
        }
        return rdmsMaterialManageDtos.get(0);
    }

    public RdmsMaterialManageReturn getMaterialManageSimpleInfoByCode_return(String code) {
        RdmsMaterialManageReturnExample materialExample = new RdmsMaterialManageReturnExample();
        materialExample.createCriteria().andCodeEqualTo(code).andDeletedEqualTo(0);
        List<RdmsMaterialManageReturn> rdmsMaterialManageReturns = rdmsMaterialManageReturnMapper.selectByExample(materialExample);
        if (rdmsMaterialManageReturns.size() > 1) {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        if (ObjectUtils.isEmpty(rdmsMaterialManageReturns)) {
            return null;
        }
        return rdmsMaterialManageReturns.get(0);
    }

    public RdmsMaterialManageDto getSimpleRecordInfo(String id) {
        RdmsMaterialManage rdmsMaterialManage = rdmsMaterialManageMapper.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(rdmsMaterialManage)) {
            RdmsMaterialManageReturn rdmsMaterialManageReturn = rdmsMaterialManageReturnMapper.selectByPrimaryKey(id);
            if (ObjectUtils.isEmpty(rdmsMaterialManageReturn)) {
                throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
            } else {
                rdmsMaterialManage = CopyUtil.copy(rdmsMaterialManageReturn, RdmsMaterialManage.class);
            }
        }
        if (ObjectUtils.isEmpty(rdmsMaterialManage)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        RdmsMaterialManageDto materialDto = CopyUtil.copy(rdmsMaterialManage, RdmsMaterialManageDto.class);
        if (!ObjectUtils.isEmpty(materialDto) && !ObjectUtils.isEmpty(materialDto.getCharacterId())) {
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(materialDto.getCharacterId());
            if(!ObjectUtils.isEmpty(rdmsCharacter)){
                materialDto.setCharacterName(rdmsCharacter.getCharacterName());
            }
        }
        if (!ObjectUtils.isEmpty(materialDto) && !ObjectUtils.isEmpty(materialDto.getCustomerId())) {
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(materialDto.getCustomerId());
            if(!ObjectUtils.isEmpty(rdmsCustomer)){
                materialDto.setCustomerName(rdmsCustomer.getCustomerName());
            }
        }
        if (!ObjectUtils.isEmpty(materialDto) && !ObjectUtils.isEmpty(materialDto.getWriterId())) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(materialDto.getWriterId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                materialDto.setWriterName(rdmsCustomerUser.getTrueName());
            }
        }
        if (!ObjectUtils.isEmpty(materialDto) && !ObjectUtils.isEmpty(materialDto.getNextNode())) {
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(materialDto.getNextNode());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser1)){
                materialDto.setNextNodeName(rdmsCustomerUser1.getTrueName());
            }
        }

        if (!ObjectUtils.isEmpty(materialDto.getSubprojectId())) {
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(materialDto.getSubprojectId());
            if(!ObjectUtils.isEmpty(subproject)){
                materialDto.setSubprojectName(subproject.getLabel());
            }
        }
        if (!ObjectUtils.isEmpty(materialDto.getPreProjectId())) {
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(materialDto.getPreProjectId());
            if(!ObjectUtils.isEmpty(rdmsPreProject)){
                materialDto.setPreprojectName(rdmsPreProject.getPreProjectName());
            }
        }
        if (!ObjectUtils.isEmpty(materialDto.getJobitemId())) {
            RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(materialDto.getJobitemId());
            if(!ObjectUtils.isEmpty(rdmsJobItem)){
                materialDto.setJobitemName(rdmsJobItem.getJobName());
            }
        }
        return materialDto;
    }

    public RdmsMaterialManage selectByPrimaryKey(String id) {
        return rdmsMaterialManageMapper.selectByPrimaryKey(id);
    }

    public RdmsMaterialManageReturn selectByPrimaryKey_return(String id) {
        return rdmsMaterialManageReturnMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsMaterialManage material) {
        if (ObjectUtils.isEmpty(material.getId())) {
            RdmsMaterialManage rdmsMaterialManageByCode = this.getMaterialManageSimpleInfoByCode_origin(material.getCode());
            if (!ObjectUtils.isEmpty(rdmsMaterialManageByCode)) {
                throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
            }
            return this.insert(material);
        } else {
            RdmsMaterialManage rdmsMaterialManage = this.selectByPrimaryKey(material.getId());
            if (ObjectUtils.isEmpty(rdmsMaterialManage)) {
                RdmsMaterialManage rdmsMaterialManageByCode = this.getMaterialManageSimpleInfoByCode_origin(material.getCode());
                if (!ObjectUtils.isEmpty(rdmsMaterialManageByCode)) {
                    throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
                }
                return this.insert(material);
            } else {
                return this.update(material);
            }
        }
    }

    public String save_return(RdmsMaterialManageReturn material) {
        if (ObjectUtils.isEmpty(material.getId())) {
            RdmsMaterialManageReturn materialManageSimpleInfoByCodeReturn = this.getMaterialManageSimpleInfoByCode_return(material.getCode());
            if (!ObjectUtils.isEmpty(materialManageSimpleInfoByCodeReturn)) {
                throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
            }
            return this.insert_return(material);
        } else {
            RdmsMaterialManageReturn rdmsMaterialManageReturn = this.selectByPrimaryKey_return(material.getId());
            if (ObjectUtils.isEmpty(rdmsMaterialManageReturn)) {
                RdmsMaterialManageReturn materialManageSimpleInfoByCodeReturn = this.getMaterialManageSimpleInfoByCode_return(material.getCode());
                if (!ObjectUtils.isEmpty(materialManageSimpleInfoByCodeReturn)) {
                    throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
                }
                return this.insert_return(material);
            } else {
                return this.update_return(material);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsMaterialManage material) {
        if (ObjectUtils.isEmpty(material.getId())) {
            material.setId(UuidUtil.getShortUuid());
        }
        RdmsMaterialManage rdmsMaterialManage = rdmsMaterialManageMapper.selectByPrimaryKey(material.getId());
        if (!ObjectUtils.isEmpty(rdmsMaterialManage)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        } else {
            material.setCreateTime(new Date());
            material.setUpdateTime(new Date());
            material.setDeleted(0);
            if (ObjectUtils.isEmpty(material.getAuxStatus())) {
                material.setAuxStatus(ApplicationStatusEnum.NOTSET.getStatus());
            }
            rdmsMaterialManageMapper.insert(material);
            return material.getId();
        }
    }

    private String insert_return(RdmsMaterialManageReturn material) {
        if (ObjectUtils.isEmpty(material.getId())) {
            material.setId(UuidUtil.getShortUuid());
        }
        RdmsMaterialManageReturn rdmsMaterialManageReturn = rdmsMaterialManageReturnMapper.selectByPrimaryKey(material.getId());
        if (!ObjectUtils.isEmpty(rdmsMaterialManageReturn)) {
            LOG.info("相同ID的数据记录已经存在!");
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        } else {
            RdmsMaterialManageDto materialManageSimpleInfoByCodeOrigin = this.getMaterialManageSimpleInfoByCode_origin(material.getCode());
            if(!ObjectUtils.isEmpty(materialManageSimpleInfoByCodeOrigin)){
                LOG.info("相同Code的数据记录已经存在!");
                throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
            }else{
                material.setCreateTime(new Date());
                material.setUpdateTime(new Date());
                material.setDeleted(0);
                if (ObjectUtils.isEmpty(material.getAuxStatus())) {
                    material.setAuxStatus(ApplicationStatusEnum.NOTSET.getStatus());
                }
                rdmsMaterialManageReturnMapper.insert(material);
            }
            return material.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsMaterialManage material) {
        if (ObjectUtils.isEmpty(material.getId())) {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialManage rdmsMaterialManage = this.selectByPrimaryKey(material.getId());
        if (ObjectUtils.isEmpty(rdmsMaterialManage)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        } else {
            material.setUpdateTime(new Date());
            material.setDeleted(0);
            rdmsMaterialManageMapper.updateByPrimaryKey(material);
            return material.getId();
        }
    }

    public String update_return(RdmsMaterialManageReturn material) {
        if (ObjectUtils.isEmpty(material.getId())) {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialManageReturn rdmsMaterialManageReturn = this.selectByPrimaryKey_return(material.getId());
        if (ObjectUtils.isEmpty(rdmsMaterialManageReturn)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        } else {
            material.setUpdateTime(new Date());
            material.setDeleted(0);
            rdmsMaterialManageReturnMapper.updateByPrimaryKey(material);
            return material.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsMaterialManage material) {
        material.setUpdateTime(new Date());
        material.setDeleted(0);
        rdmsMaterialManageMapper.updateByPrimaryKeySelective(material);
        return material.getId();
    }

    public String updateByPrimaryKeySelective_return(RdmsMaterialManageReturn material) {
        material.setUpdateTime(new Date());
        material.setDeleted(0);
        rdmsMaterialManageReturnMapper.updateByPrimaryKeySelective(material);
        return material.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsMaterialManage rdmsMaterialManage = rdmsMaterialManageMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(rdmsMaterialManage)) {
            rdmsMaterialManage.setDeleted(1);
            rdmsMaterialManage.setUpdateTime(new Date());
            this.update(rdmsMaterialManage);
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    public void delete_return(String id) {
        RdmsMaterialManageReturn rdmsMaterialManageReturn = rdmsMaterialManageReturnMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(rdmsMaterialManageReturn)) {
            rdmsMaterialManageReturn.setDeleted(1);
            rdmsMaterialManageReturn.setUpdateTime(new Date());
            this.update_return(rdmsMaterialManageReturn);
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }


}
