package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MateBook D on 2018/7/27.
 */

public class MeetMainDetailBean implements Serializable{
    private int id;
    private String title;
    private String startTime;
    private String address;
    private String content;
    private String joinStartTime;
    private String joinEndTime;
    private String joinUsers;
    private ArrayList<MeetFileBean> files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJoinStartTime() {
        return joinStartTime;
    }

    public void setJoinStartTime(String joinStartTime) {
        this.joinStartTime = joinStartTime;
    }

    public String getJoinEndTime() {
        return joinEndTime;
    }

    public void setJoinEndTime(String joinEndTime) {
        this.joinEndTime = joinEndTime;
    }

    public String getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(String joinUsers) {
        this.joinUsers = joinUsers;
    }

    public ArrayList<MeetFileBean> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<MeetFileBean> files) {
        this.files = files;
    }
}
