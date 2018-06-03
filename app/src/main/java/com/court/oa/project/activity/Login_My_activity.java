package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.tool.FitStateUI;

public class Login_My_activity extends AppCompatActivity implements View.OnClickListener{
    private TextView regist_acount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView(){
        regist_acount = findViewById(R.id.regist_acount);
        regist_acount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regist_acount:
                startActivity(new Intent(Login_My_activity.this,Register_My_activity.class));
                break;
        }

    }
}
