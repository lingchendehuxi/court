package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.SalaryListDetailAdapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.SalaryListDetailBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.tool.RefreshLayout;
import com.court.oa.project.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class MY_Salary_activity_ListDetail extends AppCompatActivity implements View.OnClickListener, OkHttpManager.DataCallBack {
    private ListView listView;
    private SalaryListDetailAdapter adapter;
    private SalaryListDetailBean salaryBean;
    private TextView real_salary_key;
    private TextView real_salary_value;
    private int wagesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_salary_listdetail);
        initView();
    }

    private void initView() {
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的工资条");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        listView = findViewById(R.id.list);
        real_salary_key = findViewById(R.id.real_salary_key);
        real_salary_value = findViewById(R.id.real_salary_value);
        Intent intent = getIntent();
        wagesId = intent.getIntExtra("wagesId", 0);
        initSalaryDate();
    }

    private void initSalaryDate() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("wagesId", "" + wagesId);
        parameters.put("userId", SharePreferenceUtils.readUser("userId", this));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.WAGE_DETAIL, parameters,
                this, null, Contants.WAGE_DETAIL);
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
                case Contants.WAGE_DETAIL:
                    Gson gson = new Gson();
                    salaryBean = gson.fromJson(jsonObj1, new TypeToken<SalaryListDetailBean>() {
                    }.getType());
                    real_salary_value.setText(salaryBean.getSfgz()+"元");
                    adapter = new SalaryListDetailAdapter(this, salaryBean.getWagesList());
                    listView.setAdapter(adapter);
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (salaryBean != null) {
            salaryBean = null;
        }
    }
}
