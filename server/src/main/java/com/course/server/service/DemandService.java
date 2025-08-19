/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class DemandService {
    private static final Logger LOG = LoggerFactory.getLogger(DemandService.class);
    @Resource
    private DemandMapper demandMapper;

    /**
     * 保存
     */
    @Transactional
    public void save(DemandDto demandDto) {
        Demand demand = CopyUtil.copy(demandDto, Demand.class);
        demand.setId(UuidUtil.getShortUuid());
        this.insert(demand);
    }

    /**
     * 新增
     */
    private void insert(Demand demand) {
        Date now = new Date();
        demand.setCreateTime(now);
        if(demand.getId().isEmpty()){
            demand.setId(UuidUtil.getShortUuid());
        }
        demandMapper.insert(demand);
    }
}
