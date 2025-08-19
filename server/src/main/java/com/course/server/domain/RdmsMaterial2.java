/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsMaterial2 {
    private String id;

    private String tokenCode;

    private String code;

    private String bomCode;

    private String name;

    private String model;

    private String unit;

    private Double amount;

    private String usageMode;

    private BigDecimal preTaxUnitPrice;

    private BigDecimal taxRate;

    private BigDecimal preTaxSumPrice;

    private BigDecimal sumPrice;

    private String sales;

    private String contactPhone;

    private String supplierName;

    private String supplierAddress;

    private String writerId;

    private String status;

    private String auxStatus;

    private String fileListStr;

    private String storageCode;

    private String batchCode;

    private String warehouseCode;

    private String reserve1;

    private String reserve2;

    private Date updateTime;

    private Date createTime;

    private Integer inBom;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBomCode() {
        return bomCode;
    }

    public void setBomCode(String bomCode) {
        this.bomCode = bomCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUsageMode() {
        return usageMode;
    }

    public void setUsageMode(String usageMode) {
        this.usageMode = usageMode;
    }

    public BigDecimal getPreTaxUnitPrice() {
        return preTaxUnitPrice;
    }

    public void setPreTaxUnitPrice(BigDecimal preTaxUnitPrice) {
        this.preTaxUnitPrice = preTaxUnitPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getPreTaxSumPrice() {
        return preTaxSumPrice;
    }

    public void setPreTaxSumPrice(BigDecimal preTaxSumPrice) {
        this.preTaxSumPrice = preTaxSumPrice;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
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

    public String getFileListStr() {
        return fileListStr;
    }

    public void setFileListStr(String fileListStr) {
        this.fileListStr = fileListStr;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getInBom() {
        return inBom;
    }

    public void setInBom(Integer inBom) {
        this.inBom = inBom;
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
        sb.append(", tokenCode=").append(tokenCode);
        sb.append(", code=").append(code);
        sb.append(", bomCode=").append(bomCode);
        sb.append(", name=").append(name);
        sb.append(", model=").append(model);
        sb.append(", unit=").append(unit);
        sb.append(", amount=").append(amount);
        sb.append(", usageMode=").append(usageMode);
        sb.append(", preTaxUnitPrice=").append(preTaxUnitPrice);
        sb.append(", taxRate=").append(taxRate);
        sb.append(", preTaxSumPrice=").append(preTaxSumPrice);
        sb.append(", sumPrice=").append(sumPrice);
        sb.append(", sales=").append(sales);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", supplierName=").append(supplierName);
        sb.append(", supplierAddress=").append(supplierAddress);
        sb.append(", writerId=").append(writerId);
        sb.append(", status=").append(status);
        sb.append(", auxStatus=").append(auxStatus);
        sb.append(", fileListStr=").append(fileListStr);
        sb.append(", storageCode=").append(storageCode);
        sb.append(", batchCode=").append(batchCode);
        sb.append(", warehouseCode=").append(warehouseCode);
        sb.append(", reserve1=").append(reserve1);
        sb.append(", reserve2=").append(reserve2);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", inBom=").append(inBom);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}