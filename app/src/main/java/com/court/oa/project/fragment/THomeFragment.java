package com.court.oa.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.court.oa.project.R;

public class THomeFragment extends Fragment implements View.OnClickListener{

private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (View) inflater.inflate(R.layout.thomefragment, null);
		initView();
		return view;
	}
	private void initView() {
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
		}
	}
}
