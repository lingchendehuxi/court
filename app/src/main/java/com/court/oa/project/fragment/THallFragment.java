package com.court.oa.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.court.oa.project.adapter.Hall_week_dataAdapter;
import com.court.oa.project.adapter.THall_Leave_Adapter;
import com.court.oa.project.bean.HallPackageGoodBean;
import com.court.oa.project.bean.HallWeekBean;
import com.court.oa.project.bean.HallWeekDetailBean;
import com.court.oa.project.bean.MeetMainDetailBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.RefreshLayout;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;


public class THallFragment extends Fragment implements View.OnClickListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener, OkHttpManager.DataCallBack {
    private View view;
    private LinearLayout hall_date;
    private TextView hall_buy;
    private List<String> listTime;
    private List<String> newListTime;
    private String currentTime;
    private String[] workDate = new String[]{"周一", "周二", "周三", "周四", "周五"};
    private String[] workUpDate = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五"};
    private LinearLayout hall_leave;
    private LinearLayout hall_work;
    private LinearLayout hall_package;
    private LinearLayout hall_commit;
    //
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView, listview_1,listview_2,listview_3;
    private THall_Leave_Adapter adapter;
    private Hall_week_dataAdapter adapterWeek;
    private int page = 1;
    private ArrayList<HallPackageGoodBean> listGood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.thallfragment, null);
        initView();
        return view;
    }
    //获得当前系统时间
    private String getTime(){
        Calendar c = Calendar.getInstance();
        int year  = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day   = c.get(Calendar.DAY_OF_MONTH);
        return year+"-"+month+"-"+day;
    }
    //获得时间列表
    private List<String> getTimeList(){
        Calendar c = Calendar.getInstance();
        int year  = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day   = c.get(Calendar.DAY_OF_MONTH);
        int daysCountOfMonth = c.getActualMaximum(Calendar.DATE);
        ArrayList<String > list = new ArrayList<>();
        for(int i = 0;i<7;i++){
            day += i;
            if(day>daysCountOfMonth){
                month++;
            }
            list.add(month+"."+day);
        }
        return list;
    }

    private void initView() {
        hall_date = view.findViewById(R.id.hall_date);
        hall_buy = view.findViewById(R.id.hall_buy);
        hall_leave = view.findViewById(R.id.hall_leave);
        hall_work = view.findViewById(R.id.hall_work);
        hall_package = view.findViewById(R.id.hall_package);
        hall_commit = view.findViewById(R.id.hall_commit);
        swipeLayout = view.findViewById(R.id.swipe_container);
        listview_1 = view.findViewById(R.id.listview_1);
        listview_2 = view.findViewById(R.id.listview_2);
        listview_3 = view.findViewById(R.id.listview_3);
        listView = (ListView) view.findViewById(R.id.list);
        hall_buy.setOnClickListener(this);
        RadioGroup radio = view.findViewById(R.id.radio);
        radio.setOnCheckedChangeListener(this);
        listTime = Utils.test(7);
        newListTime = new ArrayList<>();
        for(int i = 0;i<listTime.size();i++){
            String string = listTime.get(i);
            String newString = string.substring(5,string.length());
            newListTime.add(newString.replace("-","."));
        }
        setLeaveData();
    }

    private void goneView() {
        hall_buy.setVisibility(View.GONE);
        hall_leave.setVisibility(View.GONE);
        hall_package.setVisibility(View.GONE);
        hall_work.setVisibility(View.GONE);
        hall_commit.setVisibility(View.GONE);
    }
    //设置周菜单
    private void setWorkData() {
        goneView();
        hall_work.setVisibility(View.VISIBLE);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final TextView[] textViews = new TextView[workDate.length];
        for (int i = 0; i < workDate.length; i++) {
            final TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels / 5, LinearLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.hall_title_chose);
            textView.setText(workDate[i]);
            textViews[i] = textView;
            final int j = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int t = 0; t < workDate.length; t++) {
                        textViews[t].setSelected(false);
                    }
                    textView.setSelected(true);
                    initHallWeekDate(workUpDate[j]);
                }
            });
            hall_date.addView(textView);
        }
        textViews[0].setSelected(true);
        initHallWeekDate("星期一");
    }
    //周菜单
    private void initHallWeekDate(String weekDay) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("weekDay", weekDay);
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.HALL_WEEK, parameters,
                this, null, Contants.HALL_WEEK);
    }
    //商品列表
    private void initHallGoodListDate(String time) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("showTime", "2018-7-24");//7-24
        parameters.put("ctgId", SharePreferenceUtils.readUser("userId",getActivity()));
        parameters.put("pageIndex", ""+1);
        parameters.put("pageSize", "10");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.HALL_GOODLIST, parameters,
                this, null, Contants.HALL_GOODLIST);
    }
    //更多商品列表
    private void initHallMoreGoodListDate(String time) {
        page++;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("showTime", "2018-7-27");
        parameters.put("ctgId", SharePreferenceUtils.readUser("userId",getActivity()));
        parameters.put("pageIndex", ""+page);
        parameters.put("pageSize", "10");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.HALL_GOODLIST, parameters,
                this, null, Contants.MORE);
    }

    @Override
    public void requestFailure(Request request, IOException e, String method) {
        ToastUtil.getShortToastByString(getActivity(),
                getString(R.string.networkRequst_resultFailed));
    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        if (object.getInt("code") == 1) {
            String jsonObj1 = object.getString("data");
            switch (method) {
                case Contants.HALL_WEEK:
                    Gson gson = new Gson();
                    ArrayList<HallWeekBean> listDept = gson.fromJson(jsonObj1, new TypeToken<List<HallWeekBean>>() {
                    }.getType());
                    if("早".equals(listDept.get(0).getTimeType())){
                        adapterWeek =new Hall_week_dataAdapter(getActivity(),listDept.get(0).getMenus());
                        listview_1.setAdapter(adapterWeek);
                    }
                    if("中".equals(listDept.get(1).getTimeType())){
                        adapterWeek =new Hall_week_dataAdapter(getActivity(),listDept.get(1).getMenus());
                        listview_2.setAdapter(adapterWeek);
                    }
                    if("晚".equals(listDept.get(2).getTimeType())){
                        adapterWeek =new Hall_week_dataAdapter(getActivity(),listDept.get(2).getMenus());
                        listview_3.setAdapter(adapterWeek);
                    }
                    break;
                case Contants.HALL_GOODLIST:
                    Gson gson1 = new Gson();
                    listGood = gson1.fromJson(jsonObj1, new TypeToken<List<HallPackageGoodBean>>() {
                    }.getType());
                    adapter = new THall_Leave_Adapter(getActivity(), listGood);
                    listView.setAdapter(adapter);
                    break;
                case Contants.MORE:
                    Gson gson2 = new Gson();
                    ArrayList<HallPackageGoodBean> listGood1 = gson2.fromJson(jsonObj1, new TypeToken<List<HallPackageGoodBean>>() {
                    }.getType());
                    if(listGood1.size()>0){
                        for(int i = 0;i<listGood1.size();i++){
                            listGood.add(listGood1.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }else {
                        swipeLayout.setOnLoadListener(null);
                    }
                    break;

            }
        }
    }
    //设置打包
    private void setLeaveData() {
        goneView();
        hall_buy.setVisibility(View.VISIBLE);
        hall_leave.setVisibility(View.VISIBLE);
        setListener();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final TextView[] textViews = new TextView[newListTime.size()];
        for (int i = 0; i < newListTime.size(); i++) {
            final TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels / 5, LinearLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.hall_title_chose);
            textView.setText(newListTime.get(i));
            textViews[i] = textView;
            final int j = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int t = 0; t < newListTime.size(); t++) {
                        textViews[t].setSelected(false);
                    }
                    textView.setSelected(true);
                    currentTime = listTime.get(j);
                }
            });
            hall_date.addView(textView);
        }
        currentTime = listTime.get(0);
        initHallGoodListDate(currentTime);

    }

    private void setPackageDate() {
        goneView();
        hall_buy.setText("提交");
        hall_buy.setVisibility(View.VISIBLE);
        hall_package.setVisibility(View.VISIBLE);

    }

    private void setCommitSucess() {
        goneView();
        hall_commit.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (hall_date.getChildCount() > 0) {
            hall_date.removeAllViews();
        }
        switch (i) {
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
        switch (view.getId()) {
            case R.id.hall_buy:
                if (hall_package.getVisibility() == View.VISIBLE) {
                    setCommitSucess();
                }
                break;
        }
    }

    //set Leave


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
                listGood.clear();
                initHallGoodListDate(currentTime);
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
                initHallMoreGoodListDate(currentTime);
                swipeLayout.setLoading(false);
            }
        }, 2000);
    }
}