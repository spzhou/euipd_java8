/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.domain.RdmsFileAuth;
import com.course.server.dto.rdms.RdmsRoleUsersDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsFileAuthMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsFileAuthService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsFileAuthService.class);

    @Resource
    private RdmsFileAuthMapper rdmsFileAuthMapper;
    @Resource
    private RdmsFileService rdmsFileService;
    @Autowired
    private RdmsDepartmentService rdmsDepartmentService;
    @Autowired
    private RdmsCustomerUserService rdmsCustomerUserService;

    public RdmsFileAuth selectByPrimaryKey(String id){
        return rdmsFileAuthMapper.selectByPrimaryKey(id);
    }

    public void setFileAuthUser(List<String> fileIdList, RdmsRoleUsersDto roleUsersDto) {
        //访问权限设置
        if(!CollectionUtils.isEmpty(fileIdList)){
            for(String fileId : fileIdList){
                List<String> fileAuthIds = new ArrayList<>();
                //读取文件的权限
                RdmsFileAuth byFileId = this.getByFileId(fileId);
                if(!ObjectUtils.isEmpty(byFileId)){
                    List<String> strings = JSON.parseArray(byFileId.getAuthIdsStr(), String.class);
                    if(!CollectionUtils.isEmpty(strings)){
                        fileAuthIds.addAll(strings);
                    }
                }
                //文件的提供者
                RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(fileId);
                if(!ObjectUtils.isEmpty(rdmsFile)){
                    fileAuthIds.add(rdmsFile.getOperatorId());
                    //当前人员的directLeader
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsFile.getOperatorId());
                    if(!ObjectUtils.isEmpty(rdmsCustomerUser) && !ObjectUtils.isEmpty(rdmsCustomerUser.getDepartmentId())){
                        RdmsDepartment department = rdmsDepartmentService.selectByPrimaryKey(rdmsCustomerUser.getDepartmentId());
                        if(!ObjectUtils.isEmpty(department)){
                            fileAuthIds.add(department.getManagerId());
                        }
                    }
                }
                //文件的接收人
                if(!ObjectUtils.isEmpty(roleUsersDto.getReceiverId())){
                    fileAuthIds.add(roleUsersDto.getReceiverId());
                }

                //当前公司的管理员
                if(!ObjectUtils.isEmpty(roleUsersDto.getSuperId())){
                    fileAuthIds.add(roleUsersDto.getSuperId());
                }

                //当前公司的ipmt
                if(!ObjectUtils.isEmpty(roleUsersDto.getIpmtId())){
                    fileAuthIds.add(roleUsersDto.getIpmtId());
                }

                //当前公司的Boss
                if(!ObjectUtils.isEmpty(roleUsersDto.getBossId())){
                    fileAuthIds.add(roleUsersDto.getBossId());
                }

                //当前项目的Pdm
                if(!ObjectUtils.isEmpty(roleUsersDto.getPdmId())){
                    fileAuthIds.add(roleUsersDto.getPdmId());
                }

                //当前项目的Pjm
                if(!ObjectUtils.isEmpty(roleUsersDto.getPjmId())){
                    fileAuthIds.add(roleUsersDto.getPjmId());
                }

                //loginUserId
                //当前项目的Pjm
                if(!ObjectUtils.isEmpty(roleUsersDto.getMainPjmId())){
                    fileAuthIds.add(roleUsersDto.getMainPjmId());
                }

                //loginUserId
                if(!ObjectUtils.isEmpty(roleUsersDto.getLoginUserId())){
                    fileAuthIds.add(roleUsersDto.getLoginUserId());
                }

                //executorId
                if(!ObjectUtils.isEmpty(roleUsersDto.getExecutorId())){
                    fileAuthIds.add(roleUsersDto.getExecutorId());
                }

                //testManager
                if(!ObjectUtils.isEmpty(roleUsersDto.getTmId())){
                    fileAuthIds.add(roleUsersDto.getTmId());
                }

                //系统工程总监
                if(!ObjectUtils.isEmpty(roleUsersDto.getSysgmId())){
                    fileAuthIds.add(roleUsersDto.getSysgmId());
                }

                //系统工程师
                if(!ObjectUtils.isEmpty(roleUsersDto.getSmId())){
                    fileAuthIds.add(roleUsersDto.getSmId());
                }

                //产品总监
                if(!ObjectUtils.isEmpty(roleUsersDto.getPdgmId())){
                    fileAuthIds.add(roleUsersDto.getPdgmId());
                }

                //测试总负责人
                if(!ObjectUtils.isEmpty(roleUsersDto.getTgmId())){
                    fileAuthIds.add(roleUsersDto.getTgmId());
                }

                //生成JSON字符串
                List<String> stringList = fileAuthIds.stream().distinct().collect(Collectors.toList());
                String authIdJsonStr = JSON.toJSONString(stringList);
                RdmsFileAuth fileAuth = new RdmsFileAuth();
                fileAuth.setFileId(fileId);
                fileAuth.setAuthIdsStr(authIdJsonStr);
                this.saveByFileId(fileAuth);
            }
        }
    }

    /**
     * 保存
     */
    public String save(RdmsFileAuth fileAuth) {
        if(ObjectUtils.isEmpty(fileAuth.getId())){
            return this.insert(fileAuth);
        }else{
            RdmsFileAuth rdmsFileAuth = this.selectByPrimaryKey(fileAuth.getId());
            if(ObjectUtils.isEmpty(rdmsFileAuth)){
                return this.insert(fileAuth);
            }else{
                return this.update(fileAuth);
            }
        }
    }

    public RdmsFileAuth getByFileId(String fileId){
        RdmsFileAuthExample rdmsFileAuthExample = new RdmsFileAuthExample();
        rdmsFileAuthExample.createCriteria()
                .andFileIdEqualTo(fileId)
                .andDeletedEqualTo(0);
        List<RdmsFileAuth> rdmsFileAuths = rdmsFileAuthMapper.selectByExample(rdmsFileAuthExample);
        if(!CollectionUtils.isEmpty(rdmsFileAuths)){
            return rdmsFileAuths.get(0);
        }else{
            return null;
        }
    }
    public String saveByFileId(RdmsFileAuth fileAuth) {
        if(!ObjectUtils.isEmpty(fileAuth.getFileId())){
            RdmsFileAuth byFileId = this.getByFileId(fileAuth.getFileId());
            if(ObjectUtils.isEmpty(byFileId)){
                //insert
                return this.insert(fileAuth);
            }else{
                //update
                fileAuth.setId(byFileId.getId());
                return this.update(fileAuth);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        }
    }

    private String insert(RdmsFileAuth fileAuth) {
        if(ObjectUtils.isEmpty(fileAuth.getId())){  //当前端页面给出projectID时,将不为空
            fileAuth.setId(UuidUtil.getShortUuid());
        }
        RdmsFileAuth rdmsFileAuth = this.selectByPrimaryKey(fileAuth.getId());
        if(ObjectUtils.isEmpty(rdmsFileAuth)){
            fileAuth.setUpdateTime(new Date());
            fileAuth.setDeleted(0);
            rdmsFileAuthMapper.insert(fileAuth);
            return fileAuth.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsFileAuth fileAuth) {
        if(ObjectUtils.isEmpty(fileAuth.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsFileAuth rdmsFileAuth = this.selectByPrimaryKey(fileAuth.getId());
        if(ObjectUtils.isEmpty(rdmsFileAuth)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            fileAuth.setDeleted(0);
            fileAuth.setUpdateTime(new Date());
            rdmsFileAuthMapper.updateByPrimaryKey(fileAuth);
            return fileAuth.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsFileAuth fileAuth){
        fileAuth.setUpdateTime(new Date());
        rdmsFileAuthMapper.updateByPrimaryKeySelective(fileAuth);
        return fileAuth.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsFileAuth rdmsFileAuth = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsFileAuth)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsFileAuth.setDeleted(1);
            rdmsFileAuthMapper.updateByPrimaryKey(rdmsFileAuth);
        }
    }

}
