/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsJobItemProcessExample;
import com.course.server.domain.RdmsMaterialProcess;
import com.course.server.domain.RdmsMaterialProcessExample;
import com.course.server.domain.RdmsMaterialProcessReturn;
import com.course.server.dto.rdms.RdmsMaterialProcessDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsMaterialProcessMapper;
import com.course.server.mapper.RdmsMaterialProcessReturnMapper;
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
public class RdmsMaterialProcessService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsMaterialProcessService.class);

    @Resource
    private RdmsMaterialProcessMapper rdmsMaterialProcessMapper;
    @Resource
    private RdmsMaterialProcessReturnMapper rdmsMaterialProcessReturnMapper;


    /**
     * 根据特性编号,进行查询
     */
    public long getMaterialProcessCount(String materialId) {
        RdmsMaterialProcessExample rdmsMaterialProcessExample = new RdmsMaterialProcessExample();
        rdmsMaterialProcessExample.createCriteria().andMaterialIdEqualTo(materialId);
        return rdmsMaterialProcessMapper.countByExample(rdmsMaterialProcessExample);
    }
    /**
     */
    public List<RdmsMaterialProcessDto> getListByMaterialId(String materialId){
        RdmsMaterialProcessExample materialProcessExample = new RdmsMaterialProcessExample();
        materialProcessExample.createCriteria().andMaterialIdEqualTo(materialId).andDeletedEqualTo(0);
        List<RdmsMaterialProcess> rdmsMaterialProcesses = rdmsMaterialProcessMapper.selectByExample(materialProcessExample);
        return CopyUtil.copyList(rdmsMaterialProcesses, RdmsMaterialProcessDto.class);
    }

    public RdmsMaterialProcess selectByPrimaryKey(String id){
        return rdmsMaterialProcessMapper.selectByPrimaryKey(id);
    }
    public RdmsMaterialProcessReturn selectByPrimaryKey_return(String id){
        return rdmsMaterialProcessReturnMapper.selectByPrimaryKey(id);
    }
    /**
     * 保存
     */
    public String save(RdmsMaterialProcess materialProcess) {
        if(ObjectUtils.isEmpty(materialProcess.getId())){
            return this.insert(materialProcess);
        }else{
            RdmsMaterialProcess rdmsMaterialProcess = this.selectByPrimaryKey(materialProcess.getId());
            if(ObjectUtils.isEmpty(rdmsMaterialProcess)){
                return this.insert(materialProcess);
            }else{
                return this.update(materialProcess);
            }
        }
    }

    public String save_return(RdmsMaterialProcessReturn materialProcess) {
        if(ObjectUtils.isEmpty(materialProcess.getId())){
            return this.insert_return(materialProcess);
        }else{
            RdmsMaterialProcess rdmsMaterialProcess = this.selectByPrimaryKey(materialProcess.getId());
            if(ObjectUtils.isEmpty(rdmsMaterialProcess)){
                return this.insert_return(materialProcess);
            }else{
                return this.update_return(materialProcess);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsMaterialProcess materialProcess) {
        if(ObjectUtils.isEmpty(materialProcess.getId())){
            materialProcess.setId(UuidUtil.getShortUuid());
        }
        RdmsMaterialProcess rdmsMaterialProcess = rdmsMaterialProcessMapper.selectByPrimaryKey(materialProcess.getId());
        if(! ObjectUtils.isEmpty(rdmsMaterialProcess)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            materialProcess.setCreateTime(new Date());
            materialProcess.setDeleted(0);
            rdmsMaterialProcessMapper.insert(materialProcess);
            return materialProcess.getId();
        }
    }

    private String insert_return(RdmsMaterialProcessReturn materialProcess) {
        if(ObjectUtils.isEmpty(materialProcess.getId())){
            materialProcess.setId(UuidUtil.getShortUuid());
        }
        RdmsMaterialProcessReturn rdmsMaterialProcessReturn = rdmsMaterialProcessReturnMapper.selectByPrimaryKey(materialProcess.getId());
        if(! ObjectUtils.isEmpty(rdmsMaterialProcessReturn)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            materialProcess.setCreateTime(new Date());
            materialProcess.setDeleted(0);
            rdmsMaterialProcessReturnMapper.insert(materialProcess);
            return materialProcess.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsMaterialProcess materialProcess) {
        if(ObjectUtils.isEmpty(materialProcess.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialProcess rdmsMaterialProcess = this.selectByPrimaryKey(materialProcess.getId());
        if(ObjectUtils.isEmpty(rdmsMaterialProcess)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            materialProcess.setDeleted(0);
            rdmsMaterialProcessMapper.updateByPrimaryKey(materialProcess);
            return materialProcess.getId();
        }
    }

    public String update_return(RdmsMaterialProcessReturn materialProcess) {
        if(ObjectUtils.isEmpty(materialProcess.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsMaterialProcessReturn rdmsMaterialProcessReturn = this.selectByPrimaryKey_return(materialProcess.getId());
        if(ObjectUtils.isEmpty(rdmsMaterialProcessReturn)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            materialProcess.setDeleted(0);
            rdmsMaterialProcessReturnMapper.updateByPrimaryKey(materialProcess);
            return materialProcess.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsMaterialProcess materialProcess){
        rdmsMaterialProcessMapper.updateByPrimaryKeySelective(materialProcess);
        return materialProcess.getId();
    }

    public String updateByPrimaryKeySelective_return(RdmsMaterialProcessReturn materialProcess){
        rdmsMaterialProcessReturnMapper.updateByPrimaryKeySelective(materialProcess);
        return materialProcess.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsMaterialProcess materialProcess = rdmsMaterialProcessMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(materialProcess)){
            materialProcess.setDeleted(1);
            this.update(materialProcess);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    public void delete_return(String id) {
        RdmsMaterialProcessReturn rdmsMaterialProcessReturn = rdmsMaterialProcessReturnMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsMaterialProcessReturn)){
            rdmsMaterialProcessReturn.setDeleted(1);
            this.update_return(rdmsMaterialProcessReturn);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
