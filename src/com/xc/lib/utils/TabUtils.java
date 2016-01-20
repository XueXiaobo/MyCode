package com.xc.lib.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.xc.lib.tab.BaseTabInterface;
import com.xc.lib.tab.TabConfig;

public class TabUtils implements OnClickListener, BaseTabInterface {

	private TabConfig tabconfig;
	private int current = -1;
	private ViewGroup replace;
	private List<View> list = new ArrayList<View>();

	private void init() {
		int len = tabconfig.getCount();
		for (int i = 0; i < len; i++) {
			View view = tabconfig.get(i).tabView;
			view.setOnClickListener(this);
			view.setTag(i);
			if (view instanceof ViewGroup) {
				addClick((ViewGroup) view, i);
			}
		}
	}

	private void addClick(ViewGroup group, int tag) {
		int len = group.getChildCount();
		for (int i = 0; i < len; i++) {
			View view = group.getChildAt(i);
			view.setOnClickListener(this);
			view.setTag(tag);
			if (view instanceof ViewGroup) {
				addClick((ViewGroup) view, tag);
			}
		}
	}

	private OnItemClicklistener onitem;

	public interface OnItemClicklistener {
		public void onChange(View tabchild, int postiom);
	}

	/**
	 * 
	 * @param tabConfig
	 *            tab和被替换的页面的配置
	 * @param replaceContent
	 *            需要变化的空间
	 * @param onitem
	 *            变化的监听
	 */
	public void setOnChanView(TabConfig tabConfig, ViewGroup replaceContent,
			OnItemClicklistener onitem) {
		if (tabConfig == null) {
			Log.e("tabutils", "tabconfig -- null");
			return;
		}
		this.replace = replaceContent;
		this.tabconfig = tabConfig;
		this.onitem = onitem;
		init();
		replace(0);

	}

	@Override
	public void onClick(View v) {
		int ta = -1;
		try {
			ta = (Integer) v.getTag();
		} catch (Exception e) {
		}
		if (ta >= 0 && ta != current) {
			tabconfig.get(current).replace.getView().setVisibility(
					View.INVISIBLE);
			replace(ta);
			if (onitem != null)
				onitem.onChange(tabconfig.get(ta).tabView, ta);
		}
	}

	public void check(int count) {
		if (current != count) {
			tabconfig.get(current).replace.getView().setVisibility(
					View.INVISIBLE);
			View view = tabconfig.get(count).tabView;
			if (view instanceof RadioButton) {
				((RadioButton) view).setChecked(true);
			} else if (view instanceof CheckBox) {
				((CheckBox) view).setChecked(true);
			}
			replace(count);
		}

	}

	private void replace(int tag) {
		if (tag >= 0 && tag != current && tabconfig != null) {
			current = tag;
			View view = tabconfig.get(current).replace.getView();
			if (!list.contains(view)) {
				list.add(view);
				replace.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
			} else {
				view.setVisibility(View.VISIBLE);
			}
		}

	}

	@Override
	public void onCreate() {
		if (tabconfig != null)
			tabconfig.onCreate();
	}

	@Override
	public void onResume() {
		if (tabconfig != null)
			tabconfig.onResume();
	}

	@Override
	public void onStart() {
		if (tabconfig != null)
			tabconfig.onStart();
	}

	@Override
	public void onPause() {
		if (tabconfig != null)
			tabconfig.onPause();
	}

	@Override
	public void onStop() {
		if (tabconfig != null)
			tabconfig.onStop();
	}

	@Override
	public void onDestroy() {
		if (tabconfig != null)
			tabconfig.onDestroy();
	}
}
