package com.court.oa.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.activity.MY_Salary_activity_ListDetail;
import com.court.oa.project.bean.MeetMainBean;
import com.court.oa.project.bean.SalaryListBean;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class SalaryListAdapter extends BaseAdapter {
    public ArrayList<SalaryListBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public SalaryListAdapter(Context context, ArrayList<SalaryListBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.activity_salary_list_detail, null);
            holder = new ViewHolder();
            holder.tv_title  = view.findViewById(R.id.tv_title);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_title.setText(list.get(position).getWagesName());
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MY_Salary_activity_ListDetail.class);
                intent.putExtra("wagesId",list.get(position).getWagesId());
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder {
        TextView tv_title;
    }

}
