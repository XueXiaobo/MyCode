package com.xc.lib.back;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class SwipeBackActivity extends FragmentActivity implements
		SwipeBackActivityBase {
	private SwipeBackActivityHelper mHelper;
	protected String explain = "如果要使用，请在values下的styles.xml文件 加入 <item name=\"android:windowIsTranslucent\">true</item>";
	public static final int EDGE_LEFT = ViewDragHelper.EDGE_LEFT;
	public static final int EDGE_RIGHT = ViewDragHelper.EDGE_RIGHT;
	public static final int EDGE_BOTTOM = ViewDragHelper.EDGE_BOTTOM;
	public static final int EDGE_ALL = EDGE_LEFT | EDGE_RIGHT | EDGE_BOTTOM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new SwipeBackActivityHelper(this);
		mHelper.onActivityCreate();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	public void setEdgeTrackingEnabled(int edge) {
		getSwipeBackLayout().setEdgeTrackingEnabled(edge);
	}

	@Override
	public void scrollToFinishActivity() {
		getSwipeBackLayout().scrollToFinishActivity();
	}
}
