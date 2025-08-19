/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsCharacter {
    private String id;

    private String characterSerial;

    private String parent;

    private Integer deep;

    private String characterName;

    private Integer iterationVersion;

    private String customerId;

    private String preProjectId;

    private String projectId;

    private String subprojectId;

    private String milestoneId;

    private String productManagerId;

    private String writerId;

    private String nextNode;

    private String status;

    private String auxStatus;

    private String jobitemType;

    private String jobitemId;

    private String projectType;

    private String stage;

    private Date updateTime;

    private String fileListStr;

    private String demandListStr;

    private String mergedCharacterIdStr;

    private String moduleIdListStr;

    private Date createTime;

    private Date submitTime;

    private Date setupedTime;

    private Date planCompleteTime;

    private Double devManhour;

    private Double devManhourApproved;

    private Double testManhour;

    private Double testManhourApproved;

    private BigDecimal materialFee;

    private BigDecimal materialFeeApproved;

    private BigDecimal testFee;

    private BigDecimal powerFee;

    private BigDecimal conferenceFee;

    private BigDecimal businessFee;

    private BigDecimal cooperationFee;

    private BigDecimal propertyFee;

    private BigDecimal laborFee;

    private BigDecimal consultingFee;

    private BigDecimal managementFee;

    private BigDecimal sumOtherFee;

    private BigDecimal budget;

    private String versionUpdate;

    private String mmStatus;

    private String bomStatus;

    private Integer bomVersion;

    private String outCharacterId;

    private String cbbProjectId;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharacterSerial() {
        return characterSerial;
    }

    public void setCharacterSerial(String characterSerial) {
        this.characterSerial = characterSerial;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getIterationVersion() {
        return iterationVersion;
    }

    public void setIterationVersion(Integer iterationVersion) {
        this.iterationVersion = iterationVersion;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPreProjectId() {
        return preProjectId;
    }

    public void setPreProjectId(String preProjectId) {
        this.preProjectId = preProjectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(String subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(String milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getProductManagerId() {
        return productManagerId;
    }

    public void setProductManagerId(String productManagerId) {
        this.productManagerId = productManagerId;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuxStatus() {
        return auxStatus;
    }

    public void setAuxStatus(String auxStatus) {
        this.auxStatus = auxStatus;
    }

    public String getJobitemType() {
        return jobitemType;
    }

    public void setJobitemType(String jobitemType) {
        this.jobitemType = jobitemType;
    }

    public String getJobitemId() {
        return jobitemId;
    }

    public void setJobitemId(String jobitemId) {
        this.jobitemId = jobitemId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public String getDemandListStr() {
        return demandListStr;
    }

    public void setDemandListStr(String demandListStr) {
        this.demandListStr = demandListStr;
    }

    public String getMergedCharacterIdStr() {
        return mergedCharacterIdStr;
    }

    public void setMergedCharacterIdStr(String mergedCharacterIdStr) {
        this.mergedCharacterIdStr = mergedCharacterIdStr;
    }

    public String getModuleIdListStr() {
        return moduleIdListStr;
    }

    public void setModuleIdListStr(String moduleIdListStr) {
        this.moduleIdListStr = moduleIdListStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getSetupedTime() {
        return setupedTime;
    }

    public void setSetupedTime(Date setupedTime) {
        this.setupedTime = setupedTime;
    }

    public Date getPlanCompleteTime() {
        return planCompleteTime;
    }

    public void setPlanCompleteTime(Date planCompleteTime) {
        this.planCompleteTime = planCompleteTime;
    }

    public Double getDevManhour() {
        return devManhour;
    }

    public void setDevManhour(Double devManhour) {
        this.devManhour = devManhour;
    }

    public Double getDevManhourApproved() {
        return devManhourApproved;
    }

    public void setDevManhourApproved(Double devManhourApproved) {
        this.devManhourApproved = devManhourApproved;
    }

    public Double getTestManhour() {
        return testManhour;
    }

    public void setTestManhour(Double testManhour) {
        this.testManhour = testManhour;
    }

    public Double getTestManhourApproved() {
        return testManhourApproved;
    }

    public void setTestManhourApproved(Double testManhourApproved) {
        this.testManhourApproved = testManhourApproved;
    }

    public BigDecimal getMaterialFee() {
        return materialFee;
    }

    public void setMaterialFee(BigDecimal materialFee) {
        this.materialFee = materialFee;
    }

    public BigDecimal getMaterialFeeApproved() {
        return materialFeeApproved;
    }

    public void setMaterialFeeApproved(BigDecimal materialFeeApproved) {
        this.materialFeeApproved = materialFeeApproved;
    }

    public BigDecimal getTestFee() {
        return testFee;
    }

    public void setTestFee(BigDecimal testFee) {
        this.testFee = testFee;
    }

    public BigDecimal getPowerFee() {
        return powerFee;
    }

    public void setPowerFee(BigDecimal powerFee) {
        this.powerFee = powerFee;
    }

    public BigDecimal getConferenceFee() {
        return conferenceFee;
    }

    public void setConferenceFee(BigDecimal conferenceFee) {
        this.conferenceFee = conferenceFee;
    }

    public BigDecimal getBusinessFee() {
        return businessFee;
    }

    public void setBusinessFee(BigDecimal businessFee) {
        this.businessFee = businessFee;
    }

    public BigDecimal getCooperationFee() {
        return cooperationFee;
    }

    public void setCooperationFee(BigDecimal cooperationFee) {
        this.cooperationFee = cooperationFee;
    }

    public BigDecimal getPropertyFee() {
        return propertyFee;
    }

    public void setPropertyFee(BigDecimal propertyFee) {
        this.propertyFee = propertyFee;
    }

    public BigDecimal getLaborFee() {
        return laborFee;
    }

    public void setLaborFee(BigDecimal laborFee) {
        this.laborFee = laborFee;
    }

    public BigDecimal getConsultingFee() {
        return consultingFee;
    }

    public void setConsultingFee(BigDecimal consultingFee) {
        this.consultingFee = consultingFee;
    }

    public BigDecimal getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(BigDecimal managementFee) {
        this.managementFee = managementFee;
    }

    public BigDecimal getSumOtherFee() {
        return sumOtherFee;
    }

    public void setSumOtherFee(BigDecimal sumOtherFee) {
        this.sumOtherFee = sumOtherFee;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getVersionUpdate() {
        return versionUpdate;
    }

    public void setVersionUpdate(String versionUpdate) {
        this.versionUpdate = versionUpdate;
    }

    public String getMmStatus() {
        return mmStatus;
    }

    public void setMmStatus(String mmStatus) {
        this.mmStatus = mmStatus;
    }

    public String getBomStatus() {
        return bomStatus;
    }

    public void setBomStatus(String bomStatus) {
        this.bomStatus = bomStatus;
    }

    public Integer getBomVersion() {
        return bomVersion;
    }

    public void setBomVersion(Integer bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getOutCharacterId() {
        return outCharacterId;
    }

    public void setOutCharacterId(String outCharacterId) {
        this.outCharacterId = outCharacterId;
    }

    public String getCbbProjectId() {
        return cbbProjectId;
    }

    public void setCbbProjectId(String cbbProjectId) {
        this.cbbProjectId = cbbProjectId;
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
        sb.append(", characterSerial=").append(characterSerial);
        sb.append(", parent=").append(parent);
        sb.append(", deep=").append(deep);
        sb.append(", characterName=").append(characterName);
        sb.append(", iterationVersion=").append(iterationVersion);
        sb.append(", customerId=").append(customerId);
        sb.append(", preProjectId=").append(preProjectId);
        sb.append(", projectId=").append(projectId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", milestoneId=").append(milestoneId);
        sb.append(", productManagerId=").append(productManagerId);
        sb.append(", writerId=").append(writerId);
        sb.append(", nextNode=").append(nextNode);
        sb.append(", status=").append(status);
        sb.append(", auxStatus=").append(auxStatus);
        sb.append(", jobitemType=").append(jobitemType);
        sb.append(", jobitemId=").append(jobitemId);
        sb.append(", projectType=").append(projectType);
        sb.append(", stage=").append(stage);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", demandListStr=").append(demandListStr);
        sb.append(", mergedCharacterIdStr=").append(mergedCharacterIdStr);
        sb.append(", moduleIdListStr=").append(moduleIdListStr);
        sb.append(", createTime=").append(createTime);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", setupedTime=").append(setupedTime);
        sb.append(", planCompleteTime=").append(planCompleteTime);
        sb.append(", devManhour=").append(devManhour);
        sb.append(", devManhourApproved=").append(devManhourApproved);
        sb.append(", testManhour=").append(testManhour);
        sb.append(", testManhourApproved=").append(testManhourApproved);
        sb.append(", materialFee=").append(materialFee);
        sb.append(", materialFeeApproved=").append(materialFeeApproved);
        sb.append(", testFee=").append(testFee);
        sb.append(", powerFee=").append(powerFee);
        sb.append(", conferenceFee=").append(conferenceFee);
        sb.append(", businessFee=").append(businessFee);
        sb.append(", cooperationFee=").append(cooperationFee);
        sb.append(", propertyFee=").append(propertyFee);
        sb.append(", laborFee=").append(laborFee);
        sb.append(", consultingFee=").append(consultingFee);
        sb.append(", managementFee=").append(managementFee);
        sb.append(", sumOtherFee=").append(sumOtherFee);
        sb.append(", budget=").append(budget);
        sb.append(", versionUpdate=").append(versionUpdate);
        sb.append(", mmStatus=").append(mmStatus);
        sb.append(", bomStatus=").append(bomStatus);
        sb.append(", bomVersion=").append(bomVersion);
        sb.append(", outCharacterId=").append(outCharacterId);
        sb.append(", cbbProjectId=").append(cbbProjectId);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}
