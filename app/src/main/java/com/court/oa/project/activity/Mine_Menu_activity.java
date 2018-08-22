package com.court.oa.project.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.application.MyApplication;
import com.court.oa.project.fragment.Mine_menu_fir_fragment;
import com.court.oa.project.fragment.Mine_menu_sec_fragment;
import com.court.oa.project.tool.FitStateUI;

public class Mine_Menu_activity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Mine_menu_fir_fragment mine_question_fir_fragment;
    private Mine_menu_sec_fragment mine_question_sec_fragment;
    private FragmentManager manager = getSupportFragmentManager();
    private RadioGroup radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        MyApplication.getInstance().addActivity(this);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_mine_question_activity);
        initView();
        RadioButton rb_question = findViewById(R.id.rb_get);
        rb_question.setChecked(true);
    }

    private void initView() {
        radio = findViewById(R.id.radio);
        radio.setOnCheckedChangeListener(this);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的食堂");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
        iv_set.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {

        if (mine_question_fir_fragment != null) {
            transaction.hide(mine_question_fir_fragment);
        }
        if (mine_question_sec_fragment != null) {
            transaction.hide(mine_question_sec_fragment);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction transaction;
        transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (checkedId) {
            case R.id.rb_get:
                if (mine_question_fir_fragment == null) {
                    mine_question_fir_fragment = new Mine_menu_fir_fragment();
                    transaction.add(R.id.framelayout_question, mine_question_fir_fragment);
                } else {
                    transaction.show(mine_question_fir_fragment);
                }
                break;
            case R.id.rb_package:
                if (mine_question_sec_fragment == null) {
                    mine_question_sec_fragment = new Mine_menu_sec_fragment();
                    transaction.add(R.id.framelayout_question, mine_question_sec_fragment);
                } else {
                    transaction.show(mine_question_sec_fragment);
                }
                break;
        }
        transaction.commit();
    }
}
