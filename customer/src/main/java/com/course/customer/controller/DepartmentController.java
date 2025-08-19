/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCustomerUser;
import com.course.server.dto.RdmsDepartmentTreeDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.service.rdms.RdmsCustomerUserService;
import com.course.server.service.rdms.RdmsDepartmentService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    public static final String BUSINESS_NAME = "员工";

    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsDepartmentService rdmsDepartmentService;

    /**
     * 列表查询
     */
    @PostMapping("/listDepartment/{customerId}")
    public ResponseDto listDepartment(@PathVariable String customerId) {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsDepartmentDto> rdmsDepartmentDtos = rdmsDepartmentService.listDepartment(customerId);
        responseDto.setContent(rdmsDepartmentDtos);
        return responseDto;
    }

    @PostMapping("/getDepartmentMemberList/{departmentId}")
    public ResponseDto getDepartmentMemberList(@PathVariable String departmentId) {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsCustomerUser> memberListByDepartmentId = rdmsCustomerUserService.getMemberListByDepartmentId(departmentId);
        responseDto.setContent(memberListByDepartmentId);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto save(@RequestBody RdmsDepartmentDto departmentDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();

        ValidatorUtil.require(departmentDto.getDepartName(), "部门名称");
        ValidatorUtil.require(departmentDto.getManagerId(), "部门负责人ID");
        ValidatorUtil.require(departmentDto.getParent(), "上级部门");
//        ValidatorUtil.require(departmentDto.getAssistantId(), "助理ID");
//        ValidatorUtil.require(departmentDto.getSecretaryId(), "秘书ID");
        ValidatorUtil.require(departmentDto.getDepartDescription(), "部门描述");

        ValidatorUtil.length(departmentDto.getDepartName(), "部门名称", 2, 20);
        ValidatorUtil.length(departmentDto.getManagerId(), "部门负责人ID", 8, 8);
        ValidatorUtil.length(departmentDto.getParent(), "上级部门", 8, 8);
//        ValidatorUtil.length(departmentDto.getAssistantId(), "助理ID", 2, 4);
//        ValidatorUtil.length(departmentDto.getSecretaryId(), "秘书ID", 2, 4);
        ValidatorUtil.length(departmentDto.getDepartDescription(), "部门描述", 2, 100);

        rdmsDepartmentService.save(departmentDto);

        responseDto.setContent(departmentDto);
        return responseDto;
    }

    @PostMapping("/edit")
    @Transactional
    public ResponseDto edit(@RequestBody RdmsDepartmentDto departmentDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();

        ValidatorUtil.require(departmentDto.getDepartName(), "部门名称");
        ValidatorUtil.require(departmentDto.getManagerId(), "部门负责人ID");
        ValidatorUtil.require(departmentDto.getParent(), "上级部门");
//        ValidatorUtil.require(departmentDto.getAssistantId(), "助理ID");
//        ValidatorUtil.require(departmentDto.getSecretaryId(), "秘书ID");
        ValidatorUtil.require(departmentDto.getDepartDescription(), "部门描述");

        ValidatorUtil.length(departmentDto.getDepartName(), "部门名称", 2, 20);
        ValidatorUtil.length(departmentDto.getManagerId(), "部门负责人ID", 8, 8);
        ValidatorUtil.length(departmentDto.getParent(), "上级部门", 8, 8);
        ValidatorUtil.length(departmentDto.getDepartDescription(), "部门描述", 2, 100);

        rdmsDepartmentService.edit(departmentDto);
        //将人员的部门信息保存到人员表格中
        if(!ObjectUtils.isEmpty(departmentDto.getMemberIdList())){
            for(String memberId:departmentDto.getMemberIdList()){
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(memberId);
                customerUser.setDepartmentId(departmentDto.getId());
                rdmsCustomerUserService.update(customerUser);
            }
        }

        responseDto.setContent(departmentDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/get-departments")
    public ResponseDto getDepartments(@RequestBody RdmsResponseDto respDto) {
        ResponseDto responseDto = new ResponseDto();
        RdmsDepartmentTreeDto departmentsTree = rdmsDepartmentService.getDepartmentsTree(respDto.getCustomerId());
        responseDto.setContent(departmentsTree);
        return responseDto;
    }

    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsDepartmentService.delete(id);
        return responseDto;
    }

}
