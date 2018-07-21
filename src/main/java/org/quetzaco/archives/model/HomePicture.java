package org.quetzaco.archives.model;

import java.util.Date;

public class HomePicture {
    private Integer id;

    private String location;

    private String content;

    private Date createdDt;

    private Date updatedDt;

    private Boolean recordFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Boolean getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Boolean recordFlag) {
        this.recordFlag = recordFlag;
    }
}