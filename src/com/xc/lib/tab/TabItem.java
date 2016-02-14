package com.xc.lib.tab;

public class TabItem {
	public TabChild tabView;
	public BaseTabView tabContent;

	/**
	 * 
	 * @param replace
	 *            需要变化的页面
	 * @param tabView
	 *            标签的view
	 */
	public TabItem(BaseTabView tabContent, TabChild tabView) {
		this.tabContent = tabContent;
		this.tabView = tabView;
	}
}
