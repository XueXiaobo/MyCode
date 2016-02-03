package com.xc.lib.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public abstract class BaseListToGridAdapter extends BaseAdapter {

	private int num = 1;
	private OnItemClickListener onitem;

	@Override
	public int getCount() {
		return (int) Math.ceil(getChildCount() / (float) num);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout = null;
		if (convertView == null) {
			layout = new LinearLayout(getContext());
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setTag(new SparseArray<View>());
			layout.setWeightSum(num);
		} else {
			layout = (LinearLayout) convertView;
		}
		int id1 = num * position;
		for (int i = 0; i < num; i++) {
			addView(layout, id1 + i, parent, i);
		}
		return layout;
	}

	public void addView(LinearLayout layout, int position, ViewGroup parent, int tag) {
		SparseArray<View> list = (SparseArray<View>) layout.getTag();
		View view = list.get(tag);
		if (position < getChildCount()) {
			if (view == null) {
				view = getChildView(position, view, parent);
				list.put(tag, view);
				layout.addView(view);
				setWeight(view, 1);
			} else {
				view.setVisibility(View.VISIBLE);
				getChildView(position, view, parent);
			}
		} else {
			if (view != null)
				view.setVisibility(View.INVISIBLE);

		}
		if (view != null)
			setClick(view, new BaseListToGridAdapter.OnClick((AdapterView<?>) parent, position));

	}

	public class OnClick implements OnClickListener {
		private int position;
		private AdapterView<?> parent;

		public OnClick(AdapterView<?> parent, int position) {
			this.position = position;
			this.parent = parent;
		}

		@Override
		public void onClick(View v) {
			if (onitem != null && position < getChildCount()) {
				onitem.onItemClick(parent, v, position, position);
			}
		}
	}

	private void setWeight(View view, int weight) {
		LinearLayout.LayoutParams params = (LayoutParams) view.getLayoutParams();
		if (params.weight != weight) {
			params.weight = weight;
			params.width = 0;
			view.setLayoutParams(params);
		}
	}

	private void setClick(View view, OnClickListener click) {
		// if (view instanceof ViewGroup) {
		// int len = ((ViewGroup) view).getChildCount();
		// for (int i = 0; i < len; i++) {
		// setClick(((ViewGroup) view).getChildAt(i), click);
		// }
		// }
		view.setOnClickListener(click);
	}

	public void setOnItemClickListener(OnItemClickListener onitemclicklistener) {
		this.onitem = onitemclicklistener;
	}

	public void setNumColumns(int num) {
		this.num = num;
	}

	public int getNumColumns() {
		return num;
	}

	public abstract Context getContext();

	public abstract int getChildCount();

	public abstract View getChildView(int position, View convertView, ViewGroup parent);

}
