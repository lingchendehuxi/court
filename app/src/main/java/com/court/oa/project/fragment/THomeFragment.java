package com.court.oa.project.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.THomeAdapter;
import com.court.oa.project.bean.ArticalBean;
import com.court.oa.project.bean.ArticalListBean;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.okhttp.OkHttpManager;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.RefreshLayout;
import com.court.oa.project.utils.GlideImageLoader;
import com.court.oa.project.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class THomeFragment extends Fragment implements View.OnClickListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, OkHttpManager.DataCallBack {

    private View view;
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView;
    private THomeAdapter adapter;
    private View header;
    private ArrayList<String> listAdaver;
    private ArrayList<ArticalBean> articalList;
    private ArrayList<ArticalBean> newList;
    private ArrayList<ArticalListBean> topList;
    private ArrayList<ArticalListBean> notifyList;
    private ArrayList<ArticalListBean> hallList;
    private ArrayList<ArticalListBean> meetList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.thomefragment, null);
        initView();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    public static Handler myHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    };

    public static Handler getMainThreadHandler() {
        return myHandler;
    }

    /**
     * 初始化布局
     */
    @SuppressLint({"InlinedApi", "InflateParams"})
    private void initView() {
        header = getActivity().getLayoutInflater().inflate(R.layout.thome_header, null);
        swipeLayout = view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.theme_color);
        listAdaver = new ArrayList<>();
        topList = new ArrayList<>();
        notifyList = new ArrayList<>();
        hallList = new ArrayList<>();
        meetList = new ArrayList<>();
        newList = new ArrayList<>();
        listView = view.findViewById(R.id.list);
        listView.addHeaderView(header);
        initArticalDate();
    }

    private void initArticalDate() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("pageIndex", "" + 1);
        parameters.put("pageSize", "10");
        parameters.put("appToken", SharePreferenceUtils.readUser("appToken", getActivity()));
        OkHttpManager.postAsync(
                Contants.ARTICLE_LIST, parameters,
                this, Contants.ARTICLE_LIST);
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
                case Contants.ARTICLE_LIST:
                    Gson gson = new Gson();
                    articalList = gson.fromJson(jsonObj1, new TypeToken<List<ArticalBean>>() {
                    }.getType());
                    for (int i = 0; i < articalList.size(); i++) {
                        if ("614".equals(articalList.get(i).getCtgId())) {
                            topList.clear();
                            for (int j = 0; j < articalList.get(i).getInfoList().size(); j++) {
                                topList.add(articalList.get(i).getInfoList().get(j));
                            }
                        } else if ("611".equals(articalList.get(i).getCtgId())) {
                            meetList.clear();
                            for (int j = 0; j < articalList.get(i).getInfoList().size(); j++) {
                                meetList.add(articalList.get(i).getInfoList().get(j));
                            }
                        } else if ("612".equals(articalList.get(i).getCtgId())) {
                            notifyList.clear();
                            for (int j = 0; j < articalList.get(i).getInfoList().size(); j++) {
                                notifyList.add(articalList.get(i).getInfoList().get(j));
                            }
                        } else if ("613".equals(articalList.get(i).getCtgId())) {
                            hallList.clear();
                            for (int j = 0; j < articalList.get(i).getInfoList().size(); j++) {
                                hallList.add(articalList.get(i).getInfoList().get(j));
                            }
                        }
                    }
                    setData();
                    setListener();
                    initviewpager();
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 添加数据
     */
    private void setData() {
        newList.clear();
        if (articalList.size() >= 4) {
            for (int i = 1; i < 4; i++) {
                newList.add(articalList.get(i));
            }
            newList.get(0).setInfoList(hallList);
            newList.get(1).setInfoList(notifyList);
            newList.get(2).setInfoList(meetList);
        }

        adapter = new THomeAdapter(getActivity(), newList);
        listView.setAdapter(adapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        //swipeLayout.setOnLoadListener(this);
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
                initArticalDate();
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
            }
        }, 2000);
    }

    private void initviewpager() {
        listAdaver.clear();
        for (int i = 0; i < topList.size(); i++) {
            listAdaver.add(topList.get(i).getImgUrl());
        }
        if (listAdaver.size() > 1) {
            Banner banner = header.findViewById(R.id.myBanner);
            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(listAdaver);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            //banner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(5000);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }
}
