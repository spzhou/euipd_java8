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

public class RdmsProjectSubprojectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsProjectSubprojectExample() {
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

        public Criteria andProjectCodeIsNull() {
            addCriterion("project_code is null");
            return (Criteria) this;
        }

        public Criteria andProjectCodeIsNotNull() {
            addCriterion("project_code is not null");
            return (Criteria) this;
        }

        public Criteria andProjectCodeEqualTo(String value) {
            addCriterion("project_code =", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotEqualTo(String value) {
            addCriterion("project_code <>", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeGreaterThan(String value) {
            addCriterion("project_code >", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeGreaterThanOrEqualTo(String value) {
            addCriterion("project_code >=", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeLessThan(String value) {
            addCriterion("project_code <", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeLessThanOrEqualTo(String value) {
            addCriterion("project_code <=", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeLike(String value) {
            addCriterion("project_code like", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotLike(String value) {
            addCriterion("project_code not like", value, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeIn(List<String> values) {
            addCriterion("project_code in", values, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotIn(List<String> values) {
            addCriterion("project_code not in", values, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeBetween(String value1, String value2) {
            addCriterion("project_code between", value1, value2, "projectCode");
            return (Criteria) this;
        }

        public Criteria andProjectCodeNotBetween(String value1, String value2) {
            addCriterion("project_code not between", value1, value2, "projectCode");
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

        public Criteria andLabelIsNull() {
            addCriterion("`label` is null");
            return (Criteria) this;
        }

        public Criteria andLabelIsNotNull() {
            addCriterion("`label` is not null");
            return (Criteria) this;
        }

        public Criteria andLabelEqualTo(String value) {
            addCriterion("`label` =", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotEqualTo(String value) {
            addCriterion("`label` <>", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThan(String value) {
            addCriterion("`label` >", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThanOrEqualTo(String value) {
            addCriterion("`label` >=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThan(String value) {
            addCriterion("`label` <", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThanOrEqualTo(String value) {
            addCriterion("`label` <=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLike(String value) {
            addCriterion("`label` like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotLike(String value) {
            addCriterion("`label` not like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelIn(List<String> values) {
            addCriterion("`label` in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotIn(List<String> values) {
            addCriterion("`label` not in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelBetween(String value1, String value2) {
            addCriterion("`label` between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotBetween(String value1, String value2) {
            addCriterion("`label` not between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNull() {
            addCriterion("introduce is null");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNotNull() {
            addCriterion("introduce is not null");
            return (Criteria) this;
        }

        public Criteria andIntroduceEqualTo(String value) {
            addCriterion("introduce =", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotEqualTo(String value) {
            addCriterion("introduce <>", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThan(String value) {
            addCriterion("introduce >", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThanOrEqualTo(String value) {
            addCriterion("introduce >=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThan(String value) {
            addCriterion("introduce <", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThanOrEqualTo(String value) {
            addCriterion("introduce <=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLike(String value) {
            addCriterion("introduce like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotLike(String value) {
            addCriterion("introduce not like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceIn(List<String> values) {
            addCriterion("introduce in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotIn(List<String> values) {
            addCriterion("introduce not in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceBetween(String value1, String value2) {
            addCriterion("introduce between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotBetween(String value1, String value2) {
            addCriterion("introduce not between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andSuperviseIsNull() {
            addCriterion("supervise is null");
            return (Criteria) this;
        }

        public Criteria andSuperviseIsNotNull() {
            addCriterion("supervise is not null");
            return (Criteria) this;
        }

        public Criteria andSuperviseEqualTo(String value) {
            addCriterion("supervise =", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseNotEqualTo(String value) {
            addCriterion("supervise <>", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseGreaterThan(String value) {
            addCriterion("supervise >", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseGreaterThanOrEqualTo(String value) {
            addCriterion("supervise >=", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseLessThan(String value) {
            addCriterion("supervise <", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseLessThanOrEqualTo(String value) {
            addCriterion("supervise <=", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseLike(String value) {
            addCriterion("supervise like", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseNotLike(String value) {
            addCriterion("supervise not like", value, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseIn(List<String> values) {
            addCriterion("supervise in", values, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseNotIn(List<String> values) {
            addCriterion("supervise not in", values, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseBetween(String value1, String value2) {
            addCriterion("supervise between", value1, value2, "supervise");
            return (Criteria) this;
        }

        public Criteria andSuperviseNotBetween(String value1, String value2) {
            addCriterion("supervise not between", value1, value2, "supervise");
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

        public Criteria andKeyMemberListStrIsNull() {
            addCriterion("key_member_list_str is null");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrIsNotNull() {
            addCriterion("key_member_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrEqualTo(String value) {
            addCriterion("key_member_list_str =", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrNotEqualTo(String value) {
            addCriterion("key_member_list_str <>", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrGreaterThan(String value) {
            addCriterion("key_member_list_str >", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrGreaterThanOrEqualTo(String value) {
            addCriterion("key_member_list_str >=", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrLessThan(String value) {
            addCriterion("key_member_list_str <", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrLessThanOrEqualTo(String value) {
            addCriterion("key_member_list_str <=", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrLike(String value) {
            addCriterion("key_member_list_str like", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrNotLike(String value) {
            addCriterion("key_member_list_str not like", value, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrIn(List<String> values) {
            addCriterion("key_member_list_str in", values, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrNotIn(List<String> values) {
            addCriterion("key_member_list_str not in", values, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrBetween(String value1, String value2) {
            addCriterion("key_member_list_str between", value1, value2, "keyMemberListStr");
            return (Criteria) this;
        }

        public Criteria andKeyMemberListStrNotBetween(String value1, String value2) {
            addCriterion("key_member_list_str not between", value1, value2, "keyMemberListStr");
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

        public Criteria andAddChargeIsNull() {
            addCriterion("add_charge is null");
            return (Criteria) this;
        }

        public Criteria andAddChargeIsNotNull() {
            addCriterion("add_charge is not null");
            return (Criteria) this;
        }

        public Criteria andAddChargeEqualTo(BigDecimal value) {
            addCriterion("add_charge =", value, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeNotEqualTo(BigDecimal value) {
            addCriterion("add_charge <>", value, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeGreaterThan(BigDecimal value) {
            addCriterion("add_charge >", value, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("add_charge >=", value, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeLessThan(BigDecimal value) {
            addCriterion("add_charge <", value, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("add_charge <=", value, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeIn(List<BigDecimal> values) {
            addCriterion("add_charge in", values, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeNotIn(List<BigDecimal> values) {
            addCriterion("add_charge not in", values, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("add_charge between", value1, value2, "addCharge");
            return (Criteria) this;
        }

        public Criteria andAddChargeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("add_charge not between", value1, value2, "addCharge");
            return (Criteria) this;
        }

        public Criteria andChargeRateIsNull() {
            addCriterion("charge_rate is null");
            return (Criteria) this;
        }

        public Criteria andChargeRateIsNotNull() {
            addCriterion("charge_rate is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRateEqualTo(BigDecimal value) {
            addCriterion("charge_rate =", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotEqualTo(BigDecimal value) {
            addCriterion("charge_rate <>", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateGreaterThan(BigDecimal value) {
            addCriterion("charge_rate >", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_rate >=", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateLessThan(BigDecimal value) {
            addCriterion("charge_rate <", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_rate <=", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateIn(List<BigDecimal> values) {
            addCriterion("charge_rate in", values, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotIn(List<BigDecimal> values) {
            addCriterion("charge_rate not in", values, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_rate between", value1, value2, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_rate not between", value1, value2, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyIsNull() {
            addCriterion("incentive_policy is null");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyIsNotNull() {
            addCriterion("incentive_policy is not null");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyEqualTo(String value) {
            addCriterion("incentive_policy =", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyNotEqualTo(String value) {
            addCriterion("incentive_policy <>", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyGreaterThan(String value) {
            addCriterion("incentive_policy >", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyGreaterThanOrEqualTo(String value) {
            addCriterion("incentive_policy >=", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyLessThan(String value) {
            addCriterion("incentive_policy <", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyLessThanOrEqualTo(String value) {
            addCriterion("incentive_policy <=", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyLike(String value) {
            addCriterion("incentive_policy like", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyNotLike(String value) {
            addCriterion("incentive_policy not like", value, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyIn(List<String> values) {
            addCriterion("incentive_policy in", values, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyNotIn(List<String> values) {
            addCriterion("incentive_policy not in", values, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyBetween(String value1, String value2) {
            addCriterion("incentive_policy between", value1, value2, "incentivePolicy");
            return (Criteria) this;
        }

        public Criteria andIncentivePolicyNotBetween(String value1, String value2) {
            addCriterion("incentive_policy not between", value1, value2, "incentivePolicy");
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

        public Criteria andRdTypeIsNull() {
            addCriterion("rd_type is null");
            return (Criteria) this;
        }

        public Criteria andRdTypeIsNotNull() {
            addCriterion("rd_type is not null");
            return (Criteria) this;
        }

        public Criteria andRdTypeEqualTo(String value) {
            addCriterion("rd_type =", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeNotEqualTo(String value) {
            addCriterion("rd_type <>", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeGreaterThan(String value) {
            addCriterion("rd_type >", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeGreaterThanOrEqualTo(String value) {
            addCriterion("rd_type >=", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeLessThan(String value) {
            addCriterion("rd_type <", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeLessThanOrEqualTo(String value) {
            addCriterion("rd_type <=", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeLike(String value) {
            addCriterion("rd_type like", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeNotLike(String value) {
            addCriterion("rd_type not like", value, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeIn(List<String> values) {
            addCriterion("rd_type in", values, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeNotIn(List<String> values) {
            addCriterion("rd_type not in", values, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeBetween(String value1, String value2) {
            addCriterion("rd_type between", value1, value2, "rdType");
            return (Criteria) this;
        }

        public Criteria andRdTypeNotBetween(String value1, String value2) {
            addCriterion("rd_type not between", value1, value2, "rdType");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(String value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(String value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(String value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(String value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(String value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(String value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLike(String value) {
            addCriterion("creator_id like", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotLike(String value) {
            addCriterion("creator_id not like", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<String> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<String> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(String value1, String value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(String value1, String value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andDevVersionIsNull() {
            addCriterion("dev_version is null");
            return (Criteria) this;
        }

        public Criteria andDevVersionIsNotNull() {
            addCriterion("dev_version is not null");
            return (Criteria) this;
        }

        public Criteria andDevVersionEqualTo(BigDecimal value) {
            addCriterion("dev_version =", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionNotEqualTo(BigDecimal value) {
            addCriterion("dev_version <>", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionGreaterThan(BigDecimal value) {
            addCriterion("dev_version >", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("dev_version >=", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionLessThan(BigDecimal value) {
            addCriterion("dev_version <", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("dev_version <=", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionIn(List<BigDecimal> values) {
            addCriterion("dev_version in", values, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionNotIn(List<BigDecimal> values) {
            addCriterion("dev_version not in", values, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("dev_version between", value1, value2, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("dev_version not between", value1, value2, "devVersion");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdIsNull() {
            addCriterion("target_milestone_id is null");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdIsNotNull() {
            addCriterion("target_milestone_id is not null");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdEqualTo(String value) {
            addCriterion("target_milestone_id =", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdNotEqualTo(String value) {
            addCriterion("target_milestone_id <>", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdGreaterThan(String value) {
            addCriterion("target_milestone_id >", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdGreaterThanOrEqualTo(String value) {
            addCriterion("target_milestone_id >=", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdLessThan(String value) {
            addCriterion("target_milestone_id <", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdLessThanOrEqualTo(String value) {
            addCriterion("target_milestone_id <=", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdLike(String value) {
            addCriterion("target_milestone_id like", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdNotLike(String value) {
            addCriterion("target_milestone_id not like", value, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdIn(List<String> values) {
            addCriterion("target_milestone_id in", values, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdNotIn(List<String> values) {
            addCriterion("target_milestone_id not in", values, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdBetween(String value1, String value2) {
            addCriterion("target_milestone_id between", value1, value2, "targetMilestoneId");
            return (Criteria) this;
        }

        public Criteria andTargetMilestoneIdNotBetween(String value1, String value2) {
            addCriterion("target_milestone_id not between", value1, value2, "targetMilestoneId");
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

        public Criteria andReleaseTimeIsNull() {
            addCriterion("release_time is null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIsNotNull() {
            addCriterion("release_time is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeEqualTo(Date value) {
            addCriterion("release_time =", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotEqualTo(Date value) {
            addCriterion("release_time <>", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeGreaterThan(Date value) {
            addCriterion("release_time >", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("release_time >=", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeLessThan(Date value) {
            addCriterion("release_time <", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeLessThanOrEqualTo(Date value) {
            addCriterion("release_time <=", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIn(List<Date> values) {
            addCriterion("release_time in", values, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotIn(List<Date> values) {
            addCriterion("release_time not in", values, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeBetween(Date value1, Date value2) {
            addCriterion("release_time between", value1, value2, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotBetween(Date value1, Date value2) {
            addCriterion("release_time not between", value1, value2, "releaseTime");
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

        public Criteria andOutSourceStatusIsNull() {
            addCriterion("out_source_status is null");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusIsNotNull() {
            addCriterion("out_source_status is not null");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusEqualTo(String value) {
            addCriterion("out_source_status =", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusNotEqualTo(String value) {
            addCriterion("out_source_status <>", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusGreaterThan(String value) {
            addCriterion("out_source_status >", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusGreaterThanOrEqualTo(String value) {
            addCriterion("out_source_status >=", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusLessThan(String value) {
            addCriterion("out_source_status <", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusLessThanOrEqualTo(String value) {
            addCriterion("out_source_status <=", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusLike(String value) {
            addCriterion("out_source_status like", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusNotLike(String value) {
            addCriterion("out_source_status not like", value, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusIn(List<String> values) {
            addCriterion("out_source_status in", values, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusNotIn(List<String> values) {
            addCriterion("out_source_status not in", values, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusBetween(String value1, String value2) {
            addCriterion("out_source_status between", value1, value2, "outSourceStatus");
            return (Criteria) this;
        }

        public Criteria andOutSourceStatusNotBetween(String value1, String value2) {
            addCriterion("out_source_status not between", value1, value2, "outSourceStatus");
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