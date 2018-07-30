package com.court.oa.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private int type;

    public Leave_DeptAdapter(Context context, ArrayList<DeptBean> list,int type) {
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
        view = layoutInflater.inflate(R.layout.activity_mine_leave_chose_activity, null);
        TextView leave_part = view.findViewById(R.id.leave_part);
        TextView leave_number = view.findViewById(R.id.leave_number);
        final ListView list_1 = view.findViewById(R.id.list_1);
        CheckBox cb_part = view.findViewById(R.id.cb_part);
        final DeptBean bean = list.get(position);
        leave_part.setText(bean.getDeptName());
        leave_number.setText(bean.getUsers().size() + "人");
        if(type ==1){
            Leave_DeptUserAdapter adapter = new Leave_DeptUserAdapter(context, bean.getUsers());
            list_1.setAdapter(adapter);
        }else if(type ==2){
            Leave_DeptUserAdapter2 adapter = new Leave_DeptUserAdapter2(context, bean.getUsers());
            list_1.setAdapter(adapter);
        }


        list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("user",bean.getUsers().get(i).getRealName());
                ((Activity)context).setResult(100,intent);
                ((Activity)context).finish();
            }
        });
        cb_part.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    list_1.setVisibility(View.GONE);
                }else {
                    list_1.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }


}
