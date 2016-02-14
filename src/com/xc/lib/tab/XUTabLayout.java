package com.xc.lib.tab;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xc.lib.annotation.Resize;
import com.xc.lib.layout.FindView;
import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.view.MyPageAdapter;
import com.xc.lib.view.NoSlideViewPager;
import com.xxb.myutils.R;

/**
 * @author xxb
 * @version v1.0 创建时间：2016年2月14日 上午11:29:30
 */
public class XUTabLayout implements FindView, OnClickListener {
	@Resize(id = R.id.xuLayout)
	private RelativeLayout xuLayout;
	@Resize(id = R.id.tabViewPager)
	private NoSlideViewPager viewpager;
	@Resize(id = R.id.btab)
	private LinearLayout tabView;
	private Activity activity;
	private TabBaseAdapter adapter;

	public RelativeLayout getXuLayout() {
		return xuLayout;
	}

	public NoSlideViewPager getViewpager() {
		return viewpager;
	}

	public LinearLayout getTabView() {
		return tabView;
	}

	public TabBaseAdapter getAdapter() {
		return adapter;
	}

	public int getBottomTabHeight() {
		return bottomTabHeight;
	}

	/**
	 * 默认
	 */
	private int bottomTabHeight = LayoutUtils.getRate4px(60, 720);

	/**
	 * 设置底部tab高度
	 * 
	 * @param value
	 */
	public void setBottomTabHeight(int value) {
		this.bottomTabHeight = value;
	}

	public XUTabLayout(Activity activity) {
		this.activity = activity;
		LayoutUtils.reSize(activity, this);
	}

	/**
	 * 设置适配器
	 * 
	 * @param adapter
	 */
	public void setAdapter(TabBaseAdapter adapter) {
		this.adapter = adapter;
		notifyData();
	}

	/**
	 * 添加一个tab到界面
	 * 
	 * @param views
	 * @param tabitem
	 * @param index
	 */
	private void addTab(List<View> views, TabItem tabitem, int index) {
		views.add(tabitem.tabContent.getView());
		// 添加view进去
		View cTabView = tabitem.tabView.getView();
		cTabView.setOnClickListener(this);
		tabView.addView(cTabView, new LinearLayout.LayoutParams(0, bottomTabHeight, 1));

	}

	private List<TabItem> items;

	/**
	 * 刷新界面
	 */
	public void notifyData() {
		tabView.removeAllViews();
		List<View> views = new ArrayList<View>();
		items = new ArrayList<TabItem>();
		int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			TabItem tab = adapter.getTab(i, tabView);
			items.add(tab);
			addTab(views, tab, i);
		}
		viewpager.setAdapter(new MyPageAdapter(views));
	}

	@Override
	public View IfindView(int id) {
		return activity.findViewById(id);
	}

	/**
	 * 设置选中的状态
	 * 
	 * @param view
	 */
	private void setCurrentByView(View view) {
		// 改变界面
		int len = items.size();
		for (int i = 0; i < len; i++) {
			TabItem item = items.get(i);
			if (view == item.tabView.getView()) {
				item.tabView.onSelected();
				viewpager.setCurrentItem(i, false);
			} else {
				item.tabView.unSelected();
			}
		}
	}

	/**
	 * 设置下标
	 * 
	 * @param index
	 */
	public void setCurrentIndex(int index) {
		try {
			setCurrentByView(items.get(index).tabView.getView());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		setCurrentByView(v);
	}

}
