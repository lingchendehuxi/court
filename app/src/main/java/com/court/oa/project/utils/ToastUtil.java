package com.court.oa.project.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/*
 * 全局toast，解决多个toast同时显示
 */
public class ToastUtil {
    public static Toast mToast;
    /**
     * 传入文字
     * */
    public static void show( Context context , String text){

        if (mToast == null){
            mToast = Toast.makeText( context, text , Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER , 0 , 0);
        mToast.show();
    }
    /**
     * 传入资源文件
     * */
    public static void show(Context context, int resId){
        if (mToast == null){
            mToast = Toast.makeText( context, resId , Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(resId);
        }
        mToast.show();
    }
    /**
     * 传入文字,在中间显示
     * */
    public static void showCenter( Context context , String text){

        if (mToast == null){
            mToast = Toast.makeText( context, text , Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER , 0 , 0);
        mToast.show();
    }
    /**
     * 传入文字，带图片
     * */
    public static void showImg( Context context , String text , int resImg){

        if (mToast == null){
            mToast = Toast.makeText( context, text , Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        //添加图片的操作,这里没有设置图片和文字显示在一行的操作呢...
        LinearLayout view = (LinearLayout) mToast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resImg);
        view.addView(imageView);

        mToast.show();
    }

    public static void getShortToast(Context context,int retId){
        if (mToast == null) {
            mToast = Toast.makeText(context, retId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(retId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }


        mToast.show();
    }


    public static void getShortToastByString(Context context,String hint){
        if (mToast == null) {
            mToast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(hint);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }


        mToast.show();
    }


    public static void getLongToast(Context context,int retId){
        if (mToast == null) {
            mToast = Toast.makeText(context, retId, Toast.LENGTH_LONG);
        } else {
            mToast.setText(retId);
            mToast.setDuration(Toast.LENGTH_LONG);
        }


        mToast.show();
    }


    public static void getLongToastByString(Context context,String hint){
        if (mToast == null) {
            mToast = Toast.makeText(context, hint, Toast.LENGTH_LONG);
        } else {
            mToast.setText(hint);
            mToast.setDuration(Toast.LENGTH_LONG);
        }


        mToast.show();
    }
}