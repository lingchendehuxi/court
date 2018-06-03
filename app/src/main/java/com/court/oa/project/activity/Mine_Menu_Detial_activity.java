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
import com.court.oa.project.adapter.TMine_Menu_ListDetial_Adapter;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.tool.FitStateUI;

import java.util.ArrayList;
import java.util.HashMap;

public class Mine_Menu_Detial_activity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private TMine_Menu_ListDetial_Adapter adapter;
    private ArrayList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_menulist_detail);
        initView();
        setData();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的菜单");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);

        //
        listView = findViewById(R.id.list);

    }
    private void setData(){
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 1; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemImage", i + "默认");
            map.put("itemText", i + "默认");
            list.add(map);
        }
        adapter = new TMine_Menu_ListDetial_Adapter(Mine_Menu_Detial_activity.this, list);
        listView.setAdapter(adapter);
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
