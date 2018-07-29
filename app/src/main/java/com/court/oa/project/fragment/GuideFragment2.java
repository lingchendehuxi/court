package com.court.oa.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.court.oa.project.MainActivity;
import com.court.oa.project.R;
import com.court.oa.project.activity.StartActivity;

/**
 * Created by MateBook D on 2018/7/29.
 */

public class GuideFragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_guide2, container, false);
        TextView go_go_go = view.findViewById(R.id.go_go_go);
        go_go_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
