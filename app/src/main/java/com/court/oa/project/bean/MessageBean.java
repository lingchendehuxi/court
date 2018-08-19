package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class MessageBean implements Serializable{
    private String msgId;
    private String msgType;
    private String msgCtg;
    private String msgCtgId;
    private String msgTitle;
    private String msgContent;
    private String msgTime;
    private String isRead;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgCtg() {
        return msgCtg;
    }

    public void setMsgCtg(String msgCtg) {
        this.msgCtg = msgCtg;
    }

    public String getMsgCtgId() {
        return msgCtgId;
    }

    public void setMsgCtgId(String msgCtgId) {
        this.msgCtgId = msgCtgId;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
