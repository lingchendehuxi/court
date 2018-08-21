package com.court.oa.project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.activity.Login_My_activity;
import com.court.oa.project.activity.MY_Salary_activity;
import com.court.oa.project.activity.Mine_Menu_activity;
import com.court.oa.project.activity.Mine_leave_activity;
import com.court.oa.project.activity.Mine_meet_activity;
import com.court.oa.project.activity.Mine_set_acitivity;
import com.court.oa.project.activity.StartActivity;
import com.court.oa.project.save.SharePreferenceUtils;
import com.squareup.picasso.Picasso;

public class TMineFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView mine_pic,mine_info_go;
    private TextView mine_name,mine_job;

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
        ImageView mine_info_go = view.findViewById(R.id.mine_info_go);
        mine_info_go.setOnClickListener(this);
        ImageView iv_set = view.findViewById(R.id.iv_set);
        iv_set.setVisibility(View.GONE);

        //初始化菜单
        TextView mine_menu = view.findViewById(R.id.mine_menu);
        mine_menu.setOnClickListener(this);
        TextView mine_leave = view.findViewById(R.id.mine_leave);
        mine_leave.setOnClickListener(this);
        TextView mine_tel = view.findViewById(R.id.mine_tel);
        mine_tel.setOnClickListener(this);
        TextView mine_salary = view.findViewById(R.id.mine_salary);
        mine_salary.setOnClickListener(this);
        TextView mine_meet = view.findViewById(R.id.mine_meet);
        mine_meet.setOnClickListener(this);
        mine_name = view.findViewById(R.id.mine_name);
        mine_job = view.findViewById(R.id.mine_job);
        mine_pic = view.findViewById(R.id.mine_pic);
        mine_name.setText(SharePreferenceUtils.readUser("realName",getActivity()));
        mine_job.setText(SharePreferenceUtils.readUser("duty",getActivity()));
        if(SharePreferenceUtils.readUser("iconUrl",getActivity())!=null && !TextUtils.isEmpty(SharePreferenceUtils.readUser("iconUrl",getActivity()))){
            Picasso.with(getActivity()).load(SharePreferenceUtils.readUser("iconUrl",getActivity())).placeholder(R.mipmap.mine)
                    .error(R.mipmap.mine).into(mine_pic);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sort:
            case R.id.mine_info_go:
                startActivity(new Intent(getActivity(), Mine_set_acitivity.class));
                break;
            case R.id.mine_menu:
                startActivity(new Intent(getActivity(), Mine_Menu_activity.class));
                break;
            case R.id.mine_leave:
                startActivity(new Intent(getActivity(), Mine_leave_activity.class));
                break;
            case R.id.mine_tel:
                break;
            case R.id.mine_salary:
                startActivity(new Intent(getActivity(), MY_Salary_activity.class));
                break;
            case R.id.mine_meet:
                startActivity(new Intent(getActivity(), Mine_meet_activity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!"yes".equals(SharePreferenceUtils.readUser("login",getActivity()))){
            SharePreferenceUtils.cleanUser(getActivity());
            SharePreferenceUtils.saveUserString("Skip","true",getActivity());
            Intent intent2 = new Intent(getActivity(),Login_My_activity.class);
            startActivity(intent2);
            getActivity().finish();
        }
    }
}
