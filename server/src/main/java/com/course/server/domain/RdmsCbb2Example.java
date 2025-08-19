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

public class RdmsCbb2Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsCbb2Example() {
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

        public Criteria andCbbSerialIsNull() {
            addCriterion("cbb_serial is null");
            return (Criteria) this;
        }

        public Criteria andCbbSerialIsNotNull() {
            addCriterion("cbb_serial is not null");
            return (Criteria) this;
        }

        public Criteria andCbbSerialEqualTo(String value) {
            addCriterion("cbb_serial =", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialNotEqualTo(String value) {
            addCriterion("cbb_serial <>", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialGreaterThan(String value) {
            addCriterion("cbb_serial >", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialGreaterThanOrEqualTo(String value) {
            addCriterion("cbb_serial >=", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialLessThan(String value) {
            addCriterion("cbb_serial <", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialLessThanOrEqualTo(String value) {
            addCriterion("cbb_serial <=", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialLike(String value) {
            addCriterion("cbb_serial like", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialNotLike(String value) {
            addCriterion("cbb_serial not like", value, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialIn(List<String> values) {
            addCriterion("cbb_serial in", values, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialNotIn(List<String> values) {
            addCriterion("cbb_serial not in", values, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialBetween(String value1, String value2) {
            addCriterion("cbb_serial between", value1, value2, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCbbSerialNotBetween(String value1, String value2) {
            addCriterion("cbb_serial not between", value1, value2, "cbbSerial");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIsNull() {
            addCriterion("character_Id is null");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIsNotNull() {
            addCriterion("character_Id is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterIdEqualTo(String value) {
            addCriterion("character_Id =", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotEqualTo(String value) {
            addCriterion("character_Id <>", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdGreaterThan(String value) {
            addCriterion("character_Id >", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdGreaterThanOrEqualTo(String value) {
            addCriterion("character_Id >=", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLessThan(String value) {
            addCriterion("character_Id <", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLessThanOrEqualTo(String value) {
            addCriterion("character_Id <=", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLike(String value) {
            addCriterion("character_Id like", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotLike(String value) {
            addCriterion("character_Id not like", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIn(List<String> values) {
            addCriterion("character_Id in", values, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotIn(List<String> values) {
            addCriterion("character_Id not in", values, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdBetween(String value1, String value2) {
            addCriterion("character_Id between", value1, value2, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotBetween(String value1, String value2) {
            addCriterion("character_Id not between", value1, value2, "characterId");
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

        public Criteria andCbbNameIsNull() {
            addCriterion("cbb_name is null");
            return (Criteria) this;
        }

        public Criteria andCbbNameIsNotNull() {
            addCriterion("cbb_name is not null");
            return (Criteria) this;
        }

        public Criteria andCbbNameEqualTo(String value) {
            addCriterion("cbb_name =", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameNotEqualTo(String value) {
            addCriterion("cbb_name <>", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameGreaterThan(String value) {
            addCriterion("cbb_name >", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameGreaterThanOrEqualTo(String value) {
            addCriterion("cbb_name >=", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameLessThan(String value) {
            addCriterion("cbb_name <", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameLessThanOrEqualTo(String value) {
            addCriterion("cbb_name <=", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameLike(String value) {
            addCriterion("cbb_name like", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameNotLike(String value) {
            addCriterion("cbb_name not like", value, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameIn(List<String> values) {
            addCriterion("cbb_name in", values, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameNotIn(List<String> values) {
            addCriterion("cbb_name not in", values, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameBetween(String value1, String value2) {
            addCriterion("cbb_name between", value1, value2, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbNameNotBetween(String value1, String value2) {
            addCriterion("cbb_name not between", value1, value2, "cbbName");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceIsNull() {
            addCriterion("cbb_introduce is null");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceIsNotNull() {
            addCriterion("cbb_introduce is not null");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceEqualTo(String value) {
            addCriterion("cbb_introduce =", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceNotEqualTo(String value) {
            addCriterion("cbb_introduce <>", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceGreaterThan(String value) {
            addCriterion("cbb_introduce >", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceGreaterThanOrEqualTo(String value) {
            addCriterion("cbb_introduce >=", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceLessThan(String value) {
            addCriterion("cbb_introduce <", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceLessThanOrEqualTo(String value) {
            addCriterion("cbb_introduce <=", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceLike(String value) {
            addCriterion("cbb_introduce like", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceNotLike(String value) {
            addCriterion("cbb_introduce not like", value, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceIn(List<String> values) {
            addCriterion("cbb_introduce in", values, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceNotIn(List<String> values) {
            addCriterion("cbb_introduce not in", values, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceBetween(String value1, String value2) {
            addCriterion("cbb_introduce between", value1, value2, "cbbIntroduce");
            return (Criteria) this;
        }

        public Criteria andCbbIntroduceNotBetween(String value1, String value2) {
            addCriterion("cbb_introduce not between", value1, value2, "cbbIntroduce");
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

        public Criteria andFileIdListStrIsNull() {
            addCriterion("file_id_list_str is null");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrIsNotNull() {
            addCriterion("file_id_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrEqualTo(String value) {
            addCriterion("file_id_list_str =", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrNotEqualTo(String value) {
            addCriterion("file_id_list_str <>", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrGreaterThan(String value) {
            addCriterion("file_id_list_str >", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrGreaterThanOrEqualTo(String value) {
            addCriterion("file_id_list_str >=", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrLessThan(String value) {
            addCriterion("file_id_list_str <", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrLessThanOrEqualTo(String value) {
            addCriterion("file_id_list_str <=", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrLike(String value) {
            addCriterion("file_id_list_str like", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrNotLike(String value) {
            addCriterion("file_id_list_str not like", value, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrIn(List<String> values) {
            addCriterion("file_id_list_str in", values, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrNotIn(List<String> values) {
            addCriterion("file_id_list_str not in", values, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrBetween(String value1, String value2) {
            addCriterion("file_id_list_str between", value1, value2, "fileIdListStr");
            return (Criteria) this;
        }

        public Criteria andFileIdListStrNotBetween(String value1, String value2) {
            addCriterion("file_id_list_str not between", value1, value2, "fileIdListStr");
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

        public Criteria andDevVersionIsNull() {
            addCriterion("dev_version is null");
            return (Criteria) this;
        }

        public Criteria andDevVersionIsNotNull() {
            addCriterion("dev_version is not null");
            return (Criteria) this;
        }

        public Criteria andDevVersionEqualTo(String value) {
            addCriterion("dev_version =", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionNotEqualTo(String value) {
            addCriterion("dev_version <>", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionGreaterThan(String value) {
            addCriterion("dev_version >", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionGreaterThanOrEqualTo(String value) {
            addCriterion("dev_version >=", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionLessThan(String value) {
            addCriterion("dev_version <", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionLessThanOrEqualTo(String value) {
            addCriterion("dev_version <=", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionLike(String value) {
            addCriterion("dev_version like", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionNotLike(String value) {
            addCriterion("dev_version not like", value, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionIn(List<String> values) {
            addCriterion("dev_version in", values, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionNotIn(List<String> values) {
            addCriterion("dev_version not in", values, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionBetween(String value1, String value2) {
            addCriterion("dev_version between", value1, value2, "devVersion");
            return (Criteria) this;
        }

        public Criteria andDevVersionNotBetween(String value1, String value2) {
            addCriterion("dev_version not between", value1, value2, "devVersion");
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

        public Criteria andRelatedIdIsNull() {
            addCriterion("related_id is null");
            return (Criteria) this;
        }

        public Criteria andRelatedIdIsNotNull() {
            addCriterion("related_id is not null");
            return (Criteria) this;
        }

        public Criteria andRelatedIdEqualTo(String value) {
            addCriterion("related_id =", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdNotEqualTo(String value) {
            addCriterion("related_id <>", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdGreaterThan(String value) {
            addCriterion("related_id >", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdGreaterThanOrEqualTo(String value) {
            addCriterion("related_id >=", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdLessThan(String value) {
            addCriterion("related_id <", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdLessThanOrEqualTo(String value) {
            addCriterion("related_id <=", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdLike(String value) {
            addCriterion("related_id like", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdNotLike(String value) {
            addCriterion("related_id not like", value, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdIn(List<String> values) {
            addCriterion("related_id in", values, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdNotIn(List<String> values) {
            addCriterion("related_id not in", values, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdBetween(String value1, String value2) {
            addCriterion("related_id between", value1, value2, "relatedId");
            return (Criteria) this;
        }

        public Criteria andRelatedIdNotBetween(String value1, String value2) {
            addCriterion("related_id not between", value1, value2, "relatedId");
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