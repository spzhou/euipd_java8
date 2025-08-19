/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.api.ApiUserRegInfo;
import com.course.server.dto.rdms.*;
import com.course.server.mapper.RdmsCustomerUserJobLevelMapper;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private static final Logger LOG = LoggerFactory.getLogger(WorkerController.class);
    public static final String BUSINESS_NAME = "员工";

    @Resource
    private RdmsUserService rdmsUserService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsDepartmentService rdmsDepartmentService;
    @Resource
    private RdmsCustomerUserJobLevelMapper rdmsCustomerUserJobLevelMapper;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsIpmtService rdmsIpmtService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsBossService rdmsBossService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody RdmsWorkerPageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCustomerUserService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/searchByKeyWord")
    public ResponseDto<PageDto<RdmsCustomerUserDto>> searchByKeyWord(@RequestBody PageDto<RdmsCustomerUserDto> pageDto) {
        ResponseDto<PageDto<RdmsCustomerUserDto>> responseDto = new ResponseDto<>();
        rdmsCustomerUserService.searchByKeyWord(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/turnOver")
    public ResponseDto<String> turnOver(@RequestParam String customerId, String workerId, String newWorkerId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        String s = rdmsCustomerUserService.turnOver(customerId, workerId, newWorkerId);
        responseDto.setContent(s);
        return responseDto;
    }

    /**
     * 查询员工的工时费
     *
     * @param customerUserId
     * @return
     */
    @PostMapping("/getWorkerManhourInfo/{customerUserId}")
    public ResponseDto<RdmsHmiWorkerManhourDto> getWorkerManhourInfo(@PathVariable String customerUserId) {
        ResponseDto<RdmsHmiWorkerManhourDto> responseDto = new ResponseDto<>();
        RdmsHmiWorkerManhourDto workerManhourDto = new RdmsHmiWorkerManhourDto();
        RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);

        RdmsCustomerUserJobLevelExample jobLevelExample = new RdmsCustomerUserJobLevelExample();
        jobLevelExample.createCriteria()
                .andLevelCodeEqualTo(customerUser.getJobLevel());
        List<RdmsCustomerUserJobLevel> rdmsCustomerUserJobLevels = rdmsCustomerUserJobLevelMapper.selectByExample(jobLevelExample);
        workerManhourDto.setId(customerUser.getId());
        workerManhourDto.setJobLevel(customerUser.getJobLevel());
        workerManhourDto.setManHourFee(rdmsCustomerUserJobLevels.get(0).getManHourFee());

        responseDto.setContent(workerManhourDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    /**
     * 查询员工工作负荷
     *
     * @param customerUserId
     * @return
     */
    @PostMapping("/getWorkload/{customerUserId}")
    public ResponseDto<List<RdmsHmiWorkLoadDto>> getWorkload(@PathVariable String customerUserId) throws ParseException {
        ResponseDto<List<RdmsHmiWorkLoadDto>> responseDto = new ResponseDto<>();
        List<RdmsHmiWorkLoadDto> collectWorkLoadList = rdmsCustomerUserService.getCustomerUserWorkLoadData(customerUserId, null);
        responseDto.setContent(collectWorkLoadList);
        return responseDto;
    }

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/listAllWorkers/{customerId}")
    public ResponseDto<List<RdmsCustomerUserDto>> listAllWorkers(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUserDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllNormalWorkers(customerId);
        List<RdmsCustomerUserDto> rdmsCustomerUserDtos = CopyUtil.copyList(rdmsCustomerUsers, RdmsCustomerUserDto.class);

        for (RdmsCustomerUserDto rdmsCustomerUserDto : rdmsCustomerUserDtos) {
            if (rdmsCustomerUserDto.getCustomerId() != null) {
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(rdmsCustomerUserDto.getCustomerId());
                rdmsCustomerUserDto.setCustomerName(rdmsCustomer.getCustomerName());
            }

            if (rdmsCustomerUserDto.getDepartmentId() != null) {
                RdmsDepartment rdmsDepartment = rdmsDepartmentService.selectByPrimaryKey(rdmsCustomerUserDto.getDepartmentId());
                rdmsCustomerUserDto.setDepartmentName(rdmsDepartment.getDepartName());
            }

            //对日期时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (rdmsCustomerUserDto.getCreateTime() != null) {
                rdmsCustomerUserDto.setCreateTimeStr(sdf.format(rdmsCustomerUserDto.getCreateTime()));
            }
            if (rdmsCustomerUserDto.getJoinTime() != null) {
                rdmsCustomerUserDto.setJoinTimeStr(sdf.format(rdmsCustomerUserDto.getJoinTime()));
            }
            if (rdmsCustomerUserDto.getBirthday() != null) {
                rdmsCustomerUserDto.setBirthdayStr(sdf.format(rdmsCustomerUserDto.getBirthday()));
            }

            List<RdmsProjectSubproject> subprojectListByIpmt = rdmsSubprojectService.getSubprojectListByIpmt(rdmsCustomerUserDto.getId());
            if (!CollectionUtils.isEmpty(subprojectListByIpmt)) {
                rdmsCustomerUserDto.setIpmtTransferDisable(true);
            } else {
                rdmsCustomerUserDto.setIpmtTransferDisable(false);
            }
            List<RdmsProjectSubproject> subprojectListByPdm = rdmsSubprojectService.getSubprojectListByPdm(rdmsCustomerUserDto.getId());
            if (!CollectionUtils.isEmpty(subprojectListByPdm)) {
                rdmsCustomerUserDto.setPdmTransferDisable(true);
            } else {
                rdmsCustomerUserDto.setPdmTransferDisable(false);
            }
            List<RdmsProjectSubproject> subprojectListByPjm = rdmsSubprojectService.getSubprojectListByPjm(rdmsCustomerUserDto.getId());
            if (!CollectionUtils.isEmpty(subprojectListByPjm)) {
                rdmsCustomerUserDto.setPjmTransferDisable(true);
            } else {
                rdmsCustomerUserDto.setPjmTransferDisable(false);
            }
        }
        responseDto.setContent(rdmsCustomerUserDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    @PostMapping("/getSuperInfo/{customerId}")
    public ResponseDto<RdmsCustomerUserDto> getSuperInfo(@PathVariable String customerId) {
        ResponseDto<RdmsCustomerUserDto> responseDto = new ResponseDto<>();
        RdmsCustomerUser superviseUserInfo = rdmsCustomerUserService.getAdminUserInfo(customerId);
        RdmsCustomerUserDto copy = CopyUtil.copy(superviseUserInfo, RdmsCustomerUserDto.class);
        responseDto.setContent(copy);
        return responseDto;
    }

    @PostMapping("/getBossInfo/{customerId}")
    public ResponseDto<RdmsBossDto> getBossInfo(@PathVariable String customerId) {
        ResponseDto<RdmsBossDto> responseDto = new ResponseDto<>();
        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
        if(ObjectUtils.isEmpty(bossByCustomerId)){
            //将super设置为boss
            RdmsCustomerUser superviseUserInfo = rdmsCustomerUserService.getAdminUserInfo(customerId);
            RdmsBossDto bossDto = new RdmsBossDto();
            bossDto.setBossId(superviseUserInfo.getId());
            bossDto.setCustomerId(customerId);
            bossDto.setId(UuidUtil.getShortUuid());
            rdmsBossService.save(bossDto);
            responseDto.setContent(bossDto);
        }else{
            responseDto.setContent(bossByCustomerId);
        }

        return responseDto;
    }

    @PostMapping("/listWorkers")
    public ResponseDto<List<RdmsCustomerUserDto>> listUnderlineWorkers(@RequestParam String customerUserId, String customerId) {
        ResponseDto<List<RdmsCustomerUserDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUserDto> rdmsCustomerUserDtos = new ArrayList<>();
        //判断用户是不是IPMT或者SuperVisor
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        if (!ObjectUtils.isEmpty(rdmsCustomer)) {
            List<RdmsCustomerUser> customerUserList = rdmsCustomerUserService.findCustomerUserByLoginName(rdmsCustomer.getContactPhone());
            if (!CollectionUtils.isEmpty(customerUserList)) {
                if (customerUserList.get(0).getId().equals(customerUserId)) {
                    //Super :列出公司所有员工
                    List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllNormalWorkers(customerId);
                    rdmsCustomerUserDtos = CopyUtil.copyList(rdmsCustomerUsers, RdmsCustomerUserDto.class);
                } else {
                    List<String> ipmtIdList = rdmsIpmtService.listByCustomerId(customerId);
                    if (!CollectionUtils.isEmpty(ipmtIdList)) {
                        if (ipmtIdList.contains(customerUserId)) {
                            //IPMT :列出公司所有员工
                            List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllNormalWorkers(customerId);
                            rdmsCustomerUserDtos = CopyUtil.copyList(rdmsCustomerUsers, RdmsCustomerUserDto.class);
                        } else {
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
                                    rdmsCustomerUserDtos = CopyUtil.copyList(customerUsers, RdmsCustomerUserDto.class);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (RdmsCustomerUserDto rdmsCustomerUserDto : rdmsCustomerUserDtos) {
            if (rdmsCustomerUserDto.getCustomerId() != null) {
                RdmsCustomer rdmsCustomer1 = rdmsCustomerService.selectByPrimaryKey(rdmsCustomerUserDto.getCustomerId());
                rdmsCustomerUserDto.setCustomerName(rdmsCustomer1.getCustomerName());
            }

            if (rdmsCustomerUserDto.getDepartmentId() != null) {
                RdmsDepartment rdmsDepartment = rdmsDepartmentService.selectByPrimaryKey(rdmsCustomerUserDto.getDepartmentId());
                rdmsCustomerUserDto.setDepartmentName(rdmsDepartment.getDepartName());
            }

            //对日期时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (rdmsCustomerUserDto.getCreateTime() != null) {
                rdmsCustomerUserDto.setCreateTimeStr(sdf.format(rdmsCustomerUserDto.getCreateTime()));
            }
            if (rdmsCustomerUserDto.getJoinTime() != null) {
                rdmsCustomerUserDto.setJoinTimeStr(sdf.format(rdmsCustomerUserDto.getJoinTime()));
            }
            if (rdmsCustomerUserDto.getBirthday() != null) {
                rdmsCustomerUserDto.setBirthdayStr(sdf.format(rdmsCustomerUserDto.getBirthday()));
            }

            List<RdmsProjectSubproject> subprojectListByIpmt = rdmsSubprojectService.getSubprojectListByIpmt(rdmsCustomerUserDto.getId());
            if (!CollectionUtils.isEmpty(subprojectListByIpmt)) {
                rdmsCustomerUserDto.setIpmtTransferDisable(true);
            } else {
                rdmsCustomerUserDto.setIpmtTransferDisable(false);
            }
            List<RdmsProjectSubproject> subprojectListByPdm = rdmsSubprojectService.getSubprojectListByPdm(rdmsCustomerUserDto.getId());
            if (!CollectionUtils.isEmpty(subprojectListByPdm)) {
                rdmsCustomerUserDto.setPdmTransferDisable(true);
            } else {
                rdmsCustomerUserDto.setPdmTransferDisable(false);
            }
            List<RdmsProjectSubproject> subprojectListByPjm = rdmsSubprojectService.getSubprojectListByPjm(rdmsCustomerUserDto.getId());
            if (!CollectionUtils.isEmpty(subprojectListByPjm)) {
                rdmsCustomerUserDto.setPjmTransferDisable(true);
            } else {
                rdmsCustomerUserDto.setPjmTransferDisable(false);
            }
        }
        responseDto.setContent(rdmsCustomerUserDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/listAllWorkersByPreProjectId/{preProjectId}")
    public ResponseDto<List<RdmsHmiExecutorPlainDto>> listAllWorkersByPreProjectId(@PathVariable String preProjectId) {
        ResponseDto<List<RdmsHmiExecutorPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllWorkersByPreProjectId(preProjectId);

        //重要代码
        List<RdmsHmiExecutorPlainDto> executorPlainDtos = rdmsCustomerUsers.stream().map(e -> new RdmsHmiExecutorPlainDto(e.getId(), e.getTrueName())).collect(Collectors.toList());

        responseDto.setContent(executorPlainDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/listAllWorkersByJobitemId/{jobitemId}")
    public ResponseDto<List<RdmsHmiExecutorPlainDto>> listAllWorkersByJobitemId(@PathVariable String jobitemId) {
        ResponseDto<List<RdmsHmiExecutorPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllWorkersByJobitemId(jobitemId);

        //重要代码
        List<RdmsHmiExecutorPlainDto> executorPlainDtos = rdmsCustomerUsers.stream().map(e -> new RdmsHmiExecutorPlainDto(e.getId(), e.getTrueName())).collect(Collectors.toList());

        responseDto.setContent(executorPlainDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/listAllWorkersByCharacterId/{characterId}")
    public ResponseDto<List<RdmsHmiExecutorPlainDto>> listAllWorkersByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsHmiExecutorPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllWorkersByCharacterId(characterId);

        //重要代码
        List<RdmsHmiExecutorPlainDto> executorPlainDtos = rdmsCustomerUsers.stream().map(e -> new RdmsHmiExecutorPlainDto(e.getId(), e.getTrueName())).collect(Collectors.toList());

        responseDto.setContent(executorPlainDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/listAllWorkersByProjectId/{projectId}")
    public ResponseDto<List<RdmsHmiExecutorPlainDto>> listAllWorkersByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsHmiExecutorPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllWorkersByProjectId(projectId);

        //重要代码
        List<RdmsHmiExecutorPlainDto> executorPlainDtos = rdmsCustomerUsers.stream().map(e -> new RdmsHmiExecutorPlainDto(e.getId(), e.getTrueName())).collect(Collectors.toList());

        responseDto.setContent(executorPlainDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    /**
     * 列表查询公司所有员工
     */
    @PostMapping("/listAllWorkersBySubProjectId/{subprojectId}")
    public ResponseDto<List<RdmsHmiExecutorPlainDto>> listAllWorkersBySubProjectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsHmiExecutorPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserService.listAllWorkersBySubProjectId(subprojectId);

        //重要代码
        List<RdmsHmiExecutorPlainDto> executorPlainDtos = rdmsCustomerUsers.stream().map(e -> new RdmsHmiExecutorPlainDto(e.getId(), e.getTrueName())).collect(Collectors.toList());

        responseDto.setContent(executorPlainDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询所有员工成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto save(@RequestBody RdmsCustomerUserDto customerUserDto) throws Exception {
        ApiUserRegInfo userRegInfo = new ApiUserRegInfo();
        userRegInfo.setCustomerId(customerUserDto.getCustomerId());
        userRegInfo.setJobNum(customerUserDto.getJobNum());
        userRegInfo.setLoginName(customerUserDto.getLoginName());
        userRegInfo.setTrueName(customerUserDto.getTrueName());
        userRegInfo.setSubject(customerUserDto.getSubject());
        userRegInfo.setDegree(customerUserDto.getDegree());
        userRegInfo.setEducationBackground(customerUserDto.getEducationBackground());
        userRegInfo.setJobPosition(customerUserDto.getJobPosition());
        userRegInfo.setTitle(customerUserDto.getTitle());
        userRegInfo.setJobLevel(customerUserDto.getJobLevel());
        userRegInfo.setJoinTime(customerUserDto.getJoinTime());
        userRegInfo.setDescription(customerUserDto.getDescription());
        userRegInfo.setOfficeEmail(customerUserDto.getOfficeEmail());
        userRegInfo.setOfficeTel(customerUserDto.getOfficeTel());
        userRegInfo.setOfficeAddress(customerUserDto.getOfficeAddress());
        userRegInfo.setBirthday(customerUserDto.getBirthday());
        String jsonString = JSON.toJSONString(userRegInfo);


        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(customerUserDto.getCustomerId(), "机构ID");
        ValidatorUtil.require(customerUserDto.getLoginName(), "登录名");
        ValidatorUtil.require(customerUserDto.getTrueName(), "姓名");
        ValidatorUtil.require(customerUserDto.getSubject(), "学科专业");
        ValidatorUtil.require(customerUserDto.getEducationBackground(), "学历");
//        ValidatorUtil.require(customerUserDto.getDegree(), "学位");
//        ValidatorUtil.require(customerUserDto.getDescription(), "能力描述");
//        ValidatorUtil.require(customerUserDto.getJobLevel(), "职级代码");
        ValidatorUtil.require(customerUserDto.getTitle(), "职务");
        ValidatorUtil.require(customerUserDto.getJoinTime(), "入职时间");
        ValidatorUtil.require(customerUserDto.getStatus(), "状态");

        ValidatorUtil.length(customerUserDto.getLoginName(), "登录名称", 11, 11);
        ValidatorUtil.length(customerUserDto.getTrueName(), "真实姓名", 2, 4);
        ValidatorUtil.length(customerUserDto.getSubject(), "学科专业", 2, 20);
        ValidatorUtil.length(customerUserDto.getEducationBackground(), "学历", 2, 20);
//        ValidatorUtil.length(customerUserDto.getDegree(), "学位", 2, 20);
//        ValidatorUtil.length(customerUserDto.getDescription(), "能力描述", 10, 500);
        ValidatorUtil.length(customerUserDto.getStatus(), "状态", 2, 20);

        if (ObjectUtils.isEmpty(customerUserDto.getJobLevel())) {
            customerUserDto.setJobLevel("P4");
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

    @PostMapping("/saveWorkers")
    @Transactional
    public ResponseDto<Integer> saveWorkers(@RequestBody List<RdmsCustomerUserDto> customerUserDtoList) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        int num = 0;
        if (!CollectionUtils.isEmpty(customerUserDtoList)) {
            for (RdmsCustomerUserDto customerUserDto : customerUserDtoList) {
                RdmsCustomerUser customerUserByCustomerIdAndLoginName = rdmsCustomerUserService.getCustomerUserByCustomerIdAndLoginName(customerUserDto.getCustomerId(), customerUserDto.getLoginName());
                if (ObjectUtils.isEmpty(customerUserByCustomerIdAndLoginName)) {
                    customerUserDto.setId(UuidUtil.getShortUuid());
                } else {
                    customerUserDto.setId(customerUserByCustomerIdAndLoginName.getId());
                }
                RdmsUserDto userDto = CopyUtil.copy(customerUserDto, RdmsUserDto.class);
                String userId = rdmsUserService.save(userDto);
                customerUserDto.setUserId(userId);
                rdmsCustomerUserService.save(customerUserDto);
                num++;
            }
        }
        responseDto.setContent(num);
        return responseDto;
    }

    /**
     * 根据登录名称进行查询
     */
    @PostMapping("/login-search")
    @Transactional
    public ResponseDto loginNameSearch(@RequestBody RdmsCustomerUserDto customerUserDto) {
        ValidatorUtil.require(customerUserDto.getLoginName(), "登录名称");
        ResponseDto responseDto = new ResponseDto();

        List<RdmsCustomerUserDto> customerUserDtos = new ArrayList<>();
        //先判读customer_user中是不是有这个user,如果没有,再到公共库中去查找

        List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByLoginName(customerUserDto.getLoginName());
        if (!CollectionUtils.isEmpty(customerUserByLoginName)) {
            RdmsCustomerUser customerUser = customerUserByLoginName.get(0);
            //不能通过电话号码查其他公司的人
            if (customerUser.getCustomerId().equals(customerUserDto.getCustomerId())) {
                RdmsCustomerUserDto rdmsCustomerUserDto = CopyUtil.copy(customerUser, RdmsCustomerUserDto.class);
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerUserDto.getCustomerId());
                rdmsCustomerUserDto.setCustomerName(rdmsCustomer.getCustomerName());
                responseDto.setContent(rdmsCustomerUserDto);
                return responseDto;
            }
        }

        //判断用户是否存在
        List<RdmsUser> users = rdmsUserService.searchUserByloginName(customerUserDto.getLoginName());
//        List<RdmsCustomerUserDto> customerUserDtosTemp = new ArrayList<>();

        if (!CollectionUtils.isEmpty(users)) {
            customerUserDtos = CopyUtil.copyList(users, RdmsCustomerUserDto.class);
            for (RdmsCustomerUserDto rdmsCustomerUserDto : customerUserDtos) {
                //List<RdmsUser> userList = users.stream().filter(user -> Objects.equals(user.getLoginName(), rdmsCustomerUserDto.getLoginName())).collect(Collectors.toList());
                rdmsCustomerUserDto.setUserId(rdmsCustomerUserDto.getId()); //因为查询的是user表,所以复制过来是ID没有直接赋值给userID,所以,作此处理
                rdmsCustomerUserDto.setId(null);
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerUserDto.getCustomerId());
                rdmsCustomerUserDto.setCustomerName(rdmsCustomer.getCustomerName());
            }

            responseDto.setContent(customerUserDtos.get(0));
            return responseDto;
        }
        responseDto.setContent(null);
        responseDto.setSuccess(false);
        responseDto.setMessage("记录不存在!");
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCustomerUserService.deleteByWorkerId(id);
//        rdmsUserService.delete(id);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/listWorker")
    public ResponseDto listWorker(@RequestBody RdmsResponseDto respDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsUserService.listWorker(respDto);
        responseDto.setContent(respDto);
        return responseDto;
    }

    /**
     * 根据customerUserID获取一个工作人员信息
     */
    @PostMapping("/get-worker-info/{customerUserId}")
    public ResponseDto<RdmsCustomerUserDto> getWorkerInfo(@PathVariable String customerUserId) {
        ResponseDto<RdmsCustomerUserDto> responseDto = new ResponseDto();
        RdmsCustomerUser customerUserInfo = rdmsCustomerUserService.getCustomerUserInfo(customerUserId);
        RdmsCustomerUserDto copy = CopyUtil.copy(customerUserInfo, RdmsCustomerUserDto.class);
        responseDto.setContent(copy);
        return responseDto;
    }


}
