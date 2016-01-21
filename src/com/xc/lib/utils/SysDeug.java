package com.xc.lib.utils;

import android.util.Log;

import com.xc.lib.layout.ScreenConfig;

public class SysDeug {
	public static final String TAG = "xxb";
	public static final boolean debug = ScreenConfig.isDebug;

	public static void logD(String tag, String msg) {
		if (debug)
			Log.d(tag, msg);
	}

	public static void logI(String tag, String msg) {
		if (debug)
			Log.i(tag, msg);
	}

	public static void logE(String tag, String msg) {
		if (debug)
			Log.e(tag, msg);
	}

	public static void logD(String msg) {
		logD(TAG, msg);
	}

	public static void logI(String msg) {
		logI(TAG, msg);
	}

	public static void logE(String msg) {
		logE(TAG, msg);
	}

}
