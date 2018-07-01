package com.court.oa.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.THall_Leave_Adapter;
import com.court.oa.project.tool.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class THallFragment extends Fragment implements View.OnClickListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener,RadioGroup.OnCheckedChangeListener{
    private View view;
    private LinearLayout hall_date;
    private TextView hall_buy;
    private String[] date = new String[]{
            "05.07","05.08","05.09","05.10","05.11","05.12","05.13","05.14"};
    private String[] workDate = new String[]{"周一","周二","周三","周四","周五"};
    private LinearLayout hall_leave;
    private LinearLayout hall_work;
    private LinearLayout hall_package;
    private LinearLayout hall_commit;
    //
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView;
    private THall_Leave_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.thallfragment, null);
        initView();
        return view;
    }

    private void initView() {
        hall_date = view.findViewById(R.id.hall_date);
        hall_buy = view.findViewById(R.id.hall_buy);
        hall_leave = view.findViewById(R.id.hall_leave);
        hall_work = view.findViewById(R.id.hall_work);
        hall_package = view.findViewById(R.id.hall_package);
        hall_commit = view.findViewById(R.id.hall_commit);
        swipeLayout = view.findViewById(R.id.swipe_container);
        hall_buy.setOnClickListener(this);
        RadioGroup radio = view.findViewById(R.id.radio);
        radio.setOnCheckedChangeListener(this);
        setLeaveData();
    }
    private void goneView(){
        hall_buy.setVisibility(View.GONE);
        hall_leave.setVisibility(View.GONE);
        hall_package.setVisibility(View.GONE);
        hall_work.setVisibility(View.GONE);
        hall_commit.setVisibility(View.GONE);
    }
    private void setWorkData(){
        goneView();
        hall_work.setVisibility(View.VISIBLE);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final TextView[] textViews = new TextView[workDate.length];
        for(int i =0 ;i<workDate.length;i++){
            final TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels/5, LinearLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.hall_title_chose);
            textView.setText(workDate[i]);
            textViews[i] = textView;
            final int j = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int t =0;t<workDate.length;t++){
                        textViews[t].setSelected(false);
                    }
                    textView.setSelected(true);
                }
            });
            hall_date.addView(textView);
        }
    }
    private void setLeaveData(){
        goneView();
        hall_buy.setVisibility(View.VISIBLE);
        hall_leave.setVisibility(View.VISIBLE);
        setData();
        setListener();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final TextView[] textViews = new TextView[date.length];
        for(int i =0 ;i<date.length;i++){
            final TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels/5, LinearLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.hall_title_chose);
            textView.setText(date[i]);
            textViews[i] = textView;
            final int j = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int t =0;t<date.length;t++){
                        textViews[t].setSelected(false);
                    }
                    textView.setSelected(true);
                }
            });
            hall_date.addView(textView);
        }

    }
    private void setPackageDate(){
        goneView();
        hall_buy.setText("提交");
        hall_buy.setVisibility(View.VISIBLE);
        hall_package.setVisibility(View.VISIBLE);

    }
    private void setCommitSucess(){
        goneView();
        hall_commit.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(hall_date.getChildCount()>0){
            hall_date.removeAllViews();
        }
        switch (i){
            case R.id.rb_normal:
                setWorkData();
                break;
            case R.id.rb_send:
                setLeaveData();
                break;
            case R.id.rb_package:
                setPackageDate();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hall_buy:
                if(hall_package.getVisibility() == View.VISIBLE){
                    setCommitSucess();
                }
                break;
        }
    }

    //set Leave
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
        adapter = new THall_Leave_Adapter(getActivity(), list);
        listView.setAdapter(adapter);
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