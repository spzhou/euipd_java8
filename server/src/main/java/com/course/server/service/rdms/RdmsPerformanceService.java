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
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.OperateTypeEnum;
import com.course.server.enums.rdms.PaymentStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsPerformanceMapper;
import com.course.server.service.calculate.CalManhourService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class RdmsPerformanceService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsPerformanceService.class);

    @Resource
    private RdmsPerformanceMapper rdmsPerformanceMapper;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private CalManhourService calManhourService;
    @Resource
    private RdmsCustomerService customerService;
    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;
    @Autowired
    private RdmsProjectService rdmsProjectService;
    @Autowired
    private RdmsBudgetResearchService rdmsBudgetResearchService;
    @Autowired
    private RdmsMilestoneService rdmsMilestoneService;
    @Autowired
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;
    @Autowired
    private RdmsPerformanceService rdmsPerformanceService;
    @Autowired
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RdmsManhourService rdmsManhourService;

    private static final long EXPIRATION_TIME = TimeUnit.DAYS.toSeconds(1);  //一天的秒数

    public void getCustomerSummaryData_toRedis(String customerId, String formattedDate) throws ParseException {
        CustomerSummaryDto customerSummaryDto = new CustomerSummaryDto();
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        customerSummaryDto.setCustomerId(customerId);
        customerSummaryDto.setCustomerName(rdmsCustomer.getCustomerName());
        List<ProjectSummaryDto> projectSummaryList = new ArrayList<>();
        List<RdmsProjectDto> projectList = rdmsProjectService.getProjectListByCustomerId_setuped(customerId);
        if(!CollectionUtils.isEmpty(projectList)){
            for(RdmsProjectDto projectDto : projectList){
                ProjectSummaryDto summaryDto = rdmsPerformanceService.getProjectSummaryInfo(projectDto.getId());
                if(!ObjectUtils.isEmpty(summaryDto)){
                    if(summaryDto.getManhourImpRate().doubleValue() < 0 || summaryDto.getManhourImpRate().doubleValue() > 1.50){
                        summaryDto.setManhourImpRate(BigDecimal.valueOf(1.50));
                    }
                    if(summaryDto.getBudgetImpRate().doubleValue() < 0 || summaryDto.getBudgetImpRate().doubleValue() > 1.50){
                        summaryDto.setBudgetImpRate(BigDecimal.valueOf(1.50));
                    }
                }
                projectSummaryList.add(summaryDto);
            }
        }
        List<ProjectSummaryDto> collect = projectSummaryList.stream().sorted(Comparator.comparing(ProjectSummaryDto::getProductManagerName).reversed()).collect(Collectors.toList());
        customerSummaryDto.setProjectSummaryList(collect);
        //计算公司全局参数
        // 获取当前年份
        int currentYear = LocalDate.now().getYear();
        // 计算当前年份的第一天零时
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0, 0);
        Date startTime = this.toLocalDate(startOfYear);
        // 计算当前年份的最后一天24时（实际上是下一年的1月1日零时）
        LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);
        Date endTime = this.toLocalDate(endOfYear);

        List<RdmsPerformanceDto> customerPerformanceList = new ArrayList<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllNormalWorkers(customerId);
        if(!CollectionUtils.isEmpty(rdmsCustomerUsers)){
            for(RdmsCustomerUser rdmsCustomerUser : rdmsCustomerUsers){
                RdmsPerformanceDto rdmsPerformanceDto = rdmsPerformanceService.calPerformance(rdmsCustomerUser.getId(), 0.3, startTime, endTime, false);
                //转换为个人工时
                Double v = rdmsManhourService.transformToWorkerManhour(rdmsPerformanceDto.getSubmitHours(), rdmsPerformanceDto.getCustomerUserId(), customerId, OperateTypeEnum.DEVELOP);
                rdmsPerformanceDto.setSubmitHours(v);
                customerPerformanceList.add(rdmsPerformanceDto);
            }
        }
        if(!CollectionUtils.isEmpty(customerPerformanceList)){
            List<RdmsPerformanceDto> customerUserPerformanceList = customerPerformanceList.stream().sorted(Comparator.comparing(RdmsPerformanceDto::getPerformance).reversed()).collect(Collectors.toList());
            customerSummaryDto.setCustomerPerformanceList(customerUserPerformanceList);
        }
        /*if(!CollectionUtils.isEmpty(customerPerformanceList) && customerPerformanceList.size() >= 3){
            customerSummaryDto.setCustomerPerformanceNo1(customerSummaryDto.getCustomerPerformanceList().get(0));
            customerSummaryDto.setCustomerPerformanceNo2(customerSummaryDto.getCustomerPerformanceList().get(1));
            customerSummaryDto.setCustomerPerformanceNo3(customerSummaryDto.getCustomerPerformanceList().get(2));
        }*/

        try {
            String jsonString = JSON.toJSONString(customerSummaryDto);
            if (jsonString.isEmpty()) {
                // 处理空或无效的 JSON 字符串
                throw new IllegalArgumentException("Invalid customerSummaryDto object");
            }
            redisTemplate.opsForValue().set(formattedDate, jsonString, EXPIRATION_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            // 记录日志或采取其他措施
            System.err.println("Failed to save customer summary to Redis: " + e.getMessage());
            // 可以选择抛出自定义异常或进行其他处理
        }
    }

    private Date toLocalDate(LocalDateTime localDateTime){
        // 获取当前时区
        ZoneId zoneId = ZoneId.systemDefault();

        // 将 LocalDateTime 转换为 ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        // 将 ZonedDateTime 转换为 Instant
        java.time.Instant instant = zonedDateTime.toInstant();

        // 将 Instant 转换为 Date
        return Date.from(instant);
    }

    @Transactional
    public void getWorkerPerformanceList(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        rdmsCustomerUserService.listAllWorkers(pageDto);
        List<RdmsCustomerUser> customerUserList = pageDto.getList();
        List<String> customerUserIdList = customerUserList.stream().map(RdmsCustomerUser::getId).collect(Collectors.toList());

        List<RdmsPerformanceDto> rdmsPerformanceDtos = this.getPerformanceList(customerUserIdList);
        pageDto.setList(rdmsPerformanceDtos);
    }

    @Transactional
    public List<RdmsPerformanceDto> getPerformanceList(List<String> workerIdList){
        RdmsPerformanceExample performanceExample = new RdmsPerformanceExample();
        RdmsPerformanceExample.Criteria criteria = performanceExample.createCriteria();
        criteria.andCustomerUserIdIn(workerIdList);
        List<RdmsPerformance> rdmsPerformances = rdmsPerformanceMapper.selectByExample(performanceExample);
        List<RdmsPerformanceDto> rdmsPerformanceDtos = CopyUtil.copyList(rdmsPerformances, RdmsPerformanceDto.class);
        return rdmsPerformanceDtos;
    }

    @Transactional
    public List<RdmsPerformanceDto> getPerformanceListByCustomerUserId(String customerUserId){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MONTH, -1);
        Date dateTime = calendar.getTime();

        RdmsPerformanceExample performanceExample = new RdmsPerformanceExample();
        RdmsPerformanceExample.Criteria criteria = performanceExample.createCriteria();
        performanceExample.setOrderByClause("create_time desc");
        criteria.andCustomerUserIdEqualTo(customerUserId);
        criteria.andCreateTimeGreaterThan(dateTime);
        List<RdmsPerformance> rdmsPerformances = rdmsPerformanceMapper.selectByExample(performanceExample);
        List<RdmsPerformanceDto> rdmsPerformanceDtos = CopyUtil.copyList(rdmsPerformances, RdmsPerformanceDto.class);
        if(!CollectionUtils.isEmpty(rdmsPerformanceDtos)){
            for(RdmsPerformanceDto performanceDto: rdmsPerformanceDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(performanceDto.getCustomerUserId());
                performanceDto.setCustomerId(rdmsCustomerUser.getCustomerId());
                performanceDto.setCustomerUserName(rdmsCustomerUser.getTrueName());
                performanceDto.setJobLevel(rdmsCustomerUser.getJobLevel());
                performanceDto.setJobNum(rdmsCustomerUser.getJobNum());
                performanceDto.setJobPosition(rdmsCustomerUser.getJobPosition());
                performanceDto.setOfficeTel(rdmsCustomerUser.getOfficeTel());
                performanceDto.setTitle(rdmsCustomerUser.getTitle());
            }
        }
        return rdmsPerformanceDtos;
    }

    public void  monthAccount() throws ParseException {
        List<RdmsCustomer> allCustomerList = customerService.getAllCustomerList();
        if(!CollectionUtils.isEmpty(allCustomerList)){
            for(RdmsCustomer customer: allCustomerList){
                List<RdmsCustomerUser> customerUsers = rdmsCustomerUserService.listAllNormalWorkers(customer.getId());
                if(!CollectionUtils.isEmpty(customerUsers)){
                    for(RdmsCustomerUser customerUser: customerUsers) {
                        //判断是否存在上个月的结算记录 如果有, 则不重新计算
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDateStr = simpleDateFormat.format(new Date());
                        boolean hasRecord = this.getHasLastMonthPerformanceItemFlag(customerUser.getId(), currentDateStr);
                        if (!hasRecord) {
                            RdmsPerformanceDto workerPerformanceDto = this.getWorkerLastMonthPerformanceItem(customerUser.getId(), currentDateStr);
                            //将对应的工单设置为已结算状态
                            if (!CollectionUtils.isEmpty(workerPerformanceDto.getJobitemIdList())) {
                                for (String jobitemId : workerPerformanceDto.getJobitemIdList()) {
                                    RdmsJobItem jobItem = new RdmsJobItem();
                                    jobItem.setId(jobitemId);
                                    jobItem.setSettleAccounts(1);
                                    jobItem.setUpdateTime(new Date());
                                    rdmsJobItemService.updateByPrimaryKeySelective(jobItem);
                                }
                            }
                            this.save(workerPerformanceDto);
                        }
                    } //customerUser
                }
            } //customer
        }
    }

    public RdmsPerformanceDto getWorkerLastMonthPerformanceItem(String customerUserId, String currentDateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDay = simpleDateFormat.parse(currentDateStr);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.MONTH, -1);  //上个月
        Date lastMonthTime = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastMonthDateStr = sdf.format(lastMonthTime);

        // 需要通过用户配置进行输入, 这里暂时固定为0.3
        RdmsPerformanceDto performanceDto = this.calLastMonthPerformanceItem(customerUserId, lastMonthDateStr, 0.30);
        return performanceDto;
    }


    //绩效统计方法
    public RdmsPerformanceDto calLastMonthPerformanceItem(String customerUserId, String lastMonthDateStr /*yyyy-MM-dd*/, Double rebateRate /*延期计算折扣率*/) throws ParseException {

        //计算统计周期起止日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(lastMonthDateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);  //获得所在月份的第一天零点
        calendar.set(Calendar.HOUR_OF_DAY, 0);		    //0点
        calendar.set(Calendar.MINUTE, 0);				//0分
        calendar.set(Calendar.SECOND, 0);				//1秒
        calendar.set(Calendar.MILLISECOND, 0);			//当前数值参数超过三位数则占用秒，如：设置3500 则当前日期变为 00：00:02
        Date monthStart = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);  //获取下个月第一天零点
        calendar.set(Calendar.DAY_OF_MONTH, 1);	    //1号
        calendar.set(Calendar.HOUR_OF_DAY, 0);		    //0点
        calendar.set(Calendar.MINUTE, 0);				//0分
        calendar.set(Calendar.SECOND, 0);				//1秒
        calendar.set(Calendar.MILLISECOND, 0);			//当前数值参数超过三位数则占用秒，如：设置3500 则当前日期变为 00：00:02

//        calendar.add(Calendar.DAY_OF_MONTH, -1); //获取当前月最后一天零点
        Date monthEnd = calendar.getTime();

        return calPerformance(customerUserId, rebateRate, monthStart, monthEnd, true);
    }

    public RdmsPerformance getMonthlyPerformanceItem(String customerUserId, String monthlyStr){
        RdmsPerformanceExample performanceExample = new RdmsPerformanceExample();
        performanceExample.createCriteria()
                .andCustomerUserIdEqualTo(customerUserId)
                .andYearMonthEqualTo(monthlyStr)
                .andDeletedEqualTo(0);
        List<RdmsPerformance> rdmsPerformances = rdmsPerformanceMapper.selectByExample(performanceExample);
        if(!CollectionUtils.isEmpty(rdmsPerformances)){
            return rdmsPerformances.get(0);
        }else{
            return null;
        }

    }

    /**
     * 计算工作人员当前月份的工作绩效实时数值
     * @param customerUserId
     * @param rebateRate
     * @return
     * @throws ParseException
     */
     public RdmsPerformanceDto calCurrentMonthPerformanceItem(String customerUserId, Double rebateRate /*延期计算折扣率*/) throws ParseException {

        //计算统计周期起止日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);  //获得所在月份的第一天零点
        Date monthStart = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);  //获取下个月第一天零点
//        calendar.add(Calendar.DAY_OF_MONTH, -1); //获取当前月最后一天零点
        Date monthEnd = calendar.getTime();

        return calPerformance(customerUserId, rebateRate, monthStart, monthEnd, false);
    }

    public RdmsPerformanceDto calPerformance(String customerUserId, Double rebateRate, Date startTime, Date endTime, Boolean payTheBillFlag/*仅在月度绩效结算是为true*/) throws ParseException {
        //计算法定总工时
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String onDuty = "2023-11-27 09:00:00";
        Date onDutyTime = simpleDateFormat.parse(onDuty);
        String offDuty = "2023-11-27 17:00:00";
        Date offDutyTime = simpleDateFormat.parse(offDuty);
        CalManhourService.RspWorkHour rspWorkHour = calManhourService.calWorkHoursInLaw(startTime, endTime, onDutyTime, offDutyTime);

        double submitHours = 0.0;  //总提交工时
        double sumPerformanceManhour = 0.0;  //总计划工时
        BigDecimal sumPerformanceFee = BigDecimal.ZERO;  //总绩效工时费
        long delayTimeInMs = 0L;  //总延时
        long sumWorkTimeInMs = 0L;  //总周期
        double delayRate = 0.0;  //延期率
        double baseScore = 0.5;  //绩效基础
        //计算研发和测试工单的工作绩效
        List<RdmsJobItem> performanceJobItemList = rdmsJobItemService.getPerformanceJobItemList(customerUserId, startTime, endTime);
        /*List<RdmsJobItem> performanceJobItemList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(performanceJobItemList_origin)){
            performanceJobItemList = performanceJobItemList_origin.stream().filter(item -> item.getPerformanceManhour() > 0).collect(Collectors.toList());
        }*/
        List<String> jobItemIdList = performanceJobItemList.stream().map(RdmsJobItem::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(performanceJobItemList)){
            //计算总提交工时
            submitHours = performanceJobItemList.stream().map(RdmsJobItem::getPerformanceManhour).reduce(0.0, Double::sum);
            //计算总延期时间(毫秒)
            List<RdmsJobItem> delayJobList = performanceJobItemList.stream().filter(rdmsJobItem -> rdmsJobItem.getActualSubmissionTime().getTime() > rdmsJobItem.getPlanSubmissionTime().getTime()).collect(Collectors.toList());
            delayTimeInMs = delayJobList.stream().map(item -> item.getActualSubmissionTime().getTime() - item.getPlanSubmissionTime().getTime()).reduce(0L, Long::sum);
            //计算工作周期总时间长度
            sumWorkTimeInMs = performanceJobItemList.stream().map(item -> item.getPlanSubmissionTime().getTime() - item.getCreateTime().getTime()).reduce(0L, Long::sum);

            if((double) sumWorkTimeInMs != 0.0){
                delayRate = (double) delayTimeInMs / (double) sumWorkTimeInMs;
            }else{
                delayRate = 0.0;
            }

            //工单结算绩效工时 = 工单绩效工时 * ( 1 - 延期率 * 延期折扣 ) * ( 绩效基础 + (1 - 绩效基础)) * ( 任务单评分 / 10));   延期折扣 = 0.3 ; 绩效基础 = 0.5;
            //绩效工时费 = 求和(工单结算绩效工时 * 标准工时费率)
            if(!CollectionUtils.isEmpty(performanceJobItemList)){
                for(RdmsJobItem jobItem : performanceJobItemList){
                    if(!ObjectUtils.isEmpty(jobItem.getPerformanceManhour()) && jobItem.getPerformanceManhour() > 0.0){
                        //绩效是正数
                        double performanceManhour = jobItem.getPerformanceManhour() * (1 - (delayRate * rebateRate)) * (baseScore + ((1 -baseScore) * ((double)jobItem.getEvaluateScore() / 10.0)));
                        sumPerformanceManhour += performanceManhour;
                        BigDecimal performanceFee = jobItem.getStandManhourFee().multiply(BigDecimal.valueOf(performanceManhour));
                        sumPerformanceFee = sumPerformanceFee.add(performanceFee);
                    }else{
                        //绩效是负数  问题: 如果绩效工时是负数, 同时, 延期超过3被工时, 括号内也是负数, 就会将原本处于负数的绩效工时, 变成正数 造成绩效工时 比交付工时还高的问题!!! 所以需要处理
                        double performanceManhour = jobItem.getPerformanceManhour()/* * (1 - (delayRate * rebateRate)) * (baseScore + ((1 -baseScore) * ((double)jobItem.getEvaluateScore() / 10.0)))*/;
                        sumPerformanceManhour += performanceManhour;   //如果绩效是负数,则直接在总绩效工时中减去就可以了
                        BigDecimal performanceFee = jobItem.getStandManhourFee().multiply(BigDecimal.valueOf(performanceManhour));
                        sumPerformanceFee = sumPerformanceFee.add(performanceFee);
                    }
                }
            }

            //结账处理 如果是生成上一个月的绩效, 则将支付状态 修改为 已支付状态
            if(payTheBillFlag){
                for(RdmsJobItem jobItem: performanceJobItemList){
                    RdmsBudgetResearchExe itemByJobitemSerialNo = rdmsBudgetResearchExeService.getItemByJobitemSerialNo(jobItem.getJobSerial());
                    if(!ObjectUtils.isEmpty(itemByJobitemSerialNo)){
                        itemByJobitemSerialNo.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                    }
                }
            }
        }

        //构建返回数据
        RdmsPerformance performance = new RdmsPerformanceDto();
        //private String customerUserId;
        performance.setCustomerUserId(customerUserId);
        //private String yearMonth;
        String dateStr = simpleDateFormat.format(startTime);
        String substring = dateStr.substring(0, 7);
        performance.setYearMonth(substring);
        //private Double submitHours;
        performance.setSubmitHours(submitHours);
        double loadRate = submitHours / rspWorkHour.sumWorkHours;
        performance.setLoadRate(BigDecimal.valueOf(loadRate).setScale(2 , RoundingMode.HALF_UP));
        //private BigDecimal delayRate;
        performance.setDelayRate(BigDecimal.valueOf(delayRate).setScale(6, RoundingMode.HALF_UP));
        //private BigDecimal rebateRate;
        performance.setRebateRate(BigDecimal.valueOf(rebateRate));
        //private BigDecimal performance;
        performance.setPerformance(sumPerformanceFee.setScale(2, RoundingMode.HALF_UP));

        //绩效得分 = 求和(工单结算绩效工时) / 总计划工时 * 100
        if(submitHours != 0.0){
            double scoreOfMonth = sumPerformanceManhour / submitHours * 100;
            performance.setScore(scoreOfMonth);
        }else{
            performance.setScore(0.0);
        }


        RdmsPerformanceDto rdmsPerformanceDto = CopyUtil.copy(performance, RdmsPerformanceDto.class);
        rdmsPerformanceDto.setJobitemIdList(jobItemIdList);

        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsPerformanceDto.getCustomerUserId());
        if (!ObjectUtils.isEmpty(rdmsCustomerUser)) {
            rdmsPerformanceDto.setCustomerUserId(rdmsCustomerUser.getId());
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getJobNum())) {
                rdmsPerformanceDto.setJobNum(rdmsCustomerUser.getJobNum());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getTrueName())) {
                rdmsPerformanceDto.setCustomerUserName(rdmsCustomerUser.getTrueName());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getJobLevel())) {
                rdmsPerformanceDto.setJobLevel(rdmsCustomerUser.getJobLevel());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getTitle())) {
                rdmsPerformanceDto.setTitle(rdmsCustomerUser.getTitle());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getJobPosition())) {
                rdmsPerformanceDto.setJobPosition(rdmsCustomerUser.getJobPosition());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getOfficeTel())) {
                rdmsPerformanceDto.setOfficeTel(rdmsCustomerUser.getOfficeTel());
            }
        }
        return rdmsPerformanceDto;
    }

    public RdmsPerformanceDto calProjectPerformanceByStaff(String customerUserId, Double rebateRate, String projectId) throws ParseException {
        //计算法定总工时
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String onDuty = "2023-11-27 09:00:00";
        Date onDutyTime = simpleDateFormat.parse(onDuty);
        String offDuty = "2023-11-27 17:00:00";
        Date offDutyTime = simpleDateFormat.parse(offDuty);
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        if(ObjectUtils.isEmpty(rdmsProject) || ObjectUtils.isEmpty(rdmsProject.getCreateTime()) || ObjectUtils.isEmpty(rdmsProject.getReleaseTime())){
            return null;
        }
        CalManhourService.RspWorkHour rspWorkHour = calManhourService.calWorkHoursInLaw(rdmsProject.getCreateTime(), rdmsProject.getReleaseTime(), onDutyTime, offDutyTime);

        double submitHours = 0.0;  //总提交工时
        double sumPerformanceManhour = 0.0;  //总计划工时
        BigDecimal sumPerformanceFee = BigDecimal.ZERO;  //总绩效工时费
        long delayTimeInMs = 0L;  //总延时
        long sumWorkTimeInMs = 0L;  //总周期
        double delayRate = 0.0;  //延期率
        double baseScore = 0.5;  //绩效基础
        //计算研发和测试工单的工作绩效
        List<RdmsJobItem> performanceJobItemList = rdmsJobItemService.getProjectPerformanceJobItemList(customerUserId, projectId);
        List<String> jobItemIdList = performanceJobItemList.stream().map(RdmsJobItem::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(performanceJobItemList)){
            //计算总提交工时
            submitHours = performanceJobItemList.stream().map(RdmsJobItem::getPerformanceManhour).reduce(0.0, Double::sum);
            //计算总延期时间(毫秒)
            List<RdmsJobItem> delayJobList = performanceJobItemList.stream().filter(rdmsJobItem -> rdmsJobItem.getActualSubmissionTime().getTime() > rdmsJobItem.getPlanSubmissionTime().getTime()).collect(Collectors.toList());
            delayTimeInMs = delayJobList.stream().map(item -> item.getActualSubmissionTime().getTime() - item.getPlanSubmissionTime().getTime()).reduce(0L, Long::sum);
            //计算工作周期总时间长度
            sumWorkTimeInMs = performanceJobItemList.stream().map(item -> item.getPlanSubmissionTime().getTime() - item.getCreateTime().getTime()).reduce(0L, Long::sum);

            if((double) sumWorkTimeInMs != 0.0){
                delayRate = (double) delayTimeInMs / (double) sumWorkTimeInMs;
            }else{
                delayRate = 0.0;
            }

            //工单结算绩效工时 = 工单绩效工时 * ( 1 - 延期率 * 延期折扣 ) * ( 绩效基础 + (1 - 绩效基础)) * ( 任务单评分 / 10));   延期折扣 = 0.3 ; 绩效基础 = 0.5;
            //绩效工时费 = 求和(工单结算绩效工时 * 标准工时费率)
            if(!CollectionUtils.isEmpty(performanceJobItemList)){
                for(RdmsJobItem jobItem : performanceJobItemList){
                    double performanceManhour = jobItem.getPerformanceManhour() * (1 - (delayRate * rebateRate)) * (baseScore + ((1 -baseScore) * ((double)jobItem.getEvaluateScore() / 10.0)));
                    if(performanceManhour < 0.0){
                        performanceManhour = 0.0;
                    }
                    sumPerformanceManhour += performanceManhour;
                    BigDecimal performanceFee = jobItem.getStandManhourFee().multiply(BigDecimal.valueOf(performanceManhour));
                    sumPerformanceFee = sumPerformanceFee.add(performanceFee);
                }
            }
        }

        //构建返回数据
        RdmsPerformance performance = new RdmsPerformanceDto();
        //private String customerUserId;
        performance.setCustomerUserId(customerUserId);
        //private String yearMonth;
        performance.setYearMonth(null);
        //private Double submitHours;
        performance.setSubmitHours(submitHours);
        double loadRate = submitHours / rspWorkHour.sumWorkHours;
        performance.setLoadRate(BigDecimal.valueOf(loadRate).setScale(2 , RoundingMode.HALF_UP));
        //private BigDecimal delayRate;
        performance.setDelayRate(BigDecimal.valueOf(delayRate).setScale(6, RoundingMode.HALF_UP));
        //private BigDecimal rebateRate;
        performance.setRebateRate(BigDecimal.valueOf(rebateRate));
        //private BigDecimal performance;
        performance.setPerformance(sumPerformanceFee.setScale(2, RoundingMode.HALF_UP));

        //绩效得分 = 求和(工单结算绩效工时) / 总计划工时 * 100
        if(submitHours != 0.0){
            double scoreOfMonth = sumPerformanceManhour / submitHours * 100;
            performance.setScore(scoreOfMonth);
        }else{
            performance.setScore(0.0);
        }


        RdmsPerformanceDto rdmsPerformanceDto = CopyUtil.copy(performance, RdmsPerformanceDto.class);
        rdmsPerformanceDto.setJobitemIdList(jobItemIdList);

        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsPerformanceDto.getCustomerUserId());
        if (!ObjectUtils.isEmpty(rdmsCustomerUser)) {
            rdmsPerformanceDto.setCustomerUserId(rdmsCustomerUser.getId());
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getJobNum())) {
                rdmsPerformanceDto.setJobNum(rdmsCustomerUser.getJobNum());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getTrueName())) {
                rdmsPerformanceDto.setCustomerUserName(rdmsCustomerUser.getTrueName());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getJobLevel())) {
                rdmsPerformanceDto.setJobLevel(rdmsCustomerUser.getJobLevel());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getTitle())) {
                rdmsPerformanceDto.setTitle(rdmsCustomerUser.getTitle());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getJobPosition())) {
                rdmsPerformanceDto.setJobPosition(rdmsCustomerUser.getJobPosition());
            }
            if (!ObjectUtils.isEmpty(rdmsCustomerUser.getOfficeTel())) {
                rdmsPerformanceDto.setOfficeTel(rdmsCustomerUser.getOfficeTel());
            }
        }
        return rdmsPerformanceDto;
    }

    public boolean getHasLastMonthPerformanceItemFlag(String customerUserId, String currentDateStr /*yyyy-MM-dd*/) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDay = simpleDateFormat.parse(currentDateStr);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.MONTH, -1);  //上个月
        calendar.set(Calendar.DAY_OF_MONTH, 1);	    //1号
        calendar.set(Calendar.HOUR_OF_DAY, 0);		    //0点
        calendar.set(Calendar.MINUTE, 0);				//0分
        calendar.set(Calendar.SECOND, 0);				//1秒
        calendar.set(Calendar.MILLISECOND, 0);			//当前数值参数超过三位数则占用秒，如：设置3500 则当前日期变为 00：00:02

        Date lastMonthTime = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastMonthDateStr = sdf.format(lastMonthTime);
        String yearMonthStr = lastMonthDateStr.substring(0, 7);

        RdmsPerformanceExample performanceExample = new RdmsPerformanceExample();
        performanceExample.createCriteria()
                .andCustomerUserIdEqualTo(customerUserId)
                .andYearMonthEqualTo(yearMonthStr)
                .andDeletedEqualTo(0);
        List<RdmsPerformance> rdmsPerformances = rdmsPerformanceMapper.selectByExample(performanceExample);
        if(!CollectionUtils.isEmpty(rdmsPerformances)){
            return true;
        }else{
            return false;
        }
    }

    public @NotNull ProjectSummaryDto getProjectSummaryInfo(String projectId) throws ParseException {
        ProjectSummaryDto summaryDto = new ProjectSummaryDto();
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        summaryDto.setId(projectId);
//        private String projectCode;
        summaryDto.setProjectCode(rdmsProject.getProjectCode());
//        private String projectName;
        summaryDto.setProjectName(rdmsProject.getProjectName());
//        private String productManagerName;
        if(!ObjectUtils.isEmpty(rdmsProject.getProductManagerId())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                summaryDto.setProductManagerName(rdmsCustomerUser.getTrueName());
            }
        }
//        private String projectManagerName;
        if(!ObjectUtils.isEmpty(rdmsProject.getProjectManagerId())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProjectManagerId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                summaryDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
            }
        }
//        private String testManagerName;
        if(!ObjectUtils.isEmpty(rdmsProject.getTestManagerId())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getTestManagerId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                summaryDto.setTestManagerName(rdmsCustomerUser.getTrueName());
            }
        }
//        private String toReleaseDay;  //发布倒计时
        if(!ObjectUtils.isEmpty(rdmsProject.getReleaseTime())){
            // 指定目标日期，例如2023年12月31日
            // 将Date对象转换为LocalDate对象
            if(!ObjectUtils.isEmpty(rdmsProject.getReleaseTime())){
                // 指定目标日期，例如2023年12月31日
//                LocalDate targetDate = LocalDate.of(2023, 12, 31);

                LocalDate targetDate = rdmsProject.getReleaseTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                // 获取当前日期
                LocalDate currentDate = LocalDate.now();
                // 计算从当前日期到目标日期的天数
                long daysUntil = ChronoUnit.DAYS.between(currentDate, targetDate);
                summaryDto.setReleaseTime(rdmsProject.getReleaseTime());
                summaryDto.setToReleaseDay(daysUntil);
            }
        }
//        private String budgetImpRate; //预算执行率
        RdmsHmiBudgeStatisticsDto mainProjectBudgetExecutionSummary = rdmsBudgetResearchExeService.getMainProjectBudgetExecutionSummary(projectId);
        if(!ObjectUtils.isEmpty(mainProjectBudgetExecutionSummary)){
            BigDecimal sumBudget = mainProjectBudgetExecutionSummary.getSumBudget();
            BigDecimal sumActiveBudget = mainProjectBudgetExecutionSummary.getSumActiveBudget();
            if(!ObjectUtils.isEmpty(sumBudget) && sumBudget.intValue() > 0 && !ObjectUtils.isEmpty(sumActiveBudget)){
                summaryDto.setBudgetImpRate(sumActiveBudget.divide(sumBudget, 2, RoundingMode.HALF_UP));
            }
        }
//        private String manhourImpRate;  //工时执行率
        //获得项目的人工费汇总数据
        RdmsBudgetResearchService.ClassifyBudget staffBudgetByProjectId = rdmsBudgetResearchService.getStaffBudgetByProjectId(projectId);
        if(!ObjectUtils.isEmpty(staffBudgetByProjectId.getBudget()) && !ObjectUtils.isEmpty(staffBudgetByProjectId.getApprovedBudget()) && staffBudgetByProjectId.getApprovedBudget().intValue() > 0){
            //获取项目的实际交付工时
            BigDecimal sumActiveManhourFeeByProjectId = rdmsBudgetResearchExeService.getSumActiveManhourFeeByProjectId(projectId);
            if(staffBudgetByProjectId.getBudget().intValue() > staffBudgetByProjectId.getApprovedBudget().intValue()){  //这样写是因为不确定, 申请预算后, 是不是同时增加budget和approveBudget, 这样写,学数据更大的,应该没错
                summaryDto.setManhourImpRate(sumActiveManhourFeeByProjectId.divide(staffBudgetByProjectId.getBudget(), 2, RoundingMode.HALF_UP));
            }else {
                summaryDto.setManhourImpRate(sumActiveManhourFeeByProjectId.divide(staffBudgetByProjectId.getApprovedBudget(), 2, RoundingMode.HALF_UP));
            }
        }else {
            summaryDto.setManhourImpRate(BigDecimal.ZERO);
        }
//        private String sumJobitemNum;
        List<RdmsJobItemDto> jobitemListByProjectId = rdmsJobItemService.getJobitemListByProjectId(projectId, null, null);
        if(!CollectionUtils.isEmpty(jobitemListByProjectId)){
            summaryDto.setSumJobitemNum(jobitemListByProjectId.size());
        }else{
            summaryDto.setSumJobitemNum(0);
        }
//        private String completeJobitemNum;
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        List<RdmsJobItemDto> jobitemListByProjectId_complete = rdmsJobItemService.getJobitemListByProjectId(projectId, statusEnumList, null);
        if(!CollectionUtils.isEmpty(jobitemListByProjectId_complete)){
            summaryDto.setCompleteJobitemNum(jobitemListByProjectId_complete.size());
        }else{
            summaryDto.setCompleteJobitemNum(0);
        }
//        private String averageJobitemTime;  //平均工单工时数
        if(!CollectionUtils.isEmpty(jobitemListByProjectId)){
            Double reduce = jobitemListByProjectId.stream().map(RdmsJobItemDto::getManhour).reduce(0.0, Double::sum);
//            summaryDto.setAverageJobitemTime(reduce / jobitemListByProjectId.size());
            BigDecimal reduceBD = BigDecimal.valueOf(reduce);
            // 将 jobitemListByProjectId.size() 转换为 BigDecimal
            BigDecimal sizeBD = BigDecimal.valueOf(jobitemListByProjectId.size());
            // 计算平均工单工时并保留两位小数
            BigDecimal averageJobitemTimeBD = reduceBD.divide(sizeBD, 2, RoundingMode.HALF_UP);
            // 设置最终结果到 summaryDto
            summaryDto.setAverageJobitemTime(averageJobitemTimeBD.doubleValue());
        }else{
            summaryDto.setAverageJobitemTime(0.0);
        }
//        private String overtimeJobitemNum;  //逾期工单数
        if(!CollectionUtils.isEmpty(jobitemListByProjectId)){
            int size = (int) jobitemListByProjectId.stream().filter(jobItemDto -> {
                if (!(jobItemDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())
                        || jobItemDto.getStatus().equals(JobItemStatusEnum.ARCHIVED.getStatus())
                        || jobItemDto.getStatus().equals(JobItemStatusEnum.CANCEL.getStatus()))
                ) {
                    if (!ObjectUtils.isEmpty(jobItemDto.getPlanSubmissionTime()) && jobItemDto.getPlanSubmissionTime().getTime() < new Date().getTime()) {
                        return true;
                    }
                }
                return false;
            }).count();

            summaryDto.setOvertimeJobitemNum(size);
        }else{
            summaryDto.setOvertimeJobitemNum(0);
        }
//        private String overdueRate;  //总延期率
        Long planWorkTime = jobitemListByProjectId_complete.stream().map(item -> {
            if (item.getPlanSubmissionTime() != null && item.getCreateTime() != null) {
                return item.getPlanSubmissionTime().getTime() - item.getCreateTime().getTime();
            } else {
                return 0L;
            }
        }).reduce(0L, Long::sum);
        Long overdueWorkTime = jobitemListByProjectId_complete.stream().map(item -> {
            if (item.getPlanSubmissionTime() != null && item.getActualSubmissionTime() != null && item.getActualSubmissionTime().getTime() > item.getPlanSubmissionTime().getTime()) {
                return item.getActualSubmissionTime().getTime() - item.getPlanSubmissionTime().getTime();
            } else {
                return 0L;
            }
        }).reduce(0L, Long::sum);
        if(planWorkTime == 0){
            summaryDto.setOverdueRate(0.0);
        }else{
            summaryDto.setOverdueRate((double) overdueWorkTime / (double) planWorkTime);
        }

//        private String nextReviewMilestoneTime;  //待评审里程碑时间
        List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(projectId);
        if(!CollectionUtils.isEmpty(rdmsMilestoneDtos)){
            for(RdmsMilestoneDto milestoneDto : rdmsMilestoneDtos){
                Boolean notClosed = rdmsMilestoneService.isNotClosed(milestoneDto.getId());
                if(notClosed){
                    summaryDto.setNextReviewMilestone(milestoneDto);
                    break;
                }
            }
        }else{
            summaryDto.setNextReviewMilestone(null);
        }
//        private String submitFileNum;  //提交文档数
        List<String> jobItemIdList = jobitemListByProjectId.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(jobItemIdList)){
            List<String> propertyFileStrList = rdmsJobItemPropertyService.getListByJobitemIdList(jobItemIdList).stream().map(RdmsJobItemProperty::getFileListStr).collect(Collectors.toList());
            List<String> fileIdList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(propertyFileStrList)){
                for(String fileStr: propertyFileStrList){
                    fileIdList.addAll(JSON.parseArray(fileStr, String.class));
                }
            }
            summaryDto.setSubmitFileNum(fileIdList.size());
        }else{
            summaryDto.setSubmitFileNum(0);
        }


//        private List<Performance> performanceList;
        //得到所有参与项目工作的人
        List<String> developerIdList = jobitemListByProjectId.stream().map(RdmsJobItemDto::getExecutorId).distinct().collect(Collectors.toList());
        List<RdmsPerformanceDto> performanceList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(developerIdList)){
            //计算每个人在项目中的绩效
            for(String developerId : developerIdList){
                RdmsPerformanceDto rdmsPerformanceDto = this.calProjectPerformanceByStaff(developerId, 0.3, projectId);
                if(rdmsPerformanceDto != null){
                    performanceList.add(rdmsPerformanceDto);
                }
            }

        }
        List<RdmsPerformanceDto> sortedPerformanceList = performanceList.stream().sorted(Comparator.comparing(RdmsPerformanceDto::getPerformance).reversed()).collect(Collectors.toList());
        summaryDto.setPerformanceList(sortedPerformanceList);

        if(!CollectionUtils.isEmpty(sortedPerformanceList) && sortedPerformanceList.size() >= 3){
            summaryDto.setPerformanceNo1(sortedPerformanceList.get(0));
            summaryDto.setPerformanceNo2(sortedPerformanceList.get(1));
            summaryDto.setPerformanceNo3(sortedPerformanceList.get(2));
        }

        return summaryDto;
    }


    /*以下是通用标准服务函数*/
    public RdmsPerformance selectByPrimaryKey(String id){
        return rdmsPerformanceMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsPerformance performance) {
        if(ObjectUtils.isEmpty(performance.getId())){
            return this.insert(performance);
        }else{
            RdmsPerformance RdmsPerformance = this.selectByPrimaryKey(performance.getId());
            if(ObjectUtils.isEmpty(RdmsPerformance)){
                return this.insert(performance);
            }else{
                return this.update(performance);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsPerformance performance) {
        if(ObjectUtils.isEmpty(performance.getId())){
            performance.setId(UuidUtil.getShortUuid());
        }
        RdmsPerformance RdmsPerformance = rdmsPerformanceMapper.selectByPrimaryKey(performance.getId());
        if(! ObjectUtils.isEmpty(RdmsPerformance)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            performance.setCreateTime(new Date());
            performance.setUpdateTime(new Date());
            performance.setDeleted(0);
            rdmsPerformanceMapper.insert(performance);
            return performance.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsPerformance performance) {
        if(ObjectUtils.isEmpty(performance.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsPerformance RdmsPerformance = this.selectByPrimaryKey(performance.getId());
        if(ObjectUtils.isEmpty(RdmsPerformance)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            performance.setUpdateTime(new Date());
            performance.setDeleted(0);
            rdmsPerformanceMapper.updateByPrimaryKey(performance);
            return performance.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsPerformance performance){
        rdmsPerformanceMapper.updateByPrimaryKeySelective(performance);
        return performance.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsPerformance performance = rdmsPerformanceMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(performance)){
            performance.setDeleted(1);
            this.update(performance);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
