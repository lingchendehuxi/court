package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class SubmitHallBean implements Serializable{
    private String gid;
    private String subGoodsName;
    private String subOriginalPrice;
    private String subCurrentPrice;
    private String subSumPrice;
    private int subCount;

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSubGoodsName() {
        return subGoodsName;
    }

    public void setSubGoodsName(String subGoodsName) {
        this.subGoodsName = subGoodsName;
    }

    public String getSubOriginalPrice() {
        return subOriginalPrice;
    }

    public void setSubOriginalPrice(String subOriginalPrice) {
        this.subOriginalPrice = subOriginalPrice;
    }

    public String getSubCurrentPrice() {
        return subCurrentPrice;
    }

    public void setSubCurrentPrice(String subCurrentPrice) {
        this.subCurrentPrice = subCurrentPrice;
    }

    public String getSubSumPrice() {
        return subSumPrice;
    }

    public void setSubSumPrice(String subSumPrice) {
        this.subSumPrice = subSumPrice;
    }
}
