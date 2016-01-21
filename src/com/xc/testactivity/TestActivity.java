package com.xc.testactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xc.lib.activity.BaseActivity;
import com.xc.lib.annotation.Resize;
import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.layout.ScreenConfig;
import com.xxb.myutils.R;

public class TestActivity extends BaseActivity {
	@Resize
	private View shipei;
	
	@Resize(textEnable = true, textSize = 32)
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScreenConfig.init(this);
		setContentView(R.layout.activity_main);
		shipei = findViewById(R.id.shipei);
		tv = (TextView) findViewById(R.id.test);
		LayoutUtils.reSize(this, this);
		// view 适配多种屏幕
		// LayoutUtils.rateScale(this, findViewById(R.id.shipei), true);
		// 适配文字
		// LayoutUtils.setTextSize(new TextView(this), 20);
		// Sys(getWindow().getDecorView(), null, true);
	}
}
