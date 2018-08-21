package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.MyMenuDetailChildren;
import com.court.oa.project.bean.MyMenuDetailParent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class TMine_Menu_ListDetial_Adapter extends BaseAdapter {
    public ArrayList<MyMenuDetailChildren> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TMine_Menu_ListDetial_Adapter(Context context, ArrayList<MyMenuDetailChildren> list) {
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
            view = layoutInflater.inflate(R.layout.activity_mine_menu_list_listdetail, null);
            holder = new ViewHolder();
            holder.menu_title  = view.findViewById(R.id.menu_title);
            holder.menu_mount  = view.findViewById(R.id.menu_mount);
            holder.menu_price  = view.findViewById(R.id.menu_price);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.menu_title.setText(list.get(position).getGoodsName());
        holder.menu_mount.setText("x"+list.get(position).getSubCount());
        holder.menu_price.setText("￥"+list.get(position).getSubCurrentPrice());
        return view;
    }

    static class ViewHolder {

        TextView menu_title;
        TextView menu_mount;
        TextView menu_price;
    }

}
