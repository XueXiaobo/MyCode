package com.xc.lib.utils;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

public class MyHandler extends Handler {
	private final WeakReference<HandleMessageListener> mTarget;

	public MyHandler(HandleMessageListener target) {
		mTarget = new WeakReference<HandleMessageListener>(target);
	}

	@Override
	public void handleMessage(Message msg) {
		HandleMessageListener listener = mTarget.get();
		if (listener != null)
			listener.handleMessage(msg);
	}

	public interface HandleMessageListener {
		void handleMessage(Message message);
	}

}
