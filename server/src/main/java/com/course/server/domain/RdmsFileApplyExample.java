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

public class RdmsFileApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsFileApplyExample() {
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

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(String value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(String value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(String value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(String value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(String value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(String value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(String value) {
            addCriterion("file_id like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(String value) {
            addCriterion("file_id not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<String> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<String> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(String value1, String value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(String value1, String value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
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

        public Criteria andProposerIdIsNull() {
            addCriterion("proposer_id is null");
            return (Criteria) this;
        }

        public Criteria andProposerIdIsNotNull() {
            addCriterion("proposer_id is not null");
            return (Criteria) this;
        }

        public Criteria andProposerIdEqualTo(String value) {
            addCriterion("proposer_id =", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdNotEqualTo(String value) {
            addCriterion("proposer_id <>", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdGreaterThan(String value) {
            addCriterion("proposer_id >", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdGreaterThanOrEqualTo(String value) {
            addCriterion("proposer_id >=", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdLessThan(String value) {
            addCriterion("proposer_id <", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdLessThanOrEqualTo(String value) {
            addCriterion("proposer_id <=", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdLike(String value) {
            addCriterion("proposer_id like", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdNotLike(String value) {
            addCriterion("proposer_id not like", value, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdIn(List<String> values) {
            addCriterion("proposer_id in", values, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdNotIn(List<String> values) {
            addCriterion("proposer_id not in", values, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdBetween(String value1, String value2) {
            addCriterion("proposer_id between", value1, value2, "proposerId");
            return (Criteria) this;
        }

        public Criteria andProposerIdNotBetween(String value1, String value2) {
            addCriterion("proposer_id not between", value1, value2, "proposerId");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusIsNull() {
            addCriterion("application_status is null");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusIsNotNull() {
            addCriterion("application_status is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusEqualTo(String value) {
            addCriterion("application_status =", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotEqualTo(String value) {
            addCriterion("application_status <>", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusGreaterThan(String value) {
            addCriterion("application_status >", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusGreaterThanOrEqualTo(String value) {
            addCriterion("application_status >=", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusLessThan(String value) {
            addCriterion("application_status <", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusLessThanOrEqualTo(String value) {
            addCriterion("application_status <=", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusLike(String value) {
            addCriterion("application_status like", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotLike(String value) {
            addCriterion("application_status not like", value, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusIn(List<String> values) {
            addCriterion("application_status in", values, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotIn(List<String> values) {
            addCriterion("application_status not in", values, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusBetween(String value1, String value2) {
            addCriterion("application_status between", value1, value2, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApplicationStatusNotBetween(String value1, String value2) {
            addCriterion("application_status not between", value1, value2, "applicationStatus");
            return (Criteria) this;
        }

        public Criteria andApproverIdIsNull() {
            addCriterion("approver_id is null");
            return (Criteria) this;
        }

        public Criteria andApproverIdIsNotNull() {
            addCriterion("approver_id is not null");
            return (Criteria) this;
        }

        public Criteria andApproverIdEqualTo(String value) {
            addCriterion("approver_id =", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotEqualTo(String value) {
            addCriterion("approver_id <>", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdGreaterThan(String value) {
            addCriterion("approver_id >", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdGreaterThanOrEqualTo(String value) {
            addCriterion("approver_id >=", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdLessThan(String value) {
            addCriterion("approver_id <", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdLessThanOrEqualTo(String value) {
            addCriterion("approver_id <=", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdLike(String value) {
            addCriterion("approver_id like", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotLike(String value) {
            addCriterion("approver_id not like", value, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdIn(List<String> values) {
            addCriterion("approver_id in", values, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotIn(List<String> values) {
            addCriterion("approver_id not in", values, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdBetween(String value1, String value2) {
            addCriterion("approver_id between", value1, value2, "approverId");
            return (Criteria) this;
        }

        public Criteria andApproverIdNotBetween(String value1, String value2) {
            addCriterion("approver_id not between", value1, value2, "approverId");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNull() {
            addCriterion("approval_time is null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNotNull() {
            addCriterion("approval_time is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeEqualTo(Date value) {
            addCriterion("approval_time =", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotEqualTo(Date value) {
            addCriterion("approval_time <>", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThan(Date value) {
            addCriterion("approval_time >", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approval_time >=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThan(Date value) {
            addCriterion("approval_time <", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThanOrEqualTo(Date value) {
            addCriterion("approval_time <=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIn(List<Date> values) {
            addCriterion("approval_time in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotIn(List<Date> values) {
            addCriterion("approval_time not in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeBetween(Date value1, Date value2) {
            addCriterion("approval_time between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotBetween(Date value1, Date value2) {
            addCriterion("approval_time not between", value1, value2, "approvalTime");
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