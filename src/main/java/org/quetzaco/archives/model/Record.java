package org.quetzaco.archives.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Record implements PrimaryKey {
    private Long id;

    private Long deptId;

    private String fileNum;

    private String title;

    private String themeWord;

    private String responsible;

    private String fondsId;

    private String importance;

    private Long appendices;

    private String archiveType;

    private String deliverUser;

    private String deliverOffice;

    private String deliverDept;

    private String archiveYear;

    private String reserveDuration;

    private Long saveNum;

    private String reserveLocation;

    private String newReserveLocation;
    
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

	public String getNewReserveLocation() {
        return newReserveLocation;
    }

    public void setNewReserveLocation(String newReserveLocation) {
        this.newReserveLocation = newReserveLocation;
    }

    private String archiveDate;

    private String remark;

    private Boolean recordFlag;

    private Boolean isArchive;

    private Long userId;

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum == null ? null : itemNum.trim();
    }

    private String itemNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliverDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDt;

    private String oldFileNum;

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

    private String newFileNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum == null ? null : fileNum.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getThemeWord() {
        return themeWord;
    }

    public void setThemeWord(String themeWord) {
        this.themeWord = themeWord == null ? null : themeWord.trim();
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

    public String getDeliverOffice() {
        return deliverOffice;
    }

    public void setDeliverOffice(String deliverOffice) {
        this.deliverOffice = deliverOffice == null ? null : deliverOffice.trim();
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

    public Long getSaveNum() {
        return saveNum;
    }

    public void setSaveNum(Long saveNum) {
        this.saveNum = saveNum;
    }

    public String getReserveLocation() {
        return reserveLocation;
    }

    public void setReserveLocation(String reserveLocation) {
        this.reserveLocation = reserveLocation == null ? null : reserveLocation.trim();
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

    public Boolean getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Boolean recordFlag) {
        this.recordFlag = recordFlag;
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

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
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

    private PageSortSearch pageSortSearch;

    public PageSortSearch getPageSortSearch() {
        return pageSortSearch;
    }

    public void setPageSortSearch(PageSortSearch pageSortSearch) {
        this.pageSortSearch = pageSortSearch;
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
}