/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsBoss;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsBossDto;
import com.course.server.service.rdms.RdmsBossService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/boss")
public class BossController {
    private static final Logger LOG = LoggerFactory.getLogger(BossController.class);
    public static final String BUSINESS_NAME = "BOSS";

    @Resource
    private RdmsBossService rdmsBossService;

    /**
     * 分页查询所有BOSS信息
     * 支持分页参数，返回分页后的BOSS列表数据
     * 
     * @param pageDto 分页查询参数，包含页码、每页大小等信息
     * @return 返回分页后的BOSS列表数据
     */
    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsBossDto>> list(@RequestBody PageDto<RdmsBossDto> pageDto) {
        ResponseDto<PageDto<RdmsBossDto>> responseDto = new ResponseDto<>();
        rdmsBossService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 根据客户ID查询对应的BOSS信息
     * 通过客户ID获取该客户关联的BOSS详细信息
     * 
     * @param customerId 客户ID，用于查询关联的BOSS信息
     * @return 返回该客户对应的BOSS信息，如果不存在则返回空的BOSS对象
     */
    @PostMapping("/getBossByCustomerId/{customerId}")
    public ResponseDto<RdmsBossDto> listByCustomerId(@PathVariable String customerId) {
        ValidatorUtil.require(customerId, "客户ID");
        ResponseDto<RdmsBossDto> responseDto = new ResponseDto<>();
        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
        if (bossByCustomerId != null) {
            responseDto.setContent(bossByCustomerId);
        } else {
            responseDto.setContent(new RdmsBossDto());
        }
        return responseDto;
    }

    /**
     * 保存或更新BOSS信息
     * 支持新增和修改操作，会验证必填字段并保存到数据库
     * 
     * @param rdmsBossDto BOSS信息数据传输对象，包含客户ID和BOSS ID等必填信息
     * @return 返回保存结果，包含操作状态和成功消息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<String> save(@RequestBody RdmsBossDto rdmsBossDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(rdmsBossDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(rdmsBossDto.getBossId(), "BOSS ID");

        RdmsBoss boss = CopyUtil.copy(rdmsBossDto, RdmsBoss.class);
        String save = rdmsBossService.save(boss);

        responseDto.setContent(save);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 根据ID删除BOSS信息
     * 通过BOSS记录的唯一标识符删除对应的BOSS记录
     * 
     * @param id BOSS记录的唯一标识符
     * @return 返回删除操作的结果
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsBossService.delete(id);
        return responseDto;
    }

}
