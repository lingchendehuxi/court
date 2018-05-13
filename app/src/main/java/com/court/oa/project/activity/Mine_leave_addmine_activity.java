package com.court.oa.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.court.oa.project.R;

public class Mine_leave_addmine_activity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private RadioGroup radio;
    private LinearLayout leave_approver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_leave_addmine_activity);
        initView();
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
        iv_set.setVisibility(View.INVISIBLE);
        leave_approver = findViewById(R.id.leave_approver);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.leave_approver:
                startActivity(new Intent(Mine_leave_addmine_activity.this,Mine_leave_chose_activity.class));
                break;
        }
    }
}
