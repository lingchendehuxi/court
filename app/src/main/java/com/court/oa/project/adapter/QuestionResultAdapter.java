package com.court.oa.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.activity.MY_Salary_activity_ListDetail;
import com.court.oa.project.bean.QuestionOptionValueBean;
import com.court.oa.project.bean.SalaryListBean;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class QuestionResultAdapter extends BaseAdapter {
    public ArrayList<QuestionOptionValueBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public QuestionResultAdapter(Context context, ArrayList<QuestionOptionValueBean> list) {
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
            view = layoutInflater.inflate(R.layout.activity_question_result_item, null);
            holder = new ViewHolder();
            holder.item_title  = view.findViewById(R.id.item_title);
            holder.item_percent = view.findViewById(R.id.item_percent);
            holder.seek_progress = view.findViewById(R.id.seek_progress);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.item_title.setText(list.get(position).getTitle());
        holder.item_percent.setText(list.get(position).getPercent()+"");
        holder.seek_progress.setProgress(list.get(position).getSubmitCount());
        holder.seek_progress.setEnabled(false);
        return view;
    }

    class ViewHolder {
        TextView item_title;
        SeekBar seek_progress;
        TextView item_percent;
    }

}
