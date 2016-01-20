package com.xc.lib.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyActionTextView extends TextView {

	public MyActionTextView(Context context) {
		super(context);
	}

	public MyActionTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyActionTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public StateListDrawable getDrawable(Drawable normal, Drawable pressed) {
		StateListDrawable sd = new StateListDrawable();
		// 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
		sd.addState(new int[] { android.R.attr.state_pressed }, pressed);

		sd.addState(new int[] {}, normal);
		return sd;
	}

}
