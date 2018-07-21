package org.quetzaco.archives.model;

import org.quetzaco.archives.util.config.PropelProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="PROPEL")
public class ScheduleToOa {
    /*refrenceId   flow_node_id
            * processinstname  title
            * executor       login_name
            * processinstid   flow_id
            * bizsystem      档案系统 KMHDAXT
            * actionurl*/
    private String usermode;
    private String password;
    private String refrenceId;
    private String processinstname;
    private String executor;
    private String processinstid;
    private String bizsystem;
    private String actionUrl;


    public String getUsermode() {
        return usermode;
    }

    public void setUsermode(String usermode) {
        this.usermode = usermode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefrenceId() {
        return refrenceId;
    }

    public void setRefrenceId(String refrenceId) {
        this.refrenceId = refrenceId;
    }

    public String getProcessinstname() {
        return processinstname;
    }

    public void setProcessinstname(String processinstname) {
        this.processinstname = processinstname;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getProcessinstid() {
        return processinstid;
    }

    public void setProcessinstid(String processinstid) {
        this.processinstid = processinstid;
    }

    public String getBizsystem() {
        return bizsystem;
    }

    public void setBizsystem(String bizsystem) {
        this.bizsystem = bizsystem;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    @Override
    public String toString() {
        return "ScheduleToOa{" +
                "usermode='" + usermode + '\'' +
                ", password='" + password + '\'' +
                ", refrenceId='" + refrenceId + '\'' +
                ", processinstname='" + processinstname + '\'' +
                ", executor='" + executor + '\'' +
                ", processinstid='" + processinstid + '\'' +
                ", bizsystem='" + bizsystem + '\'' +
                ", actionUrl='" + actionUrl + '\'' +
                '}';
    }
}
