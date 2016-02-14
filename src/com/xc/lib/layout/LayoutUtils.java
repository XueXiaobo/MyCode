package com.xc.lib.layout;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
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
		rateScale(context, v, isMin, false);
		// if (v == null)
		// return;
		// if (!ScreenConfig.INIT)
		// ScreenConfig.init(context);
		// if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
		// // 适配宽高，和margin
		// ViewGroup.MarginLayoutParams params = (MarginLayoutParams)
		// v.getLayoutParams();
		// params.width = params.width <= 0 ? params.width :
		// getRate4density(params.width);
		// if (!isMin) {
		// params.height = params.height <= 0 ? params.height : (int)
		// (params.height * ScreenConfig.RATE_H + 0.5f) -
		// ScreenConfig.STATUSBARHEIGHT / 2;
		// } else {
		// params.height = params.height <= 0 ? params.height :
		// getRate4density(params.height);
		// }
		// params.leftMargin = params.leftMargin == 0 ? params.leftMargin :
		// getRate4density(params.leftMargin);
		// params.rightMargin = params.rightMargin == 0 ? params.rightMargin :
		// getRate4density(params.rightMargin);
		// params.topMargin = params.topMargin == 0 ? params.topMargin :
		// getRate4density(params.topMargin);
		// params.bottomMargin = params.bottomMargin == 0 ? params.bottomMargin
		// : getRate4density(params.bottomMargin);
		// v.setLayoutParams(params);
		//
		// } else { // 其他父控件只能适配宽高，
		// ViewGroup.LayoutParams params = v.getLayoutParams();
		// params.width = params.width <= 0 ? params.width :
		// getRate4density(params.width);
		// if (!isMin) {
		// params.height = params.height <= 0 ? params.height : (int)
		// (params.height * ScreenConfig.RATE_H + 0.5f) -
		// ScreenConfig.STATUSBARHEIGHT / 2;
		// } else {
		// params.height = params.height <= 0 ? params.height :
		// getRate4density(params.height);
		// }
		// }
		// // 适配padding
		// v.setPadding(getRate4density(v.getPaddingLeft()),
		// getRate4density(v.getPaddingTop()),
		// getRate4density(v.getPaddingRight()),
		// getRate4density(v.getPaddingBottom()));

	}

	public static void rateScale(Context context, View v, boolean isMin, boolean isMargin) {
		if (v == null)
			return;
		if (!ScreenConfig.INIT)
			ScreenConfig.init(context);
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			// 适配宽高，和margin
			ViewGroup.MarginLayoutParams params = (MarginLayoutParams) v.getLayoutParams();
			params.width = params.width <= 0 ? params.width : getRate4density(params.width);
			if (!isMin) {
				params.height = params.height <= 0 ? params.height : getRate4densityH(params.height);
			} else {
				params.height = params.height <= 0 ? params.height : getRate4density(params.height);
			}
			params.leftMargin = params.leftMargin == 0 ? params.leftMargin : getRate4density(params.leftMargin);
			params.rightMargin = params.rightMargin == 0 ? params.rightMargin : getRate4density(params.rightMargin);
			params.topMargin = params.topMargin == 0 ? params.topMargin : (isMargin ? getRate4densityH(params.topMargin) : getRate4density(params.topMargin));
			params.bottomMargin = params.bottomMargin == 0 ? params.bottomMargin : (isMargin ? getRate4densityH(params.bottomMargin) : getRate4density(params.bottomMargin));
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
	 * 通过注解让view比例化
	 * 
	 * @param context
	 *            上下文
	 * @param viewholder
	 *            需要比例化view的持有者,如果需要view注入请实现
	 *            {@linkplain com.xc.lib.layout.FindView FindView}
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

	/**
	 * 找到view 在重新比例化尺寸
	 * 
	 * @param field
	 * @param handler
	 * @param context
	 */
	private static void reSize(Field field, Object handler, Context context) {
		Resize resize = field.getAnnotation(Resize.class);
		if (resize != null) {
			try {
				field.setAccessible(true);
				Object obj = field.get(handler);
				if (obj != null) {
					reSizeForObj(context, obj, resize, field);
				} else {// 如果当前字段为null ,则检查是否需要赋值
					if (resize.idEnable() && resize.id() != -1) {
						// 如果根据id 找到了相应的对象，则赋值
						View view = null;
						if (handler instanceof FindView) {
							view = ((FindView) handler).IfindView(resize.id());
						} else if (handler instanceof Activity) {
							view = ((Activity) handler).findViewById(resize.id());
						}
						if (view != null) {
							if (resize.onClick() && handler instanceof View.OnClickListener) {
								view.setOnClickListener((View.OnClickListener) handler);
							}
							field.set(handler, view);
							reSizeForObj(context, view, resize, field);
						} else {
							SysDeug.logI("field:" + field.getName() + "未找到 view");
						}
					} else {
						SysDeug.logI("该注解指向一个null对象 field:" + field.getName());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param context
	 * @param obj
	 * @param resize
	 * @param field
	 */
	private static void reSizeForObj(Context context, Object obj, Resize resize, Field field) {
		if (View.class.isAssignableFrom(obj.getClass())) {
			// 如果是view的子类才能进行
			if (resize.enable()) {
				if (!ScreenConfig.INITBAR && (context instanceof Activity)) {
					// 初始化titlebar
					ScreenConfig.initBar((Activity) context, (View) obj);
				}
				LayoutUtils.rateScale(context, (View) obj, resize.isMin(), resize.isMargin());
				SysDeug.logI("成功适配view field:" + field.getName());
			}
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

		} else {
			SysDeug.logI("该注解只能用于view变量 field:" + field.getName());
		}
	}

	/**
	 * 比例化整个layout,
	 * 
	 * @param context
	 * @param view
	 * @param init
	 */
	public static void rateLayout(Context context, View view, boolean init) {
		if (view != null && context != null && view.getTag() == null) {
			// 获取statebar高度
			if (!ScreenConfig.INITBAR && (context instanceof Activity)) {
				// 初始化titlebar
				ScreenConfig.initBar((Activity) context, view);
			}

			if (view instanceof ViewGroup) {
				if (!init) { // 如果是初始化则不用比例化
					LayoutUtils.rateScale(context, view, true);
					view.setTag("rate");
				}
				// 如果是类似listview，viewpager 不直接比例化 需要在adapter适配
				if (view instanceof AdapterView || view instanceof ViewPager) {
					return;
				}
				// 如果设置了过滤的view也不需要
				if (ScreenConfig.rsFilter != null && !ScreenConfig.rsFilter.isResize(view)) {
					return;
				}
				// 比例化子view
				ViewGroup group = (ViewGroup) view;
				int len = group.getChildCount();
				for (int i = 0; i < len; i++) {
					rateLayout(context, group.getChildAt(i), false);
				}

			} else {
				LayoutUtils.rateScale(context, view, true);
				if (view instanceof TextView) {
					LayoutUtils.setTextSizeForSp((TextView) view);
				}
				view.setTag("rate");
			}
		}
	}

	/**
	 * 
	 * @param context
	 * @param v
	 */
	public static void rateScaleAndMargin(Context context, View v, boolean isMin) {
		rateScale(context, v, isMin, true);
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

	public static int getRate4px(float value, float distWidth) {
		return (int) (value * (ScreenConfig.SCRREN_W / distWidth) + 0.5f);
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
	 * 根据目标密度来缩放 字体根据比例适配字体大小sp valule =
	 * (int)(16*ScreenConfig.SCALEDENSITY+0.5f) -> 算法可以了
	 */
	public static void setTextSizeForSp(TextView tv) {
		setTextSize(tv, (ScreenConfig.initScaleDENSITY * tv.getTextSize()) / ScreenConfig.SCALEDENSITY);
	}
}
