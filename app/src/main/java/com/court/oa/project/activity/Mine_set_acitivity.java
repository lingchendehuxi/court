package com.court.oa.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.ToastUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class Mine_set_acitivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mine_set_info,mine_set_update,mine_set_logout,mine_set_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_set_acitivity);
        initView();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("设置");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        //
        mine_set_info = findViewById(R.id.mine_set_info);
        mine_set_info.setOnClickListener(this);
        mine_set_update = findViewById(R.id.mine_set_update);
        mine_set_update.setOnClickListener(this);
        mine_set_logout = findViewById(R.id.mine_set_logout);
        mine_set_logout.setOnClickListener(this);
        mine_set_password = findViewById(R.id.mine_set_password);
        mine_set_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
            break;
            case R.id.mine_set_info:
                Intent intent = new Intent(Mine_set_acitivity.this,UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_set_update:
                ToastUtil.show(Mine_set_acitivity.this,"暂无更新");
                break;
            case R.id.mine_set_logout:
                //清除极光推送别名
                JPushInterface.setAlias(Mine_set_acitivity.this,"",new TagAliasCallback() {
                    @Override
                    public void gotResult(int code, String alias, Set<String> tags) {
                        Log.e("liuhong","TagAliasCallback0成功==="+code);
                    }
                });
                SharePreferenceUtils.saveUserString("login", "no", Mine_set_acitivity.this);
                this.finish();
                break;
            case R.id.mine_set_password:
                Intent intent1 = new Intent(Mine_set_acitivity.this,Set_Password_activity.class);
                startActivity(intent1);
                break;
        }
    }
}
