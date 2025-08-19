/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.calculate;

import com.course.server.domain.*;
import com.course.server.dto.rdms.RdmsCharacterDto;
import com.course.server.dto.rdms.RdmsHmiWorkLoadCellDto;
import com.course.server.dto.rdms.RdmsJobItemDto;
import com.course.server.enums.rdms.JobItemTypeEnum;
import com.course.server.enums.rdms.OperateTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalManhourService {

    private static final Logger LOG = LoggerFactory.getLogger(CalManhourService.class);

    @Resource
    private RdmsJobItemService rdmsJobItemService;

    @Resource
    private CalManhourService calManhourService;

    @Resource
    private RdmsCharacterService rdmsCharacterService;

    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;

    @Resource
    private RdmsCustomerUserJobLevelService rdmsCustomerUserJobLevelService;

    @Resource
    private RdmsManhourService rdmsManhourService;

    /*
        工单及时率 = (1 - 逾期工单/全部工单数) * 100%
        ???工单开发效率 = 工单计划执行周期 / 工单实际周期

        //TODO
            对于一个人:
                1. 获得所有未完成的工单, 每个未完成的工单将工时根据执行周期,均匀分布在每一天(逾期的工单也一样处理), 所有工单数据叠加, 就是未来的工作量分布图
                2. 获得查看区间(比如追溯一个月,或一个季度)内所有已经完成的工单, 将工时均匀分布在从开始到结束的每一天, 所有工单数据叠加, 就是过去一段时间的工作效率图.

        工单成本 = 标准工时费 * 工单工时;

        对于一个功能来讲
            总工单工时 = 所有工单的工时之和;
            工时执行率 = 总工单工时/审批工时 * 100%

            实际费用 = 所有工单的费用和;
            预算执行率 = 实际费用/预算总额 * 100%

            开发效率 = 实际周期/计划周期  * 100%

        项目开发效率 = 求和(功能实际周期) / 求和(功能计划周期) * 100%
     */

    /*注意: 页面上显示工时时, 如果没有选择人员, 则显示标准工时; 如果选择人员, 则显示根据相应人员折算后的工时*/
    /*注意: 单中写的都是标准工时*/


    /*
    * 项目成本构成:
    *   1. 功能成本
    *       1.1 测试工单成本
    *       1.2 开发工单成本
    *       1.3 集成工单成本
    *           1.3.1 协作工单成本
    *   2. 任务成本
    *       2.1写作工单成本
    *   3. 功能分解
    *   4. 功能修订
    * */


    /**
     * 计算一个"未完成的"工单的工作量分布
     * 根据工单的计划提交时间和法定工时，计算每天的工作量分布
     * 
     * @param jobitemId 工单ID
     * @param onDutyTime 上班时间
     * @param offDutyTime 下班时间
     * @return 返回工作量分布列表
     * @throws ParseException 日期解析异常
     */
    public List<RdmsHmiWorkLoadCellDto> getOneJobitemWorkloadList(String jobitemId, Date onDutyTime, Date offDutyTime) throws ParseException {
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        //防止未指定提交时间的工单发生错误
        if(ObjectUtils.isEmpty(rdmsJobItem.getPlanSubmissionTime())){
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 5);
            Date time = calendar.getTime();
            rdmsJobItem.setPlanSubmissionTime(time);
        }
        //获得工单的法定工时
        RspWorkHour rspWorkHour = calWorkHoursInLaw(rdmsJobItem.getCreateTime(), rdmsJobItem.getPlanSubmissionTime(), onDutyTime, offDutyTime);

        //计算每个法定工作工时的工单工时分摊
        double loadCell = rdmsJobItem.getManhour() / rspWorkHour.sumWorkHours;

        List<RdmsHmiWorkLoadCellDto> loadCellDtos = new ArrayList<>();
        List<DayHour> dayHourList = rspWorkHour.dayHourList;
        for(DayHour dayHour : dayHourList){
            RdmsHmiWorkLoadCellDto loadCellDto = new RdmsHmiWorkLoadCellDto();
            loadCellDto.setWorkerId(rdmsJobItem.getExecutorId());
            loadCellDto.setJobitemId(jobitemId);
            loadCellDto.setDateStr(dayHour.day);
            loadCellDto.setLoadCell(loadCell * dayHour.hour);
            loadCellDtos.add(loadCellDto);
        }

        return loadCellDtos;
    }

    /**
     * 计算一个工单的工作量分布（重载方法）
     * 根据工单DTO对象计算工作量分布，支持预处理计算
     * 
     * @param jobItemDto 工单DTO对象
     * @param onDutyTime 上班时间
     * @param offDutyTime 下班时间
     * @return 返回工作量分布列表
     * @throws ParseException 日期解析异常
     */
    public List<RdmsHmiWorkLoadCellDto> getOneJobitemWorkloadList(RdmsJobItemDto jobItemDto, Date onDutyTime, Date offDutyTime) throws ParseException {
        //如果进行计算的jobitem并不存在于数据库中,说明是:在保存前,进行预处理计算的jobitem
        RdmsJobItem tempJobItem = rdmsJobItemService.selectByPrimaryKey(jobItemDto.getId());
        //计算本条工单的绩效工时
        double performanceManhour = 0.0;
        if(!ObjectUtils.isEmpty(tempJobItem)){
            double performanceManhour_stand = rdmsManhourService.calJobItemPerformanceManhour(jobItemDto.getId());
            performanceManhour = rdmsManhourService.transformToWorkerManhour(performanceManhour_stand, jobItemDto.getExecutorId(), jobItemDto.getCustomerId(), OperateTypeEnum.DEVELOP);
        }else{
            performanceManhour = jobItemDto.getManhour();
        }

        // CalManhourService.java#L128
        performanceManhour = Double.parseDouble(String.format("%.2f", performanceManhour));
        if(performanceManhour < 0){ //避免出现负数
            performanceManhour =0;
        }
        //对工单中包含的工时进行调整
        jobItemDto.setManhour(performanceManhour);
        RdmsJobItem rdmsJobItem = CopyUtil.copy(jobItemDto, RdmsJobItem.class);
        //防止未指定提交时间的工单发生错误
        if(ObjectUtils.isEmpty(rdmsJobItem.getPlanSubmissionTime())){
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 5);
            Date time = calendar.getTime();
            rdmsJobItem.setPlanSubmissionTime(time);
        }
        //获得工单的法定工时
        RspWorkHour rspWorkHour = calWorkHoursInLaw(rdmsJobItem.getCreateTime(), rdmsJobItem.getPlanSubmissionTime(), onDutyTime, offDutyTime);

        //计算每个法定工作工时的工单工时分摊
        double loadCell = rdmsJobItem.getManhour() / rspWorkHour.sumWorkHours;

        List<RdmsHmiWorkLoadCellDto> loadCellDtos = new ArrayList<>();
        List<DayHour> dayHourList = rspWorkHour.dayHourList;
        for(DayHour dayHour : dayHourList){
            RdmsHmiWorkLoadCellDto loadCellDto = new RdmsHmiWorkLoadCellDto();
            loadCellDto.setWorkerId(rdmsJobItem.getExecutorId());
            loadCellDto.setJobitemId(rdmsJobItem.getId());
            loadCellDto.setDateStr(dayHour.day);
            loadCellDto.setLoadCell(loadCell * dayHour.hour);
            loadCellDtos.add(loadCellDto);
        }

        return loadCellDtos;
    }

    public static class ResultRate{
        private int numerator; //分子
        private int denominator; //分母
        private double rate;  //率

        /**
         * 构造比率结果对象
         * 创建包含分子、分母和比率的比率结果实例
         *
         * @param numerator 分子
         * @param denominator 分母
         * @param rate 比率
         */
        ResultRate( int numerator, int denominator, double rate){
            this.numerator = numerator;
            this.denominator = denominator;
            this.rate = rate;
        }
    }


    /**
     * 功能开发工单 及时率 = (1 - 逾期工单/功能开发总工单数) * 100%
     * 计算指定功能开发工单的及时率，包括测试、开发和集成工单
     * 
     * @param characterId 功能ID
     * @return 返回及时率结果对象
     */
    public ResultRate calInTimeOfCharacterJobitem(String characterId){
        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TEST);
        typeList.add(JobItemTypeEnum.DEVELOP);
        typeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndStatusTypeList(characterId, null, typeList);
        int overTimeJobNum = 0;
        for(RdmsJobItemDto jobItemDto: jobitemList){
            if(jobItemDto.getActualSubmissionTime()==null || !ObjectUtils.isEmpty(jobItemDto.getActualSubmissionTime())){
                Date currentTime = new Date();
                if(currentTime.getTime() > jobItemDto.getPlanSubmissionTime().getTime()){
                    overTimeJobNum ++;
                }
            }else{
                if(jobItemDto.getActualSubmissionTime().getTime() > jobItemDto.getActualSubmissionTime().getTime()){
                    overTimeJobNum ++;
                }
            }
        }
        if(jobitemList.isEmpty()){
            return new ResultRate(0, 0, 0);
        }else{
            ResultRate resultRate = new ResultRate(overTimeJobNum, jobitemList.size(), (1 - ((double) overTimeJobNum / jobitemList.size())));
            return resultRate;
        }
    }

    /**
     * 项目开发工单 及时率 = (1 - 逾期工单/项目开发总工单数) * 100%
     * 计算指定子项目开发工单的及时率，包括所有类型的开发工单
     * 
     * @param subprojectId 子项目ID
     * @return 返回及时率结果对象
     */
    public ResultRate calInTimeOfSubProjectJobitem(String subprojectId){
        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TEST);
        typeList.add(JobItemTypeEnum.DEVELOP);
        typeList.add(JobItemTypeEnum.CHARACTER_INT);
        typeList.add(JobItemTypeEnum.TASK_SUBP);
        typeList.add(JobItemTypeEnum.ITERATION);
        typeList.add(JobItemTypeEnum.DECOMPOSE);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatus(subprojectId, null, typeList);
        int overTimeJobNum = 0;
        for(RdmsJobItemDto jobItemDto: jobitemList){
            //要求ActualSubmissionTime 必须是有值的, 值为空意味着还没有提交
            if(jobItemDto.getActualSubmissionTime()==null || !ObjectUtils.isEmpty(jobItemDto.getActualSubmissionTime())){
                Date currentTime = new Date();
                if(currentTime.getTime() > jobItemDto.getPlanSubmissionTime().getTime()){
                    overTimeJobNum ++;
                }
            }else{
                if(jobItemDto.getActualSubmissionTime().getTime() > jobItemDto.getActualSubmissionTime().getTime()){
                    overTimeJobNum ++;
                }
            }
        }
        if(jobitemList.isEmpty()){
            return new ResultRate(0, 0, 0);
        }else{
            ResultRate resultRate = new ResultRate(overTimeJobNum, jobitemList.size(), (1 - ((double) overTimeJobNum / jobitemList.size())));
            return resultRate;
        }
    }

    /**
     * 判断工单是否逾期
     * 比较工单的实际提交时间和计划提交时间，判断是否超过计划时间
     * 
     * @param jobitemId 工单ID
     * @return 返回true表示逾期，false表示未逾期
     */
    public boolean isJobOvertime(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        long planTime = rdmsJobItem.getPlanSubmissionTime().getTime();
        long activeTime = rdmsJobItem.getActualSubmissionTime().getTime();
        if(activeTime > planTime){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 工单成本 = 标准工时成本 * 工单工时
     * 功能开发工单 和 协作工单
     * 
     * @param jobitemId 工单ID
     * @return 返回工单成本
     */
    public double calDevJobitemCost(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(!(rdmsJobItem.getType().equals(JobItemTypeEnum.DEVELOP.getType()) || rdmsJobItem.getType().equals(JobItemTypeEnum.ASSIST.getType()))){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        return standardManhour.getDevManhourFee().doubleValue() * rdmsJobItem.getManhour();
    }

    /**
     * 工单成本 = 标准工时成本 * 工单工时
     * 测试工单
     * 
     * @param jobitemId 工单ID
     * @return 返回工单成本
     */
    public double calTestJobitemCost(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(!rdmsJobItem.getType().equals(JobItemTypeEnum.TEST.getType())){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        return standardManhour.getTestManhourFee().doubleValue() * rdmsJobItem.getManhour();
    }

    /**
     * 计算parent工单下发总 "标准"工时
     * 集成工单
     * 
     * @param jobitemId 工单ID
     * @return 返回下发工单的总工时
     */
    public double calParentJobIssueSumManhour(String jobitemId){
        List<RdmsJobItemDto> jobitemByParent = rdmsJobItemService.getJobitemByParentJobitemId(jobitemId, JobItemTypeEnum.ASSIST);
        double sumCost = 0.0;
        if(CollectionUtils.isEmpty(jobitemByParent)){
            return 0.0;
        }else{
            return jobitemByParent.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
        }
    }

    /**
     * 工单成本 = 标准工时成本 * 工单工时
     * 集成工单
     * 
     * @param jobitemId 工单ID
     * @return 返回工单成本
     */
    public double calCharacterIntJobitemCost(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(!rdmsJobItem.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        //判断下发工时是否大于集成工单任务工时, 所有子工单总工时计算集成工单成本; 否则用集成工单工时计算成本
        double issueSumManhour = calParentJobIssueSumManhour(jobitemId);
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        if(issueSumManhour > rdmsJobItem.getManhour()){
            return standardManhour.getDevManhourFee().doubleValue() * issueSumManhour;
        }else {
            return standardManhour.getDevManhourFee().doubleValue() * rdmsJobItem.getManhour();
        }
    }

    /**
     * 工单成本 = 标准工时成本 * 工单工时
     * 任务工单
     * 
     * @param jobitemId 工单ID
     * @return 返回工单成本
     */
    public double calTaskJobitemCost(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(!(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK.getType()) || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType()))){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        //判断下发工时是否大于集成工单任务工时, 所有子工单总工时计算集成工单成本; 否则用集成工单工时计算成本
        double issueSumManhour = calParentJobIssueSumManhour(jobitemId);
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        if(issueSumManhour > rdmsJobItem.getManhour()){
            return standardManhour.getDevManhourFee().doubleValue() * issueSumManhour;
        }else {
            return standardManhour.getDevManhourFee().doubleValue() * rdmsJobItem.getManhour();
        }
    }

    /**
     * 工单成本 = 标准工时成本 * 工单工时
     * 分解工单 和  修订工单
     * 
     * @param jobitemId 工单ID
     * @return 返回工单成本
     */
    public double calDecomposeOrIterationJobitemCost(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        if(!(rdmsJobItem.getType().equals(JobItemTypeEnum.ITERATION.getType()) || rdmsJobItem.getType().equals(JobItemTypeEnum.DECOMPOSE.getType()))){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        return standardManhour.getDevManhourFee().doubleValue() * rdmsJobItem.getManhour();
    }

    /**
     * 工单成本 = 标准工时成本 * 工单工时
     * 计算所有child工单的总成本
     * 
     * @param jobitemId 工单ID
     * @return 返回所有子工单的总成本
     */
    public double calChildrenJobitemsCost(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        //所有下发子工单的总工时
        double issueSumManhour = calParentJobIssueSumManhour(jobitemId);
        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(rdmsJobItem.getCustomerId());
        return standardManhour.getDevManhourFee().doubleValue() * issueSumManhour;
    }

    /**
     * 功能成本 = 所有工单成本之和
     * 计算指定功能的所有工单成本总和，包括开发、测试和集成工单
     * 
     * @param characterId 功能ID
     * @return 返回功能总成本
     */
    public double calCharacterCost(String characterId){
        //获得对应的Character
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        double sumCost = 0.0;
        //计算所有开发工单的成本
        List<RdmsJobItem> devJobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndJobType(characterId, JobItemTypeEnum.DEVELOP.getType());
        if(! CollectionUtils.isEmpty(devJobitemList)){
            for(RdmsJobItem jobItem: devJobitemList){
                double devCost = this.calDevJobitemCost(jobItem.getId());
                sumCost += devCost;
            }
        }
        List<RdmsJobItem> testJobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndJobType(characterId, JobItemTypeEnum.TEST.getType());
        if(! CollectionUtils.isEmpty(testJobitemList)){
            for(RdmsJobItem jobItem: testJobitemList){
                double testCost = this.calDevJobitemCost(jobItem.getId());
                sumCost += testCost;
            }
        }
        List<RdmsJobItem> intJobitemList = rdmsJobItemService.getJobitemListByCharacterIdAndJobType(characterId, JobItemTypeEnum.CHARACTER_INT.getType());
        if(! CollectionUtils.isEmpty(intJobitemList)){
            for(RdmsJobItem jobItem: intJobitemList){
                double intCost = this.calDevJobitemCost(jobItem.getId());
                sumCost += intCost;
            }
        }
        return sumCost;
    }

    /**
     * 子项目成本 = 所有功能成本 + 所有任务成本 + 所有功能分解成本 + 所有功能修订成本
     * 计算指定子项目的总成本，包括所有相关工单的成本
     * 
     * @param subprojectId 子项目ID
     * @return 返回子项目总成本
     */
    public double calSubProjectCost(String subprojectId){
        //功能开发成本
        double sumCost = 0.0;
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(subprojectId, null);
        for(RdmsCharacterDto characterDto : characterList){
            double characterCost = this.calCharacterCost(characterDto.getId());
            sumCost += characterCost;
        }

        //任务工单成本
        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TASK_SUBP);
        List<RdmsJobItemDto> listTypeList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, null, typeEnumList);
        for(RdmsJobItemDto jobItemDto : listTypeList){
            double taskJobitemCost = this.calTaskJobitemCost(jobItemDto.getId());
            sumCost += taskJobitemCost;
        }

        //所有功能分解/修订工单成本
        List<JobItemTypeEnum> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.DECOMPOSE);
        typeList.add(JobItemTypeEnum.ITERATION);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubProjectIdAndStatus(subprojectId, null, typeList);
        for(RdmsJobItemDto jobItemDto : jobitemList){
            double decomJobitemCost = this.calDecomposeOrIterationJobitemCost(jobItemDto.getId());
            sumCost += decomJobitemCost;
        }


        return sumCost;
    }



    public static class DutyTime{
        public Date onDutyTime;
        public Date offDutyTime;

        /**
         * 构造上下班时间对象
         * 创建包含上班和下班时间的DutyTime实例
         *
         * @param onDutyTime 上班时间
         * @param offDutyTime 下班时间
         */
        public DutyTime(Date onDutyTime, Date offDutyTime ) {
            this.onDutyTime = onDutyTime;
            this.offDutyTime = offDutyTime;
        }
    }

    /**
     * 工单开发效率 = 工单计划执行周期 / 工单实际周期
     * 计算工单的开发效率比率，基于计划工时和实际工时的比较
     * 
     * @param jobitemId 工单ID
     * @param dutyTime 上下班时间配置
     * @return 返回开发效率比率
     * @throws ParseException 日期解析异常
     */
    public double calJobitemEfficiencyRate(String jobitemId, DutyTime dutyTime) throws ParseException {
        //查出相应的工单
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        Date actualSubmissionTime = rdmsJobItem.getActualSubmissionTime();
        Date planSubmissionTime = rdmsJobItem.getPlanSubmissionTime();
        Date createTime = rdmsJobItem.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(dutyTime == null || ObjectUtils.isEmpty(dutyTime)){
            dutyTime = new DutyTime(sdf.parse("2023-11-27 09:00:00"), sdf.parse("2023-11-27 17:00:00"));
        }
        //计算计划周期  从工单下发时间到计划提交时间, 每天按8小时计算
        RspWorkHour rspWorkHour = calManhourService.calWorkHoursInLaw(createTime, planSubmissionTime, dutyTime.onDutyTime, dutyTime.offDutyTime);

        //计算计划周期  从工单下发时间到计划提交时间, 每天按8小时计算
        RspWorkHour rspWorkHour1 = calManhourService.calWorkHoursInLaw(createTime, actualSubmissionTime, dutyTime.onDutyTime, dutyTime.offDutyTime);

        double efficiency =  rspWorkHour.sumWorkHours / rspWorkHour1.sumWorkHours;
        return efficiency;
    }

    /**
     * 计算一个功能的总工单工时
     * 统计指定功能下所有工单的工时总和
     * 
     * @param characterId 功能ID
     * @return 返回功能总工时
     */
    public double calCharacterAllJobitemManhour(String characterId){
        //查出相应的工单, 把manhour加一起就行了
        List<RdmsJobItemDto> jobitemsByCharacterId = rdmsJobItemService.getJobitemsByCharacterId(characterId);
        double sumPlanManhour = jobitemsByCharacterId.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
        return sumPlanManhour;
    }

    /**
     * 工时执行率 = 总工单工时/审批工时
     * 计算功能的工时执行率，反映实际工时与审批工时的比例
     * 
     * @param characterId 功能ID
     * @return 返回工时执行率
     */
    public double calCharacterManhourExeRate(String characterId){
        double sumJobHour = this.calCharacterAllJobitemManhour(characterId);
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        return sumJobHour / (rdmsCharacter.getDevManhourApproved() + rdmsCharacter.getTestManhourApproved());
    }

    /**
     * 计算一条功能的剩余开发工时
     * 统计功能下所有开发工单的工时总和
     * 
     * @param characterId 功能ID
     * @return 返回剩余开发工时
     */
    public double calCharacterLeftDevManhour(String characterId){
        List<RdmsJobItemDto> devJobitemsByCharacterId = rdmsJobItemService.getDevJobitemsByCharacterId(characterId);
        double sumDevManhour = devJobitemsByCharacterId.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
        return sumDevManhour;
    }

    /**
     * 计算一条功能的剩余测试工时
     * 统计功能下所有测试工单的工时总和
     * 
     * @param characterId 功能ID
     * @return 返回剩余测试工时
     */
    public double calCharacterLeftTestManhour(String characterId){
        List<RdmsJobItemDto> testJobitemsByCharacterId = rdmsJobItemService.getTestJobitemsByCharacterId(characterId);
        double sumTestManhour = testJobitemsByCharacterId.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
        return sumTestManhour;
    }

    /**
     * 计算一个功能的工时效率
     * 计算功能的工时效率指标（待实现）
     * 
     * @param jobitemId 工单ID
     * @return 返回工时效率（当前返回0.0）
     */
    public double calCharacterEfficiencyRate(String jobitemId){
        //查出相应的工单

        //查出相应公司的标准工时费率

        return 0.0;
    }

    /**
     * 计算一个功能的工单及时率
     * 计算功能的工单及时率指标（待实现）
     * 
     * @param jobitemId 工单ID
     * @return 返回工单及时率（当前返回0.0）
     */
    public double calCharacterInTimeRate(String jobitemId){
        //查出相应的工单

        //查出相应公司的标准工时费率

        return 0.0;
    }

    /**
     * 计算一个功能的工单逾期率
     * 计算功能的工单逾期率指标（待实现）
     * 
     * @param jobitemId 工单ID
     * @return 返回工单逾期率（当前返回0.0）
     */
    public double calCharacterOverdueRate(String jobitemId){
        //查出相应的工单

        //查出相应公司的标准工时费率

        return 0.0;
    }

    /**
     * 计算一个子项目的工时效率
     * 计算子项目的工时效率指标（待实现）
     * 
     * @param jobitemId 工单ID
     * @return 返回工时效率（当前返回0.0）
     */
    public double calSubprojectEfficiencyRate(String jobitemId){
        //查出相应的工单

        //查出相应公司的标准工时费率

        return 0.0;
    }

    /**
     * 计算一个子项目的工单及时率
     * 计算子项目的工单及时率指标（待实现）
     * 
     * @param jobitemId 工单ID
     * @return 返回工单及时率（当前返回0.0）
     */
    public double calSubprojectInTimeRate(String jobitemId){
        //查出相应的工单

        //查出相应公司的标准工时费率

        return 0.0;
    }

    /**
     * 计算一个子项目的工单逾期率
     * 计算子项目的工单逾期率指标（待实现）
     * 
     * @param jobitemId 工单ID
     * @return 返回工单逾期率（当前返回0.0）
     */
    public double calSubprojectOverdueRate(String jobitemId){
        //查出相应的工单

        //查出相应公司的标准工时费率

        return 0.0;
    }

    /**
     * 输入起止时间,计算法定工时
     * 根据工单开始和结束时间，结合上下班时间配置，计算法定工作工时
     * 
     * @param jobStartTime  工单开始时间
     * @param jobEndTime 工单计划完成时间
     * @param onDutyTime "当天" 的上班时间  日期无效, 时间有效
     * @param offDutyTime "当天" 的下班时间 日期无效, 时间有效
     * @return 返回法定工时计算结果
     * @throws ParseException 日期解析异常
     */
    public RspWorkHour calWorkHoursInLaw(Date jobStartTime, Date jobEndTime, Date onDutyTime, Date offDutyTime ) throws ParseException {

        CalDateUtils.DutyTimeResult startDayDutyTime = CalDateUtils.getDutyTime(jobStartTime, onDutyTime, offDutyTime);
        CalDateUtils.DutyTimeResult endDayDutyTime = CalDateUtils.getDutyTime(jobEndTime, onDutyTime, offDutyTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = sdf.format(jobStartTime);
        String startAdjust = startStr;
        String endStr = sdf.format(jobEndTime);
        String endAdjust = endStr;

        //处理起始时间是周末的情况
        if(CalDateUtils.isWeekend(startStr)){
            startAdjust = CalDateUtils.getNextWorkDay(startStr, onDutyTime);
        }
        if(CalDateUtils.isWeekend(endStr)){
            endAdjust = CalDateUtils.getPrevWorkDay(endStr, offDutyTime);
        }

        long firstDayWorkHour = 0L;
        long endDayWorkHour = 0L;
        DayHour firstDayHour = new DayHour();
        DayHour endDayHour = new DayHour();
        //对开始日期进行调整, 并计算开始当日的工作时间
        if(! CalDateUtils.isWeekend(startStr)){
            if(jobStartTime.getTime() < startDayDutyTime.onDutyTime.getTime()){  //如果是上班时间之前发的工单
                startAdjust = sdf.format(startDayDutyTime.onDutyTime);  //一整天
            }else if(jobStartTime.getTime() >= startDayDutyTime.onDutyTime.getTime() && jobStartTime.getTime() <= startDayDutyTime.offDutyTime.getTime()){  //上班的时候发的工单
                //计算半天的工作时长
                firstDayWorkHour =  (startDayDutyTime.offDutyTime.getTime()-jobStartTime.getTime()) / 1000 /60/60;
                firstDayHour = new DayHour(startStr , firstDayWorkHour);
                startAdjust = CalDateUtils.nextDayOnDutyTime(jobStartTime, onDutyTime);
            }else if(jobStartTime.getTime() > startDayDutyTime.offDutyTime.getTime()){  //下班以后发的工单, 从第二天早上上班开始计算
                startAdjust = CalDateUtils.nextDayOnDutyTime(jobStartTime, onDutyTime);
            }
        }

        //对结束日期进行调整, 并计算结束当日的工作时间
        if(! CalDateUtils.isWeekend(endStr)){
            if(jobEndTime.getTime() < endDayDutyTime.onDutyTime.getTime()){  //结束时间早于上班时间
                endAdjust = CalDateUtils.prevDayOffDutyTime(jobEndTime, offDutyTime);
            }else if(jobEndTime.getTime() >= endDayDutyTime.onDutyTime.getTime() && jobEndTime.getTime() <= endDayDutyTime.offDutyTime.getTime()){  //上班的时候发的工单
                //计算半天的工作时长
                endDayWorkHour =  (jobEndTime.getTime()-endDayDutyTime.onDutyTime.getTime()) / 1000 /60/60;
                endDayHour = new DayHour(endStr , endDayWorkHour);
                endAdjust = CalDateUtils.prevDayOffDutyTime(jobEndTime, offDutyTime);
            }else if(jobEndTime.getTime() > endDayDutyTime.offDutyTime.getTime()){  //下班以后发的工单, 从第二天早上上班开始计算
                endAdjust = sdf.format(endDayDutyTime.offDutyTime);
            }
        }

        List<String> workDayList = CalDateUtils.workDay(startAdjust, endAdjust, null, null);
        long workDayHours =  workDayList.size() * (offDutyTime.getTime() - onDutyTime.getTime())/ 1000 / 60/60;
        List<DayHour> dayHourList = new ArrayList<>();
        for(String dayStr: workDayList){
            DayHour dayHour = new DayHour(dayStr, 8.0);
            dayHourList.add(dayHour);
        }
        if(firstDayHour.hour != 0){
            dayHourList.add(firstDayHour);
        }
        if(endDayHour.hour != 0){
            dayHourList.add(endDayHour);
        }
        double sumHours = (firstDayWorkHour + workDayHours + endDayWorkHour);
        RspWorkHour rspWorkHour = new RspWorkHour(sumHours, dayHourList);
        return rspWorkHour;
    }

    /**
     * 日期工时内部类
     * 用于存储特定日期的工作工时信息
     */
    private static class DayHour{
        private String day;
        private double hour;
        
        /**
         * 构造日期工时对象
         * 创建包含日期和工作工时的DayHour实例
         * 
         * @param day 日期字符串
         * @param hour 工作工时
         */
        private DayHour(String day, double hour) {
            this.day = day;
            this.hour = hour;
        }
        
        /**
         * 默认构造日期工时对象
         * 创建空的日期工时实例
         */
        private DayHour() {
            this.day = "";
            this.hour = 0;
        }

    }
    
    /**
     * 工时响应内部类
     * 用于封装工时计算结果
     */
    public static class RspWorkHour{
        public final double sumWorkHours; //总工时
        public final List<DayHour> dayHourList; //每日工时列表

        /**
         * 构造工时响应对象
         * 创建包含总工时和每日工时列表的响应实例
         *
         * @param sumWorkHours 总工时
         * @param dayHourList 每日工时列表
         */
        public RspWorkHour(double sumWorkHours, List<DayHour> dayHourList) {
            this.sumWorkHours = sumWorkHours;
            this.dayHourList = dayHourList;
        }
    }

}
