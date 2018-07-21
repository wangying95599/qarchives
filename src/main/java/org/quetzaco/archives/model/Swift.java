package org.quetzaco.archives.model;

public class Swift {
    private String prefix;

    private Integer swiftNumber;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? null : prefix.trim();
    }

    public Integer getSwiftNumber() {
        return swiftNumber;
    }

    public void setSwiftNumber(Integer swiftNumber) {
        this.swiftNumber = swiftNumber;
    }
}