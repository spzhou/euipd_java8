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

public class RdmsCharacter3Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsCharacter3Example() {
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

        public Criteria andCharacterSerialIsNull() {
            addCriterion("character_serial is null");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialIsNotNull() {
            addCriterion("character_serial is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialEqualTo(String value) {
            addCriterion("character_serial =", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialNotEqualTo(String value) {
            addCriterion("character_serial <>", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialGreaterThan(String value) {
            addCriterion("character_serial >", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialGreaterThanOrEqualTo(String value) {
            addCriterion("character_serial >=", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialLessThan(String value) {
            addCriterion("character_serial <", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialLessThanOrEqualTo(String value) {
            addCriterion("character_serial <=", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialLike(String value) {
            addCriterion("character_serial like", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialNotLike(String value) {
            addCriterion("character_serial not like", value, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialIn(List<String> values) {
            addCriterion("character_serial in", values, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialNotIn(List<String> values) {
            addCriterion("character_serial not in", values, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialBetween(String value1, String value2) {
            addCriterion("character_serial between", value1, value2, "characterSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterSerialNotBetween(String value1, String value2) {
            addCriterion("character_serial not between", value1, value2, "characterSerial");
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

        public Criteria andDeepIsNull() {
            addCriterion("deep is null");
            return (Criteria) this;
        }

        public Criteria andDeepIsNotNull() {
            addCriterion("deep is not null");
            return (Criteria) this;
        }

        public Criteria andDeepEqualTo(Integer value) {
            addCriterion("deep =", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepNotEqualTo(Integer value) {
            addCriterion("deep <>", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepGreaterThan(Integer value) {
            addCriterion("deep >", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepGreaterThanOrEqualTo(Integer value) {
            addCriterion("deep >=", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepLessThan(Integer value) {
            addCriterion("deep <", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepLessThanOrEqualTo(Integer value) {
            addCriterion("deep <=", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepIn(List<Integer> values) {
            addCriterion("deep in", values, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepNotIn(List<Integer> values) {
            addCriterion("deep not in", values, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepBetween(Integer value1, Integer value2) {
            addCriterion("deep between", value1, value2, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepNotBetween(Integer value1, Integer value2) {
            addCriterion("deep not between", value1, value2, "deep");
            return (Criteria) this;
        }

        public Criteria andCharacterNameIsNull() {
            addCriterion("character_name is null");
            return (Criteria) this;
        }

        public Criteria andCharacterNameIsNotNull() {
            addCriterion("character_name is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterNameEqualTo(String value) {
            addCriterion("character_name =", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameNotEqualTo(String value) {
            addCriterion("character_name <>", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameGreaterThan(String value) {
            addCriterion("character_name >", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameGreaterThanOrEqualTo(String value) {
            addCriterion("character_name >=", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameLessThan(String value) {
            addCriterion("character_name <", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameLessThanOrEqualTo(String value) {
            addCriterion("character_name <=", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameLike(String value) {
            addCriterion("character_name like", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameNotLike(String value) {
            addCriterion("character_name not like", value, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameIn(List<String> values) {
            addCriterion("character_name in", values, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameNotIn(List<String> values) {
            addCriterion("character_name not in", values, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameBetween(String value1, String value2) {
            addCriterion("character_name between", value1, value2, "characterName");
            return (Criteria) this;
        }

        public Criteria andCharacterNameNotBetween(String value1, String value2) {
            addCriterion("character_name not between", value1, value2, "characterName");
            return (Criteria) this;
        }

        public Criteria andIterationVersionIsNull() {
            addCriterion("iteration_version is null");
            return (Criteria) this;
        }

        public Criteria andIterationVersionIsNotNull() {
            addCriterion("iteration_version is not null");
            return (Criteria) this;
        }

        public Criteria andIterationVersionEqualTo(Integer value) {
            addCriterion("iteration_version =", value, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionNotEqualTo(Integer value) {
            addCriterion("iteration_version <>", value, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionGreaterThan(Integer value) {
            addCriterion("iteration_version >", value, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("iteration_version >=", value, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionLessThan(Integer value) {
            addCriterion("iteration_version <", value, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionLessThanOrEqualTo(Integer value) {
            addCriterion("iteration_version <=", value, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionIn(List<Integer> values) {
            addCriterion("iteration_version in", values, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionNotIn(List<Integer> values) {
            addCriterion("iteration_version not in", values, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionBetween(Integer value1, Integer value2) {
            addCriterion("iteration_version between", value1, value2, "iterationVersion");
            return (Criteria) this;
        }

        public Criteria andIterationVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("iteration_version not between", value1, value2, "iterationVersion");
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

        public Criteria andPreProjectIdIsNull() {
            addCriterion("pre_project_id is null");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdIsNotNull() {
            addCriterion("pre_project_id is not null");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdEqualTo(String value) {
            addCriterion("pre_project_id =", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdNotEqualTo(String value) {
            addCriterion("pre_project_id <>", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdGreaterThan(String value) {
            addCriterion("pre_project_id >", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("pre_project_id >=", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdLessThan(String value) {
            addCriterion("pre_project_id <", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdLessThanOrEqualTo(String value) {
            addCriterion("pre_project_id <=", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdLike(String value) {
            addCriterion("pre_project_id like", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdNotLike(String value) {
            addCriterion("pre_project_id not like", value, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdIn(List<String> values) {
            addCriterion("pre_project_id in", values, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdNotIn(List<String> values) {
            addCriterion("pre_project_id not in", values, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdBetween(String value1, String value2) {
            addCriterion("pre_project_id between", value1, value2, "preProjectId");
            return (Criteria) this;
        }

        public Criteria andPreProjectIdNotBetween(String value1, String value2) {
            addCriterion("pre_project_id not between", value1, value2, "preProjectId");
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

        public Criteria andMilestoneIdIsNull() {
            addCriterion("milestone_id is null");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdIsNotNull() {
            addCriterion("milestone_id is not null");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdEqualTo(String value) {
            addCriterion("milestone_id =", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdNotEqualTo(String value) {
            addCriterion("milestone_id <>", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdGreaterThan(String value) {
            addCriterion("milestone_id >", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdGreaterThanOrEqualTo(String value) {
            addCriterion("milestone_id >=", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdLessThan(String value) {
            addCriterion("milestone_id <", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdLessThanOrEqualTo(String value) {
            addCriterion("milestone_id <=", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdLike(String value) {
            addCriterion("milestone_id like", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdNotLike(String value) {
            addCriterion("milestone_id not like", value, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdIn(List<String> values) {
            addCriterion("milestone_id in", values, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdNotIn(List<String> values) {
            addCriterion("milestone_id not in", values, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdBetween(String value1, String value2) {
            addCriterion("milestone_id between", value1, value2, "milestoneId");
            return (Criteria) this;
        }

        public Criteria andMilestoneIdNotBetween(String value1, String value2) {
            addCriterion("milestone_id not between", value1, value2, "milestoneId");
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

        public Criteria andWriterIdIsNull() {
            addCriterion("writer_id is null");
            return (Criteria) this;
        }

        public Criteria andWriterIdIsNotNull() {
            addCriterion("writer_id is not null");
            return (Criteria) this;
        }

        public Criteria andWriterIdEqualTo(String value) {
            addCriterion("writer_id =", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdNotEqualTo(String value) {
            addCriterion("writer_id <>", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdGreaterThan(String value) {
            addCriterion("writer_id >", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdGreaterThanOrEqualTo(String value) {
            addCriterion("writer_id >=", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdLessThan(String value) {
            addCriterion("writer_id <", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdLessThanOrEqualTo(String value) {
            addCriterion("writer_id <=", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdLike(String value) {
            addCriterion("writer_id like", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdNotLike(String value) {
            addCriterion("writer_id not like", value, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdIn(List<String> values) {
            addCriterion("writer_id in", values, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdNotIn(List<String> values) {
            addCriterion("writer_id not in", values, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdBetween(String value1, String value2) {
            addCriterion("writer_id between", value1, value2, "writerId");
            return (Criteria) this;
        }

        public Criteria andWriterIdNotBetween(String value1, String value2) {
            addCriterion("writer_id not between", value1, value2, "writerId");
            return (Criteria) this;
        }

        public Criteria andNextNodeIsNull() {
            addCriterion("next_node is null");
            return (Criteria) this;
        }

        public Criteria andNextNodeIsNotNull() {
            addCriterion("next_node is not null");
            return (Criteria) this;
        }

        public Criteria andNextNodeEqualTo(String value) {
            addCriterion("next_node =", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotEqualTo(String value) {
            addCriterion("next_node <>", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeGreaterThan(String value) {
            addCriterion("next_node >", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeGreaterThanOrEqualTo(String value) {
            addCriterion("next_node >=", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeLessThan(String value) {
            addCriterion("next_node <", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeLessThanOrEqualTo(String value) {
            addCriterion("next_node <=", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeLike(String value) {
            addCriterion("next_node like", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotLike(String value) {
            addCriterion("next_node not like", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeIn(List<String> values) {
            addCriterion("next_node in", values, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotIn(List<String> values) {
            addCriterion("next_node not in", values, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeBetween(String value1, String value2) {
            addCriterion("next_node between", value1, value2, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotBetween(String value1, String value2) {
            addCriterion("next_node not between", value1, value2, "nextNode");
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

        public Criteria andAuxStatusIsNull() {
            addCriterion("aux_status is null");
            return (Criteria) this;
        }

        public Criteria andAuxStatusIsNotNull() {
            addCriterion("aux_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuxStatusEqualTo(String value) {
            addCriterion("aux_status =", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotEqualTo(String value) {
            addCriterion("aux_status <>", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusGreaterThan(String value) {
            addCriterion("aux_status >", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusGreaterThanOrEqualTo(String value) {
            addCriterion("aux_status >=", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusLessThan(String value) {
            addCriterion("aux_status <", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusLessThanOrEqualTo(String value) {
            addCriterion("aux_status <=", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusLike(String value) {
            addCriterion("aux_status like", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotLike(String value) {
            addCriterion("aux_status not like", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusIn(List<String> values) {
            addCriterion("aux_status in", values, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotIn(List<String> values) {
            addCriterion("aux_status not in", values, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusBetween(String value1, String value2) {
            addCriterion("aux_status between", value1, value2, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotBetween(String value1, String value2) {
            addCriterion("aux_status not between", value1, value2, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeIsNull() {
            addCriterion("jobitem_type is null");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeIsNotNull() {
            addCriterion("jobitem_type is not null");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeEqualTo(String value) {
            addCriterion("jobitem_type =", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeNotEqualTo(String value) {
            addCriterion("jobitem_type <>", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeGreaterThan(String value) {
            addCriterion("jobitem_type >", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeGreaterThanOrEqualTo(String value) {
            addCriterion("jobitem_type >=", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeLessThan(String value) {
            addCriterion("jobitem_type <", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeLessThanOrEqualTo(String value) {
            addCriterion("jobitem_type <=", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeLike(String value) {
            addCriterion("jobitem_type like", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeNotLike(String value) {
            addCriterion("jobitem_type not like", value, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeIn(List<String> values) {
            addCriterion("jobitem_type in", values, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeNotIn(List<String> values) {
            addCriterion("jobitem_type not in", values, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeBetween(String value1, String value2) {
            addCriterion("jobitem_type between", value1, value2, "jobitemType");
            return (Criteria) this;
        }

        public Criteria andJobitemTypeNotBetween(String value1, String value2) {
            addCriterion("jobitem_type not between", value1, value2, "jobitemType");
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

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(String value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(String value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(String value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(String value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(String value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLike(String value) {
            addCriterion("project_type like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotLike(String value) {
            addCriterion("project_type not like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<String> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<String> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(String value1, String value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(String value1, String value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
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

        public Criteria andFileListStrIsNull() {
            addCriterion("file_list_str is null");
            return (Criteria) this;
        }

        public Criteria andFileListStrIsNotNull() {
            addCriterion("file_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andFileListStrEqualTo(String value) {
            addCriterion("file_list_str =", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrNotEqualTo(String value) {
            addCriterion("file_list_str <>", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrGreaterThan(String value) {
            addCriterion("file_list_str >", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrGreaterThanOrEqualTo(String value) {
            addCriterion("file_list_str >=", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrLessThan(String value) {
            addCriterion("file_list_str <", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrLessThanOrEqualTo(String value) {
            addCriterion("file_list_str <=", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrLike(String value) {
            addCriterion("file_list_str like", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrNotLike(String value) {
            addCriterion("file_list_str not like", value, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrIn(List<String> values) {
            addCriterion("file_list_str in", values, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrNotIn(List<String> values) {
            addCriterion("file_list_str not in", values, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrBetween(String value1, String value2) {
            addCriterion("file_list_str between", value1, value2, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andFileListStrNotBetween(String value1, String value2) {
            addCriterion("file_list_str not between", value1, value2, "fileListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrIsNull() {
            addCriterion("demand_list_str is null");
            return (Criteria) this;
        }

        public Criteria andDemandListStrIsNotNull() {
            addCriterion("demand_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andDemandListStrEqualTo(String value) {
            addCriterion("demand_list_str =", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrNotEqualTo(String value) {
            addCriterion("demand_list_str <>", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrGreaterThan(String value) {
            addCriterion("demand_list_str >", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrGreaterThanOrEqualTo(String value) {
            addCriterion("demand_list_str >=", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrLessThan(String value) {
            addCriterion("demand_list_str <", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrLessThanOrEqualTo(String value) {
            addCriterion("demand_list_str <=", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrLike(String value) {
            addCriterion("demand_list_str like", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrNotLike(String value) {
            addCriterion("demand_list_str not like", value, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrIn(List<String> values) {
            addCriterion("demand_list_str in", values, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrNotIn(List<String> values) {
            addCriterion("demand_list_str not in", values, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrBetween(String value1, String value2) {
            addCriterion("demand_list_str between", value1, value2, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andDemandListStrNotBetween(String value1, String value2) {
            addCriterion("demand_list_str not between", value1, value2, "demandListStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrIsNull() {
            addCriterion("merged_character_id_str is null");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrIsNotNull() {
            addCriterion("merged_character_id_str is not null");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrEqualTo(String value) {
            addCriterion("merged_character_id_str =", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrNotEqualTo(String value) {
            addCriterion("merged_character_id_str <>", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrGreaterThan(String value) {
            addCriterion("merged_character_id_str >", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrGreaterThanOrEqualTo(String value) {
            addCriterion("merged_character_id_str >=", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrLessThan(String value) {
            addCriterion("merged_character_id_str <", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrLessThanOrEqualTo(String value) {
            addCriterion("merged_character_id_str <=", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrLike(String value) {
            addCriterion("merged_character_id_str like", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrNotLike(String value) {
            addCriterion("merged_character_id_str not like", value, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrIn(List<String> values) {
            addCriterion("merged_character_id_str in", values, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrNotIn(List<String> values) {
            addCriterion("merged_character_id_str not in", values, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrBetween(String value1, String value2) {
            addCriterion("merged_character_id_str between", value1, value2, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergedCharacterIdStrNotBetween(String value1, String value2) {
            addCriterion("merged_character_id_str not between", value1, value2, "mergedCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrIsNull() {
            addCriterion("module_id_list_str is null");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrIsNotNull() {
            addCriterion("module_id_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrEqualTo(String value) {
            addCriterion("module_id_list_str =", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrNotEqualTo(String value) {
            addCriterion("module_id_list_str <>", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrGreaterThan(String value) {
            addCriterion("module_id_list_str >", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrGreaterThanOrEqualTo(String value) {
            addCriterion("module_id_list_str >=", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrLessThan(String value) {
            addCriterion("module_id_list_str <", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrLessThanOrEqualTo(String value) {
            addCriterion("module_id_list_str <=", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrLike(String value) {
            addCriterion("module_id_list_str like", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrNotLike(String value) {
            addCriterion("module_id_list_str not like", value, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrIn(List<String> values) {
            addCriterion("module_id_list_str in", values, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrNotIn(List<String> values) {
            addCriterion("module_id_list_str not in", values, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrBetween(String value1, String value2) {
            addCriterion("module_id_list_str between", value1, value2, "moduleIdListStr");
            return (Criteria) this;
        }

        public Criteria andModuleIdListStrNotBetween(String value1, String value2) {
            addCriterion("module_id_list_str not between", value1, value2, "moduleIdListStr");
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

        public Criteria andSubmitTimeIsNull() {
            addCriterion("submit_time is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNotNull() {
            addCriterion("submit_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeEqualTo(Date value) {
            addCriterion("submit_time =", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotEqualTo(Date value) {
            addCriterion("submit_time <>", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThan(Date value) {
            addCriterion("submit_time >", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("submit_time >=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThan(Date value) {
            addCriterion("submit_time <", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThanOrEqualTo(Date value) {
            addCriterion("submit_time <=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIn(List<Date> values) {
            addCriterion("submit_time in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotIn(List<Date> values) {
            addCriterion("submit_time not in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeBetween(Date value1, Date value2) {
            addCriterion("submit_time between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotBetween(Date value1, Date value2) {
            addCriterion("submit_time not between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeIsNull() {
            addCriterion("setuped_time is null");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeIsNotNull() {
            addCriterion("setuped_time is not null");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeEqualTo(Date value) {
            addCriterion("setuped_time =", value, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeNotEqualTo(Date value) {
            addCriterion("setuped_time <>", value, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeGreaterThan(Date value) {
            addCriterion("setuped_time >", value, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("setuped_time >=", value, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeLessThan(Date value) {
            addCriterion("setuped_time <", value, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeLessThanOrEqualTo(Date value) {
            addCriterion("setuped_time <=", value, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeIn(List<Date> values) {
            addCriterion("setuped_time in", values, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeNotIn(List<Date> values) {
            addCriterion("setuped_time not in", values, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeBetween(Date value1, Date value2) {
            addCriterion("setuped_time between", value1, value2, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andSetupedTimeNotBetween(Date value1, Date value2) {
            addCriterion("setuped_time not between", value1, value2, "setupedTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeIsNull() {
            addCriterion("plan_complete_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeIsNotNull() {
            addCriterion("plan_complete_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeEqualTo(Date value) {
            addCriterion("plan_complete_time =", value, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeNotEqualTo(Date value) {
            addCriterion("plan_complete_time <>", value, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeGreaterThan(Date value) {
            addCriterion("plan_complete_time >", value, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_complete_time >=", value, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeLessThan(Date value) {
            addCriterion("plan_complete_time <", value, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_complete_time <=", value, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeIn(List<Date> values) {
            addCriterion("plan_complete_time in", values, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeNotIn(List<Date> values) {
            addCriterion("plan_complete_time not in", values, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeBetween(Date value1, Date value2) {
            addCriterion("plan_complete_time between", value1, value2, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andPlanCompleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_complete_time not between", value1, value2, "planCompleteTime");
            return (Criteria) this;
        }

        public Criteria andDevManhourIsNull() {
            addCriterion("dev_manhour is null");
            return (Criteria) this;
        }

        public Criteria andDevManhourIsNotNull() {
            addCriterion("dev_manhour is not null");
            return (Criteria) this;
        }

        public Criteria andDevManhourEqualTo(Double value) {
            addCriterion("dev_manhour =", value, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourNotEqualTo(Double value) {
            addCriterion("dev_manhour <>", value, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourGreaterThan(Double value) {
            addCriterion("dev_manhour >", value, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourGreaterThanOrEqualTo(Double value) {
            addCriterion("dev_manhour >=", value, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourLessThan(Double value) {
            addCriterion("dev_manhour <", value, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourLessThanOrEqualTo(Double value) {
            addCriterion("dev_manhour <=", value, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourIn(List<Double> values) {
            addCriterion("dev_manhour in", values, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourNotIn(List<Double> values) {
            addCriterion("dev_manhour not in", values, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourBetween(Double value1, Double value2) {
            addCriterion("dev_manhour between", value1, value2, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourNotBetween(Double value1, Double value2) {
            addCriterion("dev_manhour not between", value1, value2, "devManhour");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedIsNull() {
            addCriterion("dev_manhour_approved is null");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedIsNotNull() {
            addCriterion("dev_manhour_approved is not null");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedEqualTo(Double value) {
            addCriterion("dev_manhour_approved =", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedNotEqualTo(Double value) {
            addCriterion("dev_manhour_approved <>", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedGreaterThan(Double value) {
            addCriterion("dev_manhour_approved >", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedGreaterThanOrEqualTo(Double value) {
            addCriterion("dev_manhour_approved >=", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedLessThan(Double value) {
            addCriterion("dev_manhour_approved <", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedLessThanOrEqualTo(Double value) {
            addCriterion("dev_manhour_approved <=", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedIn(List<Double> values) {
            addCriterion("dev_manhour_approved in", values, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedNotIn(List<Double> values) {
            addCriterion("dev_manhour_approved not in", values, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedBetween(Double value1, Double value2) {
            addCriterion("dev_manhour_approved between", value1, value2, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedNotBetween(Double value1, Double value2) {
            addCriterion("dev_manhour_approved not between", value1, value2, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourIsNull() {
            addCriterion("test_manhour is null");
            return (Criteria) this;
        }

        public Criteria andTestManhourIsNotNull() {
            addCriterion("test_manhour is not null");
            return (Criteria) this;
        }

        public Criteria andTestManhourEqualTo(Double value) {
            addCriterion("test_manhour =", value, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourNotEqualTo(Double value) {
            addCriterion("test_manhour <>", value, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourGreaterThan(Double value) {
            addCriterion("test_manhour >", value, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourGreaterThanOrEqualTo(Double value) {
            addCriterion("test_manhour >=", value, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourLessThan(Double value) {
            addCriterion("test_manhour <", value, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourLessThanOrEqualTo(Double value) {
            addCriterion("test_manhour <=", value, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourIn(List<Double> values) {
            addCriterion("test_manhour in", values, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourNotIn(List<Double> values) {
            addCriterion("test_manhour not in", values, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourBetween(Double value1, Double value2) {
            addCriterion("test_manhour between", value1, value2, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourNotBetween(Double value1, Double value2) {
            addCriterion("test_manhour not between", value1, value2, "testManhour");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedIsNull() {
            addCriterion("test_manhour_approved is null");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedIsNotNull() {
            addCriterion("test_manhour_approved is not null");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedEqualTo(Double value) {
            addCriterion("test_manhour_approved =", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedNotEqualTo(Double value) {
            addCriterion("test_manhour_approved <>", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedGreaterThan(Double value) {
            addCriterion("test_manhour_approved >", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedGreaterThanOrEqualTo(Double value) {
            addCriterion("test_manhour_approved >=", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedLessThan(Double value) {
            addCriterion("test_manhour_approved <", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedLessThanOrEqualTo(Double value) {
            addCriterion("test_manhour_approved <=", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedIn(List<Double> values) {
            addCriterion("test_manhour_approved in", values, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedNotIn(List<Double> values) {
            addCriterion("test_manhour_approved not in", values, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedBetween(Double value1, Double value2) {
            addCriterion("test_manhour_approved between", value1, value2, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedNotBetween(Double value1, Double value2) {
            addCriterion("test_manhour_approved not between", value1, value2, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeIsNull() {
            addCriterion("material_fee is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeIsNotNull() {
            addCriterion("material_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeEqualTo(BigDecimal value) {
            addCriterion("material_fee =", value, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeNotEqualTo(BigDecimal value) {
            addCriterion("material_fee <>", value, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeGreaterThan(BigDecimal value) {
            addCriterion("material_fee >", value, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee >=", value, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeLessThan(BigDecimal value) {
            addCriterion("material_fee <", value, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee <=", value, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeIn(List<BigDecimal> values) {
            addCriterion("material_fee in", values, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeNotIn(List<BigDecimal> values) {
            addCriterion("material_fee not in", values, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee between", value1, value2, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee not between", value1, value2, "materialFee");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedIsNull() {
            addCriterion("material_fee_approved is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedIsNotNull() {
            addCriterion("material_fee_approved is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved =", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedNotEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved <>", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedGreaterThan(BigDecimal value) {
            addCriterion("material_fee_approved >", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved >=", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedLessThan(BigDecimal value) {
            addCriterion("material_fee_approved <", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved <=", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved in", values, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedNotIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved not in", values, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved between", value1, value2, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved not between", value1, value2, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andTestFeeIsNull() {
            addCriterion("test_fee is null");
            return (Criteria) this;
        }

        public Criteria andTestFeeIsNotNull() {
            addCriterion("test_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTestFeeEqualTo(BigDecimal value) {
            addCriterion("test_fee =", value, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeNotEqualTo(BigDecimal value) {
            addCriterion("test_fee <>", value, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeGreaterThan(BigDecimal value) {
            addCriterion("test_fee >", value, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee >=", value, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeLessThan(BigDecimal value) {
            addCriterion("test_fee <", value, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("test_fee <=", value, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeIn(List<BigDecimal> values) {
            addCriterion("test_fee in", values, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeNotIn(List<BigDecimal> values) {
            addCriterion("test_fee not in", values, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee between", value1, value2, "testFee");
            return (Criteria) this;
        }

        public Criteria andTestFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("test_fee not between", value1, value2, "testFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeIsNull() {
            addCriterion("power_fee is null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeIsNotNull() {
            addCriterion("power_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPowerFeeEqualTo(BigDecimal value) {
            addCriterion("power_fee =", value, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeNotEqualTo(BigDecimal value) {
            addCriterion("power_fee <>", value, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeGreaterThan(BigDecimal value) {
            addCriterion("power_fee >", value, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee >=", value, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeLessThan(BigDecimal value) {
            addCriterion("power_fee <", value, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("power_fee <=", value, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeIn(List<BigDecimal> values) {
            addCriterion("power_fee in", values, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeNotIn(List<BigDecimal> values) {
            addCriterion("power_fee not in", values, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee between", value1, value2, "powerFee");
            return (Criteria) this;
        }

        public Criteria andPowerFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("power_fee not between", value1, value2, "powerFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeIsNull() {
            addCriterion("conference_fee is null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeIsNotNull() {
            addCriterion("conference_fee is not null");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeEqualTo(BigDecimal value) {
            addCriterion("conference_fee =", value, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeNotEqualTo(BigDecimal value) {
            addCriterion("conference_fee <>", value, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeGreaterThan(BigDecimal value) {
            addCriterion("conference_fee >", value, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee >=", value, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeLessThan(BigDecimal value) {
            addCriterion("conference_fee <", value, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("conference_fee <=", value, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeIn(List<BigDecimal> values) {
            addCriterion("conference_fee in", values, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeNotIn(List<BigDecimal> values) {
            addCriterion("conference_fee not in", values, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee between", value1, value2, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andConferenceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conference_fee not between", value1, value2, "conferenceFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeIsNull() {
            addCriterion("business_fee is null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeIsNotNull() {
            addCriterion("business_fee is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeEqualTo(BigDecimal value) {
            addCriterion("business_fee =", value, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeNotEqualTo(BigDecimal value) {
            addCriterion("business_fee <>", value, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeGreaterThan(BigDecimal value) {
            addCriterion("business_fee >", value, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee >=", value, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeLessThan(BigDecimal value) {
            addCriterion("business_fee <", value, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("business_fee <=", value, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeIn(List<BigDecimal> values) {
            addCriterion("business_fee in", values, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeNotIn(List<BigDecimal> values) {
            addCriterion("business_fee not in", values, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee between", value1, value2, "businessFee");
            return (Criteria) this;
        }

        public Criteria andBusinessFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business_fee not between", value1, value2, "businessFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeIsNull() {
            addCriterion("cooperation_fee is null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeIsNotNull() {
            addCriterion("cooperation_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee =", value, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeNotEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee <>", value, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeGreaterThan(BigDecimal value) {
            addCriterion("cooperation_fee >", value, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee >=", value, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeLessThan(BigDecimal value) {
            addCriterion("cooperation_fee <", value, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cooperation_fee <=", value, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee in", values, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeNotIn(List<BigDecimal> values) {
            addCriterion("cooperation_fee not in", values, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee between", value1, value2, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andCooperationFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cooperation_fee not between", value1, value2, "cooperationFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeIsNull() {
            addCriterion("property_fee is null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeIsNotNull() {
            addCriterion("property_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeEqualTo(BigDecimal value) {
            addCriterion("property_fee =", value, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeNotEqualTo(BigDecimal value) {
            addCriterion("property_fee <>", value, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeGreaterThan(BigDecimal value) {
            addCriterion("property_fee >", value, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee >=", value, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeLessThan(BigDecimal value) {
            addCriterion("property_fee <", value, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("property_fee <=", value, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeIn(List<BigDecimal> values) {
            addCriterion("property_fee in", values, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeNotIn(List<BigDecimal> values) {
            addCriterion("property_fee not in", values, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee between", value1, value2, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andPropertyFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("property_fee not between", value1, value2, "propertyFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeIsNull() {
            addCriterion("labor_fee is null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeIsNotNull() {
            addCriterion("labor_fee is not null");
            return (Criteria) this;
        }

        public Criteria andLaborFeeEqualTo(BigDecimal value) {
            addCriterion("labor_fee =", value, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeNotEqualTo(BigDecimal value) {
            addCriterion("labor_fee <>", value, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeGreaterThan(BigDecimal value) {
            addCriterion("labor_fee >", value, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee >=", value, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeLessThan(BigDecimal value) {
            addCriterion("labor_fee <", value, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("labor_fee <=", value, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeIn(List<BigDecimal> values) {
            addCriterion("labor_fee in", values, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeNotIn(List<BigDecimal> values) {
            addCriterion("labor_fee not in", values, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee between", value1, value2, "laborFee");
            return (Criteria) this;
        }

        public Criteria andLaborFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("labor_fee not between", value1, value2, "laborFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeIsNull() {
            addCriterion("consulting_fee is null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeIsNotNull() {
            addCriterion("consulting_fee is not null");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeEqualTo(BigDecimal value) {
            addCriterion("consulting_fee =", value, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeNotEqualTo(BigDecimal value) {
            addCriterion("consulting_fee <>", value, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeGreaterThan(BigDecimal value) {
            addCriterion("consulting_fee >", value, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee >=", value, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeLessThan(BigDecimal value) {
            addCriterion("consulting_fee <", value, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("consulting_fee <=", value, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeIn(List<BigDecimal> values) {
            addCriterion("consulting_fee in", values, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeNotIn(List<BigDecimal> values) {
            addCriterion("consulting_fee not in", values, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee between", value1, value2, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andConsultingFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consulting_fee not between", value1, value2, "consultingFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeIsNull() {
            addCriterion("management_fee is null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeIsNotNull() {
            addCriterion("management_fee is not null");
            return (Criteria) this;
        }

        public Criteria andManagementFeeEqualTo(BigDecimal value) {
            addCriterion("management_fee =", value, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeNotEqualTo(BigDecimal value) {
            addCriterion("management_fee <>", value, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeGreaterThan(BigDecimal value) {
            addCriterion("management_fee >", value, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee >=", value, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeLessThan(BigDecimal value) {
            addCriterion("management_fee <", value, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("management_fee <=", value, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeIn(List<BigDecimal> values) {
            addCriterion("management_fee in", values, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeNotIn(List<BigDecimal> values) {
            addCriterion("management_fee not in", values, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee between", value1, value2, "managementFee");
            return (Criteria) this;
        }

        public Criteria andManagementFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("management_fee not between", value1, value2, "managementFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeIsNull() {
            addCriterion("sum_other_fee is null");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeIsNotNull() {
            addCriterion("sum_other_fee is not null");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeEqualTo(BigDecimal value) {
            addCriterion("sum_other_fee =", value, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeNotEqualTo(BigDecimal value) {
            addCriterion("sum_other_fee <>", value, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeGreaterThan(BigDecimal value) {
            addCriterion("sum_other_fee >", value, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_other_fee >=", value, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeLessThan(BigDecimal value) {
            addCriterion("sum_other_fee <", value, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_other_fee <=", value, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeIn(List<BigDecimal> values) {
            addCriterion("sum_other_fee in", values, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeNotIn(List<BigDecimal> values) {
            addCriterion("sum_other_fee not in", values, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_other_fee between", value1, value2, "sumOtherFee");
            return (Criteria) this;
        }

        public Criteria andSumOtherFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_other_fee not between", value1, value2, "sumOtherFee");
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

        public Criteria andVersionUpdateIsNull() {
            addCriterion("version_update is null");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateIsNotNull() {
            addCriterion("version_update is not null");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateEqualTo(String value) {
            addCriterion("version_update =", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateNotEqualTo(String value) {
            addCriterion("version_update <>", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateGreaterThan(String value) {
            addCriterion("version_update >", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateGreaterThanOrEqualTo(String value) {
            addCriterion("version_update >=", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateLessThan(String value) {
            addCriterion("version_update <", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateLessThanOrEqualTo(String value) {
            addCriterion("version_update <=", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateLike(String value) {
            addCriterion("version_update like", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateNotLike(String value) {
            addCriterion("version_update not like", value, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateIn(List<String> values) {
            addCriterion("version_update in", values, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateNotIn(List<String> values) {
            addCriterion("version_update not in", values, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateBetween(String value1, String value2) {
            addCriterion("version_update between", value1, value2, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andVersionUpdateNotBetween(String value1, String value2) {
            addCriterion("version_update not between", value1, value2, "versionUpdate");
            return (Criteria) this;
        }

        public Criteria andMmStatusIsNull() {
            addCriterion("mm_status is null");
            return (Criteria) this;
        }

        public Criteria andMmStatusIsNotNull() {
            addCriterion("mm_status is not null");
            return (Criteria) this;
        }

        public Criteria andMmStatusEqualTo(String value) {
            addCriterion("mm_status =", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusNotEqualTo(String value) {
            addCriterion("mm_status <>", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusGreaterThan(String value) {
            addCriterion("mm_status >", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusGreaterThanOrEqualTo(String value) {
            addCriterion("mm_status >=", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusLessThan(String value) {
            addCriterion("mm_status <", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusLessThanOrEqualTo(String value) {
            addCriterion("mm_status <=", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusLike(String value) {
            addCriterion("mm_status like", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusNotLike(String value) {
            addCriterion("mm_status not like", value, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusIn(List<String> values) {
            addCriterion("mm_status in", values, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusNotIn(List<String> values) {
            addCriterion("mm_status not in", values, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusBetween(String value1, String value2) {
            addCriterion("mm_status between", value1, value2, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andMmStatusNotBetween(String value1, String value2) {
            addCriterion("mm_status not between", value1, value2, "mmStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusIsNull() {
            addCriterion("bom_status is null");
            return (Criteria) this;
        }

        public Criteria andBomStatusIsNotNull() {
            addCriterion("bom_status is not null");
            return (Criteria) this;
        }

        public Criteria andBomStatusEqualTo(String value) {
            addCriterion("bom_status =", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusNotEqualTo(String value) {
            addCriterion("bom_status <>", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusGreaterThan(String value) {
            addCriterion("bom_status >", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusGreaterThanOrEqualTo(String value) {
            addCriterion("bom_status >=", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusLessThan(String value) {
            addCriterion("bom_status <", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusLessThanOrEqualTo(String value) {
            addCriterion("bom_status <=", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusLike(String value) {
            addCriterion("bom_status like", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusNotLike(String value) {
            addCriterion("bom_status not like", value, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusIn(List<String> values) {
            addCriterion("bom_status in", values, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusNotIn(List<String> values) {
            addCriterion("bom_status not in", values, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusBetween(String value1, String value2) {
            addCriterion("bom_status between", value1, value2, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomStatusNotBetween(String value1, String value2) {
            addCriterion("bom_status not between", value1, value2, "bomStatus");
            return (Criteria) this;
        }

        public Criteria andBomVersionIsNull() {
            addCriterion("bom_version is null");
            return (Criteria) this;
        }

        public Criteria andBomVersionIsNotNull() {
            addCriterion("bom_version is not null");
            return (Criteria) this;
        }

        public Criteria andBomVersionEqualTo(Integer value) {
            addCriterion("bom_version =", value, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionNotEqualTo(Integer value) {
            addCriterion("bom_version <>", value, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionGreaterThan(Integer value) {
            addCriterion("bom_version >", value, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("bom_version >=", value, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionLessThan(Integer value) {
            addCriterion("bom_version <", value, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionLessThanOrEqualTo(Integer value) {
            addCriterion("bom_version <=", value, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionIn(List<Integer> values) {
            addCriterion("bom_version in", values, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionNotIn(List<Integer> values) {
            addCriterion("bom_version not in", values, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionBetween(Integer value1, Integer value2) {
            addCriterion("bom_version between", value1, value2, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andBomVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("bom_version not between", value1, value2, "bomVersion");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdIsNull() {
            addCriterion("out_character_id is null");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdIsNotNull() {
            addCriterion("out_character_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdEqualTo(String value) {
            addCriterion("out_character_id =", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdNotEqualTo(String value) {
            addCriterion("out_character_id <>", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdGreaterThan(String value) {
            addCriterion("out_character_id >", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdGreaterThanOrEqualTo(String value) {
            addCriterion("out_character_id >=", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdLessThan(String value) {
            addCriterion("out_character_id <", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdLessThanOrEqualTo(String value) {
            addCriterion("out_character_id <=", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdLike(String value) {
            addCriterion("out_character_id like", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdNotLike(String value) {
            addCriterion("out_character_id not like", value, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdIn(List<String> values) {
            addCriterion("out_character_id in", values, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdNotIn(List<String> values) {
            addCriterion("out_character_id not in", values, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdBetween(String value1, String value2) {
            addCriterion("out_character_id between", value1, value2, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andOutCharacterIdNotBetween(String value1, String value2) {
            addCriterion("out_character_id not between", value1, value2, "outCharacterId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdIsNull() {
            addCriterion("cbb_project_id is null");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdIsNotNull() {
            addCriterion("cbb_project_id is not null");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdEqualTo(String value) {
            addCriterion("cbb_project_id =", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdNotEqualTo(String value) {
            addCriterion("cbb_project_id <>", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdGreaterThan(String value) {
            addCriterion("cbb_project_id >", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("cbb_project_id >=", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdLessThan(String value) {
            addCriterion("cbb_project_id <", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdLessThanOrEqualTo(String value) {
            addCriterion("cbb_project_id <=", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdLike(String value) {
            addCriterion("cbb_project_id like", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdNotLike(String value) {
            addCriterion("cbb_project_id not like", value, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdIn(List<String> values) {
            addCriterion("cbb_project_id in", values, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdNotIn(List<String> values) {
            addCriterion("cbb_project_id not in", values, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdBetween(String value1, String value2) {
            addCriterion("cbb_project_id between", value1, value2, "cbbProjectId");
            return (Criteria) this;
        }

        public Criteria andCbbProjectIdNotBetween(String value1, String value2) {
            addCriterion("cbb_project_id not between", value1, value2, "cbbProjectId");
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