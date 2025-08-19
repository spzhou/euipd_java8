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

public class RdmsMilestoneExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsMilestoneExample() {
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

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("project_id like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("project_id not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameIsNull() {
            addCriterion("milestone_name is null");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameIsNotNull() {
            addCriterion("milestone_name is not null");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameEqualTo(String value) {
            addCriterion("milestone_name =", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotEqualTo(String value) {
            addCriterion("milestone_name <>", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameGreaterThan(String value) {
            addCriterion("milestone_name >", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameGreaterThanOrEqualTo(String value) {
            addCriterion("milestone_name >=", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameLessThan(String value) {
            addCriterion("milestone_name <", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameLessThanOrEqualTo(String value) {
            addCriterion("milestone_name <=", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameLike(String value) {
            addCriterion("milestone_name like", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotLike(String value) {
            addCriterion("milestone_name not like", value, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameIn(List<String> values) {
            addCriterion("milestone_name in", values, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotIn(List<String> values) {
            addCriterion("milestone_name not in", values, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameBetween(String value1, String value2) {
            addCriterion("milestone_name between", value1, value2, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andMilestoneNameNotBetween(String value1, String value2) {
            addCriterion("milestone_name not between", value1, value2, "milestoneName");
            return (Criteria) this;
        }

        public Criteria andCheckOutIsNull() {
            addCriterion("check_out is null");
            return (Criteria) this;
        }

        public Criteria andCheckOutIsNotNull() {
            addCriterion("check_out is not null");
            return (Criteria) this;
        }

        public Criteria andCheckOutEqualTo(String value) {
            addCriterion("check_out =", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutNotEqualTo(String value) {
            addCriterion("check_out <>", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutGreaterThan(String value) {
            addCriterion("check_out >", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutGreaterThanOrEqualTo(String value) {
            addCriterion("check_out >=", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutLessThan(String value) {
            addCriterion("check_out <", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutLessThanOrEqualTo(String value) {
            addCriterion("check_out <=", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutLike(String value) {
            addCriterion("check_out like", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutNotLike(String value) {
            addCriterion("check_out not like", value, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutIn(List<String> values) {
            addCriterion("check_out in", values, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutNotIn(List<String> values) {
            addCriterion("check_out not in", values, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutBetween(String value1, String value2) {
            addCriterion("check_out between", value1, value2, "checkOut");
            return (Criteria) this;
        }

        public Criteria andCheckOutNotBetween(String value1, String value2) {
            addCriterion("check_out not between", value1, value2, "checkOut");
            return (Criteria) this;
        }

        public Criteria andReviewTimeIsNull() {
            addCriterion("review_time is null");
            return (Criteria) this;
        }

        public Criteria andReviewTimeIsNotNull() {
            addCriterion("review_time is not null");
            return (Criteria) this;
        }

        public Criteria andReviewTimeEqualTo(Date value) {
            addCriterion("review_time =", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeNotEqualTo(Date value) {
            addCriterion("review_time <>", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeGreaterThan(Date value) {
            addCriterion("review_time >", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("review_time >=", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeLessThan(Date value) {
            addCriterion("review_time <", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeLessThanOrEqualTo(Date value) {
            addCriterion("review_time <=", value, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeIn(List<Date> values) {
            addCriterion("review_time in", values, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeNotIn(List<Date> values) {
            addCriterion("review_time not in", values, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeBetween(Date value1, Date value2) {
            addCriterion("review_time between", value1, value2, "reviewTime");
            return (Criteria) this;
        }

        public Criteria andReviewTimeNotBetween(Date value1, Date value2) {
            addCriterion("review_time not between", value1, value2, "reviewTime");
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

        public Criteria andIsReleaseIsNull() {
            addCriterion("is_release is null");
            return (Criteria) this;
        }

        public Criteria andIsReleaseIsNotNull() {
            addCriterion("is_release is not null");
            return (Criteria) this;
        }

        public Criteria andIsReleaseEqualTo(String value) {
            addCriterion("is_release =", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseNotEqualTo(String value) {
            addCriterion("is_release <>", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseGreaterThan(String value) {
            addCriterion("is_release >", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseGreaterThanOrEqualTo(String value) {
            addCriterion("is_release >=", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseLessThan(String value) {
            addCriterion("is_release <", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseLessThanOrEqualTo(String value) {
            addCriterion("is_release <=", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseLike(String value) {
            addCriterion("is_release like", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseNotLike(String value) {
            addCriterion("is_release not like", value, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseIn(List<String> values) {
            addCriterion("is_release in", values, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseNotIn(List<String> values) {
            addCriterion("is_release not in", values, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseBetween(String value1, String value2) {
            addCriterion("is_release between", value1, value2, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReleaseNotBetween(String value1, String value2) {
            addCriterion("is_release not between", value1, value2, "isRelease");
            return (Criteria) this;
        }

        public Criteria andIsReviewedIsNull() {
            addCriterion("is_reviewed is null");
            return (Criteria) this;
        }

        public Criteria andIsReviewedIsNotNull() {
            addCriterion("is_reviewed is not null");
            return (Criteria) this;
        }

        public Criteria andIsReviewedEqualTo(Integer value) {
            addCriterion("is_reviewed =", value, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedNotEqualTo(Integer value) {
            addCriterion("is_reviewed <>", value, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedGreaterThan(Integer value) {
            addCriterion("is_reviewed >", value, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_reviewed >=", value, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedLessThan(Integer value) {
            addCriterion("is_reviewed <", value, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedLessThanOrEqualTo(Integer value) {
            addCriterion("is_reviewed <=", value, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedIn(List<Integer> values) {
            addCriterion("is_reviewed in", values, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedNotIn(List<Integer> values) {
            addCriterion("is_reviewed not in", values, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedBetween(Integer value1, Integer value2) {
            addCriterion("is_reviewed between", value1, value2, "isReviewed");
            return (Criteria) this;
        }

        public Criteria andIsReviewedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_reviewed not between", value1, value2, "isReviewed");
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

        public Criteria andDeletedEqualTo(String value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(String value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(String value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(String value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(String value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(String value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLike(String value) {
            addCriterion("deleted like", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotLike(String value) {
            addCriterion("deleted not like", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<String> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<String> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(String value1, String value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(String value1, String value2) {
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