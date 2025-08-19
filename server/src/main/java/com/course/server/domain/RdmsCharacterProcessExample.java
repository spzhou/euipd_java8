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

public class RdmsCharacterProcessExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsCharacterProcessExample() {
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

        public Criteria andCharacterIdIsNull() {
            addCriterion("character_id is null");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIsNotNull() {
            addCriterion("character_id is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterIdEqualTo(String value) {
            addCriterion("character_id =", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotEqualTo(String value) {
            addCriterion("character_id <>", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdGreaterThan(String value) {
            addCriterion("character_id >", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdGreaterThanOrEqualTo(String value) {
            addCriterion("character_id >=", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLessThan(String value) {
            addCriterion("character_id <", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLessThanOrEqualTo(String value) {
            addCriterion("character_id <=", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdLike(String value) {
            addCriterion("character_id like", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotLike(String value) {
            addCriterion("character_id not like", value, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdIn(List<String> values) {
            addCriterion("character_id in", values, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotIn(List<String> values) {
            addCriterion("character_id not in", values, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdBetween(String value1, String value2) {
            addCriterion("character_id between", value1, value2, "characterId");
            return (Criteria) this;
        }

        public Criteria andCharacterIdNotBetween(String value1, String value2) {
            addCriterion("character_id not between", value1, value2, "characterId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdIsNull() {
            addCriterion("executor_id is null");
            return (Criteria) this;
        }

        public Criteria andExecutorIdIsNotNull() {
            addCriterion("executor_id is not null");
            return (Criteria) this;
        }

        public Criteria andExecutorIdEqualTo(String value) {
            addCriterion("executor_id =", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotEqualTo(String value) {
            addCriterion("executor_id <>", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdGreaterThan(String value) {
            addCriterion("executor_id >", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdGreaterThanOrEqualTo(String value) {
            addCriterion("executor_id >=", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdLessThan(String value) {
            addCriterion("executor_id <", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdLessThanOrEqualTo(String value) {
            addCriterion("executor_id <=", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdLike(String value) {
            addCriterion("executor_id like", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotLike(String value) {
            addCriterion("executor_id not like", value, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdIn(List<String> values) {
            addCriterion("executor_id in", values, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotIn(List<String> values) {
            addCriterion("executor_id not in", values, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdBetween(String value1, String value2) {
            addCriterion("executor_id between", value1, value2, "executorId");
            return (Criteria) this;
        }

        public Criteria andExecutorIdNotBetween(String value1, String value2) {
            addCriterion("executor_id not between", value1, value2, "executorId");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionIsNull() {
            addCriterion("job_description is null");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionIsNotNull() {
            addCriterion("job_description is not null");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionEqualTo(String value) {
            addCriterion("job_description =", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotEqualTo(String value) {
            addCriterion("job_description <>", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionGreaterThan(String value) {
            addCriterion("job_description >", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("job_description >=", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionLessThan(String value) {
            addCriterion("job_description <", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionLessThanOrEqualTo(String value) {
            addCriterion("job_description <=", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionLike(String value) {
            addCriterion("job_description like", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotLike(String value) {
            addCriterion("job_description not like", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionIn(List<String> values) {
            addCriterion("job_description in", values, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotIn(List<String> values) {
            addCriterion("job_description not in", values, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionBetween(String value1, String value2) {
            addCriterion("job_description between", value1, value2, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotBetween(String value1, String value2) {
            addCriterion("job_description not between", value1, value2, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andDeepIsNull() {
            addCriterion("deep is null");
            return (Criteria) this;
        }

        public Criteria andDeepIsNotNull() {
            addCriterion("deep is not null");
            return (Criteria) this;
        }

        public Criteria andDeepEqualTo(Integer value) {
            addCriterion("deep =", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepNotEqualTo(Integer value) {
            addCriterion("deep <>", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepGreaterThan(Integer value) {
            addCriterion("deep >", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepGreaterThanOrEqualTo(Integer value) {
            addCriterion("deep >=", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepLessThan(Integer value) {
            addCriterion("deep <", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepLessThanOrEqualTo(Integer value) {
            addCriterion("deep <=", value, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepIn(List<Integer> values) {
            addCriterion("deep in", values, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepNotIn(List<Integer> values) {
            addCriterion("deep not in", values, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepBetween(Integer value1, Integer value2) {
            addCriterion("deep between", value1, value2, "deep");
            return (Criteria) this;
        }

        public Criteria andDeepNotBetween(Integer value1, Integer value2) {
            addCriterion("deep not between", value1, value2, "deep");
            return (Criteria) this;
        }

        public Criteria andNextNodeIsNull() {
            addCriterion("next_node is null");
            return (Criteria) this;
        }

        public Criteria andNextNodeIsNotNull() {
            addCriterion("next_node is not null");
            return (Criteria) this;
        }

        public Criteria andNextNodeEqualTo(String value) {
            addCriterion("next_node =", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotEqualTo(String value) {
            addCriterion("next_node <>", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeGreaterThan(String value) {
            addCriterion("next_node >", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeGreaterThanOrEqualTo(String value) {
            addCriterion("next_node >=", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeLessThan(String value) {
            addCriterion("next_node <", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeLessThanOrEqualTo(String value) {
            addCriterion("next_node <=", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeLike(String value) {
            addCriterion("next_node like", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotLike(String value) {
            addCriterion("next_node not like", value, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeIn(List<String> values) {
            addCriterion("next_node in", values, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotIn(List<String> values) {
            addCriterion("next_node not in", values, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeBetween(String value1, String value2) {
            addCriterion("next_node between", value1, value2, "nextNode");
            return (Criteria) this;
        }

        public Criteria andNextNodeNotBetween(String value1, String value2) {
            addCriterion("next_node not between", value1, value2, "nextNode");
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

        public Criteria andApprovalStatusIsNull() {
            addCriterion("approval_status is null");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusIsNotNull() {
            addCriterion("approval_status is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusEqualTo(String value) {
            addCriterion("approval_status =", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotEqualTo(String value) {
            addCriterion("approval_status <>", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusGreaterThan(String value) {
            addCriterion("approval_status >", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusGreaterThanOrEqualTo(String value) {
            addCriterion("approval_status >=", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusLessThan(String value) {
            addCriterion("approval_status <", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusLessThanOrEqualTo(String value) {
            addCriterion("approval_status <=", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusLike(String value) {
            addCriterion("approval_status like", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotLike(String value) {
            addCriterion("approval_status not like", value, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusIn(List<String> values) {
            addCriterion("approval_status in", values, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotIn(List<String> values) {
            addCriterion("approval_status not in", values, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusBetween(String value1, String value2) {
            addCriterion("approval_status between", value1, value2, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andApprovalStatusNotBetween(String value1, String value2) {
            addCriterion("approval_status not between", value1, value2, "approvalStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusIsNull() {
            addCriterion("process_status is null");
            return (Criteria) this;
        }

        public Criteria andProcessStatusIsNotNull() {
            addCriterion("process_status is not null");
            return (Criteria) this;
        }

        public Criteria andProcessStatusEqualTo(String value) {
            addCriterion("process_status =", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotEqualTo(String value) {
            addCriterion("process_status <>", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusGreaterThan(String value) {
            addCriterion("process_status >", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusGreaterThanOrEqualTo(String value) {
            addCriterion("process_status >=", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusLessThan(String value) {
            addCriterion("process_status <", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusLessThanOrEqualTo(String value) {
            addCriterion("process_status <=", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusLike(String value) {
            addCriterion("process_status like", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotLike(String value) {
            addCriterion("process_status not like", value, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusIn(List<String> values) {
            addCriterion("process_status in", values, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotIn(List<String> values) {
            addCriterion("process_status not in", values, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusBetween(String value1, String value2) {
            addCriterion("process_status between", value1, value2, "processStatus");
            return (Criteria) this;
        }

        public Criteria andProcessStatusNotBetween(String value1, String value2) {
            addCriterion("process_status not between", value1, value2, "processStatus");
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