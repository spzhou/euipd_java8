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

public class RdmsCustomerUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsCustomerUserExample() {
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

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(String value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(String value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(String value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(String value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(String value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(String value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLike(String value) {
            addCriterion("department_id like", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotLike(String value) {
            addCriterion("department_id not like", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<String> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<String> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(String value1, String value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(String value1, String value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andJobNumIsNull() {
            addCriterion("job_num is null");
            return (Criteria) this;
        }

        public Criteria andJobNumIsNotNull() {
            addCriterion("job_num is not null");
            return (Criteria) this;
        }

        public Criteria andJobNumEqualTo(String value) {
            addCriterion("job_num =", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumNotEqualTo(String value) {
            addCriterion("job_num <>", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumGreaterThan(String value) {
            addCriterion("job_num >", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumGreaterThanOrEqualTo(String value) {
            addCriterion("job_num >=", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumLessThan(String value) {
            addCriterion("job_num <", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumLessThanOrEqualTo(String value) {
            addCriterion("job_num <=", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumLike(String value) {
            addCriterion("job_num like", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumNotLike(String value) {
            addCriterion("job_num not like", value, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumIn(List<String> values) {
            addCriterion("job_num in", values, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumNotIn(List<String> values) {
            addCriterion("job_num not in", values, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumBetween(String value1, String value2) {
            addCriterion("job_num between", value1, value2, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobNumNotBetween(String value1, String value2) {
            addCriterion("job_num not between", value1, value2, "jobNum");
            return (Criteria) this;
        }

        public Criteria andJobPositionIsNull() {
            addCriterion("job_position is null");
            return (Criteria) this;
        }

        public Criteria andJobPositionIsNotNull() {
            addCriterion("job_position is not null");
            return (Criteria) this;
        }

        public Criteria andJobPositionEqualTo(String value) {
            addCriterion("job_position =", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionNotEqualTo(String value) {
            addCriterion("job_position <>", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionGreaterThan(String value) {
            addCriterion("job_position >", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionGreaterThanOrEqualTo(String value) {
            addCriterion("job_position >=", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionLessThan(String value) {
            addCriterion("job_position <", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionLessThanOrEqualTo(String value) {
            addCriterion("job_position <=", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionLike(String value) {
            addCriterion("job_position like", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionNotLike(String value) {
            addCriterion("job_position not like", value, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionIn(List<String> values) {
            addCriterion("job_position in", values, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionNotIn(List<String> values) {
            addCriterion("job_position not in", values, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionBetween(String value1, String value2) {
            addCriterion("job_position between", value1, value2, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andJobPositionNotBetween(String value1, String value2) {
            addCriterion("job_position not between", value1, value2, "jobPosition");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andJobLevelIsNull() {
            addCriterion("job_level is null");
            return (Criteria) this;
        }

        public Criteria andJobLevelIsNotNull() {
            addCriterion("job_level is not null");
            return (Criteria) this;
        }

        public Criteria andJobLevelEqualTo(String value) {
            addCriterion("job_level =", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotEqualTo(String value) {
            addCriterion("job_level <>", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelGreaterThan(String value) {
            addCriterion("job_level >", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelGreaterThanOrEqualTo(String value) {
            addCriterion("job_level >=", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelLessThan(String value) {
            addCriterion("job_level <", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelLessThanOrEqualTo(String value) {
            addCriterion("job_level <=", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelLike(String value) {
            addCriterion("job_level like", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotLike(String value) {
            addCriterion("job_level not like", value, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelIn(List<String> values) {
            addCriterion("job_level in", values, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotIn(List<String> values) {
            addCriterion("job_level not in", values, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelBetween(String value1, String value2) {
            addCriterion("job_level between", value1, value2, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJobLevelNotBetween(String value1, String value2) {
            addCriterion("job_level not between", value1, value2, "jobLevel");
            return (Criteria) this;
        }

        public Criteria andJoinTimeIsNull() {
            addCriterion("join_time is null");
            return (Criteria) this;
        }

        public Criteria andJoinTimeIsNotNull() {
            addCriterion("join_time is not null");
            return (Criteria) this;
        }

        public Criteria andJoinTimeEqualTo(Date value) {
            addCriterion("join_time =", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeNotEqualTo(Date value) {
            addCriterion("join_time <>", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeGreaterThan(Date value) {
            addCriterion("join_time >", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("join_time >=", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeLessThan(Date value) {
            addCriterion("join_time <", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeLessThanOrEqualTo(Date value) {
            addCriterion("join_time <=", value, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeIn(List<Date> values) {
            addCriterion("join_time in", values, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeNotIn(List<Date> values) {
            addCriterion("join_time not in", values, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeBetween(Date value1, Date value2) {
            addCriterion("join_time between", value1, value2, "joinTime");
            return (Criteria) this;
        }

        public Criteria andJoinTimeNotBetween(Date value1, Date value2) {
            addCriterion("join_time not between", value1, value2, "joinTime");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailIsNull() {
            addCriterion("office_email is null");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailIsNotNull() {
            addCriterion("office_email is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailEqualTo(String value) {
            addCriterion("office_email =", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailNotEqualTo(String value) {
            addCriterion("office_email <>", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailGreaterThan(String value) {
            addCriterion("office_email >", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailGreaterThanOrEqualTo(String value) {
            addCriterion("office_email >=", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailLessThan(String value) {
            addCriterion("office_email <", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailLessThanOrEqualTo(String value) {
            addCriterion("office_email <=", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailLike(String value) {
            addCriterion("office_email like", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailNotLike(String value) {
            addCriterion("office_email not like", value, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailIn(List<String> values) {
            addCriterion("office_email in", values, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailNotIn(List<String> values) {
            addCriterion("office_email not in", values, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailBetween(String value1, String value2) {
            addCriterion("office_email between", value1, value2, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeEmailNotBetween(String value1, String value2) {
            addCriterion("office_email not between", value1, value2, "officeEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeTelIsNull() {
            addCriterion("office_tel is null");
            return (Criteria) this;
        }

        public Criteria andOfficeTelIsNotNull() {
            addCriterion("office_tel is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeTelEqualTo(String value) {
            addCriterion("office_tel =", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotEqualTo(String value) {
            addCriterion("office_tel <>", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelGreaterThan(String value) {
            addCriterion("office_tel >", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelGreaterThanOrEqualTo(String value) {
            addCriterion("office_tel >=", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelLessThan(String value) {
            addCriterion("office_tel <", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelLessThanOrEqualTo(String value) {
            addCriterion("office_tel <=", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelLike(String value) {
            addCriterion("office_tel like", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotLike(String value) {
            addCriterion("office_tel not like", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelIn(List<String> values) {
            addCriterion("office_tel in", values, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotIn(List<String> values) {
            addCriterion("office_tel not in", values, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelBetween(String value1, String value2) {
            addCriterion("office_tel between", value1, value2, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotBetween(String value1, String value2) {
            addCriterion("office_tel not between", value1, value2, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressIsNull() {
            addCriterion("office_address is null");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressIsNotNull() {
            addCriterion("office_address is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressEqualTo(String value) {
            addCriterion("office_address =", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotEqualTo(String value) {
            addCriterion("office_address <>", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressGreaterThan(String value) {
            addCriterion("office_address >", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressGreaterThanOrEqualTo(String value) {
            addCriterion("office_address >=", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressLessThan(String value) {
            addCriterion("office_address <", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressLessThanOrEqualTo(String value) {
            addCriterion("office_address <=", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressLike(String value) {
            addCriterion("office_address like", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotLike(String value) {
            addCriterion("office_address not like", value, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressIn(List<String> values) {
            addCriterion("office_address in", values, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotIn(List<String> values) {
            addCriterion("office_address not in", values, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressBetween(String value1, String value2) {
            addCriterion("office_address between", value1, value2, "officeAddress");
            return (Criteria) this;
        }

        public Criteria andOfficeAddressNotBetween(String value1, String value2) {
            addCriterion("office_address not between", value1, value2, "officeAddress");
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNull() {
            addCriterion("login_name is null");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNotNull() {
            addCriterion("login_name is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNameEqualTo(String value) {
            addCriterion("login_name =", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotEqualTo(String value) {
            addCriterion("login_name <>", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThan(String value) {
            addCriterion("login_name >", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThanOrEqualTo(String value) {
            addCriterion("login_name >=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThan(String value) {
            addCriterion("login_name <", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThanOrEqualTo(String value) {
            addCriterion("login_name <=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLike(String value) {
            addCriterion("login_name like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotLike(String value) {
            addCriterion("login_name not like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIn(List<String> values) {
            addCriterion("login_name in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotIn(List<String> values) {
            addCriterion("login_name not in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameBetween(String value1, String value2) {
            addCriterion("login_name between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotBetween(String value1, String value2) {
            addCriterion("login_name not between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andTrueNameIsNull() {
            addCriterion("true_name is null");
            return (Criteria) this;
        }

        public Criteria andTrueNameIsNotNull() {
            addCriterion("true_name is not null");
            return (Criteria) this;
        }

        public Criteria andTrueNameEqualTo(String value) {
            addCriterion("true_name =", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotEqualTo(String value) {
            addCriterion("true_name <>", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameGreaterThan(String value) {
            addCriterion("true_name >", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameGreaterThanOrEqualTo(String value) {
            addCriterion("true_name >=", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameLessThan(String value) {
            addCriterion("true_name <", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameLessThanOrEqualTo(String value) {
            addCriterion("true_name <=", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameLike(String value) {
            addCriterion("true_name like", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotLike(String value) {
            addCriterion("true_name not like", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameIn(List<String> values) {
            addCriterion("true_name in", values, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotIn(List<String> values) {
            addCriterion("true_name not in", values, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameBetween(String value1, String value2) {
            addCriterion("true_name between", value1, value2, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotBetween(String value1, String value2) {
            addCriterion("true_name not between", value1, value2, "trueName");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterion("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterion("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterion("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterion("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterion("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterion("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterion("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterion("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterion("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterion("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundIsNull() {
            addCriterion("education_background is null");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundIsNotNull() {
            addCriterion("education_background is not null");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundEqualTo(String value) {
            addCriterion("education_background =", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundNotEqualTo(String value) {
            addCriterion("education_background <>", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundGreaterThan(String value) {
            addCriterion("education_background >", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundGreaterThanOrEqualTo(String value) {
            addCriterion("education_background >=", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundLessThan(String value) {
            addCriterion("education_background <", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundLessThanOrEqualTo(String value) {
            addCriterion("education_background <=", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundLike(String value) {
            addCriterion("education_background like", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundNotLike(String value) {
            addCriterion("education_background not like", value, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundIn(List<String> values) {
            addCriterion("education_background in", values, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundNotIn(List<String> values) {
            addCriterion("education_background not in", values, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundBetween(String value1, String value2) {
            addCriterion("education_background between", value1, value2, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andEducationBackgroundNotBetween(String value1, String value2) {
            addCriterion("education_background not between", value1, value2, "educationBackground");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("`degree` is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("`degree` is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("`degree` =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("`degree` <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("`degree` >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("`degree` >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("`degree` <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("`degree` <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("`degree` like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("`degree` not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("`degree` in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("`degree` not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("`degree` between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("`degree` not between", value1, value2, "degree");
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

        public Criteria andProfileIsNull() {
            addCriterion("profile is null");
            return (Criteria) this;
        }

        public Criteria andProfileIsNotNull() {
            addCriterion("profile is not null");
            return (Criteria) this;
        }

        public Criteria andProfileEqualTo(String value) {
            addCriterion("profile =", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotEqualTo(String value) {
            addCriterion("profile <>", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileGreaterThan(String value) {
            addCriterion("profile >", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileGreaterThanOrEqualTo(String value) {
            addCriterion("profile >=", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileLessThan(String value) {
            addCriterion("profile <", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileLessThanOrEqualTo(String value) {
            addCriterion("profile <=", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileLike(String value) {
            addCriterion("profile like", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotLike(String value) {
            addCriterion("profile not like", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileIn(List<String> values) {
            addCriterion("profile in", values, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotIn(List<String> values) {
            addCriterion("profile not in", values, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileBetween(String value1, String value2) {
            addCriterion("profile between", value1, value2, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotBetween(String value1, String value2) {
            addCriterion("profile not between", value1, value2, "profile");
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