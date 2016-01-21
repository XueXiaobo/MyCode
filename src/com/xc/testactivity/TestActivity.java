package com.xc.testactivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.xc.lib.activity.BaseActivity;
import com.xc.lib.annotation.Resize;
import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.layout.ScreenConfig;
import com.xxb.myutils.R;

public class TestActivity extends BaseActivity implements OnClickListener {
	@Resize(id = R.id.shipei)
	private View shipei;

	@Resize(textSize = 32, id = R.id.test, onClick = true)
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScreenConfig.init(this);
		setContentView(R.layout.activity_main);
		LayoutUtils.reSize(this, this);

		System.out.println(" id : " + shipei.getId() + " tv: " + tv.getId());
		// view 适配多种屏幕
		// LayoutUtils.rateScale(this, findViewById(R.id.shipei), true);
		// 适配文字
		// LayoutUtils.setTextSize(new TextView(this), 20);
		// Sys(getWindow().getDecorView(), null, true);
	}

	@Override
	public void onClick(View v) {
		System.out.println(" v : " + v);
	}
}
