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

public class RdmsCharacterDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsCharacterDataExample() {
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

        public Criteria andFunctionDescriptionIsNull() {
            addCriterion("function_description is null");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionIsNotNull() {
            addCriterion("function_description is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionEqualTo(String value) {
            addCriterion("function_description =", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionNotEqualTo(String value) {
            addCriterion("function_description <>", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionGreaterThan(String value) {
            addCriterion("function_description >", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("function_description >=", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionLessThan(String value) {
            addCriterion("function_description <", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionLessThanOrEqualTo(String value) {
            addCriterion("function_description <=", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionLike(String value) {
            addCriterion("function_description like", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionNotLike(String value) {
            addCriterion("function_description not like", value, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionIn(List<String> values) {
            addCriterion("function_description in", values, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionNotIn(List<String> values) {
            addCriterion("function_description not in", values, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionBetween(String value1, String value2) {
            addCriterion("function_description between", value1, value2, "functionDescription");
            return (Criteria) this;
        }

        public Criteria andFunctionDescriptionNotBetween(String value1, String value2) {
            addCriterion("function_description not between", value1, value2, "functionDescription");
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

        public Criteria andInputLogicalDescIsNull() {
            addCriterion("input_logical_desc is null");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescIsNotNull() {
            addCriterion("input_logical_desc is not null");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescEqualTo(String value) {
            addCriterion("input_logical_desc =", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescNotEqualTo(String value) {
            addCriterion("input_logical_desc <>", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescGreaterThan(String value) {
            addCriterion("input_logical_desc >", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescGreaterThanOrEqualTo(String value) {
            addCriterion("input_logical_desc >=", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescLessThan(String value) {
            addCriterion("input_logical_desc <", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescLessThanOrEqualTo(String value) {
            addCriterion("input_logical_desc <=", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescLike(String value) {
            addCriterion("input_logical_desc like", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescNotLike(String value) {
            addCriterion("input_logical_desc not like", value, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescIn(List<String> values) {
            addCriterion("input_logical_desc in", values, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescNotIn(List<String> values) {
            addCriterion("input_logical_desc not in", values, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescBetween(String value1, String value2) {
            addCriterion("input_logical_desc between", value1, value2, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andInputLogicalDescNotBetween(String value1, String value2) {
            addCriterion("input_logical_desc not between", value1, value2, "inputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescIsNull() {
            addCriterion("function_logical_desc is null");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescIsNotNull() {
            addCriterion("function_logical_desc is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescEqualTo(String value) {
            addCriterion("function_logical_desc =", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescNotEqualTo(String value) {
            addCriterion("function_logical_desc <>", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescGreaterThan(String value) {
            addCriterion("function_logical_desc >", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescGreaterThanOrEqualTo(String value) {
            addCriterion("function_logical_desc >=", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescLessThan(String value) {
            addCriterion("function_logical_desc <", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescLessThanOrEqualTo(String value) {
            addCriterion("function_logical_desc <=", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescLike(String value) {
            addCriterion("function_logical_desc like", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescNotLike(String value) {
            addCriterion("function_logical_desc not like", value, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescIn(List<String> values) {
            addCriterion("function_logical_desc in", values, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescNotIn(List<String> values) {
            addCriterion("function_logical_desc not in", values, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescBetween(String value1, String value2) {
            addCriterion("function_logical_desc between", value1, value2, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andFunctionLogicalDescNotBetween(String value1, String value2) {
            addCriterion("function_logical_desc not between", value1, value2, "functionLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescIsNull() {
            addCriterion("output_logical_desc is null");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescIsNotNull() {
            addCriterion("output_logical_desc is not null");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescEqualTo(String value) {
            addCriterion("output_logical_desc =", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescNotEqualTo(String value) {
            addCriterion("output_logical_desc <>", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescGreaterThan(String value) {
            addCriterion("output_logical_desc >", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescGreaterThanOrEqualTo(String value) {
            addCriterion("output_logical_desc >=", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescLessThan(String value) {
            addCriterion("output_logical_desc <", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescLessThanOrEqualTo(String value) {
            addCriterion("output_logical_desc <=", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescLike(String value) {
            addCriterion("output_logical_desc like", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescNotLike(String value) {
            addCriterion("output_logical_desc not like", value, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescIn(List<String> values) {
            addCriterion("output_logical_desc in", values, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescNotIn(List<String> values) {
            addCriterion("output_logical_desc not in", values, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescBetween(String value1, String value2) {
            addCriterion("output_logical_desc between", value1, value2, "outputLogicalDesc");
            return (Criteria) this;
        }

        public Criteria andOutputLogicalDescNotBetween(String value1, String value2) {
            addCriterion("output_logical_desc not between", value1, value2, "outputLogicalDesc");
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