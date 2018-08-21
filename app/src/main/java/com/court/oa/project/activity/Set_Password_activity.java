package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.ParseUser;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.MD5Utils;
import com.court.oa.project.utils.StringUtils;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class Set_Password_activity extends AppCompatActivity implements View.OnClickListener ,OkHttpManager.DataCallBack{

    private EditText et_oldpass,et_newpass,et_passAgain;
    private TextView tv_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_password);
        initView();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("修改密码");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);

        et_oldpass = findViewById(R.id.et_oldpass);
        et_newpass = findViewById(R.id.et_newpass);
        et_passAgain = findViewById(R.id.et_passAgain);
        tv_reset = findViewById(R.id.tv_reset);
        tv_reset.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_reset:
                if(StringUtils.isEmpty(et_oldpass.getText().toString())){
                    ToastUtil.show(this,"请输入旧密码");
                    break;
                }
                if(!Utils.isRegisterPassword(et_oldpass.getText().toString())){
                    ToastUtil.show(this,"旧密码是6-15位的数字、下划线或者大小写字母组成！");
                    break;
                }
                if(StringUtils.isEmpty(et_newpass.getText().toString())){
                    ToastUtil.show(this,"请输入新密码");
                    break;
                }
                if(!Utils.isRegisterPassword(et_newpass.getText().toString())){
                    ToastUtil.show(this,"新密码是6-15位的数字、下划线或者大小写字母组成！");
                    break;
                }
                if(!et_newpass.getText().toString().equals(et_passAgain.getText().toString())){
                    ToastUtil.show(this,"两次密码输入不一致！");
                    break;
                }
                initRegist();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initRegist(){
        ToastUtil.show(this,"修改");
//        StrPhone = et_phone.getText().toString();
//        String strPass = MD5Utils.encode(et_pass.getText().toString());
//        HashMap<String, String> parameters = new HashMap<>();
//        parameters.put("userName", StrPhone);
//        parameters.put("pwd", strPass);
//        OkHttpManager.postAsync(
//                Contants.REGIST_FOR_USER, parameters,
//                Set_Password_activity.this, null, Contants.REGIST_FOR_USER);
    }

    @Override
    public void requestFailure(Request request, IOException e, String method) {
        ToastUtil.getShortToastByString(Set_Password_activity.this,
                getString(R.string.networkRequst_resultFailed));
    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        switch (method) {
            case Contants.REGIST_FOR_USER:
                ToastUtil.show(Set_Password_activity.this,object.getString("msg"));
                if (object.getInt("code") == 1) {
//                    ParseUser.saveUser(object, this);
//                    SharePreferenceUtils.saveUserString("login", "yes", Set_Password_activity.this);
//                    SharePreferenceUtils.saveUserString("mobile",StrPhone,Set_Password_activity.this);
//                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }

                break;

            default:
                break;
        }
    }
}
