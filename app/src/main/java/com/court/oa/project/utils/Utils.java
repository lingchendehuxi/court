package com.court.oa.project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.Xml;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuhong on 2018/7/17.
 */

public class Utils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    // 判断是否有网
    public static boolean isNetworkAvailable(Context ctx)
    {
        Context context = ctx.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
        {
            return false;
        } else
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否是wifi网络.
     *
     * @param context the context
     * @return boolean
     */
    public static boolean isWifiConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    // 是否是手机号
    public static boolean isMobileNO(String mobiles)
    {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 是否是email
    public static boolean isEmail(String email)
    {
        String str = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(str);
    }

    // 只能输入由数字、26个英文字母或者下划线组成的字符串(6-15位)
    public static boolean isRegisterPassword(String pass)
    {
        String str = "^\\w{6,15}$";
        return pass.matches(str);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context)
    {
        String versionName = "";
        try
        {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0)
            {
                return "";
            }
        } catch (Exception e)
        {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    // 防止webview内存泄露
    public static void setConfigCallback(WindowManager windowManager)
    {
        try
        {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field = field.getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);

            if (null == configCallback)
            {
                return;
            }

            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch (Exception e)
        {
        }
    }

    // 隐藏虚拟键盘
    public static void hiddenInput(Context context, EditText et_Content)
    {
        if (null != et_Content)
        {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_Content.getWindowToken(), 0);
        }
    }

    // 隐藏虚拟键盘
    public static void hiddenInputForce(Activity context)
    {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(context.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String value)
    {
        value = removeBOM(value);
        try
        {
            new JSONObject(value);

        } catch (JSONException e)
        {
            Log.i("RelayRecommendActivity",
                    "JSONException--------------->" + e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 移除json返回中的bom头，当服务器端字符编码和客户端不同时出现
    public static final String removeBOM(String data)
    {
        if (StringUtils.isEmpty(data))
        {
            return data;
        }
        if (data.startsWith("\ufeff"))
        {
            return data.substring(1);
        } else
        {
            return data;
        }
    }

    // 判断集合是否为空
    public static boolean isListNotEmpty(List<?> list)
    {
        if (null != list)
        {
            if ((list.size() > 0) && !list.isEmpty())
                return true;
        }
        return false;
    }

    /**
     * 对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String beanToJson(Object obj)
    {
        try
        {
            Gson gson = new Gson();
            return gson.toJson(obj);
        } catch (Exception e)
        {
            // TODO: handle exception
            return "";
        }
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T beanfromJson(String str, Class<T> type)
    {
        try
        {
            Gson gson = new Gson();
            return gson.fromJson(str, type);
        } catch (Exception e)
        {
            // TODO: handle exception
            Log.e("Utils","beanfromJson ====" + e.getMessage());
            return null;
        }
    }

    public static String getJsonData(String str)
    {
        String data = "";
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(str);
            if (jsonObject.has("data"))
            {
                data = jsonObject.getString("data");
            }
        } catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            data = "";
        }
        return data;
    }

    public static String getJsonById(String str, String key)
    {
        String data = "";
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(str);
            if (jsonObject.has(key))
            {
                data = jsonObject.getString(key);
            }
        } catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            data = "";
        }
        return data;
    }

    /**
     * @param array
     * @param cls
     * @return
     */
    public static <T> List<T> listfromJson(String array, Class<T> cls)
    {

        List<T> list = new ArrayList<T>();

        try
        {
            JSONArray jsonArray = new JSONArray(array);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                T t = beanfromJson(jsonArray.get(i).toString(), cls);
                list.add(t);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> test(int intervals ) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
//            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        String result = format.format(today);
        Log.e("text", result);
        return result;
    }

    public static String getLocalIpAddress() {
        StringBuilder IFCONFIG = new StringBuilder();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        IFCONFIG.append(inetAddress.getHostAddress().toString() + ",");
                        break;
                    }

                }
            }
            String[] split = IFCONFIG.toString().split(",");
            if (split != null && split.length > 0) {
                String ip = split[0];
                if (ip != null && ip.length() > 0) {
                    return ip;
                } else {
                    return "127.0.0.1";
                }
            } else {
                return "127.0.0.1";
            }
        } catch (SocketException ex) {
            return "127.0.0.1";

        }
    }

    public static String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (!"xml".equals(nodeName)) {
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
        }
        return null;
    }

    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureMonth(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM");
        String result = format.format(today);
        Log.e("text", result);
        return result;
    }
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
