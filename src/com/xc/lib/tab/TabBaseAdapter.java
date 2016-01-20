package com.xc.lib.tab;

import android.view.ViewGroup;

public interface TabBaseAdapter {
	public int getCount();

	public TabItem getTab(int position, ViewGroup parent);
}
