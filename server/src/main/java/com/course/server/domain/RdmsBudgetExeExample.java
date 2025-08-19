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

public class RdmsBudgetExeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RdmsBudgetExeExample() {
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

        public Criteria andItemIdIsNull() {
            addCriterion("item_id is null");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNotNull() {
            addCriterion("item_id is not null");
            return (Criteria) this;
        }

        public Criteria andItemIdEqualTo(String value) {
            addCriterion("item_id =", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotEqualTo(String value) {
            addCriterion("item_id <>", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThan(String value) {
            addCriterion("item_id >", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThanOrEqualTo(String value) {
            addCriterion("item_id >=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThan(String value) {
            addCriterion("item_id <", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThanOrEqualTo(String value) {
            addCriterion("item_id <=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLike(String value) {
            addCriterion("item_id like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotLike(String value) {
            addCriterion("item_id not like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdIn(List<String> values) {
            addCriterion("item_id in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotIn(List<String> values) {
            addCriterion("item_id not in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdBetween(String value1, String value2) {
            addCriterion("item_id between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotBetween(String value1, String value2) {
            addCriterion("item_id not between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            addCriterion("parent_id like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("parent_id not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andItemNameIsNull() {
            addCriterion("item_name is null");
            return (Criteria) this;
        }

        public Criteria andItemNameIsNotNull() {
            addCriterion("item_name is not null");
            return (Criteria) this;
        }

        public Criteria andItemNameEqualTo(String value) {
            addCriterion("item_name =", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotEqualTo(String value) {
            addCriterion("item_name <>", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameGreaterThan(String value) {
            addCriterion("item_name >", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameGreaterThanOrEqualTo(String value) {
            addCriterion("item_name >=", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameLessThan(String value) {
            addCriterion("item_name <", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameLessThanOrEqualTo(String value) {
            addCriterion("item_name <=", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameLike(String value) {
            addCriterion("item_name like", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotLike(String value) {
            addCriterion("item_name not like", value, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameIn(List<String> values) {
            addCriterion("item_name in", values, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotIn(List<String> values) {
            addCriterion("item_name not in", values, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameBetween(String value1, String value2) {
            addCriterion("item_name between", value1, value2, "itemName");
            return (Criteria) this;
        }

        public Criteria andItemNameNotBetween(String value1, String value2) {
            addCriterion("item_name not between", value1, value2, "itemName");
            return (Criteria) this;
        }

        public Criteria andDocTypeIsNull() {
            addCriterion("doc_type is null");
            return (Criteria) this;
        }

        public Criteria andDocTypeIsNotNull() {
            addCriterion("doc_type is not null");
            return (Criteria) this;
        }

        public Criteria andDocTypeEqualTo(String value) {
            addCriterion("doc_type =", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotEqualTo(String value) {
            addCriterion("doc_type <>", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeGreaterThan(String value) {
            addCriterion("doc_type >", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeGreaterThanOrEqualTo(String value) {
            addCriterion("doc_type >=", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeLessThan(String value) {
            addCriterion("doc_type <", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeLessThanOrEqualTo(String value) {
            addCriterion("doc_type <=", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeLike(String value) {
            addCriterion("doc_type like", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotLike(String value) {
            addCriterion("doc_type not like", value, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeIn(List<String> values) {
            addCriterion("doc_type in", values, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotIn(List<String> values) {
            addCriterion("doc_type not in", values, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeBetween(String value1, String value2) {
            addCriterion("doc_type between", value1, value2, "docType");
            return (Criteria) this;
        }

        public Criteria andDocTypeNotBetween(String value1, String value2) {
            addCriterion("doc_type not between", value1, value2, "docType");
            return (Criteria) this;
        }

        public Criteria andReviewDescIsNull() {
            addCriterion("review_desc is null");
            return (Criteria) this;
        }

        public Criteria andReviewDescIsNotNull() {
            addCriterion("review_desc is not null");
            return (Criteria) this;
        }

        public Criteria andReviewDescEqualTo(String value) {
            addCriterion("review_desc =", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescNotEqualTo(String value) {
            addCriterion("review_desc <>", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescGreaterThan(String value) {
            addCriterion("review_desc >", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescGreaterThanOrEqualTo(String value) {
            addCriterion("review_desc >=", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescLessThan(String value) {
            addCriterion("review_desc <", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescLessThanOrEqualTo(String value) {
            addCriterion("review_desc <=", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescLike(String value) {
            addCriterion("review_desc like", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescNotLike(String value) {
            addCriterion("review_desc not like", value, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescIn(List<String> values) {
            addCriterion("review_desc in", values, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescNotIn(List<String> values) {
            addCriterion("review_desc not in", values, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescBetween(String value1, String value2) {
            addCriterion("review_desc between", value1, value2, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewDescNotBetween(String value1, String value2) {
            addCriterion("review_desc not between", value1, value2, "reviewDesc");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdIsNull() {
            addCriterion("review_leader_id is null");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdIsNotNull() {
            addCriterion("review_leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdEqualTo(String value) {
            addCriterion("review_leader_id =", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdNotEqualTo(String value) {
            addCriterion("review_leader_id <>", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdGreaterThan(String value) {
            addCriterion("review_leader_id >", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdGreaterThanOrEqualTo(String value) {
            addCriterion("review_leader_id >=", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdLessThan(String value) {
            addCriterion("review_leader_id <", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdLessThanOrEqualTo(String value) {
            addCriterion("review_leader_id <=", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdLike(String value) {
            addCriterion("review_leader_id like", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdNotLike(String value) {
            addCriterion("review_leader_id not like", value, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdIn(List<String> values) {
            addCriterion("review_leader_id in", values, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdNotIn(List<String> values) {
            addCriterion("review_leader_id not in", values, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdBetween(String value1, String value2) {
            addCriterion("review_leader_id between", value1, value2, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewLeaderIdNotBetween(String value1, String value2) {
            addCriterion("review_leader_id not between", value1, value2, "reviewLeaderId");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrIsNull() {
            addCriterion("review_coo_id_str is null");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrIsNotNull() {
            addCriterion("review_coo_id_str is not null");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrEqualTo(String value) {
            addCriterion("review_coo_id_str =", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrNotEqualTo(String value) {
            addCriterion("review_coo_id_str <>", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrGreaterThan(String value) {
            addCriterion("review_coo_id_str >", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrGreaterThanOrEqualTo(String value) {
            addCriterion("review_coo_id_str >=", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrLessThan(String value) {
            addCriterion("review_coo_id_str <", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrLessThanOrEqualTo(String value) {
            addCriterion("review_coo_id_str <=", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrLike(String value) {
            addCriterion("review_coo_id_str like", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrNotLike(String value) {
            addCriterion("review_coo_id_str not like", value, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrIn(List<String> values) {
            addCriterion("review_coo_id_str in", values, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrNotIn(List<String> values) {
            addCriterion("review_coo_id_str not in", values, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrBetween(String value1, String value2) {
            addCriterion("review_coo_id_str between", value1, value2, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andReviewCooIdStrNotBetween(String value1, String value2) {
            addCriterion("review_coo_id_str not between", value1, value2, "reviewCooIdStr");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedIsNull() {
            addCriterion("dev_manhour_approved is null");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedIsNotNull() {
            addCriterion("dev_manhour_approved is not null");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedEqualTo(Double value) {
            addCriterion("dev_manhour_approved =", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedNotEqualTo(Double value) {
            addCriterion("dev_manhour_approved <>", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedGreaterThan(Double value) {
            addCriterion("dev_manhour_approved >", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedGreaterThanOrEqualTo(Double value) {
            addCriterion("dev_manhour_approved >=", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedLessThan(Double value) {
            addCriterion("dev_manhour_approved <", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedLessThanOrEqualTo(Double value) {
            addCriterion("dev_manhour_approved <=", value, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedIn(List<Double> values) {
            addCriterion("dev_manhour_approved in", values, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedNotIn(List<Double> values) {
            addCriterion("dev_manhour_approved not in", values, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedBetween(Double value1, Double value2) {
            addCriterion("dev_manhour_approved between", value1, value2, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourApprovedNotBetween(Double value1, Double value2) {
            addCriterion("dev_manhour_approved not between", value1, value2, "devManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedIsNull() {
            addCriterion("test_manhour_approved is null");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedIsNotNull() {
            addCriterion("test_manhour_approved is not null");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedEqualTo(Double value) {
            addCriterion("test_manhour_approved =", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedNotEqualTo(Double value) {
            addCriterion("test_manhour_approved <>", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedGreaterThan(Double value) {
            addCriterion("test_manhour_approved >", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedGreaterThanOrEqualTo(Double value) {
            addCriterion("test_manhour_approved >=", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedLessThan(Double value) {
            addCriterion("test_manhour_approved <", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedLessThanOrEqualTo(Double value) {
            addCriterion("test_manhour_approved <=", value, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedIn(List<Double> values) {
            addCriterion("test_manhour_approved in", values, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedNotIn(List<Double> values) {
            addCriterion("test_manhour_approved not in", values, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedBetween(Double value1, Double value2) {
            addCriterion("test_manhour_approved between", value1, value2, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andTestManhourApprovedNotBetween(Double value1, Double value2) {
            addCriterion("test_manhour_approved not between", value1, value2, "testManhourApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedIsNull() {
            addCriterion("material_fee_approved is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedIsNotNull() {
            addCriterion("material_fee_approved is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved =", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedNotEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved <>", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedGreaterThan(BigDecimal value) {
            addCriterion("material_fee_approved >", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved >=", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedLessThan(BigDecimal value) {
            addCriterion("material_fee_approved <", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_approved <=", value, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved in", values, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedNotIn(List<BigDecimal> values) {
            addCriterion("material_fee_approved not in", values, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved between", value1, value2, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeApprovedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_approved not between", value1, value2, "materialFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedIsNull() {
            addCriterion("other_fee_approved is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedIsNotNull() {
            addCriterion("other_fee_approved is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved =", value, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedNotEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved <>", value, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedGreaterThan(BigDecimal value) {
            addCriterion("other_fee_approved >", value, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved >=", value, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedLessThan(BigDecimal value) {
            addCriterion("other_fee_approved <", value, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_approved <=", value, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedIn(List<BigDecimal> values) {
            addCriterion("other_fee_approved in", values, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedNotIn(List<BigDecimal> values) {
            addCriterion("other_fee_approved not in", values, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_approved between", value1, value2, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andOtherFeeApprovedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_approved not between", value1, value2, "otherFeeApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedIsNull() {
            addCriterion("sum_budget_approved is null");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedIsNotNull() {
            addCriterion("sum_budget_approved is not null");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedEqualTo(BigDecimal value) {
            addCriterion("sum_budget_approved =", value, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedNotEqualTo(BigDecimal value) {
            addCriterion("sum_budget_approved <>", value, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedGreaterThan(BigDecimal value) {
            addCriterion("sum_budget_approved >", value, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_budget_approved >=", value, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedLessThan(BigDecimal value) {
            addCriterion("sum_budget_approved <", value, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_budget_approved <=", value, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedIn(List<BigDecimal> values) {
            addCriterion("sum_budget_approved in", values, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedNotIn(List<BigDecimal> values) {
            addCriterion("sum_budget_approved not in", values, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_budget_approved between", value1, value2, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andSumBudgetApprovedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_budget_approved not between", value1, value2, "sumBudgetApproved");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveIsNull() {
            addCriterion("dev_manhour_active is null");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveIsNotNull() {
            addCriterion("dev_manhour_active is not null");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveEqualTo(Double value) {
            addCriterion("dev_manhour_active =", value, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveNotEqualTo(Double value) {
            addCriterion("dev_manhour_active <>", value, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveGreaterThan(Double value) {
            addCriterion("dev_manhour_active >", value, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveGreaterThanOrEqualTo(Double value) {
            addCriterion("dev_manhour_active >=", value, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveLessThan(Double value) {
            addCriterion("dev_manhour_active <", value, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveLessThanOrEqualTo(Double value) {
            addCriterion("dev_manhour_active <=", value, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveIn(List<Double> values) {
            addCriterion("dev_manhour_active in", values, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveNotIn(List<Double> values) {
            addCriterion("dev_manhour_active not in", values, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveBetween(Double value1, Double value2) {
            addCriterion("dev_manhour_active between", value1, value2, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andDevManhourActiveNotBetween(Double value1, Double value2) {
            addCriterion("dev_manhour_active not between", value1, value2, "devManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveIsNull() {
            addCriterion("test_manhour_active is null");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveIsNotNull() {
            addCriterion("test_manhour_active is not null");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveEqualTo(Double value) {
            addCriterion("test_manhour_active =", value, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveNotEqualTo(Double value) {
            addCriterion("test_manhour_active <>", value, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveGreaterThan(Double value) {
            addCriterion("test_manhour_active >", value, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveGreaterThanOrEqualTo(Double value) {
            addCriterion("test_manhour_active >=", value, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveLessThan(Double value) {
            addCriterion("test_manhour_active <", value, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveLessThanOrEqualTo(Double value) {
            addCriterion("test_manhour_active <=", value, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveIn(List<Double> values) {
            addCriterion("test_manhour_active in", values, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveNotIn(List<Double> values) {
            addCriterion("test_manhour_active not in", values, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveBetween(Double value1, Double value2) {
            addCriterion("test_manhour_active between", value1, value2, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andTestManhourActiveNotBetween(Double value1, Double value2) {
            addCriterion("test_manhour_active not between", value1, value2, "testManhourActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveIsNull() {
            addCriterion("material_fee_active is null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveIsNotNull() {
            addCriterion("material_fee_active is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveEqualTo(BigDecimal value) {
            addCriterion("material_fee_active =", value, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveNotEqualTo(BigDecimal value) {
            addCriterion("material_fee_active <>", value, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveGreaterThan(BigDecimal value) {
            addCriterion("material_fee_active >", value, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_active >=", value, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveLessThan(BigDecimal value) {
            addCriterion("material_fee_active <", value, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveLessThanOrEqualTo(BigDecimal value) {
            addCriterion("material_fee_active <=", value, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveIn(List<BigDecimal> values) {
            addCriterion("material_fee_active in", values, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveNotIn(List<BigDecimal> values) {
            addCriterion("material_fee_active not in", values, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_active between", value1, value2, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andMaterialFeeActiveNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("material_fee_active not between", value1, value2, "materialFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveIsNull() {
            addCriterion("other_fee_active is null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveIsNotNull() {
            addCriterion("other_fee_active is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveEqualTo(BigDecimal value) {
            addCriterion("other_fee_active =", value, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveNotEqualTo(BigDecimal value) {
            addCriterion("other_fee_active <>", value, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveGreaterThan(BigDecimal value) {
            addCriterion("other_fee_active >", value, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_active >=", value, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveLessThan(BigDecimal value) {
            addCriterion("other_fee_active <", value, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_fee_active <=", value, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveIn(List<BigDecimal> values) {
            addCriterion("other_fee_active in", values, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveNotIn(List<BigDecimal> values) {
            addCriterion("other_fee_active not in", values, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_active between", value1, value2, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andOtherFeeActiveNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_fee_active not between", value1, value2, "otherFeeActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveIsNull() {
            addCriterion("sum_budget_active is null");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveIsNotNull() {
            addCriterion("sum_budget_active is not null");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveEqualTo(BigDecimal value) {
            addCriterion("sum_budget_active =", value, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveNotEqualTo(BigDecimal value) {
            addCriterion("sum_budget_active <>", value, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveGreaterThan(BigDecimal value) {
            addCriterion("sum_budget_active >", value, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_budget_active >=", value, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveLessThan(BigDecimal value) {
            addCriterion("sum_budget_active <", value, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_budget_active <=", value, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveIn(List<BigDecimal> values) {
            addCriterion("sum_budget_active in", values, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveNotIn(List<BigDecimal> values) {
            addCriterion("sum_budget_active not in", values, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_budget_active between", value1, value2, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andSumBudgetActiveNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_budget_active not between", value1, value2, "sumBudgetActive");
            return (Criteria) this;
        }

        public Criteria andBudgetRateIsNull() {
            addCriterion("budget_rate is null");
            return (Criteria) this;
        }

        public Criteria andBudgetRateIsNotNull() {
            addCriterion("budget_rate is not null");
            return (Criteria) this;
        }

        public Criteria andBudgetRateEqualTo(BigDecimal value) {
            addCriterion("budget_rate =", value, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateNotEqualTo(BigDecimal value) {
            addCriterion("budget_rate <>", value, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateGreaterThan(BigDecimal value) {
            addCriterion("budget_rate >", value, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("budget_rate >=", value, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateLessThan(BigDecimal value) {
            addCriterion("budget_rate <", value, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("budget_rate <=", value, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateIn(List<BigDecimal> values) {
            addCriterion("budget_rate in", values, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateNotIn(List<BigDecimal> values) {
            addCriterion("budget_rate not in", values, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("budget_rate between", value1, value2, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andBudgetRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("budget_rate not between", value1, value2, "budgetRate");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrIsNull() {
            addCriterion("submit_file_id_list_str is null");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrIsNotNull() {
            addCriterion("submit_file_id_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrEqualTo(String value) {
            addCriterion("submit_file_id_list_str =", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrNotEqualTo(String value) {
            addCriterion("submit_file_id_list_str <>", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrGreaterThan(String value) {
            addCriterion("submit_file_id_list_str >", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrGreaterThanOrEqualTo(String value) {
            addCriterion("submit_file_id_list_str >=", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrLessThan(String value) {
            addCriterion("submit_file_id_list_str <", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrLessThanOrEqualTo(String value) {
            addCriterion("submit_file_id_list_str <=", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrLike(String value) {
            addCriterion("submit_file_id_list_str like", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrNotLike(String value) {
            addCriterion("submit_file_id_list_str not like", value, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrIn(List<String> values) {
            addCriterion("submit_file_id_list_str in", values, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrNotIn(List<String> values) {
            addCriterion("submit_file_id_list_str not in", values, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrBetween(String value1, String value2) {
            addCriterion("submit_file_id_list_str between", value1, value2, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andSubmitFileIdListStrNotBetween(String value1, String value2) {
            addCriterion("submit_file_id_list_str not between", value1, value2, "submitFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrIsNull() {
            addCriterion("review_file_id_list_str is null");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrIsNotNull() {
            addCriterion("review_file_id_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrEqualTo(String value) {
            addCriterion("review_file_id_list_str =", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrNotEqualTo(String value) {
            addCriterion("review_file_id_list_str <>", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrGreaterThan(String value) {
            addCriterion("review_file_id_list_str >", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrGreaterThanOrEqualTo(String value) {
            addCriterion("review_file_id_list_str >=", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrLessThan(String value) {
            addCriterion("review_file_id_list_str <", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrLessThanOrEqualTo(String value) {
            addCriterion("review_file_id_list_str <=", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrLike(String value) {
            addCriterion("review_file_id_list_str like", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrNotLike(String value) {
            addCriterion("review_file_id_list_str not like", value, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrIn(List<String> values) {
            addCriterion("review_file_id_list_str in", values, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrNotIn(List<String> values) {
            addCriterion("review_file_id_list_str not in", values, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrBetween(String value1, String value2) {
            addCriterion("review_file_id_list_str between", value1, value2, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andReviewFileIdListStrNotBetween(String value1, String value2) {
            addCriterion("review_file_id_list_str not between", value1, value2, "reviewFileIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrIsNull() {
            addCriterion("children_id_list_str is null");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrIsNotNull() {
            addCriterion("children_id_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrEqualTo(String value) {
            addCriterion("children_id_list_str =", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrNotEqualTo(String value) {
            addCriterion("children_id_list_str <>", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrGreaterThan(String value) {
            addCriterion("children_id_list_str >", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrGreaterThanOrEqualTo(String value) {
            addCriterion("children_id_list_str >=", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrLessThan(String value) {
            addCriterion("children_id_list_str <", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrLessThanOrEqualTo(String value) {
            addCriterion("children_id_list_str <=", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrLike(String value) {
            addCriterion("children_id_list_str like", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrNotLike(String value) {
            addCriterion("children_id_list_str not like", value, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrIn(List<String> values) {
            addCriterion("children_id_list_str in", values, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrNotIn(List<String> values) {
            addCriterion("children_id_list_str not in", values, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrBetween(String value1, String value2) {
            addCriterion("children_id_list_str between", value1, value2, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildrenIdListStrNotBetween(String value1, String value2) {
            addCriterion("children_id_list_str not between", value1, value2, "childrenIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrIsNull() {
            addCriterion("child_subproject_id_list_str is null");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrIsNotNull() {
            addCriterion("child_subproject_id_list_str is not null");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrEqualTo(String value) {
            addCriterion("child_subproject_id_list_str =", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrNotEqualTo(String value) {
            addCriterion("child_subproject_id_list_str <>", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrGreaterThan(String value) {
            addCriterion("child_subproject_id_list_str >", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrGreaterThanOrEqualTo(String value) {
            addCriterion("child_subproject_id_list_str >=", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrLessThan(String value) {
            addCriterion("child_subproject_id_list_str <", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrLessThanOrEqualTo(String value) {
            addCriterion("child_subproject_id_list_str <=", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrLike(String value) {
            addCriterion("child_subproject_id_list_str like", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrNotLike(String value) {
            addCriterion("child_subproject_id_list_str not like", value, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrIn(List<String> values) {
            addCriterion("child_subproject_id_list_str in", values, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrNotIn(List<String> values) {
            addCriterion("child_subproject_id_list_str not in", values, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrBetween(String value1, String value2) {
            addCriterion("child_subproject_id_list_str between", value1, value2, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andChildSubprojectIdListStrNotBetween(String value1, String value2) {
            addCriterion("child_subproject_id_list_str not between", value1, value2, "childSubprojectIdListStr");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("`result` is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("`result` is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("`result` =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("`result` <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("`result` >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("`result` >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("`result` <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("`result` <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("`result` like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("`result` not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("`result` in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("`result` not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("`result` between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("`result` not between", value1, value2, "result");
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