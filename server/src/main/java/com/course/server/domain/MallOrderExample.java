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

public class MallOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MallOrderExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
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

        public Criteria andUserNoteIsNull() {
            addCriterion("user_note is null");
            return (Criteria) this;
        }

        public Criteria andUserNoteIsNotNull() {
            addCriterion("user_note is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoteEqualTo(String value) {
            addCriterion("user_note =", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotEqualTo(String value) {
            addCriterion("user_note <>", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteGreaterThan(String value) {
            addCriterion("user_note >", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteGreaterThanOrEqualTo(String value) {
            addCriterion("user_note >=", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteLessThan(String value) {
            addCriterion("user_note <", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteLessThanOrEqualTo(String value) {
            addCriterion("user_note <=", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteLike(String value) {
            addCriterion("user_note like", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotLike(String value) {
            addCriterion("user_note not like", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteIn(List<String> values) {
            addCriterion("user_note in", values, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotIn(List<String> values) {
            addCriterion("user_note not in", values, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteBetween(String value1, String value2) {
            addCriterion("user_note between", value1, value2, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotBetween(String value1, String value2) {
            addCriterion("user_note not between", value1, value2, "userNote");
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

        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("supplier_id like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("supplier_id not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andBuyerNameIsNull() {
            addCriterion("buyer_name is null");
            return (Criteria) this;
        }

        public Criteria andBuyerNameIsNotNull() {
            addCriterion("buyer_name is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerNameEqualTo(String value) {
            addCriterion("buyer_name =", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotEqualTo(String value) {
            addCriterion("buyer_name <>", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameGreaterThan(String value) {
            addCriterion("buyer_name >", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_name >=", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameLessThan(String value) {
            addCriterion("buyer_name <", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameLessThanOrEqualTo(String value) {
            addCriterion("buyer_name <=", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameLike(String value) {
            addCriterion("buyer_name like", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotLike(String value) {
            addCriterion("buyer_name not like", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameIn(List<String> values) {
            addCriterion("buyer_name in", values, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotIn(List<String> values) {
            addCriterion("buyer_name not in", values, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameBetween(String value1, String value2) {
            addCriterion("buyer_name between", value1, value2, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotBetween(String value1, String value2) {
            addCriterion("buyer_name not between", value1, value2, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrIsNull() {
            addCriterion("buyer_addr is null");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrIsNotNull() {
            addCriterion("buyer_addr is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrEqualTo(String value) {
            addCriterion("buyer_addr =", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrNotEqualTo(String value) {
            addCriterion("buyer_addr <>", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrGreaterThan(String value) {
            addCriterion("buyer_addr >", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_addr >=", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrLessThan(String value) {
            addCriterion("buyer_addr <", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrLessThanOrEqualTo(String value) {
            addCriterion("buyer_addr <=", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrLike(String value) {
            addCriterion("buyer_addr like", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrNotLike(String value) {
            addCriterion("buyer_addr not like", value, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrIn(List<String> values) {
            addCriterion("buyer_addr in", values, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrNotIn(List<String> values) {
            addCriterion("buyer_addr not in", values, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrBetween(String value1, String value2) {
            addCriterion("buyer_addr between", value1, value2, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerAddrNotBetween(String value1, String value2) {
            addCriterion("buyer_addr not between", value1, value2, "buyerAddr");
            return (Criteria) this;
        }

        public Criteria andBuyerTelIsNull() {
            addCriterion("buyer_tel is null");
            return (Criteria) this;
        }

        public Criteria andBuyerTelIsNotNull() {
            addCriterion("buyer_tel is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerTelEqualTo(String value) {
            addCriterion("buyer_tel =", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelNotEqualTo(String value) {
            addCriterion("buyer_tel <>", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelGreaterThan(String value) {
            addCriterion("buyer_tel >", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_tel >=", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelLessThan(String value) {
            addCriterion("buyer_tel <", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelLessThanOrEqualTo(String value) {
            addCriterion("buyer_tel <=", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelLike(String value) {
            addCriterion("buyer_tel like", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelNotLike(String value) {
            addCriterion("buyer_tel not like", value, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelIn(List<String> values) {
            addCriterion("buyer_tel in", values, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelNotIn(List<String> values) {
            addCriterion("buyer_tel not in", values, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelBetween(String value1, String value2) {
            addCriterion("buyer_tel between", value1, value2, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andBuyerTelNotBetween(String value1, String value2) {
            addCriterion("buyer_tel not between", value1, value2, "buyerTel");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceIsNull() {
            addCriterion("pre_tax_price is null");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceIsNotNull() {
            addCriterion("pre_tax_price is not null");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceEqualTo(Double value) {
            addCriterion("pre_tax_price =", value, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceNotEqualTo(Double value) {
            addCriterion("pre_tax_price <>", value, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceGreaterThan(Double value) {
            addCriterion("pre_tax_price >", value, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("pre_tax_price >=", value, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceLessThan(Double value) {
            addCriterion("pre_tax_price <", value, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceLessThanOrEqualTo(Double value) {
            addCriterion("pre_tax_price <=", value, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceIn(List<Double> values) {
            addCriterion("pre_tax_price in", values, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceNotIn(List<Double> values) {
            addCriterion("pre_tax_price not in", values, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceBetween(Double value1, Double value2) {
            addCriterion("pre_tax_price between", value1, value2, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPreTaxPriceNotBetween(Double value1, Double value2) {
            addCriterion("pre_tax_price not between", value1, value2, "preTaxPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIsNull() {
            addCriterion("total_price is null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIsNotNull() {
            addCriterion("total_price is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceEqualTo(Double value) {
            addCriterion("total_price =", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotEqualTo(Double value) {
            addCriterion("total_price <>", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceGreaterThan(Double value) {
            addCriterion("total_price >", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("total_price >=", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceLessThan(Double value) {
            addCriterion("total_price <", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceLessThanOrEqualTo(Double value) {
            addCriterion("total_price <=", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIn(List<Double> values) {
            addCriterion("total_price in", values, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotIn(List<Double> values) {
            addCriterion("total_price not in", values, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceBetween(Double value1, Double value2) {
            addCriterion("total_price between", value1, value2, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotBetween(Double value1, Double value2) {
            addCriterion("total_price not between", value1, value2, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(Integer value) {
            addCriterion("pay_status =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(Integer value) {
            addCriterion("pay_status <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(Integer value) {
            addCriterion("pay_status >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_status >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(Integer value) {
            addCriterion("pay_status <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pay_status <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<Integer> values) {
            addCriterion("pay_status in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<Integer> values) {
            addCriterion("pay_status not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(Integer value1, Integer value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Integer value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Integer value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Integer value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Integer value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Integer> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Integer> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Integer value1, Integer value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andContractIdIsNull() {
            addCriterion("contract_id is null");
            return (Criteria) this;
        }

        public Criteria andContractIdIsNotNull() {
            addCriterion("contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andContractIdEqualTo(String value) {
            addCriterion("contract_id =", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotEqualTo(String value) {
            addCriterion("contract_id <>", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdGreaterThan(String value) {
            addCriterion("contract_id >", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdGreaterThanOrEqualTo(String value) {
            addCriterion("contract_id >=", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLessThan(String value) {
            addCriterion("contract_id <", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLessThanOrEqualTo(String value) {
            addCriterion("contract_id <=", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLike(String value) {
            addCriterion("contract_id like", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotLike(String value) {
            addCriterion("contract_id not like", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdIn(List<String> values) {
            addCriterion("contract_id in", values, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotIn(List<String> values) {
            addCriterion("contract_id not in", values, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdBetween(String value1, String value2) {
            addCriterion("contract_id between", value1, value2, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotBetween(String value1, String value2) {
            addCriterion("contract_id not between", value1, value2, "contractId");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Integer value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Integer value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Integer value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Integer value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Integer> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Integer> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
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

        public Criteria andApproveTimeIsNull() {
            addCriterion("approve_time is null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNotNull() {
            addCriterion("approve_time is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeEqualTo(Date value) {
            addCriterion("approve_time =", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotEqualTo(Date value) {
            addCriterion("approve_time <>", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThan(Date value) {
            addCriterion("approve_time >", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approve_time >=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThan(Date value) {
            addCriterion("approve_time <", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThanOrEqualTo(Date value) {
            addCriterion("approve_time <=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIn(List<Date> values) {
            addCriterion("approve_time in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotIn(List<Date> values) {
            addCriterion("approve_time not in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeBetween(Date value1, Date value2) {
            addCriterion("approve_time between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotBetween(Date value1, Date value2) {
            addCriterion("approve_time not between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateIsNull() {
            addCriterion("user_discount_rate is null");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateIsNotNull() {
            addCriterion("user_discount_rate is not null");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateEqualTo(Double value) {
            addCriterion("user_discount_rate =", value, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateNotEqualTo(Double value) {
            addCriterion("user_discount_rate <>", value, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateGreaterThan(Double value) {
            addCriterion("user_discount_rate >", value, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateGreaterThanOrEqualTo(Double value) {
            addCriterion("user_discount_rate >=", value, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateLessThan(Double value) {
            addCriterion("user_discount_rate <", value, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateLessThanOrEqualTo(Double value) {
            addCriterion("user_discount_rate <=", value, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateIn(List<Double> values) {
            addCriterion("user_discount_rate in", values, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateNotIn(List<Double> values) {
            addCriterion("user_discount_rate not in", values, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateBetween(Double value1, Double value2) {
            addCriterion("user_discount_rate between", value1, value2, "userDiscountRate");
            return (Criteria) this;
        }

        public Criteria andUserDiscountRateNotBetween(Double value1, Double value2) {
            addCriterion("user_discount_rate not between", value1, value2, "userDiscountRate");
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

        public Criteria andTaxRateEqualTo(Double value) {
            addCriterion("tax_rate =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(Double value) {
            addCriterion("tax_rate <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(Double value) {
            addCriterion("tax_rate >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(Double value) {
            addCriterion("tax_rate >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(Double value) {
            addCriterion("tax_rate <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(Double value) {
            addCriterion("tax_rate <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<Double> values) {
            addCriterion("tax_rate in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<Double> values) {
            addCriterion("tax_rate not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(Double value1, Double value2) {
            addCriterion("tax_rate between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(Double value1, Double value2) {
            addCriterion("tax_rate not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andShippingFeeIsNull() {
            addCriterion("shipping_fee is null");
            return (Criteria) this;
        }

        public Criteria andShippingFeeIsNotNull() {
            addCriterion("shipping_fee is not null");
            return (Criteria) this;
        }

        public Criteria andShippingFeeEqualTo(Double value) {
            addCriterion("shipping_fee =", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeNotEqualTo(Double value) {
            addCriterion("shipping_fee <>", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeGreaterThan(Double value) {
            addCriterion("shipping_fee >", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("shipping_fee >=", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeLessThan(Double value) {
            addCriterion("shipping_fee <", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeLessThanOrEqualTo(Double value) {
            addCriterion("shipping_fee <=", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeIn(List<Double> values) {
            addCriterion("shipping_fee in", values, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeNotIn(List<Double> values) {
            addCriterion("shipping_fee not in", values, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeBetween(Double value1, Double value2) {
            addCriterion("shipping_fee between", value1, value2, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeNotBetween(Double value1, Double value2) {
            addCriterion("shipping_fee not between", value1, value2, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(Double value) {
            addCriterion("discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(Double value) {
            addCriterion("discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(Double value) {
            addCriterion("discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(Double value) {
            addCriterion("discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(Double value) {
            addCriterion("discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(Double value) {
            addCriterion("discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<Double> values) {
            addCriterion("discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<Double> values) {
            addCriterion("discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(Double value1, Double value2) {
            addCriterion("discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(Double value1, Double value2) {
            addCriterion("discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andBuyFromIsNull() {
            addCriterion("buy_from is null");
            return (Criteria) this;
        }

        public Criteria andBuyFromIsNotNull() {
            addCriterion("buy_from is not null");
            return (Criteria) this;
        }

        public Criteria andBuyFromEqualTo(Integer value) {
            addCriterion("buy_from =", value, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromNotEqualTo(Integer value) {
            addCriterion("buy_from <>", value, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromGreaterThan(Integer value) {
            addCriterion("buy_from >", value, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("buy_from >=", value, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromLessThan(Integer value) {
            addCriterion("buy_from <", value, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromLessThanOrEqualTo(Integer value) {
            addCriterion("buy_from <=", value, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromIn(List<Integer> values) {
            addCriterion("buy_from in", values, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromNotIn(List<Integer> values) {
            addCriterion("buy_from not in", values, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromBetween(Integer value1, Integer value2) {
            addCriterion("buy_from between", value1, value2, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andBuyFromNotBetween(Integer value1, Integer value2) {
            addCriterion("buy_from not between", value1, value2, "buyFrom");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlIsNull() {
            addCriterion("pay_img_url is null");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlIsNotNull() {
            addCriterion("pay_img_url is not null");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlEqualTo(String value) {
            addCriterion("pay_img_url =", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlNotEqualTo(String value) {
            addCriterion("pay_img_url <>", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlGreaterThan(String value) {
            addCriterion("pay_img_url >", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("pay_img_url >=", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlLessThan(String value) {
            addCriterion("pay_img_url <", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlLessThanOrEqualTo(String value) {
            addCriterion("pay_img_url <=", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlLike(String value) {
            addCriterion("pay_img_url like", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlNotLike(String value) {
            addCriterion("pay_img_url not like", value, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlIn(List<String> values) {
            addCriterion("pay_img_url in", values, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlNotIn(List<String> values) {
            addCriterion("pay_img_url not in", values, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlBetween(String value1, String value2) {
            addCriterion("pay_img_url between", value1, value2, "payImgUrl");
            return (Criteria) this;
        }

        public Criteria andPayImgUrlNotBetween(String value1, String value2) {
            addCriterion("pay_img_url not between", value1, value2, "payImgUrl");
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