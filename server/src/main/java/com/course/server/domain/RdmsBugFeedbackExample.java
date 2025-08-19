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

public class RdmsBugFeedbackExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsBugFeedbackExample() {
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

        public Criteria andBugSerialIsNull() {
            addCriterion("bug_serial is null");
            return (Criteria) this;
        }

        public Criteria andBugSerialIsNotNull() {
            addCriterion("bug_serial is not null");
            return (Criteria) this;
        }

        public Criteria andBugSerialEqualTo(String value) {
            addCriterion("bug_serial =", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialNotEqualTo(String value) {
            addCriterion("bug_serial <>", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialGreaterThan(String value) {
            addCriterion("bug_serial >", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialGreaterThanOrEqualTo(String value) {
            addCriterion("bug_serial >=", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialLessThan(String value) {
            addCriterion("bug_serial <", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialLessThanOrEqualTo(String value) {
            addCriterion("bug_serial <=", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialLike(String value) {
            addCriterion("bug_serial like", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialNotLike(String value) {
            addCriterion("bug_serial not like", value, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialIn(List<String> values) {
            addCriterion("bug_serial in", values, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialNotIn(List<String> values) {
            addCriterion("bug_serial not in", values, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialBetween(String value1, String value2) {
            addCriterion("bug_serial between", value1, value2, "bugSerial");
            return (Criteria) this;
        }

        public Criteria andBugSerialNotBetween(String value1, String value2) {
            addCriterion("bug_serial not between", value1, value2, "bugSerial");
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

        public Criteria andAssociateProjectStrIsNull() {
            addCriterion("associate_project_str is null");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrIsNotNull() {
            addCriterion("associate_project_str is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrEqualTo(String value) {
            addCriterion("associate_project_str =", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrNotEqualTo(String value) {
            addCriterion("associate_project_str <>", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrGreaterThan(String value) {
            addCriterion("associate_project_str >", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrGreaterThanOrEqualTo(String value) {
            addCriterion("associate_project_str >=", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrLessThan(String value) {
            addCriterion("associate_project_str <", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrLessThanOrEqualTo(String value) {
            addCriterion("associate_project_str <=", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrLike(String value) {
            addCriterion("associate_project_str like", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrNotLike(String value) {
            addCriterion("associate_project_str not like", value, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrIn(List<String> values) {
            addCriterion("associate_project_str in", values, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrNotIn(List<String> values) {
            addCriterion("associate_project_str not in", values, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrBetween(String value1, String value2) {
            addCriterion("associate_project_str between", value1, value2, "associateProjectStr");
            return (Criteria) this;
        }

        public Criteria andAssociateProjectStrNotBetween(String value1, String value2) {
            addCriterion("associate_project_str not between", value1, value2, "associateProjectStr");
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

        public Criteria andConsumerIsNull() {
            addCriterion("consumer is null");
            return (Criteria) this;
        }

        public Criteria andConsumerIsNotNull() {
            addCriterion("consumer is not null");
            return (Criteria) this;
        }

        public Criteria andConsumerEqualTo(String value) {
            addCriterion("consumer =", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerNotEqualTo(String value) {
            addCriterion("consumer <>", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerGreaterThan(String value) {
            addCriterion("consumer >", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerGreaterThanOrEqualTo(String value) {
            addCriterion("consumer >=", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerLessThan(String value) {
            addCriterion("consumer <", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerLessThanOrEqualTo(String value) {
            addCriterion("consumer <=", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerLike(String value) {
            addCriterion("consumer like", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerNotLike(String value) {
            addCriterion("consumer not like", value, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerIn(List<String> values) {
            addCriterion("consumer in", values, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerNotIn(List<String> values) {
            addCriterion("consumer not in", values, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerBetween(String value1, String value2) {
            addCriterion("consumer between", value1, value2, "consumer");
            return (Criteria) this;
        }

        public Criteria andConsumerNotBetween(String value1, String value2) {
            addCriterion("consumer not between", value1, value2, "consumer");
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

        public Criteria andConfirmTelIsNull() {
            addCriterion("confirm_tel is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTelIsNotNull() {
            addCriterion("confirm_tel is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTelEqualTo(String value) {
            addCriterion("confirm_tel =", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelNotEqualTo(String value) {
            addCriterion("confirm_tel <>", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelGreaterThan(String value) {
            addCriterion("confirm_tel >", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelGreaterThanOrEqualTo(String value) {
            addCriterion("confirm_tel >=", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelLessThan(String value) {
            addCriterion("confirm_tel <", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelLessThanOrEqualTo(String value) {
            addCriterion("confirm_tel <=", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelLike(String value) {
            addCriterion("confirm_tel like", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelNotLike(String value) {
            addCriterion("confirm_tel not like", value, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelIn(List<String> values) {
            addCriterion("confirm_tel in", values, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelNotIn(List<String> values) {
            addCriterion("confirm_tel not in", values, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelBetween(String value1, String value2) {
            addCriterion("confirm_tel between", value1, value2, "confirmTel");
            return (Criteria) this;
        }

        public Criteria andConfirmTelNotBetween(String value1, String value2) {
            addCriterion("confirm_tel not between", value1, value2, "confirmTel");
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

        public Criteria andBugDescriptionIsNull() {
            addCriterion("bug_description is null");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionIsNotNull() {
            addCriterion("bug_description is not null");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionEqualTo(String value) {
            addCriterion("bug_description =", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionNotEqualTo(String value) {
            addCriterion("bug_description <>", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionGreaterThan(String value) {
            addCriterion("bug_description >", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("bug_description >=", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionLessThan(String value) {
            addCriterion("bug_description <", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionLessThanOrEqualTo(String value) {
            addCriterion("bug_description <=", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionLike(String value) {
            addCriterion("bug_description like", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionNotLike(String value) {
            addCriterion("bug_description not like", value, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionIn(List<String> values) {
            addCriterion("bug_description in", values, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionNotIn(List<String> values) {
            addCriterion("bug_description not in", values, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionBetween(String value1, String value2) {
            addCriterion("bug_description between", value1, value2, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andBugDescriptionNotBetween(String value1, String value2) {
            addCriterion("bug_description not between", value1, value2, "bugDescription");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionIsNull() {
            addCriterion("trigger_condition is null");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionIsNotNull() {
            addCriterion("trigger_condition is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionEqualTo(String value) {
            addCriterion("trigger_condition =", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionNotEqualTo(String value) {
            addCriterion("trigger_condition <>", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionGreaterThan(String value) {
            addCriterion("trigger_condition >", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionGreaterThanOrEqualTo(String value) {
            addCriterion("trigger_condition >=", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionLessThan(String value) {
            addCriterion("trigger_condition <", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionLessThanOrEqualTo(String value) {
            addCriterion("trigger_condition <=", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionLike(String value) {
            addCriterion("trigger_condition like", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionNotLike(String value) {
            addCriterion("trigger_condition not like", value, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionIn(List<String> values) {
            addCriterion("trigger_condition in", values, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionNotIn(List<String> values) {
            addCriterion("trigger_condition not in", values, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionBetween(String value1, String value2) {
            addCriterion("trigger_condition between", value1, value2, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andTriggerConditionNotBetween(String value1, String value2) {
            addCriterion("trigger_condition not between", value1, value2, "triggerCondition");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameIsNull() {
            addCriterion("analysis_name is null");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameIsNotNull() {
            addCriterion("analysis_name is not null");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameEqualTo(String value) {
            addCriterion("analysis_name =", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameNotEqualTo(String value) {
            addCriterion("analysis_name <>", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameGreaterThan(String value) {
            addCriterion("analysis_name >", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameGreaterThanOrEqualTo(String value) {
            addCriterion("analysis_name >=", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameLessThan(String value) {
            addCriterion("analysis_name <", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameLessThanOrEqualTo(String value) {
            addCriterion("analysis_name <=", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameLike(String value) {
            addCriterion("analysis_name like", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameNotLike(String value) {
            addCriterion("analysis_name not like", value, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameIn(List<String> values) {
            addCriterion("analysis_name in", values, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameNotIn(List<String> values) {
            addCriterion("analysis_name not in", values, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameBetween(String value1, String value2) {
            addCriterion("analysis_name between", value1, value2, "analysisName");
            return (Criteria) this;
        }

        public Criteria andAnalysisNameNotBetween(String value1, String value2) {
            addCriterion("analysis_name not between", value1, value2, "analysisName");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisIsNull() {
            addCriterion("essential_analysis is null");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisIsNotNull() {
            addCriterion("essential_analysis is not null");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisEqualTo(String value) {
            addCriterion("essential_analysis =", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisNotEqualTo(String value) {
            addCriterion("essential_analysis <>", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisGreaterThan(String value) {
            addCriterion("essential_analysis >", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisGreaterThanOrEqualTo(String value) {
            addCriterion("essential_analysis >=", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisLessThan(String value) {
            addCriterion("essential_analysis <", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisLessThanOrEqualTo(String value) {
            addCriterion("essential_analysis <=", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisLike(String value) {
            addCriterion("essential_analysis like", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisNotLike(String value) {
            addCriterion("essential_analysis not like", value, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisIn(List<String> values) {
            addCriterion("essential_analysis in", values, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisNotIn(List<String> values) {
            addCriterion("essential_analysis not in", values, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisBetween(String value1, String value2) {
            addCriterion("essential_analysis between", value1, value2, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andEssentialAnalysisNotBetween(String value1, String value2) {
            addCriterion("essential_analysis not between", value1, value2, "essentialAnalysis");
            return (Criteria) this;
        }

        public Criteria andSolutionIsNull() {
            addCriterion("solution is null");
            return (Criteria) this;
        }

        public Criteria andSolutionIsNotNull() {
            addCriterion("solution is not null");
            return (Criteria) this;
        }

        public Criteria andSolutionEqualTo(String value) {
            addCriterion("solution =", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotEqualTo(String value) {
            addCriterion("solution <>", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionGreaterThan(String value) {
            addCriterion("solution >", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionGreaterThanOrEqualTo(String value) {
            addCriterion("solution >=", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionLessThan(String value) {
            addCriterion("solution <", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionLessThanOrEqualTo(String value) {
            addCriterion("solution <=", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionLike(String value) {
            addCriterion("solution like", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotLike(String value) {
            addCriterion("solution not like", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionIn(List<String> values) {
            addCriterion("solution in", values, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotIn(List<String> values) {
            addCriterion("solution not in", values, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionBetween(String value1, String value2) {
            addCriterion("solution between", value1, value2, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotBetween(String value1, String value2) {
            addCriterion("solution not between", value1, value2, "solution");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorIsNull() {
            addCriterion("output_indicator is null");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorIsNotNull() {
            addCriterion("output_indicator is not null");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorEqualTo(String value) {
            addCriterion("output_indicator =", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorNotEqualTo(String value) {
            addCriterion("output_indicator <>", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorGreaterThan(String value) {
            addCriterion("output_indicator >", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorGreaterThanOrEqualTo(String value) {
            addCriterion("output_indicator >=", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorLessThan(String value) {
            addCriterion("output_indicator <", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorLessThanOrEqualTo(String value) {
            addCriterion("output_indicator <=", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorLike(String value) {
            addCriterion("output_indicator like", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorNotLike(String value) {
            addCriterion("output_indicator not like", value, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorIn(List<String> values) {
            addCriterion("output_indicator in", values, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorNotIn(List<String> values) {
            addCriterion("output_indicator not in", values, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorBetween(String value1, String value2) {
            addCriterion("output_indicator between", value1, value2, "outputIndicator");
            return (Criteria) this;
        }

        public Criteria andOutputIndicatorNotBetween(String value1, String value2) {
            addCriterion("output_indicator not between", value1, value2, "outputIndicator");
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

        public Criteria andFeedbackDescriptionIsNull() {
            addCriterion("feedback_description is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionIsNotNull() {
            addCriterion("feedback_description is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionEqualTo(String value) {
            addCriterion("feedback_description =", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionNotEqualTo(String value) {
            addCriterion("feedback_description <>", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionGreaterThan(String value) {
            addCriterion("feedback_description >", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("feedback_description >=", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionLessThan(String value) {
            addCriterion("feedback_description <", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionLessThanOrEqualTo(String value) {
            addCriterion("feedback_description <=", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionLike(String value) {
            addCriterion("feedback_description like", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionNotLike(String value) {
            addCriterion("feedback_description not like", value, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionIn(List<String> values) {
            addCriterion("feedback_description in", values, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionNotIn(List<String> values) {
            addCriterion("feedback_description not in", values, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionBetween(String value1, String value2) {
            addCriterion("feedback_description between", value1, value2, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackDescriptionNotBetween(String value1, String value2) {
            addCriterion("feedback_description not between", value1, value2, "feedbackDescription");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrIsNull() {
            addCriterion("feedback_file_id_list_str is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrIsNotNull() {
            addCriterion("feedback_file_id_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrEqualTo(String value) {
            addCriterion("feedback_file_id_list_str =", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrNotEqualTo(String value) {
            addCriterion("feedback_file_id_list_str <>", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrGreaterThan(String value) {
            addCriterion("feedback_file_id_list_str >", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrGreaterThanOrEqualTo(String value) {
            addCriterion("feedback_file_id_list_str >=", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrLessThan(String value) {
            addCriterion("feedback_file_id_list_str <", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrLessThanOrEqualTo(String value) {
            addCriterion("feedback_file_id_list_str <=", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrLike(String value) {
            addCriterion("feedback_file_id_list_str like", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrNotLike(String value) {
            addCriterion("feedback_file_id_list_str not like", value, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrIn(List<String> values) {
            addCriterion("feedback_file_id_list_str in", values, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrNotIn(List<String> values) {
            addCriterion("feedback_file_id_list_str not in", values, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrBetween(String value1, String value2) {
            addCriterion("feedback_file_id_list_str between", value1, value2, "feedbackFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFeedbackFileIdListStrNotBetween(String value1, String value2) {
            addCriterion("feedback_file_id_list_str not between", value1, value2, "feedbackFileIdListStr");
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