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
import com.court.oa.project.bean.MeetFileBean;
import com.court.oa.project.bean.MeetMainBean;
import com.court.oa.project.utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MateBook D on 2018/5/15.
 */

public class TMeeFJAdapter extends BaseAdapter {
    public ArrayList<MeetFileBean> list;
    public Context context;
    public LayoutInflater layoutInflater;

    public TMeeFJAdapter(Context context, ArrayList<MeetFileBean> list) {
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
            view = layoutInflater.inflate(R.layout.activity_meet_detail_fj, null);
            holder = new ViewHolder();
            holder.fj_img  = view.findViewById(R.id.fj_img);
            holder.fj_title  = view.findViewById(R.id.fj_title);
            holder.fj_size  = view.findViewById(R.id.fj_size);
            holder.tv_openfile  = view.findViewById(R.id.tv_openfile);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        MeetFileBean bean = list.get(position);
        if(!TextUtils.isEmpty(bean.getFileUrl())){
            Picasso.with(context).load(bean.getFileUrl()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(holder.fj_img);
        }
        holder.fj_title.setText(bean.getFileName());
        holder.fj_size.setText(bean.getFileType());
        String url = list.get(position).getFileUrl();
        int position2 = url.lastIndexOf("/");
        String file = url.substring(position2+1,url.length());
        FileUtils fileUtils = new FileUtils();
        if(fileUtils.isFileExist(file,"WJ_count")){
            holder.tv_openfile.setText("打开附件");
        }else {
            holder.tv_openfile.setText("附件");
        }
        /*if(fileUtils.isFileExist("足音.mp3","BoBoMusic")){
            tv_openfile.setText("打开附件");
        }*/
        return view;
    }

    class ViewHolder {
        ImageView fj_img;
        TextView fj_title;
        TextView fj_size;
        TextView tv_openfile;
    }

}
