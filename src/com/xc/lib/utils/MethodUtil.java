package com.xc.lib.utils;

import java.lang.reflect.Method;

/**
 * @author xxb
 * @version v1.0 创建时间：2016年1月18日 下午1:48:28
 */
public class MethodUtil {
	/**
	 * 根据对象调用指定的方法
	 * @param obj  执行的对象
	 * @param methodName 方法名
	 * @param objects 参数对象
	 */
	public static void invoke(Object obj, String methodName, Object... objects) {
		try {
			Method mtds = null;
			if (objects == null || objects.length == 0) {
				mtds = obj.getClass().getDeclaredMethod(methodName);
				mtds.setAccessible(true);
				mtds.invoke(obj);
			} else {
				int len = objects.length;
				Class<?>[] clParams = new Class<?>[len];
				for (int i = 0; i < len; i++) {
					clParams[i] = objects[i].getClass();
				}
				mtds = obj.getClass().getDeclaredMethod(methodName, clParams);
				mtds.setAccessible(true);
				mtds.invoke(obj, objects);
			}
		} catch (Exception e) {
			System.out.println("未找到方法 : " + e.toString());
		}
	}
	
	/**
	 *  调用 方法
	 * @param classPath 调用类路径
	 * @param methodName 调用的方法
	 * @param params 调用传的参数
	 */
	public static void invoke(String classPath, String methodName, Object... params) {
		try {
			invoke(Class.forName(classPath).newInstance(), methodName, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
