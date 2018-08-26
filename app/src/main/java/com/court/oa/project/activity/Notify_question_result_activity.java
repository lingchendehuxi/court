package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.QuestionResultAdapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.QuestionDetailBean;
import com.court.oa.project.bean.QuestionOptionValueBean;
import com.court.oa.project.tool.FitStateUI;

import java.util.ArrayList;

public class Notify_question_result_activity extends AppCompatActivity implements View.OnClickListener {
    private QuestionDetailBean resultBean;
    private TextView result_title;
    private ListView my_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_notify_question_result);
        initView();
    }
    private void initView(){
        Intent intent = getIntent();
        resultBean = (QuestionDetailBean) intent.getSerializableExtra("result");
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("调查结果");
        result_title = findViewById(R.id.result_title);
        my_list = findViewById(R.id.my_list);
        if(resultBean != null){
            result_title.setText(resultBean.getQuestions().get(0).getTitle());
            my_list.setAdapter(new QuestionResultAdapter(this,(ArrayList<QuestionOptionValueBean>) resultBean.getQuestions().get(0).getOptions()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
            break;
        }
    }
}
