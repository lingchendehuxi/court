package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.HallWeekDetailBean;
import com.court.oa.project.bean.TCardBean;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class TCardDetailAdapter extends BaseAdapter {
    public ArrayList<TCardBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TCardDetailAdapter(Context context, ArrayList<TCardBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.fragment_card_listitem, null);
            holder = new ViewHolder();
            holder.tv_title  = view.findViewById(R.id.tv_title);
            holder.tv_detail1  = view.findViewById(R.id.tv_detail1);
            holder.tv_detail2  = view.findViewById(R.id.tv_detail2);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        TCardBean bean = list.get(position);
        holder.tv_title.setText(bean.getAdate());
        if("0".equals(bean.getStatus())){
            holder.tv_detail1.setText(bean.getSigninDesc());
            holder.tv_detail2.setText(bean.getSignoutDesc());
        }else {
            holder.tv_detail1.setText(bean.getDesc());
            holder.tv_detail2.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_detail1;
        TextView tv_detail2;
    }

}
