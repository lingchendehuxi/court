package com.court.oa.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.fragment.GuideFragment1;
import com.court.oa.project.fragment.GuideFragment2;
import com.court.oa.project.save.SharePreferenceUtils;
import com.court.oa.project.tool.FitStateUI;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_guide);
        if("true".equals(SharePreferenceUtils.readUser("Skip",GuideActivity.this))){
            Intent intent  = new Intent(GuideActivity.this, StartActivity.class);
            startActivity(intent);
        }
        SharePreferenceUtils.saveUserString("Skip","true",GuideActivity.this);

        ViewPager viewPager = findViewById(R.id.id_viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(

                getSupportFragmentManager()) {

            //

            @Override

            public int getCount() {

                return 2;

            }

            //条目展示类型.

            @Override

            public Fragment getItem(int position) {

                Fragment fragment = null;

                switch (position) {
                    case 0:

                        fragment = new GuideFragment1();

                        break;

                    case 1:

                        fragment = new GuideFragment2();

                        break;

                }
                return fragment;

            }

        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override

            public void onPageSelected(int position) {


            }

            @Override

            public void onPageScrollStateChanged(int state) {


            }

        });

    }

}
