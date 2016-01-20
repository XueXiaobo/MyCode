package com.xc.lib.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class MyCustViewPager extends ViewPager {

	public MyCustViewPager(Context context) {
		super(context);
	}

	public MyCustViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		boolean b = false;
		try {
			b = super.onInterceptTouchEvent(arg0);
		} catch (Exception e) {
		}
		return b;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		boolean b = false;
		try {
			b = super.onTouchEvent(arg0);
		} catch (Exception e) {
		}
		return b;
	}
}
