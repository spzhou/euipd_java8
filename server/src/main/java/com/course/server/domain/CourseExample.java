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

public class CourseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CourseExample() {
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

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("`time` is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("`time` is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Integer value) {
            addCriterion("`time` =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Integer value) {
            addCriterion("`time` <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Integer value) {
            addCriterion("`time` >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`time` >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Integer value) {
            addCriterion("`time` <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Integer value) {
            addCriterion("`time` <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Integer> values) {
            addCriterion("`time` in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Integer> values) {
            addCriterion("`time` not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Integer value1, Integer value2) {
            addCriterion("`time` between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("`time` not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andVideoIsNull() {
            addCriterion("video is null");
            return (Criteria) this;
        }

        public Criteria andVideoIsNotNull() {
            addCriterion("video is not null");
            return (Criteria) this;
        }

        public Criteria andVideoEqualTo(String value) {
            addCriterion("video =", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotEqualTo(String value) {
            addCriterion("video <>", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThan(String value) {
            addCriterion("video >", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThanOrEqualTo(String value) {
            addCriterion("video >=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThan(String value) {
            addCriterion("video <", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThanOrEqualTo(String value) {
            addCriterion("video <=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLike(String value) {
            addCriterion("video like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotLike(String value) {
            addCriterion("video not like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoIn(List<String> values) {
            addCriterion("video in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotIn(List<String> values) {
            addCriterion("video not in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoBetween(String value1, String value2) {
            addCriterion("video between", value1, value2, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotBetween(String value1, String value2) {
            addCriterion("video not between", value1, value2, "video");
            return (Criteria) this;
        }

        public Criteria andVodIsNull() {
            addCriterion("vod is null");
            return (Criteria) this;
        }

        public Criteria andVodIsNotNull() {
            addCriterion("vod is not null");
            return (Criteria) this;
        }

        public Criteria andVodEqualTo(String value) {
            addCriterion("vod =", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodNotEqualTo(String value) {
            addCriterion("vod <>", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodGreaterThan(String value) {
            addCriterion("vod >", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodGreaterThanOrEqualTo(String value) {
            addCriterion("vod >=", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodLessThan(String value) {
            addCriterion("vod <", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodLessThanOrEqualTo(String value) {
            addCriterion("vod <=", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodLike(String value) {
            addCriterion("vod like", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodNotLike(String value) {
            addCriterion("vod not like", value, "vod");
            return (Criteria) this;
        }

        public Criteria andVodIn(List<String> values) {
            addCriterion("vod in", values, "vod");
            return (Criteria) this;
        }

        public Criteria andVodNotIn(List<String> values) {
            addCriterion("vod not in", values, "vod");
            return (Criteria) this;
        }

        public Criteria andVodBetween(String value1, String value2) {
            addCriterion("vod between", value1, value2, "vod");
            return (Criteria) this;
        }

        public Criteria andVodNotBetween(String value1, String value2) {
            addCriterion("vod not between", value1, value2, "vod");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("`level` is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("`level` is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(String value) {
            addCriterion("`level` =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(String value) {
            addCriterion("`level` <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(String value) {
            addCriterion("`level` >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(String value) {
            addCriterion("`level` >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(String value) {
            addCriterion("`level` <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(String value) {
            addCriterion("`level` <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLike(String value) {
            addCriterion("`level` like", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotLike(String value) {
            addCriterion("`level` not like", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<String> values) {
            addCriterion("`level` in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<String> values) {
            addCriterion("`level` not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(String value1, String value2) {
            addCriterion("`level` between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(String value1, String value2) {
            addCriterion("`level` not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andChargeIsNull() {
            addCriterion("charge is null");
            return (Criteria) this;
        }

        public Criteria andChargeIsNotNull() {
            addCriterion("charge is not null");
            return (Criteria) this;
        }

        public Criteria andChargeEqualTo(String value) {
            addCriterion("charge =", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotEqualTo(String value) {
            addCriterion("charge <>", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThan(String value) {
            addCriterion("charge >", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThanOrEqualTo(String value) {
            addCriterion("charge >=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThan(String value) {
            addCriterion("charge <", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThanOrEqualTo(String value) {
            addCriterion("charge <=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLike(String value) {
            addCriterion("charge like", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotLike(String value) {
            addCriterion("charge not like", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeIn(List<String> values) {
            addCriterion("charge in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotIn(List<String> values) {
            addCriterion("charge not in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeBetween(String value1, String value2) {
            addCriterion("charge between", value1, value2, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotBetween(String value1, String value2) {
            addCriterion("charge not between", value1, value2, "charge");
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

        public Criteria andEnrollIsNull() {
            addCriterion("enroll is null");
            return (Criteria) this;
        }

        public Criteria andEnrollIsNotNull() {
            addCriterion("enroll is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollEqualTo(Integer value) {
            addCriterion("enroll =", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollNotEqualTo(Integer value) {
            addCriterion("enroll <>", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollGreaterThan(Integer value) {
            addCriterion("enroll >", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollGreaterThanOrEqualTo(Integer value) {
            addCriterion("enroll >=", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollLessThan(Integer value) {
            addCriterion("enroll <", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollLessThanOrEqualTo(Integer value) {
            addCriterion("enroll <=", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollIn(List<Integer> values) {
            addCriterion("enroll in", values, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollNotIn(List<Integer> values) {
            addCriterion("enroll not in", values, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollBetween(Integer value1, Integer value2) {
            addCriterion("enroll between", value1, value2, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollNotBetween(Integer value1, Integer value2) {
            addCriterion("enroll not between", value1, value2, "enroll");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("created_at =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterion("updated_at =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("updated_at <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("updated_at >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_at >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("updated_at <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("updated_at <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("updated_at in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("updated_at not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("updated_at between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("updated_at not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIsNull() {
            addCriterion("teacher_id is null");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIsNotNull() {
            addCriterion("teacher_id is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherIdEqualTo(String value) {
            addCriterion("teacher_id =", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotEqualTo(String value) {
            addCriterion("teacher_id <>", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdGreaterThan(String value) {
            addCriterion("teacher_id >", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdGreaterThanOrEqualTo(String value) {
            addCriterion("teacher_id >=", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLessThan(String value) {
            addCriterion("teacher_id <", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLessThanOrEqualTo(String value) {
            addCriterion("teacher_id <=", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLike(String value) {
            addCriterion("teacher_id like", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotLike(String value) {
            addCriterion("teacher_id not like", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIn(List<String> values) {
            addCriterion("teacher_id in", values, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotIn(List<String> values) {
            addCriterion("teacher_id not in", values, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdBetween(String value1, String value2) {
            addCriterion("teacher_id between", value1, value2, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotBetween(String value1, String value2) {
            addCriterion("teacher_id not between", value1, value2, "teacherId");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameIsNull() {
            addCriterion("creator_loginname is null");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameIsNotNull() {
            addCriterion("creator_loginname is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameEqualTo(String value) {
            addCriterion("creator_loginname =", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameNotEqualTo(String value) {
            addCriterion("creator_loginname <>", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameGreaterThan(String value) {
            addCriterion("creator_loginname >", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameGreaterThanOrEqualTo(String value) {
            addCriterion("creator_loginname >=", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameLessThan(String value) {
            addCriterion("creator_loginname <", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameLessThanOrEqualTo(String value) {
            addCriterion("creator_loginname <=", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameLike(String value) {
            addCriterion("creator_loginname like", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameNotLike(String value) {
            addCriterion("creator_loginname not like", value, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameIn(List<String> values) {
            addCriterion("creator_loginname in", values, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameNotIn(List<String> values) {
            addCriterion("creator_loginname not in", values, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameBetween(String value1, String value2) {
            addCriterion("creator_loginname between", value1, value2, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andCreatorLoginnameNotBetween(String value1, String value2) {
            addCriterion("creator_loginname not between", value1, value2, "creatorLoginname");
            return (Criteria) this;
        }

        public Criteria andLikeIsNull() {
            addCriterion("`like` is null");
            return (Criteria) this;
        }

        public Criteria andLikeIsNotNull() {
            addCriterion("`like` is not null");
            return (Criteria) this;
        }

        public Criteria andLikeEqualTo(Integer value) {
            addCriterion("`like` =", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeNotEqualTo(Integer value) {
            addCriterion("`like` <>", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeGreaterThan(Integer value) {
            addCriterion("`like` >", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`like` >=", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeLessThan(Integer value) {
            addCriterion("`like` <", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeLessThanOrEqualTo(Integer value) {
            addCriterion("`like` <=", value, "like");
            return (Criteria) this;
        }

        public Criteria andLikeIn(List<Integer> values) {
            addCriterion("`like` in", values, "like");
            return (Criteria) this;
        }

        public Criteria andLikeNotIn(List<Integer> values) {
            addCriterion("`like` not in", values, "like");
            return (Criteria) this;
        }

        public Criteria andLikeBetween(Integer value1, Integer value2) {
            addCriterion("`like` between", value1, value2, "like");
            return (Criteria) this;
        }

        public Criteria andLikeNotBetween(Integer value1, Integer value2) {
            addCriterion("`like` not between", value1, value2, "like");
            return (Criteria) this;
        }

        public Criteria andCollectIsNull() {
            addCriterion("`collect` is null");
            return (Criteria) this;
        }

        public Criteria andCollectIsNotNull() {
            addCriterion("`collect` is not null");
            return (Criteria) this;
        }

        public Criteria andCollectEqualTo(Integer value) {
            addCriterion("`collect` =", value, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectNotEqualTo(Integer value) {
            addCriterion("`collect` <>", value, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectGreaterThan(Integer value) {
            addCriterion("`collect` >", value, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectGreaterThanOrEqualTo(Integer value) {
            addCriterion("`collect` >=", value, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectLessThan(Integer value) {
            addCriterion("`collect` <", value, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectLessThanOrEqualTo(Integer value) {
            addCriterion("`collect` <=", value, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectIn(List<Integer> values) {
            addCriterion("`collect` in", values, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectNotIn(List<Integer> values) {
            addCriterion("`collect` not in", values, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectBetween(Integer value1, Integer value2) {
            addCriterion("`collect` between", value1, value2, "collect");
            return (Criteria) this;
        }

        public Criteria andCollectNotBetween(Integer value1, Integer value2) {
            addCriterion("`collect` not between", value1, value2, "collect");
            return (Criteria) this;
        }

        public Criteria andWatchIsNull() {
            addCriterion("watch is null");
            return (Criteria) this;
        }

        public Criteria andWatchIsNotNull() {
            addCriterion("watch is not null");
            return (Criteria) this;
        }

        public Criteria andWatchEqualTo(Integer value) {
            addCriterion("watch =", value, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchNotEqualTo(Integer value) {
            addCriterion("watch <>", value, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchGreaterThan(Integer value) {
            addCriterion("watch >", value, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchGreaterThanOrEqualTo(Integer value) {
            addCriterion("watch >=", value, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchLessThan(Integer value) {
            addCriterion("watch <", value, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchLessThanOrEqualTo(Integer value) {
            addCriterion("watch <=", value, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchIn(List<Integer> values) {
            addCriterion("watch in", values, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchNotIn(List<Integer> values) {
            addCriterion("watch not in", values, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchBetween(Integer value1, Integer value2) {
            addCriterion("watch between", value1, value2, "watch");
            return (Criteria) this;
        }

        public Criteria andWatchNotBetween(Integer value1, Integer value2) {
            addCriterion("watch not between", value1, value2, "watch");
            return (Criteria) this;
        }

        public Criteria andPowerIsNull() {
            addCriterion("`power` is null");
            return (Criteria) this;
        }

        public Criteria andPowerIsNotNull() {
            addCriterion("`power` is not null");
            return (Criteria) this;
        }

        public Criteria andPowerEqualTo(Integer value) {
            addCriterion("`power` =", value, "power");
            return (Criteria) this;
        }

        public Criteria andPowerNotEqualTo(Integer value) {
            addCriterion("`power` <>", value, "power");
            return (Criteria) this;
        }

        public Criteria andPowerGreaterThan(Integer value) {
            addCriterion("`power` >", value, "power");
            return (Criteria) this;
        }

        public Criteria andPowerGreaterThanOrEqualTo(Integer value) {
            addCriterion("`power` >=", value, "power");
            return (Criteria) this;
        }

        public Criteria andPowerLessThan(Integer value) {
            addCriterion("`power` <", value, "power");
            return (Criteria) this;
        }

        public Criteria andPowerLessThanOrEqualTo(Integer value) {
            addCriterion("`power` <=", value, "power");
            return (Criteria) this;
        }

        public Criteria andPowerIn(List<Integer> values) {
            addCriterion("`power` in", values, "power");
            return (Criteria) this;
        }

        public Criteria andPowerNotIn(List<Integer> values) {
            addCriterion("`power` not in", values, "power");
            return (Criteria) this;
        }

        public Criteria andPowerBetween(Integer value1, Integer value2) {
            addCriterion("`power` between", value1, value2, "power");
            return (Criteria) this;
        }

        public Criteria andPowerNotBetween(Integer value1, Integer value2) {
            addCriterion("`power` not between", value1, value2, "power");
            return (Criteria) this;
        }

        public Criteria andNewCourseIsNull() {
            addCriterion("new_course is null");
            return (Criteria) this;
        }

        public Criteria andNewCourseIsNotNull() {
            addCriterion("new_course is not null");
            return (Criteria) this;
        }

        public Criteria andNewCourseEqualTo(Integer value) {
            addCriterion("new_course =", value, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseNotEqualTo(Integer value) {
            addCriterion("new_course <>", value, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseGreaterThan(Integer value) {
            addCriterion("new_course >", value, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_course >=", value, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseLessThan(Integer value) {
            addCriterion("new_course <", value, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseLessThanOrEqualTo(Integer value) {
            addCriterion("new_course <=", value, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseIn(List<Integer> values) {
            addCriterion("new_course in", values, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseNotIn(List<Integer> values) {
            addCriterion("new_course not in", values, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseBetween(Integer value1, Integer value2) {
            addCriterion("new_course between", value1, value2, "newCourse");
            return (Criteria) this;
        }

        public Criteria andNewCourseNotBetween(Integer value1, Integer value2) {
            addCriterion("new_course not between", value1, value2, "newCourse");
            return (Criteria) this;
        }

        public Criteria andPromotionIsNull() {
            addCriterion("promotion is null");
            return (Criteria) this;
        }

        public Criteria andPromotionIsNotNull() {
            addCriterion("promotion is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionEqualTo(Integer value) {
            addCriterion("promotion =", value, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionNotEqualTo(Integer value) {
            addCriterion("promotion <>", value, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionGreaterThan(Integer value) {
            addCriterion("promotion >", value, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionGreaterThanOrEqualTo(Integer value) {
            addCriterion("promotion >=", value, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionLessThan(Integer value) {
            addCriterion("promotion <", value, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionLessThanOrEqualTo(Integer value) {
            addCriterion("promotion <=", value, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionIn(List<Integer> values) {
            addCriterion("promotion in", values, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionNotIn(List<Integer> values) {
            addCriterion("promotion not in", values, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionBetween(Integer value1, Integer value2) {
            addCriterion("promotion between", value1, value2, "promotion");
            return (Criteria) this;
        }

        public Criteria andPromotionNotBetween(Integer value1, Integer value2) {
            addCriterion("promotion not between", value1, value2, "promotion");
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