package com.court.oa.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.TMine_MenuAdapter;
import com.court.oa.project.adapter.TNotifyAdapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.tool.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class Mine_Menu_activity extends AppCompatActivity implements View.OnClickListener ,RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener{
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView;
    private TMine_MenuAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine__menu_activity);
        initView();
        setData();
        setListener();
    }
    private void initView(){
        swipeLayout = findViewById(R.id.swipe_container);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的菜单");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
        }
    }
    /**
     * 添加数据
     */
    private void setData() {
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 1; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemImage", i + "默认");
            map.put("itemText", i + "默认");
            list.add(map);
        }
        listView =  findViewById(R.id.list);
        adapter = new TMine_MenuAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Mine_Menu_activity.this, Mine_Menu_Detial_activity.class);
                startActivity(intent);
            }
        });
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
                list.clear();
                for (int i = 0; i < 8; i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("itemImage", i + "刷新");
                    map.put("itemText", i + "刷新");
                    list.add(map);
                }
                adapter.notifyDataSetChanged();
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
                swipeLayout.setLoading(false);
                for (int i = 1; i < 10; i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("itemImage", i + "更多");
                    map.put("itemText", i + "更多");
                    list.add(map);
                }
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }

}
