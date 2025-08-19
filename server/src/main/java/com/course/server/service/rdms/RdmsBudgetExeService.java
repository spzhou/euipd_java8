/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsBudgetExe;
import com.course.server.domain.RdmsBudgetExeExample;
import com.course.server.domain.RdmsProjectSubproject;
import com.course.server.enums.rdms.ApplicationStatusEnum;
import com.course.server.enums.rdms.DocTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBomMapper;
import com.course.server.mapper.RdmsBudgetExeMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RdmsBudgetExeService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsBudgetExeService.class);

    @Resource
    private RdmsBudgetExeMapper rdmsBudgetExeMapper;


    /**
     * 注意: primaryKey 和 ItemId 是相同的
     * @param id
     * @return
     */
    public RdmsBudgetExe selectByPrimaryKey(String id){
        return rdmsBudgetExeMapper.selectByPrimaryKey(id);
    }


    @Transactional
    public List<RdmsBudgetExe> getChildrenListByParentId(String parentId) {
        List<String> childrenIdList = this.getChildrenIdListByParentId(parentId);
        RdmsBudgetExeExample budgetExeExample = new RdmsBudgetExeExample();
        budgetExeExample.setOrderByClause("create_time desc");
        budgetExeExample.createCriteria()
                .andIdIn(childrenIdList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetExe> rdmsBudgetExes = rdmsBudgetExeMapper.selectByExample(budgetExeExample);
        return rdmsBudgetExes;
    }

    @Transactional
    public List<String> getChildrenIdListByParentId(String parentId) {
        List<String> iterationIdList = new ArrayList<>();
        iterationIdList.add(parentId);
        List<String> childrenIdCollect = new ArrayList<>(iterationIdList);
        do {
            List<String> tempIdList = new ArrayList<>();
            for(String itemId: iterationIdList){
                List<RdmsBudgetExe> sonList = this.getSonListByParentId(itemId);
                List<String> sonIdList = sonList.stream().map(RdmsBudgetExe::getId).collect(Collectors.toList());
                List<String> remainSonIdList = new ArrayList<>();
                for(String id : sonIdList){
                    if(! iterationIdList.contains(id)){  //去掉ID与patentID相同的情况
                        remainSonIdList.add(id);
                    }
                }
                tempIdList.addAll(remainSonIdList);
            }
            if(CollectionUtils.isEmpty(tempIdList)){
                break;
            }else{
                iterationIdList.clear();
                childrenIdCollect.addAll(tempIdList);
                iterationIdList = tempIdList;
            }
        }while (true);
        return childrenIdCollect;
    }

    public List<RdmsBudgetExe> getSonListByParentId(String parentId ){
        RdmsBudgetExeExample budgetExeExample = new RdmsBudgetExeExample();
        budgetExeExample.createCriteria()
                .andParentIdEqualTo(parentId)
                .andIdNotEqualTo(parentId)   //避免将跟项目作为子项目
                .andDeletedEqualTo(0);
        return rdmsBudgetExeMapper.selectByExample(budgetExeExample);
    }

    public List<RdmsBudgetExe> getItemListByParentAndeDocType(String patent, DocTypeEnum docType){
        RdmsBudgetExeExample budgetExeExample = new RdmsBudgetExeExample();
        budgetExeExample.createCriteria()
                .andParentIdEqualTo(patent)
                .andIdNotEqualTo(patent)   //避免将跟项目作为子项目
                .andDeletedEqualTo(0)
                .andDocTypeEqualTo(docType.getType());
        return rdmsBudgetExeMapper.selectByExample(budgetExeExample);
    }

    /**
     * 保存
     */
    public String saveBudgetExe(RdmsBudgetExe budgetExe) {
        if(ObjectUtils.isEmpty(budgetExe.getId())){
            return this.insert(budgetExe);
        }else{
            RdmsBudgetExe rdmsBudgetExe = this.selectByPrimaryKey(budgetExe.getId());
            if(ObjectUtils.isEmpty(rdmsBudgetExe)){
                return this.insert(budgetExe);
            }else{
                return this.update(budgetExe);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsBudgetExe budgetExe) {
        if(ObjectUtils.isEmpty(budgetExe.getId())){
            budgetExe.setId(UuidUtil.getShortUuid());
        }
        RdmsBudgetExe rdmsBudgetExe = rdmsBudgetExeMapper.selectByPrimaryKey(budgetExe.getId());
        if(! ObjectUtils.isEmpty(rdmsBudgetExe)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            budgetExe.setCreateTime(new Date());
            budgetExe.setUpdateTime(new Date());
            budgetExe.setDeleted(0);
            rdmsBudgetExeMapper.insert(budgetExe);
            return budgetExe.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsBudgetExe budgetExe) {
        if(ObjectUtils.isEmpty(budgetExe.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBudgetExe rdmsBudgetExe = this.selectByPrimaryKey(budgetExe.getId());
        if(ObjectUtils.isEmpty(rdmsBudgetExe)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            budgetExe.setUpdateTime(new Date());
            budgetExe.setCreateTime(rdmsBudgetExe.getCreateTime());
            budgetExe.setDeleted(0);
            rdmsBudgetExeMapper.updateByPrimaryKey(budgetExe);
            return budgetExe.getId();
        }
    }

    private String updateByPrimaryKeySelective(RdmsBudgetExe budgetExe){
        rdmsBudgetExeMapper.updateByPrimaryKeySelective(budgetExe);
        return budgetExe.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBudgetExe budgetExe = rdmsBudgetExeMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(budgetExe)){
            budgetExe.setDeleted(1);
            this.update(budgetExe);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
