package com.xc.lib.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;

public final class MIUIUitls {

	private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
	private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
	private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

	public static boolean isMIUI() {
		try {
			final BuildProperties prop = BuildProperties.newInstance();
			return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
		} catch (final IOException e) {
			return false;
		}
	}

	/**
	 * 判断MIUI的悬浮窗权限
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMiuiFloatWindowOpAllowed(Context context) {
		final int version = Build.VERSION.SDK_INT;

		if (version >= 19) {
			return checkOp(context, 24); // 自己写就是24 为什么是24?看AppOpsManager
		} else {
			if ((context.getApplicationInfo().flags & 1 << 27) == 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean checkOp(Context context, int op) {
		final int version = Build.VERSION.SDK_INT;
		if (version >= 19) {
			AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
			try {
				Integer in = (Integer) invokeMethod(manager, "checkOp", op, Binder.getCallingUid(), context.getPackageName());
				if (AppOpsManager.MODE_ALLOWED == in) { // 这儿反射就自己写吧
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
		}
		return false;
	}

	public static Object invokeMethod(AppOpsManager manager, String methodName, Object... args) {
		Class clazz = manager.getClass();
		Method method;
		Object obj = null;
		try {
			method = getMethod(clazz, methodName,args[0].getClass(),args[1].getClass(),args[2].getClass());
			obj = method.invoke(manager, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;

	}

	public static Method getMethod(Class clazz, String methodName, Class... classs) throws Exception {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, classs);
		} catch (NoSuchMethodException e) {
			try {
				method = clazz.getMethod(methodName, classs);
			} catch (NoSuchMethodException ex) {
				if (clazz.getSuperclass() == null) {
					return method;
				} else {
					method = getMethod(clazz.getSuperclass(), methodName, classs);
				}
			}
		}
		method.setAccessible(true);
		return method;
	}

}
