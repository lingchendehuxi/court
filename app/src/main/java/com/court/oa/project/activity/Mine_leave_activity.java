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
import com.court.oa.project.fragment.Mine_leave_fir_fragment;
import com.court.oa.project.fragment.Mine_leave_sec_fragment;
import com.court.oa.project.fragment.Mine_leave_thr_fragment;
import com.court.oa.project.fragment.Mine_question_fir_fragment;
import com.court.oa.project.fragment.Mine_question_sec_fragment;

public class Mine_leave_activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    private Mine_leave_fir_fragment mine_leave_fir_fragment;
    private Mine_leave_sec_fragment mine_leave_sec_fragment;
    private Mine_leave_thr_fragment mine_leave_thr_fragment;
    private FragmentManager manager = getSupportFragmentManager();
    private RadioGroup radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题栏
        setContentView(R.layout.activity_mine_leave_activity);
        initView();
        RadioButton rb_unshow = findViewById(R.id.rb_unshow);
        rb_unshow.setChecked(true);
    }

    private void initView() {
        radio = findViewById(R.id.radio);
        radio.setOnCheckedChangeListener(this);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("请假");
        TextView tv_sort = findViewById(R.id.tv_sort);
        tv_sort.setVisibility(View.INVISIBLE);
        ImageView iv_set = findViewById(R.id.iv_set);
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

        if (mine_leave_fir_fragment != null) {
            transaction.hide(mine_leave_fir_fragment);
        }
        if (mine_leave_sec_fragment != null) {
            transaction.hide(mine_leave_sec_fragment);
        }
        if (mine_leave_thr_fragment != null) {
            transaction.hide(mine_leave_thr_fragment);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction transaction;
        transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (checkedId) {
            case R.id.rb_unshow:
                if (mine_leave_fir_fragment == null) {
                    mine_leave_fir_fragment = new Mine_leave_fir_fragment();
                    transaction.add(R.id.framelayout_leave, mine_leave_fir_fragment);
                } else {
                    transaction.show(mine_leave_fir_fragment);
                }
                break;
            case R.id.rb_showed:
                if (mine_leave_sec_fragment == null) {
                    mine_leave_sec_fragment = new Mine_leave_sec_fragment();
                    transaction.add(R.id.framelayout_leave, mine_leave_sec_fragment);
                } else {
                    transaction.show(mine_leave_sec_fragment);
                }
                break;
            case R.id.rb_show:
                if (mine_leave_thr_fragment == null) {
                    mine_leave_thr_fragment = new Mine_leave_thr_fragment();
                    transaction.add(R.id.framelayout_leave, mine_leave_thr_fragment);
                } else {
                    transaction.show(mine_leave_thr_fragment);
                }
                break;
        }
        transaction.commit();
    }

}
