package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.DeptBean;
import com.court.oa.project.bean.HallWeekBean;
import com.court.oa.project.bean.HallWeekDetailBean;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class Hall_week_dataAdapter extends BaseAdapter {
    public ArrayList<HallWeekDetailBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public Hall_week_dataAdapter(Context context, ArrayList<HallWeekDetailBean> list) {
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
            view = layoutInflater.inflate(R.layout.fragment_hall_week_date, null);
            holder = new ViewHolder();
            holder.tv_menu1  = view.findViewById(R.id.tv_menu1);
            holder.tv_context1  = view.findViewById(R.id.tv_context1);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        HallWeekDetailBean bean = list.get(position);
        holder.tv_menu1.setText(bean.getMenuType());
        holder.tv_context1.setText(bean.getMenuContent());
        return view;
    }

    class ViewHolder {
        TextView tv_menu1;
        TextView tv_context1;
    }

}
