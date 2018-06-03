package com.court.oa.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.court.oa.project.R;
import com.court.oa.project.activity.Meet_Detail_activity;
import com.court.oa.project.adapter.TMeetAdapter;
import com.court.oa.project.tool.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class TMeetFragment extends Fragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView;
    private TMeetAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.tmeetfragment, null);
        initView();
        setData();
        setListener();
        return view;
    }

    private void initView() {
        CheckBox cb_set = view.findViewById(R.id.cb_set);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        cb_set.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                }
            }
        });
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
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new TMeetAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Meet_Detail_activity.class);
                getActivity().startActivity(intent);
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
