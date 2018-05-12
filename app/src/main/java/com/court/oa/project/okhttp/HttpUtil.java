package com.court.oa.project.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpUtil {
	
	/**
	 * ִ��get����
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @return-get����
	 */
	public static String get(String url,Map<String, Object> parameters) throws ClientProtocolException, IOException{
		HttpResponse response = execute("GET", null, url, parameters);
				StatusLine line = response.getStatusLine();
		if(line.getStatusCode() == HttpStatus.SC_OK){
			return EntityUtils.toString(response.getEntity(),"UTF-8");
		}
		return null;
	}
	public static String post(String url,Header[] headers,Map<String,Object> parameters) throws ClientProtocolException, IOException{
		HttpResponse response = execute("POST", headers, url, parameters);
		StatusLine sl = response.getStatusLine();
		if(sl.getStatusCode() == HttpStatus.SC_OK){
			return EntityUtils.toString(response.getEntity(),"UTF-8");
		}
		return null;
	}
	/**
	 * ִ������ķ���
	 * @param method -����ķ�ʽ
	 * @param headers -�����ͷ��Ϣ
	 * @param url -�����url
	 * @param parameters -�������
	 * @return -������Ӧ����
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static HttpResponse execute(String method,Header[] headers,String url,Map<String,Object> parameters) throws ClientProtocolException, IOException{
		HttpUriRequest request = null;
		if("POST".equals(method)){
			request = new HttpPost(url);
			//Post
			if(parameters!=null && !parameters.isEmpty()){
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				
				Set<Map.Entry<String, Object>> entrys = parameters.entrySet();
				for(Map.Entry<String, Object> entry : entrys){
					list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
				
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
				((HttpPost)request).setEntity(entity);
			}
		}else{
			//����get����ʱ �ж�url���Ƿ���? ��?˵��url���Ѿ�ƴ���в����� ��ƴ�ӵĲ���ʹ��&
			if(parameters!=null && !parameters.isEmpty()){
				if(url.indexOf("?")!=-1){
					url = url + "&" + getQueryString(parameters);
				}else{
					url = url + "?" + getQueryString(parameters);
				}
			}
			request = new HttpGet(url);
		}
		//���������ͷ��Ϣ
		if(headers!=null && headers.length>0){
			for(Header h : headers){
				request.setHeader(h);
			}
		}
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(request);
		
		return response;
	}
	/**
	 * ƴ�Ӳ�ѯ�ַ���
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getQueryString(Map<String, Object> parameters) throws UnsupportedEncodingException{
		String queryString = null;
		if(parameters!=null && !parameters.isEmpty()){
			StringBuffer sb = new StringBuffer();
			Set<Map.Entry<String, Object>> entrys = parameters.entrySet();
			for(Map.Entry<String, Object> entry : entrys){
				sb.append("&");
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}
			queryString = sb.toString();
			queryString = queryString.substring(1);
		}
		return queryString;
	}

	/**获取请求头信息*//*
	public static Header[] getHeader(Context context){
		Header[] headers = {
				new BasicHeader("X-GL-Sign", Contants.SYSTEM_SIGN),
				new BasicHeader("X-GL-Token", SharePreferenceUtils.readUser("token",context)) };
		return headers;
	}*/
	/**
	 * Map获得请求头信息
	 */
	public static HashMap<String, String> headerMap(Context context){
		HashMap<String, String> map = new HashMap<String,String>();
		/*map.put("X-GL-Sign", Contants.SYSTEM_SIGN);
		map.put("X-GL-Token", SharePreferenceUtils.readUser("token", context));*/
		return map;
	}
	/**
	 * 弹出提示框
	 */
	public static void showToast(Context context,String string){
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
	public static boolean isCheckNet(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null){
			// 没有联网
			return false;
		} else {
			// 联网类型
			// String type=info.getTypeName();
			return true;
		}
	}
	
	public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
 
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
 
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }
 
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
 
            }
 
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                 
            }
 
        });
 
    }
	
}

