/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.course.server.domain.RdmsUserRole;
import com.course.server.domain.RdmsUserRoleUser;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.RdmsRoleDto;
import com.course.server.service.rdms.RdmsUserRoleService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rdms/role/user")
public class RdmsUserRoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsUserRoleController.class);
    public static final String BUSINESS_NAME = "开发者角色";

    @Resource
    private RdmsUserRoleService rdmsRoleService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsRoleService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RdmsRoleDto roleDto) {
        // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);
        ValidatorUtil.require(roleDto.getCustomerId(), "机构ID");

        ResponseDto responseDto = new ResponseDto();
        rdmsRoleService.save(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsRoleService.delete(id);
        return responseDto;
    }

    /**
     * 保存资源
     * @param roleDto
     */
    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RdmsRoleDto roleDto) {
        ResponseDto<RdmsRoleDto> responseDto = new ResponseDto<>();
        rdmsRoleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 修改授权
     * @param roleDto
     */
    @PostMapping("/save-auth")
    public ResponseDto saveAuth(@RequestBody RdmsRoleDto roleDto) {
        ResponseDto<RdmsRoleDto> responseDto = new ResponseDto<>();
        rdmsRoleService.saveAuth(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 加载已关联的资源
     */
    @PostMapping("/list-resource")
    public ResponseDto listResource(@RequestBody RdmsRoleDto roleDto) {
        ResponseDto responseDto = new ResponseDto<>();
        List<String> resourceIdList = rdmsRoleService.listResource(roleDto.getId(), roleDto.getCustomerId());
        responseDto.setContent(resourceIdList);
        return responseDto;
    }

    /**
     * 加载已关联的资源
     */
    @PostMapping("/get-customer-roles/{customerId}")
    public ResponseDto<List<RdmsUserRole>> getRoleByCustomer(@PathVariable String customerId) {
        ResponseDto<List<RdmsUserRole>> responseDto = new ResponseDto<>();
        List<RdmsUserRole> roleByCustomer = rdmsRoleService.getRoleByCustomer(customerId);
        responseDto.setContent(roleByCustomer);
        return responseDto;
    }

    /**
     * 加载已关联的资源
     */
    @PostMapping("/get-user-authed-roles")
    public ResponseDto<List<String>> listUserAuthedRole(@RequestBody RdmsRoleDto roleDto) {
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        List<RdmsUserRoleUser> rdmsUserRoleUsers = rdmsRoleService.listUserAuthedRole(roleDto.getCustomerId(), roleDto.getUserId());
        List<String> departmentList = rdmsUserRoleUsers.stream().map(RdmsUserRoleUser::getRoleId).collect(Collectors.toList());
        responseDto.setContent(departmentList);
        return responseDto;
    }

    /**
     * 保存用户
     * @param roleDto
     */
    @PostMapping("/save-user")
    public ResponseDto saveUser(@RequestBody RdmsRoleDto roleDto) {
        ResponseDto<RdmsRoleDto> responseDto = new ResponseDto<>();
        rdmsRoleService.saveUser(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 加载用户
     * @param roleId
     */
    @GetMapping("/list-user/{roleId}")
    public ResponseDto listUser(@PathVariable String roleId) {
//        LOG.info("加载用户开始");
        ResponseDto responseDto = new ResponseDto<>();
        List<String> userIdList = rdmsRoleService.listUser(roleId);
        responseDto.setContent(userIdList);
        return responseDto;
    }
}
