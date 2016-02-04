package com.xc.lib.view;

import com.xc.lib.utils.IAdapterMeasure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView implements IAdapterMeasure {
	private GestureDetector mGestureDetector;

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetecotr());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
	}

	class YScrollDetecotr extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			double angle = Math.atan2(Math.abs(distanceY), Math.abs(distanceX));
			if ((180 * angle) / Math.PI > 60) {
				return true;
			}
			return false;
		}
	}

	private boolean isMeasure = false;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		isMeasure = true;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		isMeasure = false;
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	public boolean isMeasure() {
		return isMeasure;
	}

}