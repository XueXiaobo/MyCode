package com.xc.testactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xc.lib.activity.XBaseActivity;
import com.xc.lib.adapter.XBaseTabAdapter;
import com.xc.lib.tab.BaseTabView;
import com.xc.lib.tab.TabChild;
import com.xc.lib.tab.XUTabLayout;
import com.xc.lib.tab.XUTabLayout.TabMode;
import com.xc.lib.view.XTitleBar;
import com.xxb.myutils.R;

public class TestActivity extends XBaseActivity implements OnClickListener {
	private XTitleBar titlebar;
	private XUTabLayout xuTabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rateAll();
		titlebar = XTitleBar.createDefault(this);
		xuTabLayout = new XUTabLayout(this);

		titlebar.setLeft(titlebar.leftDefaultBg, "可以的");
		titlebar.setMid("可以测试啊");
		titlebar.setRight("右边");
		xuTabLayout.setAdapter(new MyAdapter());
		xuTabLayout.setMode(TabMode.SCROLL);

	}

	class MyAdapter extends XBaseTabAdapter {

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public BaseTabView getTabView(int position, ViewGroup parent) {
			TextView content = new TextView(mContext); // 获取对应的view
			content.setText("这是第" + position);
			content.setGravity(Gravity.CENTER);
			return new MyTabContent(content);
		}

		@Override
		public TabChild getTabBtn(int position, ViewGroup parent) {
			TextView tab = new TextView(mContext);// 获取对应tab
			tab.setText("这是tab" + position);
			tab.setGravity(Gravity.CENTER);
			return new MyTabChild(tab);
		}

	}

	class MyTabChild extends TabChild {
		private View view;

		public MyTabChild(View view) {
			this.view = view;
		}

		@Override
		public View getView() {
			return view;
		}

		@Override
		public void onSelected() {
			view.setBackgroundColor(Color.RED);
			super.onSelected();
		}

		@Override
		public void unSelected() {
			view.setBackgroundColor(Color.WHITE);
			super.unSelected();
		}

	}

	class MyTabContent extends BaseTabView {
		private View view;

		public MyTabContent(View view) {
			this.view = view;
		}

		@Override
		public View getView() {
			return view;
		}

	}

	@Override
	public void onClick(View v) {
	}
}
