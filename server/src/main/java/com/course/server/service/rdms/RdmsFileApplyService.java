/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.rdms.RdmsFileApplyDto;
import com.course.server.enums.rdms.FileApplicationStatusEnum;
import com.course.server.mapper.RdmsFileApplyMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RdmsFileApplyService {

    @Resource
    private RdmsFileApplyMapper rdmsFileApplyMapper;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;


    public List<RdmsFileApplyDto> getApplyFileListByFileIdAndCustomerUserId(String fileId, String customerUserId) {
        RdmsFileApplyExample fileApplyExample = new RdmsFileApplyExample();
        RdmsFileApplyExample.Criteria criteria = fileApplyExample.createCriteria();
        criteria.andFileIdEqualTo(fileId).andProposerIdEqualTo(customerUserId);
        List<RdmsFileApply> rdmsFileApplies = rdmsFileApplyMapper.selectByExample(fileApplyExample);
        if(!CollectionUtils.isEmpty(rdmsFileApplies)){
            List<RdmsFileApplyDto> rdmsFileApplyDtos = CopyUtil.copyList(rdmsFileApplies, RdmsFileApplyDto.class);
            return rdmsFileApplyDtos;
        }else{
            return null;
        }
    }

    public List<RdmsFileApplyDto> getFileApplyList(String customerId, String customerUserId) {
        RdmsFileApplyExample fileApplyExample = new RdmsFileApplyExample();
        RdmsFileApplyExample.Criteria criteria = fileApplyExample.createCriteria();
        List<String> statusList = new ArrayList<>();
        statusList.add(FileApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(FileApplicationStatusEnum.REJECT.getStatus());
        statusList.add(FileApplicationStatusEnum.APPROVED.getStatus());
        criteria.andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0)
                .andApplicationStatusIn(statusList)
                .andProposerIdEqualTo(customerUserId);
        List<RdmsFileApply> rdmsFileApplies = rdmsFileApplyMapper.selectByExample(fileApplyExample);
        if(!CollectionUtils.isEmpty(rdmsFileApplies)){
            List<RdmsFileApplyDto> rdmsFileApplyDtos = CopyUtil.copyList(rdmsFileApplies, RdmsFileApplyDto.class);
            return rdmsFileApplyDtos;
        }else{
            return null;
        }
    }

    public List<RdmsFileApplyDto> getFileApplicationingList(String customerId, String customerUserId) {
        RdmsFileApplyExample fileApplyExample = new RdmsFileApplyExample();
        RdmsFileApplyExample.Criteria criteria = fileApplyExample.createCriteria();
        List<String> statusList = new ArrayList<>();
        statusList.add(FileApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(FileApplicationStatusEnum.APPROVED.getStatus());
        criteria.andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0)
                .andApplicationStatusIn(statusList)
                .andProposerIdEqualTo(customerUserId);
        List<RdmsFileApply> rdmsFileApplies = rdmsFileApplyMapper.selectByExample(fileApplyExample);
        if(!CollectionUtils.isEmpty(rdmsFileApplies)){
            List<RdmsFileApplyDto> rdmsFileApplyDtos = CopyUtil.copyList(rdmsFileApplies, RdmsFileApplyDto.class);
            return rdmsFileApplyDtos;
        }else{
            return null;
        }
    }

    public long getApplyNum(String customerId, String customerUserId) {
        RdmsFileApplyExample fileApplyExample = new RdmsFileApplyExample();
        RdmsFileApplyExample.Criteria criteria = fileApplyExample.createCriteria();
        List<String> statusList = new ArrayList<>();
        statusList.add(FileApplicationStatusEnum.APPLICATION.getStatus());
        criteria.andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0)
                .andApplicationStatusIn(statusList)
                .andApproverIdEqualTo(customerUserId);
        return rdmsFileApplyMapper.countByExample(fileApplyExample);
    }

    public List<RdmsFileApplyDto> getApplyList(String approverId) {
        if(!ObjectUtils.isEmpty(approverId)){
            RdmsFileApplyExample fileApplyExample = new RdmsFileApplyExample();
            RdmsFileApplyExample.Criteria criteria = fileApplyExample.createCriteria();
            criteria.andApproverIdEqualTo(approverId)
                    .andApplicationStatusEqualTo(FileApplicationStatusEnum.APPLICATION.getStatus())
                    .andDeletedEqualTo(0);
            List<RdmsFileApply> rdmsFileApplies = rdmsFileApplyMapper.selectByExample(fileApplyExample);
            List<RdmsFileApplyDto> rdmsFileApplyDtos = CopyUtil.copyList(rdmsFileApplies, RdmsFileApplyDto.class);
            if(!CollectionUtils.isEmpty(rdmsFileApplyDtos)){
                for(RdmsFileApplyDto fileApplyDto: rdmsFileApplyDtos){
                    RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(fileApplyDto.getFileId());
                    fileApplyDto.setFileName(rdmsFile.getName());
                    fileApplyDto.setUseClass(rdmsFile.getUse());
                    fileApplyDto.setFileCreateTime(rdmsFile.getCreateTime());

                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileApplyDto.getProposerId());
                    fileApplyDto.setProposerName(rdmsCustomerUser.getTrueName());
                }
            }

            return rdmsFileApplyDtos;
        }else{
            return null;
        }
    }

    public RdmsFileApplyDto getApplyInfo(String applyId) {
        if(!ObjectUtils.isEmpty(applyId)){
            RdmsFileApply fileApply = this.selectByPrimaryKey(applyId);
            RdmsFileApplyDto fileApplyDto = CopyUtil.copy(fileApply, RdmsFileApplyDto.class);

            RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(fileApplyDto.getFileId());
            fileApplyDto.setFileName(rdmsFile.getName());

            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsFile.getOperatorId());
            fileApplyDto.setOperatorName(rdmsCustomerUser.getTrueName());

            if(!ObjectUtils.isEmpty(rdmsFile.getSubprojectId())){
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(rdmsFile.getSubprojectId());
                fileApplyDto.setProjectName(subproject.getLabel());
            }else if(!ObjectUtils.isEmpty(rdmsFile.getPreProjectId())){
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsFile.getPreProjectId());
                if(! ObjectUtils.isEmpty(rdmsPreProject)){
                    fileApplyDto.setProjectName(rdmsPreProject.getPreProjectName());
                }
            }else {
                fileApplyDto.setProjectName("");
            }

            fileApplyDto.setFileCreateTime(rdmsFile.getCreateTime());
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(fileApplyDto.getProposerId());
            fileApplyDto.setProposerName(rdmsCustomerUser1.getTrueName());

            return fileApplyDto;
        }else{
            return null;
        }
    }

    public int applyApprove(String applyId) {
        if(!ObjectUtils.isEmpty(applyId)){
            RdmsFileApply fileApply = this.selectByPrimaryKey(applyId);
            fileApply.setApplicationStatus(FileApplicationStatusEnum.APPROVED.getStatus());
            fileApply.setApprovalTime(new Date());
            fileApply.setUpdateTime(new Date());
            this.updateByPrimaryKeySelective(fileApply);

            //修改文件权限
            RdmsFileAuth fileAuth = new RdmsFileAuth();
            fileAuth.setFileId(fileApply.getFileId());
            List<String> authList = new ArrayList<>();
            authList.add(fileApply.getProposerId());
            String jsonString = JSON.toJSONString(authList);
            fileAuth.setAuthIdsStr(jsonString);
            rdmsFileAuthService.saveByFileId(fileAuth);
            return 1;
        }else{
            return 0;
        }
    }

    public int applyReject(String applyId) {
        if(!ObjectUtils.isEmpty(applyId)){
            RdmsFileApply fileApply = new RdmsFileApply();
            fileApply.setId(applyId);
            fileApply.setApplicationStatus(FileApplicationStatusEnum.REJECT.getStatus());
            fileApply.setApprovalTime(new Date());
            fileApply.setUpdateTime(new Date());
            return this.updateByPrimaryKeySelective(fileApply);
        }else{
            return 0;
        }
    }

    public List<RdmsFileApply> getFileApplyByFileIdAndCustomerUserId(String fileId, String proposerId) {
        RdmsFileApplyExample fileApplyExample = new RdmsFileApplyExample();
        RdmsFileApplyExample.Criteria criteria = fileApplyExample.createCriteria();
        criteria.andFileIdEqualTo(fileId)
                .andProposerIdEqualTo(proposerId);
        return rdmsFileApplyMapper.selectByExample(fileApplyExample);
    }

    public String save(RdmsFileApply rdmsFileApply) {
        if(rdmsFileApply.getCreateTime()==null){
            rdmsFileApply.setCreateTime(new Date());
        }
        rdmsFileApply.setDeleted(0);

        if(rdmsFileApply.getId() == null || rdmsFileApply.getId().equals("")){
            return this.insert(rdmsFileApply);
        }else{
            RdmsFileApplyExample fileApplyExample = new RdmsFileApplyExample();
            fileApplyExample.createCriteria().andIdEqualTo(rdmsFileApply.getId());
            List<RdmsFileApply> rdmsFileApplies = rdmsFileApplyMapper.selectByExample(fileApplyExample);
            if(!CollectionUtils.isEmpty(rdmsFileApplies)){
                return this.update(rdmsFileApply);
            }else{
                return this.insert(rdmsFileApply);
            }
        }
    }

    private String insert(RdmsFileApply fileApply) {
        if(ObjectUtils.isEmpty(fileApply.getId())){  //当前端页面给出projectID时,将不为空
            fileApply.setId(UuidUtil.getShortUuid());
        }
        Date now = new Date();
        fileApply.setCreateTime(now);
        fileApply.setUpdateTime(now);
        rdmsFileApplyMapper.insert(fileApply);
        return fileApply.getId();
    }

    public String update(RdmsFileApply fileApply) {
        fileApply.setUpdateTime(new Date());
        rdmsFileApplyMapper.updateByPrimaryKey(fileApply);
        return fileApply.getId();
    }

    public String delete(String id) {
        RdmsFileApply rdmsFileApply = rdmsFileApplyMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsFileApply)){
            rdmsFileApply.setDeleted(1);
            rdmsFileApply.setUpdateTime(new Date());
            return this.update(rdmsFileApply);
        }else{
            return null;
        }
    }

    public RdmsFileApply selectByPrimaryKey(String fileApplyId) {
        return rdmsFileApplyMapper.selectByPrimaryKey(fileApplyId);
    }

    public int updateByPrimaryKeySelective(RdmsFileApply fileApply) {
        return rdmsFileApplyMapper.updateByPrimaryKeySelective(fileApply);
    }

}
