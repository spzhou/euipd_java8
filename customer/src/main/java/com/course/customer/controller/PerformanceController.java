/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import cn.hutool.core.util.ObjectUtil;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/performance")
public class PerformanceController {
    private static final Logger LOG = LoggerFactory.getLogger(PerformanceController.class);
    public static final String BUSINESS_NAME = "绩效管理";

    @Resource
    private RdmsPerformanceService rdmsPerformanceService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsIpmtService rdmsIpmtService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;

    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsPerformanceDto>> list(@RequestBody PageDto<RdmsPerformanceDto> pageDto) throws ParseException {
        ResponseDto<PageDto<RdmsPerformanceDto>> responseDto = new ResponseDto<>();
        List<RdmsPerformanceDto> rdmsPerformanceDtoList = new ArrayList<>();
        PageDto userPageDto = CopyUtil.copy(pageDto, PageDto.class);
        rdmsCustomerUserService.listAllWorkers(userPageDto);

        for(Object customerUser: userPageDto.getList()) {
            RdmsCustomerUser user = (RdmsCustomerUser) customerUser;
            RdmsPerformanceDto performanceDto = rdmsPerformanceService.calCurrentMonthPerformanceItem(user.getId(), 0.3);
            rdmsPerformanceDtoList.add(performanceDto);
        }
        userPageDto.setList(rdmsPerformanceDtoList);
        responseDto.setContent(userPageDto);
        return responseDto;
    }

    @PostMapping("/getCurrentPerformanceByLoginUser/{customerUserId}")
    public ResponseDto<RdmsPerformanceDto> getCurrentPerformanceByLoginUser(@PathVariable String customerUserId) throws ParseException {
        ResponseDto<RdmsPerformanceDto> responseDto = new ResponseDto<>();
            RdmsPerformanceDto performanceDto = rdmsPerformanceService.calCurrentMonthPerformanceItem(customerUserId, 0.3);
        responseDto.setContent(performanceDto);
        return responseDto;
    }

    @PostMapping("/listPerformance")
    public ResponseDto<List<RdmsPerformanceDto>> listPerformance(@RequestParam String customerUserId, String customerId ) throws ParseException {
        ResponseDto<List<RdmsPerformanceDto>> responseDto = new ResponseDto<>();
        List<RdmsPerformanceDto> rdmsPerformanceDtoList = new ArrayList<>();
        //判断用户是不是IPMT或者SuperVisor
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        if(!ObjectUtils.isEmpty(rdmsCustomer)){
            List<RdmsCustomerUser> customerUserList = rdmsCustomerUserService.findCustomerUserByLoginName(rdmsCustomer.getContactPhone());
            if(!CollectionUtils.isEmpty(customerUserList)){
                if(customerUserList.get(0).getId().equals(customerUserId)){
                    //Super :列出公司所有员工
                    List<RdmsCustomerUser> customerUsers = rdmsCustomerUserService.listAllNormalWorkers(customerId);
                    for(RdmsCustomerUser customerUser: customerUsers) {
                        RdmsPerformanceDto performanceDto = rdmsPerformanceService.calCurrentMonthPerformanceItem(customerUser.getId(), 0.3);
                        rdmsPerformanceDtoList.add(performanceDto);
                    }
                }else{
                    List<String> ipmtIdList = rdmsIpmtService.listByCustomerId(customerId);
                    if(!CollectionUtils.isEmpty(ipmtIdList)){
                        if(ipmtIdList.contains(customerUserId)){
                            //IPMT :列出公司所有员工
                            List<RdmsCustomerUser> customerUsers = rdmsCustomerUserService.listAllNormalWorkers(customerId);
                            for(RdmsCustomerUser customerUser: customerUsers) {
                                RdmsPerformanceDto performanceDto = rdmsPerformanceService.calCurrentMonthPerformanceItem(customerUser.getId(), 0.3);
                                rdmsPerformanceDtoList.add(performanceDto);
                            }
                        }else{
                            //Underline
                            List<String> allUnderLineMemberIdList = rdmsProjectService.getUnderlineMemberList(customerUserId);

                            List<RdmsProjectSubproject> subprojectListByPjms = rdmsSubprojectService.getSubprojectListByPjmIdList(allUnderLineMemberIdList);
                            if (!CollectionUtils.isEmpty(subprojectListByPjms)) {
                                List<String> subprojectIds = subprojectListByPjms.stream().map(RdmsProjectSubproject::getId).collect(Collectors.toList());
                                List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectIdList(subprojectIds);
                                if (!CollectionUtils.isEmpty(jobitemList)) {
                                    List<String> executorIdList = jobitemList.stream().map(RdmsJobItemDto::getExecutorId).collect(Collectors.toList());
                                    List<String> executorIds = executorIdList.stream().distinct().collect(Collectors.toList());
                                    //人员ID合并列表
                                    allUnderLineMemberIdList.addAll(executorIds);
                                    List<String> allUnderlineIdList = allUnderLineMemberIdList.stream().distinct().collect(Collectors.toList());

                                    List<RdmsCustomerUser> customerUsers = new ArrayList<>();
                                    allUnderlineIdList.forEach(staffId -> {
                                        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(staffId);
                                        if (!ObjectUtil.isEmpty(rdmsCustomerUser)) {
                                            customerUsers.add(rdmsCustomerUser);
                                        }
                                    });

                                    for(RdmsCustomerUser customerUser: customerUsers) {
                                        RdmsPerformanceDto performanceDto = rdmsPerformanceService.calCurrentMonthPerformanceItem(customerUser.getId(), 0.3);
                                        rdmsPerformanceDtoList.add(performanceDto);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        responseDto.setContent(rdmsPerformanceDtoList);
        return responseDto;
    }

    @PostMapping("/listMonthlyPerformance")
    public ResponseDto<List<RdmsPerformanceDto>> listMonthlyPerformance(@RequestParam Date month, String customerId ) {
        ResponseDto<List<RdmsPerformanceDto>> responseDto = new ResponseDto<>();
        // 创建一个SimpleDateFormat对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 将Date对象格式化成字符串
        String monthlyStr = sdf.format(month);

        List<RdmsPerformanceDto> rdmsPerformanceDtoList = new ArrayList<>();
        //判断用户是不是IPMT或者SuperVisor
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        if(!ObjectUtils.isEmpty(rdmsCustomer)){
            List<RdmsCustomerUser> customerUsers = rdmsCustomerUserService.listAllWorkers(customerId);
            for(RdmsCustomerUser customerUser: customerUsers) {
                RdmsPerformance monthlyPerformanceItem = rdmsPerformanceService.getMonthlyPerformanceItem(customerUser.getId(), monthlyStr);
                RdmsPerformanceDto performanceDto = CopyUtil.copy(monthlyPerformanceItem, RdmsPerformanceDto.class);
                if(!ObjectUtils.isEmpty(performanceDto)){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(performanceDto.getCustomerUserId());
                    performanceDto.setCustomerUserName(rdmsCustomerUser.getTrueName());
                    performanceDto.setJobNum(rdmsCustomerUser.getJobNum());
                }
                rdmsPerformanceDtoList.add(performanceDto);
            }
        }
        if(!CollectionUtils.isEmpty(rdmsPerformanceDtoList)){
            List<RdmsPerformanceDto> performanceDtoList = rdmsPerformanceDtoList.stream().filter(rdmsPerformanceDto -> !ObjectUtils.isEmpty(rdmsPerformanceDto)
                    && !ObjectUtils.isEmpty(rdmsPerformanceDto.getYearMonth())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(performanceDtoList)){
                List<RdmsPerformanceDto> collect = performanceDtoList.stream().sorted(Comparator.comparing(RdmsPerformanceDto::getPerformance).reversed()).collect(Collectors.toList());
                responseDto.setContent(collect);
            }else{
                responseDto.setContent(null);
            }
        }else{
            responseDto.setContent(null);
        }

        return responseDto;
    }

    @PostMapping("/createPerformanceRecord/{customerUserId}")
    public ResponseDto<RdmsPerformanceDto> createPerformanceRecord(@PathVariable String customerUserId) throws ParseException {
        ResponseDto<RdmsPerformanceDto> responseDto = new ResponseDto<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatStr = simpleDateFormat.format(new Date());

        RdmsPerformance performance = rdmsPerformanceService.getWorkerLastMonthPerformanceItem(customerUserId, dateFormatStr);
        RdmsPerformanceDto rdmsPerformanceDto = CopyUtil.copy(performance, RdmsPerformanceDto.class);
        responseDto.setContent(rdmsPerformanceDto);
        return responseDto;
    }

    @PostMapping("/getPerformanceListByUserId/{customerUserId}")
    public ResponseDto<List<RdmsPerformanceDto>> getPerformanceListByCustomerUserId(@PathVariable String customerUserId) {
        ResponseDto<List<RdmsPerformanceDto>> responseDto = new ResponseDto<>();
        List<RdmsPerformanceDto> performanceListByUserId = rdmsPerformanceService.getPerformanceListByCustomerUserId(customerUserId);
        responseDto.setContent(performanceListByUserId);
        return responseDto;
    }


}
