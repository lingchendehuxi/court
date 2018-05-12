package com.court.oa.project.okhttp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.court.oa.project.R;


public class DailogShow {
	public static Dialog dialog;
	/**
	 * 显示一个等待对话框
	 * 
	 * @param title
	 * @param message
	 */
	public static void showWaitDialog(Context context) {
		if (dialog == null) {
			dialog = new AlertDialog.Builder(context).create();
			dialog.show();
			Window window = dialog.getWindow();
			window.setContentView(R.layout.layout_wait_dialog);
		}
	}

	/**
	 * 关闭显示中的等待对话框
	 */
	public static void dismissWaitDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}
	
	
}
