package com.court.oa.project.bean;

import java.io.Serializable;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class HallWeekDetailBean implements Serializable{
    private String menuType;
    private String menuContent;

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuContent() {
        return menuContent;
    }

    public void setMenuContent(String menuContent) {
        this.menuContent = menuContent;
    }
}
