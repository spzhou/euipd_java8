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

public class RdmsDemandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsDemandExample() {
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

        public Criteria andDemandNameIsNull() {
            addCriterion("demand_name is null");
            return (Criteria) this;
        }

        public Criteria andDemandNameIsNotNull() {
            addCriterion("demand_name is not null");
            return (Criteria) this;
        }

        public Criteria andDemandNameEqualTo(String value) {
            addCriterion("demand_name =", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameNotEqualTo(String value) {
            addCriterion("demand_name <>", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameGreaterThan(String value) {
            addCriterion("demand_name >", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameGreaterThanOrEqualTo(String value) {
            addCriterion("demand_name >=", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameLessThan(String value) {
            addCriterion("demand_name <", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameLessThanOrEqualTo(String value) {
            addCriterion("demand_name <=", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameLike(String value) {
            addCriterion("demand_name like", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameNotLike(String value) {
            addCriterion("demand_name not like", value, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameIn(List<String> values) {
            addCriterion("demand_name in", values, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameNotIn(List<String> values) {
            addCriterion("demand_name not in", values, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameBetween(String value1, String value2) {
            addCriterion("demand_name between", value1, value2, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandNameNotBetween(String value1, String value2) {
            addCriterion("demand_name not between", value1, value2, "demandName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameIsNull() {
            addCriterion("demand_customer_name is null");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameIsNotNull() {
            addCriterion("demand_customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameEqualTo(String value) {
            addCriterion("demand_customer_name =", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameNotEqualTo(String value) {
            addCriterion("demand_customer_name <>", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameGreaterThan(String value) {
            addCriterion("demand_customer_name >", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("demand_customer_name >=", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameLessThan(String value) {
            addCriterion("demand_customer_name <", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("demand_customer_name <=", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameLike(String value) {
            addCriterion("demand_customer_name like", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameNotLike(String value) {
            addCriterion("demand_customer_name not like", value, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameIn(List<String> values) {
            addCriterion("demand_customer_name in", values, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameNotIn(List<String> values) {
            addCriterion("demand_customer_name not in", values, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameBetween(String value1, String value2) {
            addCriterion("demand_customer_name between", value1, value2, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andDemandCustomerNameNotBetween(String value1, String value2) {
            addCriterion("demand_customer_name not between", value1, value2, "demandCustomerName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameIsNull() {
            addCriterion("confirm_person_name is null");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameIsNotNull() {
            addCriterion("confirm_person_name is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameEqualTo(String value) {
            addCriterion("confirm_person_name =", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameNotEqualTo(String value) {
            addCriterion("confirm_person_name <>", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameGreaterThan(String value) {
            addCriterion("confirm_person_name >", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameGreaterThanOrEqualTo(String value) {
            addCriterion("confirm_person_name >=", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameLessThan(String value) {
            addCriterion("confirm_person_name <", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameLessThanOrEqualTo(String value) {
            addCriterion("confirm_person_name <=", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameLike(String value) {
            addCriterion("confirm_person_name like", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameNotLike(String value) {
            addCriterion("confirm_person_name not like", value, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameIn(List<String> values) {
            addCriterion("confirm_person_name in", values, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameNotIn(List<String> values) {
            addCriterion("confirm_person_name not in", values, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameBetween(String value1, String value2) {
            addCriterion("confirm_person_name between", value1, value2, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmPersonNameNotBetween(String value1, String value2) {
            addCriterion("confirm_person_name not between", value1, value2, "confirmPersonName");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelIsNull() {
            addCriterion("confirm_contact_tel is null");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelIsNotNull() {
            addCriterion("confirm_contact_tel is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelEqualTo(String value) {
            addCriterion("confirm_contact_tel =", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelNotEqualTo(String value) {
            addCriterion("confirm_contact_tel <>", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelGreaterThan(String value) {
            addCriterion("confirm_contact_tel >", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelGreaterThanOrEqualTo(String value) {
            addCriterion("confirm_contact_tel >=", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelLessThan(String value) {
            addCriterion("confirm_contact_tel <", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelLessThanOrEqualTo(String value) {
            addCriterion("confirm_contact_tel <=", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelLike(String value) {
            addCriterion("confirm_contact_tel like", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelNotLike(String value) {
            addCriterion("confirm_contact_tel not like", value, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelIn(List<String> values) {
            addCriterion("confirm_contact_tel in", values, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelNotIn(List<String> values) {
            addCriterion("confirm_contact_tel not in", values, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelBetween(String value1, String value2) {
            addCriterion("confirm_contact_tel between", value1, value2, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andConfirmContactTelNotBetween(String value1, String value2) {
            addCriterion("confirm_contact_tel not between", value1, value2, "confirmContactTel");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionIsNull() {
            addCriterion("demand_description is null");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionIsNotNull() {
            addCriterion("demand_description is not null");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionEqualTo(String value) {
            addCriterion("demand_description =", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionNotEqualTo(String value) {
            addCriterion("demand_description <>", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionGreaterThan(String value) {
            addCriterion("demand_description >", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("demand_description >=", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionLessThan(String value) {
            addCriterion("demand_description <", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionLessThanOrEqualTo(String value) {
            addCriterion("demand_description <=", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionLike(String value) {
            addCriterion("demand_description like", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionNotLike(String value) {
            addCriterion("demand_description not like", value, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionIn(List<String> values) {
            addCriterion("demand_description in", values, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionNotIn(List<String> values) {
            addCriterion("demand_description not in", values, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionBetween(String value1, String value2) {
            addCriterion("demand_description between", value1, value2, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andDemandDescriptionNotBetween(String value1, String value2) {
            addCriterion("demand_description not between", value1, value2, "demandDescription");
            return (Criteria) this;
        }

        public Criteria andWorkConditionIsNull() {
            addCriterion("work_condition is null");
            return (Criteria) this;
        }

        public Criteria andWorkConditionIsNotNull() {
            addCriterion("work_condition is not null");
            return (Criteria) this;
        }

        public Criteria andWorkConditionEqualTo(String value) {
            addCriterion("work_condition =", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionNotEqualTo(String value) {
            addCriterion("work_condition <>", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionGreaterThan(String value) {
            addCriterion("work_condition >", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionGreaterThanOrEqualTo(String value) {
            addCriterion("work_condition >=", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionLessThan(String value) {
            addCriterion("work_condition <", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionLessThanOrEqualTo(String value) {
            addCriterion("work_condition <=", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionLike(String value) {
            addCriterion("work_condition like", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionNotLike(String value) {
            addCriterion("work_condition not like", value, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionIn(List<String> values) {
            addCriterion("work_condition in", values, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionNotIn(List<String> values) {
            addCriterion("work_condition not in", values, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionBetween(String value1, String value2) {
            addCriterion("work_condition between", value1, value2, "workCondition");
            return (Criteria) this;
        }

        public Criteria andWorkConditionNotBetween(String value1, String value2) {
            addCriterion("work_condition not between", value1, value2, "workCondition");
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