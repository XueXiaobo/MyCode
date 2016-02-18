package com.xc.lib.tab;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
public class XUTabLayout implements FindView, OnClickListener, OnPageChangeListener {
	// 绑定view
	@Resize(id = R.id.xuLayout)
	private RelativeLayout xuLayout;
	@Resize(id = R.id.tabViewPager)
	private NoSlideViewPager viewpager;
	@Resize(id = R.id.btab)
	private LinearLayout tabView;
	private Activity activity;
	private TabBaseAdapter adapter;
	/**
	 * 当前滑动模式
	 */
	private TabMode currentMode = TabMode.NORMAL;

	/**
	 * 当前下标
	 */
	private int currentIndex = 0;

	/**
	 * 可以include xu_tablayout 或者自定义 只要对应 xuLayout,btab,tabViewPager id就可以了
	 * 
	 * @param activity
	 */
	public XUTabLayout(Activity activity) {
		this.activity = activity;
		LayoutUtils.reSize(activity, this);
		viewpager.setOnPageChangeListener(this);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

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

	/**
	 * 设置滑动模式
	 */
	public void setMode(TabMode model) {
		this.currentMode = model;
		if (model == TabMode.SCROLL) {
			viewpager.setCanScroll(true);
		} else {
			viewpager.setCanScroll(false);
		}
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
		// 天假tab 到底部布局上面
		tabView.addView(cTabView, new LinearLayout.LayoutParams(0, bottomTabHeight, 1));
		if (index == 0) {// 默认选中第一个
			tabitem.tabView.onSelected();
		}

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
			TabItem tab = adapter.getTab(i, viewpager, tabView);
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
	private void setCurrentIndex(int index) {
		// 改变界面
		int len = items.size();
		for (int i = 0; i < len; i++) {
			TabItem item = items.get(i);
			if (i == index) {
				this.currentIndex = i;
				// 改变tab状态
				item.tabView.onSelected();
			} else {// 改变tab状态
				item.tabView.unSelected();
			}
		}
	}

	private int findIndexByView(View v) {
		int len = items.size();
		for (int i = 0; i < len; i++) {
			if (v == items.get(i).tabView.getView())
				return i;
		}
		return -1;
	}

	@Override
	public void onClick(View v) {
		try {
			int index = findIndexByView(v);
			if (index != -1)
				viewpager.setCurrentItem(index, false);
		} catch (Exception e) {
		}
	}

	/**
	 * tab改变监听事件
	 */
	private TabChangeListener changelistener;

	/**
	 * 滑动数据的保存
	 */
	private RampScrollData rsd = new RampScrollData();

	public void setTabChangeListener(TabChangeListener changelistener) {
		this.changelistener = changelistener;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		rsd.setScrollState(state);
	}

	@Override
	public void onPageScrolled(int index, float offset, int offsetPixel) {
		rsd.setScroll(index, offset, offsetPixel);
	}

	@Override
	public void onPageSelected(int index) {
		if (changelistener != null) {
			changelistener.onChange(currentIndex, index);
		}
		// 滑动渐变不用这个
		if (currentMode == TabMode.NORMAL || currentMode == TabMode.SCROLL)
			setCurrentIndex(index);
	}

	// --------------------内部定义的一些类、枚举等

	/**
	 * 选中变化监听
	 * 
	 * 
	 */
	public interface TabChangeListener {
		/**
		 * 变化
		 */
		void onChange(int oldIndex, int newIndex);
	}

	/**
	 * 默认为normal 不滑动模式
	 * 
	 */
	public enum TabMode {
		SCROLL {
			@Override
			public String getName() {
				return "滑动模式";
			}
		},
		// 暂时未实现
		RAMPSCROLL {
			@Override
			public String getName() {
				return "滑动渐变-暂未实现";
			}
		},
		NORMAL {
			@Override
			public String getName() {
				return "正常模式";
			}
		};
		/**
		 * 获取描述
		 * 
		 * @return 描述
		 */
		public abstract String getName();
	}

	// 滑动数据
	class RampScrollData {

		private int state = 0;

		/**
		 * 设置滑动状态
		 * 
		 * @param state
		 */
		public void setScrollState(int state) {
			this.state = state;
		}

		/**
		 * 设置滑动数据
		 * 
		 * @param index
		 * @param offset
		 * @param offsetPixel
		 */
		public void setScroll(int index, float offset, int offsetPixel) {

		}
	}

}
