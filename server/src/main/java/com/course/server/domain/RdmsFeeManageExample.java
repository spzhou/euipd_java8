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

public class RdmsFeeManageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsFeeManageExample() {
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
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

        public Criteria andQualityIdIsNull() {
            addCriterion("quality_id is null");
            return (Criteria) this;
        }

        public Criteria andQualityIdIsNotNull() {
            addCriterion("quality_id is not null");
            return (Criteria) this;
        }

        public Criteria andQualityIdEqualTo(String value) {
            addCriterion("quality_id =", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdNotEqualTo(String value) {
            addCriterion("quality_id <>", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdGreaterThan(String value) {
            addCriterion("quality_id >", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdGreaterThanOrEqualTo(String value) {
            addCriterion("quality_id >=", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdLessThan(String value) {
            addCriterion("quality_id <", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdLessThanOrEqualTo(String value) {
            addCriterion("quality_id <=", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdLike(String value) {
            addCriterion("quality_id like", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdNotLike(String value) {
            addCriterion("quality_id not like", value, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdIn(List<String> values) {
            addCriterion("quality_id in", values, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdNotIn(List<String> values) {
            addCriterion("quality_id not in", values, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdBetween(String value1, String value2) {
            addCriterion("quality_id between", value1, value2, "qualityId");
            return (Criteria) this;
        }

        public Criteria andQualityIdNotBetween(String value1, String value2) {
            addCriterion("quality_id not between", value1, value2, "qualityId");
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

        public Criteria andAccountCostIsNull() {
            addCriterion("account_cost is null");
            return (Criteria) this;
        }

        public Criteria andAccountCostIsNotNull() {
            addCriterion("account_cost is not null");
            return (Criteria) this;
        }

        public Criteria andAccountCostEqualTo(BigDecimal value) {
            addCriterion("account_cost =", value, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostNotEqualTo(BigDecimal value) {
            addCriterion("account_cost <>", value, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostGreaterThan(BigDecimal value) {
            addCriterion("account_cost >", value, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_cost >=", value, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostLessThan(BigDecimal value) {
            addCriterion("account_cost <", value, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_cost <=", value, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostIn(List<BigDecimal> values) {
            addCriterion("account_cost in", values, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostNotIn(List<BigDecimal> values) {
            addCriterion("account_cost not in", values, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_cost between", value1, value2, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAccountCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_cost not between", value1, value2, "accountCost");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountIsNull() {
            addCriterion("applied_amount is null");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountIsNotNull() {
            addCriterion("applied_amount is not null");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountEqualTo(BigDecimal value) {
            addCriterion("applied_amount =", value, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountNotEqualTo(BigDecimal value) {
            addCriterion("applied_amount <>", value, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountGreaterThan(BigDecimal value) {
            addCriterion("applied_amount >", value, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("applied_amount >=", value, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountLessThan(BigDecimal value) {
            addCriterion("applied_amount <", value, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("applied_amount <=", value, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountIn(List<BigDecimal> values) {
            addCriterion("applied_amount in", values, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountNotIn(List<BigDecimal> values) {
            addCriterion("applied_amount not in", values, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("applied_amount between", value1, value2, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andAppliedAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("applied_amount not between", value1, value2, "appliedAmount");
            return (Criteria) this;
        }

        public Criteria andActualCostIsNull() {
            addCriterion("actual_cost is null");
            return (Criteria) this;
        }

        public Criteria andActualCostIsNotNull() {
            addCriterion("actual_cost is not null");
            return (Criteria) this;
        }

        public Criteria andActualCostEqualTo(BigDecimal value) {
            addCriterion("actual_cost =", value, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostNotEqualTo(BigDecimal value) {
            addCriterion("actual_cost <>", value, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostGreaterThan(BigDecimal value) {
            addCriterion("actual_cost >", value, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_cost >=", value, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostLessThan(BigDecimal value) {
            addCriterion("actual_cost <", value, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_cost <=", value, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostIn(List<BigDecimal> values) {
            addCriterion("actual_cost in", values, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostNotIn(List<BigDecimal> values) {
            addCriterion("actual_cost not in", values, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_cost between", value1, value2, "actualCost");
            return (Criteria) this;
        }

        public Criteria andActualCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_cost not between", value1, value2, "actualCost");
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

        public Criteria andApproverIdIsNull() {
            addCriterion("approver_id is null");
            return (Criteria) this;
        }

        public Criteria andApproverIdIsNotNull() {
            addCriterion("approver_id is not null");
            return (Criteria) this;
        }

        public Criteria andApproverIdEqualTo(String value) {
            addCriterion("approver_id =", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotEqualTo(String value) {
            addCriterion("approver_id <>", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdGreaterThan(String value) {
            addCriterion("approver_id >", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdGreaterThanOrEqualTo(String value) {
            addCriterion("approver_id >=", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdLessThan(String value) {
            addCriterion("approver_id <", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdLessThanOrEqualTo(String value) {
            addCriterion("approver_id <=", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdLike(String value) {
            addCriterion("approver_id like", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotLike(String value) {
            addCriterion("approver_id not like", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdIn(List<String> values) {
            addCriterion("approver_id in", values, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotIn(List<String> values) {
            addCriterion("approver_id not in", values, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdBetween(String value1, String value2) {
            addCriterion("approver_id between", value1, value2, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotBetween(String value1, String value2) {
            addCriterion("approver_id not between", value1, value2, "approverId");
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

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionIsNull() {
            addCriterion("approve_description is null");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionIsNotNull() {
            addCriterion("approve_description is not null");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionEqualTo(String value) {
            addCriterion("approve_description =", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionNotEqualTo(String value) {
            addCriterion("approve_description <>", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionGreaterThan(String value) {
            addCriterion("approve_description >", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("approve_description >=", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionLessThan(String value) {
            addCriterion("approve_description <", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionLessThanOrEqualTo(String value) {
            addCriterion("approve_description <=", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionLike(String value) {
            addCriterion("approve_description like", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionNotLike(String value) {
            addCriterion("approve_description not like", value, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionIn(List<String> values) {
            addCriterion("approve_description in", values, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionNotIn(List<String> values) {
            addCriterion("approve_description not in", values, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionBetween(String value1, String value2) {
            addCriterion("approve_description between", value1, value2, "approveDescription");
            return (Criteria) this;
        }

        public Criteria andApproveDescriptionNotBetween(String value1, String value2) {
            addCriterion("approve_description not between", value1, value2, "approveDescription");
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

        public Criteria andJobTypeIsNull() {
            addCriterion("job_type is null");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNotNull() {
            addCriterion("job_type is not null");
            return (Criteria) this;
        }

        public Criteria andJobTypeEqualTo(String value) {
            addCriterion("job_type =", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotEqualTo(String value) {
            addCriterion("job_type <>", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThan(String value) {
            addCriterion("job_type >", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThanOrEqualTo(String value) {
            addCriterion("job_type >=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThan(String value) {
            addCriterion("job_type <", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThanOrEqualTo(String value) {
            addCriterion("job_type <=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLike(String value) {
            addCriterion("job_type like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotLike(String value) {
            addCriterion("job_type not like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeIn(List<String> values) {
            addCriterion("job_type in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotIn(List<String> values) {
            addCriterion("job_type not in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeBetween(String value1, String value2) {
            addCriterion("job_type between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotBetween(String value1, String value2) {
            addCriterion("job_type not between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrIsNull() {
            addCriterion("approve_file_list_str is null");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrIsNotNull() {
            addCriterion("approve_file_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrEqualTo(String value) {
            addCriterion("approve_file_list_str =", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrNotEqualTo(String value) {
            addCriterion("approve_file_list_str <>", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrGreaterThan(String value) {
            addCriterion("approve_file_list_str >", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrGreaterThanOrEqualTo(String value) {
            addCriterion("approve_file_list_str >=", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrLessThan(String value) {
            addCriterion("approve_file_list_str <", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrLessThanOrEqualTo(String value) {
            addCriterion("approve_file_list_str <=", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrLike(String value) {
            addCriterion("approve_file_list_str like", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrNotLike(String value) {
            addCriterion("approve_file_list_str not like", value, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrIn(List<String> values) {
            addCriterion("approve_file_list_str in", values, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrNotIn(List<String> values) {
            addCriterion("approve_file_list_str not in", values, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrBetween(String value1, String value2) {
            addCriterion("approve_file_list_str between", value1, value2, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveFileListStrNotBetween(String value1, String value2) {
            addCriterion("approve_file_list_str not between", value1, value2, "approveFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrIsNull() {
            addCriterion("deal_with_file_list_str is null");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrIsNotNull() {
            addCriterion("deal_with_file_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrEqualTo(String value) {
            addCriterion("deal_with_file_list_str =", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrNotEqualTo(String value) {
            addCriterion("deal_with_file_list_str <>", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrGreaterThan(String value) {
            addCriterion("deal_with_file_list_str >", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrGreaterThanOrEqualTo(String value) {
            addCriterion("deal_with_file_list_str >=", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrLessThan(String value) {
            addCriterion("deal_with_file_list_str <", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrLessThanOrEqualTo(String value) {
            addCriterion("deal_with_file_list_str <=", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrLike(String value) {
            addCriterion("deal_with_file_list_str like", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrNotLike(String value) {
            addCriterion("deal_with_file_list_str not like", value, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrIn(List<String> values) {
            addCriterion("deal_with_file_list_str in", values, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrNotIn(List<String> values) {
            addCriterion("deal_with_file_list_str not in", values, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrBetween(String value1, String value2) {
            addCriterion("deal_with_file_list_str between", value1, value2, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andDealWithFileListStrNotBetween(String value1, String value2) {
            addCriterion("deal_with_file_list_str not between", value1, value2, "dealWithFileListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionIsNull() {
            addCriterion("submit_description is null");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionIsNotNull() {
            addCriterion("submit_description is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionEqualTo(String value) {
            addCriterion("submit_description =", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionNotEqualTo(String value) {
            addCriterion("submit_description <>", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionGreaterThan(String value) {
            addCriterion("submit_description >", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("submit_description >=", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionLessThan(String value) {
            addCriterion("submit_description <", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionLessThanOrEqualTo(String value) {
            addCriterion("submit_description <=", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionLike(String value) {
            addCriterion("submit_description like", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionNotLike(String value) {
            addCriterion("submit_description not like", value, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionIn(List<String> values) {
            addCriterion("submit_description in", values, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionNotIn(List<String> values) {
            addCriterion("submit_description not in", values, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionBetween(String value1, String value2) {
            addCriterion("submit_description between", value1, value2, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitDescriptionNotBetween(String value1, String value2) {
            addCriterion("submit_description not between", value1, value2, "submitDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionIsNull() {
            addCriterion("submit_approve_description is null");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionIsNotNull() {
            addCriterion("submit_approve_description is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionEqualTo(String value) {
            addCriterion("submit_approve_description =", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionNotEqualTo(String value) {
            addCriterion("submit_approve_description <>", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionGreaterThan(String value) {
            addCriterion("submit_approve_description >", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("submit_approve_description >=", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionLessThan(String value) {
            addCriterion("submit_approve_description <", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionLessThanOrEqualTo(String value) {
            addCriterion("submit_approve_description <=", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionLike(String value) {
            addCriterion("submit_approve_description like", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionNotLike(String value) {
            addCriterion("submit_approve_description not like", value, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionIn(List<String> values) {
            addCriterion("submit_approve_description in", values, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionNotIn(List<String> values) {
            addCriterion("submit_approve_description not in", values, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionBetween(String value1, String value2) {
            addCriterion("submit_approve_description between", value1, value2, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andSubmitApproveDescriptionNotBetween(String value1, String value2) {
            addCriterion("submit_approve_description not between", value1, value2, "submitApproveDescription");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrIsNull() {
            addCriterion("complete_file_list_str is null");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrIsNotNull() {
            addCriterion("complete_file_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrEqualTo(String value) {
            addCriterion("complete_file_list_str =", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrNotEqualTo(String value) {
            addCriterion("complete_file_list_str <>", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrGreaterThan(String value) {
            addCriterion("complete_file_list_str >", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrGreaterThanOrEqualTo(String value) {
            addCriterion("complete_file_list_str >=", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrLessThan(String value) {
            addCriterion("complete_file_list_str <", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrLessThanOrEqualTo(String value) {
            addCriterion("complete_file_list_str <=", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrLike(String value) {
            addCriterion("complete_file_list_str like", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrNotLike(String value) {
            addCriterion("complete_file_list_str not like", value, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrIn(List<String> values) {
            addCriterion("complete_file_list_str in", values, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrNotIn(List<String> values) {
            addCriterion("complete_file_list_str not in", values, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrBetween(String value1, String value2) {
            addCriterion("complete_file_list_str between", value1, value2, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andCompleteFileListStrNotBetween(String value1, String value2) {
            addCriterion("complete_file_list_str not between", value1, value2, "completeFileListStr");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNull() {
            addCriterion("approve_time is null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNotNull() {
            addCriterion("approve_time is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeEqualTo(Date value) {
            addCriterion("approve_time =", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotEqualTo(Date value) {
            addCriterion("approve_time <>", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThan(Date value) {
            addCriterion("approve_time >", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approve_time >=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThan(Date value) {
            addCriterion("approve_time <", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThanOrEqualTo(Date value) {
            addCriterion("approve_time <=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIn(List<Date> values) {
            addCriterion("approve_time in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotIn(List<Date> values) {
            addCriterion("approve_time not in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeBetween(Date value1, Date value2) {
            addCriterion("approve_time between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotBetween(Date value1, Date value2) {
            addCriterion("approve_time not between", value1, value2, "approveTime");
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