package com.xc.lib.view;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPageAdapter extends PagerAdapter {

	private List<View> views;
	private int size = 0;

	public MyPageAdapter(List<View> views) {
		this.views = views;
		size = views == null ? 0 : views.size();
	}

	public void reFresh(List<View> views) {
		this.views = views;
		size = views == null ? 0 : views.size();
		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return size;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (size == 0)
			return;
		container.removeView(views.get(position % size));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		try {
			container.addView(views.get(position % size),
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return views.get(position % size);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
