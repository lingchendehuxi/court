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
import com.court.oa.project.adapter.TCardAdapter;
import com.court.oa.project.adapter.TMeetAdapter;
import com.court.oa.project.tool.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class TCardFragment extends Fragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView;
    private TCardAdapter adapter;
    private CheckBox cb_part,cb_person,cb_time;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.tcardfragment, null);
        initView();
        return view;
    }

    private void initView() {
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        cb_part = view.findViewById(R.id.cb_part);
        cb_person = view.findViewById(R.id.cb_person);
        cb_time = view.findViewById(R.id.cb_time);
        cb_part.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setPartData();
                    swipeLayout.setOnLoadListener(null);
                    listView.setVisibility(View.VISIBLE);
                }else{
                    listView.setVisibility(View.INVISIBLE);
                    isShow();
                }
            }
        });
        cb_person.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setPersonData();
                    swipeLayout.setOnLoadListener(null);
                    listView.setVisibility(View.VISIBLE);
                }else{
                    listView.setVisibility(View.INVISIBLE);
                    isShow();
                }
            }
        });
        cb_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setTimeData();
                    swipeLayout.setOnLoadListener(null);
                    listView.setVisibility(View.VISIBLE);
                }else{
                    listView.setVisibility(View.INVISIBLE);
                    isShow();
                }
            }
        });
    }

    /**
     * 添加数据
     */
    private void setData() {
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemImage", i + "数据");
            map.put("itemText", i + "数据");
            list.add(map);
        }
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new TCardAdapter(getActivity(), list,2);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Meet_Detail_activity.class);
                getActivity().startActivity(intent);
            }
        });
        setListener();
    }
    /**
     * 添加部门数据
     */
    private void setPartData() {
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 1; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemImage", i + "默认");
            map.put("itemText", i + "默认");
            list.add(map);
        }
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new TCardAdapter(getActivity(), list,1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map = (HashMap<String, String>) list.get(i);
                cb_part.setText(map.get("itemImage"));
                listView.setVisibility(View.INVISIBLE);
                cb_part.setChecked(false);
            }
        });
    }
    /**
     * 添加人员数据
     */
    private void setPersonData() {
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 1; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemImage", i + "默认");
            map.put("itemText", i + "默认");
            list.add(map);
        }
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new TCardAdapter(getActivity(), list,1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map = (HashMap<String, String>) list.get(i);
                cb_person.setText(map.get("itemImage"));
                listView.setVisibility(View.INVISIBLE);
                cb_person.setChecked(false);
            }
        });
    }
    /**
     * 添加时间数据
     */
    private void setTimeData() {
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 1; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemImage", i + "默认");
            map.put("itemText", i + "默认");
            list.add(map);
        }
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new TCardAdapter(getActivity(), list,1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map = (HashMap<String, String>) list.get(i);
                cb_time.setText(map.get("itemImage"));
                listView.setVisibility(View.INVISIBLE);
                cb_time.setChecked(false);
            }
        });
    }
    //设置检查是否三个选项全选中
    private void isShow(){
        if("部门".equals(cb_part.getText().toString())||
                "人员".equals(cb_person.getText().toString())||"时间".equals(cb_time.getText().toString())){
            return;
        }
        setData();
        listView.setVisibility(View.VISIBLE);
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
