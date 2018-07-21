package org.quetzaco.archives.model;

import java.util.ArrayList;
import java.util.List;

public class FlowFormAssistExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowFormAssistExample() {
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

        public Criteria andApplyDaysIsNull() {
            addCriterion("apply_days is null");
            return (Criteria) this;
        }

        public Criteria andApplyDaysIsNotNull() {
            addCriterion("apply_days is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDaysEqualTo(String value) {
            addCriterion("apply_days =", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysNotEqualTo(String value) {
            addCriterion("apply_days <>", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysGreaterThan(String value) {
            addCriterion("apply_days >", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysGreaterThanOrEqualTo(String value) {
            addCriterion("apply_days >=", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysLessThan(String value) {
            addCriterion("apply_days <", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysLessThanOrEqualTo(String value) {
            addCriterion("apply_days <=", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysLike(String value) {
            addCriterion("apply_days like", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysNotLike(String value) {
            addCriterion("apply_days not like", value, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysIn(List<String> values) {
            addCriterion("apply_days in", values, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysNotIn(List<String> values) {
            addCriterion("apply_days not in", values, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysBetween(String value1, String value2) {
            addCriterion("apply_days between", value1, value2, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyDaysNotBetween(String value1, String value2) {
            addCriterion("apply_days not between", value1, value2, "applyDays");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNull() {
            addCriterion("apply_user is null");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNotNull() {
            addCriterion("apply_user is not null");
            return (Criteria) this;
        }

        public Criteria andApplyUserEqualTo(String value) {
            addCriterion("apply_user =", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotEqualTo(String value) {
            addCriterion("apply_user <>", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThan(String value) {
            addCriterion("apply_user >", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThanOrEqualTo(String value) {
            addCriterion("apply_user >=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThan(String value) {
            addCriterion("apply_user <", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThanOrEqualTo(String value) {
            addCriterion("apply_user <=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLike(String value) {
            addCriterion("apply_user like", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotLike(String value) {
            addCriterion("apply_user not like", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserIn(List<String> values) {
            addCriterion("apply_user in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotIn(List<String> values) {
            addCriterion("apply_user not in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserBetween(String value1, String value2) {
            addCriterion("apply_user between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotBetween(String value1, String value2) {
            addCriterion("apply_user not between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserIsNull() {
            addCriterion("assit_user is null");
            return (Criteria) this;
        }

        public Criteria andAssitUserIsNotNull() {
            addCriterion("assit_user is not null");
            return (Criteria) this;
        }

        public Criteria andAssitUserEqualTo(Long value) {
            addCriterion("assit_user =", value, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserNotEqualTo(Long value) {
            addCriterion("assit_user <>", value, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserGreaterThan(Long value) {
            addCriterion("assit_user >", value, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserGreaterThanOrEqualTo(Long value) {
            addCriterion("assit_user >=", value, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserLessThan(Long value) {
            addCriterion("assit_user <", value, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserLessThanOrEqualTo(Long value) {
            addCriterion("assit_user <=", value, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserIn(List<Long> values) {
            addCriterion("assit_user in", values, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserNotIn(List<Long> values) {
            addCriterion("assit_user not in", values, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserBetween(Long value1, Long value2) {
            addCriterion("assit_user between", value1, value2, "assitUser");
            return (Criteria) this;
        }

        public Criteria andAssitUserNotBetween(Long value1, Long value2) {
            addCriterion("assit_user not between", value1, value2, "assitUser");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(String value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(String value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(String value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(String value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(String value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLike(String value) {
            addCriterion("apply_id like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotLike(String value) {
            addCriterion("apply_id not like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<String> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<String> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(String value1, String value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(String value1, String value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andOfficeIsNull() {
            addCriterion("office is null");
            return (Criteria) this;
        }

        public Criteria andOfficeIsNotNull() {
            addCriterion("office is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeEqualTo(String value) {
            addCriterion("office =", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeNotEqualTo(String value) {
            addCriterion("office <>", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeGreaterThan(String value) {
            addCriterion("office >", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeGreaterThanOrEqualTo(String value) {
            addCriterion("office >=", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeLessThan(String value) {
            addCriterion("office <", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeLessThanOrEqualTo(String value) {
            addCriterion("office <=", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeLike(String value) {
            addCriterion("office like", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeNotLike(String value) {
            addCriterion("office not like", value, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeIn(List<String> values) {
            addCriterion("office in", values, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeNotIn(List<String> values) {
            addCriterion("office not in", values, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeBetween(String value1, String value2) {
            addCriterion("office between", value1, value2, "office");
            return (Criteria) this;
        }

        public Criteria andOfficeNotBetween(String value1, String value2) {
            addCriterion("office not between", value1, value2, "office");
            return (Criteria) this;
        }

        public Criteria andDeptIsNull() {
            addCriterion("dept is null");
            return (Criteria) this;
        }

        public Criteria andDeptIsNotNull() {
            addCriterion("dept is not null");
            return (Criteria) this;
        }

        public Criteria andDeptEqualTo(String value) {
            addCriterion("dept =", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptNotEqualTo(String value) {
            addCriterion("dept <>", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptGreaterThan(String value) {
            addCriterion("dept >", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptGreaterThanOrEqualTo(String value) {
            addCriterion("dept >=", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptLessThan(String value) {
            addCriterion("dept <", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptLessThanOrEqualTo(String value) {
            addCriterion("dept <=", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptLike(String value) {
            addCriterion("dept like", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptNotLike(String value) {
            addCriterion("dept not like", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptIn(List<String> values) {
            addCriterion("dept in", values, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptNotIn(List<String> values) {
            addCriterion("dept not in", values, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptBetween(String value1, String value2) {
            addCriterion("dept between", value1, value2, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptNotBetween(String value1, String value2) {
            addCriterion("dept not between", value1, value2, "dept");
            return (Criteria) this;
        }

        public Criteria andAssistTypeIsNull() {
            addCriterion("assist_type is null");
            return (Criteria) this;
        }

        public Criteria andAssistTypeIsNotNull() {
            addCriterion("assist_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssistTypeEqualTo(String value) {
            addCriterion("assist_type =", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeNotEqualTo(String value) {
            addCriterion("assist_type <>", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeGreaterThan(String value) {
            addCriterion("assist_type >", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeGreaterThanOrEqualTo(String value) {
            addCriterion("assist_type >=", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeLessThan(String value) {
            addCriterion("assist_type <", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeLessThanOrEqualTo(String value) {
            addCriterion("assist_type <=", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeLike(String value) {
            addCriterion("assist_type like", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeNotLike(String value) {
            addCriterion("assist_type not like", value, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeIn(List<String> values) {
            addCriterion("assist_type in", values, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeNotIn(List<String> values) {
            addCriterion("assist_type not in", values, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeBetween(String value1, String value2) {
            addCriterion("assist_type between", value1, value2, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistTypeNotBetween(String value1, String value2) {
            addCriterion("assist_type not between", value1, value2, "assistType");
            return (Criteria) this;
        }

        public Criteria andAssistContentIsNull() {
            addCriterion("assist_content is null");
            return (Criteria) this;
        }

        public Criteria andAssistContentIsNotNull() {
            addCriterion("assist_content is not null");
            return (Criteria) this;
        }

        public Criteria andAssistContentEqualTo(String value) {
            addCriterion("assist_content =", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentNotEqualTo(String value) {
            addCriterion("assist_content <>", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentGreaterThan(String value) {
            addCriterion("assist_content >", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentGreaterThanOrEqualTo(String value) {
            addCriterion("assist_content >=", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentLessThan(String value) {
            addCriterion("assist_content <", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentLessThanOrEqualTo(String value) {
            addCriterion("assist_content <=", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentLike(String value) {
            addCriterion("assist_content like", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentNotLike(String value) {
            addCriterion("assist_content not like", value, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentIn(List<String> values) {
            addCriterion("assist_content in", values, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentNotIn(List<String> values) {
            addCriterion("assist_content not in", values, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentBetween(String value1, String value2) {
            addCriterion("assist_content between", value1, value2, "assistContent");
            return (Criteria) this;
        }

        public Criteria andAssistContentNotBetween(String value1, String value2) {
            addCriterion("assist_content not between", value1, value2, "assistContent");
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

        public Criteria andRecordFlagEqualTo(String value) {
            addCriterion("record_flag =", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagNotEqualTo(String value) {
            addCriterion("record_flag <>", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagGreaterThan(String value) {
            addCriterion("record_flag >", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagGreaterThanOrEqualTo(String value) {
            addCriterion("record_flag >=", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagLessThan(String value) {
            addCriterion("record_flag <", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagLessThanOrEqualTo(String value) {
            addCriterion("record_flag <=", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagLike(String value) {
            addCriterion("record_flag like", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagNotLike(String value) {
            addCriterion("record_flag not like", value, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagIn(List<String> values) {
            addCriterion("record_flag in", values, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagNotIn(List<String> values) {
            addCriterion("record_flag not in", values, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagBetween(String value1, String value2) {
            addCriterion("record_flag between", value1, value2, "recordFlag");
            return (Criteria) this;
        }

        public Criteria andRecordFlagNotBetween(String value1, String value2) {
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