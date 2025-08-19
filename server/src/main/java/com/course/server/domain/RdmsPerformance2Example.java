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

public class RdmsPerformance2Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsPerformance2Example() {
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

        public Criteria andCustomerUserIdIsNull() {
            addCriterion("customer_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdIsNotNull() {
            addCriterion("customer_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdEqualTo(String value) {
            addCriterion("customer_user_id =", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdNotEqualTo(String value) {
            addCriterion("customer_user_id <>", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdGreaterThan(String value) {
            addCriterion("customer_user_id >", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_user_id >=", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdLessThan(String value) {
            addCriterion("customer_user_id <", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdLessThanOrEqualTo(String value) {
            addCriterion("customer_user_id <=", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdLike(String value) {
            addCriterion("customer_user_id like", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdNotLike(String value) {
            addCriterion("customer_user_id not like", value, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdIn(List<String> values) {
            addCriterion("customer_user_id in", values, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdNotIn(List<String> values) {
            addCriterion("customer_user_id not in", values, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdBetween(String value1, String value2) {
            addCriterion("customer_user_id between", value1, value2, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andCustomerUserIdNotBetween(String value1, String value2) {
            addCriterion("customer_user_id not between", value1, value2, "customerUserId");
            return (Criteria) this;
        }

        public Criteria andYearMonthIsNull() {
            addCriterion("`year_month` is null");
            return (Criteria) this;
        }

        public Criteria andYearMonthIsNotNull() {
            addCriterion("`year_month` is not null");
            return (Criteria) this;
        }

        public Criteria andYearMonthEqualTo(String value) {
            addCriterion("`year_month` =", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthNotEqualTo(String value) {
            addCriterion("`year_month` <>", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthGreaterThan(String value) {
            addCriterion("`year_month` >", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthGreaterThanOrEqualTo(String value) {
            addCriterion("`year_month` >=", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthLessThan(String value) {
            addCriterion("`year_month` <", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthLessThanOrEqualTo(String value) {
            addCriterion("`year_month` <=", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthLike(String value) {
            addCriterion("`year_month` like", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthNotLike(String value) {
            addCriterion("`year_month` not like", value, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthIn(List<String> values) {
            addCriterion("`year_month` in", values, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthNotIn(List<String> values) {
            addCriterion("`year_month` not in", values, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthBetween(String value1, String value2) {
            addCriterion("`year_month` between", value1, value2, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andYearMonthNotBetween(String value1, String value2) {
            addCriterion("`year_month` not between", value1, value2, "yearMonth");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursIsNull() {
            addCriterion("submit_hours is null");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursIsNotNull() {
            addCriterion("submit_hours is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursEqualTo(Double value) {
            addCriterion("submit_hours =", value, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursNotEqualTo(Double value) {
            addCriterion("submit_hours <>", value, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursGreaterThan(Double value) {
            addCriterion("submit_hours >", value, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursGreaterThanOrEqualTo(Double value) {
            addCriterion("submit_hours >=", value, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursLessThan(Double value) {
            addCriterion("submit_hours <", value, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursLessThanOrEqualTo(Double value) {
            addCriterion("submit_hours <=", value, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursIn(List<Double> values) {
            addCriterion("submit_hours in", values, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursNotIn(List<Double> values) {
            addCriterion("submit_hours not in", values, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursBetween(Double value1, Double value2) {
            addCriterion("submit_hours between", value1, value2, "submitHours");
            return (Criteria) this;
        }

        public Criteria andSubmitHoursNotBetween(Double value1, Double value2) {
            addCriterion("submit_hours not between", value1, value2, "submitHours");
            return (Criteria) this;
        }

        public Criteria andDelayRateIsNull() {
            addCriterion("delay_rate is null");
            return (Criteria) this;
        }

        public Criteria andDelayRateIsNotNull() {
            addCriterion("delay_rate is not null");
            return (Criteria) this;
        }

        public Criteria andDelayRateEqualTo(BigDecimal value) {
            addCriterion("delay_rate =", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateNotEqualTo(BigDecimal value) {
            addCriterion("delay_rate <>", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateGreaterThan(BigDecimal value) {
            addCriterion("delay_rate >", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_rate >=", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateLessThan(BigDecimal value) {
            addCriterion("delay_rate <", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_rate <=", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateIn(List<BigDecimal> values) {
            addCriterion("delay_rate in", values, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateNotIn(List<BigDecimal> values) {
            addCriterion("delay_rate not in", values, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_rate between", value1, value2, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_rate not between", value1, value2, "delayRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateIsNull() {
            addCriterion("rebate_rate is null");
            return (Criteria) this;
        }

        public Criteria andRebateRateIsNotNull() {
            addCriterion("rebate_rate is not null");
            return (Criteria) this;
        }

        public Criteria andRebateRateEqualTo(BigDecimal value) {
            addCriterion("rebate_rate =", value, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateNotEqualTo(BigDecimal value) {
            addCriterion("rebate_rate <>", value, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateGreaterThan(BigDecimal value) {
            addCriterion("rebate_rate >", value, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rebate_rate >=", value, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateLessThan(BigDecimal value) {
            addCriterion("rebate_rate <", value, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rebate_rate <=", value, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateIn(List<BigDecimal> values) {
            addCriterion("rebate_rate in", values, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateNotIn(List<BigDecimal> values) {
            addCriterion("rebate_rate not in", values, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rebate_rate between", value1, value2, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andRebateRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rebate_rate not between", value1, value2, "rebateRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateIsNull() {
            addCriterion("load_rate is null");
            return (Criteria) this;
        }

        public Criteria andLoadRateIsNotNull() {
            addCriterion("load_rate is not null");
            return (Criteria) this;
        }

        public Criteria andLoadRateEqualTo(BigDecimal value) {
            addCriterion("load_rate =", value, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateNotEqualTo(BigDecimal value) {
            addCriterion("load_rate <>", value, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateGreaterThan(BigDecimal value) {
            addCriterion("load_rate >", value, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("load_rate >=", value, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateLessThan(BigDecimal value) {
            addCriterion("load_rate <", value, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("load_rate <=", value, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateIn(List<BigDecimal> values) {
            addCriterion("load_rate in", values, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateNotIn(List<BigDecimal> values) {
            addCriterion("load_rate not in", values, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("load_rate between", value1, value2, "loadRate");
            return (Criteria) this;
        }

        public Criteria andLoadRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("load_rate not between", value1, value2, "loadRate");
            return (Criteria) this;
        }

        public Criteria andPerformanceIsNull() {
            addCriterion("performance is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceIsNotNull() {
            addCriterion("performance is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceEqualTo(BigDecimal value) {
            addCriterion("performance =", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceNotEqualTo(BigDecimal value) {
            addCriterion("performance <>", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceGreaterThan(BigDecimal value) {
            addCriterion("performance >", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("performance >=", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceLessThan(BigDecimal value) {
            addCriterion("performance <", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("performance <=", value, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceIn(List<BigDecimal> values) {
            addCriterion("performance in", values, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceNotIn(List<BigDecimal> values) {
            addCriterion("performance not in", values, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance between", value1, value2, "performance");
            return (Criteria) this;
        }

        public Criteria andPerformanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("performance not between", value1, value2, "performance");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Double value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Double value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Double value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Double value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Double value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Double value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Double> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Double> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Double value1, Double value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Double value1, Double value2) {
            addCriterion("score not between", value1, value2, "score");
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