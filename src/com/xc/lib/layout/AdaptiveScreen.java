package com.xc.lib.layout;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class AdaptiveScreen {
	private Activity context;

	public AdaptiveScreen(Activity ac) {
		this.context = ac;
	}

	public <T extends View> T getRateView(View parent, int id, boolean isMin) {
		View childView = findViewById(parent, id);
		if (!ScreenConfig.INITBAR) {
			if (!isMin) {
				ScreenConfig.addView(childView);
			} else {
				rateView(childView, isMin);
			}
			ScreenConfig.initBar(context, childView);
		} else {
			rateView(childView, isMin);
		}
		return (T) childView;
	}

	public <T extends View> T getRateView(int id, boolean isMin) {
		View childView = context.findViewById(id);
		if (!ScreenConfig.INITBAR) {
			if (!isMin) {
				ScreenConfig.addView(childView);
			} else {
				rateView(childView, isMin);
			}
			ScreenConfig.initBar(context, childView);
		} else {
			rateView(childView, isMin);
		}
		return (T) childView;
	}

	private View findViewById(View parent, int id) {
		return parent.findViewById(id);
	}

	public <T extends View> T getTextView(View parent, int id, boolean isMin, float textSize) {
		View childView = getRateView(parent, id, isMin);
		if (childView instanceof TextView) {
			TextView tv = (TextView) childView;
			LayoutUtils.setTextSize(tv, textSize);
		}
		return (T) childView;
	}

	public <T extends View> T getTextView(int id, boolean isMin, float textSize) {
		View childView = getRateView(id, isMin);
		if (childView instanceof TextView) {
			TextView tv = (TextView) childView;
			LayoutUtils.setTextSize(tv, textSize);
		}
		return (T) childView;
	}

	/**
	 * 控件比例化
	 */
	protected void rateView(View v, boolean isMin) {
		LayoutUtils.rateScale(context, v, isMin);
	}
}
