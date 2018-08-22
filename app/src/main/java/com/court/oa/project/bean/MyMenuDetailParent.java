package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class MyMenuDetailParent implements Serializable{
    private String id;
    private String originalPrice;
    private String sumPrice;
    private String ctgName;
    private String takeUser;
    private String takeTime;
    private String createTime;
    private String status;
    private List<MyMenuDetailChildren> subOrders;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getCtgName() {
        return ctgName;
    }

    public void setCtgName(String ctgName) {
        this.ctgName = ctgName;
    }

    public String getTakeUser() {
        return takeUser;
    }

    public void setTakeUser(String takeUser) {
        this.takeUser = takeUser;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MyMenuDetailChildren> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(List<MyMenuDetailChildren> subOrders) {
        this.subOrders = subOrders;
    }
}
