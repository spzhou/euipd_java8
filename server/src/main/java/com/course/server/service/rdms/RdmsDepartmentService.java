/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsDepartmentTreeDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.FixedDepartmentEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsDepartmentService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsDepartmentService.class);

    @Resource
    private RdmsDepartmentMapper departmentMapper;
    @Autowired
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;
    @Autowired
    private RdmsPdgmService rdmsPdgmService;
    @Autowired
    private RdmsFggmService rdmsFggmService;
    @Autowired
    private RdmsQcgmService rdmsQcgmService;
    @Autowired
    private RdmsTgmService rdmsTgmService;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsDepartmentExample rdmsDepartmentExample = new RdmsDepartmentExample();
        List<RdmsDepartment> departmentList = departmentMapper.selectByExample(rdmsDepartmentExample);
        //去掉有删除标记的记录
        List<RdmsDepartment> departmentListTemp = new ArrayList<>();
        for(RdmsDepartment rdmsDepartment : departmentList){
            if(rdmsDepartment.getDeleted() != 1){
                departmentListTemp.add(rdmsDepartment);
            }
        }

        PageInfo<RdmsDepartment> pageInfo = new PageInfo<>(departmentListTemp);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsDepartmentDto> departmentDtoList = CopyUtil.copyList(departmentListTemp, RdmsDepartmentDto.class);
        pageDto.setList(departmentDtoList);
    }

    /**
     * 列表查询
     */
    public List<RdmsDepartmentDto> listDepartment(String customerId) {
        RdmsDepartmentExample rdmsDepartmentExample = new RdmsDepartmentExample();
        rdmsDepartmentExample.setOrderByClause("create_time asc");
        RdmsDepartmentExample.Criteria criteria = rdmsDepartmentExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsDepartment> departmentList = departmentMapper.selectByExample(rdmsDepartmentExample);
        List<RdmsDepartmentDto> rdmsDepartmentDtos = CopyUtil.copyList(departmentList, RdmsDepartmentDto.class);
        for(RdmsDepartmentDto rdmsDepartmentDto : rdmsDepartmentDtos){
            //如果rdmsDepartmentDto.getDepartName()的值为FixedDepartmentEnum的code的值,则将rdmsDepartmentDto.getDepartName()的值设置为FixedDepartmentEnum的name的值
            if(FixedDepartmentEnum.isExist(rdmsDepartmentDto.getDepartName())){
                rdmsDepartmentDto.setDepartName(FixedDepartmentEnum.getEnumByCode(rdmsDepartmentDto.getDepartName()).getName());
            }
        }
        return rdmsDepartmentDtos;
    }

    public List<RdmsDepartmentDto> listDepartmentByManagerId(String managerId) {
        RdmsDepartmentExample rdmsDepartmentExample = new RdmsDepartmentExample();
        rdmsDepartmentExample.setOrderByClause("create_time asc");
        RdmsDepartmentExample.Criteria criteria = rdmsDepartmentExample.createCriteria();
        criteria.andManagerIdEqualTo(managerId).andDeletedEqualTo(0);
        List<RdmsDepartment> departmentList = departmentMapper.selectByExample(rdmsDepartmentExample);
        return CopyUtil.copyList(departmentList, RdmsDepartmentDto.class);
    }

    public RdmsDepartment getDefaultDepartment(String customerId) {
        RdmsDepartmentExample rdmsDepartmentExample = new RdmsDepartmentExample();
        rdmsDepartmentExample.setOrderByClause("create_time asc");
        RdmsDepartmentExample.Criteria criteria = rdmsDepartmentExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        criteria.andParentIsNull();
        List<RdmsDepartment> departmentList = departmentMapper.selectByExample(rdmsDepartmentExample);
        if(!CollectionUtils.isEmpty(departmentList)){
            return departmentList.get(0);
        }else{
            return null;
        }
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public String save(RdmsDepartmentDto departmentDto) {
        RdmsDepartment department = CopyUtil.copy(departmentDto, RdmsDepartment.class);
        department.setCreateTime(new Date());

        RdmsDepartmentExample departmentExample = new RdmsDepartmentExample();
        RdmsDepartmentExample.Criteria criteria = departmentExample.createCriteria();
        criteria.andCustomerIdEqualTo(departmentDto.getCustomerId())
                .andDepartNameEqualTo(departmentDto.getDepartName());
        List<RdmsDepartment> rdmsDepartments = departmentMapper.selectByExample(departmentExample);
        if(!CollectionUtils.isEmpty(rdmsDepartments)){
            //如果父级部门不同,则报错,并发出警告; 如果父级部门相同, 则更新
            if(!ObjectUtils.isEmpty(departmentDto.getParent()) && !rdmsDepartments.get(0).getParent().equals(departmentDto.getParent())){
                throw new BusinessException(BusinessExceptionCode.DEPARTMENT_NAME_EXIST);
            }else{
                department.setId(rdmsDepartments.get(0).getId());
                return this.update(department);
            }
        }else{
            return this.insert(department);
        }
    }

    public String edit(RdmsDepartmentDto departmentDto) {
        if(ObjectUtils.isEmpty(departmentDto.getId())){
            return null;
        }

        //对固定部门的Manager进行保存
        if(!ObjectUtils.isEmpty(departmentDto.getName()) && FixedDepartmentEnum.getEnumByCode(departmentDto.getName()) != null){
            switch (Objects.requireNonNull(FixedDepartmentEnum.getEnumByCode(departmentDto.getName()))){
                case SYSTEM_ENGINEERING_DEPARTMENT:
                    RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(departmentDto.getCustomerId());
                    if(!ObjectUtils.isEmpty(sysgmByCustomerId)){
                        sysgmByCustomerId.setSysgmId(departmentDto.getManagerId());
                        rdmsSysgmService.save(sysgmByCustomerId);
                    }
                    break;
                case PRODUCT_CENTER:
                    RdmsPdgmDto pdgmByCustomerId = rdmsPdgmService.getPdgmByCustomerId(departmentDto.getCustomerId());
                    if(!ObjectUtils.isEmpty(pdgmByCustomerId)){
                        pdgmByCustomerId.setPdgmId(departmentDto.getManagerId());
                        rdmsPdgmService.save(pdgmByCustomerId);
                    }
                    break;
                case REGULATION_DEPARTMENT:
                    RdmsFggmDto fggmByCustomerId = rdmsFggmService.getFggmByCustomerId(departmentDto.getCustomerId());
                    if(!ObjectUtils.isEmpty(fggmByCustomerId)){
                        fggmByCustomerId.setFggmId(departmentDto.getManagerId());
                        rdmsFggmService.save(fggmByCustomerId);
                    }
                    break;
                case QUALITY_DEPARTMENT:
                    RdmsQcgmDto qcgmByCustomerId = rdmsQcgmService.getQcgmByCustomerId(departmentDto.getCustomerId());
                    if(!ObjectUtils.isEmpty(qcgmByCustomerId)){
                        qcgmByCustomerId.setQcgmId(departmentDto.getManagerId());
                        rdmsQcgmService.save(qcgmByCustomerId);
                    }
                    break;
                case TEST_DEPARTMENT:
                    RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(departmentDto.getCustomerId());
                    if(!ObjectUtils.isEmpty(tgmByCustomerId)){
                        tgmByCustomerId.setTgmId(departmentDto.getManagerId());
                        rdmsTgmService.save(tgmByCustomerId);
                    }
                    break;
                case RESEARCH_AND_DEVELOPMENT_CENTER:
                    break;
                default:
                    break;
            }
        }

        RdmsDepartment department = CopyUtil.copy(departmentDto, RdmsDepartment.class);
        department.setUpdateTime(new Date());
        department.setDeleted(0);
        return this.update(department);
    }

    /**
     * 新增
     */
    private String insert(RdmsDepartment department) {
        if(ObjectUtils.isEmpty(department.getId())){  //当前端页面给出projectID时,将不为空
            department.setId(UuidUtil.getShortUuid());
        }
        department.setDeleted(0);
        department.setCreateTime(new Date());
        department.setUpdateTime(new Date());
        if(department.getParent() != null){
            RdmsDepartmentExample departmentExample = new RdmsDepartmentExample();
            RdmsDepartmentExample.Criteria criteria = departmentExample.createCriteria();
            criteria.andCustomerIdEqualTo(department.getCustomerId())
                    .andParentEqualTo(department.getParent())
                    .andDepartNameEqualTo(department.getDepartName());
            List<RdmsDepartment> customerDbs = departmentMapper.selectByExample(departmentExample);
            if(!CollectionUtils.isEmpty(customerDbs)){
                throw new BusinessException(BusinessExceptionCode.DEPARTMENT_NAME_EXIST);
            }else{
                departmentMapper.insert(department);
                return department.getId();
            }
        }else{
            departmentMapper.insert(department);
            return department.getId();
        }

    }

    /**
     * 更新
     */
    private String update(RdmsDepartment department) {
        if(ObjectUtils.isEmpty(department) || ObjectUtils.isEmpty(department.getId())){
            return null;
        }
        RdmsDepartment departmentDb = this.selectByPrimaryKey(department.getId());
        if(!ObjectUtils.isEmpty(departmentDb)){
            department.setId(departmentDb.getId());
            department.setUpdateTime(new Date());
            departmentMapper.updateByPrimaryKey(department);
            return department.getId();
        }
        return null;
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    public RdmsDepartment selectByPrimaryKey(String id) {
        RdmsDepartmentExample rdmsDepartmentExample = new RdmsDepartmentExample();
        rdmsDepartmentExample.createCriteria().andIdEqualTo(id);
        List<RdmsDepartment> departmentList = departmentMapper.selectByExample(rdmsDepartmentExample);
        if (CollectionUtils.isEmpty(departmentList)) {
            return null;
        } else {
            return departmentList.get(0);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsDepartment department = this.selectByPrimaryKey(id);
        if(department != null){
            department.setDeleted(1);  //设置删除标志位
            this.update(department);
        }
    }

    /**
     * 根据客户的ID查询相应客户所有的部门信息
     * @param customerId
     * @return
     */
    public RdmsDepartmentTreeDto getDepartmentsTree(String customerId) {
        RdmsDepartmentExample rdmsDepartmentExample = new RdmsDepartmentExample();
        rdmsDepartmentExample.setOrderByClause("create_time asc");
        rdmsDepartmentExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsDepartment> departmentList = departmentMapper.selectByExample(rdmsDepartmentExample);
        if (CollectionUtils.isEmpty(departmentList)) {
            return null;
        } else {
            //对所有部门关系组装成树形结构数据
            RdmsDepartmentTreeDto departmentTree = new RdmsDepartmentTreeDto();
            //1. 找出代表公司的根节点
            List<RdmsDepartment> rootDepartments = departmentList.stream().filter(rdmsDepartment -> rdmsDepartment.getParent() == null).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(rootDepartments)){
                departmentTree.setId(rootDepartments.get(0).getId());
                departmentTree.setParent(rootDepartments.get(0).getParent());
                departmentTree.setLabel(rootDepartments.get(0).getDepartName());
                departmentTree.setManagerId(rootDepartments.get(0).getManagerId());
                departmentTree.setDescription(rootDepartments.get(0).getDepartDescription());
                if(!ObjectUtils.isEmpty(rootDepartments.get(0)) && !ObjectUtils.isEmpty(rootDepartments.get(0).getFixed())){
                    departmentTree.setFixed(rootDepartments.get(0).getFixed());
                }
                departmentTree.setMemberIdList(null);
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rootDepartments.get(0).getManagerId());
                RdmsCustomerUserDto copy = CopyUtil.copy(rdmsCustomerUser, RdmsCustomerUserDto.class);
                departmentTree.setManagerInfo(copy);
            }
            //2. 添加一级部门
            String rootDepartmentId = rootDepartments.get(0).getId();
            List<RdmsDepartment> firstLevelDepartments = departmentList.stream().filter(rdmsDepartment -> rdmsDepartment.getParent() != null && rdmsDepartment.getParent().equals(rootDepartmentId)).collect(Collectors.toList());
            List<RdmsDepartmentTreeDto> firstLevelDepartmentTreeDtos = new ArrayList<>();
            if(!CollectionUtils.isEmpty(firstLevelDepartments)){
                for(RdmsDepartment department : firstLevelDepartments){
                    RdmsDepartmentTreeDto departmentTreeDto = new RdmsDepartmentTreeDto();
                    departmentTreeDto.setId(department.getId());
                    departmentTreeDto.setParent(department.getParent());
                    departmentTreeDto.setName(department.getDepartName());
                    //如果departName属于FixedDepartmentEnum枚举中的值，则取枚举中的name，否则取departName
                    if(FixedDepartmentEnum.isExist(department.getDepartName())){
                        departmentTreeDto.setLabel(FixedDepartmentEnum.getEnumByCode(department.getDepartName()).getName());
                    }else{
                        departmentTreeDto.setLabel(department.getDepartName());
                    }

                    departmentTreeDto.setManagerId(department.getManagerId());
                    departmentTreeDto.setDescription(department.getDepartDescription());
                    departmentTreeDto.setFixed(department.getFixed());
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(department.getManagerId());
                    RdmsCustomerUserDto copy = CopyUtil.copy(rdmsCustomerUser, RdmsCustomerUserDto.class);
                    departmentTreeDto.setManagerInfo(copy);
                    //添加成员idList
                    List<RdmsCustomerUser> memberListByDepartmentId = rdmsCustomerUserService.getMemberListByDepartmentId(department.getId());
                    if(!CollectionUtils.isEmpty(memberListByDepartmentId)){
                        List<String> collect = memberListByDepartmentId.stream().map(RdmsCustomerUser::getId).collect(Collectors.toList());
                        departmentTreeDto.setMemberIdList(collect);
                    }else{
                        departmentTreeDto.setMemberIdList(null);
                    }
                    firstLevelDepartmentTreeDtos.add(departmentTreeDto);
                }
            }
            departmentTree.setChildren(firstLevelDepartmentTreeDtos);
            if(!CollectionUtils.isEmpty(firstLevelDepartmentTreeDtos)){
                for (RdmsDepartmentTreeDto firstDepartmentTreeDto : firstLevelDepartmentTreeDtos) {
                    addNextLevelDepartment(firstDepartmentTreeDto, departmentList);
                }
            }
            return departmentTree;
        }
    }

    private void addNextLevelDepartment(RdmsDepartmentTreeDto currentDepartment, List<RdmsDepartment> departmentList) {
        //为一级部门进行子树的组装
        List<RdmsDepartment> childrenDepartments = departmentList.stream().filter(rdmsDepartment -> Objects.equals(rdmsDepartment.getParent(), currentDepartment.getId())).collect(Collectors.toList());
        List<RdmsDepartmentTreeDto> childrenDepartmentTreeDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(childrenDepartments)){
            for(RdmsDepartment department : childrenDepartments){
                RdmsDepartmentTreeDto departmentTreeDto = new RdmsDepartmentTreeDto();
                departmentTreeDto.setId(department.getId());
                departmentTreeDto.setParent(department.getParent());
                departmentTreeDto.setName(department.getDepartName());
                //如果departName属于FixedDepartmentEnum枚举中的值，则取枚举中的name，否则取departName
                if(FixedDepartmentEnum.isExist(department.getDepartName())){
                    departmentTreeDto.setLabel(FixedDepartmentEnum.getEnumByCode(department.getDepartName()).getName());
                }else{
                    departmentTreeDto.setLabel(department.getDepartName());
                }

                departmentTreeDto.setManagerId(department.getManagerId());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(department.getManagerId());
                RdmsCustomerUserDto copy = CopyUtil.copy(rdmsCustomerUser, RdmsCustomerUserDto.class);
                departmentTreeDto.setManagerInfo(copy);
                departmentTreeDto.setDescription(department.getDepartDescription());
                if(!ObjectUtils.isEmpty(department.getFixed())){
                    departmentTreeDto.setFixed(department.getFixed());
                }else{
                    departmentTreeDto.setFixed(0);
                }
                //添加成员idList
                List<RdmsCustomerUser> memberListByDepartmentId = rdmsCustomerUserService.getMemberListByDepartmentId(department.getId());
                if(!CollectionUtils.isEmpty(memberListByDepartmentId)){
                    List<String> collect = memberListByDepartmentId.stream().map(RdmsCustomerUser::getId).collect(Collectors.toList());
                    departmentTreeDto.setMemberIdList(collect);
                }else{
                    departmentTreeDto.setMemberIdList(null);
                }

                childrenDepartmentTreeDtos.add(departmentTreeDto);
            }
        }
        if(!CollectionUtils.isEmpty(childrenDepartmentTreeDtos)){
            currentDepartment.setChildren(childrenDepartmentTreeDtos);
            for(RdmsDepartmentTreeDto childrenDepartment : childrenDepartmentTreeDtos){
                addNextLevelDepartment(childrenDepartment, departmentList);
            }
        }
    }
}
