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

public class DemandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DemandExample() {
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

        public Criteria andDemandInstIsNull() {
            addCriterion("demand_inst is null");
            return (Criteria) this;
        }

        public Criteria andDemandInstIsNotNull() {
            addCriterion("demand_inst is not null");
            return (Criteria) this;
        }

        public Criteria andDemandInstEqualTo(String value) {
            addCriterion("demand_inst =", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstNotEqualTo(String value) {
            addCriterion("demand_inst <>", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstGreaterThan(String value) {
            addCriterion("demand_inst >", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstGreaterThanOrEqualTo(String value) {
            addCriterion("demand_inst >=", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstLessThan(String value) {
            addCriterion("demand_inst <", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstLessThanOrEqualTo(String value) {
            addCriterion("demand_inst <=", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstLike(String value) {
            addCriterion("demand_inst like", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstNotLike(String value) {
            addCriterion("demand_inst not like", value, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstIn(List<String> values) {
            addCriterion("demand_inst in", values, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstNotIn(List<String> values) {
            addCriterion("demand_inst not in", values, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstBetween(String value1, String value2) {
            addCriterion("demand_inst between", value1, value2, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDemandInstNotBetween(String value1, String value2) {
            addCriterion("demand_inst not between", value1, value2, "demandInst");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("`desc` is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("`desc` is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("`desc` =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("`desc` <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("`desc` >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("`desc` >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("`desc` <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("`desc` <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("`desc` like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("`desc` not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("`desc` in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("`desc` not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("`desc` not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameIsNull() {
            addCriterion("demand_contact_name is null");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameIsNotNull() {
            addCriterion("demand_contact_name is not null");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameEqualTo(String value) {
            addCriterion("demand_contact_name =", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameNotEqualTo(String value) {
            addCriterion("demand_contact_name <>", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameGreaterThan(String value) {
            addCriterion("demand_contact_name >", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameGreaterThanOrEqualTo(String value) {
            addCriterion("demand_contact_name >=", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameLessThan(String value) {
            addCriterion("demand_contact_name <", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameLessThanOrEqualTo(String value) {
            addCriterion("demand_contact_name <=", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameLike(String value) {
            addCriterion("demand_contact_name like", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameNotLike(String value) {
            addCriterion("demand_contact_name not like", value, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameIn(List<String> values) {
            addCriterion("demand_contact_name in", values, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameNotIn(List<String> values) {
            addCriterion("demand_contact_name not in", values, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameBetween(String value1, String value2) {
            addCriterion("demand_contact_name between", value1, value2, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactNameNotBetween(String value1, String value2) {
            addCriterion("demand_contact_name not between", value1, value2, "demandContactName");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneIsNull() {
            addCriterion("demand_contact_phone is null");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneIsNotNull() {
            addCriterion("demand_contact_phone is not null");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneEqualTo(String value) {
            addCriterion("demand_contact_phone =", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneNotEqualTo(String value) {
            addCriterion("demand_contact_phone <>", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneGreaterThan(String value) {
            addCriterion("demand_contact_phone >", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("demand_contact_phone >=", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneLessThan(String value) {
            addCriterion("demand_contact_phone <", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("demand_contact_phone <=", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneLike(String value) {
            addCriterion("demand_contact_phone like", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneNotLike(String value) {
            addCriterion("demand_contact_phone not like", value, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneIn(List<String> values) {
            addCriterion("demand_contact_phone in", values, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneNotIn(List<String> values) {
            addCriterion("demand_contact_phone not in", values, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneBetween(String value1, String value2) {
            addCriterion("demand_contact_phone between", value1, value2, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andDemandContactPhoneNotBetween(String value1, String value2) {
            addCriterion("demand_contact_phone not between", value1, value2, "demandContactPhone");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNull() {
            addCriterion("recommend is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNotNull() {
            addCriterion("recommend is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendEqualTo(String value) {
            addCriterion("recommend =", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotEqualTo(String value) {
            addCriterion("recommend <>", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThan(String value) {
            addCriterion("recommend >", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThanOrEqualTo(String value) {
            addCriterion("recommend >=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThan(String value) {
            addCriterion("recommend <", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThanOrEqualTo(String value) {
            addCriterion("recommend <=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLike(String value) {
            addCriterion("recommend like", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotLike(String value) {
            addCriterion("recommend not like", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendIn(List<String> values) {
            addCriterion("recommend in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotIn(List<String> values) {
            addCriterion("recommend not in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendBetween(String value1, String value2) {
            addCriterion("recommend between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotBetween(String value1, String value2) {
            addCriterion("recommend not between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andInvolvedIsNull() {
            addCriterion("involved is null");
            return (Criteria) this;
        }

        public Criteria andInvolvedIsNotNull() {
            addCriterion("involved is not null");
            return (Criteria) this;
        }

        public Criteria andInvolvedEqualTo(String value) {
            addCriterion("involved =", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedNotEqualTo(String value) {
            addCriterion("involved <>", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedGreaterThan(String value) {
            addCriterion("involved >", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedGreaterThanOrEqualTo(String value) {
            addCriterion("involved >=", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedLessThan(String value) {
            addCriterion("involved <", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedLessThanOrEqualTo(String value) {
            addCriterion("involved <=", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedLike(String value) {
            addCriterion("involved like", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedNotLike(String value) {
            addCriterion("involved not like", value, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedIn(List<String> values) {
            addCriterion("involved in", values, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedNotIn(List<String> values) {
            addCriterion("involved not in", values, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedBetween(String value1, String value2) {
            addCriterion("involved between", value1, value2, "involved");
            return (Criteria) this;
        }

        public Criteria andInvolvedNotBetween(String value1, String value2) {
            addCriterion("involved not between", value1, value2, "involved");
            return (Criteria) this;
        }

        public Criteria andRegionIsNull() {
            addCriterion("region is null");
            return (Criteria) this;
        }

        public Criteria andRegionIsNotNull() {
            addCriterion("region is not null");
            return (Criteria) this;
        }

        public Criteria andRegionEqualTo(String value) {
            addCriterion("region =", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotEqualTo(String value) {
            addCriterion("region <>", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThan(String value) {
            addCriterion("region >", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThanOrEqualTo(String value) {
            addCriterion("region >=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThan(String value) {
            addCriterion("region <", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThanOrEqualTo(String value) {
            addCriterion("region <=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLike(String value) {
            addCriterion("region like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotLike(String value) {
            addCriterion("region not like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionIn(List<String> values) {
            addCriterion("region in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotIn(List<String> values) {
            addCriterion("region not in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionBetween(String value1, String value2) {
            addCriterion("region between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotBetween(String value1, String value2) {
            addCriterion("region not between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andDate1IsNull() {
            addCriterion("date1 is null");
            return (Criteria) this;
        }

        public Criteria andDate1IsNotNull() {
            addCriterion("date1 is not null");
            return (Criteria) this;
        }

        public Criteria andDate1EqualTo(Date value) {
            addCriterion("date1 =", value, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1NotEqualTo(Date value) {
            addCriterion("date1 <>", value, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1GreaterThan(Date value) {
            addCriterion("date1 >", value, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1GreaterThanOrEqualTo(Date value) {
            addCriterion("date1 >=", value, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1LessThan(Date value) {
            addCriterion("date1 <", value, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1LessThanOrEqualTo(Date value) {
            addCriterion("date1 <=", value, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1In(List<Date> values) {
            addCriterion("date1 in", values, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1NotIn(List<Date> values) {
            addCriterion("date1 not in", values, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1Between(Date value1, Date value2) {
            addCriterion("date1 between", value1, value2, "date1");
            return (Criteria) this;
        }

        public Criteria andDate1NotBetween(Date value1, Date value2) {
            addCriterion("date1 not between", value1, value2, "date1");
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

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andCooperationIsNull() {
            addCriterion("cooperation is null");
            return (Criteria) this;
        }

        public Criteria andCooperationIsNotNull() {
            addCriterion("cooperation is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationEqualTo(String value) {
            addCriterion("cooperation =", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationNotEqualTo(String value) {
            addCriterion("cooperation <>", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationGreaterThan(String value) {
            addCriterion("cooperation >", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationGreaterThanOrEqualTo(String value) {
            addCriterion("cooperation >=", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationLessThan(String value) {
            addCriterion("cooperation <", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationLessThanOrEqualTo(String value) {
            addCriterion("cooperation <=", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationLike(String value) {
            addCriterion("cooperation like", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationNotLike(String value) {
            addCriterion("cooperation not like", value, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationIn(List<String> values) {
            addCriterion("cooperation in", values, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationNotIn(List<String> values) {
            addCriterion("cooperation not in", values, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationBetween(String value1, String value2) {
            addCriterion("cooperation between", value1, value2, "cooperation");
            return (Criteria) this;
        }

        public Criteria andCooperationNotBetween(String value1, String value2) {
            addCriterion("cooperation not between", value1, value2, "cooperation");
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

        public Criteria andDemandClassifyIsNull() {
            addCriterion("demand_classify is null");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyIsNotNull() {
            addCriterion("demand_classify is not null");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyEqualTo(String value) {
            addCriterion("demand_classify =", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyNotEqualTo(String value) {
            addCriterion("demand_classify <>", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyGreaterThan(String value) {
            addCriterion("demand_classify >", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyGreaterThanOrEqualTo(String value) {
            addCriterion("demand_classify >=", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyLessThan(String value) {
            addCriterion("demand_classify <", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyLessThanOrEqualTo(String value) {
            addCriterion("demand_classify <=", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyLike(String value) {
            addCriterion("demand_classify like", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyNotLike(String value) {
            addCriterion("demand_classify not like", value, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyIn(List<String> values) {
            addCriterion("demand_classify in", values, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyNotIn(List<String> values) {
            addCriterion("demand_classify not in", values, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyBetween(String value1, String value2) {
            addCriterion("demand_classify between", value1, value2, "demandClassify");
            return (Criteria) this;
        }

        public Criteria andDemandClassifyNotBetween(String value1, String value2) {
            addCriterion("demand_classify not between", value1, value2, "demandClassify");
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