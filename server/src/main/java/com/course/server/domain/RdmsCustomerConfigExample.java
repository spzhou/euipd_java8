/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.ArrayList;
import java.util.List;

public class RdmsCustomerConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsCustomerConfigExample() {
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

        public Criteria andOssAccessKeyIsNull() {
            addCriterion("oss_access_key is null");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyIsNotNull() {
            addCriterion("oss_access_key is not null");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyEqualTo(String value) {
            addCriterion("oss_access_key =", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyNotEqualTo(String value) {
            addCriterion("oss_access_key <>", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyGreaterThan(String value) {
            addCriterion("oss_access_key >", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyGreaterThanOrEqualTo(String value) {
            addCriterion("oss_access_key >=", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyLessThan(String value) {
            addCriterion("oss_access_key <", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyLessThanOrEqualTo(String value) {
            addCriterion("oss_access_key <=", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyLike(String value) {
            addCriterion("oss_access_key like", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyNotLike(String value) {
            addCriterion("oss_access_key not like", value, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyIn(List<String> values) {
            addCriterion("oss_access_key in", values, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyNotIn(List<String> values) {
            addCriterion("oss_access_key not in", values, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyBetween(String value1, String value2) {
            addCriterion("oss_access_key between", value1, value2, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeyNotBetween(String value1, String value2) {
            addCriterion("oss_access_key not between", value1, value2, "ossAccessKey");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretIsNull() {
            addCriterion("oss_access_key_secret is null");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretIsNotNull() {
            addCriterion("oss_access_key_secret is not null");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretEqualTo(String value) {
            addCriterion("oss_access_key_secret =", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretNotEqualTo(String value) {
            addCriterion("oss_access_key_secret <>", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretGreaterThan(String value) {
            addCriterion("oss_access_key_secret >", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretGreaterThanOrEqualTo(String value) {
            addCriterion("oss_access_key_secret >=", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretLessThan(String value) {
            addCriterion("oss_access_key_secret <", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretLessThanOrEqualTo(String value) {
            addCriterion("oss_access_key_secret <=", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretLike(String value) {
            addCriterion("oss_access_key_secret like", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretNotLike(String value) {
            addCriterion("oss_access_key_secret not like", value, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretIn(List<String> values) {
            addCriterion("oss_access_key_secret in", values, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretNotIn(List<String> values) {
            addCriterion("oss_access_key_secret not in", values, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretBetween(String value1, String value2) {
            addCriterion("oss_access_key_secret between", value1, value2, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssAccessKeySecretNotBetween(String value1, String value2) {
            addCriterion("oss_access_key_secret not between", value1, value2, "ossAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andOssEndpointIsNull() {
            addCriterion("oss_endpoint is null");
            return (Criteria) this;
        }

        public Criteria andOssEndpointIsNotNull() {
            addCriterion("oss_endpoint is not null");
            return (Criteria) this;
        }

        public Criteria andOssEndpointEqualTo(String value) {
            addCriterion("oss_endpoint =", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointNotEqualTo(String value) {
            addCriterion("oss_endpoint <>", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointGreaterThan(String value) {
            addCriterion("oss_endpoint >", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointGreaterThanOrEqualTo(String value) {
            addCriterion("oss_endpoint >=", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointLessThan(String value) {
            addCriterion("oss_endpoint <", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointLessThanOrEqualTo(String value) {
            addCriterion("oss_endpoint <=", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointLike(String value) {
            addCriterion("oss_endpoint like", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointNotLike(String value) {
            addCriterion("oss_endpoint not like", value, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointIn(List<String> values) {
            addCriterion("oss_endpoint in", values, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointNotIn(List<String> values) {
            addCriterion("oss_endpoint not in", values, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointBetween(String value1, String value2) {
            addCriterion("oss_endpoint between", value1, value2, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointNotBetween(String value1, String value2) {
            addCriterion("oss_endpoint not between", value1, value2, "ossEndpoint");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsIsNull() {
            addCriterion("oss_endpoint_sts is null");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsIsNotNull() {
            addCriterion("oss_endpoint_sts is not null");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsEqualTo(String value) {
            addCriterion("oss_endpoint_sts =", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsNotEqualTo(String value) {
            addCriterion("oss_endpoint_sts <>", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsGreaterThan(String value) {
            addCriterion("oss_endpoint_sts >", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsGreaterThanOrEqualTo(String value) {
            addCriterion("oss_endpoint_sts >=", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsLessThan(String value) {
            addCriterion("oss_endpoint_sts <", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsLessThanOrEqualTo(String value) {
            addCriterion("oss_endpoint_sts <=", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsLike(String value) {
            addCriterion("oss_endpoint_sts like", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsNotLike(String value) {
            addCriterion("oss_endpoint_sts not like", value, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsIn(List<String> values) {
            addCriterion("oss_endpoint_sts in", values, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsNotIn(List<String> values) {
            addCriterion("oss_endpoint_sts not in", values, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsBetween(String value1, String value2) {
            addCriterion("oss_endpoint_sts between", value1, value2, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssEndpointStsNotBetween(String value1, String value2) {
            addCriterion("oss_endpoint_sts not between", value1, value2, "ossEndpointSts");
            return (Criteria) this;
        }

        public Criteria andOssBucketIsNull() {
            addCriterion("oss_bucket is null");
            return (Criteria) this;
        }

        public Criteria andOssBucketIsNotNull() {
            addCriterion("oss_bucket is not null");
            return (Criteria) this;
        }

        public Criteria andOssBucketEqualTo(String value) {
            addCriterion("oss_bucket =", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketNotEqualTo(String value) {
            addCriterion("oss_bucket <>", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketGreaterThan(String value) {
            addCriterion("oss_bucket >", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketGreaterThanOrEqualTo(String value) {
            addCriterion("oss_bucket >=", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketLessThan(String value) {
            addCriterion("oss_bucket <", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketLessThanOrEqualTo(String value) {
            addCriterion("oss_bucket <=", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketLike(String value) {
            addCriterion("oss_bucket like", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketNotLike(String value) {
            addCriterion("oss_bucket not like", value, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketIn(List<String> values) {
            addCriterion("oss_bucket in", values, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketNotIn(List<String> values) {
            addCriterion("oss_bucket not in", values, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketBetween(String value1, String value2) {
            addCriterion("oss_bucket between", value1, value2, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketNotBetween(String value1, String value2) {
            addCriterion("oss_bucket not between", value1, value2, "ossBucket");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebIsNull() {
            addCriterion("oss_bucket_web is null");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebIsNotNull() {
            addCriterion("oss_bucket_web is not null");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebEqualTo(String value) {
            addCriterion("oss_bucket_web =", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebNotEqualTo(String value) {
            addCriterion("oss_bucket_web <>", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebGreaterThan(String value) {
            addCriterion("oss_bucket_web >", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebGreaterThanOrEqualTo(String value) {
            addCriterion("oss_bucket_web >=", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebLessThan(String value) {
            addCriterion("oss_bucket_web <", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebLessThanOrEqualTo(String value) {
            addCriterion("oss_bucket_web <=", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebLike(String value) {
            addCriterion("oss_bucket_web like", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebNotLike(String value) {
            addCriterion("oss_bucket_web not like", value, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebIn(List<String> values) {
            addCriterion("oss_bucket_web in", values, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebNotIn(List<String> values) {
            addCriterion("oss_bucket_web not in", values, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebBetween(String value1, String value2) {
            addCriterion("oss_bucket_web between", value1, value2, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssBucketWebNotBetween(String value1, String value2) {
            addCriterion("oss_bucket_web not between", value1, value2, "ossBucketWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainIsNull() {
            addCriterion("oss_domain is null");
            return (Criteria) this;
        }

        public Criteria andOssDomainIsNotNull() {
            addCriterion("oss_domain is not null");
            return (Criteria) this;
        }

        public Criteria andOssDomainEqualTo(String value) {
            addCriterion("oss_domain =", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainNotEqualTo(String value) {
            addCriterion("oss_domain <>", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainGreaterThan(String value) {
            addCriterion("oss_domain >", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainGreaterThanOrEqualTo(String value) {
            addCriterion("oss_domain >=", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainLessThan(String value) {
            addCriterion("oss_domain <", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainLessThanOrEqualTo(String value) {
            addCriterion("oss_domain <=", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainLike(String value) {
            addCriterion("oss_domain like", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainNotLike(String value) {
            addCriterion("oss_domain not like", value, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainIn(List<String> values) {
            addCriterion("oss_domain in", values, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainNotIn(List<String> values) {
            addCriterion("oss_domain not in", values, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainBetween(String value1, String value2) {
            addCriterion("oss_domain between", value1, value2, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainNotBetween(String value1, String value2) {
            addCriterion("oss_domain not between", value1, value2, "ossDomain");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebIsNull() {
            addCriterion("oss_domain_web is null");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebIsNotNull() {
            addCriterion("oss_domain_web is not null");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebEqualTo(String value) {
            addCriterion("oss_domain_web =", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebNotEqualTo(String value) {
            addCriterion("oss_domain_web <>", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebGreaterThan(String value) {
            addCriterion("oss_domain_web >", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebGreaterThanOrEqualTo(String value) {
            addCriterion("oss_domain_web >=", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebLessThan(String value) {
            addCriterion("oss_domain_web <", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebLessThanOrEqualTo(String value) {
            addCriterion("oss_domain_web <=", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebLike(String value) {
            addCriterion("oss_domain_web like", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebNotLike(String value) {
            addCriterion("oss_domain_web not like", value, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebIn(List<String> values) {
            addCriterion("oss_domain_web in", values, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebNotIn(List<String> values) {
            addCriterion("oss_domain_web not in", values, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebBetween(String value1, String value2) {
            addCriterion("oss_domain_web between", value1, value2, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssDomainWebNotBetween(String value1, String value2) {
            addCriterion("oss_domain_web not between", value1, value2, "ossDomainWeb");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnIsNull() {
            addCriterion("oss_role_arn is null");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnIsNotNull() {
            addCriterion("oss_role_arn is not null");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnEqualTo(String value) {
            addCriterion("oss_role_arn =", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnNotEqualTo(String value) {
            addCriterion("oss_role_arn <>", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnGreaterThan(String value) {
            addCriterion("oss_role_arn >", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnGreaterThanOrEqualTo(String value) {
            addCriterion("oss_role_arn >=", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnLessThan(String value) {
            addCriterion("oss_role_arn <", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnLessThanOrEqualTo(String value) {
            addCriterion("oss_role_arn <=", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnLike(String value) {
            addCriterion("oss_role_arn like", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnNotLike(String value) {
            addCriterion("oss_role_arn not like", value, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnIn(List<String> values) {
            addCriterion("oss_role_arn in", values, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnNotIn(List<String> values) {
            addCriterion("oss_role_arn not in", values, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnBetween(String value1, String value2) {
            addCriterion("oss_role_arn between", value1, value2, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andOssRoleArnNotBetween(String value1, String value2) {
            addCriterion("oss_role_arn not between", value1, value2, "ossRoleArn");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyIsNull() {
            addCriterion("vod_access_key is null");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyIsNotNull() {
            addCriterion("vod_access_key is not null");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyEqualTo(String value) {
            addCriterion("vod_access_key =", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyNotEqualTo(String value) {
            addCriterion("vod_access_key <>", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyGreaterThan(String value) {
            addCriterion("vod_access_key >", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyGreaterThanOrEqualTo(String value) {
            addCriterion("vod_access_key >=", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyLessThan(String value) {
            addCriterion("vod_access_key <", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyLessThanOrEqualTo(String value) {
            addCriterion("vod_access_key <=", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyLike(String value) {
            addCriterion("vod_access_key like", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyNotLike(String value) {
            addCriterion("vod_access_key not like", value, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyIn(List<String> values) {
            addCriterion("vod_access_key in", values, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyNotIn(List<String> values) {
            addCriterion("vod_access_key not in", values, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyBetween(String value1, String value2) {
            addCriterion("vod_access_key between", value1, value2, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeyNotBetween(String value1, String value2) {
            addCriterion("vod_access_key not between", value1, value2, "vodAccessKey");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretIsNull() {
            addCriterion("vod_access_key_secret is null");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretIsNotNull() {
            addCriterion("vod_access_key_secret is not null");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretEqualTo(String value) {
            addCriterion("vod_access_key_secret =", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretNotEqualTo(String value) {
            addCriterion("vod_access_key_secret <>", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretGreaterThan(String value) {
            addCriterion("vod_access_key_secret >", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretGreaterThanOrEqualTo(String value) {
            addCriterion("vod_access_key_secret >=", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretLessThan(String value) {
            addCriterion("vod_access_key_secret <", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretLessThanOrEqualTo(String value) {
            addCriterion("vod_access_key_secret <=", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretLike(String value) {
            addCriterion("vod_access_key_secret like", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretNotLike(String value) {
            addCriterion("vod_access_key_secret not like", value, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretIn(List<String> values) {
            addCriterion("vod_access_key_secret in", values, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretNotIn(List<String> values) {
            addCriterion("vod_access_key_secret not in", values, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretBetween(String value1, String value2) {
            addCriterion("vod_access_key_secret between", value1, value2, "vodAccessKeySecret");
            return (Criteria) this;
        }

        public Criteria andVodAccessKeySecretNotBetween(String value1, String value2) {
            addCriterion("vod_access_key_secret not between", value1, value2, "vodAccessKeySecret");
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
