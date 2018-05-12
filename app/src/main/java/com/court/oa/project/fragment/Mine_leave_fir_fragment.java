package com.court.oa.project.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.court.oa.project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mine_leave_fir_fragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_mine_leave_fir_fragment, container, false);
        return view;
    }

}
