package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.ArticalListBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class THomeListAdapter extends BaseAdapter {
    public ArrayList<ArticalListBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public THomeListAdapter(Context context, ArrayList<ArticalListBean> list) {
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
            view = layoutInflater.inflate(R.layout.fragment_home_item, null);
            holder = new ViewHolder();
            holder.home_list_title  = view.findViewById(R.id.home_list_title);
            holder.home_list_context  = view.findViewById(R.id.home_list_context);
            holder.home_list_time  = view.findViewById(R.id.home_list_time);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.home_list_title.setText(list.get(position).getTitle());
        holder.home_list_context.setText(list.get(position).getDesc());
        holder.home_list_time.setText(list.get(position).getPublishTime());
        return view;
    }

    static class ViewHolder {

        TextView home_list_title;
        TextView home_list_context;
        TextView home_list_time;
    }

}
