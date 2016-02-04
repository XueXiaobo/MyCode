package com.xc.lib.view;

import com.xc.lib.utils.IAdapterMeasure;

import android.widget.GridView;

public class XUGridView extends GridView implements IAdapterMeasure {
	public XUGridView(android.content.Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		isMeasure = true;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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