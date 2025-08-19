/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCustomer;
import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsProductManager;
import com.course.server.domain.RdmsProductManagerExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsBossDto;
import com.course.server.dto.rdms.RdmsCustomerUserDto;
import com.course.server.dto.rdms.RdmsProductManagerDto;
import com.course.server.dto.rdms.RdmsProjectDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCustomerUserMapper;
import com.course.server.mapper.RdmsProductManagerMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RdmsProductManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsProductManagerService.class);

    @Resource
    private RdmsProductManagerMapper rdmsProductManagerMapper;
    @Resource
    private RdmsCustomerUserMapper rdmsCustomerUserMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Autowired
    private RdmsBossService rdmsBossService;

    public void list(PageDto<RdmsProductManagerDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProductManagerExample rdmsProductManagerExample = new RdmsProductManagerExample();
        rdmsProductManagerExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(rdmsProductManagerExample);

        PageInfo<RdmsProductManager> pageInfo = new PageInfo<>(rdmsProductManagers);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsProductManagerDto> rdmsProductManagerDtos = CopyUtil.copyList(rdmsProductManagers, RdmsProductManagerDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsProductManagerDto productManagerDto : rdmsProductManagerDtos){
            productManagerDto.setCreateTimeStr(sdf.format(productManagerDto.getCreateTime()));
        }

        pageDto.setList(rdmsProductManagerDtos);
    }

    /**
     * 分页查询里程碑列表
     * @Param
     * @return
     */
    @Transactional
    public List<String> listByCustomerId(String customerId){
        RdmsProductManagerExample rdmsProductManagerExample = new RdmsProductManagerExample();
        rdmsProductManagerExample.createCriteria()
                .andCustomerIdEqualTo(customerId).
                andDeletedEqualTo(0);
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(rdmsProductManagerExample);
        if(rdmsProductManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        List<String> productManagerIds = new ArrayList<>();
        if(!rdmsProductManagers.isEmpty()){
            productManagerIds = JSON.parseArray(rdmsProductManagers.get(0).getProductManagerIdListStr(), String.class);
        }
        //添加超级管理员
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        RdmsCustomerUser superAdmin = rdmsCustomerUserService.getCustomerUserByCustomerIdAndLoginName(rdmsCustomer.getId(), rdmsCustomer.getContactPhone());
        productManagerIds.add(superAdmin.getId());

        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
        productManagerIds.add(bossByCustomerId.getBossId());

        //添加已经是产品经理的人
        List<RdmsProjectDto> projectList = rdmsProjectService.getCompleteProjectListByCustomerId(customerId);
        if(!CollectionUtils.isEmpty(projectList)){
            List<RdmsProjectDto> collect = projectList.stream().filter(item -> !item.getProductManagerId().isEmpty()).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)){
                List<String> pmIdList = collect.stream().map(RdmsProjectDto::getProductManagerId).collect(Collectors.toList());
                productManagerIds.addAll(pmIdList);
            }
        }

        List<String> stringList = productManagerIds.stream().distinct().collect(Collectors.toList());
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

        RdmsProductManagerExample rdmsProductManagerExample = new RdmsProductManagerExample();
        rdmsProductManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(rdmsProductManagerExample);
        if(rdmsProductManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(rdmsProductManagers.size()>0){
            List<String> productManagerIds = JSON.parseArray(rdmsProductManagers.get(0).getProductManagerIdListStr(), String.class);
            for(String id : productManagerIds){
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

        RdmsProductManagerExample rdmsProductManagerExample = new RdmsProductManagerExample();
        rdmsProductManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(rdmsProductManagerExample);
        if(rdmsProductManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(rdmsProductManagers.size()>0){
            List<String> productManagerIds = JSON.parseArray(rdmsProductManagers.get(0).getProductManagerIdListStr(), String.class);
            return productManagerIds;
        }
        return null;
    }


    @Transactional
    public List<RdmsCustomerUser> listProductManagerByCustomerId(String customerId){
        RdmsProductManagerExample rdmsProductManagerExample = new RdmsProductManagerExample();
        rdmsProductManagerExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(rdmsProductManagerExample);
        if(rdmsProductManagers.size()>1){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
        if(rdmsProductManagers.size()>0){
            List<String> productManagerIds = JSON.parseArray(rdmsProductManagers.get(0).getProductManagerIdListStr(), String.class);
            List<RdmsCustomerUser> rdmsCustomerUserList = new ArrayList<>();
            for(String id : productManagerIds){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserMapper.selectByPrimaryKey(id);
                if(rdmsCustomerUser != null && rdmsCustomerUser.getDeleted() == 0){
                    rdmsCustomerUserList.add(rdmsCustomerUser);
                }
            }
            return rdmsCustomerUserList;
        }
        return null;
    }

    public RdmsProductManager selectByPrimaryKey(String id){
        return rdmsProductManagerMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsProductManager rdmsProductManager) {
        if(rdmsProductManager.getCreateTime()==null){
            rdmsProductManager.setCreateTime(new Date());
        }
        rdmsProductManager.setDeleted(0);

        RdmsProductManagerExample productManagerExample = new RdmsProductManagerExample();
        productManagerExample.createCriteria().andCustomerIdEqualTo(rdmsProductManager.getCustomerId());
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(productManagerExample);
        if(rdmsProductManagers.size()>0){
            return this.update(rdmsProductManager);
        }else{
            return this.insert(rdmsProductManager);
        }

    }

    /**
     * 新增
     */
    private String insert(RdmsProductManager rdmsProductManager) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsProductManager.getId())){  //当前端页面给出projectID时,将不为空
            rdmsProductManager.setId(UuidUtil.getShortUuid());
        }
        rdmsProductManager.setCreateTime(new Date());
        RdmsProductManagerExample productManagerExample = new RdmsProductManagerExample();
        productManagerExample.createCriteria().andIdEqualTo(rdmsProductManager.getId());
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(productManagerExample);
        if(rdmsProductManagers.size()>0){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsProductManagerMapper.insert(rdmsProductManager);
            return rdmsProductManager.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsProductManager rdmsProductManager) {
        RdmsProductManagerExample productManagerExample = new RdmsProductManagerExample();
        productManagerExample.createCriteria().andCustomerIdEqualTo(rdmsProductManager.getCustomerId());
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(productManagerExample);
        if(rdmsProductManagers.size()>0){
            rdmsProductManager.setId(rdmsProductManagers.get(0).getId());
            rdmsProductManagerMapper.updateByPrimaryKey(rdmsProductManager);
            return rdmsProductManager.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProductManagerExample productManagerExample = new RdmsProductManagerExample();
        productManagerExample.createCriteria().andIdEqualTo(id);
        List<RdmsProductManager> rdmsProductManagers = rdmsProductManagerMapper.selectByExample(productManagerExample);
        if(rdmsProductManagers.size()>0){
            for(RdmsProductManager rdmsProductManager : rdmsProductManagers){
                rdmsProductManagerMapper.updateByPrimaryKey(rdmsProductManager);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
