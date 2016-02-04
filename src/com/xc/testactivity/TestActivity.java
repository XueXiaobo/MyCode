package com.xc.testactivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.xc.lib.activity.BaseActivity;
import com.xc.lib.view.XTitleBar;
import com.xxb.myutils.R;

public class TestActivity extends BaseActivity implements OnClickListener {
	private XTitleBar titlebar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rateAll();
		titlebar = XTitleBar.createDefault(this);
		titlebar.setLeft(titlebar.leftDefaultBg,"可以的");
		titlebar.setMid("可以测试啊");
		titlebar.setRight("右边");
//		titlebar.setDelegate(new XTitleBar.XTitlebarDelegate() {
//			
//			@Override
//			public void onTitleClick(int clickTag) {
//				System.out.println("tag : " + clickTag);
//			}
//		});
	}

	@Override
	public void onClick(View v) {
	}
}
