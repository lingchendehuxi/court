package com.court.oa.project.activity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.TMeeFJAdapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.MeetFileBean;
import com.court.oa.project.bean.MeetMainDetailBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.FileUtils;
import com.court.oa.project.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

public class Leave_apply_activity extends AppCompatActivity implements View.OnClickListener,OkHttpManager.DataCallBack {
    private TextView leave_name,leave_reason,leave_time,leave_type,leave_apply,leave_add,tv_take;
    private EditText leave_and;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_leave_apply);
        initView();
        initMeetDate();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("请假批准");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        tv_take = findViewById(R.id.tv_take);
        tv_take.setOnClickListener(this);
        leave_name = findViewById(R.id.leave_name);
        leave_reason = findViewById(R.id.leave_reason);
        leave_time = findViewById(R.id.leave_time);
        leave_type = findViewById(R.id.leave_type);
        leave_apply = findViewById(R.id.leave_apply);
        leave_add = findViewById(R.id.leave_add);

    }
    private void initMeetDate() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.MEETING_DETAIL, parameters,
                this, null, Contants.MEETING_DETAIL);
    }
    private void meetTakePartIn() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.MEETING_SIGNUP, parameters,
                this, null, Contants.MEETING_SIGNUP);
    }
    @Override
    public void requestFailure(Request request, IOException e, String method) {
        ToastUtil.getShortToastByString(this,
                getString(R.string.networkRequst_resultFailed));
    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        if (object.getInt("code") == 1) {
            String jsonObj1 = object.getString("data");
            switch (method) {
                case Contants.MEETING_DETAIL:
                    Gson gson = new Gson();
                    break;
                case Contants.MEETING_SIGNUP:
                    ToastUtil.show(Leave_apply_activity.this,object.getString("msg"));
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
            break;
            /*case R.id.ll_open_text:

                break;*/
            case R.id.tv_take:
                meetTakePartIn();
                break;
        }
    }
}