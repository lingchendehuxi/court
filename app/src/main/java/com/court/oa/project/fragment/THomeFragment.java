package com.court.oa.project.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.court.oa.project.R;
import com.court.oa.project.adapter.MyPagerAdapter1;
import com.court.oa.project.adapter.THomeAdapter;
import com.court.oa.project.tool.MyViewPagerTransformerAnim;
import com.court.oa.project.tool.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class THomeFragment extends Fragment implements View.OnClickListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private RefreshLayout swipeLayout;
    private ArrayList list;
    private ListView listView;
    private THomeAdapter adapter;
    private View header;
    private MyViewPagerTransformerAnim vp_imgs;
    private ArrayList<Bitmap> listAdaver;
    private boolean isRunning = false;
    private LinearLayout pointGroups;
    protected int lastPosition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.thomefragment, null);
        initView();
        setData();
        setListener();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    /**
     * 初始化布局
     */
    @SuppressLint({"InlinedApi", "InflateParams"})
    private void initView() {
        header = getActivity().getLayoutInflater().inflate(R.layout.thome_header, null);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.theme_color);
        vp_imgs = (MyViewPagerTransformerAnim) header.findViewById(R.id.vp_imgs);
        pointGroups = (LinearLayout) header.findViewById(R.id.point_groups);
        listAdaver = new ArrayList<>();
        Bitmap Imag1 = BitmapFactory.decodeResource(getResources(), R.mipmap.home_top);
        Bitmap Imag2 = BitmapFactory.decodeResource(getResources(), R.mipmap.home1);
        Bitmap Imag3 = BitmapFactory.decodeResource(getResources(), R.mipmap.home2);
        listAdaver.add(Imag1);
        listAdaver.add(Imag2);
        listAdaver.add(Imag3);
        isRunning = true;
        handlers.sendEmptyMessageDelayed(0, 5000);
        initviewpager();
    }

    private Handler handlers = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (listAdaver.size() > 1) {
                vp_imgs.setCurrentItem(vp_imgs.getCurrentItem() + 1);
                if (isRunning) {
                    handlers.sendEmptyMessageDelayed(0, 5000);
                }
            }

        }

        ;
    };

    /**
     * 添加数据
     */
    private void setData() {
        list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemImage", i + "默认");
            map.put("itemText", i + "默认");
            list.add(map);
        }
        listView = (ListView) view.findViewById(R.id.list);
        listView.addHeaderView(header);
        adapter = new THomeAdapter(getActivity(), list);
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
                list.clear();
                for (int i = 0; i < 3; i++) {
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

    private void initviewpager() {
        if (listAdaver.size() > 1) {
            for (int i = 0; i < listAdaver.size(); i++) {
                ImageView point = new ImageView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = 20;
                point.setLayoutParams(params);
                point.setBackgroundResource(R.drawable.point_bg);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                pointGroups.addView(point);

            }
        }
        vp_imgs.setAdapter(new MyPagerAdapter1(getContext(), listAdaver));
    }


    @SuppressWarnings("deprecation")
    private void pagerlistener() {
        vp_imgs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            /**
             */
            public void onPageSelected(int position) {
                if (listAdaver.size() > 1) {
                    position = position % listAdaver.size();
                    pointGroups.getChildAt(position).setEnabled(true);
                    pointGroups.getChildAt(lastPosition).setEnabled(false);
                    lastPosition = position;
                }

            }

            @Override
            /**
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             */
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
