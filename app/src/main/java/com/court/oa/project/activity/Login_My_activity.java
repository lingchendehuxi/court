package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.tool.FitStateUI;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Login_My_activity extends AppCompatActivity implements View.OnClickListener,OkHttpManager.DataCallBack{
    private TextView regist_acount;
    private EditText et_account;
    private EditText et_password;

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
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_account.getText().toString().length()<=0){
                    Toast.makeText(Login_My_activity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_password.getText().toString().length()<4){
                    Toast.makeText(Login_My_activity.this,"密码不能低于4位",Toast.LENGTH_SHORT).show();
                    initLogin();
                }

            }
        });
    }
    private void initLogin(){
        String mobile = et_account.getText().toString().trim();
        String pass = et_password.getText().toString().trim();
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("userName", mobile);
        parameters.put("pwd", pass);
        OkHttpManager.postAsync(
                "http://wjfy/login", parameters,
                Login_My_activity.this, null, Contants.LOGIN_FOR_PWD);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regist_acount:
                startActivity(new Intent(Login_My_activity.this,Register_My_activity.class));
                break;
        }

    }

    @Override
    public void requestFailure(Request request, IOException e, String method) {

    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        switch (method) {
            case Contants.LOGIN_FOR_PWD:
                if (object.getInt("code") == 1) {
                } else if (object.getInt("code") == -1) {
                }
                break;

            default:
                break;
        }

    }
}
