package com.court.oa.project.bean;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class DeptBean {
    private String deptName;
    private ArrayList<DeptUserBean> users;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public ArrayList<DeptUserBean> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<DeptUserBean> users) {
        this.users = users;
    }
}
