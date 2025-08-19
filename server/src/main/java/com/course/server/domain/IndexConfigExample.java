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

public class IndexConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IndexConfigExample() {
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

        public Criteria andInstitutionIdIsNull() {
            addCriterion("institution_id is null");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdIsNotNull() {
            addCriterion("institution_id is not null");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdEqualTo(String value) {
            addCriterion("institution_id =", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdNotEqualTo(String value) {
            addCriterion("institution_id <>", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdGreaterThan(String value) {
            addCriterion("institution_id >", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdGreaterThanOrEqualTo(String value) {
            addCriterion("institution_id >=", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdLessThan(String value) {
            addCriterion("institution_id <", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdLessThanOrEqualTo(String value) {
            addCriterion("institution_id <=", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdLike(String value) {
            addCriterion("institution_id like", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdNotLike(String value) {
            addCriterion("institution_id not like", value, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdIn(List<String> values) {
            addCriterion("institution_id in", values, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdNotIn(List<String> values) {
            addCriterion("institution_id not in", values, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdBetween(String value1, String value2) {
            addCriterion("institution_id between", value1, value2, "institutionId");
            return (Criteria) this;
        }

        public Criteria andInstitutionIdNotBetween(String value1, String value2) {
            addCriterion("institution_id not between", value1, value2, "institutionId");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgIsNull() {
            addCriterion("main_player_warmup_img is null");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgIsNotNull() {
            addCriterion("main_player_warmup_img is not null");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgEqualTo(String value) {
            addCriterion("main_player_warmup_img =", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgNotEqualTo(String value) {
            addCriterion("main_player_warmup_img <>", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgGreaterThan(String value) {
            addCriterion("main_player_warmup_img >", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgGreaterThanOrEqualTo(String value) {
            addCriterion("main_player_warmup_img >=", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgLessThan(String value) {
            addCriterion("main_player_warmup_img <", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgLessThanOrEqualTo(String value) {
            addCriterion("main_player_warmup_img <=", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgLike(String value) {
            addCriterion("main_player_warmup_img like", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgNotLike(String value) {
            addCriterion("main_player_warmup_img not like", value, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgIn(List<String> values) {
            addCriterion("main_player_warmup_img in", values, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgNotIn(List<String> values) {
            addCriterion("main_player_warmup_img not in", values, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgBetween(String value1, String value2) {
            addCriterion("main_player_warmup_img between", value1, value2, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainPlayerWarmupImgNotBetween(String value1, String value2) {
            addCriterion("main_player_warmup_img not between", value1, value2, "mainPlayerWarmupImg");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdIsNull() {
            addCriterion("main_live_channel_id is null");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdIsNotNull() {
            addCriterion("main_live_channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdEqualTo(String value) {
            addCriterion("main_live_channel_id =", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdNotEqualTo(String value) {
            addCriterion("main_live_channel_id <>", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdGreaterThan(String value) {
            addCriterion("main_live_channel_id >", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdGreaterThanOrEqualTo(String value) {
            addCriterion("main_live_channel_id >=", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdLessThan(String value) {
            addCriterion("main_live_channel_id <", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdLessThanOrEqualTo(String value) {
            addCriterion("main_live_channel_id <=", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdLike(String value) {
            addCriterion("main_live_channel_id like", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdNotLike(String value) {
            addCriterion("main_live_channel_id not like", value, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdIn(List<String> values) {
            addCriterion("main_live_channel_id in", values, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdNotIn(List<String> values) {
            addCriterion("main_live_channel_id not in", values, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdBetween(String value1, String value2) {
            addCriterion("main_live_channel_id between", value1, value2, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andMainLiveChannelIdNotBetween(String value1, String value2) {
            addCriterion("main_live_channel_id not between", value1, value2, "mainLiveChannelId");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlIsNull() {
            addCriterion("cover_img_redirect_url is null");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlIsNotNull() {
            addCriterion("cover_img_redirect_url is not null");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlEqualTo(String value) {
            addCriterion("cover_img_redirect_url =", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlNotEqualTo(String value) {
            addCriterion("cover_img_redirect_url <>", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlGreaterThan(String value) {
            addCriterion("cover_img_redirect_url >", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlGreaterThanOrEqualTo(String value) {
            addCriterion("cover_img_redirect_url >=", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlLessThan(String value) {
            addCriterion("cover_img_redirect_url <", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlLessThanOrEqualTo(String value) {
            addCriterion("cover_img_redirect_url <=", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlLike(String value) {
            addCriterion("cover_img_redirect_url like", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlNotLike(String value) {
            addCriterion("cover_img_redirect_url not like", value, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlIn(List<String> values) {
            addCriterion("cover_img_redirect_url in", values, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlNotIn(List<String> values) {
            addCriterion("cover_img_redirect_url not in", values, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlBetween(String value1, String value2) {
            addCriterion("cover_img_redirect_url between", value1, value2, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andCoverImgRedirectUrlNotBetween(String value1, String value2) {
            addCriterion("cover_img_redirect_url not between", value1, value2, "coverImgRedirectUrl");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeIsNull() {
            addCriterion("warmup_time is null");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeIsNotNull() {
            addCriterion("warmup_time is not null");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeEqualTo(Date value) {
            addCriterion("warmup_time =", value, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeNotEqualTo(Date value) {
            addCriterion("warmup_time <>", value, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeGreaterThan(Date value) {
            addCriterion("warmup_time >", value, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("warmup_time >=", value, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeLessThan(Date value) {
            addCriterion("warmup_time <", value, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeLessThanOrEqualTo(Date value) {
            addCriterion("warmup_time <=", value, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeIn(List<Date> values) {
            addCriterion("warmup_time in", values, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeNotIn(List<Date> values) {
            addCriterion("warmup_time not in", values, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeBetween(Date value1, Date value2) {
            addCriterion("warmup_time between", value1, value2, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andWarmupTimeNotBetween(Date value1, Date value2) {
            addCriterion("warmup_time not between", value1, value2, "warmupTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeIsNull() {
            addCriterion("show_time is null");
            return (Criteria) this;
        }

        public Criteria andShowTimeIsNotNull() {
            addCriterion("show_time is not null");
            return (Criteria) this;
        }

        public Criteria andShowTimeEqualTo(Date value) {
            addCriterion("show_time =", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeNotEqualTo(Date value) {
            addCriterion("show_time <>", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeGreaterThan(Date value) {
            addCriterion("show_time >", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("show_time >=", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeLessThan(Date value) {
            addCriterion("show_time <", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeLessThanOrEqualTo(Date value) {
            addCriterion("show_time <=", value, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeIn(List<Date> values) {
            addCriterion("show_time in", values, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeNotIn(List<Date> values) {
            addCriterion("show_time not in", values, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeBetween(Date value1, Date value2) {
            addCriterion("show_time between", value1, value2, "showTime");
            return (Criteria) this;
        }

        public Criteria andShowTimeNotBetween(Date value1, Date value2) {
            addCriterion("show_time not between", value1, value2, "showTime");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdIsNull() {
            addCriterion("up_institution_id is null");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdIsNotNull() {
            addCriterion("up_institution_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdEqualTo(String value) {
            addCriterion("up_institution_id =", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdNotEqualTo(String value) {
            addCriterion("up_institution_id <>", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdGreaterThan(String value) {
            addCriterion("up_institution_id >", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdGreaterThanOrEqualTo(String value) {
            addCriterion("up_institution_id >=", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdLessThan(String value) {
            addCriterion("up_institution_id <", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdLessThanOrEqualTo(String value) {
            addCriterion("up_institution_id <=", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdLike(String value) {
            addCriterion("up_institution_id like", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdNotLike(String value) {
            addCriterion("up_institution_id not like", value, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdIn(List<String> values) {
            addCriterion("up_institution_id in", values, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdNotIn(List<String> values) {
            addCriterion("up_institution_id not in", values, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdBetween(String value1, String value2) {
            addCriterion("up_institution_id between", value1, value2, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpInstitutionIdNotBetween(String value1, String value2) {
            addCriterion("up_institution_id not between", value1, value2, "upInstitutionId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdIsNull() {
            addCriterion("up_product_id is null");
            return (Criteria) this;
        }

        public Criteria andUpProductIdIsNotNull() {
            addCriterion("up_product_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpProductIdEqualTo(String value) {
            addCriterion("up_product_id =", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdNotEqualTo(String value) {
            addCriterion("up_product_id <>", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdGreaterThan(String value) {
            addCriterion("up_product_id >", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("up_product_id >=", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdLessThan(String value) {
            addCriterion("up_product_id <", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdLessThanOrEqualTo(String value) {
            addCriterion("up_product_id <=", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdLike(String value) {
            addCriterion("up_product_id like", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdNotLike(String value) {
            addCriterion("up_product_id not like", value, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdIn(List<String> values) {
            addCriterion("up_product_id in", values, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdNotIn(List<String> values) {
            addCriterion("up_product_id not in", values, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdBetween(String value1, String value2) {
            addCriterion("up_product_id between", value1, value2, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpProductIdNotBetween(String value1, String value2) {
            addCriterion("up_product_id not between", value1, value2, "upProductId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdIsNull() {
            addCriterion("up_course_id is null");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdIsNotNull() {
            addCriterion("up_course_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdEqualTo(String value) {
            addCriterion("up_course_id =", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdNotEqualTo(String value) {
            addCriterion("up_course_id <>", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdGreaterThan(String value) {
            addCriterion("up_course_id >", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdGreaterThanOrEqualTo(String value) {
            addCriterion("up_course_id >=", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdLessThan(String value) {
            addCriterion("up_course_id <", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdLessThanOrEqualTo(String value) {
            addCriterion("up_course_id <=", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdLike(String value) {
            addCriterion("up_course_id like", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdNotLike(String value) {
            addCriterion("up_course_id not like", value, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdIn(List<String> values) {
            addCriterion("up_course_id in", values, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdNotIn(List<String> values) {
            addCriterion("up_course_id not in", values, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdBetween(String value1, String value2) {
            addCriterion("up_course_id between", value1, value2, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andUpCourseIdNotBetween(String value1, String value2) {
            addCriterion("up_course_id not between", value1, value2, "upCourseId");
            return (Criteria) this;
        }

        public Criteria andMainCoverIsNull() {
            addCriterion("main_cover is null");
            return (Criteria) this;
        }

        public Criteria andMainCoverIsNotNull() {
            addCriterion("main_cover is not null");
            return (Criteria) this;
        }

        public Criteria andMainCoverEqualTo(String value) {
            addCriterion("main_cover =", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverNotEqualTo(String value) {
            addCriterion("main_cover <>", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverGreaterThan(String value) {
            addCriterion("main_cover >", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverGreaterThanOrEqualTo(String value) {
            addCriterion("main_cover >=", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverLessThan(String value) {
            addCriterion("main_cover <", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverLessThanOrEqualTo(String value) {
            addCriterion("main_cover <=", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverLike(String value) {
            addCriterion("main_cover like", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverNotLike(String value) {
            addCriterion("main_cover not like", value, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverIn(List<String> values) {
            addCriterion("main_cover in", values, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverNotIn(List<String> values) {
            addCriterion("main_cover not in", values, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverBetween(String value1, String value2) {
            addCriterion("main_cover between", value1, value2, "mainCover");
            return (Criteria) this;
        }

        public Criteria andMainCoverNotBetween(String value1, String value2) {
            addCriterion("main_cover not between", value1, value2, "mainCover");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowIsNull() {
            addCriterion("plat_live_show is null");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowIsNotNull() {
            addCriterion("plat_live_show is not null");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowEqualTo(Integer value) {
            addCriterion("plat_live_show =", value, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowNotEqualTo(Integer value) {
            addCriterion("plat_live_show <>", value, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowGreaterThan(Integer value) {
            addCriterion("plat_live_show >", value, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("plat_live_show >=", value, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowLessThan(Integer value) {
            addCriterion("plat_live_show <", value, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowLessThanOrEqualTo(Integer value) {
            addCriterion("plat_live_show <=", value, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowIn(List<Integer> values) {
            addCriterion("plat_live_show in", values, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowNotIn(List<Integer> values) {
            addCriterion("plat_live_show not in", values, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowBetween(Integer value1, Integer value2) {
            addCriterion("plat_live_show between", value1, value2, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andPlatLiveShowNotBetween(Integer value1, Integer value2) {
            addCriterion("plat_live_show not between", value1, value2, "platLiveShow");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagIsNull() {
            addCriterion("close_plat_menu_flag is null");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagIsNotNull() {
            addCriterion("close_plat_menu_flag is not null");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagEqualTo(Integer value) {
            addCriterion("close_plat_menu_flag =", value, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagNotEqualTo(Integer value) {
            addCriterion("close_plat_menu_flag <>", value, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagGreaterThan(Integer value) {
            addCriterion("close_plat_menu_flag >", value, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("close_plat_menu_flag >=", value, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagLessThan(Integer value) {
            addCriterion("close_plat_menu_flag <", value, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagLessThanOrEqualTo(Integer value) {
            addCriterion("close_plat_menu_flag <=", value, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagIn(List<Integer> values) {
            addCriterion("close_plat_menu_flag in", values, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagNotIn(List<Integer> values) {
            addCriterion("close_plat_menu_flag not in", values, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagBetween(Integer value1, Integer value2) {
            addCriterion("close_plat_menu_flag between", value1, value2, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andClosePlatMenuFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("close_plat_menu_flag not between", value1, value2, "closePlatMenuFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagIsNull() {
            addCriterion("black_Name_List_flag is null");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagIsNotNull() {
            addCriterion("black_Name_List_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagEqualTo(Integer value) {
            addCriterion("black_Name_List_flag =", value, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagNotEqualTo(Integer value) {
            addCriterion("black_Name_List_flag <>", value, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagGreaterThan(Integer value) {
            addCriterion("black_Name_List_flag >", value, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("black_Name_List_flag >=", value, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagLessThan(Integer value) {
            addCriterion("black_Name_List_flag <", value, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagLessThanOrEqualTo(Integer value) {
            addCriterion("black_Name_List_flag <=", value, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagIn(List<Integer> values) {
            addCriterion("black_Name_List_flag in", values, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagNotIn(List<Integer> values) {
            addCriterion("black_Name_List_flag not in", values, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagBetween(Integer value1, Integer value2) {
            addCriterion("black_Name_List_flag between", value1, value2, "blackNameListFlag");
            return (Criteria) this;
        }

        public Criteria andBlackNameListFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("black_Name_List_flag not between", value1, value2, "blackNameListFlag");
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