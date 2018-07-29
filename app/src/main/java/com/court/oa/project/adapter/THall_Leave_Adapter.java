package com.court.oa.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.HallPackageGoodBean;
import com.court.oa.project.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class THall_Leave_Adapter extends BaseAdapter {
    public ArrayList<HallPackageGoodBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public THall_Leave_Adapter(Context context, ArrayList<HallPackageGoodBean> list) {
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
        view = layoutInflater.inflate(R.layout.fragment_hall_leave_listitem, null);
        TextView hall_leave_title = view.findViewById(R.id.hall_leave_title);
        TextView hall_leave_price = view.findViewById(R.id.hall_leave_price);
        final TextView tv_showcount = view.findViewById(R.id.tv_showcount);
        TextView hall_leave_count = view.findViewById(R.id.hall_leave_count);
        final ImageView iv_reduce = view.findViewById(R.id.iv_reduce);
        ImageView iv_plus = view.findViewById(R.id.iv_plus);
        final HallPackageGoodBean bean = list.get(position);
        hall_leave_title.setText(bean.getName());
        hall_leave_price.setText(bean.getPrice());
        hall_leave_count.setText("剩余"+bean.getCount()+"份");
        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean.getCount()<=0){
                    ToastUtil.getShortToastByString(context,"已经没有了");
                }else {
                    iv_reduce.setVisibility(View.VISIBLE);
                    tv_showcount.setVisibility(View.VISIBLE);
                    int count = Integer.valueOf(tv_showcount.getText().toString());
                    count++;
                    if(count>bean.getCount()){
                        ToastUtil.getShortToastByString(context,"只能点这么多哦");
                        return;
                    }
                    tv_showcount.setText(count+"");
                }

            }
        });
        iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.valueOf(tv_showcount.getText().toString());
                count--;
                tv_showcount.setText(count+"");
                if(count<=0){
                    iv_reduce.setVisibility(View.INVISIBLE);
                    tv_showcount.setVisibility(View.INVISIBLE);
                }
            }
        });
        return view;
    }


}
