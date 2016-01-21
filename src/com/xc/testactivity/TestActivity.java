package com.xc.testactivity;

import android.os.Bundle;
import android.view.View;

import com.xc.lib.activity.BaseActivity;
import com.xc.lib.annotation.Resize;
import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.layout.ScreenConfig;
import com.xxb.myutils.R;

public class TestActivity extends BaseActivity {
	@Resize
	private String view = "";
	@Resize
	private View shipei;
	@Resize
	private View test;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScreenConfig.init(this);
		setContentView(R.layout.activity_main);
		shipei = findViewById(R.id.shipei);
		LayoutUtils.reSize(this, this);
		// view 适配多种屏幕
//		LayoutUtils.rateScale(this, findViewById(R.id.shipei), true);
		// 适配文字
		// LayoutUtils.setTextSize(new TextView(this), 20);
		// Sys(getWindow().getDecorView(), null, true);
	}
}
