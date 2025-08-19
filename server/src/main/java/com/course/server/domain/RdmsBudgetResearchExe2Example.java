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

public class RdmsBudgetResearchExe2Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsBudgetResearchExe2Example() {
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

        public Criteria andPayClassifyIsNull() {
            addCriterion("pay_classify is null");
            return (Criteria) this;
        }

        public Criteria andPayClassifyIsNotNull() {
            addCriterion("pay_classify is not null");
            return (Criteria) this;
        }

        public Criteria andPayClassifyEqualTo(String value) {
            addCriterion("pay_classify =", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyNotEqualTo(String value) {
            addCriterion("pay_classify <>", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyGreaterThan(String value) {
            addCriterion("pay_classify >", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyGreaterThanOrEqualTo(String value) {
            addCriterion("pay_classify >=", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyLessThan(String value) {
            addCriterion("pay_classify <", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyLessThanOrEqualTo(String value) {
            addCriterion("pay_classify <=", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyLike(String value) {
            addCriterion("pay_classify like", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyNotLike(String value) {
            addCriterion("pay_classify not like", value, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyIn(List<String> values) {
            addCriterion("pay_classify in", values, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyNotIn(List<String> values) {
            addCriterion("pay_classify not in", values, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyBetween(String value1, String value2) {
            addCriterion("pay_classify between", value1, value2, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayClassifyNotBetween(String value1, String value2) {
            addCriterion("pay_classify not between", value1, value2, "payClassify");
            return (Criteria) this;
        }

        public Criteria andPayIdIsNull() {
            addCriterion("pay_id is null");
            return (Criteria) this;
        }

        public Criteria andPayIdIsNotNull() {
            addCriterion("pay_id is not null");
            return (Criteria) this;
        }

        public Criteria andPayIdEqualTo(String value) {
            addCriterion("pay_id =", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotEqualTo(String value) {
            addCriterion("pay_id <>", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdGreaterThan(String value) {
            addCriterion("pay_id >", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdGreaterThanOrEqualTo(String value) {
            addCriterion("pay_id >=", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdLessThan(String value) {
            addCriterion("pay_id <", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdLessThanOrEqualTo(String value) {
            addCriterion("pay_id <=", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdLike(String value) {
            addCriterion("pay_id like", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotLike(String value) {
            addCriterion("pay_id not like", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdIn(List<String> values) {
            addCriterion("pay_id in", values, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotIn(List<String> values) {
            addCriterion("pay_id not in", values, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdBetween(String value1, String value2) {
            addCriterion("pay_id between", value1, value2, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotBetween(String value1, String value2) {
            addCriterion("pay_id not between", value1, value2, "payId");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("serial_no is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("serial_no =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("serial_no <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("serial_no >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("serial_no >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("serial_no <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("serial_no <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("serial_no like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("serial_no not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("serial_no in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("serial_no not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("serial_no between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("serial_no not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andExecutorIdIsNull() {
            addCriterion("executor_id is null");
            return (Criteria) this;
        }

        public Criteria andExecutorIdIsNotNull() {
            addCriterion("executor_id is not null");
            return (Criteria) this;
        }

        public Criteria andExecutorIdEqualTo(String value) {
            addCriterion("executor_id =", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotEqualTo(String value) {
            addCriterion("executor_id <>", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdGreaterThan(String value) {
            addCriterion("executor_id >", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdGreaterThanOrEqualTo(String value) {
            addCriterion("executor_id >=", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdLessThan(String value) {
            addCriterion("executor_id <", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdLessThanOrEqualTo(String value) {
            addCriterion("executor_id <=", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdLike(String value) {
            addCriterion("executor_id like", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotLike(String value) {
            addCriterion("executor_id not like", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdIn(List<String> values) {
            addCriterion("executor_id in", values, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotIn(List<String> values) {
            addCriterion("executor_id not in", values, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdBetween(String value1, String value2) {
            addCriterion("executor_id between", value1, value2, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotBetween(String value1, String value2) {
            addCriterion("executor_id not between", value1, value2, "executorId");
            return (Criteria) this;
        }

        public Criteria andClassifyIsNull() {
            addCriterion("classify is null");
            return (Criteria) this;
        }

        public Criteria andClassifyIsNotNull() {
            addCriterion("classify is not null");
            return (Criteria) this;
        }

        public Criteria andClassifyEqualTo(String value) {
            addCriterion("classify =", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyNotEqualTo(String value) {
            addCriterion("classify <>", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyGreaterThan(String value) {
            addCriterion("classify >", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyGreaterThanOrEqualTo(String value) {
            addCriterion("classify >=", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyLessThan(String value) {
            addCriterion("classify <", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyLessThanOrEqualTo(String value) {
            addCriterion("classify <=", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyLike(String value) {
            addCriterion("classify like", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyNotLike(String value) {
            addCriterion("classify not like", value, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyIn(List<String> values) {
            addCriterion("classify in", values, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyNotIn(List<String> values) {
            addCriterion("classify not in", values, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyBetween(String value1, String value2) {
            addCriterion("classify between", value1, value2, "classify");
            return (Criteria) this;
        }

        public Criteria andClassifyNotBetween(String value1, String value2) {
            addCriterion("classify not between", value1, value2, "classify");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceIsNull() {
            addCriterion("capital_source is null");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceIsNotNull() {
            addCriterion("capital_source is not null");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceEqualTo(String value) {
            addCriterion("capital_source =", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceNotEqualTo(String value) {
            addCriterion("capital_source <>", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceGreaterThan(String value) {
            addCriterion("capital_source >", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceGreaterThanOrEqualTo(String value) {
            addCriterion("capital_source >=", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceLessThan(String value) {
            addCriterion("capital_source <", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceLessThanOrEqualTo(String value) {
            addCriterion("capital_source <=", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceLike(String value) {
            addCriterion("capital_source like", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceNotLike(String value) {
            addCriterion("capital_source not like", value, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceIn(List<String> values) {
            addCriterion("capital_source in", values, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceNotIn(List<String> values) {
            addCriterion("capital_source not in", values, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceBetween(String value1, String value2) {
            addCriterion("capital_source between", value1, value2, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andCapitalSourceNotBetween(String value1, String value2) {
            addCriterion("capital_source not between", value1, value2, "capitalSource");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueIsNull() {
            addCriterion("payment_statue is null");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueIsNotNull() {
            addCriterion("payment_statue is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueEqualTo(String value) {
            addCriterion("payment_statue =", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueNotEqualTo(String value) {
            addCriterion("payment_statue <>", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueGreaterThan(String value) {
            addCriterion("payment_statue >", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueGreaterThanOrEqualTo(String value) {
            addCriterion("payment_statue >=", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueLessThan(String value) {
            addCriterion("payment_statue <", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueLessThanOrEqualTo(String value) {
            addCriterion("payment_statue <=", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueLike(String value) {
            addCriterion("payment_statue like", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueNotLike(String value) {
            addCriterion("payment_statue not like", value, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueIn(List<String> values) {
            addCriterion("payment_statue in", values, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueNotIn(List<String> values) {
            addCriterion("payment_statue not in", values, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueBetween(String value1, String value2) {
            addCriterion("payment_statue between", value1, value2, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentStatueNotBetween(String value1, String value2) {
            addCriterion("payment_statue not between", value1, value2, "paymentStatue");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIsNull() {
            addCriterion("payment_time is null");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIsNotNull() {
            addCriterion("payment_time is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeEqualTo(Date value) {
            addCriterion("payment_time =", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotEqualTo(Date value) {
            addCriterion("payment_time <>", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeGreaterThan(Date value) {
            addCriterion("payment_time >", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("payment_time >=", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeLessThan(Date value) {
            addCriterion("payment_time <", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeLessThanOrEqualTo(Date value) {
            addCriterion("payment_time <=", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIn(List<Date> values) {
            addCriterion("payment_time in", values, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotIn(List<Date> values) {
            addCriterion("payment_time not in", values, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeBetween(Date value1, Date value2) {
            addCriterion("payment_time between", value1, value2, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotBetween(Date value1, Date value2) {
            addCriterion("payment_time not between", value1, value2, "paymentTime");
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