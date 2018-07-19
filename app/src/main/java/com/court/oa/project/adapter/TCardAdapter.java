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
    private int type;

    public TCardAdapter(Context context, ArrayList<HashMap<String, String>> list,int type) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.type = type;
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
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        if(type == 1){
            if (convertView == null ) {
                view = layoutInflater.inflate(R.layout.fragment_card_listitem_1, null);
                holder1 = new ViewHolder1();
                holder1.txt  =(TextView) view.findViewById(R.id.tv_title);
                view.setTag(holder1);
            }else{
                view = convertView;
                holder1 = (ViewHolder1) view.getTag();
            }
            holder1.txt.setText(list.get(position).get("itemText"));
            return view;

        }else if(type == 2){
            if (convertView == null ) {
                view = layoutInflater.inflate(R.layout.fragment_card_listitem, null);
                holder2 = new ViewHolder2();
                holder2.txt  =(TextView) view.findViewById(R.id.tv_title);
                view.setTag(holder2);
            }else{
                view = convertView;
                holder2 = (ViewHolder2) view.getTag();
            }
            holder2.txt.setText(list.get(position).get("itemText"));
            return view;
        }else{

        }
        return view;
    }

    static class ViewHolder1 {

        TextView txt;
    }
    static class ViewHolder2 {

        TextView txt;
    }

}
