package com.xc.testactivity;

import android.animation.ObjectAnimator;
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
	@Resize(id = R.id.test, onClick = true)
	private TextView tv;
	@Resize(id = R.id.layout)   //先关闭  试试，再打开  可以看到适配效果。  最佳机型   mi2s
	private View layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScreenConfig.init(this);
		setContentView(R.layout.activity_main);
		LayoutUtils.reSize(this, this); // 注解适配
		// 单个适配
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
