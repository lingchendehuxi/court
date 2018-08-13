package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class SalaryListDetailBean implements Serializable{
    private ArrayList<SalaryListConsumeDetailBean> wagesList;
    private String sfgz;
    private String title;

    public ArrayList<SalaryListConsumeDetailBean> getWagesList() {
        return wagesList;
    }

    public void setWagesList(ArrayList<SalaryListConsumeDetailBean> wagesList) {
        this.wagesList = wagesList;
    }

    public String getSfgz() {
        return sfgz;
    }

    public void setSfgz(String sfgz) {
        this.sfgz = sfgz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
