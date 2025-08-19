/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RdmsGanttExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsGanttExample() {
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

        public Criteria andParentIsNull() {
            addCriterion("parent is null");
            return (Criteria) this;
        }

        public Criteria andParentIsNotNull() {
            addCriterion("parent is not null");
            return (Criteria) this;
        }

        public Criteria andParentEqualTo(String value) {
            addCriterion("parent =", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotEqualTo(String value) {
            addCriterion("parent <>", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThan(String value) {
            addCriterion("parent >", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThanOrEqualTo(String value) {
            addCriterion("parent >=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThan(String value) {
            addCriterion("parent <", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThanOrEqualTo(String value) {
            addCriterion("parent <=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLike(String value) {
            addCriterion("parent like", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotLike(String value) {
            addCriterion("parent not like", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentIn(List<String> values) {
            addCriterion("parent in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotIn(List<String> values) {
            addCriterion("parent not in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentBetween(String value1, String value2) {
            addCriterion("parent between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotBetween(String value1, String value2) {
            addCriterion("parent not between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andJobitemIdIsNull() {
            addCriterion("jobitem_id is null");
            return (Criteria) this;
        }

        public Criteria andJobitemIdIsNotNull() {
            addCriterion("jobitem_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobitemIdEqualTo(String value) {
            addCriterion("jobitem_id =", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdNotEqualTo(String value) {
            addCriterion("jobitem_id <>", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdGreaterThan(String value) {
            addCriterion("jobitem_id >", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdGreaterThanOrEqualTo(String value) {
            addCriterion("jobitem_id >=", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdLessThan(String value) {
            addCriterion("jobitem_id <", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdLessThanOrEqualTo(String value) {
            addCriterion("jobitem_id <=", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdLike(String value) {
            addCriterion("jobitem_id like", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdNotLike(String value) {
            addCriterion("jobitem_id not like", value, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdIn(List<String> values) {
            addCriterion("jobitem_id in", values, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdNotIn(List<String> values) {
            addCriterion("jobitem_id not in", values, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdBetween(String value1, String value2) {
            addCriterion("jobitem_id between", value1, value2, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andJobitemIdNotBetween(String value1, String value2) {
            addCriterion("jobitem_id not between", value1, value2, "jobitemId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIsNull() {
            addCriterion("character_id is null");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIsNotNull() {
            addCriterion("character_id is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterIdEqualTo(String value) {
            addCriterion("character_id =", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotEqualTo(String value) {
            addCriterion("character_id <>", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdGreaterThan(String value) {
            addCriterion("character_id >", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdGreaterThanOrEqualTo(String value) {
            addCriterion("character_id >=", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLessThan(String value) {
            addCriterion("character_id <", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLessThanOrEqualTo(String value) {
            addCriterion("character_id <=", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLike(String value) {
            addCriterion("character_id like", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotLike(String value) {
            addCriterion("character_id not like", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIn(List<String> values) {
            addCriterion("character_id in", values, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotIn(List<String> values) {
            addCriterion("character_id not in", values, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdBetween(String value1, String value2) {
            addCriterion("character_id between", value1, value2, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotBetween(String value1, String value2) {
            addCriterion("character_id not between", value1, value2, "characterId");
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

        public Criteria andTextIsNull() {
            addCriterion("`text` is null");
            return (Criteria) this;
        }

        public Criteria andTextIsNotNull() {
            addCriterion("`text` is not null");
            return (Criteria) this;
        }

        public Criteria andTextEqualTo(String value) {
            addCriterion("`text` =", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotEqualTo(String value) {
            addCriterion("`text` <>", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThan(String value) {
            addCriterion("`text` >", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThanOrEqualTo(String value) {
            addCriterion("`text` >=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThan(String value) {
            addCriterion("`text` <", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThanOrEqualTo(String value) {
            addCriterion("`text` <=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLike(String value) {
            addCriterion("`text` like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotLike(String value) {
            addCriterion("`text` not like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextIn(List<String> values) {
            addCriterion("`text` in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotIn(List<String> values) {
            addCriterion("`text` not in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextBetween(String value1, String value2) {
            addCriterion("`text` between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotBetween(String value1, String value2) {
            addCriterion("`text` not between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Integer value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Integer value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Integer value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Integer value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Integer value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Integer> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Integer> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Integer value1, Integer value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andManhourIsNull() {
            addCriterion("manhour is null");
            return (Criteria) this;
        }

        public Criteria andManhourIsNotNull() {
            addCriterion("manhour is not null");
            return (Criteria) this;
        }

        public Criteria andManhourEqualTo(Double value) {
            addCriterion("manhour =", value, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourNotEqualTo(Double value) {
            addCriterion("manhour <>", value, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourGreaterThan(Double value) {
            addCriterion("manhour >", value, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourGreaterThanOrEqualTo(Double value) {
            addCriterion("manhour >=", value, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourLessThan(Double value) {
            addCriterion("manhour <", value, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourLessThanOrEqualTo(Double value) {
            addCriterion("manhour <=", value, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourIn(List<Double> values) {
            addCriterion("manhour in", values, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourNotIn(List<Double> values) {
            addCriterion("manhour not in", values, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourBetween(Double value1, Double value2) {
            addCriterion("manhour between", value1, value2, "manhour");
            return (Criteria) this;
        }

        public Criteria andManhourNotBetween(Double value1, Double value2) {
            addCriterion("manhour not between", value1, value2, "manhour");
            return (Criteria) this;
        }

        public Criteria andProgressIsNull() {
            addCriterion("progress is null");
            return (Criteria) this;
        }

        public Criteria andProgressIsNotNull() {
            addCriterion("progress is not null");
            return (Criteria) this;
        }

        public Criteria andProgressEqualTo(Double value) {
            addCriterion("progress =", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotEqualTo(Double value) {
            addCriterion("progress <>", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThan(Double value) {
            addCriterion("progress >", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThanOrEqualTo(Double value) {
            addCriterion("progress >=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThan(Double value) {
            addCriterion("progress <", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThanOrEqualTo(Double value) {
            addCriterion("progress <=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressIn(List<Double> values) {
            addCriterion("progress in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotIn(List<Double> values) {
            addCriterion("progress not in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressBetween(Double value1, Double value2) {
            addCriterion("progress between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotBetween(Double value1, Double value2) {
            addCriterion("progress not between", value1, value2, "progress");
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

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andOpenIsNull() {
            addCriterion("`open` is null");
            return (Criteria) this;
        }

        public Criteria andOpenIsNotNull() {
            addCriterion("`open` is not null");
            return (Criteria) this;
        }

        public Criteria andOpenEqualTo(Integer value) {
            addCriterion("`open` =", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenNotEqualTo(Integer value) {
            addCriterion("`open` <>", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenGreaterThan(Integer value) {
            addCriterion("`open` >", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenGreaterThanOrEqualTo(Integer value) {
            addCriterion("`open` >=", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenLessThan(Integer value) {
            addCriterion("`open` <", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenLessThanOrEqualTo(Integer value) {
            addCriterion("`open` <=", value, "open");
            return (Criteria) this;
        }

        public Criteria andOpenIn(List<Integer> values) {
            addCriterion("`open` in", values, "open");
            return (Criteria) this;
        }

        public Criteria andOpenNotIn(List<Integer> values) {
            addCriterion("`open` not in", values, "open");
            return (Criteria) this;
        }

        public Criteria andOpenBetween(Integer value1, Integer value2) {
            addCriterion("`open` between", value1, value2, "open");
            return (Criteria) this;
        }

        public Criteria andOpenNotBetween(Integer value1, Integer value2) {
            addCriterion("`open` not between", value1, value2, "open");
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