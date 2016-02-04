package com.xc.lib.view;

import com.xc.lib.utils.IAdapterMeasure;

import android.widget.ListView;

public class NoScrollListView extends ListView implements IAdapterMeasure{
	public NoScrollListView(android.content.Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		isMeasure = true;
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	private boolean isMeasure = false;

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