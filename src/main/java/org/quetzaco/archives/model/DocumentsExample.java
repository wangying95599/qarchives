package org.quetzaco.archives.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DocumentsExample() {
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

        public Criteria andDocAttrIsNull() {
            addCriterion("doc_attr is null");
            return (Criteria) this;
        }

        public Criteria andDocAttrIsNotNull() {
            addCriterion("doc_attr is not null");
            return (Criteria) this;
        }

        public Criteria andDocAttrEqualTo(String value) {
            addCriterion("doc_attr =", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrNotEqualTo(String value) {
            addCriterion("doc_attr <>", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrGreaterThan(String value) {
            addCriterion("doc_attr >", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrGreaterThanOrEqualTo(String value) {
            addCriterion("doc_attr >=", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrLessThan(String value) {
            addCriterion("doc_attr <", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrLessThanOrEqualTo(String value) {
            addCriterion("doc_attr <=", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrLike(String value) {
            addCriterion("doc_attr like", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrNotLike(String value) {
            addCriterion("doc_attr not like", value, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrIn(List<String> values) {
            addCriterion("doc_attr in", values, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrNotIn(List<String> values) {
            addCriterion("doc_attr not in", values, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrBetween(String value1, String value2) {
            addCriterion("doc_attr between", value1, value2, "docAttr");
            return (Criteria) this;
        }

        public Criteria andDocAttrNotBetween(String value1, String value2) {
            addCriterion("doc_attr not between", value1, value2, "docAttr");
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

        public Criteria andEntityNumIsNull() {
            addCriterion("entity_num is null");
            return (Criteria) this;
        }

        public Criteria andEntityNumIsNotNull() {
            addCriterion("entity_num is not null");
            return (Criteria) this;
        }

        public Criteria andEntityNumEqualTo(Long value) {
            addCriterion("entity_num =", value, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumNotEqualTo(Long value) {
            addCriterion("entity_num <>", value, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumGreaterThan(Long value) {
            addCriterion("entity_num >", value, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumGreaterThanOrEqualTo(Long value) {
            addCriterion("entity_num >=", value, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumLessThan(Long value) {
            addCriterion("entity_num <", value, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumLessThanOrEqualTo(Long value) {
            addCriterion("entity_num <=", value, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumIn(List<Long> values) {
            addCriterion("entity_num in", values, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumNotIn(List<Long> values) {
            addCriterion("entity_num not in", values, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumBetween(Long value1, Long value2) {
            addCriterion("entity_num between", value1, value2, "entityNum");
            return (Criteria) this;
        }

        public Criteria andEntityNumNotBetween(Long value1, Long value2) {
            addCriterion("entity_num not between", value1, value2, "entityNum");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeIsNull() {
            addCriterion("file_createtime is null");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeIsNotNull() {
            addCriterion("file_createtime is not null");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeEqualTo(String value) {
            addCriterion("file_createtime =", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeNotEqualTo(String value) {
            addCriterion("file_createtime <>", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeGreaterThan(String value) {
            addCriterion("file_createtime >", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeGreaterThanOrEqualTo(String value) {
            addCriterion("file_createtime >=", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeLessThan(String value) {
            addCriterion("file_createtime <", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeLessThanOrEqualTo(String value) {
            addCriterion("file_createtime <=", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeLike(String value) {
            addCriterion("file_createtime like", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeNotLike(String value) {
            addCriterion("file_createtime not like", value, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeIn(List<String> values) {
            addCriterion("file_createtime in", values, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeNotIn(List<String> values) {
            addCriterion("file_createtime not in", values, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeBetween(String value1, String value2) {
            addCriterion("file_createtime between", value1, value2, "fileCreatetime");
            return (Criteria) this;
        }

        public Criteria andFileCreatetimeNotBetween(String value1, String value2) {
            addCriterion("file_createtime not between", value1, value2, "fileCreatetime");
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

        public Criteria andBeforeNumIsNull() {
            addCriterion("before_num is null");
            return (Criteria) this;
        }

        public Criteria andBeforeNumIsNotNull() {
            addCriterion("before_num is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeNumEqualTo(String value) {
            addCriterion("before_num =", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andOaSyncTypeEqualTo(String value) {
            addCriterion("oa_sync_type =", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumNotEqualTo(String value) {
            addCriterion("before_num <>", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumGreaterThan(String value) {
            addCriterion("before_num >", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumGreaterThanOrEqualTo(String value) {
            addCriterion("before_num >=", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumLessThan(String value) {
            addCriterion("before_num <", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumLessThanOrEqualTo(String value) {
            addCriterion("before_num <=", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumLike(String value) {
            addCriterion("before_num like", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumNotLike(String value) {
            addCriterion("before_num not like", value, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumIn(List<String> values) {
            addCriterion("before_num in", values, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumNotIn(List<String> values) {
            addCriterion("before_num not in", values, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumBetween(String value1, String value2) {
            addCriterion("before_num between", value1, value2, "beforeNum");
            return (Criteria) this;
        }

        public Criteria andBeforeNumNotBetween(String value1, String value2) {
            addCriterion("before_num not between", value1, value2, "beforeNum");
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

        public Criteria andDocumentLocalIdIsNull() {
            addCriterion("document_local_id is null");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdIsNotNull() {
            addCriterion("document_local_id is not null");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdEqualTo(String value) {
            addCriterion("document_local_id =", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdNotEqualTo(String value) {
            addCriterion("document_local_id <>", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdGreaterThan(String value) {
            addCriterion("document_local_id >", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdGreaterThanOrEqualTo(String value) {
            addCriterion("document_local_id >=", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdLessThan(String value) {
            addCriterion("document_local_id <", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdLessThanOrEqualTo(String value) {
            addCriterion("document_local_id <=", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdLike(String value) {
            addCriterion("document_local_id like", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdNotLike(String value) {
            addCriterion("document_local_id not like", value, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdIn(List<String> values) {
            addCriterion("document_local_id in", values, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdNotIn(List<String> values) {
            addCriterion("document_local_id not in", values, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdBetween(String value1, String value2) {
            addCriterion("document_local_id between", value1, value2, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentLocalIdNotBetween(String value1, String value2) {
            addCriterion("document_local_id not between", value1, value2, "documentLocalId");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeIsNull() {
            addCriterion("document_creatime is null");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeIsNotNull() {
            addCriterion("document_creatime is not null");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeEqualTo(String value) {
            addCriterion("document_creatime =", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeNotEqualTo(String value) {
            addCriterion("document_creatime <>", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeGreaterThan(String value) {
            addCriterion("document_creatime >", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeGreaterThanOrEqualTo(String value) {
            addCriterion("document_creatime >=", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeLessThan(String value) {
            addCriterion("document_creatime <", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeLessThanOrEqualTo(String value) {
            addCriterion("document_creatime <=", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeLike(String value) {
            addCriterion("document_creatime like", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeNotLike(String value) {
            addCriterion("document_creatime not like", value, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeIn(List<String> values) {
            addCriterion("document_creatime in", values, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeNotIn(List<String> values) {
            addCriterion("document_creatime not in", values, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeBetween(String value1, String value2) {
            addCriterion("document_creatime between", value1, value2, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andDocumentCreatimeNotBetween(String value1, String value2) {
            addCriterion("document_creatime not between", value1, value2, "documentCreatime");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdIsNull() {
            addCriterion("link_files_id is null");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdIsNotNull() {
            addCriterion("link_files_id is not null");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdEqualTo(String value) {
            addCriterion("link_files_id =", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdNotEqualTo(String value) {
            addCriterion("link_files_id <>", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdGreaterThan(String value) {
            addCriterion("link_files_id >", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdGreaterThanOrEqualTo(String value) {
            addCriterion("link_files_id >=", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdLessThan(String value) {
            addCriterion("link_files_id <", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdLessThanOrEqualTo(String value) {
            addCriterion("link_files_id <=", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdLike(String value) {
            addCriterion("link_files_id like", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdNotLike(String value) {
            addCriterion("link_files_id not like", value, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdIn(List<String> values) {
            addCriterion("link_files_id in", values, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdNotIn(List<String> values) {
            addCriterion("link_files_id not in", values, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdBetween(String value1, String value2) {
            addCriterion("link_files_id between", value1, value2, "linkFilesId");
            return (Criteria) this;
        }

        public Criteria andLinkFilesIdNotBetween(String value1, String value2) {
            addCriterion("link_files_id not between", value1, value2, "linkFilesId");
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

        public Criteria andSavaLocationIsNull() {
            addCriterion("sava_location is null");
            return (Criteria) this;
        }

        public Criteria andSavaLocationIsNotNull() {
            addCriterion("sava_location is not null");
            return (Criteria) this;
        }

        public Criteria andSavaLocationEqualTo(String value) {
            addCriterion("sava_location =", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationNotEqualTo(String value) {
            addCriterion("sava_location <>", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationGreaterThan(String value) {
            addCriterion("sava_location >", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationGreaterThanOrEqualTo(String value) {
            addCriterion("sava_location >=", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationLessThan(String value) {
            addCriterion("sava_location <", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationLessThanOrEqualTo(String value) {
            addCriterion("sava_location <=", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationLike(String value) {
            addCriterion("sava_location like", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationNotLike(String value) {
            addCriterion("sava_location not like", value, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationIn(List<String> values) {
            addCriterion("sava_location in", values, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationNotIn(List<String> values) {
            addCriterion("sava_location not in", values, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationBetween(String value1, String value2) {
            addCriterion("sava_location between", value1, value2, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andSavaLocationNotBetween(String value1, String value2) {
            addCriterion("sava_location not between", value1, value2, "savaLocation");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagIsNull() {
            addCriterion("arrange_flag is null");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagIsNotNull() {
            addCriterion("arrange_flag is not null");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagEqualTo(Boolean value) {
            addCriterion("arrange_flag =", value, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagNotEqualTo(Boolean value) {
            addCriterion("arrange_flag <>", value, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagGreaterThan(Boolean value) {
            addCriterion("arrange_flag >", value, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("arrange_flag >=", value, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagLessThan(Boolean value) {
            addCriterion("arrange_flag <", value, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("arrange_flag <=", value, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagIn(List<Boolean> values) {
            addCriterion("arrange_flag in", values, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagNotIn(List<Boolean> values) {
            addCriterion("arrange_flag not in", values, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("arrange_flag between", value1, value2, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andArrangeFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("arrange_flag not between", value1, value2, "arrangeFlag");
            return (Criteria) this;
        }

        public Criteria andDataSourceIsNull() {
            addCriterion("data_source is null");
            return (Criteria) this;
        }

        public Criteria andDataSourceIsNotNull() {
            addCriterion("data_source is not null");
            return (Criteria) this;
        }

        public Criteria andDataSourceEqualTo(String value) {
            addCriterion("data_source =", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotEqualTo(String value) {
            addCriterion("data_source <>", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceGreaterThan(String value) {
            addCriterion("data_source >", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceGreaterThanOrEqualTo(String value) {
            addCriterion("data_source >=", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLessThan(String value) {
            addCriterion("data_source <", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLessThanOrEqualTo(String value) {
            addCriterion("data_source <=", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLike(String value) {
            addCriterion("data_source like", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotLike(String value) {
            addCriterion("data_source not like", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceIn(List<String> values) {
            addCriterion("data_source in", values, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotIn(List<String> values) {
            addCriterion("data_source not in", values, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceBetween(String value1, String value2) {
            addCriterion("data_source between", value1, value2, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotBetween(String value1, String value2) {
            addCriterion("data_source not between", value1, value2, "dataSource");
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

        public Criteria andPaginalNumIsNull() {
            addCriterion("paginal_num is null");
            return (Criteria) this;
        }

        public Criteria andPaginalNumIsNotNull() {
            addCriterion("paginal_num is not null");
            return (Criteria) this;
        }

        public Criteria andPaginalNumEqualTo(Integer value) {
            addCriterion("paginal_num =", value, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumNotEqualTo(Integer value) {
            addCriterion("paginal_num <>", value, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumGreaterThan(Integer value) {
            addCriterion("paginal_num >", value, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("paginal_num >=", value, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumLessThan(Integer value) {
            addCriterion("paginal_num <", value, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumLessThanOrEqualTo(Integer value) {
            addCriterion("paginal_num <=", value, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumIn(List<Integer> values) {
            addCriterion("paginal_num in", values, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumNotIn(List<Integer> values) {
            addCriterion("paginal_num not in", values, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumBetween(Integer value1, Integer value2) {
            addCriterion("paginal_num between", value1, value2, "paginalNum");
            return (Criteria) this;
        }

        public Criteria andPaginalNumNotBetween(Integer value1, Integer value2) {
            addCriterion("paginal_num not between", value1, value2, "paginalNum");
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

        public Criteria andUpdatedDtEqualTo(String value) {
            addCriterion("updated_dt =", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtNotEqualTo(String value) {
            addCriterion("updated_dt <>", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtGreaterThan(String value) {
            addCriterion("updated_dt >", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtGreaterThanOrEqualTo(String value) {
            addCriterion("updated_dt >=", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtLessThan(String value) {
            addCriterion("updated_dt <", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtLessThanOrEqualTo(String value) {
            addCriterion("updated_dt <=", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtLike(String value) {
            addCriterion("updated_dt like", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtNotLike(String value) {
            addCriterion("updated_dt not like", value, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtIn(List<String> values) {
            addCriterion("updated_dt in", values, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtNotIn(List<String> values) {
            addCriterion("updated_dt not in", values, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtBetween(String value1, String value2) {
            addCriterion("updated_dt between", value1, value2, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andUpdatedDtNotBetween(String value1, String value2) {
            addCriterion("updated_dt not between", value1, value2, "updatedDt");
            return (Criteria) this;
        }

        public Criteria andArrangedByEqualTo(Long value) {
            addCriterion("arranged_by =", value, "arrangedBy");
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