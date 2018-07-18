package com.court.oa.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.contants.HttpNames;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.MD5Utils;
import com.court.oa.project.utils.StringUtils;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Register_My_activity extends AppCompatActivity implements View.OnClickListener ,OkHttpManager.DataCallBack{

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
                initRegist();
                break;
        }
    }

    public void setRegister(){

    }
    private void initRegist(){
        String StrPhone = et_phone.getText().toString();
        String strPass = MD5Utils.encode(et_pass.getText().toString());
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("userName", StrPhone);
        parameters.put("pwd", strPass);
        OkHttpManager.postAsync(
                HttpNames.SERVER_NAME_REGIST, parameters,
                Register_My_activity.this, null, Contants.REGIST_FOR_USER);
    }

    @Override
    public void requestFailure(Request request, IOException e, String method) {

    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        Log.d("aaa","Register_My_activity : " + object.toString());
        switch (method) {
            case Contants.REGIST_FOR_USER:
                if (object.getInt("code") == 1) {
                } else {
                }
                ToastUtil.show(Register_My_activity.this,object.getString("msg"));
                break;

            default:
                break;
        }
    }
}
