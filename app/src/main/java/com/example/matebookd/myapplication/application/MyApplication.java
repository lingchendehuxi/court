package com.example.matebookd.myapplication.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

	private List<Activity> list = new ArrayList<Activity>();
	private static MyApplication myApp;

	// 为了保证我们每次添加的activity都是添加在同一个list中,我们需要对MyApplication进行单利实现
	public static MyApplication getInstance() {
		if (myApp == null) {
			myApp = new MyApplication();
		}
		return myApp;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}


	// 添加每一个Activity
	public void addActivity(Activity activity) {
		list.add(activity);
	}

	// 退出App
	public void exitApp() {
		for (Activity ac : list) {
			ac.finish();
		}
	}

	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				Log.i(context.getPackageName(), "此appimportace =" + appProcess.importance
						+ ",context.getClass().getName()=" + context.getClass().getName());
				if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Log.i(context.getPackageName(), "处于后台" + appProcess.processName);
					return true;
				} else {
					Log.i(context.getPackageName(), "处于前台" + appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}
}
