package org.quetzaco.archives.model;

import java.io.Serializable;

public class Dept implements Serializable {
    private Long id;

    private String name;

    private Long prtId;

    private String description;

    private boolean hasChilds;

    public boolean isHasChilds() {
        return hasChilds;
    }

    public void setHasChilds(boolean hasChilds) {
        this.hasChilds = hasChilds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getPrtId() {
        return prtId;
    }

    public void setPrtId(Long prtId) {
        this.prtId = prtId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}