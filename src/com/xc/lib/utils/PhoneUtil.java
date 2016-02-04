package com.xc.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 手机相关信息获取
 * 
 * @author 62568_000
 * 
 */
public class PhoneUtil {
	/**
	 * 获取手机唯一标示
	 * 
	 * @param context
	 * @return
	 */
	public static String getImei(Context context) {
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephony.getDeviceId();
	}

	/**
	 * 获取操作系统信息
	 * 
	 * @return
	 */
	public static String getSysVer() {
		return Build.VERSION.RELEASE + "," + Build.VERSION.SDK_INT;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getPhoneMode() {
		return Build.MODEL;
	}

	/**
	 * 获取手机制式model
	 */
	public static String getPhoneModel(Context context) {
		String model = "";
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				model = "wifi";
			} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
				model = info.getSubtypeName();
			}
		}
		return model;
	}

	/**
	 * 获取手机号码
	 */
	public static String getPhoneNumber(Context context) {
		String number = "";
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			number = tm.getLine1Number();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}
}
