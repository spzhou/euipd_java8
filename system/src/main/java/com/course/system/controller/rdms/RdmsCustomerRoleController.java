/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.RdmsRoleDto;
import com.course.server.service.rdms.RdmsCustomerRoleService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rdms/role/customer")
public class RdmsCustomerRoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerRoleController.class);
    public static final String BUSINESS_NAME = "客户角色";

    @Resource
    private RdmsCustomerRoleService rdmsCustomerRoleService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCustomerRoleService.list(pageDto);
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

        ResponseDto responseDto = new ResponseDto();
        rdmsCustomerRoleService.save(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCustomerRoleService.delete(id);
        return responseDto;
    }

    /**
     * 保存资源
     * @param roleDto
     */
    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RdmsRoleDto roleDto) {
        LOG.info("保存角色资源关联开始");
        ResponseDto<RdmsRoleDto> responseDto = new ResponseDto<>();
        rdmsCustomerRoleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 加载已关联的资源
     */
    @GetMapping("/list-resource/{roleId}")
    public ResponseDto listResource(@PathVariable String roleId) {
        LOG.info("加载资源开始");
        ResponseDto responseDto = new ResponseDto<>();
        List<String> resourceIdList = rdmsCustomerRoleService.listResource(roleId);
        responseDto.setContent(resourceIdList);
        return responseDto;
    }

    /**
     * 保存用户
     * @param roleDto
     */
    @PostMapping("/save-user")
    public ResponseDto saveUser(@RequestBody RdmsRoleDto roleDto) {
        LOG.info("保存角色用户关联开始");
        ResponseDto<RdmsRoleDto> responseDto = new ResponseDto<>();
        rdmsCustomerRoleService.saveUser(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 加载用户
     * @param roleId
     */
    @GetMapping("/list-user/{roleId}")
    public ResponseDto listUser(@PathVariable String roleId) {
        LOG.info("加载用户开始");
        ResponseDto responseDto = new ResponseDto<>();
        List<String> userIdList = rdmsCustomerRoleService.listUser(roleId);
        responseDto.setContent(userIdList);
        return responseDto;
    }
}
