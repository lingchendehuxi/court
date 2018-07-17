package com.court.oa.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.MD5Utils;
import com.court.oa.project.utils.StringUtils;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.utils.Utils;

public class Register_My_activity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_phone,et_pass,et_passAgain;
    private TextView tv_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        et_phone = findViewById(R.id.et_phone);
        et_pass = findViewById(R.id.et_pass);
        et_passAgain = findViewById(R.id.et_passAgain);
        tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                if(StringUtils.isEmpty(et_phone.getText().toString())){
                    ToastUtil.show(this,"手机号为空！");
                    break;
                }
                if(!Utils.isMobileNO(et_phone.getText().toString())){
                    ToastUtil.show(this,"手机号错误！");
                    break;
                }
                if(!Utils.isRegisterPassword(et_pass.getText().toString())){
                    ToastUtil.show(this,"密码是6-15位的数字、下划线或者大小写字母组成！");
                    break;
                }
                if(!et_pass.getText().toString().equals(et_passAgain.getText().toString())){
                    ToastUtil.show(this,"确认密码错误！");
                    break;
                }
                setRegister();
                break;
        }
    }

    public void setRegister(){
        String StrPhone = et_phone.getText().toString();
        String strPass = MD5Utils.encode(et_pass.getText().toString());
        ToastUtil.show(this,"StrPhone -- "+StrPhone+" ; strPass -- "+strPass);
    }
}
