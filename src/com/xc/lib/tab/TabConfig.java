package com.xc.lib.tab;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.RadioButton;

public class TabConfig implements BaseTabInterface {
	private List<TabItem> views = new ArrayList<TabItem>();;

	@Override
	public void onCreate() {
		run(0);
	}

	@Override
	public void onResume() {
		run(1);
	}

	@Override
	public void onStart() {
		run(2);
	}

	@Override
	public void onPause() {
		run(3);
	}

	@Override
	public void onStop() {
		run(4);
	}

	@Override
	public void onDestroy() {
		run(5);
	}

	public void setCheck(int index, int vis) {
		final TabItem ti = views.get(index);
		ti.replace.getView().setVisibility(vis);
		if (ti.tabView instanceof RadioButton) {
			((RadioButton) ti.tabView).setChecked(vis == View.VISIBLE ? true
					: false);
		}
	}

	private void run(int what) {
		if (views == null)
			return;
		int len = views.size();
		for (int i = 0; i < len; i++) {
			switch (what) {
			case 0:
				views.get(i).replace.onCreate();
				break;
			case 1:
				views.get(i).replace.onResume();
				break;
			case 2:
				views.get(i).replace.onStart();
				break;
			case 3:
				views.get(i).replace.onPause();
				break;
			case 4:
				views.get(i).replace.onStop();
				break;
			case 5:
				views.get(i).replace.onDestroy();
				break;
			}
		}
	}

	public void addItem(TabItem tab) {
		if (!views.contains(tab))
			views.add(tab);
	}

	public void addItem(BaseTabView replace, View tabView) {
		addItem(new TabItem(replace, tabView));
	}

	public TabItem get(int index) {
		return views.get(index);
	}

	public int getCount() {
		return views.size();
	}

}
