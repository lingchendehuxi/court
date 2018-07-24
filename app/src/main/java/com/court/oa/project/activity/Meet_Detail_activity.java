package com.court.oa.project.activity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.tool.FitStateUI;
import com.court.oa.project.utils.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Meet_Detail_activity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_open_text;
    private TextView tv_openfile;
    FileUtils fileUtils;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_meet_list_detail);
        initView();
    }
    private void initView(){
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("会议详情");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
        ll_open_text = findViewById(R.id.ll_open_text);
        ll_open_text.setOnClickListener(this);
        tv_openfile = findViewById(R.id.tv_openfile);
        fileUtils=new FileUtils();
        /*if(fileUtils.isFileExist("足音.mp3","BoBoMusic")){
            tv_openfile.setText("打开附件");
        }*/

    }
    //设备API大于6.0时，主动申请权限
    private void requestPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
            break;
            case R.id.ll_open_text:
                if ("打开附件".equals(tv_openfile.getText().toString().trim())){
                    FileUtils.getTextFileIntent("/sdcard/zer.txt",false);
                    return;
                }
                verifyStoragePermissions(this);
                new MyAsyncTask(Meet_Detail_activity.this).execute("http://fengkui.bj.bcebos.com/%E8%B6%B3%E9%9F%B3.mp3");
                break;
        }
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class MyAsyncTask extends AsyncTask<String,Integer,Integer> {

        private Context context;
        private NotificationManager notificationManager;
        private NotificationCompat.Builder builder;

        public MyAsyncTask(Context context){
            this.context = context;
            notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentInfo("下载中...")
                    .setContentTitle("正在下载");

        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream is = null;
            OutputStream os = null;
            HttpURLConnection connection = null;
            int total_length = 0;
            try {
                URL url1 = new URL(params[0]);
                connection = (HttpURLConnection) url1.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(50000);
                connection.connect();

                if(connection.getResponseCode() == 200){
                    is = connection.getInputStream();
                    os = new FileOutputStream("/sdcard/zer.txt");
                    byte [] buf = new byte[1024];
                    int len;
                    int pro1=0;
                    int pro2=0;
                    // 获取文件流大小，用于更新进度
                    long file_length = connection.getContentLength();
                    while((len = is.read(buf))!=-1){
                        total_length += len;
                        if(file_length>0) {
                            pro1 = (int) ((total_length / (float) file_length) * 100);//传递进度（注意顺序）
                        }
                        if(pro1!=pro2) {
                            // 调用update函数，更新进度
                            publishProgress(pro2=pro1);
                        }
                        os.write(buf, 0, len);
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }


            return total_length;
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            builder.setProgress(100,values[0],false);
            notificationManager.notify(0x3,builder.build());
            //下载进度提示
            builder.setContentText("下载"+values[0]+"%");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 100) {
                tv_openfile.setText("打开附件");
            }
        }
    }
}