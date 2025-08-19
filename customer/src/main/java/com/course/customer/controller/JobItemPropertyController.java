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
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jobitem-property")
public class JobItemPropertyController {
    private static final Logger LOG = LoggerFactory.getLogger(JobItemPropertyController.class);
    public static final String BUSINESS_NAME = "工单资产";


    @Resource
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsJobItemProcessService rdmsJobItemProcessService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsCharacterProcessService rdmsCharacterProcessService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private RdmsBudgetService rdmsBudgetService;
    @Autowired
    private RdmsJobitemAssociateService rdmsJobitemAssociateService;
    @Autowired
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsPreProjectService rdmsPreProjectService;
    @Autowired
    private RdmsTgmService rdmsTgmService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;
    @Autowired
    private RdmsCbbService rdmsCbbService;
    @Autowired
    private RdmsCharacterDataService rdmsCharacterDataService;


    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsJobItemPropertyDto>> list(@RequestBody PageDto<RdmsJobItemPropertyDto> pageDto) {
        ResponseDto<PageDto<RdmsJobItemPropertyDto>> responseDto = new ResponseDto<>();
        rdmsJobItemPropertyService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     *
     */
    @PostMapping("/getJobItemProperty")
    public ResponseDto<RdmsJobItemPropertyDto> getJobItemProperty(@RequestParam String jobitemId, String loginUserId) {
        ResponseDto<RdmsJobItemPropertyDto> responseDto = new ResponseDto<>();
        List<RdmsJobItemProperty> jobItemPropertyByJobItemId = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(jobitemId);
        if (!CollectionUtils.isEmpty(jobItemPropertyByJobItemId)) {
            List<RdmsJobItemPropertyDto> rdmsJobItemPropertyDtos = CopyUtil.copyList(jobItemPropertyByJobItemId, RdmsJobItemPropertyDto.class);
            if (!CollectionUtils.isEmpty(rdmsJobItemPropertyDtos)) {
                RdmsJobItemPropertyDto propertyDto = rdmsJobItemPropertyDtos.get(0);
                if (!ObjectUtils.isEmpty(propertyDto)) {
                    {
                        if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                            List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                            if (!CollectionUtils.isEmpty(stringList)) {
                                List<RdmsFileDto> fileDtos = new ArrayList<>();
                                for (String id : stringList) {
                                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                                    //读取访问权限
                                    if (!ObjectUtils.isEmpty(fileDto)) {
                                        //补充填写文件权限信息
                                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                        if (ObjectUtils.isEmpty(byFileId)) {
                                            fileDto.setAuthStatus(true);
                                        } else {
                                            String authIdsStr = byFileId.getAuthIdsStr();
                                            if (ObjectUtils.isEmpty(authIdsStr)) {
                                                fileDto.setAuthStatus(true);
                                            } else {
                                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                                if (strings.contains(loginUserId)) {
                                                    fileDto.setAuthStatus(true);
                                                } else {
                                                    fileDto.setAuthStatus(false);
                                                }
                                            }
                                        }
                                    }
                                    fileDtos.add(fileDto);
                                }
                                propertyDto.setFileItems(fileDtos);
                            }
                        }
                    }
                    {
                        if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                            List<String> stringList = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                            if (!CollectionUtils.isEmpty(stringList)) {
                                List<RdmsFileDto> fileDtos = new ArrayList<>();
                                for (String id : stringList) {
                                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                                    //读取访问权限
                                    if (!ObjectUtils.isEmpty(fileDto)) {
                                        //补充填写文件权限信息
                                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                        if (ObjectUtils.isEmpty(byFileId)) {
                                            fileDto.setAuthStatus(true);
                                        } else {
                                            String authIdsStr = byFileId.getAuthIdsStr();
                                            if (ObjectUtils.isEmpty(authIdsStr)) {
                                                fileDto.setAuthStatus(true);
                                            } else {
                                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                                if (strings.contains(loginUserId)) {
                                                    fileDto.setAuthStatus(true);
                                                } else {
                                                    fileDto.setAuthStatus(false);
                                                }
                                            }
                                        }
                                    }
                                    fileDtos.add(fileDto);
                                }
                                propertyDto.setReferenceFileList(fileDtos);
                            }
                        }
                    }
                }
                responseDto.setContent(propertyDto);
            }
            return responseDto;
        }
        responseDto.setContent(null);
        return responseDto;
    }


    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsJobItemPropertyDto> save(@RequestBody RdmsJobItemPropertyDto propertyDto) {
        ResponseDto<RdmsJobItemPropertyDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(propertyDto.getJobItemId(), "工单ID");
        ValidatorUtil.require(propertyDto.getJobDescription(), "工作描述");
        ValidatorUtil.require(propertyDto.getTestKeyPoints(), "测试关键点说明");

        ValidatorUtil.length(propertyDto.getJobDescription(), "工作描述", 10, 2500);
        ValidatorUtil.length(propertyDto.getTestKeyPoints(), "测试关键点说明", 10, 2500);

        RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);

        List<String> strings = JSON.parseArray(jobItemProperty.getFileListStr(), String.class);
        HashSet<String> objects = new HashSet<>(strings);
        List<String> list = new ArrayList<String>(objects);
        String jsonString = JSON.toJSONString(list);
        jobItemProperty.setFileListStr(jsonString);

        rdmsJobItemPropertyService.save(jobItemProperty);

        responseDto.setContent(propertyDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/saveJobitemProperty")
    @Transactional
    public ResponseDto<RdmsJobItemPropertyDto> saveJobitemProperty(@RequestBody RdmsJobItemPropertyDto propertyDto) {
        ResponseDto<RdmsJobItemPropertyDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(propertyDto.getJobItemId(), "工单ID");
        ValidatorUtil.require(propertyDto.getJobDescription(), "工作描述");
        ValidatorUtil.length(propertyDto.getJobDescription(), "工作描述", 10, 2500);

        RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
        if (!ObjectUtils.isEmpty(jobItem)) {
            if (!(jobItem.getType().equals(JobItemTypeEnum.REVIEW.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_COO.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_MILESTONE.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_PRO.getType()))) {
                ValidatorUtil.require(propertyDto.getTestKeyPoints(), "测试关键点说明");
                ValidatorUtil.length(propertyDto.getTestKeyPoints(), "测试关键点说明", 10, 2500);
            }
        }

        RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
        String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);
        //填写文件的访问权限人员
        List<String> fileIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
            List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
            fileIds.addAll(strings);
        }
        if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
            List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
            fileIds.addAll(strings);
        }
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                //如果是评审任务
                if (jobItem.getType().equals(JobItemTypeEnum.REVIEW.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_COO.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_MILESTONE.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_PRO.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                ) {
                    if (!ObjectUtils.isEmpty(jobItem.getProductManagerId())) {
                        roleUsersDto.setPdmId(jobItem.getProductManagerId());
                    }
                }
                //如果工单是Bug工单
                if (jobItem.getType().equals(JobItemTypeEnum.BUG.getType())) {
                    if (!ObjectUtils.isEmpty(jobItem.getParentJobitemId())) {
                        RdmsJobItem testJobitem = rdmsJobItemService.selectByPrimaryKey(jobItem.getParentJobitemId());
                        if (!ObjectUtils.isEmpty(testJobitem)) {
                            roleUsersDto.setExecutorId(testJobitem.getExecutorId());  //添加测试工单执行人
                            //被测工单
                            if (!ObjectUtils.isEmpty(testJobitem.getTestedJobitemId())) {
                                RdmsJobItem testedJobitem = rdmsJobItemService.selectByPrimaryKey(testJobitem.getTestedJobitemId());
                                if (!ObjectUtils.isEmpty(testedJobitem)) {
                                    roleUsersDto.setExecutorId(testedJobitem.getExecutorId());  //添加被测工单执行人
                                    if (testedJobitem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                        RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(testedJobitem.getParentJobitemId());
                                        if (!ObjectUtils.isEmpty(intJobitem)) {
                                            roleUsersDto.setExecutorId(intJobitem.getExecutorId());  //添加集成工单执行人
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //如果工单是test工单
                if (jobItem.getType().equals(JobItemTypeEnum.TEST.getType())) {
                    //被测工单
                    if (!ObjectUtils.isEmpty(jobItem.getTestedJobitemId())) {
                        RdmsJobItem testedJobitem = rdmsJobItemService.selectByPrimaryKey(jobItem.getTestedJobitemId());
                        if (!ObjectUtils.isEmpty(testedJobitem)) {
                            roleUsersDto.setExecutorId(testedJobitem.getExecutorId());  //添加被测工单执行人
                            if (testedJobitem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(testedJobitem.getParentJobitemId());
                                if (!ObjectUtils.isEmpty(intJobitem)) {
                                    roleUsersDto.setExecutorId(intJobitem.getExecutorId());  //添加集成工单执行人
                                }
                            }
                        }
                    }
                }

                //如果工单是Assist工单
                if (jobItem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                    RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(jobItem.getParentJobitemId());
                    if (!ObjectUtils.isEmpty(intJobitem)) {
                        roleUsersDto.setExecutorId(intJobitem.getExecutorId());  //添加集成工单执行人
                        if(intJobitem.getType().equals(JobItemTypeEnum.TASK_BUG.getType())) {
                            roleUsersDto.setTmId(intJobitem.getTestManagerId());
                            RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(jobItem.getCustomerId());
                            roleUsersDto.setTgmId(tgmByCustomerId.getTgmId());
                        }
                    }
                }
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(jobItem.getProjectManagerId());
                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }

        jobItem.setPropertyId(propertyId);
        rdmsJobItemService.update(jobItem);

        responseDto.setContent(propertyDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/submitJobitemProperty")
    @Transactional
    public ResponseDto submitJobitemProperty(@RequestBody RdmsJobItemPropertyDto propertyDto) {
        ResponseDto responseDto = new ResponseDto<>();

        ValidatorUtil.require(propertyDto.getJobItemId(), "工单ID");
        ValidatorUtil.require(propertyDto.getJobDescription(), "工作描述");
        ValidatorUtil.length(propertyDto.getJobDescription(), "工作描述", 10, 2500);

        RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
        if (!ObjectUtils.isEmpty(jobItem)) {
            if (!(jobItem.getType().equals(JobItemTypeEnum.REVIEW.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_COO.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_MILESTONE.getType())
                    || jobItem.getType().equals(JobItemTypeEnum.REVIEW_PRO.getType()))) {
                ValidatorUtil.require(propertyDto.getTestKeyPoints(), "测试关键点说明");
                ValidatorUtil.length(propertyDto.getTestKeyPoints(), "测试关键点说明", 10, 2500);
            }
        }

        RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
        String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);
        //填写文件的访问权限人员
        List<String> fileIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
            List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
            fileIds.addAll(strings);
        }
        if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
            List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
            fileIds.addAll(strings);
        }
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                //如果工单是Bug工单
                if (jobItem.getType().equals(JobItemTypeEnum.BUG.getType())) {
                    if (!ObjectUtils.isEmpty(jobItem.getParentJobitemId())) {
                        RdmsJobItem testJobitem = rdmsJobItemService.selectByPrimaryKey(jobItem.getParentJobitemId());
                        if (!ObjectUtils.isEmpty(testJobitem)) {
                            roleUsersDto.setExecutorId(testJobitem.getExecutorId());  //添加测试工单执行人
                            //被测工单
                            if (!ObjectUtils.isEmpty(testJobitem.getTestedJobitemId())) {
                                RdmsJobItem testedJobitem = rdmsJobItemService.selectByPrimaryKey(testJobitem.getTestedJobitemId());
                                if (!ObjectUtils.isEmpty(testedJobitem)) {
                                    roleUsersDto.setExecutorId(testedJobitem.getExecutorId());  //添加被测工单执行人
                                    if (testedJobitem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                        RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(testedJobitem.getParentJobitemId());
                                        if (!ObjectUtils.isEmpty(intJobitem)) {
                                            roleUsersDto.setExecutorId(intJobitem.getExecutorId());  //添加集成工单执行人
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //如果工单是test工单
                if (jobItem.getType().equals(JobItemTypeEnum.TEST.getType())) {
                    //被测工单
                    if (!ObjectUtils.isEmpty(jobItem.getTestedJobitemId())) {
                        RdmsJobItem testedJobitem = rdmsJobItemService.selectByPrimaryKey(jobItem.getTestedJobitemId());
                        if (!ObjectUtils.isEmpty(testedJobitem)) {
                            roleUsersDto.setExecutorId(testedJobitem.getExecutorId());  //添加被测工单执行人
                            if (testedJobitem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(testedJobitem.getParentJobitemId());
                                if (!ObjectUtils.isEmpty(intJobitem)) {
                                    roleUsersDto.setExecutorId(intJobitem.getExecutorId());  //添加集成工单执行人
                                }
                            }
                        }
                    }
                }

                //如果工单是Assist工单
                if (jobItem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                    RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(jobItem.getParentJobitemId());
                    if (!ObjectUtils.isEmpty(intJobitem)) {
                        roleUsersDto.setExecutorId(intJobitem.getExecutorId());  //添加集成工单执行人
                        if(intJobitem.getType().equals(JobItemTypeEnum.TASK_BUG.getType())) {
                            roleUsersDto.setTmId(intJobitem.getTestManagerId());
                            RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(jobItem.getCustomerId());
                            roleUsersDto.setTgmId(tgmByCustomerId.getTgmId());
                        }
                    }
                }
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(jobItem.getProjectManagerId());
                //如果是评审任务
                if (jobItem.getType().equals(JobItemTypeEnum.REVIEW.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_COO.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_MILESTONE.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.REVIEW_PRO.getType())
                        || jobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                ) {
                    if (!ObjectUtils.isEmpty(jobItem.getProductManagerId())) {
                        roleUsersDto.setPdmId(jobItem.getProductManagerId());
                        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(jobItem.getSubprojectId());
                        roleUsersDto.setIpmtId(rdmsProjectSubproject.getSupervise());
                    }
                }
                //如果是系统任务
                if (jobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType()) && !ObjectUtils.isEmpty(jobItem.getPreProjectId())) {
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItem.getPreProjectId());
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsPreProject.getCustomerId());
                        roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
                    }
                }

                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }

        //判断是否为关联任务
        Boolean jobitemAssocitateFlag = rdmsJobitemAssociateService.getJobitemAssocitateFlag(jobItem.getId());
        if (jobitemAssocitateFlag) {
            jobItem.setJobName(jobItem.getJobName() + "(关联)");
        }

        jobItem.setPropertyId(propertyId);
        jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
        jobItem.setActualSubmissionTime(new Date());
        String jobitemId = rdmsJobItemService.update(jobItem);

        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobitemId);
        jobItemProcess.setExecutorId(jobItem.getExecutorId());
        jobItemProcess.setNextNode(jobItem.getNextNode());
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        jobItemProcess.setJobDescription("任务提交");
        jobItemProcess.setFileListStr(null);
        jobItemProcess.setActualSubmissionTime(jobItem.getActualSubmissionTime());
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());
        rdmsJobItemProcessService.save(jobItemProcess);

        responseDto.setSuccess(true);
        responseDto.setMessage("提交成功");
        return responseDto;
    }


    /**
     * 提交评审结果
     * <p>
     * 执行人与NextNode
     * 1. 工单
     * 工单的执行人(executor)就是工单的提交(submit)人,也就是说谁提交谁就是执行人. 这个概念在评审工单的处理中,评审工单的最初状态就是 submit 所以,executor是当前的项目经理
     * nextNode是下一步的执行人,这里就是评审组长
     * <p>
     * 评审组长：
     * 1) 里程碑评审 	    组长：主项目经理 项目进度类评审
     * 2) 子项目评审	    组长：产品经理 可以协同系统工程部门一同参会
     * 3) 功能评审		组长：产品经理 可以协调系统工程部门一同参会
     * 4) 主项目评审	    组长：监管委员 最终验收
     * <p>
     * 2. 工单过程
     * 工单过程执行人是 当前的登录用户
     * 工单过程的nextNode和对应的工单的nextNode相同
     */
    @PostMapping("/submitReviewResult")
    @Transactional
    public ResponseDto submitReviewResult(@RequestBody RdmsJobItemPropertyDto propertyDto) {
        ResponseDto responseDto = new ResponseDto<>();

        ValidatorUtil.require(propertyDto.getJobItemId(), "工单ID");
        ValidatorUtil.require(propertyDto.getJobDescription(), "工作描述");
        ValidatorUtil.require(propertyDto.getReviewResult(), "评审结论");
        ValidatorUtil.length(propertyDto.getJobDescription(), "工作描述", 10, 2500);

        //查得被评审的工单
        RdmsJobItem reviewJobitem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());

        if (!ObjectUtils.isEmpty(reviewJobitem)) {
            if (!(reviewJobitem.getType().equals(JobItemTypeEnum.REVIEW.getType())
                    || reviewJobitem.getType().equals(JobItemTypeEnum.REVIEW_COO.getType())
                    || reviewJobitem.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())
                    || reviewJobitem.getType().equals(JobItemTypeEnum.REVIEW_MILESTONE.getType())
                    || reviewJobitem.getType().equals(JobItemTypeEnum.REVIEW_PRO.getType()))) {
                ValidatorUtil.require(propertyDto.getTestKeyPoints(), "测试关键点说明");
                ValidatorUtil.length(propertyDto.getTestKeyPoints(), "测试关键点说明", 10, 2500);
            }
        }

        ReviewResultEnum reviewResultEnum = ReviewResultEnum.getReviewResultEnumByNum(propertyDto.getReviewResult());
        switch (Objects.requireNonNull(reviewResultEnum)) {
            //2. 通过
            case PASS: {
                if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_MILESTONE.getType())) {
                    //里程碑评审

                    RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
                    //处理工单
                    {
                        //处理当前产品经理的review工单
                        String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);
                        //填写文件的访问权限人员
                        List<String> fileIds = new ArrayList<>();
                        if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                            List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                            fileIds.addAll(strings);
                        }
                        if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                            List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                            fileIds.addAll(strings);
                        }
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(reviewJobitem.getProjectManagerId());
                            roleUsersDto.setPdmId(reviewJobitem.getProductManagerId());
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }

                        //评审工单的处理
                        reviewJobitem.setPropertyId(propertyId);
//                    reviewJobitem.setNextNode(null);
                        reviewJobitem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                        reviewJobitem.setReviewResult(ReviewResultEnum.PASS.getStatus());
                        reviewJobitem.setActualSubmissionTime(new Date());
                        String jobitemId = rdmsJobItemService.update(reviewJobitem);

                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobitemId);
                        jobItemProcess.setExecutorId(reviewJobitem.getExecutorId());
                        jobItemProcess.setNextNode(reviewJobitem.getNextNode());
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        jobItemProcess.setJobDescription("评审通过");
                        jobItemProcess.setFileListStr(null);
                        jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                        rdmsJobItemProcessService.save(jobItemProcess);
                    }

                    //给所有"子项目"项目经理发评审通知工单
                    {
                        //获取所有子项目
                        List<RdmsProjectSubprojectDto> subProjectListByParentId = rdmsProjectService.getSubProjectListByParentId(propertyDto.getProjectId(), null);
                        if (!CollectionUtils.isEmpty(subProjectListByParentId)) {
                            for (RdmsProjectSubprojectDto subprojectDto : subProjectListByParentId) {
                                //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                                RdmsJobItem notifyJobitem = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                                notifyJobitem.setId(null);
                                notifyJobitem.setJobSerial(null);
                                notifyJobitem.setJobName("评审通过:" + reviewJobitem.getJobName());
                                notifyJobitem.setTaskDescription(jobItemProperty.getJobDescription());
                                notifyJobitem.setExecutorId(subprojectDto.getProjectManagerId());  //子项目的项目经理
                                notifyJobitem.setNextNode(null); //通知到了之后, 没有下一步执行人
                                notifyJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                                notifyJobitem.setReviewResult(ReviewResultEnum.PASS.getStatus());
                                notifyJobitem.setType(JobItemTypeEnum.REVIEW_NOTIFY.getType());
                                notifyJobitem.setParentJobitemId(reviewJobitem.getId());
                                notifyJobitem.setPropertyId(null);
                                notifyJobitem.setReviewWorkerIdStr(null);
                                notifyJobitem.setFileListStr(null);
                                Calendar referenceCalendar = Calendar.getInstance();
                                referenceCalendar.setTime(new Date());
                                referenceCalendar.add(Calendar.DATE, 3);
                                Date time_3 = referenceCalendar.getTime();
                                notifyJobitem.setPlanSubmissionTime(time_3);
                                notifyJobitem.setSubprojectId(subprojectDto.getId());
                                String jobitemId = rdmsJobItemService.save(notifyJobitem);

                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(jobitemId);
                                jobItemProcess.setExecutorId(notifyJobitem.getExecutorId());
                                jobItemProcess.setNextNode(notifyJobitem.getNextNode());
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                jobItemProcess.setJobDescription("里程碑评审通过");
                                jobItemProcess.setFileListStr(null);
                                jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                                rdmsJobItemProcessService.save(jobItemProcess);
                            }
                        }
                    }

                    //处理其他Review_coo工单状态
                    {
                        List<RdmsJobItem> jobitemListByTested = rdmsJobItemService.getJobitemListByTestedIdAndJobType(reviewJobitem.getId(), JobItemTypeEnum.REVIEW_COO.getType());
                        if (!CollectionUtils.isEmpty(jobitemListByTested)) {
                            for (RdmsJobItem itemDto : jobitemListByTested) {
//                            itemDto.setNextNode(null);
                                itemDto.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                                itemDto.setActualSubmissionTime(new Date());
                                String itemId = rdmsJobItemService.update(itemDto);

                                RdmsJobItemProcess itemProcess = new RdmsJobItemProcess();
                                itemProcess.setJobItemId(itemId);
                                itemProcess.setExecutorId(itemDto.getExecutorId());
                                itemProcess.setNextNode(itemDto.getNextNode());
                                long itemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(itemId);
                                itemProcess.setDeep((int) itemProcessCount);
                                itemProcess.setJobDescription("联合评审工单提交");
                                itemProcess.setFileListStr(null);
                                itemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                                itemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                                rdmsJobItemProcessService.save(itemProcess);
                            }
                        }
                    }
                } else {
                    //不是里程碑评审
                    RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
                    rdmsManhourService.saveBudgetExeInfo(propertyDto, reviewResultEnum);
                    //处理工单
                    {
                        //处理当前产品经理的review工单
                        String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);
                        //填写文件的访问权限人员
                        List<String> fileIds = new ArrayList<>();
                        if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                            List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                            fileIds.addAll(strings);
                        }
                        if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                            List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                            fileIds.addAll(strings);
                        }
                        if (!CollectionUtils.isEmpty(fileIds)) {
                            {
                                //设置文件授权 权限
                                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                                roleUsersDto.setReceiverId(null);
                                RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
                                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                roleUsersDto.setSuperId(null);
                                roleUsersDto.setIpmtId(null);
                                roleUsersDto.setPjmId(reviewJobitem.getProjectManagerId());
                                roleUsersDto.setPdmId(reviewJobitem.getProductManagerId());
                                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                            }
                        }
                        //评审工单处理
                        reviewJobitem.setPropertyId(propertyId);
//                    reviewJobitem.setNextNode(null);
                        reviewJobitem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                        reviewJobitem.setReviewResult(ReviewResultEnum.PASS.getStatus());
                        reviewJobitem.setActualSubmissionTime(new Date());
                        String jobitemId = rdmsJobItemService.update(reviewJobitem);

                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobitemId);
                        jobItemProcess.setExecutorId(reviewJobitem.getExecutorId());
                        jobItemProcess.setNextNode(reviewJobitem.getNextNode());
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        jobItemProcess.setJobDescription("评审通过");
                        jobItemProcess.setFileListStr(null);
                        jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                        rdmsJobItemProcessService.save(jobItemProcess);
                    }

                    if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(reviewJobitem.getCharacterId());
                        RdmsCharacterDto rdmsCharacterDto = CopyUtil.copy(character, RdmsCharacterDto.class);
                        rdmsCharacterDto.setLoginUserId(propertyDto.getLoginUserId());
                        rdmsCharacterService.appendRecordSimpleInfo(rdmsCharacterDto);
                        if (!rdmsCharacterDto.getRdType().equals(RdTypeEnum.MODULE.getType())) {
                            //判断被评审的项目是否为主项目, 如果是主项目通过评审, 需要处理project表中的项目状态
                            if (reviewJobitem.getProjectId().equals(reviewJobitem.getSubprojectId())) {
                                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(reviewJobitem.getProjectId());
                                rdmsProject.setStatus(ProjectStatusEnum.COMPLETE.getStatus());
                                rdmsProjectService.update(rdmsProject);
                            }
                        }
                    } else {
                        //判断被评审的项目是否为主项目, 如果是主项目通过评审, 需要处理project表中的项目状态
                        if (reviewJobitem.getProjectId().equals(reviewJobitem.getSubprojectId())) {
                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(reviewJobitem.getProjectId());
                            rdmsProject.setStatus(ProjectStatusEnum.COMPLETE.getStatus());
                            rdmsProjectService.update(rdmsProject);

                            //将预算审批条目设置为完成状态
                            RdmsBudget budgetItemByProjectId = rdmsBudgetService.getBudgetItemByProjectId(rdmsProject.getId());
                            if(!ObjectUtils.isEmpty(budgetItemByProjectId)){
                                budgetItemByProjectId.setStatus(BudgetStatusEnum.COMPLETE.getStatus());
                                rdmsBudgetService.update(budgetItemByProjectId);
                            }
                        }
                    }

                    {
                        //给项目经理发评审通知工单
                        //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                        RdmsJobItem notifyJobitem = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                        notifyJobitem.setId(null);
                        notifyJobitem.setJobSerial(null);
                        notifyJobitem.setJobName("评审通过:" + reviewJobitem.getJobName());
                        notifyJobitem.setTaskDescription(jobItemProperty.getJobDescription());
                        notifyJobitem.setExecutorId(reviewJobitem.getExecutorId());  //评审工单的执行人, 实际上就是当前评审子项目的项目经理
                        notifyJobitem.setNextNode(null); //通知到了之后, 没有下一步执行人
                        notifyJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                        notifyJobitem.setReviewResult(ReviewResultEnum.PASS.getStatus());
                        notifyJobitem.setType(JobItemTypeEnum.REVIEW_NOTIFY.getType());
                        notifyJobitem.setParentJobitemId(reviewJobitem.getId());
                        notifyJobitem.setPropertyId(null);
                        notifyJobitem.setReviewWorkerIdStr(null);
                        notifyJobitem.setFileListStr(null);
                        Calendar referenceCalendar = Calendar.getInstance();
                        referenceCalendar.setTime(new Date());
                        referenceCalendar.add(Calendar.DATE, 3);
                        Date time_3 = referenceCalendar.getTime();
                        notifyJobitem.setPlanSubmissionTime(time_3);
                        String jobitemId = rdmsJobItemService.save(notifyJobitem);

                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobitemId);
                        jobItemProcess.setExecutorId(notifyJobitem.getExecutorId());
                        jobItemProcess.setNextNode(notifyJobitem.getNextNode());
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        jobItemProcess.setJobDescription("功能评审通过");
                        jobItemProcess.setFileListStr(null);
                        jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                        rdmsJobItemProcessService.save(jobItemProcess);
                    }

                    //处理其他Review_coo工单状态
                    {
                        List<RdmsJobItem> jobitemListByTested = rdmsJobItemService.getJobitemListByTestedIdAndJobType(reviewJobitem.getId(), JobItemTypeEnum.REVIEW_COO.getType());
                        if (!CollectionUtils.isEmpty(jobitemListByTested)) {
                            for (RdmsJobItem itemDto : jobitemListByTested) {
//                            itemDto.setNextNode(null);
                                itemDto.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                                itemDto.setActualSubmissionTime(new Date());
                                String itemId = rdmsJobItemService.update(itemDto);

                                RdmsJobItemProcess itemProcess = new RdmsJobItemProcess();
                                itemProcess.setJobItemId(itemId);
                                itemProcess.setExecutorId(itemDto.getExecutorId());
                                itemProcess.setNextNode(itemDto.getNextNode());
                                long itemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(itemId);
                                itemProcess.setDeep((int) itemProcessCount);
                                itemProcess.setJobDescription("联合评审工单提交");
                                itemProcess.setFileListStr(null);
                                itemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                                itemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                                rdmsJobItemProcessService.save(itemProcess);
                            }
                        }
                    }

                    //处理Character状态
                    ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(propertyDto.getProjectType());
                    switch (Objects.requireNonNull(projectTypeEnumByType)) {
                        case SUB_PROJECT: {
                            //处理Character状态
                            if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(reviewJobitem.getCharacterId());
//                            character.setNextNode(null);
                                character.setStatus(CharacterStatusEnum.ARCHIVED.getStatus());
                                rdmsCharacterService.update(character);

                                //CharacterProcess
                                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                                characterProcess.setCharacterId(character.getId());
                                characterProcess.setExecutorId(propertyDto.getLoginUserId());
                                characterProcess.setNextNode(character.getNextNode());
                                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                                characterProcess.setDeep((int) characterProcessCount);
                                characterProcess.setJobDescription("功能开发完成");
                                characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                                rdmsCharacterProcessService.save(characterProcess);

                                RdmsCharacterDto rdmsCharacterDto = CopyUtil.copy(character, RdmsCharacterDto.class);
                                rdmsCharacterDto.setLoginUserId(propertyDto.getLoginUserId());
                                rdmsCharacterService.appendRecordSimpleInfo(rdmsCharacterDto);

                                if (rdmsCharacterDto.getJobitemType().equals(JobItemTypeEnum.SUGGEST_UPDATE.getType())) {
                                    RdmsCharacter parentCharacter = rdmsCharacterService.selectByPrimaryKey(rdmsCharacterDto.getCharacterSerial());
                                    parentCharacter.setStatus(CharacterStatusEnum.UPDATED_HISTORY.getStatus());
                                    rdmsCharacterService.update(parentCharacter);

                                    //处理被迭代的CBB状态
                                    List<RdmsCbbDto> cbbByCbbCode = rdmsCbbService.getCbbByCbbCode(parentCharacter.getId());
                                    if(!CollectionUtils.isEmpty(cbbByCbbCode)){
                                        cbbByCbbCode.get(0).setStatus(CharacterStatusEnum.UPDATED_HISTORY.getStatus());
                                        rdmsCbbService.update(cbbByCbbCode.get(0));
                                    }

                                    //当前的CBB记录
                                    RdmsCbbDto rdmsCbbDto = rdmsCbbService.getCbbByCbbCode(character.getId()).get(0);
                                    RdmsCbb rdmsCbb = CopyUtil.copy(rdmsCbbDto, RdmsCbb.class);
                                    rdmsCbb.setCharacterId(character.getId());
                                    rdmsCbb.setCustomerId(character.getCustomerId());
                                    rdmsCbb.setCbbName(character.getCharacterName());
                                    RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(character.getId());
                                    if(!ObjectUtils.isEmpty(rdmsCharacterData)){
                                        rdmsCbb.setCbbIntroduce(rdmsCharacterData.getFunctionDescription());
                                    }
                                    if(!ObjectUtils.isEmpty(character.getSubprojectId())){
                                        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(character.getSubprojectId());
                                        if(!ObjectUtils.isEmpty(rdmsProjectSubproject)){
                                            rdmsCbb.setSupervise(rdmsProjectSubproject.getSupervise());
                                            rdmsCbb.setProductManagerId(rdmsProjectSubproject.getProductManagerId());
                                            rdmsCbb.setProjectManagerId(rdmsProjectSubproject.getProjectManagerId());
                                            rdmsCbb.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
                                            rdmsCbb.setKeyMemberListStr(rdmsProjectSubproject.getKeyMemberListStr());
                                        }
                                    }
                                    if(!ObjectUtils.isEmpty(character.getProjectId())){
                                        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(character.getProjectId());
                                        if(!ObjectUtils.isEmpty(rdmsProject)){
                                            rdmsCbb.setSystemManagerId(rdmsProject.getSystemManagerId());
                                        }
                                    }
                                    rdmsCbb.setCreaterId(character.getWriterId());
                                    rdmsCbb.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
                                    rdmsCbb.setType(DocTypeEnum.CHARACTER.getType());
                                    rdmsCbb.setRelatedId(null);
                                    rdmsCbb.setCreateTime(new Date());
                                    rdmsCbb.setReleaseTime(new Date());
                                    rdmsCbb.setDeleted(0);
                                    if (cbbByCbbCode == null || cbbByCbbCode.isEmpty()) {
                                        // 处理 cbbByCbbCode 为空的情况，可以根据业务需求设置默认值或抛出异常
                                        throw new IllegalArgumentException("cbbByCbbCode 列表不能为空");
                                    }

                                    String devVersion = cbbByCbbCode.get(0).getDevVersion();
                                    if (devVersion == null || devVersion.isEmpty()) {
                                        // 处理 devVersion 为空或 null 的情况
                                        throw new IllegalArgumentException("devVersion 不能为 null 或空字符串");
                                    }

                                    double parsedVersion;
                                    try {
                                        parsedVersion = Double.parseDouble(devVersion);
                                    } catch (NumberFormatException e) {
                                        // 处理 devVersion 不是有效整数的情况
                                        throw new IllegalArgumentException("devVersion 必须是有效的整数字符串", e);
                                    }

                                    // 增加版本号
                                    double newVersion = parsedVersion + 1;

                                    // 设置新的版本号
                                    rdmsCbb.setDevVersion(String.valueOf(newVersion));

                                    rdmsCbbService.save(rdmsCbb);
                                }else{
                                    //根据isCbb的状态,决定是否将character的信息添加到CBB表中
                                    if (propertyDto.getIsCbb().equals("1")) {
                                        //生成CBB记录
                                        RdmsCbb rdmsCbb = new RdmsCbb();
                                        rdmsCbb.setCharacterId(character.getId());
                                        rdmsCbb.setCustomerId(character.getCustomerId());
                                        rdmsCbb.setCbbName(character.getCharacterName());
                                        RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(character.getId());
                                        if(!ObjectUtils.isEmpty(rdmsCharacterData)){
                                            rdmsCbb.setCbbIntroduce(rdmsCharacterData.getFunctionDescription());
                                        }
                                        if(!ObjectUtils.isEmpty(character.getSubprojectId())){
                                            RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(character.getSubprojectId());
                                            if(!ObjectUtils.isEmpty(rdmsProjectSubproject)){
                                                rdmsCbb.setSupervise(rdmsProjectSubproject.getSupervise());
                                                rdmsCbb.setProductManagerId(rdmsProjectSubproject.getProductManagerId());
                                                rdmsCbb.setProjectManagerId(rdmsProjectSubproject.getProjectManagerId());
                                                rdmsCbb.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
                                                rdmsCbb.setKeyMemberListStr(rdmsProjectSubproject.getKeyMemberListStr());
                                            }
                                        }
                                        if(!ObjectUtils.isEmpty(character.getProjectId())){
                                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(character.getProjectId());
                                            if(!ObjectUtils.isEmpty(rdmsProject)){
                                                rdmsCbb.setSystemManagerId(rdmsProject.getSystemManagerId());
                                            }
                                        }
                                        rdmsCbb.setCreaterId(character.getWriterId());
                                        rdmsCbb.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
                                        rdmsCbb.setType(DocTypeEnum.CHARACTER.getType());
                                        rdmsCbb.setRelatedId(null);
                                        rdmsCbb.setCreateTime(new Date());
                                        rdmsCbb.setReleaseTime(new Date());
                                        rdmsCbb.setDeleted(0);
                                        rdmsCbb.setDevVersion("1.00");
                                        rdmsCbbService.save(rdmsCbb);
                                    }
                                }
                            } else if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
                                //当子项目发集成工单后, 子项的状态 应该为 integration 状态
                                RdmsProjectSubprojectDto subprojectDto = rdmsProjectService.getSubprojectBySubprojectId(propertyDto.getSubprojectId());
                                RdmsProjectSubproject subproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
                                subproject.setStatus(ProjectStatusEnum.COMPLETE.getStatus());
                                rdmsSubprojectService.updateByPrimaryKeySelective(subproject);

                                //根据isCbb的状态,决定是否将subproject的信息添加到CBB表中
                                if (propertyDto.getIsCbb().equals("1")) {
                                    {
                                        //给项目经理发CBB模块定义工单, 项目经理根据项目的信息, 填写CBB定义信息
                                        //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                                        RdmsJobItem cbbDefineJobitem = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                                        cbbDefineJobitem.setId(null);
                                        cbbDefineJobitem.setJobSerial(null);
                                        cbbDefineJobitem.setJobName("请完成CBB组件定义");
                                        cbbDefineJobitem.setTaskDescription("请根据项目的定义及执行信息,进行总结和抽象,完成对CBB组件的定义.要求指标明确,功能描述以及接口定义清晰完整.");
                                        cbbDefineJobitem.setExecutorId(reviewJobitem.getExecutorId());  //评审工单的执行人, 实际上就是当前评审子项目的项目经理
                                        cbbDefineJobitem.setNextNode(null); //通知到了之后, 没有下一步执行人
                                        cbbDefineJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                                        cbbDefineJobitem.setReviewResult(ReviewResultEnum.PASS.getStatus());
                                        cbbDefineJobitem.setType(JobItemTypeEnum.CBB_DEFINE.getType());
                                        cbbDefineJobitem.setParentJobitemId(reviewJobitem.getId());
                                        cbbDefineJobitem.setPropertyId(null);
                                        cbbDefineJobitem.setReviewWorkerIdStr(null);
                                        cbbDefineJobitem.setFileListStr(null);
                                        Calendar referenceCalendar = Calendar.getInstance();
                                        referenceCalendar.setTime(new Date());
                                        referenceCalendar.add(Calendar.DATE, 3);
                                        Date time_3 = referenceCalendar.getTime();
                                        cbbDefineJobitem.setPlanSubmissionTime(time_3);
                                        String jobitemId = rdmsJobItemService.save(cbbDefineJobitem);

                                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                        jobItemProcess.setJobItemId(jobitemId);
                                        jobItemProcess.setExecutorId(cbbDefineJobitem.getExecutorId());
                                        jobItemProcess.setNextNode(cbbDefineJobitem.getNextNode());
                                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                                        jobItemProcess.setDeep((int) jobItemProcessCount);
                                        jobItemProcess.setJobDescription("请根据项目信息进行CBB组件定义");
                                        jobItemProcess.setFileListStr(null);
                                        jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                                        rdmsJobItemProcessService.save(jobItemProcess);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                break;
            }
            //4. 有条件通过
            case CONDITIONAL: {
                RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
                rdmsManhourService.saveBudgetExeInfo(propertyDto, reviewResultEnum);
                {
                    //处理当前产品经理的review工单
                    String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);
                    //填写文件的访问权限人员
                    List<String> fileIds = new ArrayList<>();
                    if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                        List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                        fileIds.addAll(strings);
                    }
                    if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                        List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                        fileIds.addAll(strings);
                    }
                    if (!CollectionUtils.isEmpty(fileIds)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(reviewJobitem.getProjectManagerId());
                            roleUsersDto.setPdmId(reviewJobitem.getProductManagerId());
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }

                    //处理评审工单
                    reviewJobitem.setPropertyId(propertyId);
//                    reviewJobitem.setNextNode(null);
                    reviewJobitem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                    reviewJobitem.setReviewResult(ReviewResultEnum.CONDITIONAL.getStatus());
                    reviewJobitem.setActualSubmissionTime(new Date());
                    String jobitemId = rdmsJobItemService.update(reviewJobitem);

                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobitemId);
                    jobItemProcess.setExecutorId(reviewJobitem.getExecutorId());
                    jobItemProcess.setNextNode(reviewJobitem.getNextNode());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("被评审项目有条件通过");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());  //这里的submit只代表已经处理的一种状态,当增补材料提交后, 重新回到handling状态
                    rdmsJobItemProcessService.save(jobItemProcess);
                }

                if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                    RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(reviewJobitem.getCharacterId());
                    RdmsCharacterDto rdmsCharacterDto = CopyUtil.copy(character, RdmsCharacterDto.class);
                    rdmsCharacterDto.setLoginUserId(propertyDto.getLoginUserId());
                    rdmsCharacterService.appendRecordSimpleInfo(rdmsCharacterDto);
                    if (!rdmsCharacterDto.getRdType().equals(RdTypeEnum.MODULE.getType())) {
                        //判断被评审的项目是否为主项目, 如果是主项目通过评审, 需要处理project表中的项目状态
                        if (reviewJobitem.getProjectId().equals(reviewJobitem.getSubprojectId())) {
                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(reviewJobitem.getProjectId());
                            rdmsProject.setStatus(ProjectStatusEnum.COMPLETE.getStatus());
                            rdmsProjectService.update(rdmsProject);
                        }
                    }
                } else {
                    //判断被评审的项目是否为主项目, 如果是主项目通过评审, 需要处理project表中的项目状态
                    if (reviewJobitem.getProjectId().equals(reviewJobitem.getSubprojectId())) {
                        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(reviewJobitem.getProjectId());
                        rdmsProject.setStatus(ProjectStatusEnum.COMPLETE.getStatus());
                        rdmsProjectService.update(rdmsProject);

                        //将预算审批条目设置为完成状态
                        RdmsBudget budgetItemByProjectId = rdmsBudgetService.getBudgetItemByProjectId(rdmsProject.getId());
                        budgetItemByProjectId.setStatus(BudgetStatusEnum.COMPLETE.getStatus());
                        rdmsBudgetService.update(budgetItemByProjectId);
                    }
                }

                {
                    //给项目经理发评任务工单
                    //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                    RdmsJobItem taskJobitem = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                    taskJobitem.setId(null);
                    taskJobitem.setJobSerial(null);
                    taskJobitem.setJobName("有条件通过:" + reviewJobitem.getJobName());
                    taskJobitem.setTaskDescription(jobItemProperty.getJobDescription());
                    taskJobitem.setExecutorId(reviewJobitem.getExecutorId());
                    taskJobitem.setNextNode(reviewJobitem.getProjectManagerId());
                    taskJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    taskJobitem.setReviewResult(ReviewResultEnum.CONDITIONAL.getStatus());
                    taskJobitem.setPlanSubmissionTime(propertyDto.getPlanSubmissionTime());

                    if (propertyDto.getProjectType().equals(ProjectTypeEnum.SUB_PROJECT.getType())) {
                        taskJobitem.setType(JobItemTypeEnum.TASK_SUBP.getType());  //项目任务  项目经理发出的工单

                    } else if (propertyDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())) {
                        taskJobitem.setType(JobItemTypeEnum.TASK_PRO.getType());  //项目任务  项目经理发出的工单
                    }

                    taskJobitem.setCharacterId(null);  //character开发关闭 费用归属子项目
                    taskJobitem.setParentJobitemId(reviewJobitem.getId());    //评审驳回的原始工单 !!!
                    taskJobitem.setPropertyId(null);
                    taskJobitem.setReviewWorkerIdStr(null);
                    String jobitemId = rdmsJobItemService.save(taskJobitem);

                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobitemId);
                    jobItemProcess.setExecutorId(taskJobitem.getExecutorId());
                    jobItemProcess.setNextNode(taskJobitem.getNextNode());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("请根据评审要求,进行后续处理");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                    rdmsJobItemProcessService.save(jobItemProcess);
                }
                //处理其他Review工单状态
                {
                    List<RdmsJobItem> jobitemListByTested = rdmsJobItemService.getJobitemListByTestedIdAndJobType(reviewJobitem.getId(), JobItemTypeEnum.REVIEW_COO.getType());
                    if (!CollectionUtils.isEmpty(jobitemListByTested)) {
                        for (RdmsJobItem itemDto : jobitemListByTested) {
//                            itemDto.setNextNode(null);  //通过后给到项目经理
                            itemDto.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            itemDto.setActualSubmissionTime(new Date());
                            String itemId = rdmsJobItemService.update(itemDto);

                            RdmsJobItemProcess itemProcess = new RdmsJobItemProcess();
                            itemProcess.setJobItemId(itemId);
                            itemProcess.setExecutorId(itemDto.getExecutorId());
                            itemProcess.setNextNode(itemDto.getNextNode());
                            long itemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(itemId);
                            itemProcess.setDeep((int) itemProcessCount);
                            itemProcess.setJobDescription("评审工单有条件通过");
                            itemProcess.setFileListStr(null);
                            itemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                            itemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                            rdmsJobItemProcessService.save(itemProcess);
                        }
                    }
                }
                //处理Character状态
                ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(propertyDto.getProjectType());
                if (Objects.requireNonNull(projectTypeEnumByType) == ProjectTypeEnum.SUB_PROJECT) {//处理Character状态
                    if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(reviewJobitem.getCharacterId());
//                            character.setNextNode(null);
                        character.setStatus(CharacterStatusEnum.ARCHIVED.getStatus());
                        rdmsCharacterService.update(character);

                        //CharacterProcess
                        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                        characterProcess.setCharacterId(character.getId());
                        characterProcess.setExecutorId(propertyDto.getLoginUserId());
                        characterProcess.setNextNode(character.getNextNode());
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                        characterProcess.setDeep((int) characterProcessCount);
                        characterProcess.setJobDescription("功能开发完成");
                        characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                        rdmsCharacterProcessService.save(characterProcess);

                        if (character.getJobitemType().equals(JobItemTypeEnum.SUGGEST_UPDATE.getType())) {
                            RdmsCharacter parentCharacter = rdmsCharacterService.selectByPrimaryKey(character.getCharacterSerial());
                            parentCharacter.setStatus(CharacterStatusEnum.UPDATED_HISTORY.getStatus());
                            rdmsCharacterService.update(parentCharacter);

                            //处理被迭代的CBB状态
                            List<RdmsCbbDto> cbbByCbbCode = rdmsCbbService.getCbbByCbbCode(parentCharacter.getId());
                            if(!CollectionUtils.isEmpty(cbbByCbbCode)){
                                cbbByCbbCode.get(0).setStatus(CharacterStatusEnum.UPDATED_HISTORY.getStatus());
                                rdmsCbbService.update(cbbByCbbCode.get(0));
                            }

                            //生产新的CBB
                            RdmsCbb rdmsCbb = new RdmsCbb();
                            rdmsCbb.setCharacterId(character.getId());
                            rdmsCbb.setCustomerId(character.getCustomerId());
                            rdmsCbb.setCbbName(character.getCharacterName());
                            RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(character.getId());
                            if(!ObjectUtils.isEmpty(rdmsCharacterData)){
                                rdmsCbb.setCbbIntroduce(rdmsCharacterData.getFunctionDescription());
                            }
                            if(!ObjectUtils.isEmpty(character.getSubprojectId())){
                                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(character.getSubprojectId());
                                if(!ObjectUtils.isEmpty(rdmsProjectSubproject)){
                                    rdmsCbb.setSupervise(rdmsProjectSubproject.getSupervise());
                                    rdmsCbb.setProductManagerId(rdmsProjectSubproject.getProductManagerId());
                                    rdmsCbb.setProjectManagerId(rdmsProjectSubproject.getProjectManagerId());
                                    rdmsCbb.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
                                    rdmsCbb.setKeyMemberListStr(rdmsProjectSubproject.getKeyMemberListStr());
                                }
                            }
                            if(!ObjectUtils.isEmpty(character.getProjectId())){
                                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(character.getProjectId());
                                if(!ObjectUtils.isEmpty(rdmsProject)){
                                    rdmsCbb.setSystemManagerId(rdmsProject.getSystemManagerId());
                                }
                            }
                            rdmsCbb.setCreaterId(character.getWriterId());
                            rdmsCbb.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
                            rdmsCbb.setType(DocTypeEnum.CHARACTER.getType());
                            rdmsCbb.setRelatedId(null);
                            rdmsCbb.setCreateTime(new Date());
                            rdmsCbb.setReleaseTime(new Date());
                            rdmsCbb.setDeleted(0);
                            rdmsCbb.setDevVersion(String.valueOf(Integer.parseInt(cbbByCbbCode.get(0).getDevVersion()) + 1));
                            rdmsCbbService.save(rdmsCbb);
                        }else{
                            //根据isCbb的状态,决定是否将character的信息添加到CBB表中
                            if (propertyDto.getIsCbb().equals("1")) {
                                //生成CBB记录
                                RdmsCbb rdmsCbb = new RdmsCbb();
                                rdmsCbb.setCharacterId(character.getId());
                                rdmsCbb.setCustomerId(character.getCustomerId());
                                rdmsCbb.setCbbName(character.getCharacterName());
                                RdmsCharacterData rdmsCharacterData = rdmsCharacterDataService.selectByPrimaryKey(character.getId());
                                if(!ObjectUtils.isEmpty(rdmsCharacterData)){
                                    rdmsCbb.setCbbIntroduce(rdmsCharacterData.getFunctionDescription());
                                }
                                if(!ObjectUtils.isEmpty(character.getSubprojectId())){
                                    RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(character.getSubprojectId());
                                    if(!ObjectUtils.isEmpty(rdmsProjectSubproject)){
                                        rdmsCbb.setSupervise(rdmsProjectSubproject.getSupervise());
                                        rdmsCbb.setProductManagerId(rdmsProjectSubproject.getProductManagerId());
                                        rdmsCbb.setProjectManagerId(rdmsProjectSubproject.getProjectManagerId());
                                        rdmsCbb.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
                                        rdmsCbb.setKeyMemberListStr(rdmsProjectSubproject.getKeyMemberListStr());
                                    }
                                }
                                if(!ObjectUtils.isEmpty(character.getProjectId())){
                                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(character.getProjectId());
                                    if(!ObjectUtils.isEmpty(rdmsProject)){
                                        rdmsCbb.setSystemManagerId(rdmsProject.getSystemManagerId());
                                    }
                                }
                                rdmsCbb.setCreaterId(character.getWriterId());
                                rdmsCbb.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
                                rdmsCbb.setType(DocTypeEnum.CHARACTER.getType());
                                rdmsCbb.setRelatedId(null);
                                rdmsCbb.setCreateTime(new Date());
                                rdmsCbb.setReleaseTime(new Date());
                                rdmsCbb.setDeleted(0);
                                rdmsCbb.setDevVersion("1.00");
                                rdmsCbbService.save(rdmsCbb);
                            }
                        }
                    } else if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
                        //当子项目发集成工单后, 子项的状态 应该为 integration 状态
                        RdmsProjectSubprojectDto subprojectDto = rdmsProjectService.getSubprojectBySubprojectId(propertyDto.getSubprojectId());
                        RdmsProjectSubproject subproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
                        subproject.setStatus(ProjectStatusEnum.COMPLETE.getStatus());  //部重新打开集成工单, 标记一个 伪 完成状态
                        rdmsSubprojectService.updateByPrimaryKeySelective(subproject);

                        //根据isCbb的状态,决定是否将subproject的信息添加到CBB表中
                        if (propertyDto.getIsCbb().equals("1")) {
                            {
                                //给项目经理发CBB模块定义工单, 项目经理根据项目的信息, 填写CBB定义信息
                                //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                                RdmsJobItem cbbDefineJobitem = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                                cbbDefineJobitem.setId(null);
                                cbbDefineJobitem.setJobSerial(null);
                                cbbDefineJobitem.setJobName("请完成CBB组件定义");
                                cbbDefineJobitem.setTaskDescription("请根据项目的定义及执行信息,进行总结和抽象,完成对CBB组件的定义.要求指标明确,功能描述以及接口定义清晰完整.");
                                cbbDefineJobitem.setExecutorId(reviewJobitem.getExecutorId());  //评审工单的执行人, 实际上就是当前评审子项目的项目经理
                                cbbDefineJobitem.setNextNode(null); //通知到了之后, 没有下一步执行人
                                cbbDefineJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                                cbbDefineJobitem.setReviewResult(ReviewResultEnum.PASS.getStatus());
                                cbbDefineJobitem.setType(JobItemTypeEnum.CBB_DEFINE.getType());
                                cbbDefineJobitem.setParentJobitemId(reviewJobitem.getId());
                                cbbDefineJobitem.setPropertyId(null);
                                cbbDefineJobitem.setReviewWorkerIdStr(null);
                                cbbDefineJobitem.setFileListStr(null);
                                Calendar referenceCalendar = Calendar.getInstance();
                                referenceCalendar.setTime(new Date());
                                referenceCalendar.add(Calendar.DATE, 3);
                                Date time_3 = referenceCalendar.getTime();
                                cbbDefineJobitem.setPlanSubmissionTime(time_3);
                                String jobitemId = rdmsJobItemService.save(cbbDefineJobitem);

                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(jobitemId);
                                jobItemProcess.setExecutorId(cbbDefineJobitem.getExecutorId());
                                jobItemProcess.setNextNode(cbbDefineJobitem.getNextNode());
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                jobItemProcess.setJobDescription("请根据项目信息进行CBB组件定义");
                                jobItemProcess.setFileListStr(null);
                                jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                                rdmsJobItemProcessService.save(jobItemProcess);
                            }
                        }
                    }
                }
                //驳回Character的集成工单 保持不变
                break;
            }
            //6. 增补材料
            case SUPPLEMENT: {
                //处理当前项目经理的review工单
                //工单流转: 工作的过程处理当中提交时， 由于执行人是永久不变的， 所以， nextNode记录的是提交给谁就是谁
                RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
                {
                    String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);
                    //填写文件的访问权限人员
                    List<String> fileIds = new ArrayList<>();
                    if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                        List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                        fileIds.addAll(strings);
                    }
                    if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                        List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                        fileIds.addAll(strings);
                    }
                    if (!CollectionUtils.isEmpty(fileIds)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(reviewJobitem.getProjectManagerId());
                            roleUsersDto.setPdmId(reviewJobitem.getProductManagerId());
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }

                    //评审工单处理
                    reviewJobitem.setPropertyId(propertyId);
                    reviewJobitem.setNextNode(reviewJobitem.getProductManagerId());  //子项目评审 功能评审 评审组长都是产品经理
                    if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                        reviewJobitem.setStatus(JobItemStatusEnum.CHA_RECHECK.getStatus());
                    }
                    if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
                        reviewJobitem.setStatus(JobItemStatusEnum.SUB_RECHECK.getStatus());
                    }
                    reviewJobitem.setReviewResult(ReviewResultEnum.SUPPLEMENT.getStatus());
                    reviewJobitem.setActualSubmissionTime(new Date());
                    String jobitemId = rdmsJobItemService.update(reviewJobitem);

                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobitemId);
                    jobItemProcess.setExecutorId(reviewJobitem.getExecutorId());
                    jobItemProcess.setNextNode(reviewJobitem.getNextNode());   //子项目评审 功能评审 评审组长都是产品经理
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("增补评审材料过程中");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
//                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.CHA_RECHECK.getStatus());  //这里的submit只代表已经处理的一种状态,当增补材料提交后, 重新回到handling状态
                    if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.CHA_RECHECK.getStatus());
                    }
                    if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUB_RECHECK.getStatus());
                    }
                    rdmsJobItemProcessService.save(jobItemProcess);
                }

                {
                    //给项目经理发项目任务工单
                    //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                    RdmsJobItem taskJobitem = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                    taskJobitem.setId(null);
                    taskJobitem.setJobSerial(null);
                    taskJobitem.setJobName("增补材料:" + reviewJobitem.getJobName());
                    taskJobitem.setTaskDescription(jobItemProperty.getJobDescription());
                    taskJobitem.setExecutorId(reviewJobitem.getExecutorId());  //原工单的执行人 , 其实是当前子项目的项目经理
                    taskJobitem.setNextNode(propertyDto.getLoginUserId());
                    taskJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    taskJobitem.setReviewResult(ReviewResultEnum.SUPPLEMENT.getStatus());
                    taskJobitem.setPlanSubmissionTime(propertyDto.getPlanSubmissionTime());

                    if (propertyDto.getProjectType().equals(ProjectTypeEnum.SUB_PROJECT.getType())) {
                        taskJobitem.setType(JobItemTypeEnum.TASK_SUBP.getType());  //项目任务  项目经理发出的工单

                    } else if (propertyDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())) {
                        taskJobitem.setType(JobItemTypeEnum.TASK_PRO.getType());  //项目任务  项目经理发出的工单
                    }
                    taskJobitem.setCharacterId(null);  //character开发关闭 费用归属子项目
                    taskJobitem.setParentJobitemId(reviewJobitem.getId());    //评审驳回的原始工单 !!!
                    taskJobitem.setPropertyId(null);
                    taskJobitem.setReviewWorkerIdStr(null);
                    String jobitemId = rdmsJobItemService.save(taskJobitem);

                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobitemId);
                    jobItemProcess.setExecutorId(taskJobitem.getExecutorId());
                    jobItemProcess.setNextNode(taskJobitem.getNextNode());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("请根据评审要求增补材料");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                    rdmsJobItemProcessService.save(jobItemProcess);
                }

                //处理其他Review工单状态 保持不变

                ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(propertyDto.getProjectType());
                if (Objects.requireNonNull(projectTypeEnumByType) == ProjectTypeEnum.SUB_PROJECT) {//处理Character驳回状态
                    //工单流转: 工作的过程处理当中提交时， 由于执行人是永久不变的， 所以， nextNode记录的是提交给谁就是谁
                    if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(reviewJobitem.getCharacterId());
//                            character.setNextNode(reviewJobitem.getProjectManagerId());
                        character.setStatus(CharacterStatusEnum.DEV_COMPLETE.getStatus());  //这个状态可以打开Character开发的工单管理页面
                        rdmsCharacterService.update(character);

                        //CharacterProcess
                        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                        characterProcess.setCharacterId(character.getId());
                        characterProcess.setExecutorId(propertyDto.getLoginUserId());
                        characterProcess.setNextNode(character.getNextNode());
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                        characterProcess.setDeep((int) characterProcessCount);
                        characterProcess.setJobDescription("功能评审未通过,需要补充材料");
                        characterProcess.setProcessStatus(CharacterProcessStatusEnum.ONGOING.getStatus());
                        rdmsCharacterProcessService.save(characterProcess);
                    } else if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
                        //当子项目发集成工单后, 子项的状态 应该为 integration 状态
                        RdmsProjectSubprojectDto subprojectDto = rdmsProjectService.getSubprojectBySubprojectId(propertyDto.getSubprojectId());
                        RdmsProjectSubproject subproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
                        subproject.setStatus(ProjectStatusEnum.DEV_COMPLETE.getStatus());  //部重新打开集成工单, 标记一个 伪 完成状态
                        rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
                    }
                }
                break;
            }
            //8. 驳回
            case REJECT: {
                //驳回要给项目经理发一个通知工单
                {
                    //处理当前产品经理的review工单
                    //工单流转: 工作的过程处理当中提交时， 由于执行人是永久不变的， 所以， nextNode记录的是提交给谁就是谁
                    RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
                    String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);
                    //填写文件的访问权限人员
                    List<String> fileIds = new ArrayList<>();
                    if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                        List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                        fileIds.addAll(strings);
                    }
                    if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                        List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                        fileIds.addAll(strings);
                    }
                    if (!CollectionUtils.isEmpty(fileIds)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(reviewJobitem.getProjectManagerId());
                            roleUsersDto.setPdmId(reviewJobitem.getProductManagerId());
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }

                    //评审工单处理
                    reviewJobitem.setPropertyId(propertyId);
//                    reviewJobitem.setNextNode(null);
                    reviewJobitem.setStatus(JobItemStatusEnum.ARCHIVED.getStatus());  //驳回后,直接将评审工单归档
                    reviewJobitem.setReviewResult(ReviewResultEnum.REJECT.getStatus());
                    reviewJobitem.setActualSubmissionTime(new Date());
                    String jobitemId = rdmsJobItemService.update(reviewJobitem);

                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobitemId);
                    jobItemProcess.setExecutorId(reviewJobitem.getExecutorId());
                    jobItemProcess.setNextNode(reviewJobitem.getNextNode());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("评审被驳回");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                    rdmsJobItemProcessService.save(jobItemProcess);
                }

                {
                    //给项目经理发评审通知工单
                    //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                    RdmsJobItem notifyJobitem = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                    notifyJobitem.setId(null);
                    notifyJobitem.setJobSerial(null);
                    notifyJobitem.setJobName("评审通知:" + reviewJobitem.getJobName());
                    notifyJobitem.setTaskDescription("评审驳回! 请完成相关工作后, 重新提交评审.");
                    notifyJobitem.setExecutorId(reviewJobitem.getExecutorId());  //评审工单所记载的执行人, 就是提交评审工单的人
//                    notifyJobitem.setNextNode(null);
                    notifyJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    notifyJobitem.setReviewResult(ReviewResultEnum.REJECT.getStatus());
                    notifyJobitem.setType(JobItemTypeEnum.REVIEW_NOTIFY.getType());
                    notifyJobitem.setParentJobitemId(reviewJobitem.getId());    //评审驳回的原始工单 !!!
                    notifyJobitem.setPropertyId(null);
                    notifyJobitem.setReviewWorkerIdStr(null);
                    notifyJobitem.setFileListStr(null);
                    Calendar referenceCalendar = Calendar.getInstance();
                    referenceCalendar.setTime(new Date());
                    referenceCalendar.add(Calendar.DATE, 3);
                    Date time_3 = referenceCalendar.getTime();
                    notifyJobitem.setPlanSubmissionTime(time_3);
                    String jobitemId = rdmsJobItemService.save(notifyJobitem);

                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobitemId);
                    jobItemProcess.setExecutorId(notifyJobitem.getExecutorId());
                    jobItemProcess.setNextNode(notifyJobitem.getNextNode());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("评审驳回! 请完成相关工作后, 重新提交评审.");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                    rdmsJobItemProcessService.save(jobItemProcess);
                }

                //处理其他Review工单状态
                {
                    List<RdmsJobItem> jobitemListByTestedIdAndJobType = rdmsJobItemService.getJobitemListByTestedIdAndJobType(reviewJobitem.getId(), JobItemTypeEnum.REVIEW_COO.getType());
                    if (!CollectionUtils.isEmpty(jobitemListByTestedIdAndJobType)) {
                        for (RdmsJobItem itemDto : jobitemListByTestedIdAndJobType) {
//                            itemDto.setNextNode(null);  //通过后给到项目经理
                            itemDto.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            itemDto.setActualSubmissionTime(new Date());
                            String itemId = rdmsJobItemService.update(itemDto);

                            RdmsJobItemProcess itemProcess = new RdmsJobItemProcess();
                            itemProcess.setJobItemId(itemId);
                            itemProcess.setExecutorId(itemDto.getExecutorId());
                            itemProcess.setNextNode(itemDto.getNextNode());
                            long itemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(itemId);
                            itemProcess.setDeep((int) itemProcessCount);
                            itemProcess.setJobDescription("评审工单被驳回");
                            itemProcess.setFileListStr(null);
                            itemProcess.setActualSubmissionTime(reviewJobitem.getActualSubmissionTime());
                            itemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                            rdmsJobItemProcessService.save(itemProcess);
                        }
                    }
                }

                ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(propertyDto.getProjectType());
                switch (Objects.requireNonNull(projectTypeEnumByType)) {
                    case SUB_PROJECT: {
                        //处理Character驳回状态
                        if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW.getType())) {
                            RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(reviewJobitem.getCharacterId());
//                            character.setNextNode(reviewJobitem.getExecutorId());
                            character.setStatus(CharacterStatusEnum.INTEGRATION.getStatus());
                            rdmsCharacterService.update(character);

                            //CharacterProcess
                            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                            characterProcess.setCharacterId(character.getId());
                            characterProcess.setExecutorId(propertyDto.getLoginUserId());
                            characterProcess.setNextNode(character.getNextNode());
                            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                            characterProcess.setDeep((int) characterProcessCount);
                            characterProcess.setJobDescription("功能评审被驳回");
                            characterProcess.setProcessStatus(CharacterProcessStatusEnum.ONGOING.getStatus());
                            rdmsCharacterProcessService.save(characterProcess);

                            //驳回Character的集成工单
                            //工单流转: 工作的过程处理当中提交时， 由于执行人是永久不变的， 所以， nextNode记录的是提交给谁就是谁
                            {
                                RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(reviewJobitem.getTestedJobitemId());
                                if (!ObjectUtils.isEmpty(intJobitem)) {
                                    RdmsJobItem intJobItemDto = CopyUtil.copy(intJobitem, RdmsJobItem.class);
                                    intJobItemDto.setNextNode(reviewJobitem.getProjectManagerId());
                                    intJobItemDto.setStatus(JobItemStatusEnum.HANDLING.getStatus());  //出现在任务管理中
                                    intJobItemDto.setActualSubmissionTime(null);
                                    String jobitemId = rdmsJobItemService.update(intJobItemDto);

                                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                    jobItemProcess.setJobItemId(jobitemId);
                                    jobItemProcess.setExecutorId(intJobItemDto.getExecutorId());
                                    jobItemProcess.setNextNode(intJobItemDto.getNextNode());
                                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                                    jobItemProcess.setDeep((int) jobItemProcessCount);
                                    jobItemProcess.setJobDescription("被评审委员会驳回");
                                    jobItemProcess.setFileListStr(null);
                                    jobItemProcess.setActualSubmissionTime(null);
                                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.REJECT.getStatus());
                                    rdmsJobItemProcessService.save(jobItemProcess);
                                } else {
                                    throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
                                }
                            }
                        } else if (propertyDto.getJobType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
                            //当子项目发集成工单后, 子项的状态 应该为 integration 状态
                            RdmsProjectSubprojectDto subprojectDto = rdmsProjectService.getSubprojectBySubprojectId(propertyDto.getSubprojectId());
                            RdmsProjectSubproject subproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
                            if (subproject.getOutSourceStatus().equals(ProjectStatusEnum.OUT_SOURCE.getStatus())) {
                                subproject.setStatus(ProjectStatusEnum.OUT_SOURCE.getStatus());
                            } else {
                                subproject.setStatus(ProjectStatusEnum.SETUPED.getStatus());
                            }
                            rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
                        }
                        break;
                    }
                }
                break;
            }
            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }

        responseDto.setSuccess(true);
        responseDto.setMessage("提交成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/submitJobitemTestResult")
    @Transactional
    public ResponseDto submitJobitemTestResult(@RequestBody RdmsJobItemPropertyDto propertyDto) {
        ResponseDto responseDto = new ResponseDto<>();

        ValidatorUtil.require(propertyDto.getJobItemId(), "工单ID");
        ValidatorUtil.require(propertyDto.getJobDescription(), "工作描述");
        ValidatorUtil.require(propertyDto.getTestKeyPoints(), "测试关键点说明");
        ValidatorUtil.require(propertyDto.getJobEvaluate().getEvaluateScore(), "测试评分");
        ValidatorUtil.require(propertyDto.getJobEvaluate().getEvaluateDesc(), "测试评价");

        ValidatorUtil.length(propertyDto.getJobDescription(), "工作描述", 10, 2500);
        ValidatorUtil.length(propertyDto.getTestKeyPoints(), "测试关键点说明", 10, 2500);
        ValidatorUtil.length(propertyDto.getJobEvaluate().getEvaluateDesc(), "测试评价", 2, 500);

        //将测试评价写入原工单
        RdmsHmiJobEvaluateDto jobEvaluateDto = propertyDto.getJobEvaluate();
        RdmsJobItem testedJobItem = rdmsJobItemService.selectByPrimaryKey(jobEvaluateDto.getJobitemId());
        testedJobItem.setEvaluateScore(jobEvaluateDto.getEvaluateScore());
        testedJobItem.setEvaluateDesc(jobEvaluateDto.getEvaluateDesc());
        rdmsJobItemService.update(testedJobItem); //此处外产生过程变更, 所以没有过程记录

        //对property的附件和参考文件进行授权梳理
        //填写文件的访问权限人员
        List<String> fileIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
            List<String> strings = JSON.parseArray(propertyDto.getFileListStr(), String.class);
            fileIds.addAll(strings);
        }
        if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
            List<String> strings = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
            fileIds.addAll(strings);
        }
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(propertyDto.getLoginUserId());
                if (!ObjectUtils.isEmpty(propertyDto.getJobItemId())) {
                    RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
                    roleUsersDto.setTmId(jobItem.getTestManagerId());
                }
                if (!ObjectUtils.isEmpty(testedJobItem)) {
                    roleUsersDto.setExecutorId(testedJobItem.getExecutorId());  //添加被测工单执行人
                    if (testedJobItem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                        RdmsJobItem intJobitem = rdmsJobItemService.selectByPrimaryKey(testedJobItem.getParentJobitemId());
                        if (!ObjectUtils.isEmpty(intJobitem)) {
                            roleUsersDto.setExecutorId(intJobitem.getExecutorId());  //添加集成工单执行人
                        }
                    }
                }
                roleUsersDto.setReceiverId(null);
                RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(jobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(testedJobItem.getProjectManagerId());
                roleUsersDto.setPdmId(null);
                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }


        //处理当前测试工单
        RdmsJobItemProperty jobItemProperty = CopyUtil.copy(propertyDto, RdmsJobItemProperty.class);
        String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);

        RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(propertyDto.getJobItemId());
        jobItem.setPropertyId(propertyId);

        if (ObjectUtils.isEmpty(jobItem.getSubprojectId())) {
            jobItem.setSubprojectId(testedJobItem.getSubprojectId());
        }

        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItem.getSubprojectId());
        jobItem.setNextNode(subproject.getTestManagerId());
        jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
        jobItem.setActualSubmissionTime(new Date());
        String jobitemId = rdmsJobItemService.update(jobItem);

        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobitemId);
        jobItemProcess.setExecutorId(jobItem.getExecutorId());
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        jobItemProcess.setJobDescription("提交测试任务");
        jobItemProcess.setFileListStr(null);
        jobItemProcess.setNextNode(subproject.getTestManagerId());
        jobItemProcess.setActualSubmissionTime(jobItem.getActualSubmissionTime());
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());
        rdmsJobItemProcessService.save(jobItemProcess);

        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsJobItemPropertyService.delete(id);
        return responseDto;
    }

}
