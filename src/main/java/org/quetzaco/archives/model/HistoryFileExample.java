package org.quetzaco.archives.model;

import java.util.ArrayList;
import java.util.List;

public class HistoryFileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HistoryFileExample() {
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

        public Criteria andFileTypeIsNull() {
            addCriterion("file_type is null");
            return (Criteria) this;
        }

        public Criteria andFileTypeIsNotNull() {
            addCriterion("file_type is not null");
            return (Criteria) this;
        }

        public Criteria andFileTypeEqualTo(String value) {
            addCriterion("file_type =", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotEqualTo(String value) {
            addCriterion("file_type <>", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThan(String value) {
            addCriterion("file_type >", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThanOrEqualTo(String value) {
            addCriterion("file_type >=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThan(String value) {
            addCriterion("file_type <", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThanOrEqualTo(String value) {
            addCriterion("file_type <=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLike(String value) {
            addCriterion("file_type like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotLike(String value) {
            addCriterion("file_type not like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeIn(List<String> values) {
            addCriterion("file_type in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotIn(List<String> values) {
            addCriterion("file_type not in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeBetween(String value1, String value2) {
            addCriterion("file_type between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotBetween(String value1, String value2) {
            addCriterion("file_type not between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andEntryIdIsNull() {
            addCriterion("entry_id is null");
            return (Criteria) this;
        }

        public Criteria andEntryIdIsNotNull() {
            addCriterion("entry_id is not null");
            return (Criteria) this;
        }

        public Criteria andEntryIdEqualTo(String value) {
            addCriterion("entry_id =", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdNotEqualTo(String value) {
            addCriterion("entry_id <>", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdGreaterThan(String value) {
            addCriterion("entry_id >", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdGreaterThanOrEqualTo(String value) {
            addCriterion("entry_id >=", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdLessThan(String value) {
            addCriterion("entry_id <", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdLessThanOrEqualTo(String value) {
            addCriterion("entry_id <=", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdLike(String value) {
            addCriterion("entry_id like", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdNotLike(String value) {
            addCriterion("entry_id not like", value, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdIn(List<String> values) {
            addCriterion("entry_id in", values, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdNotIn(List<String> values) {
            addCriterion("entry_id not in", values, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdBetween(String value1, String value2) {
            addCriterion("entry_id between", value1, value2, "entryId");
            return (Criteria) this;
        }

        public Criteria andEntryIdNotBetween(String value1, String value2) {
            addCriterion("entry_id not between", value1, value2, "entryId");
            return (Criteria) this;
        }

        public Criteria andFileTitleIsNull() {
            addCriterion("file_title is null");
            return (Criteria) this;
        }

        public Criteria andFileTitleIsNotNull() {
            addCriterion("file_title is not null");
            return (Criteria) this;
        }

        public Criteria andFileTitleEqualTo(String value) {
            addCriterion("file_title =", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleNotEqualTo(String value) {
            addCriterion("file_title <>", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleGreaterThan(String value) {
            addCriterion("file_title >", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleGreaterThanOrEqualTo(String value) {
            addCriterion("file_title >=", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleLessThan(String value) {
            addCriterion("file_title <", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleLessThanOrEqualTo(String value) {
            addCriterion("file_title <=", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleLike(String value) {
            addCriterion("file_title like", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleNotLike(String value) {
            addCriterion("file_title not like", value, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleIn(List<String> values) {
            addCriterion("file_title in", values, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleNotIn(List<String> values) {
            addCriterion("file_title not in", values, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleBetween(String value1, String value2) {
            addCriterion("file_title between", value1, value2, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileTitleNotBetween(String value1, String value2) {
            addCriterion("file_title not between", value1, value2, "fileTitle");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNull() {
            addCriterion("file_size is null");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNotNull() {
            addCriterion("file_size is not null");
            return (Criteria) this;
        }

        public Criteria andFileSizeEqualTo(String value) {
            addCriterion("file_size =", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotEqualTo(String value) {
            addCriterion("file_size <>", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThan(String value) {
            addCriterion("file_size >", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThanOrEqualTo(String value) {
            addCriterion("file_size >=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThan(String value) {
            addCriterion("file_size <", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThanOrEqualTo(String value) {
            addCriterion("file_size <=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLike(String value) {
            addCriterion("file_size like", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotLike(String value) {
            addCriterion("file_size not like", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeIn(List<String> values) {
            addCriterion("file_size in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotIn(List<String> values) {
            addCriterion("file_size not in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeBetween(String value1, String value2) {
            addCriterion("file_size between", value1, value2, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotBetween(String value1, String value2) {
            addCriterion("file_size not between", value1, value2, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("file_path is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("file_path is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("file_path =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("file_path <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("file_path >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("file_path >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("file_path <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("file_path <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("file_path like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("file_path not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("file_path in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("file_path not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("file_path between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("file_path not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("file_name is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("file_name is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("file_name =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("file_name <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("file_name >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("file_name >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("file_name <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("file_name <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("file_name like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("file_name not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("file_name in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("file_name not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("file_name between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("file_name not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileSuffixIsNull() {
            addCriterion("file_suffix is null");
            return (Criteria) this;
        }

        public Criteria andFileSuffixIsNotNull() {
            addCriterion("file_suffix is not null");
            return (Criteria) this;
        }

        public Criteria andFileSuffixEqualTo(String value) {
            addCriterion("file_suffix =", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotEqualTo(String value) {
            addCriterion("file_suffix <>", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixGreaterThan(String value) {
            addCriterion("file_suffix >", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixGreaterThanOrEqualTo(String value) {
            addCriterion("file_suffix >=", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixLessThan(String value) {
            addCriterion("file_suffix <", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixLessThanOrEqualTo(String value) {
            addCriterion("file_suffix <=", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixLike(String value) {
            addCriterion("file_suffix like", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotLike(String value) {
            addCriterion("file_suffix not like", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixIn(List<String> values) {
            addCriterion("file_suffix in", values, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotIn(List<String> values) {
            addCriterion("file_suffix not in", values, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixBetween(String value1, String value2) {
            addCriterion("file_suffix between", value1, value2, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotBetween(String value1, String value2) {
            addCriterion("file_suffix not between", value1, value2, "fileSuffix");
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