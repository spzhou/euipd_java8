/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import cn.hutool.core.util.ObjectUtil;
import com.course.server.domain.RdmsCustomerUser;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCustomerUserDto;
import com.course.server.dto.rdms.RdmsDepartmentDto;
import com.course.server.service.rdms.RdmsCustomerUserService;
import com.course.server.service.rdms.RdmsDepartmentService;
import com.course.server.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/underline")
public class UnderlineController {
    private static final Logger LOG = LoggerFactory.getLogger(UnderlineController.class);
    public static final String BUSINESS_NAME = "下属员工";

    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Autowired
    private RdmsDepartmentService rdmsDepartmentService;

    /**
     * 列表查询
     */
    @PostMapping("/getUnderline/{workerId}")
    public ResponseDto<List<RdmsCustomerUserDto>> getUnderline(@PathVariable String workerId) {
        ResponseDto<List<RdmsCustomerUserDto>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUserDto> customerUserDtos = new ArrayList<>();
        List<RdmsDepartmentDto> rdmsDepartmentDtos = rdmsDepartmentService.listDepartmentByManagerId(workerId);
        if(!CollectionUtils.isEmpty(rdmsDepartmentDtos)){
            List<String> departmentIdList = rdmsDepartmentDtos.stream().map(RdmsDepartmentDto::getId).collect(Collectors.toList());
            if(!ObjectUtil.isEmpty(departmentIdList)){
                List<RdmsCustomerUser> memberListByDepartmentIdList = rdmsCustomerUserService.getMemberListByDepartmentIdList(departmentIdList);
                if(!CollectionUtils.isEmpty(memberListByDepartmentIdList)){
                    List<String> memberIdList = memberListByDepartmentIdList.stream().map(RdmsCustomerUser::getId).collect(Collectors.toList());
                    memberIdList.forEach(staffId -> {
                        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(staffId);
                        if(!ObjectUtil.isEmpty(rdmsCustomerUser)){
                            RdmsCustomerUserDto copy = CopyUtil.copy(rdmsCustomerUser, RdmsCustomerUserDto.class);
                            customerUserDtos.add(copy);
                        }
                    });
                }
            }
        }
        responseDto.setContent(customerUserDtos);
        return responseDto;
    }

}
