package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class TCardBean implements Serializable {
    private String adate;
    private String signinDesc;
    private String signoutDesc;
    private String desc;
    private String status;

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getSigninDesc() {
        return signinDesc;
    }

    public void setSigninDesc(String signinDesc) {
        this.signinDesc = signinDesc;
    }

    public String getSignoutDesc() {
        return signoutDesc;
    }

    public void setSignoutDesc(String signoutDesc) {
        this.signoutDesc = signoutDesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
