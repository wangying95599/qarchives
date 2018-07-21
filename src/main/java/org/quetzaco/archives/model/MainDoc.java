package org.quetzaco.archives.model;

import java.util.Date;

public class MainDoc {
    private String mid;

    private String docName;

    private Date createdDt;

    private String updateFlag;

    private String archiveLoginName;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName == null ? null : docName.trim();
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag == null ? null : updateFlag.trim();
    }

    public String getArchiveLoginName() {
        return archiveLoginName;
    }

    public void setArchiveLoginName(String archiveLoginName) {
        this.archiveLoginName = archiveLoginName == null ? null : archiveLoginName.trim();
    }
}