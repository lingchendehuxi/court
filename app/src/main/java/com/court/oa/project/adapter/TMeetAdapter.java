package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.MeetMainBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class TMeetAdapter extends BaseAdapter {
    public ArrayList<MeetMainBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TMeetAdapter(Context context, ArrayList<MeetMainBean> list) {
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
            view = layoutInflater.inflate(R.layout.fragment_meet_listitem, null);
            holder = new ViewHolder();
            holder.meetList_title  = view.findViewById(R.id.meetList_title);
            holder.meetList_context  = view.findViewById(R.id.meetList_context);
            holder.meetList_time  = view.findViewById(R.id.meetList_time);
            holder.meetList_address  = view.findViewById(R.id.meetList_address);
            holder.meetList_start  = view.findViewById(R.id.meetList_start);
            holder.meetList_end  = view.findViewById(R.id.meetList_end);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        MeetMainBean bean = list.get(position);
        holder.meetList_title.setText(bean.getTitle());
        holder.meetList_context.setText(bean.getJoinUsers());
        holder.meetList_time.setText("时间: "+bean.getStartTime());
        holder.meetList_address.setText("地点： "+bean.getAddress());
        holder.meetList_start.setText(bean.getJoinStartTime()+"报名");
        holder.meetList_end.setText(bean.getJoinEndTime()+"截止");
        return view;
    }

    class ViewHolder {
        TextView meetList_title;
        TextView meetList_context;
        TextView meetList_time;
        TextView meetList_address;
        TextView meetList_start;
        TextView meetList_end;
    }

}
