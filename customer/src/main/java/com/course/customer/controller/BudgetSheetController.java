/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCharacter;
import com.course.server.domain.RdmsManhourStandard;
import com.course.server.domain.RdmsProjectBudget;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsCharacterBudgetDto;
import com.course.server.dto.rdms.RdmsProjectBudgetDto;
import com.course.server.mapper.rdms.MyCharacterBudgetMapper;
import com.course.server.service.rdms.RdmsBudgetSheetService;
import com.course.server.service.rdms.RdmsCharacterService;
import com.course.server.service.rdms.RdmsManhourService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetSheetController {
    private static final Logger LOG = LoggerFactory.getLogger(BudgetSheetController.class);
    public static final String BUSINESS_NAME = "预算处理";

    @Resource
    private RdmsBudgetSheetService rdmsBudgetSheetService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private MyCharacterBudgetMapper myCharacterBudgetMapper;
    @Resource
    private RdmsManhourService rdmsManhourService;


    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsProjectBudgetDto>> list(@RequestBody PageDto<RdmsProjectBudgetDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectBudgetDto>> responseDto = new ResponseDto<>();
        rdmsBudgetSheetService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 对于分解记录而言, 这里的characterID是当前分解记录的 parentCharacterID
     * @param parentCharacterId
     * @return
     */
    @PostMapping("/getCharacterBudget_decompose/{parentCharacterId}")
    public ResponseDto<RdmsCharacterBudgetDto> getCharacterBudget_decompose(@PathVariable String parentCharacterId){
        ResponseDto<RdmsCharacterBudgetDto> responseDto = new ResponseDto<>();
        RdmsCharacter parentCharacter = rdmsCharacterService.selectByPrimaryKey(parentCharacterId);
        RdmsCharacterBudgetDto characterBudgetDto = CopyUtil.copy(parentCharacter, RdmsCharacterBudgetDto.class);
        List<RdmsCharacterBudgetDto> decomposeCharacterBudgetDtos = myCharacterBudgetMapper.listDecomposeCharacterBudget(parentCharacterId);
        characterBudgetDto.setDecomposeCharacterBudgets(decomposeCharacterBudgetDtos);

        double decomposeSumDevManhour = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getDevManhourApproved).reduce(0.0, Double::sum);
        double decomposeSumTestManhour = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getTestManhourApproved).reduce(0.0, Double::sum);
        BigDecimal decomposeSumMaterialFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getMaterialFeeApproved).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal decomposeSumOtherFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getSumOtherFee).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal testFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getTestFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal powerFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getPowerFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal conferenceFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getConferenceFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal businessFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getBusinessFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal cooperationFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getCooperationFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal propertyFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getPropertyFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal laborFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getLaborFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal consultingFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getConsultingFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal managementFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getManagementFee).reduce(BigDecimal.ZERO, BigDecimal::add);

        if(ObjectUtils.isEmpty(parentCharacter.getDevManhourApproved())){
            parentCharacter.setDevManhourApproved(0.0);
        }
        if(ObjectUtils.isEmpty(parentCharacter.getTestManhourApproved())){
            parentCharacter.setTestManhourApproved(0.0);
        }
        if(ObjectUtils.isEmpty(parentCharacter.getMaterialFeeApproved())){
            parentCharacter.setMaterialFeeApproved(BigDecimal.ZERO);
        }

        RdmsManhourStandard standManhour = rdmsManhourService.getStandardManhourByCustomerId(parentCharacter.getCustomerId());
        characterBudgetDto.setRemainDevManhour(parentCharacter.getDevManhourApproved() - decomposeSumDevManhour);
        characterBudgetDto.setRemainTestManhour(parentCharacter.getTestManhourApproved() - decomposeSumTestManhour);
        characterBudgetDto.setRemainMaterialBudget(parentCharacter.getMaterialFeeApproved().subtract(decomposeSumMaterialFee));
        characterBudgetDto.setRemainOtherBudget(parentCharacter.getSumOtherFee().subtract(decomposeSumOtherFee));

        characterBudgetDto.setRemainTestFee(parentCharacter.getTestFee().subtract(testFee));
        characterBudgetDto.setRemainPowerFee(parentCharacter.getPowerFee().subtract(powerFee));
        characterBudgetDto.setRemainConferenceFee(parentCharacter.getConferenceFee().subtract(conferenceFee));
        characterBudgetDto.setRemainBusinessFee(parentCharacter.getBusinessFee().subtract(businessFee));
        characterBudgetDto.setRemainCooperationFee(parentCharacter.getCooperationFee().subtract(cooperationFee));
        characterBudgetDto.setRemainPropertyFee(parentCharacter.getPropertyFee().subtract(propertyFee));
        characterBudgetDto.setRemainLaborFee(parentCharacter.getLaborFee().subtract(laborFee));
        characterBudgetDto.setRemainConsultingFee(parentCharacter.getConsultingFee().subtract(consultingFee));
        characterBudgetDto.setRemainManagementFee(parentCharacter.getManagementFee().subtract(managementFee));

        BigDecimal sumDecomposeBudget =
                standManhour.getDevManhourFee().multiply(BigDecimal.valueOf(decomposeSumDevManhour))
                .add(BigDecimal.valueOf(decomposeSumTestManhour).multiply(standManhour.getTestManhourFee()))
                .add(decomposeSumMaterialFee)
                /*.add(testFee)
                .add(powerFee)
                .add(conferenceFee)
                .add(businessFee)
                .add(cooperationFee)
                .add(propertyFee)
                .add(laborFee)
                .add(consultingFee)
                .add(managementFee)*/
                .add(decomposeSumOtherFee);

        characterBudgetDto.setRemainBudget(parentCharacter.getBudget().subtract(sumDecomposeBudget));

        characterBudgetDto.setStandDevManhourFee(standManhour.getDevManhourFee());
        characterBudgetDto.setStandTestManhourFee(standManhour.getTestManhourFee());
        responseDto.setContent(characterBudgetDto);
        return responseDto;
    }

    /**
     * 对于分迭代记录录而言, 这里的characterID是当前记录的characterID
     * @param characterId
     * @return
     */
    @PostMapping("/getCharacterBudget_iteration/{characterId}")
    public ResponseDto<RdmsCharacterBudgetDto> getCharacterBudget_iteration(@PathVariable String characterId){
        ResponseDto<RdmsCharacterBudgetDto> responseDto = new ResponseDto<>();
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsCharacterBudgetDto characterBudgetDto = CopyUtil.copy(character, RdmsCharacterBudgetDto.class);

        responseDto.setContent(characterBudgetDto);
        return responseDto;
    }

    /**
     * 当编辑某一条被分解的character时处理budget问题
     * @param characterId
     * @return
     */
    @PostMapping("/getCharacterBudgetWhenEdit/{characterId}")
    public ResponseDto<RdmsCharacterBudgetDto> getCharacterBudgetWhenEdit(@PathVariable String characterId){
        ResponseDto<RdmsCharacterBudgetDto> responseDto = new ResponseDto<>();
        RdmsCharacter currentCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsCharacter parentCharacter = rdmsCharacterService.selectByPrimaryKey(currentCharacter.getParent());

        RdmsCharacterBudgetDto characterBudgetDto = CopyUtil.copy(parentCharacter, RdmsCharacterBudgetDto.class);
        List<RdmsCharacterBudgetDto> characterBudgetDtos = myCharacterBudgetMapper.listDecomposeCharacterBudget(characterBudgetDto.getId());
        characterBudgetDto.setDecomposeCharacterBudgets(characterBudgetDtos);

        double devManhour = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getDevManhourApproved).reduce(0.0, Double::sum) - currentCharacter.getDevManhourApproved();
        double testManhour = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getTestManhourApproved).reduce(0.0, Double::sum) - currentCharacter.getTestManhourApproved();
        BigDecimal materialFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getMaterialFeeApproved).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getMaterialFeeApproved());
        BigDecimal sumOtherFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getSumOtherFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getSumOtherFee());

        BigDecimal testFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getTestFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getTestFee());
        BigDecimal powerFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getPowerFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getPowerFee());
        BigDecimal conferenceFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getConferenceFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getConferenceFee());
        BigDecimal businessFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getBusinessFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getBusinessFee());
        BigDecimal cooperationFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getCooperationFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getCooperationFee());
        BigDecimal propertyFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getPropertyFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getPropertyFee());
        BigDecimal laborFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getLaborFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getLaborFee());
        BigDecimal consultingFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getConsultingFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getConsultingFee());
        BigDecimal managementFee = characterBudgetDto.getDecomposeCharacterBudgets().stream().map(RdmsCharacterBudgetDto::getManagementFee).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(currentCharacter.getManagementFee());

        RdmsManhourStandard manhourByCustomerId = rdmsManhourService.getStandardManhourByCustomerId(parentCharacter.getCustomerId());
        characterBudgetDto.setRemainDevManhour(parentCharacter.getDevManhourApproved() - devManhour);
        characterBudgetDto.setRemainTestManhour(parentCharacter.getTestManhourApproved() - testManhour);
        characterBudgetDto.setRemainMaterialBudget(parentCharacter.getMaterialFeeApproved().subtract(materialFee));
        characterBudgetDto.setRemainOtherBudget(parentCharacter.getSumOtherFee().subtract(sumOtherFee));

        characterBudgetDto.setRemainTestFee(parentCharacter.getTestFee().subtract(testFee));
        characterBudgetDto.setRemainPowerFee(parentCharacter.getPowerFee().subtract(powerFee));
        characterBudgetDto.setRemainConferenceFee(parentCharacter.getConferenceFee().subtract(conferenceFee));
        characterBudgetDto.setRemainBusinessFee(parentCharacter.getBusinessFee().subtract(businessFee));
        characterBudgetDto.setRemainCooperationFee(parentCharacter.getCooperationFee().subtract(cooperationFee));
        characterBudgetDto.setRemainPropertyFee(parentCharacter.getPropertyFee().subtract(propertyFee));
        characterBudgetDto.setRemainLaborFee(parentCharacter.getLaborFee().subtract(laborFee));
        characterBudgetDto.setRemainConsultingFee(parentCharacter.getConsultingFee().subtract(consultingFee));
        characterBudgetDto.setRemainManagementFee(parentCharacter.getManagementFee().subtract(managementFee));

        BigDecimal sumDecomposeBudget = BigDecimal.valueOf(devManhour).multiply(manhourByCustomerId.getDevManhourFee())
                .add(BigDecimal.valueOf(testManhour).multiply(manhourByCustomerId.getTestManhourFee()))
                .add(materialFee);
                /*.add(testFee)
                .add(powerFee)
                .add(conferenceFee)
                .add(businessFee)
                .add(cooperationFee)
                .add(propertyFee)
                .add(laborFee)
                .add(consultingFee)
                .add(managementFee);*/

        characterBudgetDto.setRemainBudget(parentCharacter.getBudget().subtract(sumDecomposeBudget));
        responseDto.setContent(characterBudgetDto);
        return responseDto;
    }

    @PostMapping("/getProjectBudgetPoolBySubprojectId/{subprojectId}")
    public ResponseDto<BigDecimal> getProjectBudgetPoolBySubprojectId(@PathVariable String subprojectId){
        ResponseDto<BigDecimal> responseDto = new ResponseDto<>();
        BigDecimal projectBudgetPool = rdmsBudgetSheetService.getPoolOfSubProjectBySubprojectId(subprojectId);
        responseDto.setContent(projectBudgetPool);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsProjectBudgetDto> save(@RequestBody RdmsProjectBudgetDto budgetDto) {
        ResponseDto<RdmsProjectBudgetDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(budgetDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(budgetDto.getProjectId(), "项目ID");
        ValidatorUtil.require(budgetDto.getDevManhour(), "开发工时");
        ValidatorUtil.require(budgetDto.getDevManhourFee(), "开发工时费率");
        ValidatorUtil.require(budgetDto.getTestManhour(), "测试工时");
        ValidatorUtil.require(budgetDto.getTestManhourFee(), "测试工时费率");
        ValidatorUtil.require(budgetDto.getMaterialFee(), "物料费");
        ValidatorUtil.require(budgetDto.getOtherFee(), "其他费用");

        ValidatorUtil.length(budgetDto.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(budgetDto.getProjectId().toString(), "项目ID", 8, 8);
        ValidatorUtil.isNumeric(budgetDto.getDevManhour().toString(), "开发工时");
        ValidatorUtil.isNumeric(budgetDto.getDevManhourFee().toString(), "开发工时费率");
        ValidatorUtil.isNumeric(budgetDto.getTestManhour().toString(), "测试工时");
        ValidatorUtil.isNumeric(budgetDto.getTestManhourFee().toString(), "测试工时费率");
        ValidatorUtil.isNumeric(budgetDto.getMaterialFee().toString(), "物料费");
        ValidatorUtil.isNumeric(budgetDto.getOtherFee().toString(), "其他费用");


        RdmsProjectBudget rdmsProjectBudget = CopyUtil.copy(budgetDto, RdmsProjectBudget.class);
        rdmsBudgetSheetService.save(rdmsProjectBudget);

        responseDto.setContent(budgetDto);
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
        rdmsBudgetSheetService.delete(id);
        return responseDto;
    }

}
