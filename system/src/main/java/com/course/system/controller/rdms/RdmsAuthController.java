/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.alibaba.fastjson.JSON;

import com.course.server.domain.RdmsAuth;
import com.course.server.domain.RdmsCustomer;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.WorkerStatusEnum;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth2")
public class RdmsAuthController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsAuthController.class);
    public static final String BUSINESS_NAME = "接口授权登录";

    @Resource
    private RdmsAuthService rdmsAuthService;
    @Resource
    private RdmsUserService rdmsUserService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;

    /**
     * 根据客户的ID获取相应客户的授权信息列表
     * @param customerId
     * @return
     */
    @PostMapping("/getAccessByCustomerId/{customerId}")
    @Transactional
    public ResponseDto<List<RdmsAuthInfoDto>> getAuthByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsAuthInfoDto>> responseDto = new ResponseDto<>();
        List<RdmsAuth> accessByCustomerId = rdmsAuthService.getAuthByCustomerId(customerId);
        List<RdmsAuthInfoDto> rdmsAuthDtos = CopyUtil.copyList(accessByCustomerId, RdmsAuthInfoDto.class);
        responseDto.setContent(rdmsAuthDtos);
        return responseDto;
    }

    /**
     * 保存一条客户授权信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsAuth> save(@RequestBody RdmsAuthDto authDto) throws Exception {
        ResponseDto<RdmsAuth> responseDto = new ResponseDto<>();
        // 保存校验
        ValidatorUtil.require(authDto.getName(), "名称");
        ValidatorUtil.length(authDto.getName(), "名称", 1, 50);
        if(!authDto.getNote().isEmpty()){
            ValidatorUtil.length(authDto.getName(), "备注", 1, 500);
        }
        RdmsAuth copy = CopyUtil.copy(authDto, RdmsAuth.class);
        RdmsAuth save = rdmsAuthService.save(copy);
        responseDto.setContent(save);
        return responseDto;
    }

    /**
     * 删除一条客户授权信息
     * @param accessKey
     * @return
     */
    @DeleteMapping("/del/{accessKey}")
    public ResponseDto<String> delete(@PathVariable String accessKey) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        rdmsAuthService.delete(accessKey);
        return responseDto;
    }


    /**
     * 根据AccessKey和AccessSecret获取认证令牌
     * 验证客户端提供的访问凭证，返回对应的认证信息
     * 
     * @param tokenRequestDto 包含AccessKey和AccessSecret的令牌请求对象
     * @return 返回认证信息，如果验证失败则返回错误信息
     */
    @PostMapping("/getToken")
    public ResponseDto<RdmsAuthDto> getToken(@RequestBody RdmsTokenRequestDto tokenRequestDto) {
        ResponseDto<RdmsAuthDto> responseDto = new ResponseDto<>();

        // 验证 AccessKey 和 AccessSecret
        if (ObjectUtils.isEmpty(tokenRequestDto.getAccessKey()) || ObjectUtils.isEmpty(tokenRequestDto.getAccessSecret())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("AccessKey 或 AccessSecret 不能为空");
            return responseDto;
        }
        RdmsAuthDto authDto = rdmsAuthService.getToken(tokenRequestDto.getAccessKey(), tokenRequestDto.getAccessSecret());
        responseDto.setContent(authDto);
        return responseDto;
    }

    /**
     * 提供一个API接口测试函数
     * @param customerId
     * @return
     */
    @PostMapping("/api/getAuthTestInfo/{customerId}")
    public ResponseDto<String> getCustomerInfo(@PathVariable String customerId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setContent("接口调用成功,用户ID:" + customerId);
        return responseDto;
    }

    /**
     * OA流程处理完成后,返回一个结构体,本服务根据状态对后台数据进行操作
     */
    @PostMapping("/api/feedbackWorkflowStatus")
    public ResponseDto<String> feedbackWorkflowStatus(@RequestBody RdmsAuthWorkflowStatusDto workflowStatus) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if(!(ObjectUtils.isEmpty(workflowStatus) || ObjectUtils.isEmpty(workflowStatus.getCode()))){
            rdmsMaterialManageService.workflowBudgetDealwith(workflowStatus);
        }
        responseDto.setContent("接口调用成功");
        return responseDto;
    }

    /**
     * OA流程物料退库处理完成后,返回一个结构体,本服务根据状态对后台数据进行操作
     */
    @PostMapping("/api/feedbackReturnWarehouseApprovalStatus")
    public ResponseDto<String> feedbackReturnWarehouseApprovalStatus(@RequestBody RdmsAuthWorkflowStatusDto workflowStatus) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if(!(ObjectUtils.isEmpty(workflowStatus) || ObjectUtils.isEmpty(workflowStatus.getCode()))){
            rdmsMaterialManageService.workflowReturnWarehouseDealwith(workflowStatus);
        }
        responseDto.setContent("接口调用成功");
        return responseDto;
    }

    /**
     * 从OA发起创建新用户的程序接口
     */
    @PostMapping("/api/createUser")
    public ResponseDto<RdmsCustomerUserDto> createUser(@RequestBody RdmsCustomerUserDto customerUserDto) {
        ResponseDto<RdmsCustomerUserDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(customerUserDto.getCustomerId(), "机构ID");
        ValidatorUtil.require(customerUserDto.getJobNum(), "工号");
        ValidatorUtil.require(customerUserDto.getLoginName(), "登录名");
        ValidatorUtil.require(customerUserDto.getTrueName(), "姓名");
        ValidatorUtil.require(customerUserDto.getSubject(), "学科专业");
        ValidatorUtil.require(customerUserDto.getEducationBackground(), "学历");
        ValidatorUtil.require(customerUserDto.getDegree(), "学位");
        ValidatorUtil.require(customerUserDto.getDescription(), "能力描述");
//        ValidatorUtil.require(customerUserDto.getJobLevel(), "职级代码");
        ValidatorUtil.require(customerUserDto.getTitle(), "职务");
        ValidatorUtil.require(customerUserDto.getJoinTime(), "入职时间");
//        ValidatorUtil.require(customerUserDto.getStatus(), "状态");

        ValidatorUtil.length(customerUserDto.getLoginName(), "登录名称", 11, 11);
        ValidatorUtil.length(customerUserDto.getTrueName(), "真实姓名", 2, 4);
        ValidatorUtil.length(customerUserDto.getSubject(), "学科专业", 2, 20);
        ValidatorUtil.length(customerUserDto.getEducationBackground(), "学历", 2, 20);
        ValidatorUtil.length(customerUserDto.getDegree(), "学位", 2, 20);
        ValidatorUtil.length(customerUserDto.getDescription(), "能力描述", 2, 500);
//        ValidatorUtil.length(customerUserDto.getStatus(), "状态", 2, 20);

        if (ObjectUtils.isEmpty(customerUserDto.getJobLevel())) {
            customerUserDto.setJobLevel("P4");
        }else{
            if(customerUserDto.getJobLevel().equals("P4") || customerUserDto.getJobLevel().equals("p4")){
                customerUserDto.setJobLevel("P4");
            }else if(customerUserDto.getJobLevel().equals("P5") || customerUserDto.getJobLevel().equals("p5")){
                customerUserDto.setJobLevel("P5");
            }else if(customerUserDto.getJobLevel().equals("P6") || customerUserDto.getJobLevel().equals("p6")){
                customerUserDto.setJobLevel("P6");
            }else if(customerUserDto.getJobLevel().equals("P7") || customerUserDto.getJobLevel().equals("p7")){
                customerUserDto.setJobLevel("P7");
            }else if(customerUserDto.getJobLevel().equals("P8") || customerUserDto.getJobLevel().equals("p8")){
                customerUserDto.setJobLevel("P8");
            }else{
                customerUserDto.setJobLevel("P4");
            }
        }

        if (ObjectUtils.isEmpty(customerUserDto.getStatus())) {
            customerUserDto.setStatus(WorkerStatusEnum.WORKER_NORMAL.getStatus());
        }else{
            WorkerStatusEnum workerEnumByStatus = WorkerStatusEnum.getWorkerEnumByStatus(customerUserDto.getStatus());
            if(workerEnumByStatus == null){
                customerUserDto.setStatus(WorkerStatusEnum.WORKER_NORMAL.getStatus());
            }else{
                customerUserDto.setStatus(workerEnumByStatus.getStatus());
            }
        }

        //判断这个user是否已经在user表中,如果没有则创建这个user;如果有了,则不做任何改动
        LoginUserDto byLoginName = rdmsUserService.findByLoginName(customerUserDto.getLoginName());
        if (byLoginName == null) {
            RdmsUserDto userDto = CopyUtil.copy(customerUserDto, RdmsUserDto.class);
            String userId = rdmsUserService.save(userDto);
            customerUserDto.setUserId(userId);
            rdmsCustomerUserService.save(customerUserDto);
        } else {
            //首先向Customer_user表中进行保存
            customerUserDto.setUserId(byLoginName.getId());
            rdmsCustomerUserService.save(customerUserDto);
        }

        responseDto.setContent(customerUserDto);
        return responseDto;
    }


}
