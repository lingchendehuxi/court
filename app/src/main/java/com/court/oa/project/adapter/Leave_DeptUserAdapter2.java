package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.activity.Mine_leave_chose_activity;
import com.court.oa.project.bean.DeptUserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class Leave_DeptUserAdapter2 extends BaseAdapter {
    public ArrayList<DeptUserBean> list;
    public Context context;
    public LayoutInflater layoutInflater;
    private ArrayList<String> listString;

    public Leave_DeptUserAdapter2(Context context, ArrayList<DeptUserBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        listString = new ArrayList<>();
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
            view = layoutInflater.inflate(R.layout.fragment_leave_listuseritem2, null);
            holder = new ViewHolder();
            holder.cb_select = view.findViewById(R.id.cb_select);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        DeptUserBean bean = list.get(position);
        holder.cb_select.setText(bean.getRealName());
        holder.cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    listString.add(compoundButton.getText().toString());
                }else {
                    listString.remove(compoundButton.getText().toString());
                }
                ((Mine_leave_chose_activity)context).setListUser(listString);
            }
        });
        return view;
    }

    class ViewHolder {
        CheckBox cb_select;
    }

}
