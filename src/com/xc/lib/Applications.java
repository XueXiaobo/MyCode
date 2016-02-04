package com.xc.lib;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.xc.lib.layout.ScreenConfig;
import com.xc.lib.utils.AndroidUtil;
import com.xc.lib.utils.LocalPreference;
import com.xc.lib.utils.PhoneUtil;
import com.xc.lib.utils.TimeUtils;

public class Applications extends Application {
	private static Applications mApp;
	private static LocalPreference mPreference;
	private static List<Activity> mActivityList;

	// 内存中根目录
	private static String mMemoryDir;
	// sdcard上对应包名的根目录
	private static String mSdcardAppDir;
	// sdcard上对应包名下的下载根目录
	private static String mSdcardDownloadDir;
	// 程序版本
	private static int mVersionCode;
	private static String mVersionName;
	private static String imei; // 手机唯一标示
	private static String phone_type;// 手机型号
	private static String sdk_type;// 操作系统描述
	private static String net_model;// 手机制式
	private static String phone_number;// 手机号码
	private static String installTime;// 安装时间

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApp = this;
		init();

	}

	public void addActivity(Activity activity) {
		mActivityList.add(activity);
	}

	public void existApp() {
		for (Activity activity : mActivityList) {
			if (activity != null) {
				try {
					activity.finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void init() {
		ScreenConfig.init(this);
		mPreference = LocalPreference.getInstance(this);
		mActivityList = new ArrayList<Activity>();
		mMemoryDir = Environment.getDataDirectory() + "/" + this.getPackageName() + "/files/";
		mSdcardAppDir = Environment.getExternalStorageDirectory() + "/" + this.getPackageName() + "/";
		mSdcardDownloadDir = mSdcardAppDir + "download/";

		mVersionCode = AndroidUtil.getVersionCode(mApp);
		mVersionName = AndroidUtil.getVersionName(mApp);
		imei = PhoneUtil.getImei(this);
		phone_type = Build.MODEL;
		sdk_type = Build.VERSION.RELEASE + "," + Build.VERSION.SDK_INT;
		net_model = PhoneUtil.getPhoneModel(this);
		phone_number = PhoneUtil.getPhoneNumber(this);
		installTime = LocalPreference.getInstance(mApp).getString("installtime", "");
		if (TextUtils.isEmpty(installTime)) {
			installTime = TimeUtils.getCurrentTimeInString();
			LocalPreference.getInstance(mApp).setString("installtime", installTime);
		}
	}
	
	public static String getImei() {
		return imei;
	}

	public static void setImei(String imei) {
		Applications.imei = imei;
	}

	public static String getPhone_type() {
		return phone_type;
	}

	public static void setPhone_type(String phone_type) {
		Applications.phone_type = phone_type;
	}

	public static String getSdk_type() {
		return sdk_type;
	}

	public static void setSdk_type(String sdk_type) {
		Applications.sdk_type = sdk_type;
	}

	public static String getNet_model() {
		return net_model;
	}

	public static void setNet_model(String net_model) {
		Applications.net_model = net_model;
	}

	public static String getPhone_number() {
		return phone_number;
	}

	public static void setPhone_number(String phone_number) {
		Applications.phone_number = phone_number;
	}

	public static String getInstallTime() {
		return installTime;
	}

	public static void setInstallTime(String installTime) {
		Applications.installTime = installTime;
	}

	public static int getmVersionCode() {
		return mVersionCode;
	}

	public static Applications getmApp() {
		return mApp;
	}

	public static String getmMemoryDir() {
		return mMemoryDir;
	}

	public static String getmSdcardAppDir() {
		return mSdcardAppDir;
	}

	public static String getmSdcardDownloadDir() {
		return mSdcardDownloadDir;
	}

	public static String getmVersionName() {
		return mVersionName;
	}

	public static LocalPreference getmPreference() {
		return mPreference;
	}
}
