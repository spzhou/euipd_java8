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

public class RdmsProjectTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsProjectTaskExample() {
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

        public Criteria andTaskSerialNoIsNull() {
            addCriterion("task_serial_no is null");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoIsNotNull() {
            addCriterion("task_serial_no is not null");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoEqualTo(String value) {
            addCriterion("task_serial_no =", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoNotEqualTo(String value) {
            addCriterion("task_serial_no <>", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoGreaterThan(String value) {
            addCriterion("task_serial_no >", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("task_serial_no >=", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoLessThan(String value) {
            addCriterion("task_serial_no <", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoLessThanOrEqualTo(String value) {
            addCriterion("task_serial_no <=", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoLike(String value) {
            addCriterion("task_serial_no like", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoNotLike(String value) {
            addCriterion("task_serial_no not like", value, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoIn(List<String> values) {
            addCriterion("task_serial_no in", values, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoNotIn(List<String> values) {
            addCriterion("task_serial_no not in", values, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoBetween(String value1, String value2) {
            addCriterion("task_serial_no between", value1, value2, "taskSerialNo");
            return (Criteria) this;
        }

        public Criteria andTaskSerialNoNotBetween(String value1, String value2) {
            addCriterion("task_serial_no not between", value1, value2, "taskSerialNo");
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

        public Criteria andTaskNameIsNull() {
            addCriterion("task_name is null");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNotNull() {
            addCriterion("task_name is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNameEqualTo(String value) {
            addCriterion("task_name =", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotEqualTo(String value) {
            addCriterion("task_name <>", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThan(String value) {
            addCriterion("task_name >", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
            addCriterion("task_name >=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThan(String value) {
            addCriterion("task_name <", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThanOrEqualTo(String value) {
            addCriterion("task_name <=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLike(String value) {
            addCriterion("task_name like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotLike(String value) {
            addCriterion("task_name not like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameIn(List<String> values) {
            addCriterion("task_name in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotIn(List<String> values) {
            addCriterion("task_name not in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameBetween(String value1, String value2) {
            addCriterion("task_name between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotBetween(String value1, String value2) {
            addCriterion("task_name not between", value1, value2, "taskName");
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

        public Criteria andTestMethodIsNull() {
            addCriterion("test_method is null");
            return (Criteria) this;
        }

        public Criteria andTestMethodIsNotNull() {
            addCriterion("test_method is not null");
            return (Criteria) this;
        }

        public Criteria andTestMethodEqualTo(String value) {
            addCriterion("test_method =", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodNotEqualTo(String value) {
            addCriterion("test_method <>", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodGreaterThan(String value) {
            addCriterion("test_method >", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodGreaterThanOrEqualTo(String value) {
            addCriterion("test_method >=", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodLessThan(String value) {
            addCriterion("test_method <", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodLessThanOrEqualTo(String value) {
            addCriterion("test_method <=", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodLike(String value) {
            addCriterion("test_method like", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodNotLike(String value) {
            addCriterion("test_method not like", value, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodIn(List<String> values) {
            addCriterion("test_method in", values, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodNotIn(List<String> values) {
            addCriterion("test_method not in", values, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodBetween(String value1, String value2) {
            addCriterion("test_method between", value1, value2, "testMethod");
            return (Criteria) this;
        }

        public Criteria andTestMethodNotBetween(String value1, String value2) {
            addCriterion("test_method not between", value1, value2, "testMethod");
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

        public Criteria andManagerIdIsNull() {
            addCriterion("manager_id is null");
            return (Criteria) this;
        }

        public Criteria andManagerIdIsNotNull() {
            addCriterion("manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andManagerIdEqualTo(String value) {
            addCriterion("manager_id =", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotEqualTo(String value) {
            addCriterion("manager_id <>", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThan(String value) {
            addCriterion("manager_id >", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThanOrEqualTo(String value) {
            addCriterion("manager_id >=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThan(String value) {
            addCriterion("manager_id <", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThanOrEqualTo(String value) {
            addCriterion("manager_id <=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLike(String value) {
            addCriterion("manager_id like", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotLike(String value) {
            addCriterion("manager_id not like", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdIn(List<String> values) {
            addCriterion("manager_id in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotIn(List<String> values) {
            addCriterion("manager_id not in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdBetween(String value1, String value2) {
            addCriterion("manager_id between", value1, value2, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotBetween(String value1, String value2) {
            addCriterion("manager_id not between", value1, value2, "managerId");
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

        public Criteria andTreeDeepIsNull() {
            addCriterion("tree_deep is null");
            return (Criteria) this;
        }

        public Criteria andTreeDeepIsNotNull() {
            addCriterion("tree_deep is not null");
            return (Criteria) this;
        }

        public Criteria andTreeDeepEqualTo(Integer value) {
            addCriterion("tree_deep =", value, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepNotEqualTo(Integer value) {
            addCriterion("tree_deep <>", value, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepGreaterThan(Integer value) {
            addCriterion("tree_deep >", value, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepGreaterThanOrEqualTo(Integer value) {
            addCriterion("tree_deep >=", value, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepLessThan(Integer value) {
            addCriterion("tree_deep <", value, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepLessThanOrEqualTo(Integer value) {
            addCriterion("tree_deep <=", value, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepIn(List<Integer> values) {
            addCriterion("tree_deep in", values, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepNotIn(List<Integer> values) {
            addCriterion("tree_deep not in", values, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepBetween(Integer value1, Integer value2) {
            addCriterion("tree_deep between", value1, value2, "treeDeep");
            return (Criteria) this;
        }

        public Criteria andTreeDeepNotBetween(Integer value1, Integer value2) {
            addCriterion("tree_deep not between", value1, value2, "treeDeep");
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

        public Criteria andParentTaskIdIsNull() {
            addCriterion("parent_task_id is null");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdIsNotNull() {
            addCriterion("parent_task_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdEqualTo(String value) {
            addCriterion("parent_task_id =", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdNotEqualTo(String value) {
            addCriterion("parent_task_id <>", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdGreaterThan(String value) {
            addCriterion("parent_task_id >", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_task_id >=", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdLessThan(String value) {
            addCriterion("parent_task_id <", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdLessThanOrEqualTo(String value) {
            addCriterion("parent_task_id <=", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdLike(String value) {
            addCriterion("parent_task_id like", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdNotLike(String value) {
            addCriterion("parent_task_id not like", value, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdIn(List<String> values) {
            addCriterion("parent_task_id in", values, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdNotIn(List<String> values) {
            addCriterion("parent_task_id not in", values, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdBetween(String value1, String value2) {
            addCriterion("parent_task_id between", value1, value2, "parentTaskId");
            return (Criteria) this;
        }

        public Criteria andParentTaskIdNotBetween(String value1, String value2) {
            addCriterion("parent_task_id not between", value1, value2, "parentTaskId");
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