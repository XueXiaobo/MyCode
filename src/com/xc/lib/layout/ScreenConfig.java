package com.xc.lib.layout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 默认目标机器 1280*720 密度 2.0f mi 2s galaxy nexus
 * 
 * @author 62568_000
 * 
 */
public class ScreenConfig {

	public static ReSizeFilter rsFilter;

	public static boolean isDebug = true;
	/**
	 * 宽度与1280*720比列
	 */
	public static float RATE_W;
	/**
	 * 高度与1280*720比列 计算控件
	 */
	public static float RATE_H; //
	public static float MINRATE; // 最小的比例
	public static boolean INIT = false;
	public static boolean INITBAR = false;

	public static float ABS_RATEW;
	public static float ABS_RATEH;
	/**
	 * 当前屏幕的宽度
	 */
	public static int SCRREN_W;
	/**
	 * 当前屏幕的高度度
	 */
	public static int SCRREN_H;
	/**
	 * 状态栏的高度
	 */
	public static int STATUSBARHEIGHT;
	/**
	 * 密度
	 * 
	 */
	public static float DENSITY;
	public static float SCALEDENSITY;

	private static float RATIOWIDTH = 720;

	private static float RATIONHEIGHT = 1280;

	private static float initDENSITY = 2.0f; // 默认2.0
	public static float initScaleDENSITY = 1.72f; // 默认2.0

	public static void setRatioDensity(float density, float scaleDensity) {
		initDENSITY = density;
		initScaleDENSITY = scaleDensity;
	}

	public static void setRatioScreen(int width, int height) {
		RATIOWIDTH = width;
		RATIONHEIGHT = height;
	}

	/**
	 * 初始化屏幕数据
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		// 以1280*720为例
		if (!INIT) {
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			SCRREN_W = dm.widthPixels;
			SCRREN_H = dm.heightPixels;
			DENSITY = dm.density;
			SCALEDENSITY = dm.scaledDensity;
			ABS_RATEW = SCRREN_W / RATIOWIDTH;
			ABS_RATEH = SCRREN_H / RATIONHEIGHT;
			RATE_W = (SCRREN_W * initDENSITY) / (RATIOWIDTH * DENSITY);
			RATE_H = (SCRREN_H * initDENSITY) / (RATIONHEIGHT * DENSITY);
			MINRATE = Math.min(RATE_H, RATE_W);
			STATUSBARHEIGHT = (int) (26 * DENSITY);
			INIT = true;
		}
	}

	public static void setStatusHeight(int height) {
		STATUSBARHEIGHT = height;
		if (STATUSBARHEIGHT != 0) {
			INITBAR = true;
		}
	}

	private static int count = 0;

	private static List<View> views = new ArrayList<View>();
	private static List<View> views2 = new ArrayList<View>();

	public static void addView(View view) {
		views.add(view);
	}

	public static void addMargView(View view) {
		views2.add(view);
	}

	private static void rateViews(Context context) {
		int len = views.size();
		for (int i = 0; i < len; i++) {
			LayoutUtils.rateScale(context, views.get(i), false);
		}
		views.clear();
	}

	private static void rateViews2(Context context) {
		if (views2 == null || views2.size() == 0)
			return;
		int len = views2.size();
		for (int i = 0; i < len; i++) {
			LayoutUtils.rateScaleAndMargin(context, views2.get(i), false);
		}
		views2.clear();
	}

	public static void initBar(final Activity activity, final View v) {
		if (count != 0)
			return;
		count++;
		v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				if (!INITBAR) {
					Rect rect = new Rect();
					activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
					setStatusHeight(rect.top);
					rateViews(activity);
					rateViews2(activity);
				}
				v.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});
	}
}
