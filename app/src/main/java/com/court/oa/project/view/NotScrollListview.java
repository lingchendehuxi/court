package com.court.oa.project.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class NotScrollListview extends ListView {
	public boolean isOnMeasure;

	public NotScrollListview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NotScrollListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NotScrollListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		isOnMeasure = true;
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		isOnMeasure = false;
		super.onLayout(changed, l, t, r, b);
	}

}