package org.quetzaco.archives.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RecordExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNull() {
            addCriterion("dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNotNull() {
            addCriterion("dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeptIdEqualTo(Long value) {
            addCriterion("dept_id =", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotEqualTo(Long value) {
            addCriterion("dept_id <>", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThan(Long value) {
            addCriterion("dept_id >", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dept_id >=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThan(Long value) {
            addCriterion("dept_id <", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThanOrEqualTo(Long value) {
            addCriterion("dept_id <=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdIn(List<Long> values) {
            addCriterion("dept_id in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotIn(List<Long> values) {
            addCriterion("dept_id not in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdBetween(Long value1, Long value2) {
            addCriterion("dept_id between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotBetween(Long value1, Long value2) {
            addCriterion("dept_id not between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andFileNumIsNull() {
            addCriterion("file_num is null");
            return (Criteria) this;
        }

        public Criteria andFileNumIsNotNull() {
            addCriterion("file_num is not null");
            return (Criteria) this;
        }

        public Criteria andFileNumEqualTo(String value) {
            addCriterion("file_num =", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumNotEqualTo(String value) {
            addCriterion("file_num <>", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumGreaterThan(String value) {
            addCriterion("file_num >", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumGreaterThanOrEqualTo(String value) {
            addCriterion("file_num >=", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumLessThan(String value) {
            addCriterion("file_num <", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumLessThanOrEqualTo(String value) {
            addCriterion("file_num <=", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumLike(String value) {
            addCriterion("file_num like", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumNotLike(String value) {
            addCriterion("file_num not like", value, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumIn(List<String> values) {
            addCriterion("file_num in", values, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumNotIn(List<String> values) {
            addCriterion("file_num not in", values, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumBetween(String value1, String value2) {
            addCriterion("file_num between", value1, value2, "fileNum");
            return (Criteria) this;
        }

        public Criteria andFileNumNotBetween(String value1, String value2) {
            addCriterion("file_num not between", value1, value2, "fileNum");
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

        public Criteria andThemeWordIsNull() {
            addCriterion("theme_word is null");
            return (Criteria) this;
        }

        public Criteria andThemeWordIsNotNull() {
            addCriterion("theme_word is not null");
            return (Criteria) this;
        }

        public Criteria andThemeWordEqualTo(String value) {
            addCriterion("theme_word =", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordNotEqualTo(String value) {
            addCriterion("theme_word <>", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordGreaterThan(String value) {
            addCriterion("theme_word >", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordGreaterThanOrEqualTo(String value) {
            addCriterion("theme_word >=", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordLessThan(String value) {
            addCriterion("theme_word <", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordLessThanOrEqualTo(String value) {
            addCriterion("theme_word <=", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordLike(String value) {
            addCriterion("theme_word like", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordNotLike(String value) {
            addCriterion("theme_word not like", value, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordIn(List<String> values) {
            addCriterion("theme_word in", values, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordNotIn(List<String> values) {
            addCriterion("theme_word not in", values, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordBetween(String value1, String value2) {
            addCriterion("theme_word between", value1, value2, "themeWord");
            return (Criteria) this;
        }

        public Criteria andThemeWordNotBetween(String value1, String value2) {
            addCriterion("theme_word not between", value1, value2, "themeWord");
            return (Criteria) this;
        }

        public Criteria andResponsibleIsNull() {
            addCriterion("responsible is null");
            return (Criteria) this;
        }

        public Criteria andResponsibleIsNotNull() {
            addCriterion("responsible is not null");
            return (Criteria) this;
        }

        public Criteria andResponsibleEqualTo(String value) {
            addCriterion("responsible =", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleNotEqualTo(String value) {
            addCriterion("responsible <>", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleGreaterThan(String value) {
            addCriterion("responsible >", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleGreaterThanOrEqualTo(String value) {
            addCriterion("responsible >=", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleLessThan(String value) {
            addCriterion("responsible <", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleLessThanOrEqualTo(String value) {
            addCriterion("responsible <=", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleLike(String value) {
            addCriterion("responsible like", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleNotLike(String value) {
            addCriterion("responsible not like", value, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleIn(List<String> values) {
            addCriterion("responsible in", values, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleNotIn(List<String> values) {
            addCriterion("responsible not in", values, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleBetween(String value1, String value2) {
            addCriterion("responsible between", value1, value2, "responsible");
            return (Criteria) this;
        }

        public Criteria andResponsibleNotBetween(String value1, String value2) {
            addCriterion("responsible not between", value1, value2, "responsible");
            return (Criteria) this;
        }

        public Criteria andFondsIdIsNull() {
            addCriterion("fonds_id is null");
            return (Criteria) this;
        }

        public Criteria andFondsIdIsNotNull() {
            addCriterion("fonds_id is not null");
            return (Criteria) this;
        }

        public Criteria andFondsIdEqualTo(String value) {
            addCriterion("fonds_id =", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdNotEqualTo(String value) {
            addCriterion("fonds_id <>", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdGreaterThan(String value) {
            addCriterion("fonds_id >", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdGreaterThanOrEqualTo(String value) {
            addCriterion("fonds_id >=", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdLessThan(String value) {
            addCriterion("fonds_id <", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdLessThanOrEqualTo(String value) {
            addCriterion("fonds_id <=", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdLike(String value) {
            addCriterion("fonds_id like", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdNotLike(String value) {
            addCriterion("fonds_id not like", value, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdIn(List<String> values) {
            addCriterion("fonds_id in", values, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdNotIn(List<String> values) {
            addCriterion("fonds_id not in", values, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdBetween(String value1, String value2) {
            addCriterion("fonds_id between", value1, value2, "fondsId");
            return (Criteria) this;
        }

        public Criteria andFondsIdNotBetween(String value1, String value2) {
            addCriterion("fonds_id not between", value1, value2, "fondsId");
            return (Criteria) this;
        }

        public Criteria andImportanceIsNull() {
            addCriterion("importance is null");
            return (Criteria) this;
        }

        public Criteria andImportanceIsNotNull() {
            addCriterion("importance is not null");
            return (Criteria) this;
        }

        public Criteria andImportanceEqualTo(String value) {
            addCriterion("importance =", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceNotEqualTo(String value) {
            addCriterion("importance <>", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceGreaterThan(String value) {
            addCriterion("importance >", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceGreaterThanOrEqualTo(String value) {
            addCriterion("importance >=", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceLessThan(String value) {
            addCriterion("importance <", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceLessThanOrEqualTo(String value) {
            addCriterion("importance <=", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceLike(String value) {
            addCriterion("importance like", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceNotLike(String value) {
            addCriterion("importance not like", value, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceIn(List<String> values) {
            addCriterion("importance in", values, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceNotIn(List<String> values) {
            addCriterion("importance not in", values, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceBetween(String value1, String value2) {
            addCriterion("importance between", value1, value2, "importance");
            return (Criteria) this;
        }

        public Criteria andImportanceNotBetween(String value1, String value2) {
            addCriterion("importance not between", value1, value2, "importance");
            return (Criteria) this;
        }

        public Criteria andAppendicesIsNull() {
            addCriterion("appendices is null");
            return (Criteria) this;
        }

        public Criteria andAppendicesIsNotNull() {
            addCriterion("appendices is not null");
            return (Criteria) this;
        }

        public Criteria andAppendicesEqualTo(Long value) {
            addCriterion("appendices =", value, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesNotEqualTo(Long value) {
            addCriterion("appendices <>", value, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesGreaterThan(Long value) {
            addCriterion("appendices >", value, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesGreaterThanOrEqualTo(Long value) {
            addCriterion("appendices >=", value, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesLessThan(Long value) {
            addCriterion("appendices <", value, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesLessThanOrEqualTo(Long value) {
            addCriterion("appendices <=", value, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesIn(List<Long> values) {
            addCriterion("appendices in", values, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesNotIn(List<Long> values) {
            addCriterion("appendices not in", values, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesBetween(Long value1, Long value2) {
            addCriterion("appendices between", value1, value2, "appendices");
            return (Criteria) this;
        }

        public Criteria andAppendicesNotBetween(Long value1, Long value2) {
            addCriterion("appendices not between", value1, value2, "appendices");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeIsNull() {
            addCriterion("archive_type is null");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeIsNotNull() {
            addCriterion("archive_type is not null");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeEqualTo(String value) {
            addCriterion("archive_type =", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeNotEqualTo(String value) {
            addCriterion("archive_type <>", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeGreaterThan(String value) {
            addCriterion("archive_type >", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeGreaterThanOrEqualTo(String value) {
            addCriterion("archive_type >=", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeLessThan(String value) {
            addCriterion("archive_type <", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeLessThanOrEqualTo(String value) {
            addCriterion("archive_type <=", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeLike(String value) {
            addCriterion("archive_type like", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeNotLike(String value) {
            addCriterion("archive_type not like", value, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeIn(List<String> values) {
            addCriterion("archive_type in", values, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeNotIn(List<String> values) {
            addCriterion("archive_type not in", values, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeBetween(String value1, String value2) {
            addCriterion("archive_type between", value1, value2, "archiveType");
            return (Criteria) this;
        }

        public Criteria andArchiveTypeNotBetween(String value1, String value2) {
            addCriterion("archive_type not between", value1, value2, "archiveType");
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

        public Criteria andDeliverDeptIsNull() {
            addCriterion("deliver_dept is null");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIsNotNull() {
            addCriterion("deliver_dept is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptEqualTo(String value) {
            addCriterion("deliver_dept =", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptNotEqualTo(String value) {
            addCriterion("deliver_dept <>", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptGreaterThan(String value) {
            addCriterion("deliver_dept >", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_dept >=", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptLessThan(String value) {
            addCriterion("deliver_dept <", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptLessThanOrEqualTo(String value) {
            addCriterion("deliver_dept <=", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptLike(String value) {
            addCriterion("deliver_dept like", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptNotLike(String value) {
            addCriterion("deliver_dept not like", value, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptIn(List<String> values) {
            addCriterion("deliver_dept in", values, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptNotIn(List<String> values) {
            addCriterion("deliver_dept not in", values, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptBetween(String value1, String value2) {
            addCriterion("deliver_dept between", value1, value2, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andDeliverDeptNotBetween(String value1, String value2) {
            addCriterion("deliver_dept not between", value1, value2, "deliverDept");
            return (Criteria) this;
        }

        public Criteria andArchiveYearIsNull() {
            addCriterion("archive_year is null");
            return (Criteria) this;
        }

        public Criteria andArchiveYearIsNotNull() {
            addCriterion("archive_year is not null");
            return (Criteria) this;
        }

        public Criteria andArchiveYearEqualTo(String value) {
            addCriterion("archive_year =", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearNotEqualTo(String value) {
            addCriterion("archive_year <>", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearGreaterThan(String value) {
            addCriterion("archive_year >", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearGreaterThanOrEqualTo(String value) {
            addCriterion("archive_year >=", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearLessThan(String value) {
            addCriterion("archive_year <", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearLessThanOrEqualTo(String value) {
            addCriterion("archive_year <=", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearLike(String value) {
            addCriterion("archive_year like", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearNotLike(String value) {
            addCriterion("archive_year not like", value, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearIn(List<String> values) {
            addCriterion("archive_year in", values, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearNotIn(List<String> values) {
            addCriterion("archive_year not in", values, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearBetween(String value1, String value2) {
            addCriterion("archive_year between", value1, value2, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andArchiveYearNotBetween(String value1, String value2) {
            addCriterion("archive_year not between", value1, value2, "archiveYear");
            return (Criteria) this;
        }

        public Criteria andReserveDurationIsNull() {
            addCriterion("reserve_duration is null");
            return (Criteria) this;
        }

        public Criteria andReserveDurationIsNotNull() {
            addCriterion("reserve_duration is not null");
            return (Criteria) this;
        }

        public Criteria andReserveDurationEqualTo(String value) {
            addCriterion("reserve_duration =", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationNotEqualTo(String value) {
            addCriterion("reserve_duration <>", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationGreaterThan(String value) {
            addCriterion("reserve_duration >", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationGreaterThanOrEqualTo(String value) {
            addCriterion("reserve_duration >=", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationLessThan(String value) {
            addCriterion("reserve_duration <", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationLessThanOrEqualTo(String value) {
            addCriterion("reserve_duration <=", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationLike(String value) {
            addCriterion("reserve_duration like", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationNotLike(String value) {
            addCriterion("reserve_duration not like", value, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationIn(List<String> values) {
            addCriterion("reserve_duration in", values, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationNotIn(List<String> values) {
            addCriterion("reserve_duration not in", values, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationBetween(String value1, String value2) {
            addCriterion("reserve_duration between", value1, value2, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andReserveDurationNotBetween(String value1, String value2) {
            addCriterion("reserve_duration not between", value1, value2, "reserveDuration");
            return (Criteria) this;
        }

        public Criteria andSaveNumIsNull() {
            addCriterion("save_num is null");
            return (Criteria) this;
        }

        public Criteria andSaveNumIsNotNull() {
            addCriterion("save_num is not null");
            return (Criteria) this;
        }

        public Criteria andSaveNumEqualTo(Long value) {
            addCriterion("save_num =", value, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumNotEqualTo(Long value) {
            addCriterion("save_num <>", value, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumGreaterThan(Long value) {
            addCriterion("save_num >", value, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumGreaterThanOrEqualTo(Long value) {
            addCriterion("save_num >=", value, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumLessThan(Long value) {
            addCriterion("save_num <", value, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumLessThanOrEqualTo(Long value) {
            addCriterion("save_num <=", value, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumIn(List<Long> values) {
            addCriterion("save_num in", values, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumNotIn(List<Long> values) {
            addCriterion("save_num not in", values, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumBetween(Long value1, Long value2) {
            addCriterion("save_num between", value1, value2, "saveNum");
            return (Criteria) this;
        }

        public Criteria andSaveNumNotBetween(Long value1, Long value2) {
            addCriterion("save_num not between", value1, value2, "saveNum");
            return (Criteria) this;
        }

        public Criteria andReserveLocationIsNull() {
            addCriterion("reserve_location is null");
            return (Criteria) this;
        }

        public Criteria andReserveLocationIsNotNull() {
            addCriterion("reserve_location is not null");
            return (Criteria) this;
        }

        public Criteria andReserveLocationEqualTo(String value) {
            addCriterion("reserve_location =", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationNotEqualTo(String value) {
            addCriterion("reserve_location <>", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationGreaterThan(String value) {
            addCriterion("reserve_location >", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationGreaterThanOrEqualTo(String value) {
            addCriterion("reserve_location >=", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationLessThan(String value) {
            addCriterion("reserve_location <", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationLessThanOrEqualTo(String value) {
            addCriterion("reserve_location <=", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationLike(String value) {
            addCriterion("reserve_location like", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationNotLike(String value) {
            addCriterion("reserve_location not like", value, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationIn(List<String> values) {
            addCriterion("reserve_location in", values, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationNotIn(List<String> values) {
            addCriterion("reserve_location not in", values, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationBetween(String value1, String value2) {
            addCriterion("reserve_location between", value1, value2, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andReserveLocationNotBetween(String value1, String value2) {
            addCriterion("reserve_location not between", value1, value2, "reserveLocation");
            return (Criteria) this;
        }

        public Criteria andArchiveDateIsNull() {
            addCriterion("archive_date is null");
            return (Criteria) this;
        }

        public Criteria andArchiveDateIsNotNull() {
            addCriterion("archive_date is not null");
            return (Criteria) this;
        }

        public Criteria andArchiveDateEqualTo(String value) {
            addCriterion("archive_date =", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateNotEqualTo(String value) {
            addCriterion("archive_date <>", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateGreaterThan(String value) {
            addCriterion("archive_date >", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateGreaterThanOrEqualTo(String value) {
            addCriterion("archive_date >=", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateLessThan(String value) {
            addCriterion("archive_date <", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateLessThanOrEqualTo(String value) {
            addCriterion("archive_date <=", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateLike(String value) {
            addCriterion("archive_date like", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateNotLike(String value) {
            addCriterion("archive_date not like", value, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateIn(List<String> values) {
            addCriterion("archive_date in", values, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateNotIn(List<String> values) {
            addCriterion("archive_date not in", values, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateBetween(String value1, String value2) {
            addCriterion("archive_date between", value1, value2, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andArchiveDateNotBetween(String value1, String value2) {
            addCriterion("archive_date not between", value1, value2, "archiveDate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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

        public Criteria andIsArchiveIsNull() {
            addCriterion("is_archive is null");
            return (Criteria) this;
        }

        public Criteria andIsArchiveIsNotNull() {
            addCriterion("is_archive is not null");
            return (Criteria) this;
        }

        public Criteria andIsArchiveEqualTo(Boolean value) {
            addCriterion("is_archive =", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveNotEqualTo(Boolean value) {
            addCriterion("is_archive <>", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveGreaterThan(Boolean value) {
            addCriterion("is_archive >", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_archive >=", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveLessThan(Boolean value) {
            addCriterion("is_archive <", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveLessThanOrEqualTo(Boolean value) {
            addCriterion("is_archive <=", value, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveIn(List<Boolean> values) {
            addCriterion("is_archive in", values, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveNotIn(List<Boolean> values) {
            addCriterion("is_archive not in", values, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveBetween(Boolean value1, Boolean value2) {
            addCriterion("is_archive between", value1, value2, "isArchive");
            return (Criteria) this;
        }

        public Criteria andIsArchiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_archive not between", value1, value2, "isArchive");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andDeliverDateIsNull() {
            addCriterion("deliver_date is null");
            return (Criteria) this;
        }

        public Criteria andDeliverDateIsNotNull() {
            addCriterion("deliver_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverDateEqualTo(Date value) {
            addCriterion("deliver_date =", value, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateNotEqualTo(Date value) {
            addCriterion("deliver_date <>", value, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateGreaterThan(Date value) {
            addCriterion("deliver_date >", value, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateGreaterThanOrEqualTo(Date value) {
            addCriterion("deliver_date >=", value, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateLessThan(Date value) {
            addCriterion("deliver_date <", value, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateLessThanOrEqualTo(Date value) {
            addCriterion("deliver_date <=", value, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateIn(List<Date> values) {
            addCriterion("deliver_date in", values, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateNotIn(List<Date> values) {
            addCriterion("deliver_date not in", values, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateBetween(Date value1, Date value2) {
            addCriterion("deliver_date between", value1, value2, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDateNotBetween(Date value1, Date value2) {
            addCriterion("deliver_date not between", value1, value2, "deliverDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDtIsNull() {
            addCriterion("created_dt is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDtIsNotNull() {
            addCriterion("created_dt is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDtEqualTo(Date value) {
            addCriterion("created_dt =", value, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtNotEqualTo(Date value) {
            addCriterion("created_dt <>", value, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtGreaterThan(Date value) {
            addCriterion("created_dt >", value, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_dt >=", value, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtLessThan(Date value) {
            addCriterion("created_dt <", value, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtLessThanOrEqualTo(Date value) {
            addCriterion("created_dt <=", value, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtIn(List<Date> values) {
            addCriterion("created_dt in", values, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtNotIn(List<Date> values) {
            addCriterion("created_dt not in", values, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtBetween(Date value1, Date value2) {
            addCriterion("created_dt between", value1, value2, "createdDt");
            return (Criteria) this;
        }

        public Criteria andCreatedDtNotBetween(Date value1, Date value2) {
            addCriterion("created_dt not between", value1, value2, "createdDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtIsNull() {
            addCriterion("updated_dt is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtIsNotNull() {
            addCriterion("updated_dt is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtEqualTo(Date value) {
            addCriterion("updated_dt =", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtNotEqualTo(Date value) {
            addCriterion("updated_dt <>", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtGreaterThan(Date value) {
            addCriterion("updated_dt >", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_dt >=", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtLessThan(Date value) {
            addCriterion("updated_dt <", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtLessThanOrEqualTo(Date value) {
            addCriterion("updated_dt <=", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtIn(List<Date> values) {
            addCriterion("updated_dt in", values, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtNotIn(List<Date> values) {
            addCriterion("updated_dt not in", values, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtBetween(Date value1, Date value2) {
            addCriterion("updated_dt between", value1, value2, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtNotBetween(Date value1, Date value2) {
            addCriterion("updated_dt not between", value1, value2, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andItemNumIsNull() {
            addCriterion("item_num is null");
            return (Criteria) this;
        }

        public Criteria andItemNumIsNotNull() {
            addCriterion("item_num is not null");
            return (Criteria) this;
        }

        public Criteria andItemNumEqualTo(String value) {
            addCriterion("item_num =", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumNotEqualTo(String value) {
            addCriterion("item_num <>", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumGreaterThan(String value) {
            addCriterion("item_num >", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumGreaterThanOrEqualTo(String value) {
            addCriterion("item_num >=", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumLessThan(String value) {
            addCriterion("item_num <", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumLessThanOrEqualTo(String value) {
            addCriterion("item_num <=", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumLike(String value) {
            addCriterion("item_num like", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumNotLike(String value) {
            addCriterion("item_num not like", value, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumIn(List<String> values) {
            addCriterion("item_num in", values, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumNotIn(List<String> values) {
            addCriterion("item_num not in", values, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumBetween(String value1, String value2) {
            addCriterion("item_num between", value1, value2, "itemNum");
            return (Criteria) this;
        }

        public Criteria andItemNumNotBetween(String value1, String value2) {
            addCriterion("item_num not between", value1, value2, "itemNum");
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