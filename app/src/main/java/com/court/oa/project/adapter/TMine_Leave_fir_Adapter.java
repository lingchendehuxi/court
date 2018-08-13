package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.LeaveListBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class TMine_Leave_fir_Adapter extends BaseAdapter {
    public ArrayList<LeaveListBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TMine_Leave_fir_Adapter(Context context, ArrayList<LeaveListBean> list) {
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
            view = layoutInflater.inflate(R.layout.fragment_mine_leave_fir_listitem, null);
            holder = new ViewHolder();
            holder.leave_name  = view.findViewById(R.id.leave_name);
            holder.leave_reason  = view.findViewById(R.id.leave_reason);
            holder.leave_content  = view.findViewById(R.id.leave_content);
            holder.leave_day  = view.findViewById(R.id.leave_day);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        LeaveListBean bean = list.get(position);
        holder.leave_name.setText(bean.getApplyUser());
        holder.leave_reason.setText(bean.getVacationType());
        holder.leave_content.setText(bean.getReason());
        holder.leave_day.setText(bean.getDayCount()+"天");
        return view;
    }

    static class ViewHolder {
        TextView leave_name;
        TextView leave_reason;
        TextView leave_content;
        TextView leave_day;

    }

}
