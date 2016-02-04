package com.xc.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigInteger;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * adnroid实用工具类
 * 
 * @ClassName: AndroidUtil
 * @Description: TODO
 * @author Brook Xu
 * @date 2014年4月8日 下午1:54:46
 * @version 1.0
 */
public class AndroidUtil {

	public static final int PROGRESS_MAX_VALUE = 100;

	/**
	 * 获取签名
	 * 
	 * @param applicationContext
	 * @param pkgName
	 * @return
	 * @return String 获取签名失败，会返回null
	 */
	private static String getPublicKey(Context applicationContext, String pkgName) {
		PackageInfo packageInfo;
		try {

			if (TextUtils.isEmpty(pkgName)) {
				pkgName = applicationContext.getPackageName();
			}

			packageInfo = applicationContext.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
			Signature sign = packageInfo.signatures[0];

			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(sign.toByteArray()));

			RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();
			BigInteger big = publicKey.getModulus();

			return big.toString(16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static String getPublicKey(Context applicationContext) {
		return getPublicKey(applicationContext, null);
	}

	
	

	public static int getVersionCode(Context applicationContext) {
		try {
			PackageManager pm = applicationContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(applicationContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static String getVersionName(Context applicationContext) {
		try {
			PackageManager pm = applicationContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(applicationContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取屏幕宽度与高度
	 * 
	 * @return result[0]为宽度，result[1]为高度
	 */
	public static int[] getScreenParams(Context applicationContext) {
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

		// density比例，160dpi的为1，240dpi的为1.5（以160dpi为标准，240/160dpi）
		// float density = dm.density;

		// 屏幕密度。160dpi，240dpi等
		// int densityDpi = dm.densityDpi;

		// 字体缩放比例
		// float textScale = dm.scaledDensity;

		// 以dp值来表示的宽
		// float widthDip = dm.xdpi;

		// 以dp值来表示的高
		// float heightDip = dm.ydpi;

		// 宽上有多少个像素
		int widthPixels = dm.widthPixels;

		// 高上有多少个像素
		int heightPixels = dm.heightPixels;

		// 只返回了屏幕宽度与高度，其他参数可自行设置
		return new int[] { widthPixels, heightPixels };
	}

	/**
	 * dip转px
	 * 
	 * @param dip
	 * @return
	 */
	public static int dipToPixels(Context applicationContext, int dip) {
		Resources r = applicationContext.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
		return (int) px;
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context applicationContext, float pxValue) {
		final float scale = applicationContext.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据应用包名判断该应用是否在最前端
	 * @param context
	 * @param packageName 需要检测的应用包名
	 * @return
	 */
	public static boolean isTopActivity(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断该应用是否在最前端
	 * @param context
	 * @return
	 */
	public static boolean isTopActivitySelf(Context context) {
		String packageName = context.getApplicationContext().getPackageName();
		ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 图片uri转路径
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String uriToFilePath(Activity context, Uri uri){
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = context.managedQuery(uri,proj,null,null,null);
		int actualImageColumnIndex = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		String imgPath = actualimagecursor.getString(actualImageColumnIndex);
		return imgPath;
	}
	
	public static void showMsg(final Activity act,final String msg, final boolean isLongShow){
		act.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(isLongShow){
					Toast.makeText(act, msg, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	
	public static Intent openFile(String filePath) {

		File file = new File(filePath);
		if (!file.exists())
			return null;
		/* 取得扩展名 */
		String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
			return getImageFileIntent(filePath);
		} else if (end.equals("apk")) {
			return getApkFileIntent(filePath);
		} else if (end.equals("ppt")) {
			return getPptFileIntent(filePath);
		} else if (end.equals("xls")) {
			return getExcelFileIntent(filePath);
		} else if (end.equals("doc")) {
			return getWordFileIntent(filePath);
		} else if (end.equals("pdf")) {
			return getPdfFileIntent(filePath);
		} else if (end.equals("chm")) {
			return getChmFileIntent(filePath);
		} else if (end.equals("txt")) {
			return getTextFileIntent(filePath, false);
		} else {
			return getAllIntent(filePath);
		}
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取一个用于打开Html文件的intent
	public static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}
	
}
