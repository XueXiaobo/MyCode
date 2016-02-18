package com.xc.lib.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xc.lib.Applications;
import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.utils.MyHandler;
import com.xc.lib.utils.MyHandler.HandleMessageListener;
/**
 * 基础的activity
 * @author 62568_000
 *
 */
public class XBaseFragmentActivity extends FragmentActivity implements HandleMessageListener {
	private MyHandler handler = null;
	protected XBaseFragmentActivity mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.mContext = this;
		Applications.getmApp().addActivity(mContext);
	}

	public void closeApp() {
		Applications.getmApp().existApp();
	}

	@Override
	public void finish() {
		Applications.getmApp().removeActivity(mContext);
		super.finish();
	}

	/**
	 * 比例化所有控件
	 */
	protected void rateAll() {
		LayoutUtils.rateLayout(mContext, getRootView(), true);
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
}