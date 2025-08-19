/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.ArrayList;
import java.util.List;

public class RdmsJobitemAssociateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsJobitemAssociateExample() {
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

        public Criteria andAssociateIdIsNull() {
            addCriterion("associate_id is null");
            return (Criteria) this;
        }

        public Criteria andAssociateIdIsNotNull() {
            addCriterion("associate_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssociateIdEqualTo(String value) {
            addCriterion("associate_id =", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdNotEqualTo(String value) {
            addCriterion("associate_id <>", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdGreaterThan(String value) {
            addCriterion("associate_id >", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdGreaterThanOrEqualTo(String value) {
            addCriterion("associate_id >=", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdLessThan(String value) {
            addCriterion("associate_id <", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdLessThanOrEqualTo(String value) {
            addCriterion("associate_id <=", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdLike(String value) {
            addCriterion("associate_id like", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdNotLike(String value) {
            addCriterion("associate_id not like", value, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdIn(List<String> values) {
            addCriterion("associate_id in", values, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdNotIn(List<String> values) {
            addCriterion("associate_id not in", values, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdBetween(String value1, String value2) {
            addCriterion("associate_id between", value1, value2, "associateId");
            return (Criteria) this;
        }

        public Criteria andAssociateIdNotBetween(String value1, String value2) {
            addCriterion("associate_id not between", value1, value2, "associateId");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrIsNull() {
            addCriterion("replace_file_Ids_str is null");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrIsNotNull() {
            addCriterion("replace_file_Ids_str is not null");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrEqualTo(String value) {
            addCriterion("replace_file_Ids_str =", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrNotEqualTo(String value) {
            addCriterion("replace_file_Ids_str <>", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrGreaterThan(String value) {
            addCriterion("replace_file_Ids_str >", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrGreaterThanOrEqualTo(String value) {
            addCriterion("replace_file_Ids_str >=", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrLessThan(String value) {
            addCriterion("replace_file_Ids_str <", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrLessThanOrEqualTo(String value) {
            addCriterion("replace_file_Ids_str <=", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrLike(String value) {
            addCriterion("replace_file_Ids_str like", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrNotLike(String value) {
            addCriterion("replace_file_Ids_str not like", value, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrIn(List<String> values) {
            addCriterion("replace_file_Ids_str in", values, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrNotIn(List<String> values) {
            addCriterion("replace_file_Ids_str not in", values, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrBetween(String value1, String value2) {
            addCriterion("replace_file_Ids_str between", value1, value2, "replaceFileIdsStr");
            return (Criteria) this;
        }

        public Criteria andReplaceFileIdsStrNotBetween(String value1, String value2) {
            addCriterion("replace_file_Ids_str not between", value1, value2, "replaceFileIdsStr");
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