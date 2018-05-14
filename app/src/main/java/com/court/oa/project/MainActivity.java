package com.court.oa.project;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.court.oa.project.application.MyApplication;
import com.court.oa.project.fragment.THallFragment;
import com.court.oa.project.fragment.TMeetFragment;
import com.court.oa.project.fragment.TNotifyFragment;
import com.court.oa.project.fragment.TMineFragment;
import com.court.oa.project.fragment.THomeFragment;
import com.court.oa.project.tool.FitStateUI;

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
    private LinearLayout ll_bg;
    private int themeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_main);
        initView();
        RadioButton rb_home = findViewById(R.id.rb_home);
        rb_home.setChecked(true);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction;
        transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (checkedId) {
            case R.id.rb_home:
                if (tHomeFragment == null) {
                    tHomeFragment = new THomeFragment();
                    transaction.add(R.id.framelayout_main, tHomeFragment);
                } else {
                    transaction.show(tHomeFragment);
                }
                break;
            case R.id.rb_meet:
                if (tMeetFragment == null) {
                    tMeetFragment = new TMeetFragment();
                    transaction.add(R.id.framelayout_main, tMeetFragment);
                } else {
                    transaction.show(tMeetFragment);
                }
                break;
            case R.id.rb_hall:
                if (tHallFragment == null) {
                    tHallFragment = new THallFragment();
                    transaction.add(R.id.framelayout_main, tHallFragment);
                } else {
                    transaction.show(tHallFragment);
                }
                break;
            case R.id.rb_notify:
                if (tNotifyFragment == null) {
                    tNotifyFragment = new TNotifyFragment();
                    transaction.add(R.id.framelayout_main, tNotifyFragment);
                } else {
                    transaction.show(tNotifyFragment);
                }
                break;
            case R.id.rb_my:
                if (tMineFragment == null) {
                    tMineFragment = new TMineFragment();
                    transaction.add(R.id.framelayout_main, tMineFragment);
                } else {
                    transaction.show(tMineFragment);
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

}

