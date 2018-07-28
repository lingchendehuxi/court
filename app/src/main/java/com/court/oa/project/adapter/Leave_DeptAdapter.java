package com.court.oa.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.DeptBean;
import com.court.oa.project.bean.MeetFileBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class Leave_DeptAdapter extends BaseAdapter {
    public ArrayList<DeptBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public Leave_DeptAdapter(Context context, ArrayList<DeptBean> list) {
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
            view = layoutInflater.inflate(R.layout.activity_mine_leave_chose_activity, null);
            holder = new ViewHolder();
            holder.leave_part  = view.findViewById(R.id.fj_title);
            holder.leave_number  = view.findViewById(R.id.fj_size);
            holder.list_1 = view.findViewById(R.id.list_1);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        DeptBean bean = list.get(position);
        holder.leave_part.setText(bean.getDeptName());
        holder.leave_number.setText(list.size()+"人");
        Leave_DeptUserAdapter adapter = new Leave_DeptUserAdapter(context,bean.getUsers());
        holder.list_1.setAdapter(adapter);
        return view;
    }

    class ViewHolder {
        TextView leave_part;
        TextView leave_number;
        ListView list_1;
    }

}
