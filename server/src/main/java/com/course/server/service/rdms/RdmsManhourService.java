/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.domain.RdmsManhourStandard;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsManhourStandardMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RdmsManhourService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsManhourService.class);

    @Resource
    private RdmsManhourStandardMapper rdmsManhourStandardMapper;
    @Resource
    private RdmsCustomerUserJobLevelService rdmsCustomerUserJobLevelService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Resource
    private RdmsFeeManageService rdmsFeeManageService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsBudgetExeService rdmsBudgetExeService;
    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;

    public void list(PageDto<RdmsManhourStandardDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsManhourStandardExample manhourExample = new RdmsManhourStandardExample();
        List<RdmsManhourStandard> rdmsManhourStandards = rdmsManhourStandardMapper.selectByExample(manhourExample);

        PageInfo<RdmsManhourStandard> pageInfo = new PageInfo<>(rdmsManhourStandards);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsManhourStandardDto> rdmsManhourStandardDtos = CopyUtil.copyList(rdmsManhourStandards, RdmsManhourStandardDto.class);

        pageDto.setList(rdmsManhourStandardDtos);
    }

    public RdmsManhourStandard selectByPrimaryKey(String id){
        return rdmsManhourStandardMapper.selectByPrimaryKey(id);
    }

    public RdmsManhourStandard getStandardManhourByCustomerId(String customerId){
        RdmsManhourStandardExample manhourExample = new RdmsManhourStandardExample();
        manhourExample.createCriteria().andCustomerIdEqualTo(customerId);
        List<RdmsManhourStandard> rdmsManhourStandards = rdmsManhourStandardMapper.selectByExample(manhourExample);
        if(ObjectUtils.isEmpty(rdmsManhourStandards) || rdmsManhourStandards.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST_OR_ERROR);
        }else{
            return rdmsManhourStandards.get(0);
        }
    }

    /**
     * 获得工作人员的工时费率
     */
    @Transactional
    public BigDecimal getManhourFeeByWorkerId(String workerId){
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(workerId);
        if(!ObjectUtils.isEmpty(rdmsCustomerUser) && !ObjectUtils.isEmpty(rdmsCustomerUser.getJobLevel())){
            RdmsCustomerUserJobLevel jobLevelByLevelCode = rdmsCustomerUserJobLevelService.getJobLevelByLevelCode(rdmsCustomerUser.getJobLevel(), rdmsCustomerUser.getCustomerId());
            if(! ObjectUtils.isEmpty(jobLevelByLevelCode)){
                return jobLevelByLevelCode.getManHourFee();
            }else{
                return BigDecimal.valueOf(1);
            }
        }else{
            return BigDecimal.valueOf(1);
        }
    }

    /**
     * 将具体人员的工时转换为标准工时
     */
    public Double transformToStandManhour(Double workerManhour, String workerId, String customerId, OperateTypeEnum operateType){
        if(! ObjectUtils.isEmpty(workerManhour)){
            RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(customerId);
            BigDecimal workerManhourFee = this.getManhourFeeByWorkerId(workerId);
            BigDecimal manhour = new BigDecimal(workerManhour);
            BigDecimal manhourCost = manhour.multiply(workerManhourFee);

            double standManhour;
            if(!operateType.getType().equals(OperateTypeEnum.TEST.getType())){
                standManhour = (manhourCost.divide(standardManhour.getDevManhourFee(),6, RoundingMode.HALF_UP)).doubleValue();
            }else{
                standManhour = (manhourCost.divide(standardManhour.getTestManhourFee(), 6, RoundingMode.HALF_UP)).doubleValue();
            }
            return standManhour;
        }else{
            return 0.0;
        }
    }

    /**
     * 将标准工时转换为具体人员工时
     */
    public Double transformToWorkerManhour(Double standManhour, String workerId, String customerId, OperateTypeEnum operateType){
        if(! ObjectUtils.isEmpty(standManhour)){
            RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(customerId);
            BigDecimal workerManhourFee = this.getManhourFeeByWorkerId(workerId);
            BigDecimal stdManhour = new BigDecimal(standManhour);
            double workerManhour = 0.00;
            if(!operateType.getType().equals(OperateTypeEnum.TEST.getType())){
                //计算出工时成本 = 标准工时 * 标准工时成本
                BigDecimal hourCost = stdManhour.multiply(standardManhour.getDevManhourFee());
                workerManhour = hourCost.divide(workerManhourFee, 6, RoundingMode.HALF_UP).doubleValue();
            }else{
                BigDecimal hourCost = stdManhour.multiply(standardManhour.getTestManhourFee());
                workerManhour = hourCost.divide(workerManhourFee, 6, RoundingMode.HALF_UP).doubleValue();
            }
            return workerManhour;
        }else{
            return 0.0;
        }
    }

    @Transactional
    public List<String> getDeveloperIdListByCharacter(String characterId){
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
        jobitemTypeList.add(JobItemTypeEnum.ASSIST);
        jobitemTypeList.add(JobItemTypeEnum.TEST);
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        jobitemTypeList.add(JobItemTypeEnum.TASK);
        jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);
        List<RdmsJobItemDto> jobitemsByCharacterId = rdmsJobItemService.getJobitemListByCharacterIdAndStatusTypeList(characterId, null, jobitemTypeList);
        return jobitemsByCharacterId.stream().map(RdmsJobItemDto::getExecutorId).distinct().collect(Collectors.toList());
    }


    /**
     * 计算一个工单绩效工时
     * 一级工单: DEVELOP CHARACTER_INT TASK_SUBP TEST
     * 二级工单: ASSIST TEST
     * 三级工单: BUG TEST
     * 规则:
     *  1. 开发工单和功能集成工单, 是向项目经理交付的, 项目经理发的测试工单, 测试费用走测试预算;
     *      开发工单
     *          DEVELOP
     *          TEST
     *      集成工单
     *          CHARACTER_INT
     *              ASSIST
     *               TEST (协作工单的测试费用由集成工单支付)
     *          TEST
     *      任务工单
     *          TASK_SUBP
     *              ASSIST
     *               TEST (协作工单的测试费用由任务工单支付)
     *          TEST
     *  2. 集成工单的协作工单, 以及协作工单的测试工单, 是集成工单的执行人发的, 其费用由集成工单支付;
     *  3. 任务工单虽然是有项目经理发的, 但是, 任务工单理论上与功能开发无关, 因此, 任务工单的协作工单和测试工单, 以及协作工单的测试工单, 其费用都是由任务工单支付的
     *
     * @param jobitemId
     * @return
     */
    public double calJobItemPerformanceManhour(String jobitemId ) {
        //有效状态的工单类型
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
        testTypeEnumList.add(JobItemTypeEnum.TEST);

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(! ObjectUtils.isEmpty(rdmsJobItem)){
            //直接计算绩效工单 DEVELOP ASSIST TEST
            if(rdmsJobItem.getType().equals(JobItemTypeEnum.DEVELOP.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.DEMAND.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.DECOMPOSE.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.ITERATION.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.MERGE.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.JOIN_TO.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.CHA_RECHECK.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.ASSIST.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.BUG.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.TEST.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_TEST.getType())  //测试任务不能发协作任务,所以是直接计算绩效的
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.UPDATE.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.SUGGEST.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.SUGGEST_UPDATE.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.FUNCTION.getType())  //和Task_Function 是不同的
            ){
                return rdmsJobItem.getManhour();
            }

            //计算集成工单的绩效工时
            if(rdmsJobItem.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())){
                //获得所有协作工单
                List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
                typeEnumList.add(JobItemTypeEnum.ASSIST);
                List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, statusEnumList, typeEnumList);
                double assistAndTestJobSumManhour = 0.0;
                if(! CollectionUtils.isEmpty(assistJobitemList)){
                    for(RdmsJobItemDto assistJob: assistJobitemList){
                        //协作工单的测试工单总工时
                        List<RdmsJobItemDto> assistJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(assistJob.getId(), statusEnumList, testTypeEnumList);
                        Double testSumManhour = assistJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                        RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
                        //测试工单工时换算成开发工时
                        Double transTestJobSumHours = testSumManhour * standardManhourByCustomerId.getTestManhourFee().doubleValue() / standardManhourByCustomerId.getDevManhourFee().doubleValue();
                        assistAndTestJobSumManhour += assistJob.getManhour() + transTestJobSumHours;
                    }
                }
                return rdmsJobItem.getManhour() - assistAndTestJobSumManhour;
            }

            //计算任务工单的绩效工时
            if(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_MILESTONE.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_BUG.getType())
                    || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())
            ){
                //获得所有协作工单
                List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
                typeEnumList.add(JobItemTypeEnum.ASSIST);
                List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, statusEnumList, typeEnumList);
                double assistAndTestJobSumManhour = 0.0;
                if(! CollectionUtils.isEmpty(assistJobitemList)){
                    for(RdmsJobItemDto assistJob: assistJobitemList){
                        //协作工单的测试工单总工时
                        List<RdmsJobItemDto> assistJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(assistJob.getId(), statusEnumList, testTypeEnumList);
                        Double testSumManhour = assistJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                        RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
                        //测试工单工时换算成开发工时
                        Double transTestJobSumHours = testSumManhour * standardManhourByCustomerId.getTestManhourFee().doubleValue() / standardManhourByCustomerId.getDevManhourFee().doubleValue();
                        assistAndTestJobSumManhour += assistJob.getManhour() + transTestJobSumHours;
                    }
                }
                //任务工单本身的测试工单
                //注释说明: 任务工单本身的测试工单成本, 不应由任务工单成本支付, 任务工单执行人考虑不到后面还需要测试的情况
                /*List<RdmsJobItemDto> taskJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, statusEnumList, testTypeEnumList);
                if(! CollectionUtils.isEmpty(taskJobTestList)){
                    Double taskTestSumManhour = taskJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                    //将测试工时折算为开发工时
                    RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
                    BigDecimal calcDevManhour = BigDecimal.valueOf(taskTestSumManhour).multiply(standardManhour.getTestManhourFee()).divide(standardManhour.getDevManhourFee(), 6, RoundingMode.HALF_UP).setScale(6, RoundingMode.HALF_UP);
                    assistAndTestJobSumManhour += calcDevManhour.doubleValue();
                }*/
                return rdmsJobItem.getManhour() - assistAndTestJobSumManhour;
            }
        }
        return 0.0;
    }

    /**
     * 计算一级开发工单(DEVELOP CHARACTER_INT TASK_SUBP)的套装(包含自身支付工时的协作工单和测试工单)总工时
     * @param jobitemId
     * @return
     */
    public double calFirstLevelJobItemSuitDevManhour(String jobitemId) {
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.DEVELOP.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.DECOMPOSE.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.ITERATION.getType())
        ){
            return rdmsJobItem.getManhour();
        }

        if(rdmsJobItem.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())){
            List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
            testTypeEnumList.add(JobItemTypeEnum.TEST);

            Double sumManhour = 0.0;
            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.ASSIST);
            List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, statusEnumList, typeEnumList);
            double assistAndTestJobSumManhour = 0.0;
            if(! CollectionUtils.isEmpty(assistJobitemList)){
                for(RdmsJobItemDto assistJob: assistJobitemList){
                    //协作工单的测试工单总工时
                    List<RdmsJobItemDto> assistJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(assistJob.getId(), statusEnumList, testTypeEnumList);
                    Double testSumManhour = assistJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                    RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
                    //测试工单工时换算成开发工时
                    Double transTestJobSumHours = testSumManhour * standardManhourByCustomerId.getTestManhourFee().doubleValue() / standardManhourByCustomerId.getDevManhourFee().doubleValue();
                    assistAndTestJobSumManhour += assistJob.getManhour() + transTestJobSumHours;
                }
            }
            if(assistAndTestJobSumManhour > rdmsJobItem.getManhour()){
                sumManhour = assistAndTestJobSumManhour;
            }else {
                sumManhour = rdmsJobItem.getManhour();
            }
            return sumManhour;
        }

        if(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType()) || rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())){
            List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
            testTypeEnumList.add(JobItemTypeEnum.TEST);

            Double sumManhour = 0.0;
            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.ASSIST);
            List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, statusEnumList, typeEnumList);
            double assistAndTestJobSumManhour = 0.0;
            if(! CollectionUtils.isEmpty(assistJobitemList)){
                for(RdmsJobItemDto assistJob: assistJobitemList){
                    //协作工单的测试工单总工时
                    List<RdmsJobItemDto> assistJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(assistJob.getId(), statusEnumList, testTypeEnumList);
                    Double testSumManhour = assistJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                    RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
                    //测试工单工时换算成开发工时
                    Double transTestJobSumHours = testSumManhour * standardManhourByCustomerId.getTestManhourFee().doubleValue() / standardManhourByCustomerId.getDevManhourFee().doubleValue();
                    assistAndTestJobSumManhour += assistJob.getManhour() + transTestJobSumHours;
                }
            }

            //任务工单/质量工单 本身的测试工单
            List<RdmsJobItemDto> taskJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, statusEnumList, testTypeEnumList);
            if(! CollectionUtils.isEmpty(taskJobTestList)){
                Double taskTestSumManhour = taskJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                //将测试工时折算为开发工时
                RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
                BigDecimal calcDevManhour = BigDecimal.valueOf(taskTestSumManhour).multiply(standardManhour.getTestManhourFee()).divide(standardManhour.getDevManhourFee(), 6, RoundingMode.HALF_UP).setScale(6, RoundingMode.HALF_UP);
                assistAndTestJobSumManhour += calcDevManhour.doubleValue();
            }

            if(assistAndTestJobSumManhour > rdmsJobItem.getManhour()){
                sumManhour = assistAndTestJobSumManhour;
            }else {
                sumManhour = rdmsJobItem.getManhour();
            }
            return sumManhour;
        }
        return 0.0;
    }

    /**
     * 获取包括本工单ID在内的所有下级工单ID列表
     * @param jobitemId
     * @return
     */
    public List<String> getFirstLevelJobItemAndSubJobIdList(String jobitemId) {
        List<String> subJobIdList = new ArrayList<>();
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.DEVELOP.getType())){
            subJobIdList.add(jobitemId);
            return subJobIdList;
        }

        if(rdmsJobItem.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())){
            subJobIdList.add(jobitemId);

            List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
            testTypeEnumList.add(JobItemTypeEnum.TEST);

            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.ASSIST);

            List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, statusEnumList, typeEnumList);
            if(! CollectionUtils.isEmpty(assistJobitemList)){
                for(RdmsJobItemDto assistJob: assistJobitemList){
                    subJobIdList.add(assistJob.getId());
                    //协作工单的测试工单总工时
                    List<RdmsJobItemDto> assistJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(assistJob.getId(), statusEnumList, testTypeEnumList);
                    List<String> stringList = assistJobTestList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
                    subJobIdList.addAll(stringList);
                }
            }
            return subJobIdList;
        }

        if(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType()) || rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())){
            subJobIdList.add(jobitemId);

            List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
            testTypeEnumList.add(JobItemTypeEnum.TEST);

            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.ASSIST);

            List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, statusEnumList, typeEnumList);
            if(! CollectionUtils.isEmpty(assistJobitemList)){
                for(RdmsJobItemDto assistJob: assistJobitemList){
                    subJobIdList.add(assistJob.getId());
                    //协作工单的测试工单总工时
                    List<RdmsJobItemDto> assistJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(assistJob.getId(), statusEnumList, testTypeEnumList);
                    List<String> stringList = assistJobTestList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
                    subJobIdList.addAll(stringList);
                }
            }

            //任务工单/质量工单 本身的测试工单
            List<RdmsJobItemDto> taskJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, statusEnumList, testTypeEnumList);
            if(! CollectionUtils.isEmpty(taskJobTestList)){
                List<String> stringList = taskJobTestList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
                subJobIdList.addAll(stringList);
            }
            return subJobIdList;
        }
        return null;
    }

    /**
     * 测试费计算
     * 计算一级开发工单(DEVELOP CHARACTER_INT)由测试预算支付的测试工时 ; TASK_SUBP工单由于测试费包含在工单费用中, 无需调用这个函数重复计算, 除非需要单独计算任务工单的测试费用
     */
    public double calFirstLevelJobItemTestManhour(String jobitemId) {
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
        testTypeEnumList.add(JobItemTypeEnum.TEST);

        List<RdmsJobItemDto> tastJobList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, statusEnumList, testTypeEnumList);
        if(!CollectionUtils.isEmpty(tastJobList)){
            return tastJobList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
        }else{
            return 0.0;
        }
    }

    /**
     * 测试费计算
     * 计算一条功能由测试预算支付的测试费
     */
    public BigDecimal calCharacterAllTestManhourFee(String characterId) {
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(rdmsCharacter.getCustomerId());
        BigDecimal sumDevFee = standardManhour.getTestManhourFee().multiply(BigDecimal.valueOf(rdmsCharacter.getTestManhourApproved()));

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        //获取所有功能开发的工单
        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.DEVELOP);
        typeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, statusEnumList, typeList);
        BigDecimal haveUsed = new BigDecimal("0");
        if(!CollectionUtils.isEmpty(jobitemList)){
            for(RdmsJobItemDto jobItemDto: jobitemList){
                //任务工单的的测试费是包含在工单费用中的, 不重复计算
                if(! jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())){
                    double testManhour = this.calFirstLevelJobItemTestManhour(jobItemDto.getId());
                    BigDecimal fee = jobItemDto.getStandManhourFee().multiply(BigDecimal.valueOf(testManhour));
                    haveUsed = haveUsed.add(fee);
                }
            }
        }
        return haveUsed;


    }

    /**
     * 获取一个一级工单实际用于测试的费用
     * @param jobitemId
     * @return
     */
    public double calJobItemActiveManhourByIdAndStatus_testManhour(String jobitemId, List<JobItemStatusEnum> statusEnumList) {
        List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
        testTypeEnumList.add(JobItemTypeEnum.TEST);

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.ASSIST);
        List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, statusEnumList, typeEnumList);
        double testJobSumManhour = 0.0;
        if(! CollectionUtils.isEmpty(assistJobitemList)){
            for(RdmsJobItemDto assistJob: assistJobitemList){
                //协作工单的测试工单总工时
                List<RdmsJobItemDto> assistJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(assistJob.getId(), statusEnumList, testTypeEnumList);
                Double testSumManhour = assistJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                testJobSumManhour += testSumManhour;
            }
        }

        //任务工单本身的测试工单
        List<RdmsJobItemDto> taskJobTestList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, statusEnumList, testTypeEnumList);
        if(! CollectionUtils.isEmpty(taskJobTestList)){
            Double taskTestSumManhour = taskJobTestList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
            testJobSumManhour += taskTestSumManhour;
        }

        return testJobSumManhour;
    }

    /**
     * 计算一个工单的费用化物料费用
     * @param jobitemId
     * @return
     */
    public BigDecimal calMaterialFeeByJobitemId(String jobitemId){
        List<RdmsMaterialManageDto> listByJobitemIdAndUseMode = rdmsMaterialManageService.getListByJobitemIdAndUseMode(jobitemId, MaterialUsageModeEnum.EXPENDITURE);
        BigDecimal reduce = listByJobitemIdAndUseMode.stream().map(RdmsMaterialManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return reduce;
    }

    /**
     * 计算一个工单的其他费用
     * @param jobitemId
     * @return
     */
    public BigDecimal calOtherFeeByJobitemId(String jobitemId){
        List<RdmsFeeManageDto> listByJobitemId = rdmsFeeManageService.getListByJobitemId(jobitemId);
        BigDecimal reduce = listByJobitemId.stream().map(RdmsFeeManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return reduce;
    }

    /**
     * 计算一个工单的所有相关费用, 包括工时费用, 物料费用和其他费用
     * @param jobitemId
     * @return
     */
    @Transactional
    public BigDecimal calJobitemAllAccountCharge(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        double devManhour = this.calFirstLevelJobItemSuitDevManhour(jobitemId);  //测试工时转换为开发工时
        BigDecimal devManhourFee = standardManhourByCustomerId.getDevManhourFee().multiply(BigDecimal.valueOf(devManhour));
        BigDecimal testManhourFee = BigDecimal.ZERO;
        //任务工单和质量工单的的测试费是包含在工单费用中的, 不重复计算
        //开发工单的测试走测试预算; 功能集成工单测试走测试预算, 功能集成工单的下级工单测试, 走功能集成工单的费用; 任务工单和质量工单, 不属于功能开发, 没有独立的测试预算, 所有费用由工单自己承担;
        if(!(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType()) || rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType()))){
            double sumTestManhour = this.calFirstLevelJobItemTestManhour(jobitemId);
            testManhourFee = standardManhourByCustomerId.getTestManhourFee().multiply(BigDecimal.valueOf(sumTestManhour));
        }
        BigDecimal sumMaterialFee = BigDecimal.ZERO;
        BigDecimal sumOtherFee = BigDecimal.ZERO;
        List<String> jobIdList = this.getFirstLevelJobItemAndSubJobIdList(jobitemId);
        if(!CollectionUtils.isEmpty(jobIdList)){
            for(String jobId: jobIdList){
                BigDecimal materialFee = this.calMaterialFeeByJobitemId(jobId);
                sumMaterialFee = sumMaterialFee.add(materialFee);
                BigDecimal otherFee = this.calOtherFeeByJobitemId(jobId);
                sumOtherFee = sumOtherFee.add(otherFee);
            }
        }

        return devManhourFee.add(sumMaterialFee).add(sumOtherFee).add(testManhourFee);
    }

    /**
     * 计算一个工单的所有相关费用, 包括工时费用, 物料费用和其他费用
     * @param jobitemId
     * @return
     */
    @Transactional
    public RdmsHmiJobSuitChargeDto calJobitemSuitCharge(String jobitemId){
        RdmsHmiJobSuitChargeDto jobSuitChargeDto = new RdmsHmiJobSuitChargeDto();
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        double devManhour = this.calFirstLevelJobItemSuitDevManhour(jobitemId);  //测试工时转换为开发工时
        BigDecimal devManhourFee = standardManhourByCustomerId.getDevManhourFee().multiply(BigDecimal.valueOf(devManhour));

        BigDecimal testManhourFee = BigDecimal.ZERO;
        //任务工单和质量工单的的测试费是包含在工单费用中的, 不重复计算
        //开发工单的测试走测试预算; 功能集成工单测试走测试预算, 功能集成工单的下级工单测试, 走功能集成工单的费用; 任务工单和质量工单, 不属于功能开发, 没有独立的测试预算, 所有费用由工单自己承担;
        if(!(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType()) || rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType()))){
            double sumTestManhour = this.calFirstLevelJobItemTestManhour(jobitemId);
            testManhourFee = standardManhourByCustomerId.getTestManhourFee().multiply(BigDecimal.valueOf(sumTestManhour));
        }
        BigDecimal sumManhourFee = devManhourFee.add(testManhourFee);
        jobSuitChargeDto.setSumManhourCharge(sumManhourFee);

        BigDecimal sumMaterialFee = BigDecimal.ZERO;
        BigDecimal sumOtherFee = BigDecimal.ZERO;
        List<String> jobIdList = this.getFirstLevelJobItemAndSubJobIdList(jobitemId);
        if(!CollectionUtils.isEmpty(jobIdList)){
            for(String jobId: jobIdList){
                BigDecimal materialFee = this.calMaterialFeeByJobitemId(jobId);
                sumMaterialFee = sumMaterialFee.add(materialFee);
                BigDecimal otherFee = this.calOtherFeeByJobitemId(jobId);
                sumOtherFee = sumOtherFee.add(otherFee);
            }
        }
        jobSuitChargeDto.setSumMaterialCharge(sumMaterialFee);
        jobSuitChargeDto.setSumFeeCharge(sumOtherFee);
        jobSuitChargeDto.setSumCharge(devManhourFee.add(sumMaterialFee).add(sumOtherFee).add(testManhourFee));
        return jobSuitChargeDto;
    }

    /**
     * 计算一个子项目自己已经发的任务工单的总的记账工时
     * 记账工时: 当任务工单未超预算时, 以预算为准记账; 当工时超过给的预算工时, 则以实际工时记账
     * @param subprojectId
     * @return
     */
    @Transactional
    public BigDecimal getSubprojectTaskActiveAccountCharge(String subprojectId){
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);

        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, statusEnumList, jobitemTypeList);
        BigDecimal sumTaskManhour = BigDecimal.ZERO;
        if(!CollectionUtils.isEmpty(jobitemList)){
            for(RdmsJobItemDto jobItemDto: jobitemList){
                sumTaskManhour =sumTaskManhour.add(this.calJobitemAllAccountCharge(jobItemDto.getId()));
            }
        }
        return sumTaskManhour;
    }

    @Transactional
    public BigDecimal getCharacterActiveAccountCharge(String characterId){
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);

        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, statusEnumList, jobitemTypeList);
        BigDecimal sumActiveBudget = BigDecimal.ZERO;
        if(!CollectionUtils.isEmpty(jobitemList)){
            for(RdmsJobItemDto jobItemDto: jobitemList){
                sumActiveBudget =sumActiveBudget.add(this.calJobitemAllAccountCharge(jobItemDto.getId()));
            }
        }
        return sumActiveBudget;
    }

    /**
     * 计算子项目自己还剩下的预算金额
     * @param subprojectId
     * @return
     */
    @Transactional
    public BigDecimal getSubprojectSelfRemainBudget(String subprojectId) {
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        BigDecimal subprojectSelfActiveAccountCharge = this.getSubprojectTaskActiveAccountCharge(subprojectId);
        return subproject.getBudget().add(subproject.getAddCharge()).subtract(subprojectSelfActiveAccountCharge);
    }

    /**
     * 根据customerUserId 和 characterId 汇总他在某个功能开发过程中的工作量
     * @param customerUserId
     * @return
     */
    @Transactional
    public RdmsHmiWorkerManhourSummaryDto getManhourSummaryInfoByWorkerIdAndCharacterId(String customerUserId, String characterId){
        //Double 数据保留两位小数 format
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(6);
        nf.setRoundingMode(RoundingMode.HALF_UP);

        RdmsHmiWorkerManhourSummaryDto summaryDto = new RdmsHmiWorkerManhourSummaryDto();
        summaryDto.setId(customerUserId);  //开发者Id
        RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
        summaryDto.setWorkerName(rdmsCustomerUser1.getTrueName());
        {
            //获得所有任务工单
            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.COMPLETED);
            statusEnumList.add(JobItemStatusEnum.ARCHIVED);
            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.CHARACTER_INT);
            typeEnumList.add(JobItemTypeEnum.TASK_SUBP);
            List<RdmsJobItemDto> tasKJobitemListByExecutorId = rdmsJobItemService.getJobitemListByExecutorIdCharacterIdAndStatusListTypeList(customerUserId, characterId, statusEnumList, typeEnumList);
            if(! CollectionUtils.isEmpty(tasKJobitemListByExecutorId)){
                summaryDto.setTaskJobNum(tasKJobitemListByExecutorId.size());
                Double sumManhour = 0.0;
                for(RdmsJobItemDto jobItemDto: tasKJobitemListByExecutorId){
                    sumManhour += this.calJobItemPerformanceManhour(jobItemDto.getId());
                }
                summaryDto.setTaskJobSumManhour(Double.valueOf(nf.format(sumManhour)));
            }else{
                summaryDto.setTaskJobNum(0);
                summaryDto.setTaskJobSumManhour(0.0);
            }
        }

        {
            //获得所有开发工单
            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.COMPLETED);
            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.DEVELOP);
            typeEnumList.add(JobItemTypeEnum.ASSIST);
            List<RdmsJobItemDto> devJobitemListByExecutorId = rdmsJobItemService.getJobitemListByExecutorIdCharacterIdAndStatusListTypeList(customerUserId, characterId, statusEnumList, typeEnumList);
            if(! CollectionUtils.isEmpty(devJobitemListByExecutorId)){
                summaryDto.setDevJobNum(devJobitemListByExecutorId.size());
                Double sumDevManhour = devJobitemListByExecutorId.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                summaryDto.setDevJobSumManhour(Double.valueOf(nf.format(sumDevManhour)));
            }else{
                summaryDto.setDevJobNum(0);
                summaryDto.setDevJobSumManhour(0.0);
            }
        }

        {
            //获得所有测试工单
            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.COMPLETED);
            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.TEST);
            List<RdmsJobItemDto> testJobitemListByExecutorId = rdmsJobItemService.getJobitemListByExecutorIdCharacterIdAndStatusListTypeList(customerUserId, characterId, statusEnumList, typeEnumList);
            if(! CollectionUtils.isEmpty(testJobitemListByExecutorId)){
                summaryDto.setTestJobNum(testJobitemListByExecutorId.size());
                Double sumTestManhour = testJobitemListByExecutorId.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                summaryDto.setTestJobSumManhour(Double.valueOf(nf.format(sumTestManhour)));
            }else{
                summaryDto.setTestJobNum(0);
                summaryDto.setTestJobSumManhour(0.0);
            }
        }
        //获得相应工作人员的工时费
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
        RdmsCustomerUserJobLevel jobLevelByLevelCode = rdmsCustomerUserJobLevelService.getJobLevelByLevelCode(rdmsCustomerUser.getJobLevel(), rdmsCustomerUser.getCustomerId());
        summaryDto.setManhourFee(jobLevelByLevelCode.getManHourFee());

        summaryDto.setSumJobNum(summaryDto.getDevJobNum() + summaryDto.getTaskJobNum() + summaryDto.getTestJobNum());
        //在工时汇总时, 测试工时需要折算成开发工时
        RdmsManhourStandard rdmsManhourStandard = this.getStandardManhourByCustomerId(rdmsCustomerUser1.getCustomerId());
        double testJobSumHour = summaryDto.getTestJobSumManhour() * rdmsManhourStandard.getTestManhourFee().doubleValue() / rdmsManhourStandard.getDevManhourFee().doubleValue();
        double sumManhour = summaryDto.getDevJobSumManhour() + summaryDto.getTaskJobSumManhour() + testJobSumHour;
        summaryDto.setSumManhour(Double.valueOf(nf.format(sumManhour)));
        return summaryDto;
    }

    public BigDecimal getApprovedDevManhourFee(String characterId){
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsCharacter.getCustomerId());
        return standardManhourByCustomerId.getDevManhourFee().multiply(BigDecimal.valueOf(rdmsCharacter.getDevManhourApproved()));
    }

    @Transactional
    public BigDecimal getCharacterRemainDevManhourFee(String characterId){
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(rdmsCharacter.getCustomerId());
        BigDecimal sumDevFee = standardManhour.getDevManhourFee().multiply(BigDecimal.valueOf(rdmsCharacter.getDevManhourApproved()));

        BigDecimal characterAllDevManhourFee = this.getCharacterAllDevManhourFee(characterId);
        return sumDevFee.subtract(characterAllDevManhourFee);
    }

    @Transactional
    public BigDecimal getCharacterAllDevManhourFee(String characterId){
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(rdmsCharacter.getCustomerId());
        BigDecimal sumDevFee = standardManhour.getDevManhourFee().multiply(BigDecimal.valueOf(rdmsCharacter.getDevManhourApproved()));

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        //获取所有功能开发的工单
        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.DEVELOP);
        typeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, statusEnumList, typeList);
        BigDecimal haveUsed = new BigDecimal("0");
        if(!CollectionUtils.isEmpty(jobitemList)){
            for(RdmsJobItemDto jobItemDto: jobitemList){
                double devManhour = this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                BigDecimal fee = jobItemDto.getStandManhourFee().multiply(BigDecimal.valueOf(devManhour));
                haveUsed = haveUsed.add(fee);
            }
        }
        return haveUsed;
    }

    @Transactional
    public BigDecimal getApprovedTestManhourFee(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(! ObjectUtils.isEmpty(rdmsJobItem.getCharacterId())){
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(rdmsJobItem.getCharacterId());
            RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(rdmsCharacter.getCustomerId());
            return standardManhourByCustomerId.getTestManhourFee().multiply(BigDecimal.valueOf(rdmsCharacter.getTestManhourApproved()));
        }
        return BigDecimal.ZERO;
    }

    /**
     * 计算一个一级开发工单(DEVELOP CHARACTER_INT TASK_SUBP)的预算执行情况
     * @param jobitemId
     * @return
     */
    @Transactional
    public RdmsBudgetSummaryDto getFirstLevelJobitemBudgetExeInfo(String jobitemId){
        RdmsBudgetSummaryDto budgetDto = new RdmsBudgetSummaryDto();
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        RdmsManhourStandard standardManhourFee = this.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        budgetDto.setStandDevManhourFee(standardManhourFee.getDevManhourFee());
        budgetDto.setStandTestManhourFee(standardManhourFee.getTestManhourFee());
        BigDecimal sumFee = BigDecimal.ZERO;
        if(! ObjectUtils.isEmpty(rdmsJobItem.getStandManhourFee())){
            sumFee = rdmsJobItem.getStandManhourFee().multiply(BigDecimal.valueOf(rdmsJobItem.getManhour()));
        }
        budgetDto.setBudget(sumFee);
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.DEVELOP.getType())){
            BigDecimal remainFee = standardManhourFee.getDevManhourFee().multiply(BigDecimal.valueOf(rdmsJobItem.getManhour()));
            budgetDto.setRemainBudget(remainFee);
        }
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())){
            double remainManhour = this.calJobItemPerformanceManhour(jobitemId);
            BigDecimal remainFee = standardManhourFee.getDevManhourFee().multiply(BigDecimal.valueOf(remainManhour));
            budgetDto.setRemainBudget(remainFee);
        }
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())){
            double remainManhour = this.calJobItemPerformanceManhour(jobitemId);
            BigDecimal remainFee = standardManhourFee.getDevManhourFee().multiply(BigDecimal.valueOf(remainManhour));
            budgetDto.setRemainBudget(remainFee);
        }
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())){
            double remainManhour = this.calJobItemPerformanceManhour(jobitemId);
            BigDecimal remainFee = standardManhourFee.getDevManhourFee().multiply(BigDecimal.valueOf(remainManhour));
            budgetDto.setRemainBudget(remainFee);
        }
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())){
            double remainManhour = this.calJobItemPerformanceManhour(jobitemId);
            BigDecimal remainFee = standardManhourFee.getDevManhourFee().multiply(BigDecimal.valueOf(remainManhour));
            budgetDto.setRemainBudget(remainFee);
        }
        if(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_BUG.getType())){
            double remainManhour = this.calJobItemPerformanceManhour(jobitemId);
            BigDecimal remainFee = standardManhourFee.getDevManhourFee().multiply(BigDecimal.valueOf(remainManhour));
            budgetDto.setRemainBudget(remainFee);
        }
        return budgetDto;
    }

    @Transactional
    public BigDecimal getCharacterRemainTestManhourFeeByJobitemId(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(! ObjectUtils.isEmpty(rdmsJobItem.getCharacterId())){
            BigDecimal characterRemainTestManhourFee = this.getCharacterRemainTestManhourFee(rdmsJobItem.getCharacterId());
            return characterRemainTestManhourFee;
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    public BigDecimal getCharacterRemainTestManhourFee(String characterId){
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        if(! ObjectUtils.isEmpty(rdmsCharacter)){
            RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(rdmsCharacter.getCustomerId());
            BigDecimal sumTestFee = standardManhour.getTestManhourFee().multiply(BigDecimal.valueOf(rdmsCharacter.getTestManhourApproved()));
            BigDecimal allTestManhourFee = this.calCharacterAllTestManhourFee(characterId);
            return sumTestFee.subtract(allTestManhourFee);
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    public BigDecimal getCharacterRemainBudgetFee(String characterId){
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        BigDecimal budget = character.getBudget();
        // 开发工单 测试工单 物料采购 其他费用
        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.DEVELOP);
        typeList.add(JobItemTypeEnum.CHARACTER_INT);

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        double devSumManhour = 0.0;
        double testSumManhour = 0.0;
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, statusEnumList, typeList);
        if(!CollectionUtils.isEmpty(jobitemList)){
            for(RdmsJobItemDto jobItemDto: jobitemList){
                devSumManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                if(! jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())){
                    testSumManhour += this.calFirstLevelJobItemTestManhour(jobItemDto.getId());
                }
            }
        }
        RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(character.getCustomerId());
        BigDecimal sumDevFee = standardManhour.getDevManhourFee().multiply(BigDecimal.valueOf(devSumManhour));
        BigDecimal sumTestFee = standardManhour.getTestManhourFee().multiply(BigDecimal.valueOf(testSumManhour));

        //物料统计
        List<RdmsMaterialManageDto> materialList = rdmsMaterialManageService.getHaveApprovedListByCharacterId(characterId);
        BigDecimal haveUsed_material = new BigDecimal(0);
        if(! CollectionUtils.isEmpty(materialList)){
            List<RdmsMaterialManageDto> expenditureMaterialManageDtos = materialList.stream().filter(item -> item.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())).collect(Collectors.toList());
            if(! CollectionUtils.isEmpty(expenditureMaterialManageDtos)){
                haveUsed_material = expenditureMaterialManageDtos.stream().map(RdmsMaterialManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }

        //费用统计
        List<RdmsFeeManageDto> otherFeeList = rdmsFeeManageService.getListByCharacterId(characterId);
        BigDecimal haveUsed_otherFee = new BigDecimal(0);
        if(! CollectionUtils.isEmpty(otherFeeList)){
            haveUsed_otherFee = otherFeeList.stream().map(RdmsFeeManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return budget.subtract(sumDevFee).subtract(sumTestFee).subtract(haveUsed_material).subtract(haveUsed_otherFee);
    }


    @Transactional
    public RdmsCharacterBudgeExeInfoDto getCharacterMaterialBudgetExeInfo(String characterId){
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);

        RdmsCharacterBudgeExeInfoDto characterMaterialExeInfoDto = new RdmsCharacterBudgeExeInfoDto();
        characterMaterialExeInfoDto.setId(character.getId());  //将Id赋值为characterId
        characterMaterialExeInfoDto.setType(BudgetExeTypeEnum.MATERIAL);
        characterMaterialExeInfoDto.setTypeName(BudgetExeTypeEnum.MATERIAL.getName());
        Integer materialApplicationNumByCharacterId = rdmsMaterialManageService.getMaterialApplicationNumByCharacterId(characterId);
        characterMaterialExeInfoDto.setApproveNum(materialApplicationNumByCharacterId);
        Integer materialSubmitNumByCharacterId = rdmsMaterialManageService.getMaterialSubmitNumByCharacterId(characterId);
        characterMaterialExeInfoDto.setRecheckNum(materialSubmitNumByCharacterId);

        characterMaterialExeInfoDto.setCharacterName(character.getCharacterName());
        characterMaterialExeInfoDto.setPlanCompleteTime(character.getPlanCompleteTime());
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(character.getSubprojectId());
        characterMaterialExeInfoDto.setSubProjectName(subproject.getLabel());

        //物料统计
        List<RdmsMaterialManageDto> materialList = rdmsMaterialManageService.getListByCharacterIdForStatistics(characterId);
        if(! CollectionUtils.isEmpty(materialList))
        {
            List<RdmsMaterialManageDto> expenditureMaterialList = materialList.stream().filter(item -> item.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())).collect(Collectors.toList());
            if(! CollectionUtils.isEmpty(expenditureMaterialList)){
                characterMaterialExeInfoDto.setItemNum(expenditureMaterialList.size());
                long count1 = expenditureMaterialList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())).count();
                long count2 = expenditureMaterialList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())).count();
                characterMaterialExeInfoDto.setCompleteItemNum((int)(count1 + count2));
                BigDecimal sumAccountCost = expenditureMaterialList.stream().map(RdmsMaterialManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
                characterMaterialExeInfoDto.setActiveCharge(sumAccountCost);
            }else {
                characterMaterialExeInfoDto.setActiveCharge(BigDecimal.ZERO);
            }
            characterMaterialExeInfoDto.setSumManhour(0.0);
            characterMaterialExeInfoDto.setItemBudget(character.getMaterialFeeApproved());
            if(characterMaterialExeInfoDto.getItemBudget().doubleValue() > 0){
                BigDecimal rate_material = characterMaterialExeInfoDto.getActiveCharge().divide(characterMaterialExeInfoDto.getItemBudget(), 6, RoundingMode.HALF_UP);
                characterMaterialExeInfoDto.setBudgetRate(rate_material);
            }else{
                characterMaterialExeInfoDto.setBudgetRate(BigDecimal.ZERO);
            }

            characterMaterialExeInfoDto.setChildren(null);
        }
        return characterMaterialExeInfoDto;
    }

    @Transactional
    public List<RdmsBudgeExeInfoDto> getCharacterBudgetExeSummaryList(String characterId){
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        List<RdmsBudgeExeInfoDto> exeInfoDtoList = new ArrayList<>();

        //Double 数据保留两位小数 format
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(6);
        nf.setRoundingMode(RoundingMode.HALF_UP);

        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.DEVELOP);
        typeList.add(JobItemTypeEnum.CHARACTER_INT);
        typeList.add(JobItemTypeEnum.TEST);
        List<JobItemTypeEnum> auxTypeList = new ArrayList<>();
        auxTypeList.add(JobItemTypeEnum.CHARACTER_INT);
//        auxTypeList.add(JobItemTypeEnum.TASK_SUBP);
        //获得所有计算成本的工单
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeListNotInAuxTypeList(characterId, null, typeList, auxTypeList);
        if(! CollectionUtils.isEmpty(jobitemList))
        {
            //开发工单 包括 集成工单
            RdmsBudgeExeInfoDto exeInfoDto = new RdmsBudgeExeInfoDto();
            exeInfoDto.setId(UuidUtil.getShortUuid());
            exeInfoDto.setType(BudgetExeTypeEnum.DEVELOP);
            exeInfoDto.setTypeName(BudgetExeTypeEnum.DEVELOP.getName());
            List<RdmsJobItemDto> devJobList = jobitemList.stream().filter(item -> item.getType().equals(JobItemTypeEnum.DEVELOP.getType())).collect(Collectors.toList());
            List<RdmsJobItemDto> intJobList = jobitemList.stream().filter(item -> item.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())).collect(Collectors.toList());
            devJobList.addAll(intJobList);  //集成工单也是一种开发工单
            if(!CollectionUtils.isEmpty(devJobList)){
                exeInfoDto.setItemNum(devJobList.size());
                long count = devJobList.stream().filter(item -> item.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())).count();
                exeInfoDto.setCompleteItemNum((int)count);
                Double sumManhour = devJobList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                double sumIntManhour = Double.parseDouble(nf.format(sumManhour));
                exeInfoDto.setSumManhour(sumIntManhour);
                BigDecimal standManhourFee = this.getStandManhourFee(character.getCustomerId(), OperateTypeEnum.DEVELOP);
                exeInfoDto.setItemBudget(standManhourFee.multiply(BigDecimal.valueOf(character.getDevManhourApproved())));
                exeInfoDto.setActiveCharge(standManhourFee.multiply(BigDecimal.valueOf(sumIntManhour)));
                if(exeInfoDto.getItemBudget().doubleValue()>0){
                    BigDecimal rate = exeInfoDto.getActiveCharge().divide(exeInfoDto.getItemBudget(), 6, RoundingMode.HALF_UP);
                    exeInfoDto.setBudgetRate(rate);
                }else{
                    exeInfoDto.setBudgetRate(BigDecimal.ZERO);
                }

                List<RdmsBudgeExeInfoDto> children = new ArrayList<>();
                {
                    //集成工单的所有 child test 工单汇总
                    if(! CollectionUtils.isEmpty(intJobList)){
                        List<RdmsJobItemDto> allTestJobList = new ArrayList<>();
                        List<JobItemTypeEnum> jobTypeList = new ArrayList<>();
                        jobTypeList.add(JobItemTypeEnum.TEST);
                        for(RdmsJobItemDto jobItemDto: intJobList){
                            List<RdmsJobItemDto> testJobitemList = rdmsJobItemService.getJobitemListByParentIdStatusListTypeAndAuxTypeList(jobItemDto.getId(),null, jobTypeList, auxTypeList);
                            allTestJobList.addAll(testJobitemList);
                        }
                        {
                            RdmsBudgeExeInfoDto testJobExeInfoDto = new RdmsBudgeExeInfoDto();
                            testJobExeInfoDto.setId(UuidUtil.getShortUuid());
                            testJobExeInfoDto.setType(BudgetExeTypeEnum.TEST);
                            testJobExeInfoDto.setTypeName(BudgetExeTypeEnum.TEST.getName());
                            testJobExeInfoDto.setItemNum(allTestJobList.size());
                            if(! CollectionUtils.isEmpty(allTestJobList)){
                                Double testJobSumManhour = allTestJobList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                                double sumTestManhour = Double.parseDouble(nf.format(testJobSumManhour));
                                testJobExeInfoDto.setSumManhour(sumTestManhour);
                                BigDecimal standTestManhourFee = this.getStandManhourFee(character.getCustomerId(), OperateTypeEnum.TEST);
                                testJobExeInfoDto.setActiveCharge(standTestManhourFee.multiply(BigDecimal.valueOf(sumTestManhour)));
                                children.add(testJobExeInfoDto);
                            }
                        }
                    }
                }
                {
                    //集成工单的所有 child assist 工单汇总
                    if(! CollectionUtils.isEmpty(intJobList)){
                        List<RdmsJobItemDto> allAssistJobList = new ArrayList<>();
                        List<JobItemTypeEnum> jobTypeList = new ArrayList<>();
                        jobTypeList.add(JobItemTypeEnum.ASSIST);
                        for(RdmsJobItemDto jobItemDto: intJobList){
                            List<RdmsJobItemDto> assistJobitemList = rdmsJobItemService.getJobitemListByParentIdStatusListTypeAndAuxTypeList(jobItemDto.getId(),null, jobTypeList, auxTypeList);
                            allAssistJobList.addAll(assistJobitemList);
                        }
                        {
                            RdmsBudgeExeInfoDto assistJobExeInfoDto = new RdmsBudgeExeInfoDto();
                            assistJobExeInfoDto.setId(UuidUtil.getShortUuid());
                            assistJobExeInfoDto.setType(BudgetExeTypeEnum.ASSIST);
                            assistJobExeInfoDto.setTypeName(BudgetExeTypeEnum.ASSIST.getName());
                            assistJobExeInfoDto.setItemNum(allAssistJobList.size());
                            if(! CollectionUtils.isEmpty(allAssistJobList)){
                                Double assistJobSumManhour = allAssistJobList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                                String sumAssistManhour = nf.format(assistJobSumManhour);
                                assistJobExeInfoDto.setSumManhour(Double.parseDouble(sumAssistManhour));
                                BigDecimal standDevManhourFee = this.getStandManhourFee(character.getCustomerId(), OperateTypeEnum.DEVELOP);
                                assistJobExeInfoDto.setActiveCharge(standDevManhourFee.multiply(BigDecimal.valueOf(Double.parseDouble(sumAssistManhour))));
                                children.add(assistJobExeInfoDto);
                            }
                        }
                    }
                }
                exeInfoDto.setChildren(children);
                exeInfoDtoList.add(exeInfoDto);
            }
        }
        {
            //测试工单
            RdmsBudgeExeInfoDto testJobExeInfoDto = new RdmsBudgeExeInfoDto();
            testJobExeInfoDto.setId(UuidUtil.getShortUuid());
            testJobExeInfoDto.setType(BudgetExeTypeEnum.TEST);
            testJobExeInfoDto.setTypeName(BudgetExeTypeEnum.TEST.getName());
            List<RdmsJobItemDto> testJobList = jobitemList.stream().filter(item -> item.getType().equals(JobItemTypeEnum.TEST.getType())).collect(Collectors.toList());
            testJobExeInfoDto.setItemNum(testJobList.size());
            long count = testJobList.stream().filter(item -> item.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())).count();
            testJobExeInfoDto.setCompleteItemNum((int)count);
            if(! CollectionUtils.isEmpty(testJobList)){
                Double testJobSumManhour = testJobList.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
                String sumManhour = nf.format(testJobSumManhour);
                testJobExeInfoDto.setSumManhour(Double.parseDouble(sumManhour));
                BigDecimal standTestManhourFee = this.getStandManhourFee(character.getCustomerId(), OperateTypeEnum.TEST);
                testJobExeInfoDto.setItemBudget(standTestManhourFee.multiply(BigDecimal.valueOf(character.getTestManhourApproved())));
                testJobExeInfoDto.setActiveCharge(standTestManhourFee.multiply(BigDecimal.valueOf(Double.parseDouble(sumManhour))));
                if(testJobExeInfoDto.getItemBudget().doubleValue()>0){
                    BigDecimal rate_test = testJobExeInfoDto.getActiveCharge().divide(testJobExeInfoDto.getItemBudget(), 6, RoundingMode.HALF_UP);
                    testJobExeInfoDto.setBudgetRate(rate_test);
                }else{
                    testJobExeInfoDto.setBudgetRate(BigDecimal.ZERO);
                }

                testJobExeInfoDto.setChildren(null);
                exeInfoDtoList.add(testJobExeInfoDto);
            }
        }

        //物料统计
        List<RdmsMaterialManageDto> materialList = rdmsMaterialManageService.getListByCharacterIdForStatistics(characterId);
        if(! CollectionUtils.isEmpty(materialList))
        {
            RdmsBudgeExeInfoDto materialExeInfoDto = new RdmsBudgeExeInfoDto();
            materialExeInfoDto.setId(UuidUtil.getShortUuid());
            materialExeInfoDto.setType(BudgetExeTypeEnum.MATERIAL);
            materialExeInfoDto.setTypeName(BudgetExeTypeEnum.MATERIAL.getName());

            if(! CollectionUtils.isEmpty(materialList)){
                List<RdmsMaterialManageDto> expenditureMaterialManageDtos = materialList.stream().filter(item -> item.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())).collect(Collectors.toList());
                if(! CollectionUtils.isEmpty(expenditureMaterialManageDtos)){
                    materialExeInfoDto.setItemNum(expenditureMaterialManageDtos.size());
                    long count1 = expenditureMaterialManageDtos.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())).count();
                    long count2 = expenditureMaterialManageDtos.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())).count();
                    materialExeInfoDto.setCompleteItemNum((int)(count1 + count2));
                    BigDecimal sumAccountCost = expenditureMaterialManageDtos.stream().map(RdmsMaterialManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
                    materialExeInfoDto.setActiveCharge(sumAccountCost);
                }else {
                    materialExeInfoDto.setActiveCharge(BigDecimal.ZERO);
                    materialExeInfoDto.setItemNum(0);
                    materialExeInfoDto.setCompleteItemNum(0);
                }
            }else{
                materialExeInfoDto.setActiveCharge(BigDecimal.ZERO);
            }
            materialExeInfoDto.setSumManhour(0.0);
            materialExeInfoDto.setItemBudget(character.getMaterialFeeApproved());
            if(materialExeInfoDto.getItemBudget().doubleValue() > 0){
                BigDecimal rate_material = materialExeInfoDto.getActiveCharge().divide(materialExeInfoDto.getItemBudget(), 6, RoundingMode.HALF_UP);
                materialExeInfoDto.setBudgetRate(rate_material);
            }else{
                materialExeInfoDto.setBudgetRate(BigDecimal.ZERO);
            }

            materialExeInfoDto.setChildren(null);
            exeInfoDtoList.add(materialExeInfoDto);
        }

        //其他费用统计
        List<RdmsFeeManageDto> otherFeeList = rdmsFeeManageService.getListByCharacterId(characterId);
        if(! CollectionUtils.isEmpty(otherFeeList))
        {
            RdmsBudgeExeInfoDto otherFeeExeInfoDto = new RdmsBudgeExeInfoDto();
            otherFeeExeInfoDto.setId(UuidUtil.getShortUuid());
            otherFeeExeInfoDto.setType(BudgetExeTypeEnum.OTHER_FEE);
            otherFeeExeInfoDto.setTypeName(BudgetExeTypeEnum.OTHER_FEE.getName());

            if(! CollectionUtils.isEmpty(otherFeeList)){
                BigDecimal sumAccountCost = otherFeeList.stream().map(RdmsFeeManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
                otherFeeExeInfoDto.setActiveCharge(sumAccountCost);
            }else{
                otherFeeExeInfoDto.setActiveCharge(BigDecimal.ZERO);
            }

            otherFeeExeInfoDto.setItemNum(otherFeeList.size());
            long count1 = otherFeeList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.COMPLETE.getStatus())).count();
            long count2 = otherFeeList.stream().filter(item -> item.getStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())).count();
            otherFeeExeInfoDto.setCompleteItemNum((int)(count1 + count2));
            otherFeeExeInfoDto.setSumManhour(0.0);
            otherFeeExeInfoDto.setItemBudget(character.getSumOtherFee());
            if(otherFeeExeInfoDto.getItemBudget().doubleValue()>0){
                BigDecimal rate_fee = otherFeeExeInfoDto.getActiveCharge().divide(otherFeeExeInfoDto.getItemBudget(), 6, RoundingMode.HALF_UP);
                otherFeeExeInfoDto.setBudgetRate(rate_fee);
            }else{
                otherFeeExeInfoDto.setBudgetRate(BigDecimal.ZERO);
            }

            otherFeeExeInfoDto.setChildren(null);
            exeInfoDtoList.add(otherFeeExeInfoDto);
        }

        if(! CollectionUtils.isEmpty(exeInfoDtoList))
        {
            //汇总行
            RdmsBudgeExeInfoDto sumInfoDto = new RdmsBudgeExeInfoDto();
            sumInfoDto.setId(UuidUtil.getShortUuid());
            sumInfoDto.setTypeName("总计");
            Integer jobNum = exeInfoDtoList.stream().map(RdmsBudgeExeInfoDto::getItemNum).reduce(0, Integer::sum);
            sumInfoDto.setItemNum(jobNum);
            Integer completeNum = exeInfoDtoList.stream().map(RdmsBudgeExeInfoDto::getCompleteItemNum).reduce(0, Integer::sum);
            sumInfoDto.setCompleteItemNum(completeNum);
            Double sumJobManhour = exeInfoDtoList.stream().map(RdmsBudgeExeInfoDto::getSumManhour).reduce(0.0, Double::sum);
            String sumManhour = nf.format(sumJobManhour);
            sumInfoDto.setSumManhour(Double.parseDouble(sumManhour));
            sumInfoDto.setItemBudget(character.getBudget());
            BigDecimal sumActiveCharge = exeInfoDtoList.stream().map(RdmsBudgeExeInfoDto::getActiveCharge).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumInfoDto.setActiveCharge(sumActiveCharge);
            if(sumInfoDto.getItemBudget().doubleValue()>0){
                BigDecimal rate_sum = sumInfoDto.getActiveCharge().divide(sumInfoDto.getItemBudget(), 6, RoundingMode.HALF_UP);
                sumInfoDto.setBudgetRate(rate_sum);
            }else{
                sumInfoDto.setBudgetRate(BigDecimal.ZERO);
            }

            sumInfoDto.setChildren(null);
            exeInfoDtoList.add(sumInfoDto);
        }

        return exeInfoDtoList;
    }

    /**
     * 统计一个功能开发的预算使用情况, 统计结果与雨伞批复对应, 集成工单的协作工单和协作工单的测试工单, 由开发费分担支付
     * @param characterId
     * @return
     */
    @Transactional
    public RdmsBudgetExeSummaryDto getBudgetExeSummaryByCharacterId(String characterId) {
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsBudgetExeSummaryDto budgetExeSummary = new RdmsBudgetExeSummaryDto();
        budgetExeSummary.setId(rdmsCharacter.getId());
        budgetExeSummary.setName(rdmsCharacter.getCharacterName());
        budgetExeSummary.setDevManhourApproved(rdmsCharacter.getDevManhourApproved());
        budgetExeSummary.setTestManhourApproved(rdmsCharacter.getTestManhourApproved());
        budgetExeSummary.setMaterialBudgetApproved(rdmsCharacter.getMaterialFeeApproved());
        budgetExeSummary.setOtherFeeBudgetApproved(rdmsCharacter.getSumOtherFee());
        RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(rdmsCharacter.getCustomerId());
        BigDecimal devApprovedManhourFee = standardManhour.getDevManhourFee().multiply(BigDecimal.valueOf(budgetExeSummary.getDevManhourApproved()));
        BigDecimal testApprovedManhourFee = standardManhour.getTestManhourFee().multiply(BigDecimal.valueOf(budgetExeSummary.getTestManhourApproved()));
        budgetExeSummary.setSumBudgetApproved(devApprovedManhourFee.add(testApprovedManhourFee).add(budgetExeSummary.getMaterialBudgetApproved()).add(budgetExeSummary.getOtherFeeBudgetApproved()));

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);

        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.DEVELOP);
        jobitemTypeList.add(JobItemTypeEnum.CHARACTER_INT);

        //获得上述工单的工时统计结果
        Double sumDevManhour = 0.0;
        Double sumTestManhour = 0.0;
        BigDecimal materialFee = BigDecimal.ZERO;
        BigDecimal otherFee = BigDecimal.ZERO;

        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusListTypeList(characterId, statusEnumList, jobitemTypeList);
        if(!CollectionUtils.isEmpty(jobitemList)){
           for(RdmsJobItemDto jobItemDto: jobitemList){
               sumDevManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
               sumTestManhour += this.calFirstLevelJobItemTestManhour(jobItemDto.getId());
               materialFee = materialFee.add(this.calMaterialFeeByJobitemId(jobItemDto.getId()));
               otherFee = otherFee.add(this.calOtherFeeByJobitemId(jobItemDto.getId()));
           }
        }

        budgetExeSummary.setDevManhourActive(sumDevManhour);
        budgetExeSummary.setTestManhourActive(sumTestManhour);
        budgetExeSummary.setMaterialBudgetActive(materialFee);
        budgetExeSummary.setOtherFeeBudgetActive(otherFee);

        BigDecimal devActiveManhourFee = BigDecimal.ZERO;
        if(!ObjectUtils.isEmpty(budgetExeSummary.getDevManhourActive()) ){
            devActiveManhourFee = standardManhour.getDevManhourFee().multiply(BigDecimal.valueOf(budgetExeSummary.getDevManhourActive()));
        }

        BigDecimal testActiveManhourFee =BigDecimal.ZERO;
        if(!ObjectUtils.isEmpty(budgetExeSummary.getTestManhourActive()) ){
            testActiveManhourFee = standardManhour.getTestManhourFee().multiply(BigDecimal.valueOf(budgetExeSummary.getTestManhourActive()));
        }

        budgetExeSummary.setSumBudgetActive(devActiveManhourFee.add(testActiveManhourFee).add(budgetExeSummary.getMaterialBudgetActive()).add(budgetExeSummary.getOtherFeeBudgetActive()));
        if(ObjectUtils.isEmpty(budgetExeSummary.getSumBudgetApproved()) || budgetExeSummary.getSumBudgetApproved().doubleValue()==0.0){
            budgetExeSummary.setBudgetRate(BigDecimal.ZERO);
        }else{
            budgetExeSummary.setBudgetRate(budgetExeSummary.getSumBudgetActive().divide(budgetExeSummary.getSumBudgetApproved(), 6, RoundingMode.HALF_UP));
        }
        return budgetExeSummary;
    }

    @Transactional
    public void saveBudgetExeInfo(RdmsJobItemPropertyDto propertyDto, ReviewResultEnum reviewResultEnum) {
        //将评审结果保存到budgetExe表中
        if(propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType()))
        {
            //如果是功能评审
            RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
            RdmsBudgetExeSummaryDto budgetExeSummary = this.getBudgetExeSummaryByCharacterId(rdmsJobItem.getCharacterId());
            RdmsBudgetExe budgetExe = new RdmsBudgetExe();
            budgetExe.setId(budgetExeSummary.getId());
            budgetExe.setItemId(budgetExeSummary.getId());
            budgetExe.setParentId(rdmsJobItem.getSubprojectId());
            budgetExe.setItemName(budgetExeSummary.getName());
            budgetExe.setDocType(DocTypeEnum.CHARACTER.getType());
            budgetExe.setReviewDesc(propertyDto.getJobDescription());
            budgetExe.setReviewLeaderId(rdmsJobItem.getProjectManagerId());  //评审组长是工单记载的项目经理
            budgetExe.setReviewCooIdStr(rdmsJobItem.getReviewWorkerIdStr());
            budgetExe.setDevManhourApproved(budgetExeSummary.getDevManhourApproved());
            budgetExe.setTestManhourApproved(budgetExeSummary.getTestManhourApproved());
            budgetExe.setMaterialFeeApproved(budgetExeSummary.getMaterialBudgetApproved());
            budgetExe.setOtherFeeApproved(budgetExeSummary.getOtherFeeBudgetApproved());
            budgetExe.setDevManhourActive(budgetExeSummary.getDevManhourActive());
            budgetExe.setTestManhourActive(budgetExeSummary.getTestManhourActive());
            budgetExe.setMaterialFeeActive(budgetExeSummary.getMaterialBudgetActive());
            budgetExe.setOtherFeeActive(budgetExeSummary.getOtherFeeBudgetActive());
            budgetExe.setSumBudgetActive(budgetExeSummary.getSumBudgetActive());
            budgetExe.setSumBudgetApproved(budgetExeSummary.getSumBudgetApproved());
            budgetExe.setBudgetRate(budgetExeSummary.getBudgetRate());
            //submit 是提交评审的文件, 来自评审申请工单
            budgetExe.setSubmitFileIdListStr(rdmsJobItem.getFileListStr());
            budgetExe.setReviewFileIdListStr(propertyDto.getFileListStr());
            budgetExe.setChildrenIdListStr(null);  //功能没有children
            budgetExe.setChildSubprojectIdListStr(null);
            budgetExe.setResult(reviewResultEnum.getStatus());
            budgetExe.setReviewTime(new Date());
            budgetExe.setCreateTime(new Date());
            budgetExe.setUpdateTime(new Date());
            budgetExe.setDeleted(0);
            rdmsBudgetExeService.saveBudgetExe(budgetExe);

        }
        else if(propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_SUBP.getType()))
        {
            //如果是子项目评审
            RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
            RdmsBudgetExeSummaryDto budgetExeSummary = this.getBudgetExeSummaryBySubprojectId(rdmsJobItem.getSubprojectId());
            RdmsBudgetExe budgetExe = new RdmsBudgetExe();
            budgetExe.setId(budgetExeSummary.getId());
            budgetExe.setItemId(budgetExeSummary.getId());
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(rdmsJobItem.getSubprojectId());
            budgetExe.setParentId(subproject.getParent());
            budgetExe.setItemName(budgetExeSummary.getName());
            budgetExe.setDocType(DocTypeEnum.SUB_PROJECT.getType());
            budgetExe.setReviewDesc(propertyDto.getJobDescription());
            budgetExe.setReviewLeaderId(rdmsJobItem.getProjectManagerId());  //评审组长是工单记载的项目经理
            budgetExe.setReviewCooIdStr(rdmsJobItem.getReviewWorkerIdStr());
            budgetExe.setDevManhourApproved(budgetExeSummary.getDevManhourApproved());
            budgetExe.setTestManhourApproved(budgetExeSummary.getTestManhourApproved());
            budgetExe.setMaterialFeeApproved(budgetExeSummary.getMaterialBudgetApproved());
            budgetExe.setOtherFeeApproved(budgetExeSummary.getOtherFeeBudgetApproved());
            budgetExe.setDevManhourActive(budgetExeSummary.getDevManhourActive());
            budgetExe.setTestManhourActive(budgetExeSummary.getTestManhourActive());
            budgetExe.setMaterialFeeActive(budgetExeSummary.getMaterialBudgetActive());
            budgetExe.setOtherFeeActive(budgetExeSummary.getOtherFeeBudgetActive());
            budgetExe.setSumBudgetActive(budgetExeSummary.getSumBudgetActive());
            budgetExe.setSumBudgetApproved(budgetExeSummary.getSumBudgetApproved());
            budgetExe.setBudgetRate(budgetExeSummary.getBudgetRate());
            //submit 是提交评审的文件, 来自评审申请工单
            budgetExe.setSubmitFileIdListStr(rdmsJobItem.getFileListStr());
            budgetExe.setReviewFileIdListStr(propertyDto.getFileListStr());
            budgetExe.setChildrenIdListStr(budgetExeSummary.getChildrenIdListStr());
            budgetExe.setChildSubprojectIdListStr(budgetExeSummary.getChildSubprojectIdListStr());
            budgetExe.setResult(reviewResultEnum.getStatus());
            budgetExe.setReviewTime(new Date());
            budgetExe.setCreateTime(new Date());
            budgetExe.setUpdateTime(new Date());
            budgetExe.setDeleted(0);
            rdmsBudgetExeService.saveBudgetExe(budgetExe);
        }
    }

    /**
     * 直接对后台数据进行计算获得结果, 而不是通过经过评审的数据进行数据汇总
     *计算子项的预算执行情况  成本包括 1. 下一级子项目 2. 直属功能开发  3. 直属任务工单
     * @param subprojectId
     * @return
     */
    @Transactional
    public RdmsBudgetExeSummaryDto getBudgetExeSummaryBySubprojectId(String subprojectId) {
        RdmsBudgetExeSummaryDto budgetExeSummary = new RdmsBudgetExeSummaryDto();
        //1. 得到子项目信息
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        budgetExeSummary.setId(subprojectId);
        budgetExeSummary.setName(subproject.getLabel());

        //2. 得到所有下级子项目信息
        List<RdmsProjectSubproject> childSubprojectList = new ArrayList<>();
        List<RdmsProjectSubproject> childSubprojectLisOrigin = rdmsSubprojectService.getSubprojectListByParentId(subprojectId);
        if(!CollectionUtils.isEmpty(childSubprojectLisOrigin)){
            childSubprojectList = childSubprojectLisOrigin.stream().filter(item -> !item.getId().equals(subprojectId)).collect(Collectors.toList());
        }
        if(!CollectionUtils.isEmpty(childSubprojectList)){
            for(RdmsProjectSubproject childSubproject: childSubprojectList){
                if(childSubproject.getStatus().equals(ProjectStatusEnum.COMPLETE.getStatus()) || childSubproject.getStatus().equals(ProjectStatusEnum.ARCHIVED.getStatus())){
                    RdmsBudgetExeDto rdmsBudgetExeDto = this.getSubprojectBudgetExeSummary(childSubproject.getId());  //从子项目评审数据获得
                    RdmsBudgetExeSummaryDto rdmsBudgetExeSummaryDto = new RdmsBudgetExeSummaryDto();
                    //private String id;
                    rdmsBudgetExeSummaryDto.setId(rdmsBudgetExeDto.getId());
                    //private String name;
                    rdmsBudgetExeSummaryDto.setName(rdmsBudgetExeDto.getItemName());
                    //private Double devManhourApproved;
                    rdmsBudgetExeSummaryDto.setDevManhourApproved(rdmsBudgetExeDto.getDevManhourApproved());
                    //private Double testManhourApproved;
                    rdmsBudgetExeSummaryDto.setTestManhourApproved(rdmsBudgetExeDto.getTestManhourApproved());
                    //private BigDecimal materialBudgetApproved;
                    rdmsBudgetExeSummaryDto.setMaterialBudgetApproved(rdmsBudgetExeDto.getMaterialFeeApproved());
                    //private BigDecimal otherFeeBudgetApproved;
                    rdmsBudgetExeSummaryDto.setOtherFeeBudgetApproved(rdmsBudgetExeDto.getOtherFeeApproved());
                    //private Double devManhourActive;
                    rdmsBudgetExeSummaryDto.setDevManhourActive(rdmsBudgetExeDto.getDevManhourActive());
                    //private Double testManhourActive;
                    rdmsBudgetExeSummaryDto.setTestManhourActive(rdmsBudgetExeDto.getTestManhourActive());
                    //private BigDecimal materialBudgetActive;
                    rdmsBudgetExeSummaryDto.setMaterialBudgetActive(rdmsBudgetExeDto.getMaterialFeeActive());
                    //private BigDecimal otherFeeBudgetActive;
                    rdmsBudgetExeSummaryDto.setOtherFeeBudgetActive(rdmsBudgetExeDto.getOtherFeeActive());
                    //private BigDecimal sumBudgetApproved;
                    rdmsBudgetExeSummaryDto.setSumBudgetApproved(rdmsBudgetExeDto.getSumBudgetApproved());
                    //private BigDecimal sumBudgetActive;
                    rdmsBudgetExeSummaryDto.setSumBudgetActive(rdmsBudgetExeDto.getSumBudgetActive());
                    //private BigDecimal budgetRate;
                    rdmsBudgetExeSummaryDto.setBudgetRate(rdmsBudgetExeDto.getBudgetRate());

//                    RdmsBudgetExeSummaryDto budgetExeSummaryBySubprojectId = this.getBudgetExeSummaryBySubprojectId(childSubproject.getId());
                    if(CollectionUtils.isEmpty(budgetExeSummary.getChildSubprojectList())){
                        List<RdmsBudgetExeSummaryDto> tempList = new ArrayList<>();
                        tempList.add(rdmsBudgetExeSummaryDto);
                        budgetExeSummary.setChildSubprojectList(tempList);
                    }else{
                        budgetExeSummary.getChildSubprojectList().add(rdmsBudgetExeSummaryDto);
                    }
                }
            }
            List<String> childSubprojectIdList = childSubprojectList.stream().map(RdmsProjectSubproject::getId).collect(Collectors.toList());
            budgetExeSummary.setChildSubprojectIdListStr(JSON.toJSONString(childSubprojectIdList));
        }
        //3. 得到所有直属功能开发信息
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(subprojectId, CharacterStatusEnum.ARCHIVED);
        if(!CollectionUtils.isEmpty(characterList)){
            for(RdmsCharacterDto characterDto: characterList){
                RdmsBudgetExeSummaryDto budgetExeSummaryByCharacterId = this.getBudgetExeSummaryByCharacterId(characterDto.getId());
                if(CollectionUtils.isEmpty(budgetExeSummary.getChildren())){
                    List<RdmsBudgetExeSummaryDto> tempList = new ArrayList<>();
                    tempList.add(budgetExeSummaryByCharacterId);
                    budgetExeSummary.setChildren(tempList);
                }else{
                    budgetExeSummary.getChildren().add(budgetExeSummaryByCharacterId);
                }
            }
            List<String> childCharacterIdList = characterList.stream().map(RdmsCharacterDto::getId).collect(Collectors.toList());
            budgetExeSummary.setChildrenIdListStr(JSON.toJSONString(childCharacterIdList));
        }

        //计算实际分项费用支出
        RdmsManhourStandardExample standardExample = new RdmsManhourStandardExample();
        standardExample.createCriteria().andCustomerIdEqualTo(subproject.getCustomerId()).andDeletedEqualTo("0");
        List<RdmsManhourStandard> rdmsManhourStandards = rdmsManhourStandardMapper.selectByExample(standardExample);
        if(rdmsManhourStandards.size()>1){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }

        //总预算
        budgetExeSummary.setSumBudgetApproved(subproject.getBudget().add(subproject.getAddCharge()));
        //计算实际费用支出
        budgetExeSummary.setSumBudgetActive(BigDecimal.ZERO);
        budgetExeSummary.setDevManhourActive(0.0);
        budgetExeSummary.setTestManhourActive(0.0);
        budgetExeSummary.setMaterialBudgetActive(BigDecimal.ZERO);
        budgetExeSummary.setOtherFeeBudgetActive(BigDecimal.ZERO);
        //1. 所有子项目的实际支出
        if(!CollectionUtils.isEmpty(budgetExeSummary.getChildSubprojectList())){
            //总审批预算
            BigDecimal sumChildSubProjectApproved = budgetExeSummary.getChildSubprojectList().stream().map(RdmsBudgetExeSummaryDto::getSumBudgetApproved).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setSumBudgetApproved(budgetExeSummary.getSumBudgetApproved().add(sumChildSubProjectApproved));
            //总实际支出
            BigDecimal sumChildSubProjectActiveCharge = budgetExeSummary.getChildSubprojectList().stream().map(RdmsBudgetExeSummaryDto::getSumBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setSumBudgetActive(sumChildSubProjectActiveCharge);

            Double sumActiveDevManhour = budgetExeSummary.getChildSubprojectList().stream().map(RdmsBudgetExeSummaryDto::getDevManhourActive).reduce(0.0, Double::sum);
            budgetExeSummary.setDevManhourActive(sumActiveDevManhour);

            Double sumActiveTestManhour = budgetExeSummary.getChildSubprojectList().stream().map(RdmsBudgetExeSummaryDto::getTestManhourActive).reduce(0.0, Double::sum);
            budgetExeSummary.setTestManhourActive(sumActiveTestManhour);

            BigDecimal sumActiveMaterialCharge = budgetExeSummary.getChildSubprojectList().stream().map(RdmsBudgetExeSummaryDto::getMaterialBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setMaterialBudgetActive(sumActiveMaterialCharge);

            BigDecimal sumActiveFeeCharge = budgetExeSummary.getChildSubprojectList().stream().map(RdmsBudgetExeSummaryDto::getOtherFeeBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setOtherFeeBudgetActive(sumActiveFeeCharge);

        }
        //2. 所有直属功能的实际支出
        if(!CollectionUtils.isEmpty(budgetExeSummary.getChildren())){
            BigDecimal sumChildCharacterActiveCharge = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getSumBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setSumBudgetActive(budgetExeSummary.getSumBudgetActive().add(sumChildCharacterActiveCharge));

            Double sumActiveDevManhour = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getDevManhourActive).reduce(0.0, Double::sum);
            budgetExeSummary.setDevManhourActive(budgetExeSummary.getDevManhourActive() + sumActiveDevManhour);

            Double sumActiveTestManhour = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getTestManhourActive).reduce(0.0, Double::sum);
            budgetExeSummary.setTestManhourActive(budgetExeSummary.getTestManhourActive() + sumActiveTestManhour);

            BigDecimal sumActiveMaterialCharge = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getMaterialBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setMaterialBudgetActive(budgetExeSummary.getMaterialBudgetActive().add(sumActiveMaterialCharge));

            BigDecimal sumActiveFeeCharge = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getOtherFeeBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setOtherFeeBudgetActive(budgetExeSummary.getOtherFeeBudgetActive().add(sumActiveFeeCharge));

        }
        //3. 所有直属任务工单的实际支出
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);
        // 1. 得到所有任务工单
        List<RdmsJobItemDto> taskJobList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, jobItemStatusList, jobitemTypeList);
        Double sumTaskManhour = 0.0;
        double sumTaskDevManhour = 0.0;
        double sumTaskTestManhour = 0.0;
        BigDecimal materialFee = BigDecimal.ZERO;
        BigDecimal otherFee = BigDecimal.ZERO;

        if(!CollectionUtils.isEmpty(taskJobList)){
            for(RdmsJobItemDto jobItemDto: taskJobList){
                sumTaskManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                sumTaskDevManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                if(! jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())){
                    sumTaskTestManhour += this.calFirstLevelJobItemTestManhour(jobItemDto.getId());
                }
                materialFee = materialFee.add(this.calMaterialFeeByJobitemId(jobItemDto.getId()));
                otherFee = otherFee.add(this.calOtherFeeByJobitemId(jobItemDto.getId()));
            }
        }
        //总的执行中计入任务工单的物料和费用
        budgetExeSummary.setSumBudgetActive(budgetExeSummary.getSumBudgetActive().add(materialFee).add(otherFee));

        if(!CollectionUtils.isEmpty(rdmsManhourStandards)){
            BigDecimal sumTaskCharge = rdmsManhourStandards.get(0).getDevManhourFee().multiply(BigDecimal.valueOf(sumTaskManhour));
            budgetExeSummary.setSumBudgetActive(budgetExeSummary.getSumBudgetActive().add(sumTaskCharge));

            budgetExeSummary.setDevManhourActive(budgetExeSummary.getDevManhourActive() + sumTaskDevManhour);
            budgetExeSummary.setTestManhourActive(budgetExeSummary.getDevManhourActive() + sumTaskTestManhour);
        }
        //计算预算执行率
        if(ObjectUtils.isEmpty(budgetExeSummary.getSumBudgetApproved()) || budgetExeSummary.getSumBudgetApproved().doubleValue()==0.0){
            budgetExeSummary.setBudgetRate(BigDecimal.ZERO);
        }else{
            budgetExeSummary.setBudgetRate(budgetExeSummary.getSumBudgetActive().divide(budgetExeSummary.getSumBudgetApproved(),6,RoundingMode.HALF_UP));
        }

        return budgetExeSummary;
    }

    /**
     * 计算子项目自身的实时预算执行情况
     * @param subprojectId
     * @return
     */
    @Transactional
    public RdmsBudgetExeSummaryDto calSubprojectSelfBudgetActiveExeInfo(String subprojectId) {
        RdmsBudgetExeSummaryDto budgetExeSummary = new RdmsBudgetExeSummaryDto();
        //1. 得到子项目信息
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        budgetExeSummary.setId(subprojectId);
        budgetExeSummary.setName(subproject.getLabel());

        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.SETUPED);
        statusEnumList.add(CharacterStatusEnum.DEVELOPING);
        statusEnumList.add(CharacterStatusEnum.INTEGRATION);
        statusEnumList.add(CharacterStatusEnum.DEV_COMPLETE);
        statusEnumList.add(CharacterStatusEnum.REVIEW);
        statusEnumList.add(CharacterStatusEnum.ARCHIVED);
        //3. 得到所有直属功能开发信息
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectIdAndStatusList(subprojectId, statusEnumList);
        if(!CollectionUtils.isEmpty(characterList)){
            for(RdmsCharacterDto characterDto: characterList){
                RdmsBudgetExeSummaryDto budgetExeSummaryByCharacterId = this.getBudgetExeSummaryByCharacterId(characterDto.getId());
                if(CollectionUtils.isEmpty(budgetExeSummary.getChildren())){
                    List<RdmsBudgetExeSummaryDto> tempList = new ArrayList<>();
                    tempList.add(budgetExeSummaryByCharacterId);
                    budgetExeSummary.setChildren(tempList);
                }else{
                    budgetExeSummary.getChildren().add(budgetExeSummaryByCharacterId);
                }
            }
            List<String> childCharacterIdList = characterList.stream().map(RdmsCharacterDto::getId).collect(Collectors.toList());
            budgetExeSummary.setChildrenIdListStr(JSON.toJSONString(childCharacterIdList));
        }

        //计算实际分项费用支出
        RdmsManhourStandardExample standardExample = new RdmsManhourStandardExample();
        standardExample.createCriteria().andCustomerIdEqualTo(subproject.getCustomerId()).andDeletedEqualTo("0");
        List<RdmsManhourStandard> rdmsManhourStandards = rdmsManhourStandardMapper.selectByExample(standardExample);
        if(rdmsManhourStandards.size()>1){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }

        //总预算
        budgetExeSummary.setSumBudgetApproved(subproject.getBudget().add(subproject.getAddCharge()));
        //计算实际费用支出
        budgetExeSummary.setSumBudgetActive(BigDecimal.ZERO);
        budgetExeSummary.setDevManhourActive(0.0);
        budgetExeSummary.setTestManhourActive(0.0);
        budgetExeSummary.setMaterialBudgetActive(BigDecimal.ZERO);
        budgetExeSummary.setOtherFeeBudgetActive(BigDecimal.ZERO);

        //2. 所有直属功能的实际支出
        if(!CollectionUtils.isEmpty(budgetExeSummary.getChildren())){
            BigDecimal sumChildCharacterActiveCharge = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getSumBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setSumBudgetActive(budgetExeSummary.getSumBudgetActive().add(sumChildCharacterActiveCharge));

            Double sumActiveDevManhour = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getDevManhourActive).reduce(0.0, Double::sum);
            budgetExeSummary.setDevManhourActive(budgetExeSummary.getDevManhourActive() + sumActiveDevManhour);

            Double sumActiveTestManhour = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getTestManhourActive).reduce(0.0, Double::sum);
            budgetExeSummary.setTestManhourActive(budgetExeSummary.getTestManhourActive() + sumActiveTestManhour);

            BigDecimal sumActiveMaterialCharge = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getMaterialBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setMaterialBudgetActive(budgetExeSummary.getMaterialBudgetActive().add(sumActiveMaterialCharge));

            BigDecimal sumActiveFeeCharge = budgetExeSummary.getChildren().stream().map(RdmsBudgetExeSummaryDto::getOtherFeeBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetExeSummary.setOtherFeeBudgetActive(budgetExeSummary.getOtherFeeBudgetActive().add(sumActiveFeeCharge));

        }
        //3. 所有直属任务工单的实际支出
        List<JobItemStatusEnum>  jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        jobItemStatusList.add(JobItemStatusEnum.HANDLING);
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
        jobItemStatusList.add(JobItemStatusEnum.TESTING);
        jobItemStatusList.add(JobItemStatusEnum.CHA_RECHECK);
        jobItemStatusList.add(JobItemStatusEnum.QUA_RECHECK);
        jobItemStatusList.add(JobItemStatusEnum.EVALUATE);
        jobItemStatusList.add(JobItemStatusEnum.APPROVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);
        // 1. 得到所有任务工单
        List<RdmsJobItemDto> taskJobList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, jobItemStatusList, jobitemTypeList);
        Double sumTaskManhour = 0.0;
        double sumTaskDevManhour = 0.0;
        double sumTaskTestManhour = 0.0;
        BigDecimal materialFee = BigDecimal.ZERO;
        BigDecimal otherFee = BigDecimal.ZERO;

        if(!CollectionUtils.isEmpty(taskJobList)){
            for(RdmsJobItemDto jobItemDto: taskJobList){
                sumTaskManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                sumTaskDevManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                if(! jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())){
                    sumTaskTestManhour += this.calFirstLevelJobItemTestManhour(jobItemDto.getId());
                }
                materialFee = materialFee.add(this.calMaterialFeeByJobitemId(jobItemDto.getId()));
                otherFee = otherFee.add(this.calOtherFeeByJobitemId(jobItemDto.getId()));
            }
        }

        //总的执行中计入任务工单的物料和费用
        budgetExeSummary.setSumBudgetActive(budgetExeSummary.getSumBudgetActive().add(materialFee).add(otherFee));

        if(!CollectionUtils.isEmpty(rdmsManhourStandards)){
            BigDecimal sumTaskCharge = rdmsManhourStandards.get(0).getDevManhourFee().multiply(BigDecimal.valueOf(sumTaskManhour));
            budgetExeSummary.setSumBudgetActive(budgetExeSummary.getSumBudgetActive().add(sumTaskCharge));

            budgetExeSummary.setDevManhourActive(budgetExeSummary.getDevManhourActive() + sumTaskDevManhour);
            budgetExeSummary.setTestManhourActive(budgetExeSummary.getDevManhourActive() + sumTaskTestManhour);
        }
        //计算预算执行率
//        if(Objects.equals(budgetExeSummary.getSumBudgetApproved(), BigDecimal.ZERO)){
        if(budgetExeSummary.getSumBudgetApproved().signum() == 0){
            budgetExeSummary.setBudgetRate(BigDecimal.ZERO);
        }else{
            budgetExeSummary.setBudgetRate(budgetExeSummary.getSumBudgetActive().divide(budgetExeSummary.getSumBudgetApproved(),6,RoundingMode.HALF_UP));
        }
        return budgetExeSummary;
    }

    /**
     * 通过读取经过评审的数据记录进行数据汇总
     *计算子项的预算执行情况  成本包括 1. 下一级子项目 2. 直属功能开发  3. 直属任务工单
     * @param subprojectId
     * @return
     */
    @Transactional
    public RdmsBudgetExeDto getSubprojectBudgetExeSummary(String subprojectId) {
        RdmsBudgetExeDto subprojectBudgetExeSummary = new RdmsBudgetExeDto();
        //1. 得到当前子项目信息
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        subprojectBudgetExeSummary.setId(subprojectId);
        subprojectBudgetExeSummary.setItemName(subproject.getLabel());

        //总的审批预算, 下面还要加上所有"直接子项目"的审批总预算, 一级一级向下统计
        subprojectBudgetExeSummary.setSumBudgetApproved(subproject.getBudget().add(subproject.getAddCharge()));

        //2. 得到所有下级子项目信息
        List<RdmsBudgetExe> childSubprojectBudgetExeInfoList = rdmsBudgetExeService.getItemListByParentAndeDocType(subprojectId, DocTypeEnum.SUB_PROJECT);

        List<RdmsBudgetExeDto> rdmsBudgetExeDtos = CopyUtil.copyList(childSubprojectBudgetExeInfoList, RdmsBudgetExeDto.class);
        subprojectBudgetExeSummary.setChildSubprojectBudgetInfoList(rdmsBudgetExeDtos);

        if(!CollectionUtils.isEmpty(rdmsBudgetExeDtos)){
            BigDecimal sumChildrenApproveBudget = rdmsBudgetExeDtos.stream().map(RdmsBudgetExeDto::getSumBudgetApproved).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setSumBudgetApproved(subprojectBudgetExeSummary.getSumBudgetApproved().add(sumChildrenApproveBudget));
        }

        //3. 得到所有功能开发信息
        List<RdmsBudgetExe> characterBudgetExeInfoList = rdmsBudgetExeService.getItemListByParentAndeDocType(subprojectId, DocTypeEnum.CHARACTER);
        List<RdmsBudgetExeDto> characterBudgetExeDtos = CopyUtil.copyList(characterBudgetExeInfoList, RdmsBudgetExeDto.class);
        subprojectBudgetExeSummary.setCharacterBudgetInfoList(characterBudgetExeDtos);

        //计算实际分项费用支出
        RdmsManhourStandardExample standardExample = new RdmsManhourStandardExample();
        standardExample.createCriteria().andCustomerIdEqualTo(subproject.getCustomerId()).andDeletedEqualTo("0");
        List<RdmsManhourStandard> rdmsManhourStandards = rdmsManhourStandardMapper.selectByExample(standardExample);
        if(rdmsManhourStandards.size()>1){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }

        //计算实际费用支出
        subprojectBudgetExeSummary.setSumBudgetActive(BigDecimal.ZERO);
        subprojectBudgetExeSummary.setDevManhourActive(0.0);
        subprojectBudgetExeSummary.setTestManhourActive(0.0);
        subprojectBudgetExeSummary.setMaterialFeeActive(BigDecimal.ZERO);
        subprojectBudgetExeSummary.setOtherFeeActive(BigDecimal.ZERO);
        //1. 所有子项目的实际支出
        if(!CollectionUtils.isEmpty(subprojectBudgetExeSummary.getChildSubprojectBudgetInfoList())){
            BigDecimal sumChildSubProjectActiveCharge = subprojectBudgetExeSummary.getChildSubprojectBudgetInfoList().stream().map(RdmsBudgetExeDto::getSumBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setSumBudgetActive(sumChildSubProjectActiveCharge);

            Double sumActiveDevManhour = subprojectBudgetExeSummary.getChildSubprojectBudgetInfoList().stream().map(RdmsBudgetExeDto::getDevManhourActive).reduce(0.0, Double::sum);
            subprojectBudgetExeSummary.setDevManhourActive(sumActiveDevManhour);

            Double sumActiveTestManhour = subprojectBudgetExeSummary.getChildSubprojectBudgetInfoList().stream().map(RdmsBudgetExeDto::getTestManhourActive).reduce(0.0, Double::sum);
            subprojectBudgetExeSummary.setTestManhourActive(sumActiveTestManhour);

            BigDecimal sumActiveMaterialCharge = subprojectBudgetExeSummary.getChildSubprojectBudgetInfoList().stream().map(RdmsBudgetExeDto::getMaterialFeeActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setMaterialFeeActive(sumActiveMaterialCharge);

            BigDecimal sumActiveFeeCharge = subprojectBudgetExeSummary.getChildSubprojectBudgetInfoList().stream().map(RdmsBudgetExeDto::getOtherFeeActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setOtherFeeActive(sumActiveFeeCharge);

        }
        //2. 所有直属功能的实际支出
        if(!CollectionUtils.isEmpty(subprojectBudgetExeSummary.getCharacterBudgetInfoList())){
            BigDecimal sumChildCharacterActiveCharge = subprojectBudgetExeSummary.getCharacterBudgetInfoList().stream().map(RdmsBudgetExeDto::getSumBudgetActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setSumBudgetActive(subprojectBudgetExeSummary.getSumBudgetActive().add(sumChildCharacterActiveCharge));

            Double sumActiveDevManhour = subprojectBudgetExeSummary.getCharacterBudgetInfoList().stream().map(RdmsBudgetExeDto::getDevManhourActive).reduce(0.0, Double::sum);
            subprojectBudgetExeSummary.setDevManhourActive(subprojectBudgetExeSummary.getDevManhourActive() + sumActiveDevManhour);

            Double sumActiveTestManhour = subprojectBudgetExeSummary.getCharacterBudgetInfoList().stream().map(RdmsBudgetExeDto::getTestManhourActive).reduce(0.0, Double::sum);
            subprojectBudgetExeSummary.setTestManhourActive(subprojectBudgetExeSummary.getTestManhourActive() + sumActiveTestManhour);

            BigDecimal sumActiveMaterialCharge = subprojectBudgetExeSummary.getCharacterBudgetInfoList().stream().map(RdmsBudgetExeDto::getMaterialFeeActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setMaterialFeeActive(subprojectBudgetExeSummary.getMaterialFeeActive().add(sumActiveMaterialCharge));

            BigDecimal sumActiveFeeCharge = subprojectBudgetExeSummary.getCharacterBudgetInfoList().stream().map(RdmsBudgetExeDto::getOtherFeeActive).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setOtherFeeActive(subprojectBudgetExeSummary.getOtherFeeActive().add(sumActiveFeeCharge));

        }
        //3. 所有直属任务工单的实际支出
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);

        // 1. 得到所有任务工单
        List<RdmsJobItemDto> taskJobList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, jobItemStatusList, jobitemTypeList);
        Double sumTaskManhour = 0.0;
        double sumTaskDevManhour = 0.0;
        double sumTaskTestManhour = 0.0;
        BigDecimal materialFee = BigDecimal.ZERO;
        BigDecimal otherFee = BigDecimal.ZERO;

        if(!CollectionUtils.isEmpty(taskJobList)){
            for(RdmsJobItemDto jobItemDto: taskJobList){
                sumTaskManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                sumTaskDevManhour += this.calFirstLevelJobItemSuitDevManhour(jobItemDto.getId());
                if(! jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())){
                    sumTaskTestManhour += this.calFirstLevelJobItemTestManhour(jobItemDto.getId());
                }
                materialFee = materialFee.add(this.calMaterialFeeByJobitemId(jobItemDto.getId()));
                otherFee = otherFee.add(this.calOtherFeeByJobitemId(jobItemDto.getId()));
            }
        }
        //总的执行中计入任务工单的物料和费用
        subprojectBudgetExeSummary.setSumBudgetActive(subprojectBudgetExeSummary.getSumBudgetActive().add(materialFee).add(otherFee));

        if(!CollectionUtils.isEmpty(rdmsManhourStandards)){
            BigDecimal sumTaskCharge = rdmsManhourStandards.get(0).getDevManhourFee().multiply(BigDecimal.valueOf(sumTaskManhour));
            subprojectBudgetExeSummary.setSumBudgetActive(subprojectBudgetExeSummary.getSumBudgetActive().add(sumTaskCharge));

            subprojectBudgetExeSummary.setDevManhourActive(subprojectBudgetExeSummary.getDevManhourActive() + sumTaskDevManhour);
            subprojectBudgetExeSummary.setTestManhourActive(subprojectBudgetExeSummary.getDevManhourActive() + sumTaskTestManhour);
        }


        // 添加直接预算部分的使用数据
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary(subprojectId);
        subprojectBudgetExeSummary.setSumBudgetActive(subprojectBudgetExeSummary.getSumBudgetActive()
                .add(budgetExecutionSummary.getSumActiveBudget()));

        subprojectBudgetExeSummary.setDevManhourActive(subprojectBudgetExeSummary.getDevManhourActive());

        subprojectBudgetExeSummary.setTestManhourActive(subprojectBudgetExeSummary.getTestManhourActive());

        subprojectBudgetExeSummary.setMaterialFeeActive(subprojectBudgetExeSummary.getMaterialFeeActive()
                .add(budgetExecutionSummary.getSumActiveMaterialBudget()));

        subprojectBudgetExeSummary.setOtherFeeActive(subprojectBudgetExeSummary.getOtherFeeActive()
                .add(budgetExecutionSummary.getSumActiveOtherBudget()));

        //TODO 缺少对设备费用和工时费用的显示

        //计算预算执行率
//        if(Objects.equals(subprojectBudgetExeSummary.getSumBudgetApproved(), BigDecimal.ZERO)){
        if(subprojectBudgetExeSummary.getSumBudgetApproved().signum() == 0){
            subprojectBudgetExeSummary.setBudgetRate(BigDecimal.ZERO);
        }else{
            subprojectBudgetExeSummary.setBudgetRate(subprojectBudgetExeSummary.getSumBudgetActive().divide(subprojectBudgetExeSummary.getSumBudgetApproved(),6,RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
        }

        return subprojectBudgetExeSummary;
    }

    /**
     * 通过读取 预算执行表 获得 预算执行数据
     * @param subprojectId
     * @return
     */
    @Transactional
    public RdmsBudgetExeDto getSubprojectBudgetExeSummary_researchExe(String subprojectId) {
        RdmsBudgetExeDto subprojectBudgetExeSummary = new RdmsBudgetExeDto();
        //1. 得到当前子项目信息
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        if(ObjectUtils.isEmpty(subproject)){
            return subprojectBudgetExeSummary;
        }
        subprojectBudgetExeSummary.setId(subprojectId);
        subprojectBudgetExeSummary.setItemName(subproject.getLabel());

        //总的审批预算, 下面还要加上所有"直接子项目"的审批总预算, 一级一级向下统计
        subprojectBudgetExeSummary.setSumBudgetApproved(subproject.getBudget().add(subproject.getAddCharge()));

        //2. 得到所有下级子项目信息
        List<RdmsBudgetExeDto> rdmsBudgetExeDtos = new ArrayList<>();
        List<RdmsProjectSubproject> subprojectListByParentId = rdmsSubprojectService.getSubprojectListByParentId(subprojectId);
        if(!CollectionUtils.isEmpty(subprojectListByParentId)){
            for(RdmsProjectSubproject rdmsSubproject: subprojectListByParentId){
                RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary(rdmsSubproject.getId());

                RdmsBudgetExeDto budgetExe = new RdmsBudgetExeDto();
                budgetExe.setItemId(budgetExecutionSummary.getSubprojectId());
                budgetExe.setItemName(budgetExecutionSummary.getSubprojectName());
                budgetExe.setDocType(DocTypeEnum.SUB_PROJECT.getType());
                budgetExe.setSumManhourBudget(budgetExecutionSummary.getSumManhourBudget());
                budgetExe.setMaterialFeeApproved(budgetExecutionSummary.getSumMaterialBudget());
                budgetExe.setOtherFeeApproved(budgetExecutionSummary.getSumOtherBudget());
                budgetExe.setSumBudgetApproved(budgetExecutionSummary.getSumBudget());

                budgetExe.setSumActiveManhourBudget(budgetExecutionSummary.getSumActiveManhourBudget());
                budgetExe.setMaterialFeeActive(budgetExecutionSummary.getSumActiveMaterialBudget());
                budgetExe.setOtherFeeActive(budgetExecutionSummary.getSumActiveOtherBudget());
                budgetExe.setSumBudgetActive(budgetExecutionSummary.getSumActiveBudget());

                if(budgetExe.getSumBudgetApproved().doubleValue() != 0.0){
                    budgetExe.setBudgetRate(budgetExe.getSumBudgetActive().divide(budgetExe.getSumBudgetApproved(), 4, RoundingMode.HALF_UP));
                }else{
                    budgetExe.setBudgetRate(BigDecimal.ZERO);
                }
                rdmsBudgetExeDtos.add(budgetExe);
            }
        }
        subprojectBudgetExeSummary.setChildSubprojectBudgetInfoList(rdmsBudgetExeDtos);

        if(!CollectionUtils.isEmpty(rdmsBudgetExeDtos)){
            BigDecimal sumChildrenApproveBudget = rdmsBudgetExeDtos.stream().map(RdmsBudgetExeDto::getSumBudgetApproved).reduce(BigDecimal.ZERO, BigDecimal::add);
            subprojectBudgetExeSummary.setSumBudgetApproved(subprojectBudgetExeSummary.getSumBudgetApproved().add(sumChildrenApproveBudget));
        }

        //3. 得到所有功能开发信息
        List<RdmsBudgetExe> characterBudgetExeInfoList = rdmsBudgetExeService.getItemListByParentAndeDocType(subprojectId, DocTypeEnum.CHARACTER);
        List<RdmsBudgetExeDto> characterBudgetExeDtos = CopyUtil.copyList(characterBudgetExeInfoList, RdmsBudgetExeDto.class);
        subprojectBudgetExeSummary.setCharacterBudgetInfoList(characterBudgetExeDtos);

        return subprojectBudgetExeSummary;
    }

    @Transactional
    public RdmsBudgetSummaryDto getCharacterBudgetExeSummary(String characterId){
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        BigDecimal standDevManhourFee = this.getStandManhourFee(character.getCustomerId(), OperateTypeEnum.DEVELOP);
        BigDecimal standTestManhourFee = this.getStandManhourFee(character.getCustomerId(), OperateTypeEnum.TEST);

        RdmsBudgetSummaryDto budgetSummaryDto = new RdmsBudgetSummaryDto();
        budgetSummaryDto.setStandDevManhourFee(standDevManhourFee);
        budgetSummaryDto.setStandTestManhourFee(standTestManhourFee);
        budgetSummaryDto.setBudget(character.getBudget());
        budgetSummaryDto.setTestManhourFeeApproved(BigDecimal.valueOf(character.getTestManhourApproved()).multiply(standTestManhourFee));
        budgetSummaryDto.setDevManhourFeeApproved(BigDecimal.valueOf(character.getDevManhourApproved()).multiply(standDevManhourFee));
        budgetSummaryDto.setMaterialFeeApproved(character.getMaterialFeeApproved());
        budgetSummaryDto.setOtherFeeApproved(character.getSumOtherFee());

        //Double 数据保留两位小数 format
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(6);
        nf.setRoundingMode(RoundingMode.HALF_UP);

        //剩余开发费
        BigDecimal characterRemainDevManhourFee = this.getCharacterRemainDevManhourFee(characterId);
        budgetSummaryDto.setDevManhourFeeRemain(characterRemainDevManhourFee);

        //剩余测试费
        BigDecimal characterRemainTestManhourFee = this.getCharacterRemainTestManhourFee(characterId);
        budgetSummaryDto.setTestManhourFeeRemain(characterRemainTestManhourFee);

        //物料统计
        List<RdmsMaterialManageDto> materialList = rdmsMaterialManageService.getHaveApprovedListByCharacterId(characterId);
        if(! CollectionUtils.isEmpty(materialList)){
            List<RdmsMaterialManageDto> expenditureMaterialManageDtos = materialList.stream().filter(item -> item.getUsageMode().equals(MaterialUsageModeEnum.EXPENDITURE.getStatus())).collect(Collectors.toList());
            if(! CollectionUtils.isEmpty(expenditureMaterialManageDtos)){
                BigDecimal sumAccountCost = expenditureMaterialManageDtos.stream().map(RdmsMaterialManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
                budgetSummaryDto.setMaterialFeeRemain(budgetSummaryDto.getMaterialFeeApproved().subtract(sumAccountCost));
            }else {
                budgetSummaryDto.setMaterialFeeRemain(budgetSummaryDto.getMaterialFeeApproved());
            }
        }else{
            budgetSummaryDto.setMaterialFeeRemain(budgetSummaryDto.getMaterialFeeApproved());
        }

        //其他费用统计
        List<RdmsFeeManageDto> haveApprovedFeeList = rdmsFeeManageService.getHaveApprovedListByCharacterId(characterId);
        if(! CollectionUtils.isEmpty(haveApprovedFeeList)){
            BigDecimal sumAccountCost = haveApprovedFeeList.stream().map(RdmsFeeManageDto::getAccountCost).reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetSummaryDto.setOtherFeeRemain(budgetSummaryDto.getOtherFeeApproved().subtract(sumAccountCost));
        }else{
            budgetSummaryDto.setOtherFeeRemain(budgetSummaryDto.getOtherFeeApproved());
        }

        //总剩余
        budgetSummaryDto.setRemainBudget(budgetSummaryDto.getDevManhourFeeRemain().add(budgetSummaryDto.getTestManhourFeeRemain()).add(budgetSummaryDto.getMaterialFeeRemain()).add(budgetSummaryDto.getOtherFeeRemain()));

        return budgetSummaryDto;
    }

    /**
     * 保存
     */
    public BigDecimal getStandManhourFee(String customerId, OperateTypeEnum operateType){
        RdmsManhourStandard standardManhour = this.getStandardManhourByCustomerId(customerId);
        if(operateType.getType().equals(OperateTypeEnum.DEVELOP.getType())){
            return standardManhour.getDevManhourFee();

        }else{
            return standardManhour.getTestManhourFee();
        }
    }

    /**
     * 保存
     */
    public String save(RdmsManhourStandard manhour) {
        //查看机构是否存在
        RdmsManhourStandard standardManhourByCustomerId = this.getStandardManhourByCustomerId(manhour.getCustomerId());
        if(!ObjectUtils.isEmpty(standardManhourByCustomerId)){
            manhour.setId(standardManhourByCustomerId.getId());
            manhour.setCreateTime(new Date());
            return this.update(manhour);
        }else{
            return this.insert(manhour);
        }
    }

    private String insert(RdmsManhourStandard manhour) {
        if(org.springframework.util.ObjectUtils.isEmpty(manhour.getId())){  //当前端页面给出projectID时,将不为空
            manhour.setId(UuidUtil.getShortUuid());
        }
        RdmsManhourStandard rdmsManhourStandard = this.selectByPrimaryKey(manhour.getId());
        if(ObjectUtils.isEmpty(rdmsManhourStandard)){
            manhour.setDeleted("0");
            manhour.setCreateTime(new Date());
            rdmsManhourStandardMapper.insert(manhour);
            return manhour.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsManhourStandard manhour) {
        if(ObjectUtils.isEmpty(manhour.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsManhourStandard rdmsManhourStandard = this.selectByPrimaryKey(manhour.getId());
        if(ObjectUtils.isEmpty(rdmsManhourStandard)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            manhour.setDeleted("0");
            rdmsManhourStandardMapper.updateByPrimaryKey(manhour);
            return manhour.getId();
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsManhourStandard rdmsManhourStandard = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsManhourStandard)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsManhourStandard.setDeleted("1");
            rdmsManhourStandardMapper.updateByPrimaryKey(rdmsManhourStandard);
        }
    }

}
