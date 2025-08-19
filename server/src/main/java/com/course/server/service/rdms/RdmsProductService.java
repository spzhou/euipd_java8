/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.domain.RdmsProduct;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsProductDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsProductMapper;
import com.course.server.mapper.RdmsProjectTaskMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsProductService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsProductService.class);

    @Resource
    private RdmsProductMapper rdmsProductMapper;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;

    public void list(PageDto<RdmsProductDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProductExample productExample = new RdmsProductExample();
        List<RdmsProduct> rdmsProducts = rdmsProductMapper.selectByExample(productExample);

        PageInfo<RdmsProduct> pageInfo = new PageInfo<>(rdmsProducts);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsProductDto> rdmsProductDtos = CopyUtil.copyList(rdmsProducts, RdmsProductDto.class);
        if(!CollectionUtils.isEmpty(rdmsProductDtos)){
            for(RdmsProductDto productDto: rdmsProductDtos){
                RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(productDto.getCustomerId());
                productDto.setCustomerName(customer.getCustomerName());

                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(productDto.getProjectId());
                productDto.setProjectName(rdmsProject.getProjectName());

                productDto.setProjectManagerId(rdmsProject.getProjectManagerId());
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProjectManagerId());
                productDto.setProjectManagerName(customerUser.getTrueName());

                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(productDto.getProductManagerId());
                productDto.setProductManagerName(customerUser1.getTrueName());

                RdmsCustomerUser customerUser2 = rdmsCustomerUserService.selectByPrimaryKey(productDto.getIpmtId());
                productDto.setIpmtSuperviseName(customerUser2.getTrueName());
            }
        }


        pageDto.setList(rdmsProductDtos);
    }

    public RdmsProduct selectByPrimaryKey(String id){
        return rdmsProductMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsProduct rdmsProduct) {
        if(ObjectUtils.isEmpty(rdmsProduct.getId())){
            return this.insert(rdmsProduct);
        }else{
            RdmsProduct product = this.selectByPrimaryKey(rdmsProduct.getId());
            if(ObjectUtils.isEmpty(product)){
                return this.insert(rdmsProduct);
            }else{
                return this.update(rdmsProduct);
            }
        }
    }

    private String insert(RdmsProduct rdmsProduct) {
        if(ObjectUtils.isEmpty(rdmsProduct.getId())){  //当前端页面给出projectID时,将不为空
            rdmsProduct.setId(UuidUtil.getShortUuid());
        }
        RdmsProduct product = this.selectByPrimaryKey(rdmsProduct.getId());
        if(ObjectUtils.isEmpty(product)){
            rdmsProduct.setDeleted(0);
            rdmsProduct.setCreateTime(new Date());
            rdmsProductMapper.insert(rdmsProduct);
            return rdmsProduct.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsProduct rdmsProduct) {
        if(ObjectUtils.isEmpty(rdmsProduct.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsProduct product = this.selectByPrimaryKey(rdmsProduct.getId());
        if(ObjectUtils.isEmpty(product)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsProduct.setDeleted(0);
            rdmsProductMapper.updateByPrimaryKey(rdmsProduct);
            return rdmsProduct.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsProduct rdmsProduct){
        rdmsProductMapper.updateByPrimaryKeySelective(rdmsProduct);
        return rdmsProduct.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProduct rdmsProduct = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsProduct)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsProduct.setDeleted(1);
            rdmsProductMapper.updateByPrimaryKey(rdmsProduct);
        }
    }

}
