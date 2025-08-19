/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsBudgetResearch {
    private String id;

    private String customerId;

    private String projectId;

    private String parentId;

    private String subprojectId;

    private String preprojectId;

    private String stage;

    private BigDecimal equipmentFeeBt;

    private BigDecimal equipmentFeeZc;

    private BigDecimal equipmentFeeApprovedBt;

    private BigDecimal equipmentFeeApprovedZc;

    private BigDecimal testFeeBt;

    private BigDecimal testFeeZc;

    private BigDecimal testFeeApprovedBt;

    private BigDecimal testFeeApprovedZc;

    private BigDecimal materialFeeBt;

    private BigDecimal materialFeeZc;

    private BigDecimal materialFeeApprovedBt;

    private BigDecimal materialFeeApprovedZc;

    private BigDecimal powerFeeBt;

    private BigDecimal powerFeeZc;

    private BigDecimal powerFeeApprovedBt;

    private BigDecimal powerFeeApprovedZc;

    private BigDecimal conferenceFeeBt;

    private BigDecimal conferenceFeeZc;

    private BigDecimal conferenceFeeApprovedBt;

    private BigDecimal conferenceFeeApprovedZc;

    private BigDecimal businessFeeBt;

    private BigDecimal businessFeeZc;

    private BigDecimal businessFeeApprovedBt;

    private BigDecimal businessFeeApprovedZc;

    private BigDecimal cooperationFeeBt;

    private BigDecimal cooperationFeeZc;

    private BigDecimal cooperationFeeApprovedBt;

    private BigDecimal cooperationFeeApprovedZc;

    private BigDecimal propertyFeeBt;

    private BigDecimal propertyFeeZc;

    private BigDecimal propertyFeeApprovedBt;

    private BigDecimal propertyFeeApprovedZc;

    private BigDecimal laborFeeBt;

    private BigDecimal laborFeeZc;

    private BigDecimal laborFeeApprovedBt;

    private BigDecimal laborFeeApprovedZc;

    private BigDecimal staffFeeBt;

    private BigDecimal staffFeeZc;

    private BigDecimal staffFeeApprovedBt;

    private BigDecimal staffFeeApprovedZc;

    private BigDecimal consultingFeeBt;

    private BigDecimal consultingFeeZc;

    private BigDecimal consultingFeeApprovedBt;

    private BigDecimal consultingFeeApprovedZc;

    private BigDecimal managementFeeBt;

    private BigDecimal managementFeeZc;

    private BigDecimal managementFeeApprovedBt;

    private BigDecimal managementFeeApprovedZc;

    private BigDecimal otherFeeBt;

    private BigDecimal otherFeeZc;

    private BigDecimal otherFeeApprovedBt;

    private BigDecimal otherFeeApprovedZc;

    private BigDecimal performanceFeeApprovedBt;

    private BigDecimal performanceFeeApprovedZc;

    private BigDecimal performanceFeeBt;

    private BigDecimal performanceFeeZc;

    private BigDecimal infrastructureFeeApprovedBt;

    private BigDecimal infrastructureFeeApprovedZc;

    private BigDecimal infrastructureFeeBt;

    private BigDecimal infrastructureFeeZc;

    private Date createTime;

    private String createrId;

    private Date updateTime;

    private String modifierId;

    private Integer isRoot;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(String subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getPreprojectId() {
        return preprojectId;
    }

    public void setPreprojectId(String preprojectId) {
        this.preprojectId = preprojectId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public BigDecimal getEquipmentFeeBt() {
        return equipmentFeeBt;
    }

    public void setEquipmentFeeBt(BigDecimal equipmentFeeBt) {
        this.equipmentFeeBt = equipmentFeeBt;
    }

    public BigDecimal getEquipmentFeeZc() {
        return equipmentFeeZc;
    }

    public void setEquipmentFeeZc(BigDecimal equipmentFeeZc) {
        this.equipmentFeeZc = equipmentFeeZc;
    }

    public BigDecimal getEquipmentFeeApprovedBt() {
        return equipmentFeeApprovedBt;
    }

    public void setEquipmentFeeApprovedBt(BigDecimal equipmentFeeApprovedBt) {
        this.equipmentFeeApprovedBt = equipmentFeeApprovedBt;
    }

    public BigDecimal getEquipmentFeeApprovedZc() {
        return equipmentFeeApprovedZc;
    }

    public void setEquipmentFeeApprovedZc(BigDecimal equipmentFeeApprovedZc) {
        this.equipmentFeeApprovedZc = equipmentFeeApprovedZc;
    }

    public BigDecimal getTestFeeBt() {
        return testFeeBt;
    }

    public void setTestFeeBt(BigDecimal testFeeBt) {
        this.testFeeBt = testFeeBt;
    }

    public BigDecimal getTestFeeZc() {
        return testFeeZc;
    }

    public void setTestFeeZc(BigDecimal testFeeZc) {
        this.testFeeZc = testFeeZc;
    }

    public BigDecimal getTestFeeApprovedBt() {
        return testFeeApprovedBt;
    }

    public void setTestFeeApprovedBt(BigDecimal testFeeApprovedBt) {
        this.testFeeApprovedBt = testFeeApprovedBt;
    }

    public BigDecimal getTestFeeApprovedZc() {
        return testFeeApprovedZc;
    }

    public void setTestFeeApprovedZc(BigDecimal testFeeApprovedZc) {
        this.testFeeApprovedZc = testFeeApprovedZc;
    }

    public BigDecimal getMaterialFeeBt() {
        return materialFeeBt;
    }

    public void setMaterialFeeBt(BigDecimal materialFeeBt) {
        this.materialFeeBt = materialFeeBt;
    }

    public BigDecimal getMaterialFeeZc() {
        return materialFeeZc;
    }

    public void setMaterialFeeZc(BigDecimal materialFeeZc) {
        this.materialFeeZc = materialFeeZc;
    }

    public BigDecimal getMaterialFeeApprovedBt() {
        return materialFeeApprovedBt;
    }

    public void setMaterialFeeApprovedBt(BigDecimal materialFeeApprovedBt) {
        this.materialFeeApprovedBt = materialFeeApprovedBt;
    }

    public BigDecimal getMaterialFeeApprovedZc() {
        return materialFeeApprovedZc;
    }

    public void setMaterialFeeApprovedZc(BigDecimal materialFeeApprovedZc) {
        this.materialFeeApprovedZc = materialFeeApprovedZc;
    }

    public BigDecimal getPowerFeeBt() {
        return powerFeeBt;
    }

    public void setPowerFeeBt(BigDecimal powerFeeBt) {
        this.powerFeeBt = powerFeeBt;
    }

    public BigDecimal getPowerFeeZc() {
        return powerFeeZc;
    }

    public void setPowerFeeZc(BigDecimal powerFeeZc) {
        this.powerFeeZc = powerFeeZc;
    }

    public BigDecimal getPowerFeeApprovedBt() {
        return powerFeeApprovedBt;
    }

    public void setPowerFeeApprovedBt(BigDecimal powerFeeApprovedBt) {
        this.powerFeeApprovedBt = powerFeeApprovedBt;
    }

    public BigDecimal getPowerFeeApprovedZc() {
        return powerFeeApprovedZc;
    }

    public void setPowerFeeApprovedZc(BigDecimal powerFeeApprovedZc) {
        this.powerFeeApprovedZc = powerFeeApprovedZc;
    }

    public BigDecimal getConferenceFeeBt() {
        return conferenceFeeBt;
    }

    public void setConferenceFeeBt(BigDecimal conferenceFeeBt) {
        this.conferenceFeeBt = conferenceFeeBt;
    }

    public BigDecimal getConferenceFeeZc() {
        return conferenceFeeZc;
    }

    public void setConferenceFeeZc(BigDecimal conferenceFeeZc) {
        this.conferenceFeeZc = conferenceFeeZc;
    }

    public BigDecimal getConferenceFeeApprovedBt() {
        return conferenceFeeApprovedBt;
    }

    public void setConferenceFeeApprovedBt(BigDecimal conferenceFeeApprovedBt) {
        this.conferenceFeeApprovedBt = conferenceFeeApprovedBt;
    }

    public BigDecimal getConferenceFeeApprovedZc() {
        return conferenceFeeApprovedZc;
    }

    public void setConferenceFeeApprovedZc(BigDecimal conferenceFeeApprovedZc) {
        this.conferenceFeeApprovedZc = conferenceFeeApprovedZc;
    }

    public BigDecimal getBusinessFeeBt() {
        return businessFeeBt;
    }

    public void setBusinessFeeBt(BigDecimal businessFeeBt) {
        this.businessFeeBt = businessFeeBt;
    }

    public BigDecimal getBusinessFeeZc() {
        return businessFeeZc;
    }

    public void setBusinessFeeZc(BigDecimal businessFeeZc) {
        this.businessFeeZc = businessFeeZc;
    }

    public BigDecimal getBusinessFeeApprovedBt() {
        return businessFeeApprovedBt;
    }

    public void setBusinessFeeApprovedBt(BigDecimal businessFeeApprovedBt) {
        this.businessFeeApprovedBt = businessFeeApprovedBt;
    }

    public BigDecimal getBusinessFeeApprovedZc() {
        return businessFeeApprovedZc;
    }

    public void setBusinessFeeApprovedZc(BigDecimal businessFeeApprovedZc) {
        this.businessFeeApprovedZc = businessFeeApprovedZc;
    }

    public BigDecimal getCooperationFeeBt() {
        return cooperationFeeBt;
    }

    public void setCooperationFeeBt(BigDecimal cooperationFeeBt) {
        this.cooperationFeeBt = cooperationFeeBt;
    }

    public BigDecimal getCooperationFeeZc() {
        return cooperationFeeZc;
    }

    public void setCooperationFeeZc(BigDecimal cooperationFeeZc) {
        this.cooperationFeeZc = cooperationFeeZc;
    }

    public BigDecimal getCooperationFeeApprovedBt() {
        return cooperationFeeApprovedBt;
    }

    public void setCooperationFeeApprovedBt(BigDecimal cooperationFeeApprovedBt) {
        this.cooperationFeeApprovedBt = cooperationFeeApprovedBt;
    }

    public BigDecimal getCooperationFeeApprovedZc() {
        return cooperationFeeApprovedZc;
    }

    public void setCooperationFeeApprovedZc(BigDecimal cooperationFeeApprovedZc) {
        this.cooperationFeeApprovedZc = cooperationFeeApprovedZc;
    }

    public BigDecimal getPropertyFeeBt() {
        return propertyFeeBt;
    }

    public void setPropertyFeeBt(BigDecimal propertyFeeBt) {
        this.propertyFeeBt = propertyFeeBt;
    }

    public BigDecimal getPropertyFeeZc() {
        return propertyFeeZc;
    }

    public void setPropertyFeeZc(BigDecimal propertyFeeZc) {
        this.propertyFeeZc = propertyFeeZc;
    }

    public BigDecimal getPropertyFeeApprovedBt() {
        return propertyFeeApprovedBt;
    }

    public void setPropertyFeeApprovedBt(BigDecimal propertyFeeApprovedBt) {
        this.propertyFeeApprovedBt = propertyFeeApprovedBt;
    }

    public BigDecimal getPropertyFeeApprovedZc() {
        return propertyFeeApprovedZc;
    }

    public void setPropertyFeeApprovedZc(BigDecimal propertyFeeApprovedZc) {
        this.propertyFeeApprovedZc = propertyFeeApprovedZc;
    }

    public BigDecimal getLaborFeeBt() {
        return laborFeeBt;
    }

    public void setLaborFeeBt(BigDecimal laborFeeBt) {
        this.laborFeeBt = laborFeeBt;
    }

    public BigDecimal getLaborFeeZc() {
        return laborFeeZc;
    }

    public void setLaborFeeZc(BigDecimal laborFeeZc) {
        this.laborFeeZc = laborFeeZc;
    }

    public BigDecimal getLaborFeeApprovedBt() {
        return laborFeeApprovedBt;
    }

    public void setLaborFeeApprovedBt(BigDecimal laborFeeApprovedBt) {
        this.laborFeeApprovedBt = laborFeeApprovedBt;
    }

    public BigDecimal getLaborFeeApprovedZc() {
        return laborFeeApprovedZc;
    }

    public void setLaborFeeApprovedZc(BigDecimal laborFeeApprovedZc) {
        this.laborFeeApprovedZc = laborFeeApprovedZc;
    }

    public BigDecimal getStaffFeeBt() {
        return staffFeeBt;
    }

    public void setStaffFeeBt(BigDecimal staffFeeBt) {
        this.staffFeeBt = staffFeeBt;
    }

    public BigDecimal getStaffFeeZc() {
        return staffFeeZc;
    }

    public void setStaffFeeZc(BigDecimal staffFeeZc) {
        this.staffFeeZc = staffFeeZc;
    }

    public BigDecimal getStaffFeeApprovedBt() {
        return staffFeeApprovedBt;
    }

    public void setStaffFeeApprovedBt(BigDecimal staffFeeApprovedBt) {
        this.staffFeeApprovedBt = staffFeeApprovedBt;
    }

    public BigDecimal getStaffFeeApprovedZc() {
        return staffFeeApprovedZc;
    }

    public void setStaffFeeApprovedZc(BigDecimal staffFeeApprovedZc) {
        this.staffFeeApprovedZc = staffFeeApprovedZc;
    }

    public BigDecimal getConsultingFeeBt() {
        return consultingFeeBt;
    }

    public void setConsultingFeeBt(BigDecimal consultingFeeBt) {
        this.consultingFeeBt = consultingFeeBt;
    }

    public BigDecimal getConsultingFeeZc() {
        return consultingFeeZc;
    }

    public void setConsultingFeeZc(BigDecimal consultingFeeZc) {
        this.consultingFeeZc = consultingFeeZc;
    }

    public BigDecimal getConsultingFeeApprovedBt() {
        return consultingFeeApprovedBt;
    }

    public void setConsultingFeeApprovedBt(BigDecimal consultingFeeApprovedBt) {
        this.consultingFeeApprovedBt = consultingFeeApprovedBt;
    }

    public BigDecimal getConsultingFeeApprovedZc() {
        return consultingFeeApprovedZc;
    }

    public void setConsultingFeeApprovedZc(BigDecimal consultingFeeApprovedZc) {
        this.consultingFeeApprovedZc = consultingFeeApprovedZc;
    }

    public BigDecimal getManagementFeeBt() {
        return managementFeeBt;
    }

    public void setManagementFeeBt(BigDecimal managementFeeBt) {
        this.managementFeeBt = managementFeeBt;
    }

    public BigDecimal getManagementFeeZc() {
        return managementFeeZc;
    }

    public void setManagementFeeZc(BigDecimal managementFeeZc) {
        this.managementFeeZc = managementFeeZc;
    }

    public BigDecimal getManagementFeeApprovedBt() {
        return managementFeeApprovedBt;
    }

    public void setManagementFeeApprovedBt(BigDecimal managementFeeApprovedBt) {
        this.managementFeeApprovedBt = managementFeeApprovedBt;
    }

    public BigDecimal getManagementFeeApprovedZc() {
        return managementFeeApprovedZc;
    }

    public void setManagementFeeApprovedZc(BigDecimal managementFeeApprovedZc) {
        this.managementFeeApprovedZc = managementFeeApprovedZc;
    }

    public BigDecimal getOtherFeeBt() {
        return otherFeeBt;
    }

    public void setOtherFeeBt(BigDecimal otherFeeBt) {
        this.otherFeeBt = otherFeeBt;
    }

    public BigDecimal getOtherFeeZc() {
        return otherFeeZc;
    }

    public void setOtherFeeZc(BigDecimal otherFeeZc) {
        this.otherFeeZc = otherFeeZc;
    }

    public BigDecimal getOtherFeeApprovedBt() {
        return otherFeeApprovedBt;
    }

    public void setOtherFeeApprovedBt(BigDecimal otherFeeApprovedBt) {
        this.otherFeeApprovedBt = otherFeeApprovedBt;
    }

    public BigDecimal getOtherFeeApprovedZc() {
        return otherFeeApprovedZc;
    }

    public void setOtherFeeApprovedZc(BigDecimal otherFeeApprovedZc) {
        this.otherFeeApprovedZc = otherFeeApprovedZc;
    }

    public BigDecimal getPerformanceFeeApprovedBt() {
        return performanceFeeApprovedBt;
    }

    public void setPerformanceFeeApprovedBt(BigDecimal performanceFeeApprovedBt) {
        this.performanceFeeApprovedBt = performanceFeeApprovedBt;
    }

    public BigDecimal getPerformanceFeeApprovedZc() {
        return performanceFeeApprovedZc;
    }

    public void setPerformanceFeeApprovedZc(BigDecimal performanceFeeApprovedZc) {
        this.performanceFeeApprovedZc = performanceFeeApprovedZc;
    }

    public BigDecimal getPerformanceFeeBt() {
        return performanceFeeBt;
    }

    public void setPerformanceFeeBt(BigDecimal performanceFeeBt) {
        this.performanceFeeBt = performanceFeeBt;
    }

    public BigDecimal getPerformanceFeeZc() {
        return performanceFeeZc;
    }

    public void setPerformanceFeeZc(BigDecimal performanceFeeZc) {
        this.performanceFeeZc = performanceFeeZc;
    }

    public BigDecimal getInfrastructureFeeApprovedBt() {
        return infrastructureFeeApprovedBt;
    }

    public void setInfrastructureFeeApprovedBt(BigDecimal infrastructureFeeApprovedBt) {
        this.infrastructureFeeApprovedBt = infrastructureFeeApprovedBt;
    }

    public BigDecimal getInfrastructureFeeApprovedZc() {
        return infrastructureFeeApprovedZc;
    }

    public void setInfrastructureFeeApprovedZc(BigDecimal infrastructureFeeApprovedZc) {
        this.infrastructureFeeApprovedZc = infrastructureFeeApprovedZc;
    }

    public BigDecimal getInfrastructureFeeBt() {
        return infrastructureFeeBt;
    }

    public void setInfrastructureFeeBt(BigDecimal infrastructureFeeBt) {
        this.infrastructureFeeBt = infrastructureFeeBt;
    }

    public BigDecimal getInfrastructureFeeZc() {
        return infrastructureFeeZc;
    }

    public void setInfrastructureFeeZc(BigDecimal infrastructureFeeZc) {
        this.infrastructureFeeZc = infrastructureFeeZc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Integer getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", projectId=").append(projectId);
        sb.append(", parentId=").append(parentId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", preprojectId=").append(preprojectId);
        sb.append(", stage=").append(stage);
        sb.append(", equipmentFeeBt=").append(equipmentFeeBt);
        sb.append(", equipmentFeeZc=").append(equipmentFeeZc);
        sb.append(", equipmentFeeApprovedBt=").append(equipmentFeeApprovedBt);
        sb.append(", equipmentFeeApprovedZc=").append(equipmentFeeApprovedZc);
        sb.append(", testFeeBt=").append(testFeeBt);
        sb.append(", testFeeZc=").append(testFeeZc);
        sb.append(", testFeeApprovedBt=").append(testFeeApprovedBt);
        sb.append(", testFeeApprovedZc=").append(testFeeApprovedZc);
        sb.append(", materialFeeBt=").append(materialFeeBt);
        sb.append(", materialFeeZc=").append(materialFeeZc);
        sb.append(", materialFeeApprovedBt=").append(materialFeeApprovedBt);
        sb.append(", materialFeeApprovedZc=").append(materialFeeApprovedZc);
        sb.append(", powerFeeBt=").append(powerFeeBt);
        sb.append(", powerFeeZc=").append(powerFeeZc);
        sb.append(", powerFeeApprovedBt=").append(powerFeeApprovedBt);
        sb.append(", powerFeeApprovedZc=").append(powerFeeApprovedZc);
        sb.append(", conferenceFeeBt=").append(conferenceFeeBt);
        sb.append(", conferenceFeeZc=").append(conferenceFeeZc);
        sb.append(", conferenceFeeApprovedBt=").append(conferenceFeeApprovedBt);
        sb.append(", conferenceFeeApprovedZc=").append(conferenceFeeApprovedZc);
        sb.append(", businessFeeBt=").append(businessFeeBt);
        sb.append(", businessFeeZc=").append(businessFeeZc);
        sb.append(", businessFeeApprovedBt=").append(businessFeeApprovedBt);
        sb.append(", businessFeeApprovedZc=").append(businessFeeApprovedZc);
        sb.append(", cooperationFeeBt=").append(cooperationFeeBt);
        sb.append(", cooperationFeeZc=").append(cooperationFeeZc);
        sb.append(", cooperationFeeApprovedBt=").append(cooperationFeeApprovedBt);
        sb.append(", cooperationFeeApprovedZc=").append(cooperationFeeApprovedZc);
        sb.append(", propertyFeeBt=").append(propertyFeeBt);
        sb.append(", propertyFeeZc=").append(propertyFeeZc);
        sb.append(", propertyFeeApprovedBt=").append(propertyFeeApprovedBt);
        sb.append(", propertyFeeApprovedZc=").append(propertyFeeApprovedZc);
        sb.append(", laborFeeBt=").append(laborFeeBt);
        sb.append(", laborFeeZc=").append(laborFeeZc);
        sb.append(", laborFeeApprovedBt=").append(laborFeeApprovedBt);
        sb.append(", laborFeeApprovedZc=").append(laborFeeApprovedZc);
        sb.append(", staffFeeBt=").append(staffFeeBt);
        sb.append(", staffFeeZc=").append(staffFeeZc);
        sb.append(", staffFeeApprovedBt=").append(staffFeeApprovedBt);
        sb.append(", staffFeeApprovedZc=").append(staffFeeApprovedZc);
        sb.append(", consultingFeeBt=").append(consultingFeeBt);
        sb.append(", consultingFeeZc=").append(consultingFeeZc);
        sb.append(", consultingFeeApprovedBt=").append(consultingFeeApprovedBt);
        sb.append(", consultingFeeApprovedZc=").append(consultingFeeApprovedZc);
        sb.append(", managementFeeBt=").append(managementFeeBt);
        sb.append(", managementFeeZc=").append(managementFeeZc);
        sb.append(", managementFeeApprovedBt=").append(managementFeeApprovedBt);
        sb.append(", managementFeeApprovedZc=").append(managementFeeApprovedZc);
        sb.append(", otherFeeBt=").append(otherFeeBt);
        sb.append(", otherFeeZc=").append(otherFeeZc);
        sb.append(", otherFeeApprovedBt=").append(otherFeeApprovedBt);
        sb.append(", otherFeeApprovedZc=").append(otherFeeApprovedZc);
        sb.append(", performanceFeeApprovedBt=").append(performanceFeeApprovedBt);
        sb.append(", performanceFeeApprovedZc=").append(performanceFeeApprovedZc);
        sb.append(", performanceFeeBt=").append(performanceFeeBt);
        sb.append(", performanceFeeZc=").append(performanceFeeZc);
        sb.append(", infrastructureFeeApprovedBt=").append(infrastructureFeeApprovedBt);
        sb.append(", infrastructureFeeApprovedZc=").append(infrastructureFeeApprovedZc);
        sb.append(", infrastructureFeeBt=").append(infrastructureFeeBt);
        sb.append(", infrastructureFeeZc=").append(infrastructureFeeZc);
        sb.append(", createTime=").append(createTime);
        sb.append(", createrId=").append(createrId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", modifierId=").append(modifierId);
        sb.append(", isRoot=").append(isRoot);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}