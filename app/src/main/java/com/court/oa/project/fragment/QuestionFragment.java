package com.court.oa.project.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.activity.Question_activity;
import com.court.oa.project.bean.QuestionOptionBean;
import com.court.oa.project.bean.QuestionOptionValueBean;
import com.court.oa.project.bean.SubmitQuestionChildren;
import com.court.oa.project.contants.Contants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MateBook D on 2018/7/29.
 */

public class QuestionFragment extends Fragment {
    private TextView tv_title;
    private RadioGroup radioGroup;
    private RadioButton rb_one,rb_two,rb_thr,rb_four;
    private ArrayList<QuestionOptionValueBean> list;
    private ArrayList<SubmitQuestionChildren> listChildren;
    private QuestionOptionBean questionOptionBean;
    private SubmitQuestionChildren children;
    private View view;
    private int currentPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_question, container, false);
        initView();
        return view;
    }
    private void initView(){
        tv_title = view.findViewById(R.id.tv_title);
        radioGroup = view.findViewById(R.id.radio);
        rb_one = view.findViewById(R.id.rb_one);
        rb_two = view.findViewById(R.id.rb_two);
        rb_thr = view.findViewById(R.id.rb_thr);
        rb_four = view.findViewById(R.id.rb_four);
        Bundle bundle = this.getArguments();
        questionOptionBean = (QuestionOptionBean) bundle.getSerializable(Contants.QUESTION_ID);
        list = (ArrayList<QuestionOptionValueBean>) questionOptionBean.getOptions();
        currentPosition = bundle.getInt(Contants.QUESTION_GOBACK);
        children = new SubmitQuestionChildren();
        if(listChildren == null){
            listChildren = new ArrayList<>();
        }
        tv_title.setText(questionOptionBean.getTitle());

        RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        params_rb.setMargins(0,0,60,120);
        for(int i = 0;i<list.size();i++){
            final int j = i;
            if(questionOptionBean.getExamType() == 1) {
                RadioButton button = new RadioButton(getContext());
                button.setTextAppearance(getContext(), R.style.question_style);
                button.setButtonDrawable(getResources().getDrawable(R.drawable.leave_type_ico));
                button.setCompoundDrawablePadding(60);
                button.setText(list.get(i).getTitle());
                button.setLayoutParams(params_rb);
                radioGroup.addView(button);
                button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            listChildren.clear();
                            children.setOpId(list.get(j).getOpId());
                            children.setOpContent(list.get(j).getTitle());
                            listChildren.add(children);
                            ((Question_activity) getActivity()).setQustionValue(listChildren, currentPosition);
                        }
                    }
                });
            }else{
                listChildren.clear();
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setButtonDrawable(getResources().getDrawable(R.drawable.leave_type_ico));
                checkBox.setCompoundDrawablePadding(60);
                checkBox.setText(list.get(i).getTitle());
                checkBox.setLayoutParams(params_rb);
                radioGroup.addView(checkBox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            children.setOpId(list.get(j).getOpId());
                            children.setOpContent(list.get(j).getTitle());
                            listChildren.add(children);
                        }else {
                            listChildren.remove(j);
                        }
                        ((Question_activity) getActivity()).setQustionValue(listChildren, currentPosition);
                    }
                });
            }

        }
    }
}
