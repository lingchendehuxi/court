package com.court.oa.project.bean;

/**
 * Created by MateBook D on 2018/7/29.
 */

public class LeaveDetailBean {
    private int vid;
    private String type;
    private String startTime;
    private String endTime;
    private String reason;
    private String applyUser;
    private String auditUser;
    private String copyUsser;
    private String status;

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getCopyUsser() {
        return copyUsser;
    }

    public void setCopyUsser(String copyUsser) {
        this.copyUsser = copyUsser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
