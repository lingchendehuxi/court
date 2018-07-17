package com.court.oa.project.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


/*
 * 全局toast，解决多个toast同时显示
 */
public class ToastUtil {
    private static Toast toast;
    private static View view;

    private ToastUtil() {
    }

    @SuppressLint("ShowToast")
    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
        }
        toast.setView(view);
    }

    public static void showShortToast(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
    }

    public static void showLongToast(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, CharSequence msg,
                                  int duration) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showToastGravity(Context context, int msg,
                                  int duration,int gravity) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.setGravity(gravity,0,0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showToast(Context context, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}