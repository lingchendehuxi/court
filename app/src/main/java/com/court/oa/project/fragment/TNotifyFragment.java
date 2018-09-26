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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.activity.Leave_apply_activity;
import com.court.oa.project.activity.Meet_Detail_activity;
import com.court.oa.project.activity.Mine_leave_activity;
import com.court.oa.project.activity.Notify_Detail_activity;
import com.court.oa.project.activity.Notify_question_result_activity;
import com.court.oa.project.activity.Question_activity;
import com.court.oa.project.adapter.TMeetAdapter;
import com.court.oa.project.adapter.TNotifyAdapter;
import com.court.oa.project.bean.MeetMainBean;
import com.court.oa.project.bean.MessageBean;
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


public class TNotifyFragment extends Fragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, OkHttpManager.DataCallBack {
    private View view;
    private RefreshLayout swipeLayout;
    private ListView listView;
    private TNotifyAdapter adapter;
    private ArrayList<MessageBean> listMessage = new ArrayList<>();
    private int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.tnotifyfragment, null);
        initView();
        setListener();
        return view;
    }

    private void initView() {
        swipeLayout = view.findViewById(R.id.swipe_container);
        listView = view.findViewById(R.id.list);
        initMessageData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listMessage.get(i).getMsgCtg() == 3) {
                    Intent intent = new Intent(getActivity(), Question_activity.class);
                    intent.putExtra("exId", listMessage.get(i).getMsgCtgId());
                    getActivity().startActivity(intent);
                }else if(listMessage.get(i).getMsgCtg() == 2){
                    Intent intent = new Intent(getActivity(), Leave_apply_activity.class);
                    intent.putExtra("id",listMessage.get(i).getMsgId()+"");
                    intent.putExtra("type",2);
                    getActivity().startActivity(intent);
                }else if(listMessage.get(i).getMsgCtg() == 1){
                    Intent intent = new Intent(getActivity(), Meet_Detail_activity.class);
                    intent.putExtra("meetId", listMessage.get(i).getMsgCtgId());
                    getActivity().startActivity(intent);
                }

            }
        });
    }

    private void initMessageData() {
        page = 1;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("pageIndex", "" + page);
        parameters.put("pageSize", "10");
        parameters.put("uid", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.MESSAGE_LIST, parameters,
                this, Contants.MESSAGE_LIST);
    }

    private void initMoreMessageData() {
        page++;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("pageIndex", "" + page);
        parameters.put("pageSize", "10");
        parameters.put("uid", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.MESSAGE_LIST, parameters,
                this, Contants.MORE);
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
                case Contants.MESSAGE_LIST:
                    Gson gson = new Gson();
                    listMessage = gson.fromJson(jsonObj1, new TypeToken<List<MessageBean>>() {
                    }.getType());
                    adapter = new TNotifyAdapter(getActivity(), listMessage);
                    listView.setAdapter(adapter);
                    if (listMessage.size() <= 10) {
                        swipeLayout.setOnLoadListener(null);
                    } else {
                        swipeLayout.setOnLoadListener(this);
                    }
                    break;
                case Contants.MORE:
                    Gson gson1 = new Gson();
                    ArrayList<MessageBean> listMeet1 = gson1.fromJson(jsonObj1, new TypeToken<List<MessageBean>>() {
                    }.getType());
                    if (listMeet1.size() > 0) {
                        for (int i = 0; i < listMeet1.size(); i++) {
                            listMessage.add(listMeet1.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
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
                if (listMessage != null) {
                    listMessage.clear();
                }
                initMessageData();
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
                initMoreMessageData();
            }
        }, 2000);
    }


}
