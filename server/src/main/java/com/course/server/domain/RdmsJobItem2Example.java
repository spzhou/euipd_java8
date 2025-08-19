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

public class RdmsJobItem2Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsJobItem2Example() {
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

        public Criteria andJobSerialIsNull() {
            addCriterion("job_serial is null");
            return (Criteria) this;
        }

        public Criteria andJobSerialIsNotNull() {
            addCriterion("job_serial is not null");
            return (Criteria) this;
        }

        public Criteria andJobSerialEqualTo(String value) {
            addCriterion("job_serial =", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialNotEqualTo(String value) {
            addCriterion("job_serial <>", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialGreaterThan(String value) {
            addCriterion("job_serial >", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialGreaterThanOrEqualTo(String value) {
            addCriterion("job_serial >=", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialLessThan(String value) {
            addCriterion("job_serial <", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialLessThanOrEqualTo(String value) {
            addCriterion("job_serial <=", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialLike(String value) {
            addCriterion("job_serial like", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialNotLike(String value) {
            addCriterion("job_serial not like", value, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialIn(List<String> values) {
            addCriterion("job_serial in", values, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialNotIn(List<String> values) {
            addCriterion("job_serial not in", values, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialBetween(String value1, String value2) {
            addCriterion("job_serial between", value1, value2, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobSerialNotBetween(String value1, String value2) {
            addCriterion("job_serial not between", value1, value2, "jobSerial");
            return (Criteria) this;
        }

        public Criteria andJobNameIsNull() {
            addCriterion("job_name is null");
            return (Criteria) this;
        }

        public Criteria andJobNameIsNotNull() {
            addCriterion("job_name is not null");
            return (Criteria) this;
        }

        public Criteria andJobNameEqualTo(String value) {
            addCriterion("job_name =", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotEqualTo(String value) {
            addCriterion("job_name <>", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameGreaterThan(String value) {
            addCriterion("job_name >", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameGreaterThanOrEqualTo(String value) {
            addCriterion("job_name >=", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLessThan(String value) {
            addCriterion("job_name <", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLessThanOrEqualTo(String value) {
            addCriterion("job_name <=", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLike(String value) {
            addCriterion("job_name like", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotLike(String value) {
            addCriterion("job_name not like", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameIn(List<String> values) {
            addCriterion("job_name in", values, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotIn(List<String> values) {
            addCriterion("job_name not in", values, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameBetween(String value1, String value2) {
            addCriterion("job_name between", value1, value2, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotBetween(String value1, String value2) {
            addCriterion("job_name not between", value1, value2, "jobName");
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

        public Criteria andTestedJobitemIdIsNull() {
            addCriterion("tested_jobitem_id is null");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdIsNotNull() {
            addCriterion("tested_jobitem_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdEqualTo(String value) {
            addCriterion("tested_jobitem_id =", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdNotEqualTo(String value) {
            addCriterion("tested_jobitem_id <>", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdGreaterThan(String value) {
            addCriterion("tested_jobitem_id >", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdGreaterThanOrEqualTo(String value) {
            addCriterion("tested_jobitem_id >=", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdLessThan(String value) {
            addCriterion("tested_jobitem_id <", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdLessThanOrEqualTo(String value) {
            addCriterion("tested_jobitem_id <=", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdLike(String value) {
            addCriterion("tested_jobitem_id like", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdNotLike(String value) {
            addCriterion("tested_jobitem_id not like", value, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdIn(List<String> values) {
            addCriterion("tested_jobitem_id in", values, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdNotIn(List<String> values) {
            addCriterion("tested_jobitem_id not in", values, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdBetween(String value1, String value2) {
            addCriterion("tested_jobitem_id between", value1, value2, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andTestedJobitemIdNotBetween(String value1, String value2) {
            addCriterion("tested_jobitem_id not between", value1, value2, "testedJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdIsNull() {
            addCriterion("parent_jobitem_id is null");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdIsNotNull() {
            addCriterion("parent_jobitem_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdEqualTo(String value) {
            addCriterion("parent_jobitem_id =", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdNotEqualTo(String value) {
            addCriterion("parent_jobitem_id <>", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdGreaterThan(String value) {
            addCriterion("parent_jobitem_id >", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_jobitem_id >=", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdLessThan(String value) {
            addCriterion("parent_jobitem_id <", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdLessThanOrEqualTo(String value) {
            addCriterion("parent_jobitem_id <=", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdLike(String value) {
            addCriterion("parent_jobitem_id like", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdNotLike(String value) {
            addCriterion("parent_jobitem_id not like", value, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdIn(List<String> values) {
            addCriterion("parent_jobitem_id in", values, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdNotIn(List<String> values) {
            addCriterion("parent_jobitem_id not in", values, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdBetween(String value1, String value2) {
            addCriterion("parent_jobitem_id between", value1, value2, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andParentJobitemIdNotBetween(String value1, String value2) {
            addCriterion("parent_jobitem_id not between", value1, value2, "parentJobitemId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdIsNull() {
            addCriterion("project_manager_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdIsNotNull() {
            addCriterion("project_manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdEqualTo(String value) {
            addCriterion("project_manager_id =", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdNotEqualTo(String value) {
            addCriterion("project_manager_id <>", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdGreaterThan(String value) {
            addCriterion("project_manager_id >", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_manager_id >=", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdLessThan(String value) {
            addCriterion("project_manager_id <", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdLessThanOrEqualTo(String value) {
            addCriterion("project_manager_id <=", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdLike(String value) {
            addCriterion("project_manager_id like", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdNotLike(String value) {
            addCriterion("project_manager_id not like", value, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdIn(List<String> values) {
            addCriterion("project_manager_id in", values, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdNotIn(List<String> values) {
            addCriterion("project_manager_id not in", values, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdBetween(String value1, String value2) {
            addCriterion("project_manager_id between", value1, value2, "projectManagerId");
            return (Criteria) this;
        }

        public Criteria andProjectManagerIdNotBetween(String value1, String value2) {
            addCriterion("project_manager_id not between", value1, value2, "projectManagerId");
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

        public Criteria andTestManagerIdIsNull() {
            addCriterion("test_manager_id is null");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdIsNotNull() {
            addCriterion("test_manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdEqualTo(String value) {
            addCriterion("test_manager_id =", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdNotEqualTo(String value) {
            addCriterion("test_manager_id <>", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdGreaterThan(String value) {
            addCriterion("test_manager_id >", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdGreaterThanOrEqualTo(String value) {
            addCriterion("test_manager_id >=", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdLessThan(String value) {
            addCriterion("test_manager_id <", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdLessThanOrEqualTo(String value) {
            addCriterion("test_manager_id <=", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdLike(String value) {
            addCriterion("test_manager_id like", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdNotLike(String value) {
            addCriterion("test_manager_id not like", value, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdIn(List<String> values) {
            addCriterion("test_manager_id in", values, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdNotIn(List<String> values) {
            addCriterion("test_manager_id not in", values, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdBetween(String value1, String value2) {
            addCriterion("test_manager_id between", value1, value2, "testManagerId");
            return (Criteria) this;
        }

        public Criteria andTestManagerIdNotBetween(String value1, String value2) {
            addCriterion("test_manager_id not between", value1, value2, "testManagerId");
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

        public Criteria andAuxTypeIsNull() {
            addCriterion("aux_type is null");
            return (Criteria) this;
        }

        public Criteria andAuxTypeIsNotNull() {
            addCriterion("aux_type is not null");
            return (Criteria) this;
        }

        public Criteria andAuxTypeEqualTo(String value) {
            addCriterion("aux_type =", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeNotEqualTo(String value) {
            addCriterion("aux_type <>", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeGreaterThan(String value) {
            addCriterion("aux_type >", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeGreaterThanOrEqualTo(String value) {
            addCriterion("aux_type >=", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeLessThan(String value) {
            addCriterion("aux_type <", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeLessThanOrEqualTo(String value) {
            addCriterion("aux_type <=", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeLike(String value) {
            addCriterion("aux_type like", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeNotLike(String value) {
            addCriterion("aux_type not like", value, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeIn(List<String> values) {
            addCriterion("aux_type in", values, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeNotIn(List<String> values) {
            addCriterion("aux_type not in", values, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeBetween(String value1, String value2) {
            addCriterion("aux_type between", value1, value2, "auxType");
            return (Criteria) this;
        }

        public Criteria andAuxTypeNotBetween(String value1, String value2) {
            addCriterion("aux_type not between", value1, value2, "auxType");
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

        public Criteria andDemandIdStrIsNull() {
            addCriterion("demand_id_str is null");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrIsNotNull() {
            addCriterion("demand_id_str is not null");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrEqualTo(String value) {
            addCriterion("demand_id_str =", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrNotEqualTo(String value) {
            addCriterion("demand_id_str <>", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrGreaterThan(String value) {
            addCriterion("demand_id_str >", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrGreaterThanOrEqualTo(String value) {
            addCriterion("demand_id_str >=", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrLessThan(String value) {
            addCriterion("demand_id_str <", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrLessThanOrEqualTo(String value) {
            addCriterion("demand_id_str <=", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrLike(String value) {
            addCriterion("demand_id_str like", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrNotLike(String value) {
            addCriterion("demand_id_str not like", value, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrIn(List<String> values) {
            addCriterion("demand_id_str in", values, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrNotIn(List<String> values) {
            addCriterion("demand_id_str not in", values, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrBetween(String value1, String value2) {
            addCriterion("demand_id_str between", value1, value2, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andDemandIdStrNotBetween(String value1, String value2) {
            addCriterion("demand_id_str not between", value1, value2, "demandIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrIsNull() {
            addCriterion("merge_character_id_str is null");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrIsNotNull() {
            addCriterion("merge_character_id_str is not null");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrEqualTo(String value) {
            addCriterion("merge_character_id_str =", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrNotEqualTo(String value) {
            addCriterion("merge_character_id_str <>", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrGreaterThan(String value) {
            addCriterion("merge_character_id_str >", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrGreaterThanOrEqualTo(String value) {
            addCriterion("merge_character_id_str >=", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrLessThan(String value) {
            addCriterion("merge_character_id_str <", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrLessThanOrEqualTo(String value) {
            addCriterion("merge_character_id_str <=", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrLike(String value) {
            addCriterion("merge_character_id_str like", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrNotLike(String value) {
            addCriterion("merge_character_id_str not like", value, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrIn(List<String> values) {
            addCriterion("merge_character_id_str in", values, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrNotIn(List<String> values) {
            addCriterion("merge_character_id_str not in", values, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrBetween(String value1, String value2) {
            addCriterion("merge_character_id_str between", value1, value2, "mergeCharacterIdStr");
            return (Criteria) this;
        }

        public Criteria andMergeCharacterIdStrNotBetween(String value1, String value2) {
            addCriterion("merge_character_id_str not between", value1, value2, "mergeCharacterIdStr");
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

        public Criteria andStandManhourFeeIsNull() {
            addCriterion("stand_manhour_fee is null");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeIsNotNull() {
            addCriterion("stand_manhour_fee is not null");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeEqualTo(BigDecimal value) {
            addCriterion("stand_manhour_fee =", value, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeNotEqualTo(BigDecimal value) {
            addCriterion("stand_manhour_fee <>", value, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeGreaterThan(BigDecimal value) {
            addCriterion("stand_manhour_fee >", value, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("stand_manhour_fee >=", value, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeLessThan(BigDecimal value) {
            addCriterion("stand_manhour_fee <", value, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("stand_manhour_fee <=", value, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeIn(List<BigDecimal> values) {
            addCriterion("stand_manhour_fee in", values, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeNotIn(List<BigDecimal> values) {
            addCriterion("stand_manhour_fee not in", values, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stand_manhour_fee between", value1, value2, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andStandManhourFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stand_manhour_fee not between", value1, value2, "standManhourFee");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourIsNull() {
            addCriterion("performance_manhour is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourIsNotNull() {
            addCriterion("performance_manhour is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourEqualTo(Double value) {
            addCriterion("performance_manhour =", value, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourNotEqualTo(Double value) {
            addCriterion("performance_manhour <>", value, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourGreaterThan(Double value) {
            addCriterion("performance_manhour >", value, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourGreaterThanOrEqualTo(Double value) {
            addCriterion("performance_manhour >=", value, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourLessThan(Double value) {
            addCriterion("performance_manhour <", value, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourLessThanOrEqualTo(Double value) {
            addCriterion("performance_manhour <=", value, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourIn(List<Double> values) {
            addCriterion("performance_manhour in", values, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourNotIn(List<Double> values) {
            addCriterion("performance_manhour not in", values, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourBetween(Double value1, Double value2) {
            addCriterion("performance_manhour between", value1, value2, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andPerformanceManhourNotBetween(Double value1, Double value2) {
            addCriterion("performance_manhour not between", value1, value2, "performanceManhour");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionIsNull() {
            addCriterion("task_description is null");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionIsNotNull() {
            addCriterion("task_description is not null");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionEqualTo(String value) {
            addCriterion("task_description =", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionNotEqualTo(String value) {
            addCriterion("task_description <>", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionGreaterThan(String value) {
            addCriterion("task_description >", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("task_description >=", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionLessThan(String value) {
            addCriterion("task_description <", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionLessThanOrEqualTo(String value) {
            addCriterion("task_description <=", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionLike(String value) {
            addCriterion("task_description like", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionNotLike(String value) {
            addCriterion("task_description not like", value, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionIn(List<String> values) {
            addCriterion("task_description in", values, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionNotIn(List<String> values) {
            addCriterion("task_description not in", values, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionBetween(String value1, String value2) {
            addCriterion("task_description between", value1, value2, "taskDescription");
            return (Criteria) this;
        }

        public Criteria andTaskDescriptionNotBetween(String value1, String value2) {
            addCriterion("task_description not between", value1, value2, "taskDescription");
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

        public Criteria andPlanSubmissionTimeIsNull() {
            addCriterion("plan_submission_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeIsNotNull() {
            addCriterion("plan_submission_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeEqualTo(Date value) {
            addCriterion("plan_submission_time =", value, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeNotEqualTo(Date value) {
            addCriterion("plan_submission_time <>", value, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeGreaterThan(Date value) {
            addCriterion("plan_submission_time >", value, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_submission_time >=", value, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeLessThan(Date value) {
            addCriterion("plan_submission_time <", value, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_submission_time <=", value, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeIn(List<Date> values) {
            addCriterion("plan_submission_time in", values, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeNotIn(List<Date> values) {
            addCriterion("plan_submission_time not in", values, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeBetween(Date value1, Date value2) {
            addCriterion("plan_submission_time between", value1, value2, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPlanSubmissionTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_submission_time not between", value1, value2, "planSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeIsNull() {
            addCriterion("actual_submission_time is null");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeIsNotNull() {
            addCriterion("actual_submission_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeEqualTo(Date value) {
            addCriterion("actual_submission_time =", value, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeNotEqualTo(Date value) {
            addCriterion("actual_submission_time <>", value, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeGreaterThan(Date value) {
            addCriterion("actual_submission_time >", value, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("actual_submission_time >=", value, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeLessThan(Date value) {
            addCriterion("actual_submission_time <", value, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeLessThanOrEqualTo(Date value) {
            addCriterion("actual_submission_time <=", value, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeIn(List<Date> values) {
            addCriterion("actual_submission_time in", values, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeNotIn(List<Date> values) {
            addCriterion("actual_submission_time not in", values, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeBetween(Date value1, Date value2) {
            addCriterion("actual_submission_time between", value1, value2, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andActualSubmissionTimeNotBetween(Date value1, Date value2) {
            addCriterion("actual_submission_time not between", value1, value2, "actualSubmissionTime");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNull() {
            addCriterion("property_id is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNotNull() {
            addCriterion("property_id is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdEqualTo(String value) {
            addCriterion("property_id =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(String value) {
            addCriterion("property_id <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(String value) {
            addCriterion("property_id >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(String value) {
            addCriterion("property_id >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(String value) {
            addCriterion("property_id <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(String value) {
            addCriterion("property_id <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLike(String value) {
            addCriterion("property_id like", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotLike(String value) {
            addCriterion("property_id not like", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<String> values) {
            addCriterion("property_id in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<String> values) {
            addCriterion("property_id not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(String value1, String value2) {
            addCriterion("property_id between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(String value1, String value2) {
            addCriterion("property_id not between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrIsNull() {
            addCriterion("review_worker_id_str is null");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrIsNotNull() {
            addCriterion("review_worker_id_str is not null");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrEqualTo(String value) {
            addCriterion("review_worker_id_str =", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrNotEqualTo(String value) {
            addCriterion("review_worker_id_str <>", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrGreaterThan(String value) {
            addCriterion("review_worker_id_str >", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrGreaterThanOrEqualTo(String value) {
            addCriterion("review_worker_id_str >=", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrLessThan(String value) {
            addCriterion("review_worker_id_str <", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrLessThanOrEqualTo(String value) {
            addCriterion("review_worker_id_str <=", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrLike(String value) {
            addCriterion("review_worker_id_str like", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrNotLike(String value) {
            addCriterion("review_worker_id_str not like", value, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrIn(List<String> values) {
            addCriterion("review_worker_id_str in", values, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrNotIn(List<String> values) {
            addCriterion("review_worker_id_str not in", values, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrBetween(String value1, String value2) {
            addCriterion("review_worker_id_str between", value1, value2, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewWorkerIdStrNotBetween(String value1, String value2) {
            addCriterion("review_worker_id_str not between", value1, value2, "reviewWorkerIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewResultIsNull() {
            addCriterion("review_result is null");
            return (Criteria) this;
        }

        public Criteria andReviewResultIsNotNull() {
            addCriterion("review_result is not null");
            return (Criteria) this;
        }

        public Criteria andReviewResultEqualTo(String value) {
            addCriterion("review_result =", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultNotEqualTo(String value) {
            addCriterion("review_result <>", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultGreaterThan(String value) {
            addCriterion("review_result >", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultGreaterThanOrEqualTo(String value) {
            addCriterion("review_result >=", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultLessThan(String value) {
            addCriterion("review_result <", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultLessThanOrEqualTo(String value) {
            addCriterion("review_result <=", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultLike(String value) {
            addCriterion("review_result like", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultNotLike(String value) {
            addCriterion("review_result not like", value, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultIn(List<String> values) {
            addCriterion("review_result in", values, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultNotIn(List<String> values) {
            addCriterion("review_result not in", values, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultBetween(String value1, String value2) {
            addCriterion("review_result between", value1, value2, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andReviewResultNotBetween(String value1, String value2) {
            addCriterion("review_result not between", value1, value2, "reviewResult");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescIsNull() {
            addCriterion("evaluate_desc is null");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescIsNotNull() {
            addCriterion("evaluate_desc is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescEqualTo(String value) {
            addCriterion("evaluate_desc =", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescNotEqualTo(String value) {
            addCriterion("evaluate_desc <>", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescGreaterThan(String value) {
            addCriterion("evaluate_desc >", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescGreaterThanOrEqualTo(String value) {
            addCriterion("evaluate_desc >=", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescLessThan(String value) {
            addCriterion("evaluate_desc <", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescLessThanOrEqualTo(String value) {
            addCriterion("evaluate_desc <=", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescLike(String value) {
            addCriterion("evaluate_desc like", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescNotLike(String value) {
            addCriterion("evaluate_desc not like", value, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescIn(List<String> values) {
            addCriterion("evaluate_desc in", values, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescNotIn(List<String> values) {
            addCriterion("evaluate_desc not in", values, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescBetween(String value1, String value2) {
            addCriterion("evaluate_desc between", value1, value2, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateDescNotBetween(String value1, String value2) {
            addCriterion("evaluate_desc not between", value1, value2, "evaluateDesc");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreIsNull() {
            addCriterion("evaluate_score is null");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreIsNotNull() {
            addCriterion("evaluate_score is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreEqualTo(Integer value) {
            addCriterion("evaluate_score =", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreNotEqualTo(Integer value) {
            addCriterion("evaluate_score <>", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreGreaterThan(Integer value) {
            addCriterion("evaluate_score >", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("evaluate_score >=", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreLessThan(Integer value) {
            addCriterion("evaluate_score <", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreLessThanOrEqualTo(Integer value) {
            addCriterion("evaluate_score <=", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreIn(List<Integer> values) {
            addCriterion("evaluate_score in", values, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreNotIn(List<Integer> values) {
            addCriterion("evaluate_score not in", values, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreBetween(Integer value1, Integer value2) {
            addCriterion("evaluate_score between", value1, value2, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("evaluate_score not between", value1, value2, "evaluateScore");
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

        public Criteria andCompleteTimeIsNull() {
            addCriterion("complete_time is null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIsNotNull() {
            addCriterion("complete_time is not null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeEqualTo(Date value) {
            addCriterion("complete_time =", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotEqualTo(Date value) {
            addCriterion("complete_time <>", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThan(Date value) {
            addCriterion("complete_time >", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("complete_time >=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThan(Date value) {
            addCriterion("complete_time <", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("complete_time <=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIn(List<Date> values) {
            addCriterion("complete_time in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotIn(List<Date> values) {
            addCriterion("complete_time not in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeBetween(Date value1, Date value2) {
            addCriterion("complete_time between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("complete_time not between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsIsNull() {
            addCriterion("settle_accounts is null");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsIsNotNull() {
            addCriterion("settle_accounts is not null");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsEqualTo(Integer value) {
            addCriterion("settle_accounts =", value, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsNotEqualTo(Integer value) {
            addCriterion("settle_accounts <>", value, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsGreaterThan(Integer value) {
            addCriterion("settle_accounts >", value, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("settle_accounts >=", value, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsLessThan(Integer value) {
            addCriterion("settle_accounts <", value, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsLessThanOrEqualTo(Integer value) {
            addCriterion("settle_accounts <=", value, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsIn(List<Integer> values) {
            addCriterion("settle_accounts in", values, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsNotIn(List<Integer> values) {
            addCriterion("settle_accounts not in", values, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsBetween(Integer value1, Integer value2) {
            addCriterion("settle_accounts between", value1, value2, "settleAccounts");
            return (Criteria) this;
        }

        public Criteria andSettleAccountsNotBetween(Integer value1, Integer value2) {
            addCriterion("settle_accounts not between", value1, value2, "settleAccounts");
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

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
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