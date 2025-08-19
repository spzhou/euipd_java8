/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsProjectTask;
import com.course.server.domain.RdmsProjectTaskExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsProjectTaskDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsProjectTaskMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsProjectTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsProjectTaskService.class);

    @Resource
    private RdmsProjectTaskMapper rdmsProjectTaskMapper;




    public void list(PageDto<RdmsProjectTaskDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectTaskExample projectTaskExample = new RdmsProjectTaskExample();
        List<RdmsProjectTask> rdmsProjectTasks = rdmsProjectTaskMapper.selectByExample(projectTaskExample);

        PageInfo<RdmsProjectTask> pageInfo = new PageInfo<>(rdmsProjectTasks);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsProjectTaskDto> rdmsCustomerUserJobLevelDtos = CopyUtil.copyList(rdmsProjectTasks, RdmsProjectTaskDto.class);

        pageDto.setList(rdmsCustomerUserJobLevelDtos);
    }

    public RdmsProjectTask selectByPrimaryKey(String id){
        return rdmsProjectTaskMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsProjectTask projectTask) {
        if(ObjectUtils.isEmpty(projectTask.getId())){
            return this.insert(projectTask);
        }else{
            RdmsProjectTask rdmsProjectTask = this.selectByPrimaryKey(projectTask.getId());
            if(ObjectUtils.isEmpty(rdmsProjectTask)){
                return this.insert(projectTask);
            }else{
                return this.update(projectTask);
            }
        }
    }

    private String insert(RdmsProjectTask projectTask) {
        if(ObjectUtils.isEmpty(projectTask.getId())){  //当前端页面给出projectID时,将不为空
            projectTask.setId(UuidUtil.getShortUuid());
        }
        projectTask.setTaskSerialNo(projectTask.getId());
        RdmsProjectTask rdmsProjectTask = this.selectByPrimaryKey(projectTask.getId());
        if(ObjectUtils.isEmpty(rdmsProjectTask)){
            projectTask.setDeleted(0);
            projectTask.setCreateTime(new Date());
            rdmsProjectTaskMapper.insert(projectTask);
            return projectTask.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsProjectTask projectTask) {
        if(ObjectUtils.isEmpty(projectTask.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsProjectTask rdmsProjectTask = this.selectByPrimaryKey(projectTask.getId());
        if(ObjectUtils.isEmpty(rdmsProjectTask)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            projectTask.setDeleted(0);
            rdmsProjectTaskMapper.updateByPrimaryKey(projectTask);
            return projectTask.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsProjectTask projectTask){
        rdmsProjectTaskMapper.updateByPrimaryKeySelective(projectTask);
        return projectTask.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProjectTask rdmsProjectTask = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsProjectTask)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsProjectTask.setDeleted(1);
            rdmsProjectTaskMapper.updateByPrimaryKey(rdmsProjectTask);
        }
    }

}
