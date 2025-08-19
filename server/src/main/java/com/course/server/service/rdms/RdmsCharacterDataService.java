/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.course.server.domain.*;
import com.course.server.domain.rdms.MyCharacter;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.mapper.rdms.MyCharacterMapper;
import com.course.server.service.calculate.CalBudgetService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsCharacterDataService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCharacterDataService.class);

    @Resource
    private RdmsCharacterDataMapper rdmsCharacterDataMapper;

    public RdmsCharacterData selectByPrimaryKey(String id){
        return rdmsCharacterDataMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsCharacterData cData) {
        if(ObjectUtils.isEmpty(cData.getId())){
            return this.insert(cData);
        }else{
            RdmsCharacterData rdmsCharacterData = this.selectByPrimaryKey(cData.getId());
            if(ObjectUtils.isEmpty(rdmsCharacterData)){
                return this.insert(cData);
            }else{
                return this.update(cData);
            }
        }
    }

    private String insert(RdmsCharacterData cData) {
        if(org.springframework.util.ObjectUtils.isEmpty(cData.getId())){  //当前端页面给出projectID时,将不为空
            cData.setId(UuidUtil.getShortUuid());
        }
        RdmsCharacterData rdmsCharacterData = this.selectByPrimaryKey(cData.getId());
        if(ObjectUtils.isEmpty(rdmsCharacterData)){
            cData.setCreateTime(new Date());
            cData.setUpdateTime(new Date());
            rdmsCharacterDataMapper.insert(cData);
            return cData.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsCharacterData cData) {
        if(ObjectUtils.isEmpty(cData.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsCharacterData rdmsCharacterData = this.selectByPrimaryKey(cData.getId());
        if(ObjectUtils.isEmpty(rdmsCharacterData)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsCharacterDataMapper.updateByPrimaryKey(cData);
            return cData.getId();
        }
    }
}
