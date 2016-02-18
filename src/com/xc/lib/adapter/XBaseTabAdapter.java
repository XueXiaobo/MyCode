package com.xc.lib.adapter;

import android.view.ViewGroup;

import com.xc.lib.tab.BaseTabView;
import com.xc.lib.tab.TabBaseAdapter;
import com.xc.lib.tab.TabChild;
import com.xc.lib.tab.TabItem;

/**
 * @author xxb
 * @version v1.0 创建时间：2016年2月15日 上午11:14:28
 */
public abstract class XBaseTabAdapter implements TabBaseAdapter {
	@Override
	public TabItem getTab(int position, ViewGroup cparent,ViewGroup tparent) {
		return new TabItem(getTabView(position, cparent), getTabBtn(position, tparent));
	}

	public abstract BaseTabView getTabView(int position, ViewGroup parent);

	public abstract TabChild getTabBtn(int position, ViewGroup parent);

}
