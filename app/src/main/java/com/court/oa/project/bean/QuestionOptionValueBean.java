package com.court.oa.project.bean;

import java.io.Serializable;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class QuestionOptionValueBean implements Serializable {
    private String opId;
    private String title;
    private int submitCount;
    private int percent;

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
