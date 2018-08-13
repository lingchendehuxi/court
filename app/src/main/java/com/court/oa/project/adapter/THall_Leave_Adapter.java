package com.court.oa.project.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
    public Handler myHandler;

    public THall_Leave_Adapter(Context context, ArrayList<HallPackageGoodBean> list,Handler myHandler) {
        this.context = context;
        this.list = list;
        this.myHandler = myHandler;
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
        ViewHolder holder = null;
        if(convertView ==null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_hall_leave_listitem, null);
            holder.hall_leave_title = convertView.findViewById(R.id.hall_leave_title);
            holder.hall_leave_price = convertView.findViewById(R.id.hall_leave_price);
            holder.tv_showcount = convertView.findViewById(R.id.tv_showcount);
            holder.hall_leave_count = convertView.findViewById(R.id.hall_leave_count);
            holder.iv_reduce = convertView.findViewById(R.id.iv_reduce);
            holder.iv_plus = convertView.findViewById(R.id.iv_plus);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HallPackageGoodBean bean = list.get(position);
        holder.hall_leave_title.setText(bean.getName());
        holder.hall_leave_price.setText(bean.getPrice());
        holder.hall_leave_count.setText("剩余"+bean.getCount()+"份");
        holder.iv_plus.setTag(position);
        holder.iv_plus.setOnClickListener(new ButtonClickListener(holder.tv_showcount,bean.getCount(),holder.iv_reduce,Double.valueOf(bean.getPrice())));
        holder.iv_reduce.setTag(position);
        holder.iv_reduce.setOnClickListener(new ButtonClickListener(holder.tv_showcount,bean.getCount(),holder.iv_reduce,Double.valueOf(bean.getPrice())));
        return convertView;
    }
    private final class ViewHolder{
        public ImageView iv_plus;		//商品添加
        public ImageView iv_reduce;		//商品减少
        public TextView hall_leave_title;			//商品名称
        public TextView hall_leave_price;			//商品价格
        public TextView tv_showcount;		//商品数量
        public TextView hall_leave_count;		//商品剩余

    }

    //Button点击监听器
    private final class ButtonClickListener implements View.OnClickListener{
        private TextView tv_showcount;
        private int count;
        private double price;
        private ImageView iv_reduce;
        public ButtonClickListener(TextView textView,int count,ImageView iv_reduce,double price){
            tv_showcount = textView;
            this.count = count;
            this.iv_reduce = iv_reduce;
            this.price = price;
        }
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.iv_plus){
                if(count<=0){
                    ToastUtil.getShortToastByString(context,"已经没有了");
                }else {
                    iv_reduce.setVisibility(View.VISIBLE);
                    tv_showcount.setVisibility(View.VISIBLE);
                    int number = Integer.valueOf(tv_showcount.getText().toString());
                    number++;
                    if(number>count){
                        number--;
                        ToastUtil.getShortToastByString(context,"只能点这么多哦");
                        return;
                    }
                    tv_showcount.setText(number+"");
                    HallPackageGoodBean bean = list.get((int)v.getTag());
                    price = number * price;
                    bean.setCount(number);
                    bean.setUnit(""+price);
                    myHandler.sendMessage(myHandler.obtainMessage(100, bean));
                }
            }else if(v.getId() == R.id.iv_reduce){
                int number = Integer.valueOf(tv_showcount.getText().toString());
                number--;
                if(number<=0){
                    iv_reduce.setVisibility(View.INVISIBLE);
                    tv_showcount.setVisibility(View.INVISIBLE);
                }
                tv_showcount.setText(number+"");
                HallPackageGoodBean bean = list.get((int)v.getTag());
                price = number * price;
                bean.setCount(number);
                bean.setUnit(""+price);
                myHandler.sendMessage(myHandler.obtainMessage(101, bean));
            }
        }
    }


}
