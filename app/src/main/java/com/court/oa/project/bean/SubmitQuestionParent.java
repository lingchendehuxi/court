package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class SubmitQuestionParent implements Serializable{
    private String qusId;
    private ArrayList<SubmitQuestionChildren> choseOptions;

    public String getQusId() {
        return qusId;
    }

    public void setQusId(String qusId) {
        this.qusId = qusId;
    }

    public ArrayList<SubmitQuestionChildren> getChoseOptions() {
        return choseOptions;
    }

    public void setChoseOptions(ArrayList<SubmitQuestionChildren> choseOptions) {
        this.choseOptions = choseOptions;
    }
}
