package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.fragment.GuideFragment1;
import com.court.oa.project.fragment.GuideFragment2;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_start);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if("yes".equals(SharePreferenceUtils.readUser("login",StartActivity.this))){
                        Intent intent  = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else{
                        Intent intent = new Intent(StartActivity.this, Login_My_activity.class);
                        startActivity(intent);
                    }
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 2000);

    }

}
