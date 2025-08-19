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
import com.course.server.dto.rdms.RdmsProjectDto;
import com.course.server.dto.rdms.RdmsProjectManagerDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCustomerUserMapper;
import com.course.server.mapper.RdmsProjectManagerMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsProjectManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsProjectManagerService.class);

    @Resource
    private RdmsProjectManagerMapper rdmsProjectManagerMapper;
    @Resource
    private RdmsCustomerUserMapper rdmsCustomerUserMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsSubprojectService RdmsSubprojectService;

    public void list(PageDto<RdmsProjectManagerDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectManagerExample rdmsProjectManagerExample = new RdmsProjectManagerExample();
        rdmsProjectManagerExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(rdmsProjectManagerExample);

        PageInfo<RdmsProjectManager> pageInfo = new PageInfo<>(rdmsProjectManagers);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsProjectManagerDto> rdmsProjectManagerDtos = CopyUtil.copyList(rdmsProjectManagers, RdmsProjectManagerDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsProjectManagerDto ipmtDto : rdmsProjectManagerDtos){
            ipmtDto.setCreateTimeStr(sdf.format(ipmtDto.getCreateTime()));
        }

        pageDto.setList(rdmsProjectManagerDtos);
    }

    /**
     * 分页查询里程碑列表
     * @Param pageDto.KeyWord is ProjectId
     * @return
     */
    @Transactional
    public List<String> listByCustomerId(String customerId){
        RdmsProjectManagerExample rdmsProjectManagerExample = new RdmsProjectManagerExample();
        rdmsProjectManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(rdmsProjectManagerExample);
        if(rdmsProjectManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(!rdmsProjectManagers.isEmpty()){
            List<String> projectManagerIds = JSON.parseArray(rdmsProjectManagers.get(0).getProjectManagerIdListStr(), String.class);

            //添加超级管理员
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
            RdmsCustomerUser superAdmin = rdmsCustomerUserService.getCustomerUserByCustomerIdAndLoginName(rdmsCustomer.getId(), rdmsCustomer.getContactPhone());
            projectManagerIds.add(superAdmin.getId());

            //添加已经是项目经理的人
            List<RdmsProjectSubproject> subprojectList = RdmsSubprojectService.getSubprojectListByCustomerId(customerId);
            if(!CollectionUtils.isEmpty(subprojectList)){
                List<RdmsProjectSubproject> collect = subprojectList.stream().filter(item -> !item.getProjectManagerId().isEmpty()).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(collect)){
                    List<String> pjmIdList = collect.stream().map(RdmsProjectSubproject::getProjectManagerId).collect(Collectors.toList());
                    projectManagerIds.addAll(pjmIdList);
                }
            }

            List<String> stringList = projectManagerIds.stream().distinct().collect(Collectors.toList());
            return stringList;
        }
        return null;
    }

    @Transactional
    public List<RdmsCustomerUser> getProjectManagerListByCustomerId(String customerId){
        RdmsProjectManagerExample rdmsProjectManagerExample = new RdmsProjectManagerExample();
        rdmsProjectManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(rdmsProjectManagerExample);
        if(!rdmsProjectManagers.isEmpty()){
            List<String> projectManagerIds = JSON.parseArray(rdmsProjectManagers.get(0).getProjectManagerIdListStr(), String.class);
            List<RdmsCustomerUser> rdmsCustomerUserList = new ArrayList<>();
            for(String id : projectManagerIds){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserMapper.selectByPrimaryKey(id);
                if(rdmsCustomerUser != null && rdmsCustomerUser.getDeleted() == 0){
                    rdmsCustomerUserList.add(rdmsCustomerUser);
                }
            }
            return rdmsCustomerUserList;
        }
        return null;
    }
    @Transactional
    public List<String> getIdListByCustomerId(String customerId){
        RdmsProjectManagerExample rdmsProjectManagerExample = new RdmsProjectManagerExample();
        rdmsProjectManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(rdmsProjectManagerExample);
        if(!rdmsProjectManagers.isEmpty()){
            List<String> projectManagerIds = JSON.parseArray(rdmsProjectManagers.get(0).getProjectManagerIdListStr(), String.class);
            return projectManagerIds;
        }
        return null;
    }
    /**
     * 保存
     */
    public String save(RdmsProjectManager rdmsProjectManager) {
        if(rdmsProjectManager.getCreateTime()==null){
            rdmsProjectManager.setCreateTime(new Date());
        }
        rdmsProjectManager.setDeleted(0);

        RdmsProjectManagerExample projectManagerExample = new RdmsProjectManagerExample();
        projectManagerExample.createCriteria().andCustomerIdEqualTo(rdmsProjectManager.getCustomerId());
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(projectManagerExample);
        if(rdmsProjectManagers.size()>0){
            return this.update(rdmsProjectManager);
        }else{
            return this.insert(rdmsProjectManager);
        }

    }

    /**
     * 新增
     */
    private String insert(RdmsProjectManager rdmsProjectManager) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsProjectManager.getId())){  //当前端页面给出projectID时,将不为空
            rdmsProjectManager.setId(UuidUtil.getShortUuid());
        }
        rdmsProjectManager.setCreateTime(new Date());
        RdmsProjectManagerExample projectManagerExample = new RdmsProjectManagerExample();
        projectManagerExample.createCriteria().andIdEqualTo(rdmsProjectManager.getId());
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(projectManagerExample);
        if(rdmsProjectManagers.size()>0){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsProjectManagerMapper.insert(rdmsProjectManager);
            return rdmsProjectManager.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsProjectManager rdmsProjectManager) {
        RdmsProjectManagerExample projectManagerExample = new RdmsProjectManagerExample();
        projectManagerExample.createCriteria().andCustomerIdEqualTo(rdmsProjectManager.getCustomerId());
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(projectManagerExample);
        if(rdmsProjectManagers.size()>0){
            rdmsProjectManager.setId(rdmsProjectManagers.get(0).getId());
            rdmsProjectManagerMapper.updateByPrimaryKey(rdmsProjectManager);
            return rdmsProjectManager.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProjectManagerExample projectManagerExample = new RdmsProjectManagerExample();
        projectManagerExample.createCriteria().andIdEqualTo(id);
        List<RdmsProjectManager> rdmsProjectManagers = rdmsProjectManagerMapper.selectByExample(projectManagerExample);
        if(rdmsProjectManagers.size()>0){
            for(RdmsProjectManager rdmsProjectManager : rdmsProjectManagers){
                rdmsProjectManagerMapper.updateByPrimaryKey(rdmsProjectManager);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
