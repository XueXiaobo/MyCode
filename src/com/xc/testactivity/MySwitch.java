package com.xc.testactivity;

import android.content.Context;
import android.util.AttributeSet;

import com.xc.lib.view.ToggleButton;

public class MySwitch extends ToggleButton {

	public MySwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected ToggleConfig getConfig() {
		return null;
	}

}
