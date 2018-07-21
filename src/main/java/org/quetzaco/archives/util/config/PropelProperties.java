package org.quetzaco.archives.util.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "PROPEL")
public class PropelProperties {
    private String usermode;
    private String password;
    private boolean isPropel;
    private String bizsystem;
    private String actionurl;

    public String getActionurl() {
        return actionurl;
    }

    public void setActionurl(String actionurl) {
        this.actionurl = actionurl;
    }

    public String getBizsystem() {
        return bizsystem;
    }

    public void setBizsystem(String bizsystem) {
        this.bizsystem = bizsystem;
    }

    public boolean isPropel() {
        return isPropel;
    }

    public void setIsPropel(boolean propel) {
        isPropel = propel;
    }

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

    @Override
    public String toString() {
        return "PropelProperties{" +
                "usermode='" + usermode + '\'' +
                ", password='" + password + '\'' +
                ", isPropel=" + isPropel +
                ", bizsystem='" + bizsystem + '\'' +
                ", actionurl='" + actionurl + '\'' +
                '}';
    }
}
