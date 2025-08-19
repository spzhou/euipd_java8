/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsGantt;
import com.course.server.domain.RdmsGanttLinks;
import com.course.server.domain.RdmsJobItemGantt;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsGanttDto;
import com.course.server.dto.rdms.RdmsGanttLinksDto;
import com.course.server.dto.rdms.RdmsJobItemDto;
import com.course.server.dto.rdms.RdmsJobItemGanttDto;
import com.course.server.enums.rdms.OperateTypeEnum;
import com.course.server.service.rdms.*;
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
@RequestMapping("/gantt")
public class GanttController {
    private static final Logger LOG = LoggerFactory.getLogger(GanttController.class);
    public static final String BUSINESS_NAME = "Gantt图";

    @Resource
    private RdmsGanttService rdmsGanttService;
    @Resource
    private RdmsJobItemGanttService rdmsGanttJobitemService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsGanttLinksService rdmsGanttLinksService;
    @Autowired
    private RdmsJobItemService rdmsJobItemService;
    @Autowired
    private RdmsJobItemGanttService rdmsJobItemGanttService;


    @PostMapping("/saveLink")
    public ResponseDto<String> saveLink(@RequestBody RdmsGanttLinksDto rdmsGanttLinksDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsGanttLinks rdmsGanttLinks = CopyUtil.copy(rdmsGanttLinksDto, RdmsGanttLinks.class);
        String save = rdmsGanttLinksService.save(rdmsGanttLinks);
        responseDto.setContent(save);
        return responseDto;
    }

    @PostMapping("/delLink/{id}")
    public ResponseDto<String> delLink(@PathVariable String id) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        rdmsGanttLinksService.delete(id);
        responseDto.setContent(id);
        return responseDto;
    }

    @PostMapping("/getGanttLinkListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsGanttLinksDto>> getGanttLinkListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsGanttLinksDto>> responseDto = new ResponseDto<>();
        List<String> collect = new ArrayList<>();
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectId(subprojectId, null, null);
        if(!CollectionUtils.isEmpty(jobitemList)){
            collect = jobitemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
        }
        List<RdmsJobItemGantt> jobitemListBySubprojectId = rdmsJobItemGanttService.getJobitemListBySubprojectId(subprojectId);
        if(!CollectionUtils.isEmpty(jobitemListBySubprojectId)){
            List<String> collect2 = jobitemListBySubprojectId.stream().map(RdmsJobItemGantt::getId).collect(Collectors.toList());
            collect.addAll(collect2);
        }
        if(!CollectionUtils.isEmpty(collect)){
            List<RdmsGanttLinks> listBySourceIdList = rdmsGanttLinksService.getListBySourceIdList(collect);
            List<RdmsGanttLinksDto> rdmsGanttLinksDtos = CopyUtil.copyList(listBySourceIdList, RdmsGanttLinksDto.class);
            responseDto.setContent(rdmsGanttLinksDtos);
        }else{
            responseDto.setContent(null);
        }
        return responseDto;

    }

    @PostMapping("/getJobInfoAndSaveGanttData/{customerId}")
    public ResponseDto<Boolean> getJobInfoAndSaveGanttData(@PathVariable String customerId) {
        rdmsGanttService.getJobInfoAndSaveGanttData(customerId);
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(true);
        return responseDto;
    }


    @PostMapping("/saveTaskPlanJobitem")
    public ResponseDto<String> saveTaskPlanJobitem(@RequestBody RdmsJobItemGanttDto jobItemGanttDto) {
        String s = rdmsGanttJobitemService.saveJobitemToPlan(jobItemGanttDto);
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setContent(s);
        return responseDto;
    }

    @PostMapping("/getGanttListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsGanttDto>> getGanttListBySubprojectId(@PathVariable String subprojectId) {
        List<RdmsGanttDto> ganttList = rdmsGanttService.getGanttListBySubprojectId(subprojectId);
        if(!CollectionUtils.isEmpty(ganttList)){
            for(RdmsGanttDto item : ganttList){
                Double v = rdmsManhourService.transformToWorkerManhour(item.getManhour(), item.getExecutorId(), item.getCustomerId(), OperateTypeEnum.DEVELOP);
                item.setManhour(v);
            }
        }
        ResponseDto<List<RdmsGanttDto>> responseDto = new ResponseDto<>();
        responseDto.setContent(ganttList);
        return responseDto;
    }

    @PostMapping("/getJobItemGanttById/{JobItemGantt}")
    public ResponseDto<RdmsJobItemGanttDto> getJobItemGanttById(@PathVariable String JobItemGantt) {
        ResponseDto<RdmsJobItemGanttDto> responseDto = new ResponseDto<>();
        RdmsJobItemGantt jobItemGantt = rdmsGanttJobitemService.selectByPrimaryKey(JobItemGantt);
        if(jobItemGantt != null){
            RdmsJobItemGanttDto jobItemGanttDto = CopyUtil.copy(jobItemGantt, RdmsJobItemGanttDto.class);
            Double v = rdmsManhourService.transformToWorkerManhour(jobItemGantt.getManhour(), jobItemGantt.getExecutorId(), jobItemGantt.getCustomerId(), OperateTypeEnum.DEVELOP);
            jobItemGanttDto.setManhour(v);
            responseDto.setContent(jobItemGanttDto);
        }else{
            responseDto.setContent(null);
        }
        return responseDto;
    }

}
