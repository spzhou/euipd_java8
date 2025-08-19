/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.domain.rdms.MyCharacter;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsBudgetResearchDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/character")
public class CharacterController {
    private static final Logger LOG = LoggerFactory.getLogger(CharacterController.class);
    public static final String BUSINESS_NAME = "特性定义";

    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsCharacterProcessService rdmsCharacterProcessService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsDemandService rdmsDemandService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsJobItemService rdmsJobItemsService;
    @Resource
    private RdmsJobItemProcessService rdmsJobItemProcessService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Resource
    private RdmsBudgetResearchService rdmsBudgetResearchService;
    @Resource
    private RdmsMilestoneService rdmsMilestoneService;
    @Resource
    private RdmsFeeManageService rdmsFeeManageService;
    @Resource
    private RdmsProjectSecLevelService rdmsProjectSecLevelService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private RdmsBossService rdmsBossService;
    @Resource
    private RdmsSysgmService rdmsSysgmService;
    @Autowired
    private RdmsBugFeedbackService rdmsBugFeedbackService;
    @Autowired
    private RdmsCbbService rdmsCbbService;


    @PostMapping("/listSuggestItems")
    public ResponseDto listSuggestItems(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsCharacterService.listSuggestItems(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 根据customerId进行列表查询
     */
    @PostMapping("/listByCustomerId")
    public ResponseDto<PageDto<RdmsCharacterDto>> listByCustomerId(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.listByCustomerId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 根据customerId进行列表查询
     */
    @PostMapping("/listDevelopingCharactersByPjm")
    public ResponseDto<PageDto<RdmsCharacterDto>> listDevelopingCharactersByPjm(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.listDevelopingCharactersByPjm(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listTestCharactersByTm")
    public ResponseDto<PageDto<RdmsCharacterDto>> listTestCharactersByTm(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.listTestCharactersByTm(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 根据PreProjectId进行列表查询
     */
    @PostMapping("/listByPreProjectId")
    public ResponseDto<PageDto<RdmsCharacterDto>> listByPreProjectId(
            @RequestBody PageDto<RdmsCharacterDto> pageDto
    ) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.listByPreProjectId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表查询所有预立项项目项下经过审批的特性功能清单
     */
    @PostMapping("/listAllApprovedByPreProjectId")
    public ResponseDto<List<RdmsCharacterDto>> listAllApprovedByPreProjectId(@RequestParam String preProjectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listAllApprovedByPreProjectId(preProjectId, loginUserId);
        responseDto.setContent(characterDtos);
        return responseDto;
    }

    @PostMapping("/getDevelopCharacterHasListFlag/{userId}")
    public ResponseDto<Boolean> getDevelopCharacterHasListFlag(@PathVariable String userId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        Integer countOfDevelopingCharacterByPjm = rdmsCharacterService.getCountOfDevelopingCharacterByPjm(userId);
        responseDto.setContent(countOfDevelopingCharacterByPjm >0);
        return responseDto;
    }

    /**
     * 根据ProjectId进行列表查询
     */
    @PostMapping("/listByProjectId/{projectId}")
    public ResponseDto<List<RdmsCharacterDto>> listByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listByProjectId(projectId);
        responseDto.setContent(characterDtos);
        return responseDto;
    }

    /**
     * 根据ProjectId进行列表查询
     */
    @PostMapping("/getSetupedCharacterListByProjectId")
    public ResponseDto<List<RdmsCharacterDto>> getSetupedCharacterListByProjectId(@RequestParam String projectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.getSetupedCharacterListByProjectId(projectId, loginUserId);
        responseDto.setContent(characterDtos);
        return responseDto;
    }

    /**
     * 根据ProjectId进行列表查询
     */
    @PostMapping("/getSetupCharacterListByProjectId")
    public ResponseDto<List<RdmsCharacterDto>> getSetupCharacterListByProjectId(@RequestParam String projectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.getSetupCharacterListByProjectId(projectId, loginUserId);
        responseDto.setContent(characterDtos);
        return responseDto;
    }

    /**
     * 根据subprojectId进行列表查询
     */
    @PostMapping("/getSetupedCharacterListBySubProjectId")
    public ResponseDto<List<RdmsCharacterDto>> getSetupedCharacterListBySubProjectId(@RequestParam String subprojectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.getSetupedCharacterListBySubProjectId(subprojectId, loginUserId);
        responseDto.setContent(characterDtos);
        return responseDto;
    }

    @PostMapping("/startResearchAndDevelopment")
    public ResponseDto<String> startResearchAndDevelopment(@RequestBody RdmsCharacterService.CbbListStr cbbListStr) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        String id = rdmsCharacterService.startResearchAndDevelopment(cbbListStr);
        responseDto.setContent(id);
        return responseDto;
    }

    /**
     * 根据SubprojectId进行列表查询
     */
    @PostMapping("/getCharacterSimpleInfo/{characterId}")
    public ResponseDto<RdmsCharacterDto> getCharacterSimpleInfo(@PathVariable String characterId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto characterById = rdmsCharacterService.getCharacterSimpleInfo(characterId);
        if(!ObjectUtils.isEmpty(characterById) && !ObjectUtils.isEmpty(characterById.getModuleIdListStr())){
            List<String> characterIdList = JSON.parseArray(characterById.getModuleIdListStr(), String.class);
            if(!CollectionUtils.isEmpty(characterIdList)){
                List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByIdList(characterById.getCustomerId(), characterIdList);
                characterById.setReferenceCharacterList(characterList);
            }
        }
        responseDto.setContent(characterById);
        return responseDto;
    }

    /**
     * 根据功能的ID List查询功能名称List
     */
    @PostMapping("/getCharacterNameListByIdList")
    public ResponseDto<RdmsHmiMergeCharactersDto> getCharacterNameListByIdList(@RequestBody RdmsHmiMergeCharactersDto characterNameListDto) {
        ResponseDto<RdmsHmiMergeCharactersDto> responseDto = new ResponseDto<>();
        List<String> characterNameListByIdList = rdmsCharacterService.getCharacterNameListByIdList(characterNameListDto.getCustomerId(), characterNameListDto.getCharacterIdList());
        RdmsHmiMergeCharactersDto nameListDto = new RdmsHmiMergeCharactersDto();
        nameListDto.setCharacterNameList(characterNameListByIdList);
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterNameListDto.getCharacterIdList().get(0));
        if(!ObjectUtils.isEmpty(rdmsCharacter) && rdmsCharacter.getPreProjectId() != null){
            nameListDto.setPreProjectId(rdmsCharacter.getPreProjectId());
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsCharacter.getPreProjectId());
            if(rdmsPreProject.getProductManagerId() != null){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsPreProject.getProductManagerId());
                nameListDto.setProductManagerId(rdmsCustomerUser.getId());
                nameListDto.setProductManagerName(rdmsCustomerUser.getTrueName());
            }
        }
        responseDto.setContent(nameListDto);
        return responseDto;
    }

    /**
     * 根据SubprojectId进行列表查询
     */
    @PostMapping("/getCharacterTreeByProjectId/{subProjectId}")
    public ResponseDto<List<RdmsHmiTree<RdmsCharacterSimpleDto>>> getCharacterTreeByProjectId(@PathVariable String subProjectId) {
        ResponseDto<List<RdmsHmiTree<RdmsCharacterSimpleDto>>> responseDto = new ResponseDto<>();
        RdmsHmiTree<RdmsCharacterSimpleDto> characterTree = rdmsCharacterService.getCharacterTreeByProjectId(subProjectId);
        //添加跟节点信息
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subProjectId);
        characterTree.setId(subproject.getId());
        characterTree.setDeep(-1);
        characterTree.setLabel("产品: "+subproject.getLabel());
        characterTree.setParent(subproject.getId());
        characterTree.setStatus(subproject.getStatus());
        List<RdmsHmiTree<RdmsCharacterSimpleDto>> treeList = new ArrayList<>();
        treeList.add(characterTree);
        responseDto.setContent(treeList);
        return responseDto;
    }

    @PostMapping("/getDocTreeByCharacterId")
    public ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> getDocTreeByCharacterId(@RequestParam String characterId, String loginUserId) {
        ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> responseDto = new ResponseDto<>();
        RdmsHmiTree<RdmsFileDto> docTreeByCharacterId = rdmsCharacterService.getDocTreeByCharacterId(characterId, loginUserId);
        List<RdmsHmiTree<RdmsFileDto>> rdmsHmiTreeList = new ArrayList<>();
        rdmsHmiTreeList.add(docTreeByCharacterId);
        responseDto.setContent(rdmsHmiTreeList);
        return responseDto;
    }

    @PostMapping("/getCharacterReviewDetailInfo")
    public ResponseDto<RdmsHmiCharacterReviewDetailDto> getCharacterReviewDetailInfo(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsHmiCharacterReviewDetailDto> responseDto = new ResponseDto<>();
        RdmsHmiCharacterReviewDetailDto characterReviewDetailInfo = rdmsCharacterService.getCharacterReviewDetailInfo(characterId, loginUserId);
        responseDto.setContent(characterReviewDetailInfo);
        return responseDto;
    }

    @PostMapping("/getCharacterDocPageInfo")
    public ResponseDto<RdmsHmiCharacterDocPageInfoDto> getCharacterDocPageInfo(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsHmiCharacterDocPageInfoDto> responseDto = new ResponseDto<>();
        RdmsHmiCharacterDocPageInfoDto characterDocPageInfo = rdmsCharacterService.getCharacterDocPageInfo(characterId, loginUserId);
        responseDto.setContent(characterDocPageInfo);
        return responseDto;
    }

    @PostMapping("/getCharacterArchiveDocs")
    public ResponseDto<RdmsHmiCharacterArchiveDocsDto> getCharacterArchiveDocs(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsHmiCharacterArchiveDocsDto> responseDto = new ResponseDto<>();
        RdmsHmiCharacterArchiveDocsDto characterArchiveDocs = rdmsCharacterService.getCharacterArchiveDocs(characterId, loginUserId);
        responseDto.setContent(characterArchiveDocs);
        return responseDto;
    }

    @PostMapping("/getCharacterManhourExeInfoList/{characterId}")
    public ResponseDto<List<RdmsBudgeExeInfoDto>> getCharacterBudgetExeInfoList(@PathVariable String characterId) {
        ResponseDto<List<RdmsBudgeExeInfoDto>> responseDto = new ResponseDto<>();
        List<RdmsBudgeExeInfoDto> characterManhourExeInfoList = rdmsManhourService.getCharacterBudgetExeSummaryList(characterId);

        //另一种统计方法: 将assist工单作为开发工单统计; test工单全部作为测试费统计. 结果是一样的
        //RdmsBudgetExeSummaryDto budgetExeSummaryByCharacterId = rdmsManhourService.getBudgetExeSummaryByCharacterId(characterId);
        responseDto.setContent(characterManhourExeInfoList);
        return responseDto;
    }

    @PostMapping("/getCharacterBudgetExeSummary/{characterId}")
    public ResponseDto<RdmsBudgetSummaryDto> getCharacterBudgetExeSummary(@PathVariable String characterId) {
        ResponseDto<RdmsBudgetSummaryDto> responseDto = new ResponseDto<>();
        RdmsBudgetSummaryDto characterBudgetExeSummary = rdmsManhourService.getCharacterBudgetExeSummary(characterId);
        responseDto.setContent(characterBudgetExeSummary);
        return responseDto;
    }

    /**
     * 根据SubprojectId进行列表查询
     */
    @PostMapping("/getCharacterRecordDetailById")
    public ResponseDto<RdmsCharacterDto> getCharacterRecordDetailById(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto characterById = rdmsCharacterService.getCharacterRecordDetailById(characterId, loginUserId);
        responseDto.setContent(characterById);
        return responseDto;
    }

    /**
     * 根据SubprojectId进行列表查询
     */
    @PostMapping("/getCharacterDocDetailById")
    public ResponseDto<RdmsCharacterDto> getCharacterDocDetailById(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        if(!ObjectUtils.isEmpty(rdmsCharacter)){
            if(rdmsCharacter.getAuxStatus().equals(CharacterStatusEnum.UPDATE.getStatus())){
                characterId = rdmsCharacter.getParent();
            }
            RdmsCharacterDto characterById = rdmsCharacterService.getCharacterRecordDetailById(characterId, loginUserId);
            responseDto.setContent(characterById);
        }

        return responseDto;
    }

    /**
     * 根据工单号获取工单记录的CharacterList 列表
     */
    @PostMapping("/getCharacterListByJobitemId")
    public ResponseDto<List<RdmsCharacterDto>> getCharacterListByJobitemId(@RequestParam String jobitemId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterListByJobitemId = rdmsCharacterService.getCharacterListByJobitemId(jobitemId, loginUserId);
        responseDto.setContent(characterListByJobitemId);
        return responseDto;
    }

    /**
     * 根据工单号获取迭代功能记录
     */
    @PostMapping("/getIterationCharacterByJobitemId")
    public ResponseDto<RdmsCharacterDto> getIterationCharacterByJobitemId(@RequestParam String jobitemId, String loginUserId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto character = rdmsCharacterService.getIterationCharacterByJobitemId(jobitemId, loginUserId);
        responseDto.setContent(character);
        return responseDto;
    }

    /**
     * 根据工单号获取迭代功能记录
     */
    @PostMapping("/getMergedCharacterByJobitemId")
    public ResponseDto<RdmsCharacterDto> getMergedCharacterByJobitemId(@RequestParam String jobitemId, String loginUserId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto character = rdmsCharacterService.getMergedCharacterByJobitemId(jobitemId, loginUserId);
        responseDto.setContent(character);
        return responseDto;
    }

    @PostMapping("/getProjectTeamIdsByCharacterId/{characterId}")
    public ResponseDto<RdmProjectTeamInfoDto> getProjectTeamIdsByCharacterId(@PathVariable String characterId) {
        ResponseDto<RdmProjectTeamInfoDto> responseDto = new ResponseDto<>();
        RdmProjectTeamInfoDto teamInfoDto = new RdmProjectTeamInfoDto();
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getByCharacterId(characterId);
        if(!CollectionUtils.isEmpty(rdmsProjectSecLevels)){
            teamInfoDto.setProjectSecLevel(rdmsProjectSecLevels.get(0).getLevel());
        }else{
            teamInfoDto.setProjectSecLevel(0);
        }
        List<String> projectTeamIds = rdmsProjectSecLevelService.getProjectTeamIdsByCharacterId(characterId);
        teamInfoDto.setTeamIdList(projectTeamIds);
        responseDto.setContent(teamInfoDto);
        return responseDto;
    }

    @PostMapping("/getMergedCharacter/{characterId}")
    public ResponseDto<List<MyCharacter>> getMergedCharacter(@PathVariable String characterId) {
        ResponseDto<List<MyCharacter>> responseDto = new ResponseDto<>();
        List<MyCharacter> mergedCharacter = rdmsCharacterService.getMergedCharacter(characterId);
        responseDto.setContent(mergedCharacter);
        return responseDto;
    }

    @PostMapping("/getUpdateCharacterList")
    public ResponseDto<List<RdmsCharacterDto>> getUpdateCharacterList(@RequestParam String preProjectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> updateCharacterList = rdmsCharacterService.getUpdateCharacterList(preProjectId, loginUserId);
        responseDto.setContent(updateCharacterList);
        return responseDto;
    }

    @PostMapping("/list-approved")
    public ResponseDto<PageDto<RdmsCharacterDto>> listApproved(
            @RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.listApproved(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/list-module-super")
    public ResponseDto<PageDto<RdmsCharacterDto>> listModuleBySuper(
            @RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.listModuleBySuper(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     */
    @PostMapping("/getSetupListByProjectId")
    public ResponseDto<List<RdmsCharacterDto>> getSetupListByProjectId(@RequestParam String projectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> rdmsProductCharacterDtos = rdmsCharacterService.getSetupListByProjectId(projectId, loginUserId);
        responseDto.setContent(rdmsProductCharacterDtos);
        return responseDto;
    }

    /**
     * 获取一个Character的全部信息
     */
    @PostMapping("/getHmiCharacterDocDto")
    public ResponseDto<RdmsHmiCharacterDocDto> getHmiCharacterDocDto(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsHmiCharacterDocDto> responseDto = new ResponseDto<>();
        RdmsHmiCharacterDocDto hmiCharacterDocDto = rdmsCharacterService.getHmiCharacterDocDto(characterId, loginUserId);
        responseDto.setContent(hmiCharacterDocDto);
        return responseDto;
    }

    /**
     *
     */
    @PostMapping("/getHmiAllAttachmentsOfCharacter/{characterId}")
    public ResponseDto<List<RdmsFileDto>> getHmiAllAttachmentsOfCharacter(@PathVariable String characterId) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        List<RdmsFileDto> hmiAllAttachmentsOfCharacterDto = rdmsCharacterService.getHmiAllAttachmentsOfCharacter(characterId);
        responseDto.setContent(hmiAllAttachmentsOfCharacterDto);
        return responseDto;
    }

    /**
     *功能集成工单全部附件读取
     */
    @PostMapping("/getHmiAllAttachmentsOfIntCharacter/{characterId}")
    public ResponseDto<List<RdmsFileDto>> getHmiAllAttachmentsOfIntCharacter(@PathVariable String characterId) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        List<RdmsFileDto> hmiAllAttachmentsOfIntCharacter = rdmsCharacterService.getHmiAllAttachmentsOfIntCharacter(characterId);
        responseDto.setContent(hmiAllAttachmentsOfIntCharacter);
        return responseDto;
    }


    @PostMapping("/listAllCharactersByProjectId")
    public ResponseDto<List<RdmsCharacterDto>> listAllCharactersByProjectId(@RequestParam String projectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> rdmsProductCharacterDtos = rdmsCharacterService.listAllCharactersByProjectId(projectId, loginUserId);
        responseDto.setContent(rdmsProductCharacterDtos);
        return responseDto;
    }

    @PostMapping("/listAllCharactersBySubProjectId")
    public ResponseDto<List<RdmsCharacterDto>> listAllCharactersBySubProjectId(@RequestParam String subprojectId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> rdmsProductCharacterDtos = rdmsCharacterService.listAllCharactersBySubProjectId(subprojectId, loginUserId);
        responseDto.setContent(rdmsProductCharacterDtos);
        return responseDto;
    }

/*    @PostMapping("/listCharactersBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsCharacterDto>> listCharactersBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> rdmsProductCharacterDtos = rdmsCharacterService.listCharactersBySubprojectId(subprojectId);
        responseDto.setContent(rdmsProductCharacterDtos);
        return responseDto;
    }*/

    @PostMapping("/getDecomposedChildrenCharacters")
    public ResponseDto<List<RdmsCharacterDto>> getDecomposedChildrenCharacters(@RequestParam String parentCharacterId, String loginUserId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> rdmsProductCharacterDtos = rdmsCharacterService.getDecomposedChildrenCharacters(parentCharacterId, CharacterStatusEnum.SAVED.getStatus(), loginUserId);
        responseDto.setContent(rdmsProductCharacterDtos);
        return responseDto;
    }

    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getCharacterListAndAllDevelopJobitem")
    public ResponseDto<PageDto<RdmsCharacterDto>> getCharacterListAndAllDevelopJobitem(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.getCharacterListAndAllDevelopJobitem(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getCharacterAndJobitemList/{characterId}")
    public ResponseDto<RdmsCharacterDto> getCharacterAndJobitemList(@PathVariable String characterId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto characterAndJobitemList = rdmsCharacterService.getCharacterAndJobitemList(characterId);
        responseDto.setContent(characterAndJobitemList);
        return responseDto;
    }

    @PostMapping("/getCharacterAndTestTaskJobitemList/{characterId}")
    public ResponseDto<RdmsCharacterDto> getCharacterAndTestTaskJobitemList(@PathVariable String characterId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto characterAndJobitemList = rdmsCharacterService.getCharacterAndTestTaskJobitemList(characterId);
        responseDto.setContent(characterAndJobitemList);
        return responseDto;
    }

    @PostMapping("/getCharacterListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsHmiCharacterPlainDto>> getCharacterListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsHmiCharacterPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(subprojectId, null);
        if(!CollectionUtils.isEmpty(characterList)){
            List<RdmsHmiCharacterPlainDto> characterPlainList = characterList.stream().map(item -> new RdmsHmiCharacterPlainDto(item.getId(), item.getCharacterName())).collect(Collectors.toList());
            responseDto.setContent(characterPlainList);
        }
        return responseDto;
    }

    @PostMapping("/getArchivedCharacterListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsHmiCharacterPlainDto>> getArchivedCharacterListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsHmiCharacterPlainDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getArchivedCharacterListByCustomerId(customerId);
        if(!CollectionUtils.isEmpty(characterList)){
            List<RdmsHmiCharacterPlainDto> characterPlainList = characterList.stream().map(item -> new RdmsHmiCharacterPlainDto(item.getId(), item.getCharacterName())).collect(Collectors.toList());
            responseDto.setContent(characterPlainList);
        }
        return responseDto;
    }

    @PostMapping("/getLastReviewInfo")
    public ResponseDto<RdmsHmiJobItemDocPageDto> getLastReviewInfo(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsHmiJobItemDocPageDto> responseDto = new ResponseDto<>();
        RdmsHmiJobItemDocPageDto lastReviewInfo = rdmsCharacterService.getLastReviewInfo(characterId, loginUserId);
        responseDto.setContent(lastReviewInfo);
        return responseDto;
    }

    @PostMapping("/getCharacterMaterialBudgetExeInfoList/{pjmId}")
    public ResponseDto<List<RdmsCharacterBudgeExeInfoDto>> getCharacterListByPjm(@PathVariable String pjmId) {
        ResponseDto<List<RdmsCharacterBudgeExeInfoDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByPjmByMmStatus(pjmId, CharacterStatusEnum.NOTSET);
        List<RdmsCharacterBudgeExeInfoDto> characterBudgeExeInfoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(characterList)){
            for(RdmsCharacterDto characterDto: characterList){
                RdmsCharacterBudgeExeInfoDto characterMaterialBudgetExeInfo = rdmsManhourService.getCharacterMaterialBudgetExeInfo(characterDto.getId());
                characterBudgeExeInfoList.add(characterMaterialBudgetExeInfo);
            }
        }
        responseDto.setContent(characterBudgeExeInfoList);
        return responseDto;
    }

    @PostMapping("/getMaterialHasListFlag/{pjmId}")
    public ResponseDto<Boolean> getMaterialHasListFlag(@PathVariable String pjmId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
//        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByPjmByMmStatus(pjmId, CharacterStatusEnum.NOTSET);
        List<RdmsMaterialManageDto> listByLoginUser = rdmsMaterialManageService.getListByNextNodeIdForApprover(pjmId);
        responseDto.setContent(!listByLoginUser.isEmpty());
        return responseDto;
    }

    @PostMapping("/getCharacterAndSubmitJobitems/{characterId}")
    public ResponseDto<RdmsCharacterDto> getCharacterAndSubmitJobitems(@PathVariable String characterId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto characterAndJobitemList = rdmsCharacterService.getCharacterAndSubmitJobitems(characterId);
        responseDto.setContent(characterAndJobitemList);
        return responseDto;
    }

    @PostMapping("/getCharacterAndTestJobSubmitJobitems/{characterId}")
    public ResponseDto<RdmsCharacterDto> getCharacterAndTestJobSubmitJobitems(@PathVariable String characterId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto characterAndJobitemList = rdmsCharacterService.getCharacterAndTestJobSubmitJobitems(characterId);
        responseDto.setContent(characterAndJobitemList);
        return responseDto;
    }

    @PostMapping("/getCharacterDevelopCompleteStatus/{characterId}")
    public ResponseDto<RdmsHmiCharacterCompleteStatusDto> getCharacterDevelopCompleteStatus(@PathVariable String characterId) {
        ResponseDto<RdmsHmiCharacterCompleteStatusDto> responseDto = new ResponseDto<>();
        RdmsHmiCharacterCompleteStatusDto characterDevelopCompleteInfoAndStatus = rdmsCharacterService.getCharacterDevelopCompleteInfoAndStatus(characterId);
        responseDto.setContent(characterDevelopCompleteInfoAndStatus);
        return responseDto;
    }

    /**
     * 查询依据CharacterId创建的工单, 并且这些工单处于submit状态
     */
    @PostMapping("/getNumOfReviewCharactersByNextNode/{nextNode}")
    public ResponseDto<Integer> getNumOfReviewCharactersByNextNode(@PathVariable String nextNode) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.REVIEW);
        Integer reviewJobNum = rdmsCharacterService.getNumOfCharactersByNextNodeAndStatus(nextNode, statusEnumList);
        responseDto.setContent(reviewJobNum);
        return responseDto;
    }


    /**
     * 查询依据CharacterId创建的工单, 并且这些工单处于submit状态
     */
    @PostMapping("/getUpdateCharacterNumByUserId/{userId}")
    public ResponseDto<Integer> getUpdateCharacterNumByUserId(@PathVariable String userId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.REVIEW);
        Integer updateCharacterNum = rdmsCharacterService.getNumOfUpdateCharactersByUserId(userId);
        responseDto.setContent(updateCharacterNum);
        return responseDto;
    }


    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getCharacterListAndAllTestJobitem")
    public ResponseDto<PageDto<RdmsCharacterDto>> getCharacterListAndAllTestJobitem(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<JobItemTypeEnum> jobItemTypeEnumList = new ArrayList<>();
        jobItemTypeEnumList.add(JobItemTypeEnum.TEST);

        rdmsCharacterService.getDevelopCharacterAndJobitemListByMyCharacter(pageDto, JobItemStatusEnum.HANDLING.getStatus(), jobItemTypeEnumList);
        responseDto.setContent(pageDto);
        return responseDto;
    }
     /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getCharacterListAndAllSubmitTestJobitem")
    public ResponseDto<PageDto<RdmsCharacterDto>> getCharacterListAndAllSubmitTestJobitem(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<JobItemTypeEnum> jobItemTypeEnumList = new ArrayList<>();
        jobItemTypeEnumList.add(JobItemTypeEnum.TEST);
        rdmsCharacterService.getDevelopCharacterAndJobitemListByMyCharacter(pageDto, JobItemStatusEnum.SUBMIT.getStatus(), jobItemTypeEnumList);
        responseDto.setContent(pageDto);
        return responseDto;
    }
     /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getDevelopingCharacterList")
    public ResponseDto<PageDto<RdmsCharacterDto>> getDevelopingCharacterList(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.getDevelopingCharacterList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getEvaluateJobitemList")
    public ResponseDto<PageDto<RdmsCharacterDto>> getEvaluateJobitemList(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.getEvaluateJobitemList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getTestingJobitemList")
    public ResponseDto<PageDto<RdmsCharacterDto>> getTestingJobitemList(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.getTestingJobitemList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    /**
     */
    @PostMapping("/getCharacterIntegrationJobList")
    public ResponseDto<PageDto<RdmsCharacterDto>> getCharacterIntegrationJobList(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.getCharacterIntegrationJobList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    /**
     */
    @PostMapping("/getCharacterIntSubmitOriginInfo/{characterId}")
    public ResponseDto<RdmsHmiCharacterIntOriginDto> getCharacterIntSubmitOriginInfo(@PathVariable String characterId) {
        ResponseDto<RdmsHmiCharacterIntOriginDto> responseDto = new ResponseDto<>();
        RdmsHmiCharacterIntOriginDto submitOriginDto = rdmsCharacterService.getCharacterIntSubmitOriginInfo(characterId);
        responseDto.setContent(submitOriginDto);
        return responseDto;
    }
    @PostMapping("/getIntegrationJobitemInfo/{characterId}")
    public ResponseDto<RdmsJobItemDto> getIntegrationJobitemInfo(@PathVariable String characterId) {
        ResponseDto<RdmsJobItemDto> responseDto = new ResponseDto<>();
        RdmsJobItemDto integrationJobitemInfo = rdmsCharacterService.getIntegrationJobitemInfo(characterId);
        responseDto.setContent(integrationJobitemInfo);
        return responseDto;
    }

    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getApprovedJobitemList")
    public ResponseDto<PageDto<RdmsCharacterDto>> getApprovedJobitemList(@RequestBody PageDto<RdmsCharacterDto> pageDto) {
        ResponseDto<PageDto<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        rdmsCharacterService.getApprovedJobitemList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    /**
     * 保存功能/特性信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsCharacterDto> save(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(characterDto.getCharacterName(), "功能/特性名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "功能/特性描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");
        ValidatorUtil.require(characterDto.getWriterId(), "编制者");
        ValidatorUtil.require(characterDto.getProjectType(), "项目类型");

        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 2500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 2500);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 10, 2500);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 10, 2500);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 10, 2500);

        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        character.setFileListStr(jsonString);

        List<RdmsDemandDto> demandList = characterDto.getDemandList();
        if(demandList != null){
            List<String> stringList = demandList.stream().map(RdmsDemandDto::getId).collect(Collectors.toList());
            String demandIdStr = JSON.toJSONString(stringList);
            character.setDemandListStr(demandIdStr);
        }

        character.setStatus(CharacterStatusEnum.SAVED.getStatus());
        character.setNextNode(characterDto.getWriterId());  //保存的时候,下一节点是自己
        rdmsCharacterService.save(character);

        if(! CollectionUtils.isEmpty(demandList)){
            for(RdmsDemandDto demandDto : demandList){
                RdmsDemand rdmsDemandDiscern = rdmsDemandService.selectByPrimaryKey(demandDto.getId());
                rdmsDemandDiscern.setStatus(DemandStatusEnum.COMPLETED.getStatus());
                rdmsDemandService.update(rdmsDemandDiscern);
            }
        }

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveSuggestCharacterToSetuped")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveSuggestCharacterToSetuped(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(characterDto.getWriterId(), "编制者");
        ValidatorUtil.require(characterDto.getProjectType(), "项目类型");
        ValidatorUtil.require(characterDto.getMilestoneId(), "里程碑");

        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        character.setFileListStr(jsonString);

        character.setStatus(CharacterStatusEnum.SETUPED.getStatus());
        character.setProjectType(ProjectTypeEnum.DEV_PROJECT.getType());
        RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(characterDto.getMilestoneId());
        character.setPlanCompleteTime(rdmsMilestoneDto.getReviewTime());
        String characterId = rdmsCharacterService.save(character);

        //将功能建议发起任务单标记为完成
        List<RdmsJobItem> suggestOriginJobitem = rdmsJobItemsService.getSuggestOriginJobitem(characterId);
        if(!CollectionUtils.isEmpty(suggestOriginJobitem)){
            for(RdmsJobItem jobItem: suggestOriginJobitem){
                jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                rdmsJobItemsService.update(jobItem);
            }
        }

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存功能/特性信息
     */
    @PostMapping("/saveRecordAndData")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveRecordAndData(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getCharacterName(), "功能/特性名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "功能/特性描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");
        ValidatorUtil.require(characterDto.getWriterId(), "编制者");
        ValidatorUtil.require(characterDto.getProjectType(), "项目类型");
        if(!characterDto.getJobitemType().equals(JobItemTypeEnum.SUGGEST.getType()) && !characterDto.getJobitemType().equals(JobItemTypeEnum.SUGGEST_UPDATE.getType()) ){
            ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        }

        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 2500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 2500);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 10, 2500);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 10, 2500);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 10, 2500);

        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        characterDto.setFileListStr(jsonString);

        List<RdmsDemandDto> demandList = characterDto.getDemandList();
        if(demandList != null){
            List<String> stringList = demandList.stream().map(RdmsDemandDto::getId).collect(Collectors.toList());
            String demandIdStr = JSON.toJSONString(stringList);
            characterDto.setDemandListStr(demandIdStr);
        }

        characterDto.setStatus(CharacterStatusEnum.SAVED.getStatus());
        characterDto.setNextNode(characterDto.getWriterId());  //保存的时候,下一节点是自己
        characterDto.setAuxStatus(CharacterStatusEnum.NOTSET.getStatus());  //保存的时候,下一节点是自己
        characterDto.setWriterId(characterDto.getLoginUserId());  //将编制人修改为自己

        if((!ObjectUtils.isEmpty(characterDto.getId())) && ObjectUtils.isEmpty(characterDto.getCharacterSerial())){
            characterDto.setCharacterSerial(characterDto.getId());
        }
        rdmsCharacterService.saveRecordAndData(characterDto);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(characterDto.getFileIds())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(null);
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(characterDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                if(!ObjectUtils.isEmpty(characterDto.getProductManagerId())){
                    roleUsersDto.setPdmId(characterDto.getProductManagerId());
                }
                if(!ObjectUtils.isEmpty(characterDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                    if(!ObjectUtils.isEmpty(rdmsPreProject)){
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(characterDto.getFileIds(), roleUsersDto);
            }
        }


        if(! CollectionUtils.isEmpty(demandList)){
            for(RdmsDemandDto demandDto : demandList){
                RdmsDemand rdmsDemandDiscern = rdmsDemandService.selectByPrimaryKey(demandDto.getId());
                rdmsDemandDiscern.setStatus(DemandStatusEnum.COMPLETED.getStatus());
                rdmsDemandService.update(rdmsDemandDiscern);
            }
        }

        if(characterDto.getJobitemType().equals(JobItemTypeEnum.SUGGEST_UPDATE.getType()) ){
            //修改原始功能记录状态
            RdmsCharacter originCharacter = rdmsCharacterService.selectByPrimaryKey(characterDto.getCharacterSerial());
            //添加一个功能处理过程信息
            //如果原始记录已经处于 升级建议 状态, 不需要重复保存编辑过程记录
            if (!originCharacter.getStatus().equals(CharacterStatusEnum.UPDATED.getStatus())) {
                //添加一条CharacterProcess记录
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(originCharacter.getId());
                characterProcess.setExecutorId(characterDto.getLoginUserId());
                characterProcess.setNextNode(characterDto.getLoginUserId());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(originCharacter.getId());
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("开始对功能升级进行修订");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
            }

            originCharacter.setStatus(CharacterStatusEnum.UPDATED.getStatus());
            rdmsCharacterService.update(originCharacter);
        }

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getRecordAndData")
    @Transactional
    public ResponseDto<RdmsCharacterDto> getRecordAndData(@RequestParam String characterId, String loginUserId) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacterDto recordAndData = new RdmsCharacterDto();
        if(characterId != null){
            recordAndData = rdmsCharacterService.getRecordAndData(characterId, loginUserId);
        }
        responseDto.setContent(recordAndData);
        return responseDto;
    }

    @PostMapping("/get-approved-items-num")
    public ResponseDto<Long> getApprovedItemsNum(@RequestBody RdmsRequestDto requestDto) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long examineItemsNum = rdmsCharacterService.getApprovedItemsNum(requestDto.getCustomerId(), requestDto.getPreProjectId(), requestDto.getCustomerUserId());
        responseDto.setContent(examineItemsNum);
        return responseDto;
    }

    @Data
    private static class RespSuggestNum{
        private long suggestNum;
        private long suggestUpdateNum;
        private long materialAppNum;
        private long feeAppNum;
        private long bugfeedbackNum;
    }
    @PostMapping("/get-suggest-items-num/{customerUserId}")
    public ResponseDto<RespSuggestNum> getSuggestItemsNum(@PathVariable String customerUserId) {
        ResponseDto<RespSuggestNum> responseDto = new ResponseDto<>();
        RespSuggestNum suggestNum = new RespSuggestNum();
        suggestNum.setSuggestNum(rdmsCharacterService.getSuggestNum(customerUserId));
        suggestNum.setSuggestUpdateNum(rdmsCbbService.getSuggestUpdateNum(customerUserId));
        List<RdmsMaterialManageDto> materialApplicationList = rdmsMaterialManageService.getMaterialApplicationListByWriterId(customerUserId);
        suggestNum.setMaterialAppNum(materialApplicationList.size());
        List<RdmsFeeManageDto> feeApplicationList = rdmsFeeManageService.getFeeApplicationListByWriterId(customerUserId);
        suggestNum.setFeeAppNum(feeApplicationList.size());
        long bugRecheckNumByUserId = rdmsBugFeedbackService.getBugRecheckNumByUserId(customerUserId);
        suggestNum.setBugfeedbackNum(bugRecheckNumByUserId);

        responseDto.setContent(suggestNum);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/approve")
    @Transactional
    public ResponseDto<String> approve(@RequestBody RdmsCharacterProcessDto characterProcessDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterProcessDto.getCharacterId(), "特性ID");
        ValidatorUtil.require(characterProcessDto.getExecutorId(), "执行者ID");
        ValidatorUtil.require(characterProcessDto.getJobDescription(), "工作描述");
        ValidatorUtil.require(characterProcessDto.getCharacterBudget(), "审批工时");

        String respStr = rdmsCharacterService.approve(characterProcessDto);

        responseDto.setContent(respStr);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/close/{characterId}")
    @Transactional
    public ResponseDto<String> approve(@PathVariable String characterId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        String respStr = rdmsCharacterService.close(characterId);
        responseDto.setContent(respStr);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/modifyCharactersToApprovedStatus")
    @Transactional
    public ResponseDto modifyCharactersToApprovedStatus(@RequestBody List<RdmsCharacterDto> characterDtos) {
        ResponseDto responseDto = new ResponseDto<>();

        if(! CollectionUtils.isEmpty(characterDtos)){
            for(RdmsCharacterDto characterDto: characterDtos){
                characterDto.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                characterDto.setProjectId(null);
                rdmsCharacterService.update(CopyUtil.copy(characterDto, RdmsCharacter.class));

                //添加一条CharacterProcess记录
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterDto.getId());
                characterProcess.setExecutorId(characterDto.getProductManagerId());  //理论上是监管委员, 但是,在没有进入正式立项前, 监管委员是没有的
                characterProcess.setNextNode(characterDto.getProductManagerId());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterDto.getId());
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("立项过程中退回产品经理");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.EXAMINE.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
            }
        }
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/saveDecomposeCharacter")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveDecomposeCharacter(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(characterDto.getCharacterName(), "功能/特性名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "功能/特性描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");
        ValidatorUtil.require(characterDto.getWriterId(), "编制者");
        ValidatorUtil.require(characterDto.getProjectType(), "项目类型");
        ValidatorUtil.require(characterDto.getParent(), "父级功能");

        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 2500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 2500);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 10, 2500);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 10, 2500);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 10, 2500);

        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        character.setFileListStr(jsonString);

        List<RdmsDemandDto> demandList = characterDto.getDemandList();
        if(demandList != null){
            List<String> stringList = demandList.stream().map(RdmsDemandDto::getId).collect(Collectors.toList());
            String demandIdStr = JSON.toJSONString(stringList);
            character.setDemandListStr(demandIdStr);
        }

        character.setStatus(CharacterStatusEnum.SAVED.getStatus());
        character.setNextNode(characterDto.getLoginUserId());  //保存的时候,下一节点是自己
        character.setJobitemType(JobItemTypeEnum.DECOMPOSE.getType());
        //在审批之前,暂时填充审批预算字段
        character.setDevManhourApproved(character.getDevManhour());
        character.setTestManhourApproved(character.getTestManhour());
        character.setMaterialFeeApproved(character.getMaterialFee());
        character.setTestFee(character.getTestFee());
        character.setPowerFee(character.getPowerFee());
        character.setConferenceFee(character.getConferenceFee());
        character.setBusinessFee(character.getBusinessFee());
        character.setCooperationFee(character.getCooperationFee());
        character.setPropertyFee(character.getPropertyFee());
        character.setLaborFee(character.getLaborFee());
        character.setConsultingFee(character.getConsultingFee());
        character.setManagementFee(character.getManagementFee());

        RdmsManhourStandard standardManhour = rdmsManhourService.getStandardManhourByCustomerId(characterDto.getCustomerId());

        if(characterDto.getProjectType().equals(ProjectTypeEnum.DEV_PROJECT.getType())){
            double sumOtherFee = character.getTestFee().floatValue()
                    + character.getPowerFee().floatValue()
                    + character.getConferenceFee().floatValue()
                    + character.getBusinessFee().floatValue()
                    + character.getCooperationFee().floatValue()
                    + character.getPropertyFee().floatValue()
                    + character.getLaborFee().floatValue()
                    + character.getConsultingFee().floatValue()
                    + character.getManagementFee().floatValue();
            character.setSumOtherFee(BigDecimal.valueOf(sumOtherFee));

            double budget = character.getDevManhourApproved() * standardManhour.getDevManhourFee().floatValue()
                    + character.getTestManhourApproved() * standardManhour.getTestManhourFee().floatValue()
                    + character.getMaterialFeeApproved().floatValue()
                    + sumOtherFee;

            character.setBudget(BigDecimal.valueOf(budget));
        }

        RdmsCharacter parentCharacter = rdmsCharacterService.selectByPrimaryKey(characterDto.getParent());
        character.setPlanCompleteTime(parentCharacter.getPlanCompleteTime());
        character.setModuleIdListStr(parentCharacter.getModuleIdListStr());

        RdmsCharacterData characterData = CopyUtil.copy(characterDto, RdmsCharacterData.class);
        rdmsCharacterService.saveDecomposeCharacter(character, characterData);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(characterDto.getFileIds())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(characterDto.getLoginUserId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(characterDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(characterDto.getProjectManagerId());
                roleUsersDto.setPdmId(characterDto.getProductManagerId());
                if(!ObjectUtils.isEmpty(characterDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                    if(!ObjectUtils.isEmpty(rdmsPreProject)){
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(characterDto.getFileIds(), roleUsersDto);
            }
        }

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


    /**
     * 保存一条功能迭代定义, 在完成审批之前,他完全就是一个新的组件定义, 在完成审批后, 代替掉原来的旧版本记录
     * 作为一条新的组件定义, 他拥有自己的一个process过程
     * 在完成审批后, 旧的版本是什么状态, 就将这条迭代的记录标记为什么状态
     */
    @PostMapping("/saveIterationCharacter")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveIterationCharacter(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(characterDto.getCharacterName(), "功能/特性名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "功能/特性描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");
        ValidatorUtil.require(characterDto.getWriterId(), "编制者");
        ValidatorUtil.require(characterDto.getStatus(), "状态");
        ValidatorUtil.require(characterDto.getProjectType(), "项目类型");

        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 2500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 2500);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 10, 2500);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 10, 2500);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 10, 2500);

//        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        characterDto.setFileListStr(jsonString);

        List<RdmsDemandDto> demandList = characterDto.getDemandList();
        if(demandList != null){
            List<String> stringList = demandList.stream().map(RdmsDemandDto::getId).collect(Collectors.toList());
            String demandIdStr = JSON.toJSONString(stringList);
            characterDto.setDemandListStr(demandIdStr);
        }

        if(characterDto.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus())){
//            characterDto.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
            //将被迭代的原始记录的aux状态设置为history
            /*RdmsCharacter iterationOriginRecord = rdmsCharacterService.getIterationOriginRecord(characterDto.getId());
            iterationOriginRecord.setAuxStatus(CharacterStatusEnum.HISTORY.getStatus());
            rdmsCharacterService.update(iterationOriginRecord);*/

            if(characterDto.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
                if(!ObjectUtils.isEmpty(characterDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                    if(!ObjectUtils.isEmpty(rdmsPreProject)){
                        characterDto.setNextNode(rdmsPreProject.getSystemManagerId());
                    }
                }else{
                    RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(characterDto.getCustomerId());
                    if (!ObjectUtils.isEmpty(sysgmByCustomerId)) {
                        characterDto.setNextNode(sysgmByCustomerId.getSysgmId());
                    }
                }
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                if(!ObjectUtils.isEmpty(rdmsPreProject)){
                    characterDto.setNextNode(rdmsPreProject.getSystemManagerId());
                }
            }else if(characterDto.getProjectType().equals(ProjectTypeEnum.DEV_PROJECT.getType())){
                RdmsJobItemDto jobitemByCharacterId = rdmsJobItemsService.getOriginJobitemByCharacterId(characterDto.getId());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobitemByCharacterId.getSubprojectId());
                characterDto.setNextNode(subproject.getProjectManagerId());
            }

            characterDto.setSubmitTime(new Date());

        }else{
            characterDto.setStatus(CharacterStatusEnum.SAVED.getStatus());
            characterDto.setNextNode(characterDto.getLoginUserId());  //保存的时候,下一节点是自己
        }

        String characterId = rdmsCharacterService.saveRecordAndData(characterDto);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(characterDto.getFileIds())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                if(!ObjectUtils.isEmpty(characterDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                    roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                }else {
                    RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(characterDto.getCustomerId());
                    if(!ObjectUtils.isEmpty(sysgmByCustomerId)){
                        roleUsersDto.setSysgmId(sysgmByCustomerId.getSysgmId());
                    }
                }

                roleUsersDto.setLoginUserId(null);
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(characterDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                if(characterDto.getProjectType().equals(ProjectTypeEnum.DEV_PROJECT.getType())){
                    if(!ObjectUtils.isEmpty(characterDto.getProjectManagerId())){
                        roleUsersDto.setPjmId(characterDto.getProjectManagerId());
                    }
                }
                roleUsersDto.setPdmId(characterDto.getProductManagerId());
                if(!ObjectUtils.isEmpty(characterDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                    if(!ObjectUtils.isEmpty(rdmsPreProject)){
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(characterDto.getFileIds(), roleUsersDto);
            }
        }

        if(characterDto.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus())){
            //创建CharacterProcess记录
            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(characterId);
            characterProcess.setExecutorId(characterDto.getLoginUserId());
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
            characterProcess.setDeep((int)characterProcessCount);
            characterProcess.setJobDescription("提交组件定义表");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
            characterProcess.setNextNode(characterDto.getNextNode());

            rdmsCharacterProcessService.save(characterProcess);

            //将工单的状态标记为提交submit
            if(!ObjectUtils.isEmpty(characterDto)){
                RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(characterDto.getJobitemId());
                jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
//                jobItem.setNextNode(characterDto.getProductManagerId());
                jobItem.setActualSubmissionTime(new Date());
                RdmsJobItemDto jobitemByCharacterId = rdmsJobItemsService.getOriginJobitemByCharacterId(characterDto.getId());
                if(jobitemByCharacterId.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobitemByCharacterId.getPreProjectId());
                    if(!ObjectUtils.isEmpty(rdmsPreProject)){
                        jobItem.setProductManagerId(rdmsPreProject.getProductManagerId());
                        jobItem.setNextNode(rdmsPreProject.getSystemManagerId());
                    }
                }else if(jobitemByCharacterId.getProjectType().equals(ProjectTypeEnum.DEV_PROJECT.getType())){
                    RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobitemByCharacterId.getSubprojectId());
                    jobItem.setProductManagerId(subproject.getProductManagerId());
                    jobItem.setProjectManagerId(subproject.getProjectManagerId());
                    jobItem.setNextNode(subproject.getProjectManagerId());
                }else{
                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobitemByCharacterId.getProjectId());
                    jobItem.setProjectManagerId(rdmsProject.getProjectManagerId());
                }
                String jobitemId = rdmsJobItemsService.update(jobItem);

                //添加工单过程记录
                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                jobItemProcess.setJobItemId(jobitemId);
                jobItemProcess.setExecutorId(characterDto.getLoginUserId());
                jobItemProcess.setNextNode(jobItem.getExecutorId());
                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                jobItemProcess.setDeep((int)jobItemProcessCount);
                jobItemProcess.setJobDescription("提交功能修订工单");
                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());

                List<String> characterIdList = new ArrayList<>();
                characterIdList.add(characterId);
                String characterIdStr = JSON.toJSONString(characterIdList);
                jobItemProcess.setCharacterIdListStr(characterIdStr);

                jobItemProcess.setActualSubmissionTime(jobItem.getActualSubmissionTime());
                rdmsJobItemProcessService.save(jobItemProcess);
            }
        }

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


    /**
     */
    @PostMapping("/saveMergeCharacter")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveMergeCharacter(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
//        ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(characterDto.getCharacterName(), "功能/特性名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "功能/特性描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");
//        ValidatorUtil.require(characterDto.getWriterId(), "编制者");
        ValidatorUtil.require(characterDto.getStatus(), "状态");
        ValidatorUtil.require(characterDto.getProjectType(), "项目类型");


        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 2500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 2500);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 10, 2500);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 10, 2500);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 10, 2500);

        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        characterDto.setFileListStr(jsonString);

        List<RdmsDemandDto> demandList = characterDto.getDemandList();
        if(demandList != null){
            List<String> stringList = demandList.stream().map(RdmsDemandDto::getId).collect(Collectors.toList());
            String demandIdStr = JSON.toJSONString(stringList);
            characterDto.setDemandListStr(demandIdStr);
        }

        if(characterDto.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus())){
            characterDto.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
            if(characterDto.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
                characterDto.setNextNode(characterDto.getProductManagerId());
            }else if(characterDto.getProjectType().equals(ProjectTypeEnum.DEV_PROJECT.getType())){
                RdmsJobItemDto jobitemByCharacterId = rdmsJobItemsService.getOriginJobitemByCharacterId(characterDto.getId());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobitemByCharacterId.getSubprojectId());
                characterDto.setNextNode(subproject.getProjectManagerId());
            }

            characterDto.setSubmitTime(new Date());

        }else{
            characterDto.setStatus(CharacterStatusEnum.SAVED.getStatus());
            characterDto.setNextNode(characterDto.getLoginUserId());  //保存的时候,下一节点是自己
        }
        String characterId = rdmsCharacterService.saveRecordAndData(characterDto);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(characterDto.getFileIds())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(null);
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(characterDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                roleUsersDto.setPdmId(characterDto.getProductManagerId());
                if(!ObjectUtils.isEmpty(characterDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(characterDto.getPreProjectId());
                    if(!ObjectUtils.isEmpty(rdmsPreProject)){
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(characterDto.getFileIds(), roleUsersDto);
            }
        }

        if(characterDto.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus())){
            //创建CharacterProcess记录
            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(characterId);
            characterProcess.setExecutorId(characterDto.getLoginUserId());
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
            characterProcess.setDeep((int)characterProcessCount);
            characterProcess.setJobDescription("提交组件定义表");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
            characterProcess.setNextNode(characterDto.getProductManagerId());
            rdmsCharacterProcessService.save(characterProcess);

            //将工单的状态标记为提交submit
            if(!ObjectUtils.isEmpty(characterDto)){
                RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(characterDto.getJobitemId());
                jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
                jobItem.setNextNode(characterDto.getProductManagerId());
                jobItem.setActualSubmissionTime(new Date());
                RdmsJobItemDto jobitemByCharacterId = rdmsJobItemsService.getOriginJobitemByCharacterId(characterDto.getId());
                if(jobitemByCharacterId.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobitemByCharacterId.getPreProjectId());
                    jobItem.setProductManagerId(rdmsPreProject.getProductManagerId());
                }else{
                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobitemByCharacterId.getProjectId());
                    jobItem.setProjectManagerId(rdmsProject.getProjectManagerId());
                }
                String jobitemId = rdmsJobItemsService.update(jobItem);

                //添加工单过程记录
                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                jobItemProcess.setJobItemId(jobitemId);
                jobItemProcess.setExecutorId(characterDto.getLoginUserId());
                jobItemProcess.setNextNode(characterDto.getNextNode());
                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                jobItemProcess.setDeep((int)jobItemProcessCount);
                jobItemProcess.setJobDescription("提交功能修订工单");
                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());

                List<String> characterIdList = new ArrayList<>();
                characterIdList.add(characterId);
                String characterIdStr = JSON.toJSONString(characterIdList);
                jobItemProcess.setCharacterIdListStr(characterIdStr);

                jobItemProcess.setActualSubmissionTime(jobItem.getActualSubmissionTime());
                rdmsJobItemProcessService.save(jobItemProcess);
            }
        }

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


    /**
     * 在立项审批时,驳回某个组件定义给审批人,重新处理
     */
    @PostMapping("/reject/{characterId}")
    @Transactional
    public ResponseDto reject(@PathVariable String characterId) {
        ResponseDto responseDto = new ResponseDto<>();

        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsProject rdmsProjectInfo = rdmsProjectService.selectByPrimaryKey(rdmsCharacter.getProjectId());

        //处理驳回的Character
        if(rdmsCharacter.getStatus().equals(CharacterStatusEnum.SETUP.getStatus())){
            rdmsCharacter.setStatus(CharacterStatusEnum.REFUSE.getStatus());
        }else{
            rdmsCharacter.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
        }
        rdmsCharacter.setNextNode(rdmsCharacter.getProductManagerId());
        String projectId = rdmsCharacter.getProjectId();
        rdmsCharacter.setProjectId(null);
        rdmsCharacterService.update(rdmsCharacter);

        //处理Character process
        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
        characterProcess.setCharacterId(rdmsCharacter.getId());
        characterProcess.setExecutorId(rdmsProjectInfo.getSupervise());
        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(rdmsCharacter.getId());
        characterProcess.setDeep((int)characterProcessCount);
        characterProcess.setJobDescription("立项审批时,被驳回!");
        if(rdmsCharacter.getStatus().equals(CharacterStatusEnum.SETUP.getStatus())){
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.REFUSE.getStatus());
        }else{
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
        }

        characterProcess.setNextNode(rdmsCharacter.getProductManagerId());
        rdmsCharacterProcessService.save(characterProcess);

        //处理Character 对应的 工单
        RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(rdmsCharacter.getJobitemId());
        if(!ObjectUtils.isEmpty(jobItem)){
            jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
//            jobItem.setTaskDescription("全部或部分组件定义被驳回修订");
            jobItem.setNextNode(jobItem.getProductManagerId());
            String jobItemId = rdmsJobItemsService.update(jobItem);

            //9. 对应工单的process为完成状态
            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
            jobItemProcess.setJobItemId(jobItemId);
            jobItemProcess.setExecutorId(jobItem.getExecutorId());
            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
            jobItemProcess.setDeep((int)jobItemProcessCount);
            jobItemProcess.setJobDescription("全部或部分组件定义被驳回修订");
            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.REJECT.getStatus());
            jobItemProcess.setNextNode(jobItem.getProductManagerId());
            rdmsJobItemProcessService.save(jobItemProcess);
        }

        //处理项目
       /* List<RdmsCharacterDto> rdmsCharacterDtos = rdmsCharacterService.listAllCharactersByProjectId(projectId);
        if(CollectionUtils.isEmpty(rdmsCharacterDtos)){
            rdmsProjectService.delete(projectId);
        }*/

        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/submit")
    @Transactional
    public ResponseDto<RdmsCharacterDto> submit(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(characterDto.getCharacterName(), "功能/特性名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "功能/特性描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");
        ValidatorUtil.require(characterDto.getWriterId(), "编制者");

        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 2500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 2500);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 10, 2500);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 10, 2500);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 10, 2500);

        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        character.setFileListStr(jsonString);

        List<RdmsDemandDto> demandList = characterDto.getDemandList();
        if(demandList != null){
            List<String> stringList = demandList.stream().map(RdmsDemandDto::getId).collect(Collectors.toList());
            String demandIdStr = JSON.toJSONString(stringList);
            character.setDemandListStr(demandIdStr);
        }
        character.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
        character.setNextNode(characterDto.getProductManagerId());  //submit时, 下一节点是产品经理
        character.setSubmitTime(new Date());

        RdmsCharacter originCharacter = rdmsCharacterService.selectByPrimaryKey(character.getCharacterSerial());
        character.setPlanCompleteTime(originCharacter.getPlanCompleteTime());

        String characterId = rdmsCharacterService.save(character);

        //处理需求记录的状态
        if(! CollectionUtils.isEmpty(demandList)){
            for(RdmsDemandDto demandDiscernDto : demandList){
                RdmsDemand rdmsDemandDiscern = rdmsDemandService.selectByPrimaryKey(demandDiscernDto.getId());
                rdmsDemandDiscern.setStatus(DemandStatusEnum.COMPLETED.getStatus());
                rdmsDemandService.update(rdmsDemandDiscern);
            }
        }

        //创建"提交"过程记录
        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
        characterProcess.setCharacterId(characterId);
        characterProcess.setExecutorId(character.getWriterId());
        List<RdmsCharacterProcessDto> rdmsCharacterProcessDtos = rdmsCharacterProcessService.listByCharacterId(characterId);
        if(rdmsCharacterProcessDtos.size() > 0){
            characterProcess.setDeep(rdmsCharacterProcessDtos.size());
        }else {
            characterProcess.setDeep(0);
        }
        characterProcess.setJobDescription("编制人提交");
        characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());

        characterProcess.setNextNode(characterDto.getProductManagerId());
        characterProcess.setCreateTime(new Date());
        characterProcess.setDeleted(0);
        rdmsCharacterProcessService.submit(characterProcess);

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/saveDistribution")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveDistribution(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterDto.getId());
        character.setSubprojectId(characterDto.getSubprojectId());
        character.setModuleIdListStr(characterDto.getModuleIdListStr());
        rdmsCharacterService.update(character);

        //CharacterProcess
        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
        characterProcess.setCharacterId(character.getId());
        characterProcess.setExecutorId(characterDto.getLoginUserId());
        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
        characterProcess.setDeep((int)characterProcessCount);
        characterProcess.setJobDescription("功能已经分配到子项目");
        characterProcess.setProcessStatus(CharacterProcessStatusEnum.DISTRIBUTE.getStatus());
        RdmsProjectSubprojectDto subproject = rdmsProjectService.getSubprojectBySubprojectId(characterDto.getSubprojectId());
        characterProcess.setNextNode(subproject.getProjectManagerId());
        rdmsCharacterProcessService.save(characterProcess);

        //调整对应的子项目预算信息
        RdmsBudgetResearchDto budgetData = new RdmsBudgetResearchDto();
        budgetData.setTestFeeZc(budgetData.getTestFeeZc().add(character.getTestFee()));
        budgetData.setMaterialFeeZc(budgetData.getMaterialFeeZc().add(character.getMaterialFee()));
        budgetData.setPowerFeeZc(budgetData.getPowerFeeZc().add(character.getPowerFee()));
        budgetData.setConferenceFeeZc(budgetData.getConferenceFeeZc().add(character.getConferenceFee()));
        budgetData.setBusinessFeeZc(budgetData.getBusinessFeeZc().add(character.getBusinessFee()));
        budgetData.setCooperationFeeZc(budgetData.getCooperationFeeZc().add(character.getCooperationFee()));
        budgetData.setPropertyFeeZc(budgetData.getPropertyFeeZc().add(character.getPropertyFee()));
        budgetData.setLaborFeeZc(budgetData.getLaborFeeZc().add(character.getLaborFee()));

        RdmsManhourStandard standManhour = rdmsManhourService.getStandardManhourByCustomerId(subproject.getCustomerId());
        BigDecimal devFee = BigDecimal.valueOf(character.getDevManhourApproved()).multiply(standManhour.getDevManhourFee());
        BigDecimal testFee = BigDecimal.valueOf(character.getTestManhourApproved()).multiply(standManhour.getTestManhourFee());
        budgetData.setStaffFeeZc(budgetData.getStaffFeeZc().add(devFee.add(testFee)));
        budgetData.setConsultingFeeZc(budgetData.getConsultingFeeZc().add(character.getConsultingFee()));
        budgetData.setManagementFeeZc(budgetData.getManagementFeeZc().add(character.getManagementFee()));

        rdmsBudgetResearchService.budgetAdjustForDistributeCharacter(subproject.getCustomerId(), subproject.getParent(), subproject.getId(), budgetData);

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getCharacterTree/{characterId}")
    @Transactional
    public ResponseDto<RdmsCharacterInheritTreeDto> getCharacterTree(@PathVariable String characterId) {
        ResponseDto<RdmsCharacterInheritTreeDto> responseDto = new ResponseDto<>();
        RdmsCharacterInheritTreeDto characterInheritTree = rdmsCharacterService.getCharacterInheritTree(characterId);
        responseDto.setContent(characterInheritTree);
        responseDto.setSuccess(true);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/saveIterationCharacterRecord")
    @Transactional
    public ResponseDto<String> saveIterationCharacterRecord(@RequestBody RdmsRequestDto requestDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        String updateId = rdmsCharacterService.saveIterationCharacterRecord(requestDto.getCharacterId(), requestDto.getPreProjectId());
        responseDto.setContent(updateId);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/saveProjectUpdate")
    @Transactional
    public ResponseDto<String> saveProjectUpdate(@RequestBody RdmsPreProjectDto preProjectDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        //查看是否有重名项目
        long l = rdmsProjectService.projectNameIsUsed(preProjectDto.getCustomerId(), preProjectDto.getPreProjectName());
        if(l > 0){
            long time = new Date().getTime();
            String substring = Long.toString(time).substring(0, 6);
            preProjectDto.setPreProjectName(preProjectDto.getPreProjectName() + substring);
        }
        //创建preProject
        preProjectDto.setId(null);
        RdmsPreProject preProject = CopyUtil.copy(preProjectDto, RdmsPreProject.class);
        String preProjectId = rdmsPreProjectService.save(preProject);
        //更新project状态
        RdmsProject project = new RdmsProject();
        project.setId(preProjectDto.getProjectId());
        project.setVersionUpdate("UPDATED");
        rdmsProjectService.updateByPrimaryKeySelective(project);

        //将迭代的功能分配和preProject
        List<RdmsCharacterDto> characterDtoList = preProjectDto.getCharacterDtoList();
        if(! CollectionUtils.isEmpty(characterDtoList)){
            for(RdmsCharacterDto characterDto: characterDtoList){
                rdmsCharacterService.saveUpdateCharacterRecord(characterDto.getId(), preProjectId);
            }
        }
        responseDto.setContent(preProjectId);
        return responseDto;
    }

    /**
     * 保存核定工时
     */
    @PostMapping("/save-confirm-manhour")
    @Transactional
    public ResponseDto<RdmsCharacterDto> saveConfirmManhour(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterDto.getId());
        character.setDevManhourApproved(characterDto.getDevManhourApproved());
        character.setTestManhourApproved(characterDto.getTestManhourApproved());
        character.setMaterialFeeApproved(characterDto.getMaterialFeeApproved());
        character.setTestFee(characterDto.getTestFee());
        character.setPowerFee(characterDto.getPowerFee());
        character.setConferenceFee(characterDto.getConferenceFee());
        character.setBusinessFee(characterDto.getBusinessFee());
        character.setCooperationFee(characterDto.getCooperationFee());
        character.setPropertyFee(characterDto.getPropertyFee());
        character.setLaborFee(characterDto.getLaborFee());
        character.setConsultingFee(characterDto.getConsultingFee());
        character.setManagementFee(characterDto.getManagementFee());

        RdmsManhourStandard manhourByCustomerId = rdmsManhourService.getStandardManhourByCustomerId(character.getCustomerId());

        double sumOtherFee = character.getTestFee().floatValue()
                + character.getPowerFee().floatValue()
                + character.getConferenceFee().floatValue()
                + character.getBusinessFee().floatValue()
                + character.getCooperationFee().floatValue()
                + character.getPropertyFee().floatValue()
                + character.getLaborFee().floatValue()
                + character.getConsultingFee().floatValue()
                + character.getManagementFee().floatValue();
        character.setSumOtherFee(BigDecimal.valueOf(sumOtherFee));

        double budget = character.getDevManhourApproved() * manhourByCustomerId.getDevManhourFee().floatValue()
                + character.getTestManhourApproved() * manhourByCustomerId.getTestManhourFee().floatValue()
                + character.getMaterialFeeApproved().floatValue()
                + sumOtherFee;

        character.setBudget(BigDecimal.valueOf(budget));

        rdmsCharacterService.update(character);

        responseDto.setContent(characterDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


    /**
     * 对特性的内容进行修改时,调用此函数
     * 每次modify都会导致版本号升级
     */
    @PostMapping("/modify")
    @Transactional
    public ResponseDto<RdmsCharacterDto> modify(@RequestBody RdmsCharacterDto characterDto) {
        ResponseDto<RdmsCharacterDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(characterDto.getId(), "功能/组件ID");
        ValidatorUtil.require(characterDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(characterDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(characterDto.getCharacterName(), "功能/特性名称");
        ValidatorUtil.require(characterDto.getFunctionDescription(), "功能/特性描述");
        ValidatorUtil.require(characterDto.getWorkCondition(), "生效条件/应用范围");
        ValidatorUtil.require(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件");
        ValidatorUtil.require(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求");
        ValidatorUtil.require(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标");
        ValidatorUtil.require(characterDto.getWriterId(), "编制者");

        ValidatorUtil.length(characterDto.getCharacterName(), "功能/特性名称", 4, 80);
        ValidatorUtil.length(characterDto.getFunctionDescription(), "功能/特性描述", 10, 500);
        ValidatorUtil.length(characterDto.getWorkCondition(), "生效条件/应用范围", 10, 900);
        ValidatorUtil.length(characterDto.getInputLogicalDesc(), "输入逻辑/使用条件", 20, 900);
        ValidatorUtil.length(characterDto.getFunctionLogicalDesc(), "功能逻辑/功能要求", 20, 900);
        ValidatorUtil.length(characterDto.getOutputLogicalDesc(), "输出逻辑/交付指标", 20, 900);

        RdmsCharacter character = CopyUtil.copy(characterDto, RdmsCharacter.class);
        String jsonString = JSON.toJSONString(characterDto.getFileIds());
        character.setFileListStr(jsonString);
        if(characterDto.getStatus().equals(CharacterStatusEnum.SAVED.getStatus())){
            character.setNextNode(characterDto.getWriterId());  //保存的时候,下一节点是自己
        }else if(characterDto.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus())){
            character.setNextNode(characterDto.getProductManagerId());  //submit时, 下一节点是产品经理
            character.setSubmitTime(new Date());
        }
        //处理版本号
        //TODO 待调试
        Integer iteration = characterDto.getIterationVersion() + 1;
        character.setIterationVersion(iteration);
        rdmsCharacterService.update(character);

        responseDto.setContent(characterDto);
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
        rdmsCharacterService.delete(id);
        return responseDto;
    }

}
