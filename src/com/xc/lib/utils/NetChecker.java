package com.xc.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetChecker {
	
	private NetChecker() {
	}

	private static class SingletonHolder {
		/**
		 * 静态初始化器，由JVM来保证线程安全
		 */
		private static NetChecker instance = new NetChecker();
	}
	
	public static NetChecker getInstance() {
		return SingletonHolder.instance;
	}
	
	


	/**
	 * 检测是否有网络连接
	 * 
	 * @return
	 * 
	 */
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null) {
			return false;
		} else {
			if (activeNetInfo.isConnected()) {
				if ((activeNetInfo.isAvailable())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
	
	
	/**
	 * 检测wifi是否可用
	 * 
	 * @return
	 * 
	 */
	public boolean isWifiAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (activeNetInfo == null) {
			return false;
		} else {
			if (activeNetInfo.isConnected()) {
				if (activeNetInfo.isAvailable()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
}
