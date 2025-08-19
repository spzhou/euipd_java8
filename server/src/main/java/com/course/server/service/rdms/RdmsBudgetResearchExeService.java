/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsBudgetResearchDto;
import com.course.server.dto.RdmsBudgetResearchExeDto;
import com.course.server.dto.rdms.RdmsHmiBudgeStatisticsDto;
import com.course.server.dto.rdms.RdmsHmiBudgeStatisticsItemDto;
import com.course.server.dto.rdms.RdmsProjectDto;
import com.course.server.dto.rdms.RdmsProjectSubprojectDto;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBudgetResearchExeMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RdmsBudgetResearchExeService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsBudgetResearchExeService.class);

    @Resource
    private RdmsBudgetResearchExeMapper rdmsBudgetResearchExeMapper;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsBudgetResearchService rdmsBudgetResearchService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Autowired
    private RdmsPreProjectService rdmsPreProjectService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;

    public BigDecimal getSumActiveManhourFeeByProjectId(String projectId){
        RdmsBudgetResearchExeExample budgetResearchExeExample = new RdmsBudgetResearchExeExample();
        budgetResearchExeExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andClassifyEqualTo(BudgetClassifyStdEnum.STAFF.getClassify())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetResearchExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            BigDecimal reduce = rdmsBudgetResearchExes.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            return reduce;
        }else{
            return BigDecimal.ZERO;
        }

    }

    /**
     * 获取所有子项目项下的预算执行条目
     * @param serialNo
     * @return
     */
    public RdmsBudgetResearchExe getItemByJobitemSerialNo(String serialNo){
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andSerialNoEqualTo(serialNo)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            return rdmsBudgetResearchExes.get(0);
        }else{
            return null;
        }

    }

    /**
     * 获取某个子项目指定费用类别的统计结果
     * @param subprojectId
     * @param classify
     * @return
     */
    public RdmsHmiBudgeStatisticsItemDto getBudgetStatisticsItem(String subprojectId, @NotNull BudgetClassifyStdEnum classify){
        RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
        RdmsBudgetResearchService.ClassifyBudget budgetItem = new RdmsBudgetResearchService.ClassifyBudget();
        switch (classify){
            case EQUIPMENT: {
                budgetItem = rdmsBudgetResearchService.getEquipmentBudgetBySubprojectId(subprojectId);
                break;
            }
            case MATERIAL: {
                budgetItem = rdmsBudgetResearchService.getMaterialBudgetBySubprojectId(subprojectId);
                break;
            }
            case TEST: {
                budgetItem = rdmsBudgetResearchService.getTestBudgetBySubprojectId(subprojectId);
                break;
            }
            case POWER: {
                budgetItem = rdmsBudgetResearchService.getPowerBudgetBySubprojectId(subprojectId);
                break;
            }
            case CONFERENCE: {
                budgetItem = rdmsBudgetResearchService.getConferenceBudgetBySubprojectId(subprojectId);
                break;
            }
            case BUSINESS: {
                budgetItem = rdmsBudgetResearchService.getBusinessBudgetBySubprojectId(subprojectId);
                break;
            }
            case COOPERATION: {
                budgetItem = rdmsBudgetResearchService.getCooperationBudgetBySubprojectId(subprojectId);
                break;
            }
            case PROPERTY: {
                budgetItem = rdmsBudgetResearchService.getPropertyBudgetBySubprojectId(subprojectId);
                break;
            }
            case CONSULTING: {
                budgetItem = rdmsBudgetResearchService.getConsultingBudgetBySubprojectId(subprojectId);
                break;
            }
            case OTHER: {
                budgetItem = rdmsBudgetResearchService.getOtherBudgetBySubprojectId(subprojectId);
                break;
            }
            case LABOR: {
                budgetItem = rdmsBudgetResearchService.getLaborBudgetBySubprojectId(subprojectId);
                break;
            }
            case MANAGEMENT: {
                budgetItem = rdmsBudgetResearchService.getManagementBudgetBySubprojectId(subprojectId);
                break;
            }
            case PERFORMANCE: {
                budgetItem = rdmsBudgetResearchService.getPerformanceBudgetBySubprojectId(subprojectId);
                break;
            }
            case STAFF: {
                budgetItem = rdmsBudgetResearchService.getStaffBudgetBySubprojectId(subprojectId);
                break;
            }
            case INFRASTRUCTURE: {
                budgetItem = rdmsBudgetResearchService.getInfrastructureBudgetBySubprojectId(subprojectId);
                break;
            }

            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if(!ObjectUtils.isEmpty(budgetItem.getBudget())){
            budgeStatisticsItemDto.setApprovedBudgetBt(budgetItem.getApprovedBudgetBt());
            budgeStatisticsItemDto.setApprovedBudgetZc(budgetItem.getApprovedBudgetZc());
            budgeStatisticsItemDto.setApprovedBudget(budgetItem.getApprovedBudget());

            budgeStatisticsItemDto.setBudgetBt(budgetItem.getBudgetBt());
            budgeStatisticsItemDto.setBudgetZc(budgetItem.getBudgetZc());
            budgeStatisticsItemDto.setBudget(budgetItem.getBudget());

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }else{
            budgeStatisticsItemDto.setApprovedBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }

        //汇总数据
        List<String> payClassifyList = new ArrayList<>();
        payClassifyList.add(PayClassifyEnum.CHARACTER.getClassify());
        payClassifyList.add(PayClassifyEnum.TASK_SUBP.getClassify());
        payClassifyList.add(PayClassifyEnum.TASK_TEST.getClassify());
        payClassifyList.add(PayClassifyEnum.SUBP_MATERIAL.getClassify());
        payClassifyList.add(PayClassifyEnum.SUBP_FEE.getClassify());
        payClassifyList.add(PayClassifyEnum.SUB_PROJECT.getClassify());
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andClassifyEqualTo(classify.getClassify())
                .andPayClassifyIn(payClassifyList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            //private String customerId;
            budgeStatisticsItemDto.setCustomerId(rdmsBudgetResearchExes.get(0).getCustomerId());
            //private String projectId;
            budgeStatisticsItemDto.setProjectId(rdmsBudgetResearchExes.get(0).getProjectId());
            //private String subprojectId;
            budgeStatisticsItemDto.setSubprojectId(subprojectId);

            //private String classify;
            budgeStatisticsItemDto.setClassify(classify.getClassify());

            //private BigDecimal budgetExeBt;
            List<RdmsBudgetResearchExe> btList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btList)){
                BigDecimal reduce = btList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExeZc;
            List<RdmsBudgetResearchExe> zcList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcList)){
                BigDecimal reduce = zcList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExe;
            budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExeBt().add(budgeStatisticsItemDto.getBudgetExeZc()));

            //private BigDecimal budgetExeConfirmBt;
            //private BigDecimal budgetExeConfirmZc;
            //private BigDecimal budgetExeConfirm;

            //private BigDecimal budgetOngoingBt;
            List<RdmsBudgetResearchExe> btGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btGoingList)){
                BigDecimal reduce = btGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoingZc;
            List<RdmsBudgetResearchExe> zcGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcGoingList)){
                BigDecimal reduce = zcGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoing;
            budgeStatisticsItemDto.setBudgetOngoing(budgeStatisticsItemDto.getBudgetOngoingBt().add(budgeStatisticsItemDto.getBudgetOngoingZc()));
            //预算总支出统计 包含应付未付的金额
            //budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExe().add(budgeStatisticsItemDto.getBudgetOngoing()));

            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));

        }else{
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }
        return budgeStatisticsItemDto;
    }

    /**
     * 获取某个子项目指定费用类别的统计结果
     * @param preprojectId
     * @param classify
     * @return
     */
    public RdmsHmiBudgeStatisticsItemDto getBudgetStatisticsItem_preProject(String preprojectId, @NotNull BudgetClassifyStdEnum classify){
        RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
        RdmsBudgetResearchService.ClassifyBudget budgetItem = new RdmsBudgetResearchService.ClassifyBudget();
        switch (classify){
            case EQUIPMENT: {
                budgetItem = rdmsBudgetResearchService.getEquipmentBudgetByPreProjectId(preprojectId);
                break;
            }
            case MATERIAL: {
                budgetItem = rdmsBudgetResearchService.getMaterialBudgetByPreProjectId(preprojectId);
                break;
            }
            case TEST: {
                budgetItem = rdmsBudgetResearchService.getTestBudgetByPreProjectId(preprojectId);
                break;
            }
            case POWER: {
                budgetItem = rdmsBudgetResearchService.getPowerBudgetByPreProjectId(preprojectId);
                break;
            }
            case CONFERENCE: {
                budgetItem = rdmsBudgetResearchService.getConferenceBudgetByPreProjectId(preprojectId);
                break;
            }
            case BUSINESS: {
                budgetItem = rdmsBudgetResearchService.getBusinessBudgetByPreProjectId(preprojectId);
                break;
            }
            case COOPERATION: {
                budgetItem = rdmsBudgetResearchService.getCooperationBudgetByPreProjectId(preprojectId);
                break;
            }
            case PROPERTY: {
                budgetItem = rdmsBudgetResearchService.getPropertyBudgetByPreProjectId(preprojectId);
                break;
            }
            case CONSULTING: {
                budgetItem = rdmsBudgetResearchService.getConsultingBudgetByPreProjectId(preprojectId);
                break;
            }
            case OTHER: {
                budgetItem = rdmsBudgetResearchService.getOtherBudgetByPreProjectId(preprojectId);
                break;
            }
            case LABOR: {
                budgetItem = rdmsBudgetResearchService.getLaborBudgetByPreProjectId(preprojectId);
                break;
            }
            case MANAGEMENT: {
                budgetItem = rdmsBudgetResearchService.getManagementBudgetByPreProjectId(preprojectId);
                break;
            }
            case PERFORMANCE: {
                budgetItem = rdmsBudgetResearchService.getPerformanceBudgetByPreProjectId(preprojectId);
                break;
            }
            case STAFF: {
                budgetItem = rdmsBudgetResearchService.getStaffBudgetByPreProjectId(preprojectId);
                break;
            }
            case INFRASTRUCTURE: {
                budgetItem = rdmsBudgetResearchService.getInfrastructureBudgetByPreProjectId(preprojectId);
                break;
            }

            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if(!ObjectUtils.isEmpty(budgetItem.getBudget())){
            budgeStatisticsItemDto.setApprovedBudgetBt(budgetItem.getApprovedBudgetBt());
            budgeStatisticsItemDto.setApprovedBudgetZc(budgetItem.getApprovedBudgetZc());
            budgeStatisticsItemDto.setApprovedBudget(budgetItem.getApprovedBudget());

            budgeStatisticsItemDto.setBudgetBt(budgetItem.getBudgetBt());
            budgeStatisticsItemDto.setBudgetZc(budgetItem.getBudgetZc());
            budgeStatisticsItemDto.setBudget(budgetItem.getBudget());

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }else{
            budgeStatisticsItemDto.setApprovedBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }

        //汇总数据
        List<String> payClassifyList = new ArrayList<>();
        payClassifyList.add(PayClassifyEnum.TASK_FUNCTION.getClassify());
        payClassifyList.add(PayClassifyEnum.FUNCTION_FEE.getClassify());
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andClassifyEqualTo(classify.getClassify())
                .andPayClassifyIn(payClassifyList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            //private String customerId;
            budgeStatisticsItemDto.setCustomerId(rdmsBudgetResearchExes.get(0).getCustomerId());
            //private String projectId;
            budgeStatisticsItemDto.setProjectId(null);
            //private String subprojectId;
            budgeStatisticsItemDto.setSubprojectId(null);

            budgeStatisticsItemDto.setPreprojectId(preprojectId);
            //private String classify;
            budgeStatisticsItemDto.setClassify(classify.getClassify());

            //private BigDecimal budgetExeBt;
            List<RdmsBudgetResearchExe> btList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btList)){
                BigDecimal reduce = btList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExeZc;
            List<RdmsBudgetResearchExe> zcList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcList)){
                BigDecimal reduce = zcList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExe;
            budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExeBt().add(budgeStatisticsItemDto.getBudgetExeZc()));

            //private BigDecimal budgetExeConfirmBt;
            //private BigDecimal budgetExeConfirmZc;
            //private BigDecimal budgetExeConfirm;

            //private BigDecimal budgetOngoingBt;
            List<RdmsBudgetResearchExe> btGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btGoingList)){
                BigDecimal reduce = btGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoingZc;
            List<RdmsBudgetResearchExe> zcGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcGoingList)){
                BigDecimal reduce = zcGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoing;
            budgeStatisticsItemDto.setBudgetOngoing(budgeStatisticsItemDto.getBudgetOngoingBt().add(budgeStatisticsItemDto.getBudgetOngoingZc()));
            //预算总支出统计 包含应付未付的金额
            //budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExe().add(budgeStatisticsItemDto.getBudgetOngoing()));

            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));

        }else{
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }
        return budgeStatisticsItemDto;
    }

    public RdmsHmiBudgeStatisticsItemDto getBudgetStatisticsItemByStage(String customerId, BudgetTypeEnum stage, @NotNull BudgetClassifyStdEnum classify, List<String> payClassifyList){
        RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
        RdmsBudgetResearchService.ClassifyBudget budgetItem = new RdmsBudgetResearchService.ClassifyBudget();
        switch (classify){
            case EQUIPMENT: {
                budgetItem = rdmsBudgetResearchService.getEquipmentBudgetByStage(customerId, stage);
                break;
            }
            case MATERIAL: {
                budgetItem = rdmsBudgetResearchService.getMaterialBudgetByStage(customerId, stage);
                break;
            }
            case TEST: {
                budgetItem = rdmsBudgetResearchService.getTestBudgetByStage(customerId, stage);
                break;
            }
            case POWER: {
                budgetItem = rdmsBudgetResearchService.getPowerBudgetByStage(customerId, stage);
                break;
            }
            case CONFERENCE: {
                budgetItem = rdmsBudgetResearchService.getConferenceBudgetByStage(customerId, stage);
                break;
            }
            case BUSINESS: {
                budgetItem = rdmsBudgetResearchService.getBusinessBudgetByStage(customerId, stage);
                break;
            }
            case COOPERATION: {
                budgetItem = rdmsBudgetResearchService.getCooperationBudgetByStage(customerId, stage);
                break;
            }
            case PROPERTY: {
                budgetItem = rdmsBudgetResearchService.getPropertyBudgetByStage(customerId, stage);
                break;
            }
            case CONSULTING: {
                budgetItem = rdmsBudgetResearchService.getConsultingBudgetByStage(customerId, stage);
                break;
            }
            case OTHER: {
                budgetItem = rdmsBudgetResearchService.getOtherBudgetByStage(customerId, stage);
                break;
            }
            case LABOR: {
                budgetItem = rdmsBudgetResearchService.getLaborBudgetByStage(customerId, stage);
                break;
            }
            case MANAGEMENT: {
                budgetItem = rdmsBudgetResearchService.getManagementBudgetByStage(customerId, stage);
                break;
            }
            case PERFORMANCE: {
                budgetItem = rdmsBudgetResearchService.getPerformanceBudgetByStage(customerId, stage);
                break;
            }
            case STAFF: {
                budgetItem = rdmsBudgetResearchService.getStaffBudgetByStage(customerId, stage);
                break;
            }
            case INFRASTRUCTURE: {
                budgetItem = rdmsBudgetResearchService.getInfrastructureBudgetByStage(customerId, stage);
                break;
            }

            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if(!ObjectUtils.isEmpty(budgetItem.getBudget())){
            budgeStatisticsItemDto.setApprovedBudgetBt(budgetItem.getApprovedBudgetBt());
            budgeStatisticsItemDto.setApprovedBudgetZc(budgetItem.getApprovedBudgetZc());
            budgeStatisticsItemDto.setApprovedBudget(budgetItem.getApprovedBudget());

            budgeStatisticsItemDto.setBudgetBt(budgetItem.getBudgetBt());
            budgeStatisticsItemDto.setBudgetZc(budgetItem.getBudgetZc());
            budgeStatisticsItemDto.setBudget(budgetItem.getBudget());

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }else{
            budgeStatisticsItemDto.setApprovedBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }

        //汇总数据
        if(!CollectionUtils.isEmpty(payClassifyList)){
            for(String payClassify: payClassifyList){
                PayClassifyEnum payClassifyEnumByClassify = PayClassifyEnum.getPayClassifyEnumByClassify(payClassify);
                if(ObjectUtils.isEmpty(payClassifyEnumByClassify)){
                    return null;
                }
            }
        }else{
            return null;
        }

        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andClassifyEqualTo(classify.getClassify())
                .andPayClassifyIn(payClassifyList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            //private String customerId;
            budgeStatisticsItemDto.setCustomerId(rdmsBudgetResearchExes.get(0).getCustomerId());
            //private String projectId;
            budgeStatisticsItemDto.setProjectId(null);
            //private String subprojectId;
            budgeStatisticsItemDto.setSubprojectId(null);

            budgeStatisticsItemDto.setPreprojectId(null);
            //private String classify;
            budgeStatisticsItemDto.setClassify(classify.getClassify());

            //private BigDecimal budgetExeBt;
            List<RdmsBudgetResearchExe> btList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btList)){
                BigDecimal reduce = btList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExeZc;
            List<RdmsBudgetResearchExe> zcList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcList)){
                BigDecimal reduce = zcList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExe;
            budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExeBt().add(budgeStatisticsItemDto.getBudgetExeZc()));

            //private BigDecimal budgetExeConfirmBt;
            //private BigDecimal budgetExeConfirmZc;
            //private BigDecimal budgetExeConfirm;

            //private BigDecimal budgetOngoingBt;
            List<RdmsBudgetResearchExe> btGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btGoingList)){
                BigDecimal reduce = btGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoingZc;
            List<RdmsBudgetResearchExe> zcGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcGoingList)){
                BigDecimal reduce = zcGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoing;
            budgeStatisticsItemDto.setBudgetOngoing(budgeStatisticsItemDto.getBudgetOngoingBt().add(budgeStatisticsItemDto.getBudgetOngoingZc()));
            //预算总支出统计 包含应付未付的金额
            //budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExe().add(budgeStatisticsItemDto.getBudgetOngoing()));

            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));

        }else{
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }
        return budgeStatisticsItemDto;
    }

    public RdmsHmiBudgeStatisticsItemDto getCustomerBudgetStatisticsItem(String customerId, @NotNull BudgetClassifyStdEnum classify){
        RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
        RdmsBudgetResearchService.ClassifyBudget budgetItem = new RdmsBudgetResearchService.ClassifyBudget();
        switch (classify){
            case EQUIPMENT: {
                budgetItem = rdmsBudgetResearchService.getEquipmentBudgetByCustomer(customerId);
                break;
            }
            case MATERIAL: {
                budgetItem = rdmsBudgetResearchService.getMaterialBudgetByCustomer(customerId);
                break;
            }
            case TEST: {
                budgetItem = rdmsBudgetResearchService.getTestBudgetByCustomer(customerId);
                break;
            }
            case POWER: {
                budgetItem = rdmsBudgetResearchService.getPowerBudgetByCustomer(customerId);
                break;
            }
            case CONFERENCE: {
                budgetItem = rdmsBudgetResearchService.getConferenceBudgetByCustomer(customerId);
                break;
            }
            case BUSINESS: {
                budgetItem = rdmsBudgetResearchService.getBusinessBudgetByCustomer(customerId);
                break;
            }
            case COOPERATION: {
                budgetItem = rdmsBudgetResearchService.getCooperationBudgetByCustomer(customerId);
                break;
            }
            case PROPERTY: {
                budgetItem = rdmsBudgetResearchService.getPropertyBudgetByCustomer(customerId);
                break;
            }
            case CONSULTING: {
                budgetItem = rdmsBudgetResearchService.getConsultingBudgetByCustomer(customerId);
                break;
            }
            case OTHER: {
                budgetItem = rdmsBudgetResearchService.getOtherBudgetByCustomer(customerId);
                break;
            }
            case LABOR: {
                budgetItem = rdmsBudgetResearchService.getLaborBudgetByCustomer(customerId);
                break;
            }
            case MANAGEMENT: {
                budgetItem = rdmsBudgetResearchService.getManagementBudgetByCustomer(customerId);
                break;
            }
            case PERFORMANCE: {
                budgetItem = rdmsBudgetResearchService.getPerformanceBudgetByCustomer(customerId);
                break;
            }
            case STAFF: {
                budgetItem = rdmsBudgetResearchService.getStaffBudgetByCustomer(customerId);
                break;
            }
            case INFRASTRUCTURE: {
                budgetItem = rdmsBudgetResearchService.getInfrastructureBudgetByCustomer(customerId);
                break;
            }

            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if(!ObjectUtils.isEmpty(budgetItem.getBudget())){
            budgeStatisticsItemDto.setApprovedBudgetBt(budgetItem.getApprovedBudgetBt());
            budgeStatisticsItemDto.setApprovedBudgetZc(budgetItem.getApprovedBudgetZc());
            budgeStatisticsItemDto.setApprovedBudget(budgetItem.getApprovedBudget());

            budgeStatisticsItemDto.setBudgetBt(budgetItem.getBudgetBt());
            budgeStatisticsItemDto.setBudgetZc(budgetItem.getBudgetZc());
            budgeStatisticsItemDto.setBudget(budgetItem.getBudget());

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }else{
            budgeStatisticsItemDto.setApprovedBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }

        //汇总数据
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andClassifyEqualTo(classify.getClassify())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            //private String customerId;
            budgeStatisticsItemDto.setCustomerId(rdmsBudgetResearchExes.get(0).getCustomerId());
            //private String projectId;
            budgeStatisticsItemDto.setProjectId(null);
            //private String subprojectId;
            budgeStatisticsItemDto.setSubprojectId(null);

            budgeStatisticsItemDto.setPreprojectId(null);
            //private String classify;
            budgeStatisticsItemDto.setClassify(classify.getClassify());

            //private BigDecimal budgetExeBt;
            List<RdmsBudgetResearchExe> btList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btList)){
                BigDecimal reduce = btList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExeZc;
            List<RdmsBudgetResearchExe> zcList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcList)){
                BigDecimal reduce = zcList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExe;
            budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExeBt().add(budgeStatisticsItemDto.getBudgetExeZc()));

            //private BigDecimal budgetExeConfirmBt;
            //private BigDecimal budgetExeConfirmZc;
            //private BigDecimal budgetExeConfirm;

            //private BigDecimal budgetOngoingBt;
            List<RdmsBudgetResearchExe> btGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btGoingList)){
                BigDecimal reduce = btGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoingZc;
            List<RdmsBudgetResearchExe> zcGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcGoingList)){
                BigDecimal reduce = zcGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoing;
            budgeStatisticsItemDto.setBudgetOngoing(budgeStatisticsItemDto.getBudgetOngoingBt().add(budgeStatisticsItemDto.getBudgetOngoingZc()));
            //预算总支出统计 包含应付未付的金额
            //budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExe().add(budgeStatisticsItemDto.getBudgetOngoing()));

            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));

        }else{
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }
        return budgeStatisticsItemDto;
    }

    /**
     * 获取 主 项目指定费用类别的统计结果
     * @param projectId
     * @param classify
     * @return
     */
    public RdmsHmiBudgeStatisticsItemDto getBudgetStatisticsItem_mainProject(String projectId, @NotNull BudgetClassifyStdEnum classify){
        RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
        RdmsBudgetResearchService.ClassifyBudget budgetItem = new RdmsBudgetResearchService.ClassifyBudget();
        switch (classify){
            case EQUIPMENT: {
                budgetItem = rdmsBudgetResearchService.getEquipmentBudgetByProjectId(projectId);
                break;
            }
            case MATERIAL: {
                budgetItem = rdmsBudgetResearchService.getMaterialBudgetByProjectId(projectId);
                break;
            }
            case TEST: {
                budgetItem = rdmsBudgetResearchService.getTestBudgetByProjectId(projectId);
                break;
            }

            case POWER: {
                budgetItem = rdmsBudgetResearchService.getPowerBudgetByProjectId(projectId);
                break;
            }
            case CONFERENCE: {
                budgetItem = rdmsBudgetResearchService.getConferenceBudgetByProjectId(projectId);
                break;
            }
            case BUSINESS: {
                budgetItem = rdmsBudgetResearchService.getBusinessBudgetByProjectId(projectId);
                break;
            }
            case COOPERATION: {
                budgetItem = rdmsBudgetResearchService.getCooperationBudgetByProjectId(projectId);
                break;
            }
            case PROPERTY: {
                budgetItem = rdmsBudgetResearchService.getPropertyBudgetByProjectId(projectId);
                break;
            }
            case CONSULTING: {
                budgetItem = rdmsBudgetResearchService.getConsultingBudgetByProjectId(projectId);
                break;
            }
            case OTHER: {
                budgetItem = rdmsBudgetResearchService.getOtherBudgetByProjectId(projectId);
                break;
            }
            case LABOR: {
                budgetItem = rdmsBudgetResearchService.getLaborBudgetByProjectId(projectId);
                break;
            }
            case MANAGEMENT: {
                budgetItem = rdmsBudgetResearchService.getManagementBudgetByProjectId(projectId);
                break;
            }
            case PERFORMANCE: {
                budgetItem = rdmsBudgetResearchService.getPerformanceBudgetByProjectId(projectId);
                break;
            }
            case STAFF: {
                budgetItem = rdmsBudgetResearchService.getStaffBudgetByProjectId(projectId);
                break;
            }
            case INFRASTRUCTURE: {
                budgetItem = rdmsBudgetResearchService.getInfrastructureBudgetByProjectId(projectId);
                break;
            }

            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if(!ObjectUtils.isEmpty(budgetItem.getBudget())){
            budgeStatisticsItemDto.setApprovedBudgetBt(budgetItem.getApprovedBudgetBt());
            budgeStatisticsItemDto.setApprovedBudgetZc(budgetItem.getApprovedBudgetZc());
            budgeStatisticsItemDto.setApprovedBudget(budgetItem.getApprovedBudget());

            budgeStatisticsItemDto.setBudgetBt(budgetItem.getBudgetBt());
            budgeStatisticsItemDto.setBudgetZc(budgetItem.getBudgetZc());
            budgeStatisticsItemDto.setBudget(budgetItem.getBudget());

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }else{
            budgeStatisticsItemDto.setApprovedBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }

        //汇总数据
        List<String> payClassifyList = new ArrayList<>();
        payClassifyList.add(PayClassifyEnum.CHARACTER.getClassify());
        payClassifyList.add(PayClassifyEnum.TASK_SUBP.getClassify());
        payClassifyList.add(PayClassifyEnum.TASK_TEST.getClassify());
        payClassifyList.add(PayClassifyEnum.SUBP_MATERIAL.getClassify());
        payClassifyList.add(PayClassifyEnum.SUBP_FEE.getClassify());
        payClassifyList.add(PayClassifyEnum.SUB_PROJECT.getClassify());
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andClassifyEqualTo(classify.getClassify())
                .andPayClassifyIn(payClassifyList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            //private String customerId;
            budgeStatisticsItemDto.setCustomerId(rdmsBudgetResearchExes.get(0).getCustomerId());
            //private String projectId;
            budgeStatisticsItemDto.setProjectId(projectId);
            //private String subprojectId;
            budgeStatisticsItemDto.setSubprojectId(projectId);

            //private String classify;
            budgeStatisticsItemDto.setClassify(classify.getClassify());

            //private BigDecimal budgetExeBt;
            List<RdmsBudgetResearchExe> btList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btList)){
                BigDecimal reduce = btList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExeZc;
            List<RdmsBudgetResearchExe> zcList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcList)){
                BigDecimal reduce = zcList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExe;
            budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExeBt().add(budgeStatisticsItemDto.getBudgetExeZc()));

            //private BigDecimal budgetExeConfirmBt;
            //private BigDecimal budgetExeConfirmZc;
            //private BigDecimal budgetExeConfirm;

            //private BigDecimal budgetOngoingBt;
            List<RdmsBudgetResearchExe> btGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btGoingList)){
                BigDecimal reduce = btGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoingZc;
            List<RdmsBudgetResearchExe> zcGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcGoingList)){
                BigDecimal reduce = zcGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoing;
            budgeStatisticsItemDto.setBudgetOngoing(budgeStatisticsItemDto.getBudgetOngoingBt().add(budgeStatisticsItemDto.getBudgetOngoingZc()));
            //预算总支出统计 包含应付未付的金额
            //budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExe().add(budgeStatisticsItemDto.getBudgetOngoing()));
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }else{
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }
        return budgeStatisticsItemDto;
    }

    public RdmsHmiBudgeStatisticsItemDto getBudgetStatisticsItem_product(String projectId, @NotNull BudgetClassifyStdEnum classify){
        RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
        RdmsBudgetResearchService.ClassifyBudget budgetItem = new RdmsBudgetResearchService.ClassifyBudget();
        switch (classify){
            case EQUIPMENT: {
                budgetItem = rdmsBudgetResearchService.getEquipmentBudgetByProductId(projectId);
                break;
            }
            case MATERIAL: {
                budgetItem = rdmsBudgetResearchService.getMaterialBudgetByProductId(projectId);
                break;
            }
            case TEST: {
                budgetItem = rdmsBudgetResearchService.getTestBudgetByProductId(projectId);
                break;
            }

            case POWER: {
                budgetItem = rdmsBudgetResearchService.getPowerBudgetByProductId(projectId);
                break;
            }
            case CONFERENCE: {
                budgetItem = rdmsBudgetResearchService.getConferenceBudgetByProductId(projectId);
                break;
            }
            case BUSINESS: {
                budgetItem = rdmsBudgetResearchService.getBusinessBudgetByProductId(projectId);
                break;
            }
            case COOPERATION: {
                budgetItem = rdmsBudgetResearchService.getCooperationBudgetByProductId(projectId);
                break;
            }
            case PROPERTY: {
                budgetItem = rdmsBudgetResearchService.getPropertyBudgetByProductId(projectId);
                break;
            }
            case CONSULTING: {
                budgetItem = rdmsBudgetResearchService.getConsultingBudgetByProductId(projectId);
                break;
            }
            case OTHER: {
                budgetItem = rdmsBudgetResearchService.getOtherBudgetByProductId(projectId);
                break;
            }
            case LABOR: {
                budgetItem = rdmsBudgetResearchService.getLaborBudgetByProductId(projectId);
                break;
            }
            case MANAGEMENT: {
                budgetItem = rdmsBudgetResearchService.getManagementBudgetByProductId(projectId);
                break;
            }
            case PERFORMANCE: {
                budgetItem = rdmsBudgetResearchService.getPerformanceBudgetByProductId(projectId);
                break;
            }
            case STAFF: {
                budgetItem = rdmsBudgetResearchService.getStaffBudgetByProductId(projectId);
                break;
            }
            case INFRASTRUCTURE: {
                budgetItem = rdmsBudgetResearchService.getInfrastructureBudgetByProductId(projectId);
                break;
            }

            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if(!ObjectUtils.isEmpty(budgetItem.getBudget())){
            budgeStatisticsItemDto.setApprovedBudgetBt(budgetItem.getApprovedBudgetBt());
            budgeStatisticsItemDto.setApprovedBudgetZc(budgetItem.getApprovedBudgetZc());
            budgeStatisticsItemDto.setApprovedBudget(budgetItem.getApprovedBudget());

            budgeStatisticsItemDto.setBudgetBt(budgetItem.getBudgetBt());
            budgeStatisticsItemDto.setBudgetZc(budgetItem.getBudgetZc());
            budgeStatisticsItemDto.setBudget(budgetItem.getBudget());

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }else{
            budgeStatisticsItemDto.setApprovedBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setApprovedBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setBudgetBt(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudgetZc(BigDecimal.ZERO);
            budgeStatisticsItemDto.setBudget(BigDecimal.ZERO);

            budgeStatisticsItemDto.setClassifyName(budgetItem.getClassifyName());
            budgeStatisticsItemDto.setClassify(budgetItem.getClassify());
        }

        //汇总数据
        List<String> payClassifyList = new ArrayList<>();
        payClassifyList.add(PayClassifyEnum.PROJECT.getClassify());
        payClassifyList.add(PayClassifyEnum.PROJ_MATERIAL.getClassify());
        payClassifyList.add(PayClassifyEnum.PROJ_FEE.getClassify());
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andClassifyEqualTo(classify.getClassify())
                .andPayClassifyIn(payClassifyList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            //private String customerId;
            budgeStatisticsItemDto.setCustomerId(rdmsBudgetResearchExes.get(0).getCustomerId());
            //private String projectId;
            budgeStatisticsItemDto.setProjectId(projectId);
            //private String subprojectId;
            budgeStatisticsItemDto.setSubprojectId(projectId);

            //private String classify;
            budgeStatisticsItemDto.setClassify(classify.getClassify());

            //private BigDecimal budgetExeBt;
            List<RdmsBudgetResearchExe> btList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btList)){
                BigDecimal reduce = btList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExeZc;
            List<RdmsBudgetResearchExe> zcList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.PAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcList)){
                BigDecimal reduce = zcList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetExeZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetExeZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetExe;
            budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExeBt().add(budgeStatisticsItemDto.getBudgetExeZc()));

            //private BigDecimal budgetExeConfirmBt;
            //private BigDecimal budgetExeConfirmZc;
            //private BigDecimal budgetExeConfirm;

            //private BigDecimal budgetOngoingBt;
            List<RdmsBudgetResearchExe> btGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SUBSIDY.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(btGoingList)){
                BigDecimal reduce = btGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingBt(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingBt(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoingZc;
            List<RdmsBudgetResearchExe> zcGoingList = rdmsBudgetResearchExes.stream().filter(item -> item.getCapitalSource().equals(CapitalSourceEnum.SELF.getSource()) && item.getPaymentStatue().equals(PaymentStatusEnum.UNPAID.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(zcGoingList)){
                BigDecimal reduce = zcGoingList.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgeStatisticsItemDto.setBudgetOngoingZc(reduce);
            }else {
                budgeStatisticsItemDto.setBudgetOngoingZc(BigDecimal.ZERO);
            }
            //private BigDecimal budgetOngoing;
            budgeStatisticsItemDto.setBudgetOngoing(budgeStatisticsItemDto.getBudgetOngoingBt().add(budgeStatisticsItemDto.getBudgetOngoingZc()));
            //预算总支出统计 包含应付未付的金额
            //budgeStatisticsItemDto.setBudgetExe(budgeStatisticsItemDto.getBudgetExe().add(budgeStatisticsItemDto.getBudgetOngoing()));
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }else{
            //private BigDecimal budgetResidueBt;
            BigDecimal budgetResidueBt = budgeStatisticsItemDto.getBudgetBt().subtract(budgeStatisticsItemDto.getBudgetExeBt()).subtract(budgeStatisticsItemDto.getBudgetOngoingBt());
            budgeStatisticsItemDto.setBudgetResidueBt(budgetResidueBt);
            //private BigDecimal budgetResidueZc;
            BigDecimal budgetResidueZc = budgeStatisticsItemDto.getBudgetZc().subtract(budgeStatisticsItemDto.getBudgetExeZc()).subtract(budgeStatisticsItemDto.getBudgetOngoingZc());
            budgeStatisticsItemDto.setBudgetResidueZc(budgetResidueZc);
            //private BigDecimal budgetResidue;
            budgeStatisticsItemDto.setBudgetResidue(budgeStatisticsItemDto.getBudgetResidueBt().add(budgeStatisticsItemDto.getBudgetResidueZc()));
        }
        return budgeStatisticsItemDto;
    }

    /**
     * 获取一个子项目的预算支出统计页面
     * @param subprojectId
     * @return
     */
    public RdmsHmiBudgeStatisticsDto getBudgetExecutionSummary(String subprojectId) {
        RdmsHmiBudgeStatisticsDto budgeStatisticsDto = new RdmsHmiBudgeStatisticsDto();
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        //private String customerId;
        budgeStatisticsDto.setCustomerId(subproject.getCustomerId());
        //private String projectId;
        budgeStatisticsDto.setProjectId(subproject.getProjectId());
        //private String parentId;
        budgeStatisticsDto.setParentId(subproject.getParent());
        //private String subprojectId;
        budgeStatisticsDto.setSubprojectId(subprojectId);
        budgeStatisticsDto.setProjectCode(subproject.getProjectCode());

        //private String customerName;
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(subproject.getCustomerId());
        budgeStatisticsDto.setCustomerName(rdmsCustomer.getCustomerName());
        //private String projectName;
        if(subproject.getId().equals(subproject.getProjectId())){
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
            budgeStatisticsDto.setProjectName(rdmsProject.getProjectName());
        }else{
            budgeStatisticsDto.setProjectName(subproject.getLabel());
        }
        //private String parentName;
        RdmsProjectSubproject subproject1 = rdmsSubprojectService.selectByPrimaryKey(subproject.getParent());
        budgeStatisticsDto.setParentName(subproject1.getLabel());
        //private String subprojectName;
        budgeStatisticsDto.setSubprojectName(subproject.getLabel());

        //private String projectManagerId;
        budgeStatisticsDto.setProjectManagerId(subproject.getProjectManagerId());
        //private String projectManagerName;
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
        budgeStatisticsDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

        //List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList;
        List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList = new ArrayList<>();

        RdmsHmiBudgeStatisticsItemDto equipmentItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.EQUIPMENT);
        if(!ObjectUtils.isEmpty(equipmentItem)){
            statisticsItemDtoList.add(equipmentItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());  //0
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto materialItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.MATERIAL);
        if(!ObjectUtils.isEmpty(materialItem)){
            statisticsItemDtoList.add(materialItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());  //1
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto testItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.TEST);
        if(!ObjectUtils.isEmpty(testItem)){
            statisticsItemDtoList.add(testItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.TEST.getClassify());  //2
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto powerItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.POWER);
        if(!ObjectUtils.isEmpty(powerItem)){
            statisticsItemDtoList.add(powerItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.POWER.getClassify());  //3
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto conferenceItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.CONFERENCE);
        if(!ObjectUtils.isEmpty(conferenceItem)){
            statisticsItemDtoList.add(conferenceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify()); //4
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto businessItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.BUSINESS);
        if(!ObjectUtils.isEmpty(businessItem)){
            statisticsItemDtoList.add(businessItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify()); //5
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto cooperationItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.COOPERATION);
        if(!ObjectUtils.isEmpty(cooperationItem)){
            statisticsItemDtoList.add(cooperationItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify()); //6
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto propertyItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.PROPERTY);
        if(!ObjectUtils.isEmpty(propertyItem)){
            statisticsItemDtoList.add(propertyItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify()); //7
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto consultingItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.CONSULTING);
        if(!ObjectUtils.isEmpty(consultingItem)){
            statisticsItemDtoList.add(consultingItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify()); //8
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto otherItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.OTHER);
        if(!ObjectUtils.isEmpty(otherItem)){
            statisticsItemDtoList.add(otherItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.OTHER.getClassify()); //9
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto laborItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.LABOR);
        if(!ObjectUtils.isEmpty(laborItem)){
            statisticsItemDtoList.add(laborItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.LABOR.getClassify()); //10
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto managementItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.MANAGEMENT);
        if(!ObjectUtils.isEmpty(managementItem)){
            statisticsItemDtoList.add(managementItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify()); //11
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto performanceItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.PERFORMANCE);
        if(!ObjectUtils.isEmpty(performanceItem)){
            statisticsItemDtoList.add(performanceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify()); //12
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto staffItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.STAFF);
        if(!ObjectUtils.isEmpty(staffItem)){
            statisticsItemDtoList.add(staffItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.STAFF.getClassify()); //13
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto infrastructureItem = this.getBudgetStatisticsItem(subprojectId, BudgetClassifyStdEnum.INFRASTRUCTURE);
        if(!ObjectUtils.isEmpty(infrastructureItem)){
            statisticsItemDtoList.add(infrastructureItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(subproject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(subproject.getProjectId());
            budgeStatisticsItemDto.setSubprojectId(subproject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify()); //14
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }


        budgeStatisticsDto.setStatisticsItemDtoList(statisticsItemDtoList);

        budgeStatisticsDto.setSumEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudget());
        budgeStatisticsDto.setSumActiveEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetOngoing()));

        budgeStatisticsDto.setSumManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudget());
        budgeStatisticsDto.setSumActiveManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetOngoing()));

        budgeStatisticsDto.setSumMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudget());
        budgeStatisticsDto.setSumActiveMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetOngoing()));

        BigDecimal otherBudget = budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudget() //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudget())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudget())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudget())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudget())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudget())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudget())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudget())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudget())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudget())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudget())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudget())  //infrastructure
                ;
        budgeStatisticsDto.setSumOtherBudget(otherBudget);

        BigDecimal otherActiveBudget =
                     budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetExe() //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetOngoing())  //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetExe())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetOngoing())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetExe())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetOngoing())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetExe())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetOngoing())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetExe())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetOngoing())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetExe())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetOngoing())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetExe())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetOngoing())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetExe())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetOngoing())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetExe())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetOngoing())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetExe())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetOngoing())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetExe())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetOngoing())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetExe())  //infrastructure
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetOngoing())  //infrastructure
                ;
        budgeStatisticsDto.setSumActiveOtherBudget(otherActiveBudget);

        budgeStatisticsDto.setSumBudget(
                budgeStatisticsDto
                        .getSumEquipmentBudget()
                        .add(budgeStatisticsDto.getSumManhourBudget())
                        .add(budgeStatisticsDto.getSumMaterialBudget())
                        .add(budgeStatisticsDto.getSumOtherBudget()));
        budgeStatisticsDto.setSumActiveBudget(
                budgeStatisticsDto
                        .getSumActiveEquipmentBudget()
                        .add(budgeStatisticsDto.getSumActiveManhourBudget())
                        .add(budgeStatisticsDto.getSumActiveMaterialBudget())
                        .add(budgeStatisticsDto.getSumActiveOtherBudget()));

        if(budgeStatisticsDto.getSumBudget().doubleValue() != 0.00){
            budgeStatisticsDto.setBudgetExeRate(budgeStatisticsDto.getSumActiveBudget().multiply(BigDecimal.valueOf(100)).divide(budgeStatisticsDto.getSumBudget(), 2, RoundingMode.HALF_UP));
        }else{
            budgeStatisticsDto.setBudgetExeRate(BigDecimal.ZERO);
        }

        return budgeStatisticsDto;
    }

    /**
     * 获取一个预立项项目的预算支出统计页面
     * @param preprojectId
     * @return
     */
    public RdmsHmiBudgeStatisticsDto getBudgetExecutionSummary_preproject(String preprojectId) {
        //看有没有budget_research记录, 没有的话, 创建一个空记录
        RdmsBudgetResearchDto recordPreProjectIdAndStage = rdmsBudgetResearchService.getRecordByPreProjectIdAndStage(preprojectId);
        if(!recordPreProjectIdAndStage.getHasRecord()){
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(preprojectId);
            rdmsBudgetResearchService.initBudgetResearchItem(rdmsPreProject.getCustomerId(), null, null, null, rdmsPreProject.getId(), BudgetTypeEnum.PRE_PROJECT, 1);
        }

        RdmsHmiBudgeStatisticsDto budgeStatisticsDto = new RdmsHmiBudgeStatisticsDto();
        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(preprojectId);
        //private String customerId;
        budgeStatisticsDto.setCustomerId(rdmsPreProject.getCustomerId());
        //private String projectId;
        budgeStatisticsDto.setProjectId(null);
        //private String parentId;
        budgeStatisticsDto.setParentId(null);
        //private String subprojectId;
        budgeStatisticsDto.setSubprojectId(null);
        budgeStatisticsDto.setPreprojectId(preprojectId);
        budgeStatisticsDto.setProjectCode(rdmsPreProject.getProjectCode());

        //private String customerName;
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(rdmsPreProject.getCustomerId());
        budgeStatisticsDto.setCustomerName(rdmsCustomer.getCustomerName());
        //private String projectName;
        budgeStatisticsDto.setProjectName(rdmsPreProject.getPreProjectName());
        //private String parentName;
        budgeStatisticsDto.setParentName(null);
        //private String subprojectName;
        budgeStatisticsDto.setSubprojectName(null);

        //private String projectManagerId;
        budgeStatisticsDto.setProjectManagerId(null);
        //private String projectManagerName;
        budgeStatisticsDto.setProjectManagerName(null);

        RdmsSysgm rdmsSysgm = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
        budgeStatisticsDto.setSysgmId(rdmsSysgm.getSysgmId());
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsSysgm.getSysgmId());
        budgeStatisticsDto.setSysgmName(rdmsCustomerUser.getTrueName());

        //List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList;
        List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList = new ArrayList<>();

        RdmsHmiBudgeStatisticsItemDto equipmentItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.EQUIPMENT);
        if(!ObjectUtils.isEmpty(equipmentItem)){
            statisticsItemDtoList.add(equipmentItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());  //0
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto materialItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.MATERIAL);
        if(!ObjectUtils.isEmpty(materialItem)){
            statisticsItemDtoList.add(materialItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());  //1
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto testItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.TEST);
        if(!ObjectUtils.isEmpty(testItem)){
            statisticsItemDtoList.add(testItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.TEST.getClassify());  //2
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto powerItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.POWER);
        if(!ObjectUtils.isEmpty(powerItem)){
            statisticsItemDtoList.add(powerItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.POWER.getClassify());  //3
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto conferenceItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.CONFERENCE);
        if(!ObjectUtils.isEmpty(conferenceItem)){
            statisticsItemDtoList.add(conferenceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify()); //4
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto businessItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.BUSINESS);
        if(!ObjectUtils.isEmpty(businessItem)){
            statisticsItemDtoList.add(businessItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify()); //5
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto cooperationItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.COOPERATION);
        if(!ObjectUtils.isEmpty(cooperationItem)){
            statisticsItemDtoList.add(cooperationItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify()); //6
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto propertyItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.PROPERTY);
        if(!ObjectUtils.isEmpty(propertyItem)){
            statisticsItemDtoList.add(propertyItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify()); //7
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto consultingItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.CONSULTING);
        if(!ObjectUtils.isEmpty(consultingItem)){
            statisticsItemDtoList.add(consultingItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify()); //8
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto otherItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.OTHER);
        if(!ObjectUtils.isEmpty(otherItem)){
            statisticsItemDtoList.add(otherItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.OTHER.getClassify()); //9
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto laborItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.LABOR);
        if(!ObjectUtils.isEmpty(laborItem)){
            statisticsItemDtoList.add(laborItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.LABOR.getClassify()); //10
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto managementItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.MANAGEMENT);
        if(!ObjectUtils.isEmpty(managementItem)){
            statisticsItemDtoList.add(managementItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify()); //11
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto performanceItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.PERFORMANCE);
        if(!ObjectUtils.isEmpty(performanceItem)){
            statisticsItemDtoList.add(performanceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify()); //12
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto staffItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.STAFF);
        if(!ObjectUtils.isEmpty(staffItem)){
            statisticsItemDtoList.add(staffItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.STAFF.getClassify()); //13
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto infrastructureItem = this.getBudgetStatisticsItem_preProject(preprojectId, BudgetClassifyStdEnum.INFRASTRUCTURE);
        if(!ObjectUtils.isEmpty(infrastructureItem)){
            statisticsItemDtoList.add(infrastructureItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(rdmsPreProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(rdmsPreProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify()); //14
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }


        budgeStatisticsDto.setStatisticsItemDtoList(statisticsItemDtoList);

        budgeStatisticsDto.setSumEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudget());
        budgeStatisticsDto.setSumActiveEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetOngoing()));

        budgeStatisticsDto.setSumManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudget());
        budgeStatisticsDto.setSumActiveManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetOngoing()));

        budgeStatisticsDto.setSumMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudget());
        budgeStatisticsDto.setSumActiveMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetOngoing()));

        BigDecimal otherBudget = budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudget() //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudget())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudget())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudget())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudget())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudget())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudget())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudget())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudget())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudget())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudget())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudget())  //infrastructure
                ;
        budgeStatisticsDto.setSumOtherBudget(otherBudget);

        BigDecimal otherActiveBudget =
                     budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetExe() //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetOngoing())  //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetExe())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetOngoing())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetExe())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetOngoing())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetExe())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetOngoing())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetExe())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetOngoing())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetExe())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetOngoing())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetExe())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetOngoing())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetExe())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetOngoing())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetExe())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetOngoing())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetExe())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetOngoing())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetExe())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetOngoing())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetExe())  //infrastructure
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetOngoing())  //infrastructure
                ;
        budgeStatisticsDto.setSumActiveOtherBudget(otherActiveBudget);

        budgeStatisticsDto.setSumBudget(
                budgeStatisticsDto
                        .getSumEquipmentBudget()
                        .add(budgeStatisticsDto.getSumManhourBudget())
                        .add(budgeStatisticsDto.getSumMaterialBudget())
                        .add(budgeStatisticsDto.getSumOtherBudget()));
        budgeStatisticsDto.setSumActiveBudget(
                budgeStatisticsDto
                        .getSumActiveEquipmentBudget()
                        .add(budgeStatisticsDto.getSumActiveManhourBudget())
                        .add(budgeStatisticsDto.getSumActiveMaterialBudget())
                        .add(budgeStatisticsDto.getSumActiveOtherBudget()));

        if(budgeStatisticsDto.getSumBudget().doubleValue() != 0.00){
            budgeStatisticsDto.setBudgetExeRate(budgeStatisticsDto.getSumActiveBudget().multiply(BigDecimal.valueOf(100)).divide(budgeStatisticsDto.getSumBudget(), 2, RoundingMode.HALF_UP));
        }else{
            budgeStatisticsDto.setBudgetExeRate(BigDecimal.ZERO);
        }

        return budgeStatisticsDto;
    }

    /**
     * 根据预算阶段 对预算进行汇总
     * @param stage  参数可以是  Pre_project project product
     * @return
     */
    public RdmsHmiBudgeStatisticsDto getBudgetStageExecutionSummary(String customerId, @NotNull BudgetTypeEnum stage) {
        List<String> payClassifyList = new ArrayList<>();
        switch (stage){
            case PRE_PROJECT: {
                payClassifyList.add(PayClassifyEnum.TASK_FUNCTION.getClassify());
                payClassifyList.add(PayClassifyEnum.FUNCTION_FEE.getClassify());
                break;
            }
            case PROJECT:{
                payClassifyList.add(PayClassifyEnum.CHARACTER.getClassify());
                payClassifyList.add(PayClassifyEnum.TASK_SUBP.getClassify());
                payClassifyList.add(PayClassifyEnum.TASK_TEST.getClassify());
                payClassifyList.add(PayClassifyEnum.SUBP_MATERIAL.getClassify());
                payClassifyList.add(PayClassifyEnum.SUBP_FEE.getClassify());
                payClassifyList.add(PayClassifyEnum.SUB_PROJECT.getClassify());  //这里是否需要
                break;
            }
            case PRODUCT:{
                payClassifyList.add(PayClassifyEnum.PROJECT.getClassify());
                payClassifyList.add(PayClassifyEnum.PROJ_MATERIAL.getClassify());
                payClassifyList.add(PayClassifyEnum.PROJ_FEE.getClassify());
                break;
            }
            default:{
                return null;
            }
        }

        //构建返回数据
        RdmsHmiBudgeStatisticsDto budgeStatisticsDto = new RdmsHmiBudgeStatisticsDto();
        //private String customerId;
        budgeStatisticsDto.setCustomerId(customerId);
        //private String projectId;
        budgeStatisticsDto.setProjectId(null);
        //private String parentId;
        budgeStatisticsDto.setParentId(null);
        //private String subprojectId;
        budgeStatisticsDto.setSubprojectId(null);
        budgeStatisticsDto.setPreprojectId(null);
        budgeStatisticsDto.setProjectCode(null);

        //private String customerName;
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        budgeStatisticsDto.setCustomerName(rdmsCustomer.getCustomerName());
        //private String projectName;
        budgeStatisticsDto.setProjectName(null);
        //private String parentName;
        budgeStatisticsDto.setParentName(null);
        //private String subprojectName;
        budgeStatisticsDto.setSubprojectName(null);

        //private String projectManagerId;
        budgeStatisticsDto.setProjectManagerId(null);
        //private String projectManagerName;
        budgeStatisticsDto.setProjectManagerName(null);

        RdmsSysgm rdmsSysgm = rdmsSysgmService.getSysgmByCustomerId(customerId);
        budgeStatisticsDto.setSysgmId(rdmsSysgm.getSysgmId());
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsSysgm.getSysgmId());
        budgeStatisticsDto.setSysgmName(rdmsCustomerUser.getTrueName());

        //List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList;
        List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList = new ArrayList<>();

        //获得各种预算分类下, 已经发生支出的数据
        RdmsHmiBudgeStatisticsItemDto equipmentItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.EQUIPMENT, payClassifyList);
        if(!ObjectUtils.isEmpty(equipmentItem)){
            statisticsItemDtoList.add(equipmentItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());  //0
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto materialItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.MATERIAL, payClassifyList);
        if(!ObjectUtils.isEmpty(materialItem)){
            statisticsItemDtoList.add(materialItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());  //1
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto testItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.TEST, payClassifyList);
        if(!ObjectUtils.isEmpty(testItem)){
            statisticsItemDtoList.add(testItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.TEST.getClassify());  //2
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto powerItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.POWER, payClassifyList);
        if(!ObjectUtils.isEmpty(powerItem)){
            statisticsItemDtoList.add(powerItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.POWER.getClassify());  //3
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto conferenceItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.CONFERENCE, payClassifyList);
        if(!ObjectUtils.isEmpty(conferenceItem)){
            statisticsItemDtoList.add(conferenceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify()); //4
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto businessItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.BUSINESS, payClassifyList);
        if(!ObjectUtils.isEmpty(businessItem)){
            statisticsItemDtoList.add(businessItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify()); //5
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto cooperationItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.COOPERATION, payClassifyList);
        if(!ObjectUtils.isEmpty(cooperationItem)){
            statisticsItemDtoList.add(cooperationItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify()); //6
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto propertyItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.PROPERTY, payClassifyList);
        if(!ObjectUtils.isEmpty(propertyItem)){
            statisticsItemDtoList.add(propertyItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify()); //7
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto consultingItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.CONSULTING, payClassifyList);
        if(!ObjectUtils.isEmpty(consultingItem)){
            statisticsItemDtoList.add(consultingItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify()); //8
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto otherItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.OTHER, payClassifyList);
        if(!ObjectUtils.isEmpty(otherItem)){
            statisticsItemDtoList.add(otherItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.OTHER.getClassify()); //9
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto laborItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.LABOR, payClassifyList);
        if(!ObjectUtils.isEmpty(laborItem)){
            statisticsItemDtoList.add(laborItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.LABOR.getClassify()); //10
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto managementItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.MANAGEMENT, payClassifyList);
        if(!ObjectUtils.isEmpty(managementItem)){
            statisticsItemDtoList.add(managementItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify()); //11
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto performanceItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.PERFORMANCE, payClassifyList);
        if(!ObjectUtils.isEmpty(performanceItem)){
            statisticsItemDtoList.add(performanceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify()); //12
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto staffItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.STAFF, payClassifyList);
        if(!ObjectUtils.isEmpty(staffItem)){
            statisticsItemDtoList.add(staffItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.STAFF.getClassify()); //13
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto infrastructureItem = this.getBudgetStatisticsItemByStage(customerId, stage, BudgetClassifyStdEnum.INFRASTRUCTURE, payClassifyList);
        if(!ObjectUtils.isEmpty(infrastructureItem)){
            statisticsItemDtoList.add(infrastructureItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify()); //14
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }


        budgeStatisticsDto.setStatisticsItemDtoList(statisticsItemDtoList);

        budgeStatisticsDto.setSumEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudget());
        budgeStatisticsDto.setSumActiveEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetOngoing()));

        budgeStatisticsDto.setSumManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudget());
        budgeStatisticsDto.setSumActiveManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetOngoing()));

        budgeStatisticsDto.setSumMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudget());
        budgeStatisticsDto.setSumActiveMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetOngoing()));

        BigDecimal otherBudget = budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudget() //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudget())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudget())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudget())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudget())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudget())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudget())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudget())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudget())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudget())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudget())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudget())  //infrastructure
                ;
        budgeStatisticsDto.setSumOtherBudget(otherBudget);

        BigDecimal otherActiveBudget =
                     budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetExe() //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetOngoing())  //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetExe())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetOngoing())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetExe())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetOngoing())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetExe())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetOngoing())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetExe())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetOngoing())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetExe())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetOngoing())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetExe())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetOngoing())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetExe())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetOngoing())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetExe())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetOngoing())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetExe())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetOngoing())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetExe())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetOngoing())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetExe())  //infrastructure
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetOngoing())  //infrastructure
                ;
        budgeStatisticsDto.setSumActiveOtherBudget(otherActiveBudget);

        budgeStatisticsDto.setSumBudget(
                budgeStatisticsDto
                        .getSumEquipmentBudget()
                        .add(budgeStatisticsDto.getSumManhourBudget())
                        .add(budgeStatisticsDto.getSumMaterialBudget())
                        .add(budgeStatisticsDto.getSumOtherBudget()));
        budgeStatisticsDto.setSumActiveBudget(
                budgeStatisticsDto
                        .getSumActiveEquipmentBudget()
                        .add(budgeStatisticsDto.getSumActiveManhourBudget())
                        .add(budgeStatisticsDto.getSumActiveMaterialBudget())
                        .add(budgeStatisticsDto.getSumActiveOtherBudget()));

        if(budgeStatisticsDto.getSumBudget().doubleValue() != 0.00){
            budgeStatisticsDto.setBudgetExeRate(budgeStatisticsDto.getSumActiveBudget().multiply(BigDecimal.valueOf(100)).divide(budgeStatisticsDto.getSumBudget(), 2, RoundingMode.HALF_UP));
        }else{
            budgeStatisticsDto.setBudgetExeRate(BigDecimal.ZERO);
        }

        return budgeStatisticsDto;
    }

    public RdmsHmiBudgeStatisticsDto getCustomerBudgetExecutionSummary(String customerId) {
        List<String> payClassifyList = new ArrayList<>();

        //构建返回数据
        RdmsHmiBudgeStatisticsDto budgeStatisticsDto = new RdmsHmiBudgeStatisticsDto();
        //private String customerId;
        budgeStatisticsDto.setCustomerId(customerId);
        //private String projectId;
        budgeStatisticsDto.setProjectId(null);
        //private String parentId;
        budgeStatisticsDto.setParentId(null);
        //private String subprojectId;
        budgeStatisticsDto.setSubprojectId(null);
        budgeStatisticsDto.setPreprojectId(null);
        budgeStatisticsDto.setProjectCode(null);

        //private String customerName;
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        budgeStatisticsDto.setCustomerName(rdmsCustomer.getCustomerName());
        //private String projectName;
        budgeStatisticsDto.setProjectName(null);
        //private String parentName;
        budgeStatisticsDto.setParentName(null);
        //private String subprojectName;
        budgeStatisticsDto.setSubprojectName(null);

        //private String projectManagerId;
        budgeStatisticsDto.setProjectManagerId(null);
        //private String projectManagerName;
        budgeStatisticsDto.setProjectManagerName(null);

        RdmsSysgm rdmsSysgm = rdmsSysgmService.getSysgmByCustomerId(customerId);
        budgeStatisticsDto.setSysgmId(rdmsSysgm.getSysgmId());
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsSysgm.getSysgmId());
        budgeStatisticsDto.setSysgmName(rdmsCustomerUser.getTrueName());

        //List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList;
        List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList = new ArrayList<>();

        //获得各种预算分类下, 已经发生支出的数据
        RdmsHmiBudgeStatisticsItemDto equipmentItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.EQUIPMENT);
        if(!ObjectUtils.isEmpty(equipmentItem)){
            statisticsItemDtoList.add(equipmentItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());  //0
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto materialItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.MATERIAL);
        if(!ObjectUtils.isEmpty(materialItem)){
            statisticsItemDtoList.add(materialItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());  //1
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto testItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.TEST);
        if(!ObjectUtils.isEmpty(testItem)){
            statisticsItemDtoList.add(testItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.TEST.getClassify());  //2
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto powerItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.POWER);
        if(!ObjectUtils.isEmpty(powerItem)){
            statisticsItemDtoList.add(powerItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.POWER.getClassify());  //3
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto conferenceItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.CONFERENCE);
        if(!ObjectUtils.isEmpty(conferenceItem)){
            statisticsItemDtoList.add(conferenceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify()); //4
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto businessItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.BUSINESS);
        if(!ObjectUtils.isEmpty(businessItem)){
            statisticsItemDtoList.add(businessItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify()); //5
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto cooperationItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.COOPERATION);
        if(!ObjectUtils.isEmpty(cooperationItem)){
            statisticsItemDtoList.add(cooperationItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify()); //6
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto propertyItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.PROPERTY);
        if(!ObjectUtils.isEmpty(propertyItem)){
            statisticsItemDtoList.add(propertyItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify()); //7
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto consultingItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.CONSULTING);
        if(!ObjectUtils.isEmpty(consultingItem)){
            statisticsItemDtoList.add(consultingItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify()); //8
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto otherItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.OTHER);
        if(!ObjectUtils.isEmpty(otherItem)){
            statisticsItemDtoList.add(otherItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.OTHER.getClassify()); //9
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto laborItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.LABOR);
        if(!ObjectUtils.isEmpty(laborItem)){
            statisticsItemDtoList.add(laborItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.LABOR.getClassify()); //10
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto managementItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.MANAGEMENT);
        if(!ObjectUtils.isEmpty(managementItem)){
            statisticsItemDtoList.add(managementItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify()); //11
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto performanceItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.PERFORMANCE);
        if(!ObjectUtils.isEmpty(performanceItem)){
            statisticsItemDtoList.add(performanceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify()); //12
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto staffItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.STAFF);
        if(!ObjectUtils.isEmpty(staffItem)){
            statisticsItemDtoList.add(staffItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.STAFF.getClassify()); //13
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto infrastructureItem = this.getCustomerBudgetStatisticsItem(customerId, BudgetClassifyStdEnum.INFRASTRUCTURE);
        if(!ObjectUtils.isEmpty(infrastructureItem)){
            statisticsItemDtoList.add(infrastructureItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(customerId);
            budgeStatisticsItemDto.setProjectId(null);
            budgeStatisticsItemDto.setSubprojectId(null);
            budgeStatisticsItemDto.setPreprojectId(null);
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify()); //14
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }


        budgeStatisticsDto.setStatisticsItemDtoList(statisticsItemDtoList);

        budgeStatisticsDto.setSumEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudget());
        budgeStatisticsDto.setSumActiveEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetOngoing()));

        budgeStatisticsDto.setSumManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudget());
        budgeStatisticsDto.setSumActiveManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetOngoing()));

        budgeStatisticsDto.setSumMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudget());
        budgeStatisticsDto.setSumActiveMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetOngoing()));

        BigDecimal otherBudget = budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudget() //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudget())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudget())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudget())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudget())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudget())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudget())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudget())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudget())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudget())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudget())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudget())  //infrastructure
                ;
        budgeStatisticsDto.setSumOtherBudget(otherBudget);

        BigDecimal otherActiveBudget =
                     budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetExe() //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetOngoing())  //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetExe())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetOngoing())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetExe())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetOngoing())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetExe())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetOngoing())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetExe())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetOngoing())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetExe())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetOngoing())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetExe())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetOngoing())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetExe())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetOngoing())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetExe())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetOngoing())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetExe())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetOngoing())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetExe())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetOngoing())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetExe())  //infrastructure
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetOngoing())  //infrastructure
                ;
        budgeStatisticsDto.setSumActiveOtherBudget(otherActiveBudget);

        budgeStatisticsDto.setSumBudget(
                budgeStatisticsDto
                        .getSumEquipmentBudget()
                        .add(budgeStatisticsDto.getSumManhourBudget())
                        .add(budgeStatisticsDto.getSumMaterialBudget())
                        .add(budgeStatisticsDto.getSumOtherBudget()));
        budgeStatisticsDto.setSumActiveBudget(
                budgeStatisticsDto
                        .getSumActiveEquipmentBudget()
                        .add(budgeStatisticsDto.getSumActiveManhourBudget())
                        .add(budgeStatisticsDto.getSumActiveMaterialBudget())
                        .add(budgeStatisticsDto.getSumActiveOtherBudget()));

        if(budgeStatisticsDto.getSumBudget().doubleValue() != 0.00){
            budgeStatisticsDto.setBudgetExeRate(budgeStatisticsDto.getSumActiveBudget().multiply(BigDecimal.valueOf(100)).divide(budgeStatisticsDto.getSumBudget(), 2, RoundingMode.HALF_UP));
        }else{
            budgeStatisticsDto.setBudgetExeRate(BigDecimal.ZERO);
        }

        return budgeStatisticsDto;
    }

    /**
     * 根据不同的预算阶段, 多所有预算进行汇总
     */
    private @Nullable RdmsBudgetResearchDto getSumBudgetResearchByStage(String customerId, BudgetTypeEnum stage) {
        //根据预算阶段对所有预算进行汇总
        List<RdmsBudgetResearch> budgetResearchListByStage = rdmsBudgetResearchService.getListByStage(customerId, stage);
        if(!CollectionUtils.isEmpty(budgetResearchListByStage)){
            BigDecimal equipmentBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getEquipmentFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal equipmentApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getEquipmentFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal equipmentZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getEquipmentFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal equipmentApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getEquipmentFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal testBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getTestFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal testApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getTestFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal testZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getTestFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal testApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getTestFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal materialBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getMaterialFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal materialApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getMaterialFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal materialZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getMaterialFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal materialApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getMaterialFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal powerBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPowerFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal powerApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPowerFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal powerZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPowerFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal powerApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPowerFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal conferenceBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConferenceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal conferenceApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConferenceFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal conferenceZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConferenceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal conferenceApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConferenceFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal businessBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getBusinessFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal businessApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getBusinessFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal businessZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getBusinessFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal businessApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getBusinessFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal cooperationBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getCooperationFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal cooperationApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getCooperationFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal cooperationZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getCooperationFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal cooperationApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getCooperationFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal propertyBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPropertyFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal propertyApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPropertyFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal propertyZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPropertyFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal propertyApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPropertyFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal laborBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getLaborFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal laborApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getLaborFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal laborZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getLaborFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal laborApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getLaborFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal staffBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getStaffFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal staffApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getStaffFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal staffZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getStaffFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal staffApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getStaffFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal consultingBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConsultingFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal consultingApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConsultingFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal consultingZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConsultingFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal consultingApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getConsultingFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal managementBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getManagementFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal managementApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getManagementFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal managementZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getManagementFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal managementApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getManagementFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal otherBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getOtherFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal otherApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getOtherFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal otherZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getOtherFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal otherApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getOtherFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal performanceBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPerformanceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal performanceApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPerformanceFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal performanceZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPerformanceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal performanceApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getPerformanceFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal infrastructureBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getInfrastructureFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal infrastructureApprovedBt = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getInfrastructureFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal infrastructureZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getInfrastructureFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal infrastructureApprovedZc = budgetResearchListByStage.stream().map(RdmsBudgetResearch::getInfrastructureFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            RdmsBudgetResearchDto budgetResearchSumRecord = new RdmsBudgetResearchDto();
            budgetResearchSumRecord.setCustomerId(customerId);
            budgetResearchSumRecord.setProjectId(null);
            budgetResearchSumRecord.setParentId(null);
            budgetResearchSumRecord.setSubprojectId(null);
            budgetResearchSumRecord.setPreprojectId(null);
            budgetResearchSumRecord.setStage(stage.getType());
            budgetResearchSumRecord.setIsRoot(1);
            budgetResearchSumRecord.setEquipmentFeeApprovedBt(equipmentApprovedBt);
            budgetResearchSumRecord.setEquipmentFeeApprovedZc(equipmentApprovedZc);
            budgetResearchSumRecord.setEquipmentFeeBt(equipmentBt);
            budgetResearchSumRecord.setEquipmentFeeZc(equipmentZc);

            budgetResearchSumRecord.setTestFeeApprovedBt(testApprovedBt);
            budgetResearchSumRecord.setTestFeeApprovedZc(testApprovedZc);
            budgetResearchSumRecord.setTestFeeBt(testBt);
            budgetResearchSumRecord.setTestFeeZc(testZc);

            budgetResearchSumRecord.setMaterialFeeApprovedBt(materialApprovedBt);
            budgetResearchSumRecord.setMaterialFeeApprovedZc(materialApprovedZc);
            budgetResearchSumRecord.setMaterialFeeBt(materialBt);
            budgetResearchSumRecord.setMaterialFeeZc(materialZc);

            budgetResearchSumRecord.setPowerFeeApprovedBt(powerApprovedBt);
            budgetResearchSumRecord.setPowerFeeApprovedZc(powerApprovedZc);
            budgetResearchSumRecord.setPowerFeeBt(powerBt);
            budgetResearchSumRecord.setPowerFeeZc(powerZc);

            budgetResearchSumRecord.setConferenceFeeApprovedBt(conferenceApprovedBt);
            budgetResearchSumRecord.setConferenceFeeApprovedZc(conferenceApprovedZc);
            budgetResearchSumRecord.setConferenceFeeBt(conferenceBt);
            budgetResearchSumRecord.setConferenceFeeZc(conferenceZc);

            budgetResearchSumRecord.setBusinessFeeApprovedBt(businessApprovedBt);
            budgetResearchSumRecord.setBusinessFeeApprovedZc(businessApprovedZc);
            budgetResearchSumRecord.setBusinessFeeBt(businessBt);
            budgetResearchSumRecord.setBusinessFeeZc(businessZc);

            budgetResearchSumRecord.setCooperationFeeApprovedBt(cooperationApprovedBt);
            budgetResearchSumRecord.setCooperationFeeApprovedZc(cooperationApprovedZc);
            budgetResearchSumRecord.setCooperationFeeBt(cooperationBt);
            budgetResearchSumRecord.setCooperationFeeZc(cooperationZc);

            budgetResearchSumRecord.setPropertyFeeApprovedBt(propertyApprovedBt);
            budgetResearchSumRecord.setPropertyFeeApprovedZc(propertyApprovedZc);
            budgetResearchSumRecord.setPropertyFeeBt(propertyBt);
            budgetResearchSumRecord.setPropertyFeeZc(propertyZc);

            budgetResearchSumRecord.setLaborFeeApprovedBt(laborApprovedBt);
            budgetResearchSumRecord.setLaborFeeApprovedZc(laborApprovedZc);
            budgetResearchSumRecord.setLaborFeeBt(laborBt);
            budgetResearchSumRecord.setLaborFeeZc(laborZc);

            budgetResearchSumRecord.setStaffFeeApprovedBt(staffApprovedBt);
            budgetResearchSumRecord.setStaffFeeApprovedZc(staffApprovedZc);
            budgetResearchSumRecord.setStaffFeeBt(staffBt);
            budgetResearchSumRecord.setStaffFeeZc(staffZc);

            budgetResearchSumRecord.setConsultingFeeApprovedBt(consultingApprovedBt);
            budgetResearchSumRecord.setConsultingFeeApprovedZc(consultingApprovedZc);
            budgetResearchSumRecord.setConsultingFeeBt(consultingBt);
            budgetResearchSumRecord.setConsultingFeeZc(consultingZc);

            budgetResearchSumRecord.setManagementFeeApprovedBt(managementApprovedBt);
            budgetResearchSumRecord.setManagementFeeApprovedZc(managementApprovedZc);
            budgetResearchSumRecord.setManagementFeeBt(managementBt);
            budgetResearchSumRecord.setManagementFeeZc(managementZc);

            budgetResearchSumRecord.setOtherFeeApprovedBt(otherApprovedBt);
            budgetResearchSumRecord.setOtherFeeApprovedZc(otherApprovedZc);
            budgetResearchSumRecord.setOtherFeeBt(otherBt);
            budgetResearchSumRecord.setOtherFeeZc(otherZc);

            budgetResearchSumRecord.setPerformanceFeeApprovedBt(performanceApprovedBt);
            budgetResearchSumRecord.setPerformanceFeeApprovedZc(performanceApprovedZc);
            budgetResearchSumRecord.setPerformanceFeeBt(performanceBt);
            budgetResearchSumRecord.setPerformanceFeeZc(performanceZc);

            budgetResearchSumRecord.setInfrastructureFeeApprovedBt(infrastructureApprovedBt);
            budgetResearchSumRecord.setInfrastructureFeeApprovedZc(infrastructureApprovedZc);
            budgetResearchSumRecord.setInfrastructureFeeBt(infrastructureBt);
            budgetResearchSumRecord.setInfrastructureFeeZc(infrastructureZc);

            return budgetResearchSumRecord;
        }else{
            return null;
        }
    }

    public List<RdmsHmiBudgeStatisticsDto> getMainProjectBudgetExecutionSummaryList(String customerId){
        List<RdmsProjectDto> projectList = rdmsProjectService.getProjectListByCustomerId_setuped(customerId);
        List<RdmsHmiBudgeStatisticsDto> budgeStatisticsList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(projectList)){
            for(RdmsProjectDto projectDto: projectList){
                List<RdmsHmiBudgeStatisticsDto> subprojectStatisticsItemList = new ArrayList<>();
                RdmsHmiBudgeStatisticsDto budgetExecutionSummaryMainProject = this.getBudgetExecutionSummary_mainProject(projectDto.getId());
                List<RdmsProjectSubprojectDto> subprojectList = rdmsProjectService.getSubprojectListByProjectId(projectDto.getId());
                if(!CollectionUtils.isEmpty(subprojectList)){
                    for(RdmsProjectSubprojectDto subprojectDto : subprojectList){
                        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = this.getBudgetExecutionSummary(subprojectDto.getId());
                        subprojectStatisticsItemList.add(budgetExecutionSummary);
                    }
                }
                budgetExecutionSummaryMainProject.setSubprojectStatisticsItemList(subprojectStatisticsItemList);
                budgeStatisticsList.add(budgetExecutionSummaryMainProject);
            }
        }
        return budgeStatisticsList;
    }

    public RdmsHmiBudgeStatisticsDto getMainProjectBudgetExecutionSummary(String projectId){
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        RdmsHmiBudgeStatisticsDto budgetExecutionSummaryMainProject = new RdmsHmiBudgeStatisticsDto();
        if(!ObjectUtils.isEmpty(rdmsProject)){
            budgetExecutionSummaryMainProject = this.getBudgetExecutionSummary_mainProject(projectId);
            List<RdmsHmiBudgeStatisticsDto> subprojectStatisticsItemList = new ArrayList<>();
            List<RdmsProjectSubprojectDto> subprojectList = rdmsProjectService.getSubprojectListByProjectId(projectId);
            if(!CollectionUtils.isEmpty(subprojectList)){
                for(RdmsProjectSubprojectDto subprojectDto : subprojectList){
                    RdmsHmiBudgeStatisticsDto budgetExecutionSummary = this.getBudgetExecutionSummary(subprojectDto.getId());
                    subprojectStatisticsItemList.add(budgetExecutionSummary);
                }
            }
            budgetExecutionSummaryMainProject.setSubprojectStatisticsItemList(subprojectStatisticsItemList);

        }
        return budgetExecutionSummaryMainProject;
    }

    public List<RdmsHmiBudgeStatisticsDto> getMainProjectBudgetExecutionSummaryListByIPMT(String customerUserId){
        List<RdmsProjectDto> projectList = rdmsProjectService.getMainProjectBudgetExecutionSummaryListByIPMT(customerUserId);
        List<RdmsHmiBudgeStatisticsDto> budgeStatisticsList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(projectList)){
            for(RdmsProjectDto projectDto: projectList){
                List<RdmsHmiBudgeStatisticsDto> subprojectStatisticsItemList = new ArrayList<>();
                RdmsHmiBudgeStatisticsDto budgetExecutionSummaryMainProject = this.getBudgetExecutionSummary_mainProject(projectDto.getId());
                List<RdmsProjectSubprojectDto> subprojectList = rdmsProjectService.getSubprojectListByProjectId(projectDto.getId());
                if(!CollectionUtils.isEmpty(subprojectList)){
                    for(RdmsProjectSubprojectDto subprojectDto : subprojectList){
                        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = this.getBudgetExecutionSummary(subprojectDto.getId());
                        subprojectStatisticsItemList.add(budgetExecutionSummary);
                    }
                }
                budgetExecutionSummaryMainProject.setSubprojectStatisticsItemList(subprojectStatisticsItemList);
                budgeStatisticsList.add(budgetExecutionSummaryMainProject);
            }
        }
        return budgeStatisticsList;
    }

    public List<RdmsHmiBudgeStatisticsDto> getUnderlineProjectBudgetExecutionSummaryList(String customerUserId){
        List<RdmsProjectDto> projectList = rdmsProjectService.getUnderlineProjectList(customerUserId);
        List<RdmsHmiBudgeStatisticsDto> budgeStatisticsList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(projectList)){
            for(RdmsProjectDto projectDto: projectList){
                List<RdmsHmiBudgeStatisticsDto> subprojectStatisticsItemList = new ArrayList<>();
                RdmsHmiBudgeStatisticsDto budgetExecutionSummaryMainProject = this.getBudgetExecutionSummary_mainProject(projectDto.getId());
                List<RdmsProjectSubprojectDto> subprojectList = rdmsProjectService.getSubprojectListByProjectId(projectDto.getId());
                if(!CollectionUtils.isEmpty(subprojectList)){
                    for(RdmsProjectSubprojectDto subprojectDto : subprojectList){
                        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = this.getBudgetExecutionSummary(subprojectDto.getId());
                        subprojectStatisticsItemList.add(budgetExecutionSummary);
                    }
                }
                budgetExecutionSummaryMainProject.setSubprojectStatisticsItemList(subprojectStatisticsItemList);
                budgeStatisticsList.add(budgetExecutionSummaryMainProject);
            }
        }
        return budgeStatisticsList;
    }
    /**
     * 获取 主 项目的预算支出统计页面数据
     * @param projectId 主项目Id
     * @return 预算表的详细信息
     */
    public RdmsHmiBudgeStatisticsDto getBudgetExecutionSummary_mainProject(String projectId) {
        //看有没有budget_research记录, 没有的话, 创建一个空记录
        RdmsBudgetResearchDto recordProjectIdAndStage = rdmsBudgetResearchService.getRecordByProjectIdAndStage(projectId, BudgetTypeEnum.PROJECT);
        if(!recordProjectIdAndStage.getHasRecord()){
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
            rdmsBudgetResearchService.initBudgetResearchItem(rdmsProject.getCustomerId(), rdmsProject.getId(), rdmsProject.getId(), rdmsProject.getId(), null, BudgetTypeEnum.PROJECT, 1);
        }

        RdmsHmiBudgeStatisticsDto budgeStatisticsDto = new RdmsHmiBudgeStatisticsDto();
        RdmsProject mainProject = rdmsProjectService.selectByPrimaryKey(projectId);
        //private String customerId;
        budgeStatisticsDto.setCustomerId(mainProject.getCustomerId());
        //private String projectId;
        budgeStatisticsDto.setProjectId(mainProject.getId());
        //private String parentId;
        budgeStatisticsDto.setParentId(mainProject.getId());
        //private String subprojectId;
        budgeStatisticsDto.setSubprojectId(mainProject.getId());
        budgeStatisticsDto.setProjectCode(mainProject.getProjectCode());

        //private String customerName;
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(mainProject.getCustomerId());
        budgeStatisticsDto.setCustomerName(rdmsCustomer.getCustomerName());
        //private String projectName;
        budgeStatisticsDto.setProjectName(mainProject.getProjectName());
        //private String parentName;
        budgeStatisticsDto.setParentName(mainProject.getProjectName());
        //private String subprojectName;
        budgeStatisticsDto.setSubprojectName(mainProject.getProjectName());

        //private String projectManagerId;
        budgeStatisticsDto.setProjectManagerId(mainProject.getProjectManagerId());
        //private String projectManagerName;
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(mainProject.getProjectManagerId());
        if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
            budgeStatisticsDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
        }

        //List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList;
        List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList = new ArrayList<>();
        RdmsHmiBudgeStatisticsItemDto equipmentItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.EQUIPMENT);
        if(!ObjectUtils.isEmpty(equipmentItem)){
            statisticsItemDtoList.add(equipmentItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto materialItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.MATERIAL);
        if(!ObjectUtils.isEmpty(materialItem)){
            statisticsItemDtoList.add(materialItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto testItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.TEST);
        if(!ObjectUtils.isEmpty(testItem)){
            statisticsItemDtoList.add(testItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto powerItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.POWER);
        if(!ObjectUtils.isEmpty(powerItem)){
            statisticsItemDtoList.add(powerItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto conferenceItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.CONFERENCE);
        if(!ObjectUtils.isEmpty(conferenceItem)){
            statisticsItemDtoList.add(conferenceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto businessItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.BUSINESS);
        if(!ObjectUtils.isEmpty(businessItem)){
            statisticsItemDtoList.add(businessItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto cooperationItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.COOPERATION);
        if(!ObjectUtils.isEmpty(cooperationItem)){
            statisticsItemDtoList.add(cooperationItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto propertyItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.PROPERTY);
        if(!ObjectUtils.isEmpty(propertyItem)){
            statisticsItemDtoList.add(propertyItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto consultingItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.CONSULTING);
        if(!ObjectUtils.isEmpty(consultingItem)){
            statisticsItemDtoList.add(consultingItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto otherItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.OTHER);
        if(!ObjectUtils.isEmpty(otherItem)){
            statisticsItemDtoList.add(otherItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto laborItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.LABOR);
        if(!ObjectUtils.isEmpty(laborItem)){
            statisticsItemDtoList.add(laborItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto managementItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.MANAGEMENT);
        if(!ObjectUtils.isEmpty(managementItem)){
            statisticsItemDtoList.add(managementItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto performanceItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.PERFORMANCE);
        if(!ObjectUtils.isEmpty(performanceItem)){
            statisticsItemDtoList.add(performanceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto staffItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.STAFF);
        if(!ObjectUtils.isEmpty(staffItem)){
            statisticsItemDtoList.add(staffItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto infrastructureItem = this.getBudgetStatisticsItem_mainProject(projectId, BudgetClassifyStdEnum.INFRASTRUCTURE);
        if(!ObjectUtils.isEmpty(infrastructureItem)){
            statisticsItemDtoList.add(infrastructureItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(mainProject.getCustomerId());
            budgeStatisticsItemDto.setProjectId(mainProject.getId());
            budgeStatisticsItemDto.setSubprojectId(mainProject.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        budgeStatisticsDto.setStatisticsItemDtoList(statisticsItemDtoList);
        budgeStatisticsDto.setSumEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudget());
        budgeStatisticsDto.setSumActiveEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetOngoing()));

        budgeStatisticsDto.setSumManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudget());
        budgeStatisticsDto.setSumActiveManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetOngoing()));

        budgeStatisticsDto.setSumMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudget());
        budgeStatisticsDto.setSumActiveMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetOngoing()));

        BigDecimal otherBudget = budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudget() //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudget())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudget())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudget())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudget())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudget())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudget())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudget())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudget())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudget())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudget())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudget())  //infrastructure
                ;
        budgeStatisticsDto.setSumOtherBudget(otherBudget);
        BigDecimal otherActiveBudget =
                budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetExe() //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetOngoing())  //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetExe())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetOngoing())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetExe())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetOngoing())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetExe())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetOngoing())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetExe())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetOngoing())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetExe())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetOngoing())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetExe())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetOngoing())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetExe())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetOngoing())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetExe())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetOngoing())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetExe())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetOngoing())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetExe())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetOngoing())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetExe())  //infrastructure
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetOngoing())  //infrastructure
                ;
        budgeStatisticsDto.setSumActiveOtherBudget(otherActiveBudget);

        budgeStatisticsDto.setSumBudget(
                budgeStatisticsDto.getSumEquipmentBudget()
                        .add(budgeStatisticsDto.getSumManhourBudget())
                        .add(budgeStatisticsDto.getSumMaterialBudget())
                        .add(budgeStatisticsDto.getSumOtherBudget()));
        budgeStatisticsDto.setSumActiveBudget(
                budgeStatisticsDto.getSumActiveEquipmentBudget()
                        .add(budgeStatisticsDto.getSumActiveManhourBudget())
                        .add(budgeStatisticsDto.getSumActiveMaterialBudget())
                        .add(budgeStatisticsDto.getSumActiveOtherBudget()));

        if(budgeStatisticsDto.getSumBudget().doubleValue() != 0.00){
            budgeStatisticsDto.setBudgetExeRate(budgeStatisticsDto.getSumActiveBudget().multiply(BigDecimal.valueOf(100)).divide(budgeStatisticsDto.getSumBudget(), 2, RoundingMode.HALF_UP));
        }else{
            budgeStatisticsDto.setBudgetExeRate(BigDecimal.ZERO);
        }
        return budgeStatisticsDto;
    }

    public RdmsHmiBudgeStatisticsDto getBudgetExecutionSummary_product(String projectId) {
        //看有没有budget_research记录, 没有的话, 创建一个空记录
        RdmsBudgetResearchDto recordProjectIdAndStage = rdmsBudgetResearchService.getRecordByProjectIdAndStage(projectId, BudgetTypeEnum.PRODUCT);
        if(!recordProjectIdAndStage.getHasRecord()){
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
            rdmsBudgetResearchService.initBudgetResearchItem(rdmsProject.getCustomerId(), rdmsProject.getId(), null, null, null, BudgetTypeEnum.PRODUCT, 1);
        }

        RdmsHmiBudgeStatisticsDto budgeStatisticsDto = new RdmsHmiBudgeStatisticsDto();
        RdmsProject product = rdmsProjectService.selectByPrimaryKey(projectId);
        //private String customerId;
        budgeStatisticsDto.setCustomerId(product.getCustomerId());
        //private String projectId;
        budgeStatisticsDto.setProjectId(product.getId());
        //private String parentId;
        budgeStatisticsDto.setParentId(product.getId());
        //private String subprojectId;
        budgeStatisticsDto.setSubprojectId(product.getId());
        budgeStatisticsDto.setProjectCode(product.getProjectCode());

        //private String customerName;
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(product.getCustomerId());
        budgeStatisticsDto.setCustomerName(rdmsCustomer.getCustomerName());
        //private String projectName;
        budgeStatisticsDto.setProjectName(product.getProjectName());
        //private String parentName;
        budgeStatisticsDto.setParentName(product.getProjectName());
        //private String subprojectName;
        budgeStatisticsDto.setSubprojectName(product.getProjectName());

        //private String projectManagerId;
        budgeStatisticsDto.setProjectManagerId(product.getProjectManagerId());
        //private String projectManagerName;
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(product.getProjectManagerId());
        budgeStatisticsDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

        //List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList;
        List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList = new ArrayList<>();
        RdmsHmiBudgeStatisticsItemDto equipmentItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.EQUIPMENT);
        if(!ObjectUtils.isEmpty(equipmentItem)){
            statisticsItemDtoList.add(equipmentItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto materialItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.MATERIAL);
        if(!ObjectUtils.isEmpty(materialItem)){
            statisticsItemDtoList.add(materialItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto testItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.TEST);
        if(!ObjectUtils.isEmpty(testItem)){
            statisticsItemDtoList.add(testItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto powerItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.POWER);
        if(!ObjectUtils.isEmpty(powerItem)){
            statisticsItemDtoList.add(powerItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto conferenceItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.CONFERENCE);
        if(!ObjectUtils.isEmpty(conferenceItem)){
            statisticsItemDtoList.add(conferenceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto businessItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.BUSINESS);
        if(!ObjectUtils.isEmpty(businessItem)){
            statisticsItemDtoList.add(businessItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto cooperationItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.COOPERATION);
        if(!ObjectUtils.isEmpty(cooperationItem)){
            statisticsItemDtoList.add(cooperationItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }
        RdmsHmiBudgeStatisticsItemDto propertyItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.PROPERTY);
        if(!ObjectUtils.isEmpty(propertyItem)){
            statisticsItemDtoList.add(propertyItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto consultingItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.CONSULTING);
        if(!ObjectUtils.isEmpty(consultingItem)){
            statisticsItemDtoList.add(consultingItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto otherItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.OTHER);
        if(!ObjectUtils.isEmpty(otherItem)){
            statisticsItemDtoList.add(otherItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto laborItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.LABOR);
        if(!ObjectUtils.isEmpty(laborItem)){
            statisticsItemDtoList.add(laborItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto managementItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.MANAGEMENT);
        if(!ObjectUtils.isEmpty(managementItem)){
            statisticsItemDtoList.add(managementItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto performanceItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.PERFORMANCE);
        if(!ObjectUtils.isEmpty(performanceItem)){
            statisticsItemDtoList.add(performanceItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto staffItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.STAFF);
        if(!ObjectUtils.isEmpty(staffItem)){
            statisticsItemDtoList.add(staffItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        RdmsHmiBudgeStatisticsItemDto infrastructureItem = this.getBudgetStatisticsItem_product(projectId, BudgetClassifyStdEnum.INFRASTRUCTURE);
        if(!ObjectUtils.isEmpty(infrastructureItem)){
            statisticsItemDtoList.add(infrastructureItem);
        }else{
            RdmsHmiBudgeStatisticsItemDto budgeStatisticsItemDto = new RdmsHmiBudgeStatisticsItemDto();
            budgeStatisticsItemDto.setCustomerId(product.getCustomerId());
            budgeStatisticsItemDto.setProjectId(product.getId());
            budgeStatisticsItemDto.setSubprojectId(product.getId());
            budgeStatisticsItemDto.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            statisticsItemDtoList.add(budgeStatisticsItemDto);
        }

        budgeStatisticsDto.setStatisticsItemDtoList(statisticsItemDtoList);
        budgeStatisticsDto.setSumEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudget());
        budgeStatisticsDto.setSumActiveEquipmentBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(0).getBudgetOngoing()));

        budgeStatisticsDto.setSumManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudget());
        budgeStatisticsDto.setSumActiveManhourBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(13).getBudgetOngoing()));

        budgeStatisticsDto.setSumMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudget());
        budgeStatisticsDto.setSumActiveMaterialBudget(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetExe().add(budgeStatisticsDto.getStatisticsItemDtoList().get(1).getBudgetOngoing()));

        BigDecimal otherBudget = budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudget() //test
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudget())  //power
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudget())  //conference
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudget())  //business
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudget())  //cooperation
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudget())  //property
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudget())  //consulting
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudget())  //other
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudget())  //labor
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudget())  //management
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudget())  //performance
                .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudget())  //infrastructure
                ;
        budgeStatisticsDto.setSumOtherBudget(otherBudget);
        BigDecimal otherActiveBudget =
                budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetExe() //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(2).getBudgetOngoing())  //test
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetExe())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(3).getBudgetOngoing())  //power
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetExe())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(4).getBudgetOngoing())  //conference
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetExe())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(5).getBudgetOngoing())  //business
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetExe())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(6).getBudgetOngoing())  //cooperation
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetExe())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(7).getBudgetOngoing())  //property
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetExe())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(8).getBudgetOngoing())  //consulting
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetExe())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(9).getBudgetOngoing())  //other
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetExe())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(10).getBudgetOngoing())  //labor
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetExe())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(11).getBudgetOngoing())  //management
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetExe())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(12).getBudgetOngoing())  //performance
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetExe())  //infrastructure
                        .add(budgeStatisticsDto.getStatisticsItemDtoList().get(14).getBudgetOngoing())  //infrastructure
                ;
        budgeStatisticsDto.setSumActiveOtherBudget(otherActiveBudget);

        budgeStatisticsDto.setSumBudget(
                budgeStatisticsDto.getSumEquipmentBudget()
                        .add(budgeStatisticsDto.getSumManhourBudget())
                        .add(budgeStatisticsDto.getSumMaterialBudget())
                        .add(budgeStatisticsDto.getSumOtherBudget()));
        budgeStatisticsDto.setSumActiveBudget(
                budgeStatisticsDto.getSumActiveEquipmentBudget()
                        .add(budgeStatisticsDto.getSumActiveManhourBudget())
                        .add(budgeStatisticsDto.getSumActiveMaterialBudget())
                        .add(budgeStatisticsDto.getSumActiveOtherBudget()));

        if(budgeStatisticsDto.getSumBudget().doubleValue() != 0.00){
            budgeStatisticsDto.setBudgetExeRate(budgeStatisticsDto.getSumActiveBudget().multiply(BigDecimal.valueOf(100)).divide(budgeStatisticsDto.getSumBudget(), 2, RoundingMode.HALF_UP));
        }else{
            budgeStatisticsDto.setBudgetExeRate(BigDecimal.ZERO);
        }
        return budgeStatisticsDto;
    }

    public void listByKeywordIsSubprojectId(PageDto<RdmsBudgetResearchExeDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.setOrderByClause("create_time desc");
        budgetExeExample.createCriteria()
                .andSubprojectIdEqualTo(pageDto.getKeyWord())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);

        PageInfo<RdmsBudgetResearchExe> pageInfo = new PageInfo<>(rdmsBudgetResearchExes);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsBudgetResearchExeDto> rdmsBudgetResearchExeDtos = CopyUtil.copyList(rdmsBudgetResearchExes, RdmsBudgetResearchExeDto.class);

        pageDto.setList(rdmsBudgetResearchExeDtos);
    }

    public void listByKeywordIsProjectId(PageDto<RdmsBudgetResearchExeDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.setOrderByClause("create_time desc");
        budgetExeExample.createCriteria()
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);

        PageInfo<RdmsBudgetResearchExe> pageInfo = new PageInfo<>(rdmsBudgetResearchExes);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsBudgetResearchExeDto> rdmsBudgetResearchExeDtos = CopyUtil.copyList(rdmsBudgetResearchExes, RdmsBudgetResearchExeDto.class);

        pageDto.setList(rdmsBudgetResearchExeDtos);
    }

    public RdmsBudgetResearchExe selectByPrimaryKey(String id){
        return rdmsBudgetResearchExeMapper.selectByPrimaryKey(id);
    }

    public Long getCountBySerialNo(String serialNo){
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andSerialNoEqualTo(serialNo)
                .andDeletedEqualTo(0);
        long l = rdmsBudgetResearchExeMapper.countByExample(budgetExeExample);
        return l;
    }

    public RdmsBudgetResearchExe getRecordBySerialNo(String serialNo){
        RdmsBudgetResearchExeExample budgetExeExample = new RdmsBudgetResearchExeExample();
        budgetExeExample.createCriteria()
                .andSerialNoEqualTo(serialNo)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            return rdmsBudgetResearchExes.get(0);
        }else{
            return null;
        }
    }

    @Transactional
    public List<String> saveBudgetExeList(List<RdmsBudgetResearchExeDto> budgetExeList){
        List<String> exeIdList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(budgetExeList)){
            for(RdmsBudgetResearchExe budgetResearchExe: budgetExeList){
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(budgetResearchExe.getSubprojectId());
                budgetResearchExe.setParentId(subproject.getParent());
                budgetResearchExe.setPayClassify(PayClassifyEnum.SUB_PROJECT.getClassify());  //费用计入当前的子项目
                //比对序号是否存在, 序号存在的不保存
                Long countBySerialNo = this.getCountBySerialNo(budgetResearchExe.getSerialNo());
                if(countBySerialNo == 0){
                    String id = this.save(budgetResearchExe);
                    exeIdList.add(id);
                }
            }
        }
        return exeIdList;
    }

    @Transactional
    public BigDecimal getActiveBudgetBySubprojectId(String subprojectId){
        RdmsBudgetResearchExeExample budgetResearchExeExample = new RdmsBudgetResearchExeExample();
        budgetResearchExeExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearchExe> rdmsBudgetResearchExes = rdmsBudgetResearchExeMapper.selectByExample(budgetResearchExeExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetResearchExes)){
            BigDecimal sumBudgetExe = rdmsBudgetResearchExes.stream().map(RdmsBudgetResearchExe::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            return sumBudgetExe;
        }else{
            return BigDecimal.ZERO;
        }
    }

    /**
     * 保存
     */
    public String save(RdmsBudgetResearchExe budgetExe) {
        if(ObjectUtils.isEmpty(budgetExe.getId())){
            return this.insert(budgetExe);
        }else{
            RdmsBudgetResearchExe rdmsBudgetResearchExe = this.selectByPrimaryKey(budgetExe.getId());
            if(ObjectUtils.isEmpty(rdmsBudgetResearchExe)){
                return this.insert(budgetExe);
            }else{
                budgetExe.setCreateTime(rdmsBudgetResearchExe.getCreateTime());
                return this.update(budgetExe);
            }
        }
    }

    private String insert(RdmsBudgetResearchExe budgetExe) {
        if(ObjectUtils.isEmpty(budgetExe.getId())){  //当前端页面给出projectID时,将不为空
            budgetExe.setId(UuidUtil.getShortUuid());
        }
        RdmsBudgetResearchExe rdmsBudgetResearchExe = this.selectByPrimaryKey(budgetExe.getId());
        if(ObjectUtils.isEmpty(rdmsBudgetResearchExe)){
            budgetExe.setDeleted(0);
            budgetExe.setCreateTime(new Date());
            rdmsBudgetResearchExeMapper.insert(budgetExe);
            return budgetExe.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsBudgetResearchExe budgetExe) {
        if(ObjectUtils.isEmpty(budgetExe.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBudgetResearchExe rdmsBudgetResearchExe = this.selectByPrimaryKey(budgetExe.getId());
        if(ObjectUtils.isEmpty(rdmsBudgetResearchExe)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            budgetExe.setDeleted(0);
            budgetExe.setCreateTime(rdmsBudgetResearchExe.getCreateTime());
            rdmsBudgetResearchExeMapper.updateByPrimaryKey(budgetExe);
            return budgetExe.getId();
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBudgetResearchExe rdmsBudgetResearchExe = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsBudgetResearchExe)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsBudgetResearchExe.setDeleted(1);
            rdmsBudgetResearchExeMapper.updateByPrimaryKey(rdmsBudgetResearchExe);
        }
    }

}
