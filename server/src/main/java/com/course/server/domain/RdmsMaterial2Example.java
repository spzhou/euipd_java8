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

public class RdmsMaterial2Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsMaterial2Example() {
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

        public Criteria andTokenCodeIsNull() {
            addCriterion("token_code is null");
            return (Criteria) this;
        }

        public Criteria andTokenCodeIsNotNull() {
            addCriterion("token_code is not null");
            return (Criteria) this;
        }

        public Criteria andTokenCodeEqualTo(String value) {
            addCriterion("token_code =", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeNotEqualTo(String value) {
            addCriterion("token_code <>", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeGreaterThan(String value) {
            addCriterion("token_code >", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeGreaterThanOrEqualTo(String value) {
            addCriterion("token_code >=", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeLessThan(String value) {
            addCriterion("token_code <", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeLessThanOrEqualTo(String value) {
            addCriterion("token_code <=", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeLike(String value) {
            addCriterion("token_code like", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeNotLike(String value) {
            addCriterion("token_code not like", value, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeIn(List<String> values) {
            addCriterion("token_code in", values, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeNotIn(List<String> values) {
            addCriterion("token_code not in", values, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeBetween(String value1, String value2) {
            addCriterion("token_code between", value1, value2, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andTokenCodeNotBetween(String value1, String value2) {
            addCriterion("token_code not between", value1, value2, "tokenCode");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andBomCodeIsNull() {
            addCriterion("bom_code is null");
            return (Criteria) this;
        }

        public Criteria andBomCodeIsNotNull() {
            addCriterion("bom_code is not null");
            return (Criteria) this;
        }

        public Criteria andBomCodeEqualTo(String value) {
            addCriterion("bom_code =", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeNotEqualTo(String value) {
            addCriterion("bom_code <>", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeGreaterThan(String value) {
            addCriterion("bom_code >", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bom_code >=", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeLessThan(String value) {
            addCriterion("bom_code <", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeLessThanOrEqualTo(String value) {
            addCriterion("bom_code <=", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeLike(String value) {
            addCriterion("bom_code like", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeNotLike(String value) {
            addCriterion("bom_code not like", value, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeIn(List<String> values) {
            addCriterion("bom_code in", values, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeNotIn(List<String> values) {
            addCriterion("bom_code not in", values, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeBetween(String value1, String value2) {
            addCriterion("bom_code between", value1, value2, "bomCode");
            return (Criteria) this;
        }

        public Criteria andBomCodeNotBetween(String value1, String value2) {
            addCriterion("bom_code not between", value1, value2, "bomCode");
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

        public Criteria andModelIsNull() {
            addCriterion("model is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("model is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("model =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("model <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("model >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("model >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("model <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("model <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("model like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("model not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("model in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("model not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("model between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("model not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Double value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Double value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Double value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Double value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Double value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Double> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Double> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Double value1, Double value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Double value1, Double value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andUsageModeIsNull() {
            addCriterion("usage_mode is null");
            return (Criteria) this;
        }

        public Criteria andUsageModeIsNotNull() {
            addCriterion("usage_mode is not null");
            return (Criteria) this;
        }

        public Criteria andUsageModeEqualTo(String value) {
            addCriterion("usage_mode =", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeNotEqualTo(String value) {
            addCriterion("usage_mode <>", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeGreaterThan(String value) {
            addCriterion("usage_mode >", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeGreaterThanOrEqualTo(String value) {
            addCriterion("usage_mode >=", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeLessThan(String value) {
            addCriterion("usage_mode <", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeLessThanOrEqualTo(String value) {
            addCriterion("usage_mode <=", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeLike(String value) {
            addCriterion("usage_mode like", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeNotLike(String value) {
            addCriterion("usage_mode not like", value, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeIn(List<String> values) {
            addCriterion("usage_mode in", values, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeNotIn(List<String> values) {
            addCriterion("usage_mode not in", values, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeBetween(String value1, String value2) {
            addCriterion("usage_mode between", value1, value2, "usageMode");
            return (Criteria) this;
        }

        public Criteria andUsageModeNotBetween(String value1, String value2) {
            addCriterion("usage_mode not between", value1, value2, "usageMode");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceIsNull() {
            addCriterion("pre_tax_unit_price is null");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceIsNotNull() {
            addCriterion("pre_tax_unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceEqualTo(BigDecimal value) {
            addCriterion("pre_tax_unit_price =", value, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("pre_tax_unit_price <>", value, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("pre_tax_unit_price >", value, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_tax_unit_price >=", value, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceLessThan(BigDecimal value) {
            addCriterion("pre_tax_unit_price <", value, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_tax_unit_price <=", value, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceIn(List<BigDecimal> values) {
            addCriterion("pre_tax_unit_price in", values, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("pre_tax_unit_price not in", values, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_tax_unit_price between", value1, value2, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_tax_unit_price not between", value1, value2, "preTaxUnitPrice");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("tax_rate is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("tax_rate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(BigDecimal value) {
            addCriterion("tax_rate =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(BigDecimal value) {
            addCriterion("tax_rate <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(BigDecimal value) {
            addCriterion("tax_rate >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_rate >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(BigDecimal value) {
            addCriterion("tax_rate <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_rate <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<BigDecimal> values) {
            addCriterion("tax_rate in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<BigDecimal> values) {
            addCriterion("tax_rate not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_rate between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_rate not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceIsNull() {
            addCriterion("pre_tax_sum_price is null");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceIsNotNull() {
            addCriterion("pre_tax_sum_price is not null");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceEqualTo(BigDecimal value) {
            addCriterion("pre_tax_sum_price =", value, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceNotEqualTo(BigDecimal value) {
            addCriterion("pre_tax_sum_price <>", value, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceGreaterThan(BigDecimal value) {
            addCriterion("pre_tax_sum_price >", value, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_tax_sum_price >=", value, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceLessThan(BigDecimal value) {
            addCriterion("pre_tax_sum_price <", value, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_tax_sum_price <=", value, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceIn(List<BigDecimal> values) {
            addCriterion("pre_tax_sum_price in", values, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceNotIn(List<BigDecimal> values) {
            addCriterion("pre_tax_sum_price not in", values, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_tax_sum_price between", value1, value2, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxSumPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_tax_sum_price not between", value1, value2, "preTaxSumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceIsNull() {
            addCriterion("sum_price is null");
            return (Criteria) this;
        }

        public Criteria andSumPriceIsNotNull() {
            addCriterion("sum_price is not null");
            return (Criteria) this;
        }

        public Criteria andSumPriceEqualTo(BigDecimal value) {
            addCriterion("sum_price =", value, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceNotEqualTo(BigDecimal value) {
            addCriterion("sum_price <>", value, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceGreaterThan(BigDecimal value) {
            addCriterion("sum_price >", value, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_price >=", value, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceLessThan(BigDecimal value) {
            addCriterion("sum_price <", value, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_price <=", value, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceIn(List<BigDecimal> values) {
            addCriterion("sum_price in", values, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceNotIn(List<BigDecimal> values) {
            addCriterion("sum_price not in", values, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_price between", value1, value2, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSumPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_price not between", value1, value2, "sumPrice");
            return (Criteria) this;
        }

        public Criteria andSalesIsNull() {
            addCriterion("sales is null");
            return (Criteria) this;
        }

        public Criteria andSalesIsNotNull() {
            addCriterion("sales is not null");
            return (Criteria) this;
        }

        public Criteria andSalesEqualTo(String value) {
            addCriterion("sales =", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesNotEqualTo(String value) {
            addCriterion("sales <>", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesGreaterThan(String value) {
            addCriterion("sales >", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesGreaterThanOrEqualTo(String value) {
            addCriterion("sales >=", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesLessThan(String value) {
            addCriterion("sales <", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesLessThanOrEqualTo(String value) {
            addCriterion("sales <=", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesLike(String value) {
            addCriterion("sales like", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesNotLike(String value) {
            addCriterion("sales not like", value, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesIn(List<String> values) {
            addCriterion("sales in", values, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesNotIn(List<String> values) {
            addCriterion("sales not in", values, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesBetween(String value1, String value2) {
            addCriterion("sales between", value1, value2, "sales");
            return (Criteria) this;
        }

        public Criteria andSalesNotBetween(String value1, String value2) {
            addCriterion("sales not between", value1, value2, "sales");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNull() {
            addCriterion("contact_phone is null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNotNull() {
            addCriterion("contact_phone is not null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneEqualTo(String value) {
            addCriterion("contact_phone =", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotEqualTo(String value) {
            addCriterion("contact_phone <>", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThan(String value) {
            addCriterion("contact_phone >", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("contact_phone >=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThan(String value) {
            addCriterion("contact_phone <", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("contact_phone <=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLike(String value) {
            addCriterion("contact_phone like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotLike(String value) {
            addCriterion("contact_phone not like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIn(List<String> values) {
            addCriterion("contact_phone in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotIn(List<String> values) {
            addCriterion("contact_phone not in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneBetween(String value1, String value2) {
            addCriterion("contact_phone between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotBetween(String value1, String value2) {
            addCriterion("contact_phone not between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNull() {
            addCriterion("supplier_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNotNull() {
            addCriterion("supplier_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameEqualTo(String value) {
            addCriterion("supplier_name =", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotEqualTo(String value) {
            addCriterion("supplier_name <>", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThan(String value) {
            addCriterion("supplier_name >", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_name >=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThan(String value) {
            addCriterion("supplier_name <", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThanOrEqualTo(String value) {
            addCriterion("supplier_name <=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLike(String value) {
            addCriterion("supplier_name like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotLike(String value) {
            addCriterion("supplier_name not like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIn(List<String> values) {
            addCriterion("supplier_name in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotIn(List<String> values) {
            addCriterion("supplier_name not in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameBetween(String value1, String value2) {
            addCriterion("supplier_name between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotBetween(String value1, String value2) {
            addCriterion("supplier_name not between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressIsNull() {
            addCriterion("supplier_address is null");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressIsNotNull() {
            addCriterion("supplier_address is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressEqualTo(String value) {
            addCriterion("supplier_address =", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotEqualTo(String value) {
            addCriterion("supplier_address <>", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressGreaterThan(String value) {
            addCriterion("supplier_address >", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_address >=", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressLessThan(String value) {
            addCriterion("supplier_address <", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressLessThanOrEqualTo(String value) {
            addCriterion("supplier_address <=", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressLike(String value) {
            addCriterion("supplier_address like", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotLike(String value) {
            addCriterion("supplier_address not like", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressIn(List<String> values) {
            addCriterion("supplier_address in", values, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotIn(List<String> values) {
            addCriterion("supplier_address not in", values, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressBetween(String value1, String value2) {
            addCriterion("supplier_address between", value1, value2, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotBetween(String value1, String value2) {
            addCriterion("supplier_address not between", value1, value2, "supplierAddress");
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

        public Criteria andAuxStatusIsNull() {
            addCriterion("aux_status is null");
            return (Criteria) this;
        }

        public Criteria andAuxStatusIsNotNull() {
            addCriterion("aux_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuxStatusEqualTo(String value) {
            addCriterion("aux_status =", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotEqualTo(String value) {
            addCriterion("aux_status <>", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusGreaterThan(String value) {
            addCriterion("aux_status >", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusGreaterThanOrEqualTo(String value) {
            addCriterion("aux_status >=", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusLessThan(String value) {
            addCriterion("aux_status <", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusLessThanOrEqualTo(String value) {
            addCriterion("aux_status <=", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusLike(String value) {
            addCriterion("aux_status like", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotLike(String value) {
            addCriterion("aux_status not like", value, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusIn(List<String> values) {
            addCriterion("aux_status in", values, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotIn(List<String> values) {
            addCriterion("aux_status not in", values, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusBetween(String value1, String value2) {
            addCriterion("aux_status between", value1, value2, "auxStatus");
            return (Criteria) this;
        }

        public Criteria andAuxStatusNotBetween(String value1, String value2) {
            addCriterion("aux_status not between", value1, value2, "auxStatus");
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

        public Criteria andStorageCodeIsNull() {
            addCriterion("storage_code is null");
            return (Criteria) this;
        }

        public Criteria andStorageCodeIsNotNull() {
            addCriterion("storage_code is not null");
            return (Criteria) this;
        }

        public Criteria andStorageCodeEqualTo(String value) {
            addCriterion("storage_code =", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeNotEqualTo(String value) {
            addCriterion("storage_code <>", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeGreaterThan(String value) {
            addCriterion("storage_code >", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeGreaterThanOrEqualTo(String value) {
            addCriterion("storage_code >=", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeLessThan(String value) {
            addCriterion("storage_code <", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeLessThanOrEqualTo(String value) {
            addCriterion("storage_code <=", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeLike(String value) {
            addCriterion("storage_code like", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeNotLike(String value) {
            addCriterion("storage_code not like", value, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeIn(List<String> values) {
            addCriterion("storage_code in", values, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeNotIn(List<String> values) {
            addCriterion("storage_code not in", values, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeBetween(String value1, String value2) {
            addCriterion("storage_code between", value1, value2, "storageCode");
            return (Criteria) this;
        }

        public Criteria andStorageCodeNotBetween(String value1, String value2) {
            addCriterion("storage_code not between", value1, value2, "storageCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIsNull() {
            addCriterion("batch_code is null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIsNotNull() {
            addCriterion("batch_code is not null");
            return (Criteria) this;
        }

        public Criteria andBatchCodeEqualTo(String value) {
            addCriterion("batch_code =", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotEqualTo(String value) {
            addCriterion("batch_code <>", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeGreaterThan(String value) {
            addCriterion("batch_code >", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeGreaterThanOrEqualTo(String value) {
            addCriterion("batch_code >=", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLessThan(String value) {
            addCriterion("batch_code <", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLessThanOrEqualTo(String value) {
            addCriterion("batch_code <=", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeLike(String value) {
            addCriterion("batch_code like", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotLike(String value) {
            addCriterion("batch_code not like", value, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeIn(List<String> values) {
            addCriterion("batch_code in", values, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotIn(List<String> values) {
            addCriterion("batch_code not in", values, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeBetween(String value1, String value2) {
            addCriterion("batch_code between", value1, value2, "batchCode");
            return (Criteria) this;
        }

        public Criteria andBatchCodeNotBetween(String value1, String value2) {
            addCriterion("batch_code not between", value1, value2, "batchCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNull() {
            addCriterion("warehouse_code is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIsNotNull() {
            addCriterion("warehouse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeEqualTo(String value) {
            addCriterion("warehouse_code =", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotEqualTo(String value) {
            addCriterion("warehouse_code <>", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThan(String value) {
            addCriterion("warehouse_code >", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_code >=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThan(String value) {
            addCriterion("warehouse_code <", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLessThanOrEqualTo(String value) {
            addCriterion("warehouse_code <=", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeLike(String value) {
            addCriterion("warehouse_code like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotLike(String value) {
            addCriterion("warehouse_code not like", value, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeIn(List<String> values) {
            addCriterion("warehouse_code in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotIn(List<String> values) {
            addCriterion("warehouse_code not in", values, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeBetween(String value1, String value2) {
            addCriterion("warehouse_code between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andWarehouseCodeNotBetween(String value1, String value2) {
            addCriterion("warehouse_code not between", value1, value2, "warehouseCode");
            return (Criteria) this;
        }

        public Criteria andReserve1IsNull() {
            addCriterion("reserve1 is null");
            return (Criteria) this;
        }

        public Criteria andReserve1IsNotNull() {
            addCriterion("reserve1 is not null");
            return (Criteria) this;
        }

        public Criteria andReserve1EqualTo(String value) {
            addCriterion("reserve1 =", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1NotEqualTo(String value) {
            addCriterion("reserve1 <>", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1GreaterThan(String value) {
            addCriterion("reserve1 >", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1GreaterThanOrEqualTo(String value) {
            addCriterion("reserve1 >=", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1LessThan(String value) {
            addCriterion("reserve1 <", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1LessThanOrEqualTo(String value) {
            addCriterion("reserve1 <=", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1Like(String value) {
            addCriterion("reserve1 like", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1NotLike(String value) {
            addCriterion("reserve1 not like", value, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1In(List<String> values) {
            addCriterion("reserve1 in", values, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1NotIn(List<String> values) {
            addCriterion("reserve1 not in", values, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1Between(String value1, String value2) {
            addCriterion("reserve1 between", value1, value2, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve1NotBetween(String value1, String value2) {
            addCriterion("reserve1 not between", value1, value2, "reserve1");
            return (Criteria) this;
        }

        public Criteria andReserve2IsNull() {
            addCriterion("reserve2 is null");
            return (Criteria) this;
        }

        public Criteria andReserve2IsNotNull() {
            addCriterion("reserve2 is not null");
            return (Criteria) this;
        }

        public Criteria andReserve2EqualTo(String value) {
            addCriterion("reserve2 =", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2NotEqualTo(String value) {
            addCriterion("reserve2 <>", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2GreaterThan(String value) {
            addCriterion("reserve2 >", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2GreaterThanOrEqualTo(String value) {
            addCriterion("reserve2 >=", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2LessThan(String value) {
            addCriterion("reserve2 <", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2LessThanOrEqualTo(String value) {
            addCriterion("reserve2 <=", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2Like(String value) {
            addCriterion("reserve2 like", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2NotLike(String value) {
            addCriterion("reserve2 not like", value, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2In(List<String> values) {
            addCriterion("reserve2 in", values, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2NotIn(List<String> values) {
            addCriterion("reserve2 not in", values, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2Between(String value1, String value2) {
            addCriterion("reserve2 between", value1, value2, "reserve2");
            return (Criteria) this;
        }

        public Criteria andReserve2NotBetween(String value1, String value2) {
            addCriterion("reserve2 not between", value1, value2, "reserve2");
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

        public Criteria andInBomIsNull() {
            addCriterion("in_bom is null");
            return (Criteria) this;
        }

        public Criteria andInBomIsNotNull() {
            addCriterion("in_bom is not null");
            return (Criteria) this;
        }

        public Criteria andInBomEqualTo(Integer value) {
            addCriterion("in_bom =", value, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomNotEqualTo(Integer value) {
            addCriterion("in_bom <>", value, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomGreaterThan(Integer value) {
            addCriterion("in_bom >", value, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_bom >=", value, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomLessThan(Integer value) {
            addCriterion("in_bom <", value, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomLessThanOrEqualTo(Integer value) {
            addCriterion("in_bom <=", value, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomIn(List<Integer> values) {
            addCriterion("in_bom in", values, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomNotIn(List<Integer> values) {
            addCriterion("in_bom not in", values, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomBetween(Integer value1, Integer value2) {
            addCriterion("in_bom between", value1, value2, "inBom");
            return (Criteria) this;
        }

        public Criteria andInBomNotBetween(Integer value1, Integer value2) {
            addCriterion("in_bom not between", value1, value2, "inBom");
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