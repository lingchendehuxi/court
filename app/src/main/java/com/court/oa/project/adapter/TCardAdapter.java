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

public class TCardAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TCardAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.fragment_card_listitem_1, null);
            holder = new ViewHolder();
            holder.tv_title = view.findViewById(R.id.tv_title);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_title.setText(list.get(position).get("itemText"));
        return view;
    }

    static class ViewHolder {
        TextView tv_title;
    }

}
