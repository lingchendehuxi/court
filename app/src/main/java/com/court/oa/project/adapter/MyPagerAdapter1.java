package com.court.oa.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class MyPagerAdapter1 extends PagerAdapter {
    private Context context;
    private ArrayList<Bitmap> listBitmap;
    public MyPagerAdapter1(Context context, ArrayList<Bitmap> listBitmap){
        this.context = context;
        this.listBitmap = listBitmap;
    }

    @Override
    /**
     */
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    /**
     */
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView view = new ImageView(context);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        if (listBitmap.size() == 0) {
            return view;
        }
        view.setImageBitmap(listBitmap.get(position % listBitmap.size()));
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                /*if (TextUtils.isEmpty(listAdaver.get(
                        position % listBitmap.size()).getAdverImageLink())) {
                    return;
                }*/
                //跳转方法
               /* intent = new Intent(getActivity(),
                        ShouYeDongAdverDetailActivity.class);
                intent.putExtra("adverImageLink",
                        listAdaver.get(position % listBitmap.size())
                                .getAdverImageLink());

                startActivity(intent);*/
            }
        });

        // 在这个方法里面设置图片的点击事件
        return view;
    }

    @Override
    /**
     * �ж� view��object�Ķ�Ӧ��ϵ
     */
    public boolean isViewFromObject(View view, Object object) {
        if (view == object) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    /**
     * ���ٶ�Ӧλ���ϵ�object
     */
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        object = null;
    }
}

