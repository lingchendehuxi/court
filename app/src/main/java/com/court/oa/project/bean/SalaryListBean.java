package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class SalaryListBean implements Serializable{
    private int wagesId;
    private String wagesName;

    public int getWagesId() {
        return wagesId;
    }

    public void setWagesId(int wagesId) {
        this.wagesId = wagesId;
    }

    public String getWagesName() {
        return wagesName;
    }

    public void setWagesName(String wagesName) {
        this.wagesName = wagesName;
    }
}
