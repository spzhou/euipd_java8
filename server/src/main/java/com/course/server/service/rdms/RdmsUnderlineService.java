/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsUnderline;
import com.course.server.domain.RdmsUnderlineExample;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsUnderlineMapper;
import com.course.server.util.UuidUtil;
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
public class RdmsUnderlineService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsUnderlineService.class);
    @Resource
    private RdmsUnderlineMapper rdmsUnderlineMapper;

    /**
     * 根据主键查询下划线信息
     * 通过ID查询下划线详细信息
     * 
     * @param id 下划线ID
     * @return 返回下划线对象
     */
    public RdmsUnderline selectByPrimaryKey(String id){
        return rdmsUnderlineMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsUnderline underline) {
        if(ObjectUtils.isEmpty(underline.getId())){
            return this.insert(underline);
        }else{
            RdmsUnderline rdmsUnderline = this.selectByPrimaryKey(underline.getId());
            if(ObjectUtils.isEmpty(rdmsUnderline)){
                return this.insert(underline);
            }else{
                return this.update(underline);
            }
        }
    }

    /**
     * 根据工人ID获取下划线信息
     * 通过工人ID查询对应的下划线信息
     * 
     * @param workerId 工人ID
     * @return 返回下划线对象，如果不存在则返回null
     */
    public RdmsUnderline getByWorkerId(String workerId){
        RdmsUnderlineExample rdmsUnderlineExample = new RdmsUnderlineExample();
        rdmsUnderlineExample.createCriteria()
                .andWorkerIdEqualTo(workerId)
                .andDeletedEqualTo(0);
        List<RdmsUnderline> rdmsUnderlines = rdmsUnderlineMapper.selectByExample(rdmsUnderlineExample);
        if(!CollectionUtils.isEmpty(rdmsUnderlines)){
            return rdmsUnderlines.get(0);
        }else{
            return null;
        }
    }
    /**
     * 获取直接领导列表
     * 根据下划线ID查询直接领导列表
     * 
     * @param underlineId 下划线ID
     * @return 返回直接领导列表
     */
    public List<RdmsUnderline> getDirectLeaderList(String underlineId){
        RdmsUnderlineExample rdmsUnderlineExample = new RdmsUnderlineExample();
        rdmsUnderlineExample.createCriteria()
                .andUnderlineIdsStrLike("%"+underlineId+"%")
                .andDeletedEqualTo(0);
        List<RdmsUnderline> rdmsUnderlines = rdmsUnderlineMapper.selectByExample(rdmsUnderlineExample);
        return rdmsUnderlines;
    }
    /**
     * 根据工人ID保存下划线信息
     * 如果工人ID已存在则更新，否则新增
     * 
     * @param underline 下划线对象
     * @return 返回保存后的ID
     * @throws BusinessException 输入信息错误异常
     */
    public String saveByWorkerId(RdmsUnderline underline) {
        if(!ObjectUtils.isEmpty(underline.getWorkerId())){
            RdmsUnderline rdmsUnderline = this.getByWorkerId(underline.getWorkerId());
            if(ObjectUtils.isEmpty(rdmsUnderline)){
                //insert
                return this.insert(underline);
            }else{
                //update
                underline.setId(rdmsUnderline.getId());
                return this.update(underline);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        }
    }

    private String insert(RdmsUnderline underline) {
        if(ObjectUtils.isEmpty(underline.getId())){  //当前端页面给出projectID时,将不为空
            underline.setId(UuidUtil.getShortUuid());
        }
        RdmsUnderline rdmsUnderline = this.selectByPrimaryKey(underline.getId());
        if(ObjectUtils.isEmpty(rdmsUnderline)){
            underline.setUpdateTime(new Date());
            underline.setDeleted(0);
            rdmsUnderlineMapper.insert(underline);
            return underline.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    /**
     * 更新下划线信息
     * 根据ID更新下划线信息
     * 
     * @param underline 下划线对象
     * @return 返回更新后的ID
     * @throws BusinessException 数据记录错误或记录不存在异常
     */
    public String update(RdmsUnderline underline) {
        if(ObjectUtils.isEmpty(underline.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsUnderline rdmsUnderline = this.selectByPrimaryKey(underline.getId());
        if(ObjectUtils.isEmpty(rdmsUnderline)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            underline.setDeleted(0);
            underline.setUpdateTime(new Date());
            rdmsUnderlineMapper.updateByPrimaryKey(underline);
            return underline.getId();
        }
    }

    /**
     * 选择性更新下划线信息
     * 根据ID选择性更新下划线信息
     * 
     * @param underline 下划线对象
     * @return 返回更新后的ID
     */
    public String updateByPrimaryKeySelective(RdmsUnderline underline){
        underline.setUpdateTime(new Date());
        rdmsUnderlineMapper.updateByPrimaryKeySelective(underline);
        return underline.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsUnderline rdmsUnderline = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsUnderline)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsUnderline.setDeleted(1);
            rdmsUnderlineMapper.updateByPrimaryKey(rdmsUnderline);
        }
    }

}
