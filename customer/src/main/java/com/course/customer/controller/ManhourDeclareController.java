/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsManhourDeclare;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsManhourDeclareDto;
import com.course.server.service.rdms.RdmsManhourDeclareService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manhour-declare")
public class ManhourDeclareController {
    private static final Logger LOG = LoggerFactory.getLogger(ManhourDeclareController.class);
    public static final String BUSINESS_NAME = "MANHOUR_DECLARE";

    @Resource
    private RdmsManhourDeclareService rdmsManhourDeclareService;

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<String> save(@RequestBody RdmsManhourDeclareDto manhourDeclareDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(manhourDeclareDto.getJobitemId(), "任务ID");
        ValidatorUtil.require(manhourDeclareDto.getManhour(), "申报工时");
//        ValidatorUtil.require(manhourDeclareDto.getReason(), "申报原因");

        RdmsManhourDeclare manhourDeclare = CopyUtil.copy(manhourDeclareDto, RdmsManhourDeclare.class);
        manhourDeclare.setId(manhourDeclareDto.getJobitemId());
        String save = rdmsManhourDeclareService.save(manhourDeclare);

        responseDto.setContent(save);
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
        rdmsManhourDeclareService.delete(id);
        return responseDto;
    }

}
