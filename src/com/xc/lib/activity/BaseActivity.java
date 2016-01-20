package com.xc.lib.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.TextView;

import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.layout.ScreenConfig;
import com.xc.lib.utils.MyHandler;
import com.xc.lib.utils.MyHandler.HandleMessageListener;

@SuppressWarnings("unchecked")
public class BaseActivity extends Activity implements HandleMessageListener, OnScrollListener {
	private MyHandler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		// setEdgeTrackingEnabled(EDGE_LEFT);
	}

	/**
	 * 获取xml 的最外层view
	 */
	protected View getRootView() {
		View rootView = null;
		try {
			rootView = ((ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootView;
	}

	protected void reFreshPage() {
	}

	protected <T extends View> T getView(int id) {
		View childView = findViewById(id);
		return (T) childView;
	}

	protected <T extends View> T getRateView(int id, boolean isMin) {
		View childView = findViewById(id);
		if (!ScreenConfig.INITBAR) {
			if (!isMin) {
				ScreenConfig.addView(childView);
			} else {
				rateView(childView, isMin);
			}
			ScreenConfig.initBar(this, childView);
		} else {
			rateView(childView, isMin);
		}
		return (T) childView;
	}

	protected <T extends View> T getTextView(int id, boolean isMin, float textSize) {
		View childView = getRateView(id, isMin);
		if (childView instanceof TextView) {
			TextView tv = (TextView) childView;
			LayoutUtils.setTextSize(tv, textSize);
		}
		return (T) childView;
	}

	/**
	 * 控件比例化
	 */
	protected void rateView(View v, boolean isMin) {
		LayoutUtils.rateScale(this, v, isMin);
	}

	protected void showSoftInput(EditText view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	protected void cancelSoftInput(EditText view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public Handler getHandler() {
		if (handler == null)
			handler = new MyHandler(this);
		return handler;
	}

	@Override
	public void handleMessage(Message arg0) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (!isonBottom)
			return;
		if (firstVisibleItem + visibleItemCount >= totalItemCount) {
			onBottom();
		}
	}

	private boolean isonBottom = false;

	protected void onBottomStop() {
		isonBottom = false;
	}

	protected void onBottomStart() {
		isonBottom = true;
	}

	public void onBottom() {
	}
}