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

public class RdmsPreProjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsPreProjectExample() {
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

        public Criteria andPreProjectNameIsNull() {
            addCriterion("pre_project_name is null");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameIsNotNull() {
            addCriterion("pre_project_name is not null");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameEqualTo(String value) {
            addCriterion("pre_project_name =", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameNotEqualTo(String value) {
            addCriterion("pre_project_name <>", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameGreaterThan(String value) {
            addCriterion("pre_project_name >", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("pre_project_name >=", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameLessThan(String value) {
            addCriterion("pre_project_name <", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameLessThanOrEqualTo(String value) {
            addCriterion("pre_project_name <=", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameLike(String value) {
            addCriterion("pre_project_name like", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameNotLike(String value) {
            addCriterion("pre_project_name not like", value, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameIn(List<String> values) {
            addCriterion("pre_project_name in", values, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameNotIn(List<String> values) {
            addCriterion("pre_project_name not in", values, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameBetween(String value1, String value2) {
            addCriterion("pre_project_name between", value1, value2, "preProjectName");
            return (Criteria) this;
        }

        public Criteria andPreProjectNameNotBetween(String value1, String value2) {
            addCriterion("pre_project_name not between", value1, value2, "preProjectName");
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

        public Criteria andPreProjectIntroduceIsNull() {
            addCriterion("pre_project_introduce is null");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceIsNotNull() {
            addCriterion("pre_project_introduce is not null");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceEqualTo(String value) {
            addCriterion("pre_project_introduce =", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceNotEqualTo(String value) {
            addCriterion("pre_project_introduce <>", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceGreaterThan(String value) {
            addCriterion("pre_project_introduce >", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceGreaterThanOrEqualTo(String value) {
            addCriterion("pre_project_introduce >=", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceLessThan(String value) {
            addCriterion("pre_project_introduce <", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceLessThanOrEqualTo(String value) {
            addCriterion("pre_project_introduce <=", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceLike(String value) {
            addCriterion("pre_project_introduce like", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceNotLike(String value) {
            addCriterion("pre_project_introduce not like", value, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceIn(List<String> values) {
            addCriterion("pre_project_introduce in", values, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceNotIn(List<String> values) {
            addCriterion("pre_project_introduce not in", values, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceBetween(String value1, String value2) {
            addCriterion("pre_project_introduce between", value1, value2, "preProjectIntroduce");
            return (Criteria) this;
        }

        public Criteria andPreProjectIntroduceNotBetween(String value1, String value2) {
            addCriterion("pre_project_introduce not between", value1, value2, "preProjectIntroduce");
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

        public Criteria andSystemManagerIdIsNull() {
            addCriterion("system_manager_id is null");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdIsNotNull() {
            addCriterion("system_manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdEqualTo(String value) {
            addCriterion("system_manager_id =", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdNotEqualTo(String value) {
            addCriterion("system_manager_id <>", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdGreaterThan(String value) {
            addCriterion("system_manager_id >", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdGreaterThanOrEqualTo(String value) {
            addCriterion("system_manager_id >=", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdLessThan(String value) {
            addCriterion("system_manager_id <", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdLessThanOrEqualTo(String value) {
            addCriterion("system_manager_id <=", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdLike(String value) {
            addCriterion("system_manager_id like", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdNotLike(String value) {
            addCriterion("system_manager_id not like", value, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdIn(List<String> values) {
            addCriterion("system_manager_id in", values, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdNotIn(List<String> values) {
            addCriterion("system_manager_id not in", values, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdBetween(String value1, String value2) {
            addCriterion("system_manager_id between", value1, value2, "systemManagerId");
            return (Criteria) this;
        }

        public Criteria andSystemManagerIdNotBetween(String value1, String value2) {
            addCriterion("system_manager_id not between", value1, value2, "systemManagerId");
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