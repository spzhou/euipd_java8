/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.util.ArrayList;
import java.util.List;

public class MainShowMenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MainShowMenuExample() {
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

        public Criteria andVideoClassIsNull() {
            addCriterion("video_class is null");
            return (Criteria) this;
        }

        public Criteria andVideoClassIsNotNull() {
            addCriterion("video_class is not null");
            return (Criteria) this;
        }

        public Criteria andVideoClassEqualTo(String value) {
            addCriterion("video_class =", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassNotEqualTo(String value) {
            addCriterion("video_class <>", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassGreaterThan(String value) {
            addCriterion("video_class >", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassGreaterThanOrEqualTo(String value) {
            addCriterion("video_class >=", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassLessThan(String value) {
            addCriterion("video_class <", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassLessThanOrEqualTo(String value) {
            addCriterion("video_class <=", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassLike(String value) {
            addCriterion("video_class like", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassNotLike(String value) {
            addCriterion("video_class not like", value, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassIn(List<String> values) {
            addCriterion("video_class in", values, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassNotIn(List<String> values) {
            addCriterion("video_class not in", values, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassBetween(String value1, String value2) {
            addCriterion("video_class between", value1, value2, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoClassNotBetween(String value1, String value2) {
            addCriterion("video_class not between", value1, value2, "videoClass");
            return (Criteria) this;
        }

        public Criteria andVideoIdIsNull() {
            addCriterion("video_id is null");
            return (Criteria) this;
        }

        public Criteria andVideoIdIsNotNull() {
            addCriterion("video_id is not null");
            return (Criteria) this;
        }

        public Criteria andVideoIdEqualTo(String value) {
            addCriterion("video_id =", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotEqualTo(String value) {
            addCriterion("video_id <>", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdGreaterThan(String value) {
            addCriterion("video_id >", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdGreaterThanOrEqualTo(String value) {
            addCriterion("video_id >=", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdLessThan(String value) {
            addCriterion("video_id <", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdLessThanOrEqualTo(String value) {
            addCriterion("video_id <=", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdLike(String value) {
            addCriterion("video_id like", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotLike(String value) {
            addCriterion("video_id not like", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdIn(List<String> values) {
            addCriterion("video_id in", values, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotIn(List<String> values) {
            addCriterion("video_id not in", values, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdBetween(String value1, String value2) {
            addCriterion("video_id between", value1, value2, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotBetween(String value1, String value2) {
            addCriterion("video_id not between", value1, value2, "videoId");
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

        public Criteria andSubjectTitleIsNull() {
            addCriterion("subject_title is null");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleIsNotNull() {
            addCriterion("subject_title is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleEqualTo(String value) {
            addCriterion("subject_title =", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleNotEqualTo(String value) {
            addCriterion("subject_title <>", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleGreaterThan(String value) {
            addCriterion("subject_title >", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleGreaterThanOrEqualTo(String value) {
            addCriterion("subject_title >=", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleLessThan(String value) {
            addCriterion("subject_title <", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleLessThanOrEqualTo(String value) {
            addCriterion("subject_title <=", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleLike(String value) {
            addCriterion("subject_title like", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleNotLike(String value) {
            addCriterion("subject_title not like", value, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleIn(List<String> values) {
            addCriterion("subject_title in", values, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleNotIn(List<String> values) {
            addCriterion("subject_title not in", values, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleBetween(String value1, String value2) {
            addCriterion("subject_title between", value1, value2, "subjectTitle");
            return (Criteria) this;
        }

        public Criteria andSubjectTitleNotBetween(String value1, String value2) {
            addCriterion("subject_title not between", value1, value2, "subjectTitle");
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