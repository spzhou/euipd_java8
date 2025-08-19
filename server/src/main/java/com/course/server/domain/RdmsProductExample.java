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

public class RdmsProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsProductExample() {
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

        public Criteria andProductCodeIsNull() {
            addCriterion("product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("product_code not between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andBudgetIsNull() {
            addCriterion("budget is null");
            return (Criteria) this;
        }

        public Criteria andBudgetIsNotNull() {
            addCriterion("budget is not null");
            return (Criteria) this;
        }

        public Criteria andBudgetEqualTo(BigDecimal value) {
            addCriterion("budget =", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetNotEqualTo(BigDecimal value) {
            addCriterion("budget <>", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetGreaterThan(BigDecimal value) {
            addCriterion("budget >", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("budget >=", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetLessThan(BigDecimal value) {
            addCriterion("budget <", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetLessThanOrEqualTo(BigDecimal value) {
            addCriterion("budget <=", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetIn(List<BigDecimal> values) {
            addCriterion("budget in", values, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetNotIn(List<BigDecimal> values) {
            addCriterion("budget not in", values, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("budget between", value1, value2, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("budget not between", value1, value2, "budget");
            return (Criteria) this;
        }

        public Criteria andIpmtIdIsNull() {
            addCriterion("ipmt_id is null");
            return (Criteria) this;
        }

        public Criteria andIpmtIdIsNotNull() {
            addCriterion("ipmt_id is not null");
            return (Criteria) this;
        }

        public Criteria andIpmtIdEqualTo(String value) {
            addCriterion("ipmt_id =", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdNotEqualTo(String value) {
            addCriterion("ipmt_id <>", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdGreaterThan(String value) {
            addCriterion("ipmt_id >", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdGreaterThanOrEqualTo(String value) {
            addCriterion("ipmt_id >=", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdLessThan(String value) {
            addCriterion("ipmt_id <", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdLessThanOrEqualTo(String value) {
            addCriterion("ipmt_id <=", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdLike(String value) {
            addCriterion("ipmt_id like", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdNotLike(String value) {
            addCriterion("ipmt_id not like", value, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdIn(List<String> values) {
            addCriterion("ipmt_id in", values, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdNotIn(List<String> values) {
            addCriterion("ipmt_id not in", values, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdBetween(String value1, String value2) {
            addCriterion("ipmt_id between", value1, value2, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andIpmtIdNotBetween(String value1, String value2) {
            addCriterion("ipmt_id not between", value1, value2, "ipmtId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdIsNull() {
            addCriterion("product_manager_id is null");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdIsNotNull() {
            addCriterion("product_manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdEqualTo(String value) {
            addCriterion("product_manager_id =", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdNotEqualTo(String value) {
            addCriterion("product_manager_id <>", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdGreaterThan(String value) {
            addCriterion("product_manager_id >", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_manager_id >=", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdLessThan(String value) {
            addCriterion("product_manager_id <", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdLessThanOrEqualTo(String value) {
            addCriterion("product_manager_id <=", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdLike(String value) {
            addCriterion("product_manager_id like", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdNotLike(String value) {
            addCriterion("product_manager_id not like", value, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdIn(List<String> values) {
            addCriterion("product_manager_id in", values, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdNotIn(List<String> values) {
            addCriterion("product_manager_id not in", values, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdBetween(String value1, String value2) {
            addCriterion("product_manager_id between", value1, value2, "productManagerId");
            return (Criteria) this;
        }

        public Criteria andProductManagerIdNotBetween(String value1, String value2) {
            addCriterion("product_manager_id not between", value1, value2, "productManagerId");
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

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSetupTimeIsNull() {
            addCriterion("setup_time is null");
            return (Criteria) this;
        }

        public Criteria andSetupTimeIsNotNull() {
            addCriterion("setup_time is not null");
            return (Criteria) this;
        }

        public Criteria andSetupTimeEqualTo(Date value) {
            addCriterion("setup_time =", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeNotEqualTo(Date value) {
            addCriterion("setup_time <>", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeGreaterThan(Date value) {
            addCriterion("setup_time >", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("setup_time >=", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeLessThan(Date value) {
            addCriterion("setup_time <", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeLessThanOrEqualTo(Date value) {
            addCriterion("setup_time <=", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeIn(List<Date> values) {
            addCriterion("setup_time in", values, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeNotIn(List<Date> values) {
            addCriterion("setup_time not in", values, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeBetween(Date value1, Date value2) {
            addCriterion("setup_time between", value1, value2, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeNotBetween(Date value1, Date value2) {
            addCriterion("setup_time not between", value1, value2, "setupTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanIsNull() {
            addCriterion("release_time_plan is null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanIsNotNull() {
            addCriterion("release_time_plan is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanEqualTo(Date value) {
            addCriterion("release_time_plan =", value, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanNotEqualTo(Date value) {
            addCriterion("release_time_plan <>", value, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanGreaterThan(Date value) {
            addCriterion("release_time_plan >", value, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanGreaterThanOrEqualTo(Date value) {
            addCriterion("release_time_plan >=", value, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanLessThan(Date value) {
            addCriterion("release_time_plan <", value, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanLessThanOrEqualTo(Date value) {
            addCriterion("release_time_plan <=", value, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanIn(List<Date> values) {
            addCriterion("release_time_plan in", values, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanNotIn(List<Date> values) {
            addCriterion("release_time_plan not in", values, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanBetween(Date value1, Date value2) {
            addCriterion("release_time_plan between", value1, value2, "releaseTimePlan");
            return (Criteria) this;
        }

        public Criteria andReleaseTimePlanNotBetween(Date value1, Date value2) {
            addCriterion("release_time_plan not between", value1, value2, "releaseTimePlan");
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