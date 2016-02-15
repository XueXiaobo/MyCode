package com.xc.testactivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xc.lib.activity.XBaseActivity;
import com.xc.lib.tab.BaseTabView;
import com.xc.lib.tab.TabBaseAdapter;
import com.xc.lib.tab.TabChild;
import com.xc.lib.tab.TabItem;
import com.xc.lib.tab.XUTabLayout;
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
		
	}

	
	class MyAdapter implements TabBaseAdapter{

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public TabItem getTab(int position, ViewGroup parent) {
			TextView content = new TextView(mContext);
			content.setText("这是第"+position);
			content.setGravity(Gravity.CENTER);
			TextView tab = new TextView(mContext);
			tab.setText("这是tab"+position);
			tab.setGravity(Gravity.CENTER);
			return new TabItem(new MyTabContent(content), new MyTabChild(tab));
		}
		
	}
	
	class MyTabChild extends TabChild{
		private View view;
		public MyTabChild(View view){
			this.view = view;
		}
		@Override
		public View getView() {
			return view;
		}
		@Override
		public void onSelected() {
			System.out.println("on-Select");
			super.onSelected();
		}
		@Override
		public void unSelected() {
			System.out.println("un-Select");
			super.unSelected();
		}
		
	}
	
	class MyTabContent extends BaseTabView{
		private View view;
		public MyTabContent(View view){
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
