/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.calculate;

import com.course.server.domain.RdmsCharacter;
import com.course.server.domain.RdmsManhourStandard;
import com.course.server.dto.rdms.RdmsCharacterBudgetDto;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCharacterMapper;
import com.course.server.mapper.RdmsCustomerUserJobLevelMapper;
import com.course.server.service.rdms.RdmsManhourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class CalBudgetService {

    private static final Logger LOG = LoggerFactory.getLogger(CalBudgetService.class);

    @Resource
    private RdmsCharacterMapper rdmsCharacterMapper;
    @Resource
    private RdmsManhourService rdmsManhourService;


    /**
     * 设置角色预算信息
     * 根据预算数据传输对象设置角色的各项预算信息
     * 
     * @param character 角色对象
     * @param budget 角色预算数据传输对象
     */
    @Transactional
    public void setCharacterBudget(RdmsCharacter character, RdmsCharacterBudgetDto budget){
        character.setDevManhour(budget.getDevManhour());
        character.setTestManhour(budget.getTestManhour());
        character.setMaterialFee(budget.getMaterialFee());

        character.setDevManhourApproved(budget.getDevManhourApproved());
        character.setTestManhourApproved(budget.getTestManhourApproved());
        character.setMaterialFeeApproved(budget.getMaterialFeeApproved());

        character.setTestFee(budget.getTestFee());
        character.setPowerFee(budget.getPowerFee());
        character.setConferenceFee(budget.getConferenceFee());
        character.setBusinessFee(budget.getBusinessFee());
        character.setCooperationFee(budget.getCooperationFee());
        character.setPropertyFee(budget.getPropertyFee());
        character.setLaborFee(budget.getLaborFee());
        character.setConsultingFee(budget.getConsultingFee());
        character.setManagementFee(budget.getManagementFee());

        BigDecimal otherFee = this.calcSumOtherFee(character);
        character.setSumOtherFee(otherFee);

        BigDecimal aFloat = this.calcCharacterBudget(character, character.getCustomerId());
        character.setBudget(aFloat);

    }

    /**
     * 计算功能/特性开发的总预算
     * 根据角色信息和客户ID计算角色的总预算金额
     * 
     * @param character 角色对象
     * @param customerId 客户ID
     * @return 返回计算后的总预算金额
     */
    @Transactional
    public BigDecimal calcCharacterBudget(RdmsCharacter character, String customerId){
        RdmsManhourStandard standardManhourByCustomerId = rdmsManhourService.getStandardManhourByCustomerId(customerId);
        BigDecimal devManhourFee = BigDecimal.valueOf(character.getDevManhourApproved()).multiply(standardManhourByCustomerId.getDevManhourFee());
        BigDecimal testManhourFee = BigDecimal.valueOf(character.getTestManhourApproved()).multiply(standardManhourByCustomerId.getTestManhourFee());
        BigDecimal materialFee = ObjectUtils.isEmpty(character.getMaterialFeeApproved()) ? BigDecimal.ZERO : character.getMaterialFeeApproved();
        BigDecimal testFee = ObjectUtils.isEmpty(character.getTestFee()) ? BigDecimal.ZERO : character.getTestFee();
        BigDecimal powerFee = ObjectUtils.isEmpty(character.getPowerFee()) ? BigDecimal.ZERO : character.getPowerFee();
        BigDecimal conferenceFee = ObjectUtils.isEmpty(character.getConferenceFee()) ? BigDecimal.ZERO : character.getConferenceFee();
        BigDecimal businessFee = ObjectUtils.isEmpty(character.getBusinessFee()) ? BigDecimal.ZERO : character.getBusinessFee();
        BigDecimal cooperationFee = ObjectUtils.isEmpty(character.getCooperationFee()) ? BigDecimal.ZERO : character.getCooperationFee();
        BigDecimal propertyFee = ObjectUtils.isEmpty(character.getPropertyFee()) ? BigDecimal.ZERO : character.getPropertyFee();
        BigDecimal laborFee = ObjectUtils.isEmpty(character.getLaborFee()) ? BigDecimal.ZERO : character.getLaborFee();
        BigDecimal consultingFee = ObjectUtils.isEmpty(character.getConsultingFee()) ? BigDecimal.ZERO : character.getConsultingFee();
        BigDecimal managementFee = ObjectUtils.isEmpty(character.getManagementFee()) ? BigDecimal.ZERO : character.getManagementFee();
        return devManhourFee
                .add(testManhourFee)
                .add(materialFee)
                .add(testFee)
                .add(powerFee)
                .add(conferenceFee)
                .add(businessFee)
                .add(cooperationFee)
                .add(propertyFee)
                .add(laborFee)
                .add(consultingFee)
                .add(managementFee);
    }

    /**
     * 计算其他费用总和
     * 计算角色除开发工时和测试工时之外的所有其他费用总和
     * 
     * @param character 角色对象
     * @return 返回其他费用总和
     */
    @Transactional
    public BigDecimal calcSumOtherFee(RdmsCharacter character){
        BigDecimal testFee = ObjectUtils.isEmpty(character.getTestFee()) ? BigDecimal.ZERO : character.getTestFee();
        BigDecimal powerFee = ObjectUtils.isEmpty(character.getPowerFee()) ? BigDecimal.ZERO : character.getPowerFee();
        BigDecimal conferenceFee = ObjectUtils.isEmpty(character.getConferenceFee()) ? BigDecimal.ZERO : character.getConferenceFee();
        BigDecimal businessFee = ObjectUtils.isEmpty(character.getBusinessFee()) ? BigDecimal.ZERO : character.getBusinessFee();
        BigDecimal cooperationFee = ObjectUtils.isEmpty(character.getCooperationFee()) ? BigDecimal.ZERO : character.getCooperationFee();
        BigDecimal propertyFee = ObjectUtils.isEmpty(character.getPropertyFee()) ? BigDecimal.ZERO : character.getPropertyFee();
        BigDecimal laborFee = ObjectUtils.isEmpty(character.getLaborFee()) ? BigDecimal.ZERO : character.getLaborFee();
        BigDecimal consultingFee = ObjectUtils.isEmpty(character.getConsultingFee()) ? BigDecimal.ZERO : character.getConsultingFee();
        BigDecimal managementFee = ObjectUtils.isEmpty(character.getManagementFee()) ? BigDecimal.ZERO : character.getManagementFee();
        return testFee
                .add(powerFee)
                .add(conferenceFee)
                .add(businessFee)
                .add(cooperationFee)
                .add(propertyFee)
                .add(laborFee)
                .add(consultingFee)
                .add(managementFee);
    }

}
