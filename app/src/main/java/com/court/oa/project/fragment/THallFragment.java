package com.court.oa.project.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.court.oa.project.R;


public class THallFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout hall_chose;
    private LinearLayout hall_date;
    private String[] date = new String[]{
            "05.07","05.08","05.09","05.10","05.11","05.12","05.13","05.14"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.thallfragment, null);
        initView();
        return view;
    }

    private void initView() {
        hall_chose = view.findViewById(R.id.hall_chose);
        hall_chose.setOnClickListener(this);
        hall_date = view.findViewById(R.id.hall_date);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final TextView[] textViews = new TextView[date.length];
        for(int i =0 ;i<date.length;i++){
            final TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels/5, LinearLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.hall_title_chose);
            textView.setText(date[i]);
            textViews[i] = textView;
            final int j = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int t =0;t<date.length;t++){
                        textViews[t].setSelected(false);
                    }
                    textView.setSelected(true);
                }
            });
            hall_date.addView(textView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hall_chose:
                break;
        }
    }
}