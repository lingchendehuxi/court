package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.TMeetAdapter;
import com.court.oa.project.adapter.TMine_MenuAdapter;
import com.court.oa.project.adapter.TMine_Menu_ListDetial_Adapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.bean.MyMenuDetailChildren;
import com.court.oa.project.bean.MyMenuDetailParent;
import com.court.oa.project.bean.MyMenuSendBean;
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

public class Mine_Menu_Detial_activity extends AppCompatActivity implements View.OnClickListener ,OkHttpManager.DataCallBack{
    private ListView listView;
    private TMine_Menu_ListDetial_Adapter adapter;
    private MyMenuDetailParent menuDetailParentBean;
    private String oid;
    private TextView all_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_menulist_detail);
        initView();
        oid = getIntent().getStringExtra("oid");
        setData();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的熟菜");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        all_price = findViewById(R.id.all_price);
        //
        listView = findViewById(R.id.list);

    }
    private void setData(){
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("oid", oid);
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", this));
        OkHttpManager.postAsync(
                Contants.ORDER_DETAIL, parameters,
                this, Contants.ORDER_DETAIL);
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
                case Contants.ORDER_DETAIL:
                    Gson gson = new Gson();
                    menuDetailParentBean = gson.fromJson(jsonObj1, new TypeToken<MyMenuDetailParent>() {
                    }.getType());
                    adapter = new TMine_Menu_ListDetial_Adapter(this, (ArrayList<MyMenuDetailChildren>) menuDetailParentBean.getSubOrders());
                    listView.setAdapter(adapter);
                    all_price.setText("￥ "+menuDetailParentBean.getSumPrice()+"元");
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
        }
    }
}
