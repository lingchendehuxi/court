package com.court.oa.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;

/**
 * Created by liuhong on 2018/8/21.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_mobile,tv_duty,tv_role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_userinfo_acitivity);
        initView();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("个人信息");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);

        tv_mobile = findViewById(R.id.tv_mobile);
        tv_duty = findViewById(R.id.tv_duty);
        tv_role = findViewById(R.id.tv_role);
        tv_mobile.setText("姓名:       "+SharePreferenceUtils.readUser("realName",this));
        tv_duty.setText("工号:       "+SharePreferenceUtils.readUser("userCode",this));
        tv_role.setText("部门:       "+SharePreferenceUtils.readUser("dept",this));
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
