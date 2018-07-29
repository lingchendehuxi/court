package com.court.oa.project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.court.oa.project.R;
import com.court.oa.project.activity.Leave_apply_activity;
import com.court.oa.project.activity.Meet_Detail_activity;
import com.court.oa.project.activity.Notify_Detail_activity;
import com.court.oa.project.adapter.TMine_Question_fir_Adapter;
import com.court.oa.project.bean.LeaveListBean;
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
public class Mine_leave_thr_fragment extends Fragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener ,OkHttpManager.DataCallBack{
    private View view;
    private RefreshLayout swipeLayout;
    private ListView listView;
    private TMine_Question_fir_Adapter adapter;
    private int page = 1;
    private int type = 0;
    private ArrayList<LeaveListBean> listLeave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_mine_leave_fir_fragment, container, false);
        setData();
        initLeaveDate();
        setListener();
        return view;
    }
    /**
     * 添加数据
     */
    private void setData() {
        swipeLayout = view.findViewById(R.id.swipe_container);
        listView =  view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Notify_Detail_activity.class);
                getActivity().startActivity(intent);
            }
        });
    }
    private void initLeaveDate() {
        page=1;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("pageIndex", ""+page);
        parameters.put("type", ""+type);
        parameters.put("pageSize", "10");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.LEAVE_LIST, parameters,
                this, null, Contants.LEAVE_LIST);
    }
    private void initMoreLeaveDate() {
        page++;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("pageIndex", ""+page);
        parameters.put("type", ""+type);
        parameters.put("pageSize", "10");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.LEAVE_LIST, parameters,
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
                case Contants.LEAVE_LIST:
                    Gson gson = new Gson();
                    listLeave = gson.fromJson(jsonObj1, new TypeToken<List<LeaveListBean>>() {
                    }.getType());
                    adapter = new TMine_Question_fir_Adapter(getActivity(), listLeave);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getActivity(), Leave_apply_activity.class);
                            intent.putExtra("id",listLeave.get(i).getId()+"");
                            getActivity().startActivity(intent);
                        }
                    });
                    break;
                case Contants.MORE:
                    Gson gson1 = new Gson();
                    ArrayList<LeaveListBean> listLeave1 = gson1.fromJson(jsonObj1, new TypeToken<List<LeaveListBean>>() {
                    }.getType());
                    if(listLeave1.size()!=0){
                        for(int i = 0;i<listLeave1.size();i++){
                            listLeave.add(listLeave1.get(i));
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
                initLeaveDate();
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
                initMoreLeaveDate();
                swipeLayout.setLoading(false);
            }
        }, 2000);
    }


}
