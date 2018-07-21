package org.quetzaco.archives.model;

import java.util.ArrayList;
import java.util.List;

public class FlowFormDestoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowFormDestoryExample() {
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

        public Criteria andFlowIdIsNull() {
            addCriterion("flow_id is null");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNotNull() {
            addCriterion("flow_id is not null");
            return (Criteria) this;
        }

        public Criteria andFlowIdEqualTo(Long value) {
            addCriterion("flow_id =", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotEqualTo(Long value) {
            addCriterion("flow_id <>", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThan(Long value) {
            addCriterion("flow_id >", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThanOrEqualTo(Long value) {
            addCriterion("flow_id >=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThan(Long value) {
            addCriterion("flow_id <", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThanOrEqualTo(Long value) {
            addCriterion("flow_id <=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdIn(List<Long> values) {
            addCriterion("flow_id in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotIn(List<Long> values) {
            addCriterion("flow_id not in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdBetween(Long value1, Long value2) {
            addCriterion("flow_id between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotBetween(Long value1, Long value2) {
            addCriterion("flow_id not between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andReelNumIsNull() {
            addCriterion("reel_num is null");
            return (Criteria) this;
        }

        public Criteria andReelNumIsNotNull() {
            addCriterion("reel_num is not null");
            return (Criteria) this;
        }

        public Criteria andReelNumEqualTo(String value) {
            addCriterion("reel_num =", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumNotEqualTo(String value) {
            addCriterion("reel_num <>", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumGreaterThan(String value) {
            addCriterion("reel_num >", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumGreaterThanOrEqualTo(String value) {
            addCriterion("reel_num >=", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumLessThan(String value) {
            addCriterion("reel_num <", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumLessThanOrEqualTo(String value) {
            addCriterion("reel_num <=", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumLike(String value) {
            addCriterion("reel_num like", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumNotLike(String value) {
            addCriterion("reel_num not like", value, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumIn(List<String> values) {
            addCriterion("reel_num in", values, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumNotIn(List<String> values) {
            addCriterion("reel_num not in", values, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumBetween(String value1, String value2) {
            addCriterion("reel_num between", value1, value2, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelNumNotBetween(String value1, String value2) {
            addCriterion("reel_num not between", value1, value2, "reelNum");
            return (Criteria) this;
        }

        public Criteria andReelTypeIsNull() {
            addCriterion("reel_type is null");
            return (Criteria) this;
        }

        public Criteria andReelTypeIsNotNull() {
            addCriterion("reel_type is not null");
            return (Criteria) this;
        }

        public Criteria andReelTypeEqualTo(String value) {
            addCriterion("reel_type =", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeNotEqualTo(String value) {
            addCriterion("reel_type <>", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeGreaterThan(String value) {
            addCriterion("reel_type >", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeGreaterThanOrEqualTo(String value) {
            addCriterion("reel_type >=", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeLessThan(String value) {
            addCriterion("reel_type <", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeLessThanOrEqualTo(String value) {
            addCriterion("reel_type <=", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeLike(String value) {
            addCriterion("reel_type like", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeNotLike(String value) {
            addCriterion("reel_type not like", value, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeIn(List<String> values) {
            addCriterion("reel_type in", values, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeNotIn(List<String> values) {
            addCriterion("reel_type not in", values, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeBetween(String value1, String value2) {
            addCriterion("reel_type between", value1, value2, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelTypeNotBetween(String value1, String value2) {
            addCriterion("reel_type not between", value1, value2, "reelType");
            return (Criteria) this;
        }

        public Criteria andReelSizeIsNull() {
            addCriterion("reel_size is null");
            return (Criteria) this;
        }

        public Criteria andReelSizeIsNotNull() {
            addCriterion("reel_size is not null");
            return (Criteria) this;
        }

        public Criteria andReelSizeEqualTo(Integer value) {
            addCriterion("reel_size =", value, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeNotEqualTo(Integer value) {
            addCriterion("reel_size <>", value, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeGreaterThan(Integer value) {
            addCriterion("reel_size >", value, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("reel_size >=", value, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeLessThan(Integer value) {
            addCriterion("reel_size <", value, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeLessThanOrEqualTo(Integer value) {
            addCriterion("reel_size <=", value, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeIn(List<Integer> values) {
            addCriterion("reel_size in", values, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeNotIn(List<Integer> values) {
            addCriterion("reel_size not in", values, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeBetween(Integer value1, Integer value2) {
            addCriterion("reel_size between", value1, value2, "reelSize");
            return (Criteria) this;
        }

        public Criteria andReelSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("reel_size not between", value1, value2, "reelSize");
            return (Criteria) this;
        }

        public Criteria andDestoryUserIsNull() {
            addCriterion("destory_user is null");
            return (Criteria) this;
        }

        public Criteria andDestoryUserIsNotNull() {
            addCriterion("destory_user is not null");
            return (Criteria) this;
        }

        public Criteria andDestoryUserEqualTo(String value) {
            addCriterion("destory_user =", value, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserNotEqualTo(String value) {
            addCriterion("destory_user <>", value, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserGreaterThan(String value) {
            addCriterion("destory_user >", value, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserGreaterThanOrEqualTo(String value) {
            addCriterion("destory_user >=", value, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserLessThan(String value) {
            addCriterion("destory_user <", value, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserLessThanOrEqualTo(String value) {
            addCriterion("destory_user <=", value, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserIn(List<String> values) {
            addCriterion("destory_user in", values, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserNotIn(List<String> values) {
            addCriterion("destory_user not in", values, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserBetween(String value1, String value2) {
            addCriterion("destory_user between", value1, value2, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andDestoryUserNotBetween(String value1, String value2) {
            addCriterion("destory_user not between", value1, value2, "destoryUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserIsNull() {
            addCriterion("supervise_user is null");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserIsNotNull() {
            addCriterion("supervise_user is not null");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserEqualTo(String value) {
            addCriterion("supervise_user =", value, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserNotEqualTo(String value) {
            addCriterion("supervise_user <>", value, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserGreaterThan(String value) {
            addCriterion("supervise_user >", value, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserGreaterThanOrEqualTo(String value) {
            addCriterion("supervise_user >=", value, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserLessThan(String value) {
            addCriterion("supervise_user <", value, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserLessThanOrEqualTo(String value) {
            addCriterion("supervise_user <=", value, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserIn(List<String> values) {
            addCriterion("supervise_user in", values, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserNotIn(List<String> values) {
            addCriterion("supervise_user not in", values, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserBetween(String value1, String value2) {
            addCriterion("supervise_user between", value1, value2, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andSuperviseUserNotBetween(String value1, String value2) {
            addCriterion("supervise_user not between", value1, value2, "superviseUser");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andRecordFlagIsNull() {
            addCriterion("record_flag is null");
            return (Criteria) this;
        }

        public Criteria andRecordFlagIsNotNull() {
            addCriterion("record_flag is not null");
            return (Criteria) this;
        }

        public Criteria andRecordFlagEqualTo(Boolean value) {
            addCriterion("record_flag =", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagNotEqualTo(Boolean value) {
            addCriterion("record_flag <>", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagGreaterThan(Boolean value) {
            addCriterion("record_flag >", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("record_flag >=", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagLessThan(Boolean value) {
            addCriterion("record_flag <", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("record_flag <=", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagIn(List<Boolean> values) {
            addCriterion("record_flag in", values, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagNotIn(List<Boolean> values) {
            addCriterion("record_flag not in", values, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("record_flag between", value1, value2, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("record_flag not between", value1, value2, "recordFlag");
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