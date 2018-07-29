package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class HallWeekBean implements Serializable{
    private String timeType;
    private ArrayList<HallWeekDetailBean> menus;

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public ArrayList<HallWeekDetailBean> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<HallWeekDetailBean> menus) {
        this.menus = menus;
    }
}
