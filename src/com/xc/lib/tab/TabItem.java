package com.xc.lib.tab;

import android.view.View;

public class TabItem {
	public View tabView;
	public BaseTabView replace;
	/**
	 * 
	 * @param replace   需要变化的页面
	 * @param tabView   标签的view
	 */
	public TabItem(BaseTabView replace, View tabView) {
		this.replace = replace;
		this.tabView = tabView;
	}
}
