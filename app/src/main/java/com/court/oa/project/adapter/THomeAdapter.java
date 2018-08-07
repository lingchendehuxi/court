package com.court.oa.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.activity.Meet_Detail_activity;
import com.court.oa.project.activity.Notify_Detail_activity;
import com.court.oa.project.bean.ArticalBean;
import com.court.oa.project.bean.ArticalListBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class THomeAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater layoutInflater;
    private ArrayList<ArticalBean> listBean;

    public THomeAdapter(Context context, ArrayList<ArticalBean> listBean) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        return listBean.size();
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
            view = layoutInflater.inflate(R.layout.fragment_home_listitem, null);
            holder = new ViewHolder();
            holder.txt  = view.findViewById(R.id.home_title);
            holder.home_more  = view.findViewById(R.id.home_more);
            holder.not_list = view.findViewById(R.id.not_list);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.txt.setText(listBean.get(position).getCtgName());
        THomeListAdapter adapter = new THomeListAdapter(context,listBean.get(position).getInfoList());
        holder.not_list.setAdapter(adapter);
        final int j = position;
        holder.home_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).otherSkip(j);
            }
        });
        holder.not_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(j==0){
                    ((MainActivity)context).otherSkip(j);
                }else if(j==1){
                    Intent intent = new Intent(context, Notify_Detail_activity.class);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, Meet_Detail_activity.class);
                    intent.putExtra("meetId",listBean.get(j).getInfoList().get(i).getId());
                    context.startActivity(intent);
                }
            }
        });
        return view;
    }

    static class ViewHolder {

        TextView txt;
        TextView home_more;
        ListView not_list;
    }

}
