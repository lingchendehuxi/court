package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.MyMenuSendBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class TMine_MenuAdapter extends BaseAdapter {
    public ArrayList<MyMenuSendBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TMine_MenuAdapter(Context context, ArrayList<MyMenuSendBean> list) {
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
            view = layoutInflater.inflate(R.layout.activity_mine_menu_listitem, null);
            holder = new ViewHolder();
            holder.tmine_menu_Listtitle  =(TextView) view.findViewById(R.id.tmine_menu_Listtitle);
            holder.tmine_menu_Listname  =(TextView) view.findViewById(R.id.tmine_menu_Listname);
            holder.tmine_menu_Listtime  =(TextView) view.findViewById(R.id.tmine_menu_Listtime);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.tmine_menu_Listtitle.setText(list.get(position).getDesc());
        holder.tmine_menu_Listname.setText(list.get(position).getTakeUser());
        holder.tmine_menu_Listtime.setText(list.get(position).getTakeTime());
        return view;
    }

    static class ViewHolder {

        TextView tmine_menu_Listtitle;
        TextView tmine_menu_Listname;
        TextView tmine_menu_Listtime;
    }

}
