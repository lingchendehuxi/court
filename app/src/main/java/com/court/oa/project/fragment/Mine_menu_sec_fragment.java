package com.court.oa.project.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.TMine_MenuAdapter;
import com.court.oa.project.bean.MyMenuSendBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Mine_menu_sec_fragment extends Fragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener,OkHttpManager.DataCallBack{

    private View view;
    private RefreshLayout swipeLayout;
    private ArrayList<MyMenuSendBean> listBean;
    private ListView listView;
    private TMine_MenuAdapter adapter;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine_question_sec_fragment, container, false);
        initView();
        setListener();
        initMyHallDate();
        return  view;
    }
    //请求数据
    private void initMyHallDate() {
        page=1;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("pageIndex", ""+page);
        parameters.put("pageSize", "10");
        parameters.put("orderType", "2");
        parameters.put("uid", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.ORDER_GOODLIST, parameters,
                this, null, Contants.ORDER_GOODLIST);
    }
    private void initMoreMyHallDate() {
        page++;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("pageIndex", ""+page);
        parameters.put("pageSize", "10");
        parameters.put("orderType", "2");
        parameters.put("uid", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.ORDER_GOODLIST, parameters,
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
                case Contants.ORDER_GOODLIST:
                    Gson gson = new Gson();
                    listBean = gson.fromJson(jsonObj1, new TypeToken<List<MyMenuSendBean>>() {
                    }.getType());
                    adapter = new TMine_MenuAdapter(getActivity(), listBean);
                    listView.setAdapter(adapter);
                    break;
                case Contants.MORE:
                    Gson gson1 = new Gson();
                    ArrayList<MyMenuSendBean> listMeet1 = gson1.fromJson(jsonObj1, new TypeToken<List<MyMenuSendBean>>() {
                    }.getType());
                    if(listMeet1.size()!=0){
                        for(int i = 0;i<listMeet1.size();i++){
                            listBean.add(listMeet1.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;

                default:
                    break;
            }
        }

    }
    /**
     * 添加数据
     */
    private void initView() {
        swipeLayout = view.findViewById(R.id.swipe_container);
        listView =  view.findViewById(R.id.list);
        listView.setClickable(false);
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
                initMyHallDate();
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
                initMoreMyHallDate();
                swipeLayout.setLoading(false);
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listBean.clear();
    }

}
