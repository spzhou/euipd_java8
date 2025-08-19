/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.enums.rdms.CustomerConfigStatusEnum;
import com.course.server.enums.rdms.CustomerStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.mapper.rdms.MyCustomerMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.course.server.constants.Constants.KEY;
import static com.course.server.enums.rdms.CustomerStatusEnum.getCustomerEnumByStatus;

@Service
public class RdmsCustomerConfigService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerConfigService.class);

    @Resource
    private RdmsCustomerConfigMapper rdmsCustomerConfigMapper;


    public RdmsCustomerConfig getCustomerConfig(String customerId){
        RdmsCustomerConfigExample customerConfigExample = new RdmsCustomerConfigExample();
        customerConfigExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsCustomerConfig> rdmsCustomerConfigs = rdmsCustomerConfigMapper.selectByExample(customerConfigExample);
        //TODO 判断如果用户配置文件不存在,则使用系统默认的OSS
        if(CollectionUtils.isEmpty(rdmsCustomerConfigs)){
            return null;
        }else{
            return rdmsCustomerConfigs.get(0);
        }

    }
    /**
     * 列表查询
     */
    public void list(PageDto<RdmsCustomerConfigDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerConfigExample customerConfigExample = new RdmsCustomerConfigExample();
        customerConfigExample.createCriteria().andDeletedEqualTo(0);
        List<RdmsCustomerConfig> rdmsCustomerConfigs = rdmsCustomerConfigMapper.selectByExample(customerConfigExample);
        PageInfo<RdmsCustomerConfig> pageInfo = new PageInfo<>(rdmsCustomerConfigs);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsCustomerConfigDto> rdmsCustomerConfigDtos = CopyUtil.copyList(rdmsCustomerConfigs, RdmsCustomerConfigDto.class);
        pageDto.setList(rdmsCustomerConfigDtos);
    }

    /**
     * 保存
     */
    @Transactional
    public void save(RdmsCustomerConfig customerConfig) {
        if(customerConfig.getId() != null){
            RdmsCustomerConfig rdmsCustomerConfig = rdmsCustomerConfigMapper.selectByPrimaryKey(customerConfig.getId());
            if(rdmsCustomerConfig != null){
                this.update(customerConfig);
            }else {
                this.insert(customerConfig);
            }
        }
    }

    private String insert(RdmsCustomerConfig rdmsCustomerConfig) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsCustomerConfig.getId())){  //当前端页面给出projectID时,将不为空
            rdmsCustomerConfig.setId(UuidUtil.getShortUuid());
        }
        RdmsCustomerConfigExample rdmsCustomerConfigExample = new RdmsCustomerConfigExample();
        rdmsCustomerConfigExample.createCriteria().andCustomerIdEqualTo(rdmsCustomerConfig.getCustomerId());
        List<RdmsCustomerConfig> rdmsCustomerConfigs = rdmsCustomerConfigMapper.selectByExample(rdmsCustomerConfigExample);
        if(! CollectionUtils.isEmpty(rdmsCustomerConfigs)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsCustomerConfig.setStatus(CustomerConfigStatusEnum.USED.getStatus());
            rdmsCustomerConfigMapper.insert(rdmsCustomerConfig);
            return rdmsCustomerConfig.getId();
        }
    }

    public String update(RdmsCustomerConfig rdmsCustomerConfig) {
        RdmsCustomerConfigExample rdmsCustomerConfigExample = new RdmsCustomerConfigExample();
        rdmsCustomerConfigExample.createCriteria().andIdEqualTo(rdmsCustomerConfig.getId());
        List<RdmsCustomerConfig> productCharacters = rdmsCustomerConfigMapper.selectByExample(rdmsCustomerConfigExample);
        if(! CollectionUtils.isEmpty(productCharacters)){
            rdmsCustomerConfigMapper.updateByPrimaryKey(rdmsCustomerConfig);
            return rdmsCustomerConfig.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCustomerConfigExample rdmsCustomerConfigExample = new RdmsCustomerConfigExample();
        rdmsCustomerConfigExample.createCriteria().andIdEqualTo(id);
        List<RdmsCustomerConfig> productCharacters = rdmsCustomerConfigMapper.selectByExample(rdmsCustomerConfigExample);
        if(! CollectionUtils.isEmpty(productCharacters)){
            RdmsCustomerConfig productCharacter = productCharacters.get(0);
            productCharacter.setDeleted(1);
            rdmsCustomerConfigMapper.updateByPrimaryKey(productCharacter);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }
}
