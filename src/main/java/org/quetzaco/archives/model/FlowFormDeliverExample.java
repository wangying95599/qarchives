package org.quetzaco.archives.model;

import java.util.ArrayList;
import java.util.List;

public class FlowFormDeliverExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowFormDeliverExample() {
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

        public Criteria andDeliverUserIsNull() {
            addCriterion("deliver_user is null");
            return (Criteria) this;
        }

        public Criteria andDeliverUserIsNotNull() {
            addCriterion("deliver_user is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverUserEqualTo(String value) {
            addCriterion("deliver_user =", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserNotEqualTo(String value) {
            addCriterion("deliver_user <>", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserGreaterThan(String value) {
            addCriterion("deliver_user >", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_user >=", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserLessThan(String value) {
            addCriterion("deliver_user <", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserLessThanOrEqualTo(String value) {
            addCriterion("deliver_user <=", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserLike(String value) {
            addCriterion("deliver_user like", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserNotLike(String value) {
            addCriterion("deliver_user not like", value, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserIn(List<String> values) {
            addCriterion("deliver_user in", values, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserNotIn(List<String> values) {
            addCriterion("deliver_user not in", values, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserBetween(String value1, String value2) {
            addCriterion("deliver_user between", value1, value2, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andDeliverUserNotBetween(String value1, String value2) {
            addCriterion("deliver_user not between", value1, value2, "deliverUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIsNull() {
            addCriterion("receive_user is null");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIsNotNull() {
            addCriterion("receive_user is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveUserEqualTo(Long value) {
            addCriterion("receive_user =", value, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserNotEqualTo(Long value) {
            addCriterion("receive_user <>", value, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserGreaterThan(Long value) {
            addCriterion("receive_user >", value, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserGreaterThanOrEqualTo(Long value) {
            addCriterion("receive_user >=", value, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserLessThan(Long value) {
            addCriterion("receive_user <", value, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserLessThanOrEqualTo(Long value) {
            addCriterion("receive_user <=", value, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIn(List<Long> values) {
            addCriterion("receive_user in", values, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserNotIn(List<Long> values) {
            addCriterion("receive_user not in", values, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserBetween(Long value1, Long value2) {
            addCriterion("receive_user between", value1, value2, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andReceiveUserNotBetween(Long value1, Long value2) {
            addCriterion("receive_user not between", value1, value2, "receiveUser");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeIsNull() {
            addCriterion("deliver_office is null");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeIsNotNull() {
            addCriterion("deliver_office is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeEqualTo(String value) {
            addCriterion("deliver_office =", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeNotEqualTo(String value) {
            addCriterion("deliver_office <>", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeGreaterThan(String value) {
            addCriterion("deliver_office >", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_office >=", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeLessThan(String value) {
            addCriterion("deliver_office <", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeLessThanOrEqualTo(String value) {
            addCriterion("deliver_office <=", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeLike(String value) {
            addCriterion("deliver_office like", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeNotLike(String value) {
            addCriterion("deliver_office not like", value, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeIn(List<String> values) {
            addCriterion("deliver_office in", values, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeNotIn(List<String> values) {
            addCriterion("deliver_office not in", values, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeBetween(String value1, String value2) {
            addCriterion("deliver_office between", value1, value2, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverOfficeNotBetween(String value1, String value2) {
            addCriterion("deliver_office not between", value1, value2, "deliverOffice");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdIsNull() {
            addCriterion("deliver_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdIsNotNull() {
            addCriterion("deliver_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdEqualTo(Long value) {
            addCriterion("deliver_dept_id =", value, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdNotEqualTo(Long value) {
            addCriterion("deliver_dept_id <>", value, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdGreaterThan(Long value) {
            addCriterion("deliver_dept_id >", value, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("deliver_dept_id >=", value, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdLessThan(Long value) {
            addCriterion("deliver_dept_id <", value, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdLessThanOrEqualTo(Long value) {
            addCriterion("deliver_dept_id <=", value, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdIn(List<Long> values) {
            addCriterion("deliver_dept_id in", values, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdNotIn(List<Long> values) {
            addCriterion("deliver_dept_id not in", values, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdBetween(Long value1, Long value2) {
            addCriterion("deliver_dept_id between", value1, value2, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIdNotBetween(Long value1, Long value2) {
            addCriterion("deliver_dept_id not between", value1, value2, "deliverDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdIsNull() {
            addCriterion("receive_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdIsNotNull() {
            addCriterion("receive_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdEqualTo(Long value) {
            addCriterion("receive_dept_id =", value, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdNotEqualTo(Long value) {
            addCriterion("receive_dept_id <>", value, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdGreaterThan(Long value) {
            addCriterion("receive_dept_id >", value, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("receive_dept_id >=", value, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdLessThan(Long value) {
            addCriterion("receive_dept_id <", value, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdLessThanOrEqualTo(Long value) {
            addCriterion("receive_dept_id <=", value, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdIn(List<Long> values) {
            addCriterion("receive_dept_id in", values, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdNotIn(List<Long> values) {
            addCriterion("receive_dept_id not in", values, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdBetween(Long value1, Long value2) {
            addCriterion("receive_dept_id between", value1, value2, "receiveDeptId");
            return (Criteria) this;
        }

        public Criteria andReceiveDeptIdNotBetween(Long value1, Long value2) {
            addCriterion("receive_dept_id not between", value1, value2, "receiveDeptId");
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