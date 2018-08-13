package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.SalaryListBean;
import com.court.oa.project.bean.SalaryListConsumeDetailBean;
import com.court.oa.project.bean.SalaryListDetailBean;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class SalaryListDetailAdapter extends BaseAdapter {
    public ArrayList<SalaryListConsumeDetailBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public SalaryListDetailAdapter(Context context, ArrayList<SalaryListConsumeDetailBean> list) {
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
            view = layoutInflater.inflate(R.layout.activity_salary_list_detail_list, null);
            holder = new ViewHolder();
            holder.salary_key  = view.findViewById(R.id.salary_key);
            holder.salary_value  = view.findViewById(R.id.salary_value);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.salary_key.setText(list.get(position).getKeyName());
        holder.salary_value.setText(list.get(position).getKeyValue());
        return view;
    }

    class ViewHolder {
        TextView salary_key;
        TextView salary_value;
    }

}
