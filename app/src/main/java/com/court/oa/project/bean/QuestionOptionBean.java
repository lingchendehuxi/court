package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class QuestionOptionBean implements Serializable{
    private String qusId;
    private String title;
    private String examType;
    private String sequence;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    private List<QuestionOptionValueBean> options;

    public String getQusId() {
        return qusId;
    }

    public void setQusId(String qusId) {
        this.qusId = qusId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public List<QuestionOptionValueBean> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionValueBean> options) {
        this.options = options;
    }
}
