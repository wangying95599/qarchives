package org.quetzaco.archives.model;

import java.util.ArrayList;
import java.util.List;

public class FlowFormLendingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowFormLendingExample() {
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

        public Criteria andDepIdIsNull() {
            addCriterion("dep_id is null");
            return (Criteria) this;
        }

        public Criteria andDepIdIsNotNull() {
            addCriterion("dep_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepIdEqualTo(Long value) {
            addCriterion("dep_id =", value, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdNotEqualTo(Long value) {
            addCriterion("dep_id <>", value, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdGreaterThan(Long value) {
            addCriterion("dep_id >", value, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dep_id >=", value, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdLessThan(Long value) {
            addCriterion("dep_id <", value, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdLessThanOrEqualTo(Long value) {
            addCriterion("dep_id <=", value, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdIn(List<Long> values) {
            addCriterion("dep_id in", values, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdNotIn(List<Long> values) {
            addCriterion("dep_id not in", values, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdBetween(Long value1, Long value2) {
            addCriterion("dep_id between", value1, value2, "depId");
            return (Criteria) this;
        }

        public Criteria andDepIdNotBetween(Long value1, Long value2) {
            addCriterion("dep_id not between", value1, value2, "depId");
            return (Criteria) this;
        }

        public Criteria andLendingUserIsNull() {
            addCriterion("lending_user is null");
            return (Criteria) this;
        }

        public Criteria andLendingUserIsNotNull() {
            addCriterion("lending_user is not null");
            return (Criteria) this;
        }

        public Criteria andLendingUserEqualTo(String value) {
            addCriterion("lending_user =", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserNotEqualTo(String value) {
            addCriterion("lending_user <>", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserGreaterThan(String value) {
            addCriterion("lending_user >", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserGreaterThanOrEqualTo(String value) {
            addCriterion("lending_user >=", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserLessThan(String value) {
            addCriterion("lending_user <", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserLessThanOrEqualTo(String value) {
            addCriterion("lending_user <=", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserLike(String value) {
            addCriterion("lending_user like", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserNotLike(String value) {
            addCriterion("lending_user not like", value, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserIn(List<String> values) {
            addCriterion("lending_user in", values, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserNotIn(List<String> values) {
            addCriterion("lending_user not in", values, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserBetween(String value1, String value2) {
            addCriterion("lending_user between", value1, value2, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingUserNotBetween(String value1, String value2) {
            addCriterion("lending_user not between", value1, value2, "lendingUser");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionIsNull() {
            addCriterion("lending_permission is null");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionIsNotNull() {
            addCriterion("lending_permission is not null");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionEqualTo(String value) {
            addCriterion("lending_permission =", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionNotEqualTo(String value) {
            addCriterion("lending_permission <>", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionGreaterThan(String value) {
            addCriterion("lending_permission >", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionGreaterThanOrEqualTo(String value) {
            addCriterion("lending_permission >=", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionLessThan(String value) {
            addCriterion("lending_permission <", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionLessThanOrEqualTo(String value) {
            addCriterion("lending_permission <=", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionLike(String value) {
            addCriterion("lending_permission like", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionNotLike(String value) {
            addCriterion("lending_permission not like", value, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionIn(List<String> values) {
            addCriterion("lending_permission in", values, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionNotIn(List<String> values) {
            addCriterion("lending_permission not in", values, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionBetween(String value1, String value2) {
            addCriterion("lending_permission between", value1, value2, "lendingPermission");
            return (Criteria) this;
        }

        public Criteria andLendingPermissionNotBetween(String value1, String value2) {
            addCriterion("lending_permission not between", value1, value2, "lendingPermission");
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

        public Criteria andManagerIdIsNull() {
            addCriterion("manager_id is null");
            return (Criteria) this;
        }

        public Criteria andManagerIdIsNotNull() {
            addCriterion("manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andManagerIdEqualTo(Long value) {
            addCriterion("manager_id =", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotEqualTo(Long value) {
            addCriterion("manager_id <>", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThan(Long value) {
            addCriterion("manager_id >", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("manager_id >=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThan(Long value) {
            addCriterion("manager_id <", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThanOrEqualTo(Long value) {
            addCriterion("manager_id <=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdIn(List<Long> values) {
            addCriterion("manager_id in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotIn(List<Long> values) {
            addCriterion("manager_id not in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdBetween(Long value1, Long value2) {
            addCriterion("manager_id between", value1, value2, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotBetween(Long value1, Long value2) {
            addCriterion("manager_id not between", value1, value2, "managerId");
            return (Criteria) this;
        }

        public Criteria andLendingTypeIsNull() {
            addCriterion("lending_type is null");
            return (Criteria) this;
        }

        public Criteria andLendingTypeIsNotNull() {
            addCriterion("lending_type is not null");
            return (Criteria) this;
        }

        public Criteria andLendingTypeEqualTo(String value) {
            addCriterion("lending_type =", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeNotEqualTo(String value) {
            addCriterion("lending_type <>", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeGreaterThan(String value) {
            addCriterion("lending_type >", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeGreaterThanOrEqualTo(String value) {
            addCriterion("lending_type >=", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeLessThan(String value) {
            addCriterion("lending_type <", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeLessThanOrEqualTo(String value) {
            addCriterion("lending_type <=", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeLike(String value) {
            addCriterion("lending_type like", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeNotLike(String value) {
            addCriterion("lending_type not like", value, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeIn(List<String> values) {
            addCriterion("lending_type in", values, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeNotIn(List<String> values) {
            addCriterion("lending_type not in", values, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeBetween(String value1, String value2) {
            addCriterion("lending_type between", value1, value2, "lendingType");
            return (Criteria) this;
        }

        public Criteria andLendingTypeNotBetween(String value1, String value2) {
            addCriterion("lending_type not between", value1, value2, "lendingType");
            return (Criteria) this;
        }

        public Criteria andHostIdIsNull() {
            addCriterion("host_id is null");
            return (Criteria) this;
        }

        public Criteria andHostIdIsNotNull() {
            addCriterion("host_id is not null");
            return (Criteria) this;
        }

        public Criteria andHostIdEqualTo(Long value) {
            addCriterion("host_id =", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotEqualTo(Long value) {
            addCriterion("host_id <>", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdGreaterThan(Long value) {
            addCriterion("host_id >", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdGreaterThanOrEqualTo(Long value) {
            addCriterion("host_id >=", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdLessThan(Long value) {
            addCriterion("host_id <", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdLessThanOrEqualTo(Long value) {
            addCriterion("host_id <=", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdIn(List<Long> values) {
            addCriterion("host_id in", values, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotIn(List<Long> values) {
            addCriterion("host_id not in", values, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdBetween(Long value1, Long value2) {
            addCriterion("host_id between", value1, value2, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotBetween(Long value1, Long value2) {
            addCriterion("host_id not between", value1, value2, "hostId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdIsNull() {
            addCriterion("loan_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdIsNotNull() {
            addCriterion("loan_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdEqualTo(Long value) {
            addCriterion("loan_dept_id =", value, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdNotEqualTo(Long value) {
            addCriterion("loan_dept_id <>", value, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdGreaterThan(Long value) {
            addCriterion("loan_dept_id >", value, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("loan_dept_id >=", value, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdLessThan(Long value) {
            addCriterion("loan_dept_id <", value, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdLessThanOrEqualTo(Long value) {
            addCriterion("loan_dept_id <=", value, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdIn(List<Long> values) {
            addCriterion("loan_dept_id in", values, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdNotIn(List<Long> values) {
            addCriterion("loan_dept_id not in", values, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdBetween(Long value1, Long value2) {
            addCriterion("loan_dept_id between", value1, value2, "loanDeptId");
            return (Criteria) this;
        }

        public Criteria andLoanDeptIdNotBetween(Long value1, Long value2) {
            addCriterion("loan_dept_id not between", value1, value2, "loanDeptId");
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