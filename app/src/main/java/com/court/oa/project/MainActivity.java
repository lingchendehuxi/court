package com.court.oa.project;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.court.oa.project.activity.Login_My_activity;
import com.court.oa.project.activity.StartActivity;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.contants.Contants;
import com.court.oa.project.fragment.TCardFragment;
import com.court.oa.project.fragment.THallFragment;
import com.court.oa.project.fragment.TMeetFragment;
import com.court.oa.project.fragment.TNotifyFragment;
import com.court.oa.project.fragment.TMineFragment;
import com.court.oa.project.fragment.THomeFragment;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentManager manager = getSupportFragmentManager();
    private RadioGroup radio;
    private long firstClick;
    private Fragment fragment;
    private THomeFragment tHomeFragment;
    private TMeetFragment tMeetFragment;
    private THallFragment tHallFragment;
    private TNotifyFragment tNotifyFragment;
    private TMineFragment tMineFragment;
    private TCardFragment tCardFragment;
    private LinearLayout ll_bg;
    private int themeColor;
    public IWXAPI wxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_main);
        wxApi = WXAPIFactory.createWXAPI(this, Contants.APPID);
        wxApi.registerApp(Contants.APPID);
        initView();
        RadioButton rb_home = findViewById(R.id.rb_home);
        rb_home.setChecked(true);
        //设置别名
        Log.d("liuhong","000000 ==== "+SharePreferenceUtils.readUser("userId",this));
        if("yes".equals(SharePreferenceUtils.readUser("login",this)) && !"true".equals(SharePreferenceUtils.readUser("user_device_token",this))){
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
                    SharePreferenceUtils.readUser("userId",this)));
            Log.d("liuhong","11111 ==== "+SharePreferenceUtils.readUser("userId",this));
        }else{
            Log.d("liuhong","333333 ==== "+SharePreferenceUtils.readUser("userId",this));
        }

    }

    private void initView() {
        radio = findViewById(R.id.radio);
        radio.setOnCheckedChangeListener(this);
        ll_bg = findViewById(R.id.ll_bg);
        themeColor = getResources().getColor(R.color.theme_color);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //状态栏颜色字体(白底黑字)修改 MIUI6+
            FitStateUI.setMiuiStatusBarDarkMode(this, true);
        }*/
    }
    public void otherSkip(int position){
        switch (position){
            case 0:
                radio.check(R.id.rb_hall);
                break;
            case 1:
                radio.check(R.id.rb_notify);
                break;
            case 2:
                radio.check(R.id.rb_meet);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction;
        transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (checkedId) {
            case R.id.rb_home://首页
                if (tHomeFragment == null) {
                    tHomeFragment = new THomeFragment();
                    transaction.add(R.id.framelayout_main, tHomeFragment);
                } else {
                    transaction.show(tHomeFragment);
                }
                break;
            case R.id.rb_meet://会务
                if (tMeetFragment == null) {
                    tMeetFragment = new TMeetFragment();
                    transaction.add(R.id.framelayout_main, tMeetFragment);
                } else {
                    transaction.show(tMeetFragment);
                }
                break;
            case R.id.rb_hall://食堂
                if (tHallFragment == null) {
                    tHallFragment = new THallFragment();
                    transaction.add(R.id.framelayout_main, tHallFragment);
                } else {
                    transaction.show(tHallFragment);
                }
                break;
            case R.id.rb_notify://通知
                if (tNotifyFragment == null) {
                    tNotifyFragment = new TNotifyFragment();
                    transaction.add(R.id.framelayout_main, tNotifyFragment);
                } else {
                    transaction.show(tNotifyFragment);
                }
                break;
            case R.id.rb_my://我的
                if (tMineFragment == null) {
                    tMineFragment = new TMineFragment();
                    transaction.add(R.id.framelayout_main, tMineFragment);
                } else {
                    transaction.show(tMineFragment);
                }
                break;
            case R.id.rb_card://考勤
                if (tCardFragment == null) {
                    tCardFragment = new TCardFragment();
                    transaction.add(R.id.framelayout_main, tCardFragment);
                } else {
                    transaction.show(tCardFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {

        if (tHomeFragment != null) {
            transaction.hide(tHomeFragment);
        }
        if (tMeetFragment != null) {
            transaction.hide(tMeetFragment);
        }
        if (tHallFragment != null) {
            transaction.hide(tHallFragment);
        }
        if (tNotifyFragment != null) {
            transaction.hide(tNotifyFragment);
        }
        if (tMineFragment != null) {
            transaction.hide(tMineFragment);
        }
        if (tCardFragment != null) {
            transaction.hide(tCardFragment);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 1000) {
                Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
                firstClick = secondClick;
                return true;
            } else {
                MyApplication.getInstance().exitApp();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 在Fragment申请权限无法回调onRequestPermissionsResult方法
     * 进行处理让它把改事件传递到我们的fragment中
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 获取到Activity下的Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null)
        {
            return;
        }
        // 查找在Fragment中onRequestPermissionsResult方法并调用
        for (Fragment fragment : fragments)
        {
            if (fragment != null)
            {
                // 这里就会调用我们Fragment中的onRequestPermissionsResult方法
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if("no".equals(SharePreferenceUtils.readUser("login",this))){
            SharePreferenceUtils.cleanUser(this);
            SharePreferenceUtils.saveUserString("Skip","true",this);
            Intent intent2 = new Intent(this,Login_My_activity.class);
            startActivity(intent2);
            this.finish();
        }
    }


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    Log.i(TAG, "Set tag and alias success");
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    SharePreferenceUtils.saveUserString("user_device_token","true",MainActivity.this);
                    break;
                case 6002:
                    Log.i(TAG, "Failed to set alias and tags due to timeout. Try again after 60s.");
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    Log.e(TAG, "Failed with errorCode = " + code);
            }
        }
    };
    private String TAG = "Main_JPush";
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}

