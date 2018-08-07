package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class ArticalBean implements Serializable{
    private String ctgId;
    private String ctgName;
    private ArrayList<ArticalListBean> infoList;

    public String getCtgId() {
        return ctgId;
    }

    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    public String getCtgName() {
        return ctgName;
    }

    public void setCtgName(String ctgName) {
        this.ctgName = ctgName;
    }

    public ArrayList<ArticalListBean> getInfoList() {
        return infoList;
    }

    public void setInfoList(ArrayList<ArticalListBean> infoList) {
        this.infoList = infoList;
    }
}
