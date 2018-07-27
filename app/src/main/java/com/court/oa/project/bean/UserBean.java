package com.court.oa.project.bean;

import java.io.Serializable;

/**
 * Created by liuhong on 2018/7/27.
 */

public class UserBean implements Serializable{


    /**
     * userId : 6068
     * role : 3
     * appToken : B4C1EEA84EAA31368822BF3B43348533
     * realName : 15800564544
     * duty : null
     * iconUrl : null
     */

    private int userId;
    private int role;
    private String appToken;
    private String realName;
    private Object duty;
    private Object iconUrl;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Object getDuty() {
        return duty;
    }

    public void setDuty(Object duty) {
        this.duty = duty;
    }

    public Object getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(Object iconUrl) {
        this.iconUrl = iconUrl;
    }
}
