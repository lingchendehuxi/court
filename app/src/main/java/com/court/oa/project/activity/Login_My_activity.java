package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.UserBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.ParseUser;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.MD5Utils;
import com.court.oa.project.utils.StringUtils;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Login_My_activity extends AppCompatActivity implements View.OnClickListener,OkHttpManager.DataCallBack{
    private TextView regist_acount,tv_login;
    private EditText et_account;
    private EditText et_password;
    private String mobile;

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
        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
    }
    private void initLogin(){
        mobile = et_account.getText().toString().trim();
        String pass = MD5Utils.encode(et_password.getText().toString().trim());
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("userName", mobile);
        parameters.put("pwd", pass);
        OkHttpManager.postAsync(
                Contants.LOGIN_FOR_PWD, parameters,
                Login_My_activity.this, Contants.LOGIN_FOR_PWD);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regist_acount:
                startActivity(new Intent(Login_My_activity.this,Register_My_activity.class));
                break;
            case R.id.tv_login:
                if(StringUtils.isEmpty(et_account.getText().toString())){
                    ToastUtil.show(this,"请输入手机号！");
                    break;
                }
                if(!Utils.isMobileNO(et_account.getText().toString())){
                    ToastUtil.show(this,"手机号错误！");
                    break;
                }
                if(StringUtils.isEmpty(et_password.getText().toString())){
                    ToastUtil.show(this,"请输入密码！");
                    break;
                }
                initLogin();
                break;
        }

    }

    @Override
    public void requestFailure(Request request, IOException e, String method) {
        ToastUtil.getShortToastByString(Login_My_activity.this,
                getString(R.string.networkRequst_resultFailed));
    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        String jsonObj = object.getString("data");
//        Log.d("liuhong","login : " + jsonObj);
        switch (method) {
            case Contants.LOGIN_FOR_PWD:
                Gson gson = new Gson();
                UserBean userBean = gson.fromJson(jsonObj,new TypeToken<UserBean>() {
                }.getType());
                ToastUtil.show(Login_My_activity.this,object.getString("msg"));
                if (object.getInt("code") == 1) {
                    ParseUser.saveUser(object, this);
                    SharePreferenceUtils.saveUserString("login", "yes", Login_My_activity.this);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }

            default:
                break;
        }

    }
}
