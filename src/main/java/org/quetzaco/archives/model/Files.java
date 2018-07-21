package org.quetzaco.archives.model;

import io.searchbox.annotations.JestId;

import java.util.Date;

public class Files {
    private Long id;

    private String docId;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    private Long deptId;



    private String fileType;

    private String location;

    private Date uploadDate;

    private String fileName;
    @JestId
    private String fileId;

    private Boolean recordFlag;

    private String content;

    private String archiveType;

    private String archiveYear;
    
    private String info;

    public Long getArrangedBy() {
        return arrangedBy;
    }

    public void setArrangedBy(Long arrangedBy) {
        this.arrangedBy = arrangedBy;
    }

    private Long arrangedBy;
    
    public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getArchiveType() {
        return archiveType;
    }

    public void setArchiveType(String archiveType) {
        this.archiveType = archiveType;
    }

    public String getArchiveYear() {
        return archiveYear;
    }

    public void setArchiveYear(String archiveYear) {
        this.archiveYear = archiveYear;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId == null ? null : docId.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public Boolean getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Boolean recordFlag) {
        this.recordFlag = recordFlag;
    }

    @Override
    public String toString() {
        return "Files{" +
                "docId='" + docId + '\'' +
                ", id=" + id +
                ", fileType='" + fileType + '\'' +
                ", location='" + location + '\'' +
                ", uploadDate=" + uploadDate +
                ", fileName='" + fileName + '\'' +
                ", fileId='" + fileId + '\'' +
                ", recordFlag=" + recordFlag +
                '}';
    }
}