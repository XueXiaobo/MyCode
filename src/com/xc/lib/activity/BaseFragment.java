package com.xc.lib.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.utils.MyHandler;
import com.xc.lib.utils.MyHandler.HandleMessageListener;

public class BaseFragment extends Fragment implements HandleMessageListener {
	private MyHandler handler = null;
	protected Activity mContext;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	/**
	 * 比例化所有控件
	 */
	protected void rateAll(View root) {
		LayoutUtils.rateLayout(mContext, root, true);
	}

	protected void showSoftInput(EditText view) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	protected void cancelSoftInput(EditText view) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
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