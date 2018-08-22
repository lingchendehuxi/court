package com.court.oa.project.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/7/28.
 */

public class SumbitBean implements Serializable{
    private ArrayList<SubmitQuestionParent> questions;

    public ArrayList<SubmitQuestionParent> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<SubmitQuestionParent> questions) {
        this.questions = questions;
    }
}
