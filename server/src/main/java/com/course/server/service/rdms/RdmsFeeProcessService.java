/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsFeeProcess;
import com.course.server.domain.RdmsFeeProcessExample;
import com.course.server.dto.rdms.RdmsFeeProcessDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsFeeProcessMapper;
import com.course.server.mapper.RdmsMaterialProcessMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsFeeProcessService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsFeeProcessService.class);

    @Resource
    private RdmsFeeProcessMapper rdmsFeeProcessMapper;


    /**
     * 根据特性编号,进行查询
     */
    public long getFeeProcessCount(String feeId) {
        RdmsFeeProcessExample rdmsFeeProcessExample = new RdmsFeeProcessExample();
        rdmsFeeProcessExample.createCriteria().andFeeIdEqualTo(feeId);
        return rdmsFeeProcessMapper.countByExample(rdmsFeeProcessExample);
    }
    /**
     */
    public List<RdmsFeeProcessDto> getListByFeeId(String feeId){
        RdmsFeeProcessExample feeProcessExample = new RdmsFeeProcessExample();
        feeProcessExample.createCriteria().andFeeIdEqualTo(feeId).andDeletedEqualTo(0);
        List<RdmsFeeProcess> rdmsFeeProcesses = rdmsFeeProcessMapper.selectByExample(feeProcessExample);
        return CopyUtil.copyList(rdmsFeeProcesses, RdmsFeeProcessDto.class);
    }

    public RdmsFeeProcess selectByPrimaryKey(String id){
        return rdmsFeeProcessMapper.selectByPrimaryKey(id);
    }
    /**
     * 保存
     */
    public String save(RdmsFeeProcess feeProcess) {
        if(ObjectUtils.isEmpty(feeProcess.getId())){
            return this.insert(feeProcess);
        }else{
            RdmsFeeProcess rdmsFeeProcess = this.selectByPrimaryKey(feeProcess.getId());
            if(ObjectUtils.isEmpty(rdmsFeeProcess)){
                return this.insert(feeProcess);
            }else{
                return this.update(feeProcess);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsFeeProcess feeProcess) {
        if(ObjectUtils.isEmpty(feeProcess.getId())){
            feeProcess.setId(UuidUtil.getShortUuid());
        }
        RdmsFeeProcess rdmsFeeProcess = rdmsFeeProcessMapper.selectByPrimaryKey(feeProcess.getId());
        if(! ObjectUtils.isEmpty(rdmsFeeProcess)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            feeProcess.setCreateTime(new Date());
            feeProcess.setDeleted(0);
            rdmsFeeProcessMapper.insert(feeProcess);
            return feeProcess.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsFeeProcess feeProcess) {
        if(ObjectUtils.isEmpty(feeProcess.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsFeeProcess rdmsFeeProcess = this.selectByPrimaryKey(feeProcess.getId());
        if(ObjectUtils.isEmpty(rdmsFeeProcess)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            feeProcess.setDeleted(0);
            rdmsFeeProcessMapper.updateByPrimaryKey(feeProcess);
            return feeProcess.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsFeeProcess feeProcess){
        rdmsFeeProcessMapper.updateByPrimaryKeySelective(feeProcess);
        return feeProcess.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsFeeProcess feeProcess = rdmsFeeProcessMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(feeProcess)){
            feeProcess.setDeleted(1);
            this.update(feeProcess);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
