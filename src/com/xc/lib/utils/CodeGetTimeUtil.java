package com.xc.lib.utils;

import android.os.Handler;
import android.os.SystemClock;

import com.xc.lib.iface.Callback;

/**
 * 倒计时
 * 
 * @author xxb
 * 
 */
public class CodeGetTimeUtil {
	private Callback<Integer> callback;
	private boolean isRun = true;
	private MyThread timeThread;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			callback.onSuccess(msg.what);
		};
	};

	// 开始一个倒计时 
	public void start(int max, Callback<Integer> callback) {
		this.callback = callback;
		if (timeThread == null || !timeThread.isAlive()) {
			isRun = true;
			timeThread = new MyThread(max);
			timeThread.start();
		}
	}

	public void stop() {
		isRun = false;
	}

	class MyThread extends Thread {
		private int current;

		public MyThread(int max) {
			this.current = max;
		}

		@Override
		public void run() {
			while (isRun && current >= 0) {
				handler.sendEmptyMessage(current);
				current--;
				SystemClock.sleep(1000);
			}
		}
	}
}
