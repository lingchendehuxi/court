package com.court.oa.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.Leave_DeptAdapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.DeptBean;
import com.court.oa.project.bean.MeetMainDetailBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class Mine_leave_chose_activity extends AppCompatActivity implements View.OnClickListener ,OkHttpManager.DataCallBack{
    private ListView listView;
    private ArrayList<DeptBean> listDept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_leave_chose_top);
        initView();
        initDeptDate();
    }
    private void initView() {
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("选择联系人");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        listView = findViewById(R.id.listView);
    }
    private void initDeptDate() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("key", "");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.LEAVE_USERLIST, parameters,
                this, null, Contants.LEAVE_USERLIST);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
        }
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
                    listDept = gson.fromJson(jsonObj1, new TypeToken<List<DeptBean>>() {
                    }.getType());
                    Leave_DeptAdapter adapter = new Leave_DeptAdapter(this,listDept);
                    listView.setAdapter(adapter);
                    break;
                    }

        }
    }
}
