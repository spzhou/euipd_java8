/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.FileUseEnum;
import com.course.server.enums.rdms.DocTypeEnum;
import com.course.server.enums.rdms.FileApplicationStatusEnum;
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.JobItemTypeEnum;
import com.course.server.mapper.RdmsFileApplyMapper;
import com.course.server.mapper.RdmsFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RdmsFileService {

    @Resource
    private RdmsFileMapper rdmsFileMapper;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsFileDownloadService rdmsFileDownloadService;
    @Resource
    private RdmsFileApplyService rdmsFileApplyService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private RdmsProjectService rdmsProjectService;

    @Value("${oss.domain}")
    private String ossDomain;
    @Autowired
    private RdmsBossService rdmsBossService;

    /**
     * 列表查询
     */
    @Transactional
    public void list(PageDto<RdmsFileDto> pageDto) {
        //确认公司和管理员是否对应
        if(!ObjectUtils.isEmpty(pageDto.getCustomerId()) && !ObjectUtils.isEmpty(pageDto.getActor())){
            RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(pageDto.getCustomerId());
            if(!ObjectUtils.isEmpty(bossByCustomerId)){
                if(bossByCustomerId.getBossId().equals(pageDto.getActor())){
                    //Boss
                    PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
                    RdmsFileExample fileExample = new RdmsFileExample();
                    fileExample.setOrderByClause("create_time desc");
                    fileExample.createCriteria().andCustomerIdEqualTo(pageDto.getCustomerId()).andDeletedEqualTo(0);
                    List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
                    PageInfo<RdmsFile> pageInfo = new PageInfo<>(rdmsFiles);
                    pageDto.setTotal(pageInfo.getTotal());
                    List<RdmsFileDto> fileDtoList = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
                    if(! CollectionUtils.isEmpty(fileDtoList)){
                        for(RdmsFileDto fileDto: fileDtoList){
                            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                            fileDto.setOperatorName(rdmsCustomerUser.getTrueName());

                            if(!ObjectUtils.isEmpty(fileDto.getSubprojectId())){
                                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(fileDto.getSubprojectId());
                                if(!ObjectUtils.isEmpty(subproject)){
                                    fileDto.setProjectName(subproject.getLabel());
                                }
                            }else if(!ObjectUtils.isEmpty(fileDto.getPreProjectId())){
                                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(fileDto.getPreProjectId());
                                if(! ObjectUtils.isEmpty(rdmsPreProject)){
                                    fileDto.setProjectName(rdmsPreProject.getPreProjectName());
                                }
                            }
                        }
                    }
                    pageDto.setList(fileDtoList);
                }else{
                    //一般用户
                    PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
                    RdmsFileExample fileExample = new RdmsFileExample();
                    fileExample.setOrderByClause("create_time desc");
                    fileExample.createCriteria()
                            .andCustomerIdEqualTo(pageDto.getCustomerId())
                            .andOperatorIdEqualTo(pageDto.getActor())
                            .andDeletedEqualTo(0);
                    List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
                    PageInfo<RdmsFile> pageInfo = new PageInfo<>(rdmsFiles);
                    pageDto.setTotal(pageInfo.getTotal());
                    List<RdmsFileDto> fileDtoList = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
                    if(! CollectionUtils.isEmpty(fileDtoList)){
                        for(RdmsFileDto fileDto: fileDtoList){
                            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                            fileDto.setOperatorName(rdmsCustomerUser.getTrueName());

                            if(!ObjectUtils.isEmpty(fileDto.getSubprojectId())){
                                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(fileDto.getSubprojectId());
                                fileDto.setProjectName(subproject.getLabel());
                            }else if(!ObjectUtils.isEmpty(fileDto.getPreProjectId())){
                                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(fileDto.getPreProjectId());
                                fileDto.setProjectName(rdmsPreProject.getPreProjectName());
                            }
                        }
                    }
                    pageDto.setList(fileDtoList);
                }
            }
        }
    }

    @Transactional
    public void listApplyFiles(PageDto<RdmsFileDto> pageDto) {
        List<String> fileIdList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageDto.getCustomerId()) && !ObjectUtils.isEmpty(pageDto.getActor())) {
            List<RdmsFileApplyDto> fileApplyList = rdmsFileApplyService.getFileApplyList(pageDto.getCustomerId(), pageDto.getActor());
            if (!CollectionUtils.isEmpty(fileApplyList)) {
                fileIdList = fileApplyList.stream().map(RdmsFileApplyDto::getFileId).distinct().collect(Collectors.toList());
            }
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(fileIdList)){
                PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
                RdmsFileExample fileExample = new RdmsFileExample();
                fileExample.setOrderByClause("create_time desc");
                fileExample.createCriteria()
                        .andIdIn(fileIdList)
                        .andDeletedEqualTo(0);
                List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
                PageInfo<RdmsFile> pageInfo = new PageInfo<>(rdmsFiles);
                pageDto.setTotal(pageInfo.getTotal());
                fileDtoList = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
                if (!CollectionUtils.isEmpty(fileDtoList)) {
                    for (RdmsFileDto fileDto : fileDtoList) {
                        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                        fileDto.setOperatorName(rdmsCustomerUser.getTrueName());

                        if (!ObjectUtils.isEmpty(fileDto.getSubprojectId())) {
                            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(fileDto.getSubprojectId());
                            fileDto.setProjectName(subproject.getLabel());
                        } else if (!ObjectUtils.isEmpty(fileDto.getPreProjectId())) {
                            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(fileDto.getPreProjectId());
                            fileDto.setProjectName(rdmsPreProject.getPreProjectName());
                        }
                        //查看fileDto是否在自己的下载文件中, 如果是,则状态标记为已下载
                        RdmsFileDownloadDto downloadFileByFileId = rdmsFileDownloadService.getDownloadFileByFileId(fileDto.getId());
                        if (!ObjectUtils.isEmpty(downloadFileByFileId)) {
                            fileDto.setApplyStatus(FileApplicationStatusEnum.DOWNLOADED.getStatus());
                        } else {
                            //查看申请数据表,并填写状态
                            List<RdmsFileApplyDto> applyStatusByFileIdAndCustomerUserId = rdmsFileApplyService.getApplyFileListByFileIdAndCustomerUserId(fileDto.getId(), pageDto.getActor());
                            if (!CollectionUtils.isEmpty(applyStatusByFileIdAndCustomerUserId)) {
                                fileDto.setApplyStatus(applyStatusByFileIdAndCustomerUserId.get(0).getApplicationStatus());
                            } else {
                                fileDto.setApplyStatus(FileApplicationStatusEnum.NOTSET.getStatus());
                            }
                        }
                    }
                }
            }
            pageDto.setList(fileDtoList);
        }
    }

    @Transactional
    public void searchByKeyWord(PageDto<RdmsFileDto> pageDto) {
        if(!ObjectUtils.isEmpty(pageDto.getCustomerId())){
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            RdmsFileExample fileExample = new RdmsFileExample();
            fileExample.setOrderByClause("create_time desc");
            fileExample.createCriteria()
                    .andCustomerIdEqualTo(pageDto.getCustomerId())
                    .andOperatorIdEqualTo(pageDto.getActor())
                    .andNameLike("%"+ pageDto.getKeyWord() +"%")
                    .andDeletedEqualTo(0);
            List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
            PageInfo<RdmsFile> pageInfo = new PageInfo<>(rdmsFiles);
            pageDto.setTotal(pageInfo.getTotal());
            List<RdmsFileDto> fileDtoList = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
            if(! CollectionUtils.isEmpty(fileDtoList)){
                for(RdmsFileDto fileDto: fileDtoList){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                    fileDto.setOperatorName(rdmsCustomerUser.getTrueName());
                }
            }
            pageDto.setList(fileDtoList);

        }
    }

    /**
     * 检索不是自己的文件
     * @param pageDto
     */
    @Transactional
    public void searchMoreFileByKeyWord(PageDto<RdmsFileDto> pageDto) {
        if(!ObjectUtils.isEmpty(pageDto.getCustomerId())){
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            RdmsFileExample fileExample = new RdmsFileExample();
            fileExample.setOrderByClause("create_time desc");
            fileExample.createCriteria()
                    .andCustomerIdEqualTo(pageDto.getCustomerId())
                    .andOperatorIdNotEqualTo(pageDto.getActor())
                    .andNameLike("%"+ pageDto.getKeyWord() +"%")
                    .andDeletedEqualTo(0);
            List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
            PageInfo<RdmsFile> pageInfo = new PageInfo<>(rdmsFiles);
            pageDto.setTotal(pageInfo.getTotal());

            List<RdmsFileDto> fileDtoList = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
            if(! CollectionUtils.isEmpty(fileDtoList)){
                for(RdmsFileDto fileDto: fileDtoList){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                    fileDto.setOperatorName(rdmsCustomerUser.getTrueName());

                    //查看自己是否有文件权限
                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                    if(!ObjectUtils.isEmpty(byFileId)){
                        String authIdsStr = byFileId.getAuthIdsStr();
                        if(!ObjectUtils.isEmpty(authIdsStr)){
                            List<String> authIdList = JSON.parseArray(authIdsStr, String.class);
                            //判断自己是否有权限
                            if(authIdList.contains(pageDto.getActor())){
                                fileDto.setApplyStatus(FileApplicationStatusEnum.DOWNLOADED.getStatus());
                            }else{
                                //查看申请数据表,并填写状态
                                List<RdmsFileApplyDto> applyStatusByFileIdAndCustomerUserId = rdmsFileApplyService.getApplyFileListByFileIdAndCustomerUserId(fileDto.getId(), pageDto.getActor());
                                if(!CollectionUtils.isEmpty(applyStatusByFileIdAndCustomerUserId)){
                                    fileDto.setApplyStatus(applyStatusByFileIdAndCustomerUserId.get(0).getApplicationStatus());
                                }else{
                                    fileDto.setApplyStatus(FileApplicationStatusEnum.NOTSET.getStatus());
                                }
                            }
                        }
                    }else{
                        //查看申请数据表,并填写状态
                        List<RdmsFileApplyDto> applyStatusByFileIdAndCustomerUserId = rdmsFileApplyService.getApplyFileListByFileIdAndCustomerUserId(fileDto.getId(), pageDto.getActor());
                        if(!CollectionUtils.isEmpty(applyStatusByFileIdAndCustomerUserId)){
                            fileDto.setApplyStatus(applyStatusByFileIdAndCustomerUserId.get(0).getApplicationStatus());
                        }else{
                            fileDto.setApplyStatus(FileApplicationStatusEnum.NOTSET.getStatus());
                        }
                    }
                }
            }
            pageDto.setList(fileDtoList);
        }
    }

    /**
     * 通过use和itemid为核心参数进行文件查询
     */
    public List<RdmsFileDto> listFileInfo(RdmsAttachmentDto file) {
        RdmsFileExample fileExample = new RdmsFileExample();
        fileExample.createCriteria().andUseEqualTo(file.getUse()).andItemIdEqualTo(file.getItemId()).andDeletedEqualTo(0);
        if(file.getCustomerId() != null && ! file.getCustomerId().equals("")){
            fileExample.createCriteria().andCustomerIdEqualTo(file.getCustomerId());
        }
        if(file.getPreProjectId() != null && ! file.getPreProjectId().equals("")){
            fileExample.createCriteria().andPreProjectIdEqualTo(file.getPreProjectId());
        }
        if(file.getProjectId() != null && ! file.getProjectId().equals("")){
            fileExample.createCriteria().andProjectIdEqualTo(file.getProjectId());
        }
        if(file.getSubprojectId() != null && ! file.getSubprojectId().equals("")){
            fileExample.createCriteria().andSubprojectIdEqualTo(file.getSubprojectId());
        }
        if(file.getOperatorId() != null && ! file.getOperatorId().equals("")){
            fileExample.createCriteria().andOperatorIdEqualTo(file.getOperatorId());
        }
        List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
        return CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
    }

    public List<RdmsFileDto> getFileListByOperatorId(String customerId , String operatorId) {
        RdmsFileExample fileExample = new RdmsFileExample();
        fileExample.setOrderByClause("create_time desc");
        fileExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andOperatorIdEqualTo(operatorId)
                .andDeletedEqualTo(0);
        List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
        List<RdmsFileDto> fileDtoList = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
        if(! CollectionUtils.isEmpty(fileDtoList)){
            for(RdmsFileDto fileDto: fileDtoList){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                fileDto.setOperatorName(rdmsCustomerUser.getTrueName());
            }
        }
        return fileDtoList;
    }

    @Transactional
    public List<RdmsHmiTree<RdmsFileDto>> getMyArchiveTreeByWorkerId(String workerId){
        //worker的所有工单id
        List<RdmsJobItem> allJobList = rdmsJobItemService.getJobitemListByWorkerId(workerId);
        //获得characterId不是Null的所有工单
        List<RdmsJobItem> characterJobList = rdmsJobItemService.getJobitemListByWorkerId(workerId)
                .stream().filter(item -> item.getCharacterId() != null).collect(Collectors.toList());
        List<RdmsJobItem> noCharacterJobList = allJobList.stream().filter(item -> !characterJobList.contains(item)).collect(Collectors.toList());
        List<RdmsJobItem> subProjectJobItemList = new ArrayList<>();
        List<RdmsJobItem> preProjectJobItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(noCharacterJobList)){
            List<RdmsJobItem> subProjectJobList = noCharacterJobList.stream().filter(item -> item.getSubprojectId() != null).collect(Collectors.toList());
            subProjectJobItemList = subProjectJobList;

            List<RdmsJobItem> preProjectJobList = noCharacterJobList.stream().filter(item -> !subProjectJobList.contains(item)).collect(Collectors.toList());
            preProjectJobItemList = preProjectJobList;
        }


        List<String> characterIdList = characterJobList.stream().map(RdmsJobItem::getCharacterId).distinct().collect(Collectors.toList());
        List<RdmsCharacter> rdmsCharacters = new ArrayList<>();
        if(!CollectionUtils.isEmpty(characterIdList)){
            rdmsCharacters = rdmsCharacterService.getcharacterListByCharacterIdList(characterIdList);
        }

        List<RdmsCharacter> subProjectCharacterList = rdmsCharacters.stream().filter(item -> item.getSubprojectId() != null).collect(Collectors.toList());
        List<RdmsCharacter> preProjectCharacterList = rdmsCharacters.stream().filter(item -> !subProjectCharacterList.contains(item)).collect(Collectors.toList());

        List<RdmsHmiTree<RdmsFileDto>> xProjectTreeNodeList = new ArrayList<>();
        //子项目的character文件树
        List<String> subProjectIdList = subProjectCharacterList.stream().map(RdmsCharacter::getSubprojectId).distinct().collect(Collectors.toList());
        for(String subprojectId: subProjectIdList){
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
            if(!ObjectUtils.isEmpty(subproject)){
                RdmsHmiTree<RdmsFileDto> subProjectTreeNode = new RdmsHmiTree<>();
                subProjectTreeNode.setId(subproject.getId());
                subProjectTreeNode.setLabel(subproject.getLabel());
                subProjectTreeNode.setValue("");
                subProjectTreeNode.setParent("");
                subProjectTreeNode.setStatus(DocTypeEnum.SUB_PROJECT.getType());
                subProjectTreeNode.setObj(null);

                List<RdmsCharacter> rdmsCharacterList = subProjectCharacterList.stream().filter(item -> item.getSubprojectId().equals(subprojectId)).collect(Collectors.toList());
                List<String> characterIds = rdmsCharacterList.stream().map(RdmsCharacter::getId).collect(Collectors.toList());
                List<RdmsHmiTree<RdmsFileDto>> characterTreeList = getCharacterDocTreeList(workerId, characterIds, workerId);

                subProjectTreeNode.setChildren(characterTreeList);
                xProjectTreeNodeList.add(subProjectTreeNode);
            }
        }

        //预立项项目的character文件树
        List<String> preProjectIdList = preProjectCharacterList.stream().map(RdmsCharacter::getPreProjectId).distinct().collect(Collectors.toList());
        for(String preprojectId: preProjectIdList){
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(preprojectId);
            RdmsHmiTree<RdmsFileDto> preProjectTreeNode = new RdmsHmiTree<>();
            preProjectTreeNode.setId(rdmsPreProject.getId());
            preProjectTreeNode.setLabel(rdmsPreProject.getPreProjectName());
            preProjectTreeNode.setValue("");
            preProjectTreeNode.setParent("");
            preProjectTreeNode.setStatus(DocTypeEnum.DEMAND.getType());
            preProjectTreeNode.setObj(null);

            List<RdmsCharacter> rdmsCharacterList = preProjectCharacterList.stream().filter(item -> item.getPreProjectId().equals(preprojectId)).collect(Collectors.toList());
            List<String> characterIds = rdmsCharacterList.stream().map(RdmsCharacter::getId).collect(Collectors.toList());
            List<RdmsHmiTree<RdmsFileDto>> characterTreeList = getCharacterDocTreeList(workerId, characterIds, workerId);

            preProjectTreeNode.setChildren(characterTreeList);
            xProjectTreeNodeList.add(preProjectTreeNode);
        }

        //characterId为null的subproject下的工单
        if(!CollectionUtils.isEmpty(subProjectJobItemList)){
            List<RdmsHmiTree<RdmsFileDto>> subprojectDirectDocTreeListCharacterIdNull = this.getSubprojectDirectDocTreeList_characterIdNull(subProjectJobItemList, workerId);
            for(RdmsHmiTree<RdmsFileDto> subprojectTreeNode: subprojectDirectDocTreeListCharacterIdNull){
                if(!ObjectUtils.isEmpty(subprojectTreeNode)){
                    List<String> subprojectIdList = xProjectTreeNodeList.stream().map(RdmsHmiTree<RdmsFileDto>::getId).collect(Collectors.toList());
                    if(subprojectIdList.contains(subprojectTreeNode.getId())){
                        List<RdmsHmiTree<RdmsFileDto>> collect = xProjectTreeNodeList.stream().filter(item -> item.getId().equals(subprojectTreeNode.getId())).collect(Collectors.toList());
                        if(!collect.isEmpty()){
                            if(!collect.get(0).getChildren().isEmpty()){
                                collect.get(0).getChildren().addAll(subprojectTreeNode.getChildren());
                            }
                        }
                    }else{
                        xProjectTreeNodeList.add(subprojectTreeNode);
                    }
                }
            }
        }

        //characterId为null的preProject下的工单
        if(!CollectionUtils.isEmpty(preProjectJobItemList)){
            List<RdmsHmiTree<RdmsFileDto>> preProjectDirectDocTreeListCharacterIdNull = this.getPreProjectDirectDocTreeList_characterIdNull(preProjectJobItemList, workerId);
            for(RdmsHmiTree<RdmsFileDto> preProjectTreeNode: preProjectDirectDocTreeListCharacterIdNull){
                if(!ObjectUtils.isEmpty(preProjectTreeNode)){
                    List<String> subprojectIdList = xProjectTreeNodeList.stream().map(RdmsHmiTree<RdmsFileDto>::getId).collect(Collectors.toList());
                    if(subprojectIdList.contains(preProjectTreeNode.getId())){
                        List<RdmsHmiTree<RdmsFileDto>> collect = xProjectTreeNodeList.stream().filter(item -> item.getId().equals(preProjectTreeNode.getId())).collect(Collectors.toList());
                        if(!collect.isEmpty()){
                            if(!collect.get(0).getChildren().isEmpty()){
                                collect.get(0).getChildren().addAll(preProjectTreeNode.getChildren());
                            }
                        }
                    }else{
                        xProjectTreeNodeList.add(preProjectTreeNode);
                    }
                }
            }
        }

        return xProjectTreeNodeList;
    }

    @Transactional
    public List<RdmsHmiTree<RdmsFileDto>> getReferenceArchiveTreeByCharacterId(String characterId, String loginUserId){
        List<RdmsHmiTree<RdmsFileDto>> rootTreeNodeList = new ArrayList<>();
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);

        if (!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getModuleIdListStr())) {
            List<String> characterIdList = JSON.parseArray(rdmsCharacter.getModuleIdListStr(), String.class);
            if (!CollectionUtils.isEmpty(characterIdList)) {
                RdmsHmiTree<RdmsFileDto> rootTreeNode = new RdmsHmiTree<>();
                rootTreeNode.setId(characterId);
                rootTreeNode.setLabel(rdmsCharacter.getCharacterName());
                rootTreeNode.setValue("");
                rootTreeNode.setParent("");
                rootTreeNode.setStatus(DocTypeEnum.CHARACTER.getType());
                rootTreeNode.setObj(null);

                List<RdmsHmiTree<RdmsFileDto>> characterTreeList = getCharacterAllDocTreeList(characterIdList, loginUserId);
                rootTreeNode.setChildren(characterTreeList);
                rootTreeNodeList.add(rootTreeNode);

                return rootTreeNodeList;
            }
        }
        return rootTreeNodeList;
    }

    @Transactional
    public List<RdmsHmiTree<RdmsFileDto>> getArchiveTreeBySubprojectId(String subprojectId, String loginUserId){

        List<RdmsHmiTree<RdmsFileDto>> subProjectTreeNodeList = new ArrayList<>();

        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        if(ObjectUtils.isEmpty(subproject)){
            return subProjectTreeNodeList;
        }
        RdmsHmiTree<RdmsFileDto> subProjectTreeNode = new RdmsHmiTree<>();
        subProjectTreeNode.setId(subproject.getId());
        subProjectTreeNode.setLabel(subproject.getLabel());
        subProjectTreeNode.setValue("");
        subProjectTreeNode.setParent("");
        subProjectTreeNode.setStatus(DocTypeEnum.SUB_PROJECT.getType());
        subProjectTreeNode.setObj(null);

        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listAllCharactersBySubProjectId(subprojectId, loginUserId);
        List<String> characterIds = characterDtos.stream().map(RdmsCharacter::getId).collect(Collectors.toList());
        List<RdmsHmiTree<RdmsFileDto>> characterTreeList = getCharacterDocTreeList(null, characterIds, loginUserId);  //null是为了得到所有文件

        //由功能构成的子节点List
        List<RdmsHmiTree<RdmsFileDto>> childrenTreeList = new ArrayList<>(characterTreeList);

        //由任务工单构成的子节点List
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);

        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);
        jobitemTypeList.add(JobItemTypeEnum.TASK_MILESTONE);
        jobitemTypeList.add(JobItemTypeEnum.REVIEW_SUBP);
        jobitemTypeList.add(JobItemTypeEnum.REVIEW_MILESTONE);
        jobitemTypeList.add(JobItemTypeEnum.REVIEW_COO);
        List<RdmsJobItemDto> taskJobItemDtos = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, jobItemStatusList, jobitemTypeList);

        List<RdmsHmiTree<RdmsFileDto>> jobTreeNodeList = new ArrayList<>();
        for(RdmsJobItem jobItem: taskJobItemDtos){
            RdmsHmiTree<RdmsFileDto> jobTreeNode = new RdmsHmiTree<>();
            jobTreeNode.setId(jobItem.getId());
            jobTreeNode.setLabel(jobItem.getJobName());
            jobTreeNode.setValue("");
            jobTreeNode.setParent(jobItem.getCharacterId());
            jobTreeNode.setStatus(DocTypeEnum.JOBITEM.getType());
            jobTreeNode.setObj(null);

            List<RdmsHmiTree<RdmsFileDto>> docTreeNodeList = new ArrayList<>();
            List<RdmsFileDto> jobitemAllAttachments = rdmsJobItemService.getJobitemAllAttachments(jobItem.getId());  //包含任务工单衍生的下级工单
            if(!CollectionUtils.isEmpty(jobitemAllAttachments)){
                for(RdmsFileDto fileDto: jobitemAllAttachments){
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }

                    RdmsHmiTree<RdmsFileDto> docTreeNode = new RdmsHmiTree<>();
                    docTreeNode.setId(fileDto.getId());
                    docTreeNode.setLabel(fileDto.getName());
                    docTreeNode.setValue(fileDto.getPath());
                    docTreeNode.setParent(jobItem.getCharacterId());
                    docTreeNode.setStatus(DocTypeEnum.FILE.getType());
                    docTreeNode.setObj(fileDto);

                    docTreeNodeList.add(docTreeNode);
                }
            }
            jobTreeNode.setChildren(docTreeNodeList);
            jobTreeNodeList.add(jobTreeNode);
        }
        childrenTreeList.addAll(jobTreeNodeList);

        //列表下级子项目的nodeList
        List<RdmsHmiTree<RdmsFileDto>> subprojectTreeNodeList = new ArrayList<>();
        List<RdmsProjectSubproject> subprojectListByParentId = rdmsSubprojectService.getSubprojectListByParentId(subprojectId);
        if(!CollectionUtils.isEmpty(subprojectListByParentId)){
            for(RdmsProjectSubproject projectSubproject: subprojectListByParentId){
                List<RdmsHmiTree<RdmsFileDto>> archiveTreeBySubprojectId = this.getArchiveTreeBySubprojectId(projectSubproject.getId(), loginUserId);
                subprojectTreeNodeList.addAll(archiveTreeBySubprojectId);
            }
        }

        childrenTreeList.addAll(subprojectTreeNodeList);

        subProjectTreeNode.setChildren(childrenTreeList);  //下一级的树
        subProjectTreeNodeList.add(subProjectTreeNode);

        return subProjectTreeNodeList;
    }

    @Transactional
    public List<RdmsHmiTree<RdmsFileDto>> getArchiveTreeByPreProjectId(String preprojectId, String loginUserId){
        List<RdmsHmiTree<RdmsFileDto>> preProjectTreeNodeList = new ArrayList<>();

        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(preprojectId);
        if(ObjectUtils.isEmpty(rdmsPreProject)){
            return preProjectTreeNodeList;
        }
        RdmsHmiTree<RdmsFileDto> preProjectTreeNode = new RdmsHmiTree<>();
        preProjectTreeNode.setId(rdmsPreProject.getId());
        preProjectTreeNode.setLabel(rdmsPreProject.getPreProjectName());
        preProjectTreeNode.setValue("");
        preProjectTreeNode.setParent("");
        preProjectTreeNode.setStatus(DocTypeEnum.PRE_PROJECT.getType());
        preProjectTreeNode.setObj(null);

        //由任务工单构成的子节点List
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);

        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_FUNCTION);

        List<RdmsJobItemDto> taskJobItemDtos = rdmsJobItemService.getJobitemListByPreProjectIdAndStatusListTypeList(preprojectId, jobItemStatusList, jobitemTypeList);

        List<RdmsHmiTree<RdmsFileDto>> jobTreeNodeList = new ArrayList<>();
        for(RdmsJobItem jobItem: taskJobItemDtos){
            RdmsHmiTree<RdmsFileDto> jobTreeNode = new RdmsHmiTree<>();
            jobTreeNode.setId(jobItem.getId());
            jobTreeNode.setLabel(jobItem.getJobName());
            jobTreeNode.setValue("");
            jobTreeNode.setParent(jobItem.getCharacterId());
            jobTreeNode.setStatus(DocTypeEnum.JOBITEM.getType());
            jobTreeNode.setObj(null);

            List<RdmsHmiTree<RdmsFileDto>> docTreeNodeList = new ArrayList<>();
            List<RdmsFileDto> jobitemAllAttachments = rdmsJobItemService.getJobitemAllAttachments(jobItem.getId());  //包含任务工单衍生的下级工单
            if(!CollectionUtils.isEmpty(jobitemAllAttachments)){
                for(RdmsFileDto fileDto: jobitemAllAttachments){
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }

                    RdmsHmiTree<RdmsFileDto> docTreeNode = new RdmsHmiTree<>();
                    docTreeNode.setId(fileDto.getId());
                    docTreeNode.setLabel(fileDto.getName());
                    docTreeNode.setValue(fileDto.getPath());
                    docTreeNode.setParent(jobItem.getCharacterId());
                    docTreeNode.setStatus(DocTypeEnum.FILE.getType());
                    docTreeNode.setObj(fileDto);

                    docTreeNodeList.add(docTreeNode);
                }
            }
            jobTreeNode.setChildren(docTreeNodeList);
            jobTreeNodeList.add(jobTreeNode);
        }
        preProjectTreeNode.setChildren(jobTreeNodeList);  //下一级的树
        preProjectTreeNodeList.add(preProjectTreeNode);

        return preProjectTreeNodeList;
    }

    @Transactional
    public List<RdmsHmiTree<RdmsFileDto>> getTestArchiveTreeByProjectId(String projectId, String loginUserId){
        List<RdmsHmiTree<RdmsFileDto>> projectTreeNodeList = new ArrayList<>();

        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        if(ObjectUtils.isEmpty(rdmsProject)){
            return projectTreeNodeList;
        }
        RdmsHmiTree<RdmsFileDto> projectTreeNode = new RdmsHmiTree<>();
        projectTreeNode.setId(rdmsProject.getId());
        projectTreeNode.setLabel(rdmsProject.getProjectName());
        projectTreeNode.setValue("");
        projectTreeNode.setParent("");
        projectTreeNode.setStatus(DocTypeEnum.PROJECT.getType());
        projectTreeNode.setObj(null);

        //由任务工单构成的子节点List
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);

        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_TEST);
        jobitemTypeList.add(JobItemTypeEnum.TEST);

        List<RdmsJobItemDto> taskJobItemDtos = rdmsJobItemService.getJobitemListByProjectIdAndStatusListTypeList(projectId, jobItemStatusList, jobitemTypeList);

        List<RdmsHmiTree<RdmsFileDto>> jobTreeNodeList = new ArrayList<>();
        for(RdmsJobItem jobItem: taskJobItemDtos){
            RdmsHmiTree<RdmsFileDto> jobTreeNode = new RdmsHmiTree<>();
            jobTreeNode.setId(jobItem.getId());
            jobTreeNode.setLabel(jobItem.getJobName());
            jobTreeNode.setValue("");
            jobTreeNode.setParent(jobItem.getCharacterId());
            jobTreeNode.setStatus(DocTypeEnum.JOBITEM.getType());
            jobTreeNode.setObj(null);

            List<RdmsHmiTree<RdmsFileDto>> docTreeNodeList = new ArrayList<>();
            List<RdmsFileDto> jobitemAllAttachments = rdmsJobItemService.getJobitemAllAttachments(jobItem.getId());  //包含任务工单衍生的下级工单
            if(!CollectionUtils.isEmpty(jobitemAllAttachments)){
                for(RdmsFileDto fileDto: jobitemAllAttachments){
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }

                    RdmsHmiTree<RdmsFileDto> docTreeNode = new RdmsHmiTree<>();
                    docTreeNode.setId(fileDto.getId());
                    docTreeNode.setLabel(fileDto.getName());
                    docTreeNode.setValue(fileDto.getPath());
                    docTreeNode.setParent(jobItem.getCharacterId());
                    docTreeNode.setStatus(DocTypeEnum.FILE.getType());
                    docTreeNode.setObj(fileDto);

                    docTreeNodeList.add(docTreeNode);
                }
            }
            jobTreeNode.setChildren(docTreeNodeList);
            jobTreeNodeList.add(jobTreeNode);
        }
        projectTreeNode.setChildren(jobTreeNodeList);  //下一级的树
        projectTreeNodeList.add(projectTreeNode);

        return projectTreeNodeList;
    }

    private List<RdmsHmiTree<RdmsFileDto>> getCharacterDocTreeList(String workerId, List<String> characterIdList, String loginUserId) {
        List<RdmsHmiTree<RdmsFileDto>> characterTreeNodeList = new ArrayList<>();
        for(String characterId: characterIdList){
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
            RdmsHmiTree<RdmsFileDto> characterTreeNode = new RdmsHmiTree<>();
            characterTreeNode.setId(rdmsCharacter.getId());
            characterTreeNode.setLabel(rdmsCharacter.getCharacterName());
            characterTreeNode.setValue("");
            characterTreeNode.setParent(rdmsCharacter.getSubprojectId());
            characterTreeNode.setStatus(DocTypeEnum.CHARACTER.getType());
            characterTreeNode.setObj(null);

            List<RdmsJobItem> jobitemListByCharacterId = rdmsJobItemService.getJobitemListByCharacterIdAndWorkerId(characterId, workerId);
            List<RdmsHmiTree<RdmsFileDto>> jobTreeNodeList = new ArrayList<>();
            for(RdmsJobItem jobItem: jobitemListByCharacterId){
                RdmsHmiTree<RdmsFileDto> jobTreeNode = new RdmsHmiTree<>();
                jobTreeNode.setId(jobItem.getId());
                jobTreeNode.setLabel(jobItem.getJobName());
                jobTreeNode.setValue("");
                jobTreeNode.setParent(jobItem.getCharacterId());
                jobTreeNode.setStatus(DocTypeEnum.JOBITEM.getType());
                jobTreeNode.setObj(null);

                List<RdmsHmiTree<RdmsFileDto>> docTreeNodeList = new ArrayList<>();
                List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemService.getJobItemArchiveDocs(jobItem.getId(), workerId);
                for(RdmsFileDto fileDto: jobItemArchiveDocs){
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }

                    RdmsHmiTree<RdmsFileDto> docTreeNode = new RdmsHmiTree<>();
                    docTreeNode.setId(fileDto.getId());
                    docTreeNode.setLabel(fileDto.getName());
                    docTreeNode.setValue(fileDto.getPath());
                    docTreeNode.setParent(jobItem.getCharacterId());
                    docTreeNode.setStatus(DocTypeEnum.FILE.getType());
                    docTreeNode.setObj(fileDto);

                    docTreeNodeList.add(docTreeNode);
                }
                jobTreeNode.setChildren(docTreeNodeList);
                jobTreeNodeList.add(jobTreeNode);
            }
            characterTreeNode.setChildren(jobTreeNodeList);
            characterTreeNodeList.add(characterTreeNode);
        }
        return characterTreeNodeList;
    }

    /**
     * 获取功能组件的参考功能文献
     * @param characterIdList
     * @param loginUserId
     * @return
     */
    private List<RdmsHmiTree<RdmsFileDto>> getCharacterAllDocTreeList(List<String> characterIdList, String loginUserId) {
        List<RdmsHmiTree<RdmsFileDto>> characterTreeNodeList = new ArrayList<>();
        for(String characterId: characterIdList){
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
            RdmsHmiTree<RdmsFileDto> characterTreeNode = new RdmsHmiTree<>();
            characterTreeNode.setId(rdmsCharacter.getId());
            characterTreeNode.setLabel(rdmsCharacter.getCharacterName());
            characterTreeNode.setValue("");
            characterTreeNode.setParent(rdmsCharacter.getSubprojectId());
            characterTreeNode.setStatus(DocTypeEnum.CHARACTER.getType());
            characterTreeNode.setObj(null);

            List<RdmsJobItem> jobitemListByCharacterId = rdmsJobItemService.getJobitemListByCharacterIdAndWorkerId(characterId, null);
            List<RdmsHmiTree<RdmsFileDto>> jobTreeNodeList = new ArrayList<>();
            for(RdmsJobItem jobItem: jobitemListByCharacterId){
                RdmsHmiTree<RdmsFileDto> jobTreeNode = new RdmsHmiTree<>();
                jobTreeNode.setId(jobItem.getId());
                jobTreeNode.setLabel(jobItem.getJobName());
                jobTreeNode.setValue("");
                jobTreeNode.setParent(jobItem.getCharacterId());
                jobTreeNode.setStatus(DocTypeEnum.JOBITEM.getType());
                jobTreeNode.setObj(null);

                List<RdmsHmiTree<RdmsFileDto>> docTreeNodeList = new ArrayList<>();
                List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemService.getJobItemArchiveDocs(jobItem.getId(), loginUserId);
                for(RdmsFileDto fileDto: jobItemArchiveDocs){
                    //开放参考文献访问权限
                    fileDto.setAuthStatus(true);  //作为参考文献打开时, 具有所有文件的下载权限

                    RdmsHmiTree<RdmsFileDto> docTreeNode = new RdmsHmiTree<>();
                    docTreeNode.setId(fileDto.getId());
                    docTreeNode.setLabel(fileDto.getName());
                    docTreeNode.setValue(fileDto.getPath());
                    docTreeNode.setParent(jobItem.getCharacterId());
                    docTreeNode.setStatus(DocTypeEnum.FILE.getType());
                    docTreeNode.setObj(fileDto);

                    docTreeNodeList.add(docTreeNode);
                }
                jobTreeNode.setChildren(docTreeNodeList);
                jobTreeNodeList.add(jobTreeNode);
            }
            characterTreeNode.setChildren(jobTreeNodeList);
            characterTreeNodeList.add(characterTreeNode);
        }
        return characterTreeNodeList;
    }

    private List<RdmsHmiTree<RdmsFileDto>> getSubprojectDirectDocTreeList_characterIdNull(List<RdmsJobItem> jobItemList, String loginUserId) {
        List<RdmsHmiTree<RdmsFileDto>> subProjectTreeNodeList = new ArrayList<>();
        List<String> subProjectIdList_characterIdNull = jobItemList.stream().map(RdmsJobItem::getSubprojectId).distinct().collect(Collectors.toList());

        for(String subprojectId: subProjectIdList_characterIdNull){
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
            RdmsHmiTree<RdmsFileDto> subProjectTreeNode = new RdmsHmiTree<>();
            subProjectTreeNode.setId(subproject.getId());
            subProjectTreeNode.setLabel(subproject.getLabel());
            subProjectTreeNode.setValue("");
            subProjectTreeNode.setParent("");
            subProjectTreeNode.setStatus(DocTypeEnum.SUB_PROJECT.getType());
            subProjectTreeNode.setObj(null);

            List<RdmsHmiTree<RdmsFileDto>> jobTreeNodeList = new ArrayList<>();
            List<RdmsJobItem> collect = jobItemList.stream().filter(item -> item.getSubprojectId().equals(subprojectId)).collect(Collectors.toList());

            for(RdmsJobItem jobItem: collect){
                RdmsHmiTree<RdmsFileDto> jobTreeNode = new RdmsHmiTree<>();
                jobTreeNode.setId(jobItem.getId());
                jobTreeNode.setLabel(jobItem.getJobName());
                jobTreeNode.setValue("");
                jobTreeNode.setParent(jobItem.getCharacterId());
                jobTreeNode.setStatus(DocTypeEnum.JOBITEM.getType());
                jobTreeNode.setObj(null);

                List<RdmsHmiTree<RdmsFileDto>> docTreeNodeList = new ArrayList<>();
                List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemService.getJobItemArchiveDocs(jobItem.getId(), loginUserId);
                for(RdmsFileDto fileDto: jobItemArchiveDocs){
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }

                    RdmsHmiTree<RdmsFileDto> docTreeNode = new RdmsHmiTree<>();
                    docTreeNode.setId(fileDto.getId());
                    docTreeNode.setLabel(fileDto.getName());
                    docTreeNode.setValue(fileDto.getPath());
                    docTreeNode.setParent(jobItem.getCharacterId());
                    docTreeNode.setStatus(DocTypeEnum.FILE.getType());
                    docTreeNode.setObj(fileDto);

                    docTreeNodeList.add(docTreeNode);
                }
                jobTreeNode.setChildren(docTreeNodeList);
                jobTreeNodeList.add(jobTreeNode);
            }
            subProjectTreeNode.setChildren(jobTreeNodeList);
            subProjectTreeNodeList.add(subProjectTreeNode);
        }
        return subProjectTreeNodeList;
    }

    private List<RdmsHmiTree<RdmsFileDto>> getPreProjectDirectDocTreeList_characterIdNull(List<RdmsJobItem> jobItemList, String loginUserId) {
        List<RdmsHmiTree<RdmsFileDto>> preProjectTreeNodeList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(jobItemList)){
            List<RdmsJobItem> jobItemListCollect = jobItemList.stream().filter(rdmsJobItem -> rdmsJobItem.getPreProjectId() != null).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(jobItemListCollect)){
                List<String> preProjectIdList_characterIdNull = jobItemListCollect.stream().map(RdmsJobItem::getPreProjectId).distinct().collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(preProjectIdList_characterIdNull)){
//                    List<String> stringList = preProjectIdList_characterIdNull.stream().filter(Objects::nonNull).collect(Collectors.toList());
//                    if(!CollectionUtils.isEmpty(preProjectIdList_characterIdNull)){
                        for(String preProjectId: preProjectIdList_characterIdNull){
                            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(preProjectId);
                            RdmsHmiTree<RdmsFileDto> preProjectTreeNode = new RdmsHmiTree<>();
                            preProjectTreeNode.setId(rdmsPreProject.getId());
                            preProjectTreeNode.setLabel(rdmsPreProject.getPreProjectName());
                            preProjectTreeNode.setValue("");
                            preProjectTreeNode.setParent("");
                            preProjectTreeNode.setStatus(DocTypeEnum.DEMAND.getType());
                            preProjectTreeNode.setObj(null);

                            List<RdmsHmiTree<RdmsFileDto>> jobTreeNodeList = new ArrayList<>();
                            List<RdmsJobItem> collect = jobItemListCollect.stream().filter(item -> item.getPreProjectId().equals(preProjectId)).collect(Collectors.toList());
                            for(RdmsJobItem jobItem: collect){
                                RdmsHmiTree<RdmsFileDto> jobTreeNode = new RdmsHmiTree<>();
                                jobTreeNode.setId(jobItem.getId());
                                jobTreeNode.setLabel(jobItem.getJobName());
                                jobTreeNode.setValue("");
                                jobTreeNode.setParent(jobItem.getCharacterId());
                                jobTreeNode.setStatus(DocTypeEnum.JOBITEM.getType());
                                jobTreeNode.setObj(null);

                                List<RdmsHmiTree<RdmsFileDto>> docTreeNodeList = new ArrayList<>();
                                List<RdmsFileDto> jobItemArchiveDocs = rdmsJobItemService.getJobItemArchiveDocs(jobItem.getId(), loginUserId);
                                for(RdmsFileDto fileDto: jobItemArchiveDocs){
                                    //读取访问权限
                                    if(!ObjectUtils.isEmpty(fileDto)){
                                        //补充填写文件权限信息
                                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                        if(ObjectUtils.isEmpty(byFileId)){
                                            fileDto.setAuthStatus(true);
                                        }else{
                                            String authIdsStr = byFileId.getAuthIdsStr();
                                            if(ObjectUtils.isEmpty(authIdsStr)){
                                                fileDto.setAuthStatus(true);
                                            }else{
                                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                                if(strings.contains(loginUserId)){
                                                    fileDto.setAuthStatus(true);
                                                }else{
                                                    fileDto.setAuthStatus(false);
                                                }
                                            }
                                        }
                                    }

                                    RdmsHmiTree<RdmsFileDto> docTreeNode = new RdmsHmiTree<>();
                                    docTreeNode.setId(fileDto.getId());
                                    docTreeNode.setLabel(fileDto.getName());
                                    docTreeNode.setValue(fileDto.getPath());
                                    docTreeNode.setParent(jobItem.getCharacterId());
                                    docTreeNode.setStatus(DocTypeEnum.FILE.getType());
                                    docTreeNode.setObj(fileDto);

                                    docTreeNodeList.add(docTreeNode);
                                }
                                jobTreeNode.setChildren(docTreeNodeList);
                                jobTreeNodeList.add(jobTreeNode);
                            }
                            preProjectTreeNode.setChildren(jobTreeNodeList);
                            preProjectTreeNodeList.add(preProjectTreeNode);
                        }
//                    }
                }
            }
        }

        return preProjectTreeNodeList;
    }

    /**
     * 使用idList进行查询
     */
    public List<RdmsFileDto> getFileListByIdList(List<String> idList) {
        RdmsFileExample fileExample = new RdmsFileExample();
        if(! CollectionUtils.isEmpty(idList)){
            RdmsFileExample.Criteria criteria = fileExample.createCriteria();
            criteria.andIdIn(idList).andDeletedEqualTo(0);
            List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
            List<RdmsFileDto> fileDtos = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
            for(RdmsFileDto fileDto: fileDtos){
                fileDto.setAbsPath(ossDomain + '/' + fileDto.getPath());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                fileDto.setOperatorName(rdmsCustomerUser.getTrueName());
            }
            return fileDtos;
        }else{
            return null;
        }
    }

    public Integer getCustomerFilesSumSize(String customerId) {
        RdmsFileExample fileExample = new RdmsFileExample();
        if(! ObjectUtils.isEmpty(customerId)) {
            RdmsFileExample.Criteria criteria = fileExample.createCriteria();
            criteria.andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
            List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
            if (!CollectionUtils.isEmpty(rdmsFiles)) {
                return rdmsFiles.stream().map(RdmsFile::getSize).reduce(0, Integer::sum);
            } else {
                return 0;
            }
        }else{
            return 0;
        }
    }

    /**
     * 使用idList进行查询
     */
    public List<RdmsFileDto> getFileListByIdListStr(String fileIdListStr, String loginUserId) {
        if(!ObjectUtils.isEmpty(fileIdListStr) && fileIdListStr.length()>6){
            List<String> idList = JSON.parseArray(fileIdListStr, String.class);
            RdmsFileExample fileExample = new RdmsFileExample();
            RdmsFileExample.Criteria criteria = fileExample.createCriteria();
            criteria.andIdIn(idList).andDeletedEqualTo(0);
            List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
            List<RdmsFileDto> fileDtos = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
            if(!CollectionUtils.isEmpty(fileDtos)){
                for(RdmsFileDto fileDto: fileDtos){
                    //读取访问权限
                    if(!ObjectUtils.isEmpty(fileDto)){
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(org.springframework.util.ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> authIds = JSON.parseArray(authIdsStr, String.class);
                                if(authIds.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }
                    }
                }
            }
            for(RdmsFileDto fileDto: fileDtos){
                fileDto.setAbsPath(ossDomain + '/' + fileDto.getPath());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                    fileDto.setOperatorName(rdmsCustomerUser.getTrueName());
                }
            }
            return fileDtos;
        }else{
            return null;
        }
    }

    public String getApproverId(String fileId) {
        String approverId;
        RdmsFile rdmsFile = this.selectByPrimaryKey(fileId);
        if(!ObjectUtils.isEmpty(rdmsFile.getSubprojectId())){
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(rdmsFile.getSubprojectId());
            if(! ObjectUtils.isEmpty(subproject)){
                approverId = subproject.getProjectManagerId();  //子项目文件有子项目经理审批
            }else{
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(rdmsFile.getCustomerId());
                List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByLoginName(rdmsCustomer.getContactPhone());
                approverId = customerUserByLoginName.get(0).getId();  //由管理员批
            }
        }else if(!ObjectUtils.isEmpty(rdmsFile.getPreProjectId())){
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsFile.getPreProjectId());
            if(! ObjectUtils.isEmpty(rdmsPreProject)){
                approverId = rdmsPreProject.getProductManagerId();  //预立项项目的由产品经理批
            }else{
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(rdmsFile.getCustomerId());
                List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByLoginName(rdmsCustomer.getContactPhone());
                approverId = customerUserByLoginName.get(0).getId();
            }
        }else{
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(rdmsFile.getCustomerId());
            List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByLoginName(rdmsCustomer.getContactPhone());
            approverId = customerUserByLoginName.get(0).getId();
        }
        return approverId;
    }

    public List<RdmsFileDto> getFileListByCustomerIdAndAdminId(String customerId, String adminId) {
        if(!ObjectUtils.isEmpty(customerId) && !ObjectUtils.isEmpty(adminId)){
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
            List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByLoginName(rdmsCustomer.getContactPhone());
            if(!CollectionUtils.isEmpty(customerUserByLoginName)){
                if(customerUserByLoginName.get(0).getId().equals(adminId)){
                    RdmsFileExample fileExample = new RdmsFileExample();
                    RdmsFileExample.Criteria criteria = fileExample.createCriteria();
                    criteria.andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
                    List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
                    List<RdmsFileDto> fileDtos = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
                    for(RdmsFileDto fileDto: fileDtos){
                        fileDto.setAbsPath(ossDomain + '/' + fileDto.getPath());
                    }
                    return fileDtos;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
        return null;
    }


    /**
     *
     */
    public String save(RdmsFile rdmsFile) {
        if(rdmsFile.getCreateTime()==null){
            rdmsFile.setCreateTime(new Date());
        }
        rdmsFile.setDeleted(0);

        if(rdmsFile.getId() == null || rdmsFile.getId().equals("")){
            return this.insert(rdmsFile);
        }else{
            RdmsFileExample fileExample = new RdmsFileExample();
            fileExample.createCriteria().andIdEqualTo(rdmsFile.getId());
            List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
            if(!CollectionUtils.isEmpty(rdmsFiles)){
                return this.update(rdmsFile);
            }else{
                return this.insert(rdmsFile);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsFile file) {
        if(org.springframework.util.ObjectUtils.isEmpty(file.getId())){  //当前端页面给出projectID时,将不为空
            file.setId(UuidUtil.getShortUuid());
        }
        Date now = new Date();
        file.setCreateTime(now);
        file.setUpdateTime(now);
        rdmsFileMapper.insert(file);
        return file.getId();
    }

    /**
     * 更新
     */
    public String update(RdmsFile file) {
        file.setUpdateTime(new Date());
        rdmsFileMapper.updateByPrimaryKey(file);
        return file.getId();
    }

    /**
     * 删除
     */
    public String delete(String id) {
        RdmsFile rdmsFile = rdmsFileMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsFile)){
            rdmsFile.setDeleted(1);
            return this.update(rdmsFile);
        }else{
            return null;
        }
    }

    public List<RdmsFileDto> getFileListByUseAndItemId(RdmsFileQueryDto fileQueryDto){
        RdmsFileExample fileExample = new RdmsFileExample();
        fileExample.createCriteria()
                .andUseEqualTo(fileQueryDto.getUse())
                .andItemIdEqualTo(fileQueryDto.getItemId())
//                .andOperatorIdEqualTo(fileQueryDto.getOperatorId())   //不能用这个条件, 就是要把符合条件所有人的都读出来, 否则保存的时候, 会丢掉别人提供的文件
                .andDeletedEqualTo(0);
        List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);

        return CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
    }

    public List<RdmsFileDto> getFileListByJobitemId(String jobitemId){
        RdmsFileExample fileExample = new RdmsFileExample();
        fileExample.createCriteria()
                .andItemIdEqualTo(jobitemId)
                .andUseEqualTo(FileGroupingEnum.PROPERTY.getCode())
                .andDeletedEqualTo(0);
        List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);

        return CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
    }

    public RdmsFileDto getFileByFullName(String fullName){
        RdmsFileExample fileExample = new RdmsFileExample();
        fileExample.createCriteria()
                .andPathEqualTo(fullName)
                .andDeletedEqualTo(0);
        List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(fileExample);
        if(!CollectionUtils.isEmpty(rdmsFiles)){
            List<RdmsFileDto> fileDtos = CopyUtil.copyList(rdmsFiles, RdmsFileDto.class);
            return fileDtos.get(0);
        }else{
            return null;
        }
    }

    /**
     *
     */
    public RdmsFile selectByPrimaryKey(String fileId) {
        return rdmsFileMapper.selectByPrimaryKey(fileId);
    }

    /**
     *
     */
    public void saveByKey(RdmsFile rdmsFile) {
        RdmsFile fileDb = selectByKey(rdmsFile.getKey());
        if (fileDb == null) {
            this.insert(rdmsFile);
        } else {
            this.update(rdmsFile);
        }
    }

    public RdmsFile selectByKey(String key) {
        RdmsFileExample example = new RdmsFileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<RdmsFile> rdmsFiles = rdmsFileMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(rdmsFiles)) {
            return null;
        } else {
            return rdmsFiles.get(0);
        }
    }

    public RdmsFileDto getFileSimpleRecordInfo(String fileId) {
        RdmsFile rdmsFile = this.selectByPrimaryKey(fileId);
        if(!ObjectUtils.isEmpty(rdmsFile)){
            RdmsFileDto fileDto = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
            fileDto.setOperatorName(rdmsCustomerUser.getTrueName());
            fileDto.setAbsPath(ossDomain + '/' + fileDto.getPath());
            return fileDto;
        }else{
            return null;
        }

    }

}
