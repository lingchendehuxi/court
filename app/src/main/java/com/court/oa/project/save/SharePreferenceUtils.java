package com.court.oa.project.save;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;
import java.util.Set;

public class SharePreferenceUtils {
	public static String user_msg = "user_msg";
	public static String base_url = "base_url";

	/** 保存appConfig配置 */
	public static void saveApp(Map<String, String> map, Context context) {
		SharedPreferences share = context.getSharedPreferences(base_url, Context.MODE_PRIVATE);
		Editor editor = share.edit();
		Set<String> set = map.keySet();
		for (String string : set) {
			editor.putString(string, map.get(string));
		}
		editor.commit();
	}

	/** 读取appConfig配置 */
	public static String readApp(String key, Context context) {
		SharedPreferences share = context.getSharedPreferences(base_url, Context.MODE_PRIVATE);
		return share.getString(key, "");
	}

	public static void saveAppString(String key, String value, Context context) {
		SharedPreferences share = context.getSharedPreferences(base_url, Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void cleanApp(Context context) {
		SharedPreferences share = context.getSharedPreferences(base_url, Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.clear().commit();
	}

	/** 保存用户信息 */
	public static void saveUser(Map<String, String> map, Context context) {
		SharedPreferences share = context.getSharedPreferences(user_msg, Context.MODE_PRIVATE);
		Editor editor = share.edit();
		Set<String> set = map.keySet();
		for (String key : set) {
			editor.putString(key, map.get(key));
		}
		editor.commit();
	}

	/** 开发测试用 */
	public static void saveUserString(String key, String value, Context context) {
		SharedPreferences share = context.getSharedPreferences(user_msg, Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/***/
	public static String readUser(String key, Context context) {
		SharedPreferences share = context.getSharedPreferences(user_msg, Context.MODE_PRIVATE);
		return share.getString(key, "");

	}

	public static void cleanUser(Context context) {
		SharedPreferences share = context.getSharedPreferences(user_msg, Context.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.clear().commit();
	}
}
