/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.Demand;
import com.course.server.domain.Sales;
import com.course.server.dto.DemandDto;
import com.course.server.dto.SalesDto;
import com.course.server.mapper.DemandMapper;
import com.course.server.mapper.SalesMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SalesService {
    private static final Logger LOG = LoggerFactory.getLogger(SalesService.class);
    @Resource
    private SalesMapper salesMapper;

    /**
     * 保存
     */
    @Transactional
    public void save(SalesDto salesDto) {
        Sales sales = CopyUtil.copy(salesDto, Sales.class);
        sales.setId(UuidUtil.getShortUuid());
        this.insert(sales);
    }

    /**
     * 新增
     */
    private void insert(Sales sales) {
        Date now = new Date();
        sales.setCreateTime(now);
        if(sales.getId().isEmpty()){
            sales.setId(UuidUtil.getShortUuid());
        }
        salesMapper.insert(sales);
    }
}
