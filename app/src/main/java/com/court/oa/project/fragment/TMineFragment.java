package com.court.oa.project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;
import com.court.oa.project.activity.Mine_Menu_activity;
import com.court.oa.project.activity.Mine_leave_activity;
import com.court.oa.project.activity.Mine_question_activity;
import com.court.oa.project.activity.Mine_set_acitivity;

public class TMineFragment extends Fragment implements View.OnClickListener {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.tminefragment, null);
        initView();
        return view;
    }

    private void initView() {
        ImageView iv_back = view.findViewById(R.id.iv_back);
        iv_back.setVisibility(View.INVISIBLE);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("我的");
        TextView tv_sort = view.findViewById(R.id.tv_sort);
        tv_sort.setText("设置");
        tv_sort.setOnClickListener(this);
        ImageView iv_set = view.findViewById(R.id.iv_set);
        iv_set.setVisibility(View.GONE);

        //初始化菜单
        TextView mine_menu = view.findViewById(R.id.mine_menu);
        mine_menu.setOnClickListener(this);
        TextView mine_question = view.findViewById(R.id.mine_question);
        mine_question.setOnClickListener(this);
        TextView mine_leave = view.findViewById(R.id.mine_leave);
        mine_leave.setOnClickListener(this);
        TextView mine_tel = view.findViewById(R.id.mine_tel);
        mine_tel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sort:
                startActivity(new Intent(getActivity(), Mine_set_acitivity.class));
                break;
            case R.id.mine_menu:
                startActivity(new Intent(getActivity(), Mine_Menu_activity.class));
                break;
            case R.id.mine_question:
                startActivity(new Intent(getActivity(), Mine_question_activity.class));
                break;
            case R.id.mine_leave:
                startActivity(new Intent(getActivity(), Mine_leave_activity.class));
                break;
            case R.id.mine_tel:
                break;
        }
    }
}
