package com.court.oa.project.fragment;

import android.content.Intent;
import android.opengl.Visibility;
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
import com.court.oa.project.activity.Leave_apply_activity;
import com.court.oa.project.activity.Meet_Detail_activity;
import com.court.oa.project.activity.Mine_leave_addmine_activity;
import com.court.oa.project.activity.Mine_leave_chose_activity;
import com.court.oa.project.adapter.TCardAdapter;
import com.court.oa.project.adapter.TCardDetailAdapter;
import com.court.oa.project.adapter.TMine_Leave_fir_Adapter;
import com.court.oa.project.bean.LeaveListBean;
import com.court.oa.project.bean.TCardBean;
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
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class TCardFragment extends Fragment implements OkHttpManager.DataCallBack {
    private View view;
    private ArrayList list;
    private ListView listView;
    private TCardAdapter adapter;
    private TCardDetailAdapter tCardDetailAdapter;
    private CheckBox cb_person, cb_time;
    private ArrayList<TCardBean> tCardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tcardfragment, null);
        initView();
        return view;
    }

    private void initView() {
        cb_person = view.findViewById(R.id.cb_person);
        listView = view.findViewById(R.id.list);
        cb_time = view.findViewById(R.id.cb_time);
        cb_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Mine_leave_chose_activity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 100);
            }
        });
        cb_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setTimeData();
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                }
            }
        });
        if("3".equals(SharePreferenceUtils.readUser("role",getActivity()))){
            cb_person.setVisibility(View.GONE);
        }else {
            cb_person.setVisibility(View.VISIBLE);
        }
    }

    /**
     * else
     * 添加数据
     */
    private void initCardData(String data) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("month", data);
        parameters.put("uid", SharePreferenceUtils.readUser("userId", getActivity()));
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.CARD_LIST, parameters,
                this, Contants.CARD_LIST);
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
                case Contants.CARD_LIST:
                    Gson gson = new Gson();
                    tCardList = gson.fromJson(jsonObj1, new TypeToken<List<TCardBean>>() {
                    }.getType());
                    adapter = null;
                    tCardDetailAdapter = new TCardDetailAdapter(getActivity(),tCardList);
                    listView.setAdapter(tCardDetailAdapter);
                    listView.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

    }


    /**
     * 添加时间数据
     */
    private void setTimeData() {
        list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("itemText", "上月");
        map.put("itemValue", Utils.getFetureMonth(Utils.getCurrentMonthLastDay()));
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("itemText", "本月");
        map1.put("itemValue", Utils.getFetureMonth(0));
        list.add(map);
        list.add(map1);
        listView = view.findViewById(R.id.list);
        adapter = new TCardAdapter(getActivity(), list);
        tCardDetailAdapter = null;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> map = (HashMap<String, String>) list.get(i);
                cb_time.setText(map.get("itemText"));
                listView.setVisibility(View.INVISIBLE);
                cb_time.setChecked(false);
                isShow(map.get("itemValue"));
            }
        });
    }

    //设置检查是否两个选项全选中
    private void isShow(String data) {
        if(cb_person.getVisibility() == View.INVISIBLE){
            initCardData(data);
        }else {
            if ("人员".equals(cb_person.getText().toString()) || "时间".equals(cb_time.getText().toString())) {
                ToastUtil.getShortToastByString(getActivity(),"请先选择员工");
                return;
            }
            initCardData(data);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && requestCode == 100) {
            cb_person.setText(data.getStringExtra("user"));
        }
    }
}
