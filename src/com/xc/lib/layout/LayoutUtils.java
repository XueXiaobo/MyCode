package com.xc.lib.layout;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.xc.lib.annotation.Resize;
import com.xc.lib.utils.SysDeug;
/**
 * 
 * @author xxb
 * @version 1.0
 * @see 创建时间：2016年1月21日 上午8:57:43
 *
 */
public class LayoutUtils {
	/**
	 * 对view进行比例化，达到适配多种屏幕
	 * 
	 * @param context
	 * @param v
	 */
	public static void rateScale(Context context, View v, boolean isMin) {
		if (v == null)
			return;
		if (!ScreenConfig.INIT)
			ScreenConfig.init(context);
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			// 适配宽高，和margin
			ViewGroup.MarginLayoutParams params = (MarginLayoutParams) v.getLayoutParams();
			params.width = params.width <= 0 ? params.width : getRate4density(params.width);
			if (!isMin) {
				params.height = params.height <= 0 ? params.height : (int) (params.height * ScreenConfig.RATE_H + 0.5f) - ScreenConfig.STATUSBARHEIGHT / 2;
			} else {
				params.height = params.height <= 0 ? params.height : getRate4density(params.height);
			}
			params.leftMargin = params.leftMargin == 0 ? params.leftMargin : getRate4density(params.leftMargin);
			params.rightMargin = params.rightMargin == 0 ? params.rightMargin : getRate4density(params.rightMargin);
			params.topMargin = params.topMargin == 0 ? params.topMargin : getRate4density(params.topMargin);
			params.bottomMargin = params.bottomMargin == 0 ? params.bottomMargin : getRate4density(params.bottomMargin);
			v.setLayoutParams(params);

		} else { // 其他父控件只能适配宽高，
			ViewGroup.LayoutParams params = v.getLayoutParams();
			params.width = params.width <= 0 ? params.width : getRate4density(params.width);
			if (!isMin) {
				params.height = params.height <= 0 ? params.height : (int) (params.height * ScreenConfig.RATE_H + 0.5f) - ScreenConfig.STATUSBARHEIGHT / 2;
			} else {
				params.height = params.height <= 0 ? params.height : getRate4density(params.height);
			}
		}
		// 适配padding
		v.setPadding(getRate4density(v.getPaddingLeft()), getRate4density(v.getPaddingTop()), getRate4density(v.getPaddingRight()), getRate4density(v.getPaddingBottom()));

	}

	/**
	 * 通过注解让view比例化,一定要在view初始化完了后才使用，不然无效果
	 * 
	 * @param context
	 *            上下文
	 * @param obj
	 *            需要比例化view的持有者
	 */
	public static void reSize(Context context, Object viewholder) {
		Field[] fields = viewholder.getClass().getDeclaredFields();
		if (fields != null) {
			int len = fields.length;
			for (int i = 0; i < len; i++) {
				Field field = fields[i];
				Class<?> fieldType = field.getType();
				if (/* 过滤基本类型 */fieldType.isPrimitive() ||
				/* 过滤数组 */fieldType.isArray()) {
					continue;
				}
				reSize(field, viewholder, context);
			}
		}
	}

	private static void reSize(Field field, Object handler, Context context) {
		Resize resize = field.getAnnotation(Resize.class);
		if (resize != null && resize.enable()) {
			try {
				field.setAccessible(true);
				Object obj = field.get(handler);
				if (obj != null) {
					if (View.class.isAssignableFrom(obj.getClass())) {
						// 如果是view的子类才能进行
						LayoutUtils.rateScale(context, (View) obj, false);
						// 如果使用文字适配并且是继承至TextView
						if (resize.textEnable() && TextView.class.isAssignableFrom(obj.getClass())) {
							int textSize = resize.textSize();
							SysDeug.logI("字体适配 field:" + field.getName() + " size : " + textSize);
							if (textSize != -1) {
								LayoutUtils.setTextSize((TextView) obj, textSize);
							} else {
								LayoutUtils.setTextSizeForSp((TextView) obj);
							}
						}
						SysDeug.logI("成功适配view field:" + field.getName());
					} else {
						SysDeug.logI("该注解只能用于view变量 field:" + field.getName());
					}
				} else {
					SysDeug.logI("该注解指向一个null对象 field:" + field.getName());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param context
	 * @param v
	 */
	public static void rateScaleAndMargin(Context context, View v, boolean isMin) {
		if (v == null)
			return;
		if (!ScreenConfig.INIT)
			ScreenConfig.init(context);
		if (v.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
			RelativeLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
			params.width = params.width <= 0 ? params.width : getRate4density(params.width);
			if (!isMin) {
				params.height = params.height <= 0 ? params.height : (int) (params.height * ScreenConfig.RATE_H + 0.5f) - ScreenConfig.STATUSBARHEIGHT / 2;
			} else {
				params.height = params.height <= 0 ? params.height : getRate4density(params.height);
			}
			params.leftMargin = params.leftMargin == 0 ? params.leftMargin : getRate4density(params.leftMargin);
			params.rightMargin = params.rightMargin == 0 ? params.rightMargin : getRate4density(params.rightMargin);
			params.topMargin = params.topMargin == 0 ? params.topMargin : getRate4densityH(params.topMargin);
			params.bottomMargin = params.bottomMargin == 0 ? params.bottomMargin : getRate4densityH(params.bottomMargin);
			v.setLayoutParams(params);

		} else if (v.getLayoutParams() instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) v.getLayoutParams();
			params.width = params.width <= 0 ? params.width : getRate4density(params.width);
			if (!isMin) {
				params.height = params.height <= 0 ? params.height : (int) (params.height * ScreenConfig.RATE_H + 0.5f) - ScreenConfig.STATUSBARHEIGHT / 2;
			} else {
				params.height = params.height <= 0 ? params.height : getRate4density(params.height);
			}
			params.leftMargin = params.leftMargin == 0 ? params.leftMargin : getRate4density(params.leftMargin);
			params.rightMargin = params.rightMargin == 0 ? params.rightMargin : getRate4density(params.rightMargin);

			params.topMargin = params.topMargin == 0 ? params.topMargin : getRate4densityH(params.topMargin);

			params.bottomMargin = params.bottomMargin == 0 ? params.bottomMargin : getRate4densityH(params.bottomMargin);
			v.setLayoutParams(params);
		}
		v.setPadding(getRate4density(v.getPaddingLeft()), getRate4density(v.getPaddingTop()), getRate4density(v.getPaddingRight()), getRate4density(v.getPaddingBottom()));

	}

	public static int getRate4density(float value) {
		return (int) (value * ScreenConfig.RATE_W + 0.5f);
	}

	public static int getRate4densityH(float value) {
		return (int) (value * ScreenConfig.RATE_H + 0.5f);
	}

	public static int getRate4px(float value) {
		return (int) (value * ScreenConfig.ABS_RATEW + 0.5f);
	}

	public static int getRate4pxH(float value) {
		return (int) (value * ScreenConfig.ABS_RATEH + 0.5f);
	}

	/**
	 * 字体根据比例适配字体大小 px
	 */
	public static void setTextSize(TextView tv, float value) {
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getRate4px(value));
	}

	/**
	 * 根据目标密度来缩放
	 * 字体根据比例适配字体大小sp valule = (int)(16*ScreenConfig.SCALEDENSITY+0.5f) -> 算法可以了
	 */
	public static void setTextSizeForSp(TextView tv) {
		setTextSize(tv, (ScreenConfig.initScaleDENSITY * tv.getTextSize()) / ScreenConfig.SCALEDENSITY);
	}
}
