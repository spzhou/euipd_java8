/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.ArrayList;
import java.util.List;

public class RdmsOaHrmUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsOaHrmUserExample() {
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

        public Criteria andCompanystartdateIsNull() {
            addCriterion("companystartdate is null");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateIsNotNull() {
            addCriterion("companystartdate is not null");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateEqualTo(String value) {
            addCriterion("companystartdate =", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateNotEqualTo(String value) {
            addCriterion("companystartdate <>", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateGreaterThan(String value) {
            addCriterion("companystartdate >", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateGreaterThanOrEqualTo(String value) {
            addCriterion("companystartdate >=", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateLessThan(String value) {
            addCriterion("companystartdate <", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateLessThanOrEqualTo(String value) {
            addCriterion("companystartdate <=", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateLike(String value) {
            addCriterion("companystartdate like", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateNotLike(String value) {
            addCriterion("companystartdate not like", value, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateIn(List<String> values) {
            addCriterion("companystartdate in", values, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateNotIn(List<String> values) {
            addCriterion("companystartdate not in", values, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateBetween(String value1, String value2) {
            addCriterion("companystartdate between", value1, value2, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andCompanystartdateNotBetween(String value1, String value2) {
            addCriterion("companystartdate not between", value1, value2, "companystartdate");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberIsNull() {
            addCriterion("tempresidentnumber is null");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberIsNotNull() {
            addCriterion("tempresidentnumber is not null");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberEqualTo(String value) {
            addCriterion("tempresidentnumber =", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberNotEqualTo(String value) {
            addCriterion("tempresidentnumber <>", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberGreaterThan(String value) {
            addCriterion("tempresidentnumber >", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberGreaterThanOrEqualTo(String value) {
            addCriterion("tempresidentnumber >=", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberLessThan(String value) {
            addCriterion("tempresidentnumber <", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberLessThanOrEqualTo(String value) {
            addCriterion("tempresidentnumber <=", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberLike(String value) {
            addCriterion("tempresidentnumber like", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberNotLike(String value) {
            addCriterion("tempresidentnumber not like", value, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberIn(List<String> values) {
            addCriterion("tempresidentnumber in", values, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberNotIn(List<String> values) {
            addCriterion("tempresidentnumber not in", values, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberBetween(String value1, String value2) {
            addCriterion("tempresidentnumber between", value1, value2, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andTempresidentnumberNotBetween(String value1, String value2) {
            addCriterion("tempresidentnumber not between", value1, value2, "tempresidentnumber");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("createdate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createdate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(String value) {
            addCriterion("createdate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(String value) {
            addCriterion("createdate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(String value) {
            addCriterion("createdate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(String value) {
            addCriterion("createdate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(String value) {
            addCriterion("createdate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(String value) {
            addCriterion("createdate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLike(String value) {
            addCriterion("createdate like", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotLike(String value) {
            addCriterion("createdate not like", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<String> values) {
            addCriterion("createdate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<String> values) {
            addCriterion("createdate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(String value1, String value2) {
            addCriterion("createdate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(String value1, String value2) {
            addCriterion("createdate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andLanguageIsNull() {
            addCriterion("`language` is null");
            return (Criteria) this;
        }

        public Criteria andLanguageIsNotNull() {
            addCriterion("`language` is not null");
            return (Criteria) this;
        }

        public Criteria andLanguageEqualTo(String value) {
            addCriterion("`language` =", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotEqualTo(String value) {
            addCriterion("`language` <>", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThan(String value) {
            addCriterion("`language` >", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThanOrEqualTo(String value) {
            addCriterion("`language` >=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThan(String value) {
            addCriterion("`language` <", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThanOrEqualTo(String value) {
            addCriterion("`language` <=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLike(String value) {
            addCriterion("`language` like", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotLike(String value) {
            addCriterion("`language` not like", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageIn(List<String> values) {
            addCriterion("`language` in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotIn(List<String> values) {
            addCriterion("`language` not in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageBetween(String value1, String value2) {
            addCriterion("`language` between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotBetween(String value1, String value2) {
            addCriterion("`language` not between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateIsNull() {
            addCriterion("workstartdate is null");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateIsNotNull() {
            addCriterion("workstartdate is not null");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateEqualTo(String value) {
            addCriterion("workstartdate =", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotEqualTo(String value) {
            addCriterion("workstartdate <>", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateGreaterThan(String value) {
            addCriterion("workstartdate >", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateGreaterThanOrEqualTo(String value) {
            addCriterion("workstartdate >=", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateLessThan(String value) {
            addCriterion("workstartdate <", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateLessThanOrEqualTo(String value) {
            addCriterion("workstartdate <=", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateLike(String value) {
            addCriterion("workstartdate like", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotLike(String value) {
            addCriterion("workstartdate not like", value, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateIn(List<String> values) {
            addCriterion("workstartdate in", values, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotIn(List<String> values) {
            addCriterion("workstartdate not in", values, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateBetween(String value1, String value2) {
            addCriterion("workstartdate between", value1, value2, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andWorkstartdateNotBetween(String value1, String value2) {
            addCriterion("workstartdate not between", value1, value2, "workstartdate");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1IsNull() {
            addCriterion("subcompanyid1 is null");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1IsNotNull() {
            addCriterion("subcompanyid1 is not null");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1EqualTo(String value) {
            addCriterion("subcompanyid1 =", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotEqualTo(String value) {
            addCriterion("subcompanyid1 <>", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1GreaterThan(String value) {
            addCriterion("subcompanyid1 >", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1GreaterThanOrEqualTo(String value) {
            addCriterion("subcompanyid1 >=", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1LessThan(String value) {
            addCriterion("subcompanyid1 <", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1LessThanOrEqualTo(String value) {
            addCriterion("subcompanyid1 <=", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1Like(String value) {
            addCriterion("subcompanyid1 like", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotLike(String value) {
            addCriterion("subcompanyid1 not like", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1In(List<String> values) {
            addCriterion("subcompanyid1 in", values, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotIn(List<String> values) {
            addCriterion("subcompanyid1 not in", values, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1Between(String value1, String value2) {
            addCriterion("subcompanyid1 between", value1, value2, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotBetween(String value1, String value2) {
            addCriterion("subcompanyid1 not between", value1, value2, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameIsNull() {
            addCriterion("subcompanyname is null");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameIsNotNull() {
            addCriterion("subcompanyname is not null");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameEqualTo(String value) {
            addCriterion("subcompanyname =", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameNotEqualTo(String value) {
            addCriterion("subcompanyname <>", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameGreaterThan(String value) {
            addCriterion("subcompanyname >", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameGreaterThanOrEqualTo(String value) {
            addCriterion("subcompanyname >=", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameLessThan(String value) {
            addCriterion("subcompanyname <", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameLessThanOrEqualTo(String value) {
            addCriterion("subcompanyname <=", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameLike(String value) {
            addCriterion("subcompanyname like", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameNotLike(String value) {
            addCriterion("subcompanyname not like", value, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameIn(List<String> values) {
            addCriterion("subcompanyname in", values, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameNotIn(List<String> values) {
            addCriterion("subcompanyname not in", values, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameBetween(String value1, String value2) {
            addCriterion("subcompanyname between", value1, value2, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andSubcompanynameNotBetween(String value1, String value2) {
            addCriterion("subcompanyname not between", value1, value2, "subcompanyname");
            return (Criteria) this;
        }

        public Criteria andJoblevelIsNull() {
            addCriterion("joblevel is null");
            return (Criteria) this;
        }

        public Criteria andJoblevelIsNotNull() {
            addCriterion("joblevel is not null");
            return (Criteria) this;
        }

        public Criteria andJoblevelEqualTo(String value) {
            addCriterion("joblevel =", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotEqualTo(String value) {
            addCriterion("joblevel <>", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelGreaterThan(String value) {
            addCriterion("joblevel >", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelGreaterThanOrEqualTo(String value) {
            addCriterion("joblevel >=", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelLessThan(String value) {
            addCriterion("joblevel <", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelLessThanOrEqualTo(String value) {
            addCriterion("joblevel <=", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelLike(String value) {
            addCriterion("joblevel like", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotLike(String value) {
            addCriterion("joblevel not like", value, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelIn(List<String> values) {
            addCriterion("joblevel in", values, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotIn(List<String> values) {
            addCriterion("joblevel not in", values, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelBetween(String value1, String value2) {
            addCriterion("joblevel between", value1, value2, "joblevel");
            return (Criteria) this;
        }

        public Criteria andJoblevelNotBetween(String value1, String value2) {
            addCriterion("joblevel not between", value1, value2, "joblevel");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNull() {
            addCriterion("startdate is null");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNotNull() {
            addCriterion("startdate is not null");
            return (Criteria) this;
        }

        public Criteria andStartdateEqualTo(String value) {
            addCriterion("startdate =", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotEqualTo(String value) {
            addCriterion("startdate <>", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThan(String value) {
            addCriterion("startdate >", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThanOrEqualTo(String value) {
            addCriterion("startdate >=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThan(String value) {
            addCriterion("startdate <", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThanOrEqualTo(String value) {
            addCriterion("startdate <=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLike(String value) {
            addCriterion("startdate like", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotLike(String value) {
            addCriterion("startdate not like", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateIn(List<String> values) {
            addCriterion("startdate in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotIn(List<String> values) {
            addCriterion("startdate not in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateBetween(String value1, String value2) {
            addCriterion("startdate between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotBetween(String value1, String value2) {
            addCriterion("startdate not between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("`password` is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("`password` is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("`password` =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("`password` <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("`password` >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("`password` >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("`password` <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("`password` <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("`password` like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("`password` not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("`password` in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("`password` not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("`password` between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("`password` not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andBelongtoidIsNull() {
            addCriterion("belongtoid is null");
            return (Criteria) this;
        }

        public Criteria andBelongtoidIsNotNull() {
            addCriterion("belongtoid is not null");
            return (Criteria) this;
        }

        public Criteria andBelongtoidEqualTo(String value) {
            addCriterion("belongtoid =", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidNotEqualTo(String value) {
            addCriterion("belongtoid <>", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidGreaterThan(String value) {
            addCriterion("belongtoid >", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidGreaterThanOrEqualTo(String value) {
            addCriterion("belongtoid >=", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidLessThan(String value) {
            addCriterion("belongtoid <", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidLessThanOrEqualTo(String value) {
            addCriterion("belongtoid <=", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidLike(String value) {
            addCriterion("belongtoid like", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidNotLike(String value) {
            addCriterion("belongtoid not like", value, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidIn(List<String> values) {
            addCriterion("belongtoid in", values, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidNotIn(List<String> values) {
            addCriterion("belongtoid not in", values, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidBetween(String value1, String value2) {
            addCriterion("belongtoid between", value1, value2, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andBelongtoidNotBetween(String value1, String value2) {
            addCriterion("belongtoid not between", value1, value2, "belongtoid");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeIsNull() {
            addCriterion("subcompanycode is null");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeIsNotNull() {
            addCriterion("subcompanycode is not null");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeEqualTo(String value) {
            addCriterion("subcompanycode =", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeNotEqualTo(String value) {
            addCriterion("subcompanycode <>", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeGreaterThan(String value) {
            addCriterion("subcompanycode >", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeGreaterThanOrEqualTo(String value) {
            addCriterion("subcompanycode >=", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeLessThan(String value) {
            addCriterion("subcompanycode <", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeLessThanOrEqualTo(String value) {
            addCriterion("subcompanycode <=", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeLike(String value) {
            addCriterion("subcompanycode like", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeNotLike(String value) {
            addCriterion("subcompanycode not like", value, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeIn(List<String> values) {
            addCriterion("subcompanycode in", values, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeNotIn(List<String> values) {
            addCriterion("subcompanycode not in", values, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeBetween(String value1, String value2) {
            addCriterion("subcompanycode between", value1, value2, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andSubcompanycodeNotBetween(String value1, String value2) {
            addCriterion("subcompanycode not between", value1, value2, "subcompanycode");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescIsNull() {
            addCriterion("jobactivitydesc is null");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescIsNotNull() {
            addCriterion("jobactivitydesc is not null");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescEqualTo(String value) {
            addCriterion("jobactivitydesc =", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescNotEqualTo(String value) {
            addCriterion("jobactivitydesc <>", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescGreaterThan(String value) {
            addCriterion("jobactivitydesc >", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescGreaterThanOrEqualTo(String value) {
            addCriterion("jobactivitydesc >=", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescLessThan(String value) {
            addCriterion("jobactivitydesc <", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescLessThanOrEqualTo(String value) {
            addCriterion("jobactivitydesc <=", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescLike(String value) {
            addCriterion("jobactivitydesc like", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescNotLike(String value) {
            addCriterion("jobactivitydesc not like", value, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescIn(List<String> values) {
            addCriterion("jobactivitydesc in", values, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescNotIn(List<String> values) {
            addCriterion("jobactivitydesc not in", values, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescBetween(String value1, String value2) {
            addCriterion("jobactivitydesc between", value1, value2, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andJobactivitydescNotBetween(String value1, String value2) {
            addCriterion("jobactivitydesc not between", value1, value2, "jobactivitydesc");
            return (Criteria) this;
        }

        public Criteria andBememberdateIsNull() {
            addCriterion("bememberdate is null");
            return (Criteria) this;
        }

        public Criteria andBememberdateIsNotNull() {
            addCriterion("bememberdate is not null");
            return (Criteria) this;
        }

        public Criteria andBememberdateEqualTo(String value) {
            addCriterion("bememberdate =", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateNotEqualTo(String value) {
            addCriterion("bememberdate <>", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateGreaterThan(String value) {
            addCriterion("bememberdate >", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateGreaterThanOrEqualTo(String value) {
            addCriterion("bememberdate >=", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateLessThan(String value) {
            addCriterion("bememberdate <", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateLessThanOrEqualTo(String value) {
            addCriterion("bememberdate <=", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateLike(String value) {
            addCriterion("bememberdate like", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateNotLike(String value) {
            addCriterion("bememberdate not like", value, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateIn(List<String> values) {
            addCriterion("bememberdate in", values, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateNotIn(List<String> values) {
            addCriterion("bememberdate not in", values, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateBetween(String value1, String value2) {
            addCriterion("bememberdate between", value1, value2, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andBememberdateNotBetween(String value1, String value2) {
            addCriterion("bememberdate not between", value1, value2, "bememberdate");
            return (Criteria) this;
        }

        public Criteria andModifiedIsNull() {
            addCriterion("modified is null");
            return (Criteria) this;
        }

        public Criteria andModifiedIsNotNull() {
            addCriterion("modified is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedEqualTo(String value) {
            addCriterion("modified =", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotEqualTo(String value) {
            addCriterion("modified <>", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedGreaterThan(String value) {
            addCriterion("modified >", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedGreaterThanOrEqualTo(String value) {
            addCriterion("modified >=", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedLessThan(String value) {
            addCriterion("modified <", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedLessThanOrEqualTo(String value) {
            addCriterion("modified <=", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedLike(String value) {
            addCriterion("modified like", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotLike(String value) {
            addCriterion("modified not like", value, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedIn(List<String> values) {
            addCriterion("modified in", values, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotIn(List<String> values) {
            addCriterion("modified not in", values, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedBetween(String value1, String value2) {
            addCriterion("modified between", value1, value2, "modified");
            return (Criteria) this;
        }

        public Criteria andModifiedNotBetween(String value1, String value2) {
            addCriterion("modified not between", value1, value2, "modified");
            return (Criteria) this;
        }

        public Criteria andMobilecallIsNull() {
            addCriterion("mobilecall is null");
            return (Criteria) this;
        }

        public Criteria andMobilecallIsNotNull() {
            addCriterion("mobilecall is not null");
            return (Criteria) this;
        }

        public Criteria andMobilecallEqualTo(String value) {
            addCriterion("mobilecall =", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallNotEqualTo(String value) {
            addCriterion("mobilecall <>", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallGreaterThan(String value) {
            addCriterion("mobilecall >", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallGreaterThanOrEqualTo(String value) {
            addCriterion("mobilecall >=", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallLessThan(String value) {
            addCriterion("mobilecall <", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallLessThanOrEqualTo(String value) {
            addCriterion("mobilecall <=", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallLike(String value) {
            addCriterion("mobilecall like", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallNotLike(String value) {
            addCriterion("mobilecall not like", value, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallIn(List<String> values) {
            addCriterion("mobilecall in", values, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallNotIn(List<String> values) {
            addCriterion("mobilecall not in", values, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallBetween(String value1, String value2) {
            addCriterion("mobilecall between", value1, value2, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andMobilecallNotBetween(String value1, String value2) {
            addCriterion("mobilecall not between", value1, value2, "mobilecall");
            return (Criteria) this;
        }

        public Criteria andNativeplaceIsNull() {
            addCriterion("nativeplace is null");
            return (Criteria) this;
        }

        public Criteria andNativeplaceIsNotNull() {
            addCriterion("nativeplace is not null");
            return (Criteria) this;
        }

        public Criteria andNativeplaceEqualTo(String value) {
            addCriterion("nativeplace =", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceNotEqualTo(String value) {
            addCriterion("nativeplace <>", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceGreaterThan(String value) {
            addCriterion("nativeplace >", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceGreaterThanOrEqualTo(String value) {
            addCriterion("nativeplace >=", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceLessThan(String value) {
            addCriterion("nativeplace <", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceLessThanOrEqualTo(String value) {
            addCriterion("nativeplace <=", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceLike(String value) {
            addCriterion("nativeplace like", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceNotLike(String value) {
            addCriterion("nativeplace not like", value, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceIn(List<String> values) {
            addCriterion("nativeplace in", values, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceNotIn(List<String> values) {
            addCriterion("nativeplace not in", values, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceBetween(String value1, String value2) {
            addCriterion("nativeplace between", value1, value2, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andNativeplaceNotBetween(String value1, String value2) {
            addCriterion("nativeplace not between", value1, value2, "nativeplace");
            return (Criteria) this;
        }

        public Criteria andCertificatenumIsNull() {
            addCriterion("certificatenum is null");
            return (Criteria) this;
        }

        public Criteria andCertificatenumIsNotNull() {
            addCriterion("certificatenum is not null");
            return (Criteria) this;
        }

        public Criteria andCertificatenumEqualTo(String value) {
            addCriterion("certificatenum =", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumNotEqualTo(String value) {
            addCriterion("certificatenum <>", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumGreaterThan(String value) {
            addCriterion("certificatenum >", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumGreaterThanOrEqualTo(String value) {
            addCriterion("certificatenum >=", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumLessThan(String value) {
            addCriterion("certificatenum <", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumLessThanOrEqualTo(String value) {
            addCriterion("certificatenum <=", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumLike(String value) {
            addCriterion("certificatenum like", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumNotLike(String value) {
            addCriterion("certificatenum not like", value, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumIn(List<String> values) {
            addCriterion("certificatenum in", values, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumNotIn(List<String> values) {
            addCriterion("certificatenum not in", values, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumBetween(String value1, String value2) {
            addCriterion("certificatenum between", value1, value2, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andCertificatenumNotBetween(String value1, String value2) {
            addCriterion("certificatenum not between", value1, value2, "certificatenum");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(String value) {
            addCriterion("height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(String value) {
            addCriterion("height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(String value) {
            addCriterion("height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(String value) {
            addCriterion("height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(String value) {
            addCriterion("height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(String value) {
            addCriterion("height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLike(String value) {
            addCriterion("height like", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotLike(String value) {
            addCriterion("height not like", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<String> values) {
            addCriterion("height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<String> values) {
            addCriterion("height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(String value1, String value2) {
            addCriterion("height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(String value1, String value2) {
            addCriterion("height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andLoginidIsNull() {
            addCriterion("loginid is null");
            return (Criteria) this;
        }

        public Criteria andLoginidIsNotNull() {
            addCriterion("loginid is not null");
            return (Criteria) this;
        }

        public Criteria andLoginidEqualTo(String value) {
            addCriterion("loginid =", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotEqualTo(String value) {
            addCriterion("loginid <>", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidGreaterThan(String value) {
            addCriterion("loginid >", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidGreaterThanOrEqualTo(String value) {
            addCriterion("loginid >=", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidLessThan(String value) {
            addCriterion("loginid <", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidLessThanOrEqualTo(String value) {
            addCriterion("loginid <=", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidLike(String value) {
            addCriterion("loginid like", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotLike(String value) {
            addCriterion("loginid not like", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidIn(List<String> values) {
            addCriterion("loginid in", values, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotIn(List<String> values) {
            addCriterion("loginid not in", values, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidBetween(String value1, String value2) {
            addCriterion("loginid between", value1, value2, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotBetween(String value1, String value2) {
            addCriterion("loginid not between", value1, value2, "loginid");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNull() {
            addCriterion("created is null");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNotNull() {
            addCriterion("created is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedEqualTo(String value) {
            addCriterion("created =", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotEqualTo(String value) {
            addCriterion("created <>", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThan(String value) {
            addCriterion("created >", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThanOrEqualTo(String value) {
            addCriterion("created >=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThan(String value) {
            addCriterion("created <", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThanOrEqualTo(String value) {
            addCriterion("created <=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLike(String value) {
            addCriterion("created like", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotLike(String value) {
            addCriterion("created not like", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedIn(List<String> values) {
            addCriterion("created in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotIn(List<String> values) {
            addCriterion("created not in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedBetween(String value1, String value2) {
            addCriterion("created between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotBetween(String value1, String value2) {
            addCriterion("created not between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("`degree` is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("`degree` is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("`degree` =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("`degree` <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("`degree` >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("`degree` >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("`degree` <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("`degree` <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("`degree` like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("`degree` not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("`degree` in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("`degree` not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("`degree` between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("`degree` not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andBepartydateIsNull() {
            addCriterion("bepartydate is null");
            return (Criteria) this;
        }

        public Criteria andBepartydateIsNotNull() {
            addCriterion("bepartydate is not null");
            return (Criteria) this;
        }

        public Criteria andBepartydateEqualTo(String value) {
            addCriterion("bepartydate =", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateNotEqualTo(String value) {
            addCriterion("bepartydate <>", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateGreaterThan(String value) {
            addCriterion("bepartydate >", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateGreaterThanOrEqualTo(String value) {
            addCriterion("bepartydate >=", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateLessThan(String value) {
            addCriterion("bepartydate <", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateLessThanOrEqualTo(String value) {
            addCriterion("bepartydate <=", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateLike(String value) {
            addCriterion("bepartydate like", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateNotLike(String value) {
            addCriterion("bepartydate not like", value, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateIn(List<String> values) {
            addCriterion("bepartydate in", values, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateNotIn(List<String> values) {
            addCriterion("bepartydate not in", values, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateBetween(String value1, String value2) {
            addCriterion("bepartydate between", value1, value2, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andBepartydateNotBetween(String value1, String value2) {
            addCriterion("bepartydate not between", value1, value2, "bepartydate");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(String value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(String value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(String value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(String value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(String value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(String value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLike(String value) {
            addCriterion("weight like", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotLike(String value) {
            addCriterion("weight not like", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<String> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<String> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(String value1, String value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(String value1, String value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNull() {
            addCriterion("telephone is null");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNotNull() {
            addCriterion("telephone is not null");
            return (Criteria) this;
        }

        public Criteria andTelephoneEqualTo(String value) {
            addCriterion("telephone =", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotEqualTo(String value) {
            addCriterion("telephone <>", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThan(String value) {
            addCriterion("telephone >", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("telephone >=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThan(String value) {
            addCriterion("telephone <", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThanOrEqualTo(String value) {
            addCriterion("telephone <=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLike(String value) {
            addCriterion("telephone like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotLike(String value) {
            addCriterion("telephone not like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneIn(List<String> values) {
            addCriterion("telephone in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotIn(List<String> values) {
            addCriterion("telephone not in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneBetween(String value1, String value2) {
            addCriterion("telephone between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotBetween(String value1, String value2) {
            addCriterion("telephone not between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andClassificationIsNull() {
            addCriterion("classification is null");
            return (Criteria) this;
        }

        public Criteria andClassificationIsNotNull() {
            addCriterion("classification is not null");
            return (Criteria) this;
        }

        public Criteria andClassificationEqualTo(String value) {
            addCriterion("classification =", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationNotEqualTo(String value) {
            addCriterion("classification <>", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationGreaterThan(String value) {
            addCriterion("classification >", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationGreaterThanOrEqualTo(String value) {
            addCriterion("classification >=", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationLessThan(String value) {
            addCriterion("classification <", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationLessThanOrEqualTo(String value) {
            addCriterion("classification <=", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationLike(String value) {
            addCriterion("classification like", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationNotLike(String value) {
            addCriterion("classification not like", value, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationIn(List<String> values) {
            addCriterion("classification in", values, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationNotIn(List<String> values) {
            addCriterion("classification not in", values, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationBetween(String value1, String value2) {
            addCriterion("classification between", value1, value2, "classification");
            return (Criteria) this;
        }

        public Criteria andClassificationNotBetween(String value1, String value2) {
            addCriterion("classification not between", value1, value2, "classification");
            return (Criteria) this;
        }

        public Criteria andResidentplaceIsNull() {
            addCriterion("residentplace is null");
            return (Criteria) this;
        }

        public Criteria andResidentplaceIsNotNull() {
            addCriterion("residentplace is not null");
            return (Criteria) this;
        }

        public Criteria andResidentplaceEqualTo(String value) {
            addCriterion("residentplace =", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceNotEqualTo(String value) {
            addCriterion("residentplace <>", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceGreaterThan(String value) {
            addCriterion("residentplace >", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceGreaterThanOrEqualTo(String value) {
            addCriterion("residentplace >=", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceLessThan(String value) {
            addCriterion("residentplace <", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceLessThanOrEqualTo(String value) {
            addCriterion("residentplace <=", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceLike(String value) {
            addCriterion("residentplace like", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceNotLike(String value) {
            addCriterion("residentplace not like", value, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceIn(List<String> values) {
            addCriterion("residentplace in", values, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceNotIn(List<String> values) {
            addCriterion("residentplace not in", values, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceBetween(String value1, String value2) {
            addCriterion("residentplace between", value1, value2, "residentplace");
            return (Criteria) this;
        }

        public Criteria andResidentplaceNotBetween(String value1, String value2) {
            addCriterion("residentplace not between", value1, value2, "residentplace");
            return (Criteria) this;
        }

        public Criteria andLastnameIsNull() {
            addCriterion("lastname is null");
            return (Criteria) this;
        }

        public Criteria andLastnameIsNotNull() {
            addCriterion("lastname is not null");
            return (Criteria) this;
        }

        public Criteria andLastnameEqualTo(String value) {
            addCriterion("lastname =", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameNotEqualTo(String value) {
            addCriterion("lastname <>", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameGreaterThan(String value) {
            addCriterion("lastname >", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameGreaterThanOrEqualTo(String value) {
            addCriterion("lastname >=", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameLessThan(String value) {
            addCriterion("lastname <", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameLessThanOrEqualTo(String value) {
            addCriterion("lastname <=", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameLike(String value) {
            addCriterion("lastname like", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameNotLike(String value) {
            addCriterion("lastname not like", value, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameIn(List<String> values) {
            addCriterion("lastname in", values, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameNotIn(List<String> values) {
            addCriterion("lastname not in", values, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameBetween(String value1, String value2) {
            addCriterion("lastname between", value1, value2, "lastname");
            return (Criteria) this;
        }

        public Criteria andLastnameNotBetween(String value1, String value2) {
            addCriterion("lastname not between", value1, value2, "lastname");
            return (Criteria) this;
        }

        public Criteria andHealthinfoIsNull() {
            addCriterion("healthinfo is null");
            return (Criteria) this;
        }

        public Criteria andHealthinfoIsNotNull() {
            addCriterion("healthinfo is not null");
            return (Criteria) this;
        }

        public Criteria andHealthinfoEqualTo(String value) {
            addCriterion("healthinfo =", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoNotEqualTo(String value) {
            addCriterion("healthinfo <>", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoGreaterThan(String value) {
            addCriterion("healthinfo >", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoGreaterThanOrEqualTo(String value) {
            addCriterion("healthinfo >=", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoLessThan(String value) {
            addCriterion("healthinfo <", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoLessThanOrEqualTo(String value) {
            addCriterion("healthinfo <=", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoLike(String value) {
            addCriterion("healthinfo like", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoNotLike(String value) {
            addCriterion("healthinfo not like", value, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoIn(List<String> values) {
            addCriterion("healthinfo in", values, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoNotIn(List<String> values) {
            addCriterion("healthinfo not in", values, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoBetween(String value1, String value2) {
            addCriterion("healthinfo between", value1, value2, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andHealthinfoNotBetween(String value1, String value2) {
            addCriterion("healthinfo not between", value1, value2, "healthinfo");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNull() {
            addCriterion("enddate is null");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNotNull() {
            addCriterion("enddate is not null");
            return (Criteria) this;
        }

        public Criteria andEnddateEqualTo(String value) {
            addCriterion("enddate =", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotEqualTo(String value) {
            addCriterion("enddate <>", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThan(String value) {
            addCriterion("enddate >", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThanOrEqualTo(String value) {
            addCriterion("enddate >=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThan(String value) {
            addCriterion("enddate <", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThanOrEqualTo(String value) {
            addCriterion("enddate <=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLike(String value) {
            addCriterion("enddate like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotLike(String value) {
            addCriterion("enddate not like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateIn(List<String> values) {
            addCriterion("enddate in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotIn(List<String> values) {
            addCriterion("enddate not in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateBetween(String value1, String value2) {
            addCriterion("enddate between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotBetween(String value1, String value2) {
            addCriterion("enddate not between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusIsNull() {
            addCriterion("maritalstatus is null");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusIsNotNull() {
            addCriterion("maritalstatus is not null");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusEqualTo(String value) {
            addCriterion("maritalstatus =", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotEqualTo(String value) {
            addCriterion("maritalstatus <>", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusGreaterThan(String value) {
            addCriterion("maritalstatus >", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusGreaterThanOrEqualTo(String value) {
            addCriterion("maritalstatus >=", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusLessThan(String value) {
            addCriterion("maritalstatus <", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusLessThanOrEqualTo(String value) {
            addCriterion("maritalstatus <=", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusLike(String value) {
            addCriterion("maritalstatus like", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotLike(String value) {
            addCriterion("maritalstatus not like", value, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusIn(List<String> values) {
            addCriterion("maritalstatus in", values, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotIn(List<String> values) {
            addCriterion("maritalstatus not in", values, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusBetween(String value1, String value2) {
            addCriterion("maritalstatus between", value1, value2, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andMaritalstatusNotBetween(String value1, String value2) {
            addCriterion("maritalstatus not between", value1, value2, "maritalstatus");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIsNull() {
            addCriterion("departmentname is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIsNotNull() {
            addCriterion("departmentname is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameEqualTo(String value) {
            addCriterion("departmentname =", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotEqualTo(String value) {
            addCriterion("departmentname <>", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameGreaterThan(String value) {
            addCriterion("departmentname >", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameGreaterThanOrEqualTo(String value) {
            addCriterion("departmentname >=", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLessThan(String value) {
            addCriterion("departmentname <", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLessThanOrEqualTo(String value) {
            addCriterion("departmentname <=", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLike(String value) {
            addCriterion("departmentname like", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotLike(String value) {
            addCriterion("departmentname not like", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIn(List<String> values) {
            addCriterion("departmentname in", values, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotIn(List<String> values) {
            addCriterion("departmentname not in", values, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameBetween(String value1, String value2) {
            addCriterion("departmentname between", value1, value2, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotBetween(String value1, String value2) {
            addCriterion("departmentname not between", value1, value2, "departmentname");
            return (Criteria) this;
        }

        public Criteria andFolkIsNull() {
            addCriterion("folk is null");
            return (Criteria) this;
        }

        public Criteria andFolkIsNotNull() {
            addCriterion("folk is not null");
            return (Criteria) this;
        }

        public Criteria andFolkEqualTo(String value) {
            addCriterion("folk =", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkNotEqualTo(String value) {
            addCriterion("folk <>", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkGreaterThan(String value) {
            addCriterion("folk >", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkGreaterThanOrEqualTo(String value) {
            addCriterion("folk >=", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkLessThan(String value) {
            addCriterion("folk <", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkLessThanOrEqualTo(String value) {
            addCriterion("folk <=", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkLike(String value) {
            addCriterion("folk like", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkNotLike(String value) {
            addCriterion("folk not like", value, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkIn(List<String> values) {
            addCriterion("folk in", values, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkNotIn(List<String> values) {
            addCriterion("folk not in", values, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkBetween(String value1, String value2) {
            addCriterion("folk between", value1, value2, "folk");
            return (Criteria) this;
        }

        public Criteria andFolkNotBetween(String value1, String value2) {
            addCriterion("folk not between", value1, value2, "folk");
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

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(String value) {
            addCriterion("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(String value) {
            addCriterion("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(String value) {
            addCriterion("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(String value) {
            addCriterion("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(String value) {
            addCriterion("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(String value) {
            addCriterion("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLike(String value) {
            addCriterion("birthday like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotLike(String value) {
            addCriterion("birthday not like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<String> values) {
            addCriterion("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<String> values) {
            addCriterion("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(String value1, String value2) {
            addCriterion("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(String value1, String value2) {
            addCriterion("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andIsadaccountIsNull() {
            addCriterion("isadaccount is null");
            return (Criteria) this;
        }

        public Criteria andIsadaccountIsNotNull() {
            addCriterion("isadaccount is not null");
            return (Criteria) this;
        }

        public Criteria andIsadaccountEqualTo(String value) {
            addCriterion("isadaccount =", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountNotEqualTo(String value) {
            addCriterion("isadaccount <>", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountGreaterThan(String value) {
            addCriterion("isadaccount >", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountGreaterThanOrEqualTo(String value) {
            addCriterion("isadaccount >=", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountLessThan(String value) {
            addCriterion("isadaccount <", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountLessThanOrEqualTo(String value) {
            addCriterion("isadaccount <=", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountLike(String value) {
            addCriterion("isadaccount like", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountNotLike(String value) {
            addCriterion("isadaccount not like", value, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountIn(List<String> values) {
            addCriterion("isadaccount in", values, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountNotIn(List<String> values) {
            addCriterion("isadaccount not in", values, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountBetween(String value1, String value2) {
            addCriterion("isadaccount between", value1, value2, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andIsadaccountNotBetween(String value1, String value2) {
            addCriterion("isadaccount not between", value1, value2, "isadaccount");
            return (Criteria) this;
        }

        public Criteria andAccounttypeIsNull() {
            addCriterion("accounttype is null");
            return (Criteria) this;
        }

        public Criteria andAccounttypeIsNotNull() {
            addCriterion("accounttype is not null");
            return (Criteria) this;
        }

        public Criteria andAccounttypeEqualTo(String value) {
            addCriterion("accounttype =", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeNotEqualTo(String value) {
            addCriterion("accounttype <>", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeGreaterThan(String value) {
            addCriterion("accounttype >", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeGreaterThanOrEqualTo(String value) {
            addCriterion("accounttype >=", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeLessThan(String value) {
            addCriterion("accounttype <", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeLessThanOrEqualTo(String value) {
            addCriterion("accounttype <=", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeLike(String value) {
            addCriterion("accounttype like", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeNotLike(String value) {
            addCriterion("accounttype not like", value, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeIn(List<String> values) {
            addCriterion("accounttype in", values, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeNotIn(List<String> values) {
            addCriterion("accounttype not in", values, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeBetween(String value1, String value2) {
            addCriterion("accounttype between", value1, value2, "accounttype");
            return (Criteria) this;
        }

        public Criteria andAccounttypeNotBetween(String value1, String value2) {
            addCriterion("accounttype not between", value1, value2, "accounttype");
            return (Criteria) this;
        }

        public Criteria andTextfield1IsNull() {
            addCriterion("textfield1 is null");
            return (Criteria) this;
        }

        public Criteria andTextfield1IsNotNull() {
            addCriterion("textfield1 is not null");
            return (Criteria) this;
        }

        public Criteria andTextfield1EqualTo(String value) {
            addCriterion("textfield1 =", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1NotEqualTo(String value) {
            addCriterion("textfield1 <>", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1GreaterThan(String value) {
            addCriterion("textfield1 >", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1GreaterThanOrEqualTo(String value) {
            addCriterion("textfield1 >=", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1LessThan(String value) {
            addCriterion("textfield1 <", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1LessThanOrEqualTo(String value) {
            addCriterion("textfield1 <=", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1Like(String value) {
            addCriterion("textfield1 like", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1NotLike(String value) {
            addCriterion("textfield1 not like", value, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1In(List<String> values) {
            addCriterion("textfield1 in", values, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1NotIn(List<String> values) {
            addCriterion("textfield1 not in", values, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1Between(String value1, String value2) {
            addCriterion("textfield1 between", value1, value2, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield1NotBetween(String value1, String value2) {
            addCriterion("textfield1 not between", value1, value2, "textfield1");
            return (Criteria) this;
        }

        public Criteria andTextfield2IsNull() {
            addCriterion("textfield2 is null");
            return (Criteria) this;
        }

        public Criteria andTextfield2IsNotNull() {
            addCriterion("textfield2 is not null");
            return (Criteria) this;
        }

        public Criteria andTextfield2EqualTo(String value) {
            addCriterion("textfield2 =", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2NotEqualTo(String value) {
            addCriterion("textfield2 <>", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2GreaterThan(String value) {
            addCriterion("textfield2 >", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2GreaterThanOrEqualTo(String value) {
            addCriterion("textfield2 >=", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2LessThan(String value) {
            addCriterion("textfield2 <", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2LessThanOrEqualTo(String value) {
            addCriterion("textfield2 <=", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2Like(String value) {
            addCriterion("textfield2 like", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2NotLike(String value) {
            addCriterion("textfield2 not like", value, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2In(List<String> values) {
            addCriterion("textfield2 in", values, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2NotIn(List<String> values) {
            addCriterion("textfield2 not in", values, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2Between(String value1, String value2) {
            addCriterion("textfield2 between", value1, value2, "textfield2");
            return (Criteria) this;
        }

        public Criteria andTextfield2NotBetween(String value1, String value2) {
            addCriterion("textfield2 not between", value1, value2, "textfield2");
            return (Criteria) this;
        }

        public Criteria andJobcallIsNull() {
            addCriterion("jobcall is null");
            return (Criteria) this;
        }

        public Criteria andJobcallIsNotNull() {
            addCriterion("jobcall is not null");
            return (Criteria) this;
        }

        public Criteria andJobcallEqualTo(String value) {
            addCriterion("jobcall =", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallNotEqualTo(String value) {
            addCriterion("jobcall <>", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallGreaterThan(String value) {
            addCriterion("jobcall >", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallGreaterThanOrEqualTo(String value) {
            addCriterion("jobcall >=", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallLessThan(String value) {
            addCriterion("jobcall <", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallLessThanOrEqualTo(String value) {
            addCriterion("jobcall <=", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallLike(String value) {
            addCriterion("jobcall like", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallNotLike(String value) {
            addCriterion("jobcall not like", value, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallIn(List<String> values) {
            addCriterion("jobcall in", values, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallNotIn(List<String> values) {
            addCriterion("jobcall not in", values, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallBetween(String value1, String value2) {
            addCriterion("jobcall between", value1, value2, "jobcall");
            return (Criteria) this;
        }

        public Criteria andJobcallNotBetween(String value1, String value2) {
            addCriterion("jobcall not between", value1, value2, "jobcall");
            return (Criteria) this;
        }

        public Criteria andManageridIsNull() {
            addCriterion("managerid is null");
            return (Criteria) this;
        }

        public Criteria andManageridIsNotNull() {
            addCriterion("managerid is not null");
            return (Criteria) this;
        }

        public Criteria andManageridEqualTo(String value) {
            addCriterion("managerid =", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridNotEqualTo(String value) {
            addCriterion("managerid <>", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridGreaterThan(String value) {
            addCriterion("managerid >", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridGreaterThanOrEqualTo(String value) {
            addCriterion("managerid >=", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridLessThan(String value) {
            addCriterion("managerid <", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridLessThanOrEqualTo(String value) {
            addCriterion("managerid <=", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridLike(String value) {
            addCriterion("managerid like", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridNotLike(String value) {
            addCriterion("managerid not like", value, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridIn(List<String> values) {
            addCriterion("managerid in", values, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridNotIn(List<String> values) {
            addCriterion("managerid not in", values, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridBetween(String value1, String value2) {
            addCriterion("managerid between", value1, value2, "managerid");
            return (Criteria) this;
        }

        public Criteria andManageridNotBetween(String value1, String value2) {
            addCriterion("managerid not between", value1, value2, "managerid");
            return (Criteria) this;
        }

        public Criteria andAssistantidIsNull() {
            addCriterion("assistantid is null");
            return (Criteria) this;
        }

        public Criteria andAssistantidIsNotNull() {
            addCriterion("assistantid is not null");
            return (Criteria) this;
        }

        public Criteria andAssistantidEqualTo(String value) {
            addCriterion("assistantid =", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidNotEqualTo(String value) {
            addCriterion("assistantid <>", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidGreaterThan(String value) {
            addCriterion("assistantid >", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidGreaterThanOrEqualTo(String value) {
            addCriterion("assistantid >=", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidLessThan(String value) {
            addCriterion("assistantid <", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidLessThanOrEqualTo(String value) {
            addCriterion("assistantid <=", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidLike(String value) {
            addCriterion("assistantid like", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidNotLike(String value) {
            addCriterion("assistantid not like", value, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidIn(List<String> values) {
            addCriterion("assistantid in", values, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidNotIn(List<String> values) {
            addCriterion("assistantid not in", values, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidBetween(String value1, String value2) {
            addCriterion("assistantid between", value1, value2, "assistantid");
            return (Criteria) this;
        }

        public Criteria andAssistantidNotBetween(String value1, String value2) {
            addCriterion("assistantid not between", value1, value2, "assistantid");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeIsNull() {
            addCriterion("departmentcode is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeIsNotNull() {
            addCriterion("departmentcode is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeEqualTo(String value) {
            addCriterion("departmentcode =", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeNotEqualTo(String value) {
            addCriterion("departmentcode <>", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeGreaterThan(String value) {
            addCriterion("departmentcode >", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeGreaterThanOrEqualTo(String value) {
            addCriterion("departmentcode >=", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeLessThan(String value) {
            addCriterion("departmentcode <", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeLessThanOrEqualTo(String value) {
            addCriterion("departmentcode <=", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeLike(String value) {
            addCriterion("departmentcode like", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeNotLike(String value) {
            addCriterion("departmentcode not like", value, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeIn(List<String> values) {
            addCriterion("departmentcode in", values, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeNotIn(List<String> values) {
            addCriterion("departmentcode not in", values, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeBetween(String value1, String value2) {
            addCriterion("departmentcode between", value1, value2, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andDepartmentcodeNotBetween(String value1, String value2) {
            addCriterion("departmentcode not between", value1, value2, "departmentcode");
            return (Criteria) this;
        }

        public Criteria andBelongtoIsNull() {
            addCriterion("belongto is null");
            return (Criteria) this;
        }

        public Criteria andBelongtoIsNotNull() {
            addCriterion("belongto is not null");
            return (Criteria) this;
        }

        public Criteria andBelongtoEqualTo(String value) {
            addCriterion("belongto =", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoNotEqualTo(String value) {
            addCriterion("belongto <>", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoGreaterThan(String value) {
            addCriterion("belongto >", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoGreaterThanOrEqualTo(String value) {
            addCriterion("belongto >=", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoLessThan(String value) {
            addCriterion("belongto <", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoLessThanOrEqualTo(String value) {
            addCriterion("belongto <=", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoLike(String value) {
            addCriterion("belongto like", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoNotLike(String value) {
            addCriterion("belongto not like", value, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoIn(List<String> values) {
            addCriterion("belongto in", values, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoNotIn(List<String> values) {
            addCriterion("belongto not in", values, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoBetween(String value1, String value2) {
            addCriterion("belongto between", value1, value2, "belongto");
            return (Criteria) this;
        }

        public Criteria andBelongtoNotBetween(String value1, String value2) {
            addCriterion("belongto not between", value1, value2, "belongto");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andSeclevelIsNull() {
            addCriterion("seclevel is null");
            return (Criteria) this;
        }

        public Criteria andSeclevelIsNotNull() {
            addCriterion("seclevel is not null");
            return (Criteria) this;
        }

        public Criteria andSeclevelEqualTo(String value) {
            addCriterion("seclevel =", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelNotEqualTo(String value) {
            addCriterion("seclevel <>", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelGreaterThan(String value) {
            addCriterion("seclevel >", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelGreaterThanOrEqualTo(String value) {
            addCriterion("seclevel >=", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelLessThan(String value) {
            addCriterion("seclevel <", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelLessThanOrEqualTo(String value) {
            addCriterion("seclevel <=", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelLike(String value) {
            addCriterion("seclevel like", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelNotLike(String value) {
            addCriterion("seclevel not like", value, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelIn(List<String> values) {
            addCriterion("seclevel in", values, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelNotIn(List<String> values) {
            addCriterion("seclevel not in", values, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelBetween(String value1, String value2) {
            addCriterion("seclevel between", value1, value2, "seclevel");
            return (Criteria) this;
        }

        public Criteria andSeclevelNotBetween(String value1, String value2) {
            addCriterion("seclevel not between", value1, value2, "seclevel");
            return (Criteria) this;
        }

        public Criteria andPolicyIsNull() {
            addCriterion("policy is null");
            return (Criteria) this;
        }

        public Criteria andPolicyIsNotNull() {
            addCriterion("policy is not null");
            return (Criteria) this;
        }

        public Criteria andPolicyEqualTo(String value) {
            addCriterion("policy =", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyNotEqualTo(String value) {
            addCriterion("policy <>", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyGreaterThan(String value) {
            addCriterion("policy >", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyGreaterThanOrEqualTo(String value) {
            addCriterion("policy >=", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyLessThan(String value) {
            addCriterion("policy <", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyLessThanOrEqualTo(String value) {
            addCriterion("policy <=", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyLike(String value) {
            addCriterion("policy like", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyNotLike(String value) {
            addCriterion("policy not like", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyIn(List<String> values) {
            addCriterion("policy in", values, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyNotIn(List<String> values) {
            addCriterion("policy not in", values, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyBetween(String value1, String value2) {
            addCriterion("policy between", value1, value2, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyNotBetween(String value1, String value2) {
            addCriterion("policy not between", value1, value2, "policy");
            return (Criteria) this;
        }

        public Criteria andJobtitleIsNull() {
            addCriterion("jobtitle is null");
            return (Criteria) this;
        }

        public Criteria andJobtitleIsNotNull() {
            addCriterion("jobtitle is not null");
            return (Criteria) this;
        }

        public Criteria andJobtitleEqualTo(String value) {
            addCriterion("jobtitle =", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotEqualTo(String value) {
            addCriterion("jobtitle <>", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleGreaterThan(String value) {
            addCriterion("jobtitle >", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleGreaterThanOrEqualTo(String value) {
            addCriterion("jobtitle >=", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleLessThan(String value) {
            addCriterion("jobtitle <", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleLessThanOrEqualTo(String value) {
            addCriterion("jobtitle <=", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleLike(String value) {
            addCriterion("jobtitle like", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotLike(String value) {
            addCriterion("jobtitle not like", value, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleIn(List<String> values) {
            addCriterion("jobtitle in", values, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotIn(List<String> values) {
            addCriterion("jobtitle not in", values, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleBetween(String value1, String value2) {
            addCriterion("jobtitle between", value1, value2, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andJobtitleNotBetween(String value1, String value2) {
            addCriterion("jobtitle not between", value1, value2, "jobtitle");
            return (Criteria) this;
        }

        public Criteria andWorkcodeIsNull() {
            addCriterion("workcode is null");
            return (Criteria) this;
        }

        public Criteria andWorkcodeIsNotNull() {
            addCriterion("workcode is not null");
            return (Criteria) this;
        }

        public Criteria andWorkcodeEqualTo(String value) {
            addCriterion("workcode =", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeNotEqualTo(String value) {
            addCriterion("workcode <>", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeGreaterThan(String value) {
            addCriterion("workcode >", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeGreaterThanOrEqualTo(String value) {
            addCriterion("workcode >=", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeLessThan(String value) {
            addCriterion("workcode <", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeLessThanOrEqualTo(String value) {
            addCriterion("workcode <=", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeLike(String value) {
            addCriterion("workcode like", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeNotLike(String value) {
            addCriterion("workcode not like", value, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeIn(List<String> values) {
            addCriterion("workcode in", values, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeNotIn(List<String> values) {
            addCriterion("workcode not in", values, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeBetween(String value1, String value2) {
            addCriterion("workcode between", value1, value2, "workcode");
            return (Criteria) this;
        }

        public Criteria andWorkcodeNotBetween(String value1, String value2) {
            addCriterion("workcode not between", value1, value2, "workcode");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNull() {
            addCriterion("departmentid is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNotNull() {
            addCriterion("departmentid is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidEqualTo(String value) {
            addCriterion("departmentid =", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotEqualTo(String value) {
            addCriterion("departmentid <>", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThan(String value) {
            addCriterion("departmentid >", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThanOrEqualTo(String value) {
            addCriterion("departmentid >=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThan(String value) {
            addCriterion("departmentid <", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThanOrEqualTo(String value) {
            addCriterion("departmentid <=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLike(String value) {
            addCriterion("departmentid like", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotLike(String value) {
            addCriterion("departmentid not like", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIn(List<String> values) {
            addCriterion("departmentid in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotIn(List<String> values) {
            addCriterion("departmentid not in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidBetween(String value1, String value2) {
            addCriterion("departmentid between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotBetween(String value1, String value2) {
            addCriterion("departmentid not between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andHomeaddressIsNull() {
            addCriterion("homeaddress is null");
            return (Criteria) this;
        }

        public Criteria andHomeaddressIsNotNull() {
            addCriterion("homeaddress is not null");
            return (Criteria) this;
        }

        public Criteria andHomeaddressEqualTo(String value) {
            addCriterion("homeaddress =", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressNotEqualTo(String value) {
            addCriterion("homeaddress <>", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressGreaterThan(String value) {
            addCriterion("homeaddress >", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressGreaterThanOrEqualTo(String value) {
            addCriterion("homeaddress >=", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressLessThan(String value) {
            addCriterion("homeaddress <", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressLessThanOrEqualTo(String value) {
            addCriterion("homeaddress <=", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressLike(String value) {
            addCriterion("homeaddress like", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressNotLike(String value) {
            addCriterion("homeaddress not like", value, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressIn(List<String> values) {
            addCriterion("homeaddress in", values, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressNotIn(List<String> values) {
            addCriterion("homeaddress not in", values, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressBetween(String value1, String value2) {
            addCriterion("homeaddress between", value1, value2, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andHomeaddressNotBetween(String value1, String value2) {
            addCriterion("homeaddress not between", value1, value2, "homeaddress");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andLastmoddateIsNull() {
            addCriterion("lastmoddate is null");
            return (Criteria) this;
        }

        public Criteria andLastmoddateIsNotNull() {
            addCriterion("lastmoddate is not null");
            return (Criteria) this;
        }

        public Criteria andLastmoddateEqualTo(String value) {
            addCriterion("lastmoddate =", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateNotEqualTo(String value) {
            addCriterion("lastmoddate <>", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateGreaterThan(String value) {
            addCriterion("lastmoddate >", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateGreaterThanOrEqualTo(String value) {
            addCriterion("lastmoddate >=", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateLessThan(String value) {
            addCriterion("lastmoddate <", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateLessThanOrEqualTo(String value) {
            addCriterion("lastmoddate <=", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateLike(String value) {
            addCriterion("lastmoddate like", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateNotLike(String value) {
            addCriterion("lastmoddate not like", value, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateIn(List<String> values) {
            addCriterion("lastmoddate in", values, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateNotIn(List<String> values) {
            addCriterion("lastmoddate not in", values, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateBetween(String value1, String value2) {
            addCriterion("lastmoddate between", value1, value2, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andLastmoddateNotBetween(String value1, String value2) {
            addCriterion("lastmoddate not between", value1, value2, "lastmoddate");
            return (Criteria) this;
        }

        public Criteria andEducationlevelIsNull() {
            addCriterion("educationlevel is null");
            return (Criteria) this;
        }

        public Criteria andEducationlevelIsNotNull() {
            addCriterion("educationlevel is not null");
            return (Criteria) this;
        }

        public Criteria andEducationlevelEqualTo(String value) {
            addCriterion("educationlevel =", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelNotEqualTo(String value) {
            addCriterion("educationlevel <>", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelGreaterThan(String value) {
            addCriterion("educationlevel >", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelGreaterThanOrEqualTo(String value) {
            addCriterion("educationlevel >=", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelLessThan(String value) {
            addCriterion("educationlevel <", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelLessThanOrEqualTo(String value) {
            addCriterion("educationlevel <=", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelLike(String value) {
            addCriterion("educationlevel like", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelNotLike(String value) {
            addCriterion("educationlevel not like", value, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelIn(List<String> values) {
            addCriterion("educationlevel in", values, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelNotIn(List<String> values) {
            addCriterion("educationlevel not in", values, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelBetween(String value1, String value2) {
            addCriterion("educationlevel between", value1, value2, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andEducationlevelNotBetween(String value1, String value2) {
            addCriterion("educationlevel not between", value1, value2, "educationlevel");
            return (Criteria) this;
        }

        public Criteria andIslabouunionIsNull() {
            addCriterion("islabouunion is null");
            return (Criteria) this;
        }

        public Criteria andIslabouunionIsNotNull() {
            addCriterion("islabouunion is not null");
            return (Criteria) this;
        }

        public Criteria andIslabouunionEqualTo(String value) {
            addCriterion("islabouunion =", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionNotEqualTo(String value) {
            addCriterion("islabouunion <>", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionGreaterThan(String value) {
            addCriterion("islabouunion >", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionGreaterThanOrEqualTo(String value) {
            addCriterion("islabouunion >=", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionLessThan(String value) {
            addCriterion("islabouunion <", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionLessThanOrEqualTo(String value) {
            addCriterion("islabouunion <=", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionLike(String value) {
            addCriterion("islabouunion like", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionNotLike(String value) {
            addCriterion("islabouunion not like", value, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionIn(List<String> values) {
            addCriterion("islabouunion in", values, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionNotIn(List<String> values) {
            addCriterion("islabouunion not in", values, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionBetween(String value1, String value2) {
            addCriterion("islabouunion between", value1, value2, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andIslabouunionNotBetween(String value1, String value2) {
            addCriterion("islabouunion not between", value1, value2, "islabouunion");
            return (Criteria) this;
        }

        public Criteria andLocationidIsNull() {
            addCriterion("locationid is null");
            return (Criteria) this;
        }

        public Criteria andLocationidIsNotNull() {
            addCriterion("locationid is not null");
            return (Criteria) this;
        }

        public Criteria andLocationidEqualTo(String value) {
            addCriterion("locationid =", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidNotEqualTo(String value) {
            addCriterion("locationid <>", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidGreaterThan(String value) {
            addCriterion("locationid >", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidGreaterThanOrEqualTo(String value) {
            addCriterion("locationid >=", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidLessThan(String value) {
            addCriterion("locationid <", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidLessThanOrEqualTo(String value) {
            addCriterion("locationid <=", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidLike(String value) {
            addCriterion("locationid like", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidNotLike(String value) {
            addCriterion("locationid not like", value, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidIn(List<String> values) {
            addCriterion("locationid in", values, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidNotIn(List<String> values) {
            addCriterion("locationid not in", values, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidBetween(String value1, String value2) {
            addCriterion("locationid between", value1, value2, "locationid");
            return (Criteria) this;
        }

        public Criteria andLocationidNotBetween(String value1, String value2) {
            addCriterion("locationid not between", value1, value2, "locationid");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceIsNull() {
            addCriterion("regresidentplace is null");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceIsNotNull() {
            addCriterion("regresidentplace is not null");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceEqualTo(String value) {
            addCriterion("regresidentplace =", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceNotEqualTo(String value) {
            addCriterion("regresidentplace <>", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceGreaterThan(String value) {
            addCriterion("regresidentplace >", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceGreaterThanOrEqualTo(String value) {
            addCriterion("regresidentplace >=", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceLessThan(String value) {
            addCriterion("regresidentplace <", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceLessThanOrEqualTo(String value) {
            addCriterion("regresidentplace <=", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceLike(String value) {
            addCriterion("regresidentplace like", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceNotLike(String value) {
            addCriterion("regresidentplace not like", value, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceIn(List<String> values) {
            addCriterion("regresidentplace in", values, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceNotIn(List<String> values) {
            addCriterion("regresidentplace not in", values, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceBetween(String value1, String value2) {
            addCriterion("regresidentplace between", value1, value2, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andRegresidentplaceNotBetween(String value1, String value2) {
            addCriterion("regresidentplace not between", value1, value2, "regresidentplace");
            return (Criteria) this;
        }

        public Criteria andDsporderIsNull() {
            addCriterion("dsporder is null");
            return (Criteria) this;
        }

        public Criteria andDsporderIsNotNull() {
            addCriterion("dsporder is not null");
            return (Criteria) this;
        }

        public Criteria andDsporderEqualTo(String value) {
            addCriterion("dsporder =", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderNotEqualTo(String value) {
            addCriterion("dsporder <>", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderGreaterThan(String value) {
            addCriterion("dsporder >", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderGreaterThanOrEqualTo(String value) {
            addCriterion("dsporder >=", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderLessThan(String value) {
            addCriterion("dsporder <", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderLessThanOrEqualTo(String value) {
            addCriterion("dsporder <=", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderLike(String value) {
            addCriterion("dsporder like", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderNotLike(String value) {
            addCriterion("dsporder not like", value, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderIn(List<String> values) {
            addCriterion("dsporder in", values, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderNotIn(List<String> values) {
            addCriterion("dsporder not in", values, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderBetween(String value1, String value2) {
            addCriterion("dsporder between", value1, value2, "dsporder");
            return (Criteria) this;
        }

        public Criteria andDsporderNotBetween(String value1, String value2) {
            addCriterion("dsporder not between", value1, value2, "dsporder");
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