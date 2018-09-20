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
import com.court.oa.project.bean.LeaveDetailBean;
import com.court.oa.project.bean.LeaveListBean;
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
import java.util.List;

import okhttp3.Request;

public class Leave_apply_activity extends AppCompatActivity implements View.OnClickListener, OkHttpManager.DataCallBack {
    private TextView leave_name, leave_reason, leave_time, leave_type, leave_apply, leave_add, tv_pass, tv_unpass;
    private EditText leave_edit;
    private int type;
    private LeaveDetailBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_leave_apply);
        initView();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        type = intent.getIntExtra("type", 0);
        leaveDetail(id);
    }

    private void initView() {
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        if (type == 2) {
            tv_title.setText("请假详情");
        } else {
            tv_title.setText("请假批准");
        }
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        tv_pass = findViewById(R.id.tv_pass);
        leave_name = findViewById(R.id.leave_name);
        leave_reason = findViewById(R.id.leave_reason);
        leave_time = findViewById(R.id.leave_time);
        leave_type = findViewById(R.id.leave_type);
        leave_apply = findViewById(R.id.leave_apply);
        leave_add = findViewById(R.id.leave_add);
        leave_edit = findViewById(R.id.leave_edit);
        tv_unpass = findViewById(R.id.tv_unpass);
        tv_pass.setOnClickListener(this);
        tv_unpass.setOnClickListener(this);

    }

    private void leaveDetail(String id) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.LEAVE_DETAIL, parameters,
                this, Contants.LEAVE_DETAIL);
    }

    private void leavePass(String auditUser, int isPass) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        parameters.put("vid", bean.getVid());
        parameters.put("auditUser", auditUser);
        parameters.put("isPass", isPass);
        parameters.put("desc", leave_edit.getText().toString().trim());
        OkHttpManager.postAsync(
                Contants.LEAVE_APPLYER, parameters,
                this, Contants.LEAVE_APPLYER);
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
                case Contants.LEAVE_APPLYER:
                    ToastUtil.show(Leave_apply_activity.this, object.getString("msg"));
                    finish();
                    break;
                case Contants.LEAVE_DETAIL:
                    Gson gson = new Gson();
                    bean = gson.fromJson(jsonObj1, new TypeToken<LeaveDetailBean>() {
                    }.getType());
                    leave_name.setText(bean.getApplyUser());
                    leave_type.setText("原因: " + bean.getType());
                    leave_time.setText("时间: " + bean.getStartTime() + " - " + bean.getEndTime());
                    leave_reason.setText("理由: " + bean.getReason());
                    leave_apply.setText("审批人: " + bean.getAuditUser());
                    leave_add.setText("抄送人:" + bean.getCopyUsser());
                    if (type == 2) {
                        leave_edit.setText(bean.getDesc());
                        leave_edit.setFocusable(false);
                        leave_edit.setClickable(false);
                        tv_unpass.setVisibility(View.GONE);
                        if ("1".equals(bean.getStatus())) {
                            tv_pass.setText("审批通过");
                            tv_pass.setBackgroundResource(R.color.theme_color);
                            tv_pass.setClickable(false);
                        } else if ("90".equals(bean.getStatus())) {
                            tv_pass.setText("审批不通过");
                            tv_pass.setBackgroundResource(R.color.notify_context_gray);
                            tv_pass.setClickable(false);
                        } else if ("0".equals(bean.getStatus())) {
                            tv_pass.setText("待审批");
                            tv_pass.setBackgroundResource(R.color.theme_color);
                            tv_pass.setClickable(false);
                        }
                        tv_pass.setEnabled(false);
                    }

                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_pass:
                if (type == 1) {
                    if (!leave_edit.getText().toString().trim().isEmpty()) {
                        if (bean.getDayCount() > Contants.DAYCOUNT && !SharePreferenceUtils.readUser("role", Leave_apply_activity.this).equals("1")) {
                            Intent intent = new Intent(Leave_apply_activity.this, Mine_leave_chose_activity.class);
                            intent.putExtra("type", 1);
                            intent.putExtra("isShow", 1);
                            startActivityForResult(intent, 1);
                        } else {
                            leavePass("", 1);
                        }
                    } else {
                        ToastUtil.getShortToastByString(Leave_apply_activity.this, "请添加批注");
                    }
                }
                break;
            case R.id.tv_unpass:
                if (type == 1) {
                    if (!leave_edit.getText().toString().trim().isEmpty()) {
                        if (bean.getDayCount() > Contants.DAYCOUNT && !SharePreferenceUtils.readUser("role", Leave_apply_activity.this).equals("1")) {
                            ToastUtil.getShortToastByString(Leave_apply_activity.this, "时间超过0.5天，请先选择下级审核人");
                            Intent intent = new Intent(Leave_apply_activity.this, Mine_leave_chose_activity.class);
                            intent.putExtra("type", 1);
                            intent.putExtra("isShow", 1);
                            startActivityForResult(intent, 2);
                        } else {
                            leavePass("", 0);
                        }
                    } else {
                        ToastUtil.getShortToastByString(Leave_apply_activity.this, "请添加批注");
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && requestCode == 1) {
            leave_apply.setText(bean.getAuditUser() + "," + data.getStringExtra("user"));
            if (data.getStringExtra("user").length() == 0) {
                ToastUtil.getShortToastByString(Leave_apply_activity.this, "时间超过0.5天，请先选择下级审核人");
                return;
            }
            leavePass(data.getStringExtra("user"), 1);
        } else if (resultCode == 100 && requestCode == 2) {
            leave_apply.setText(bean.getAuditUser() + "," + data.getStringExtra("user"));
            if (data.getStringExtra("user").length() == 0) {
                ToastUtil.getShortToastByString(Leave_apply_activity.this, "时间超过0.5天，请先选择下级审核人");
                return;
            }
            leavePass(data.getStringExtra("user"), 0);
        }
    }
}