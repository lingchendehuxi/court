package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class MyMenuDetailChildren implements Serializable{
    private String gid;
    private String goodsName;
    private String subOriginalPrice;
    private String subCurrentPrice;
    private String subSumPrice;
    private String subCount;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getSubCount() {
        return subCount;
    }

    public void setSubCount(String subCount) {
        this.subCount = subCount;
    }
}
