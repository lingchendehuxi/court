package com.court.oa.project.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.SalaryListAdapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.SalaryListBean;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MY_Salary_activity extends AppCompatActivity implements View.OnClickListener ,OkHttpManager.DataCallBack,RefreshLayout.OnLoadListener,SwipeRefreshLayout.OnRefreshListener{
    private RefreshLayout swipeLayout;
    private ListView listView;
    private SalaryListAdapter adapter;
    private int page = 1;
    private ArrayList<SalaryListBean> listBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_salary_list);
        initView();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的工资条");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        swipeLayout = findViewById(R.id.swipe_container);
        listView = findViewById(R.id.list);
        setListener();
        initSalaryDate();
    }
    private void initSalaryDate() {
        page=1;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("pageIndex", ""+page);
        parameters.put("pageSize", "10");
        parameters.put("userId", SharePreferenceUtils.readUser("userId", this));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.WAGE_LIST, parameters,
                this, Contants.WAGE_LIST);
    }
    private void initMoreSalaryDate() {
        page++;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("pageIndex", ""+page);
        parameters.put("pageSize", "10");
        parameters.put("userId", SharePreferenceUtils.readUser("userId", this));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.WAGE_LIST, parameters,
                this, Contants.MORE);
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
                case Contants.WAGE_LIST:
                    Gson gson = new Gson();
                    listBeans = gson.fromJson(jsonObj1, new TypeToken<List<SalaryListBean>>() {
                    }.getType());
                    adapter = new SalaryListAdapter(this, listBeans);
                    listView.setAdapter(adapter);
                    if(listBeans.size()<=10){
                        swipeLayout.setOnLoadListener(null);
                    }else {
                        swipeLayout.setOnLoadListener(this);
                    }
                    break;
                case Contants.MORE:
                    Gson gson1 = new Gson();
                    ArrayList<SalaryListBean> listSalary1 = gson1.fromJson(jsonObj1, new TypeToken<List<SalaryListBean>>() {
                    }.getType());
                    if(listSalary1.size()!=0){
                        for(int i = 0;i<listSalary1.size();i++){
                            listBeans.add(listSalary1.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }else {
                        swipeLayout.setOnLoadListener(null);
                    }
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新
                initSalaryDate();
                swipeLayout.setRefreshing(false);
            }
        }, 2000);

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新
                initMoreSalaryDate();
                swipeLayout.setLoading(false);
            }
        }, 2000);
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
    protected void onDestroy() {
        super.onDestroy();
        if(listBeans!=null){
            listBeans.clear();
        }
    }
}
