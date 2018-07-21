package org.quetzaco.archives.model;

public class OaDepartment {
    private Long orgid;

    private Long orglevel;

    private Long parentorgid;

    private String orgseq;

    private String orgname;

    private String orgno;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrglevel() {
        return orglevel;
    }

    public void setOrglevel(Long orglevel) {
        this.orglevel = orglevel;
    }

    public Long getParentorgid() {
        return parentorgid;
    }

    public void setParentorgid(Long parentorgid) {
        this.parentorgid = parentorgid;
    }

    public String getOrgseq() {
        return orgseq;
    }

    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq == null ? null : orgseq.trim();
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public String getOrgno() {
        return orgno;
    }

    public void setOrgno(String orgno) {
        this.orgno = orgno == null ? null : orgno.trim();
    }
}