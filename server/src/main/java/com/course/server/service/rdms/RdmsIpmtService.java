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
import com.course.server.dto.rdms.RdmsIpmtDto;
import com.course.server.dto.rdms.RdmsProjectDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCustomerUserMapper;
import com.course.server.mapper.RdmsIpmtMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsIpmtService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsIpmtService.class);

    @Resource
    private RdmsIpmtMapper rdmsIpmtMapper;
    @Resource
    private RdmsCustomerUserMapper rdmsCustomerUserMapper;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsProjectService rdmsProjectService;

    public void list(PageDto<RdmsIpmtDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsIpmtExample rdmsIpmtExample = new RdmsIpmtExample();
        rdmsIpmtExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsIpmt> rdmsIpmts = rdmsIpmtMapper.selectByExample(rdmsIpmtExample);

        PageInfo<RdmsIpmt> pageInfo = new PageInfo<>(rdmsIpmts);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsIpmtDto> rdmsIpmtDtos = CopyUtil.copyList(rdmsIpmts, RdmsIpmtDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsIpmtDto ipmtDto : rdmsIpmtDtos){
            ipmtDto.setCreateTimeStr(sdf.format(ipmtDto.getCreateTime()));
        }
        pageDto.setList(rdmsIpmtDtos);
    }

    /**
     */
    @Transactional
    public List<String> listByCustomerId(String customerId){
        RdmsIpmtExample rdmsIpmtExample = new RdmsIpmtExample();
        rdmsIpmtExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsIpmt> rdmsIpmts = rdmsIpmtMapper.selectByExample(rdmsIpmtExample);
        if(rdmsIpmts.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(!rdmsIpmts.isEmpty()){
            List<String> ipmtIdList = JSON.parseArray(rdmsIpmts.get(0).getIpmtIdList(), String.class);
            //添加超级管理员
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
            RdmsCustomerUser superAdmin = rdmsCustomerUserService.getCustomerUserByCustomerIdAndLoginName(rdmsCustomer.getId(), rdmsCustomer.getContactPhone());
            ipmtIdList.add(superAdmin.getId());

            //添加已经是监管委员的人
            List<RdmsProjectDto> projectList = rdmsProjectService.getCompleteProjectListByCustomerId(customerId);
            if(!CollectionUtils.isEmpty(projectList)){
                List<String> superIdList = projectList.stream().map(RdmsProjectDto::getSupervise).collect(Collectors.toList());
                ipmtIdList.addAll(superIdList);
            }

            List<String> stringList = ipmtIdList.stream().distinct().collect(Collectors.toList());
            return stringList;
        }
        return null;
    }

    @Transactional
    public List<RdmsCustomerUser> listIpmtByCustomerId(String customerId){
        RdmsIpmtExample rdmsIpmtExample = new RdmsIpmtExample();
        rdmsIpmtExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsIpmt> rdmsIpmts = rdmsIpmtMapper.selectByExample(rdmsIpmtExample);
        if(rdmsIpmts.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(!rdmsIpmts.isEmpty()){
            List<String> ipmtIds = JSON.parseArray(rdmsIpmts.get(0).getIpmtIdList(), String.class);
            List<RdmsCustomerUser> rdmsCustomerUsers = new ArrayList<>();
            for(String ipmtId : ipmtIds){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserMapper.selectByPrimaryKey(ipmtId);
                if(rdmsCustomerUser != null && rdmsCustomerUser.getDeleted() == 0){
                    rdmsCustomerUsers.add(rdmsCustomerUser);
                }
            }
            return rdmsCustomerUsers;
        }
        return null;
    }

    /**
     * 保存
     */
    public String save(RdmsIpmt rdmsIpmt) {
        if(rdmsIpmt.getCreateTime()==null){
            rdmsIpmt.setCreateTime(new Date());
        }
        rdmsIpmt.setDeleted(0);

        RdmsIpmtExample ipmtExample = new RdmsIpmtExample();
        ipmtExample.createCriteria().andCustomerIdEqualTo(rdmsIpmt.getCustomerId());
        List<RdmsIpmt> rdmsIpmts = rdmsIpmtMapper.selectByExample(ipmtExample);
        if(!rdmsIpmts.isEmpty()){
            return this.update(rdmsIpmt);
        }else{
            return this.insert(rdmsIpmt);
        }

    }

    /**
     * 新增
     */
    private String insert(RdmsIpmt rdmsIpmt) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsIpmt.getId())){  //当前端页面给出projectID时,将不为空
            rdmsIpmt.setId(UuidUtil.getShortUuid());
        }
        RdmsIpmtExample ipmtExample = new RdmsIpmtExample();
        ipmtExample.createCriteria().andIdEqualTo(rdmsIpmt.getId());
        List<RdmsIpmt> rdmsIpmts = rdmsIpmtMapper.selectByExample(ipmtExample);
        if(!rdmsIpmts.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsIpmtMapper.insert(rdmsIpmt);
            return rdmsIpmt.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsIpmt rdmsIpmt) {
        RdmsIpmtExample ipmtExample = new RdmsIpmtExample();
        ipmtExample.createCriteria().andCustomerIdEqualTo(rdmsIpmt.getCustomerId());
        List<RdmsIpmt> rdmsIpmts = rdmsIpmtMapper.selectByExample(ipmtExample);
        if(!rdmsIpmts.isEmpty()){
            rdmsIpmt.setId(rdmsIpmts.get(0).getId());
            rdmsIpmtMapper.updateByPrimaryKey(rdmsIpmt);
            return rdmsIpmt.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsIpmt rdmsIpmts = rdmsIpmtMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsIpmts)){
            rdmsIpmts.setDeleted(1);
            this.update(rdmsIpmts);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
