/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RdmsBudgetResearchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsBudgetResearchExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("customer_id like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("customer_id not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("project_id like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("project_id not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            addCriterion("parent_id like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("parent_id not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdIsNull() {
            addCriterion("subproject_id is null");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdIsNotNull() {
            addCriterion("subproject_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdEqualTo(String value) {
            addCriterion("subproject_id =", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdNotEqualTo(String value) {
            addCriterion("subproject_id <>", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdGreaterThan(String value) {
            addCriterion("subproject_id >", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdGreaterThanOrEqualTo(String value) {
            addCriterion("subproject_id >=", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdLessThan(String value) {
            addCriterion("subproject_id <", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdLessThanOrEqualTo(String value) {
            addCriterion("subproject_id <=", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdLike(String value) {
            addCriterion("subproject_id like", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdNotLike(String value) {
            addCriterion("subproject_id not like", value, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdIn(List<String> values) {
            addCriterion("subproject_id in", values, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdNotIn(List<String> values) {
            addCriterion("subproject_id not in", values, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdBetween(String value1, String value2) {
            addCriterion("subproject_id between", value1, value2, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andSubprojectIdNotBetween(String value1, String value2) {
            addCriterion("subproject_id not between", value1, value2, "subprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdIsNull() {
            addCriterion("preproject_id is null");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdIsNotNull() {
            addCriterion("preproject_id is not null");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdEqualTo(String value) {
            addCriterion("preproject_id =", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdNotEqualTo(String value) {
            addCriterion("preproject_id <>", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdGreaterThan(String value) {
            addCriterion("preproject_id >", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdGreaterThanOrEqualTo(String value) {
            addCriterion("preproject_id >=", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdLessThan(String value) {
            addCriterion("preproject_id <", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdLessThanOrEqualTo(String value) {
            addCriterion("preproject_id <=", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdLike(String value) {
            addCriterion("preproject_id like", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdNotLike(String value) {
            addCriterion("preproject_id not like", value, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdIn(List<String> values) {
            addCriterion("preproject_id in", values, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdNotIn(List<String> values) {
            addCriterion("preproject_id not in", values, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdBetween(String value1, String value2) {
            addCriterion("preproject_id between", value1, value2, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andPreprojectIdNotBetween(String value1, String value2) {
            addCriterion("preproject_id not between", value1, value2, "preprojectId");
            return (Criteria) this;
        }

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(String value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(String value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(String value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(String value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(String value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(String value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLike(String value) {
            addCriterion("stage like", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotLike(String value) {
            addCriterion("stage not like", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<String> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<String> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(String value1, String value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(String value1, String value2) {
            addCriterion("stage not between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtIsNull() {
            addCriterion("equipment_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtIsNotNull() {
            addCriterion("equipment_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_bt =", value, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_bt <>", value, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtGreaterThan(BigDecimal value) {
            addCriterion("equipment_fee_bt >", value, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_bt >=", value, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtLessThan(BigDecimal value) {
            addCriterion("equipment_fee_bt <", value, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_bt <=", value, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_bt in", values, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_bt not in", values, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_bt between", value1, value2, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_bt not between", value1, value2, "equipmentFeeBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcIsNull() {
            addCriterion("equipment_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcIsNotNull() {
            addCriterion("equipment_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_zc =", value, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_zc <>", value, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcGreaterThan(BigDecimal value) {
            addCriterion("equipment_fee_zc >", value, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_zc >=", value, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcLessThan(BigDecimal value) {
            addCriterion("equipment_fee_zc <", value, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_zc <=", value, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_zc in", values, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_zc not in", values, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_zc between", value1, value2, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_zc not between", value1, value2, "equipmentFeeZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtIsNull() {
            addCriterion("equipment_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtIsNotNull() {
            addCriterion("equipment_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_bt =", value, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_bt <>", value, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("equipment_fee_approved_bt >", value, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_bt >=", value, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("equipment_fee_approved_bt <", value, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_bt <=", value, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_approved_bt in", values, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_approved_bt not in", values, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_approved_bt between", value1, value2, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_approved_bt not between", value1, value2, "equipmentFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcIsNull() {
            addCriterion("equipment_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcIsNotNull() {
            addCriterion("equipment_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_zc =", value, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_zc <>", value, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("equipment_fee_approved_zc >", value, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_zc >=", value, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("equipment_fee_approved_zc <", value, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("equipment_fee_approved_zc <=", value, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_approved_zc in", values, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("equipment_fee_approved_zc not in", values, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_approved_zc between", value1, value2, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andEquipmentFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("equipment_fee_approved_zc not between", value1, value2, "equipmentFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtIsNull() {
            addCriterion("test_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtIsNotNull() {
            addCriterion("test_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtEqualTo(BigDecimal value) {
            addCriterion("test_fee_bt =", value, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("test_fee_bt <>", value, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtGreaterThan(BigDecimal value) {
            addCriterion("test_fee_bt >", value, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_bt >=", value, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtLessThan(BigDecimal value) {
            addCriterion("test_fee_bt <", value, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_bt <=", value, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtIn(List<BigDecimal> values) {
            addCriterion("test_fee_bt in", values, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("test_fee_bt not in", values, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_bt between", value1, value2, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_bt not between", value1, value2, "testFeeBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcIsNull() {
            addCriterion("test_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcIsNotNull() {
            addCriterion("test_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcEqualTo(BigDecimal value) {
            addCriterion("test_fee_zc =", value, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("test_fee_zc <>", value, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcGreaterThan(BigDecimal value) {
            addCriterion("test_fee_zc >", value, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_zc >=", value, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcLessThan(BigDecimal value) {
            addCriterion("test_fee_zc <", value, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_zc <=", value, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcIn(List<BigDecimal> values) {
            addCriterion("test_fee_zc in", values, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("test_fee_zc not in", values, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_zc between", value1, value2, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_zc not between", value1, value2, "testFeeZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtIsNull() {
            addCriterion("test_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtIsNotNull() {
            addCriterion("test_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_bt =", value, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_bt <>", value, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("test_fee_approved_bt >", value, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_bt >=", value, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("test_fee_approved_bt <", value, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_bt <=", value, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("test_fee_approved_bt in", values, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("test_fee_approved_bt not in", values, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_approved_bt between", value1, value2, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_approved_bt not between", value1, value2, "testFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcIsNull() {
            addCriterion("test_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcIsNotNull() {
            addCriterion("test_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_zc =", value, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_zc <>", value, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("test_fee_approved_zc >", value, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_zc >=", value, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("test_fee_approved_zc <", value, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee_approved_zc <=", value, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("test_fee_approved_zc in", values, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("test_fee_approved_zc not in", values, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_approved_zc between", value1, value2, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andTestFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee_approved_zc not between", value1, value2, "testFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtIsNull() {
            addCriterion("material_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtIsNotNull() {
            addCriterion("material_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtEqualTo(BigDecimal value) {
            addCriterion("material_fee_bt =", value, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("material_fee_bt <>", value, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtGreaterThan(BigDecimal value) {
            addCriterion("material_fee_bt >", value, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_bt >=", value, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtLessThan(BigDecimal value) {
            addCriterion("material_fee_bt <", value, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_bt <=", value, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtIn(List<BigDecimal> values) {
            addCriterion("material_fee_bt in", values, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("material_fee_bt not in", values, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_bt between", value1, value2, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_bt not between", value1, value2, "materialFeeBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcIsNull() {
            addCriterion("material_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcIsNotNull() {
            addCriterion("material_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcEqualTo(BigDecimal value) {
            addCriterion("material_fee_zc =", value, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("material_fee_zc <>", value, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcGreaterThan(BigDecimal value) {
            addCriterion("material_fee_zc >", value, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_zc >=", value, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcLessThan(BigDecimal value) {
            addCriterion("material_fee_zc <", value, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_zc <=", value, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcIn(List<BigDecimal> values) {
            addCriterion("material_fee_zc in", values, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("material_fee_zc not in", values, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_zc between", value1, value2, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_zc not between", value1, value2, "materialFeeZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtIsNull() {
            addCriterion("material_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtIsNotNull() {
            addCriterion("material_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_bt =", value, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_bt <>", value, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("material_fee_approved_bt >", value, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_bt >=", value, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("material_fee_approved_bt <", value, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_bt <=", value, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved_bt in", values, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved_bt not in", values, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved_bt between", value1, value2, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved_bt not between", value1, value2, "materialFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcIsNull() {
            addCriterion("material_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcIsNotNull() {
            addCriterion("material_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_zc =", value, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_zc <>", value, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("material_fee_approved_zc >", value, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_zc >=", value, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("material_fee_approved_zc <", value, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved_zc <=", value, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved_zc in", values, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved_zc not in", values, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved_zc between", value1, value2, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved_zc not between", value1, value2, "materialFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtIsNull() {
            addCriterion("power_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtIsNotNull() {
            addCriterion("power_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtEqualTo(BigDecimal value) {
            addCriterion("power_fee_bt =", value, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("power_fee_bt <>", value, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtGreaterThan(BigDecimal value) {
            addCriterion("power_fee_bt >", value, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_bt >=", value, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtLessThan(BigDecimal value) {
            addCriterion("power_fee_bt <", value, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_bt <=", value, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtIn(List<BigDecimal> values) {
            addCriterion("power_fee_bt in", values, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("power_fee_bt not in", values, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_bt between", value1, value2, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_bt not between", value1, value2, "powerFeeBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcIsNull() {
            addCriterion("power_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcIsNotNull() {
            addCriterion("power_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcEqualTo(BigDecimal value) {
            addCriterion("power_fee_zc =", value, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("power_fee_zc <>", value, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcGreaterThan(BigDecimal value) {
            addCriterion("power_fee_zc >", value, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_zc >=", value, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcLessThan(BigDecimal value) {
            addCriterion("power_fee_zc <", value, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_zc <=", value, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcIn(List<BigDecimal> values) {
            addCriterion("power_fee_zc in", values, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("power_fee_zc not in", values, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_zc between", value1, value2, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_zc not between", value1, value2, "powerFeeZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtIsNull() {
            addCriterion("power_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtIsNotNull() {
            addCriterion("power_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_bt =", value, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_bt <>", value, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("power_fee_approved_bt >", value, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_bt >=", value, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("power_fee_approved_bt <", value, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_bt <=", value, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("power_fee_approved_bt in", values, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("power_fee_approved_bt not in", values, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_approved_bt between", value1, value2, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_approved_bt not between", value1, value2, "powerFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcIsNull() {
            addCriterion("power_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcIsNotNull() {
            addCriterion("power_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_zc =", value, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_zc <>", value, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("power_fee_approved_zc >", value, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_zc >=", value, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("power_fee_approved_zc <", value, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee_approved_zc <=", value, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("power_fee_approved_zc in", values, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("power_fee_approved_zc not in", values, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_approved_zc between", value1, value2, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPowerFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee_approved_zc not between", value1, value2, "powerFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtIsNull() {
            addCriterion("conference_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtIsNotNull() {
            addCriterion("conference_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtEqualTo(BigDecimal value) {
            addCriterion("conference_fee_bt =", value, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("conference_fee_bt <>", value, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtGreaterThan(BigDecimal value) {
            addCriterion("conference_fee_bt >", value, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_bt >=", value, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtLessThan(BigDecimal value) {
            addCriterion("conference_fee_bt <", value, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_bt <=", value, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtIn(List<BigDecimal> values) {
            addCriterion("conference_fee_bt in", values, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("conference_fee_bt not in", values, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_bt between", value1, value2, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_bt not between", value1, value2, "conferenceFeeBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcIsNull() {
            addCriterion("conference_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcIsNotNull() {
            addCriterion("conference_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcEqualTo(BigDecimal value) {
            addCriterion("conference_fee_zc =", value, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("conference_fee_zc <>", value, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcGreaterThan(BigDecimal value) {
            addCriterion("conference_fee_zc >", value, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_zc >=", value, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcLessThan(BigDecimal value) {
            addCriterion("conference_fee_zc <", value, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_zc <=", value, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcIn(List<BigDecimal> values) {
            addCriterion("conference_fee_zc in", values, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("conference_fee_zc not in", values, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_zc between", value1, value2, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_zc not between", value1, value2, "conferenceFeeZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtIsNull() {
            addCriterion("conference_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtIsNotNull() {
            addCriterion("conference_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_bt =", value, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_bt <>", value, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("conference_fee_approved_bt >", value, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_bt >=", value, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("conference_fee_approved_bt <", value, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_bt <=", value, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("conference_fee_approved_bt in", values, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("conference_fee_approved_bt not in", values, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_approved_bt between", value1, value2, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_approved_bt not between", value1, value2, "conferenceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcIsNull() {
            addCriterion("conference_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcIsNotNull() {
            addCriterion("conference_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_zc =", value, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_zc <>", value, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("conference_fee_approved_zc >", value, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_zc >=", value, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("conference_fee_approved_zc <", value, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee_approved_zc <=", value, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("conference_fee_approved_zc in", values, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("conference_fee_approved_zc not in", values, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_approved_zc between", value1, value2, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee_approved_zc not between", value1, value2, "conferenceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtIsNull() {
            addCriterion("business_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtIsNotNull() {
            addCriterion("business_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtEqualTo(BigDecimal value) {
            addCriterion("business_fee_bt =", value, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("business_fee_bt <>", value, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtGreaterThan(BigDecimal value) {
            addCriterion("business_fee_bt >", value, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_bt >=", value, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtLessThan(BigDecimal value) {
            addCriterion("business_fee_bt <", value, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_bt <=", value, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtIn(List<BigDecimal> values) {
            addCriterion("business_fee_bt in", values, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("business_fee_bt not in", values, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_bt between", value1, value2, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_bt not between", value1, value2, "businessFeeBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcIsNull() {
            addCriterion("business_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcIsNotNull() {
            addCriterion("business_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcEqualTo(BigDecimal value) {
            addCriterion("business_fee_zc =", value, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("business_fee_zc <>", value, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcGreaterThan(BigDecimal value) {
            addCriterion("business_fee_zc >", value, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_zc >=", value, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcLessThan(BigDecimal value) {
            addCriterion("business_fee_zc <", value, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_zc <=", value, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcIn(List<BigDecimal> values) {
            addCriterion("business_fee_zc in", values, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("business_fee_zc not in", values, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_zc between", value1, value2, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_zc not between", value1, value2, "businessFeeZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtIsNull() {
            addCriterion("business_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtIsNotNull() {
            addCriterion("business_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_bt =", value, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_bt <>", value, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("business_fee_approved_bt >", value, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_bt >=", value, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("business_fee_approved_bt <", value, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_bt <=", value, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("business_fee_approved_bt in", values, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("business_fee_approved_bt not in", values, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_approved_bt between", value1, value2, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_approved_bt not between", value1, value2, "businessFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcIsNull() {
            addCriterion("business_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcIsNotNull() {
            addCriterion("business_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_zc =", value, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_zc <>", value, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("business_fee_approved_zc >", value, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_zc >=", value, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("business_fee_approved_zc <", value, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee_approved_zc <=", value, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("business_fee_approved_zc in", values, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("business_fee_approved_zc not in", values, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_approved_zc between", value1, value2, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee_approved_zc not between", value1, value2, "businessFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtIsNull() {
            addCriterion("cooperation_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtIsNotNull() {
            addCriterion("cooperation_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_bt =", value, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_bt <>", value, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtGreaterThan(BigDecimal value) {
            addCriterion("cooperation_fee_bt >", value, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_bt >=", value, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtLessThan(BigDecimal value) {
            addCriterion("cooperation_fee_bt <", value, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_bt <=", value, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_bt in", values, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_bt not in", values, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_bt between", value1, value2, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_bt not between", value1, value2, "cooperationFeeBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcIsNull() {
            addCriterion("cooperation_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcIsNotNull() {
            addCriterion("cooperation_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_zc =", value, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_zc <>", value, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcGreaterThan(BigDecimal value) {
            addCriterion("cooperation_fee_zc >", value, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_zc >=", value, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcLessThan(BigDecimal value) {
            addCriterion("cooperation_fee_zc <", value, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_zc <=", value, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_zc in", values, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_zc not in", values, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_zc between", value1, value2, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_zc not between", value1, value2, "cooperationFeeZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtIsNull() {
            addCriterion("cooperation_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtIsNotNull() {
            addCriterion("cooperation_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_bt =", value, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_bt <>", value, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("cooperation_fee_approved_bt >", value, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_bt >=", value, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("cooperation_fee_approved_bt <", value, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_bt <=", value, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_approved_bt in", values, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_approved_bt not in", values, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_approved_bt between", value1, value2, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_approved_bt not between", value1, value2, "cooperationFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcIsNull() {
            addCriterion("cooperation_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcIsNotNull() {
            addCriterion("cooperation_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_zc =", value, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_zc <>", value, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("cooperation_fee_approved_zc >", value, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_zc >=", value, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("cooperation_fee_approved_zc <", value, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee_approved_zc <=", value, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_approved_zc in", values, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee_approved_zc not in", values, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_approved_zc between", value1, value2, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee_approved_zc not between", value1, value2, "cooperationFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtIsNull() {
            addCriterion("property_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtIsNotNull() {
            addCriterion("property_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtEqualTo(BigDecimal value) {
            addCriterion("property_fee_bt =", value, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("property_fee_bt <>", value, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtGreaterThan(BigDecimal value) {
            addCriterion("property_fee_bt >", value, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_bt >=", value, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtLessThan(BigDecimal value) {
            addCriterion("property_fee_bt <", value, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_bt <=", value, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtIn(List<BigDecimal> values) {
            addCriterion("property_fee_bt in", values, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("property_fee_bt not in", values, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_bt between", value1, value2, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_bt not between", value1, value2, "propertyFeeBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcIsNull() {
            addCriterion("property_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcIsNotNull() {
            addCriterion("property_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcEqualTo(BigDecimal value) {
            addCriterion("property_fee_zc =", value, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("property_fee_zc <>", value, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcGreaterThan(BigDecimal value) {
            addCriterion("property_fee_zc >", value, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_zc >=", value, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcLessThan(BigDecimal value) {
            addCriterion("property_fee_zc <", value, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_zc <=", value, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcIn(List<BigDecimal> values) {
            addCriterion("property_fee_zc in", values, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("property_fee_zc not in", values, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_zc between", value1, value2, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_zc not between", value1, value2, "propertyFeeZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtIsNull() {
            addCriterion("property_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtIsNotNull() {
            addCriterion("property_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_bt =", value, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_bt <>", value, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("property_fee_approved_bt >", value, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_bt >=", value, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("property_fee_approved_bt <", value, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_bt <=", value, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("property_fee_approved_bt in", values, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("property_fee_approved_bt not in", values, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_approved_bt between", value1, value2, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_approved_bt not between", value1, value2, "propertyFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcIsNull() {
            addCriterion("property_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcIsNotNull() {
            addCriterion("property_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_zc =", value, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_zc <>", value, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("property_fee_approved_zc >", value, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_zc >=", value, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("property_fee_approved_zc <", value, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee_approved_zc <=", value, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("property_fee_approved_zc in", values, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("property_fee_approved_zc not in", values, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_approved_zc between", value1, value2, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee_approved_zc not between", value1, value2, "propertyFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtIsNull() {
            addCriterion("labor_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtIsNotNull() {
            addCriterion("labor_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtEqualTo(BigDecimal value) {
            addCriterion("labor_fee_bt =", value, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("labor_fee_bt <>", value, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtGreaterThan(BigDecimal value) {
            addCriterion("labor_fee_bt >", value, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_bt >=", value, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtLessThan(BigDecimal value) {
            addCriterion("labor_fee_bt <", value, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_bt <=", value, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtIn(List<BigDecimal> values) {
            addCriterion("labor_fee_bt in", values, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("labor_fee_bt not in", values, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_bt between", value1, value2, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_bt not between", value1, value2, "laborFeeBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcIsNull() {
            addCriterion("labor_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcIsNotNull() {
            addCriterion("labor_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcEqualTo(BigDecimal value) {
            addCriterion("labor_fee_zc =", value, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("labor_fee_zc <>", value, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcGreaterThan(BigDecimal value) {
            addCriterion("labor_fee_zc >", value, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_zc >=", value, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcLessThan(BigDecimal value) {
            addCriterion("labor_fee_zc <", value, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_zc <=", value, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcIn(List<BigDecimal> values) {
            addCriterion("labor_fee_zc in", values, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("labor_fee_zc not in", values, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_zc between", value1, value2, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_zc not between", value1, value2, "laborFeeZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtIsNull() {
            addCriterion("labor_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtIsNotNull() {
            addCriterion("labor_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_bt =", value, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_bt <>", value, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("labor_fee_approved_bt >", value, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_bt >=", value, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("labor_fee_approved_bt <", value, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_bt <=", value, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("labor_fee_approved_bt in", values, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("labor_fee_approved_bt not in", values, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_approved_bt between", value1, value2, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_approved_bt not between", value1, value2, "laborFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcIsNull() {
            addCriterion("labor_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcIsNotNull() {
            addCriterion("labor_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_zc =", value, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_zc <>", value, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("labor_fee_approved_zc >", value, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_zc >=", value, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("labor_fee_approved_zc <", value, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee_approved_zc <=", value, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("labor_fee_approved_zc in", values, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("labor_fee_approved_zc not in", values, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_approved_zc between", value1, value2, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andLaborFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee_approved_zc not between", value1, value2, "laborFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtIsNull() {
            addCriterion("staff_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtIsNotNull() {
            addCriterion("staff_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtEqualTo(BigDecimal value) {
            addCriterion("staff_fee_bt =", value, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("staff_fee_bt <>", value, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtGreaterThan(BigDecimal value) {
            addCriterion("staff_fee_bt >", value, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_bt >=", value, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtLessThan(BigDecimal value) {
            addCriterion("staff_fee_bt <", value, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_bt <=", value, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtIn(List<BigDecimal> values) {
            addCriterion("staff_fee_bt in", values, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("staff_fee_bt not in", values, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_bt between", value1, value2, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_bt not between", value1, value2, "staffFeeBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcIsNull() {
            addCriterion("staff_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcIsNotNull() {
            addCriterion("staff_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcEqualTo(BigDecimal value) {
            addCriterion("staff_fee_zc =", value, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("staff_fee_zc <>", value, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcGreaterThan(BigDecimal value) {
            addCriterion("staff_fee_zc >", value, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_zc >=", value, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcLessThan(BigDecimal value) {
            addCriterion("staff_fee_zc <", value, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_zc <=", value, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcIn(List<BigDecimal> values) {
            addCriterion("staff_fee_zc in", values, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("staff_fee_zc not in", values, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_zc between", value1, value2, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_zc not between", value1, value2, "staffFeeZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtIsNull() {
            addCriterion("staff_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtIsNotNull() {
            addCriterion("staff_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_bt =", value, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_bt <>", value, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("staff_fee_approved_bt >", value, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_bt >=", value, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("staff_fee_approved_bt <", value, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_bt <=", value, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("staff_fee_approved_bt in", values, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("staff_fee_approved_bt not in", values, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_approved_bt between", value1, value2, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_approved_bt not between", value1, value2, "staffFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcIsNull() {
            addCriterion("staff_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcIsNotNull() {
            addCriterion("staff_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_zc =", value, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_zc <>", value, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("staff_fee_approved_zc >", value, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_zc >=", value, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("staff_fee_approved_zc <", value, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("staff_fee_approved_zc <=", value, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("staff_fee_approved_zc in", values, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("staff_fee_approved_zc not in", values, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_approved_zc between", value1, value2, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andStaffFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("staff_fee_approved_zc not between", value1, value2, "staffFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtIsNull() {
            addCriterion("consulting_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtIsNotNull() {
            addCriterion("consulting_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_bt =", value, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_bt <>", value, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtGreaterThan(BigDecimal value) {
            addCriterion("consulting_fee_bt >", value, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_bt >=", value, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtLessThan(BigDecimal value) {
            addCriterion("consulting_fee_bt <", value, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_bt <=", value, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_bt in", values, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_bt not in", values, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_bt between", value1, value2, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_bt not between", value1, value2, "consultingFeeBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcIsNull() {
            addCriterion("consulting_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcIsNotNull() {
            addCriterion("consulting_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_zc =", value, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_zc <>", value, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcGreaterThan(BigDecimal value) {
            addCriterion("consulting_fee_zc >", value, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_zc >=", value, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcLessThan(BigDecimal value) {
            addCriterion("consulting_fee_zc <", value, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_zc <=", value, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_zc in", values, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_zc not in", values, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_zc between", value1, value2, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_zc not between", value1, value2, "consultingFeeZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtIsNull() {
            addCriterion("consulting_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtIsNotNull() {
            addCriterion("consulting_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_bt =", value, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_bt <>", value, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("consulting_fee_approved_bt >", value, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_bt >=", value, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("consulting_fee_approved_bt <", value, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_bt <=", value, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_approved_bt in", values, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_approved_bt not in", values, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_approved_bt between", value1, value2, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_approved_bt not between", value1, value2, "consultingFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcIsNull() {
            addCriterion("consulting_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcIsNotNull() {
            addCriterion("consulting_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_zc =", value, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_zc <>", value, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("consulting_fee_approved_zc >", value, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_zc >=", value, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("consulting_fee_approved_zc <", value, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee_approved_zc <=", value, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_approved_zc in", values, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("consulting_fee_approved_zc not in", values, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_approved_zc between", value1, value2, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee_approved_zc not between", value1, value2, "consultingFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtIsNull() {
            addCriterion("management_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtIsNotNull() {
            addCriterion("management_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtEqualTo(BigDecimal value) {
            addCriterion("management_fee_bt =", value, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("management_fee_bt <>", value, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtGreaterThan(BigDecimal value) {
            addCriterion("management_fee_bt >", value, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_bt >=", value, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtLessThan(BigDecimal value) {
            addCriterion("management_fee_bt <", value, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_bt <=", value, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtIn(List<BigDecimal> values) {
            addCriterion("management_fee_bt in", values, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("management_fee_bt not in", values, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_bt between", value1, value2, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_bt not between", value1, value2, "managementFeeBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcIsNull() {
            addCriterion("management_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcIsNotNull() {
            addCriterion("management_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcEqualTo(BigDecimal value) {
            addCriterion("management_fee_zc =", value, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("management_fee_zc <>", value, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcGreaterThan(BigDecimal value) {
            addCriterion("management_fee_zc >", value, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_zc >=", value, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcLessThan(BigDecimal value) {
            addCriterion("management_fee_zc <", value, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_zc <=", value, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcIn(List<BigDecimal> values) {
            addCriterion("management_fee_zc in", values, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("management_fee_zc not in", values, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_zc between", value1, value2, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_zc not between", value1, value2, "managementFeeZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtIsNull() {
            addCriterion("management_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtIsNotNull() {
            addCriterion("management_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_bt =", value, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_bt <>", value, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("management_fee_approved_bt >", value, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_bt >=", value, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("management_fee_approved_bt <", value, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_bt <=", value, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("management_fee_approved_bt in", values, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("management_fee_approved_bt not in", values, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_approved_bt between", value1, value2, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_approved_bt not between", value1, value2, "managementFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcIsNull() {
            addCriterion("management_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcIsNotNull() {
            addCriterion("management_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_zc =", value, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_zc <>", value, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("management_fee_approved_zc >", value, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_zc >=", value, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("management_fee_approved_zc <", value, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee_approved_zc <=", value, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("management_fee_approved_zc in", values, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("management_fee_approved_zc not in", values, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_approved_zc between", value1, value2, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andManagementFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee_approved_zc not between", value1, value2, "managementFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtIsNull() {
            addCriterion("other_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtIsNotNull() {
            addCriterion("other_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtEqualTo(BigDecimal value) {
            addCriterion("other_fee_bt =", value, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("other_fee_bt <>", value, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtGreaterThan(BigDecimal value) {
            addCriterion("other_fee_bt >", value, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_bt >=", value, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtLessThan(BigDecimal value) {
            addCriterion("other_fee_bt <", value, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_bt <=", value, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtIn(List<BigDecimal> values) {
            addCriterion("other_fee_bt in", values, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("other_fee_bt not in", values, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_bt between", value1, value2, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_bt not between", value1, value2, "otherFeeBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcIsNull() {
            addCriterion("other_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcIsNotNull() {
            addCriterion("other_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcEqualTo(BigDecimal value) {
            addCriterion("other_fee_zc =", value, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("other_fee_zc <>", value, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcGreaterThan(BigDecimal value) {
            addCriterion("other_fee_zc >", value, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_zc >=", value, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcLessThan(BigDecimal value) {
            addCriterion("other_fee_zc <", value, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_zc <=", value, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcIn(List<BigDecimal> values) {
            addCriterion("other_fee_zc in", values, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("other_fee_zc not in", values, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_zc between", value1, value2, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_zc not between", value1, value2, "otherFeeZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtIsNull() {
            addCriterion("other_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtIsNotNull() {
            addCriterion("other_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_bt =", value, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_bt <>", value, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("other_fee_approved_bt >", value, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_bt >=", value, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("other_fee_approved_bt <", value, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_bt <=", value, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("other_fee_approved_bt in", values, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("other_fee_approved_bt not in", values, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_approved_bt between", value1, value2, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_approved_bt not between", value1, value2, "otherFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcIsNull() {
            addCriterion("other_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcIsNotNull() {
            addCriterion("other_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_zc =", value, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_zc <>", value, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("other_fee_approved_zc >", value, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_zc >=", value, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("other_fee_approved_zc <", value, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved_zc <=", value, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("other_fee_approved_zc in", values, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("other_fee_approved_zc not in", values, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_approved_zc between", value1, value2, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_approved_zc not between", value1, value2, "otherFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtIsNull() {
            addCriterion("performance_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtIsNotNull() {
            addCriterion("performance_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_bt =", value, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_bt <>", value, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("performance_fee_approved_bt >", value, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_bt >=", value, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("performance_fee_approved_bt <", value, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_bt <=", value, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("performance_fee_approved_bt in", values, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("performance_fee_approved_bt not in", values, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_approved_bt between", value1, value2, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_approved_bt not between", value1, value2, "performanceFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcIsNull() {
            addCriterion("performance_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcIsNotNull() {
            addCriterion("performance_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_zc =", value, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_zc <>", value, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("performance_fee_approved_zc >", value, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_zc >=", value, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("performance_fee_approved_zc <", value, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_approved_zc <=", value, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("performance_fee_approved_zc in", values, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("performance_fee_approved_zc not in", values, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_approved_zc between", value1, value2, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_approved_zc not between", value1, value2, "performanceFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtIsNull() {
            addCriterion("performance_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtIsNotNull() {
            addCriterion("performance_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtEqualTo(BigDecimal value) {
            addCriterion("performance_fee_bt =", value, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("performance_fee_bt <>", value, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtGreaterThan(BigDecimal value) {
            addCriterion("performance_fee_bt >", value, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_bt >=", value, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtLessThan(BigDecimal value) {
            addCriterion("performance_fee_bt <", value, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_bt <=", value, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtIn(List<BigDecimal> values) {
            addCriterion("performance_fee_bt in", values, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("performance_fee_bt not in", values, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_bt between", value1, value2, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_bt not between", value1, value2, "performanceFeeBt");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcIsNull() {
            addCriterion("performance_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcIsNotNull() {
            addCriterion("performance_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcEqualTo(BigDecimal value) {
            addCriterion("performance_fee_zc =", value, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("performance_fee_zc <>", value, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcGreaterThan(BigDecimal value) {
            addCriterion("performance_fee_zc >", value, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_zc >=", value, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcLessThan(BigDecimal value) {
            addCriterion("performance_fee_zc <", value, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("performance_fee_zc <=", value, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcIn(List<BigDecimal> values) {
            addCriterion("performance_fee_zc in", values, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("performance_fee_zc not in", values, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_zc between", value1, value2, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andPerformanceFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance_fee_zc not between", value1, value2, "performanceFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtIsNull() {
            addCriterion("infrastructure_fee_approved_bt is null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtIsNotNull() {
            addCriterion("infrastructure_fee_approved_bt is not null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_bt =", value, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtNotEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_bt <>", value, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtGreaterThan(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_bt >", value, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_bt >=", value, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtLessThan(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_bt <", value, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_bt <=", value, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_approved_bt in", values, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtNotIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_approved_bt not in", values, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_approved_bt between", value1, value2, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_approved_bt not between", value1, value2, "infrastructureFeeApprovedBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcIsNull() {
            addCriterion("infrastructure_fee_approved_zc is null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcIsNotNull() {
            addCriterion("infrastructure_fee_approved_zc is not null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_zc =", value, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcNotEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_zc <>", value, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcGreaterThan(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_zc >", value, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_zc >=", value, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcLessThan(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_zc <", value, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_approved_zc <=", value, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_approved_zc in", values, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcNotIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_approved_zc not in", values, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_approved_zc between", value1, value2, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeApprovedZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_approved_zc not between", value1, value2, "infrastructureFeeApprovedZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtIsNull() {
            addCriterion("infrastructure_fee_bt is null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtIsNotNull() {
            addCriterion("infrastructure_fee_bt is not null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_bt =", value, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtNotEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_bt <>", value, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtGreaterThan(BigDecimal value) {
            addCriterion("infrastructure_fee_bt >", value, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_bt >=", value, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtLessThan(BigDecimal value) {
            addCriterion("infrastructure_fee_bt <", value, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_bt <=", value, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_bt in", values, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtNotIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_bt not in", values, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_bt between", value1, value2, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeBtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_bt not between", value1, value2, "infrastructureFeeBt");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcIsNull() {
            addCriterion("infrastructure_fee_zc is null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcIsNotNull() {
            addCriterion("infrastructure_fee_zc is not null");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_zc =", value, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcNotEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_zc <>", value, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcGreaterThan(BigDecimal value) {
            addCriterion("infrastructure_fee_zc >", value, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_zc >=", value, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcLessThan(BigDecimal value) {
            addCriterion("infrastructure_fee_zc <", value, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("infrastructure_fee_zc <=", value, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_zc in", values, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcNotIn(List<BigDecimal> values) {
            addCriterion("infrastructure_fee_zc not in", values, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_zc between", value1, value2, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andInfrastructureFeeZcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("infrastructure_fee_zc not between", value1, value2, "infrastructureFeeZc");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIsNull() {
            addCriterion("creater_id is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIsNotNull() {
            addCriterion("creater_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdEqualTo(String value) {
            addCriterion("creater_id =", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotEqualTo(String value) {
            addCriterion("creater_id <>", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThan(String value) {
            addCriterion("creater_id >", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThanOrEqualTo(String value) {
            addCriterion("creater_id >=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThan(String value) {
            addCriterion("creater_id <", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThanOrEqualTo(String value) {
            addCriterion("creater_id <=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLike(String value) {
            addCriterion("creater_id like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotLike(String value) {
            addCriterion("creater_id not like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIn(List<String> values) {
            addCriterion("creater_id in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotIn(List<String> values) {
            addCriterion("creater_id not in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdBetween(String value1, String value2) {
            addCriterion("creater_id between", value1, value2, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotBetween(String value1, String value2) {
            addCriterion("creater_id not between", value1, value2, "createrId");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNull() {
            addCriterion("modifier_id is null");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNotNull() {
            addCriterion("modifier_id is not null");
            return (Criteria) this;
        }

        public Criteria andModifierIdEqualTo(String value) {
            addCriterion("modifier_id =", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotEqualTo(String value) {
            addCriterion("modifier_id <>", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThan(String value) {
            addCriterion("modifier_id >", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThanOrEqualTo(String value) {
            addCriterion("modifier_id >=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThan(String value) {
            addCriterion("modifier_id <", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThanOrEqualTo(String value) {
            addCriterion("modifier_id <=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLike(String value) {
            addCriterion("modifier_id like", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotLike(String value) {
            addCriterion("modifier_id not like", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdIn(List<String> values) {
            addCriterion("modifier_id in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotIn(List<String> values) {
            addCriterion("modifier_id not in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdBetween(String value1, String value2) {
            addCriterion("modifier_id between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotBetween(String value1, String value2) {
            addCriterion("modifier_id not between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andIsRootIsNull() {
            addCriterion("is_root is null");
            return (Criteria) this;
        }

        public Criteria andIsRootIsNotNull() {
            addCriterion("is_root is not null");
            return (Criteria) this;
        }

        public Criteria andIsRootEqualTo(Integer value) {
            addCriterion("is_root =", value, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootNotEqualTo(Integer value) {
            addCriterion("is_root <>", value, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootGreaterThan(Integer value) {
            addCriterion("is_root >", value, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_root >=", value, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootLessThan(Integer value) {
            addCriterion("is_root <", value, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootLessThanOrEqualTo(Integer value) {
            addCriterion("is_root <=", value, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootIn(List<Integer> values) {
            addCriterion("is_root in", values, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootNotIn(List<Integer> values) {
            addCriterion("is_root not in", values, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootBetween(Integer value1, Integer value2) {
            addCriterion("is_root between", value1, value2, "isRoot");
            return (Criteria) this;
        }

        public Criteria andIsRootNotBetween(Integer value1, Integer value2) {
            addCriterion("is_root not between", value1, value2, "isRoot");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Integer value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Integer value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Integer value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Integer value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Integer> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Integer> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Integer value1, Integer value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}