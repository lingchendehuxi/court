package com.court.oa.project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.bean.DeptBean;
import com.court.oa.project.bean.DeptUserBean;
import com.court.oa.project.bean.MeetFileBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class Leave_DeptUserAdapter extends BaseAdapter {
    public ArrayList<DeptUserBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public Leave_DeptUserAdapter(Context context, ArrayList<DeptUserBean> list) {
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
            view = layoutInflater.inflate(R.layout.fragment_card_listitem_1, null);
            holder = new ViewHolder();
            holder.tv_title  = view.findViewById(R.id.tv_title);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        DeptUserBean bean = list.get(position);
        holder.tv_title.setText(bean.getRealName());
        return view;
    }

    class ViewHolder {
        TextView tv_title;
    }

}
