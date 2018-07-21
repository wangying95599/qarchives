package org.quetzaco.archives.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.searchbox.annotations.JestId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Documents implements PrimaryKey, Comparable{
    @JestId
    private Long id;

    private String fileName;

    private String fileNum;

    private Long deptId;

    private String title;

    private String docAttr;

    private String responsible;

    private String fondsId;

    private String importance;

    private Long appendices;

    private String archiveType;

    private String deliverUser;

    private String deliverDept;

    private String archiveYear;

    private String reserveDuration;

    private Long entityNum;

    private String fileCreatetime;

    private String reserveLocation;

    private String newReserveLocation;

    public String getOldFileNum() {
        return oldFileNum;
    }

    public void setOldFileNum(String oldFileNum) {
        this.oldFileNum = oldFileNum;
    }

    public String getNewFileNum() {
        return newFileNum;
    }

    public void setNewFileNum(String newFileNum) {
        this.newFileNum = newFileNum;
    }

    private String oldFileNum;

    private String newFileNum;

    public String getNewReserveLocation() {
        return newReserveLocation;
    }

    public void setNewReserveLocation(String newReserveLocation) {
        this.newReserveLocation = newReserveLocation;
    }
    
    private String location;


    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	private String lendFlag;
	private String lendFlagDesc;
	private String type;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLendFlag() {
		return lendFlag;
	}

	public void setLendFlag(String lendFlag) {
		this.lendFlag = lendFlag;
	}

	public String getLendFlagDesc() {
		return lendFlagDesc;
	}

	public void setLendFlagDesc(String lendFlagDesc) {
		this.lendFlagDesc = lendFlagDesc;
	}

	private String beforeNum;

    private String archiveDate;

    private String remark;

    private String recordFlag;

    private String documentLocalId;

    private String documentCreatime;

    private String linkFilesId;

    private Boolean isArchive;

    private Long userId;

    private String savaLocation;

    private Boolean arrangeFlag;

    private String dataSource;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliverDate;

    private Integer paginalNum;

    private String updatedDt;

    private String insideNum;
    
    private String option;

    private Long arrangedBy;

    public Long getArrangedBy() {
        return arrangedBy;
    }

    public void setArrangedBy(Long arrangedBy) {
        this.arrangedBy = arrangedBy;
    }

    public String getOaSyncType() {
        return oaSyncType;
    }

    public void setOaSyncType(String oaSyncType) {
        this.oaSyncType = oaSyncType;
    }

    private String oaSyncType;

    public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Boolean getArchive() {
        return isArchive;
    }

    public void setArchive(Boolean archive) {
        isArchive = archive;
    }

    public String getInsideNum() {
        return insideNum;
    }

    public void setInsideNum(String insideNum) {
        this.insideNum = insideNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum == null ? null : fileNum.trim();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDocAttr() {
        return docAttr;
    }

    public void setDocAttr(String docAttr) {
        this.docAttr = docAttr == null ? null : docAttr.trim();
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible == null ? null : responsible.trim();
    }

    public String getFondsId() {
        return fondsId;
    }

    public void setFondsId(String fondsId) {
        this.fondsId = fondsId == null ? null : fondsId.trim();
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance == null ? null : importance.trim();
    }

    public Long getAppendices() {
        return appendices;
    }

    public void setAppendices(Long appendices) {
        this.appendices = appendices;
    }

    public String getArchiveType() {
        return archiveType;
    }

    public void setArchiveType(String archiveType) {
        this.archiveType = archiveType == null ? null : archiveType.trim();
    }

    public String getDeliverUser() {
        return deliverUser;
    }

    public void setDeliverUser(String deliverUser) {
        this.deliverUser = deliverUser == null ? null : deliverUser.trim();
    }

    public String getDeliverDept() {
        return deliverDept;
    }

    public void setDeliverDept(String deliverDept) {
        this.deliverDept = deliverDept == null ? null : deliverDept.trim();
    }

    public String getArchiveYear() {
        return archiveYear;
    }

    public void setArchiveYear(String archiveYear) {
        this.archiveYear = archiveYear == null ? null : archiveYear.trim();
    }

    public String getReserveDuration() {
        return reserveDuration;
    }

    public void setReserveDuration(String reserveDuration) {
        this.reserveDuration = reserveDuration == null ? null : reserveDuration.trim();
    }

    public Long getEntityNum() {
        return entityNum;
    }

    public void setEntityNum(Long entityNum) {
        this.entityNum = entityNum;
    }

    public String getFileCreatetime() {
        return fileCreatetime;
    }

    public void setFileCreatetime(String fileCreatetime) {
        this.fileCreatetime = fileCreatetime == null ? null : fileCreatetime.trim();
    }

    public String getReserveLocation() {
        return reserveLocation;
    }

    public void setReserveLocation(String reserveLocation) {
        this.reserveLocation = reserveLocation == null ? null : reserveLocation.trim();
    }

    public String getBeforeNum() {
        return beforeNum;
    }

    public void setBeforeNum(String beforeNum) {
        this.beforeNum = beforeNum == null ? null : beforeNum.trim();
    }

    public String getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(String archiveDate) {
        this.archiveDate = archiveDate == null ? null : archiveDate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }

    public String getDocumentLocalId() {
        return documentLocalId;
    }

    public void setDocumentLocalId(String documentLocalId) {
        this.documentLocalId = documentLocalId == null ? null : documentLocalId.trim();
    }

    public String getDocumentCreatime() {
        return documentCreatime;
    }

    public void setDocumentCreatime(String documentCreatime) {
        this.documentCreatime = documentCreatime == null ? null : documentCreatime.trim();
    }

    public String getLinkFilesId() {
        return linkFilesId;
    }

    public void setLinkFilesId(String linkFilesId) {
        this.linkFilesId = linkFilesId == null ? null : linkFilesId.trim();
    }

    public Boolean getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSavaLocation() {
        return savaLocation;
    }

    public void setSavaLocation(String savaLocation) {
        this.savaLocation = savaLocation == null ? null : savaLocation.trim();
    }

    public Boolean getArrangeFlag() {
        return arrangeFlag;
    }

    public void setArrangeFlag(Boolean arrangeFlag) {
        this.arrangeFlag = arrangeFlag;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Integer getPaginalNum() {
        return paginalNum;
    }

    public void setPaginalNum(Integer paginalNum) {
        this.paginalNum = paginalNum;
    }

    public String getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt == null ? null : updatedDt.trim();
    }

    private String startYear;
    private String endYear;
    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    @Override
    public int compareTo(Object o) {
        Documents documents = (Documents) o;
        return this.getDocumentCreatime().compareTo(((Documents) o).getDocumentCreatime());
    }

    private AcousticImage acousticImage;

    public AcousticImage getAcousticImage() {
        return acousticImage;
    }

    public void setAcousticImage(AcousticImage acousticImage) {
        this.acousticImage = acousticImage;
    }
}