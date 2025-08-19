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
import com.course.server.dto.rdms.RdmsBossDto;
import com.course.server.dto.rdms.RdmsCustomerUserDto;
import com.course.server.dto.rdms.RdmsSysgmDto;
import com.course.server.dto.rdms.RdmsSystemManagerDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCustomerUserMapper;
import com.course.server.mapper.RdmsSystemManagerMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsSystemManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsSystemManagerService.class);

    @Resource
    private RdmsSystemManagerMapper rdmsSystemManagerMapper;
    @Resource
    private RdmsCustomerUserMapper rdmsCustomerUserMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Autowired
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;

    public void list(PageDto<RdmsSystemManagerDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsSystemManagerExample rdmsSystemManagerExample = new RdmsSystemManagerExample();
        rdmsSystemManagerExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(rdmsSystemManagerExample);

        PageInfo<RdmsSystemManager> pageInfo = new PageInfo<>(rdmsSystemManagers);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsSystemManagerDto> rdmsSystemManagerDtos = CopyUtil.copyList(rdmsSystemManagers, RdmsSystemManagerDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsSystemManagerDto systemManagerDto : rdmsSystemManagerDtos){
            systemManagerDto.setCreateTimeStr(sdf.format(systemManagerDto.getCreateTime()));
        }
        pageDto.setList(rdmsSystemManagerDtos);
    }

    /**
     * 分页查询里程碑列表
     * @Param
     * @return
     */
    @Transactional
    public List<String> listByCustomerId(String customerId){
        RdmsSystemManagerExample rdmsSystemManagerExample = new RdmsSystemManagerExample();
        rdmsSystemManagerExample.createCriteria()
                .andCustomerIdEqualTo(customerId).
                andDeletedEqualTo(0);
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(rdmsSystemManagerExample);
        if(rdmsSystemManagers.isEmpty()){
            RdmsSystemManager rdmsSystemManager = new RdmsSystemManager();
            rdmsSystemManager.setId(UuidUtil.getShortUuid());
            rdmsSystemManager.setCustomerId(customerId);
            RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
            List<String> systemManagerIds = new ArrayList<>();
            systemManagerIds.add(bossByCustomerId.getBossId());
            String jsonString = JSON.toJSONString(systemManagerIds);
            rdmsSystemManager.setSystemManagerIdListStr(jsonString);
            rdmsSystemManager.setCreateTime(new Date());
            rdmsSystemManager.setDeleted(0);
            this.insert(rdmsSystemManager);
            rdmsSystemManagers= rdmsSystemManagerMapper.selectByExample(rdmsSystemManagerExample);
        }
        if(rdmsSystemManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        List<String> systemManagerIds = new ArrayList<>();
        if(!rdmsSystemManagers.isEmpty()){
            systemManagerIds = JSON.parseArray(rdmsSystemManagers.get(0).getSystemManagerIdListStr(), String.class);
        }
        //超级管理员是默认的系统工程师
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        RdmsCustomerUser superAdmin = rdmsCustomerUserService.getCustomerUserByCustomerIdAndLoginName(rdmsCustomer.getId(), rdmsCustomer.getContactPhone());
        if(!systemManagerIds.contains(superAdmin.getId())){
            systemManagerIds.add(superAdmin.getId());

            RdmsSystemManager rdmsSystemManager = new RdmsSystemManager();
            if(!ObjectUtils.isEmpty(rdmsSystemManagers)){
                rdmsSystemManager.setId(rdmsSystemManagers.get(0).getId());
            }
            rdmsSystemManager.setCustomerId(customerId);
            rdmsSystemManager.setSystemManagerIdListStr(JSON.toJSONString(systemManagerIds));
            this.save(rdmsSystemManager);
        }
        //Boss是默认的系统工程师
        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
        if(!systemManagerIds.contains(bossByCustomerId.getBossId())){
            systemManagerIds.add(bossByCustomerId.getBossId());

            RdmsSystemManager rdmsSystemManager = new RdmsSystemManager();
            rdmsSystemManager.setId(rdmsSystemManagers.get(0).getId());
            rdmsSystemManager.setCustomerId(customerId);
            rdmsSystemManager.setSystemManagerIdListStr(JSON.toJSONString(systemManagerIds));
            this.save(rdmsSystemManager);
        }
        //系统工程总监是默认的系统工程师
        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(customerId);
        if(!ObjectUtils.isEmpty(sysgmByCustomerId) && !systemManagerIds.contains(sysgmByCustomerId.getSysgmId())){
            systemManagerIds.add(sysgmByCustomerId.getSysgmId());

            RdmsSystemManager rdmsSystemManager = new RdmsSystemManager();
            rdmsSystemManager.setId(rdmsSystemManagers.get(0).getId());
            rdmsSystemManager.setCustomerId(customerId);
            rdmsSystemManager.setSystemManagerIdListStr(JSON.toJSONString(systemManagerIds));
            this.save(rdmsSystemManager);
        }
        List<String> stringList = systemManagerIds.stream().distinct().collect(Collectors.toList());
        return stringList;
    }

    /**
     * 分页查询里程碑列表
     * @Param
     * @return
     */
    @Transactional
    public List<RdmsCustomerUserDto> getListByCustomerId(String customerId){
        List<RdmsCustomerUser> customerUsers = new ArrayList<>();

        RdmsSystemManagerExample rdmsSystemManagerExample = new RdmsSystemManagerExample();
        rdmsSystemManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(rdmsSystemManagerExample);
        if(rdmsSystemManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(rdmsSystemManagers.size()>0){
            List<String> systemManagerIds = JSON.parseArray(rdmsSystemManagers.get(0).getSystemManagerIdListStr(), String.class);
            for(String id : systemManagerIds){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(id);
                customerUsers.add(rdmsCustomerUser);
            }
            List<RdmsCustomerUserDto> rdmsCustomerUserDtos = CopyUtil.copyList(customerUsers, RdmsCustomerUserDto.class);
            return rdmsCustomerUserDtos;
        }
        return null;
    }

    /**
     * 分页查询里程碑列表
     * @Param
     * @return
     */
    @Transactional
    public List<String> getIdListByCustomerId(String customerId){
        List<RdmsCustomerUser> customerUsers = new ArrayList<>();

        RdmsSystemManagerExample rdmsSystemManagerExample = new RdmsSystemManagerExample();
        rdmsSystemManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(rdmsSystemManagerExample);
        if(rdmsSystemManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(rdmsSystemManagers.size()>0){
            List<String> systemManagerIds = JSON.parseArray(rdmsSystemManagers.get(0).getSystemManagerIdListStr(), String.class);
            return systemManagerIds;
        }
        return null;
    }


    @Transactional
    public List<RdmsCustomerUser> listSystemManagerByCustomerId(String customerId){
        RdmsSystemManagerExample rdmsSystemManagerExample = new RdmsSystemManagerExample();
        rdmsSystemManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(rdmsSystemManagerExample);
        if(rdmsSystemManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(rdmsSystemManagers.size()>0){
            List<String> systemManagerIds = JSON.parseArray(rdmsSystemManagers.get(0).getSystemManagerIdListStr(), String.class);
            List<RdmsCustomerUser> rdmsCustomerUserList = new ArrayList<>();
            for(String id : systemManagerIds){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserMapper.selectByPrimaryKey(id);
                if(rdmsCustomerUser != null && rdmsCustomerUser.getDeleted() == 0){
                    rdmsCustomerUserList.add(rdmsCustomerUser);
                }
            }
            return rdmsCustomerUserList;
        }
        return null;
    }

    public RdmsSystemManager selectByPrimaryKey(String id){
        return rdmsSystemManagerMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsSystemManager rdmsSystemManager) {
        if(rdmsSystemManager.getCreateTime()==null){
            rdmsSystemManager.setCreateTime(new Date());
        }
        rdmsSystemManager.setDeleted(0);

        RdmsSystemManagerExample systemManagerExample = new RdmsSystemManagerExample();
        systemManagerExample.createCriteria().andCustomerIdEqualTo(rdmsSystemManager.getCustomerId());
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(systemManagerExample);
        if(rdmsSystemManagers.size()>0){
            return this.update(rdmsSystemManager);
        }else{
            return this.insert(rdmsSystemManager);
        }

    }

    /**
     * 新增
     */
    private String insert(RdmsSystemManager rdmsSystemManager) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsSystemManager.getId())){  //当前端页面给出projectID时,将不为空
            rdmsSystemManager.setId(UuidUtil.getShortUuid());
        }
        rdmsSystemManager.setCreateTime(new Date());
        RdmsSystemManagerExample systemManagerExample = new RdmsSystemManagerExample();
        systemManagerExample.createCriteria().andIdEqualTo(rdmsSystemManager.getId());
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(systemManagerExample);
        if(rdmsSystemManagers.size()>0){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsSystemManagerMapper.insert(rdmsSystemManager);
            return rdmsSystemManager.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsSystemManager rdmsSystemManager) {
        RdmsSystemManagerExample systemManagerExample = new RdmsSystemManagerExample();
        systemManagerExample.createCriteria().andCustomerIdEqualTo(rdmsSystemManager.getCustomerId());
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(systemManagerExample);
        if(rdmsSystemManagers.size()>0){
            rdmsSystemManager.setId(rdmsSystemManagers.get(0).getId());
            rdmsSystemManagerMapper.updateByPrimaryKey(rdmsSystemManager);
            return rdmsSystemManager.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsSystemManagerExample systemManagerExample = new RdmsSystemManagerExample();
        systemManagerExample.createCriteria().andIdEqualTo(id);
        List<RdmsSystemManager> rdmsSystemManagers = rdmsSystemManagerMapper.selectByExample(systemManagerExample);
        if(rdmsSystemManagers.size()>0){
            for(RdmsSystemManager rdmsSystemManager : rdmsSystemManagers){
                rdmsSystemManagerMapper.updateByPrimaryKey(rdmsSystemManager);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
