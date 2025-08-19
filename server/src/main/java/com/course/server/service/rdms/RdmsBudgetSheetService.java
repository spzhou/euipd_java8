/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.domain.RdmsProjectBudget;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsCharacterBudgetDto;
import com.course.server.dto.rdms.RdmsProjectBudgetDto;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.enums.rdms.ProjectTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCharacterMapper;
import com.course.server.mapper.RdmsProjectBudgetMapper;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RdmsBudgetSheetService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsBudgetSheetService.class);

    @Resource
    private RdmsProjectBudgetMapper rdmsProjectBudgetMapper;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsCharacterMapper rdmsCharacterMapper;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;


    public void list(PageDto<RdmsProjectBudgetDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectBudgetExample jobLevelExample = new RdmsProjectBudgetExample();
        List<RdmsProjectBudget> rdmsProjectBudgets = rdmsProjectBudgetMapper.selectByExample(jobLevelExample);

        PageInfo<RdmsProjectBudget> pageInfo = new PageInfo<>(rdmsProjectBudgets);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsProjectBudgetDto> rdmsProjectBudgetDtos = CopyUtil.copyList(rdmsProjectBudgets, RdmsProjectBudgetDto.class);

        pageDto.setList(rdmsProjectBudgetDtos);
    }

    public RdmsProjectBudget selectByPrimaryKey(String id){
        return rdmsProjectBudgetMapper.selectByPrimaryKey(id);
    }

    public BigDecimal calCharacterBudget(RdmsCharacterBudgetDto characterBudget){
        RdmsManhourStandard manhourByCustomerId = rdmsManhourService.getStandardManhourByCustomerId(characterBudget.getCustomerId());
        BigDecimal budget = BigDecimal.valueOf(characterBudget.getDevManhourApproved()).multiply(manhourByCustomerId.getDevManhourFee())
                .add(BigDecimal.valueOf(characterBudget.getTestManhourApproved()).multiply(manhourByCustomerId.getTestManhourFee()))
                .add(characterBudget.getMaterialFeeApproved())
                .add(characterBudget.getTestFee())
                .add(characterBudget.getPowerFee())
                .add(characterBudget.getConferenceFee())
                .add(characterBudget.getBusinessFee())
                .add(characterBudget.getCooperationFee())
                .add(characterBudget.getPropertyFee())
                .add(characterBudget.getLaborFee())
                .add(characterBudget.getConsultingFee())
                .add(characterBudget.getManagementFee());
        return budget;
    }

    public BigDecimal getPoolOfSubProjectBySubprojectId(String subprojectId){
        RdmsCharacterExample characterExample = new RdmsCharacterExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(CharacterStatusEnum.SAVED.getStatus());
        statusList.add(CharacterStatusEnum.HISTORY.getStatus());
        statusList.add(CharacterStatusEnum.DISCARD.getStatus());
        characterExample.createCriteria().andSubprojectIdEqualTo(subprojectId)
                .andStatusNotIn(statusList)
                .andAuxStatusNotEqualTo(CharacterStatusEnum.HISTORY.getStatus())
                .andProjectTypeNotEqualTo(ProjectTypeEnum.PRE_PROJECT.getType());
        List<RdmsCharacter> rdmsCharacters = rdmsCharacterMapper.selectByExample(characterExample);
        //注意: 当数据库字段值有null存在时, 是不能做 notEqualTo 不等于 比较的;  可以做EqualTo比价
//        List<RdmsCharacter> rdmsCharacterList = rdmsCharacters.stream().filter(item -> !item.getAuxStatus().equals(CharacterStatusEnum.HISTORY.getStatus())).collect(Collectors.toList());
        BigDecimal sumBudget = rdmsCharacters.stream().map(RdmsCharacter::getBudget).reduce(BigDecimal.ZERO, BigDecimal::add);

        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        if(! ObjectUtils.isEmpty(subproject)){
            BigDecimal projectBudget = subproject.getBudget().add(subproject.getAddCharge());
            return projectBudget.subtract(sumBudget);
        }else{
            return new BigDecimal("0.0");
        }
    }

    /**
     * 保存
     */
    public String save(RdmsProjectBudget budget) {
        if(ObjectUtils.isEmpty(budget.getId())){
            return this.insert(budget);
        }else{
            RdmsProjectBudget rdmsProjectBudget = this.selectByPrimaryKey(budget.getId());
            if(ObjectUtils.isEmpty(rdmsProjectBudget)){
                return this.insert(budget);
            }else{
                return this.update(budget);
            }
        }
    }

    private String insert(RdmsProjectBudget budget) {
        if(org.springframework.util.ObjectUtils.isEmpty(budget.getId())){  //当前端页面给出projectID时,将不为空
            budget.setId(UuidUtil.getShortUuid());
        }
        RdmsProjectBudget rdmsProjectBudget = this.selectByPrimaryKey(budget.getId());
        if(ObjectUtils.isEmpty(rdmsProjectBudget)){
            budget.setDeleted("0");
            budget.setCreateTime(new Date());
            rdmsProjectBudgetMapper.insert(budget);
            return budget.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsProjectBudget budget) {
        if(ObjectUtils.isEmpty(budget.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsProjectBudget rdmsProjectBudget = this.selectByPrimaryKey(budget.getId());
        if(ObjectUtils.isEmpty(rdmsProjectBudget)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            budget.setDeleted("0");
            budget.setCreateTime(rdmsProjectBudget.getCreateTime());
            rdmsProjectBudgetMapper.updateByPrimaryKey(budget);
            return budget.getId();
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProjectBudget rdmsProjectBudget = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsProjectBudget)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsProjectBudget.setDeleted("1");
            rdmsProjectBudgetMapper.updateByPrimaryKey(rdmsProjectBudget);
        }
    }

}
