/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.course.server.dto.PageDto;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.rdms.RdmsCustomerRoleService;
import com.course.server.service.rdms.RdmsUserResourceService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rdms/resource/user")
public class RdmsUserResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsUserResourceController.class);
    public static final String BUSINESS_NAME = "开发者资源";

    @Resource
    private RdmsUserResourceService rdmsUserResourceService;
    @Resource
    private RdmsCustomerRoleService rdmsCustomerRoleService;


    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsUserResourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody String jsonStr) {
        // 保存校验
        ValidatorUtil.require(jsonStr, "资源");

        ResponseDto responseDto = new ResponseDto();
        rdmsUserResourceService.saveJson(jsonStr);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsUserResourceService.delete(id);
        return responseDto;
    }

    /**
     * 资源树查询
     */
    @GetMapping("/load-tree/{customerId}")
    public ResponseDto loadTree(@PathVariable String customerId) {
        ResponseDto responseDto = new ResponseDto();
        //1. 查自己所在的customer对应的role，通过customer-role-customer表读取
        List<String> customerRoleList = rdmsCustomerRoleService.getCustomerRole(customerId);

        //2. 在通过role，查customer-role-resource表，得到所有的resourceid
        List<String> resourceIdListAll = new ArrayList<>();
        for(String roleId : customerRoleList){
            List<String> resourceIdList = rdmsCustomerRoleService.listResource(roleId);
            resourceIdListAll.addAll(resourceIdList);
        }
        //去除重复项
        List<String> resourceIdList = resourceIdListAll.stream().distinct().collect(Collectors.toList());
        //排序
        resourceIdList = resourceIdList.stream().sorted().collect(Collectors.toList());
        //3. 通过这些resourceid构建一棵tree

        List<ResourceDto> resourceDtoList = rdmsUserResourceService.loadTreeByResourceList(resourceIdList);
        responseDto.setContent(resourceDtoList);
        return responseDto;
    }
}
