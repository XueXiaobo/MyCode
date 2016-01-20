package com.xc.lib.tab;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.xc.lib.utils.TabUtils;
import com.xc.lib.utils.TabUtils.OnItemClicklistener;

public class TabControl implements OnItemClicklistener {
	private ViewGroup replace;
	private LinearLayout tablayout;
	private TabBaseAdapter adapter;
	private OnItemClicklistener listener;
	private TabUtils tu;

	public TabControl(ViewGroup replace, LinearLayout tablayout) {
		this.replace = replace;
		this.tablayout = tablayout;
	}

	public void setAdapter(TabBaseAdapter adapter) {
		this.adapter = adapter;
		if (adapter != null)
			init();
	}

	public void setOnItemClickListener(OnItemClicklistener listener) {
		this.listener = listener;
	}

	@Override
	public void onChange(View tabchild, int postiom) {
		if (listener != null)
			listener.onChange(tabchild, postiom);
	}

	public void check(int count) {
		if (tu != null) {
			tu.check(count);
		}
	}

	private void init() {
		if (replace != null && tablayout != null) {
			tablayout.removeAllViews();
			TabConfig config = new TabConfig();
			tu = new TabUtils();
			int len = adapter.getCount();
			for (int i = 0; i < len; i++) {
				TabItem item = adapter.getTab(i, tablayout);
				tablayout.addView(item.tabView);
				LinearLayout.LayoutParams params = (LayoutParams) item.tabView
						.getLayoutParams();
				params.weight = 1;
				item.tabView.setLayoutParams(params);
				config.addItem(item);
			}
			tu.setOnChanView(config, replace, this);
		}
	}
}
