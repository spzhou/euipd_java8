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
import com.course.server.service.rdms.RdmsCustomerResourceService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rdms/resource/customer")
public class RdmsCustomerResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerResourceController.class);
    public static final String BUSINESS_NAME = "客户授权资源";

    @Resource
    private RdmsCustomerResourceService rdmsCustomerResourceService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCustomerResourceService.list(pageDto);
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
        rdmsCustomerResourceService.saveJson(jsonStr);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCustomerResourceService.delete(id);
        return responseDto;
    }

    /**
     * 资源树查询
     */
    @GetMapping("/load-tree")
    public ResponseDto loadTree() {
        ResponseDto responseDto = new ResponseDto();
        List<ResourceDto> resourceDtoList = rdmsCustomerResourceService.loadTree();
        responseDto.setContent(resourceDtoList);
        return responseDto;
    }
}
