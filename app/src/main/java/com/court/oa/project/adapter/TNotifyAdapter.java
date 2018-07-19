package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class TNotifyAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TNotifyAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
            view = layoutInflater.inflate(R.layout.fragment_notify_listitem, null);
            holder = new ViewHolder();
            holder.txt  =(TextView) view.findViewById(R.id.notifyList_title);
            holder.tv_time = view.findViewById(R.id.tv_time);
            holder.tv_question = view.findViewById(R.id.tv_question);
            holder.tv_person = view.findViewById(R.id.tv_person);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.txt.setText(list.get(position).get("itemText"));
        String type = list.get(position).get("isQustion");
        if("0".equals(type)){
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_person.setVisibility(View.VISIBLE);
            holder.tv_question.setVisibility(View.GONE);
        }else {
            holder.tv_question.setVisibility(View.VISIBLE);
            holder.tv_person.setVisibility(View.INVISIBLE);
            holder.tv_time.setVisibility(View.GONE);
        }
        holder.tv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    class ViewHolder {

        TextView txt;
        TextView tv_question;
        TextView tv_time;
        TextView tv_person;
    }

}
