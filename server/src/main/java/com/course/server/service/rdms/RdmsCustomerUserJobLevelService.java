/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsCustomerUserJobLevel;
import com.course.server.domain.RdmsCustomerUserJobLevelExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsCustomerUserJobLevelDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCustomerUserJobLevelMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RdmsCustomerUserJobLevelService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerUserJobLevelService.class);

    @Resource
    private RdmsCustomerUserJobLevelMapper rdmsCustomerUserJobLevelMapper;


    public void list(PageDto<RdmsCustomerUserJobLevelDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerUserJobLevelExample jobLevelExample = new RdmsCustomerUserJobLevelExample();
        List<RdmsCustomerUserJobLevel> rdmsCustomerUserJobLevels = rdmsCustomerUserJobLevelMapper.selectByExample(jobLevelExample);

        PageInfo<RdmsCustomerUserJobLevel> pageInfo = new PageInfo<>(rdmsCustomerUserJobLevels);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsCustomerUserJobLevelDto> rdmsCustomerUserJobLevelDtos = CopyUtil.copyList(rdmsCustomerUserJobLevels, RdmsCustomerUserJobLevelDto.class);

        pageDto.setList(rdmsCustomerUserJobLevelDtos);
    }

    public List<RdmsCustomerUserJobLevelDto> listAll(String customerId) {
        RdmsCustomerUserJobLevelExample jobLevelExample = new RdmsCustomerUserJobLevelExample();
        jobLevelExample.setOrderByClause("level_code desc");
        jobLevelExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo("0");
        List<RdmsCustomerUserJobLevel> rdmsCustomerUserJobLevels = rdmsCustomerUserJobLevelMapper.selectByExample(jobLevelExample);
        List<RdmsCustomerUserJobLevelDto> rdmsCustomerUserJobLevelDtos = CopyUtil.copyList(rdmsCustomerUserJobLevels, RdmsCustomerUserJobLevelDto.class);
        return rdmsCustomerUserJobLevelDtos;
    }


    public RdmsCustomerUserJobLevel selectByPrimaryKey(String id){
        return rdmsCustomerUserJobLevelMapper.selectByPrimaryKey(id);
    }

    public int glanceByLevelCode(RdmsCustomerUserJobLevelDto jobLevelDto){
        RdmsCustomerUserJobLevelExample jobLevelExample = new RdmsCustomerUserJobLevelExample();
        jobLevelExample.createCriteria().andLevelCodeEqualTo(jobLevelDto.getLevelCode()).andCustomerIdEqualTo(jobLevelDto.getCustomerId()).andDeletedEqualTo("0");
        List<RdmsCustomerUserJobLevel> rdmsCustomerUserJobLevels = rdmsCustomerUserJobLevelMapper.selectByExample(jobLevelExample);
        if(! CollectionUtils.isEmpty(rdmsCustomerUserJobLevels)){
            return rdmsCustomerUserJobLevels.size();
        }
        return 0;
    }

    public RdmsCustomerUserJobLevel getJobLevelByLevelCode(String levelCode, String customerId){
        if(!ObjectUtils.isEmpty(levelCode)){
            RdmsCustomerUserJobLevelExample jobLevelExample = new RdmsCustomerUserJobLevelExample();
            jobLevelExample.createCriteria().andLevelCodeEqualTo(levelCode).andCustomerIdEqualTo(customerId).andDeletedEqualTo("0");
            List<RdmsCustomerUserJobLevel> rdmsCustomerUserJobLevels = rdmsCustomerUserJobLevelMapper.selectByExample(jobLevelExample);
            if(! CollectionUtils.isEmpty(rdmsCustomerUserJobLevels)){
                return rdmsCustomerUserJobLevels.get(0);
            }
        }else{
            throw new RuntimeException("存在职级为空的情况,请检查所有员工的职级设定!");
        }
        return null;
    }

    /**
     * 保存
     */
    public String save(RdmsCustomerUserJobLevel jobLevel) {
        if(!ObjectUtils.isEmpty(jobLevel) && !ObjectUtils.isEmpty(jobLevel.getLevelCode())){
            String upperCase = jobLevel.getLevelCode().toUpperCase();
            jobLevel.setLevelCode(upperCase);
        }
        RdmsCustomerUserJobLevelExample jobLevelExample = new RdmsCustomerUserJobLevelExample();
        jobLevelExample.createCriteria().andLevelCodeEqualTo(jobLevel.getLevelCode()).andCustomerIdEqualTo(jobLevel.getCustomerId()).andDeletedEqualTo("0");
        List<RdmsCustomerUserJobLevel> rdmsCustomerUserJobLevels = rdmsCustomerUserJobLevelMapper.selectByExample(jobLevelExample);
        if(CollectionUtils.isEmpty(rdmsCustomerUserJobLevels)){
            if(ObjectUtils.isEmpty(jobLevel.getId())){
                return this.insert(jobLevel);
            }else{
                RdmsCustomerUserJobLevel rdmsCustomerUserJobLevel = this.selectByPrimaryKey(jobLevel.getId());
                if(ObjectUtils.isEmpty(rdmsCustomerUserJobLevel)){
                    return this.insert(jobLevel);
                }else{
                    return this.update(jobLevel);
                }
            }
        }else{
            RdmsCustomerUserJobLevel rdmsCustomerUserJobLevel = rdmsCustomerUserJobLevels.get(0);
            jobLevel.setId(rdmsCustomerUserJobLevel.getId());
            return this.update(jobLevel);
        }
    }

    private String insert(RdmsCustomerUserJobLevel jobLevel) {
        if(org.springframework.util.ObjectUtils.isEmpty(jobLevel.getId())){  //当前端页面给出projectID时,将不为空
            jobLevel.setId(UuidUtil.getShortUuid());
        }
        RdmsCustomerUserJobLevel rdmsCustomerUserJobLevel = this.selectByPrimaryKey(jobLevel.getId());
        if(ObjectUtils.isEmpty(rdmsCustomerUserJobLevel)){
            jobLevel.setDeleted("0");
            rdmsCustomerUserJobLevelMapper.insert(jobLevel);
            return jobLevel.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsCustomerUserJobLevel jobLevel) {
        if(ObjectUtils.isEmpty(jobLevel.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsCustomerUserJobLevel rdmsCustomerUserJobLevel = this.selectByPrimaryKey(jobLevel.getId());
        if(ObjectUtils.isEmpty(rdmsCustomerUserJobLevel)){
            return this.insert(jobLevel);
        }else{
            jobLevel.setDeleted("0");
            rdmsCustomerUserJobLevelMapper.updateByPrimaryKey(jobLevel);
            return jobLevel.getId();
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCustomerUserJobLevel rdmsCustomerUserJobLevel = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsCustomerUserJobLevel)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsCustomerUserJobLevel.setDeleted("1");
            rdmsCustomerUserJobLevelMapper.updateByPrimaryKey(rdmsCustomerUserJobLevel);
        }
    }

}
