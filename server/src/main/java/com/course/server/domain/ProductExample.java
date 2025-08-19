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

public class ProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductExample() {
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

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(String value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(String value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(String value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(String value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(String value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLike(String value) {
            addCriterion("product_id like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotLike(String value) {
            addCriterion("product_id not like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<String> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<String> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(String value1, String value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(String value1, String value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Long value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Long value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Long value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Long value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Long value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Long> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Long> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Long value1, Long value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Long value1, Long value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNull() {
            addCriterion("goods_name is null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNotNull() {
            addCriterion("goods_name is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameEqualTo(String value) {
            addCriterion("goods_name =", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotEqualTo(String value) {
            addCriterion("goods_name <>", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThan(String value) {
            addCriterion("goods_name >", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThanOrEqualTo(String value) {
            addCriterion("goods_name >=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThan(String value) {
            addCriterion("goods_name <", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThanOrEqualTo(String value) {
            addCriterion("goods_name <=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLike(String value) {
            addCriterion("goods_name like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotLike(String value) {
            addCriterion("goods_name not like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIn(List<String> values) {
            addCriterion("goods_name in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotIn(List<String> values) {
            addCriterion("goods_name not in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameBetween(String value1, String value2) {
            addCriterion("goods_name between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotBetween(String value1, String value2) {
            addCriterion("goods_name not between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroIsNull() {
            addCriterion("goods_intro is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroIsNotNull() {
            addCriterion("goods_intro is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroEqualTo(String value) {
            addCriterion("goods_intro =", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroNotEqualTo(String value) {
            addCriterion("goods_intro <>", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroGreaterThan(String value) {
            addCriterion("goods_intro >", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroGreaterThanOrEqualTo(String value) {
            addCriterion("goods_intro >=", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroLessThan(String value) {
            addCriterion("goods_intro <", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroLessThanOrEqualTo(String value) {
            addCriterion("goods_intro <=", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroLike(String value) {
            addCriterion("goods_intro like", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroNotLike(String value) {
            addCriterion("goods_intro not like", value, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroIn(List<String> values) {
            addCriterion("goods_intro in", values, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroNotIn(List<String> values) {
            addCriterion("goods_intro not in", values, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroBetween(String value1, String value2) {
            addCriterion("goods_intro between", value1, value2, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andGoodsIntroNotBetween(String value1, String value2) {
            addCriterion("goods_intro not between", value1, value2, "goodsIntro");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNull() {
            addCriterion("original_price is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNotNull() {
            addCriterion("original_price is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceEqualTo(Integer value) {
            addCriterion("original_price =", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotEqualTo(Integer value) {
            addCriterion("original_price <>", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThan(Integer value) {
            addCriterion("original_price >", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("original_price >=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThan(Integer value) {
            addCriterion("original_price <", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThanOrEqualTo(Integer value) {
            addCriterion("original_price <=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIn(List<Integer> values) {
            addCriterion("original_price in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotIn(List<Integer> values) {
            addCriterion("original_price not in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceBetween(Integer value1, Integer value2) {
            addCriterion("original_price between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("original_price not between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceIsNull() {
            addCriterion("selling_price is null");
            return (Criteria) this;
        }

        public Criteria andSellingPriceIsNotNull() {
            addCriterion("selling_price is not null");
            return (Criteria) this;
        }

        public Criteria andSellingPriceEqualTo(Integer value) {
            addCriterion("selling_price =", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceNotEqualTo(Integer value) {
            addCriterion("selling_price <>", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceGreaterThan(Integer value) {
            addCriterion("selling_price >", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("selling_price >=", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceLessThan(Integer value) {
            addCriterion("selling_price <", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceLessThanOrEqualTo(Integer value) {
            addCriterion("selling_price <=", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceIn(List<Integer> values) {
            addCriterion("selling_price in", values, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceNotIn(List<Integer> values) {
            addCriterion("selling_price not in", values, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceBetween(Integer value1, Integer value2) {
            addCriterion("selling_price between", value1, value2, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("selling_price not between", value1, value2, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andStockNumIsNull() {
            addCriterion("stock_num is null");
            return (Criteria) this;
        }

        public Criteria andStockNumIsNotNull() {
            addCriterion("stock_num is not null");
            return (Criteria) this;
        }

        public Criteria andStockNumEqualTo(Integer value) {
            addCriterion("stock_num =", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumNotEqualTo(Integer value) {
            addCriterion("stock_num <>", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumGreaterThan(Integer value) {
            addCriterion("stock_num >", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_num >=", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumLessThan(Integer value) {
            addCriterion("stock_num <", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumLessThanOrEqualTo(Integer value) {
            addCriterion("stock_num <=", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumIn(List<Integer> values) {
            addCriterion("stock_num in", values, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumNotIn(List<Integer> values) {
            addCriterion("stock_num not in", values, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumBetween(Integer value1, Integer value2) {
            addCriterion("stock_num between", value1, value2, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_num not between", value1, value2, "stockNum");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
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

        public Criteria andLoginNameIsNull() {
            addCriterion("login_name is null");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNotNull() {
            addCriterion("login_name is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNameEqualTo(String value) {
            addCriterion("login_name =", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotEqualTo(String value) {
            addCriterion("login_name <>", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThan(String value) {
            addCriterion("login_name >", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThanOrEqualTo(String value) {
            addCriterion("login_name >=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThan(String value) {
            addCriterion("login_name <", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThanOrEqualTo(String value) {
            addCriterion("login_name <=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLike(String value) {
            addCriterion("login_name like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotLike(String value) {
            addCriterion("login_name not like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIn(List<String> values) {
            addCriterion("login_name in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotIn(List<String> values) {
            addCriterion("login_name not in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameBetween(String value1, String value2) {
            addCriterion("login_name between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotBetween(String value1, String value2) {
            addCriterion("login_name not between", value1, value2, "loginName");
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

        public Criteria andPerformanceBasePriceIsNull() {
            addCriterion("performance_base_price is null");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceIsNotNull() {
            addCriterion("performance_base_price is not null");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceEqualTo(Integer value) {
            addCriterion("performance_base_price =", value, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceNotEqualTo(Integer value) {
            addCriterion("performance_base_price <>", value, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceGreaterThan(Integer value) {
            addCriterion("performance_base_price >", value, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("performance_base_price >=", value, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceLessThan(Integer value) {
            addCriterion("performance_base_price <", value, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceLessThanOrEqualTo(Integer value) {
            addCriterion("performance_base_price <=", value, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceIn(List<Integer> values) {
            addCriterion("performance_base_price in", values, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceNotIn(List<Integer> values) {
            addCriterion("performance_base_price not in", values, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceBetween(Integer value1, Integer value2) {
            addCriterion("performance_base_price between", value1, value2, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPerformanceBasePriceNotBetween(Integer value1, Integer value2) {
            addCriterion("performance_base_price not between", value1, value2, "performanceBasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceIsNull() {
            addCriterion("platform_purchase_price is null");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceIsNotNull() {
            addCriterion("platform_purchase_price is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceEqualTo(Integer value) {
            addCriterion("platform_purchase_price =", value, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceNotEqualTo(Integer value) {
            addCriterion("platform_purchase_price <>", value, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceGreaterThan(Integer value) {
            addCriterion("platform_purchase_price >", value, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("platform_purchase_price >=", value, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceLessThan(Integer value) {
            addCriterion("platform_purchase_price <", value, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceLessThanOrEqualTo(Integer value) {
            addCriterion("platform_purchase_price <=", value, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceIn(List<Integer> values) {
            addCriterion("platform_purchase_price in", values, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceNotIn(List<Integer> values) {
            addCriterion("platform_purchase_price not in", values, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceBetween(Integer value1, Integer value2) {
            addCriterion("platform_purchase_price between", value1, value2, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPlatformPurchasePriceNotBetween(Integer value1, Integer value2) {
            addCriterion("platform_purchase_price not between", value1, value2, "platformPurchasePrice");
            return (Criteria) this;
        }

        public Criteria andPricingTypeIsNull() {
            addCriterion("pricing_type is null");
            return (Criteria) this;
        }

        public Criteria andPricingTypeIsNotNull() {
            addCriterion("pricing_type is not null");
            return (Criteria) this;
        }

        public Criteria andPricingTypeEqualTo(Integer value) {
            addCriterion("pricing_type =", value, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeNotEqualTo(Integer value) {
            addCriterion("pricing_type <>", value, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeGreaterThan(Integer value) {
            addCriterion("pricing_type >", value, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pricing_type >=", value, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeLessThan(Integer value) {
            addCriterion("pricing_type <", value, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeLessThanOrEqualTo(Integer value) {
            addCriterion("pricing_type <=", value, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeIn(List<Integer> values) {
            addCriterion("pricing_type in", values, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeNotIn(List<Integer> values) {
            addCriterion("pricing_type not in", values, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeBetween(Integer value1, Integer value2) {
            addCriterion("pricing_type between", value1, value2, "pricingType");
            return (Criteria) this;
        }

        public Criteria andPricingTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("pricing_type not between", value1, value2, "pricingType");
            return (Criteria) this;
        }

        public Criteria andComIdIsNull() {
            addCriterion("com_id is null");
            return (Criteria) this;
        }

        public Criteria andComIdIsNotNull() {
            addCriterion("com_id is not null");
            return (Criteria) this;
        }

        public Criteria andComIdEqualTo(String value) {
            addCriterion("com_id =", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdNotEqualTo(String value) {
            addCriterion("com_id <>", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdGreaterThan(String value) {
            addCriterion("com_id >", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdGreaterThanOrEqualTo(String value) {
            addCriterion("com_id >=", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdLessThan(String value) {
            addCriterion("com_id <", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdLessThanOrEqualTo(String value) {
            addCriterion("com_id <=", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdLike(String value) {
            addCriterion("com_id like", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdNotLike(String value) {
            addCriterion("com_id not like", value, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdIn(List<String> values) {
            addCriterion("com_id in", values, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdNotIn(List<String> values) {
            addCriterion("com_id not in", values, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdBetween(String value1, String value2) {
            addCriterion("com_id between", value1, value2, "comId");
            return (Criteria) this;
        }

        public Criteria andComIdNotBetween(String value1, String value2) {
            addCriterion("com_id not between", value1, value2, "comId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdIsNull() {
            addCriterion("com_contact_user_id is null");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdIsNotNull() {
            addCriterion("com_contact_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdEqualTo(String value) {
            addCriterion("com_contact_user_id =", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdNotEqualTo(String value) {
            addCriterion("com_contact_user_id <>", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdGreaterThan(String value) {
            addCriterion("com_contact_user_id >", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("com_contact_user_id >=", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdLessThan(String value) {
            addCriterion("com_contact_user_id <", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdLessThanOrEqualTo(String value) {
            addCriterion("com_contact_user_id <=", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdLike(String value) {
            addCriterion("com_contact_user_id like", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdNotLike(String value) {
            addCriterion("com_contact_user_id not like", value, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdIn(List<String> values) {
            addCriterion("com_contact_user_id in", values, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdNotIn(List<String> values) {
            addCriterion("com_contact_user_id not in", values, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdBetween(String value1, String value2) {
            addCriterion("com_contact_user_id between", value1, value2, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContactUserIdNotBetween(String value1, String value2) {
            addCriterion("com_contact_user_id not between", value1, value2, "comContactUserId");
            return (Criteria) this;
        }

        public Criteria andComContractIdIsNull() {
            addCriterion("com_contract_id is null");
            return (Criteria) this;
        }

        public Criteria andComContractIdIsNotNull() {
            addCriterion("com_contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andComContractIdEqualTo(String value) {
            addCriterion("com_contract_id =", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdNotEqualTo(String value) {
            addCriterion("com_contract_id <>", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdGreaterThan(String value) {
            addCriterion("com_contract_id >", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdGreaterThanOrEqualTo(String value) {
            addCriterion("com_contract_id >=", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdLessThan(String value) {
            addCriterion("com_contract_id <", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdLessThanOrEqualTo(String value) {
            addCriterion("com_contract_id <=", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdLike(String value) {
            addCriterion("com_contract_id like", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdNotLike(String value) {
            addCriterion("com_contract_id not like", value, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdIn(List<String> values) {
            addCriterion("com_contract_id in", values, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdNotIn(List<String> values) {
            addCriterion("com_contract_id not in", values, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdBetween(String value1, String value2) {
            addCriterion("com_contract_id between", value1, value2, "comContractId");
            return (Criteria) this;
        }

        public Criteria andComContractIdNotBetween(String value1, String value2) {
            addCriterion("com_contract_id not between", value1, value2, "comContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdIsNull() {
            addCriterion("plat_contact_user_id is null");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdIsNotNull() {
            addCriterion("plat_contact_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdEqualTo(String value) {
            addCriterion("plat_contact_user_id =", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdNotEqualTo(String value) {
            addCriterion("plat_contact_user_id <>", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdGreaterThan(String value) {
            addCriterion("plat_contact_user_id >", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("plat_contact_user_id >=", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdLessThan(String value) {
            addCriterion("plat_contact_user_id <", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdLessThanOrEqualTo(String value) {
            addCriterion("plat_contact_user_id <=", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdLike(String value) {
            addCriterion("plat_contact_user_id like", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdNotLike(String value) {
            addCriterion("plat_contact_user_id not like", value, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdIn(List<String> values) {
            addCriterion("plat_contact_user_id in", values, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdNotIn(List<String> values) {
            addCriterion("plat_contact_user_id not in", values, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdBetween(String value1, String value2) {
            addCriterion("plat_contact_user_id between", value1, value2, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContactUserIdNotBetween(String value1, String value2) {
            addCriterion("plat_contact_user_id not between", value1, value2, "platContactUserId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdIsNull() {
            addCriterion("plat_contract_id is null");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdIsNotNull() {
            addCriterion("plat_contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdEqualTo(String value) {
            addCriterion("plat_contract_id =", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdNotEqualTo(String value) {
            addCriterion("plat_contract_id <>", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdGreaterThan(String value) {
            addCriterion("plat_contract_id >", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdGreaterThanOrEqualTo(String value) {
            addCriterion("plat_contract_id >=", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdLessThan(String value) {
            addCriterion("plat_contract_id <", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdLessThanOrEqualTo(String value) {
            addCriterion("plat_contract_id <=", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdLike(String value) {
            addCriterion("plat_contract_id like", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdNotLike(String value) {
            addCriterion("plat_contract_id not like", value, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdIn(List<String> values) {
            addCriterion("plat_contract_id in", values, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdNotIn(List<String> values) {
            addCriterion("plat_contract_id not in", values, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdBetween(String value1, String value2) {
            addCriterion("plat_contract_id between", value1, value2, "platContractId");
            return (Criteria) this;
        }

        public Criteria andPlatContractIdNotBetween(String value1, String value2) {
            addCriterion("plat_contract_id not between", value1, value2, "platContractId");
            return (Criteria) this;
        }

        public Criteria andChargeIsNull() {
            addCriterion("charge is null");
            return (Criteria) this;
        }

        public Criteria andChargeIsNotNull() {
            addCriterion("charge is not null");
            return (Criteria) this;
        }

        public Criteria andChargeEqualTo(String value) {
            addCriterion("charge =", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotEqualTo(String value) {
            addCriterion("charge <>", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThan(String value) {
            addCriterion("charge >", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeGreaterThanOrEqualTo(String value) {
            addCriterion("charge >=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThan(String value) {
            addCriterion("charge <", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLessThanOrEqualTo(String value) {
            addCriterion("charge <=", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeLike(String value) {
            addCriterion("charge like", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotLike(String value) {
            addCriterion("charge not like", value, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeIn(List<String> values) {
            addCriterion("charge in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotIn(List<String> values) {
            addCriterion("charge not in", values, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeBetween(String value1, String value2) {
            addCriterion("charge between", value1, value2, "charge");
            return (Criteria) this;
        }

        public Criteria andChargeNotBetween(String value1, String value2) {
            addCriterion("charge not between", value1, value2, "charge");
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

        public Criteria andEnrollIsNull() {
            addCriterion("enroll is null");
            return (Criteria) this;
        }

        public Criteria andEnrollIsNotNull() {
            addCriterion("enroll is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollEqualTo(Integer value) {
            addCriterion("enroll =", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollNotEqualTo(Integer value) {
            addCriterion("enroll <>", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollGreaterThan(Integer value) {
            addCriterion("enroll >", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollGreaterThanOrEqualTo(Integer value) {
            addCriterion("enroll >=", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollLessThan(Integer value) {
            addCriterion("enroll <", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollLessThanOrEqualTo(Integer value) {
            addCriterion("enroll <=", value, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollIn(List<Integer> values) {
            addCriterion("enroll in", values, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollNotIn(List<Integer> values) {
            addCriterion("enroll not in", values, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollBetween(Integer value1, Integer value2) {
            addCriterion("enroll between", value1, value2, "enroll");
            return (Criteria) this;
        }

        public Criteria andEnrollNotBetween(Integer value1, Integer value2) {
            addCriterion("enroll not between", value1, value2, "enroll");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(String value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(String value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(String value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(String value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(String value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(String value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLike(String value) {
            addCriterion("course_id like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotLike(String value) {
            addCriterion("course_id not like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<String> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<String> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(String value1, String value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(String value1, String value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
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