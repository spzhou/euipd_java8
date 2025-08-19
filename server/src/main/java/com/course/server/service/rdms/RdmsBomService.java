/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsBom;
import com.course.server.domain.RdmsBomExample;
import com.course.server.domain.RdmsBomTree;

import com.course.server.enums.rdms.ApplicationStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBomMapper;

import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RdmsBomService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsBomService.class);

    @Resource
    private RdmsBomMapper rdmsBomMapper;
    @Resource
    private RdmsBomTreeService rdmsBomTreeService;

    public List<RdmsBom> getAllRecordList(){
        RdmsBomExample bomExample = new RdmsBomExample();
        bomExample.createCriteria().andDeletedEqualTo(0);
        return rdmsBomMapper.selectByExample(bomExample);
    }

    public List<String> getBomCodeListByCharacterId(String characterId){
        RdmsBomExample bomExample = new RdmsBomExample();
        bomExample.createCriteria().andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);
        List<RdmsBom> rdmsBoms = rdmsBomMapper.selectByExample(bomExample);
        return rdmsBoms.stream().map(RdmsBom::getBomCode).collect(Collectors.toList());
    }

    public RdmsBom selectByPrimaryKey(String id){
        return rdmsBomMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsBom bom) {
        if(ObjectUtils.isEmpty(bom.getId())){
            return this.insert(bom);
        }else{
            RdmsBom rdmsBom = this.selectByPrimaryKey(bom.getId());
            if(ObjectUtils.isEmpty(rdmsBom)){
                return this.insert(bom);
            }else{
                return this.update(bom);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsBom bom) {
        if(ObjectUtils.isEmpty(bom.getId())){
            bom.setId(UuidUtil.getShortUuid());
        }
        RdmsBom rdmsBom = rdmsBomMapper.selectByPrimaryKey(bom.getId());
        if(! ObjectUtils.isEmpty(rdmsBom)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            bom.setCreateTime(new Date());
            bom.setUpdateTime(new Date());
            bom.setDeleted(0);
            if(ObjectUtils.isEmpty(bom.getAuxStatus())){
                bom.setAuxStatus(ApplicationStatusEnum.NOTSET.getStatus());
            }
            rdmsBomMapper.insert(bom);
            //同时保存到bomTree中
            RdmsBomTree bomTree = new RdmsBomTree();
            bomTree.setId(bom.getId());
            bomTree.setParent(null);
            bomTree.setDeep(0);
            bomTree.setBomCode(bom.getBomCode());
            bomTree.setName(bom.getName());
            bomTree.setModel(bom.getModel());
            bomTree.setUnit(bom.getUnit());
            bomTree.setAmount(bom.getAmount());
            bomTree.setSubprojectId(bom.getSubprojectId());
            rdmsBomTreeService.save(bomTree);

            return bom.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsBom bom) {
        if(ObjectUtils.isEmpty(bom.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBom rdmsBom = this.selectByPrimaryKey(bom.getId());
        if(ObjectUtils.isEmpty(rdmsBom)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            bom.setUpdateTime(new Date());
            bom.setCreateTime(rdmsBom.getCreateTime());
//            bom.setDeleted(0);
            rdmsBomMapper.updateByPrimaryKey(bom);

            //同时保存到bomTree中
            RdmsBomTree bomTree = rdmsBomTreeService.selectByPrimaryKey(bom.getId());
            bomTree.setParent(bomTree.getParent());
            bomTree.setDeep(bomTree.getDeep());
            bomTree.setBomCode(bom.getBomCode());
            bomTree.setName(bom.getName());
            bomTree.setModel(bom.getModel());
            bomTree.setUnit(bom.getUnit());
            bomTree.setAmount(bom.getAmount());
            bomTree.setSubprojectId(bom.getSubprojectId());
            rdmsBomTreeService.save(bomTree);

            return bom.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsBom bom){
        rdmsBomMapper.updateByPrimaryKeySelective(bom);

        //同时保存到bomTree中
        RdmsBomTree bomTree = rdmsBomTreeService.selectByPrimaryKey(bom.getId());
        bomTree.setParent(bomTree.getParent());
        bomTree.setDeep(bomTree.getDeep());
        bomTree.setBomCode(bom.getBomCode());
        bomTree.setName(bom.getName());
        bomTree.setModel(bom.getModel());
        bomTree.setUnit(bom.getUnit());
        bomTree.setAmount(bom.getAmount());
        bomTree.setSubprojectId(bom.getSubprojectId());
        rdmsBomTreeService.updateByPrimaryKeySelective(bomTree);

        return bom.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBom bom = rdmsBomMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(bom)){
            bom.setDeleted(1);
            this.update(bom);

            //删除bomTree
            rdmsBomTreeService.delete(id);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
