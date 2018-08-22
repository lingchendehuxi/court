package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class SubmitHallParentBean implements Serializable{
    private ArrayList<SubmitHallBean> subOrders;

    public ArrayList<SubmitHallBean> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(ArrayList<SubmitHallBean> subOrders) {
        this.subOrders = subOrders;
    }
}
