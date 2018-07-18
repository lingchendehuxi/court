package com.court.oa.project.okhttp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.court.oa.project.contants.HttpNames;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 习惯 on 2016/5/5.
 * �?个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐�?
 */

public class OkHttpManager {
	public static Context context;
	
	public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * 静�?�实�?
     */
    private static OkHttpManager sOkHttpManager;

    /**
     * okhttpclient实例
     */
    private OkHttpClient mClient;

    /**
     * 因为我们请求数据�?般都是子线程中请求，在这里我们使用了handler
     */
    private Handler mHandler;

    /**
     * 构�?�方�?
     */
    private OkHttpManager() {

        mClient = new OkHttpClient();
        /**
         * 在这里直接设置连接超�?.读取超时，写入超�?
         */
        mClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);

        /**
         * 如果是用�?3.0之前的版�?  使用以下直接设置连接超时.读取超时，写入超�?
         */

        //client.setConnectTimeout(10, TimeUnit.SECONDS);
        //client.setWriteTimeout(10, TimeUnit.SECONDS);
        //client.setReadTimeout(30, TimeUnit.SECONDS);


        /**
         * 初始化handler
         */
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 单例模式  获取OkHttpManager实例
     *
     * @return
     */
    public static OkHttpManager getInstance() {

        if (sOkHttpManager == null) {
            sOkHttpManager = new OkHttpManager();
        }
        return sOkHttpManager;
    }
    //------------------------get方式拼接url参数--------------------------

    /**
     * 用于GET方式拼接Url
     *
     * @param params
     * @param baseUrl
     * @return
     */
    private String getUrl(HashMap<String, String> params, String baseUrl) {
        StringBuilder builder = new StringBuilder(baseUrl);
        if (params.isEmpty()) {
            return baseUrl;
        } else {
            builder.append("?");
            Set<Map.Entry<String, String>> set = params.entrySet();
            for (Map.Entry<String, String> entry : set) {
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append("&");
            }
            builder = builder.replace(builder.lastIndexOf("&"), builder.length() + 1, "");
        }
        return builder.toString();
    }

    //-------------------------同步的方式请求数�?--------------------------

    /**
     * 对外提供的get方法,同步的方�?
     *
     * @param url 传入的地�?
     * @return
     */
    public static Response getSync(String url) {

        //通过获取到的实例来调用内部方�?
        return sOkHttpManager.inner_getSync(url);
    }

    /**
     * GET方式请求的内部�?�辑处理方式，同步的方式
     *
     * @param url
     * @return
     */
    private Response inner_getSync(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            //同步请求返回的是response对象
            response = mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 对外提供的同步获取String的方�?
     *
     * @param url
     * @return
     */
    public static String getSyncString(String url) {
        return sOkHttpManager.inner_getSyncString(url);
    }


    /**
     * 同步方法
     */
    private String inner_getSyncString(String url) {
        String result = null;
        try {
            /**
             * 把取得到的结果转为字符串，这里最好用string()
             */
            result = inner_getSync(url).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //-------------------------异步的方式请求数�?--------------------------

    /**
     * Get方式请求网络
     *
     * @param url
     * @param callBack
     */
    public static void getAsync(String url, DataCallBack callBack,Map<String, String> headersParams,String method) {
        getInstance().inner_getAsync(url, callBack,headersParams,method);
    }

    /**
     * 内部逻辑请求的方�?
     *
     * @param url
     * @param callBack
     * @return
     */
    private void inner_getAsync(String url, final DataCallBack callBack,Map<String, String> headersParams,final String method) {
    	Builder getbuilder = new Request.Builder().url(url).headers(SetHeaders(headersParams));
        final Request request = getbuilder.build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack,method);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack,method);
                }
                deliverDataSuccess(result, callBack,method);
            }
        });
    }


    /**
     * 分发失败的时候调�?
     *
     * @param request
     * @param e
     * @param callBack
     */
    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack,final String method) {
        /**
         * 在这里使用异步处�?
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    Log.e("-----------", "Error");
                    callBack.requestFailure(request, e,method);
                }
            }
        });
    }

    /**
     * 分发成功的时候调�?
     *登陆拦截
     * @param result
     * @param callBack
     */
    /*private void deliverDataSuccess(final String result, final DataCallBack callBack,final String method) {
        *//**
         * 在这里使用异步线程处�?
         *//*
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                    	JSONObject object = new JSONObject(result);
                    	if("3306".equals(JSONCatch.parseString("code", object))){
                    		if(callBack instanceof Activity){
                    			context = (Activity)callBack;
                    		}else{
                    			context = ((android.support.v4.app.Fragment) callBack).getActivity();
                    		}
                    		Intent intent = new Intent(context,Login_loginfastlyActivity.class);
                    		context.startActivity(intent);
                    		return;
                    	}
                        callBack.requestSuccess(result,method);
                        Log.e("-----------", "OK");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }*/
    private void deliverDataSuccess(final String result, final DataCallBack callBack,final String method) {
        /**
         * 在这里使用异步线程处�?
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if("3306".equals(JSONCatch.parseString("code", object))){
                            if(callBack instanceof Activity){
                                context = (Activity)callBack;
                            }else{
                                context = ((android.support.v4.app.Fragment) callBack).getActivity();
                            }
                            /*Intent intent = new Intent(context,Login_loginfastlyActivity.class);
                            context.startActivity(intent);*/
                            return;
                        }
                        callBack.requestSuccess(result,method);
                        Log.e("-----------", "OK");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 数据回调接口
     */
    public interface DataCallBack {
        void requestFailure(Request request, IOException e, String method);

        void requestSuccess(String result, String method) throws Exception;
    }

    //-------------------------提交表单--------------------------

    /**
     * post方式请求网络
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void postAsync(String url, Map<String, String> params, DataCallBack callBack,Map<String, String> headersParams,String method) {
    	if(callBack instanceof Activity){
    		if(HttpUtil.isCheckNet((Activity)callBack)){
    			DailogShow.showWaitDialog((Activity)callBack);
    			getInstance().inner_postAsync(HttpNames.SERVER_ADDRESS + url, params, callBack,headersParams,method);
    		}else{
    			HttpUtil.showToast((Activity)callBack, "请检查网络");
    		}
    	}else{
    		if(HttpUtil.isCheckNet(((android.support.v4.app.Fragment) callBack).getActivity())){
    			DailogShow.showWaitDialog(((android.support.v4.app.Fragment)callBack).getActivity());
    			getInstance().inner_postAsync(HttpNames.SERVER_ADDRESS + url, params, callBack,headersParams,method);
    		}else{
    			HttpUtil.showToast(((android.support.v4.app.Fragment) callBack).getActivity(), "请检查网络");
    		}
    	}
    }
    /** 
     * 设置请求�? 
     * @param headersParams 
     * @return 
     */  
    private Headers SetHeaders(Map<String, String> headersParams){  
        Headers headers=null;  
        okhttp3.Headers.Builder headersbuilder=new okhttp3.Headers.Builder();  
          
        if(headersParams != null)  
        {  
            Iterator<String> iterator = headersParams.keySet().iterator();  
            String key = "";  
            while (iterator.hasNext()) {  
                key = iterator.next().toString();  
                headersbuilder.add(key, headersParams.get(key));  
                Log.d("get http", "get_headers==="+key+"===="+headersParams.get(key));  
            }  
        }  
        headers=headersbuilder.build();  
          
        return headers;  
    }  
    private void inner_postAsync(String url, Map<String, String> params, final DataCallBack callBack,Map<String, String> headersParams,final String method) {

        RequestBody requestBody = null;
        if (params == null) {
            params = new HashMap<>();
        }
        
        /**
         * 如果�?3.0之前版本的，构建表单数据是下面的�?�?
         */
        //FormEncodingBuilder builder = new FormEncodingBuilder();

        /**
         * 3.0之后版本
         */
        FormBody.Builder builder = new FormBody.Builder();

        /**
         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查�?
         */
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formbody�?
             */
            builder.add(key, value);
        }
        requestBody = builder.build();
        //结果返回
        Builder okbuilder = new Request.Builder().url(url).headers(SetHeaders(headersParams));
        final Request request = okbuilder.post(requestBody).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            	DailogShow.dismissWaitDialog();
                deliverDataFailure(request, e, callBack,method);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            	DailogShow.dismissWaitDialog();
                String result = response.body().string();
                deliverDataSuccess(result, callBack,method);
            }


        });
    }


    //-------------------------文件下载--------------------------
    public static void downloadAsync(String url, String desDir, DataCallBack callBack,String method) {
        getInstance().inner_downloadAsync(url, desDir, callBack,method);
    }

    /**
     * 下载文件的内部�?�辑处理�?
     *
     * @param url      下载地址
     * @param desDir   目标地址
     * @param callBack
     */
    private void inner_downloadAsync(final String url, final String desDir, final DataCallBack callBack,final String method) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack,method);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                /**
                 * 在这里进行文件的下载处理
                 */
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    //文件名和目标地址
                    File file = new File(desDir, getFileName(url));
                    //把请求回来的response对象装换为字节流
                    inputStream = response.body().byteStream();
                    fileOutputStream = new FileOutputStream(file);
                    int len = 0;
                    byte[] bytes = new byte[2048];
                    //循环读取数据
                    while ((len = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }
                    //关闭文件输出�?
                    fileOutputStream.flush();
                    //调用分发数据成功的方�?
                    deliverDataSuccess(file.getAbsolutePath(), callBack,method);
                } catch (IOException e) {
                    //如果失败，调用此方法
                    deliverDataFailure(request, e, callBack,method);
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }

                }
            }

        });
    }

    /**
     * 根据文件url获取文件的路径名�?
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        String path = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
        return path;
    }

    /**
     * POST提交Json数据
     *
     * @param url
     */
    public static void postJson(String url, String json, DataCallBack callBack,String method) {
        getInstance().inner_postJson(url, json, callBack,method);
    }

    private void inner_postJson(String url, String json, final DataCallBack callBack,final String method) {
//        String json = "{\"desc\":\"OK\",\"status\":1000,\"data\":{\"wendu\":\"-6\",\"ganmao\":\"天冷空气湿度大，易发生感冒，请注意�?�当增加衣服，加强自我防护避免感冒�?�\",\"forecast\":[{\"fengxiang\":\"无持续风向\",\"fengli\":\"微风级\",\"high\":\"高温 -2℃\",\"type\":\"多云\",\"low\":\"低温 -7℃\",\"date\":\"20日星期三\"},{\"fengxiang\":\"无持续风向\",\"fengli\":\"微风级\",\"high\":\"高温 -2℃\",\"type\":\"阴\",\"low\":\"低温 -8℃\",\"date\":\"21日星期四\"},{\"fengxiang\":\"北风\",\"fengli\":\"4-5级\",\"high\":\"高温 -6℃\",\"type\":\"晴\",\"low\":\"低温 -16℃\",\"date\":\"22日星期五\"},{\"fengxiang\":\"北风\",\"fengli\":\"5-6级\",\"high\":\"高温 -9℃\",\"type\":\"晴\",\"low\":\"低温 -15℃\",\"date\":\"23日星期六\"},{\"fengxiang\":\"北风\",\"fengli\":\"4-5级\",\"high\":\"高温 -3℃\",\"type\":\"晴\",\"low\":\"低温 -12℃\",\"date\":\"24日星期天\"}],\"yesterday\":{\"fl\":\"微风\",\"fx\":\"无持续风向\",\"high\":\"高温 -2℃\",\"type\":\"晴\",\"low\":\"低温 -9℃\",\"date\":\"19日星期二\"},\"aqi\":\"127\",\"city\":\"北京\"}}";
        RequestBody body = RequestBody.create(JSON, json);
        final Request request = new Request.Builder().url(url).post(body).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack,method);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                deliverDataSuccess(result, callBack,method);
            }
        });
    }

    /**
     * Https的get请求
     * 访问带有自证书的Https
     * �?要加线程
     * InputStream certificates= getAssets().open("yyw_servlet.cer")
     */
    public static void httpGet3(String url, InputStream certificates, DataCallBack callBack,String method) {
        getInstance().inner_httpGet3(url, certificates, callBack,method);
    }

    private void inner_httpGet3(final String getUrl, final InputStream certificates, final DataCallBack callBack,final String method) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = SafeOkHttp.setCertificates(new URL(getUrl), certificates);
                    final Request request = getCacheRequest_NOT_STORE(getUrl);
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            deliverDataFailure(request, e, callBack,method);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            deliverDataSuccess(result, callBack,method);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * no-store用于防止重要的信息被无意的发布�?�在请求消息中发送将使得请求和响应消息都不使用缓存，完全不存下來�?
     *
     * @param getUrl
     * @return
     */
    public static Request getCacheRequest_NOT_STORE(String getUrl) {
        return new Request.Builder()
                .cacheControl(new CacheControl.Builder()
                        .noStore()
                        .build())
                .url(getUrl)
                .build();
    }

    /**
     * Https的get请求
     * 双方验证
     * 不需要加线程
     * 参数   getAssets().open("client.p12"),"123456",getAssets().open("server.cer")
     */
    public static void httpGet4(String url, InputStream keyClient, String password, InputStream certificates, DataCallBack callBack,String method) {
        getInstance().inner_httpGet4(url, keyClient, password, certificates, callBack,method);
    }

    private void inner_httpGet4(final String getUrl, InputStream keyClient, String password, InputStream certificates, final DataCallBack callBack,final String method) {
        try {
            OkHttpClient client = SafeOkHttp.setCertificatesTwo(new URL(getUrl), keyClient, password, certificates);
            final Request request = new Request.Builder().url(getUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    deliverDataFailure(request, e, callBack,method);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack,method);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     */
    public static void upFile(String url, File file, final DataCallBack callBack,String method) {
        getInstance().inner_upFile(url, file, callBack,method);
    }

    private void inner_upFile(String url, File file, final DataCallBack callBack,final String method) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename =\"" + file.getName() + "\""), RequestBody.create(null, file)).build();
//        body = OkHttpHelper.getRequestBody(new UIProgressRequestListener() {
//            @Override
//            public void onUIProgressRequest(long allBytes, long currentBytes, boolean done) {
//                float progress = currentBytes * 100f / allBytes;
//                Log.i(TAG, "onUIProgressRequest: 总长度：" + allBytes + " 当前下载的长度：" + currentBytes + "是否下载完成�?" + done + "下载进度�?" + progress);
//            }
//        }, body);
        final Request request = new Request.Builder().post(body).url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack,method);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                deliverDataSuccess(result, callBack,method);
            }
        });
    }


}
