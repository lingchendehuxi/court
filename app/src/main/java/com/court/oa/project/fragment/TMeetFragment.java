package com.court.oa.project.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.activity.Meet_Detail_activity;
import com.court.oa.project.activity.MipcaActivityCapture;
import com.court.oa.project.activity.Register_My_activity;
import com.court.oa.project.adapter.TMeetAdapter;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.ParseUser;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.RefreshLayout;
import com.court.oa.project.utils.MD5Utils;
import com.court.oa.project.utils.ToastUtil;
import com.court.oa.project.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

public class TMeetFragment extends Fragment implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener,OkHttpManager.DataCallBack{
    private View view;
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView;
    private TMeetAdapter adapter;
    private ImageView iv_scaner;
    private Activity contextActivity;

    private final static int SCANNIN_GREQUEST_CODE = 1;
    private final int CAMERA_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.tmeetfragment, null);
        contextActivity = getActivity();
        initView();
        setData();
        setListener();
        return view;
    }

    private void initView() {
        CheckBox cb_set = view.findViewById(R.id.cb_set);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        iv_scaner = view.findViewById(R.id.iv_scaner);
        initMeetDate();

    }
    private void initMeetDate(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("order", "");
        parameters.put("pageIndex", "1");
        parameters.put("pageSize", "10");
        OkHttpManager.postAsync(
                Contants.MEETING_LIST, parameters,
                this, null, Contants.MEETING_LIST);
    }
    @Override
    public void requestFailure(Request request, IOException e, String method) {
        ToastUtil.getShortToastByString(getActivity(),
                getString(R.string.networkRequst_resultFailed));
    }

    @Override
    public void requestSuccess(String result, String method) throws Exception {
        JSONObject object = new JSONObject(result);
        switch (method) {
            case Contants.MEETING_LIST:
                ToastUtil.show(getContext(),object.getString("msg"));
                if (object.getInt("code") == 1) {
                }

                break;

            default:
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
        iv_scaner.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_scaner:
                if(Utils.isFastClick()){
                    scaner();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this,"相机权限已申请",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(contextActivity, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            } else {
                //用户勾选了不再询问
                //提示用户手动打开权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(contextActivity, Manifest.permission.CAMERA)) {
                    Toast.makeText(contextActivity, "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == contextActivity.RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    //textView.setText(bundle.getString("result"));
                    Toast.makeText(contextActivity, bundle.getString("result"), Toast.LENGTH_SHORT).show();
                    //显示
//                    byte[] b = bundle.getByteArray("bitmap");
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//                    mImageView.setImageBitmap(bitmap);
                }
                break;
        }
    }

    public void scaner() {
        if(ActivityCompat.checkSelfPermission(contextActivity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if(ActivityCompat.shouldShowRequestPermissionRationale(contextActivity,Manifest.permission.CAMERA)){
                new AlertDialog.Builder(contextActivity)
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(contextActivity,new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            }
                        }).show();

            }else {
                //申请相机权限
                ActivityCompat.requestPermissions(contextActivity,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        }else {
            Intent intent = new Intent();
            intent.setClass(contextActivity, MipcaActivityCapture.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
        }
    }
}
