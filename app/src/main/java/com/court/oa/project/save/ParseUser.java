package com.court.oa.project.save;

import android.content.Context;

import com.court.oa.project.okhttp.JSONCatch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ParseUser {
	//登陆保存
	public static void saveUser(JSONObject obj,Context context) {
		JSONObject jsonobject;
		try {
			if("null".equals(obj.getString("data"))){
				return;
			}
			jsonobject = obj.getJSONObject("data");
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", JSONCatch.parseString("userId", jsonobject));
			map.put("role", JSONCatch.parseString("role", jsonobject));
			map.put("appToken", JSONCatch.parseString("appToken", jsonobject));
			map.put("realName", JSONCatch.parseString("realName", jsonobject)+"");
			map.put("duty", JSONCatch.parseString("duty", jsonobject));
			map.put("mobile", JSONCatch.parseString("mobile", jsonobject));
			map.put("iconUrl", JSONCatch.parseString("iconUrl", jsonobject));
			map.put("userCode", JSONCatch.parseString("userCode", jsonobject));
			map.put("dept", JSONCatch.parseString("dept", jsonobject));
			SharePreferenceUtils.saveUser(map,context);
		} catch (JSONException e) {				
			e.printStackTrace();
		}
	}

	
}
