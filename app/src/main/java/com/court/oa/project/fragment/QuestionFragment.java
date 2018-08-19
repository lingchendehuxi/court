package com.court.oa.project.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.QuestionOptionValueBean;
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
    private View view;
    private String title;

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
        list = (ArrayList<QuestionOptionValueBean>) bundle.getSerializable(Contants.QUESTION_ID);
        title = bundle.getString("question_title");
        tv_title.setText(title);

        RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        params_rb.setMargins(0,0,60,120);
        for(int i = 0;i<list.size();i++){
            RadioButton button = new RadioButton(getContext());
            button.setTextAppearance(getContext(),R.style.question_style);
            button.setButtonDrawable(getResources().getDrawable(R.drawable.leave_type_ico));
            button.setCompoundDrawablePadding(60);
            button.setText(list.get(i).getTitle());
            button.setLayoutParams(params_rb);
            radioGroup.addView(button);
            /*CheckBox checkBox = new CheckBox(getContext());
            checkBox.setButtonDrawable(getResources().getDrawable(R.drawable.leave_type_ico));
            checkBox.setCompoundDrawablePadding(60);
            checkBox.setText(list.get(i).getTitle());
            checkBox.setLayoutParams(params_rb);
            radioGroup.addView(checkBox);*/
        }
    }
}
