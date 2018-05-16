package com.junmeng.shequ.view;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class MyViewPagerTransformerAnim extends ViewPager{
	private View mLeft;
	private View mRight;

	private float mTrans;
	private float mScale;

	private static final float MIN_SCALE = 0.6f;
	private Map<Integer, View> mChildren = new HashMap<Integer, View>();

	/*
	 * Ҫ���������췽��
	 */
	public MyViewPagerTransformerAnim(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewPagerTransformerAnim(Context context) {
		super(context);
	}

	/*
	 * ����put�ķ���
	 */
	public void setViewForPosition(View view, int position) {
		mChildren.put(position, view);
	}

	/*
	 * remove�ķ���
	 */
	public void removeViewFromPosition(Integer position) {
		mChildren.remove(position);
	}

	/**
	 * ��д�ķ���
	 */
	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixels) {

		// Log.e("TAG", "position =" + position + ",offset = " + offset);
		mLeft = mChildren.get(position);
		mRight = mChildren.get(position + 1);

		animStack(mLeft, mRight, offset, offsetPixels);// ��������Ч��

		super.onPageScrolled(position, offset, offsetPixels);
	}

	private void animStack(View left, View right, float offset, int offsetPixels) {
		if (right != null) {

			// ��0-1ҳ��offset:0`1
			mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;

			mTrans = -getWidth() - getPageMargin() + offsetPixels;

			ViewHelper.setScaleX(right, mScale);
			ViewHelper.setScaleY(right, mScale);

			ViewHelper.setTranslationX(right, mTrans);
		}
		if (left != null) {
			left.bringToFront();
		}
	}
}
